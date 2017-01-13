package com.extspeeder.example.financialdemo.db.order.generated;

import com.extspeeder.example.financialdemo.db.order.Order;
import com.extspeeder.example.financialdemo.db.order.OrderEntityStoreSerializerImpl;
import com.extspeeder.example.financialdemo.db.order.OrderManager;
import com.speedment.enterprise.fastcache.runtime.HasStatistics.Statistics;
import com.speedment.enterprise.fastcache.runtime.entitystore.EntityStore;
import com.speedment.enterprise.fastcache.runtime.entitystore.EntityStoreHolder;
import com.speedment.enterprise.fastcache.runtime.entitystore.StringSelection.Encoding;
import com.speedment.enterprise.fastcache.runtime.fieldcache.FieldCache.OfFloat;
import com.speedment.enterprise.fastcache.runtime.fieldcache.FieldCache.OfInt;
import com.speedment.enterprise.fastcache.runtime.fieldcache.FieldCache.OfLong;
import com.speedment.enterprise.fastcache.runtime.fieldcache.FieldCache.OfObject;
import com.speedment.enterprise.fastcache.runtime.fieldcache.FieldCache.OfShort;
import com.speedment.enterprise.fastcache.runtime.fieldcache.FieldCache.OfString;
import com.speedment.enterprise.fastcache.runtime.fieldcache.FieldCache;
import com.speedment.enterprise.fastcache.runtime.fieldcache.FieldCacheBuilder;
import com.speedment.enterprise.fastcache.runtime.internal.util.StatisticsInternalUtil;
import com.speedment.enterprise.fastcache.runtime.util.FastCacheHolderUtil;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.StreamSupplierComponent;
import com.speedment.web.licenseservice.fastpiq.helper.BuySell;
import com.speedment.web.licenseservice.fastpiq.helper.OrderType;
import com.speedment.web.licenseservice.fastpiq.helper.Status;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import javax.annotation.Generated;
import static java.util.Objects.requireNonNull;

/**
 * A holder class for the various caches that are used to speed up the {@link
 * OrderManager}.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * 
 * @author Speedment
 */
@Generated("Speedment")
public final class GeneratedOrderCacheHolder implements EntityStoreHolder<Order> {
    
    private final static TableIdentifier<Order> TABLE_IDENTIFIER = TableIdentifier.of("db0", "piq", "orders");
    private final EntityStore<Order> entityStore;
    private final OfLong<Order> fieldIdCache;
    private final OfShort<Order> fieldDateCreatedCache;
    private final OfObject<Order, BuySell> fieldDirectionCache;
    private final OfObject<Order, OrderType> fieldOrderTypeCache;
    private final OfInt<Order> fieldQuantityCache;
    private final OfObject<Order, Status> fieldStatusCache;
    private final OfFloat<Order> fieldLimitPriceCache;
    private final OfString<Order> fieldInstrumentSymbolCache;
    private final OfString<Order> fieldInstrumentSectorCache;
    private final OfString<Order> fieldInstrumentIndustryCache;
    private final OfString<Order> fieldTraderNameCache;
    private final OfString<Order> fieldTraderGroupCache;
    private final OfFloat<Order> fieldPriceCache;
    private final OfShort<Order> fieldDateExecutedCache;
    private final OfString<Order> fieldInstrumentNameCache;
    
