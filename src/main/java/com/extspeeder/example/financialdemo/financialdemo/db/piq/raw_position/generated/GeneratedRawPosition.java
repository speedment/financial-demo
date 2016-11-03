package com.extspeeder.example.financialdemo.financialdemo.db.piq.raw_position.generated;

import com.extspeeder.example.financialdemo.financialdemo.db.piq.raw_position.RawPosition;
import com.speedment.Entity;
import com.speedment.config.db.mapper.identity.DoubleIdentityMapper;
import com.speedment.config.db.mapper.identity.IntegerIdentityMapper;
import com.speedment.config.db.mapper.identity.LongIdentityMapper;
import com.speedment.config.db.mapper.identity.StringIdentityMapper;
import com.speedment.field.ComparableField;
import com.speedment.field.FieldIdentifier;
import com.speedment.field.StringField;
import com.speedment.internal.core.field.ComparableFieldImpl;
import com.speedment.plugin.extspeeder.runtime.field.VirtualStringField;
import java.util.Optional;
import javax.annotation.Generated;

/**
 * The generated base interface representing an entity (for example, a row)
 * in the Table financialdemo.db0.piq.daily_position_performance.
 * <p>
 * This file has been automatically generated by Ext Speeder. Any changes
 * made to it will be overwritten.
 * 
 * @author Ext Speeder
 */
@Generated("Ext Speeder")
public interface GeneratedRawPosition extends Entity<RawPosition> {
    
    /**
     * A field representation of the virtual field 'trader_name' derived from the
     * column 'name' in table 'trader'.
     */
    final StringField<RawPosition, String> TRADER_NAME = new VirtualStringField<>(Identifier.TRADER_NAME, RawPosition::getTraderName, new StringIdentityMapper(), false);
    /**
     * A field representation of the virtual field 'trader_group' derived from
     * the column 'name' in table 'cohort'.
     */
    final StringField<RawPosition, String> TRADER_GROUP = new VirtualStringField<>(Identifier.TRADER_GROUP, RawPosition::getTraderGroup, new StringIdentityMapper(), false);
    /**
     * A field representation of the virtual field 'trader_group_type' derived
     * from the column 'cohort_type' in table 'cohort'.
     */
    final StringField<RawPosition, String> TRADER_GROUP_TYPE = new VirtualStringField<>(Identifier.TRADER_GROUP_TYPE, RawPosition::getTraderGroupType, new StringIdentityMapper(), false);
    /**
     * A field representation of the virtual field 'instrument_name' derived from
     * the column 'name' in table 'instrument'.
     */
    final StringField<RawPosition, String> INSTRUMENT_NAME = new VirtualStringField<>(Identifier.INSTRUMENT_NAME, o -> o.getInstrumentName().orElse(null), new StringIdentityMapper(), false);
    /**
     * A field representation of the virtual field 'instrument_symbol' derived
     * from the column 'symbol' in table 'instrument'.
     */
    final StringField<RawPosition, String> INSTRUMENT_SYMBOL = new VirtualStringField<>(Identifier.INSTRUMENT_SYMBOL, RawPosition::getInstrumentSymbol, new StringIdentityMapper(), false);
    /**
     * A field representation of the virtual field 'instrument_sector' derived
     * from the column 'sector' in table 'instrument'.
     */
    final StringField<RawPosition, String> INSTRUMENT_SECTOR = new VirtualStringField<>(Identifier.INSTRUMENT_SECTOR, o -> o.getInstrumentSector().orElse(null), new StringIdentityMapper(), false);
    /**
     * A field representation of the virtual field 'instrument_industry' derived
     * from the column 'industry' in table 'instrument'.
     */
    final StringField<RawPosition, String> INSTRUMENT_INDUSTRY = new VirtualStringField<>(Identifier.INSTRUMENT_INDUSTRY, o -> o.getInstrumentIndustry().orElse(null), new StringIdentityMapper(), false);
    /**
     * This Field corresponds to the {@link RawPosition} field that can be
     * obtained using the {@link RawPosition#getId()} method.
     */
    final ComparableField<RawPosition, Long, Long> ID = new ComparableFieldImpl<>(Identifier.ID, RawPosition::getId, RawPosition::setId, new LongIdentityMapper(), true);
    /**
     * This Field corresponds to the {@link RawPosition} field that can be
     * obtained using the {@link RawPosition#getPnl()} method.
     */
    final ComparableField<RawPosition, Double, Double> PNL = new ComparableFieldImpl<>(Identifier.PNL, RawPosition::getPnl, RawPosition::setPnl, new DoubleIdentityMapper(), false);
    /**
     * This Field corresponds to the {@link RawPosition} field that can be
     * obtained using the {@link RawPosition#getInitiateTradingMktVal()} method.
     */
    final ComparableField<RawPosition, Double, Double> INITIATE_TRADING_MKT_VAL = new ComparableFieldImpl<>(Identifier.INITIATE_TRADING_MKT_VAL, RawPosition::getInitiateTradingMktVal, RawPosition::setInitiateTradingMktVal, new DoubleIdentityMapper(), false);
    /**
     * This Field corresponds to the {@link RawPosition} field that can be
     * obtained using the {@link RawPosition#getLiquidateTradingMktVal()} method.
     */
    final ComparableField<RawPosition, Double, Double> LIQUIDATE_TRADING_MKT_VAL = new ComparableFieldImpl<>(Identifier.LIQUIDATE_TRADING_MKT_VAL, RawPosition::getLiquidateTradingMktVal, RawPosition::setLiquidateTradingMktVal, new DoubleIdentityMapper(), false);
    /**
     * This Field corresponds to the {@link RawPosition} field that can be
     * obtained using the {@link RawPosition#getValueDate()} method.
     */
    final ComparableField<RawPosition, Integer, Integer> VALUE_DATE = new ComparableFieldImpl<>(Identifier.VALUE_DATE, RawPosition::getValueDate, RawPosition::setValueDate, new IntegerIdentityMapper(), false);
    
