package com.extspeeder.example.financialdemo.controller;

import com.extspeeder.example.financialdemo.component.SizeCache;
import com.extspeeder.example.financialdemo.controller.param.Filter;
import com.extspeeder.example.financialdemo.controller.param.Sort;
import com.extspeeder.example.financialdemo.db.prices.PriceStore;
import com.extspeeder.example.financialdemo.db.prices.PriceStoreManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.speedment.runtime.field.StringField;
import com.speedment.runtime.field.trait.HasComparableOperators;
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
public final class PricesController {

    private final SizeCache sizeCache;
    
    private final Gson gson;
    private final PriceStoreManager manager;
    
    @Autowired
    PricesController(Gson gson, PriceStoreManager manager) {
        this.gson      = requireNonNull(gson);
        this.manager   = requireNonNull(manager);
        this.sizeCache = new SizeCache();
    }
    
    @RequestMapping(value = "/speeder/prices", method = GET, produces = APPLICATION_JSON_VALUE)
    public PriceTotalResult handleGet(
        @RequestParam(name="callback", required=false) String callback,
        @RequestParam(name="start", required=false) Long start,
        @RequestParam(name="limit", required=false) Long limit,
        @RequestParam(name="filter", required=false) String sFilters,
        @RequestParam(name="sort", required=false) String sSorts,
        HttpServletResponse response) {
        
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
        
        Stream<PriceStore> stream = manager.stream();
        Stream<PriceStore> totalStream = manager.stream();

        for (final Filter filter : filters) {
            final Predicate<PriceStore> predicate = findPredicate(filter);
            stream      = stream.filter(predicate);
            totalStream = totalStream.filter(predicate);
        }
        
        if (sorts != null && !sorts.isEmpty()) {
            final Optional<Comparator<PriceStore>> comparator = sorts.stream()
                .map(PricesController::sortToComparator)
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
            return PriceTotalResult.from(stream, totalCount);
        } finally {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Request-Method", "*");
            response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,Content-Type");
        }
    }
    
    private static HasComparableOperators<PriceStore, ?> findField(String property) {
        switch (property) {
            case "id"        : return PriceStore.ID;
            case "valueDate" : return PriceStore.VALUE_DATE;
            case "open"      : return PriceStore.OPEN;
            case "close"     : return PriceStore.CLOSE;
            case "high"      : return PriceStore.HIGH;
            case "low"       : return PriceStore.LOW;
            case "instrumentSymbol" : return PriceStore.INSTRUMENT_SYMBOL;
            default : throw new IllegalArgumentException(
                "Unknown property: " + property + "."
            );
        }
    }
    
    private static Object findOperand(Filter filter) {
        if (filter.getValue() == null) {
            return null;
        } else {
            switch (filter.getProperty()) {
                case "id"        : return Long.parseLong(filter.getValue());
                case "valueDate" : return Integer.parseInt(filter.getValue());
                case "open"      :
                case "close"     :
                case "high"      :
                case "low"       : return Float.parseFloat(filter.getValue());
                case "instrumentSymbol" : return filter.getValue();
                default : throw new IllegalArgumentException(
                    "Unknown property: " + filter.getProperty() + "."
                );
            }
        }
    }
    
    private static <V extends Comparable<? super V>> Predicate<PriceStore> 
    findPredicate(Filter filter) {
        try {
            @SuppressWarnings("unchecked")
            final HasComparableOperators<PriceStore, V> field = 
                (HasComparableOperators<PriceStore, V>) findField(filter.getProperty());

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
                        final StringField<PriceStore, ?> stringField =
                            (StringField<PriceStore, ?>) field;

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
    
    private static Comparator<PriceStore> sortToComparator(Sort sort) {
        final HasComparableOperators<PriceStore, ?> field = findField(sort.getProperty());
        final Comparator<PriceStore> comparator = field.comparator();
        if (sort.getDirection() == Sort.Direction.DESC) {
            return comparator.reversed();
        } else {
            return comparator;
        }
    }
    
    public final static class PriceTotalResult {
        
        private final Collection<PriceResult> data;
        private final long total;
        
        static PriceTotalResult from(Stream<PriceStore> stream, long total) {
            return new PriceTotalResult(
                stream.map(PriceResult::from).collect(toList()),
                total
            );
        }

        private PriceTotalResult(Collection<PriceResult> data, long total) {
            this.data  = data;
            this.total = total;
        }

        public Collection<PriceResult> getData() {
            return data;
        }

        public long getTotal() {
            return total;
        }
    }
    
    public final static class PriceResult {
        
        private final long id;
        private final int valueDate;
        private final float open;
        private final Float close;
        private final float high;
        private final float low;
        private final String instrumentSymbol;
        
        static PriceResult from(PriceStore original) {
            return new PriceResult(
                original.getId(),
                original.getValueDate(),
                original.getOpen(),
                original.getClose(),
                original.getHigh(),
                original.getLow(),
                original.getInstrumentSymbol()
            );
        }

        public long getId() {
            return id;
        }

        public long getValueDate() {
            return valueDate;
        }

        public float getOpen() {
            return open;
        }

        public Float getClose() {
            return close;
        }

        public float getHigh() {
            return high;
        }

        public float getLow() {
            return low;
        }

        public String getInstrumentSymbol() {
            return instrumentSymbol;
        }

        private PriceResult(long id, int valueDate, float open, Float close, float high, float low, String instrumentSymbol) {
            this.id        = id;
            this.valueDate = valueDate;
            this.open      = open;
            this.close     = close;
            this.high      = high;
            this.low       = low;
            this.instrumentSymbol = instrumentSymbol;
        }
    }
}