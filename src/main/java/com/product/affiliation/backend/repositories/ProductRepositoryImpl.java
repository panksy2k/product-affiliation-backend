package com.product.affiliation.backend.repositories;

import com.product.affiliation.backend.messaging.event.GetProductPayload;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.Tuple;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class ProductRepositoryImpl implements ProductRepository {
    private final Vertx vtx;
    private final SqlClient pgClient;

    public ProductRepositoryImpl(Vertx vertx, SqlClient client) {
        this.vtx = vertx;
        this.pgClient = client;
    }

    @Override
    public Future<List<GetProductPayload>> findProducts(List<String> queryWhereClauseCriteria) {
        StringBuilder sqlBuilder = new StringBuilder("select * from product where ");
        for(int i = 0; i < queryWhereClauseCriteria.size(); i++) {
          String expr = queryWhereClauseCriteria.get(i);

          if(i == queryWhereClauseCriteria.size() - 1) {
            sqlBuilder.append(expr);
          } else {
            sqlBuilder.append(expr).append(" and ");
          }
        }

        ProductRowMapper mapper = new ProductRowMapper();

        return pgClient.preparedQuery(sqlBuilder.toString())
                .mapping(mapper)
                .execute()
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
  public Future<GetProductPayload> saveProduct(GetProductPayload newProduct) {
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
        .append("amazonChoice,")
        .append("purveyor) ")
        .append("values (")
        .append(IntStream.rangeClosed(1, 19).mapToObj(r -> String.format("%s%d", "$", r)).collect(Collectors.joining(", ")))
       .append(") RETURNING id;").toString();

    Tuple tupleValue = Tuple.of(
            newProduct.getPrice(),
            newProduct.getName(),
            newProduct.getAffiliateURL().toString(),
            newProduct.getProductCondition(),
            newProduct.getScreenSize(),
            newProduct.getMaxDisplayResolution(),
            newProduct.getBrand(),
            newProduct.getBrandSeries(),
            newProduct.getHdmiPortsQty(),
            newProduct.getRefreshRate(),
            newProduct.getConnectivityTech(),
            newProduct.getAspectRatio(),
            newProduct.getDisplayType(),
            newProduct.getDimension(),
            newProduct.getWarrantyValue(),
            newProduct.getSpecialFeatures(),
            newProduct.getColor(),
            newProduct.isAmazonChoice(),
            newProduct.getPurveyor());

    Future<GetProductPayload> productFuture = pgClient.preparedQuery(sqlBuilder).execute(tupleValue).flatMap(rs -> {
      if (rs.rowCount() == 0) {
        return Future.failedFuture("Could not create a Product ");
      }

      Row createdProduct = rs.iterator().next();
      Long productID = createdProduct.getLong("id");
      return Future.succeededFuture(GetProductPayload.withId(productID, newProduct));
    });

    return productFuture;
  }

  @Override
  public Future<Boolean> removeProduct(Long productId) {
      String sql = "DELETE from product where id = $1 RETURNING id;";
      return pgClient.preparedQuery(sql).execute(Tuple.of(productId)).map(rs -> rs.rowCount() != 0);
  }

  @Override
  public Future<Optional<GetProductPayload>> findProduct(Long productId) {
      String sql = "select * from product where id = $1";
      Tuple tupleId = Tuple.of(productId);

      return pgClient.preparedQuery(sql).mapping(new ProductRowMapper()).execute(tupleId).map((RowSet<GetProductPayload> rsProduct) -> {
          if(rsProduct.rowCount() == 0) {
            return Optional.empty();
          }

          return Optional.of(rsProduct.iterator().next());
      });
  }
}