    /**
     * Returns the virtual value of column 'name' in foreign table 'trader'.
     * 
     * @return the virtual value TraderName
     */
    String getTraderName();
    
    /**
     * Sets the internal value of virtual column 'name'. This will not affect the
     * database value.
     * 
     * @param traderName the virtual value
     */
    void setTraderName(String traderName);
    
    /**
     * Returns the virtual value of column 'name' in foreign table 'cohort'.
     * 
     * @return the virtual value TraderGroup
     */
    String getTraderGroup();
    
    /**
     * Sets the internal value of virtual column 'name'. This will not affect the
     * database value.
     * 
     * @param traderGroup the virtual value
     */
    void setTraderGroup(String traderGroup);
    
    /**
     * Returns the virtual value of column 'cohort_type' in foreign table
     * 'cohort'.
     * 
     * @return the virtual value TraderGroupType
     */
    String getTraderGroupType();
    
    /**
     * Sets the internal value of virtual column 'cohort_type'. This will not
     * affect the database value.
     * 
     * @param traderGroupType the virtual value
     */
    void setTraderGroupType(String traderGroupType);
    
    /**
     * Returns the virtual value of column 'name' in foreign table 'instrument'.
     * 
     * @return the virtual value InstrumentName
     */
    Optional<String> getInstrumentName();
    
    /**
     * Sets the internal value of virtual column 'name'. This will not affect the
     * database value.
     * 
     * @param instrumentName the virtual value
     */
    void setInstrumentName(String instrumentName);
    
    /**
     * Returns the virtual value of column 'symbol' in foreign table
     * 'instrument'.
     * 
     * @return the virtual value InstrumentSymbol
     */
    String getInstrumentSymbol();
    
    /**
     * Sets the internal value of virtual column 'symbol'. This will not affect
     * the database value.
     * 
     * @param instrumentSymbol the virtual value
     */
    void setInstrumentSymbol(String instrumentSymbol);
    
    /**
     * Returns the virtual value of column 'sector' in foreign table
     * 'instrument'.
     * 
     * @return the virtual value InstrumentSector
     */
    Optional<String> getInstrumentSector();
    
    /**
     * Sets the internal value of virtual column 'sector'. This will not affect
     * the database value.
     * 
     * @param instrumentSector the virtual value
     */
    void setInstrumentSector(String instrumentSector);
    
    /**
     * Returns the virtual value of column 'industry' in foreign table
     * 'instrument'.
     * 
     * @return the virtual value InstrumentIndustry
     */
    Optional<String> getInstrumentIndustry();
    
