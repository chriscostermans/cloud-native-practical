package com.ezgroceries.shoppinglist;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Chris Costermans (u24390)
 * @since release/ (2020-11-03)
 */
//@AutoConfigureMockMvc
//@SpringBootTest
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ComponentScan({ "com.ezgroceries.shoppinglist", "config:" })
public class CocktailControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCocktailsTest() throws Exception {
        mockMvc.perform(get("/cocktails?search=Russian")
            .accept(MediaType.parseMediaType("application/json")))
//        Resolved [org.springframework.web.bind.MissingServletRequestParameterException: Required String parameter 'search' is not present]
//        MockHttpServletResponse:
//        Status = 400
//        Error message = Required String parameter 'search' is not present
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"));
    }
}
