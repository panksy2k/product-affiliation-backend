package com.product.affiliation.backend.validator;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.Validator;
import com.product.affiliation.backend.errors.ValidationException;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import org.thymeleaf.util.StringUtils;

public class ProductDataValidator {

    private Validator<JsonObject> _validator = ValidatorBuilder.<JsonObject>of()
            .constraint(JsonObject::encode, "filters", f -> f.notNull())
            .build();

    public Future<JsonObject> validatePayload(JsonObject payloadCreateBlog) {
      JsonArray errorMessages = new JsonArray();

      try {
        if(payloadCreateBlog == null || payloadCreateBlog.isEmpty()) {
          JsonObject error = new JsonObject();
          error.put("message", "Filters are empty!");
          errorMessages.add(error);

          throw new ValidationException(errorMessages);
        }

        Iterator<Map.Entry<String, Object>> iterator = payloadCreateBlog.iterator();
        while (iterator.hasNext()) {
          Map.Entry<String, Object> jsonElement = iterator.next();

          if (StringUtils.isEmpty(jsonElement.getKey())) {
            JsonObject error = new JsonObject();
            error.put("message", "Filter with column details are absent!");
            errorMessages.add(error);

            throw new ValidationException(errorMessages);
          }

          if (Objects.isNull(jsonElement.getValue())) {
            JsonObject error = new JsonObject();
            error.put("message", "Filter with data/operation are absent!");
            errorMessages.add(error);

            throw new ValidationException(errorMessages);
          }
        }
      } catch(ValidationException e) {
        return Future.failedFuture(new ValidationException(errorMessages));
      }

      return Future.succeededFuture(payloadCreateBlog);
    }
}
