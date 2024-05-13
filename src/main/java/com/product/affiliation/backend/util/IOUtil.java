package com.product.affiliation.backend.util;

import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.lang3.StringUtils;

public class IOUtil {
    public static boolean isValidUrl(String strURL) {
      if(StringUtils.isBlank(strURL)) {
        return false;
      }

      try {
        new URL(strURL);
        return true;
      } catch(MalformedURLException e) {
        return false;
      }
    }
}
