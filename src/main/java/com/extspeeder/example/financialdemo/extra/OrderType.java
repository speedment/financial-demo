package com.extspeeder.example.financialdemo.extra;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public enum OrderType {
    
    MARKET, LIMIT, STOP;
    
    public String toDatabase() {
        return name();
    }
    
    public static OrderType fromDatabase(String value) {
        switch (value) {
            case "MARKET" : return MARKET;
            case "LIMIT"  : return LIMIT;
            case "STOP"   : return STOP;
            default : throw new IllegalArgumentException(
                "Unknown OrderType constant '" + value + "'."
            );
        }
    }
}