package com.extspeeder.example.financialdemo.financialdemo.db.piq.raw_position;

import com.extspeeder.example.financialdemo.financialdemo.db.piq.raw_position.generated.GeneratedRawPositionImpl;

/**
 * An implementation representing an entity (for example, a row) in the Table
 * financialdemo.db0.piq.daily_position_performance.
 * <p>
 * This file is safe to edit. It will not be overwritten by the code
 * generator.
 * 
 * @author Speedment
 */
public abstract class RawPositionImpl extends GeneratedRawPositionImpl implements RawPosition {
    
    @Override
    public String getInstrumentNameUnwrapped() {
        return instrumentName;
    }
    
}