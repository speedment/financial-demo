package com.extspeeder.example.financialdemo.controller.param;

import com.google.gson.annotations.SerializedName;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class Filter {

    public enum Operator {
        @SerializedName("eq") EQUAL,
        @SerializedName("ne") NOT_EQUAL,
        @SerializedName("lt") LESS_THAN,
        @SerializedName("le") LESS_OR_EQUAL,
        @SerializedName("gt") GREATER_THAN,
        @SerializedName("ge") GREATER_OR_EQUAL,
        @SerializedName("like") LIKE;
    }
       
    private final Operator operator;
    private final String value;
    private final String property;
    
    public Filter(Operator operator, String value, String property) {
        this.operator = requireNonNull(operator);
        this.value    = requireNonNull(value);
        this.property = requireNonNull(property);
    }

    public Operator getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }

    public String getProperty() {
        return property;
    }

    @Override
    public String toString() {
        return "Filter{operator=" + operator + 
            ", value=" + value + 
            ", property=" + property + '}';
    }
}