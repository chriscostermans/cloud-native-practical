package com.ezgroceries.shoppinglist.services;

import com.ezgroceries.shoppinglist.persistence.cocktail.CocktailEntity;
import com.ezgroceries.shoppinglist.persistence.shoppingList.ShoppingListEntity;
import com.ezgroceries.shoppinglist.persistence.shoppingList.ShoppingListRepository;
import com.ezgroceries.shoppinglist.contracts.resources.CocktailResource;
import com.ezgroceries.shoppinglist.contracts.resources.ShoppingListResource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * @author Chris Costermans (u24390)
 * @since release/ (2020-11-01)
 */
@Service
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;

    private final CocktailService cocktailService;

    public ShoppingListService(ShoppingListRepository shoppingListRepository, CocktailService cocktailService) {
        this.shoppingListRepository = shoppingListRepository;
        this.cocktailService = cocktailService;
    }

    public ShoppingListResource createShoppingList(String name){
        ShoppingListEntity shoppingListEntity = new ShoppingListEntity(name);
        ShoppingListEntity newShoppingList = shoppingListRepository.save(shoppingListEntity);
        return new ShoppingListResource(newShoppingList.getId(), newShoppingList.getName());
    }


    public List<String> addCocktailsToShoppingList(UUID shoppingListId, List<String> cocktails) {
        List<CocktailEntity> cocktailEntities = cocktailService.findByCocktailId(cocktails);
        return shoppingListRepository.findById(shoppingListId).map(shoppingList -> {
            shoppingList.addCocktailsToShoppingList(cocktailEntities);
            shoppingListRepository.save(shoppingList);
            return cocktails;
        }).orElseThrow(() -> new RuntimeException("shoppingListId " + shoppingListId + " not found"));
    }

    private UUID fillCocktailId(CocktailResource cocktailResources)  {
        UUID id = cocktailResources.getCocktailId();
        return id;
    }

    public ShoppingListResource getShoppingListIngredients(UUID shoppingListId) {
        Optional<ShoppingListEntity> shoppingListIngredients = shoppingListRepository.findById(shoppingListId);
        return shoppingListIngredients.map(this::fillCocktails)
            .orElseThrow(() -> new RuntimeException("shoppingLstId " + shoppingListId + " not found"));
    }

    private ShoppingListResource fillCocktails(ShoppingListEntity shoppingListEntity) {
        return new ShoppingListResource(shoppingListEntity.getId(), shoppingListEntity.getName());
    }

    public List<ShoppingListResource> getShoppingLists() {
        List<ShoppingListEntity> shoppingListEntity = shoppingListRepository.findAll();
        return shoppingListEntity.stream().map(this::fillCocktailIngredients).collect(Collectors.toList());
    }

    private ShoppingListResource fillCocktailIngredients(ShoppingListEntity entity){
        ShoppingListResource shoppingListResource = fillCocktails(entity);
        List<CocktailEntity> cocktailEntities = (entity.getCocktails() != null) ? entity.getCocktails() : new ArrayList<>();
        List<String> cocktailIds = cocktailEntities.stream().map(CocktailEntity::getId).map(UUID::toString).collect(Collectors.toList());
        List<String> ingredients = cocktailService.findByCocktailId(cocktailIds).stream().map(CocktailEntity::getIngredients).flatMap(Set::stream).distinct().collect(Collectors.toList());
        shoppingListResource.addIngredients(ingredients);
        return shoppingListResource;
    }
}
