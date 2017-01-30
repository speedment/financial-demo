package com.extspeeder.example.financialdemo.extra;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class CohortTypeMapper implements TypeMapper<String, CohortType> {

    @Override
    public String getLabel() {
        return "String to CohortType Enum Mapper";
    }
    
    @Override
    public Class<CohortType> getJavaType(Column column) {
        return CohortType.class;
    }

    @Override
    public CohortType toJavaType(Column column, Class<?> clazz, String value) {
        return CohortType.fromDatabase(value);
    }

    @Override
    public String toDatabaseType(CohortType value) {
        return value == null ? null : value.toDatabase();
    }
}