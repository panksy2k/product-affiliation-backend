package com.product.affiliation.backend.repositories;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.product.affiliation.backend.messaging.event.BrandEnum;
import com.product.affiliation.backend.messaging.event.ColorEnum;
import com.product.affiliation.backend.messaging.event.ConditionEnum;
import com.product.affiliation.backend.messaging.event.ConnectivityTechEnum;
import com.product.affiliation.backend.messaging.event.DisplayTypeEnum;
import com.product.affiliation.backend.messaging.event.GetProductsEventPayload;
import com.product.affiliation.backend.messaging.event.ProductResponseEventPayload;
import com.product.affiliation.backend.messaging.event.ProductTypeEnum;
import com.product.affiliation.backend.messaging.event.SpecialFeatureEnum;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.junit5.Checkpoint;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.SqlClient;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ExtendWith(VertxExtension.class)
public class ProductRepositoryImplTest {
  private static final String DB_NAME = "products";
  private static final String DB_USER = "pankaj";
  private static final String DB_PWD = "pwd";

  private ProductRepositoryImpl SUT;

  @Container
  private PostgreSQLContainer containerPostgresql = new PostgreSQLContainer<>("postgres:11-alpine")
    .withDatabaseName(DB_NAME).withUsername(DB_USER).withPassword(DB_PWD);

  @BeforeEach
  public void init(Vertx vertx, VertxTestContext context) {
      Integer firstMappedPort = containerPostgresql.getFirstMappedPort();


      String dbURL = String.format("postgresql://%s:%s@localhost:%d/%s", DB_USER, DB_PWD, firstMappedPort, DB_NAME);
      SqlClient client = PgPool.client(vertx, dbURL);

      SUT = new ProductRepositoryImpl(vertx, client);
      SUT.createProductTable().onSuccess(handler -> context.completeNow()).onFailure(errorHandler -> context.failNow(errorHandler));
  }

  @Test
  public void testContainerIsOk() {
    assertTrue(containerPostgresql.isRunning(), "container is not running, shame!");
  }

  @Test
  public void testSaveProduct(VertxTestContext context) throws Exception {
      //Given
      ProductResponseEventPayload p = createProduct();

      //When
      context.verify(() -> {
        Future<ProductResponseEventPayload> productFuture = SUT.saveProduct(p);
        productFuture.onFailure(e -> context.failNow(e));
        productFuture.onSuccess(product -> {
            //Then
            assertNotEquals(0L, product.getId());
            context.completeNow();
        });
      });
  }

  private static List<GetProductsEventPayload> getFetchCriteria() {
    return Arrays.asList(
      GetProductsEventPayload.builder().withBrand(Set.of(BrandEnum.DELL)).build(),
      GetProductsEventPayload.builder().withProductCondition(Set.of(ConditionEnum.NEW)).build(),
      GetProductsEventPayload.builder().withScreenSize("27 Inch").build(),
      GetProductsEventPayload.builder().withMaxDisplayResolution("1920 x 1080").build(),
      GetProductsEventPayload.builder().withColors(Set.of(ColorEnum.BLACK)).build(),
      GetProductsEventPayload.builder().withMaxDisplayType(DisplayTypeEnum.HD).build(),
      GetProductsEventPayload.builder().withAmazonChoice(true).build());
  }

  @ParameterizedTest
  @MethodSource("getFetchCriteria")
  public void testFindProducts(GetProductsEventPayload inputParam, VertxTestContext context) throws Exception {
    //Given

    ProductResponseEventPayload p = createProduct();
    Checkpoint savedCheckpoint = context.checkpoint();
    Checkpoint retrivedCheckpoint = context.checkpoint();

    context.verify(() -> {
      SUT.saveProduct(p)
         .compose((ProductResponseEventPayload saved) -> {
           //When
           savedCheckpoint.flag();
           return SUT.findProducts(inputParam);
         })
        .onSuccess(fetched -> {
          //Then
          retrivedCheckpoint.flag();
          Assertions.assertFalse(fetched.isEmpty());
          context.completeNow();
        })
        .onFailure(context::failNow);
    });
  }

  @Test
  public void testRemoveProduct(VertxTestContext context) throws Exception{
    ProductResponseEventPayload p = createProduct();
    Checkpoint savedCheckpoint = context.checkpoint();
    Checkpoint deletedCheckpoint = context.checkpoint();

    context.verify(() -> {
      SUT.saveProduct(p)
        .map((ProductResponseEventPayload saved) -> {
          long id = saved.getId();
          Assertions.assertNotEquals(0L, id);
          savedCheckpoint.flag();
          return id;
        })
        .compose(id -> SUT.removeProduct(id))
        .onSuccess(d -> {
          Assertions.assertTrue(d);
          deletedCheckpoint.flag();
        })
        .onFailure(error -> context.failNow(error));
    });
  }

  @Test
  public void testFindProductById(VertxTestContext context) throws Exception {
      //Given
    ProductResponseEventPayload p = createProduct();

      Checkpoint savedCheckpoint = context.checkpoint();
      Checkpoint retrivedCheckpoint = context.checkpoint();

      //When
      context.verify(() -> {
        SUT.saveProduct(p)
            .map(savedPrd -> {
                long productId = savedPrd.getId();
                savedCheckpoint.flag();
                return productId;
            })
            .compose(pid -> SUT.findProduct(pid))
            .onSuccess(prdOptional -> {
              if(prdOptional.isPresent()) {
                  retrivedCheckpoint.flag();
                  ProductResponseEventPayload fetchedPrd = prdOptional.get();
                  System.out.println(fetchedPrd);
                  Assertions.assertEquals(1L, fetchedPrd.getId());
              } else {
                Assertions.fail("Product cannot be fetched");
              }

              context.completeNow();
            })
            .onFailure(context::failNow);
          });
  }

  private static ProductResponseEventPayload createProduct() throws Exception {
    return new ProductResponseEventPayload(0L,
      "Dell SE2222H 21.5 Inch Full HD (1920x1080) Monitor",
      "https://www.amazon.co.uk/dp/B095749V5V/ref=sspa_dk_detail_5?pd_rd_i=B095749V5V&pd_rd_w=912fo&content-id=amzn1.sym.84ea1bf1-65a8-4363-b8f5-f0df58cbb686&pf_rd_p=84ea1bf1-65a8-4363-b8f5-f0df58cbb686&pf_rd_r=MBM0N7W2E8CDFQXHB343&pd_rd_wg=aV22e&pd_rd_r=1c384c09-a39a-4922-b127-6793c694e633&s=computers&sp_csd=d2lkZ2V0TmFtZT1zcF9kZXRhaWw&th=1",
      ConditionEnum.NEW,
      69.99D,
      "3 Years",
      "27 Inch",
      "60Hz",
      "1920 x 1080",
      DisplayTypeEnum.HD,
      "4.9D x 50.4W x 29.5H centimetres",
      Stream.of(SpecialFeatureEnum.FlickerFree, SpecialFeatureEnum.TiltAdjustment).collect(Collectors.toSet()),
      BrandEnum.DELL,
      null,
      ColorEnum.BLACK,
      true,
      Stream.of(ConnectivityTechEnum.HDMI).collect(Collectors.toSet()),
      ProductTypeEnum.MONITOR,
      Short.valueOf("2"),
      "16:9");
  }
}
