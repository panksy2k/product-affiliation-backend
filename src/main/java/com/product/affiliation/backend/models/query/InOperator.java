package com.product.affiliation.backend.models.query;

import java.util.Set;

public class InOperator<V> implements Operator<V> {

    private final String columnName;
    private final Set<V> allInclusiveValues;

    public InOperator(String columnName, Set<V> allValues) {
        this.columnName = columnName;
        if(allValues == null) {
            throw new IllegalArgumentException("InOperator should at least have 1 value for SQL expression");
        }

        this.allInclusiveValues = allValues;
    }

    @Override
    public Set<V> values() {
        return allInclusiveValues;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }
}
