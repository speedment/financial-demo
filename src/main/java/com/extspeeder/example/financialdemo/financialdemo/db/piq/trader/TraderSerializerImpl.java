package com.extspeeder.example.financialdemo.financialdemo.db.piq.trader;

import com.extspeeder.example.financialdemo.financialdemo.db.piq.trader.generated.GeneratedTraderSerializerImpl;
import com.speedment.manager.Manager;

/**
 * A {@link org.mapdb.Serializer} class for table {@link
 * com.speedment.config.db.Table} named trader. representing an entity (for
 * example, a row) in the Table financialdemo.db0.piq.trader.
 * <p>
 * This file is safe to edit. It will not be overwritten by the code
 * generator.
 * 
 * @author Speedment
 */
public final class TraderSerializerImpl extends GeneratedTraderSerializerImpl {
    
    private final static long serialVersionUID = GeneratedTraderSerializerImpl.serialVersionUID + 1;
    
    public TraderSerializerImpl(final Manager<Trader> manager) {
        super(manager);
    }
}