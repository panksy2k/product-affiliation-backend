package com.product.affiliation.backend.messaging.event;

import java.util.HashMap;
import java.util.Map;

public enum ColorEnum {
  BLACK, WHITE;

  private static final Map<String, ColorEnum> color = new HashMap<>();

  static {
    for(ColorEnum c : values()) {
      color.put(c.name(), c);
    }
  }

  public static ColorEnum forName(String enumName) {
    return color.get(enumName);
  }
}
