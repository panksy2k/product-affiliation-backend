package com.product.affiliation.backend.services;

import com.product.affiliation.backend.messaging.event.BrandEnum;
import com.product.affiliation.backend.messaging.event.ColorEnum;
import com.product.affiliation.backend.messaging.event.ConditionEnum;
import com.product.affiliation.backend.messaging.event.ConnectivityTechEnum;
import com.product.affiliation.backend.messaging.event.DisplayTypeEnum;
import com.product.affiliation.backend.messaging.event.GetProductsEventPayload;
import com.product.affiliation.backend.messaging.event.ProductResponseEventPayload;
import com.product.affiliation.backend.messaging.event.ProductTypeEnum;
import com.product.affiliation.backend.messaging.event.SpecialFeatureEnum;
import com.product.affiliation.backend.messaging.receiver.EventReceiver;
import com.product.affiliation.backend.repositories.ProductRepository;
import io.vertx.core.Future;
import io.vertx.junit5.Checkpoint;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import java.util.Arrays;
import java.util.Set;
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
  public void testFindProducts(VertxTestContext context) {
    //Given
    Mockito.when(mockedRepository.findProducts(Mockito.any(GetProductsEventPayload.class)))
      .thenReturn(Future.succeededFuture(Arrays.asList(getMockedProduct())));

    Checkpoint findCheckPoint = context.checkpoint();

    //When
    context.verify(() -> {
      SUT.findProducts(GetProductsEventPayload.builder().withAmazonChoice(true).build())
        .onSuccess(prdList -> {
          Assertions.assertFalse(prdList.isEmpty());
          Assertions.assertEquals(1, prdList.size());

          findCheckPoint.flag();

          context.completeNow();
        })
        .onFailure(context::failNow);
    });
  }

  private ProductResponseEventPayload getMockedProduct() {
    return new ProductResponseEventPayload(0L,
      "Dell SE2222H 21.5 Inch Full HD (1920x1080) Monitor",
        "https://www.amazon.co.uk/dp/B095749V5V/ref=sspa_dk_detail_5?pd_rd_i=B095749V5V&pd_rd_w=912fo&content-id"
          + "=amzn1.sym.84ea1bf1-65a8-4363-b8f5-f0df58cbb686&pf_rd_p=84ea1bf1-65a8-4363-b8f5-f0df58cbb686&pf_rd_r"
          + "=MBM0N7W2E8CDFQXHB343&pd_rd_wg=aV22e&pd_rd_r=1c384c09-a39a-4922-b127-6793c694e633&s=computers&sp_csd"
          + "=d2lkZ2V0TmFtZT1zcF9kZXRhaWw&th=1",
      ConditionEnum.NEW,
      69.99D,
      "3 Years",
      "27 Inch",
      "60Hz",
      "1920 x 1080",
      DisplayTypeEnum.HD,
      "4.9D x 50.4W x 29.5H centimetres",
      Set.of(SpecialFeatureEnum.FlickerFree, SpecialFeatureEnum.CurvedScreen),
      BrandEnum.DELL,
      null,
      ColorEnum.BLACK,
      true,
      Set.of(ConnectivityTechEnum.HDMI),
      ProductTypeEnum.MONITOR,
      Short.valueOf("2"),
      "16:9");
  }

  @Test
  @DisplayName("New Product Creation")
  public void createProduct(VertxTestContext context) throws Exception {
    //Given
    Mockito.when(mockedRepository.saveProduct(Mockito.any(ProductResponseEventPayload.class)))
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
