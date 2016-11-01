package com.extspeeder.example.financialdemo.config;

import com.extspeeder.example.financialdemo.financialdemo.FinancialdemoApplication;
import com.extspeeder.example.financialdemo.financialdemo.db.piq.order.Order;
import com.extspeeder.example.financialdemo.financialdemo.db.piq.order.OrderManager;
import com.extspeeder.example.financialdemo.financialdemo.db.piq.price_store.PriceStore;
import com.extspeeder.example.financialdemo.financialdemo.db.piq.price_store.PriceStoreManager;
import com.extspeeder.example.financialdemo.financialdemo.db.piq.raw_position.RawPosition;
import com.extspeeder.example.financialdemo.financialdemo.db.piq.raw_position.RawPositionManager;
import com.speedment.Speedment;
import com.speedment.enterprise.offheapreadonlycache.OffHeapReadOnlyCacheComponent;
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
    
    public final static String 
        ORDERS      = "financialdemo.db0.piq.orders",
        PRICE_STORE = "financialdemo.db0.piq.price_store",
        POSITIONS   = "financialdemo.db0.piq.daily_position_performance";
    
    private @Value("${dbms.username}") String username;
    private @Value("${dbms.password}") String password;
    private @Value("${dbms.schema}") String schema;

    @Bean
//    @Qualifier("onheap")
    public Speedment getOnheapSpeedment() {
        return new FinancialdemoApplication()
//            .with(Table.class, ORDERS, DemoConfig::disable)
//            .with(Table.class, PRICE_STORE, DemoConfig::disable)
            .withUsername(username)
            .withPassword(password)
            .withSchema(schema)
            .with(OffHeapReadOnlyCacheComponent::createOnHeap)
            .build();
    }
    
//    @Bean
//    @Qualifier("offheap")
//    public Speedment getOffheapSpeedment() {
//        return new FinancialdemoApplication()
//            .with(Table.class, POSITIONS, DemoConfig::disable)
//            .withUsername(username)
//            .withPassword(password)
//            .withSchema(schema)
//            .with(OffHeapReadOnlyCacheComponent::createOffHeap)
//            .build();
//    }
    
    @Bean
    public RawPositionManager getRawPositionManager(/*@Qualifier("onheap") */Speedment speedment) {
        return (RawPositionManager) speedment.managerOf(RawPosition.class);
    }
    
    @Bean
    public OrderManager getOrderManager(/*@Qualifier("offheap") */Speedment speedment) {
        return (OrderManager) speedment.managerOf(Order.class);
    }
    
    @Bean
    public PriceStoreManager getPriceStoreManager(/*@Qualifier("offheap") */Speedment speedment) {
        return (PriceStoreManager) speedment.managerOf(PriceStore.class);
    }
//    
//    private static void disable(Table table) {
//        System.out.println("Disabling " + table.getName());
//        table.mutator().setEnabled(false);
//    }
}