package com.extspeeder.example.financialdemo.extra;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class OrderTypeMapper implements TypeMapper<String, OrderType> {

    @Override
    public String getLabel() {
        return "String to MARKET/LIMIT/STOP Enum Mapper";
    }
    
    @Override
    public Class<OrderType> getJavaType(Column column) {
        return OrderType.class;
    }

    @Override
    public OrderType toJavaType(Column column, Class<?> clazz, String value) {
        return OrderType.fromDatabase(value);
    }

    @Override
    public String toDatabaseType(OrderType value) {
        return value == null ? null : value.toDatabase();
    }
}