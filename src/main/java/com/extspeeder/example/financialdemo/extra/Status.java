package com.extspeeder.example.financialdemo.extra;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public enum Status {

    FILLED, CANCELLED, EXPIRED, OPEN, REJECTED;
    
    public String toDatabase() {
        return name();
    }
    
    public static Status fromDatabase(String value) {
        if (value == null) return null; 
        else switch (value) {
            case "FILLED"    : return FILLED;
            case "CANCELLED" : return CANCELLED;
            case "EXPIRED"   : return EXPIRED;
            case "OPEN"      : return OPEN;
            case "REJECTED"  : return REJECTED;
            default : throw new IllegalArgumentException(
                "Unknown Status constant '" + value + "'."
            );
        }
    }
}