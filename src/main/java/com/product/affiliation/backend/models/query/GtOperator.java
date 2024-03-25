package com.product.affiliation.backend.models.query;

import java.util.Set;

public class GtOperator<V> implements Operator<V> {

    private final String columnName;
    private final Set<V> maxRangeValue;

    public GtOperator(String columnName, Set<V> allValues) {
        this.columnName = columnName;

        if(allValues == null || allValues.size() != 1) {
            throw new IllegalArgumentException("GtOperator should have 1 value for SQL expression");
        }

        this.maxRangeValue =  allValues;
    }
    @Override
    public String name() {
        return GtOperator.class.getSimpleName();
    }

    @Override
    public Set<V> values() {
        return maxRangeValue;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }
}
