package com.extspeeder.example.financialdemo.financialdemo.db.piq.raw_position;

import com.extspeeder.example.financialdemo.financialdemo.db.piq.raw_position.generated.GeneratedRawPosition;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * An interface representing an entity (for example, a row) in the Table
 * financialdemo.db0.piq.daily_position_performance.
 * <p>
 * This file is safe to edit. It will not be overwritten by the code
 * generator.
 * 
 * @author Speedment
 */
public interface RawPosition extends GeneratedRawPosition {
    
    DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
    
    String getInstrumentNameUnwrapped();
    
    String getInstrumentNameOrEmpty();
    
    String getInstrumentSectorOrEmpty();
    
    String getInstrumentIndustryOrEmpty();
    
    String getValueDateAsString();
    
}