package com.extspeeder.example.financialdemo.controller;

import com.extspeeder.example.financialdemo.financialdemo.db.piq.raw_position.RawPosition;
import com.extspeeder.example.financialdemo.financialdemo.db.piq.raw_position.RawPositionManager;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import static java.util.Objects.requireNonNull;
import java.util.function.Function;
import java.util.function.Predicate;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toConcurrentMap;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
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

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public Collection<Result> handleGet(
            @RequestParam(name="callback") String callback,
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
            leaf       = false;
        } else {
            final String[] keys = aKeys.split(SEPARATOR);
            
            for (int i = 0; i < keys.length; i++) {
                positions = positions.filter(filter(groups[i], keys[i]));
            }
            
            if (groups.length > keys.length) {
                classifier = classifier(groups[keys.length]);
                leaf       = false;
            } else {
                classifier = null;
                leaf       = true;
            }
        }
        
        final ResultFactory factory = new ResultFactory(
            identifier(groups), 
            classifier == null
                ? pos -> null
                : classifier,
            leaf
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
                .map(c -> c.apply(pos))
                .collect(joining(SEPARATOR));
    }
    
    private static Function<RawPosition, String> classifier(String group) {
        switch (group) {
            case "traderName"         : return RawPosition::getTraderName;
            case "traderGroup"        : return RawPosition::getTraderGroup;
            case "traderGroupType"    : return RawPosition::getTraderGroupType;
            case "instrumentName"     : return RawPosition::getInstrumentNameUnwrapped;
            case "instrumentSymbol"   : return RawPosition::getInstrumentSymbol;
            case "instrumentSector"   : return RawPosition::getInstrumentSectorUnwrapped;
            case "instrumentIndustry" : return RawPosition::getInstrumentIndustryUnwrapped;
            default : throw new IllegalArgumentException(
                "Unknown group '" + group + "'."
            );
        }
    }
    
    private static Predicate<RawPosition> filter(String group, String key) {
        switch (group) {
            case "traderName"         : return RawPosition.TRADER_NAME.equal(key);
            case "traderGroup"        : return RawPosition.TRADER_GROUP.equal(key);
            case "traderGroupType"    : return RawPosition.TRADER_GROUP_TYPE.equal(key);
            case "instrumentName"     : return RawPosition.INSTRUMENT_NAME.equal(key);
            case "instrumentSymbol"   : return RawPosition.INSTRUMENT_SYMBOL.equal(key);
            case "instrumentSector"   : return RawPosition.INSTRUMENT_SECTOR.equal(key);
            case "instrumentIndustry" : return RawPosition.INSTRUMENT_INDUSTRY.equal(key);
            default : throw new IllegalArgumentException(
                "Unknown group '" + group + "'."
            );
        }
    }
    
    private final static class ResultFactory {
        
        private final Function<RawPosition, String> getId;
        private final Function<RawPosition, String> getName;
        private final boolean leaf;

        public ResultFactory(
                Function<RawPosition, String> getId,
                Function<RawPosition, String> getName,
                boolean leaf) {
            
            this.getId   = requireNonNull(getId);
            this.getName = requireNonNull(getName);
            this.leaf    = leaf;
        }
        
        public Result createFrom(RawPosition pos) {
            return new Result(
                getId.apply(pos),
                getName.apply(pos),
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
                pos.getInstrumentIndustryUnwrapped(),
                pos.getValueDate()
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
        
        private int minDate;
        private int maxDate;

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
                      String instrumentIndustry,
                      int valueDate) {
            
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
            
            this.minDate = valueDate;
            this.maxDate = valueDate;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public boolean isLeaf() {
            return leaf;
        }

        public double getInitiateTradingMktValue() {
            return initiateTradingMktValue;
        }

        public double getLiquidateTradingMktValue() {
            return liquidateTradingMktValue;
        }

        public double getPnl() {
            return pnl;
        }

        public String getTraderName() {
            return traderName;
        }

        public String getTraderGroup() {
            return traderGroup;
        }

        public String getTraderGroupType() {
            return traderGroupType;
        }

        public String getInstrumentName() {
            return instrumentName;
        }

        public String getInstrumentSymbol() {
            return instrumentSymbol;
        }

        public String getInstrumentSector() {
            return instrumentSector;
        }

        public String getInstrumentIndustry() {
            return instrumentIndustry;
        }
        
        public String getMinDate() {
            return FORMAT.format(Date.from(Instant.ofEpochSecond(minDate)));
        }
        
        public String getMaxDate() {
            return FORMAT.format(Date.from(Instant.ofEpochSecond(maxDate)));
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
            
            minDate = Math.min(minDate, other.minDate);
            maxDate = Math.max(maxDate, other.maxDate);
            
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