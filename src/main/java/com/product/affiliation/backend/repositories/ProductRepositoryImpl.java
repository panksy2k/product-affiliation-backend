package com.product.affiliation.backend.repositories;

import com.product.affiliation.backend.messaging.event.BrandEnum;
import com.product.affiliation.backend.messaging.event.ColorEnum;
import com.product.affiliation.backend.messaging.event.ConditionEnum;
import com.product.affiliation.backend.messaging.event.ConnectivityTechEnum;
import com.product.affiliation.backend.messaging.event.GetProductsEventPayload;
import com.product.affiliation.backend.messaging.event.ProductResponseEventPayload;
import com.product.affiliation.backend.messaging.event.SpecialFeatureEnum;
import com.product.affiliation.backend.util.QueryHelper;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.Tuple;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;
import org.apache.commons.lang3.StringUtils;

public class ProductRepositoryImpl implements ProductRepository {
    private final Vertx vtx;
    private final SqlClient pgClient;

    public ProductRepositoryImpl(Vertx vertx, SqlClient client) {
        this.vtx = vertx;
        this.pgClient = client;
    }

    @Override
    public Future<List<ProductResponseEventPayload>> findProducts(GetProductsEventPayload productFetchCriteria) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM product WHERE ");
        Tuple params = Tuple.tuple();
        int paramIndex = 0;

        boolean hasPredicate = false;

        if(productFetchCriteria.getProductCondition() != null && !productFetchCriteria.getProductCondition().isEmpty()) {
          Set<ConditionEnum> productConditionSet = productFetchCriteria.getProductCondition();

          hasPredicate = true;

          if(productConditionSet.size() == 1) {
            ++paramIndex;
            sqlBuilder.append("productCondition = ").append("$").append(paramIndex);
            params.addString(productConditionSet.iterator().next().name());
          } else {
            ++paramIndex;
            sqlBuilder.append("productCondition IN ($").append(paramIndex).append(")");
            params.addArrayOfString(productConditionSet.stream().map(ConditionEnum::name).toArray(String[] :: new));
          }
        }

        if(productFetchCriteria.getScreenSize() != null && !productFetchCriteria.getScreenSize().isEmpty()) {
          sqlBuilder.append(hasPredicate? " AND "  : " ").append("(");

          int frequency = 0;
          for(String sizeScreen : productFetchCriteria.getScreenSize()) {
            ++frequency;

            if(frequency > 1) {
              sqlBuilder.append(" OR ");
            }

            ++paramIndex;
            sqlBuilder.append("screenSize = $").append(paramIndex);
            params.addString(sizeScreen);
          }

          sqlBuilder.append(")");
          hasPredicate = true;
        }

        if(StringUtils.isNotBlank(productFetchCriteria.getRefreshRate())) {
          ++paramIndex;
          sqlBuilder.append(hasPredicate? " AND " : " ").append("refreshRate = $").append(paramIndex);
          params.addString(productFetchCriteria.getRefreshRate());
          hasPredicate = true;
        }

        if(StringUtils.isNotBlank(productFetchCriteria.getMaxDisplayResolution())) {
          ++paramIndex;
          sqlBuilder.append(hasPredicate? " AND " : " ").append("maxDisplayResolution = $").append(paramIndex);
          params.addString(productFetchCriteria.getMaxDisplayResolution());
          hasPredicate = true;
        }

        if(productFetchCriteria.getDisplayType() != null) {
          ++paramIndex;
          sqlBuilder.append(hasPredicate? " AND " : " ").append("displayType = $").append(paramIndex);
          params.addString(productFetchCriteria.getDisplayType().name());
          hasPredicate = true;
        }

        if(productFetchCriteria.getBrand() != null && !productFetchCriteria.getBrand().isEmpty()) {
          sqlBuilder.append(hasPredicate? " AND "  : " ").append("(");

          int frequency = 0;
          for(BrandEnum brd : productFetchCriteria.getBrand()) {
            ++frequency;

            if(frequency > 1) {
              sqlBuilder.append(" OR ");
            }

            ++paramIndex;
            sqlBuilder.append("brand = $").append(paramIndex);
            params.addString(brd.name());
          }

          sqlBuilder.append(")");

          hasPredicate = true;
        }

        if(productFetchCriteria.getColor() != null && !productFetchCriteria.getColor().isEmpty()) {
          sqlBuilder.append(hasPredicate? " AND "  : " ").append("(");

          int colorFrequency = 0;
          for(ColorEnum clr : productFetchCriteria.getColor()) {
            ++colorFrequency;

            if(colorFrequency > 1) {
              sqlBuilder.append(" OR ");
            }

            ++paramIndex;
            sqlBuilder.append("color = $").append(paramIndex);
            params.addString(clr.name());
          }

          sqlBuilder.append(")");

          hasPredicate = true;
        }

