package com.extspeeder.example.financialdemo.financialdemo.db.piq.order;

import com.extspeeder.example.financialdemo.financialdemo.db.piq.order.generated.GeneratedOrderSerializerImpl;
import com.speedment.manager.Manager;

/**
 * A {@link org.mapdb.Serializer} class for table {@link
 * com.speedment.config.db.Table} named orders. representing an entity (for
 * example, a row) in the Table financialdemo.db0.piq.orders.
 * <p>
 * This file is safe to edit. It will not be overwritten by the code
 * generator.
 * 
 * @author Speedment
 */
public final class OrderSerializerImpl extends GeneratedOrderSerializerImpl {
    
    private final static long serialVersionUID = GeneratedOrderSerializerImpl.serialVersionUID + 1;
    
    public OrderSerializerImpl(final Manager<Order> manager) {
        super(manager);
    }
}