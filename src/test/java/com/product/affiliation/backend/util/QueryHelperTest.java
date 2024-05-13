package com.product.affiliation.backend.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class QueryHelperTest {

  @Test
  public void insertArraySql() {
      assertEquals("\'{\"HELLO\",\"WORLD\"}\'", QueryHelper.insertArraySql('{', '}', "HELLO", "WORLD"));
  }
}
