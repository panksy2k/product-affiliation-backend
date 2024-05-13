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
import java.util.List;
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

      setArray(SCREEN_SIZE, l -> l.toArray(String[]::new), sa -> productsEventPayloadBuilder.withScreenSize(sa), rootNode);
      setTextValue(REFRESH_RATE, s -> productsEventPayloadBuilder.withRefreshRate(s), rootNode);
      setTextValue(DISPLAY_RESOLUTION, s -> productsEventPayloadBuilder.withMaxDisplayResolution(s), rootNode);

      productsEventPayloadBuilder.withAmazonChoice(rootNode.findValue(AMAZON_CHOICE).asBoolean());

      setEnum(DISPLAY_TYPE,
        DisplayTypeEnum::forName,
        ea -> productsEventPayloadBuilder.withMaxDisplayType(ea),
        rootNode);

      setEnumArray(COLOR, ColorEnum::forName, ea -> productsEventPayloadBuilder.withColors(ea), rootNode);
      setEnumArray(PRODUCT_CONDITION,
        ConditionEnum::forName,
        ea -> productsEventPayloadBuilder.withProductCondition(ea),
        rootNode);
      setEnumArray(BRAND,
        BrandEnum::forName,
        ea -> productsEventPayloadBuilder.withBrand(ea),
        rootNode);
      setEnumArray(CONNECTIVITY_TECH,
        ConnectivityTechEnum::forName,
        ea -> productsEventPayloadBuilder.withConnectivityTech(ea),
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
    Double p = rootNode.findValue(fieldName).asDouble();
    if(p != null && !p.isNaN()) {
      priceStore.accept(p);
    }
  }

  private <E extends Enum<E>> void setEnumArray(String fieldName,
                                                Function<String, E> strToEnum,
                                                Consumer<E[]> valueStore,
                                                JsonNode rootNode) {

    List<String> fieldValueList = rootNode.findValuesAsText(fieldName);
    if(!Util.isEmpty(fieldValueList)) {
      E array[] = (E[]) fieldValueList.stream().map(strToEnum).toArray();
      valueStore.accept(array);
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

  private void setArray(String fieldName, Function<List<String>, String[]> func, Consumer<String[]> valueStore, JsonNode rootNode) {
    List<String> valueList = rootNode.findValuesAsText(fieldName);
    if(!Util.isEmpty(valueList)) {
        valueStore.accept(func.apply(valueList));
    }
  }

  private void setTextValue(String fieldName, Consumer<String> setTextValueFunc, JsonNode rootNode) {
    String jsonStringValue = rootNode.findValue(fieldName).asText();
    if(StringUtils.isNotBlank(jsonStringValue)) {
      setTextValueFunc.accept(jsonStringValue);
    }
  }
}
