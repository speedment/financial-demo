package com.extspeeder.example.financialdemo.controller;

import com.speedment.enterprise.datastore.runtime.DataStoreNotLoadedException;
import com.speedment.runtime.core.manager.Manager;

/**
 *
 * @author Emil Forslund
 * @since  1.1.8
 */
abstract class AbstractSpeedmentTest {
    
    protected final void waitUntilLoaded(Manager<?> manager) {
        for (int i = 0; i < 1000; i++) {
            try {
                final long count = manager.stream().count();
                break;
            } catch (final DataStoreNotLoadedException ex) {}
            
            try {
                Thread.sleep(500);
            } catch (final InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}