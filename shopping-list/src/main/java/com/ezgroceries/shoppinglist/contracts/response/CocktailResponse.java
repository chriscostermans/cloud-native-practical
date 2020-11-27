package com.ezgroceries.shoppinglist.contracts.response;

import java.util.List;
import java.util.UUID;

/**
 * @author Chris Costermans (u24390)
 * @since release/ (2020-11-02)
 */
public class CocktailResponse {

    private List<UUID> cocktailIds;

    public List<UUID> getCocktailIds() {
        return cocktailIds;
    }

    public void setCocktailIds(List<UUID> cocktailIds) {
        this.cocktailIds = cocktailIds;
    }
}
