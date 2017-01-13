package com.extspeeder.example.financialdemo.aggregator;

import com.extspeeder.example.financialdemo.db.position.RawPosition;
import com.speedment.enterprise.fastcache.runtime.entitystore.EntityStore;
import com.speedment.enterprise.fastcache.runtime.function.EntityCollector;
import static java.util.Collections.singleton;
import static java.util.Objects.requireNonNull;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * 
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class AggregateRawPositions
implements EntityCollector<RawPosition, PositionResult> {
    
    private final Function<RawPosition, String> identifier;
    
    public AggregateRawPositions(Function<RawPosition, String> identifier) {
        this.identifier = requireNonNull(identifier);
    }
    
    @Override
    public Supplier<PositionResult> supplier() {
        return () -> new PositionResult(identifier);
    }

    @Override
    public BiConsumer<PositionResult, RawPosition> accumulator() {
        return PositionResult::aggregate;
    }

    @Override
    public ObjLongConsumer<PositionResult> referenceAccumulator(
            EntityStore<RawPosition> store) {
        
        return (res, ref) -> res.aggregate(store, ref);
    }

    @Override
    public BinaryOperator<PositionResult> combiner() {
        return PositionResult::aggregate;
    }

    @Override
    public Function<PositionResult, PositionResult> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return singleton(Collector.Characteristics.IDENTITY_FINISH);
    }
}