package com.extspeeder.example.financialdemo.db.position;

import com.extspeeder.example.financialdemo.db.position.generated.GeneratedRawPosition;

/**
 * The main interface for entities of the {@code
 * daily_position_performance}-table in the database.
 * <p>
 * This file is safe to edit. It will not be overwritten by the code generator.
 * 
 * @author Speedment
 */
public interface RawPosition extends GeneratedRawPosition {
    
    String getInstrumentNameOrNull();
    
    String getInstrumentNameOrEmpty();
    
    String getInstrumentSectorOrEmpty();
    
    String getInstrumentIndustryOrEmpty();
    
}