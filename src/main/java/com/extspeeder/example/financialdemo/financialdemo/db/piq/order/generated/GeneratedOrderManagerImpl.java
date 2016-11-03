package com.extspeeder.example.financialdemo.financialdemo.db.piq.order.generated;

import com.extspeeder.example.financialdemo.extra.BuySell;
import com.extspeeder.example.financialdemo.extra.OrderType;
import com.extspeeder.example.financialdemo.extra.Status;
import com.extspeeder.example.financialdemo.financialdemo.db.piq.order.Order;
import com.extspeeder.example.financialdemo.financialdemo.db.piq.order.OrderImpl;
import com.speedment.Speedment;
import com.speedment.exception.SpeedmentException;
import com.speedment.field.FieldIdentifier;
import com.speedment.field.trait.FieldTrait;
import static com.speedment.internal.util.sql.ResultSetUtil.*;
import com.speedment.plugin.extspeeder.runtime.manager.AbstractExtSpeederSqlManager;
import com.speedment.util.tuple.Tuple1;
import com.speedment.util.tuple.Tuples;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Stream;
import javax.annotation.Generated;

/**
 * The generated base manager implementation representing an entity (for
 * example, a row) in the Table financialdemo.db0.piq.orders.
 * <p>
 * This file has been automatically generated by Ext Speeder. Any changes
 * made to it will be overwritten.
 * 
 * @author Ext Speeder
 */
@Generated("Ext Speeder")
public abstract class GeneratedOrderManagerImpl extends AbstractExtSpeederSqlManager<Order> implements GeneratedOrderManager {
    
    private final static Tuple1<Class<Long>> PRIMARY_KEY_CLASSES = Tuples.of(Long.class);
    
    protected GeneratedOrderManagerImpl(Speedment speedment) {
        super(speedment);
        setEntityMapper(this::newEntityFrom);
    }
    
    protected Order newEntityFrom(ResultSet resultSet) throws SQLException, SpeedmentException {
        final Order entity = newEmptyEntity();
        try {
            entity.setId(resultSet.getLong(1));
            entity.setDateCreated(resultSet.getInt(2));
            entity.setDirection(Order.DIRECTION.typeMapper().toJavaType(resultSet.getString(3)));
            entity.setOrderType(Order.ORDER_TYPE.typeMapper().toJavaType(resultSet.getString(4)));
            entity.setQuantity(resultSet.getInt(5));
            entity.setStatus(Order.STATUS.typeMapper().toJavaType(resultSet.getString(6)));
            entity.setLimitPrice(getDouble(resultSet, 7));
            entity.setInstrumentSymbol(resultSet.getString(8));
            entity.setInstrumentSector(resultSet.getString(9));
            entity.setInstrumentIndustry(resultSet.getString(10));
            entity.setTraderName(resultSet.getString(11));
            entity.setTraderGroup(resultSet.getString(12));
            entity.setTraderGroupType(resultSet.getString(13));
            entity.setPrice(getDouble(resultSet, 14));
            entity.setDateExecuted(getInt(resultSet, 15));
            entity.setInstrumentName(resultSet.getString(16));
        }
        catch (SQLException sqle) {
            throw new SpeedmentException(sqle);
        }
        return entity;
    }
    
    @Override
    public Order newEmptyEntity() {
        return new OrderImpl() {
            @Override
            protected Speedment speedment() {
                return speedment;
            }
        };
    }
    
    @Override
    public Object get(Order entity, FieldIdentifier<Order> identifier) {
        switch ((Order.Identifier) identifier) {
            case ID : return entity.getId();
            case DATE_CREATED : return entity.getDateCreated();
            case DIRECTION : return entity.getDirection();
            case ORDER_TYPE : return entity.getOrderType();
            case QUANTITY : return entity.getQuantity();
            case STATUS : return entity.getStatus();
            case LIMIT_PRICE : return entity.getLimitPrice().orElse(null);
            case INSTRUMENT_SYMBOL : return entity.getInstrumentSymbol();
            case INSTRUMENT_SECTOR : return entity.getInstrumentSector().orElse(null);
            case INSTRUMENT_INDUSTRY : return entity.getInstrumentIndustry().orElse(null);
            case TRADER_NAME : return entity.getTraderName();
            case TRADER_GROUP : return entity.getTraderGroup();
            case TRADER_GROUP_TYPE : return entity.getTraderGroupType();
            case PRICE : return entity.getPrice();
            case DATE_EXECUTED : return entity.getDateExecuted();
            case INSTRUMENT_NAME : return entity.getInstrumentName().orElse(null);
            default : throw new IllegalArgumentException("Unknown identifier '" + identifier + "'.");
        }
    }
    
    @Override
    public void set(Order entity, FieldIdentifier<Order> identifier, Object value) {
        switch ((Order.Identifier) identifier) {
            case ID : entity.setId((Long) value); break;
            case DATE_CREATED : entity.setDateCreated((Integer) value); break;
            case DIRECTION : entity.setDirection((BuySell) value); break;
            case ORDER_TYPE : entity.setOrderType((OrderType) value); break;
            case QUANTITY : entity.setQuantity((Integer) value); break;
            case STATUS : entity.setStatus((Status) value); break;
            case LIMIT_PRICE : entity.setLimitPrice((Double) value); break;
            case INSTRUMENT_SYMBOL : entity.setInstrumentSymbol((String) value); break;
            case INSTRUMENT_SECTOR : entity.setInstrumentSector((String) value); break;
            case INSTRUMENT_INDUSTRY : entity.setInstrumentIndustry((String) value); break;
            case TRADER_NAME : entity.setTraderName((String) value); break;
            case TRADER_GROUP : entity.setTraderGroup((String) value); break;
            case TRADER_GROUP_TYPE : entity.setTraderGroupType((String) value); break;
            case PRICE : entity.setPrice((Double) value); break;
            case DATE_EXECUTED : entity.setDateExecuted((Integer) value); break;
            case INSTRUMENT_NAME : entity.setInstrumentName((String) value); break;
            default : throw new IllegalArgumentException("Unknown identifier '" + identifier + "'.");
        }
    }
    
    @Override
    public Stream<FieldTrait> fields() {
        return Stream.of(
            Order.ID,
            Order.DATE_CREATED,
            Order.DIRECTION,
            Order.ORDER_TYPE,
            Order.QUANTITY,
            Order.STATUS,
            Order.LIMIT_PRICE,
            Order.INSTRUMENT_SYMBOL,
            Order.INSTRUMENT_SECTOR,
            Order.INSTRUMENT_INDUSTRY,
            Order.TRADER_NAME,
            Order.TRADER_GROUP,
            Order.TRADER_GROUP_TYPE,
            Order.PRICE,
            Order.DATE_EXECUTED,
            Order.INSTRUMENT_NAME
        );
    }
    
    @Override
    public Stream<FieldTrait> primaryKeyFields() {
        return Stream.of(
            Order.ID
        );
    }
    
    @Override
    public Tuple1<Class<Long>> getPrimaryKeyClasses() {
        return PRIMARY_KEY_CLASSES;
    }
    
    @Override
    public Order newCopyOf(Order source) {
        final Order copy = new OrderImpl() {
            @Override
            protected final Speedment speedment() {
                return speedment;
            }
        };
        
        copy.setId(source.getId());
        copy.setDateCreated(source.getDateCreated());
        copy.setDirection(source.getDirection());
        copy.setOrderType(source.getOrderType());
        copy.setQuantity(source.getQuantity());
        copy.setStatus(source.getStatus());
        source.getLimitPrice().ifPresent(copy::setLimitPrice);
        
        return copy;
    }
}