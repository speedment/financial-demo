package com.extspeeder.example.financialdemo.controller;

import com.extspeeder.example.financialdemo.component.SizeCache;
import com.extspeeder.example.financialdemo.controller.param.Filter;
import com.extspeeder.example.financialdemo.controller.param.Sort;
import com.extspeeder.example.financialdemo.financialdemo.db.piq.price_store.PriceStore;
import com.extspeeder.example.financialdemo.financialdemo.db.piq.price_store.PriceStoreManager;
import com.speedment.field.ComparableField;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
@RestController("/prices")
public final class PricesController {

    private final SizeCache sizeCache;
    private @Autowired PriceStoreManager manager;
    
    PricesController() {
        sizeCache = new SizeCache();
    }
    
    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public PriceTotalResult handleGet(
        @RequestParam(name="callback") String callback,
        @RequestParam(name="start", required=false) Long start,
        @RequestParam(name="limit", required=false) Long limit,
        @RequestParam(name="filters", required=false) Collection<Filter> oFilters,
        @RequestParam(name="sort", required=false) Collection<Sort> sorts,
        HttpServletResponse response) {
        
        final Collection<Filter> filters;
        if (oFilters == null || oFilters.isEmpty()) {
            filters = Collections.emptyList();
        } else {
            filters = oFilters;
        }
        
        Stream<PriceStore> stream = manager.stream();
        Stream<PriceStore> totalStream = manager.stream();

        for (final Filter filter : filters) {
            final Predicate<PriceStore> predicate = findPredicate(filter);
            stream = stream.filter(predicate);
            stream = stream.filter(predicate);
        }

        if (sorts != null && !sorts.isEmpty()) {
            final Optional<Comparator<PriceStore>> comparator = sorts.stream()
                .map(PricesController::sortToComparator)
                .reduce(Comparator::thenComparing);
            
            if (comparator.isPresent()) {
                stream = stream.sorted(comparator.get());
            }
        }
        
        if (start != null) {
            stream = stream.skip(start);
        }
        
        if (limit != null) {
            stream = stream.limit(limit);
        }

        final long totalCount = sizeCache.computeIfAbsent(
            getClass().getName(),
            filters.toString(),
            totalStream::count
        );

        try {
            return PriceTotalResult.from(stream, totalCount);
        } finally {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Request-Method", "*");
            response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,Content-Type");
        }
    }
    
    private static ComparableField<PriceStore, ?, ?> findField(String property) {
        switch (property) {
            case "id"        : return PriceStore.ID;
            case "valueDate" : return PriceStore.VALUE_DATE;
            case "open"      : return PriceStore.OPEN;
            case "close"     : return PriceStore.CLOSE;
            case "high"      : return PriceStore.HIGH;
            case "low"       : return PriceStore.LOW;
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
                case "id"        :
                case "valueDate" : return Long.parseLong(filter.getValue());
                case "open"      :
                case "close"     :
                case "high"      :
                case "low"       : return Double.parseDouble(filter.getValue());
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
            final ComparableField<PriceStore, ?, V> field = 
                (ComparableField<PriceStore, ?, V>) findField(filter.getProperty());

            @SuppressWarnings("unchecked")
            final V operand = (V) findOperand(filter);
            
            switch (filter.getOperator()) {
                case EQUAL            : return field.equal(operand);
                case NOT_EQUAL        : return field.notEqual(operand);
                case LESS_THAN        : return field.lessThan(operand);
                case LESS_OR_EQUAL    : return field.lessOrEqual(operand);
                case GREATER_THAN     : return field.greaterThan(operand);
                case GREATER_OR_EQUAL : return field.greaterOrEqual(operand);
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
        final ComparableField<PriceStore, ?, ?> field = findField(sort.getProperty());
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
        private final long valueDate;
        private final double open;
        private final Double close;
        private final double high;
        private final double low;
        
        static PriceResult from(PriceStore original) {
            return new PriceResult(
                original.getId(),
                original.getValueDate(),
                original.getOpen(),
                original.getClose().orElse(null),
                original.getHigh(),
                original.getLow()
            );
        }

        public long getId() {
            return id;
        }

        public long getValueDate() {
            return valueDate;
        }

        public double getOpen() {
            return open;
        }

        public Double getClose() {
            return close;
        }

        public double getHigh() {
            return high;
        }

        public double getLow() {
            return low;
        }

        private PriceResult(long id, long valueDate, double open, Double close, double high, double low) {
            this.id        = id;
            this.valueDate = valueDate;
            this.open      = open;
            this.close     = close;
            this.high      = high;
            this.low       = low;
        }
    }
}