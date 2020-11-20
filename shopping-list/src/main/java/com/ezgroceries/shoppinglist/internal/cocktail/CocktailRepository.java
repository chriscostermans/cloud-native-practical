package com.ezgroceries.shoppinglist.internal.cocktail;

import java.util.List;

/**
 * @author Chris Costermans (u24390)
 * @since release/ (2020-11-20)
 */
public interface CocktailRepository {

    List<CocktailEntity> findByIdDrink(String idDrink);
    List<CocktailEntity> findAll();
}
