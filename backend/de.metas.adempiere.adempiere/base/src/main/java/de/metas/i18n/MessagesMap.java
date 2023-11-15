package de.metas.i18n;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public final class MessagesMap
{
	public static final MessagesMap EMPTY = new MessagesMap(ImmutableList.of());

	private final ImmutableMap<AdMessageKey, Message> byAdMessage;
	private final ImmutableMap<AdMessageId, Message> byId;

	MessagesMap(final List<Message> list)
	{
		this.byAdMessage = Maps.uniqueIndex(list, Message::getAdMessage);
		this.byId = list.stream()
				.filter(message -> message.getAdMessageId() != null)
				.collect(ImmutableMap.toImmutableMap(Message::getAdMessageId, message -> message));
	}

	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("size", byAdMessage.size())
				.toString();
	}

	public boolean isMessageExists(AdMessageKey adMessage)
	{
		return getByAdMessage(adMessage) != null;
	}

	public Optional<AdMessageId> getIdByAdMessage(final AdMessageKey adMessage)
	{
		return Optional.ofNullable(getByAdMessage(adMessage)).map(Message::getAdMessageId);
	}

	@Nullable
	public Message getByAdMessage(@NonNull final String adMessage)
	{
		return getByAdMessage(AdMessageKey.of(adMessage));
	}

	@Nullable
	public Message getByAdMessage(@NonNull final AdMessageKey adMessage)
	{
		return byAdMessage.get(adMessage);
	}

	public Stream<Message> stream() {return byAdMessage.values().stream();}

	public Map<String, String> toStringMap(final String adLanguage, final String prefix, boolean removePrefix)
	{
		final Function<Message, String> keyFunction;
		if (removePrefix)
		{
			final int beginIndex = prefix.length();
			keyFunction = message -> message.getAdMessage().toAD_Message().substring(beginIndex);
		}
		else
		{
			keyFunction = message -> message.getAdMessage().toAD_Message();
		}

		return stream()
				.filter(message -> message.getAdMessage().startsWith(prefix))
				.collect(ImmutableMap.toImmutableMap(keyFunction, message -> message.getMsgText(adLanguage)));
	}

	public Optional<Message> getById(@NonNull final AdMessageId adMessageId)
	{
		return Optional.ofNullable(byId.get(adMessageId));
	}

	public Optional<AdMessageKey> getAdMessageKeyById(final AdMessageId adMessageId)
	{
		return getById(adMessageId).map(Message::getAdMessage);
	}
}
