package com.extspeeder.example.financialdemo.controller;

import com.extspeeder.example.financialdemo.component.SizeCache;
import com.extspeeder.example.financialdemo.controller.param.Filter;
import com.extspeeder.example.financialdemo.controller.param.Sort;
import com.extspeeder.example.financialdemo.controller.util.TimeUtil;
import com.extspeeder.example.financialdemo.extra.BuySell;
import com.extspeeder.example.financialdemo.extra.OrderType;
import com.extspeeder.example.financialdemo.extra.Status;
import com.extspeeder.example.financialdemo.financialdemo.db.piq.order.Order;
import com.extspeeder.example.financialdemo.financialdemo.db.piq.order.OrderManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.speedment.field.trait.ComparableFieldTrait;
import com.speedment.field.trait.StringFieldTrait;
import java.text.ParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
public final class OrdersController {
    
    private final SizeCache sizeCache;
    
    private @Autowired Gson gson;
    private @Autowired OrderManager manager;
    
    OrdersController() {
        sizeCache = new SizeCache();
    }
    
    @RequestMapping(value = "/orders", method = GET, produces = APPLICATION_JSON_VALUE)
    public OrderTotalResult handleGet(
            @RequestParam(name="callback", required=false) String callback,
            @RequestParam(name="start", required=false) Long start,
            @RequestParam(name="limit", required=false) Long limit,
            @RequestParam(name="filters", required=false) String sFilters,
            @RequestParam(name="sort", required=false) String sSorts,
            HttpServletResponse response
    ) throws ParseException, NumberFormatException {
        
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
        
        Stream<Order> stream      = manager.stream();
        Stream<Order> totalStream = manager.stream();

        for (final Filter filter : filters) {
            final Predicate<Order> predicate = findPredicate(filter);
            stream      = stream.filter(predicate);
            totalStream = totalStream.filter(predicate);
        }

        if (sorts != null && !sorts.isEmpty()) {
            final Optional<Comparator<Order>> comparator = sorts.stream()
                .map(OrdersController::sortToComparator)
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
            return OrderTotalResult.from(stream, totalCount);
        } finally {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Request-Method", "*");
            response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,Content-Type");
        }
    }
    
    private static ComparableFieldTrait<Order, ?, ?> findField(String property) {
        switch (property) {
            case "id"                 : return Order.ID;
            case "dateCreated"        : return Order.DATE_CREATED;
            case "direction"          : return Order.DIRECTION;
            case "instrumentSymbol"   : return Order.INSTRUMENT_SYMBOL;
            case "instrumentSector"   : return Order.INSTRUMENT_SECTOR;
            case "instrumentIndustry" : return Order.INSTRUMENT_INDUSTRY;
            case "orderType"          : return Order.ORDER_TYPE;
            case "quantity"           : return Order.QUANTITY;
            case "status"             : return Order.STATUS;
            case "traderName"         : return Order.TRADER_NAME;
            case "traderGroup"        : return Order.TRADER_GROUP;
            case "traderGroupType"    : return Order.TRADER_GROUP_TYPE;
            case "execPrice"          : return Order.PRICE;
            default : throw new IllegalArgumentException(
                "Unknown property: " + property + "."
            );
        }
    }
    
    private static Object findOperand(Filter filter) throws ParseException, NumberFormatException {
        if (filter.getValue() == null) {
            return null;
        } else {
            switch (filter.getProperty()) {
                case "id"                 : return Long.parseLong(filter.getValue());
                case "dateCreated"        : return TimeUtil.toEpochSecs(filter.getValue());
                case "direction"          : return BuySell.valueOf(filter.getValue());
                case "traderName"         : // Fallthrough
                case "traderGroup"        : // Fallthrough
                case "traderGroupType"    : // Fallthrough
                case "instrumentSymbol"   : // Fallthrough
                case "instrumentSector"   : // Fallthrough
                case "instrumentIndustry" : return filter.getValue();
                case "orderType"          : return OrderType.valueOf(filter.getValue());
                case "status"             : return Status.valueOf(filter.getValue());
                case "quantity"           : return Integer.parseInt(filter.getValue());
                case "execPrice"          : return Double.parseDouble(filter.getValue());
                default : throw new IllegalArgumentException(
                    "Unknown property: " + filter.getProperty() + "."
                );
            }
        }
    }
    
