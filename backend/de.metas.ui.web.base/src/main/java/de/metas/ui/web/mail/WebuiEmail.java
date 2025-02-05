package de.metas.ui.web.mail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Builder(toBuilder = true)
@Value
public class WebuiEmail
{
	@NonNull
	String emailId;
	UserId ownerUserId;

	LookupValue from;
	@Default
	LookupValuesList to = LookupValuesList.EMPTY;
	String subject;
	String message;
	@Default
	LookupValuesList attachments = LookupValuesList.EMPTY;

	boolean sent;

	DocumentPath contextDocumentPath;

	@JsonIgnore
	public UserId getFromUserId() {return from.getIdAs(UserId::ofRepoId);}
}
