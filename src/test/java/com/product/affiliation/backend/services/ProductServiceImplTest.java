package com.product.affiliation.backend.services;

import static org.junit.jupiter.api.Assertions.*;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class ProductServiceImplTest {

  private ProductServiceImpl productService;



  @BeforeEach
  public void init() {
    //productService = new ProductServiceImpl();
  }

  @Test
  public void testFindProducts() {
    JsonObject payload = new JsonObject(jsonPayloadAsString());
    productService.findProducts(payload);
  }

  private String jsonPayloadAsString() {
    return "{\n"
      + "  \"amazonChoiceYesOrNo\": {\n"
      + "    \"eq\": \"true\"\n"
      + "  },\n"
      + "  \"displayType\": {\n"
      + "    \"eq\": \"LED\"\n"
      + "  },\n"
      + "  \"screenSize\": {\n"
      + "    \"in\": [\n"
      + "      \"35\",\n"
      + "      \"32\"\n"
      + "    ]\n"
      + "  },\n"
      + "  \"color\": {\n"
      + "    \"in\": [\n"
      + "      \"White\",\n"
      + "      \"Black\"\n"
      + "    ]\n"
      + "  },\n"
      + "  \"refreshRate\": {\n"
      + "    \"eq\": \"75\"\n"
      + "  },\n"
      + "  \"displayResolution\": {\n"
      + "    \"eq\": \"2950 x 1440\"\n"
      + "  },\n"
      + "  \"connectivityTech\": {\n"
      + "    \"in\": [\n"
      + "      \"USBC\",\n"
      + "      \"HDMI\"\n"
      + "    ]\n"
      + "  },\n"
      + "  \"brand\": {\n"
      + "    \"in\": [\n"
      + "      \"BENQ\",\n"
      + "      \"Samsung\"\n"
      + "    ]\n"
      + "  }\n"
      + "}";
  }
}