        if(productFetchCriteria.getConnectivityTech() != null && !productFetchCriteria.getConnectivityTech().isEmpty()) {
          ++paramIndex;
          sqlBuilder.append(hasPredicate? " AND "  : " ").append("connectivityTech IN ($").append(paramIndex).append(")");
          params.addArrayOfString(productFetchCriteria.getConnectivityTech().stream().map(ConnectivityTechEnum::name)
            .toArray(String[] :: new));
          hasPredicate = true;
        }

        if(productFetchCriteria.isAmazonChoice()) {
          ++paramIndex;
          sqlBuilder.append(hasPredicate? " AND " : " ").append("amazonChoice = $").append(paramIndex);
          params.addBoolean(Boolean.TRUE);
          hasPredicate = true;
        }

        if(productFetchCriteria.getPriceFrom() != null) {
          ++paramIndex;
          sqlBuilder.append(hasPredicate? " AND " : " ").append("priceFrom >= $").append(paramIndex);
          params.addDouble(productFetchCriteria.getPriceFrom());
          hasPredicate = true;
        }

        if(productFetchCriteria.getPriceTo() != null) {
          ++paramIndex;
          sqlBuilder.append(hasPredicate? " AND " : " ").append("priceTo < $").append(paramIndex);
          params.addDouble(productFetchCriteria.getPriceTo());
        }

        System.out.println(sqlBuilder.toString());

        final ProductRowMapper mapper = new ProductRowMapper();

        return pgClient.preparedQuery(sqlBuilder.toString())
                .mapping(mapper)
                .execute(params)
                .map(rows -> StreamSupport.stream(rows.spliterator(), false).collect(Collectors.toList()));
    }

    @Override
    public Future<Void> createProductTable() {
        return this.vtx.fileSystem().readFile("sql/product.sql")
                .map(b -> b.toString())
                .compose(query -> pgClient.query(query).execute())
                .compose(rowsAffected -> Future.succeededFuture());
    }

  @Override
  public Future<ProductResponseEventPayload> saveProduct(ProductResponseEventPayload newProduct) {
     String sqlBuilder = new StringBuilder().append("insert into product (price, ")
        .append("name, ")
        .append("affiliateURL, ")
        .append("productCondition, ")
        .append("screenSize, ")
        .append("maxDisplayResolution, ")
        .append("brand, ")
        .append("brandSeries,  ")
        .append("hdmiPortsQty, ")
        .append("refreshRate, ")
        .append("connectivityTech, ")
        .append("aspectRatio, ")
        .append("displayType, ")
        .append("dimension, ")
        .append("warranty, ")
        .append("specialFeatures, ")
        .append("color, ")
        .append("amazonChoice)")
        .append(" values (")
        .append(IntStream.rangeClosed(1, 18).mapToObj(r -> String.format("%s%d", "$", r)).collect(Collectors.joining(", ")))
       .append(") RETURNING id;").toString();

    Tuple tupleValue = Tuple.of(
            newProduct.getPrice(),
            newProduct.getName(),
            newProduct.getAffiliateURL(),
            newProduct.getProductCondition().name(),
            newProduct.getScreenSize(),
            newProduct.getMaxDisplayResolution(),
            newProduct.getBrand().name(),
            newProduct.getBrandSeries(),
            newProduct.getHdmiPortsQty(),
            newProduct.getRefreshRate(),
            newProduct.getConnectivityTech().stream()
                .map(ConnectivityTechEnum::name).toArray(String[] :: new),
            newProduct.getAspectRatio(),
            newProduct.getDisplayType().name(),
            newProduct.getDimension(),
            newProduct.getWarrantyValue(),
            newProduct.getSpecialFeatures().stream()
                .map(SpecialFeatureEnum::name).toArray(String[] :: new),
            newProduct.getColor().name(),
            newProduct.isAmazonChoice());

    Future<ProductResponseEventPayload> productFuture = pgClient.preparedQuery(sqlBuilder).execute(tupleValue)
      .flatMap(rs -> {
        if (rs.rowCount() == 0) {
          return Future.failedFuture("Could not create a Product ");
        }

        Row createdProduct = rs.iterator().next();
        Long productID = createdProduct.getLong("id");
        return Future.succeededFuture(ProductResponseEventPayload.withId(productID, newProduct));
    });

    return productFuture;
  }

  @Override
  public Future<Boolean> removeProduct(Long productId) {
      String sql = "DELETE from product where id = $1 RETURNING id;";
      return pgClient.preparedQuery(sql).execute(Tuple.of(productId)).map(rs -> rs.rowCount() != 0);
  }

  @Override
  public Future<Optional<ProductResponseEventPayload>> findProduct(Long productId) {
      String sql = "select * from product where id = $1";
      Tuple tupleId = Tuple.of(productId);

      return pgClient.preparedQuery(sql).mapping(new ProductRowMapper()).execute(tupleId).map((RowSet<ProductResponseEventPayload> rsProduct) -> {
          if(rsProduct.rowCount() == 0) {
            return Optional.empty();
          }

          return Optional.of(rsProduct.iterator().next());
      });
  }
}
