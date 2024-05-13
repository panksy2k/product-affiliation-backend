package com.product.affiliation.backend.messaging.event;

import java.util.Set;

public class ProductResponseEventPayload {
  private long id;
  private String name;
  private String affiliateURL;
  private ConditionEnum productCondition;
  private Double price;
  private String warrantyValue;
  private String screenSize;
  private String refreshRate;
  private String maxDisplayResolution;
  private DisplayTypeEnum displayType;
  private String dimension;
  private Set<SpecialFeatureEnum> specialFeatures;
  private BrandEnum brand;
  private String brandSeries;
  private ColorEnum color;
  private boolean amazonChoice;
  private Set<ConnectivityTechEnum> connectivityTech;
  private ProductTypeEnum productType;
  private Short hdmiPortsQty;
  private String aspectRatio;

  public ProductTypeEnum getProductType() {
    return productType;
  }

  public void setProductType(ProductTypeEnum productType) {
    this.productType = productType;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAffiliateURL() {
    return affiliateURL;
  }

  public void setAffiliateURL(String affiliateURL) {
    this.affiliateURL = affiliateURL;
  }

  public ConditionEnum getProductCondition() {
    return productCondition;
  }

  public void setProductCondition(ConditionEnum productCondition) {
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

  public DisplayTypeEnum getDisplayType() {
    return displayType;
  }

  public void setDisplayType(DisplayTypeEnum displayType) {
    this.displayType = displayType;
  }

  public String getDimension() {
    return dimension;
  }

  public void setDimension(String dimension) {
    this.dimension = dimension;
  }

  public Set<SpecialFeatureEnum> getSpecialFeatures() {
    return specialFeatures;
  }

  public void setSpecialFeatures(
    Set<SpecialFeatureEnum> specialFeatures) {
    this.specialFeatures = specialFeatures;
  }

  public BrandEnum getBrand() {
    return brand;
  }

  public void setBrand(BrandEnum brand) {
    this.brand = brand;
  }

  public String getBrandSeries() {
    return brandSeries;
  }

  public void setBrandSeries(String brandSeries) {
    this.brandSeries = brandSeries;
  }

  public ColorEnum getColor() {
    return color;
  }

  public void setColor(ColorEnum color) {
    this.color = color;
  }

  public boolean isAmazonChoice() {
    return amazonChoice;
  }

  public void setAmazonChoice(boolean amazonChoice) {
    this.amazonChoice = amazonChoice;
  }

  public Set<ConnectivityTechEnum> getConnectivityTech() {
    return connectivityTech;
  }

  public void setConnectivityTech(
    Set<ConnectivityTechEnum> connectivityTech) {
    this.connectivityTech = connectivityTech;
  }

  public Short getHdmiPortsQty() {
    return hdmiPortsQty;
  }

  public void setHdmiPortsQty(Short hdmiPortsQty) {
    this.hdmiPortsQty = hdmiPortsQty;
  }

  public String getAspectRatio() {
    return aspectRatio;
  }

  public void setAspectRatio(String aspectRatio) {
    this.aspectRatio = aspectRatio;
  }

  public ProductResponseEventPayload(long id) {
    this.id = id;
  }

  public ProductResponseEventPayload(long id, String name, String affiliateURL, ConditionEnum productCondition,
                                     Double price, String warrantyValue, String screenSize, String refreshRate,
                                     String maxDisplayResolution, DisplayTypeEnum displayType, String dimension,
                                     Set<SpecialFeatureEnum> specialFeatures, BrandEnum brand, String brandSeries,
                                     ColorEnum color, boolean amazonChoice, Set<ConnectivityTechEnum> connectivityTech,
                                     ProductTypeEnum type, Short hdmiPortsQty, String aspectRatio) {
    this.id = id;
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
    this.brandSeries = brandSeries;
    this.color = color;
    this.amazonChoice = amazonChoice;
    this.connectivityTech = connectivityTech;
    this.productType = type;
    this.hdmiPortsQty = hdmiPortsQty;
    this.aspectRatio = aspectRatio;
  }

  public static ProductResponseEventPayload withId(Long id, ProductResponseEventPayload withoutIdPayload) {
    return new ProductResponseEventPayload(id,
        withoutIdPayload.getName(),
        withoutIdPayload.getAffiliateURL(),
        withoutIdPayload.getProductCondition(),
        withoutIdPayload.getPrice(),
        withoutIdPayload.getWarrantyValue(),
        withoutIdPayload.getScreenSize(),
        withoutIdPayload.getRefreshRate(),
        withoutIdPayload.getMaxDisplayResolution(),
        withoutIdPayload.getDisplayType(),
        withoutIdPayload.getDimension(),
        withoutIdPayload.getSpecialFeatures(),
        withoutIdPayload.getBrand(),
        withoutIdPayload.getBrandSeries(),
        withoutIdPayload.getColor(),
        withoutIdPayload.isAmazonChoice(),
        withoutIdPayload.getConnectivityTech(),
        withoutIdPayload.getProductType(),
        withoutIdPayload.getHdmiPortsQty(),
        withoutIdPayload.getAspectRatio());
  }
}
