package com.extspeeder.example.financialdemo.financialdemo.db.piq.price_store.generated;

import com.extspeeder.example.financialdemo.financialdemo.db.piq.price_store.PriceStore;
import com.extspeeder.example.financialdemo.financialdemo.db.piq.price_store.PriceStoreManager;
import com.speedment.component.ProjectComponent;
import com.speedment.config.db.Table;
import com.speedment.enterprise.offheapreadonlycache.manager.HasSerializer;
import com.speedment.internal.core.manager.sql.SqlManager;
import com.speedment.util.tuple.Tuple1;
import javax.annotation.Generated;
import org.mapdb.Serializer;

/**
 * The generated base manager representing an entity (for example, a row) in
 * the Table financialdemo.db0.piq.price_store.
 * <p>
 * This file has been automatically generated by Ext Speeder. Any changes
 * made to it will be overwritten.
 * 
 * @author Ext Speeder
 */
@Generated("Ext Speeder")
public interface GeneratedPriceStoreManager extends HasSerializer<PriceStore>, SqlManager<PriceStore> {
    
    @Override
    default Serializer<PriceStore> newSerializer() {
        return new GeneratedPriceStoreSerializerImpl(this);
    }
    
    @Override
    default Long primaryKeyFor(PriceStore entity) {
        return entity.getId();
    }
    
    @Override
    default Table getTable() {
        return speedment().get(ProjectComponent.class).getProject().findTableByName("db0.piq.price_store");
    }
    
    @Override
    default Class<PriceStoreManager> getManagerClass() {
        return PriceStoreManager.class;
    }
    
    @Override
    default Class<PriceStore> getEntityClass() {
        return PriceStore.class;
    }
    
    @Override
    Tuple1<Class<Long>> getPrimaryKeyClasses();
}