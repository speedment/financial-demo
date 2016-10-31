package com.extspeeder.example.financialdemo.extra;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public enum BuySell {
    
    BUY, SELL;
    
    public String toDatabase() {
        return name();
    }
    
    public static BuySell fromDatabase(String value) {
        switch (value) {
            case "BUY" : return BUY;
            case "SELL" : return SELL;
            default : throw new IllegalArgumentException(
                "Unknown BuySell constant '" + value + "'."
            );
        }
    }
}