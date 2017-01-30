package com.extspeeder.example.financialdemo.extra;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class BuySellSerializer 
extends AbstractEnumTypeSerializer<BuySell> {

    public BuySellSerializer() {
        super(BuySell.class, BuySell.values());
    }
}