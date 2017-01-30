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
import com.speedment.enterprise.license.runtime.component.AbstractLicenseComponent;
import com.speedment.enterprise.license.runtime.component.LicenseComponent;
import com.speedment.enterprise.license.runtime.component.SpeedmentLicenseComponent;
import com.speedment.enterprise.license.runtime.internal.io.LicenseIOUtil;
import com.speedment.enterprise.virtualcolumn.runtime.VirtualColumnBundle;
import com.speedment.runtime.core.ApplicationBuilder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.speedment.enterprise.datastore.runtime.DataStoreComponent;

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
            .withComponent(SpeedmentLicenseComponent.class)
            
            .withParam(LicenseComponent.LICENSE_KEY, 
                "zyabOXWnkqJDD0R8ZmFzdGNhY2hlLHZpcnR1YWwtY29sdW1uczs8Khijp5zP" + 
                "zY2jeERJ8SEH3bFYx+m1kRRoOiBXa194pF0xb5pNpYDOmCYyw/9e/c8X7iB3" + 
                "heWkK6RtFOhIVhINvrP7BSDDk8yl7veJGNVaHqJyt3qdH+qn30NUW/XgcbOu" + 
                "ADfsALvUNhJJ71lXH+eZ1HcX0yu24fFRYeWt5IuacQ=="
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