    /**
     * Sets the internal value of virtual column 'industry'. This will not affect
     * the database value.
     * 
     * @param instrumentIndustry the virtual value
     */
    void setInstrumentIndustry(String instrumentIndustry);
    
    /**
     * Returns the id of this RawPosition. The id field corresponds to the
     * database column db0.piq.daily_position_performance.id.
     * 
     * @return the id of this RawPosition
     */
    Long getId();
    
    /**
     * Returns the pnl of this RawPosition. The pnl field corresponds to the
     * database column db0.piq.daily_position_performance.pnl.
     * 
     * @return the pnl of this RawPosition
     */
    Double getPnl();
    
    /**
     * Returns the initiateTradingMktVal of this RawPosition. The
     * initiateTradingMktVal field corresponds to the database column
     * db0.piq.daily_position_performance.total_initiate_mkt_val.
     * 
     * @return the initiateTradingMktVal of this RawPosition
     */
    Double getInitiateTradingMktVal();
    
    /**
     * Returns the liquidateTradingMktVal of this RawPosition. The
     * liquidateTradingMktVal field corresponds to the database column
     * db0.piq.daily_position_performance.total_liquidate_mkt_val.
     * 
     * @return the liquidateTradingMktVal of this RawPosition
     */
    Double getLiquidateTradingMktVal();
    
    /**
     * Returns the valueDate of this RawPosition. The valueDate field corresponds
     * to the database column db0.piq.daily_position_performance.value_date_int.
     * 
     * @return the valueDate of this RawPosition
     */
    Integer getValueDate();
    
    /**
     * Sets the id of this RawPosition. The id field corresponds to the database
     * column db0.piq.daily_position_performance.id.
     * 
     * @param id to set of this RawPosition
     * @return this RawPosition instance
     */
    RawPosition setId(Long id);
    
    /**
     * Sets the pnl of this RawPosition. The pnl field corresponds to the
     * database column db0.piq.daily_position_performance.pnl.
     * 
     * @param pnl to set of this RawPosition
     * @return this RawPosition instance
     */
    RawPosition setPnl(Double pnl);
    
    /**
     * Sets the initiateTradingMktVal of this RawPosition. The
     * initiateTradingMktVal field corresponds to the database column
     * db0.piq.daily_position_performance.total_initiate_mkt_val.
     * 
     * @param initiateTradingMktVal to set of this RawPosition
     * @return this RawPosition instance
     */
    RawPosition setInitiateTradingMktVal(Double initiateTradingMktVal);
    
    /**
     * Sets the liquidateTradingMktVal of this RawPosition. The
     * liquidateTradingMktVal field corresponds to the database column
     * db0.piq.daily_position_performance.total_liquidate_mkt_val.
     * 
     * @param liquidateTradingMktVal to set of this RawPosition
     * @return this RawPosition instance
     */
    RawPosition setLiquidateTradingMktVal(Double liquidateTradingMktVal);
    
    /**
     * Sets the valueDate of this RawPosition. The valueDate field corresponds to
     * the database column db0.piq.daily_position_performance.value_date_int.
     * 
     * @param valueDate to set of this RawPosition
     * @return this RawPosition instance
     */
    RawPosition setValueDate(Integer valueDate);
    
    enum Identifier implements FieldIdentifier<RawPosition> {
        
        ID ("id"),
        PNL ("pnl"),
        INITIATE_TRADING_MKT_VAL ("total_initiate_mkt_val"),
        LIQUIDATE_TRADING_MKT_VAL ("total_liquidate_mkt_val"),
        VALUE_DATE ("value_date_int"),
        TRADER_NAME ("trader_name"),
        TRADER_GROUP ("trader_group"),
        TRADER_GROUP_TYPE ("trader_group_type"),
        INSTRUMENT_NAME ("instrument_name"),
        INSTRUMENT_SYMBOL ("instrument_symbol"),
        INSTRUMENT_SECTOR ("instrument_sector"),
        INSTRUMENT_INDUSTRY ("instrument_industry");
        
        private final String columnName;
        
        Identifier(String columnName) {
            this.columnName = columnName;
        }
        
        @Override
        public String dbmsName() {
            return "db0";
        }
        
        @Override
        public String schemaName() {
            return "piq";
        }
        
        @Override
        public String tableName() {
            return "daily_position_performance";
        }
        
        @Override
        public String columnName() {
            return this.columnName;
        }
    }
}