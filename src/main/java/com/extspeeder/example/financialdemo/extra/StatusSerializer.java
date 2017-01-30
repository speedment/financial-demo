package com.extspeeder.example.financialdemo.extra;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class StatusSerializer 
extends AbstractEnumTypeSerializer<Status> {

    public StatusSerializer() {
        super(Status.class, Status.values());
    }
}