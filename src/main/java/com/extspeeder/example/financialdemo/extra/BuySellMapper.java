package com.extspeeder.example.financialdemo.extra;

import com.speedment.config.db.mapper.TypeMapper;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class BuySellMapper implements TypeMapper<String, BuySell> {

    @Override
    public Class<BuySell> getJavaType() {
        return BuySell.class;
    }

    @Override
    public Class<String> getDatabaseType() {
        return String.class;
    }

    @Override
    public BuySell toJavaType(String value) {
        return BuySell.fromDatabase(value);
    }

    @Override
    public String toDatabaseType(BuySell value) {
        return value == null ? null : value.toDatabase();
    }

    @Override
    public boolean isIdentityMapper() {
        return false;
    }
}