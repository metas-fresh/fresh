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

package de.metas.audit.apirequest.request;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import de.metas.audit.apirequest.HttpMethod;
import de.metas.audit.apirequest.config.ApiAuditConfigId;
import de.metas.organization.OrgId;
import de.metas.security.RoleId;
import de.metas.ui_trace.UITraceExternalId;
import de.metas.user.UserId;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Instant;

@ExtendWith(SnapshotExtension.class)
public class ApiRequestAuditRepositoryTest
{
	private ApiRequestAuditRepository apiRequestAuditRepository;
	private Expect expect;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		apiRequestAuditRepository = new ApiRequestAuditRepository();
	}

	@Test
	public void save()
	{
		//given
		final ApiRequestAudit apiRequestAudit = ApiRequestAudit.builder()
				.orgId(OrgId.ofRepoId(1))
				.roleId(RoleId.SYSTEM)
				.userId(UserId.METASFRESH)
				.apiAuditConfigId(ApiAuditConfigId.ofRepoId(3))
				.status(Status.RECEIVED)
				.isErrorAcknowledged(false)
				.body("body")
				.method(HttpMethod.POST)
				.path("path")
				.remoteAddress("remoteAddress")
				.remoteHost("remoteHost")
				.time(Instant.ofEpochMilli(0))
				.httpHeaders("httpHeaders")
				.requestURI("request/uri")
				.uiTraceExternalId(UITraceExternalId.ofString("myUITraceExternalId"))
				.build();

		//when
		final ApiRequestAudit result = apiRequestAuditRepository.save(apiRequestAudit);

		//then
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}
}
