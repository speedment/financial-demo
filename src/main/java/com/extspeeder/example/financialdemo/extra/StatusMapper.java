package com.extspeeder.example.financialdemo.extra;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class StatusMapper implements TypeMapper<String, Status> {

    @Override
    public String getLabel() {
        return "String to Status Enum Mapper";
    }
    
    @Override
    public Class<Status> getJavaType(Column column) {
        return Status.class;
    }

    @Override
    public Status toJavaType(Column column, Class<?> clazz, String value) {
        return Status.fromDatabase(value);
    }

    @Override
    public String toDatabaseType(Status value) {
        return value == null ? null : value.toDatabase();
    }
}