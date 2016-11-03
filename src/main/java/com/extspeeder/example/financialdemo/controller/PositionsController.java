package com.extspeeder.example.financialdemo.controller;

import com.extspeeder.example.financialdemo.financialdemo.db.piq.raw_position.RawPosition;
import com.extspeeder.example.financialdemo.financialdemo.db.piq.raw_position.RawPositionManager;
import com.speedment.internal.util.testing.Stopwatch;
import java.text.ParseException;
import java.util.Collection;
import static java.util.Objects.requireNonNull;
import java.util.function.Function;
import java.util.function.Predicate;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toConcurrentMap;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletResponse;
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
@RestController
public class PositionsController {
    
    private final static String SEPARATOR  = ">>";
    
    private @Autowired RawPositionManager rawPositions;

    @RequestMapping(value = "/speeder/positions", method = GET, produces = APPLICATION_JSON_VALUE)
    public Collection<Result> handleGet(
            @RequestParam(name="callback", required=false) String callback,
            @RequestParam(name="startDate") Integer iFrom, 
            @RequestParam(name="endDate") Integer iTo,
            @RequestParam(name="drillDownPath") String aGroups,
            @RequestParam(name="drillDownKey", required=false) String aKeys,
            HttpServletResponse response
    ) throws ParseException, NumberFormatException {
        
        final Stopwatch sw = Stopwatch.createStarted();
        final String[] groups = aGroups.split(SEPARATOR);
        
        Stream<RawPosition> positions = rawPositions.stream()
            .parallel().filter(RawPosition.VALUE_DATE.between(iFrom, iTo));
        
        final Function<RawPosition, String> classifier;
        final int usedGroups;
        
        if (aKeys == null || "root".equals(aKeys)) {
            classifier = classifier(groups[0]);
            usedGroups = 1;
        } else {
            final String[] keys = aKeys.split(SEPARATOR);
            usedGroups = Math.min(groups.length, keys.length + 1);
            
            for (int i = 0; i < keys.length; i++) {
                positions = positions.filter(filter(groups[i], keys[i]));
            }
            
            if (groups.length > keys.length) {
                classifier = classifier(groups[keys.length]);
            } else {
                classifier = null;
            }
        }
        
        final ResultFactory factory = new ResultFactory(
            identifier(groups, usedGroups)
        );
        
        try {
            if (classifier == null) {
                return positions.map(factory::createFrom).collect(toList());
            } else {
                return positions.collect(toConcurrentMap(
                    classifier, 
                    factory::createFrom, 
                    Result::aggregate
                )).values();
            }
        } finally {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Request-Method", "*");
            response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,Content-Type");
            System.out.println("Finished in: " + sw.stop());
        }
    }
    
    private static Function<RawPosition, String> identifier(String[] groups, int limit) {
        switch (limit) {
            case 0 : return pos -> "";
            case 1 : return classifier(groups[0]);
            case 2 : return pos -> 
                classifier(groups[0]).apply(pos) + SEPARATOR + 
                classifier(groups[1]).apply(pos);
            case 3 : return pos -> 
                classifier(groups[0]).apply(pos) + SEPARATOR + 
                classifier(groups[1]).apply(pos) + SEPARATOR +
                classifier(groups[2]).apply(pos);
            case 4 : return pos -> 
                classifier(groups[0]).apply(pos) + SEPARATOR + 
                classifier(groups[1]).apply(pos) + SEPARATOR +
                classifier(groups[2]).apply(pos) + SEPARATOR +
                classifier(groups[3]).apply(pos);
            case 5 : return pos -> 
                classifier(groups[0]).apply(pos) + SEPARATOR + 
                classifier(groups[1]).apply(pos) + SEPARATOR +
                classifier(groups[2]).apply(pos) + SEPARATOR +
                classifier(groups[3]).apply(pos) + SEPARATOR +
                classifier(groups[4]).apply(pos);
            default : return pos -> Stream.of(groups)
                .limit(limit)
                .map(PositionsController::classifier)
                .map(c -> c.apply(pos))
                .collect(joining(SEPARATOR));
        }
    }
    
    private static Function<RawPosition, String> classifier(String group) {
        switch (group) {
            case "valueDate"          : return RawPosition::getValueDateAsString;
            case "traderName"         : return RawPosition::getTraderName;
            case "traderGroup"        : return RawPosition::getTraderGroup;
            case "traderGroupType"    : return RawPosition::getTraderGroupType;
            case "instrumentName"     : return RawPosition::getInstrumentNameOrEmpty;
            case "instrumentSymbol"   : return RawPosition::getInstrumentSymbol;
            case "instrumentSector"   : return RawPosition::getInstrumentSectorOrEmpty;
            case "instrumentIndustry" : return RawPosition::getInstrumentIndustryOrEmpty;
            default : throw new IllegalArgumentException(
                "Unknown group '" + group + "'."
            );
        }
    }
    
    private static Predicate<RawPosition> filter(String group, String key) throws ParseException, NumberFormatException {
        switch (group) {
            case "valueDate"          : return RawPosition.VALUE_DATE.equal(Integer.parseInt(key));
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

        public ResultFactory(Function<RawPosition, String> getId) {
            this.getId = requireNonNull(getId);
        }
        
        public Result createFrom(RawPosition pos) {
            return new Result(
                getId.apply(pos),
                pos.getInitiateTradingMktVal(),
                pos.getLiquidateTradingMktVal(),
                pos.getPnl(),
                pos.getInstrumentNameUnwrapped(),
                pos.getValueDate()
            );
        }
    }
    
    public final static class Result {
        
        private final String id;
        
        private double initiateTradingMktValue;
        private double liquidateTradingMktValue;
        private double pnl;
        private String instrumentName;
        private int minDate;
        private int maxDate;

        public Result(String id,
                      double initiateTradingMktValue, 
                      double liquidateTradingMktValue, 
                      double pnl,
                      String instrumentName,
                      int valueDate) {
            
            this.id                       = id;
            this.initiateTradingMktValue  = initiateTradingMktValue;
            this.liquidateTradingMktValue = liquidateTradingMktValue;
            this.pnl                      = pnl;
            this.instrumentName           = instrumentName;
            
            this.minDate = valueDate;
            this.maxDate = valueDate;
        }

        public String getId() {
            return id;
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

        public String getInstrumentName() {
            return instrumentName;
        }

        public int getMinDate() {
            return minDate;
        }
        
        public int getMaxDate() {
            return maxDate;
        }
        
        public Result aggregate(Result other) {
            initiateTradingMktValue  += other.initiateTradingMktValue;
            liquidateTradingMktValue += other.liquidateTradingMktValue;
            pnl                      += other.pnl;
            
            instrumentName = aggregate(instrumentName, other.instrumentName);
            
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