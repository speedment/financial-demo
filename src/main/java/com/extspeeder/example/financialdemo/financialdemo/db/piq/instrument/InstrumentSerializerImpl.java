package com.extspeeder.example.financialdemo.financialdemo.db.piq.instrument;

import com.extspeeder.example.financialdemo.financialdemo.db.piq.instrument.generated.GeneratedInstrumentSerializerImpl;
import com.speedment.manager.Manager;

/**
 * A {@link org.mapdb.Serializer} class for table {@link
 * com.speedment.config.db.Table} named instrument. representing an entity
 * (for example, a row) in the Table financialdemo.db0.piq.instrument.
 * <p>
 * This file is safe to edit. It will not be overwritten by the code
 * generator.
 * 
 * @author Speedment
 */
public final class InstrumentSerializerImpl extends GeneratedInstrumentSerializerImpl {
    
    private final static long serialVersionUID = GeneratedInstrumentSerializerImpl.serialVersionUID + 1;
    
    public InstrumentSerializerImpl(final Manager<Instrument> manager) {
        super(manager);
    }
}