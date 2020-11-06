package com.ezgroceries.shoppinglist.services;

import com.ezgroceries.shoppinglist.resources.CocktailResource;
import com.ezgroceries.shoppinglist.resources.ShoppingList;
import com.ezgroceries.shoppinglist.resources.ShoppingListIngredients;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * @author Chris Costermans (u24390)
 * @since release/ (2020-11-01)
 */
@Service
public class ShoppingListService {

    ShoppingList shoppingList = new ShoppingList();

    public ShoppingList createShoppingList(ShoppingList shoppingList){
        shoppingList.setShoppingListId(UUID.randomUUID());
        return shoppingList;
    }

//    public CocktailList addCocktailsToShoppingList(UUID shoppingListId, List<CocktailResource> cocktailResource){
    public List<UUID> addCocktailsToShoppingList(UUID shoppingListId, List<CocktailResource> cocktailResource){
        shoppingList.setShoppingListId(shoppingListId);
        shoppingList.setCocktailId(cocktailResource.stream().map(this::fillCocktailId).collect(Collectors.toList()));

        List<UUID> cocktailIds = new ArrayList<UUID>(cocktailResource.size());
        for (CocktailResource cocktail : cocktailResource) {
            cocktailIds.add(cocktail.getCocktailId());
        }
        return cocktailIds;

//        Ter info : alternatieven voor for-loop : stream en gewone for-loop
//        CocktailList cocktailList = new CocktailList();
//        cocktailList.setCocktailIds(cocktailResource.stream().map(this::fillCocktailId).collect(Collectors.toList()));
//        return cocktailList;

//        List<UUID> cocktailIds = new ArrayList<>();
//        int size = cocktailResource.size();
//        for (int i = 0; i < size; i++){
//            cocktailIds.add(cocktailResource.get(i).getCocktailId());
//        }
//        return cocktailIds;
    }

    private UUID fillCocktailId(CocktailResource cocktailResources)  {
//        CocktailList cocktailId = new CocktailList();
        UUID id = cocktailResources.getCocktailId();
        return id;
    }

    public ShoppingListIngredients getShoppingListIngredients(UUID shoppingListId) {
        ShoppingListIngredients shoppingListIngredients = new ShoppingListIngredients(
            shoppingListId,
            "Stephanie's birthday",
            Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao"));
        return shoppingListIngredients;
    }

    public List<ShoppingListIngredients> getShoppingLists() {
        List<ShoppingListIngredients> shoppingListIngredients = new ArrayList<ShoppingListIngredients>();
        shoppingListIngredients = Arrays.asList(
            new ShoppingListIngredients(
            UUID.fromString("4ba92a46-1d1b-4e52-8e38-13cd56c7224c"),
            "Stephanie's birthday",
            Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao")),
            new ShoppingListIngredients(
            UUID.fromString("6c7d09c2-8a25-4d54-a979-25ae779d2465"),
            "My Birthday",
            Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao")));
        return shoppingListIngredients;
    }
}