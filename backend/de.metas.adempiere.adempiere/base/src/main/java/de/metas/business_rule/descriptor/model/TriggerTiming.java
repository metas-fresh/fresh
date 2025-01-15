package de.metas.business_rule.descriptor.model;

import org.adempiere.ad.modelvalidator.ModelChangeType;

import java.util.Optional;

public enum TriggerTiming
{
	NEW,
	UPDATE,
	DELETE,
	;

	public static Optional<TriggerTiming> ofModelChangeType(final ModelChangeType changeType)
	{
		switch (changeType)
		{
			case BEFORE_NEW:
				return Optional.of(NEW);
			case BEFORE_CHANGE:
				return Optional.of(UPDATE);
			case BEFORE_DELETE:
				return Optional.of(DELETE);
			default:
				return Optional.empty();
		}
	}
}
