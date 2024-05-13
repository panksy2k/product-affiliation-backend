package com.product.affiliation.backend.messaging.event;

import java.util.HashMap;
import java.util.Map;

public enum BrandEnum {
  DELL, HP, SAMSUNG, BENQ;

  private static Map<String, BrandEnum> mapper = new HashMap<>();

  static {
    for(BrandEnum b : BrandEnum.values()) {
      mapper.put(b.name(), b);
    }
  }

  public static BrandEnum forName(String brandName) {
    return mapper.get(brandName);
  }
}
