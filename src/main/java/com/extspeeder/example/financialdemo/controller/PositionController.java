package com.extspeeder.example.financialdemo.controller;

import com.extspeeder.example.financialdemo.financialdemo.db.piq.raw_position.RawPosition;
import com.extspeeder.example.financialdemo.financialdemo.db.piq.raw_position.RawPositionManager;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import static java.util.Objects.requireNonNull;
import java.util.function.Function;
import java.util.function.Predicate;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toConcurrentMap;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
@RestController("/positions")
public class PositionController {
    
    private final static String SEPARATOR  = ">>";
    private final static DateFormat FORMAT = new SimpleDateFormat("yyyyMMdd");
    
    private @Autowired RawPositionManager rawPositions;

    @RequestMapping(method = GET)
    public Collection<Result> handleGet(
            @RequestParam(name="startDate") String startDate, 
            @RequestParam(name="endDate") String endDate,
            @RequestParam(name="drillDownPath") String aGroups,
            @RequestParam(name="drillDownKey", required=false) String aKeys
    ) throws ParseException {
        
        final int iFrom = (int) (FORMAT.parse(startDate).getTime() / 1000);
        final int iTo   = (int) (FORMAT.parse(endDate).getTime() / 1000);
        final String[] groups = aGroups.split(SEPARATOR);
        
        Stream<RawPosition> positions = rawPositions.stream()
            .parallel().filter(RawPosition.VALUE_DATE.between(iFrom, iTo));
        
        final Function<RawPosition, String> classifier;
        final boolean leaf;
        
        if (aKeys == null || "root".equals(aKeys)) {
            classifier = classifier(groups[0]);
            leaf = false;
        } else {
            final String[] keys = aKeys.split(SEPARATOR);
            
            for (int i = 0; i < keys.length; i++) {
                positions = positions.filter(filter(groups[i], keys[i]));
            }
            
            if (groups.length > keys.length) {
                classifier = classifier(groups[keys.length]);
                leaf = false;
            } else {
                classifier = null;
                leaf = true;
            }
        }
        
        final ResultFactory factory = new ResultFactory(
            identifier(groups), 
            groups[groups.length - 1],
            FORMAT, leaf
        );
        
        if (classifier == null) {
            return positions.map(factory::createFrom).collect(toList());
        } else {
            return positions.collect(toConcurrentMap(
                classifier, 
                factory::createFrom, 
                Result::aggregate
            )).values();
        }
    }
    
    private static Function<RawPosition, String> identifier(String[] groups) {
        return pos -> Stream.of(groups)
                .map(PositionController::classifier)
                .map(f -> f.apply(pos))
                .collect(joining(SEPARATOR));
    }
    
    private static Function<RawPosition, String> classifier(String group) {
        switch (group) {
            case "trader_name"         : return RawPosition::getTraderName;
            case "trader_group"        : return RawPosition::getTraderGroup;
            case "trader_group_type"   : return RawPosition::getTraderGroupType;
            case "instrument_name"     : return RawPosition::getInstrumentNameUnwrapped;
            case "instrument_symbol"   : return RawPosition::getInstrumentSymbol;
            case "instrument_sector"   : return RawPosition::getInstrumentSectorUnwrapped;
            case "instrument_industry" : return RawPosition::getInstrumentIndustryUnwrapped;
            default : throw new IllegalArgumentException(
                "Unknown group '" + group + "'."
            );
        }
    }
    
    private static Predicate<RawPosition> filter(String group, String key) {
        switch (group) {
            case "trader_name"         : return RawPosition.TRADER_NAME.equal(key);
            case "trader_group"        : return RawPosition.TRADER_GROUP.equal(key);
            case "trader_group_type"   : return RawPosition.TRADER_GROUP_TYPE.equal(key);
            case "instrument_name"     : return RawPosition.INSTRUMENT_NAME.equal(key);
            case "instrument_symbol"   : return RawPosition.INSTRUMENT_SYMBOL.equal(key);
            case "instrument_sector"   : return RawPosition.INSTRUMENT_SECTOR.equal(key);
            case "instrument_industry" : return RawPosition.INSTRUMENT_INDUSTRY.equal(key);
            default : throw new IllegalArgumentException(
                "Unknown group '" + group + "'."
            );
        }
    }
    
    private final static class ResultFactory {
        
        private final Function<RawPosition, String> getId;
        private final String name;
        private final DateFormat format;
        private final boolean leaf;

        public ResultFactory(
                Function<RawPosition, String> getId, 
                String name, 
                DateFormat format, 
                boolean leaf) {
            
            this.getId  = requireNonNull(getId);
            this.name   = requireNonNull(name);
            this.format = requireNonNull(format);
            this.leaf   = leaf;
        }
        
        public Result createFrom(RawPosition pos) {
            return new Result(
                getId.apply(pos),
                name,
                leaf,
                pos.getInitiateTradingMktVal(),
                pos.getLiquidateTradingMktVal(),
                pos.getPnl(),
                pos.getTraderName(),
                pos.getTraderGroup(),
                pos.getTraderGroupType(),
                pos.getInstrumentNameUnwrapped(),
                pos.getInstrumentSymbol(),
                pos.getInstrumentSectorUnwrapped(),
                pos.getInstrumentIndustryUnwrapped()
            );
        }
    }
    
    public final static class Result {
        
        private final String id;
        private final String name;
        private final boolean leaf;
        
        private double initiateTradingMktValue;
        private double liquidateTradingMktValue;
        private double pnl;
        
        private String traderName;
        private String traderGroup;
        private String traderGroupType;
        private String instrumentName;
        private String instrumentSymbol;
        private String instrumentSector;
        private String instrumentIndustry;

        public Result(String id, 
                      String name, 
                      boolean leaf, 
                      double initiateTradingMktValue, 
                      double liquidateTradingMktValue, 
                      double pnl, 
                      String traderName, 
                      String traderGroup, 
                      String traderGroupType, 
                      String instrumentName, 
                      String instrumentSymbol, 
                      String instrumentSector, 
                      String instrumentIndustry) {
            
            this.id                       = id;
            this.name                     = name;
            this.leaf                     = leaf;
            this.initiateTradingMktValue  = initiateTradingMktValue;
            this.liquidateTradingMktValue = liquidateTradingMktValue;
            this.pnl                      = pnl;
            this.traderName               = traderName;
            this.traderGroup              = traderGroup;
            this.traderGroupType          = traderGroupType;
            this.instrumentName           = instrumentName;
            this.instrumentSymbol         = instrumentSymbol;
            this.instrumentSector         = instrumentSector;
            this.instrumentIndustry       = instrumentIndustry;
        }
        
        public Result aggregate(Result other) {
            initiateTradingMktValue  += other.initiateTradingMktValue;
            liquidateTradingMktValue += other.liquidateTradingMktValue;
            pnl                      += other.pnl;
            
            traderName         = aggregate(traderName,         other.traderName);
            traderGroup        = aggregate(traderGroup,        other.traderGroup);
            traderGroupType    = aggregate(traderGroupType,    other.traderGroupType);
            instrumentName     = aggregate(instrumentName,     other.instrumentName);
            instrumentSymbol   = aggregate(instrumentSymbol,   other.instrumentSymbol);
            instrumentSector   = aggregate(instrumentSector,   other.instrumentSector);
            instrumentIndustry = aggregate(instrumentIndustry, other.instrumentIndustry);
            
            return this;
        }

        private static String aggregate(String a, String b) {
            if (a == null || b == null) {
                return null;
            } else {
                return a.equals(b) ? a : null;
            }
        }
    }
}