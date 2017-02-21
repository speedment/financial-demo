package com.extspeeder.example.financialdemo.controller;

import com.extspeeder.example.financialdemo.config.DemoConfig;
import com.extspeeder.example.financialdemo.controller.har.HarTester;
import com.extspeeder.example.financialdemo.db.position.RawPosition;
import com.extspeeder.example.financialdemo.db.position.RawPositionManager;
import com.speedment.runtime.field.StringField;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 *
 * @author Emil Forslund
 * @since  1.1.8
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {
    DemoConfig.class
})
public class PositionsControllerTest extends AbstractSpeedmentTest {
    
    private MockMvc mockMvc;
    private HarTester tester;
    
    private @Autowired RawPositionManager manager;

    @Before
    public void loadFromDatabase() throws URISyntaxException {
        mockMvc = MockMvcBuilders.standaloneSetup(
            new PositionsController(manager)
        ).build();
        
        
        tester = HarTester.create(getClass().getResourceAsStream("/piq.xh.io.har"));
        
        waitUntilLoaded(manager);
    }
    
    /**
     * Test of handleGet method, of class OrdersController.
     * 
     * @throws java.lang.Exception  if something goes wrong
     */
    @Test
    public void testHandleGet() throws Exception {
        System.out.println("Testing /speeder/positions");
        tester.stream()
            .filter(test -> test.getRequest().getPath().startsWith("/speeder/positions"))
            .forEach(test -> {
                System.out.println("Testing " + test.getRequest().getMethod() + ": " + test.getRequest().getPath() + " " + test.getRequest().getParams());
                test.execute(mockMvc);
            });
    }
    
    @Test
    public void verifyInvalidUtf8Bug() throws Exception {
        count(RawPosition.INSTRUMENT_INDUSTRY);
        count(RawPosition.INSTRUMENT_NAME);
        count(RawPosition.INSTRUMENT_SECTOR);
        count(RawPosition.INSTRUMENT_SYMBOL);
        count(RawPosition.TRADER_GROUP);
        count(RawPosition.TRADER_GROUP_TYPE);
        count(RawPosition.TRADER_NAME);
    }
    
    
    private void count(StringField<RawPosition, ?> field) {
        final AtomicLong counter = new AtomicLong();
        
        manager.stream()
            .filter(field.contains("abc"))
            .forEachOrdered(e -> counter.incrementAndGet());
        
        System.out.format(
            "%40s: %,d matches.%n", 
            field.identifier(), 
            counter.get()
        );
    }
}