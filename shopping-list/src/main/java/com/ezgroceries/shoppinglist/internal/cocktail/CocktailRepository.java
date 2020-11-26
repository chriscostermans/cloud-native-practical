package com.ezgroceries.shoppinglist.internal.cocktail;

import com.ezgroceries.shoppinglist.internal.shoppingList.ShoppingListEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.Repository;

/**
 * @author Chris Costermans (u24390)
 * @since release/ (2020-11-20)
 */
public interface CocktailRepository extends Repository<CocktailEntity, UUID> {

    List<CocktailEntity> findByIdDrinkIn(List<String> cocktailIds);
    CocktailEntity save(CocktailEntity cocktailEntity);
    List<CocktailEntity> findByCocktailIdIn(List<UUID> cocktailIds);
}
