/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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
package org.compiere.util;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CacheMgt;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.logging.MetasfreshLastError;
import de.metas.organization.OrgId;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.dao.impl.InArrayQueryFilter;
import org.adempiere.ad.migration.logger.IMigrationLogger;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.DBDeadLockDetectedException;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.DBForeignKeyConstraintException;
import org.adempiere.exceptions.DBNoConnectionException;
import org.adempiere.exceptions.DBUniqueConstraintException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.sql.IStatementsFactory;
import org.adempiere.sql.impl.StatementsFactory;
import org.adempiere.util.lang.ImmutablePair;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.adempiere.util.trxConstraints.api.ITrxConstraints;
import org.adempiere.util.trxConstraints.api.ITrxConstraintsBL;
import org.compiere.db.AdempiereDatabase;
import org.compiere.db.CConnection;
import org.compiere.db.Database;
import org.compiere.dbPort.Convert;
import org.compiere.model.MSequence;
import org.compiere.model.Null;
import org.compiere.model.POInfo;
import org.compiere.model.POResultSet;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * General Database Interface
 */
@UtilityClass
public class DB
{
	public final String SYSCONFIG_SYSTEM_NATIVE_SEQUENCE = "SYSTEM_NATIVE_SEQUENCE";

	private final IStatementsFactory statementsFactory = StatementsFactory.instance;

	/**
	 * Specifies what to do in case the SQL command fails.
	 */
	public enum OnFail
	{
		/**
		 * Throws {@link DBException}
		 */
		ThrowException,
		/**
		 * Save the exception using {@link MetasfreshLastError#saveError(Logger, String, Throwable)} methods.
		 * <p>
		 * NOTE: this is totally legacy and you shall no longer use it.
		 */
		@Deprecated
		SaveError,
		/**
		 * Ignore the error but log it.
		 */
		IgnoreButLog;

		/**
		 * Gets corresponding {@link OnFail} value for given <code>ignoreError</code> flag.
		 * <p>
		 * NOTE: avoid using this method. We introduced it just to migrate from old API.
		 *
		 * @param ignoreError
		 * @return {@link OnFail} value
		 */
		public static OnFail valueOfIgnoreError(final boolean ignoreError)
		{
			return ignoreError ? OnFail.IgnoreButLog : OnFail.SaveError;
		}
	}

	/**
	 * Connection Descriptor
	 */
	private CConnection s_connection = null;
	private final ReentrantLock s_connectionLock = new ReentrantLock();

	/**
	 * Logger
	 */
	private final Logger log = LogManager.getLogger(DB.class);

	/**
	 * SQL Statement Separator "; "
	 */
	public final String SQLSTATEMENT_SEPARATOR = "; ";

	/**************************************************************************
	 * Set connection.
	 *
	 * If the connection was already set and it's the same, this method does nothing.
	 *
	 * @param cc connection
	 */
	public void setDBTarget(final CConnection cc)
	{
		if (cc == null)
		{
			throw new IllegalArgumentException("Connection is NULL");
		}

		s_connectionLock.lock();
		try
		{
			// If we are trying to set exactly the same connection then do nothing
			if (s_connection != null && s_connection.equals(cc))
			{
				return;
			}

			//
			// Close previous connection if any
			DB.closeTarget();

			//
			// Set the new connection
			s_connection = cc;
			s_connection.setDataSource();
			log.info("Target database set: {}", s_connection);

			// Reset the cache, else we could have cached records from old database, which does not exist in our new database
			CacheMgt.get().reset();
		}
		finally
		{
			s_connectionLock.unlock();
		}
	}   // setDBTarget

	/**************************************************************************
	 * Close Target
	 */
	public void closeTarget()
	{
		boolean closed = false;

		s_connectionLock.lock();
		try
		{
			if (s_connection != null)
			{
				s_connection.closeDataSource();
				closed = true;
			}
			s_connection = null;
		}
		finally
		{
			s_connectionLock.unlock();
		}
		if (closed)
		{
			log.debug("Target database closed");
		}
	}    // closeTarget

	/**
	 * @return current {@link CConnection} or <code>null</code>
	 */
	private CConnection getCConnection()
	{
		s_connectionLock.lock();
		try
		{
			return s_connection;
		}
		finally
		{
			s_connectionLock.unlock();
		}
	}

	/**
	 * Connect to database and initialize all connections.
	 *
	 * @return True if success, false otherwise
	 */
	public boolean connect()
	{
		// direct connection
		boolean success = false;
		Connection connRW = null;
		Connection connRO = null;
		Connection connID = null;
		try
		{
			connRW = getConnectionRW();
			if (connRW != null)
			{
				connRW.close();
			}

			connRO = getConnectionRO();
			if (connRO != null)
			{
				connRO.close();
			}

			connID = getConnectionID();
			if (connID != null)
			{
				connID.close();
			}
			success = ((connRW != null) && (connRO != null) && (connID != null));
		}
		catch (final Exception e)
		{
			// logging here could cause infinite loop
			// log.error("Could not connect to DB", e);
			System.err.println("Could not connect to DB - " + e.getLocalizedMessage());
			e.printStackTrace();
			success = false;
		}
		finally
		{
			close(connRW);
			close(connRO);
			close(connID);
		}
		return success;
	}

	/**
	 * Checks if database connection is established.
	 *
	 * @return true, if connected to database
	 */
	public boolean isConnected()
	{
		final CConnection cc = getCConnection();
		if (cc == null)
		{
			return false;
		}

		final boolean checkIfUnknown = true;
		return cc.isDatabaseOK(checkIfUnknown);
	}   // isConnected

	/**
	 * @return Connection (r/w)
	 */
	public Connection getConnectionRW()
	{
		return createConnection(true, false, Connection.TRANSACTION_READ_COMMITTED);
	}   // getConnectionRW

	/**
	 * Return everytime a new r/w no AutoCommit, Serializable connection. To be used to ID
	 *
	 * @return Connection (r/w)
	 */
	public Connection getConnectionID()
	{
		return createConnection(false, false, Connection.TRANSACTION_READ_COMMITTED);
	}   // getConnectionID

	/**
	 * Return read committed, read/only from pool.
	 *
	 * @return Connection (r/o)
	 */
	public Connection getConnectionRO()
	{
		return createConnection(true, true, Connection.TRANSACTION_READ_COMMITTED);     // see below
	}    // getConnectionRO

	/**
	 * Create new Connection. The connection must be closed explicitly by the application
	 *
	 * @param autoCommit auto commit
	 * @param trxLevel   - Connection.TRANSACTION_READ_UNCOMMITTED, Connection.TRANSACTION_READ_COMMITTED, Connection.TRANSACTION_REPEATABLE_READ, or Connection.TRANSACTION_READ_COMMITTED.
	 * @return Connection connection
	 */
	public Connection createConnection(final boolean autoCommit, final int trxLevel)
	{

		final CConnection cc = getCConnection();
		if (cc == null)
		{
			throw new DBNoConnectionException();
		}

		final Connection conn = cc.getConnection(autoCommit, trxLevel);
		if (conn == null)
		{
			throw new DBNoConnectionException();
		}

		boolean success = false;
		try
		{
			// hengsin: failed to set autocommit can lead to severe lock up of the system
			if (conn.getAutoCommit() != autoCommit)
			{
				throw new DBException("Failed to set the requested auto commit mode on connection. [autoCommit=" + autoCommit + "]");
			}

			success = true;
			return conn;
		}
		catch (final SQLException e)
		{
			throw new DBException("Failed checking the connection", e);
		}
		finally
		{
			if (!success)
			{
				close(conn);
			}
		}
	}    // createConnection

	/**
	 * Create new Connection. The connection must be closed explicitly by the application
	 *
	 * @param autoCommit auto commit
	 * @param readOnly
	 * @param trxLevel   - Connection.TRANSACTION_READ_UNCOMMITTED, Connection.TRANSACTION_READ_COMMITTED, Connection.TRANSACTION_REPEATABLE_READ, or Connection.TRANSACTION_READ_COMMITTED.
	 */
	public Connection createConnection(final boolean autoCommit, final boolean readOnly, final int trxLevel)
	{
		// NOTE: hengsin: this could be problematic as it can be reuse for readwrite activites after return to pool,
		/*
		 * if (conn != null) { try { conn.setReadOnly(readOnly); } catch (SQLException ex) { conn = null; log.error(ex.getMessage(), ex); } }
		 */

		return createConnection(autoCommit, trxLevel);
	}   // createConnection

	/**
	 * Get Database Driver. Access to database specific functionality.
	 *
	 * @return Adempiere Database Driver
	 */
	public AdempiereDatabase getDatabase()
	{
		final CConnection s_cc = getCConnection();
		if (s_cc != null)
		{
			return s_cc.getDatabase();
		}
		log.error("No Database Connection (getDatabase). Returning null.");
		return null;
	}   // getDatabase

	// begin vpj-cd e-evolution 02/07/2005 PostgreSQL

	/**
	 * Do we have a Postgre DB ?
	 *
	 * @return true if connected to PostgreSQL
	 */
	public boolean isPostgreSQL()
	{
		final CConnection s_cc = getCConnection();
		if (s_cc != null)
		{
			return true;
		}
		log.error("No Database (isPostgreSQL). Returning false.");
		return false;
	}

	// begin vpj-cd e-evolution 02/07/2005 PostgreSQL

	/**
	 * Get Database Info
	 *
	 * @return info
	 */
	public String getDatabaseInfo()
	{
		final CConnection s_cc = getCConnection();
		if (s_cc != null)
		{
			return s_cc.getDBInfo();
		}
		return "No Database";
	}    // getDatabaseInfo

	/**************************************************************************
	 * Prepare Call
	 */
	public CallableStatement prepareCall(final String SQL, final int resultSetConcurrency, final String trxName)
	{
		if (SQL == null || SQL.length() == 0)
		{
			throw new IllegalArgumentException("Required parameter missing - " + SQL);
		}
		return statementsFactory.newCCallableStatement(ResultSet.TYPE_FORWARD_ONLY, resultSetConcurrency, SQL, trxName);
	}    // prepareCall

