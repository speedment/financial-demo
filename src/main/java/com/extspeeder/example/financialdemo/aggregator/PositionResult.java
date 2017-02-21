package com.extspeeder.example.financialdemo.aggregator;

import com.extspeeder.example.financialdemo.aggregator.RawPositionToConcurrentMap.ObjLongFunction;
import com.extspeeder.example.financialdemo.db.position.RawPosition;
import com.speedment.enterprise.datastore.runtime.entitystore.EntityStore;
import com.speedment.enterprise.datastore.runtime.entitystore.StringSelection;
import static java.util.Objects.requireNonNull;
import java.util.function.Function;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class PositionResult {

    private final ObjLongFunction<EntityStore<RawPosition>, String> refIdentifier;
    private final Function<RawPosition, String> identifier;
    
    private String id;
    private float initiateTradingMktValue;
    private float liquidateTradingMktValue;
    private float pnl;
    private String instrumentName;
    private int minDate;
    private int maxDate;
    
    private boolean instrumentNameSet = false;
    
    public PositionResult(
            ObjLongFunction<EntityStore<RawPosition>, String> refIdentifier,
            Function<RawPosition, String> identifier) {
        
        this.refIdentifier = requireNonNull(refIdentifier);
        this.identifier    = requireNonNull(identifier);
    }

    public String getId() {
        return id;
    }

    public float getInitiateTradingMktValue() {
        return initiateTradingMktValue;
    }

    public float getLiquidateTradingMktValue() {
        return liquidateTradingMktValue;
    }

    public float getPnl() {
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

    public boolean isInstrumentNameSet() {
        return instrumentNameSet;
    }
    
    public PositionResult aggregateRef(EntityStore<RawPosition> store, long reference) {
        if (id == null) {
            id = refIdentifier.apply(store, reference);
            
        } else {
            initiateTradingMktValue  += store.deserializeFloat(reference, RawPosition.INITIATE_TRADING_MKT_VAL);
            liquidateTradingMktValue += store.deserializeFloat(reference, RawPosition.LIQUIDATE_TRADING_MKT_VAL);
            pnl                      += store.deserializeFloat(reference, RawPosition.PNL);

            final int date = store.deserializeInt(reference, RawPosition.VALUE_DATE);
            minDate = Math.min(minDate, date);
            maxDate = Math.max(maxDate, date);

            if (instrumentNameSet) {
                if (instrumentName == null) {
                    if (store.isNull(reference, RawPosition.INSTRUMENT_NAME)) {
                        instrumentName = null;
                    } else {
                        instrumentName = store.deserialize(reference, RawPosition.INSTRUMENT_NAME);
                    }
                } else {
                    if (store.isNull(reference, RawPosition.INSTRUMENT_NAME)
                    ||  store.selectString(reference, RawPosition.INSTRUMENT_NAME)
                        .compareTo(instrumentName, StringSelection.Encoding.UTF_8) != 0) {
                        instrumentName = null;
                    }
                }
            } else {
                if (store.isNull(reference, RawPosition.INSTRUMENT_NAME)) {
                    instrumentName = null;
                } else {
                    instrumentName = store.deserialize(reference, RawPosition.INSTRUMENT_NAME);
                    instrumentNameSet = true;
                }
            }
        }
        
        return this;
    }
//    
    public PositionResult aggregate(RawPosition rawPosition) {
        if (id == null) {
            id = identifier.apply(rawPosition);
        }
        
        initiateTradingMktValue  += rawPosition.getInitiateTradingMktVal();
        liquidateTradingMktValue += rawPosition.getLiquidateTradingMktVal();
        pnl                      += rawPosition.getPnl();
        
        final int date = rawPosition.getValueDate();
        minDate = Math.min(minDate, date);
        maxDate = Math.max(maxDate, date);
        
        final String name = rawPosition.getInstrumentNameOrNull();
        if (instrumentNameSet) {
            if (instrumentName == null) {
                instrumentName = name;
            } else {
                if (name == null
                || !name.equals(instrumentName)) {
                    instrumentName = null;
                }
            }
        } else {
            instrumentName = name;
            instrumentNameSet = true;
        }
        
        return this;
    }
    
    public PositionResult aggregate(PositionResult result) {
        if (id == null) id = result.id;

        initiateTradingMktValue  += result.initiateTradingMktValue;
        liquidateTradingMktValue += result.liquidateTradingMktValue;
        pnl                      += result.pnl;
        minDate = Math.min(minDate, result.minDate);
        maxDate = Math.max(maxDate, result.maxDate);
        
        final String name = result.instrumentName;
        if (instrumentNameSet) {
            if (instrumentName == null) {
                instrumentName = name;
            } else {
                if (name == null
                || !name.equals(instrumentName)) {
                    instrumentName = null;
                }
            }
        } else {
            instrumentName = name;
            instrumentNameSet = true;
        }
        
        return this;
    }
}