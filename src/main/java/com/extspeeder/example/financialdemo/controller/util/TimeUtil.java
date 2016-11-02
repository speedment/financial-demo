package com.extspeeder.example.financialdemo.controller.util;

import java.text.ParseException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class TimeUtil {
    
    public final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");
    
    public static int toEpochSecs(String date) throws ParseException {
        return (int) (Instant.from(DATE_FORMAT.parse(date)).getEpochSecond());
    }
    
    public static String fromEpochSecs(int epochSecs) {
        return DATE_FORMAT.format(Instant.ofEpochSecond(epochSecs));
    }
    
    public static String fromEpochMillis(long epochMillis) {
        return fromEpochSecs((int) (epochMillis / 1_000));
    }
    
    private TimeUtil() {}
}