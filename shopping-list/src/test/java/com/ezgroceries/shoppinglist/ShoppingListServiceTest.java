package com.ezgroceries.shoppinglist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ezgroceries.shoppinglist.internal.cocktail.CocktailEntity;
import com.ezgroceries.shoppinglist.internal.shoppingList.ShoppingListEntity;
import com.ezgroceries.shoppinglist.internal.shoppingList.ShoppingListRepository;
import com.ezgroceries.shoppinglist.resources.ShoppingList;
import com.ezgroceries.shoppinglist.resources.ShoppingListIngredients;
import com.ezgroceries.shoppinglist.services.CocktailService;
import com.ezgroceries.shoppinglist.services.ShoppingListService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Chris Costermans (u24390)
 * @since release/ (2020-11-26)
 * A Junit test case testing the ShoppingListService.
 */
public class ShoppingListServiceTest {

    private CocktailService cocktailService = mock(CocktailService.class);
    private ShoppingListService shoppingListService;
    private ShoppingListRepository shoppingListRepository = mock(ShoppingListRepository.class);

    @BeforeEach
    public void setup() throws Exception {
        shoppingListService = new ShoppingListService(shoppingListRepository, cocktailService);
    }

    @Test
    public void testCreateShoppingList() {
        ShoppingList newShoppingList = shoppingListService.createShoppingList("Stephanie's birthday");
        assertNotNull(newShoppingList, "shoppingList should never be null");
        assertNotNull(newShoppingList.getShoppingListId(),"shoppingListId should never be null");
        assertEquals("Stephanie's birthday", newShoppingList.getName(), "wrong name");
    }

    @Test
    public void testAddCocktailsToShoppingList() {
        UUID shoppingListId = UUID.fromString("97c8e5bd-5353-426e-b57b-69eb2260ace3");
        String name = "Stephanie's birthday";
        String cocktailId = "23b3d85a-3928-41c0-a533-6538a71e17c4";
        String cocktailId2 = "d615ec78-fe93-467b-8d26-5d26d8eab073";
        List<String> cocktails = Arrays.asList(cocktailId, cocktailId2);
        ShoppingListEntity shoppingListEntity = new ShoppingListEntity(name);
        List<CocktailEntity> foundCocktails = new ArrayList<>();
        CocktailEntity cocktailEntity = new CocktailEntity();
        cocktailEntity.setId(UUID.fromString(cocktailId));
        cocktailEntity.setName("Margerita");
        foundCocktails.add(cocktailEntity);
        cocktailEntity.setId(UUID.fromString(cocktailId2));
        cocktailEntity.setName("Blue Margerita");
        foundCocktails.add(cocktailEntity);
        when(cocktailService.findByCocktailId(cocktails)).thenReturn(foundCocktails);
        when(shoppingListRepository.findById(shoppingListId)).thenReturn(Optional.of(shoppingListEntity));
        List<String> updatedShoppingList = shoppingListService.addCocktailsToShoppingList(shoppingListId, cocktails);
        assertNotNull(updatedShoppingList.size(), "shoppingList should never be null");
        verify(shoppingListRepository).save(shoppingListEntity);
    }

    @Test
    public void testGetShoppingListIngredients() {
        UUID shoppingListId = UUID.fromString("eb18bb7c-61f3-4c9f-981c-55b1b8ee8915");
        String name = "Stephanie's birthday";
        List<String> foundingredients = Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao");
        ShoppingListEntity foundShoppingList = new ShoppingListEntity(name);
        when(shoppingListRepository.findById(shoppingListId)).thenReturn(Optional.of(foundShoppingList));
        ShoppingListIngredients shoppingListIngredients = shoppingListService.getShoppingListIngredients(shoppingListId);
        List<String> in = shoppingListIngredients.getIngredients();
        assertNotNull(shoppingListIngredients, "shoppingList should never be null");
        assertEquals("Stephanie's birthday", shoppingListIngredients.getName(), "wrong name");
        assertNotNull(shoppingListIngredients.getIngredients().size(), "ingredients should never be null");
    }

    @Test
    public void testGetShoppingLists() {
        int expectedNumbersOfLists = 2;
        String name = "Stephanie's birthday";
        String name2 = "My birthday";
        ShoppingListEntity shoppingListEntity = new ShoppingListEntity(name);
        ShoppingListEntity shoppingListEntity2 = new ShoppingListEntity(name2);
        List<ShoppingListEntity> foundshoppingLists = Arrays.asList(shoppingListEntity,shoppingListEntity2);
        when(shoppingListRepository.findAll()).thenReturn(foundshoppingLists);
        List<ShoppingListIngredients> shoppingLists = shoppingListService.getShoppingLists();
        assertNotNull(shoppingLists, "shoppingLists should never be null");
        assertEquals(expectedNumbersOfLists, shoppingLists.size(),"shoppingList size should be 2");
        assertEquals(name,shoppingLists.get(0).getName(),"wrong name");
        assertEquals(name2,shoppingLists.get(1).getName(),"wrong name2");
    }

}
