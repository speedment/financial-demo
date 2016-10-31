package com.extspeeder.example.financialdemo.extra;

import com.speedment.config.db.mapper.TypeMapper;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class StatusMapper implements TypeMapper<String, Status> {

    @Override
    public Class<Status> getJavaType() {
        return Status.class;
    }

    @Override
    public Class<String> getDatabaseType() {
        return String.class;
    }

    @Override
    public Status toJavaType(String value) {
        return Status.fromDatabase(value);
    }

    @Override
    public String toDatabaseType(Status value) {
        return value == null ? null : value.toDatabase();
    }

    @Override
    public boolean isIdentityMapper() {
        return false;
    }
}