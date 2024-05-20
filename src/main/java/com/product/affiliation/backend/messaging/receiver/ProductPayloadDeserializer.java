package com.product.affiliation.backend.messaging.receiver;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.affiliation.backend.messaging.event.BrandEnum;
import com.product.affiliation.backend.messaging.event.ColorEnum;
import com.product.affiliation.backend.messaging.event.ConditionEnum;
import com.product.affiliation.backend.messaging.event.ConnectivityTechEnum;
import com.product.affiliation.backend.messaging.event.DisplayTypeEnum;
import com.product.affiliation.backend.messaging.event.GetProductsEventPayload;
import com.product.affiliation.backend.util.Util;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.common.serialization.Deserializer;

public class ProductPayloadDeserializer implements Deserializer<ProductPayloadOrError> {

  private static final String PRODUCT_CONDITION = "productCondition";
  private static final String SCREEN_SIZE = "screenSize";
  private static final String REFRESH_RATE = "refreshRate";
  private static final String DISPLAY_RESOLUTION = "displayResolution";
  private static final String DISPLAY_TYPE = "displayType";
  private static final String BRAND = "brand";
  private static final String COLOR = "color";
  private static final String AMAZON_CHOICE = "amazonChoiceYesOrNo";
  private static final String CONNECTIVITY_TECH = "connectivityTech";
  private static final String PRICE_FROM = "priceFrom";
  private static final String PRICE_TO = "priceTo";

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public ProductPayloadOrError deserialize(String topic, byte[] data) {
    final var value = new String(data);
    final GetProductsEventPayload.GetProductsEventPayloadBuilder productsEventPayloadBuilder = GetProductsEventPayload.builder();

    try {
      JsonNode rootNode = objectMapper.readTree(data);

      setArray(SCREEN_SIZE, productsEventPayloadBuilder::withScreenSize, rootNode);
      setTextValue(REFRESH_RATE, productsEventPayloadBuilder::withRefreshRate, rootNode);
      setTextValue(DISPLAY_RESOLUTION, productsEventPayloadBuilder::withMaxDisplayResolution, rootNode);

      productsEventPayloadBuilder.withAmazonChoice(rootNode.findValue(AMAZON_CHOICE).asBoolean());

      setEnum(DISPLAY_TYPE,
        DisplayTypeEnum::forName,
        productsEventPayloadBuilder::withMaxDisplayType,
        rootNode);

      setEnumArray(COLOR, ColorEnum::forName, productsEventPayloadBuilder::withColors, rootNode);
      setEnumArray(PRODUCT_CONDITION,
        ConditionEnum::forName,
        productsEventPayloadBuilder::withProductCondition,
        rootNode);
      setEnumArray(BRAND,
        BrandEnum::forName,
        productsEventPayloadBuilder::withBrand,
        rootNode);
      setEnumArray(CONNECTIVITY_TECH,
        ConnectivityTechEnum::forName,
        productsEventPayloadBuilder::withConnectivityTech,
        rootNode);

      setDouble(PRICE_FROM, productsEventPayloadBuilder :: withPriceFrom, rootNode);
      setDouble(PRICE_TO, productsEventPayloadBuilder :: withPriceTo, rootNode);

      return new ProductPayloadOrError(productsEventPayloadBuilder.build(), null, value);
    }
    catch(Exception e) {
      return new ProductPayloadOrError(null, e, value);
    }
  }

  private void setDouble(String fieldName, Consumer<Double> priceStore, JsonNode rootNode) {

    if(rootNode.findValue(fieldName) != null && rootNode.findValue(fieldName).isDouble()) {
      Double p = rootNode.findValue(fieldName).asDouble();
      if(p != null && !p.isNaN()) {
        priceStore.accept(p);
      }
    }
  }

  private <E extends Enum<E>> void setEnumArray(String fieldName,
                                                Function<String, E> strToEnum,
                                                Consumer<Set<E>> valueStore,
                                                JsonNode rootNode) {

    JsonNode enumArrayNode = rootNode.findValue(fieldName);

    if(!Objects.isNull(enumArrayNode) && enumArrayNode.isArray() && enumArrayNode.size() > 0) {
      Set<E> collectionOfEnum = new LinkedHashSet<>();

      for(int i = 0; i < enumArrayNode.size(); i++) {
        String enumStrValue = enumArrayNode.get(i).asText();
        collectionOfEnum.add(strToEnum.apply(enumStrValue));
      }

      valueStore.accept(collectionOfEnum);
    }
  }

  private <E extends Enum<E>> void setEnum(String fieldName,
                                                Function<String, E> strToEnum,
                                                Consumer<E> valueStore,
                                                JsonNode rootNode) {

    String fieldValue = rootNode.findValue(fieldName).asText();
    if(StringUtils.isNotBlank(fieldValue)) {
      E array = strToEnum.apply(fieldValue);
      valueStore.accept(array);
    }
  }

  private void setArray(String fieldName, Consumer<String[]> valueStore, JsonNode rootNode) {
    JsonNode arrayNode = rootNode.findValue(fieldName);

    if(!Objects.isNull(arrayNode) && arrayNode.isArray() && arrayNode.size() > 0) {
      String[] arrValue = new String[arrayNode.size()];

      for(int i = 0; i < arrayNode.size(); i++) {
        arrValue[i] = arrayNode.get(i).asText();
      }

      valueStore.accept(arrValue);
    }
  }

  private void setTextValue(String fieldName, Consumer<String> setTextValueFunc, JsonNode rootNode) {
    String jsonStringValue = rootNode.findValue(fieldName).asText();
    if(StringUtils.isNotBlank(jsonStringValue)) {
      setTextValueFunc.accept(jsonStringValue);
    }
  }
}
