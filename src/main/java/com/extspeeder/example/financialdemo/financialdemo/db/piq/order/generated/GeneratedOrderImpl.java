package com.extspeeder.example.financialdemo.financialdemo.db.piq.order.generated;

import com.extspeeder.example.financialdemo.extra.BuySell;
import com.extspeeder.example.financialdemo.extra.OrderType;
import com.extspeeder.example.financialdemo.extra.Status;
import com.extspeeder.example.financialdemo.financialdemo.db.piq.order.Order;
import com.speedment.Speedment;
import com.speedment.internal.core.code.AbstractBaseEntity;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import javax.annotation.Generated;

/**
 * The generated base implementation representing an entity (for example, a
 * row) in the Table financialdemo.db0.piq.orders.
 * <p>
 * This file has been automatically generated by Ext Speeder. Any changes
 * made to it will be overwritten.
 * 
 * @author Ext Speeder
 */
@Generated("Ext Speeder")
public abstract class GeneratedOrderImpl extends AbstractBaseEntity<Order> implements Order {
    
    private String instrumentSymbol;
    private String instrumentSector;
    private String instrumentIndustry;
    private String traderName;
    private String traderGroup;
    private String traderGroupType;
    private Double price;
    private Integer dateExecuted;
    private Long id;
    private Integer dateCreated;
    private BuySell direction;
    private OrderType orderType;
    private Integer quantity;
    private Status status;
    
    protected GeneratedOrderImpl() {
        
    }
    
    @Override
    public String getInstrumentSymbol() {
        return instrumentSymbol;
    }
    
    @Override
    public void setInstrumentSymbol(String instrumentSymbol) {
        this.instrumentSymbol = instrumentSymbol;
    }
    
    @Override
    public Optional<String> getInstrumentSector() {
        return Optional.ofNullable(instrumentSector);
    }
    
    @Override
    public void setInstrumentSector(String instrumentSector) {
        this.instrumentSector = instrumentSector;
    }
    
    @Override
    public Optional<String> getInstrumentIndustry() {
        return Optional.ofNullable(instrumentIndustry);
    }
    
    @Override
    public void setInstrumentIndustry(String instrumentIndustry) {
        this.instrumentIndustry = instrumentIndustry;
    }
    
    @Override
    public String getTraderName() {
        return traderName;
    }
    
    @Override
    public void setTraderName(String traderName) {
        this.traderName = traderName;
    }
    
    @Override
    public String getTraderGroup() {
        return traderGroup;
    }
    
    @Override
    public void setTraderGroup(String traderGroup) {
        this.traderGroup = traderGroup;
    }
    
    @Override
    public String getTraderGroupType() {
        return traderGroupType;
    }
    
    @Override
    public void setTraderGroupType(String traderGroupType) {
        this.traderGroupType = traderGroupType;
    }
    
    @Override
    public Double getPrice() {
        return price;
    }
    
    @Override
    public void setPrice(Double price) {
        this.price = price;
    }
    
    @Override
    public Integer getDateExecuted() {
        return dateExecuted;
    }
    
    @Override
    public void setDateExecuted(Integer dateExecuted) {
        this.dateExecuted = dateExecuted;
    }
    
    @Override
    public Long getId() {
        return id;
    }
    
    @Override
    public Integer getDateCreated() {
        return dateCreated;
    }
    
    @Override
    public BuySell getDirection() {
        return direction;
    }
    
    @Override
    public OrderType getOrderType() {
        return orderType;
    }
    
    @Override
    public Integer getQuantity() {
        return quantity;
    }
    
    @Override
    public Status getStatus() {
        return status;
    }
    
    @Override
    public final Order setId(Long id) {
        this.id = id;
        return this;
    }
    
    @Override
    public final Order setDateCreated(Integer dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }
    
    @Override
    public final Order setDirection(BuySell direction) {
        this.direction = direction;
        return this;
    }
    
    @Override
    public final Order setOrderType(OrderType orderType) {
        this.orderType = orderType;
        return this;
    }
    
    @Override
    public final Order setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
    
    @Override
    public final Order setStatus(Status status) {
        this.status = status;
        return this;
    }
    
    @Override
    public String toString() {
        final StringJoiner sj = new StringJoiner(", ", "{ ", " }");
        sj.add("id = "+Objects.toString(getId()));
        sj.add("dateCreated = "+Objects.toString(getDateCreated()));
        sj.add("direction = "+Objects.toString(getDirection()));
        sj.add("orderType = "+Objects.toString(getOrderType()));
        sj.add("quantity = "+Objects.toString(getQuantity()));
        sj.add("status = "+Objects.toString(getStatus()));
        return "OrderImpl "+sj.toString();
    }
    
    @Override
    public boolean equals(Object that) {
        if (this == that) { return true; }
        if (!(that instanceof Order)) { return false; }
        final Order thatOrder = (Order)that;
        if (!Objects.equals(this.getId(), thatOrder.getId())) {return false; }
        if (!Objects.equals(this.getDateCreated(), thatOrder.getDateCreated())) {return false; }
        if (!Objects.equals(this.getDirection(), thatOrder.getDirection())) {return false; }
        if (!Objects.equals(this.getOrderType(), thatOrder.getOrderType())) {return false; }
        if (!Objects.equals(this.getQuantity(), thatOrder.getQuantity())) {return false; }
        if (!Objects.equals(this.getStatus(), thatOrder.getStatus())) {return false; }
        return true;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(getId());
        hash = 31 * hash + Objects.hashCode(getDateCreated());
        hash = 31 * hash + Objects.hashCode(getDirection());
        hash = 31 * hash + Objects.hashCode(getOrderType());
        hash = 31 * hash + Objects.hashCode(getQuantity());
        hash = 31 * hash + Objects.hashCode(getStatus());
        return hash;
    }
    
    @Override
    public Class<Order> entityClass() {
        return Order.class;
    }
}