package com.extspeeder.example.financialdemo.financialdemo.db.piq.raw_position;

import com.extspeeder.example.financialdemo.financialdemo.db.piq.raw_position.generated.GeneratedRawPosition;

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
    
    default String getInstrumentNameUnwrapped() {
        return getInstrumentName().orElse(null);
    }
    
    default String getInstrumentSectorUnwrapped() {
        return getInstrumentSector().orElse(null);
    }
    
    default String getInstrumentIndustryUnwrapped() {
        return getInstrumentIndustry().orElse(null);
    }
    
}