    public GeneratedOrderCacheHolder(
            EntityStore<Order> entityStore,
            OfLong<Order> fieldIdCache,
            OfShort<Order> fieldDateCreatedCache,
            OfObject<Order, BuySell> fieldDirectionCache,
            OfObject<Order, OrderType> fieldOrderTypeCache,
            OfInt<Order> fieldQuantityCache,
            OfObject<Order, Status> fieldStatusCache,
            OfFloat<Order> fieldLimitPriceCache,
            OfString<Order> fieldInstrumentSymbolCache,
            OfString<Order> fieldInstrumentSectorCache,
            OfString<Order> fieldInstrumentIndustryCache,
            OfString<Order> fieldTraderNameCache,
            OfString<Order> fieldTraderGroupCache,
            OfFloat<Order> fieldPriceCache,
            OfShort<Order> fieldDateExecutedCache,
            OfString<Order> fieldInstrumentNameCache) {
        
        this.entityStore                  = requireNonNull(entityStore);
        this.fieldIdCache                 = requireNonNull(fieldIdCache);
        this.fieldDateCreatedCache        = requireNonNull(fieldDateCreatedCache);
        this.fieldDirectionCache          = requireNonNull(fieldDirectionCache);
        this.fieldOrderTypeCache          = requireNonNull(fieldOrderTypeCache);
        this.fieldQuantityCache           = requireNonNull(fieldQuantityCache);
        this.fieldStatusCache             = requireNonNull(fieldStatusCache);
        this.fieldLimitPriceCache         = requireNonNull(fieldLimitPriceCache);
        this.fieldInstrumentSymbolCache   = requireNonNull(fieldInstrumentSymbolCache);
        this.fieldInstrumentSectorCache   = requireNonNull(fieldInstrumentSectorCache);
        this.fieldInstrumentIndustryCache = requireNonNull(fieldInstrumentIndustryCache);
        this.fieldTraderNameCache         = requireNonNull(fieldTraderNameCache);
        this.fieldTraderGroupCache        = requireNonNull(fieldTraderGroupCache);
        this.fieldPriceCache              = requireNonNull(fieldPriceCache);
        this.fieldDateExecutedCache       = requireNonNull(fieldDateExecutedCache);
        this.fieldInstrumentNameCache     = requireNonNull(fieldInstrumentNameCache);
    }
    
