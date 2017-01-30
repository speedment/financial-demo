package com.extspeeder.example.financialdemo.extra;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class BuySellMapper implements TypeMapper<String, BuySell> {

    @Override
    public String getLabel() {
        return "String to BUY/SELL Enum Mapper";
    }
    
    @Override
    public Class<BuySell> getJavaType(Column column) {
        return BuySell.class;
    }

    @Override
    public BuySell toJavaType(Column column, Class<?> clazz, String value) {
        return BuySell.fromDatabase(value);
    }

    @Override
    public String toDatabaseType(BuySell value) {
        return value == null ? null : value.toDatabase();
    }
}