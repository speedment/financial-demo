package com.extspeeder.example.financialdemo.controller;

import com.extspeeder.example.financialdemo.component.SizeCache;
import com.extspeeder.example.financialdemo.controller.param.Filter;
import com.extspeeder.example.financialdemo.controller.param.Sort;
import com.extspeeder.example.financialdemo.db.position.RawPosition;
import com.extspeeder.example.financialdemo.db.position.RawPositionManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.speedment.runtime.field.StringField;
import com.speedment.runtime.field.trait.HasComparableOperators;
import java.text.ParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.function.Predicate;
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
public class RawPositionsController {

    private final Gson gson;
    private final RawPositionManager manager;
    private final SizeCache sizeCache;
    
    @Autowired
    RawPositionsController(Gson gson, RawPositionManager manager) {
        this.gson      = requireNonNull(gson);
        this.manager   = requireNonNull(manager);
        this.sizeCache = new SizeCache();
    }
    
    @RequestMapping(value = "/speeder/rawpositions", method = GET, produces = APPLICATION_JSON_VALUE)
    public RawPositionTotalResult handleGet(
            @RequestParam(name="callback", required=false) String callback,
            @RequestParam(name="start", required=false) Long start,
            @RequestParam(name="limit", required=false) Long limit,
            @RequestParam(name="filter", required=false) String sFilters,
            @RequestParam(name="sort", required=false) String sSorts,
            HttpServletResponse response
    ) throws ParseException {
        
        final List<Filter> filters;
        if (sFilters == null || "[]".equals(sFilters)) {
            filters = Collections.emptyList();
        } else {
            filters = gson.fromJson(sFilters, new TypeToken<List<Filter>>(){}.getType());
        }
        
        final List<Sort> sorts;
        if (sSorts == null || "[]".equals(sSorts)) {
            sorts = Collections.emptyList();
        } else {
            sorts = gson.fromJson(sSorts, new TypeToken<List<Sort>>(){}.getType());
        }
        
        Stream<RawPosition> stream = manager.stream();
        Stream<RawPosition> totalStream = manager.stream();

        for (final Filter filter : filters) {
            final Predicate<RawPosition> predicate = findPredicate(filter);
            stream      = stream.filter(predicate);
            totalStream = totalStream.filter(predicate);
        }
        
        if (sorts != null && !sorts.isEmpty()) {
            final Optional<Comparator<RawPosition>> comparator = sorts.stream()
                .map(RawPositionsController::sortToComparator)
                .reduce(Comparator::thenComparing);
            
            if (comparator.isPresent()) {
                stream = stream.sorted(comparator.get());
            }
        }
        
        if (start != null && start > 0) {
            stream = stream.skip(start);
        }
        
        if (limit != null) {
            stream = stream.limit(limit);
        } else {
            stream = stream.limit(100);
        }

        final long totalCount = sizeCache.computeIfAbsent(
            sFilters, totalStream::count
        );

        try {
            return RawPositionTotalResult.from(stream, totalCount);
        } finally {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Request-Method", "*");
            response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,Content-Type");
        }
    }
    
    private static Comparator<RawPosition> sortToComparator(Sort sort) {
        final HasComparableOperators<RawPosition, ?> field = findField(sort.getProperty());
        final Comparator<RawPosition> comparator = field.comparator();
        if (sort.getDirection() == Sort.Direction.DESC) {
            return comparator.reversed();
        } else {
            return comparator;
        }
    }
    
    private static HasComparableOperators<RawPosition, ?> findField(String property) {
        switch (property) {
            case "id"                       : return RawPosition.ID;
            case "pnl"                      : return RawPosition.PNL;
            case "initiateTradingMktVal"    : return RawPosition.INITIATE_TRADING_MKT_VAL;
            case "liquidateTradingMktVal"   : return RawPosition.LIQUIDATE_TRADING_MKT_VAL;
            case "valueDate"                : return RawPosition.VALUE_DATE;
            case "traderName"               : return RawPosition.TRADER_NAME;
            case "traderGroup"              : return RawPosition.TRADER_GROUP;
            case "traderGroupType"          : return RawPosition.TRADER_GROUP_TYPE;
            case "instrumentName"           : return RawPosition.INSTRUMENT_NAME;
            case "instrumentSymbol"         : return RawPosition.INSTRUMENT_SYMBOL;
            case "instrumentSector"         : return RawPosition.INSTRUMENT_SECTOR;
            case "instrumentIndustry"       : return RawPosition.INSTRUMENT_INDUSTRY;
            default : throw new IllegalArgumentException(
                "Unknown property '" + property + "'."
            );
        }
    }
    
