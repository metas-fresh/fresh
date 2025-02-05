package de.metas.ui.web.mail;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.adempiere.ad.validationRule.IValidationRuleFactory;
import org.compiere.model.I_AD_User;
import org.compiere.util.DisplayType;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.UnaryOperator;

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

@Component
public class WebuiMailRepository
{
	private final ApplicationEventPublisher eventPublisher;

	private final AtomicInteger nextEmailId = new AtomicInteger(1);
	private final Cache<String, WebuiEmailEntry> emailsById = CacheBuilder.newBuilder()
			.expireAfterAccess(2, TimeUnit.HOURS)
			.removalListener(notification -> onEmailRemoved(((WebuiEmailEntry)notification.getValue()).getEmail()))
			.build();

	private final LookupDataSource emailToLookup;

	public WebuiMailRepository(
			@NonNull final ApplicationEventPublisher eventPublisher,
			@NonNull final LookupDataSourceFactory lookupDataSourceFactory)
	{
		this.eventPublisher = eventPublisher;
		emailToLookup = lookupDataSourceFactory.getLookupDataSource(builder -> builder
				.setCtxTableName(null)
				.setCtxColumnName(org.compiere.model.I_AD_User.COLUMNNAME_AD_User_ID)
				.setDisplayType(DisplayType.Search)
				.addValidationRule(Services.get(IValidationRuleFactory.class).createSQLValidationRule(I_AD_User.COLUMNNAME_EMail + " IS NOT NULL"))
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.buildForDefaultScope());
	}

	public WebuiEmail createNewEmail(
			@NonNull final UserId ownerUserId,
			@NonNull final LookupValue from,
			@NonNull final LookupValuesList toList,
			@Nullable final DocumentPath contextDocumentPath)
	{
		final String emailId = String.valueOf(nextEmailId.getAndIncrement());

		final WebuiEmail email = WebuiEmail.builder()
				.emailId(emailId)
				.ownerUserId(ownerUserId)
				.from(from)
				.to(toList)
				.contextDocumentPath(contextDocumentPath)
				.build();

		emailsById.put(emailId, new WebuiEmailEntry(email));
		return email;
	}

	private WebuiEmailEntry getEmailEntry(final String emailId)
	{
		final WebuiEmailEntry emailEntry = emailsById.getIfPresent(emailId);
		if (emailEntry == null)
		{
			throw new EntityNotFoundException("Email not found")
					.setParameter("emailId", emailId);
		}
		return emailEntry;
	}

	public WebuiEmail getEmail(final String emailId)
	{
		return getEmailEntry(emailId).getEmail();
	}

	WebuiEmailChangeResult changeEmail(final String emailId, final UnaryOperator<WebuiEmail> emailModifier)
	{
		return getEmailEntry(emailId).compute(emailModifier);
	}

	public void removeEmailById(final String emailId)
	{
		emailsById.invalidate(emailId);
	}

	/**
	 * Called when the email was removed from our internal cache.
	 */
	private void onEmailRemoved(final WebuiEmail email)
	{
		eventPublisher.publishEvent(new WebuiEmailRemovedEvent(email));
	}

	public LookupValuesPage getToTypeahead(final String ignoredEmailId, final String query)
	{
		final Evaluatee ctx = Evaluatees.empty(); // TODO

		// TODO: filter only those which have a valid email address

		return emailToLookup.findEntities(ctx, query);
	}

	public LookupValue getToByUserId(final Integer adUserId)
	{
		return emailToLookup.findById(adUserId);
	}

	@ToString
	private static final class WebuiEmailEntry
	{
		private WebuiEmail email;

		public WebuiEmailEntry(@NonNull final WebuiEmail email)
		{
			this.email = email;
		}

		public synchronized WebuiEmail getEmail()
		{
			return email;
		}

		public synchronized WebuiEmailChangeResult compute(final UnaryOperator<WebuiEmail> modifier)
		{
			final WebuiEmail emailOld = email;
			final WebuiEmail emailNew = modifier.apply(emailOld);
			if (emailNew == null)
			{
				throw new NullPointerException("email");
			}

			email = emailNew;
			return WebuiEmailChangeResult.builder().email(emailNew).originalEmail(emailOld).build();
		}
	}

	@Value
	@AllArgsConstructor
	public static class WebuiEmailRemovedEvent
	{
		@NonNull WebuiEmail email;
	}
}
