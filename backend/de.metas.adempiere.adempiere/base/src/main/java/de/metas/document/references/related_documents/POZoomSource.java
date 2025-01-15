/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.document.references.related_documents;

import com.google.common.base.MoreObjects;
import de.metas.common.util.pair.IPair;
import de.metas.common.util.pair.ImmutablePair;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.table.api.MinimalColumnInfo;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;

import java.util.ArrayList;
import java.util.Properties;

/**
 * Note that webui records own source implementation.
 */
public final class POZoomSource implements IZoomSource
{
	public static POZoomSource of(final PO po, final AdWindowId adWindowId)
	{
		return new POZoomSource(po, adWindowId);
	}

	public static POZoomSource of(final PO po)
	{
		final AdWindowId adWindowId = null;
		return new POZoomSource(po, adWindowId);
	}

	private final PO po;
	private final AdWindowId adWindowId;
	private final String keyColumnName;

	@Getter
	private final boolean genericZoomOrigin;

	private POZoomSource(@NonNull final PO po, final AdWindowId adWindowId)
	{
		this.po = po;
		this.adWindowId = adWindowId;

		final IPair<String, Boolean> pair = extractKeyColumnNameOrNull(po);

		keyColumnName = pair.getLeft();
		genericZoomOrigin = pair.getRight();
	}

	/**
	 * @return the name of a key column that is also flagged as GenericZoomOrigin and {@code true},if there is exactly one such column.<br>
	 *         Otherwise it returns {@code null} and {@code false}.
	 */
	private static IPair<String, Boolean> extractKeyColumnNameOrNull(@NonNull final PO po)
	{
		final String[] keyColumnNamesArr = po.get_KeyColumns();
		if (keyColumnNamesArr == null)
		{
			return null;
		}

		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

		final ArrayList<String> eligibleKeyColumnNames = new ArrayList<>();
		for (String element : keyColumnNamesArr)
		{
			final MinimalColumnInfo column = adTableDAO.getMinimalColumnInfo(po.get_TableName(), element);
			if (column.isGenericZoomOrigin())
			{
				eligibleKeyColumnNames.add(element);
			}
		}

		if (eligibleKeyColumnNames.size() != 1)
		{
			return ImmutablePair.of(null, Boolean.FALSE);
		}

		return ImmutablePair.of(eligibleKeyColumnNames.get(0), Boolean.TRUE);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("po", po)
				.add("AD_Window_ID", adWindowId)
				.toString();
	}

	public PO getPO()
	{
		return po;
	}

	@Override
	public AdWindowId getAD_Window_ID()
	{
		return adWindowId;
	}

	@Override
	public String getTableName()
	{
		return po.get_TableName();
	}

	@Override
	public int getAD_Table_ID()
	{
		return po.get_Table_ID();
	}

	@Override
	public String getKeyColumnNameOrNull()
	{
		return keyColumnName;
	}

	@Override
	public int getRecord_ID()
	{
		return po.get_ID();
	}

	@Override
	public Properties getCtx()
	{
		return po.getCtx();
	}

	@Override
	public String getTrxName()
	{
		return po.get_TrxName();
	}

	@Override
	public Evaluatee createEvaluationContext()
	{
		final Properties privateCtx = Env.deriveCtx(getCtx());

		final PO po = getPO();
		final POInfo poInfo = po.getPOInfo();
		for (int i = 0; i < poInfo.getColumnCount(); i++)
		{
			final Object val;
			final int dispType = poInfo.getColumnDisplayType(i);
			if (DisplayType.isID(dispType))
			{
				// make sure we get a 0 instead of a null for foreign keys
				val = po.get_ValueAsInt(i);
			}
			else
			{
				val = po.get_Value(i);
			}

			if (val == null)
			{
				continue;
			}

			if (val instanceof Integer)
			{
				Env.setContext(privateCtx, "#" + po.get_ColumnName(i), (Integer)val);
			}
			else if (val instanceof String)
			{
				Env.setContext(privateCtx, "#" + po.get_ColumnName(i), (String)val);
			}
		}

		return Evaluatees.ofCtx(privateCtx, Env.WINDOW_None, false);
	}

	@Override
	public boolean hasField(final String columnName)
	{
		return po.getPOInfo().hasColumnName(columnName);
	}

	@Override
	public Object getFieldValue(final String columnName)
	{
		return po.get_Value(columnName);
	}
}