    private static Object findOperand(Filter filter) throws ParseException, NumberFormatException {
        if (filter.getValue() == null) {
            return null;
        } else {
            switch (filter.getProperty()) {
                case "id"                     : return Long.parseLong(filter.getValue());
                case "pnl"                    : // Fallthrough
                case "initiateTradingMktVal"  : // Fallthrough
                case "liquidateTradingMktVal" : return Double.parseDouble(filter.getValue());
                case "valueDate"              : return Integer.parseInt(filter.getValue());
                case "traderName"             : // Fallthrough
                case "traderGroup"            : // Fallthrough
                case "instrumentName"         : // Fallthrough
                case "instrumentSymbol"       : // Fallthrough
                case "instrumentSector"       : // Fallthrough
                case "instrumentIndustry"     : return filter.getValue();
                case "traderGroupType"        : return filter.getValue();
                default : throw new IllegalArgumentException(
                    "Unknown property: " + filter.getProperty() + "."
                );
            }
        }
    }
    
    private static <V extends Comparable<? super V>> Predicate<RawPosition> 
    findPredicate(Filter filter) throws ParseException, NumberFormatException {
        try {
            @SuppressWarnings("unchecked")
            final HasComparableOperators<RawPosition, V> field = 
                (HasComparableOperators<RawPosition, V>) findField(filter.getProperty());

            @SuppressWarnings("unchecked")
            final V operand = (V) findOperand(filter);
            
            switch (filter.getOperator()) {
                case EQUAL            : return field.equal(operand);
                case NOT_EQUAL        : return field.notEqual(operand);
                case LESS_THAN        : return field.lessThan(operand);
                case LESS_OR_EQUAL    : return field.lessOrEqual(operand);
                case GREATER_THAN     : return field.greaterThan(operand);
                case GREATER_OR_EQUAL : return field.greaterOrEqual(operand);
                case LIKE             : {
                    try {
                        @SuppressWarnings("unchecked")
                        final StringField<RawPosition, ?> stringField =
                            (StringField<RawPosition, ?>) field;

                        return stringField.contains(filter.getValue());
                    } catch (final ClassCastException ex) {
                        throw new IllegalArgumentException(
                            "Expecting a property of type 'String' if " + 
                            "operator 'like' is used.", ex
                        );
                    }
                }
                default : throw new IllegalArgumentException(
                    "Unknown property: " + filter.getProperty() + "."
                );
            }
        } catch (final ClassCastException ex) {
            throw new IllegalArgumentException(
                "The specified filter object contained values of an " + 
                "unparseable type.", ex
            );
        }
    }
    
    public final static class RawPositionTotalResult {
        
        private final Collection<RawPositionResult> data;
        private final long total;
        
        static RawPositionTotalResult from(Stream<RawPosition> stream, long total) {
            return new RawPositionTotalResult(
                stream.map(RawPositionResult::from).collect(toList()),
                total
            );
        }
        
        private RawPositionTotalResult(Collection<RawPositionResult> data, long total) {
            this.data  = data;
            this.total = total;
        }

        public Collection<RawPositionResult> getData() {
            return data;
        }

        public long getTotal() {
            return total;
        }
    }
    
    public final static class RawPositionResult {
        
        private final long id;
        private final double pnl;
        private final double initiateTradingMktVal;
        private final double liquidateTradingMktVal;
        private final int valueDate;
        private final String traderName;
        private final String traderGroup;
        private final String traderGroupType;
        private final String instrumentName;
        private final String instrumentSymbol;
        private final String instrumentSector;
        private final String instrumentIndustry;
        
        public static RawPositionResult from(RawPosition pos) {
            return new RawPositionResult(
                pos.getId(),
                pos.getPnl(),
                pos.getInitiateTradingMktVal(),
                pos.getLiquidateTradingMktVal(),
                pos.getValueDate(),
                pos.getTraderName(),
                pos.getTraderGroup(),
                pos.getTraderGroupType(),
                pos.getInstrumentNameOrNull(),
                pos.getInstrumentSymbol(),
                pos.getInstrumentSector().orElse(null),
                pos.getInstrumentIndustry().orElse(null)
            );
        }

        public RawPositionResult(
                long id, 
                double pnl, 
                double initiateTradingMktVal, 
                double liquidateTradingMktVal, 
                int valueDate, 
                String traderName, 
                String traderGroup, 
                String traderGroupType, 
                String instrumentName, 
                String instrumentSymbol, 
                String instrumentSector, 
                String instrumentIndustry) {
            
            this.id                     = id;
            this.pnl                    = pnl;
            this.initiateTradingMktVal  = initiateTradingMktVal;
            this.liquidateTradingMktVal = liquidateTradingMktVal;
            this.valueDate              = valueDate;
            this.traderName             = traderName;
            this.traderGroup            = traderGroup;
            this.traderGroupType        = traderGroupType;
            this.instrumentName         = instrumentName;
            this.instrumentSymbol       = instrumentSymbol;
            this.instrumentSector       = instrumentSector;
            this.instrumentIndustry     = instrumentIndustry;
        }

        public long getId() {
            return id;
        }

        public double getPnl() {
            return pnl;
        }

        public double getInitiateTradingMktVal() {
            return initiateTradingMktVal;
        }

        public double getLiquidateTradingMktVal() {
            return liquidateTradingMktVal;
        }

        public int getValueDate() {
            return valueDate;
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
    }
}