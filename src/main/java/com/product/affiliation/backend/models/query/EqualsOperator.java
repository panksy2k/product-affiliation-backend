package com.product.affiliation.backend.models.query;

import java.util.Set;

public class EqualsOperator<V> implements Operator<V> {

    private final String columnName;
    private final Set<V> value;

    public EqualsOperator(String columnName, Set<V> value) {
        this.columnName = columnName;
        if(value == null) {
            throw new IllegalArgumentException("EqualsOperator should not be null");
        }

        this.value = value;
    }

    @Override
    public Set<V> values() {
        return value;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }
}