	/**************************************************************************
	 * Prepare Statement
	 *
	 * @param sql
	 * @return Prepared Statement
	 * @deprecated
	 */
	@Deprecated
	public CPreparedStatement prepareStatement(final String sql)
	{
		int concurrency = ResultSet.CONCUR_READ_ONLY;
		final String upper = sql.toUpperCase();
		if (upper.startsWith("UPDATE ") || upper.startsWith("DELETE "))
		{
			concurrency = ResultSet.CONCUR_UPDATABLE;
		}
		return prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, concurrency, null);
	}    // prepareStatement

	public CPreparedStatement prepareStatement(final String sql, @Nullable final String trxName)
	{
		int concurrency = ResultSet.CONCUR_READ_ONLY;
		final String upper = sql.toUpperCase();
		if (upper.startsWith("UPDATE ") || upper.startsWith("DELETE "))
		{
			concurrency = ResultSet.CONCUR_UPDATABLE;
		}
		return prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, concurrency, trxName);
	}    // prepareStatement

	/**
	 * Prepare Statement.
	 *
	 * @param sql                  sql statement
	 * @param resultSetType        - ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_SCROLL_SENSITIVE
	 * @param resultSetConcurrency - ResultSet.CONCUR_READ_ONLY or ResultSet.CONCUR_UPDATABLE
	 * @return Prepared Statement r/o or r/w depending on concur
	 * @deprecated
	 */
	@Deprecated
	public CPreparedStatement prepareStatement(final String sql,
			final int resultSetType, final int resultSetConcurrency)
	{
		return prepareStatement(sql, resultSetType, resultSetConcurrency, null);
	}    // prepareStatement

	/**
	 * Prepare Statement.
	 *
	 * @param sql                  sql statement
	 * @param resultSetType        - ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_SCROLL_SENSITIVE
	 * @param resultSetConcurrency - ResultSet.CONCUR_READ_ONLY or ResultSet.CONCUR_UPDATABLE
	 * @param trxName              transaction name
	 * @return Prepared Statement r/o or r/w depending on concur
	 */
	public CPreparedStatement prepareStatement(final String sql,
			final int resultSetType,
			final int resultSetConcurrency,
			final String trxName)
	{
		if (sql == null || sql.length() == 0)
		{
			throw new IllegalArgumentException("No SQL");
		}
		//
		return statementsFactory.newCPreparedStatement(resultSetType, resultSetConcurrency, sql, trxName);
	}    // prepareStatement

	/**
	 * @return a connection and prepared statement that will internally fetch only 1000 rows at a time, in order not to overuse local memory.
	 * Please make sure to close them both!
	 * <p>
	 * Also see https://jdbc.postgresql.org/documentation/head/query.html
	 */
	public ImmutablePair<Connection, PreparedStatement> prepareConnectionAndStatementForDataExport(
			@NonNull final String sqlSelect,
			@Nullable final List<?> sqlParams/* not ImmutableList because list elements might be null */)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		try
		{
			conn = DB.createConnection(false, Connection.TRANSACTION_READ_COMMITTED); // autoCommit = false

			// Make sure connection's autoCommit is false, else setFetchSize won't work at least with postgresql-jdbc driver
			Check.assume(!conn.getAutoCommit(), "JDBC connection's AutoCommit flag shall be false");
			pstmt = conn.prepareStatement(sqlSelect);

			Check.assume(pstmt.getResultSetType() == ResultSet.TYPE_FORWARD_ONLY, "Prepared statement's ResultSetType shall be TYPE_FORWARD_ONLY");

			pstmt.setFetchSize(1000);
			DB.setParameters(pstmt, sqlParams);
			return ImmutablePair.of(conn, pstmt);
		}
		catch (final SQLException e)
		{
			close(pstmt); // make sure to return our resources if things went south
			close(conn);
			throw DBException.wrapIfNeeded(e);
		}
	}

	/**
	 * Create Read Only Statement
	 *
	 * @return Statement
	 */
	public Statement createStatement()
	{
		return createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, null);
	}    // createStatement

	/**
	 * Create Statement.
	 *
	 * @param resultSetType        - ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_SCROLL_SENSITIVE
	 * @param resultSetConcurrency - ResultSet.CONCUR_READ_ONLY or ResultSet.CONCUR_UPDATABLE
	 * @param trxName              transaction name
	 * @return Statement - either r/w ir r/o depending on concur
	 */
	public Statement createStatement(final int resultSetType, final int resultSetConcurrency, final String trxName)
	{
		return statementsFactory.newCStatement(resultSetType, resultSetConcurrency, trxName);
	}    // createStatement

	/**
	 * Set parameters for given statement
	 *
	 * @param stmt   statements
	 * @param params parameters array; if null or empty array, no parameters are set
	 */
	public void setParameters(@NonNull final PreparedStatement stmt, @Nullable final Object... params) throws SQLException
	{
		if (params == null || params.length == 0)
		{
			return;
		}
		//
		for (int i = 0; i < params.length; i++)
		{
			setParameter(stmt, i + 1, params[i]);
		}
	}

	/**
	 * Set parameters for given statement
	 *
	 * @param stmt   statements
	 * @param params parameters list; if null or empty list, no parameters are set
	 */
	public void setParameters(@NonNull final PreparedStatement stmt, @Nullable final List<?> params)
			throws SQLException
	{
		if (params == null || params.size() == 0)
		{
			return;
		}
		for (int i = 0; i < params.size(); i++)
		{
			setParameter(stmt, i + 1, params.get(i));
		}
	}

	/**
	 * Set PreparedStatement's parameter. Similar with calling <code>pstmt.setObject(index, param)</code>
	 */
	public void setParameter(
			@NonNull final PreparedStatement pstmt,
			final int index,
			@Nullable final Object param) throws SQLException
	{
		if (param == null)
		{
			pstmt.setObject(index, null);
		}
		else if (param instanceof String)
		{
			pstmt.setString(index, (String)param);
		}
		else if (param instanceof Integer)
		{
			pstmt.setInt(index, ((Integer)param).intValue());
		}
		else if (param instanceof BigDecimal)
		{
			pstmt.setBigDecimal(index, (BigDecimal)param);
		}
		else if (param instanceof Timestamp)
		{
			pstmt.setTimestamp(index, (Timestamp)param);
		}
		else if (param instanceof Instant)
		{
			pstmt.setTimestamp(index, TimeUtil.asTimestamp(param));
		}
		else if (param instanceof java.util.Date)
		{
			pstmt.setTimestamp(index, new Timestamp(((java.util.Date)param).getTime()));
		}
		else if (param instanceof LocalDateTime)
		{
			pstmt.setTimestamp(index, TimeUtil.asTimestamp((LocalDateTime)param));
		}
		else if (param instanceof LocalDate)
		{
			pstmt.setTimestamp(index, TimeUtil.asTimestamp((LocalDate)param));
		}
		else if (param instanceof ZonedDateTime)
		{
			pstmt.setTimestamp(index, TimeUtil.asTimestamp(param));
		}
		else if (param instanceof Boolean)
		{
			pstmt.setString(index, StringUtils.ofBoolean((Boolean)param));
		}
		else if (param instanceof RepoIdAware)
		{
			pstmt.setInt(index, ((RepoIdAware)param).getRepoId());
		}
		else if (param instanceof ReferenceListAwareEnum)
		{
			pstmt.setString(index, ((ReferenceListAwareEnum)param).getCode());
		}
		else if(param instanceof byte[])
		{
			pstmt.setBytes(index, (byte[])param);
		}
		else
		{
			throw new DBException("Unknown parameter type " + index + " - " + param + " (" + param.getClass() + ")");
		}
	}

	/**
	 * Execute Update. saves "DBExecuteError" in Log
	 *
	 * @param trxName optional transaction name
	 * @return number of rows updated or -1 if error
	 * @deprecated please use the {@code ...Ex} variant of this method.
	 */
	@Deprecated
	public int executeUpdate(final String sql, final String trxName)
	{
		final Object[] params = null;
		final int timeOut = 0;
		final OnFail onFail = OnFail.valueOfIgnoreError(false);
		final ISqlUpdateReturnProcessor updateReturnProcessor = null;
		return executeUpdate(sql, params, onFail, trxName, timeOut, updateReturnProcessor);
	}    // executeUpdate

	/**
	 * Execute Update. saves "DBExecuteError" in Log
	 *
	 * @param ignoreError if true, no execution error is reported
	 * @param trxName     transaction
	 * @return number of rows updated or -1 if error
	 * @deprecated please use the {@code ...Ex} variant of this method.
	 */
	@Deprecated
	public int executeUpdate(final String sql, final boolean ignoreError, final String trxName)
	{
		final Object[] sqlParams = null;
		final OnFail onFail = OnFail.valueOfIgnoreError(ignoreError);
		final int timeOut = 0;
		final ISqlUpdateReturnProcessor updpateReturnProcessor = null;
		return executeUpdate(sql, sqlParams, onFail, trxName, timeOut, updpateReturnProcessor);
	}    // executeUpdate

	/**
	 * Execute Update. saves "DBExecuteError" in Log
	 *
	 * @param trxName transaction
	 * @return number of rows updated or -1 if error
	 * @deprecated please use the {@code ...Ex} variant of this method.
	 */
	@Deprecated
	public int executeUpdate(final String sql, final int param, final String trxName)
	{
		final Object[] params = new Object[] { param };
		final OnFail onFail = OnFail.valueOfIgnoreError(false);
		final int timeOut = 0;
		final ISqlUpdateReturnProcessor updateReturnProcessor = null;
		return executeUpdate(sql, params, onFail, trxName, timeOut, updateReturnProcessor);
	}    // executeUpdate

	/**
	 * Execute Update. saves "DBExecuteError" in Log
	 *
	 * @param ignoreError if true, no execution error is reported
	 * @param trxName     optional transaction name
	 * @return number of rows updated or -1 if error
	 * @deprecated please use the {@code ...Ex} variant of this method.
	 */
	@Deprecated
	public int executeUpdate(final String sql, final Object[] params, final boolean ignoreError, final String trxName)
	{
		final OnFail onFail = OnFail.valueOfIgnoreError(ignoreError);
		final int timeOut = 0;
		final ISqlUpdateReturnProcessor updateReturnProcessor = null;
		return executeUpdate(sql, params, onFail, trxName, timeOut, updateReturnProcessor);
	}

	/**
	 * Execute SQL Update.
	 *
	 * @param onFail                what to do if the update fails
	 * @param updateReturnProcessor
	 * @return update count
	 * @throws DBException if update fails and {@link OnFail#ThrowException}.
	 * @deprecated please use the {@code ...Ex} variant of this method.
	 */
	@Deprecated
	public int executeUpdate(final String sql,
			final Object[] params,
			@NonNull final OnFail onFail,
			final String trxName,
			final int timeOut,
			final ISqlUpdateReturnProcessor updateReturnProcessor)
	{
		if (Check.isEmpty(sql, true))
		{
			throw new IllegalArgumentException("Required parameter missing - " + sql);
		}

		//
		int no = -1;
		CPreparedStatement cs = statementsFactory.newCPreparedStatement(
				ResultSet.TYPE_FORWARD_ONLY,
				ResultSet.CONCUR_UPDATABLE,
				sql, // converted in call
				trxName);

		try
		{
			setParameters(cs, params);
			if (timeOut > 0 && getDatabase().isQueryTimeoutSupported())
			{
				cs.setQueryTimeout(timeOut);
			}

			if (updateReturnProcessor != null)
			{
				// NOTE: this is an UPDATE query, so we shall log migration scripts
				final ResultSet rs = cs.executeQueryAndLogMigationScripts();
				int rows = 0;
				try
				{
					while (rs.next())
					{
						updateReturnProcessor.process(rs);
						rows++;
					}
				}
				finally
				{
					DB.close(rs);
				}
				no = rows;
			}
			else
			{
				no = cs.executeUpdate();
			}

			// No Transaction - Commit
			if (Services.get(ITrxManager.class).isNull(trxName))
			{
				cs.commit();    // Local commit
				// Connection conn = cs.getConnection();
				// if (conn != null && !conn.getAutoCommit()) // is null for remote
				// conn.commit();
			}
		}
		catch (final DBException ex)
		{
			throw ex;
		}
		catch (final Exception ex)
		{
			Exception sqlException = DBException.extractSQLExceptionOrNull(ex);
			// metas-2009_0021_AP1_CR061: teo_sarca: begin
			if (sqlException instanceof SQLException
					&& DBException.isUniqueContraintError(sqlException))
			{
				sqlException = new DBUniqueConstraintException((SQLException)sqlException, sql, params)
						.setSqlIfAbsent(sql, params);
			}
			// metas-2009_0021_AP1_CR061: teo_sarca: end

			if (sqlException instanceof SQLException
					&& DBException.isDeadLockDetected(sqlException))
			{
				try
				{
					final Connection connection = cs.getConnection();
					sqlException = new DBDeadLockDetectedException(sqlException, connection)
							.setSqlIfAbsent(sql, params);
				}
				catch (final SQLException | DBException e1)
				{
					sqlException.addSuppressed(e1);

					// tried to get the connection to get further infos and throw a more specific exception, but even that failed
					e1.printStackTrace(); // printing the stacktrace (to err), just to make sure it's recorded somewhere
					// now try to log it
					log.error(
							"Caught an additional exception while trying to get the connection of our DBDeadLockDetectedException: " + cs.getSql() + " [" + trxName + "] - " + e1.getMessage());
				}
			}

			if (sqlException instanceof SQLException
					&& DBException.isForeignKeyViolation(sqlException))
			{
				sqlException = new DBForeignKeyConstraintException(sqlException)
						.setSqlIfAbsent(sql, params);
			}

			//
			// Handle the sqlException
			if (onFail == OnFail.SaveError)
			{
				log.error(cs.getSql() + " [" + trxName + "]", sqlException);
				MetasfreshLastError.saveError(log, "DBExecuteError", sqlException);
			}
			else if (onFail == OnFail.IgnoreButLog)
			{
				log.error(cs.getSql() + " [" + trxName + "] - " + sqlException.getLocalizedMessage());
			}
			else if (onFail == OnFail.ThrowException)
			{
				throw DBException.wrapIfNeeded(sqlException != null ? sqlException : ex)
						.setSqlIfAbsent(sql, params);
			}
			// Unknown OnFail option
			// => throw the exception
			else
			{
				throw DBException.wrapIfNeeded(sqlException != null ? sqlException : ex)
						.setSqlIfAbsent(sql, params);
			}
		}
		finally
		{
			// Always close cursor
			DB.close(cs);
		}

		return no;
	}    // executeUpdate

	/**
	 * Execute Update and throw exception.
	 *
	 * @param params  statement parameters
	 * @param trxName transaction
	 * @return number of rows updated
	 */
	public int executeUpdateEx(final String sql, final Object[] params, @Nullable final String trxName) throws DBException
	{
		final int timeOut = 0;
		return executeUpdateEx(sql, params, trxName, timeOut);
	}

	/**
	 * Execute Update and throw exception.
	 *
	 * @param params  statement parameters
	 * @param trxName transaction
	 * @param timeOut optional timeOut parameter
	 * @return number of rows updated
	 */
	public int executeUpdateEx(final String sql, final Object[] params, final String trxName, final int timeOut) throws DBException
	{
		final OnFail onFail = OnFail.ThrowException;
		final ISqlUpdateReturnProcessor updateReturnProcessor = null;
		return executeUpdate(sql, params, onFail, trxName, timeOut, updateReturnProcessor);
	}

	/**
	 * Execute Update and throw exception.
	 *
	 * @see {@link #executeUpdateEx(String, Object[], String)}
	 */
	public int executeUpdateEx(final String sql, @Nullable final String trxName) throws DBException
	{
		final Object[] params = null;
		final int timeOut = 0;
		final OnFail onFail = OnFail.ThrowException;
		final ISqlUpdateReturnProcessor updateReturnProcessor = null;
		return executeUpdate(sql, params, onFail, trxName, timeOut, updateReturnProcessor);
	}    // executeUpdateEx

