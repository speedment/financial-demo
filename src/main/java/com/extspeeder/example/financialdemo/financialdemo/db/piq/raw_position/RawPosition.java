package com.extspeeder.example.financialdemo.financialdemo.db.piq.raw_position;

import com.extspeeder.example.financialdemo.financialdemo.db.piq.raw_position.generated.GeneratedRawPosition;
import com.speedment.config.db.mapper.identity.StringIdentityMapper;
import com.speedment.field.StringField;
import com.speedment.plugin.extspeeder.runtime.field.VirtualStringField;

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
    
    final StringField<RawPosition, String> INSTRUMENT_NAME = new VirtualStringField<>(Identifier.INSTRUMENT_NAME, o -> o.getInstrumentNameOrEmpty(), new StringIdentityMapper(), false);
    final StringField<RawPosition, String> INSTRUMENT_SECTOR = new VirtualStringField<>(Identifier.INSTRUMENT_SECTOR, o -> o.getInstrumentSectorOrEmpty(), new StringIdentityMapper(), false);
    final StringField<RawPosition, String> INSTRUMENT_INDUSTRY = new VirtualStringField<>(Identifier.INSTRUMENT_INDUSTRY, o -> o.getInstrumentIndustryOrEmpty(), new StringIdentityMapper(), false);
    
    String getInstrumentNameUnwrapped();
    
    String getInstrumentNameOrEmpty();
    
    String getInstrumentSectorOrEmpty();
    
    String getInstrumentIndustryOrEmpty();
    
    String getValueDateAsString();
    
}