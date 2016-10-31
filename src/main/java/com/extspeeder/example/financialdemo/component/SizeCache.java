package com.extspeeder.example.financialdemo.component;

import static com.speedment.util.NullUtil.requireNonNulls;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.LongSupplier;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
public class SizeCache {

    private final Map<String, Map<String, Long>> maps;

    public SizeCache() {
        this.maps = new ConcurrentHashMap<>();
    }

    public Long computeIfAbsent(String path, String filter, LongSupplier supplier) {
        requireNonNulls(path, filter, supplier);
        return maps
            .computeIfAbsent(path, $ -> new ConcurrentHashMap<>())
            .computeIfAbsent(filter, $ -> supplier.getAsLong());
    }

    public void clear() {
        maps.clear();
    }
}