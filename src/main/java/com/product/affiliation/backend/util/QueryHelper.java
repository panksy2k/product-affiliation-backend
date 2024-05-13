package com.product.affiliation.backend.util;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.util.Objects;

public class QueryHelper {

  public static String createExpression(String columnName, JsonObject childJsonObject) {
    if (childJsonObject != null) {
      if (childJsonObject.containsKey("eq") && !Objects.isNull(childJsonObject.getValue("eq"))) {
        return columnName + " = " + childJsonObject.getValue("eq");
      } else if (childJsonObject.containsKey("in") && !Objects.isNull(childJsonObject.getValue("in"))) {
        JsonArray inValue = (JsonArray) childJsonObject.getValue("in");
        return columnName + " in (" + String.join(",", inValue.getList()) + ")";
      } else if (childJsonObject.containsKey("lt") && !Objects.isNull(childJsonObject.getValue("lt"))) {
        Double eqValue = (Double) childJsonObject.getValue("lt");
        return columnName + " < " + eqValue;
      } else if (childJsonObject.containsKey("gt") && !Objects.isNull(childJsonObject.getValue("gt"))) {
        Double eqValue = (Double) childJsonObject.getValue("gt");
        return columnName + " > " + eqValue;
      }
    }

    return "";
  }

  public static String insertArraySql(char arrayStart, char arrayEnd, String... values) {
    StringBuffer sb = new StringBuffer();
    sb.append("'").append(arrayStart);

    for(String v : values) {
      sb.append("\"").append(v).append("\"").append(",");
    }

    return sb.deleteCharAt(sb.length() - 1).append(arrayEnd).append("'").toString();
  }
}
