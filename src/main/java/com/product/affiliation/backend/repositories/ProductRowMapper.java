package com.product.affiliation.backend.repositories;

import com.product.affiliation.backend.errors.ValidationException;
import com.product.affiliation.backend.messaging.event.GetProductPayload;
import io.vertx.core.json.JsonArray;
import io.vertx.sqlclient.Row;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Function;
import org.apache.commons.lang3.StringUtils;

public class ProductRowMapper implements Function<Row, GetProductPayload> {

  @Override
  public GetProductPayload apply(Row row) throws ValidationException {
    GetProductPayload temp = new GetProductPayload(row.getLong("id"));
    temp.setName(row.getString("name"));
    temp.setPrice(row.getDouble("price"));

    String affiliateURL = row.getString("affiliateurl");
    if(StringUtils.isBlank(affiliateURL)) {
      throw new ValidationException(new JsonArray().set(0, "Affiliate URL not found"));
    } else {
      try {
        temp.setAffiliateURL(new URL(affiliateURL));
      } catch(MalformedURLException e) {
        throw new ValidationException(new JsonArray().set(0, "Affiliate URL is malformed"));
      }
    }

    temp.setProductCondition(row.getString("productcondition"));
    temp.setScreenSize(row.getString("screensize"));
    temp.setMaxDisplayResolution(row.getString("maxdisplayresolution"));
    temp.setBrand(row.getString("brand"));
    temp.setBrandSeries(row.getString("brandseries"));
    temp.setHdmiPortsQty(row.getShort("hdmiportsqty"));
    temp.setRefreshRate(row.getString("refreshrate"));
    temp.setConnectivityTech(row.getArrayOfStrings("connectivitytech"));
    temp.setAspectRatio(row.getString("aspectratio"));
    temp.setDisplayType(row.getString("displaytype"));
    temp.setDimension(row.getString("dimension"));
    temp.setWarrantyValue(row.getString("warranty"));
    temp.setSpecialFeatures(row.getArrayOfStrings("specialfeatures"));
    temp.setColor(row.getString("color"));
    temp.setAmazonChoice(row.getBoolean("amazonchoice"));
    temp.setPurveyor(row.getString("purveyor"));

    return temp;
  }
}
