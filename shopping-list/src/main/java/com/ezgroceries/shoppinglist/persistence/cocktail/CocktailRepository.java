package com.ezgroceries.shoppinglist.persistence.cocktail;

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
    List<CocktailEntity> findByIdIn(List<UUID> cocktailIds);
    List<CocktailEntity> findByNameContainingIgnoreCase(String search);
}
