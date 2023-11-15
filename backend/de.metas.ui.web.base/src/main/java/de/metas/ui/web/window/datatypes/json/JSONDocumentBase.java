package de.metas.ui.web.window.datatypes.json;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.exceptions.InvalidDocumentPathException;
import lombok.Getter;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

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

/**
 * Base class for all "Document"-ish JSONs
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
// @JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE) // cannot use it because of "otherProperties"
public abstract class JSONDocumentBase
{
	@JsonProperty("windowId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final WindowId windowId;

	@JsonProperty("id")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final DocumentId id;

	@JsonProperty("tabId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String tabId;
	//
	@JsonProperty("tabid")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Deprecated
	private final String tabid;

	@JsonProperty("rowId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final DocumentId rowId;

	@JsonProperty("deleted")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean deleted;

	@JsonProperty("fieldsByName")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Getter
	private Map<String, JSONDocumentField> fieldsByName;

	/** Any other properties */
	private final Map<String, Object> otherProperties = new LinkedHashMap<>();

	@JsonIgnore
	private boolean unboxPasswordFields = false;

	protected JSONDocumentBase(final DocumentPath documentPath)
	{
		if (documentPath == null)
		{
			windowId = null;
			id = null;
			tabId = null;
			rowId = null;
		}
		else if (documentPath.isRootDocument())
		{
			windowId = documentPath.getWindowIdOrNull();
			id = documentPath.getDocumentId();
			tabId = null;
			rowId = null;
		}
		else if (documentPath.isSingleIncludedDocument())
		{
			windowId = documentPath.getWindowIdOrNull();
			id = documentPath.getDocumentId();
			tabId = documentPath.getDetailId().toJson();
			rowId = documentPath.getSingleRowId();
		}
		else
		{
			// shall not happen
			throw new InvalidDocumentPathException(documentPath, "only root path and single included document path are allowed");
		}

		tabid = tabId;
	}

	protected JSONDocumentBase(final DocumentId documentId)
	{
		windowId = null;
		id = documentId;
		tabId = null;
		tabid = tabId;
		rowId = null;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("windowId", windowId)
				.add("id", id)
				.add("tablId", tabId)
				.add("rowId", rowId)
				.toString();
	}

	@JsonIgnore
	protected final void setUnboxPasswordFields()
	{
		this.unboxPasswordFields = true;
	}

	@JsonIgnore
	public WindowId getWindowId()
	{
		return windowId;
	}

	@JsonIgnore
	public DocumentId getId()
	{
		return id;
	}

	@JsonIgnore
	public String getTabId()
	{
		return tabId;
	}

	@JsonIgnore
	public DocumentId getRowId()
	{
		return rowId;
	}

	public final void setDeleted()
	{
		deleted = Boolean.TRUE;
	}

	@JsonAnyGetter
	private Map<String, Object> getOtherProperties()
	{
		return otherProperties;
	}

	@JsonAnySetter
	private void putOtherProperty(final String name, final Object jsonValue)
	{
		otherProperties.put(name, jsonValue);
	}

	protected final JSONDocumentBase putDebugProperty(final String name, final Object jsonValue)
	{
		otherProperties.put("debug-" + name, jsonValue);
		return this;
	}

	public final void setFields(final Collection<JSONDocumentField> fields)
	{
		setFields(fields == null ? null : Maps.uniqueIndex(fields, JSONDocumentField::getField));
	}

	@JsonIgnore
	protected final void setFields(final Map<String, JSONDocumentField> fieldsByName)
	{
		this.fieldsByName = fieldsByName;

		if (unboxPasswordFields && fieldsByName != null && !fieldsByName.isEmpty())
		{
			fieldsByName.forEach((fieldName, field) -> field.unboxPasswordField());
		}
	}

	@JsonIgnore
	protected final int getFieldsCount()
	{
		return fieldsByName == null ? 0 : fieldsByName.size();
	}
}
