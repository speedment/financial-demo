package com.extspeeder.example.financialdemo.financialdemo.db.piq.raw_position;

import com.extspeeder.example.financialdemo.financialdemo.db.piq.raw_position.generated.GeneratedRawPositionSerializerImpl;
import com.speedment.manager.Manager;

/**
 * A {@link org.mapdb.Serializer} class for table {@link
 * com.speedment.config.db.Table} named daily_position_performance.
 * representing an entity (for example, a row) in the Table
 * financialdemo.db0.piq.daily_position_performance.
 * <p>
 * This file is safe to edit. It will not be overwritten by the code
 * generator.
 * 
 * @author Speedment
 */
public final class RawPositionSerializerImpl extends GeneratedRawPositionSerializerImpl {
    
    private final static long serialVersionUID = GeneratedRawPositionSerializerImpl.serialVersionUID + 1;
    
    public RawPositionSerializerImpl(final Manager<RawPosition> manager) {
        super(manager);
    }
}