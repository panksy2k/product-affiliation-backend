package com.product.affiliation.backend.models.query;

import java.util.Set;

public class LtOperator<V> implements Operator<V> {

    private final String columnName;
    private final Set<V> minRangeValue;

    public LtOperator(String columnName, Set<V> atleastValue) {
        this.columnName = columnName;

        if(atleastValue == null || atleastValue.size() != 1) {
            throw new IllegalArgumentException("LtOperator should have 1 value for SQL expression");
        }

        this.minRangeValue = atleastValue;
    }

    @Override
    public String name() {
        return LtOperator.class.getSimpleName();
    }

    @Override
    public Set<V> values() {
        return minRangeValue;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }
}
