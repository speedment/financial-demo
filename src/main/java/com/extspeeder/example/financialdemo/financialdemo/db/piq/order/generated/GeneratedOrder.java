package com.extspeeder.example.financialdemo.financialdemo.db.piq.order.generated;

import com.extspeeder.example.financialdemo.extra.BuySell;
import com.extspeeder.example.financialdemo.extra.BuySellMapper;
import com.extspeeder.example.financialdemo.extra.OrderType;
import com.extspeeder.example.financialdemo.extra.OrderTypeMapper;
import com.extspeeder.example.financialdemo.extra.Status;
import com.extspeeder.example.financialdemo.extra.StatusMapper;
import com.extspeeder.example.financialdemo.financialdemo.db.piq.order.Order;
import com.speedment.Entity;
import com.speedment.config.db.mapper.identity.DoubleIdentityMapper;
import com.speedment.config.db.mapper.identity.IntegerIdentityMapper;
import com.speedment.config.db.mapper.identity.LongIdentityMapper;
import com.speedment.config.db.mapper.identity.StringIdentityMapper;
import com.speedment.config.db.mapper.time.TimestampToIntMapper;
import com.speedment.field.ComparableField;
import com.speedment.field.FieldIdentifier;
import com.speedment.field.StringField;
import com.speedment.internal.core.field.ComparableFieldImpl;
import com.speedment.plugin.extspeeder.runtime.field.VirtualComparableField;
import com.speedment.plugin.extspeeder.runtime.field.VirtualStringField;
import java.sql.Timestamp;
import java.util.Optional;
import javax.annotation.Generated;

/**
 * The generated base interface representing an entity (for example, a row)
 * in the Table financialdemo.db0.piq.orders.
 * <p>
 * This file has been automatically generated by Ext Speeder. Any changes
 * made to it will be overwritten.
 * 
 * @author Ext Speeder
 */
@Generated("Ext Speeder")
public interface GeneratedOrder extends Entity<Order> {
    
    /**
     * A field representation of the virtual field 'instrument_symbol' derived
     * from the column 'symbol' in table 'instrument'.
     */
    final StringField<Order, String> INSTRUMENT_SYMBOL = new VirtualStringField<>(Identifier.INSTRUMENT_SYMBOL, Order::getInstrumentSymbol, new StringIdentityMapper(), false);
    /**
     * A field representation of the virtual field 'instrument_sector' derived
     * from the column 'sector' in table 'instrument'.
     */
    final StringField<Order, String> INSTRUMENT_SECTOR = new VirtualStringField<>(Identifier.INSTRUMENT_SECTOR, o -> o.getInstrumentSector().orElse(null), new StringIdentityMapper(), false);
    /**
     * A field representation of the virtual field 'instrument_industry' derived
     * from the column 'industry' in table 'instrument'.
     */
    final StringField<Order, String> INSTRUMENT_INDUSTRY = new VirtualStringField<>(Identifier.INSTRUMENT_INDUSTRY, o -> o.getInstrumentIndustry().orElse(null), new StringIdentityMapper(), false);
    /**
     * A field representation of the virtual field 'trader_name' derived from the
     * column 'name' in table 'trader'.
     */
    final StringField<Order, String> TRADER_NAME = new VirtualStringField<>(Identifier.TRADER_NAME, Order::getTraderName, new StringIdentityMapper(), false);
    /**
     * A field representation of the virtual field 'trader_group' derived from
     * the column 'name' in table 'cohort'.
     */
    final StringField<Order, String> TRADER_GROUP = new VirtualStringField<>(Identifier.TRADER_GROUP, Order::getTraderGroup, new StringIdentityMapper(), false);
    /**
     * A field representation of the virtual field 'trader_group_type' derived
     * from the column 'cohort_type' in table 'cohort'.
     */
    final StringField<Order, String> TRADER_GROUP_TYPE = new VirtualStringField<>(Identifier.TRADER_GROUP_TYPE, Order::getTraderGroupType, new StringIdentityMapper(), false);
    /**
     * A field representation of the virtual field 'price' derived from the
     * column 'price' in table 'execution'.
     */
    final ComparableField<Order, Double, Double> PRICE = new VirtualComparableField<>(Identifier.PRICE, Order::getPrice, new DoubleIdentityMapper(), false);
    /**
     * A field representation of the virtual field 'date_executed' derived from
     * the column 'transact_time' in table 'execution'.
     */
    final ComparableField<Order, Timestamp, Integer> DATE_EXECUTED = new VirtualComparableField<>(Identifier.DATE_EXECUTED, Order::getDateExecuted, new TimestampToIntMapper(), false);
    /**
     * A field representation of the virtual field 'instrument_name' derived from
     * the column 'name' in table 'instrument'.
     */
    final StringField<Order, String> INSTRUMENT_NAME = new VirtualStringField<>(Identifier.INSTRUMENT_NAME, o -> o.getInstrumentName().orElse(null), new StringIdentityMapper(), false);
    /**
     * This Field corresponds to the {@link Order} field that can be obtained
     * using the {@link Order#getId()} method.
     */
    final ComparableField<Order, Long, Long> ID = new ComparableFieldImpl<>(Identifier.ID, Order::getId, Order::setId, new LongIdentityMapper(), true);
    /**
     * This Field corresponds to the {@link Order} field that can be obtained
     * using the {@link Order#getDateCreated()} method.
     */
    final ComparableField<Order, Timestamp, Integer> DATE_CREATED = new ComparableFieldImpl<>(Identifier.DATE_CREATED, Order::getDateCreated, Order::setDateCreated, new TimestampToIntMapper(), false);
    /**
     * This Field corresponds to the {@link Order} field that can be obtained
     * using the {@link Order#getDirection()} method.
     */
    final ComparableField<Order, String, BuySell> DIRECTION = new ComparableFieldImpl<>(Identifier.DIRECTION, Order::getDirection, Order::setDirection, new BuySellMapper(), false);
    /**
     * This Field corresponds to the {@link Order} field that can be obtained
     * using the {@link Order#getOrderType()} method.
     */
    final ComparableField<Order, String, OrderType> ORDER_TYPE = new ComparableFieldImpl<>(Identifier.ORDER_TYPE, Order::getOrderType, Order::setOrderType, new OrderTypeMapper(), false);
    /**
     * This Field corresponds to the {@link Order} field that can be obtained
     * using the {@link Order#getQuantity()} method.
     */
    final ComparableField<Order, Integer, Integer> QUANTITY = new ComparableFieldImpl<>(Identifier.QUANTITY, Order::getQuantity, Order::setQuantity, new IntegerIdentityMapper(), false);
    /**
     * This Field corresponds to the {@link Order} field that can be obtained
     * using the {@link Order#getStatus()} method.
     */
    final ComparableField<Order, String, Status> STATUS = new ComparableFieldImpl<>(Identifier.STATUS, Order::getStatus, Order::setStatus, new StatusMapper(), false);
    /**
     * This Field corresponds to the {@link Order} field that can be obtained
     * using the {@link Order#getLimitPrice()} method.
     */
    final ComparableField<Order, Double, Double> LIMIT_PRICE = new ComparableFieldImpl<>(Identifier.LIMIT_PRICE, o -> o.getLimitPrice().orElse(null), Order::setLimitPrice, new DoubleIdentityMapper(), false);
    
