package com.product.affiliation.backend.messaging.event;

import java.util.HashMap;
import java.util.Map;

public enum ConnectivityTechEnum {
  USBC("USB Type C"),
  HDMI("HDMI"),
  USBA("USB Type A");

  private final String desc;

  ConnectivityTechEnum(String desc) {
    this.desc = desc;
  }

  @Override
  public String toString() {
    return this.desc;
  }

  private static final Map<String, ConnectivityTechEnum> connectivity = new HashMap<>();

  static {
    for(ConnectivityTechEnum c : values()) {
      connectivity.put(c.name(), c);
    }
  }

  public static ConnectivityTechEnum forName(String enumName) {
    return connectivity.get(enumName);
  }
}
