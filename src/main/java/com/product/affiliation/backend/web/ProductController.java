package com.product.affiliation.backend.web;

import com.product.affiliation.backend.services.ProductService;
import com.product.affiliation.backend.validator.ProductDataValidator;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class ProductController {
    private final ProductService productService;
    private final ProductDataValidator productDataValidator;

    public ProductController(ProductService service) {
        this.productService = service;
        this.productDataValidator = new ProductDataValidator();
    }

    public void getProducts(RoutingContext context) {
      JsonObject jsonObjectFilterCriteria = context.body().asJsonObject();

      productDataValidator
        .validatePayload(jsonObjectFilterCriteria)
        .compose(filters -> productService.findProducts(filters))
        .onFailure(context::fail)
        .onSuccess(allProductsList -> {
            JsonObject responseBody = JsonObject.mapFrom(allProductsList);
            context.response().setStatusCode(200).end(responseBody.encode());
        });
    }
}

