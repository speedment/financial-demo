package com.extspeeder.example.financialdemo.controller;

import com.extspeeder.example.financialdemo.config.DemoConfig;
import com.extspeeder.example.financialdemo.controller.har.HarTester;
import com.extspeeder.example.financialdemo.db.order.OrderManager;
import com.google.gson.Gson;
import java.net.URISyntaxException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
public class OrdersControllerTest extends AbstractSpeedmentTest {
    
    private MockMvc mockMvc;
    private HarTester tester;
    
    private @Autowired Gson gson;
    private @Autowired OrderManager manager;

    @Before
    public void loadFromDatabase() throws URISyntaxException {
        mockMvc = MockMvcBuilders.standaloneSetup(
            new OrdersController(gson, manager)
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
        System.out.println("Testing /speeder/orders");
        tester.stream()
            .filter(test -> test.getRequest().getPath().startsWith("/speeder/orders"))
            .forEach(test -> {
                System.out.println("Testing " + test.getRequest().getMethod() + ": " + test.getRequest().getPath() + " " + test.getRequest().getParams());
                test.execute(mockMvc);
            });
    }
    
    @Test
    public void testVerifyCohortTypeBug() throws Exception {

        mockMvc.perform(get("/speeder/orders")
            .param("_dc", "1487373594073")
            .param("start", "0")
            .param("limit", "300")
            .param("sort", "[{\"property\":\"dateCreated\",\"direction\":\"DESC\"}]")
            .param("filter", "[{\"property\":\"dateCreated\",\"value\":\"20150606\",\"operator\":\"le\"},{\"property\":\"traderGroupType\",\"value\":\"San Francisco\",\"operator\":\"eq\"}]")
        ).andExpect(status().is2xxSuccessful());
    }
}