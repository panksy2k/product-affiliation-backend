package com.product.affiliation.backend.config;

import com.product.affiliation.backend.models.ApplicationConfiguration;
import io.vertx.core.Future;

public interface ApplicationConfig {
  Future<ApplicationConfiguration> receiveApplicationConfig();
}
