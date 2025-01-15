package org.compiere.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Maps;
import de.metas.ad_reference.ReferenceId;
import de.metas.cache.CCache;
import de.metas.i18n.po.POTrlInfo;
import de.metas.i18n.po.POTrlRepository;
import de.metas.logging.LogManager;
import de.metas.security.TableAccessLevel;
import de.metas.util.NumberUtils;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.TableAndColumnName;
import org.adempiere.ad.table.api.TableName;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.validationRule.AdValRuleId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.POWrapper;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.copy.ColumnCloningStrategy;
import org.compiere.model.copy.TableCloningEnabled;
import org.compiere.model.copy.TableDownlineCloningStrategy;
import org.compiere.model.copy.TableWhenChildCloningStrategy;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Persistent Object Info. Provides structural information
 *
 * @author Jorg Janke
 * @author Victor Perez, e-Evolution SC
 * <li>[ 2195894 ] Improve performance in PO engine
 * <li><a href="http://sourceforge.net/tracker/index.php?func=detail&aid=2195894&group_id=176962&atid=879335">http://sourceforge.net/tracker/index.php?func=detail&aid=2195894&group_id=176962&atid=879335</a>
 */
public final class POInfo implements Serializable, ColumnDisplayTypeProvider
{
	/**
	 * Used by Remote FinReport
	 */
	static final long serialVersionUID = -5976719579744948419L;

	@Nullable
	public static POInfo getPOInfo(final int AD_Table_ID)
	{
		return getPOInfo(null, AD_Table_ID, ITrx.TRXNAME_None);
	}

	@Nullable
	public static POInfo getPOInfo(@NonNull final AdTableId tableId)
	{
		return getPOInfo(tableId.getRepoId());
	}

	/**
	 * Please consider using {@link #getPOInfo(int)}.
	 *
	 * @param ctx_NOTUSED context
	 * @param AD_Table_ID AD_Table_ID
	 * @return POInfo
	 */
	@Nullable
	public static POInfo getPOInfo(final Properties ctx_NOTUSED, final int AD_Table_ID)
	{
		return getPOInfo(ctx_NOTUSED, AD_Table_ID, ITrx.TRXNAME_None);
	}

	@Nullable
	public static POInfo getPOInfo(@NonNull final String tableName)
	{
		return getPOInfoMap().getByTableNameOrNull(tableName);
	}

	@NonNull
	public static POInfo getPOInfoNotNull(@NonNull final String tableName)
	{
		return getPOInfoMap().getByTableName(tableName);
	}

	@NonNull
	public static POInfo getPOInfoNotNull(@NonNull final AdTableId adTableId)
	{
		return getPOInfoMap().getByTableId(adTableId);
	}

	public static Optional<POInfo> getPOInfoIfPresent(@NonNull final String tableName)
	{
		return Optional.ofNullable(getPOInfoMap().getByTableNameOrNull(tableName));
	}

	public static Optional<POInfo> getPOInfoIfPresent(@NonNull final AdTableId adTableId)
	{
		return Optional.ofNullable(getPOInfoMap().getByTableIdOrNull(adTableId));
	}

	/**
	 * Please consider using {@link #getPOInfo(int)}.
	 */
	@Nullable
	public static POInfo getPOInfo(
			@SuppressWarnings("unused") @Nullable final Properties ctx_NOTUSED,
			final int adTableRepoId,
			@SuppressWarnings("unused") @Nullable final String trxName_NOTUSED)
	{
		final AdTableId adTableId = AdTableId.ofRepoId(adTableRepoId);
		return getPOInfoMap().getByTableIdOrNull(adTableId);
	}

	/**
	 * Please consider using {@link #getPOInfo(String)}.
	 */
	@Nullable
	public static POInfo getPOInfo(
			@SuppressWarnings("unused") @Nullable final Properties ctx_NOTUSED,
			final String tableName,
			@SuppressWarnings("unused") @Nullable final String trxName_NOTUSED)
	{
		return getPOInfoMap().getByTableNameOrNull(tableName);
	}

	private static final Logger logger = LogManager.getLogger(POInfo.class);

	public static final String CACHE_PREFIX = "POInfo";
	private static final CCache<Integer, POInfoMap> poInfoMapCache = CCache.<Integer, POInfoMap>builder()
			.cacheName(CACHE_PREFIX)
			.expireMinutes(CCache.EXPIREMINUTES_Never)
			.additionalTableNameToResetFor(I_AD_Table.Table_Name)
			.additionalTableNameToResetFor(I_AD_Column.Table_Name)
			.additionalTableNameToResetFor(I_AD_Element.Table_Name)
			.additionalTableNameToResetFor(I_AD_Ref_Table.Table_Name)
			.additionalTableNameToResetFor(I_AD_Val_Rule.Table_Name)
			.build();

	@Getter
	@NonNull private final AdTableId adTableId;
	@NonNull private final String m_TableName;
	private final TableAccessLevel m_AccessLevel;
	private final boolean m_isView;
	private final ImmutableList<POInfoColumn> m_columns;
	private final ImmutableMap<String, Integer> columnName2columnIndex;
	private final ImmutableMap<AdColumnId, Integer> adColumnId2columnIndex;
	@Getter
	private final ImmutableSet<TableAndColumnName> tableAndRecordColumnNames;
	private final ImmutableList<String> m_keyColumnNames;
	/**
	 * Single Primary Key.
	 * <p>
	 * If table has composed primary key, this variable will be set to null
	 */
	@Nullable private final String m_keyColumnName;
	private final int firstValidId;

	/**
	 * Table needs to keep log
	 */
	private final boolean m_IsChangeLog;

	private final boolean m_HasStaleableColumns;
	@Getter private final int webuiViewPageLength;
	@Getter private final TableCloningEnabled cloningEnabled;
	@Getter private final TableWhenChildCloningStrategy whenChildCloningStrategy;
	@Getter private final TableDownlineCloningStrategy downlineCloningStrategy;