<<<<<<< HEAD
	/**
	 * Execute Update and throw exception.
	 *
	 * @see {@link #executeUpdateEx(String, Object[], String)}
	 */
	public int executeUpdateEx(final String sql, final String trxName, final int timeOut) throws DBException
=======
		final ExecuteUpdateRequest executeUpdateRequest = ExecuteUpdateRequest.builder()
				.sql(sql)
				.onFail(onFail)
				.trxName(trxName)
				.timeOut(timeOut)

				.build();

		final SQLUpdateResult result = executeUpdate(executeUpdateRequest);

		return result.getReturnedValue();

	}

	public int executeUpdateAndThrowExceptionOnFail(final String sql,
													final Object[] params,
													final String trxName,
													final int timeOut,
													final ISqlUpdateReturnProcessor updateReturnProcessor)
>>>>>>> bceb13a01f1 (webui Record Change Log - display timestamps using frontend locale/timezone (#17848))
	{
		final Object[] params = null;
		final OnFail onFail = OnFail.ThrowException;
		final ISqlUpdateReturnProcessor updateReturnProcessor = null;
		return executeUpdate(sql, params, onFail, trxName, timeOut, updateReturnProcessor);
	}    // executeUpdateEx

	public int executeUpdateEx(final String sql,
			final Object[] params,
			final String trxName,
			final int timeOut,
			final ISqlUpdateReturnProcessor updateReturnProcessor)
	{
		return executeUpdate(sql, params, OnFail.ThrowException, trxName, timeOut, updateReturnProcessor);
	}

	/**
	 * Prepares and executes a callable statement and makes sure that its closed at the end.
	 *
	 * @param functionCall something like <code>"select " + function + "(?,?,?)"</code>
	 */
	public void executeFunctionCallEx(
			final String trxName,
			final String functionCall,
			final Object[] params)
	{
		try (final CallableStatement callableStmt = prepareCall(functionCall, ResultSet.CONCUR_UPDATABLE, trxName))
		{
			setParameters(callableStmt, params);
			callableStmt.execute();
		}
		catch (final SQLException e)
		{
			throw new DBException(e, functionCall, params);
		}
	}

	/**
	 * Commit - commit on RW connection. Is not required as RW connection is AutoCommit (exception: with transaction)
	 *
	 * @param throwException if true, re-throws exception
	 * @param trxName        transaction name
	 * @return true if not needed or success
	 * @throws SQLException
	 */
	public boolean commit(final boolean throwException, final String trxName) throws SQLException, IllegalStateException
	{
		// Not on transaction scope, Connection are thus auto commit
		if (trxName == null)
		{
			return true;
		}

		try
		{
			final Trx trx = Trx.get(trxName, false);
			if (trx != null)
			{
				return trx.commit(true);
			}

			if (throwException)
			{
				throw new IllegalStateException("Could not load transation with identifier: " + trxName);
			}
			else
			{
				return false;
			}
		}
		catch (final SQLException e)
		{
			log.error("[" + trxName + "]", e);
			if (throwException)
			{
				throw e;
			}
			return false;
		}
	}    // commit

	/**
	 * Rollback - rollback on RW connection. Is has no effect as RW connection is AutoCommit (exception: with transaction)
	 *
	 * @param throwException if true, re-throws exception
	 * @param trxName        transaction name
	 * @return true if not needed or success
	 * @throws SQLException
	 */
	public boolean rollback(final boolean throwException, final String trxName) throws SQLException
	{
		try
		{
			Connection conn = null;
			final Trx trx = trxName == null ? null : Trx.get(trxName, true);
			if (trx != null)
			{
				return trx.rollback(true);
			}
			else
			{
				conn = DB.getConnectionRW();
			}
			if (conn != null && !conn.getAutoCommit())
			{
				conn.rollback();
			}
		}
		catch (final SQLException e)
		{
			log.error("[" + trxName + "]", e);
			if (throwException)
			{
				throw e;
			}
			return false;
		}
		return true;
	}    // commit

	/**
	 * Get int Value from sql
	 *
	 * @param trxName trx
	 * @param sql     sql
	 * @param params  array of parameters
	 * @return first value or -1 if not found
	 * @throws DBException if there is any SQLException
	 */
	public int getSQLValueEx(@Nullable final String trxName, final String sql, final Object... params) throws DBException
	{
		int retValue = -1;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = prepareStatement(sql, trxName);
			setParameters(pstmt, params);
			pstmt.setMaxRows(1);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				retValue = rs.getInt(1);
			}
			else
			{
				log.debug("Got no integer value for {}", sql);
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql, params); // metas: tsa
		}
		finally
		{
			close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return retValue;
	}

	/**
	 * Get String Value from sql
	 *
	 * @param trxName trx
	 * @param sql     sql
	 * @param params  collection of parameters
	 * @return first value or -1
	 * @throws DBException if there is any SQLException
	 */
	public int getSQLValueEx(final String trxName, final String sql, final List<Object> params)
	{
		return getSQLValueEx(trxName, sql, params.toArray(new Object[params.size()]));
	}

	/**
	 * Get int Value from sql
	 *
	 * @param trxName trx
	 * @param sql     sql
	 * @param params  array of parameters
	 * @return first value or -1 if not found or error
	 * @deprecated please use the {@code ...Ex} variant of this method.
	 */
	@Deprecated
	public int getSQLValue(@Nullable final String trxName, final String sql, final Object... params)
	{
		int retValue = -1;
		try
		{
			retValue = getSQLValueEx(trxName, sql, params);
		}
		catch (final Exception e)
		{
			log.error(sql, DBException.extractSQLExceptionOrNull(e));
		}
		return retValue;
	}

	/**
	 * Get int Value from sql
	 *
	 * @param trxName trx
	 * @param sql     sql
	 * @param params  collection of parameters
	 * @return first value or null
	 * @deprecated please use the {@code ...Ex} variant of this method.
	 */
	@Deprecated
	public int getSQLValue(final String trxName, final String sql, final List<Object> params)
	{
		return getSQLValue(trxName, sql, params.toArray(new Object[params.size()]));
	}

	/**
	 * Get String Value from sql
	 *
	 * @param trxName trx
	 * @param sql     sql
	 * @param params  array of parameters
	 * @return first value or null
	 * @throws DBException if there is any SQLException
	 */
	public String getSQLValueStringEx(@Nullable final String trxName, final String sql, final Object... params)
	{
		String retValue = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = prepareStatement(sql, trxName);
			setParameters(pstmt, params);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				retValue = rs.getString(1);
			}
			else
			{
				log.debug("Got no string value for {}", sql);
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql, params); // metas: tsa
		}
		finally
		{
			close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return retValue;
	}

	/**
	 * Get String Value from sql
	 *
	 * @param trxName trx
	 * @param sql     sql
	 * @param params  collection of parameters
	 * @return first value or null
	 * @throws DBException if there is any SQLException
	 */
	public String getSQLValueStringEx(final String trxName, final String sql, final List<Object> params)
	{
		return getSQLValueStringEx(trxName, sql, params.toArray(new Object[params.size()]));
	}

	/**
	 * Get String Value from sql
	 *
	 * @param trxName trx
	 * @param sql     sql
	 * @param params  array of parameters
	 * @return first value or null
	 * @deprecated please use the {@code ...Ex} variant of this method.
	 */
	@Deprecated
	public String getSQLValueString(final String trxName, final String sql, final Object... params)
	{
		String retValue = null;
		try
		{
			retValue = getSQLValueStringEx(trxName, sql, params);
		}
		catch (final Exception e)
		{
			log.error("Error while executing: {}", sql, DBException.extractSQLExceptionOrNull(e));
		}
		return retValue;
	}

	/**
	 * Get String Value from sql
	 *
	 * @param trxName trx
	 * @param sql     sql
	 * @param params  collection of parameters
	 * @return first value or null
	 * @deprecated please use the {@code ...Ex} variant of this method.
	 */
	@Deprecated
	public String getSQLValueString(final String trxName, final String sql, final List<Object> params)
	{
		return getSQLValueString(trxName, sql, params.toArray(new Object[params.size()]));
	}

	/**
	 * Get BigDecimal Value from sql
	 *
	 * @param trxName trx
	 * @param sql     sql
	 * @param params  array of parameters
	 * @return first value or null if not found
	 * @throws DBException if there is any SQLException
	 */
	public BigDecimal getSQLValueBDEx(final String trxName, final String sql, final Object... params) throws DBException
	{
		BigDecimal retValue = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = prepareStatement(sql, trxName);
			setParameters(pstmt, params);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				retValue = rs.getBigDecimal(1);
			}
			else
			{
				log.debug("Got no BigDecimal value for {}", sql);
			}
		}
		catch (final SQLException e)
		{
			// log.error(sql, getSQLException(e));
			throw new DBException(e, sql, params); // metas: tsa
		}
		finally
		{
			close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return retValue;
	}

	/**
	 * Get BigDecimal Value from sql
	 *
	 * @param trxName trx
	 * @param sql     sql
	 * @param params  collection of parameters
	 * @return first value or null if not found
	 * @throws DBException if there is any SQLException
	 */
	public BigDecimal getSQLValueBDEx(final String trxName, final String sql, final List<Object> params) throws DBException
	{
		return getSQLValueBDEx(trxName, sql, params.toArray(new Object[params.size()]));
	}

	/**
	 * Get BigDecimal Value from sql
	 *
	 * @param trxName trx
	 * @param sql     sql
	 * @param params  array of parameters
	 * @return first value or null
	 * @deprecated please use the {@code ...Ex} variant of this method.
	 */
	@Deprecated
	public BigDecimal getSQLValueBD(final String trxName, final String sql, final Object... params)
	{
		try
		{
			return getSQLValueBDEx(trxName, sql, params);
		}
		catch (final Exception e)
		{
			log.error(sql, DBException.extractSQLExceptionOrNull(e));
		}
		return null;
	}

	/**
	 * Get BigDecimal Value from sql
	 *
	 * @param trxName trx
	 * @param sql     sql
	 * @param params  collection of parameters
	 * @return first value or null
	 * @deprecated please use the {@code ...Ex} variant of this method.
	 */
	@Deprecated
	public BigDecimal getSQLValueBD(final String trxName, final String sql, final List<Object> params)
	{
		return getSQLValueBD(trxName, sql, params.toArray(new Object[params.size()]));
	}

	/**
	 * Get Timestamp Value from sql
	 *
	 * @param trxName trx
	 * @param sql     sql
	 * @param params  array of parameters
	 * @return first value or null
	 * @throws DBException if there is any SQLException
	 */
	public Timestamp getSQLValueTSEx(final String trxName, final String sql, final Object... params)
	{
		Timestamp retValue = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = prepareStatement(sql, trxName);
			setParameters(pstmt, params);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				retValue = rs.getTimestamp(1);
			}
			else
			{
				log.debug("Got no Timestamp value for {}", sql);
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql, params); // metas: tsa
		}
		finally
		{
			close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return retValue;
	}

	/**
	 * Get BigDecimal Value from sql
	 *
	 * @param trxName trx
	 * @param sql     sql
	 * @param params  collection of parameters
	 * @return first value or null if not found
	 * @throws DBException if there is any SQLException
	 */
	public Timestamp getSQLValueTSEx(final String trxName, final String sql, final List<Object> params) throws DBException
	{
		return getSQLValueTSEx(trxName, sql, params.toArray(new Object[params.size()]));
	}

	/**
	 * Get Timestamp Value from sql
	 *
	 * @param trxName trx
	 * @param sql     sql
	 * @param params  array of parameters
	 * @return first value or null
	 * @deprecated please use the {@code ...Ex} variant of this method.
	 */
	@Deprecated
	public Timestamp getSQLValueTS(final String trxName, final String sql, final Object... params)
	{
		try
		{
			return getSQLValueTSEx(trxName, sql, params);
		}
		catch (final Exception e)
		{
			log.error(sql, DBException.extractSQLExceptionOrNull(e));
		}
		return null;
	}

	/**
	 * Get Timestamp Value from sql
	 *
	 * @return first value or null
	 * @deprecated please use the {@code ...Ex} variant of this method.
	 */
	@Deprecated
	public Timestamp getSQLValueTS(final String trxName, final String sql, final List<Object> params)
	{
		final Object[] arr = new Object[params.size()];
		params.toArray(arr);
		return getSQLValueTS(trxName, sql, arr);
	}

	public <T> T[] getSQLValueArrayEx(
			@Nullable final String trxName,
			@NonNull final String sql,
			@Nullable final Object... params)
	{
		final PreparedStatement pstmt = prepareStatement(sql, trxName);
		ResultSet rs = null;
		try
		{
			setParameters(pstmt, params);
			rs = pstmt.executeQuery();

			if (rs.next())
			{
				@SuppressWarnings("unchecked")
				final T[] arr = (T[])rs.getArray(1).getArray();
				return arr;
			}
			else
			{
				log.debug("Got no array value for sql={}; params={}", sql, params);
				return null;
			}
		}
		catch (final SQLException ex)
		{
			throw DBException.wrapIfNeeded(ex)
					.appendParametersToMessage()
					.setParameter("trxName", trxName)
					.setParameter("sql", sql)
					.setParameter("params", params);
		}
		finally
		{
			close(rs, pstmt);
		}

	}

	/**
	 * Get Array of Key Name Pairs
	 *
	 * @param sql      select with id / name as first / second column
	 * @param optional if true (-1,"") is added
	 * @return array of {@link KeyNamePair}
	 * @see #getKeyNamePairs(String, boolean, Object...)
	 */
	public KeyNamePair[] getKeyNamePairs(final String sql, final boolean optional)
	{
		return getKeyNamePairs(sql, optional, (Object[])null);
	}

	/**
	 * Get Array of Key Name Pairs
	 *
	 * @param sql      select with id / name as first / second column
	 * @param optional if true (-1,"") is added
	 * @param params   query parameters
	 */
	public KeyNamePair[] getKeyNamePairs(final String sql, final boolean optional, final Object... params)
	{
		return getKeyNamePairs(null, sql, optional, params);
	}

	/**
	 * Get Array of Key Name Pairs
	 *
	 * @param sql      select with id as first column, name as second column and optionally description as third column
	 * @param optional if true (-1,"") is added
	 * @param params   query parameters
	 */
	public KeyNamePair[] getKeyNamePairs(
			@Nullable final String trxName,
			@NonNull final String sql,
			final boolean optional,
			final Object... params)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		final ArrayList<KeyNamePair> list = new ArrayList<>();
		if (optional)
		{
			list.add(KeyNamePair.EMPTY);
		}
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			setParameters(pstmt, params);
			rs = pstmt.executeQuery();

			final ResultSetMetaData rsmd = rs.getMetaData();
			final int columnCount = rsmd.getColumnCount();
			final boolean hasDescription = columnCount >= 3;

			while (rs.next())
			{
				final String description = hasDescription ? rs.getString(3) : null;
				final int key = rs.getInt(1);
				final String name = rs.getString(2);

				list.add(KeyNamePair.of(key, name, description));
			}
		}
		catch (final Exception e)
		{
			throw DBException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("trxName", trxName)
					.setParameter("sql", sql)
					.setParameter("optional", optional)
					.setParameter("params", params);
		}
		finally
		{
			close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		final KeyNamePair[] retValue = new KeyNamePair[list.size()];
		list.toArray(retValue);
		// s_log.debug("getKeyNamePairs #" + retValue.length);
		return retValue;
	}    // getKeyNamePairs

	/**
	 * Is Sales Order Trx. Assumes Sales Order. Queries IsSOTrx of table with where clause
	 *
	 * @param tableName   table
	 * @param whereClause where clause
	 * @return true (default) or false if tested that not SO
	 */
	public Optional<SOTrx> retrieveRecordSOTrx(final String tableName, final String whereClause)
	{
		if (Check.isEmpty(tableName, true))
		{
			log.error("No TableName");
			return Optional.empty();
		}

		if (Check.isEmpty(whereClause, true))
		{
			log.error("No Where Clause");
			return Optional.empty();
		}

		//
		// Extract the SQL to select the IsSOTrx column
		final POInfo poInfo = POInfo.getPOInfo(tableName);
		final String sqlSelectIsSOTrx;
		// Case: tableName has the "IsSOTrx" column
		if (poInfo != null && poInfo.isPhysicalColumn("IsSOTrx"))
		{
			sqlSelectIsSOTrx = "SELECT IsSOTrx FROM " + tableName + " WHERE " + whereClause;
		}
		// Case: tableName does NOT have the "IsSOTrx" column but ends with "Line", so we will check the parent table.
		else if (tableName.endsWith("Line"))
		{
			final String parentTableName = tableName.substring(0, tableName.indexOf("Line"));
			final POInfo parentPOInfo = POInfo.getPOInfo(parentTableName);
			if (parentPOInfo != null && parentPOInfo.isPhysicalColumn("IsSOTrx"))
			{
				// metas: use IN instead of EXISTS as the subquery should be highly selective
				sqlSelectIsSOTrx = "SELECT IsSOTrx FROM " + parentTableName
						+ " h WHERE h." + parentTableName + "_ID IN (SELECT l." + parentTableName + "_ID FROM " + tableName
						+ " l WHERE " + whereClause + ")";
			}
			else
			{
				sqlSelectIsSOTrx = null;
			}
		}
		// Fallback: no IsSOTrx
		else
		{
			return Optional.empty();
		}

		//
		// Fetch IsSOTrx value if possible
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sqlSelectIsSOTrx, ITrx.TRXNAME_None);
			pstmt.setMaxRows(1);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				final boolean isSOTrx = DisplayType.toBoolean(rs.getString(1));
				return SOTrx.optionalOfBoolean(isSOTrx);
			}
			else
			{
				log.trace("No records were found to fetch the IsSOTrx from SQL: {}", sqlSelectIsSOTrx);
				return Optional.empty();
			}
		}
		catch (final Exception ex)
		{
			final SQLException sqlEx = DBException.extractSQLExceptionOrNull(ex);
			log.trace("Error while checking isSOTrx (SQL: {})", sqlSelectIsSOTrx, sqlEx);

			return Optional.empty();
		}
		finally
		{
			close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}    // isSOTrx

	/**************************************************************************
	 * Get next number for Key column = 0 is Error. * @param ctx client
	 *
	 * @param TableName table name
	 * @param trxName optionl transaction name
	 * @return next no
	 */
	public int getNextID(final Properties ctx, final String TableName, final String trxName)
	{
		if (ctx == null)
		{
			throw new IllegalArgumentException("Context missing");
		}
		if (TableName == null || TableName.length() == 0)
		{
			throw new IllegalArgumentException("TableName missing");
		}
		return getNextID(Env.getAD_Client_ID(ctx), TableName, trxName);
	}    // getNextID

	/**
	 * Get next ID for database record.
	 * If you consider using this in your business logic, please take a look at {@link IDocumentNoBuilderFactory} first.
	 * <p>
	 * <b>WARNING:</b> the underlying sequence might be reset, depending on existing primary keys in the DB
	 * <p>
	 *
	 * @param trxName optional Transaction Name
	 * @return next primary key number
	 */
	public int getNextID(final int AD_Client_ID, final String TableName, final String trxName)
	{
		final boolean useNativeSequences = DB.isUseNativeSequences(AD_Client_ID, TableName);
		if (useNativeSequences)
		{
			final String sequenceName = getTableSequenceName(TableName);
			final int nextId = CConnection.get().getDatabase().getNextID(sequenceName);
			return nextId;
		}

		return MSequence.getNextID(AD_Client_ID, TableName, trxName);
	}    // getNextID

	public String TO_TABLESEQUENCE_NEXTVAL(final String tableName)
	{
		final String sequenceName = getTableSequenceName(tableName);
		return CConnection.get().getDatabase().TO_SEQUENCE_NEXTVAL(sequenceName);
	}

	/**
	 * Is this a remote client connection.
	 * <p>
	 * Deprecated, always return false.
	 *
	 * @return true if client and RMI or Objects on Server
	 * @deprecated
	 */
	@Deprecated
	public boolean isRemoteObjects()
	{
		return false;
	}    // isRemoteObjects

	/**
	 * Is this a remote client connection
	 * <p>
	 * Deprecated, always return false.
	 *
	 * @return true if client and RMI or Process on Server
	 * @deprecated
	 */
	@Deprecated
	public boolean isRemoteProcess()
	{
		return false;
	}    // isRemoteProcess

	/**
	 * Converts given parameter object to SQL code. Also supports {@link RepoIdAware}.
	 *
	 * @return parameter as SQL code
	 */
	public String TO_SQL(@Nullable final Object param)
	{
		// TODO: check and refactor together with buildSqlList(...)
		if (param == null || param instanceof Null)
		{
			return "NULL";
		}
		else if (param instanceof String)
		{
			return TO_STRING((String)param);
		}
		else if (param instanceof Integer)
		{
			return String.valueOf(param);
		}
		else if (param instanceof RepoIdAware)
		{
			return String.valueOf(((RepoIdAware)param).getRepoId());
		}
		else if(param instanceof ReferenceListAwareEnum)
		{
			return TO_STRING(((ReferenceListAwareEnum)param).getCode());
		}
		else if (param instanceof BigDecimal)
		{
			return TO_NUMBER((BigDecimal)param, DisplayType.Number);
		}
		else if (param instanceof Timestamp)
		{
			return TO_DATE((Timestamp)param);
		}
		else if (param instanceof java.util.Date)
		{
			return TO_DATE(TimeUtil.asTimestamp((java.util.Date)param));
		}
		else if (TimeUtil.isDateOrTimeObject(param))
		{
			return TO_DATE(TimeUtil.asTimestamp(param));
		}
		else if (param instanceof Boolean)
		{
			return TO_STRING(DisplayType.toBooleanString((Boolean)param));
		}
		else
		{
			throw new DBException("Unknown parameter type: " + param + " (" + param.getClass() + ")");
		}
	}

	public String TO_DATE(@NonNull final ZonedDateTime zdt)
	{
		return Database.TO_DATE(zdt);
	}

	public String TO_DATE(@NonNull final Instant instant)
	{
		return Database.TO_DATE(instant);
	}

	/**
	 * Create SQL TO Date String from LocalDate (without time zone)
	 *
	 * @param localDate Date to be converted
	 * @return 'YYYY-MM-DD'::timestamp without time zone
	 */
	@NonNull
	public String TO_DATE(@NonNull final LocalDate localDate)
	{
		return Database.TO_DATE(localDate);
	}

	/**
	 * Create SQL TO Date String from Timestamp
	 *
	 * @param time    Date to be converted
	 * @param dayOnly true if time set to 00:00:00
	 * @return TO_DATE(' 2001 - 01 - 30 18 : 10 : 20 ', ' ' YYYY - MM - DD HH24 : MI : SS ') or TO_DATE('2001-01-30',''YYYY-MM-DD')
	 */
	public String TO_DATE(@Nullable final Timestamp time, final boolean dayOnly)
	{
		return Database.TO_DATE(time, dayOnly);
	}

	/**
	 * Create SQL TO Date String from Timestamp
	 *
	 * @param day day time
	 * @return TO_DATE String (day only)
	 */
	public String TO_DATE(final Timestamp day)
	{
		return TO_DATE(day, true);
	}

	/**
	 * Create SQL for formatted Date, Number
	 *
	 * @param columnName          the column name in the SQL
	 * @param displayType         Display Type
	 * @param AD_Language_NOTUSED not used
	 * @return TRIM(TO_CHAR ( columnName, ' 999G999G999G990D00 ', ' NLS_NUMERIC_CHARACTERS = ' ', . ' ' ')) or TRIM(TO_CHAR(columnName,'TM9')) depending on DisplayType and Language
	 * @see org.compiere.util.DisplayType
	 * @see org.compiere.util.Env
	 */
	public String TO_CHAR(final String columnName, final int displayType, @Nullable final String AD_Language_NOTUSED)
	{
		return Database.TO_CHAR(columnName, displayType);
	}

	/**
	 * @see #TO_CHAR(String, int, String)
	 */
	public String TO_CHAR(final String columnName, final int displayType)
	{
		return Database.TO_CHAR(columnName, displayType);
	}

	/**
	 * Create SQL for formatted Date, Number.
	 *
	 * @param columnName          the column name in the SQL
	 * @param displayType         Display Type
	 * @param AD_Language_NOTUSED 6 character language setting (from Env.LANG_*)
	 * @param formatPattern       formatting pattern to be used ( {@link DecimalFormat} pattern, {@link SimpleDateFormat} pattern etc). In case the formatting pattern is not supported or is not valid, the
	 *                            implementation method can ignore it silently.
	 * @return SQL code
	 * @see Database#TO_CHAR(String, int, String)
	 */
	public String TO_CHAR(final String columnName, final int displayType, @Nullable final String AD_Language_NOTUSED, final String formatPattern)
	{
		if (columnName == null || columnName.length() == 0)
		{
			throw new IllegalArgumentException("Required parameter missing");
		}
		return Database.TO_CHAR(columnName, displayType, formatPattern);
	}   // TO_CHAR

	/**
	 * Return number as string for INSERT statements with correct precision
	 *
	 * @param number      number
	 * @param displayType display Type
	 * @return number as string
	 */
	public String TO_NUMBER(final BigDecimal number, final int displayType)
	{
		return Database.TO_NUMBER(number, displayType);
	}

	/**
	 * Package Strings for SQL command in quotes
	 *
	 * @param txt String with text
	 * @return escaped string for insert statement (NULL if null)
	 */
	public String TO_STRING(final String txt)
	{
		return TO_STRING(txt, 0);
	}   // TO_STRING

	/**
	 * Package Strings for SQL command in quotes.
	 *
	 * <pre>
	 * 	-	include in ' (single quotes)
	 * 	-	replace ' with ''
	 * </pre>
	 *
	 * @param txt       String with text
	 * @param maxLength Maximum Length of content or 0 to ignore
	 * @return escaped string for insert statement (NULL if null)
	 */
	public String TO_STRING(final String txt, final int maxLength)
	{
		if (txt == null
			// || txt.length() == 0 gh #213: don't return null for the empty string, (e.g. we have X_MRP_ProductInfo_Detail.ASIKey='' which is different from NULL)
		)
		{
			return "NULL";
		}
		// Length
		String text = txt;
		if (maxLength != 0 && text.length() > maxLength)
		{
			text = txt.substring(0, maxLength);
		}

		// copy characters (we need to look through anyway)
		final StringBuilder out = new StringBuilder();
		out.append(QUOTE);        // '
		for (int i = 0; i < text.length(); i++)
		{
			final char c = text.charAt(i);
			if (c == QUOTE)
			{
				out.append("''");
			}
			else
			{
				out.append(c);
			}
		}
		out.append(QUOTE);        // '
		//
		return out.toString();
	}    // TO_STRING

	public String TO_BOOLEAN(@Nullable final Boolean value)
	{
		final String valueStr = DisplayType.toBooleanString(value);
		return TO_STRING(valueStr);
	}

	/**
	 * @param comment
	 * @return SQL multiline comment
	 */
	public String TO_COMMENT(final String comment)
	{
		if (Check.isEmpty(comment, true))
		{
			return "";
		}

		return "/* "
				+ comment.replace("/*", " ").replace("*/", " ")
				+ " */";
	}

	/**
	 * convenient method to close result set
	 */
	public void close(@Nullable final ResultSet rs)
	{
		try
		{
			if (rs != null)
			{
				rs.close();
			}
		}
		catch (final SQLException ignored)
		{

		}
	}

	/**
	 * convenient method to close statement
	 *
	 * @param st
	 */
	public void close(@Nullable final Statement st)
	{
		try
		{
			if (st != null)
			{
				st.close();
			}
		}
		catch (final SQLException e)
		{

		}
	}

	/**
	 * convenient method to close result set and statement
	 *
	 * @param rs    result set
	 * @param pstmt statement
	 * @see #close(ResultSet)
	 * @see #close(Statement)
	 */
	public void close(@Nullable final ResultSet rs, @Nullable final Statement pstmt)
	{
		close(rs);
		close(pstmt);
	}

	/**
	 * convenient method to close a {@link POResultSet}
	 *
	 * @param rs result set
	 * @see POResultSet#close()
	 */
	public void close(final POResultSet<?> rs)
	{
		if (rs != null)
		{
			rs.close();
		}
	}

	/**
	 * Silently close the given database connection.
	 * <p>
	 * This method will never throw {@link SQLException}s.
	 *
	 * @param conn database connection.
	 */
	public void close(final Connection conn)
	{
		if (conn != null)
		{
			try
			{
				conn.close();
			}
			catch (final SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Quote
	 */
	private final char QUOTE = '\'';
	public final String QUOTE_STRING = String.valueOf(QUOTE);

	// Following methods are kept for BeanShell compatibility.
	// See BF [ 2030233 ] Remove duplicate code from DB class
	// TODO: remove this when BeanShell will support varargs methods
	public int getSQLValue(@Nullable final String trxName, final String sql)
	{
		return getSQLValue(trxName, sql, new Object[] {});
	}

	public int getSQLValue(@Nullable final String trxName, final String sql, final int int_param1)
	{
		return getSQLValue(trxName, sql, new Object[] { int_param1 });
	}

	public int getSQLValue(final String trxName, final String sql, final int int_param1, final int int_param2)
	{
		return getSQLValue(trxName, sql, new Object[] { int_param1, int_param2 });
	}

	public int getSQLValue(final String trxName, final String sql, final String str_param1)
	{
		return getSQLValue(trxName, sql, new Object[] { str_param1 });
	}

	public int getSQLValue(final String trxName, final String sql, final int int_param1, final String str_param2)
	{
		return getSQLValue(trxName, sql, new Object[] { int_param1, str_param2 });
	}

	public String getSQLValueString(final String trxName, final String sql, final int int_param1)
	{
		return getSQLValueString(trxName, sql, new Object[] { int_param1 });
	}

	public BigDecimal getSQLValueBD(final String trxName, final String sql, final int int_param1)
	{
		return getSQLValueBD(trxName, sql, new Object[] { int_param1 });
	}

	/**
	 * Create persistent selection in T_Selection table
	 */
	public void createT_Selection(@NonNull final PInstanceId pinstanceId, @NonNull final Iterable<Integer> selection, @Nullable final String trxName)
	{
		final int pinstanceRepoId = pinstanceId.getRepoId();

		StringBuilder insert = new StringBuilder();
		insert.append("INSERT INTO T_SELECTION(AD_PINSTANCE_ID, T_SELECTION_ID) ");
		int counter = 0;
		for (final Integer selectedId : selection)
		{
			counter++;
			if (counter > 1)
			{
				insert.append(" UNION ");
			}
			insert.append("SELECT ");
			insert.append(pinstanceRepoId);
			insert.append(", ");
			insert.append(selectedId);
			// insert.append(" FROM DUAL "); -- oracle

			if (counter >= 1000)
			{
				DB.executeUpdateEx(insert.toString(), trxName);
				insert = new StringBuilder();
				insert.append("INSERT INTO T_SELECTION(AD_PINSTANCE_ID, T_SELECTION_ID) ");
				counter = 0;
			}
		}
		if (counter > 0)
		{
			DB.executeUpdateEx(insert.toString(), trxName);
		}
	}

	/**
	 * Create persistent selection in T_Selection table
	 *
	 * @return generated AD_PInstance_ID that can be used to identify the selection
	 */
	public PInstanceId createT_Selection(@NonNull final Iterable<Integer> selection, @Nullable final String trxName)
	{
		final PInstanceId pinstanceId = Services.get(IADPInstanceDAO.class).createSelectionId();
		createT_Selection(pinstanceId, selection, trxName);
		return pinstanceId;
	}

	public PInstanceId createT_Selection(@NonNull final Set<? extends RepoIdAware> selection, @Nullable final String trxName)
	{
		final ImmutableList<Integer> ids = RepoIdAwares.asRepoIds(selection);
		return createT_Selection(ids, trxName);
	}

	public void createT_Selection(
			@NonNull final PInstanceId selectionId, 
			@NonNull final Collection<? extends RepoIdAware> selection,
			@Nullable final String trxName)
	{
		final ImmutableList<Integer> ids = RepoIdAwares.asRepoIds(selection);
		createT_Selection(selectionId, ids, trxName);
	}

	public PInstanceId createT_Selection(@NonNull final TableRecordReferenceSet recordRefs, final String trxName)
	{
		final Set<Integer> ids = recordRefs.toIntSet();
		return createT_Selection(ids, trxName);
	}

	public String createT_Selection_SqlWhereClause(
			@NonNull final PInstanceId selectionId,
			@NonNull final String sqlJoinColumn)
	{
		Check.assumeNotEmpty(sqlJoinColumn, "sqlJoinColumn is not empty");

		return "EXISTS (SELECT 1 FROM T_SELECTION zz WHERE zz.AD_PInstance_ID=" + selectionId.getRepoId()
				+ " AND zz.T_Selection_ID=" + sqlJoinColumn + ")";
	}

	/**
	 * Delete T_Selection
	 *
	 * @param pinstanceId
	 * @param trxName
	 * @return number of records that were deleted
	 */
	public int deleteT_Selection(final PInstanceId pinstanceId, @Nullable final String trxName)
	{
		final String sql = "DELETE FROM T_SELECTION WHERE AD_PInstance_ID=?";
		final int no = DB.executeUpdateEx(sql, new Object[] { pinstanceId }, trxName);
		return no;
	}

	/**
	 * Returns the current ITrxConstraints instance of the current thread. The instance is created on-the-fly the first time this method is called from a given thread. It is destroyed when the calling
	 * thread finishes.
	 * <p>
	 * Note that there might be more than one instance per thread, but there is only one active instance at a time. See {@link #saveConstraints()} and {@link #restoreConstraints()} for details.
	 */
	// metas 02367
	public ITrxConstraints getConstraints()
	{
		return Services.get(ITrxConstraintsBL.class).getConstraints();
	}

	/**
	 * Saves the current constraints instance of the current thread to be restored later on.
	 * <p>
	 * More specifically, the current constraints are copied and the copy is pushed to a stack (i.e. on top of the current instance). Therefore, the next invocation of {@link #getConstraints()} will
	 * return the copy. The calling thread can modify the copy for its temporary needs (e.g. relax some constraint while calling a particular method).
	 *
	 * @see #restoreConstraints()
	 */
	// metas 02367
	public void saveConstraints()
	{
		Services.get(ITrxConstraintsBL.class).saveConstraints();
	}

	/**
	 * Discards the currently active constraints instance and restores the one that has previously been saved.
	 *
	 * @see #saveConstraints()
	 */
	// metas 02367
	public void restoreConstraints()
	{
		Services.get(ITrxConstraintsBL.class).restoreConstraints();
	}

	public final String SQL_EmptyList = "(-1)";

	/**
	 * Build an SQL list for the given parameters.<br>
	 * E.g. for <code>paramsIn={1,2,3}</code> it returns the string <code>"(?,?,?)"</code>, and it will copy <code>paramsIn</code> to the list <code>paramsOut</code>. Note that e.g. if we need
	 * something like <code>AND ..._ID IN (?,?,?)</code>, then the ordering paramsIn's elements doesn't really matter.
	 *
	 * @param paramsIn
	 * @param paramsOut a list containing the prepared statement parameters for the returned SQL's question marks.
	 * @return SQL list (string)
	 */
	public String buildSqlList(final Collection<? extends Object> paramsIn, @NonNull final List<Object> paramsOut)
	{
		return buildSqlList(paramsIn, paramsOut::addAll);
	}

	public String buildSqlList(final Collection<? extends Object> paramsIn, @NonNull final Consumer<Collection<? extends Object>> paramsOutCollector)
	{
		if (paramsIn == null || paramsIn.isEmpty())
		{
			return SQL_EmptyList;
		}

		final StringBuilder sql = new StringBuilder("?");
		final int len = paramsIn.size();
		for (int i = 1; i < len; i++)
		{
			sql.append(",?");
		}

		paramsOutCollector.accept(paramsIn);

		return sql.insert(0, "(").append(")").toString();
	}

	/**
	 * @return e.g. (e.g. ColumnName IN (1, 2) OR ColumnName IS NULL)
	 */
	public String buildSqlList(
			@NonNull final String columnName,
			@NonNull final Collection<? extends Object> paramsIn)
	{
		final List<Object> paramsOut = null;
		return buildSqlList(columnName, paramsIn, paramsOut);
	}

	/**
	 * Build an SQL list (e.g. ColumnName IN (?, ?) OR ColumnName IS NULL)<br>
	 *
	 * @param paramsOut  if null, the parameters will be embedded in returned SQL
	 * @return sql
	 * @see InArrayQueryFilter
	 */
	public String buildSqlList(
			@NonNull final String columnName,
			@NonNull final Collection<? extends Object> paramsIn,
			@Nullable final List<Object> paramsOut)
	{
		Check.assumeNotEmpty(paramsIn, "paramsIn not empty");

		final boolean embedSqlParams = paramsOut == null;

		final InArrayQueryFilter<?> builder = new InArrayQueryFilter<>(columnName, paramsIn)
				.setEmbedSqlParams(embedSqlParams);
		final String sql = builder.getSql();

		if (!embedSqlParams)
		{
			final List<Object> sqlParams = builder.getSqlParams(Env.getCtx());
			paramsOut.addAll(sqlParams);
		}

		return sql;
	}

	/**
	 * Build an SQL list for given parameters. <br>
	 * E.g. for <code>paramsIn={1,2,3}</code> it might return the string <code>"(3,1,2)"</code>, depending on the order in which the elements are returned from the given <code>paramIn</code>'s
	 * iterable.
	 * <p>
	 * In other words, note that depending on the actual type of <code>paramsIn</code>, the ordering might vary, but usually that is not a problem.
	 *
	 * <p>
	 * <b>IMPORTANT: Please use {@link #buildSqlList(Collection, List)} instead!</b> When we used this method with Integer paramters, we got stuff which caused syntax errors! Example:
	 *
	 * <pre>
	 * WHERE M_ShipmentSchedule_ID IN (1150174'1150174',1150175'1150175',..
	 * </pre>
	 *
	 * @param paramsIn
	 * @return SQL list
	 * @see #buildSqlList(Collection, List)
	 */
	public String buildSqlList(final Collection<? extends Object> paramsIn)
	{
		if (paramsIn == null || paramsIn.isEmpty())
		{
			return SQL_EmptyList;
		}

		final StringBuilder sql = new StringBuilder();
		for (final Object paramIn : paramsIn)
		{
			if (sql.length() > 0)
			{
				sql.append(",");
			}

			// TODO: check and refactor together with TO_SQL(..)
			if (paramIn == null || paramIn instanceof Null)
			{
				sql.append("NULL");
			}
			else if (paramIn instanceof BigDecimal)
			{
				sql.append(TO_NUMBER((BigDecimal)paramIn, DisplayType.Number));
			}
			else if (paramIn instanceof Integer)
			{
				sql.append(paramIn);
			}
			else if (paramIn instanceof Date)
			{
				sql.append(TO_DATE(TimeUtil.asTimestamp((Date)paramIn)));
			}
			else
			{
				sql.append(TO_STRING(paramIn.toString()));
			}
		}

		return sql.insert(0, "(").append(")").toString();
	}

	public boolean isUseNativeSequences()
	{
		final boolean useNativeSequencesDefault = false;
		final boolean result = Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_SYSTEM_NATIVE_SEQUENCE, useNativeSequencesDefault);
		return result;
	}

	public boolean isUseNativeSequences(final int AD_Client_ID, final String TableName)
	{
		log.debug("Checking if we shall use native sequences for {} (AD_Client_ID={})", TableName, AD_Client_ID);

		//
		// Check: If Log Migration Scripts is enabled then don't use native sequences
		if (Ini.isPropertyBool(Ini.P_LOGMIGRATIONSCRIPT)
				&& Services.get(IMigrationLogger.class).isLogTableName(TableName))
		{
			log.debug("Returning 'false' for table {} because Ini-{} is active and this table is supposed to be logged", TableName, Ini.P_LOGMIGRATIONSCRIPT);
			return false;
		}

		//
		// Check: if Maintain Dictionary Entries is activated then don't use native sequences
		final boolean adempiereSys = Ini.isPropertyBool(Ini.P_ADEMPIERESYS);
		if (adempiereSys)
		{
			log.debug("Returning 'false' because Ini-{} (Maintain dictionary) is active ", Ini.P_ADEMPIERESYS);
			return false;
		}

		//
		// Check: if we shall use an external ID System (e.g. Dictionary/Project ID server) then don't use native sequences
		if (MSequence.isUseExternalIDSystem(TableName, AD_Client_ID)) // metas: 01558
		{
			log.debug("Returning 'false' because MSequence.isUseExternalIDSystem() returned 'true' for TableName {} and AD_Client_ID {}", TableName, AD_Client_ID);
			return false;
		}
		//
		// Default: use native sequences if activated
		final boolean useNativeSequences = isUseNativeSequences();
		log.debug("Returning the result of isUseNativeSequences: {}", useNativeSequences);
		return useNativeSequences;
	}

	public void setUseNativeSequences(final boolean enabled)
	{
		final Properties ctx = Env.getCtx();
		final int adClientId = Env.getAD_Client_ID(ctx);
		Check.assume(adClientId == 0, "Context AD_Client_ID shall be System if you want to change {} configuration, but it was {}",
				SYSCONFIG_SYSTEM_NATIVE_SEQUENCE, adClientId);

		Services.get(ISysConfigBL.class).setValue(SYSCONFIG_SYSTEM_NATIVE_SEQUENCE, enabled, ClientId.SYSTEM, OrgId.ANY);
	}

	public String getTableSequenceName(final String tableName)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");
		return tableName + "_SEQ";
	}

	/**
	 * Create database table sequence for given table name.
	 */
	public void createTableSequence(final String tableName)
	{
		final String sequenceName = getTableSequenceName(tableName);
		CConnection.get().getDatabase().createSequence(sequenceName,
				1, // increment
				1, // minvalue
				Integer.MAX_VALUE, // maxvalue
				1000000, // start
				ITrx.TRXNAME_ThreadInherited);
	}

	/**
	 * Get SQL DataType
	 *
	 * @param displayType AD_Reference_ID
	 * @param columnName  name
	 * @param fieldLength length
	 * @return SQL Data Type in Oracle Notation
	 */
	public String getSQLDataType(final int displayType, final String columnName, final int fieldLength)
	{
		return getDatabase().getSQLDataType(displayType, columnName, fieldLength);
	}

	/**
	 * Convert given SQL to database native SQL.
	 * <p>
	 * NOTE:
	 * <ul>
	 * <li>the main purpose of this method is to adapt old code which is not using PostgreSQL compatible SQLs while the whole system is not using conversion at all</li>
	 * <li>use it only if is really needed</li>
	 * </ul>
	 *
	 * @param sql
	 * @return converted SQL
	 */
	public String convertSqlToNative(final String sql)
	{
		Check.assumeNotEmpty(sql, "sql not empty");
		final Convert converter = getDatabase().getConvert();
		final List<String> sqlsConverted = converter.convert(sql);
		if (sqlsConverted == null || sqlsConverted.isEmpty())
		{
			throw new DBException("Failed to convert SQL: " + sql);
		}
		else if (sqlsConverted.size() == 1)
		{
			return sqlsConverted.get(0);
		}
		else
		{
			throw new DBException("Failed to convert SQL: " + sql
					+ "\nOnly one resulting SQL was expected but we got: " + sqlsConverted);
		}
	}

	/**
	 * @return default value for given <code>returnType</code>
	 */
	@SuppressWarnings("unchecked")
	public <AT> AT retrieveDefaultValue(final Class<AT> returnType)
	{
		if (returnType.isAssignableFrom(BigDecimal.class))
		{
			return (AT)BigDecimal.ZERO;
		}
		else if (returnType.isAssignableFrom(Integer.class) || returnType == int.class)
		{
			return (AT)Integer.valueOf(0);
		}
		else if (returnType.isAssignableFrom(Timestamp.class))
		{
			return null;
		}
		else if (returnType.isAssignableFrom(Boolean.class) || returnType == boolean.class)
		{
			return (AT)Boolean.FALSE;
		}
		else if (returnType.isAssignableFrom(String.class))
		{
			return null;
		}
		else if (returnType.isAssignableFrom(Double.class) || returnType == double.class)
		{
			return (AT)Double.valueOf(0.00);
		}
		else
		{
			return null;
		}
	}

	/**
	 * Retrieves a primite value from given {@link ResultSet}. <br/>
	 * The value is converted to given type.<br/>
	 */
	@SuppressWarnings("unchecked")
	public <AT> AT retrieveValue(final ResultSet rs, final String columnName, final Class<AT> returnType) throws SQLException
	{
		final AT value;
		if (returnType.isAssignableFrom(BigDecimal.class))
		{
			value = (AT)rs.getBigDecimal(columnName);
		}
		else if (returnType.isAssignableFrom(Double.class) || returnType == double.class)
		{
			value = (AT)Double.valueOf(rs.getDouble(columnName));
		}
		else if (returnType.isAssignableFrom(Integer.class) || returnType == int.class)
		{
			value = (AT)Integer.valueOf(rs.getInt(columnName));
		}
		else if (returnType.isAssignableFrom(Timestamp.class))
		{
			value = (AT)rs.getTimestamp(columnName);
		}
		else if (returnType.isAssignableFrom(Date.class))
		{
			final Timestamp ts = rs.getTimestamp(columnName);
			value = (AT)ts;
		}
		else if (returnType.isAssignableFrom(Boolean.class) || returnType == boolean.class)
		{
			value = (AT)StringUtils.toBoolean(rs.getString(columnName), Boolean.FALSE);
		}
		else if (returnType.isAssignableFrom(String.class))
		{
			value = (AT)rs.getString(columnName);
		}
		else
		{
			value = (AT)rs.getObject(columnName);
		}

		return value;
	}

	/**
	 * Retrieves a primite value from given {@link ResultSet}. <br/>
	 * The value is converted to given type.<br/>
	 */
	@SuppressWarnings("unchecked")
	public <AT> AT retrieveValue(final ResultSet rs, final int columnIndex, final Class<AT> returnType) throws SQLException
	{
		final AT value;
		if (returnType.isAssignableFrom(BigDecimal.class))
		{
			value = (AT)rs.getBigDecimal(columnIndex);
		}
		else if (returnType.isAssignableFrom(Double.class) || returnType == double.class)
		{
			value = (AT)Double.valueOf(rs.getDouble(columnIndex));
		}
		else if (returnType.isAssignableFrom(Integer.class) || returnType == int.class)
		{
			value = (AT)Integer.valueOf(rs.getInt(columnIndex));
		}
		else if (returnType.isAssignableFrom(Timestamp.class))
		{
			value = (AT)rs.getTimestamp(columnIndex);
		}
		else if (returnType.isAssignableFrom(Date.class))
		{
			final Timestamp ts = rs.getTimestamp(columnIndex);
			value = (AT)ts;
		}
		else if (returnType.isAssignableFrom(LocalDateTime.class))
		{
			final Timestamp ts = rs.getTimestamp(columnIndex);
			value = (AT)TimeUtil.asLocalDateTime(ts);
		}
		else if (returnType.isAssignableFrom(LocalDate.class))
		{
			final Timestamp ts = rs.getTimestamp(columnIndex);
			value = (AT)TimeUtil.asLocalDate(ts);
		}
		else if (returnType.isAssignableFrom(Boolean.class) || returnType == boolean.class)
		{
			value = (AT)StringUtils.toBoolean(rs.getString(columnIndex), Boolean.FALSE);
		}
		else if (returnType.isAssignableFrom(String.class))
		{
			value = (AT)rs.getString(columnIndex);
		}
		else if(RepoIdAware.class.isAssignableFrom(returnType))
		{
			@SuppressWarnings("unchecked")
			final Class<? extends RepoIdAware> repoIdAwareType = (Class<? extends RepoIdAware>)returnType;
			value = (AT)RepoIdAwares.ofRepoIdOrNull(rs.getInt(columnIndex), repoIdAwareType);
		}
		else
		{
			value = (AT)rs.getObject(columnIndex);
		}

		return value;
	}

	public <AT> AT retrieveValueOrDefault(final ResultSet rs, final int columnIndex, final Class<AT> returnType) throws SQLException
	{
		final AT value = retrieveValue(rs, columnIndex, returnType);
		if (value != null)
		{
			return value;
		}

		return retrieveDefaultValue(returnType);
	}

	@FunctionalInterface
	public interface ResultSetConsumer
	{
		void accept(ResultSet rs) throws SQLException;
	}

	public void forEachRow(
			@NonNull final String sql,
			@Nullable final List<Object> sqlParams,
			@NonNull final ResultSetConsumer rowConsumer)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				rowConsumer.accept(rs);
			}
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql, sqlParams);
		}
		finally
		{
			close(rs, pstmt);
		}
	}

	@FunctionalInterface
	public interface ResultSetRowLoader<T>
	{
		@Nullable
		T retrieveRowOrNull(ResultSet rs) throws SQLException;
	}

	@NonNull
	public static <T> ImmutableList<T> retrieveRowsOutOfTrx(
			@NonNull final CharSequence sql,
			@Nullable final List<Object> sqlParams,
			@NonNull final ResultSetRowLoader<T> loader)
	{
		return retrieveRows(sql, sqlParams, ITrx.TRXNAME_None, loader);
	}

	@NonNull
	public static <T> ImmutableList<T> retrieveRows(
			@NonNull final CharSequence sql,
			@Nullable final List<Object> sqlParams,
			@NonNull final ResultSetRowLoader<T> loader)
	{
		return retrieveRows(sql, sqlParams, ITrx.TRXNAME_ThreadInherited, loader);
	}

	@NonNull
