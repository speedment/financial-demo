package com.extspeeder.example.financialdemo.extra;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class CohortTypeSerializer 
extends AbstractEnumTypeSerializer<CohortType> {

    public CohortTypeSerializer() {
        super(CohortType.class, CohortType.values());
    }
}