    private static <V extends Comparable<? super V>> Predicate<Order> 
    findPredicate(Filter filter) throws ParseException, NumberFormatException {
        try {
            @SuppressWarnings("unchecked")
            final ComparableFieldTrait<Order, ?, V> field = 
                (ComparableFieldTrait<Order, ?, V>) findField(filter.getProperty());

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
                        final StringFieldTrait<Order, ?> stringField =
                            (StringFieldTrait<Order, ?>) field;

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
    
    private static Comparator<Order> sortToComparator(Sort sort) {
        final ComparableFieldTrait<Order, ?, ?> field = findField(sort.getProperty());
        final Comparator<Order> comparator = field.comparator();
        if (sort.getDirection() == Sort.Direction.DESC) {
            return comparator.reversed();
        } else {
            return comparator;
        }
    }
    
    public final static class OrderTotalResult {
        
        private final Collection<OrderResult> data;
        private final long total;
        
        static OrderTotalResult from(Stream<Order> stream, long total) {
            return new OrderTotalResult(
                stream.map(OrderResult::from).collect(toList()),
                total
            );
        }

        private OrderTotalResult(Collection<OrderResult> data, long total) {
            this.data  = data;
            this.total = total;
        }

        public Collection<OrderResult> getData() {
            return data;
        }

        public long getTotal() {
            return total;
        }
    }
    
    public final static class OrderResult {
        
        private final long id;
        private final String valueDate;
        private final long dateCreated;
        private final Long dateExecuted;
        private final BuySell direction; 
        private final String instrumentSymbol;
        private final String instrumentIndustry;
        private final String instrumentSector;
        private final OrderType orderType;
        private final Status status;
        private final String traderName;
        private final Double execPrice;
        private final Integer quantity;
        
        static OrderResult from(Order original) {
            return new OrderResult(
                original.getId(),
                TimeUtil.fromEpochSecs(original.getDateCreated()),
                original.getDateCreated() * 1_000L,
                (original.getDateExecuted() == null ? null : (original.getDateExecuted() * 1_000L)),
                original.getDirection(),
                original.getInstrumentSymbol(),
                original.getInstrumentIndustry().orElse(null),
                original.getInstrumentSector().orElse(null),
                original.getOrderType(),
                original.getStatus(),
                original.getTraderName(),
                original.getPrice(),
                original.getQuantity()
            );
        }

        public OrderResult(
                long id, 
                String valueDate, 
                long dateCreated, 
                Long dateExecuted, 
                BuySell direction, 
                String instrumentSymbol,
                String instrumentIndustry,
                String instrumentSector,
                OrderType orderType, 
                Status status, 
                String traderName,
                Double price,
                Integer quantity) {
            
            this.id                 = id;
            this.valueDate          = valueDate;
            this.dateCreated        = dateCreated;
            this.dateExecuted       = dateExecuted;
            this.direction          = direction;
            this.instrumentSymbol   = instrumentSymbol;
            this.instrumentIndustry = instrumentIndustry;
            this.instrumentSector   = instrumentSector;
            this.orderType          = orderType;
            this.status             = status;
            this.traderName         = traderName;
            this.execPrice          = price;
            this.quantity           = quantity;
        }

        public long getId() {
            return id;
        }

        public String getValueDate() {
            return valueDate;
        }

        public long getDateCreated() {
            return dateCreated;
        }

        public long getDateExecuted() {
            return dateExecuted;
        }

        public BuySell getDirection() {
            return direction;
        }

        public String getInstrumentSymbol() {
            return instrumentSymbol;
        }

        public String getInstrumentIndustry() {
            return instrumentIndustry;
        }

        public String getInstrumentSector() {
            return instrumentSector;
        }

        public OrderType getOrderType() {
            return orderType;
        }

        public Status getStatus() {
            return status;
        }

        public String getTraderName() {
            return traderName;
        }

        public Double getExecPrice() {
            return execPrice;
        }

        public Integer getQuantity() {
            return quantity;
        }
    }
}