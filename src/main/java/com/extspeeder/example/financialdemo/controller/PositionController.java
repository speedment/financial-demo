package com.extspeeder.example.financialdemo.controller;

import com.extspeeder.example.financialdemo.financialdemo.db.piq.raw_position.RawPosition;
import com.extspeeder.example.financialdemo.financialdemo.db.piq.raw_position.RawPositionManager;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import static java.util.Objects.requireNonNull;
import java.util.function.Function;
import java.util.function.Predicate;
import static java.util.stream.Collectors.groupingByConcurrent;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.reducing;
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
    public Map<String, Result> handleGet(
            @RequestParam(name="startDate") String startDate, 
            @RequestParam(name="endDate") String endDate,
            @RequestParam(name="drillDownKey") String aKeys,
            @RequestParam(name="drillDownPath", required=false) String aGroups
    ) throws ParseException {
        
        final int iFrom = (int) (FORMAT.parse(startDate).getTime() / 1000);
        final int iTo   = (int) (FORMAT.parse(endDate).getTime() / 1000);
        final Function<RawPosition, String> classifier;
        final Predicate<RawPosition> filter;
        
        if (aGroups == null || "root".equals(aGroups)) {
            requireNonNull(aKeys, "Expecting atleast one key to group by.");
            
            final String[] keys = aKeys.split(SEPARATOR);
            
            if (keys.length == 0) {
                throw new IllegalArgumentException(
                    "Expected atleast one 'drillDownKey'."
                );
            }
            
            classifier = classifier(keys[0]);
            
            final Map<String, Result> result = rawPositions.stream().parallel()
                .filter(RawPosition.VALUE_DATE.between(iFrom, iTo))
                .collect(
                    groupingByConcurrent(classifier, 
                        mapping(Result::from, 
                            reducing(Result.ZERO, Result::sum)
                        )
                    )
                );
            
            return result;
        } else {
            throw new UnsupportedOperationException(
                "Only root group is supported right now...."
            );
        }
    }
    
    private static Function<RawPosition, String> classifier(String aKeys) {
        switch (aKeys) {
            case "trader_name"         : return RawPosition::getTraderName;
            case "trader_group"        : return RawPosition::getTraderGroup;
            case "trader_group_type"   : return RawPosition::getTraderGroupType;
            case "instrument_name"     : return RawPosition::getInstrumentNameUnwrapped;
            case "instrument_symbol"   : return RawPosition::getInstrumentSymbol;
            case "instrument_sector"   : return RawPosition::getInstrumentSectorUnwrapped;
            case "instrument_industry" : return RawPosition::getInstrumentIndustryUnwrapped;
            default : throw new IllegalArgumentException(
                "Unknown key '" + aKeys + "'."
            );
        }
    }
    
    public final static class Result {
        
        private final BigDecimal initiateTradingMktValue;
        private final BigDecimal liquidateTradingMktValue;
        
        public final static Result ZERO = new Result(0, 0);
        
        private Result(BigDecimal initiateTradingMktValue, BigDecimal liquidateTradingMktValue) {
            this.initiateTradingMktValue  = initiateTradingMktValue;
            this.liquidateTradingMktValue = liquidateTradingMktValue;
        }

        private Result(double initiateTradingMktValue, double liquidateTradingMktValue) {
            this.initiateTradingMktValue  = BigDecimal.valueOf(initiateTradingMktValue);
            this.liquidateTradingMktValue = BigDecimal.valueOf(liquidateTradingMktValue);
        }

        public double getInitiateTradingMktValue() {
            return initiateTradingMktValue.doubleValue();
        }

        public double getLiquidateTradingMktValue() {
            return liquidateTradingMktValue.doubleValue();
        }
        
        public Result sum(Result other) {
            return new Result(
                initiateTradingMktValue.add(other.initiateTradingMktValue),
                liquidateTradingMktValue.add(other.liquidateTradingMktValue)
            );
        }
        
        public static Result from(RawPosition position) {
            return new Result(
                position.getInitiateTradingMktVal(),
                position.getLiquidateTradingMktVal()
            );
        }

        @Override
        public String toString() {
            return "Result{" + "initiateTradingMktValue=" + initiateTradingMktValue + ", liquidateTradingMktValue=" + liquidateTradingMktValue + '}';
        }
    }
}