	private final String sqlWhereClauseByKeys;
	private final String sqlSelectByKeys;
	private final String sqlSelectColumns;
	private final String sqlSelect;

	private final POTrlInfo trlInfo;

	public static POInfoMap getPOInfoMap()
	{
		return poInfoMapCache.getOrLoad(0, POInfo::retrievePOInfoMap);
	}

	private static POInfoMap retrievePOInfoMap()
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT "
						   + "t.TableName, " // 1
						   + "c.ColumnName, " // 2
						   + "c.AD_Reference_ID, "        // 3
						   + "c.IsMandatory, " // 4
						   + "c.IsUpdateable, " // 5
						   + "c.DefaultValue, " // 6
						   + "e.Name, "                                                    // 7
						   + "e.Description, "                                                // 8
						   + "c.AD_Column_ID, "                                            // 9
						   + "c.IsKey,c.IsParent, "                                        // 10..11
						   + "c.AD_Reference_Value_ID, "                                    // 12
						   + "vr.Code, "                                                    // 13
						   + "c.FieldLength, c.ValueMin, c.ValueMax, c.IsTranslated"        // 14..17
						   + ",t.AccessLevel"                                                // 18
						   + ",c.ColumnSQL"                                                // 19
						   + ",c.IsEncrypted "                                                // 20
						   + ",c.IsAllowLogging"                                            // 21
						   + ",t.IsChangeLog "                                                // 22
						   + ",c.IsLazyLoading "                                            // 23
						   + ",c.IsCalculated "                                            // 24 // metas
						   + ",c.AD_Val_Rule_ID "                                            // 25 // metas
						   + ",t.AD_Table_ID "                                                // 26 // metas
						   + ",c." + I_AD_Column.COLUMNNAME_IsUseDocSequence                // 27 // metas: 05133
						   + ",c." + I_AD_Column.COLUMNNAME_IsStaleable                    // 28 // metas: 01537
						   + ",c." + I_AD_Column.COLUMNNAME_IsSelectionColumn                // 29 // metas
						   + ",t." + I_AD_Table.COLUMNNAME_IsView                            // 30 // metas
						   + ",c." + I_AD_Column.COLUMNNAME_IsRestAPICustomColumn              // 31
						   + ", rt_table.TableName AS AD_Reference_Value_TableName"
						   + ", rt_keyColumn.AD_Reference_ID AS AD_Reference_Value_KeyColumn_DisplayType"
						   + ", t." + I_AD_Table.COLUMNNAME_WEBUI_View_PageLength
						   + ", t." + I_AD_Table.COLUMNNAME_CloningEnabled
						   + ", t." + I_AD_Table.COLUMNNAME_DownlineCloningStrategy
						   + ", t." + I_AD_Table.COLUMNNAME_WhenChildCloningStrategy
						   + ", c." + I_AD_Column.COLUMNNAME_CloningStrategy + " AS columnCloningStrategy"
						   + ", c." + I_AD_Column.COLUMNNAME_IsIdentifier + " AS columnIsIdentifier"
		);
		sql.append(" FROM AD_Table t "
						   + " INNER JOIN AD_Column c ON (t.AD_Table_ID=c.AD_Table_ID) "
						   + " LEFT OUTER JOIN AD_Val_Rule vr ON (c.AD_Val_Rule_ID=vr.AD_Val_Rule_ID) "
						   + " INNER JOIN AD_Element e ON (c.AD_Element_ID=e.AD_Element_ID) "
						   + " LEFT OUTER JOIN AD_Ref_Table rt ON (rt.AD_Reference_ID=c.AD_Reference_Value_ID)"
						   + " LEFT OUTER JOIN AD_Table rt_table on (rt_table.AD_Table_ID=rt.AD_Table_ID)"
						   + " LEFT OUTER JOIN AD_Column rt_keyColumn on (rt_keyColumn.AD_Column_ID=rt.AD_Key)"
		);
		sql.append(" WHERE t.IsActive='Y' AND c.IsActive='Y'");
		sql.append(" ORDER BY t.TableName, c.ColumnName");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), ITrx.TRXNAME_None);
			rs = pstmt.executeQuery();

			final ArrayList<POInfo> poInfos = new ArrayList<>();
			POInfoHeader currentHeader = null;
			final ArrayList<POInfoColumn> currentColumns = new ArrayList<>(100);

			while (rs.next())
			{
				final POInfoHeader header = retrievePOInfoHeader(rs);
				final POInfoColumn column = retrievePOInfoColumn(rs);

				if (currentHeader == null || !currentHeader.equals(header))
				{
					if (currentHeader != null && !currentColumns.isEmpty())
					{
						final POInfo poInfo = new POInfo(currentHeader, currentColumns);
						poInfos.add(poInfo);
					}

					currentHeader = header;
					currentColumns.clear();
				}

				currentColumns.add(column);
			}

			if (currentHeader != null && !currentColumns.isEmpty())
			{
				final POInfo poInfo = new POInfo(currentHeader, currentColumns);
				poInfos.add(poInfo);
			}

			final POInfoMap result = new POInfoMap(poInfos);
			logger.info("Loaded {} in {}", result, stopwatch.stop());
			return result;
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	private POInfo(
			@NonNull final POInfoHeader header,
			@NonNull final List<POInfoColumn> columns)
	{
		final List<String> keyColumnNames = new ArrayList<>();
		final List<String> parentColumnNames = new ArrayList<>();
		boolean hasStaleableColumns = false;
		for (final POInfoColumn column : columns)
		{
			if (column.isKey())
			{
				keyColumnNames.add(column.getColumnName());
			}
			if (column.isParent())
			{
				parentColumnNames.add(column.getColumnName());
			}
			if (column.isStaleable())
			{
				hasStaleableColumns = true;
			}
		}

		this.m_TableName = header.getTableName();
		this.adTableId = header.getAdTableId();
		this.m_isView = header.isView();
		this.m_AccessLevel = header.getAccessLevel();
		this.m_IsChangeLog = header.isChangeLog();
		this.m_columns = ImmutableList.copyOf(columns);
		this.m_HasStaleableColumns = hasStaleableColumns;
		this.webuiViewPageLength = header.getWebuiViewPageLength();
		this.cloningEnabled = header.getCloningEnabled();
		this.whenChildCloningStrategy = header.getWhenChildCloningStrategy();
		this.downlineCloningStrategy = header.getDownlineCloningStrategy();

		//
		// Iterate columns and build pre-calculated values and indexes
		// NOTE: we would like to have the columnNames searched case-insensitive
		// because role's addAccessSQL parsers are using POInfo for checking column availability,
		// and ofc in SQL queries could be with ANY case...
		final ImmutableSortedMap.Builder<String, Integer> columnName2columnIndexBuilder = ImmutableSortedMap.orderedBy(String.CASE_INSENSITIVE_ORDER);
		final ImmutableMap.Builder<AdColumnId, Integer> adColumnId2columnIndexBuilder = ImmutableMap.builder();
		final List<String> translatedColumnNames = new ArrayList<>();
		for (int columnIndex = 0, columnsCount = m_columns.size(); columnIndex < columnsCount; columnIndex++)
		{
			final POInfoColumn columnInfo = m_columns.get(columnIndex);
			final String columnName = columnInfo.getColumnName();
			final AdColumnId adColumnId = columnInfo.getAD_Column_ID();

			columnName2columnIndexBuilder.put(columnName, columnIndex);
			adColumnId2columnIndexBuilder.put(adColumnId, columnIndex);

			if (columnInfo.isTranslated())
			{
				translatedColumnNames.add(columnName);
			}
		}
		this.columnName2columnIndex = columnName2columnIndexBuilder.build();
		this.adColumnId2columnIndex = adColumnId2columnIndexBuilder.build();

		//
		// Set Key Columns Info
		// First: check key columns
		if (!keyColumnNames.isEmpty())
		{
			this.m_keyColumnNames = ImmutableList.copyOf(keyColumnNames);

			// Set m_keyColumnName only if we have a single primary key
			if (keyColumnNames.size() == 1)
			{
				this.m_keyColumnName = keyColumnNames.get(0);
			}
			else
			{
				// FIXME: this case shall be forbidden, but i am letting out now because of "EDI_C_BPartner_Product_v" which is breaking this case
				this.m_keyColumnName = null;
			}
		}
		// Second: check parent columns
		else if (!parentColumnNames.isEmpty())
		{
			this.m_keyColumnNames = ImmutableList.copyOf(parentColumnNames);
			this.m_keyColumnName = null;
		}
		// Fallback: there are no keys nor parents
		else
		{
			this.m_keyColumnNames = ImmutableList.of();
			this.m_keyColumnName = null;
		}

		// First valid ID
		if (this.m_keyColumnName != null)
		{
			this.firstValidId = POWrapper.getFirstValidIdByColumnName(this.m_keyColumnName);
		}
		else
		{
			this.firstValidId = 0;
		}

		//
		// Correct IsLazyLoading,
		// i.e. Always load effective Key columns (Key or Parent)
		for (final POInfoColumn column : m_columns)
		{
			if (column.IsLazyLoading && m_keyColumnNames.contains(column.getColumnName()))
			{
				column.IsLazyLoading = false;
				logger.info("Column {}.{} was marked as IsLazyLoading but effectively it is an key column, we we set IsLazyLoading=false.", this.m_TableName, column.getColumnName());
			}
		}

		//
		// Setup some pre-built SQLs which are frequently used
		sqlSelectColumns = buildSqlSelectColumns();
		sqlSelect = buildSqlSelect();
		sqlWhereClauseByKeys = buildSqlWhereClauseByKeys();
		sqlSelectByKeys = buildSqlSelectByKeys();

		trlInfo = !translatedColumnNames.isEmpty()
				? POTrlRepository.instance.createPOTrlInfo(m_TableName, m_keyColumnName, translatedColumnNames)
				: POTrlInfo.NOT_TRANSLATED;

		tableAndRecordColumnNames = computeTableAndRecordIdColumnNames(columnName2columnIndex.keySet());
	}

	private static POInfoHeader retrievePOInfoHeader(@NonNull final ResultSet rs) throws SQLException
	{
		return POInfoHeader.builder()
				.tableName(rs.getString(I_AD_Table.COLUMNNAME_TableName))
				.adTableId(AdTableId.ofRepoId(rs.getInt(I_AD_Table.COLUMNNAME_AD_Table_ID)))
				.accessLevel(TableAccessLevel.forAccessLevel(rs.getString(I_AD_Table.COLUMNNAME_AccessLevel)))
				.isView(StringUtils.toBoolean(rs.getString(I_AD_Table.COLUMNNAME_IsView)))
				.isChangeLog(StringUtils.toBoolean(rs.getString(I_AD_Table.COLUMNNAME_IsChangeLog)))
				.webuiViewPageLength(Math.max(rs.getInt(I_AD_Table.COLUMNNAME_WEBUI_View_PageLength), 0))
				.cloningEnabled(TableCloningEnabled.ofCode(rs.getString(I_AD_Table.COLUMNNAME_CloningEnabled)))
				.whenChildCloningStrategy(TableWhenChildCloningStrategy.ofCode(rs.getString(I_AD_Table.COLUMNNAME_WhenChildCloningStrategy)))
				.downlineCloningStrategy(TableDownlineCloningStrategy.ofCode(rs.getString(I_AD_Table.COLUMNNAME_DownlineCloningStrategy)))
				.build();
	}

	private static POInfoColumn retrievePOInfoColumn(@NonNull final ResultSet rs) throws SQLException
	{
		final String m_TableName = rs.getString(I_AD_Table.COLUMNNAME_TableName);
		final String ColumnName = rs.getString(2);
		final int AD_Reference_ID = rs.getInt(3);
		final boolean IsMandatory = StringUtils.toBoolean(rs.getString(4));
		final boolean IsUpdateable = StringUtils.toBoolean(rs.getString(5));
		final String DefaultLogic = rs.getString(6);
		final String Name = rs.getString(7);
		final String Description = rs.getString(8);
		final AdColumnId AD_Column_ID = AdColumnId.ofRepoId(rs.getInt(9));
		final boolean isKeyColumn = StringUtils.toBoolean(rs.getString(10));
		final boolean isParentColumn = StringUtils.toBoolean(rs.getString(11));
		final ReferenceId AD_Reference_Value_ID = ReferenceId.ofRepoIdOrNull(rs.getInt(12));
		final TableName AD_Reference_Value_TableName = TableName.ofNullableString(rs.getString("AD_Reference_Value_TableName"));
		final int AD_Reference_Value_KeyColumn_DisplayType = rs.getInt("AD_Reference_Value_KeyColumn_DisplayType");
		// String ValidationCode = rs.getString(13);
		final int FieldLength = rs.getInt(14);
		final String ValueMin = rs.getString(15);
		final String ValueMax = rs.getString(16);
		final boolean IsTranslated = StringUtils.toBoolean(rs.getString(17));
		//
		final String ColumnSQL = rs.getString(19);
		final boolean IsEncrypted = StringUtils.toBoolean(rs.getString(20));
		final boolean IsAllowLogging = StringUtils.toBoolean(rs.getString(21));
		final boolean IsLazyLoading = StringUtils.toBoolean(rs.getString(23)); // metas
		final boolean IsCalculated = StringUtils.toBoolean(rs.getString(24)); // metas
		final AdValRuleId AD_Val_Rule_ID = AdValRuleId.ofRepoIdOrNull(rs.getInt(25)); // metas
		final boolean isUseDocumentSequence = StringUtils.toBoolean(rs.getString(I_AD_Column.COLUMNNAME_IsUseDocSequence)); // metas: 05133
		final boolean isStaleableColumn = StringUtils.toBoolean(rs.getString(I_AD_Column.COLUMNNAME_IsStaleable)); // metas: 01537
		final boolean isSelectionColumn = StringUtils.toBoolean(rs.getString(I_AD_Column.COLUMNNAME_IsSelectionColumn));
		final boolean isRestAPICustomColumn = StringUtils.toBoolean(rs.getString(I_AD_Column.COLUMNNAME_IsRestAPICustomColumn));
		final boolean isIdentifier = StringUtils.toBoolean(rs.getString("columnIsIdentifier"));
		final ColumnCloningStrategy cloningStrategy = ColumnCloningStrategy.ofCode(rs.getString("columnCloningStrategy"));

		final POInfoColumn col = new POInfoColumn(
				AD_Column_ID, m_TableName, ColumnName, ColumnSQL, AD_Reference_ID,
				IsMandatory,
				IsUpdateable,
				DefaultLogic,
				Name, // ColumnLabel
				Description, // ColumnDescription
				isKeyColumn,
				isParentColumn,
				AD_Reference_Value_ID,
				AD_Reference_Value_TableName,
				AD_Reference_Value_KeyColumn_DisplayType,
				AD_Val_Rule_ID,
				FieldLength,
				ValueMin,
				ValueMax,
				IsTranslated,
				IsEncrypted,
				IsAllowLogging,
				isRestAPICustomColumn,
				cloningStrategy,
				isIdentifier);
		col.IsLazyLoading = IsLazyLoading; // metas
		col.IsCalculated = IsCalculated; // metas
		col.IsUseDocumentSequence = isUseDocumentSequence; // metas: _05133
		col.IsStaleable = isStaleableColumn; // metas: 01537
		col.IsSelectionColumn = isSelectionColumn;
		return col;
	}

	private static ImmutableSet<TableAndColumnName> computeTableAndRecordIdColumnNames(final ImmutableSet<String> columnNames)
	{
		final ImmutableSet.Builder<TableAndColumnName> result = ImmutableSet.builder();
		for (final String columnName : columnNames)
		{
			final int idx = columnName.indexOf(ITableRecordReference.COLUMNNAME_Record_ID);
			final String prefix;
			if (idx < 0)
			{
				continue;
			}
			else if (idx == 0)
			{
				prefix = "";
			}
			else
			{
				prefix = columnName.substring(0, idx);
			}

			final String tableIdColumnName = getTableIdColumnNameByPrefix(prefix, columnNames);
			if (tableIdColumnName == null)
			{
				continue;
			}

			result.add(TableAndColumnName.ofTableAndColumnStrings(tableIdColumnName, columnName));
		}

		return result.build();
	}

	@Nullable
	private static String getTableIdColumnNameByPrefix(final String prefix, final ImmutableSet<String> columnNames)
	{
		// Try with Prefix_AD_Table_ID
		String tableColumnName = prefix + ITableRecordReference.COLUMNNAME_AD_Table_ID;
		if (columnNames.contains(tableColumnName))
		{
			return tableColumnName;
		}

		// try with Prefix_Table_ID
		tableColumnName = prefix + "Table_ID";
		if (columnNames.contains(tableColumnName))
		{
			return tableColumnName;
		}

		return null;
	}

	@Override
	public String toString()
	{
		return "POInfo[" + getTableName() + " / " + getAdTableId() + "]";
	}   // toString

	/**
	 * String representation for index
	 *
	 * @param index column index
	 * @return String Representation
	 */
	public String toString(final int index)
	{
		if (index < 0 || index >= m_columns.size())
		{
			return "POInfo[" + getTableName() + "-(InvalidColumnIndex=" + index + ")]";
		}
		return "POInfo[" + getTableName() + "-" + m_columns.get(index).toString() + "]";
	}   // toString

	/**
	 * Get Table Name
	 *
	 * @return Table Name
	 */
	public String getTableName()
	{
		return m_TableName;
	}

	/**
	 * @return table name (upper case)
	 */
	public String getTableNameUC()
	{
		return m_TableName.toUpperCase();
	}

	public int getAD_Table_ID()
	{
		return getAdTableId().getRepoId();
	}

	public boolean isView()
	{
		return m_isView;
	}

	/**
	 * @return single primary key or null
	 */
	@Nullable
	public String getKeyColumnName()
	{
		return m_keyColumnName;
	}

	public boolean isSingleKeyColumnName()
	{
		return m_keyColumnName != null;
	}

	public String getSingleKeyColumnName()
	{
		if (!isSingleKeyColumnName())
		{
			throw new AdempiereException("Table " + getTableName() + " does not have a single key column");
		}
		return getKeyColumnName();
	}

	/**
	 * @return list column names which compose the primary key or empty list; never return null
	 */
	@NonNull
	public List<String> getKeyColumnNames()
	{
		return m_keyColumnNames;
	}

	/**
	 * Creates a new array copy of {@link #getKeyColumnNames()}
	 *
	 * @return array of key column names
	 */
	public String[] getKeyColumnNamesAsArray()
	{
		return m_keyColumnNames.toArray(new String[0]);
	}

	public int getFirstValidId()
	{
		return firstValidId;
	}

	/**
	 * Get Table Access Level
	 *
	 * @return {@link TableAccessLevel}; never returns <code>null</code>
	 */
	public TableAccessLevel getAccessLevel()
	{
		return m_AccessLevel;
	}    // getAccessLevel

	/**
	 * Gets Table Access Level as integer.
	 *
	 * @return {@link TableAccessLevel#getAccessLevelInt()}
	 * @deprecated Please use {@link #getAccessLevel()}
	 */
	@Deprecated
	int getAccessLevelInt()
	{
		return m_AccessLevel.getAccessLevelInt();
	}

	/**************************************************************************
	 * Get ColumnCount
	 *
	 * @return column count
	 */
	public int getColumnCount()
	{
		return m_columns.size();
	}   // getColumnCount

	public boolean hasColumnName(final String columnName)
	{
		final int idx = getColumnIndex(columnName);
		return idx >= 0;
	}

	/**
	 * Get Column Index
	 *
	 * @param ColumnName column name
	 * @return index of column with ColumnName or -1 if not found
	 */
	// TODO: handle column names ignoring the case
	public int getColumnIndex(final String ColumnName)
	{
		final Integer columnIndex = columnName2columnIndex.get(ColumnName);
		if (columnIndex != null)
		{
			return columnIndex;
		}

		return -1;
	}   // getColumnIndex

	public int getColumnIndex(@NonNull final AdColumnId adColumnId)
	{
		final Integer columnIndex = adColumnId2columnIndex.get(adColumnId);
		if (columnIndex != null)
		{
			return columnIndex;
		}

		//
		// Fallback: for some reason column index was not found
		// => iterate columns and try to get it
		logger.warn("ColumnIndex was not found for AD_Column_ID={} on '{}'. Searching one by one.", adColumnId, this);
		for (int i = 0; i < m_columns.size(); i++)
		{
			if (AdColumnId.equals(adColumnId, m_columns.get(i).AD_Column_ID))
			{
				return i;
			}
		}
		return -1;
	}   // getColumnIndex

	/**
	 * @return AD_Column_ID if found, -1 if not found
	 */
	@Nullable
	public AdColumnId getAD_Column_ID(final String columnName)
	{
		final int columnIndex = getColumnIndex(columnName);
		if (columnIndex < 0)
		{
			return null;
		}

		return m_columns.get(columnIndex).getAD_Column_ID();
	}

	/**
	 * Get Column
	 *
	 * @param index index
	 * @return column
	 */
	@Nullable
	public POInfoColumn getColumn(final int index)
	{
		if (index < 0 || index >= m_columns.size())
		{
			return null;
		}
		return m_columns.get(index);
	}

	@Nullable
	public POInfoColumn getColumn(final String columnName)
	{
		final int columnIndex = getColumnIndex(columnName);
		return columnIndex >= 0 ? m_columns.get(columnIndex) : null;
	}

	@NonNull
	public POInfoColumn getColumnNotNull(final String columnName)
	{
		final int columnIndex = getColumnIndex(columnName);
		if (columnIndex < 0)
		{
			throw new AdempiereException("No column info found for " + getTableName() + "." + columnName);
		}
		return m_columns.get(columnIndex);
	}

	/**
	 * @return immutable set of all column names
	 */
	public Set<String> getColumnNames()
	{
		return columnName2columnIndex.keySet();
	}

	/**
	 * Get Column Name
	 *
	 * @param index index
	 * @return ColumnName column name
	 */
	@Nullable
	public String getColumnName(final int index)
	{
		if (index < 0 || index >= m_columns.size())
		{
			return null;
		}
		return m_columns.get(index).getColumnName();
	}

	@NonNull
	public String getColumnNameNotNull(final int index)
	{
		if (index < 0 || index >= m_columns.size())
		{
			throw new AdempiereException("index out of bound");
		}
		return m_columns.get(index).getColumnName();
	}   // getColumnName

	/**
	 * Get Column SQL or Column Name, including the "AS ColumnName"
	 *
	 * @param index column index
	 */
	@Nullable
	public String getColumnSqlForSelect(final int index)
	{
		if (index < 0 || index >= m_columns.size())
		{
			return null;
		}

		final POInfoColumn columnInfo = m_columns.get(index);
		return columnInfo.getColumnSqlForSelect();
	}

	/**
	 * Get Column SQL or Column Name, without the "AS ColumnName"
	 */
	@Nullable
	public String getColumnSql(final int index)
	{
		if (index < 0 || index >= m_columns.size())
		{
			return null;
		}

		final POInfoColumn columnInfo = m_columns.get(index);
		return columnInfo.isVirtualColumn() ? columnInfo.getColumnSQL() : columnInfo.getColumnName();
	}

	/**
	 * Get Column SQL or Column Name, without the "AS ColumnName"
	 */
	@Nullable
	public String getColumnSql(final String columnName)
	{
		final int columnIndex = getColumnIndex(columnName);
		if (columnIndex < 0)
		{
			throw new IllegalArgumentException("Column name " + columnName + " not found in " + this);
		}
		return getColumnSql(columnIndex);
	}

	/**
	 * Is Column Virtual?
	 *
	 * @param index index
	 * @return true if column is virtual
	 */
	public boolean isVirtualColumn(final int index)
	{
		if (index < 0 || index >= m_columns.size())
		{
			// If index is not valid, we consider this a virtual column
			return true;
		}
		return m_columns.get(index).isVirtualColumn();
	}   // isVirtualColumn

	public boolean isVirtualColumn(final String columnName)
	{
		final int columnIndex = getColumnIndex(columnName);
		if (columnIndex < 0)
		{
			return false;
		}
		return isVirtualColumn(columnIndex);
	}

	/**
	 * @return true if column exist and it's physical (i.e. not a virtual column)
	 */
	public boolean isPhysicalColumn(final String columnName)
	{
		final int columnIndex = getColumnIndex(columnName);
		if (columnIndex < 0)
		{
			return false;
		}

		return !isVirtualColumn(columnIndex);
	}

	@Nullable
	public Class<?> getColumnClass(final int index)
	{
		if (index < 0 || index >= m_columns.size())
		{
			return null;
		}
		return m_columns.get(index).ColumnClass;
	}   // getColumnClass

	@Nullable
	public Class<?> getColumnClass(final String columnName)
	{
		final int columnIndex = getColumnIndex(columnName);
		if (columnIndex < 0)
		{
			return null;
		}
		return getColumnClass(columnIndex);
	}

	/**
	 * Get Column Display Type
	 *
	 * @param index index
	 * @return DisplayType
	 */
	public int getColumnDisplayType(final int index)
	{
		if (index < 0 || index >= m_columns.size())
		{
			return DisplayType.String;
		}
		return m_columns.get(index).getDisplayType();
	}   // getColumnDisplayType

	@Override
	public int getColumnDisplayType(final String columnName)
	{
		final int columnIndex = getColumnIndex(columnName);
		if (columnIndex < 0)
		{
			throw new IllegalArgumentException("Column name " + columnName + " not found in " + this);
		}
		return getColumnDisplayType(columnIndex);
	}

	/**
	 * Get Column Default Logic
	 *
	 * @param index index
	 * @return Default Logic
	 */
	@Nullable
	public String getDefaultLogic(final int index)
	{
		if (index < 0 || index >= m_columns.size())
		{
			return null;
		}
		return m_columns.get(index).DefaultLogic;
	}   // getDefaultLogic

	/**
	 * Is Column Mandatory
	 *
	 * @param index index
	 * @return true if column mandatory
	 */
	public boolean isColumnMandatory(final int index)
	{
		if (index < 0 || index >= m_columns.size())
		{
			return false;
		}
		return m_columns.get(index).IsMandatory;
	}   // isMandatory

	public boolean isColumnMandatory(final String columnName)
	{
		final int columnIndex = getColumnIndex(columnName);
		return isColumnMandatory(columnIndex);
	}

	// metas-03035 begin
	// method has been added to find out which table a given column references
	//

	/**
	 * Returns the columns <code>AD_Reference_Value_ID</code>.
	 *
	 * @param index index
	 * @return the column's AD_Reference_Value_ID or -1 if the given index is not valid
	 */
	@Nullable
	public ReferenceId getColumnReferenceValueId(final int index)
	{
		if (index < 0 || index >= m_columns.size())
		{
			return null;
		}
		return m_columns.get(index).AD_Reference_Value_ID;
	}

	/**
	 * Returns the columns <code>AD_Reference_Value_ID</code>.
	 *
	 * @return the column's AD_Reference_Value_ID or -1 if the given index is not valid
	 */
	@Nullable
	public ReferenceId getColumnReferenceValueId(final String columnName)
	{
		final int columnIndex = getColumnIndex(columnName);
		return getColumnReferenceValueId(columnIndex);
	}

	public boolean isColumnUpdateable(final int index)
	{
		if (index < 0 || index >= m_columns.size())
		{
			return false;
		}
		return m_columns.get(index).IsUpdateable;
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isColumnUpdateable(final String columnName)
	{
		final int columnIndex = getColumnIndex(columnName);
		return isColumnUpdateable(columnIndex);
	}   // isUpdateable

	@Nullable
	public String getReferencedTableNameOrNull(final String columnName)
	{
		final int columnIndex = getColumnIndex(columnName);
		final POInfoColumn poInfoColumn = m_columns.get(columnIndex);
		return poInfoColumn.getReferencedTableNameOrNull();
	}

	@Nullable
	public Lookup getColumnLookup(final Properties ctx, final int columnIndex)
	{
		return m_columns.get(columnIndex).getLookup(ctx, Env.WINDOW_None);
	}

	public boolean isKey(final int index)
	{
		if (index < 0 || index >= m_columns.size())
		{
			return false;
		}
		return m_columns.get(index).IsKey;
	}

	public boolean isKey(final String columnName)
	{
		final int columnIndex = getColumnIndex(columnName);
		if (columnIndex < 0)
		{
			return false;
		}
		return isKey(columnIndex);
	}

	/**
	 * Is Column (data) Encrypted
	 *
	 * @param index index
	 * @return true if column is encrypted
	 */
	public boolean isEncrypted(final int index)
	{
		if (index < 0 || index >= m_columns.size())
		{
			return false;
		}
		return m_columns.get(index).IsEncrypted;
	}   // isEncrypted

	/**
	 * Is allowed logging on this column
	 *
	 * @param index index
	 * @return true if column is allowed to be logged
	 */
	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isAllowLogging(final int index)
	{
		if (index < 0 || index >= m_columns.size())
		{
			return false;
		}
		return m_columns.get(index).IsAllowLogging;
	} // isAllowLogging

	/**
	 * Get Column FieldLength
	 *
	 * @param index index
	 * @return field length
	 */
	public int getFieldLength(final int index)
	{
		if (index < 0 || index >= m_columns.size())
		{
			return 0;
		}
		return m_columns.get(index).FieldLength;
	}   // getFieldLength

	/**
	 * Get Column FieldLength
	 *
	 * @param columnName Column Name
	 * @return field length or 0
	 */
	public int getFieldLength(final String columnName)
	{
		final int index = getColumnIndex(columnName);
		if (index >= 0)
		{
			return getFieldLength(index);
		}
		return 0;
	}

	/**
	 * Validate Content
	 *
	 * @param index index
	 * @param value new Value
	 * @return null if all valid otherwise error message
	 */
	@Nullable
	public String validate(final int index, final Object value)
	{
		if (index < 0 || index >= m_columns.size())
		{
			return "RangeError";
		}
		// Mandatory (i.e. not null
		if (m_columns.get(index).IsMandatory && value == null)
		{
			return "IsMandatory";
		}
		if (value == null)
		{
			return null;
		}

		// Length ignored

		//
		if (m_columns.get(index).ValueMin != null)
		{
			BigDecimal value_BD = null;
			if (m_columns.get(index).ValueMin_BD != null)
			{
				value_BD = NumberUtils.asBigDecimal(value, null);
			}
			// Both are Numeric
			if (m_columns.get(index).ValueMin_BD != null && value_BD != null)
			{    // error: 1 - 0 => 1 - OK: 1 - 1 => 0 & 1 - 10 => -1
				final int comp = m_columns.get(index).ValueMin_BD.compareTo(value_BD);
				if (comp > 0)
				{
					return "MinValue=" + m_columns.get(index).ValueMin_BD
							+ "(" + m_columns.get(index).ValueMin + ")"
							+ " - compared with Numeric Value=" + value_BD + "(" + value + ")"
							+ " - results in " + comp;
				}
			}
			else
			// String
			{
				final int comp = m_columns.get(index).ValueMin.compareTo(value.toString());
				if (comp > 0)
				{
					return "MinValue=" + m_columns.get(index).ValueMin
							+ " - compared with String Value=" + value
							+ " - results in " + comp;
				}
			}
		}
		if (m_columns.get(index).ValueMax != null)
		{
			BigDecimal value_BD = null;
			if (m_columns.get(index).ValueMax_BD != null)
			{
				value_BD = NumberUtils.asBigDecimal(value, null);
			}
			// Both are Numeric
			if (m_columns.get(index).ValueMax_BD != null && value_BD != null)
			{    // error 12 - 20 => -1 - OK: 12 - 12 => 0 & 12 - 10 => 1
				final int comp = m_columns.get(index).ValueMax_BD.compareTo(value_BD);
				if (comp < 0)
				{
					return "MaxValue=" + m_columns.get(index).ValueMax_BD + "(" + m_columns.get(index).ValueMax + ")"
							+ " - compared with Numeric Value=" + value_BD + "(" + value + ")"
							+ " - results in " + comp;
				}
			}
			else
			// String
			{
				final int comp = m_columns.get(index).ValueMax.compareTo(value.toString());
				if (comp < 0)
				{
					return "MaxValue=" + m_columns.get(index).ValueMax
							+ " - compared with String Value=" + value
							+ " - results in " + comp;
				}
			}
		}
		return null;
	}   // validate

	public boolean isLazyLoading(final int index)
	{
		if (index < 0 || index >= m_columns.size())
		{
			return true;
		}
		return m_columns.get(index).IsLazyLoading;
	}

	/**
	 * Build select clause (i.e. SELECT column names FROM TableName)
	 *
	 * @return string builder
	 */
	public StringBuilder buildSelect()
	{
		return new StringBuilder(sqlSelect);
	}

	private String buildSqlSelect()
	{
		return "SELECT " + getSqlSelectColumns()
				+ "\n FROM " + getTableName();
	}

	private String buildSqlSelectColumns()
	{
		final StringBuilder sql = new StringBuilder();
		final int size = getColumnCount();
		for (int i = 0; i < size; i++)
		{
			if (isLazyLoading(i))
			{
				continue;
			}
			if (sql.length() > 0)
			{
				sql.append(",");
			}
			sql.append(getColumnSqlForSelect(i)); // Normal and Virtual Column
		}

		return sql.toString();
	}

	/**
	 * @return all columns to select in SQL format (i.e. ColumnName1, ColumnName2 ....)
	 */
	public String getSqlSelectColumns()
	{
		return sqlSelectColumns;
	}

	/**
	 * @return if table save log
	 */
	public boolean isChangeLog()
	{
		return m_IsChangeLog;
	}

	// metas: us215
	public boolean isCalculated(final int index)
	{
		if (index < 0 || index >= m_columns.size())
		{
			return false;
		}
		return m_columns.get(index).IsCalculated;
	}

	public boolean isCalculated(final String columnName)
	{
		final int columnIndex = getColumnIndex(columnName);
		if (columnIndex < 0)
		{
			return false;
		}
		return isCalculated(columnIndex);
	}

	/**
	 * Shall we generate auto-sequence for given String column
	 *
	 * @return true if we shall generate auto-sequence
	 */
	// metas 05133
	public boolean isUseDocSequence(final int index)
	{
		if (index < 0 || index >= m_columns.size())
		{
			return false;
		}
		return m_columns.get(index).IsUseDocumentSequence;
	}

	public boolean isUseDocSequence(final String columnName)
	{
		return isUseDocSequence(getColumnIndex(columnName));
	}

	/**
	 * @return true if we shall re-load the model after save
	 */
	public boolean isLoadAfterSave()
	{
		// TODO: implement a better logic
		return false;
	}

	/**
	 * Gets SQL to be used when loading from model table
	 * <p>
	 * The returned SQL will look like: <br>
	 * SELECT columnName1, columnName2, (SELECT ...) as VirtualColumn3 FROM TableName
	 *
	 * @return SELECT ... FROM TableName
	 */
	/* package */String getSqlSelect()
	{
		return sqlSelect;
	}

	/**
	 * Gets SQL to be used when loading a record
	 * <p>
	 * e.g. SELECT columnNames FROM TableName WHERE KeyColumn1=? AND KeyColumn2=?
	 *
	 * @return SELECT SQL for loading
	 */
	/* package */String getSqlSelectByKeys()
	{
		return sqlSelectByKeys;
	}

	private String buildSqlSelectByKeys()
	{
		return getSqlSelect()
				+ "\n WHERE " + getSqlWhereClauseByKeys();
	}

	/**
	 * Gets SQL Where Clause by KeyColumns.
	 * <p>
	 * NOTE: values are parameterized
	 *
	 * @return SQL where clause (e.g. KeyColumn_ID=?)
	 */
	public String getSqlWhereClauseByKeys()
	{
		return sqlWhereClauseByKeys;
	}

	private String buildSqlWhereClauseByKeys()
	{
		final StringBuilder sb = new StringBuilder();
		for (final String keyColumnName : m_keyColumnNames)
		{
			if (sb.length() > 0)
			{
				sb.append(" AND ");
			}

			sb.append(keyColumnName).append("=?");
		}
		return sb.toString();
	}

	/* package */boolean isColumnStaleable(final int columnIndex)
	{
		if (columnIndex < 0 || columnIndex >= m_columns.size())
		{
			return true;
		}

		return m_columns.get(columnIndex).IsStaleable;
	}

	/* package */boolean hasStaleableColumns()
	{
		return m_HasStaleableColumns;
	}

	/**
	 * @return list of column names which are translated
	 */
	public List<String> getTranslatedColumnNames()
	{
		return trlInfo.getTranslatedColumnNames();
	}

	public POTrlInfo getTrlInfo()
	{
		return trlInfo;
	}

	public boolean isPasswordColumn(final int index)
	{
		if (index < 0 || index >= m_columns.size())
		{
			return false;
		}

		return m_columns.get(index).isPasswordColumn();

	}

	public Optional<String> getSingleParentColumnName()
	{
		String singleColumnName = null;
		for (final POInfoColumn column : m_columns)
		{
			if (!column.isParent())
			{
				continue;
			}

			if (singleColumnName != null)
			{
				// more than one parent columns found
				return Optional.empty();
			}
			else
			{
				singleColumnName = column.getColumnName();
			}
		}

		return Optional.ofNullable(singleColumnName);
	}

	public boolean isRestAPICustomColumn(final int index)
	{
		if (index < 0 || index >= m_columns.size())
		{
			return false;
		}
		return m_columns.get(index).IsRestAPICustomColumn;
	}

	public boolean isRestAPICustomColumn(final String columnName)
	{
		final int columnIndex = getColumnIndex(columnName);
		return isRestAPICustomColumn(columnIndex);
	}

	public boolean isParentLinkColumn(final String columnName)
	{
		final POInfoColumn column = getColumn(columnName);
		return column != null && column.isParent();
	}

	@NonNull
	public Stream<POInfoColumn> streamColumns(@NonNull final Predicate<POInfoColumn> poInfoColumnPredicate)
	{
		return m_columns.stream()
				.filter(poInfoColumnPredicate);
	}

	public Optional<String> getTableIdColumnName(@NonNull final String recordIdColumnName)
	{
		return tableAndRecordColumnNames.stream()
				.filter(tableAndRecordColumnName -> tableAndRecordColumnName.equalsByColumnName(recordIdColumnName))
				.map(TableAndColumnName::getTableNameAsString)
				.findFirst();
	}

	@Value
	@Builder
	private static class POInfoHeader
	{
		@NonNull String tableName;
		@NonNull AdTableId adTableId;
		@NonNull TableAccessLevel accessLevel;
		boolean isView;
		boolean isChangeLog;
		int webuiViewPageLength;
		@NonNull TableCloningEnabled cloningEnabled;
		@NonNull TableWhenChildCloningStrategy whenChildCloningStrategy;
		@NonNull TableDownlineCloningStrategy downlineCloningStrategy;
	}

	public static class POInfoMap
	{
		private final ImmutableMap<AdTableId, POInfo> byTableId;
		private final ImmutableMap<String, POInfo> byTableNameUC;

		public POInfoMap(@NonNull final List<POInfo> poInfos)
		{
			byTableId = Maps.uniqueIndex(poInfos, POInfo::getAdTableId);
			byTableNameUC = Maps.uniqueIndex(poInfos, POInfo::getTableNameUC);
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("size", byTableId.size())
					.toString();
		}

		@Nullable
		public POInfo getByTableIdOrNull(@NonNull final AdTableId tableId)
		{
			return byTableId.get(tableId);
		}

		@NonNull
		public POInfo getByTableId(@NonNull final AdTableId tableId)
		{
			final POInfo poInfo = getByTableIdOrNull(tableId);
			if (poInfo == null)
			{
				throw new AdempiereException("No POInfo found for " + tableId);
			}
			return poInfo;
		}

		@Nullable
		public POInfo getByTableNameOrNull(@NonNull final String tableName)
		{
			return byTableNameUC.get(tableName.toUpperCase());
		}

		@NonNull
		public POInfo getByTableName(@NonNull final String tableName)
		{
			final POInfo poInfo = getByTableNameOrNull(tableName);
			if (poInfo == null)
			{
				throw new AdempiereException("No POInfo found for " + tableName);
			}
			return poInfo;
		}

		public Stream<POInfo> stream()
		{
			return byTableId.values().stream();
		}

		public int size()
		{
			return byTableId.size();
		}

		public ImmutableCollection<POInfo> toCollection()
		{
			return byTableId.values();
		}
	}
}   // POInfo
