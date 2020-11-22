package com.ezgroceries.shoppinglist.internal.cocktail;

import java.util.List;
import java.util.UUID;

/**
 * @author Chris Costermans (u24390)
 * @since release/ (2020-11-20)
 */
public interface CocktailRepository {

    List<CocktailEntity> findByIdDrinkIn(List<String> cocktailIds);
    List<CocktailEntity> findAllByIdDrinkIn(List<UUID> cocktailIds);
}
