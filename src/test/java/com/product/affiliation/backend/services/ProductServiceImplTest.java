package com.product.affiliation.backend.services;

import com.product.affiliation.backend.messaging.event.GetProductPayload;
import com.product.affiliation.backend.messaging.receiver.EventReceiver;
import com.product.affiliation.backend.repositories.ProductRepository;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.Checkpoint;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import java.net.URL;
import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(VertxExtension.class)
@ExtendWith(MockitoExtension.class)

public class ProductServiceImplTest {

  private ProductServiceImpl SUT;

  @Mock
  private ProductRepository mockedRepository;

  @Mock
  private EventReceiver mockedEventRepository;

  @BeforeEach
  public void init() {
    SUT = new ProductServiceImpl(mockedRepository, mockedEventRepository);
  }

  @Test
  @DisplayName("Find Existing Products")
  public void testFindProducts(VertxTestContext context) throws Exception {
    //Given

    Mockito.when(mockedRepository.findProducts(Mockito.anyList()))
      .thenReturn(Future.succeededFuture(Arrays.asList(getMockedProduct())));

    Checkpoint findCheckPoint = context.checkpoint();

    //When
    JsonObject payload = new JsonObject(jsonPayloadAsString());

    context.verify(() -> {
      SUT.findProducts(payload)
        .onSuccess(prdList -> {
          Assertions.assertFalse(prdList.isEmpty());
          Assertions.assertEquals(1, prdList.size());

          findCheckPoint.flag();

          context.completeNow();
        })
        .onFailure(context::failNow);
    });
  }

  private GetProductPayload getMockedProduct() throws Exception {
    return new GetProductPayload(0L,
      "Dell SE2222H 21.5 Inch Full HD (1920x1080) Monitor",
      new URL(
        "https://www.amazon.co.uk/dp/B095749V5V/ref=sspa_dk_detail_5?pd_rd_i=B095749V5V&pd_rd_w=912fo&content-id"
          + "=amzn1.sym.84ea1bf1-65a8-4363-b8f5-f0df58cbb686&pf_rd_p=84ea1bf1-65a8-4363-b8f5-f0df58cbb686&pf_rd_r"
          + "=MBM0N7W2E8CDFQXHB343&pd_rd_wg=aV22e&pd_rd_r=1c384c09-a39a-4922-b127-6793c694e633&s=computers&sp_csd"
          + "=d2lkZ2V0TmFtZT1zcF9kZXRhaWw&th=1"),
      "NEW",
      69.99,
      "3 Years",
      "27 Inch",
      "60Hz",
      "1920 x 1080",
      "HD",
      "4.9D x 50.4W x 29.5H centimetres",
      Stream.of("Anti Glare Screen", "Flicker Free").toArray(String[]::new),
      "Dell",
      "Black",
      true,
      Stream.of("HDMI").toArray(String[]::new),
      "Amazon",
      "SE2222H",
      Short.valueOf("2"),
      "16:9");
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

  @Test
  @DisplayName("New Product Creation")
  public void createProduct(VertxTestContext context) throws Exception {
    //Given
    Mockito.when(mockedRepository.saveProduct(Mockito.any(GetProductPayload.class)))
      .thenReturn(Future.succeededFuture(getMockedProduct()));

    Checkpoint createChkPoint = context.checkpoint();

    context.verify(() -> {
      SUT.createProduct(getMockedProduct())
        .onFailure(context::failNow)
        .onSuccess(p -> {
          long createProductId = p.getId();
          Assert.assertEquals(0L, createProductId);
          createChkPoint.flag();
          context.completeNow();
        });
    });
  }

  @Test
  @DisplayName("Remove Product")
  public void removeProduct(VertxTestContext context) {
    Mockito.when(mockedRepository.removeProduct(2L))
      .thenReturn(Future.succeededFuture(Boolean.TRUE));

    context.verify(() -> {
      SUT.removeProduct(2L).onFailure(context::failNow).onSuccess(result -> {
          Assertions.assertTrue(result);
          context.completeNow();
      });
    });
  }
}
