package de.metas.i18n;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.currency.Amount;
import de.metas.reflist.ReferenceId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.service.IADReferenceDAO;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

/**
 * Translatable Strings Facade.
 */
@UtilityClass
public class TranslatableStrings
{
	public TranslatableStringBuilder builder()
	{
		return TranslatableStringBuilder.newInstance();
	}

	public ITranslatableString join(final String joiningString, final Object... trls)
	{
		Check.assumeNotEmpty(trls, "trls is not empty");

		final List<ITranslatableString> trlsList = Stream.of(trls)
				.flatMap(TranslatableStrings::explodeCollections)
				.map(TranslatableStrings::toTranslatableStringOrNull)
				.filter(Objects::nonNull) // skip nulls
				.collect(ImmutableList.toImmutableList());

		return joinList(joiningString, trlsList);
	}

	private Stream<Object> explodeCollections(final Object obj)
	{
		if (obj == null)
		{
			return Stream.empty();
		}
		else if (obj instanceof Collection)
		{
			@SuppressWarnings("unchecked") final Collection<Object> coll = (Collection<Object>)obj;
			return coll.stream()
					.flatMap(TranslatableStrings::explodeCollections);
		}
		else
		{
			return Stream.of(obj);
		}
	}

	/**
	 * @return translatable string or null if the <code>trlObj</code> is null or empty string
	 */
	@Nullable
	private ITranslatableString toTranslatableStringOrNull(final Object trlObj)
	{
		if (trlObj == null)
		{
			return null;
		}
		else if (trlObj instanceof ITranslatableString)
		{
			return (ITranslatableString)trlObj;
		}
		else
		{
			final String trlStr = trlObj.toString();
			if (trlStr == null || trlStr.isEmpty())
			{
				return null;
			}
			else
			{
				return constant(trlStr);
			}
		}
	}

	public ITranslatableString joinList(final String joiningString, @NonNull final List<ITranslatableString> trls)
	{
		if (trls.isEmpty())
		{
			return empty();
		}
		else if (trls.size() == 1)
		{
			return trls.get(0);
		}
		else
		{
			return new CompositeTranslatableString(trls, joiningString);
		}
	}

	public Collector<ITranslatableString, ?, ITranslatableString> joining(final String joiningString)
	{
		final Supplier<List<ITranslatableString>> supplier = ArrayList::new;
		@SuppressWarnings("Convert2MethodRef") final BiConsumer<List<ITranslatableString>, ITranslatableString> accumulator = (accum, e) -> accum.add(e);
		final BinaryOperator<List<ITranslatableString>> combiner = (accum1, accum2) -> {
			accum1.addAll(accum2);
			return accum1;
		};
		final Function<List<ITranslatableString>, ITranslatableString> finisher = accum -> joinList(joiningString, accum);
		return Collector.of(supplier, accumulator, combiner, finisher);
	}

	public ITranslatableString constant(@Nullable final String value)
	{
		return ConstantTranslatableString.of(value);
	}

	public ITranslatableString anyLanguage(@Nullable final String value)
	{
		return ConstantTranslatableString.anyLanguage(value);
	}

	@NonNull
	public ITranslatableString singleLanguage(@Nullable final String adLanguage, @Nullable final String value)
	{
		if (adLanguage == null || Check.isBlank(adLanguage))
		{
			return ConstantTranslatableString.of(value);
		}

		final String valueNorm = value == null ? "" : value;
		return ofMap(ImmutableMap.of(adLanguage, valueNorm), valueNorm);
	}

	@NonNull
	public ITranslatableString empty()
	{
		return ConstantTranslatableString.EMPTY;
	}

	public boolean isEmpty(final ITranslatableString trl)
	{
		if (trl == null)
		{
			return true;
		}
		else if (trl instanceof ConstantTranslatableString)
		{
			return ((ConstantTranslatableString)trl).isEmpty();
		}
		else if (trl instanceof ImmutableTranslatableString)
		{
			return ((ImmutableTranslatableString)trl).isEmpty();
		}
		else
		{
			return false;
		}
	}

	public boolean isBlank(@Nullable final ITranslatableString trl)
	{
		if (trl == null)
		{
			return true;
		}
		else if (trl == ConstantTranslatableString.EMPTY)
		{
			return true;
		}
		else if (trl instanceof ConstantTranslatableString
				|| trl instanceof ImmutableTranslatableString
		)
		{
			return Check.isBlank(trl.getDefaultValue());
		}
		else
		{
			return false;
		}
	}

	@NonNull
	public ITranslatableString nullToEmpty(@Nullable final ITranslatableString trl)
	{
		return trl != null ? trl : empty();
	}

	public ITranslatableString amount(@NonNull final Amount amount)
	{
		return builder().append(amount).build();
	}

	public NumberTranslatableString number(final BigDecimal valueBD, final int displayType)
	{
		return NumberTranslatableString.of(valueBD, displayType);
	}

