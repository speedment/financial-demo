package com.extspeeder.example.financialdemo.config;

import com.extspeeder.example.financialdemo.FinancialdemoApplication;
import com.extspeeder.example.financialdemo.FinancialdemoApplicationBuilder;
import com.extspeeder.example.financialdemo.db.order.OrderManager;
import com.extspeeder.example.financialdemo.db.position.RawPositionManager;
import com.extspeeder.example.financialdemo.db.prices.PriceStoreManager;
import com.google.gson.Gson;
import com.speedment.enterprise.fastcache.runtime.FastCacheBundle;
import com.speedment.enterprise.fastcache.runtime.FastCacheStreamSupplierComponent;
import com.speedment.enterprise.virtualcolumn.runtime.VirtualColumnBundle;
import com.speedment.runtime.core.ApplicationBuilder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
@Configuration
public class DemoConfig {
    
    private @Value("${dbms.host}") String host;
    private @Value("${dbms.port}") int port;
    private @Value("${dbms.username}") String username;
    private @Value("${dbms.password}") String password;
    private @Value("${dbms.schema}") String schema;
    
    private @Value("${reload.cores}") int reloadingCores;
    private @Value("${reload.interval}") int reloadInterval; // Seconds
    
    @Bean
    public Gson getGson() {
        return new Gson();
    }
    
    @Bean
    public ExecutorService getExecutorService() {
//        return Executors.newScheduledThreadPool(reloadingCores);
        return Executors.newFixedThreadPool(reloadingCores);
    }

    @Bean
    public FinancialdemoApplication getApplication(ExecutorService scheduler) {
        final FinancialdemoApplication app = new FinancialdemoApplicationBuilder()
            .withIpAddress(host)
            .withPort(port)
            .withUsername(username)
            .withPassword(password)
            .withSchema(schema)
            
            // The order of the following two is important.
            .withBundle(VirtualColumnBundle.class)
            .withBundle(FastCacheBundle.class)
            
            .withLogging(ApplicationBuilder.LogType.PERSIST)
            .withLogging(ApplicationBuilder.LogType.UPDATE)
            .withLogging(ApplicationBuilder.LogType.REMOVE)
            .withLogging(ApplicationBuilder.LogType.APPLICATION_BUILDER)
            .withLogging(ApplicationBuilder.LogType.STREAM)
            .build();
        
        final FastCacheStreamSupplierComponent streamSupplier =
            app.getOrThrow(FastCacheStreamSupplierComponent.class);
        
        scheduler.submit(() -> streamSupplier.reload(scheduler));
        
//        scheduler.scheduleAtFixedRate(
//            () -> streamSupplier.reload(scheduler),
//            0, reloadInterval, 
//            TimeUnit.SECONDS
//        );
        
        return app;
    }
    
    @Bean
    public RawPositionManager getRawPositionManager(FinancialdemoApplication app) {
        return app.getOrThrow(RawPositionManager.class);
    }
    
    @Bean
    public OrderManager getOrderManager(FinancialdemoApplication app) {
        return app.getOrThrow(OrderManager.class);
    }
    
    @Bean
    public PriceStoreManager getPriceStoreManager(FinancialdemoApplication app) {
        return app.getOrThrow(PriceStoreManager.class);
    }
    
    @Bean
    public FastCacheStreamSupplierComponent getStreamSupplier(FinancialdemoApplication app) {
        return app.getOrThrow(FastCacheStreamSupplierComponent.class);
    }
}