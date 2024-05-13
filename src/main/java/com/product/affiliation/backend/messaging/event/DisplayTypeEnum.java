package com.product.affiliation.backend.messaging.event;

import java.util.HashMap;
import java.util.Map;

public enum DisplayTypeEnum {
  LED,
  LCD,
  HD;

  private static final Map<String, DisplayTypeEnum> display = new HashMap<>();

  static {
    for(DisplayTypeEnum c : values()) {
      display.put(c.name(), c);
    }
  }

  public static DisplayTypeEnum forName(String enumName) {
    return display.get(enumName);
  }
}
