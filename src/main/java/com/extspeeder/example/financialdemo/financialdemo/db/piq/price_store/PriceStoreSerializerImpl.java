package com.extspeeder.example.financialdemo.financialdemo.db.piq.price_store;

import com.extspeeder.example.financialdemo.financialdemo.db.piq.price_store.generated.GeneratedPriceStoreSerializerImpl;
import com.speedment.manager.Manager;

/**
 * A {@link org.mapdb.Serializer} class for table {@link
 * com.speedment.config.db.Table} named price_store. representing an entity
 * (for example, a row) in the Table financialdemo.db0.piq.price_store.
 * <p>
 * This file is safe to edit. It will not be overwritten by the code
 * generator.
 * 
 * @author Speedment
 */
public final class PriceStoreSerializerImpl extends GeneratedPriceStoreSerializerImpl {
    
    private final static long serialVersionUID = GeneratedPriceStoreSerializerImpl.serialVersionUID + 1;
    
    public PriceStoreSerializerImpl(final Manager<PriceStore> manager) {
        super(manager);
    }
}