    /**
     * Returns the virtual value of column 'symbol' in foreign table
     * 'instrument'.
     * 
     * @return the virtual value InstrumentSymbol
     */
    String getInstrumentSymbol();
    
    /**
     * Sets the internal value of virtual column 'symbol'. This will not affect
     * the database value.
     * 
     * @param instrumentSymbol the virtual value
     */
    void setInstrumentSymbol(String instrumentSymbol);
    
    /**
     * Returns the virtual value of column 'sector' in foreign table
     * 'instrument'.
     * 
     * @return the virtual value InstrumentSector
     */
    Optional<String> getInstrumentSector();
    
    /**
     * Sets the internal value of virtual column 'sector'. This will not affect
     * the database value.
     * 
     * @param instrumentSector the virtual value
     */
    void setInstrumentSector(String instrumentSector);
    
    /**
     * Returns the virtual value of column 'industry' in foreign table
     * 'instrument'.
     * 
     * @return the virtual value InstrumentIndustry
     */
    Optional<String> getInstrumentIndustry();
    
    /**
     * Sets the internal value of virtual column 'industry'. This will not affect
     * the database value.
     * 
     * @param instrumentIndustry the virtual value
     */
    void setInstrumentIndustry(String instrumentIndustry);
    
    /**
     * Returns the virtual value of column 'name' in foreign table 'trader'.
     * 
     * @return the virtual value TraderName
     */
    String getTraderName();
    
    /**
     * Sets the internal value of virtual column 'name'. This will not affect the
     * database value.
     * 
     * @param traderName the virtual value
     */
    void setTraderName(String traderName);
    
    /**
     * Returns the virtual value of column 'name' in foreign table 'cohort'.
     * 
     * @return the virtual value TraderGroup
     */
    String getTraderGroup();
    
    /**
     * Sets the internal value of virtual column 'name'. This will not affect the
     * database value.
     * 
     * @param traderGroup the virtual value
     */
    void setTraderGroup(String traderGroup);
    
    /**
     * Returns the virtual value of column 'cohort_type' in foreign table
     * 'cohort'.
     * 
     * @return the virtual value TraderGroupType
     */
    String getTraderGroupType();
    
    /**
     * Sets the internal value of virtual column 'cohort_type'. This will not
     * affect the database value.
     * 
     * @param traderGroupType the virtual value
     */
    void setTraderGroupType(String traderGroupType);
    
    /**
     * Returns the virtual value of column 'price' in foreign table 'execution'.
     * 
     * @return the virtual value Price
     */
    Double getPrice();
    
    /**
     * Sets the internal value of virtual column 'price'. This will not affect
     * the database value.
     * 
     * @param price the virtual value
     */
    void setPrice(Double price);
    
