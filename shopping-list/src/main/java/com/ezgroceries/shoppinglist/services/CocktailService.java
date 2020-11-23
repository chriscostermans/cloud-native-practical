package com.ezgroceries.shoppinglist.services;

import com.ezgroceries.shoppinglist.client.CocktailDBClient;
import com.ezgroceries.shoppinglist.client.CocktailDBResponse;
import com.ezgroceries.shoppinglist.internal.cocktail.CocktailEntity;
import com.ezgroceries.shoppinglist.internal.cocktail.CocktailRepository;
import com.ezgroceries.shoppinglist.resources.CocktailResource;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import jdk.internal.joptsimple.internal.Strings;
import org.springframework.stereotype.Service;

/**
 * @author Chris Costermans (u24390)
 * @since release/ (2020-11-01)
 */
@Service
public class CocktailService {

    private final CocktailRepository cocktailRepository;

    private final CocktailDBClient cocktailDBClient;

    public CocktailService(CocktailRepository cocktailRepository, CocktailDBClient cocktailDBClient) {
        this.cocktailRepository = cocktailRepository;
        this.cocktailDBClient = cocktailDBClient;
    }

    public List<CocktailResource> searchCocktails(String search) {
        CocktailDBResponse cocktailDBResponse = cocktailDBClient.searchCocktails(search);
        return mergeCocktails(cocktailDBResponse.getDrinks());
    }

    private List<CocktailResource> mergeCocktails(List<CocktailDBResponse.DrinkResource> drinks) {
        //Get all the idDrink attributes
        List<String> ids = drinks.stream().map(CocktailDBResponse.DrinkResource::getIdDrink).collect(Collectors.toList());

        //Get all the ones we already have from our DB, use a Map for convenient lookup
        Map<String, CocktailEntity> existingEntityMap = cocktailRepository.findByIdDrinkIn(ids).stream().collect(Collectors.toMap(CocktailEntity::getIdDrink, o -> o, (o, o2) -> o));

        //Stream over all the drinks, map them to the existing ones, persist a new one if not existing
        Map<String, CocktailEntity> allEntityMap = drinks.stream().map(drinkResource -> {
            CocktailEntity cocktailEntity = existingEntityMap.get(drinkResource.getIdDrink());
            if (cocktailEntity == null) {
                CocktailEntity newCocktailEntity = new CocktailEntity();
                newCocktailEntity.setId(UUID.randomUUID());
                newCocktailEntity.setIdDrink(drinkResource.getIdDrink());
                newCocktailEntity.setName(drinkResource.getStrDrink());
                cocktailEntity = cocktailRepository.save(newCocktailEntity);
            }
            return cocktailEntity;
        }).collect(Collectors.toMap(CocktailEntity::getIdDrink, o -> o, (o, o2) -> o));

        //Merge drinks and our entities, transform to CocktailResource instances
        return mergeAndTransform(drinks, allEntityMap);
    }

    private List<CocktailResource> mergeAndTransform(List<CocktailDBResponse.DrinkResource> drinks, Map<String, CocktailEntity> allEntityMap) {
        return drinks.stream().map(drinkResource -> new CocktailResource(allEntityMap.get(drinkResource.getIdDrink()).getId(), drinkResource.getStrDrink(), drinkResource.getStrGlass(),
            drinkResource.getStrInstructions(), drinkResource.getStrDrinkThumb(), getIngredients(drinkResource))).collect(Collectors.toList());
    }

    private List<String> getIngredients(CocktailDBResponse.DrinkResource drinkResource) {
        return
        Stream.of(
            drinkResource.getStrIngredient1(),
            drinkResource.getStrIngredient2(),
            drinkResource.getStrIngredient3(),
            drinkResource.getStrIngredient4(),
            drinkResource.getStrIngredient5(),
            drinkResource.getStrIngredient6(),
            drinkResource.getStrIngredient7()
            ).filter(
            i -> !Strings.isNullOrEmpty(i)
            ).collect(
            Collectors.toList());
    }

    public List<CocktailEntity> findByCocktailId(List<String> cocktails) {
        return cocktailRepository.findByCocktailIdIn(cocktails.stream().map(UUID::fromString).collect(Collectors.toList()));
    }
}
