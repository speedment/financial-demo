package com.extspeeder.example.financialdemo.component;

import static com.speedment.common.codegen.internal.util.NullUtil.requireNonNulls;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.LongSupplier;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
public class SizeCache {

    private final Map<String, Long> maps;

    public SizeCache() {
        this.maps = new ConcurrentHashMap<>();
    }

    public Long computeIfAbsent(String filter, LongSupplier supplier) {
        requireNonNulls(supplier);
        return maps.computeIfAbsent(
            filter == null ? "[]" : filter, 
            $ -> supplier.getAsLong()
        );
    }

    public void clear() {
        maps.clear();
    }
}