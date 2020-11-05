package com.ezgroceries.shoppinglist.controllers;

import com.ezgroceries.shoppinglist.resources.CocktailResource;
import com.ezgroceries.shoppinglist.resources.ShoppingList;
import com.ezgroceries.shoppinglist.resources.ShoppingListIngredients;
import com.ezgroceries.shoppinglist.services.ShoppingListService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Chris Costermans (u24390)
 * @since release/ (2020-11-01)
 * Shopping List API
 */
@RestController
//@RequestMapping(value = "/shopping-lists", produces = "application/json")     // verplaatst naar Service-call niveau
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    @Autowired
    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }


//    Create a new shopping list
//    @PostMapping(consumes = "application/json")
//    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @RequestMapping(value = "/shopping-lists", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingList createShoppingList(@RequestBody ShoppingList shoppingList) {
        return shoppingListService.createShoppingList(shoppingList);
    }


//    Add cocktails to a shopping list
    @PostMapping
    @RequestMapping(value = "/shopping-lists/{shoppingListId}/cocktails", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
//    public CocktailList addCocktailsToShoppingList(@PathVariable UUID shoppingListId, @RequestBody List<CocktailResource> cocktailResource) {
    public List<UUID> addCocktailsToShoppingList(@PathVariable UUID shoppingListId, @RequestBody List<CocktailResource> cocktailResource) {
        return shoppingListService.addCocktailsToShoppingList(shoppingListId, cocktailResource);
    }


//    Get a shopping list distinct ingredients
    @GetMapping
    @RequestMapping(value = "/shopping-lists/{shoppingListId}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ShoppingListIngredients getShoppingListsIngredients(@PathVariable UUID shoppingListId) {
        return shoppingListService.getShoppingListIngredients(shoppingListId);
    }


//    Get all shopping lists
    @GetMapping
    @RequestMapping(value = "/shopping-lists", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<ShoppingListIngredients> getShoppingLists() {
        return shoppingListService.getShoppingLists();
    }

}
