package com.extspeeder.example.financialdemo.financialdemo.db.piq.raw_position;

import static com.extspeeder.example.financialdemo.controller.util.TimeUtil.DATE_FORMAT;
import com.extspeeder.example.financialdemo.financialdemo.db.piq.raw_position.generated.GeneratedRawPositionImpl;
import java.time.Instant;
import java.util.Date;

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
        final Integer valueDate = getValueDate();
        if (valueDate == null) {
            return null;
        } else {
            return DATE_FORMAT.format(Date.from(Instant.ofEpochSecond(valueDate)));
        }
    }
}
