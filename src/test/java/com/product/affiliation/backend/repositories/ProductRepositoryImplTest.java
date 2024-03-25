package com.product.affiliation.backend.repositories;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.product.affiliation.backend.models.Product;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.pgclient.PgPool;
import io.vertx.pgclient.impl.PgPoolImpl;
import io.vertx.pgclient.impl.PgPoolOptions;
import io.vertx.sqlclient.SqlClient;
import java.net.URL;
import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
  public void saveProduct(VertxTestContext context) throws Exception {
      //Given
      Product p = new Product(0L,
              "Dell SE2222H 21.5 Inch Full HD (1920x1080) Monitor",
              new URL("https://www.amazon.co.uk/dp/B095749V5V/ref=sspa_dk_detail_5?pd_rd_i=B095749V5V&pd_rd_w=912fo&content-id=amzn1.sym.84ea1bf1-65a8-4363-b8f5-f0df58cbb686&pf_rd_p=84ea1bf1-65a8-4363-b8f5-f0df58cbb686&pf_rd_r=MBM0N7W2E8CDFQXHB343&pd_rd_wg=aV22e&pd_rd_r=1c384c09-a39a-4922-b127-6793c694e633&s=computers&sp_csd=d2lkZ2V0TmFtZT1zcF9kZXRhaWw&th=1"),
              "NEW",
              69.99,
              "3 Years",
              "27 Inch",
              "60 Hz",
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

      //When
      context.verify(() -> {
        Future<Product> productFuture = SUT.saveProduct(p);
        productFuture.onFailure(e -> context.failNow(e));
        productFuture.onSuccess(product -> {
            //Then
            assertFalse(product.getId() == 0L);
            context.completeNow();
        });
      });
  }

  @Test
  public void findProducts() {
  }


  @Test
  void removeProduct() {
  }
}