<<<<<<< HEAD
	private static <T> ImmutableList<T> retrieveRows(
=======
	public static <T> ImmutableList<T> retrieveRows(
			@NonNull final CharSequence sql,
			@Nullable final Object[] sqlParams,
			@NonNull final ResultSetRowLoader<T> loader)
	{
		final List<Object> sqlParamsList = sqlParams != null && sqlParams.length > 0 ? Arrays.asList(sqlParams) : null;
		return retrieveRows(sql, sqlParamsList, loader);
	}

	@NonNull
	public static <T> ImmutableSet<T> retrieveUniqueRows(
>>>>>>> bceb13a01f1 (webui Record Change Log - display timestamps using frontend locale/timezone (#17848))
			@NonNull final CharSequence sql,
			@Nullable final List<Object> sqlParams,
			@Nullable final String trxName,
			@NonNull final ResultSetRowLoader<T> loader)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = prepareStatement(sql.toString(), trxName);
			setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			final ImmutableList.Builder<T> rows = ImmutableList.builder();
			while (rs.next())
			{
				final T row = loader.retrieveRowOrNull(rs);
				if (row != null)
				{
					rows.add(row);
				}
			}

			return rows.build();
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql, sqlParams);
		}
		finally
		{
			close(rs, pstmt);
		}
	}

	public void forFirstRowIfAny(
			@NonNull final String sql,
			@Nullable final List<Object> sqlParams,
			@NonNull final ResultSetConsumer consumer)
	{
		retrieveFirstRowOrNull(sql, sqlParams, (rs) -> {
			consumer.accept(rs);
			return null;
		});
	}

	public <T> T retrieveFirstRowOrNull(
			@NonNull final String sql,
			@Nullable final List<Object> sqlParams,
			@NonNull final ResultSetRowLoader<T> loader)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				return loader.retrieveRowOrNull(rs);
			}
			else
			{
				return null;
			}
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql, sqlParams);
		}
		finally
		{
			close(rs, pstmt);
		}
	}

	public String normalizeDBIdentifier(@NonNull final String dbIdentifier, @NonNull final DatabaseMetaData md)
	{
		try
		{
			if (md.storesUpperCaseIdentifiers())
			{
				return dbIdentifier.toUpperCase();
			}
			else if (md.storesLowerCaseIdentifiers())
			{
				return dbIdentifier.toLowerCase();
			}
			else
			{
				return dbIdentifier;
			}
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex);
		}
	}

	public boolean isDBColumnPresent(@NonNull final String tableName, @NonNull final String columnName)
	{
		final String catalog = getDatabase().getCatalog();
		final String schema = getDatabase().getSchema();
		String tableNameNorm = tableName;
		String columnNameNorm = columnName;

		Connection conn = null;
		ResultSet rs = null;
		try
		{
			conn = getConnectionRO();
			final DatabaseMetaData md = conn.getMetaData();
			tableNameNorm = normalizeDBIdentifier(tableName, md);
			columnNameNorm = normalizeDBIdentifier(columnName, md);

			//
			rs = md.getColumns(catalog, schema, tableNameNorm, columnNameNorm);
			if (rs.next())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex)
					.appendParametersToMessage()
					.setParameter("catalog", catalog)
					.setParameter("schema", schema)
					.setParameter("tableNameNorm", tableNameNorm)
					.setParameter("columnNameNorm", columnNameNorm);
		}
		finally
		{
			DB.close(rs);
			DB.close(conn);
		}

	}

	/**
	 * @param sqlStatement SQL statement to be executed
	 * @param parameters   Parameters to be used in the {@param sqlStatement}
	 * @param trxName      transaction name
	 * @return each resulted row as a {@link List<String>}
	 */
	public ImmutableList<List<String>> getSQL_ResultRowsAsListsOfStrings(final String sqlStatement, final List<Object> parameters, final String trxName)
	{
		List<List<String>> result = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = prepareStatement(sqlStatement, trxName);

			setParameters(pstmt, parameters);

			rs = pstmt.executeQuery();

			final int columnsCount = rs.getMetaData().getColumnCount();

			if (columnsCount > 0)
			{
				result = new ArrayList<>();

				while (rs.next())
				{
					final List<String> row = new ArrayList<>();

					for (int i = 1; i <= columnsCount; i++)
					{
						row.add(rs.getString(i));
					}

					result.add(row);
				}
			}
		}
		catch (final SQLException sqlException)
		{
			throw new DBException(sqlException, sqlStatement, parameters);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
		return result != null ? ImmutableList.copyOf(result) : ImmutableList.of();
	}

} // DB
