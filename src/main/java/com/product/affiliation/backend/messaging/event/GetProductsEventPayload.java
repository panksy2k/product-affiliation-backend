package com.product.affiliation.backend.messaging.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class GetProductsEventPayload extends ProductEventPayload {
  static final String TYPE = "GET_PRODUCT";

  @JsonProperty
  private Set<ConditionEnum> productCondition;
  @JsonProperty
  private List<String> screenSize;
  @JsonProperty
  private String refreshRate;
  @JsonProperty
  private String maxDisplayResolution;
  @JsonProperty
  private DisplayTypeEnum displayType;
  @JsonProperty
  private Set<BrandEnum> brand;
  @JsonProperty
  private Set<ColorEnum> color;
  @JsonProperty("amazonChoiceYesOrNo")
  private boolean amazonChoice;
  @JsonProperty
  private Set<ConnectivityTechEnum> connectivityTech;
  @JsonProperty
  private Double priceFrom;
  @JsonProperty
  private Double priceTo;

  public GetProductsEventPayload(@JsonProperty Long id) {
    super(null);
  }

  public GetProductsEventPayload(Long id, Set<ConditionEnum> productCondition, List<String> screenSize,
                                 String refreshRate, String maxDisplayResolution, DisplayTypeEnum displayType,
                                 Set<BrandEnum> brand, Set<ColorEnum> color, boolean amazonChoice,
                                 Set<ConnectivityTechEnum> connectivityTech, Double priceFrom, Double priceTo) {
    super(id);
    this.productCondition = productCondition;
    this.screenSize = screenSize;
    this.refreshRate = refreshRate;
    this.maxDisplayResolution = maxDisplayResolution;
    this.displayType = displayType;
    this.brand = brand;
    this.color = color;
    this.amazonChoice = amazonChoice;
    this.connectivityTech = connectivityTech;
    this.priceFrom = priceFrom;
    this.priceTo = priceTo;
  }

  @Override
  public String getType() {
    return TYPE;
  }

  public Set<ConditionEnum> getProductCondition() {
    return productCondition;
  }

  public void setProductCondition(
    Set<ConditionEnum> productCondition) {
    this.productCondition = productCondition;
  }

  public List<String> getScreenSize() {
    return screenSize;
  }

  public void setScreenSize(List<String> screenSize) {
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

  public Set<BrandEnum> getBrand() {
    return brand;
  }

  public void setBrand(Set<BrandEnum> brand) {
    this.brand = brand;
  }

  public Set<ColorEnum> getColor() {
    return color;
  }

  public void setColor(Set<ColorEnum> color) {
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

  public Double getPriceFrom() {
    return priceFrom;
  }

  public void setPriceFrom(Double priceFrom) {
    this.priceFrom = priceFrom;
  }

  public Double getPriceTo() {
    return priceTo;
  }

  public void setPriceTo(Double priceTo) {
    this.priceTo = priceTo;
  }

  public static GetProductsEventPayloadBuilder builder() {
    return new GetProductsEventPayloadBuilder();
  }

  public static final class GetProductsEventPayloadBuilder {
    private Set<ConditionEnum> pc;
    private List<String> screenSize;
    private String refreshRate;
    private String maxDisplayResolution;
    private DisplayTypeEnum displayType;
    private Set<BrandEnum> brand;
    private Set<ColorEnum> color;
    private boolean amazonChoice;
    private Set<ConnectivityTechEnum> connectivityTech;
    private Double priceFrom;
    private Double priceTo;

    GetProductsEventPayloadBuilder() {}

    public GetProductsEventPayloadBuilder withProductCondition(Set<ConditionEnum> productCondition) {
      this.pc = productCondition;
      return this;
    }

    public GetProductsEventPayloadBuilder withScreenSize(String... size) {
      screenSize = Arrays.asList(size);
      return this;
    }

    public GetProductsEventPayloadBuilder withRefreshRate(String refresh) {
      refreshRate = refresh;
      return this;
    }

    public GetProductsEventPayloadBuilder withMaxDisplayResolution(String displayResolution) {
      maxDisplayResolution =  displayResolution;
      return this;
    }

    public GetProductsEventPayloadBuilder withMaxDisplayType(DisplayTypeEnum dtype) {
      displayType =  dtype;
      return this;
    }

    public GetProductsEventPayloadBuilder withBrand(Set<BrandEnum> brands) {
      this.brand = brands;
      return this;
    }

    public GetProductsEventPayloadBuilder withColors(Set<ColorEnum> colors) {
      this.color =  colors;
      return this;
    }

    public GetProductsEventPayloadBuilder withAmazonChoice(boolean isAmazonChoice) {
      amazonChoice =  isAmazonChoice;
      return this;
    }

    public GetProductsEventPayloadBuilder withConnectivityTech(Set<ConnectivityTechEnum> connectivity) {
      connectivityTech = connectivity;
      return this;
    }

    public GetProductsEventPayloadBuilder withPriceFrom(Double fromPrice) {
      priceFrom = fromPrice;
      return this;
    }

    public GetProductsEventPayloadBuilder withPriceTo(Double toPrice) {
      priceTo = toPrice;
      return this;
    }

    public GetProductsEventPayload build() {
      return new GetProductsEventPayload(null, this.pc, this.screenSize, this.refreshRate, this.maxDisplayResolution, this.displayType,
        this.brand, this.color, this.amazonChoice, this.connectivityTech, this.priceFrom, this.priceTo);
    }
  }
}
