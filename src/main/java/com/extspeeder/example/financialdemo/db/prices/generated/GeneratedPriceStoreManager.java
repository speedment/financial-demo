package com.extspeeder.example.financialdemo.db.prices.generated;

import com.extspeeder.example.financialdemo.db.prices.PriceStore;
import com.speedment.runtime.core.manager.Manager;
import javax.annotation.Generated;

/**
 * The generated base interface for the manager of every {@link
 * com.extspeeder.example.financialdemo.db.prices.PriceStore} entity.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@Generated("Speedment")
public interface GeneratedPriceStoreManager extends Manager<PriceStore> {
    
    @Override
    default Class<PriceStore> getEntityClass() {
        return PriceStore.class;
    }
}