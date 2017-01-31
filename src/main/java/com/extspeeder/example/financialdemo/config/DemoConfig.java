package com.extspeeder.example.financialdemo.config;

import com.extspeeder.example.financialdemo.FinancialdemoApplication;
import com.extspeeder.example.financialdemo.FinancialdemoApplicationBuilder;
import com.extspeeder.example.financialdemo.db.order.OrderManager;
import com.extspeeder.example.financialdemo.db.position.RawPositionManager;
import com.extspeeder.example.financialdemo.db.prices.PriceStoreManager;
import com.google.gson.Gson;
import com.speedment.common.logger.Level;
import com.speedment.common.logger.LoggerManager;
import com.speedment.enterprise.datastore.runtime.DataStoreBundle;
import com.speedment.enterprise.datastore.runtime.DataStoreComponent;
import com.speedment.enterprise.license.runtime.component.AbstractLicenseComponent;
import com.speedment.enterprise.license.runtime.component.LicenseComponent;
import com.speedment.enterprise.license.runtime.internal.io.LicenseIOUtil;
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
    
    @Bean
    public Gson getGson() {
        return new Gson();
    }
    
    @Bean
    public ExecutorService getExecutorService() {
        return Executors.newFixedThreadPool(reloadingCores);
    }

    @Bean
    public FinancialdemoApplication getApplication(ExecutorService scheduler) {
        
        LoggerManager.getLogger(AbstractLicenseComponent.class).setLevel(Level.DEBUG);
        LoggerManager.getLogger(LicenseIOUtil.class).setLevel(Level.DEBUG);
        
        final FinancialdemoApplication app = new FinancialdemoApplicationBuilder()
            .withIpAddress(host)
            .withPort(port)
            .withUsername(username)
            .withPassword(password)
            .withSchema(schema)
            
            // The order of the following two is important.
            .withBundle(VirtualColumnBundle.class)
            .withBundle(DataStoreBundle.class)
            
            .withParam(LicenseComponent.LICENSE_KEY, 
                "aOkINrKGOmBDLESZZGF0YXN0b3JlLHZpcnR1YWwtY29sdW1ucztkcsDDzd+H" +
                "XcB+o8U3PmgsrcyD6OICfRJIKOn57c2juAZ/rToykN+v7NicqcFr3Y/1kMMp" + 
                "5wRseLAqXOdtXZ/r9+PXJqktL+NHkZ7m0Vy4/1MMRyezkJeJ+todZci761hi" + 
                "kz6SSy0zN73TvnCfBkiokqSR17k8HQUYHaeK0BC1XA=="
            )
            
            .withLogging(ApplicationBuilder.LogType.PERSIST)
            .withLogging(ApplicationBuilder.LogType.UPDATE)
            .withLogging(ApplicationBuilder.LogType.REMOVE)
            .withLogging(ApplicationBuilder.LogType.APPLICATION_BUILDER)
            .withLogging(ApplicationBuilder.LogType.STREAM)
            .build();
        
        final DataStoreComponent streamSupplier =
            app.getOrThrow(DataStoreComponent.class);
        
        scheduler.submit(() -> streamSupplier.reload(scheduler));
        
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
    public DataStoreComponent getStreamSupplier(FinancialdemoApplication app) {
        return app.getOrThrow(DataStoreComponent.class);
    }
}