package com.product.affiliation.backend.config;

import com.product.affiliation.backend.models.ApplicationConfiguration;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.testcontainers.containers.PostgreSQLContainer;

public class EmbeddedApplicationConfig implements ApplicationConfig {
  private static final String DB_USER = "pankaj";
  private static final String DB_PASSWD = "secret";
  private static final String DB_TABLE = "products";
  private final Vertx vtx;

  public EmbeddedApplicationConfig(Vertx vertx) {
    this.vtx = vertx;
  }

  @Override
  public Future<ApplicationConfiguration> receiveApplicationConfig() {
    return this.vtx.executeBlocking(promise -> {
      try {
        JsonObject containerConfig = runPostgreSQLContainer();
        promise.complete(containerConfig);
      } catch(Exception e) {
        promise.fail(e);
      }
    }).map(data -> {
      JsonObject containerData = JsonObject.mapFrom(data);
      return new ApplicationConfiguration(containerData.getString("db_url"));
    });
  }

  private JsonObject runPostgreSQLContainer() throws Exception {
    try {
      PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:11-alpine")
        .withDatabaseName(DB_TABLE).withUsername(DB_USER).withPassword(DB_PASSWD);

      container.start();

      JsonObject config = new JsonObject();
      config.put("db_url",
        String.format("postgresql://pankaj:secret@localhost:%d/%s", container.getFirstMappedPort(), DB_TABLE));

      return config;
    } catch (Exception e) {
      throw new RuntimeException("Unable to create Postgresql container " + e.getMessage());
    }
  }
}
