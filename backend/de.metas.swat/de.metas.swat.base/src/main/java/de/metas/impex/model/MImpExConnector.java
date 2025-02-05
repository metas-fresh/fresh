package de.metas.impex.model;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

import org.compiere.util.Util;

import de.metas.adempiere.util.Parameter;
import de.metas.impex.spi.IConnector;

public class MImpExConnector extends X_ImpEx_Connector
{
	private static final long serialVersionUID = -4915263677859779046L;

	public MImpExConnector(Properties ctx, int C_ImpExConnector_ID, String trxName)
	{
		super(ctx, C_ImpExConnector_ID, trxName);
	}

	public MImpExConnector(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	private IConnector getConnector()
	{
		final String className = getImpEx_ConnectorType().getClassname();
		final IConnector connector = ConnectionPool.getInstance().getConnection(className);
		return connector;
	}

	public CompletableFuture<IConnector> useConnectorAsync()
	{
		return CompletableFuture.supplyAsync(() -> {
			final IConnector connector = getConnector();

			final List<MImpexConnectorParam> mParams = MImpexConnectorParam.retrieve(this);
			final List<Parameter> connectorParams = new ArrayList<>(mParams.size());

			for (final MImpexConnectorParam mParam : mParams)
			{
				final Parameter connectorParam = new Parameter(
						mParam.getName(),
						mParam.getParamName(),
						mParam.getDescription(),
						mParam.getAD_Reference_ID(),
						mParam.getSeqNo());
				connectorParam.setValue(mParam.getParamValue());
				connectorParams.add(connectorParam);
			}

			connector.open(connectorParams);

			return connector;
		});
	}

	public CompletableFuture<Void> createParametersAsync()
	{
		return CompletableFuture.runAsync(() -> {
			deleteParameters();

			for (final Parameter param : getConnector().getParameters())
			{
				final MImpexConnectorParam mParam = new MImpexConnectorParam(
						getCtx(), 0, get_TrxName());
				mParam.setParamName(param.displayName);
				mParam.setDescription(param.description);
				mParam.setAD_Reference_ID(param.displayType);
				mParam.setName(param.name);
				mParam.setSeqNo(param.seqNo);
				mParam.setImpEx_Connector_ID(this.get_ID());

				mParam.saveEx();
			}
		});
	}

	private void deleteParameters()
	{
		for (final MImpexConnectorParam mParam : MImpexConnectorParam.retrieve(this))
		{
			mParam.deleteEx(false);
		}
	}

	@Override
	protected boolean beforeDelete()
	{
		deleteParameters();
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (success && (newRecord || is_ValueChanged(COLUMNNAME_ImpEx_ConnectorType_ID)))
		{
			createParametersAsync();
		}
		return success;
	}

	public static class ConnectionPool
	{
		private static final ConnectionPool instance = new ConnectionPool();
		private ConnectionPool() {}

		public static ConnectionPool getInstance()
		{
			return instance;
		}

		public IConnector getConnection(String className)
		{
			try
			{
				return Util.getInstance(IConnector.class, className);
			}
			catch (Exception e)
			{
				throw new RuntimeException("An error occured while getting the connection", e);
			}
		}
	}
}