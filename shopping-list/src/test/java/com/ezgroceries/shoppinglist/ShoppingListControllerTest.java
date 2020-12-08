package com.ezgroceries.shoppinglist;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ezgroceries.shoppinglist.contracts.response.ShoppingListResponse;
import com.ezgroceries.shoppinglist.contracts.resources.ShoppingListResource;
import com.ezgroceries.shoppinglist.services.ShoppingListService;
import com.google.common.collect.Lists;
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
        final UUID expectedId = UUID.fromString("eb18bb7c-61f3-4c9f-981c-55b1b8ee8915");
        final String expectedName = "Stephanie's birthday";
        ShoppingListResource mockShoppingList = new ShoppingListResource();
        mockShoppingList.setShoppingListId(expectedId);
        mockShoppingList.setName(expectedName);
        given(shoppingListService.createShoppingList(givenName)).willReturn(mockShoppingList);
        this.mockMvc.perform(post("/shopping-lists")
            .accept(MediaType.parseMediaType("application/json"))
            .content("Stephanie's birthday")
            .contentType(MediaType.parseMediaType("application/json")))
            .andExpect(status().isCreated())      // MockHttpServletResponse: Status = 200
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("shoppingListId").value("eb18bb7c-61f3-4c9f-981c-55b1b8ee8915"))
            .andExpect(jsonPath("$.name").value(expectedName));
        verify(shoppingListService).createShoppingList(givenName);
    }


    @Test
    public void addCocktailsToShoppingListTest() throws Exception {
        final UUID givenId = UUID.fromString("97c8e5bd-5353-426e-b57b-69eb2260ace3");
        int expectedNumbersOfLists = 2;
        final String expectedCocktailId = "23b3d85a-3928-41c0-a533-6538a71e17c4";       // body
        final String expectedcocktailId2 ="d615ec78-fe93-467b-8d26-5d26d8eab073";
        List<String> mockCocktails = new ArrayList<>();
        mockCocktails.add(expectedCocktailId);
        mockCocktails.add(expectedcocktailId2);
        given(shoppingListService.addCocktailsToShoppingList(givenId, mockCocktails)).willReturn(mockCocktails);
//        this.mockMvc.perform(post("/shopping-lists/{shoppingListId}/cocktails")
        this.mockMvc.perform(post("/shopping-lists/97c8e5bd-5353-426e-b57b-69eb2260ace3/cocktails")
            .accept(MediaType.parseMediaType("application/json"))
            .content("[{\"23b3d85a-3928-41c0-a533-6538a71e17c4\"}, {\"d615ec78-fe93-467b-8d26-5d26d8eab073\"}]")
            .contentType("application/json"));
//            .andExpect(status().isOk())
//            .andExpect(content().contentType("application/json"))
//            .andExpect(jsonPath("$.length()").value(expectedNumbersOfLists))
//            .andExpect(jsonPath("[0].cocktailId").value(expectedCocktailId))
//            .andExpect(jsonPath("[1].cocktailId").value(expectedcocktailId2));
    }


    @Test
    public void getShoppingListIngredientsTest() throws Exception {
        int expectedNumbersOfIngredients = 5;
        final UUID givenId = UUID.fromString("eb18bb7c-61f3-4c9f-981c-55b1b8ee8915");
        final UUID expectedId = UUID.fromString("eb18bb7c-61f3-4c9f-981c-55b1b8ee8915");
        final String expectedName = "Stephanie's birthday";                     // response
        final List<String> expectedIngredients = Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao");  // response
        ShoppingListResource mockShoppingListResource = new ShoppingListResource();
        mockShoppingListResource.setShoppingListId(expectedId);
        mockShoppingListResource.setName(expectedName);
        mockShoppingListResource.setIngredients(expectedIngredients);
        given(shoppingListService.getShoppingListIngredients(givenId)).willReturn(mockShoppingListResource);
//        this.mockMvc.perform(get("/shopping-lists/{shoppingListId}")
        this.mockMvc.perform(get("/shopping-lists/eb18bb7c-61f3-4c9f-981c-55b1b8ee8915")
            .accept(MediaType.parseMediaType("application/json")))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.shoppingListId").value("eb18bb7c-61f3-4c9f-981c-55b1b8ee8915"))
            .andExpect(jsonPath("$.name").value(expectedName))
            .andExpect(jsonPath("$.ingredients.length()").value(expectedNumbersOfIngredients))
            .andExpect(jsonPath("$.ingredients").value(Lists.newArrayList(expectedIngredients)));
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
        List<ShoppingListResource> mockShoppingLists = new ArrayList<>();
        ShoppingListResource mockShoppingListResource = new ShoppingListResource();
        mockShoppingListResource.setShoppingListId(expectedId);
        mockShoppingListResource.setName(expectedName);
        mockShoppingListResource.setIngredients(expectedIngredients);
        mockShoppingLists.add(mockShoppingListResource);
        mockShoppingListResource = new ShoppingListResource();
        mockShoppingListResource.setShoppingListId(expectedId2);
        mockShoppingListResource.setName(expectedName2);
        mockShoppingListResource.setIngredients(expectedIngredients2);
        mockShoppingLists.add(mockShoppingListResource);
        given(shoppingListService.getShoppingLists()).willReturn(mockShoppingLists);
        this.mockMvc.perform(get("/shopping-lists")
            .accept(MediaType.parseMediaType("application/json")))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.length()").value(expectedNumbersOfLists))
            .andExpect(jsonPath("$[0].shoppingListId").value("4ba92a46-1d1b-4e52-8e38-13cd56c7224c"))
            .andExpect(jsonPath("$[0].name").value(expectedName))
            .andExpect(jsonPath("$[0].ingredients.length()").value(expectedNumbersOfIngredients))
            .andExpect(jsonPath("$[0].ingredients").value(Lists.newArrayList(expectedIngredients)))
            .andExpect(jsonPath("$[1].shoppingListId").value("6c7d09c2-8a25-4d54-a979-25ae779d2465"))
            .andExpect(jsonPath("$[1].name").value(expectedName2))
            .andExpect(jsonPath("$[1].ingredients.length()").value(expectedNumbersOfIngredients))
            .andExpect(jsonPath("$[1].ingredients").value(Lists.newArrayList(expectedIngredients2)));
        verify(shoppingListService).getShoppingLists();
    }
}
