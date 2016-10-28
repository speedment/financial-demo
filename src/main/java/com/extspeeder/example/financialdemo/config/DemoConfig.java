package com.extspeeder.example.financialdemo.config;

import com.extspeeder.example.financialdemo.financialdemo.FinancialdemoApplication;
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
    
    private @Value("${dbms.username}") String username;
    private @Value("${dbms.password}") String password;

    @Bean
    public Speedment getSpeedment() {
        return new FinancialdemoApplication()
            .withUsername(username)
            .withPassword(password)
            .with(OffHeapReadOnlyCacheComponent::createOffHeap)
            .build();
    }
    
    @Bean
    public RawPositionManager getRawPositionManager(Speedment speedment) {
        return (RawPositionManager) speedment.managerOf(RawPosition.class);
    }
}