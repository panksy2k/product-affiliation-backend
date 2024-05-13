package com.product.affiliation.backend.messaging.event;

import java.util.HashMap;
import java.util.Map;

public enum ConditionEnum {
  NEW,
  USED;

  private static final Map<String, ConditionEnum> conditions = new HashMap<>();

  static {
    for(ConditionEnum c : values()) {
      conditions.put(c.name(), c);
    }
  }

  public static ConditionEnum forName(String enumName) {
    return conditions.get(enumName);
  }
}
