package com.ezgroceries.shoppinglist;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ezgroceries.shoppinglist.resources.ShoppingList;
import com.ezgroceries.shoppinglist.resources.ShoppingListIngredients;
import com.ezgroceries.shoppinglist.services.ShoppingListService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Chris Costermans (u24390)
 * @since release/ (2020-11-03)
 */
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ComponentScan({ "com.ezgroceries.shoppinglist", "config:" })
public class ShoppingListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingListService shoppingListService;

//    @Resource
//    private ShoppingList shoppingList;

//    private Gson gson;

    @Test
    public void createShoppingListTest() throws Exception {
        final String givenName = "Stephanie's birthday";
        final UUID expectedId = UUID.fromString("eb18bb7c-61f3-4c9f-981c-55b1b8ee8915");    // body
        final String expectedName = "Stephanie's birthday";     // body
        ShoppingList mockShoppingList = new ShoppingList();
        mockShoppingList.setShoppingListId(expectedId);
        mockShoppingList.setName(expectedName);
        given(shoppingListService.createShoppingList(givenName)).willReturn(mockShoppingList);
//        String jsonIn = gson.toJson(shoppingList);
//        String jsonOut = gson.toJson(service.simulateHomeLoan(request, CLIENT_ID, SCOPE_ID));
        this.mockMvc.perform(post("/shopping-lists")
            .accept(MediaType.parseMediaType("application/json"))
            .content("{\"name\": \"Stephanie's birthday\"}")
            .contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))
            .andExpect(status().isCreated())      // MockHttpServletResponse: Status = 200
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("shoppingListId").value(expectedId))
            .andExpect(jsonPath("name").value(expectedName)); // java.lang.AssertionError: No value at JSON path "$.name"
        verify(shoppingListService).createShoppingList(givenName);
    }


    @Test
    public void addCocktailsToShoppingListTest() throws Exception {
//        shoppingListId = "97c8e5bd-5353-426e-b57b-69eb2260ace3"   // pathvariable
        int expectedNumbersOfLists = 2;
        final String expectedCocktailId = "23b3d85a-3928-41c0-a533-6538a71e17c4";       // body
        final String expectedcocktailId2 ="d615ec78-fe93-467b-8d26-5d26d8eab073";       // body
//        this.mockMvc.perform(post("/shopping-lists/{shoppingListId}/cocktails")
        this.mockMvc.perform(post("/shopping-lists/97c8e5bd-5353-426e-b57b-69eb2260ace3/cocktails")
            .accept(MediaType.parseMediaType("application/json")))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.length()").value(expectedNumbersOfLists))
            .andExpect(jsonPath("[0].cocktailId").value(expectedCocktailId))
            .andExpect(jsonPath("[1].cocktailId").value(expectedcocktailId2));
    }


    @Test
    public void getShoppingListIngredientsTest() throws Exception {
        int expectedNumbersOfIngredients = 5;
        final UUID givenId = UUID.fromString("eb18bb7c-61f3-4c9f-981c-55b1b8ee8915");
        final UUID expectedId = UUID.fromString("eb18bb7c-61f3-4c9f-981c-55b1b8ee8915");
        final String expectedName = "Stephanie's birthday";                     // response
        final List<String> expectedIngredients = Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao");  // response
        ShoppingListIngredients mockShoppingListIngredients = new ShoppingListIngredients();
        mockShoppingListIngredients.setShoppingListId(expectedId);
        mockShoppingListIngredients.setName(expectedName);
        mockShoppingListIngredients.setIngredients(expectedIngredients);
        given(shoppingListService.getShoppingListIngredients(givenId)).willReturn(mockShoppingListIngredients);
//        this.mockMvc.perform(get("/shopping-lists/{shoppingListId}")
        this.mockMvc.perform(get("/shopping-lists/eb18bb7c-61f3-4c9f-981c-55b1b8ee8915")
            .accept(MediaType.parseMediaType("application/json")))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.shoppingListId").value(expectedId))
            .andExpect(jsonPath("$.name").value(expectedName))
            .andExpect(jsonPath("$.length()").value(expectedNumbersOfIngredients))
            .andExpect(jsonPath("$.ingredients").value(expectedIngredients));
        verify(shoppingListService).getShoppingListIngredients(givenId);
    }


    @Test
    void getShoppingListsTest() throws Exception {
        int expectedNumbersOfLists = 2;
        int expectedNumbersOfIngredients = 5;
        final UUID expectedId = UUID.fromString("4ba92a46-1d1b-4e52-8e38-13cd56c7224c");
        final String expectedName = "Stephanie's birthday";
        final List<String> expectedIngredients = Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao");
        final UUID expectedId2 = UUID.fromString("6c7d09c2-8a25-4d54-a979-25ae779d2465");
        final String expectedName2 = "My birthday";
        final List<String> expectedIngredients2 = Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao");
        List<ShoppingListIngredients> mockShoppingLists = new ArrayList<>();
        ShoppingListIngredients mockShoppingListIngredients = new ShoppingListIngredients();
        mockShoppingListIngredients.setShoppingListId(expectedId);
        mockShoppingListIngredients.setName(expectedName);
        mockShoppingListIngredients.setIngredients(expectedIngredients);
        mockShoppingLists.add(mockShoppingListIngredients);
        mockShoppingListIngredients.setShoppingListId(expectedId2);
        mockShoppingListIngredients.setName(expectedName2);
        mockShoppingListIngredients.setIngredients(expectedIngredients2);
        mockShoppingLists.add(mockShoppingListIngredients);
        given(shoppingListService.getShoppingLists()).willReturn(mockShoppingLists);
        this.mockMvc.perform(get("/shopping-lists")
            .accept(MediaType.parseMediaType("application/json")))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.length()").value(expectedNumbersOfLists))
            .andExpect(jsonPath("$[0].shoppingListId").value(expectedId))
            .andExpect(jsonPath("$[0].name").value(expectedName))
            .andExpect(jsonPath("$[0].length()").value(expectedNumbersOfIngredients))
            .andExpect(jsonPath("$[0].ingredients").value(expectedIngredients))
            .andExpect(jsonPath("$[0].shoppingListId").value(expectedId2))
            .andExpect(jsonPath("$[0].name").value(expectedName2))
            .andExpect(jsonPath("$[0].length()").value(expectedNumbersOfIngredients))
            .andExpect(jsonPath("$[0].ingredients").value(expectedIngredients2));
        verify(shoppingListService).getShoppingLists();
    }
}