	public NumberTranslatableString number(final int valueInt)
	{
		return NumberTranslatableString.of(valueInt);
	}

	public DateTimeTranslatableString date(@NonNull final java.util.Date date)
	{
		return DateTimeTranslatableString.ofDate(date);
	}

	public DateTimeTranslatableString date(@NonNull final LocalDate date)
	{
		return DateTimeTranslatableString.ofDate(date);
	}

	public DateTimeTranslatableString date(@NonNull final Object obj, final int displayType)
	{
		return DateTimeTranslatableString.ofObject(obj, displayType);
	}

	public DateTimeTranslatableString dateAndTime(@NonNull final ZonedDateTime date)
	{
		return DateTimeTranslatableString.ofDateTime(date);
	}

	public DateTimeTranslatableString dateAndTime(@NonNull final java.util.Date date)
	{
		return DateTimeTranslatableString.ofDateTime(date);
	}

	public ITranslatableString ofMap(final Map<String, String> trlMap)
	{
		if (trlMap == null || trlMap.isEmpty())
		{
			return ConstantTranslatableString.EMPTY;
		}
		else
		{
			return ImmutableTranslatableString.ofMap(trlMap, ConstantTranslatableString.EMPTY.getDefaultValue());
		}
	}

	public ITranslatableString ofMap(final Map<String, String> trlMap, final String defaultValue)
	{
		if (trlMap == null || trlMap.isEmpty())
		{
			return ConstantTranslatableString.of(defaultValue);
		}
		else
		{
			return ImmutableTranslatableString.ofMap(trlMap, defaultValue);
		}
	}

	public ForwardingTranslatableString forwardingTo(@NonNull final Supplier<ITranslatableString> delegateSupplier)
	{
		return new ForwardingTranslatableString(delegateSupplier);
	}

	public ITranslatableString copyOf(@NonNull final ITranslatableString trl)
	{
		return copyOfNullable(trl);
	}

	/**
	 * @return {@link ImmutableTranslatableString} or {@link #empty()} if <code>trl</code> was null
	 */
	public ITranslatableString copyOfNullable(@Nullable final ITranslatableString trl)
	{
		if (trl == null)
		{
			return ConstantTranslatableString.EMPTY;
		}

		if (trl instanceof ConstantTranslatableString)
		{
			return trl;
		}
		if (trl instanceof ImmutableTranslatableString)
		{
			return trl;
		}

		final Set<String> adLanguages = trl.getAD_Languages();
		final Map<String, String> trlMap = new LinkedHashMap<>(adLanguages.size());
		for (final String adLanguage : adLanguages)
		{
			final String trlString = trl.translate(adLanguage);
			if (trlString == null)
			{
				continue;
			}

			trlMap.put(adLanguage, trlString);
		}

		return ofMap(trlMap, trl.getDefaultValue());
	}

	public ITranslatableString ofTimeZone(@NonNull final ZoneId timeZone, @NonNull final TextStyle textStyle)
	{
		return TimeZoneTranslatableString.ofZoneId(timeZone, textStyle);
	}

	public static ITranslatableString parse(@Nullable final String text)
	{
		if (text == null || text.isEmpty())
		{
			return TranslatableStrings.empty();
		}

		final TranslatableStringBuilder builder = TranslatableStrings.builder();

		String inStr = text;
		int idx = inStr.indexOf('@');
		while (idx != -1)
		{
			builder.append(inStr.substring(0, idx)); // up to @
			inStr = inStr.substring(idx + 1); // from first @

			final int j = inStr.indexOf('@'); // next @
			if (j < 0) // no second tag
			{
				inStr = "@" + inStr;
				break;
			}

			final String token = inStr.substring(0, j);
			if (token.isEmpty())
			{
				builder.append("@");
			}
			else
			{
				builder.appendADElement(token); // replace context
			}

			inStr = inStr.substring(j + 1);    // from second @
			idx = inStr.indexOf('@');
		}

		// add remainder
		if (inStr.length() > 0)
		{
			builder.append(inStr);
		}

		return builder.build();
	}

	public static ITranslatableString adElementOrMessage(@NonNull final String columnName)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		return msgBL.translatable(columnName);
	}

	public static ITranslatableString adMessage(@NonNull final AdMessageKey adMessage, @Nullable final Object... msgParameters)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		return msgBL.getTranslatableMsgText(adMessage, msgParameters);
	}

	public static ITranslatableString adRefList(final int adReferenceId, @NonNull final String value)
	{
		return adRefList(ReferenceId.ofRepoId(adReferenceId), value);
	}

	public static ITranslatableString adRefList(@NonNull final ReferenceId adReferenceId, @NonNull final String value)
	{
		final IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);

		return adReferenceDAO.getRefListById(adReferenceId)
				.getItemByValue(value)
				.map(IADReferenceDAO.ADRefListItem::getName)
				.orElseGet(() -> anyLanguage(value));
	}

}
