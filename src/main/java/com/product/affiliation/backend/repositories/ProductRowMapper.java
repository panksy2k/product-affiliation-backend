package com.product.affiliation.backend.repositories;

import com.product.affiliation.backend.errors.ValidationException;
import com.product.affiliation.backend.models.Product;
import io.vertx.core.json.JsonArray;
import io.vertx.sqlclient.Row;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Function;
import org.apache.commons.lang3.StringUtils;

public class ProductRowMapper implements Function<Row, Product> {

  @Override
  public Product apply(Row row) throws ValidationException {
    Product temp = new Product();

    temp.setId(row.getLong("id"));
    temp.setName(row.getString("name"));
    temp.setPrice(row.getDouble("price"));

    String affiliateURL = row.getString("affiliateURL");
    if(StringUtils.isBlank(affiliateURL)) {
      throw new ValidationException(new JsonArray().set(0, "Affiliate URL not found"));
    } else {
      try {
        temp.setAffiliateURL(new URL(affiliateURL));
      } catch(MalformedURLException e) {
        throw new ValidationException(new JsonArray().set(0, "Affiliate URL is malformed"));
      }
    }

    temp.setProductCondition(row.getString("productCondition"));
    temp.setScreenSize(row.getString("screenSize"));
    temp.setMaxDisplayResolution(row.getString("maxDisplayResolution"));
    temp.setBrand(row.getString("brand"));
    temp.setBrandSeries(row.getString("brandSeries"));
    temp.setHdmiPortsQty(row.getShort("hdmiPortsQty"));
    temp.setRefreshRate(row.getString("refreshRate"));
    temp.setConnectivityTech(row.getArrayOfStrings("connectivityTech"));
    temp.setAspectRatio(row.getString("aspectRatio"));
    temp.setDisplayType(row.getString("displayType"));
    temp.setDimension(row.getString("dimension"));
    temp.setWarrantyValue(row.getString("warranty"));
    temp.setSpecialFeatures(row.getArrayOfStrings("specialFeatures"));
    temp.setColor(row.getString("color"));
    temp.setAmazonChoice(row.getBoolean("amazonChoice"));
    temp.setPurveyor(row.getString("purveyor"));

    return temp;
  }
}
