package com.extspeeder.example.financialdemo.aggregator;

import com.extspeeder.example.financialdemo.db.position.RawPosition;
import com.speedment.enterprise.datastore.runtime.entitystore.EntityStore;
import com.speedment.enterprise.datastore.runtime.function.EntityCollector;
import static java.util.Collections.singleton;
import java.util.Map;
import static java.util.Objects.requireNonNull;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class RawPositionToConcurrentMap<K>
implements EntityCollector<RawPosition, Map<K, PositionResult>> {
    
    @FunctionalInterface
    public interface ObjLongFunction<T, R> {
        R apply(T obj, long ref);
    }

    private final ObjLongFunction<EntityStore<RawPosition>, K> refClassifier;
    private final ObjLongFunction<EntityStore<RawPosition>, String> refIdentifier;
    private final Function<RawPosition, String> identifier;

    public RawPositionToConcurrentMap(
            ObjLongFunction<EntityStore<RawPosition>, K> refClassifier,
            ObjLongFunction<EntityStore<RawPosition>, String> refIdentifier,
            Function<RawPosition, String> identifier) {
        
        this.refClassifier = requireNonNull(refClassifier);
        this.refIdentifier = requireNonNull(refIdentifier);
        this.identifier    = requireNonNull(identifier);
    }
    
    @Override
    public Supplier<Map<K, PositionResult>> supplier() {
        return ConcurrentHashMap::new;
    }

    @Override
    public BiConsumer<Map<K, PositionResult>, RawPosition> accumulator() {
        throw new IllegalStateException();
    }

    @Override
    public ObjLongConsumer<Map<K, PositionResult>> referenceAccumulator(
            EntityStore<RawPosition> store) {
        
        return (result, ref) -> result.computeIfAbsent(
            refClassifier.apply(store, ref), 
            k -> new PositionResult(refIdentifier, identifier)
        ).aggregateRef(store, ref);
    }

    @Override
    public BinaryOperator<Map<K, PositionResult>> combiner() {
        return (first, second) -> {
            second.forEach((key, value) ->
                first.merge(key, value, PositionResult::aggregate)
            );
            return first;
        };
    }

    @Override
    public Function<Map<K, PositionResult>, Map<K, PositionResult>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return singleton(Collector.Characteristics.IDENTITY_FINISH);
    }

}