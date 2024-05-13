package com.product.affiliation.backend.messaging.event;

import java.util.HashMap;
import java.util.Map;

public enum SpecialFeatureEnum {
  CurvedScreen,
  TiltAdjustment,
  FlickerFree;

  private static final Map<String, SpecialFeatureEnum> feature = new HashMap<>();

  static {
    for(SpecialFeatureEnum c : values()) {
      feature.put(c.name(), c);
    }
  }

  public static SpecialFeatureEnum forName(String enumName) {
    return feature.get(enumName);
  }
}
