package com.extspeeder.example.financialdemo.controller;

import com.extspeeder.example.financialdemo.aggregator.PositionResult;
import com.extspeeder.example.financialdemo.aggregator.RawPositionToConcurrentMap;
import com.extspeeder.example.financialdemo.aggregator.RawPositionToConcurrentMap.ObjLongFunction;
import com.extspeeder.example.financialdemo.db.position.RawPosition;
import com.extspeeder.example.financialdemo.db.position.RawPositionManager;
import com.speedment.enterprise.datastore.runtime.entitystore.EntityStore;
import com.speedment.enterprise.datastore.runtime.function.EntityFunction;
import com.speedment.runtime.core.internal.util.testing.Stopwatch;
import com.speedment.runtime.field.StringField;
import com.speedment.runtime.field.trait.HasIntValue;
import com.speedment.runtime.field.trait.HasReferenceValue;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.function.Predicate;
import static java.util.stream.Collectors.joining;
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
    
    private final RawPositionManager rawPositions;
    
    @Autowired
    PositionsController(RawPositionManager rawPositions) {
        this.rawPositions = requireNonNull(rawPositions);
    }

    @RequestMapping(
        value    = "/speeder/positions", 
        method   = GET, 
        produces = APPLICATION_JSON_VALUE
    ) public Collection<PositionResult> handleGet(
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
        
        final Function<RawPosition, Object> classifier;
        final ObjLongFunction<EntityStore<RawPosition>, Object> refClassifier;
        final int usedGroups;
        
        if (aKeys == null || "root".equals(aKeys)) {
            classifier    = classifier(groups[0]);
            refClassifier = refClassifier(groups[0]);
            usedGroups    = 1;
        } else {
            final String[] keys = aKeys.split(SEPARATOR);
            usedGroups = Math.min(groups.length, keys.length + 1);
            
            for (int i = 0; i < keys.length; i++) {
                positions = positions.filter(filter(groups[i], keys[i]));
            }
            
            if (groups.length > keys.length) {
                classifier    = classifier(groups[keys.length]);
                refClassifier = refClassifier(groups[keys.length]);
            } else {
                classifier    = null;
                refClassifier = null;
            }
        }

        final Function<RawPosition, String> identifier =
            identifier(groups, usedGroups);
        
        final ObjLongFunction<EntityStore<RawPosition>, String> refIdentifier =
            refIdentifier(groups, usedGroups);

        try {
            if (classifier == null) {
                return positions
                    .map(new EntityFunction<RawPosition, PositionResult>() {
                        @Override
                        public LongFunction<PositionResult> asReferenceFunction(
                                EntityStore<RawPosition> store) {
                            
                            return ref -> new PositionResult(refIdentifier, identifier)
                                .aggregateRef(store, ref);
                        }

                        @Override
                        public PositionResult apply(RawPosition pos) {
                            return new PositionResult(refIdentifier, identifier)
                                .aggregate(pos);
                        }
                    })
                    .collect(toList());
                
            } else {
                return positions.collect(new RawPositionToConcurrentMap<>(
                    refClassifier,
                    refIdentifier,
                    identifier
                )).values();
            }
        } finally {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Request-Method", "*");
            response.setHeader(
                "Access-Control-Allow-Headers", 
                "X-Requested-With,Content-Type"
            );
            System.out.println("Finished in: " + sw.stop());
        }
    }
    
    private static Function<RawPosition, String> identifier(
            String[] groups, int limit) {
        
        switch (limit) {
            case 0 : return pos -> "";
            
            case 1 : {
                final Function<RawPosition, Object> f0 = classifier(groups[0]);
                return pos -> f0.apply(pos).toString();
            }
            case 2 : {
                final Function<RawPosition, Object> f0 = classifier(groups[0]);
                final Function<RawPosition, Object> f1 = classifier(groups[1]);
                return pos -> f0.apply(pos) + SEPARATOR + f1.apply(pos);
            }
            case 3 : {
                final Function<RawPosition, Object> f0 = classifier(groups[0]);
                final Function<RawPosition, Object> f1 = classifier(groups[1]);
                final Function<RawPosition, Object> f2 = classifier(groups[2]);
                
                return pos -> f0.apply(pos) + SEPARATOR + 
                              f1.apply(pos) + SEPARATOR + 
                              f2.apply(pos);
            }
            case 4 : {
                final Function<RawPosition, Object> f0 = classifier(groups[0]);
                final Function<RawPosition, Object> f1 = classifier(groups[1]);
                final Function<RawPosition, Object> f2 = classifier(groups[2]);
                final Function<RawPosition, Object> f3 = classifier(groups[3]);
                
                return pos -> f0.apply(pos) + SEPARATOR + 
                              f1.apply(pos) + SEPARATOR + 
                              f2.apply(pos) + SEPARATOR + 
                              f3.apply(pos);
            }
            case 5 : {
                final Function<RawPosition, Object> f0 = classifier(groups[0]);
                final Function<RawPosition, Object> f1 = classifier(groups[1]);
                final Function<RawPosition, Object> f2 = classifier(groups[2]);
                final Function<RawPosition, Object> f3 = classifier(groups[3]);
                final Function<RawPosition, Object> f4 = classifier(groups[4]);
                
                return pos -> f0.apply(pos) + SEPARATOR + 
                              f1.apply(pos) + SEPARATOR + 
                              f2.apply(pos) + SEPARATOR + 
                              f3.apply(pos) + SEPARATOR + 
                              f4.apply(pos);
            }
            case 6 : {
                final Function<RawPosition, Object> f0 = classifier(groups[0]);
                final Function<RawPosition, Object> f1 = classifier(groups[1]);
                final Function<RawPosition, Object> f2 = classifier(groups[2]);
                final Function<RawPosition, Object> f3 = classifier(groups[3]);
                final Function<RawPosition, Object> f4 = classifier(groups[4]);
                final Function<RawPosition, Object> f5 = classifier(groups[5]);
                
                return pos -> f0.apply(pos) + SEPARATOR + 
                              f1.apply(pos) + SEPARATOR + 
                              f2.apply(pos) + SEPARATOR + 
                              f3.apply(pos) + SEPARATOR + 
                              f4.apply(pos) + SEPARATOR + 
                              f5.apply(pos);
            }
            case 7 : {
                final Function<RawPosition, Object> f0 = classifier(groups[0]);
                final Function<RawPosition, Object> f1 = classifier(groups[1]);
                final Function<RawPosition, Object> f2 = classifier(groups[2]);
                final Function<RawPosition, Object> f3 = classifier(groups[3]);
                final Function<RawPosition, Object> f4 = classifier(groups[4]);
                final Function<RawPosition, Object> f5 = classifier(groups[5]);
                final Function<RawPosition, Object> f6 = classifier(groups[6]);
                
                return pos -> f0.apply(pos) + SEPARATOR + 
                              f1.apply(pos) + SEPARATOR + 
                              f2.apply(pos) + SEPARATOR + 
                              f3.apply(pos) + SEPARATOR + 
                              f4.apply(pos) + SEPARATOR + 
                              f5.apply(pos) + SEPARATOR + 
                              f6.apply(pos);
            }
            default : {
                final List<Function<RawPosition, Object>> f = Stream.of(groups)
                    .limit(limit)
                    .map(PositionsController::classifier)
                    .collect(toList());
                
                return pos -> f.stream()
                    .map(c -> c.apply(pos))
                    .map(Object::toString)
                    .collect(joining(SEPARATOR));
            }
        }
    }
    
    private static ObjLongFunction<EntityStore<RawPosition>, String> refIdentifier(
            String[] groups, int limit) {
        
        switch (limit) {
            case 0 : return (store, ref) -> "";
            case 1 : return (store, ref) -> String.valueOf(refClassifier(groups[0]).apply(store, ref));
            case 2 : {
                final ObjLongFunction<EntityStore<RawPosition>, Object> f0 = refClassifier(groups[0]);
                final ObjLongFunction<EntityStore<RawPosition>, Object> f1 = refClassifier(groups[1]);
                return (store, ref) -> f0.apply(store, ref) + SEPARATOR + 
                                       f1.apply(store, ref);
            }
            case 3 : {
                final ObjLongFunction<EntityStore<RawPosition>, Object> f0 = refClassifier(groups[0]);
                final ObjLongFunction<EntityStore<RawPosition>, Object> f1 = refClassifier(groups[1]);
                final ObjLongFunction<EntityStore<RawPosition>, Object> f2 = refClassifier(groups[2]);
                return (store, ref) -> f0.apply(store, ref) + SEPARATOR + 
                                       f1.apply(store, ref) + SEPARATOR +
                                       f2.apply(store, ref);
            }
            case 4 : {
                final ObjLongFunction<EntityStore<RawPosition>, Object> f0 = refClassifier(groups[0]);
                final ObjLongFunction<EntityStore<RawPosition>, Object> f1 = refClassifier(groups[1]);
                final ObjLongFunction<EntityStore<RawPosition>, Object> f2 = refClassifier(groups[2]);
                final ObjLongFunction<EntityStore<RawPosition>, Object> f3 = refClassifier(groups[3]);
                return (store, ref) -> f0.apply(store, ref) + SEPARATOR + 
                                       f1.apply(store, ref) + SEPARATOR +
                                       f2.apply(store, ref) + SEPARATOR +
                                       f3.apply(store, ref);
            }
            case 5 : {
                final ObjLongFunction<EntityStore<RawPosition>, Object> f0 = refClassifier(groups[0]);
                final ObjLongFunction<EntityStore<RawPosition>, Object> f1 = refClassifier(groups[1]);
                final ObjLongFunction<EntityStore<RawPosition>, Object> f2 = refClassifier(groups[2]);
                final ObjLongFunction<EntityStore<RawPosition>, Object> f3 = refClassifier(groups[3]);
                final ObjLongFunction<EntityStore<RawPosition>, Object> f4 = refClassifier(groups[4]);
                return (store, ref) -> f0.apply(store, ref) + SEPARATOR + 
                                       f1.apply(store, ref) + SEPARATOR +
                                       f2.apply(store, ref) + SEPARATOR +
                                       f3.apply(store, ref) + SEPARATOR +
                                       f4.apply(store, ref);
            }
            case 6 : {
                final ObjLongFunction<EntityStore<RawPosition>, Object> f0 = refClassifier(groups[0]);
                final ObjLongFunction<EntityStore<RawPosition>, Object> f1 = refClassifier(groups[1]);
                final ObjLongFunction<EntityStore<RawPosition>, Object> f2 = refClassifier(groups[2]);
                final ObjLongFunction<EntityStore<RawPosition>, Object> f3 = refClassifier(groups[3]);
                final ObjLongFunction<EntityStore<RawPosition>, Object> f4 = refClassifier(groups[4]);
                final ObjLongFunction<EntityStore<RawPosition>, Object> f5 = refClassifier(groups[5]);
                return (store, ref) -> f0.apply(store, ref) + SEPARATOR + 
                                       f1.apply(store, ref) + SEPARATOR +
                                       f2.apply(store, ref) + SEPARATOR +
                                       f3.apply(store, ref) + SEPARATOR +
                                       f4.apply(store, ref) + SEPARATOR +
                                       f5.apply(store, ref);
            }
            case 7 : {
                final ObjLongFunction<EntityStore<RawPosition>, Object> f0 = refClassifier(groups[0]);
                final ObjLongFunction<EntityStore<RawPosition>, Object> f1 = refClassifier(groups[1]);
                final ObjLongFunction<EntityStore<RawPosition>, Object> f2 = refClassifier(groups[2]);
                final ObjLongFunction<EntityStore<RawPosition>, Object> f3 = refClassifier(groups[3]);
                final ObjLongFunction<EntityStore<RawPosition>, Object> f4 = refClassifier(groups[4]);
                final ObjLongFunction<EntityStore<RawPosition>, Object> f5 = refClassifier(groups[5]);
                final ObjLongFunction<EntityStore<RawPosition>, Object> f6 = refClassifier(groups[6]);
                return (store, ref) -> f0.apply(store, ref) + SEPARATOR + 
                                       f1.apply(store, ref) + SEPARATOR +
                                       f2.apply(store, ref) + SEPARATOR +
                                       f3.apply(store, ref) + SEPARATOR +
                                       f4.apply(store, ref) + SEPARATOR +
                                       f5.apply(store, ref) + SEPARATOR +
                                       f6.apply(store, ref);
            }
            default : {
                final List<ObjLongFunction<EntityStore<RawPosition>, Object>> f 
                    = Stream.of(groups)
                        .limit(limit)
                        .map(PositionsController::refClassifier)
                        .collect(toList());
                
                return (store, ref) -> f.stream()
                    .map(c -> c.apply(store, ref))
                    .map(Object::toString)
                    .collect(joining(SEPARATOR));
            }
        }
    }
    
    private static Function<RawPosition, Object> classifier(String group) {
        switch (group) {
            case "valueDate"          : return RawPosition::getValueDate;
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
    
    private static ObjLongFunction<EntityStore<RawPosition>, Object> refClassifier(String group) {
        switch (group) {
            case "valueDate"          : return intDeserializer(RawPosition.VALUE_DATE);
            case "traderName"         : return stringDeserializer(RawPosition.TRADER_NAME);
            case "traderGroup"        : return stringDeserializer(RawPosition.TRADER_GROUP);
            case "traderGroupType"    : return stringDeserializer(RawPosition.TRADER_GROUP_TYPE);
            case "instrumentName"     : return nullableDeserializer(RawPosition.INSTRUMENT_NAME, "");
            case "instrumentSymbol"   : return stringDeserializer(RawPosition.INSTRUMENT_SYMBOL);
            case "instrumentSector"   : return nullableDeserializer(RawPosition.INSTRUMENT_SECTOR, "");
            case "instrumentIndustry" : return nullableDeserializer(RawPosition.INSTRUMENT_INDUSTRY, "");
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
    
    private static ObjLongFunction<EntityStore<RawPosition>, Object> stringDeserializer(StringField<RawPosition, ?> field) {
        return (store, ref) -> store.deserialize(ref, field);
    }
    
    private static ObjLongFunction<EntityStore<RawPosition>, Object> nullableDeserializer(HasReferenceValue<RawPosition, ?, ?> field, Object defaultValue) {
        return (store, ref) -> 
            store.isNull(ref, field)
                ? defaultValue
                : store.deserialize(ref, field);
    }
    
    private static ObjLongFunction<EntityStore<RawPosition>, Object> intDeserializer(HasIntValue<RawPosition, ?> field) {
        return (store, ref) -> store.deserializeInt(ref, field);
    }
}