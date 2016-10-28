package com.extspeeder.example.financialdemo.financialdemo.db.piq.raw_position.generated;

import com.extspeeder.example.financialdemo.financialdemo.db.piq.raw_position.RawPosition;
import com.speedment.plugin.extspeeder.runtime.FieldMapper;
import com.speedment.plugin.extspeeder.runtime.servlet.ExtSpeederHttpServlet;
import javax.annotation.Generated;
import static com.speedment.encoder.JsonEncoder.jsonValue;

/**
 * The generated servlet representing an entity (for example, a row) in the
 * Table financialdemo.db0.piq.daily_position_performance.
 * <p>
 * This file has been automatically generated by Ext Speeder. Any changes
 * made to it will be overwritten.
 * 
 * @author Ext Speeder
 */
@Generated("Ext Speeder")
public class GeneratedRawPositionServlet extends ExtSpeederHttpServlet<RawPosition> {
    
    private final static long serialVersionUID = -1187945133;
    public static FieldMapper<RawPosition> FIELD_MAPPER = columnName -> {
            switch (columnName) {
                case "pnl" : return RawPosition.PNL;
                case "initiate_trading_mkt_val" : return RawPosition.INITIATE_TRADING_MKT_VAL;
                case "liquidate_trading_mkt_val" : return RawPosition.LIQUIDATE_TRADING_MKT_VAL;
                case "value_date" : return RawPosition.VALUE_DATE;
                case "trader_id" : return RawPosition.TRADER_ID;
                case "trader_name" : return RawPosition.TRADER_NAME;
                case "trader_group" : return RawPosition.TRADER_GROUP;
                case "trader_group_type" : return RawPosition.TRADER_GROUP_TYPE;
                case "instrument_id" : return RawPosition.INSTRUMENT_ID;
                case "instrument_name" : return RawPosition.INSTRUMENT_NAME;
                case "instrument_symbol" : return RawPosition.INSTRUMENT_SYMBOL;
                case "instrument_sector" : return RawPosition.INSTRUMENT_SECTOR;
                case "instrument_industry" : return RawPosition.INSTRUMENT_INDUSTRY;
                case "start_date" : return RawPosition.START_DATE;
                case "end_date" : return RawPosition.END_DATE;
                default : return null;
            }
    };
    
    @Override
    public Class<RawPosition> entityClass() {
        return RawPosition.class;
    }
    
    @Override
    public FieldMapper<RawPosition> fieldMapper() {
        return FIELD_MAPPER;
    }
    
    @Override
    public String toJson(RawPosition entity) {
        return entity == null ? "null" : new StringBuilder()
            .append('{')
            .append("\"pnl\":").append(jsonValue(entity.getPnl())).append(", ")
            .append("\"initiate_trading_mkt_val\":").append(jsonValue(entity.getInitiateTradingMktVal())).append(", ")
            .append("\"liquidate_trading_mkt_val\":").append(jsonValue(entity.getLiquidateTradingMktVal())).append(", ")
            .append("\"value_date\":").append(jsonValue(entity.getValueDate())).append(", ")
            .append("\"trader_id\":").append(jsonValue(entity.getTraderId())).append(", ")
            .append("\"trader_name\":").append(jsonValue(entity.getTraderName())).append(", ")
            .append("\"trader_group\":").append(jsonValue(entity.getTraderGroup())).append(", ")
            .append("\"trader_group_type\":").append(jsonValue(entity.getTraderGroupType())).append(", ")
            .append("\"instrument_id\":").append(jsonValue(entity.getInstrumentId())).append(", ")
            .append("\"instrument_name\":").append(jsonValue(entity.getInstrumentName())).append(", ")
            .append("\"instrument_symbol\":").append(jsonValue(entity.getInstrumentSymbol())).append(", ")
            .append("\"instrument_sector\":").append(jsonValue(entity.getInstrumentSector())).append(", ")
            .append("\"instrument_industry\":").append(jsonValue(entity.getInstrumentIndustry())).append(", ")
            .append("\"start_date\":").append(jsonValue(entity.getStartDate())).append(", ")
            .append("\"end_date\":").append(jsonValue(entity.getEndDate()))
            .append('}')
        .toString();
    }
    
    @Override
    public String getServletInfo() {
        return "Servlet for the daily_position_performance table in the piq schema.";
    }
}