    /**
     * Returns the virtual value of column 'transact_time' in foreign table
     * 'execution'.
     * 
     * @return the virtual value DateExecuted
     */
    Integer getDateExecuted();
    
    /**
     * Sets the internal value of virtual column 'transact_time'. This will not
     * affect the database value.
     * 
     * @param dateExecuted the virtual value
     */
    void setDateExecuted(Integer dateExecuted);
    
    /**
     * Returns the virtual value of column 'name' in foreign table 'instrument'.
     * 
     * @return the virtual value InstrumentName
     */
    Optional<String> getInstrumentName();
    
    /**
     * Sets the internal value of virtual column 'name'. This will not affect the
     * database value.
     * 
     * @param instrumentName the virtual value
     */
    void setInstrumentName(String instrumentName);
    
    /**
     * Returns the id of this Order. The id field corresponds to the database
     * column db0.piq.orders.id.
     * 
     * @return the id of this Order
     */
    Long getId();
    
    /**
     * Returns the dateCreated of this Order. The dateCreated field corresponds
     * to the database column db0.piq.orders.date_created.
     * 
     * @return the dateCreated of this Order
     */
    Integer getDateCreated();
    
    /**
     * Returns the direction of this Order. The direction field corresponds to
     * the database column db0.piq.orders.direction.
     * 
     * @return the direction of this Order
     */
    BuySell getDirection();
    
    /**
     * Returns the orderType of this Order. The orderType field corresponds to
     * the database column db0.piq.orders.order_type.
     * 
     * @return the orderType of this Order
     */
    OrderType getOrderType();
    
    /**
     * Returns the quantity of this Order. The quantity field corresponds to the
     * database column db0.piq.orders.quantity.
     * 
     * @return the quantity of this Order
     */
    Integer getQuantity();
    
    /**
     * Returns the status of this Order. The status field corresponds to the
     * database column db0.piq.orders.status.
     * 
     * @return the status of this Order
     */
    Status getStatus();
    
    /**
     * Returns the limitPrice of this Order. The limitPrice field corresponds to
     * the database column db0.piq.orders.limit_price.
     * 
     * @return the limitPrice of this Order
     */
    Optional<Double> getLimitPrice();
    
    /**
     * Sets the id of this Order. The id field corresponds to the database column
     * db0.piq.orders.id.
     * 
     * @param id to set of this Order
     * @return this Order instance
     */
    Order setId(Long id);
    
    /**
     * Sets the dateCreated of this Order. The dateCreated field corresponds to
     * the database column db0.piq.orders.date_created.
     * 
     * @param dateCreated to set of this Order
     * @return this Order instance
     */
    Order setDateCreated(Integer dateCreated);
    
    /**
     * Sets the direction of this Order. The direction field corresponds to the
     * database column db0.piq.orders.direction.
     * 
     * @param direction to set of this Order
     * @return this Order instance
     */
    Order setDirection(BuySell direction);
    
    /**
     * Sets the orderType of this Order. The orderType field corresponds to the
     * database column db0.piq.orders.order_type.
     * 
     * @param orderType to set of this Order
     * @return this Order instance
     */
    Order setOrderType(OrderType orderType);
    
    /**
     * Sets the quantity of this Order. The quantity field corresponds to the
     * database column db0.piq.orders.quantity.
     * 
     * @param quantity to set of this Order
     * @return this Order instance
     */
    Order setQuantity(Integer quantity);
    
    /**
     * Sets the status of this Order. The status field corresponds to the
     * database column db0.piq.orders.status.
     * 
     * @param status to set of this Order
     * @return this Order instance
     */
    Order setStatus(Status status);
    
    /**
     * Sets the limitPrice of this Order. The limitPrice field corresponds to the
     * database column db0.piq.orders.limit_price.
     * 
     * @param limitPrice to set of this Order
     * @return this Order instance
     */
    Order setLimitPrice(Double limitPrice);
    
    enum Identifier implements FieldIdentifier<Order> {
        
        ID ("id"),
        DATE_CREATED ("date_created"),
        DIRECTION ("direction"),
        ORDER_TYPE ("order_type"),
        QUANTITY ("quantity"),
        STATUS ("status"),
        LIMIT_PRICE ("limit_price"),
        INSTRUMENT_SYMBOL ("instrument_symbol"),
        INSTRUMENT_SECTOR ("instrument_sector"),
        INSTRUMENT_INDUSTRY ("instrument_industry"),
        TRADER_NAME ("trader_name"),
        TRADER_GROUP ("trader_group"),
        TRADER_GROUP_TYPE ("trader_group_type"),
        PRICE ("price"),
        DATE_EXECUTED ("date_executed"),
        INSTRUMENT_NAME ("instrument_name");
        
        private final String columnName;
        
        Identifier(String columnName) {
            this.columnName = columnName;
        }
        
        @Override
        public String dbmsName() {
            return "db0";
        }
        
        @Override
        public String schemaName() {
            return "piq";
        }
        
        @Override
        public String tableName() {
            return "orders";
        }
        
        @Override
        public String columnName() {
            return this.columnName;
        }
    }
}