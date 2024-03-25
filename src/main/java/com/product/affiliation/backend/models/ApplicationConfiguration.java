package com.product.affiliation.backend.models;

public class ApplicationConfiguration {

  private final String databaseUrl;

  public ApplicationConfiguration(String databaseUrl) {
    this.databaseUrl = databaseUrl;
  }

  public String getDatabaseUrl() {
    return databaseUrl;
  }
}
