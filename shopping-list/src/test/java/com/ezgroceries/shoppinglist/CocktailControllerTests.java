package com.ezgroceries.shoppinglist;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ezgroceries.shoppinglist.contracts.resources.CocktailResource;
import com.ezgroceries.shoppinglist.services.CocktailService;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

/**
 * @author Chris Costermans (u24390)
 * @since release/ (2020-11-03)
 */
//@AutoConfigureMockMvc
//@SpringBootTest
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ComponentScan({ "com.ezgroceries.shoppinglist", "config:" })
public class CocktailControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CocktailService cocktailService;

    @Test
    void getCocktailsTest() throws Exception {
        int expectedNumbersOfLists = 2;
        final String givenSearch = "Russian";
        final UUID expectedCocktailId = UUID.fromString("23b3d85a-3928-41c0-a533-6538a71e17c4");
        final String expectedName = "Margerita";
        final String expectedGlass = "Cocktail glass";
        final String expectedInstructions = "Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..";
        final String expectedImage = "https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg";
        final List<String> expectedIngredients = Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt");
        final UUID expectedcocktailId2 = UUID.fromString("d615ec78-fe93-467b-8d26-5d26d8eab073");
        final String expectedName2 = "Blue Margerita";
        final String expectedGlass2 = "Cocktail glass";
        final String expectedInstructions2 = "Rub rim of cocktail glass with lime juice. Dip rim in coarse salt..";
        final String expectedImage2 = "https://www.thecocktaildb.com/images/media/drink/qtvvyq1439905913.jpg";
        final List<String> expectedIngredients2 = Arrays.asList("Tequila", "Blue Curacao", "Lime juice", "Salt");
        List<CocktailResource> mockCocktails = new ArrayList<>();
        CocktailResource mockCocktailResource = new CocktailResource();
        mockCocktailResource.setCocktailId(expectedCocktailId);
        mockCocktailResource.setName(expectedName);
        mockCocktailResource.setGlass(expectedGlass);
        mockCocktailResource.setInstructions(expectedInstructions);
        mockCocktailResource.setImage(expectedImage);
        mockCocktailResource.setIngredients(expectedIngredients);
        mockCocktails.add(mockCocktailResource);
        mockCocktailResource = new CocktailResource();
        mockCocktailResource.setCocktailId(expectedcocktailId2);
        mockCocktailResource.setName(expectedName2);
        mockCocktailResource.setGlass(expectedGlass2);
        mockCocktailResource.setInstructions(expectedInstructions2);
        mockCocktailResource.setImage(expectedImage2);
        mockCocktailResource.setIngredients(expectedIngredients2);
        mockCocktails.add(mockCocktailResource);
        given(cocktailService.searchCocktails(givenSearch)).willReturn(mockCocktails);
        ResultActions resultActions = mockMvc.perform(get("/cocktails?search=Russian")
            .accept(MediaType.parseMediaType("application/json")))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.length()").value(expectedNumbersOfLists))
            .andExpect(jsonPath("$[0].name").value(expectedName))
            .andExpect(jsonPath("$[0].glass").value(expectedGlass))
            .andExpect(jsonPath("$[0].image").value(expectedImage))
            .andExpect(jsonPath("$[0].ingredients.length()").value(4))
            .andExpect(jsonPath("$[0].ingredients").value(Lists.newArrayList(expectedIngredients)))
            .andExpect(jsonPath("$[1].name").value(expectedName2))
            .andExpect(jsonPath("$[1].glass").value(expectedGlass2))
            .andExpect(jsonPath("$[1].image").value(expectedImage2))
            .andExpect(jsonPath("$[1].ingredients.length()").value(4))
            .andExpect(jsonPath("$[1].ingredients").value(Lists.newArrayList(expectedIngredients2)));
        verify(cocktailService).searchCocktails("Russian");
    }
}
