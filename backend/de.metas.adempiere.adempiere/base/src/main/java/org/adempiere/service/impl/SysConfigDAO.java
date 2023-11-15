package org.adempiere.service.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigDAO;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.util.DB;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SysConfigDAO implements ISysConfigDAO
{
	private final Logger logger = LogManager.getLogger(SysConfigDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, SysConfigMap> cache = CCache.<Integer, SysConfigMap>builder()
			.tableName(I_AD_SysConfig.Table_Name)
			.initialCapacity(1)
			.expireMinutes(CCache.EXPIREMINUTES_Never)
			.build();

	@Override
	public void setValue(
			@NonNull final String name,
			@Nullable final String value,
			@NonNull final ClientAndOrgId clientAndOrgId)
	{
		I_AD_SysConfig sysConfig = queryBL
				.createQueryBuilder(I_AD_SysConfig.class)
				.addEqualsFilter(I_AD_SysConfig.COLUMNNAME_Name, name)
				.addEqualsFilter(I_AD_SysConfig.COLUMNNAME_AD_Client_ID, clientAndOrgId.getClientId())
				.addEqualsFilter(I_AD_SysConfig.COLUMNNAME_AD_Org_ID, clientAndOrgId.getOrgId())
				.create()
				.firstOnly(I_AD_SysConfig.class);

		final String oldValue;
		if (sysConfig == null)
		{
			oldValue = null;
			sysConfig = InterfaceWrapperHelper.newInstance(I_AD_SysConfig.class);
			InterfaceWrapperHelper.setValue(sysConfig, I_AD_SysConfig.COLUMNNAME_AD_Client_ID, clientAndOrgId.getClientId().getRepoId());
			sysConfig.setName(name);
			sysConfig.setAD_Org_ID(clientAndOrgId.getOrgId().getRepoId());
		}
		else
		{
			oldValue = sysConfig.getValue();
		}

		final String valuePO = value != null ? value : SysConfigEntryValue.NO_VALUE_MARKER;
		sysConfig.setValue(valuePO);
		InterfaceWrapperHelper.save(sysConfig);
		logger.info("Set SysConfig `{}` to `{}` (Old value: `{}`, Client/Org: {})", name, valuePO, oldValue, clientAndOrgId);
	}

	@Override
	public void setValue(final @NonNull String name, final int value, final @NonNull ClientAndOrgId clientAndOrgId)
	{
		setValue(name, String.valueOf(value), clientAndOrgId);
	}

	@Override
	public void setValue(final @NonNull String name, final boolean value, final @NonNull ClientAndOrgId clientAndOrgId)
	{
		setValue(name, StringUtils.ofBoolean(value), clientAndOrgId);
	}

	@Override
	public Optional<String> getValue(@NonNull final String name, @NonNull final ClientAndOrgId clientAndOrgId)
	{
		final Optional<String> value = SpringContextHolder.instance.getProperty(name);
		if (value.isPresent())
		{
			return value;
		}

		return getMap().getValueAsString(name, clientAndOrgId);
	}

	@Override
	public List<String> retrieveNamesForPrefix(
			@NonNull final String prefix,
			@NonNull final ClientAndOrgId clientAndOrgId)
	{
		return getMap().getNamesForPrefix(prefix, clientAndOrgId);
	}

	private SysConfigMap getMap()
	{
		return cache.getOrLoad(0, this::retrieveMap);
	}

	private SysConfigMap retrieveMap()
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();
		final ImmutableListMultimap<String, SysConfigEntryValue> entryValuesByName = retrieveSysConfigEntryValues();

		final ImmutableMap<String, SysConfigEntry> entiresByName = entryValuesByName.asMap()
				.entrySet()
				.stream()
				.map(e -> SysConfigEntry.builder()
						.name(e.getKey())
						.entryValues(e.getValue())
						.build())
				.collect(ImmutableMap.toImmutableMap(
						SysConfigEntry::getName,
						entry -> entry
				));

		final SysConfigMap result = new SysConfigMap(entiresByName);

		logger.info("Retrieved {} in {}", result, stopwatch.stop());
		return result;
	}

	protected ImmutableListMultimap<String, SysConfigEntryValue> retrieveSysConfigEntryValues()
	{

		final String sql = "SELECT Name, Value, AD_Client_ID, AD_Org_ID FROM AD_SysConfig"
				+ " WHERE IsActive='Y'"
				+ " ORDER BY Name, AD_Client_ID, AD_Org_ID";

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
		{
			final ImmutableListMultimap.Builder<String, SysConfigEntryValue> resultBuilder = ImmutableListMultimap.builder();

			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final String name = rs.getString("Name");
				final String value = rs.getString("Value");
				final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(
						ClientId.ofRepoId(rs.getInt("AD_Client_ID")),
						OrgId.ofRepoId(rs.getInt("AD_Org_ID")));

				final SysConfigEntryValue entryValue = SysConfigEntryValue.of(value, clientAndOrgId);

				resultBuilder.put(name, entryValue);
			}

			return resultBuilder.build();
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
}
