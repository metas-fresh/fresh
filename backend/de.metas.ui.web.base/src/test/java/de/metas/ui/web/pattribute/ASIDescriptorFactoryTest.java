package de.metas.ui.web.pattribute;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CCacheStats;
import de.metas.handlingunits.attribute.storage.impl.AIWithHUPIAttributeValue;
import de.metas.handlingunits.attribute.storage.impl.NullAttributeStorage;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.ui.web.pattribute.ASIDescriptorFactory.ASIAttributeFieldBinding;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.model.IDocumentFieldView;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.spi.IAttributeValueHandler;
import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;
import org.adempiere.mm.attributes.spi.IAttributeValuesProviderFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.compiere.util.NamePair;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AdempiereTestWatcher.class)
class ASIDescriptorFactoryTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	private I_M_Attribute createAttribute(
			@NonNull String name,
			@NonNull AttributeValueType type,
			@Nullable Class<? extends IAttributeValuesProvider> attributeValuesProviderClass)
	{
		final I_M_Attribute attribute = InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
		attribute.setValue(name);
		attribute.setName(name);
		attribute.setAttributeValueType(type.getCode());
		attribute.setAD_JavaClass_ID(attributeValuesProviderClass != null ? createAD_JavaClass_ID(attributeValuesProviderClass) : -1);
		InterfaceWrapperHelper.save(attribute);

		return attribute;
	}

	private static int createAD_JavaClass_ID(final @NonNull Class<? extends IAttributeValuesProvider> attributeValuesProviderClass)
	{
		final I_AD_JavaClass javaClassDef = newInstanceOutOfTrx(I_AD_JavaClass.class);
		javaClassDef.setName(attributeValuesProviderClass.getName());
		javaClassDef.setClassname(attributeValuesProviderClass.getName());
		InterfaceWrapperHelper.save(javaClassDef);
		return javaClassDef.getAD_JavaClass_ID();
	}

	private static I_M_AttributeSetInstance createASI()
	{
		final I_M_AttributeSetInstance asi = InterfaceWrapperHelper.newInstance(I_M_AttributeSetInstance.class);
		InterfaceWrapperHelper.save(asi);
		return asi;
	}

	private AIWithHUPIAttributeValue newAIWithHUPIAttributeValue(final I_M_AttributeInstance ai)
	{
		final I_M_HU_PI_Attribute piAttribute = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Attribute.class);
		piAttribute.setM_Attribute_ID(ai.getM_Attribute_ID());
		return new AIWithHUPIAttributeValue(NullAttributeStorage.instance, ai, piAttribute, false);
	}

	private static IDocumentFieldView mockASIField(final DocumentFieldDescriptor fieldDescriptor, final Object value)
	{
		final IDocumentFieldView field = Mockito.mock(IDocumentFieldView.class);
		Mockito.doReturn(fieldDescriptor).when(field).getDescriptor();
		Mockito.doReturn(value).when(field).getValueAs(value.getClass());
		return field;
	}

	@Nested
	class Assert_WebuiASIField_and_AIWithHUPIAttributeValue_setGetValue_are_consistent
	{
		@Test
		void string()
		{
			final I_M_Attribute attribute = createAttribute("StringAttr", AttributeValueType.STRING, null);
			final DocumentFieldDescriptor fieldDescriptor = new ASIDescriptorFactory().createDocumentFieldDescriptor(attribute).getOrBuild();

			final ASIAttributeFieldBinding asiBinding = fieldDescriptor.getDataBindingNotNull(ASIAttributeFieldBinding.class);
			final I_M_AttributeInstance ai = asiBinding.createAndSaveM_AttributeInstance(
					createASI(),
					mockASIField(fieldDescriptor, "string value"));
			assertThat(asiBinding.readValue(ai)).isEqualTo("string value");

			//
			// Make sure it's consistent with HU attributes BLs
			final AIWithHUPIAttributeValue aiWithHUPIAttributeValue = newAIWithHUPIAttributeValue(ai);
			assertThat(aiWithHUPIAttributeValue.getValue()).isEqualTo("string value");
		}

		@Test
		void number()
		{
			final I_M_Attribute attribute = createAttribute("NumberAttr", AttributeValueType.NUMBER, null);
			final DocumentFieldDescriptor fieldDescriptor = new ASIDescriptorFactory().createDocumentFieldDescriptor(attribute).getOrBuild();

			final ASIAttributeFieldBinding asiBinding = fieldDescriptor.getDataBindingNotNull(ASIAttributeFieldBinding.class);
			final I_M_AttributeInstance ai = asiBinding.createAndSaveM_AttributeInstance(
					createASI(),
					mockASIField(fieldDescriptor, new BigDecimal("12345")));
			assertThat(asiBinding.readValue(ai)).isEqualTo(new BigDecimal("12345"));

			//
			// Make sure it's consistent with HU attributes BLs
			final AIWithHUPIAttributeValue aiWithHUPIAttributeValue = newAIWithHUPIAttributeValue(ai);
			assertThat(aiWithHUPIAttributeValue.getValue()).isEqualTo(new BigDecimal("12345"));
		}

		@Test
		void date()
		{
			final I_M_Attribute attribute = createAttribute("DateAttr", AttributeValueType.DATE, null);
			final DocumentFieldDescriptor fieldDescriptor = new ASIDescriptorFactory().createDocumentFieldDescriptor(attribute).getOrBuild();

			final ASIAttributeFieldBinding asiBinding = fieldDescriptor.getDataBindingNotNull(ASIAttributeFieldBinding.class);
			final I_M_AttributeInstance ai = asiBinding.createAndSaveM_AttributeInstance(
					createASI(),
					mockASIField(fieldDescriptor, LocalDate.parse("2022-05-06")));
			assertThat(asiBinding.readValue(ai)).isEqualTo(LocalDate.parse("2022-05-06"));

			//
			// Make sure it's consistent with HU attributes BLs
			final AIWithHUPIAttributeValue aiWithHUPIAttributeValue = newAIWithHUPIAttributeValue(ai);
			assertThat(aiWithHUPIAttributeValue.getValue()).isEqualTo(TimeUtil.asTimestamp(LocalDate.parse("2022-05-06")));
		}

		@Test
		void list_With_String_key()
		{
			final I_M_Attribute attribute = createAttribute("StringAttr", AttributeValueType.LIST, StringProvider.class);
			final DocumentFieldDescriptor fieldDescriptor = new ASIDescriptorFactory().createDocumentFieldDescriptor(attribute).getOrBuild();

			final ASIAttributeFieldBinding asiBinding = fieldDescriptor.getDataBindingNotNull(ASIAttributeFieldBinding.class);
			final I_M_AttributeInstance ai = asiBinding.createAndSaveM_AttributeInstance(
					createASI(),
					mockASIField(fieldDescriptor, StringLookupValue.of("stringKey", "string item")));
			assertThat(asiBinding.readValue(ai)).isEqualTo("stringKey");

			//
			// Make sure it's consistent with HU attributes BLs
			final AIWithHUPIAttributeValue aiWithHUPIAttributeValue = newAIWithHUPIAttributeValue(ai);
			assertThat(aiWithHUPIAttributeValue.getValue()).isEqualTo("stringKey");
		}

		@Test
		void list_With_Numeric_key()
		{
			final I_M_Attribute attribute = createAttribute("NumericAttr", AttributeValueType.LIST, NumberProvider.class);
			final DocumentFieldDescriptor fieldDescriptor = new ASIDescriptorFactory().createDocumentFieldDescriptor(attribute).getOrBuild();

			final ASIAttributeFieldBinding asiBinding = fieldDescriptor.getDataBindingNotNull(ASIAttributeFieldBinding.class);
			final I_M_AttributeInstance ai = asiBinding.createAndSaveM_AttributeInstance(
					createASI(),
					mockASIField(fieldDescriptor, IntegerLookupValue.of(1234, "numeric item")));
			assertThat(asiBinding.readValue(ai)).isEqualTo(new BigDecimal("1234"));

			//
			// Make sure it's consistent with HU attributes BLs
			final AIWithHUPIAttributeValue aiWithHUPIAttributeValue = newAIWithHUPIAttributeValue(ai);
			assertThat(aiWithHUPIAttributeValue.getValue()).isEqualTo(new BigDecimal("1234"));
		}
	}

	public static class StringProvider extends MockedAttributeValueTypeProvider
	{
		public StringProvider()
		{
			super(AttributeValueType.STRING);
		}
	}

	public static class NumberProvider extends MockedAttributeValueTypeProvider
	{
		public NumberProvider()
		{
			super(AttributeValueType.NUMBER);
		}
	}

	private static abstract class MockedAttributeValueTypeProvider implements IAttributeValueHandler, IAttributeValuesProvider, IAttributeValuesProviderFactory
	{
		private final AttributeValueType attributeValueType;

		MockedAttributeValueTypeProvider(@NonNull final AttributeValueType attributeValueType) {this.attributeValueType = attributeValueType;}

		@Override
		public IAttributeValuesProvider createAttributeValuesProvider(final I_M_Attribute attributeRecord)
		{
			return this;
		}

		@Override
		public String getAttributeValueType()
		{
			return attributeValueType.getCode();
		}

		@Override
		public boolean isAllowAnyValue()
		{
			return false;
		}

		@Override
		public Evaluatee prepareContext(final IAttributeSet attributeSet)
		{
			return Evaluatees.empty();
		}

		@Override
		public List<? extends NamePair> getAvailableValues(final Evaluatee evalCtx)
		{
			return ImmutableList.of();
		}

		@Override
		public NamePair getAttributeValueOrNull(final Evaluatee evalCtx, final Object valueKey)
		{
			return null;
		}

		@Override
		public AttributeValueId getAttributeValueIdOrNull(final Object valueKey)
		{
			return null;
		}

		@Override
		public NamePair getNullValue()
		{
			return null;
		}

		@Override
		public boolean isHighVolume()
		{
			return false;
		}

		@Override
		public String getCachePrefix()
		{
			return getClass().getSimpleName();
		}

		@Override
		public List<CCacheStats> getCacheStats()
		{
			return ImmutableList.of();
		}
	}

}