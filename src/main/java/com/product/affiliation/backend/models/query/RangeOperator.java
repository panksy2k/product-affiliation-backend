package com.product.affiliation.backend.models.query;

import java.util.Set;

public class RangeOperator<V> implements Operator<V> {

    private final String columnName;
    private final Set<V> rangeValues;

    public RangeOperator(String columnName, Set<V> allValues) {
        this.columnName = columnName;

        if(allValues == null || allValues.size() != 2) {
            throw new IllegalArgumentException("RangeOperator should have 2 values for SQL expression");
        }

        this.rangeValues = allValues;
    }

    @Override
    public Set<V> values() {
        return rangeValues;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }
}
