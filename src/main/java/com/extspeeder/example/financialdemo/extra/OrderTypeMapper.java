package com.extspeeder.example.financialdemo.extra;

import com.speedment.config.db.mapper.TypeMapper;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class OrderTypeMapper implements TypeMapper<String, OrderType> {

    @Override
    public Class<OrderType> getJavaType() {
        return OrderType.class;
    }

    @Override
    public Class<String> getDatabaseType() {
        return String.class;
    }

    @Override
    public OrderType toJavaType(String value) {
        return OrderType.fromDatabase(value);
    }

    @Override
    public String toDatabaseType(OrderType value) {
        return value == null ? null : value.toDatabase();
    }

    @Override
    public boolean isIdentityMapper() {
        return false;
    }
}