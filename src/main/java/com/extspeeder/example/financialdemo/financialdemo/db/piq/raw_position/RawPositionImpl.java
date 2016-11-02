package com.extspeeder.example.financialdemo.financialdemo.db.piq.raw_position;

import com.extspeeder.example.financialdemo.controller.util.TimeUtil;
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
    
    @Override
    public String getInstrumentSectorOrEmpty() {
        return instrumentSector == null ? "" : instrumentSector;
    }

    @Override
    public String getInstrumentNameOrEmpty() {
        return instrumentName == null ? "" : instrumentName;
    }
    
    @Override
    public String getInstrumentIndustryOrEmpty() {
        return instrumentIndustry == null ? "" : instrumentIndustry;
    }

    @Override
    public String getValueDateAsString() {
        try {
            final Integer valueDate = getValueDate();
            if (valueDate == null) {
                return null;
            } else {
                return TimeUtil.fromEpochSecs(valueDate);
            }
        } catch (final Exception ex) {
            System.err.println(String.format(
                "Error parsing epoch second '%d' of raw position '%d' to " +
                "string.",
                getValueDate(),
                getId()
            ));
            throw ex;
        }
    }
}
