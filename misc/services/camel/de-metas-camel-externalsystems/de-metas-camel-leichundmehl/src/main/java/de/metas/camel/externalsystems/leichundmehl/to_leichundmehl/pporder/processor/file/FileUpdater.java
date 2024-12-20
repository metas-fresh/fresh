/*
 * #%L
 * de-metas-camel-leichundmehl
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.pporder.processor.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.pporder.ExportPPOrderRouteContext;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.util.Replacement;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.util.XMLUtil;
import de.metas.common.externalsystem.leichundmehl.JsonExternalSystemLeichMehlPluFileConfig;
import de.metas.common.externalsystem.leichundmehl.JsonPluFileAudit;
import de.metas.common.externalsystem.leichundmehl.JsonProcessedKeys;
import de.metas.common.externalsystem.leichundmehl.JsonReplacementSource;
import de.metas.common.externalsystem.leichundmehl.JsonTargetFieldType;
import de.metas.common.util.Check;
import de.metas.common.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.Nullable;
import javax.xml.transform.TransformerException;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.ATTRIBUTE_TARGET_ELEMENT_DATA;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.ATTRIBUTE_TARGET_ELEMENT_FORMAT;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.ATTRIBUTE_TARGET_ELEMENT_TEMPLATE;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.ELEMENT_TAG_PRINT_OBJECT;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.ELEMENT_TAG_PRINT_OBJECTS;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.NODE_ATTRIBUTE_NAME;
import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.NODE_ATTRIBUTE_TYPE;

@Value
public class FileUpdater
{
	private final static Map<JsonTargetFieldType, String> attributeTypeToTargetFieldMap = initializeAttributeTypeToTargetField();

	@NonNull
	Document document;

	@NonNull
	ExportPPOrderRouteContext context;

	@NonNull
	String fileName;

	@NonNull
	ProcessLogger processLogger;

	@Builder
	public FileUpdater(
			@NonNull final String fileName,
			@NonNull final Document document,
			@NonNull final ExportPPOrderRouteContext context,
			@NonNull final ProcessLogger processLogger)
	{
		this.document = document;
		this.context = context;
		this.fileName = fileName;
		this.processLogger = processLogger;
	}

	@NonNull
	public JsonPluFileAudit updateDocument() throws TransformerException
	{
		final ImmutableList.Builder<String> missingKeys = ImmutableList.builder();
		final ImmutableList.Builder<JsonProcessedKeys> processedKeys = ImmutableList.builder();

		final NodeList printObjectsChildNodes = getPrintObjectList().orElse(null);

		if (printObjectsChildNodes == null)
		{
			this.processLogger.logMessage("No printObject found on in the plu file; filename=" + this.fileName, this.context.getAdPInstance());

			return JsonPluFileAudit.builder()
					.fileName(this.fileName)
					.missingKeys(this.context.getPluFileConfigKeys())
					.build();
		}

		for (final JsonExternalSystemLeichMehlPluFileConfig config : this.context.getPluFileConfigs().getPluFileConfigs())
		{
			final Element element = findElementToUpdate(config, printObjectsChildNodes).orElse(null);

			if (element == null)
			{
				missingKeys.add(config.getTargetFieldName());
			}
			else
			{
				processedKeys.add(updateElement(element, config));
			}
		}

		return JsonPluFileAudit.builder()
				.fileName(this.fileName)
				.missingKeys(missingKeys.build())
				.processedKeys(processedKeys.build())
				.build();
	}

	@Nullable
	private String resolveValueToUpdate(@NonNull final JsonExternalSystemLeichMehlPluFileConfig config)
	{
		final JsonNode replacementSourceTree = getReplacementSourceTreeOrNull(config.getReplacementSource());
		if (replacementSourceTree == null)
		{
			return null;
		}

		final Replacement replacement = Replacement.of(config.getReplacement());

		final JsonNode targetNode = replacementSourceTree.at(replacement.getJsonPath());

		if (targetNode == null || !targetNode.isValueNode())
		{
			if (replacementSourceTree.isArray() && replacementSourceTree.size() == 1)
			{

				targetNode = replacementSourceTree.get(0).at(replacement.getJsonPath());
			}
			else
			{
				return null;
			}
		}

		return targetNode.textValue();
	}

	@Nullable
	private JsonNode getReplacementSourceTreeOrNull(@NonNull final JsonReplacementSource jsonReplacementSource)
	{
		switch (jsonReplacementSource)
		{
			case Product ->
			{
				return JsonObjectMapperHolder.sharedJsonObjectMapper().convertValue(this.context.getProductInfoNonNull(), JsonNode.class);
			}
			case PPOrder ->
			{
				return JsonObjectMapperHolder.sharedJsonObjectMapper().convertValue(this.context.getManufacturingOrderNonNull(), JsonNode.class);
			}
			case CustomProcessResult ->
			{
				return extractCustomQueryResonseAsJSON();
			}
			default -> throw new RuntimeException("Unhandled type=" + jsonReplacementSource.getCode());
		}
	}

	@Nullable
	private JsonNode extractCustomQueryResonseAsJSON()
	{
		if (Check.isBlank(this.context.getCustomQueryProcessResponse()))
		{
			return null;
		}
		try
		{
			return JsonObjectMapperHolder.sharedJsonObjectMapper().readTree(this.context.getCustomQueryProcessResponse());
		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeException("Unable to read JSON-response from Custom Query AD_Process invocation", e);
		}
	}

	@NonNull
	private Optional<NodeList> getPrintObjectList()
	{
		final Element printObjectsElement = XMLUtil.getElementByTag(this.document, ELEMENT_TAG_PRINT_OBJECTS);
		if (printObjectsElement == null)
		{
			return Optional.empty();
		}

		return Optional.of(printObjectsElement.getChildNodes());
	}

	@NonNull
	private Optional<Element> findElementToUpdate(
			@NonNull final JsonExternalSystemLeichMehlPluFileConfig config,
			@NonNull final NodeList printObjectsChildNodes)
	{
		final String targetTagToUpdate = attributeTypeToTargetFieldMap.get(config.getTargetFieldType());

		if (targetTagToUpdate == null)
		{
			processLogger.logMessage("No tag is configured for TargetFieldType=" + targetTagToUpdate, this.context.getAdPInstance());
			return Optional.empty();
		}

		return getMatchingPrintObject(config, printObjectsChildNodes)
				.map(printObject -> XMLUtil.getElementByTag(printObject, targetTagToUpdate));
	}

	@NonNull
	private Optional<Element> getMatchingPrintObject(
			@NonNull final JsonExternalSystemLeichMehlPluFileConfig config,
			@NonNull final NodeList printObjectsChildNodes)
	{
		for (int index = 0; index < printObjectsChildNodes.getLength(); index++)
		{
			final Node currentNode = printObjectsChildNodes.item(index);

			if (currentNode.getNodeType() != Node.ELEMENT_NODE && !currentNode.getNodeName().equals(ELEMENT_TAG_PRINT_OBJECT))
			{
				continue;
			}

			final Element printObjectElement = (Element)currentNode;

			final boolean isMatchingNodeByNameAndType = XMLUtil.hasAttribute(printObjectElement, NODE_ATTRIBUTE_NAME, config.getTargetFieldName())
					&& XMLUtil.hasAttribute(printObjectElement, NODE_ATTRIBUTE_TYPE, config.getTargetFieldType().getCode());

			if (isMatchingNodeByNameAndType)
			{
				return Optional.of(printObjectElement);
			}
		}

		return Optional.empty();
	}

	@NonNull
	private JsonProcessedKeys updateElement(
			@NonNull final Element elementToUpdate,
			@NonNull final JsonExternalSystemLeichMehlPluFileConfig config)
	{
		final String replacementValue = StringUtils.nullToEmpty(resolveValueToUpdate(config));

		final String oldElementValue = StringUtils.nullToEmpty(elementToUpdate.getTextContent());

		final String newElementValue = computeNewValue(config.getReplacePattern(), replacementValue, oldElementValue);

		elementToUpdate.setTextContent(newElementValue);

		return JsonProcessedKeys.builder()
				.key(config.getTargetFieldName())
				.oldValue(oldElementValue)
				.newValue(newElementValue)
				.build();
	}

	@NonNull
	private String computeNewValue(
			@NonNull final String replacementPattern,
			@NonNull final String replacementValue,
			@NonNull final String oldElementValue)
	{
		final Pattern pattern = Pattern.compile(replacementPattern);
		final Matcher matcher = pattern.matcher(oldElementValue);

		if (matcher.matches())
		{
			final int matchingGroupIndex = matcher.groupCount() > 0 ? 1 : 0;

			final String matchedSequence = matcher.group(matchingGroupIndex);

			return oldElementValue.replaceAll(matchedSequence, replacementValue);
		}

		return oldElementValue;
	}

	@NonNull
	private static ImmutableMap<JsonTargetFieldType, String> initializeAttributeTypeToTargetField()
	{
		final ImmutableMap.Builder<JsonTargetFieldType, String> attributeTypeToTargetFieldMap = ImmutableMap.builder();

		attributeTypeToTargetFieldMap.put(JsonTargetFieldType.TextArea, ATTRIBUTE_TARGET_ELEMENT_DATA);
		attributeTypeToTargetFieldMap.put(JsonTargetFieldType.EAN13, ATTRIBUTE_TARGET_ELEMENT_DATA);
		attributeTypeToTargetFieldMap.put(JsonTargetFieldType.EAN128, ATTRIBUTE_TARGET_ELEMENT_DATA);
		attributeTypeToTargetFieldMap.put(JsonTargetFieldType.NumberField, ATTRIBUTE_TARGET_ELEMENT_FORMAT);
		attributeTypeToTargetFieldMap.put(JsonTargetFieldType.Date, ATTRIBUTE_TARGET_ELEMENT_TEMPLATE);
		attributeTypeToTargetFieldMap.put(JsonTargetFieldType.UnitChar, ATTRIBUTE_TARGET_ELEMENT_DATA);
		attributeTypeToTargetFieldMap.put(JsonTargetFieldType.Graphic, ATTRIBUTE_TARGET_ELEMENT_DATA);

		return attributeTypeToTargetFieldMap.build();
	}
}
