package com.extspeeder.example.financialdemo.controller;

import com.extspeeder.example.financialdemo.config.DemoConfig;
import com.extspeeder.example.financialdemo.controller.har.HarTester;
import com.extspeeder.example.financialdemo.db.prices.PriceStoreManager;
import com.google.gson.Gson;
import java.net.URISyntaxException;
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
public class PricesControllerTest extends AbstractSpeedmentTest {
    
    private MockMvc mockMvc;
    private HarTester tester;
    
    private @Autowired Gson gson;
    private @Autowired PriceStoreManager manager;

    @Before
    public void loadFromDatabase() throws URISyntaxException {
        mockMvc = MockMvcBuilders.standaloneSetup(
            new PricesController(gson, manager)
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
        System.out.println("Testing /speeder/prices");
        tester.stream()
            .filter(test -> test.getRequest().getPath().startsWith("/speeder/prices"))
            .forEach(test -> {
                System.out.println("Testing " + test.getRequest().getMethod() + ": " + test.getRequest().getPath() + " " + test.getRequest().getParams());
                test.execute(mockMvc);
            });
    }
}