package com.extspeeder.example.financialdemo.financialdemo.db.piq.cohort;

import com.extspeeder.example.financialdemo.financialdemo.db.piq.cohort.generated.GeneratedCohortSerializerImpl;
import com.speedment.manager.Manager;

/**
 * A {@link org.mapdb.Serializer} class for table {@link
 * com.speedment.config.db.Table} named cohort. representing an entity (for
 * example, a row) in the Table financialdemo.db0.piq.cohort.
 * <p>
 * This file is safe to edit. It will not be overwritten by the code
 * generator.
 * 
 * @author Speedment
 */
public final class CohortSerializerImpl extends GeneratedCohortSerializerImpl {
    
    private final static long serialVersionUID = GeneratedCohortSerializerImpl.serialVersionUID + 1;
    
    public CohortSerializerImpl(final Manager<Cohort> manager) {
        super(manager);
    }
}