    @Override
    public EntityStore<Order> getEntityStore() {
        return entityStore;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <ENTITY, T> FieldCache<ENTITY, T> getFieldCache(ColumnIdentifier<ENTITY> columnId) {
        if (columnId instanceof Order.Identifier) {
            final Order.Identifier _id = (Order.Identifier) columnId;
            switch (_id) {
                case ID                  : return (FieldCache<ENTITY, T>) fieldIdCache;
                case DATE_CREATED        : return (FieldCache<ENTITY, T>) fieldDateCreatedCache;
                case DIRECTION           : return (FieldCache<ENTITY, T>) fieldDirectionCache;
                case ORDER_TYPE          : return (FieldCache<ENTITY, T>) fieldOrderTypeCache;
                case QUANTITY            : return (FieldCache<ENTITY, T>) fieldQuantityCache;
                case STATUS              : return (FieldCache<ENTITY, T>) fieldStatusCache;
                case LIMIT_PRICE         : return (FieldCache<ENTITY, T>) fieldLimitPriceCache;
                case INSTRUMENT_SYMBOL   : return (FieldCache<ENTITY, T>) fieldInstrumentSymbolCache;
                case INSTRUMENT_SECTOR   : return (FieldCache<ENTITY, T>) fieldInstrumentSectorCache;
                case INSTRUMENT_INDUSTRY : return (FieldCache<ENTITY, T>) fieldInstrumentIndustryCache;
                case TRADER_NAME         : return (FieldCache<ENTITY, T>) fieldTraderNameCache;
                case TRADER_GROUP        : return (FieldCache<ENTITY, T>) fieldTraderGroupCache;
                case PRICE               : return (FieldCache<ENTITY, T>) fieldPriceCache;
                case DATE_EXECUTED       : return (FieldCache<ENTITY, T>) fieldDateExecutedCache;
                case INSTRUMENT_NAME     : return (FieldCache<ENTITY, T>) fieldInstrumentNameCache;
                default : throw new UnsupportedOperationException(
                    String.format("Unknown enum constant '%s'.", _id)
                );
            }
        } else {
            final String _colName = columnId.getColumnName();
            switch (_colName) {
                case "id"                  : return (FieldCache<ENTITY, T>) fieldIdCache;
                case "date_created_int"    : return (FieldCache<ENTITY, T>) fieldDateCreatedCache;
                case "direction"           : return (FieldCache<ENTITY, T>) fieldDirectionCache;
                case "order_type"          : return (FieldCache<ENTITY, T>) fieldOrderTypeCache;
                case "quantity"            : return (FieldCache<ENTITY, T>) fieldQuantityCache;
                case "status"              : return (FieldCache<ENTITY, T>) fieldStatusCache;
                case "limit_price"         : return (FieldCache<ENTITY, T>) fieldLimitPriceCache;
                case "instrument_symbol"   : return (FieldCache<ENTITY, T>) fieldInstrumentSymbolCache;
                case "instrument_sector"   : return (FieldCache<ENTITY, T>) fieldInstrumentSectorCache;
                case "instrument_industry" : return (FieldCache<ENTITY, T>) fieldInstrumentIndustryCache;
                case "trader_name"         : return (FieldCache<ENTITY, T>) fieldTraderNameCache;
                case "trader_group"        : return (FieldCache<ENTITY, T>) fieldTraderGroupCache;
                case "price"               : return (FieldCache<ENTITY, T>) fieldPriceCache;
                case "date_executed"       : return (FieldCache<ENTITY, T>) fieldDateExecutedCache;
                case "instrument_name"     : return (FieldCache<ENTITY, T>) fieldInstrumentNameCache;
                default : throw new UnsupportedOperationException(
                    String.format("Unknown column name '%s'.", _colName)
                );
            }
        }
    }
    
    public static CompletableFuture<GeneratedOrderCacheHolder> reload(StreamSupplierComponent streamSupplier, ExecutorService executor) {
        final OrderEntityStoreSerializerImpl serializer = 
            new OrderEntityStoreSerializerImpl();
        final CompletableFuture<EntityStore<Order>> entityStoreFuture = 
            FastCacheHolderUtil.buildEntityStore(streamSupplier, executor, serializer, TABLE_IDENTIFIER);
        
        final CompletableFuture<FieldCache.OfLong<Order>> fieldIdCacheFuture =
            FastCacheHolderUtil.buildLongCache(entityStoreFuture, Order.ID, FieldCache.UNINDEXED | FieldCache.REFERENCE_ORDER | FieldCache.DISTINCT);
        
        final CompletableFuture<FieldCache.OfShort<Order>> fieldDateCreatedCacheFuture =
            FastCacheHolderUtil.buildShortCache(entityStoreFuture, Order.DATE_CREATED, 0);
        
        final CompletableFuture<FieldCache.OfObject<Order, BuySell>> fieldDirectionCacheFuture =
            FastCacheHolderUtil.buildObjectCache(entityStoreFuture, Order.DIRECTION, 0);
        
        final CompletableFuture<FieldCache.OfObject<Order, OrderType>> fieldOrderTypeCacheFuture =
            FastCacheHolderUtil.buildObjectCache(entityStoreFuture, Order.ORDER_TYPE, 0);
        
        final CompletableFuture<FieldCache.OfInt<Order>> fieldQuantityCacheFuture =
            FastCacheHolderUtil.buildIntCache(entityStoreFuture, Order.QUANTITY, 0);
        
        final CompletableFuture<FieldCache.OfObject<Order, Status>> fieldStatusCacheFuture =
            FastCacheHolderUtil.buildObjectCache(entityStoreFuture, Order.STATUS, 0);
        
        final CompletableFuture<FieldCache.OfFloat<Order>> fieldLimitPriceCacheFuture =
            FastCacheHolderUtil.buildFloatCache(entityStoreFuture, Order.LIMIT_PRICE, 0);
        
        final CompletableFuture<FieldCache.OfString<Order>> fieldInstrumentSymbolCacheFuture =
            FastCacheHolderUtil.buildStringCache(entityStoreFuture, Order.INSTRUMENT_SYMBOL, 0, Encoding.UTF_8);
        
        final CompletableFuture<FieldCache.OfString<Order>> fieldInstrumentSectorCacheFuture =
            FastCacheHolderUtil.buildStringCache(entityStoreFuture, Order.INSTRUMENT_SECTOR, 0, Encoding.UTF_8);
        
        final CompletableFuture<FieldCache.OfString<Order>> fieldInstrumentIndustryCacheFuture =
            FastCacheHolderUtil.buildStringCache(entityStoreFuture, Order.INSTRUMENT_INDUSTRY, 0, Encoding.UTF_8);
        
        final CompletableFuture<FieldCache.OfString<Order>> fieldTraderNameCacheFuture =
            FastCacheHolderUtil.buildStringCache(entityStoreFuture, Order.TRADER_NAME, 0, Encoding.UTF_8);
        
        final CompletableFuture<FieldCache.OfString<Order>> fieldTraderGroupCacheFuture =
            FastCacheHolderUtil.buildStringCache(entityStoreFuture, Order.TRADER_GROUP, 0, Encoding.UTF_8);
        
        final CompletableFuture<FieldCache.OfFloat<Order>> fieldPriceCacheFuture =
            FastCacheHolderUtil.buildFloatCache(entityStoreFuture, Order.PRICE, 0);
        
        final CompletableFuture<FieldCache.OfShort<Order>> fieldDateExecutedCacheFuture =
            FastCacheHolderUtil.buildShortCache(entityStoreFuture, Order.DATE_EXECUTED, 0);
        
        final CompletableFuture<FieldCache.OfString<Order>> fieldInstrumentNameCacheFuture =
            FastCacheHolderUtil.buildStringCache(entityStoreFuture, Order.INSTRUMENT_NAME, 0, Encoding.UTF_8);
        
        return entityStoreFuture.thenApplyAsync(entityStore -> {
            try {
                return new GeneratedOrderCacheHolder(
                    entityStore,
                    fieldIdCacheFuture.get(),
                    fieldDateCreatedCacheFuture.get(),
                    fieldDirectionCacheFuture.get(),
                    fieldOrderTypeCacheFuture.get(),
                    fieldQuantityCacheFuture.get(),
                    fieldStatusCacheFuture.get(),
                    fieldLimitPriceCacheFuture.get(),
                    fieldInstrumentSymbolCacheFuture.get(),
                    fieldInstrumentSectorCacheFuture.get(),
                    fieldInstrumentIndustryCacheFuture.get(),
                    fieldTraderNameCacheFuture.get(),
                    fieldTraderGroupCacheFuture.get(),
                    fieldPriceCacheFuture.get(),
                    fieldDateExecutedCacheFuture.get(),
                    fieldInstrumentNameCacheFuture.get()
                );
            } catch (final ExecutionException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    
    @Override
    public void close() {
        entityStore.close();
        fieldIdCache.close();
        fieldDateCreatedCache.close();
        fieldDirectionCache.close();
        fieldOrderTypeCache.close();
        fieldQuantityCache.close();
        fieldStatusCache.close();
        fieldLimitPriceCache.close();
        fieldInstrumentSymbolCache.close();
        fieldInstrumentSectorCache.close();
        fieldInstrumentIndustryCache.close();
        fieldTraderNameCache.close();
        fieldTraderGroupCache.close();
        fieldPriceCache.close();
        fieldDateExecutedCache.close();
        fieldInstrumentNameCache.close();
    }
    
    @Override
    public Map<String, Map<Statistics, ?>> getStatistics() {
        return StatisticsInternalUtil.getStatistics(    
            this,
            TABLE_IDENTIFIER,
            Order.Identifier.ID,
            Order.Identifier.DATE_CREATED,
            Order.Identifier.DIRECTION,
            Order.Identifier.ORDER_TYPE,
            Order.Identifier.QUANTITY,
            Order.Identifier.STATUS,
            Order.Identifier.LIMIT_PRICE,
            Order.Identifier.INSTRUMENT_SYMBOL,
            Order.Identifier.INSTRUMENT_SECTOR,
            Order.Identifier.INSTRUMENT_INDUSTRY,
            Order.Identifier.TRADER_NAME,
            Order.Identifier.TRADER_GROUP,
            Order.Identifier.PRICE,
            Order.Identifier.DATE_EXECUTED,
            Order.Identifier.INSTRUMENT_NAME
        );
    }
}