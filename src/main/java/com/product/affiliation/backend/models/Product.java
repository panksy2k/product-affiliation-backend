package com.product.affiliation.backend.models;

import java.net.URL;

public class Product {
  private long id;
  private String name;
  private URL affiliateURL;
  private String productCondition;
  private Double price;
  private String warrantyValue;
  private String screenSize;
  private String refreshRate;
  private String maxDisplayResolution;
  private String displayType;
  private String dimension;
  private String[] specialFeatures;
  private String brand;
  private String color;
  private boolean amazonChoice;
  private String[] connectivityTech;
  private String purveyor;
  private String brandSeries;
  private short hdmiPortsQty;
  private String aspectRatio;

  public Product(long id, String name, URL affiliateURL, String productCondition, Double price, String warrantyValue,
                 String screenSize, String refreshRate, String maxDisplayResolution, String displayType,
                 String dimension,
                 String[] specialFeatures, String brand, String color, boolean amazonChoice, String[] connectivityTech,
                 String purveyor, String brandSeries, short hdmiPortsQty, String aspectRatio) {
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
    this.color = color;
    this.amazonChoice = amazonChoice;
    this.connectivityTech = connectivityTech;
    this.purveyor = purveyor;
    this.brandSeries = brandSeries;
    this.hdmiPortsQty = hdmiPortsQty;
    this.aspectRatio = aspectRatio;
  }

  public Product() {
    super();
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


  public static Product withId(long id, Product oldProduct) {
      Product p = new Product(id, oldProduct.getName(), oldProduct.getAffiliateURL(), oldProduct.getProductCondition(),
        oldProduct.getPrice(), oldProduct.getWarrantyValue(), oldProduct.getScreenSize(),
        oldProduct.getRefreshRate(), oldProduct.getMaxDisplayResolution(),
        oldProduct.getDisplayType(), oldProduct.getDimension(), oldProduct.getSpecialFeatures(), oldProduct.getBrand(),
        oldProduct.getColor(), oldProduct.isAmazonChoice(), oldProduct.getConnectivityTech(), oldProduct.getPurveyor(),
        oldProduct.getBrandSeries(), oldProduct.getHdmiPortsQty(), oldProduct.getAspectRatio());

      return p;
  }
}
