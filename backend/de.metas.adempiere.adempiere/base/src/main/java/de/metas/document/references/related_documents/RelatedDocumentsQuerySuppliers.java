package de.metas.document.references.related_documents;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.model.MQuery;

@UtilityClass
public class RelatedDocumentsQuerySuppliers
{
	public static RelatedDocumentsQuerySupplier ofQuery(@NonNull final MQuery query)
	{
		final MQuery queryCopy = query.deepCopy();
		return queryCopy::deepCopy;
	}
}
