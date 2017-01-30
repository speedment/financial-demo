package com.extspeeder.example.financialdemo.extra;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class OrderTypeSerializer 
extends AbstractEnumTypeSerializer<OrderType> {

    public OrderTypeSerializer() {
        super(OrderType.class, OrderType.values());
    }
}