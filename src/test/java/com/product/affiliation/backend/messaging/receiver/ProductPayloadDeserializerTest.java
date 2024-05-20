package com.product.affiliation.backend.messaging.receiver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.product.affiliation.backend.messaging.event.BrandEnum;
import com.product.affiliation.backend.messaging.event.ColorEnum;
import com.product.affiliation.backend.messaging.event.ConditionEnum;
import com.product.affiliation.backend.messaging.event.ConnectivityTechEnum;
import com.product.affiliation.backend.messaging.event.DisplayTypeEnum;
import com.product.affiliation.backend.messaging.event.GetProductsEventPayload;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductPayloadDeserializerTest {

  private byte[] loadedFile;
  private static final String SAMPLE_JSON_FILE = "product.json";


  @BeforeEach
  public void init()  {
    try {
      Path path = Paths.get(getClass().getClassLoader().getResource(SAMPLE_JSON_FILE).toURI());
      loadedFile = Files.readAllBytes(path);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testDeserializeCorrectly() {
    //Given
    ProductPayloadDeserializer deserializer = new ProductPayloadDeserializer();

    //When
    ProductPayloadOrError actualResponse = deserializer.deserialize(null, loadedFile);
    GetProductsEventPayload actualPayload = (GetProductsEventPayload) actualResponse.getPayload();

    //Then
    assertEquals("GET_PRODUCT", actualPayload.getType());
    assertTrue(actualPayload.isAmazonChoice());
    assertEquals(DisplayTypeEnum.LED, actualPayload.getDisplayType());

    assertTrue(actualPayload.getScreenSize().contains("35"));
    assertTrue(actualPayload.getScreenSize().contains("32"));

    assertTrue(actualPayload.getColor().contains(ColorEnum.BLACK));
    assertTrue(actualPayload.getColor().contains(ColorEnum.WHITE));

    assertEquals("75", actualPayload.getRefreshRate());
    assertEquals("2950 x 1440", actualPayload.getMaxDisplayResolution());

    assertTrue(actualPayload.getConnectivityTech().contains(ConnectivityTechEnum.USBC));
    assertTrue(actualPayload.getConnectivityTech().contains(ConnectivityTechEnum.HDMI));

    assertTrue(actualPayload.getBrand().contains(BrandEnum.BENQ));
    assertTrue(actualPayload.getBrand().contains(BrandEnum.SAMSUNG));

    assertTrue(actualPayload.getProductCondition().contains(ConditionEnum.NEW));

    assertEquals(1.009D, actualPayload.getPriceFrom());
    assertEquals(1.009D, actualPayload.getPriceTo());
  }
}
