package com.product.affiliation.backend.repositories;

import static com.product.affiliation.backend.util.IOUtil.isValidUrl;
import com.product.affiliation.backend.errors.ValidationException;
import com.product.affiliation.backend.messaging.event.BrandEnum;
import com.product.affiliation.backend.messaging.event.ColorEnum;
import com.product.affiliation.backend.messaging.event.ConditionEnum;
import com.product.affiliation.backend.messaging.event.ConnectivityTechEnum;
import com.product.affiliation.backend.messaging.event.DisplayTypeEnum;
import com.product.affiliation.backend.messaging.event.ProductResponseEventPayload;
import com.product.affiliation.backend.messaging.event.SpecialFeatureEnum;
import io.vertx.sqlclient.Row;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProductRowMapper implements Function<Row, ProductResponseEventPayload> {

  @Override
  public ProductResponseEventPayload apply(Row row) throws ValidationException {
    ProductResponseEventPayload temp = new ProductResponseEventPayload(row.getLong("id"));

    temp.setName(row.getString("name"));
    temp.setPrice(row.getDouble("price"));

    String affiliateURL = row.getString("affiliateurl");
    if(isValidUrl(affiliateURL)) {
      temp.setAffiliateURL(affiliateURL);
    }

    temp.setProductCondition(ConditionEnum.forName(row.getString("productcondition")));
    temp.setScreenSize(row.getString("screensize"));
    temp.setMaxDisplayResolution(row.getString("maxdisplayresolution"));
    temp.setBrand(BrandEnum.forName(row.getString("brand")));
    temp.setBrandSeries(row.getString("brandseries"));
    temp.setHdmiPortsQty(row.getShort("hdmiportsqty"));
    temp.setRefreshRate(row.getString("refreshrate"));

    String[] connectivityTechSupport = row.getArrayOfStrings("connectivitytech");
    if(connectivityTechSupport != null && connectivityTechSupport.length > 0) {
      temp.setConnectivityTech(Arrays.stream(connectivityTechSupport).map(ConnectivityTechEnum::forName).filter(
          Objects::nonNull).collect(Collectors.toSet()));
    }

    temp.setAspectRatio(row.getString("aspectratio"));
    temp.setDisplayType(DisplayTypeEnum.forName(row.getString("displaytype")));
    temp.setDimension(row.getString("dimension"));
    temp.setWarrantyValue(row.getString("warranty"));

    String[] specialFeatures = row.getArrayOfStrings("specialfeatures");
    if(specialFeatures != null && specialFeatures.length > 0) {
      temp.setSpecialFeatures(Arrays.stream(specialFeatures).map(SpecialFeatureEnum::forName).collect(Collectors.toSet()));
    }

    temp.setColor(ColorEnum.forName(row.getString("color")));
    temp.setAmazonChoice(row.getBoolean("amazonchoice"));

    return temp;
  }
}
