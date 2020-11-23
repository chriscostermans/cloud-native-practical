package com.ezgroceries.shoppinglist.resources;

import java.util.List;
import java.util.UUID;

/**
 * @author Chris Costermans (u24390)
 * @since release/ (2020-11-01)
 */
public class ShoppingList {

    private UUID shoppingListId;
    private String name;
    private List<UUID> cocktailId;

    public ShoppingList(){
    }

    public ShoppingList(UUID shoppingListId, String name) {
        this.shoppingListId = shoppingListId;
        this.name = name;
    }

    public ShoppingList(UUID shoppingListId, String name, List<UUID> cocktailId) {
        this.shoppingListId = shoppingListId;
        this.name = name;
        this.cocktailId = cocktailId;
    }

    public UUID getShoppingListId() {
        return shoppingListId;
    }

    public void setShoppingListId(UUID shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UUID> getCocktailId() {
        return cocktailId;
    }

    public void setCocktailId(List<UUID> cocktailId) {
        this.cocktailId = cocktailId;
    }
}
