package com.extspeeder.example.financialdemo.extra;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public enum CohortType {

    SYSTEMATIC,
    DISCRETIONARY,
    INTERNAL,
    HEDGE,
    AGGRESSIVE_ALPHA;
    
    public String toDatabase() {
        return name();
    }
    
    public static CohortType fromDatabase(String value) {
        if (value == null) return null; 
        else switch (value) {
            case "Systematic"       : return SYSTEMATIC;
            case "Discretionary"    : return DISCRETIONARY;
            case "Internal"         : return INTERNAL;
            case "Hedge"            : return HEDGE;
            case "Aggressive Alpha" : return AGGRESSIVE_ALPHA;
            default : throw new IllegalArgumentException(
                "Unknown CohortType constant '" + value + "'."
            );
        }
    }
}