package de.metas.document.references.related_documents;

import org.compiere.model.MQuery;

@FunctionalInterface
public interface RelatedDocumentsQuerySupplier
{
	MQuery getQuery();
}
