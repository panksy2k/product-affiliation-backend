package com.product.affiliation.backend.messaging.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URL;
import java.util.Arrays;

public class UpdateProductPayload extends ProductPayload {
  static final String TYPE = "UPDATE_PRODUCT";

  @JsonProperty
  private String name;
  @JsonProperty
  private URL affiliateURL;
  @JsonProperty
  private String productCondition;
  @JsonProperty
  private Double price;
  @JsonProperty
  private String warrantyValue;
  @JsonProperty
  private String screenSize;
  @JsonProperty
  private String refreshRate;
  @JsonProperty
  private String maxDisplayResolution;
  @JsonProperty
  private String displayType;
  @JsonProperty
  private String dimension;
  @JsonProperty
  private String[] specialFeatures;
  @JsonProperty
  private String brand;
  @JsonProperty
  private String color;
  @JsonProperty
  private boolean amazonChoice;
  @JsonProperty
  private String[] connectivityTech;
  @JsonProperty
  private String purveyor;
  @JsonProperty
  private String brandSeries;
  @JsonProperty
  private short hdmiPortsQty;
  @JsonProperty
  private String aspectRatio;

  public UpdateProductPayload(@JsonProperty Long id) {
    super(id);
  }

  @Override
  public String getType() {
    return TYPE;
  }

  public UpdateProductPayload(@JsonProperty Long id, String name, URL affiliateURL, String productCondition, Double price, String warrantyValue,
                              String screenSize, String refreshRate, String maxDisplayResolution, String displayType,
                              String dimension,
                              String[] specialFeatures, String brand, String color, boolean amazonChoice, String[] connectivityTech,
                              String purveyor, String brandSeries, short hdmiPortsQty, String aspectRatio) {
    super(id);
    this.name = name;
    this.affiliateURL = affiliateURL;
    this.productCondition = productCondition;
    this.price = price;
    this.warrantyValue = warrantyValue;
    this.screenSize = screenSize;
    this.refreshRate = refreshRate;
    this.maxDisplayResolution = maxDisplayResolution;
    this.displayType = displayType;
    this.dimension = dimension;
    this.specialFeatures = specialFeatures;
    this.brand = brand;
    this.color = color;
    this.amazonChoice = amazonChoice;
    this.connectivityTech = connectivityTech;
    this.purveyor = purveyor;
    this.brandSeries = brandSeries;
    this.hdmiPortsQty = hdmiPortsQty;
    this.aspectRatio = aspectRatio;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public URL getAffiliateURL() {
    return affiliateURL;
  }

  public void setAffiliateURL(URL affiliateURL) {
    this.affiliateURL = affiliateURL;
  }

  public String getProductCondition() {
    return productCondition;
  }

  public void setProductCondition(String productCondition) {
    this.productCondition = productCondition;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getWarrantyValue() {
    return warrantyValue;
  }

  public void setWarrantyValue(String warrantyValue) {
    this.warrantyValue = warrantyValue;
  }

  public String getScreenSize() {
    return screenSize;
  }

  public void setScreenSize(String screenSize) {
    this.screenSize = screenSize;
  }

  public String getRefreshRate() {
    return refreshRate;
  }

  public void setRefreshRate(String refreshRate) {
    this.refreshRate = refreshRate;
  }

  public String getMaxDisplayResolution() {
    return maxDisplayResolution;
  }

  public void setMaxDisplayResolution(String maxDisplayResolution) {
    this.maxDisplayResolution = maxDisplayResolution;
  }

  public String getDisplayType() {
    return displayType;
  }

  public void setDisplayType(String displayType) {
    this.displayType = displayType;
  }

  public String getDimension() {
    return dimension;
  }

  public void setDimension(String dimension) {
    this.dimension = dimension;
  }

  public String[] getSpecialFeatures() {
    return specialFeatures;
  }

  public void setSpecialFeatures(String[] specialFeatures) {
    this.specialFeatures = specialFeatures;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public boolean isAmazonChoice() {
    return amazonChoice;
  }

  public void setAmazonChoice(boolean amazonChoice) {
    this.amazonChoice = amazonChoice;
  }

  public String[] getConnectivityTech() {
    return connectivityTech;
  }

  public void setConnectivityTech(String[] connectivityTech) {
    this.connectivityTech = connectivityTech;
  }

  public String getPurveyor() {
    return purveyor;
  }

  public void setPurveyor(String purveyor) {
    this.purveyor = purveyor;
  }

  public String getBrandSeries() {
    return brandSeries;
  }

  public void setBrandSeries(String brandSeries) {
    this.brandSeries = brandSeries;
  }

  public short getHdmiPortsQty() {
    return hdmiPortsQty;
  }

  public void setHdmiPortsQty(short hdmiPortsQty) {
    this.hdmiPortsQty = hdmiPortsQty;
  }

  public String getAspectRatio() {
    return aspectRatio;
  }

  public void setAspectRatio(String aspectRatio) {
    this.aspectRatio = aspectRatio;
  }


  public static UpdateProductPayload withId(long id, UpdateProductPayload oldProduct) {
      UpdateProductPayload
              p = new UpdateProductPayload(id, oldProduct.getName(), oldProduct.getAffiliateURL(), oldProduct.getProductCondition(),
        oldProduct.getPrice(), oldProduct.getWarrantyValue(), oldProduct.getScreenSize(),
        oldProduct.getRefreshRate(), oldProduct.getMaxDisplayResolution(),
        oldProduct.getDisplayType(), oldProduct.getDimension(), oldProduct.getSpecialFeatures(), oldProduct.getBrand(),
        oldProduct.getColor(), oldProduct.isAmazonChoice(), oldProduct.getConnectivityTech(), oldProduct.getPurveyor(),
        oldProduct.getBrandSeries(), oldProduct.getHdmiPortsQty(), oldProduct.getAspectRatio());

      return p;
  }

  @Override
  public String toString() {
    return "Product{" +
      "id=" + getId() +
      ", name='" + name + '\'' +
      ", affiliateURL=" + affiliateURL +
      ", productCondition='" + productCondition + '\'' +
      ", price=" + price +
      ", warrantyValue='" + warrantyValue + '\'' +
      ", screenSize='" + screenSize + '\'' +
      ", refreshRate='" + refreshRate + '\'' +
      ", maxDisplayResolution='" + maxDisplayResolution + '\'' +
      ", displayType='" + displayType + '\'' +
      ", dimension='" + dimension + '\'' +
      ", specialFeatures=" + Arrays.toString(specialFeatures) +
      ", brand='" + brand + '\'' +
      ", color='" + color + '\'' +
      ", amazonChoice=" + amazonChoice +
      ", connectivityTech=" + Arrays.toString(connectivityTech) +
      ", purveyor='" + purveyor + '\'' +
      ", brandSeries='" + brandSeries + '\'' +
      ", hdmiPortsQty=" + hdmiPortsQty +
      ", aspectRatio='" + aspectRatio + '\'' +
      '}';
  }
}
