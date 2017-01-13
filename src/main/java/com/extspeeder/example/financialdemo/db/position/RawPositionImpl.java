package com.extspeeder.example.financialdemo.db.position;

import com.extspeeder.example.financialdemo.db.position.generated.GeneratedRawPositionImpl;

/**
 * The default implementation of the {@link
 * com.extspeeder.example.financialdemo.db.position.RawPosition}-interface.
 * <p>
 * This file is safe to edit. It will not be overwritten by the code generator.
 * 
 * @author Speedment
 */
public final class RawPositionImpl extends GeneratedRawPositionImpl implements RawPosition {

    @Override
    public String getInstrumentNameOrNull() {
        return instrumentName;
    }
    
    @Override
    public String getInstrumentNameOrEmpty() {
        return getInstrumentName().orElse("");
    }

    @Override
    public String getInstrumentSectorOrEmpty() {
        return getInstrumentSector().orElse("");
    }

    @Override
    public String getInstrumentIndustryOrEmpty() {
        return getInstrumentIndustry().orElse("");
    }
    
    
}