package com.ezgroceries.shoppinglist.services;

import com.ezgroceries.shoppinglist.internal.cocktail.CocktailEntity;
import com.ezgroceries.shoppinglist.internal.shoppingList.ShoppingListEntity;
import com.ezgroceries.shoppinglist.internal.shoppingList.ShoppingListRepository;
import com.ezgroceries.shoppinglist.resources.CocktailResource;
import com.ezgroceries.shoppinglist.resources.ShoppingList;
import com.ezgroceries.shoppinglist.resources.ShoppingListIngredients;
import java.util.ArrayList;
import java.util.Arrays;
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

    public ShoppingList createShoppingList(String name){
        ShoppingListEntity shoppingListEntity = new ShoppingListEntity(name);
        ShoppingListEntity newShoppingList = shoppingListRepository.save(shoppingListEntity);
        return new ShoppingList(newShoppingList.getId(), newShoppingList.getName());
    }

//    public CocktailList addCocktailsToShoppingList(UUID shoppingListId, List<CocktailResource> cocktailResource){
//    public List<UUID> addCocktailsToShoppingList(UUID shoppingListId, List<String> cocktails){
//        List<CocktailEntity> cocktailEntities = cocktailService.findAllByCocktailId(cocktails);
//
//        ShoppingList shoppingList = new ShoppingList();
//        shoppingList.setShoppingListId(shoppingListId);
//        shoppingList.setCocktailId(cocktailResource.stream().map(this::fillCocktailId).collect(Collectors.toList()));
//        shoppingList.addCocktails(cocktailEntities);
//        shoppingListRepository.save(shoppingList);
//
//        List<UUID> cocktailIds = new ArrayList<UUID>(cocktailResource.size());
//        for (CocktailResource cocktail : cocktailResource) {
//            cocktailIds.add(cocktail.getCocktailId());
//        }
//        return cocktailIds;

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
//    }


    public List<String> addCocktailsToShoppingList(UUID shoppingListId, List<String> cocktails) {
        List<CocktailEntity> cocktailEntities = cocktailService.findByCocktailId(cocktails);
        return shoppingListRepository.findById(shoppingListId).map(shoppingList -> {
            shoppingList.addCocktailsToShoppingList(cocktailEntities);
            shoppingListRepository.save(shoppingList);
//            return fromShoppingListEntity(shoppingList);
//            return new ShoppingList(shoppingList.getId(), shoppingList.getName());
            return cocktails;
        }).orElseThrow(() -> new RuntimeException("shoppingListId " + shoppingListId + " not found"));
    }

    private UUID fillCocktailId(CocktailResource cocktailResources)  {
//        CocktailList cocktailId = new CocktailList();
        UUID id = cocktailResources.getCocktailId();
        return id;
    }

    public ShoppingListIngredients getShoppingListIngredients(UUID shoppingListId) {
//        ShoppingListIngredients shoppingListIngredients = new ShoppingListIngredients(
//            shoppingListId,
//            "Stephanie's birthday",
//            Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao"));
//        return shoppingListIngredients;
        Optional<ShoppingListEntity> shoppingListResource = shoppingListRepository.findById(shoppingListId);
        return shoppingListResource.map(this::fillCocktails)
            .orElseThrow(() -> new RuntimeException("shoppingLstId " + shoppingListId + " not found"));
    }

    private ShoppingListIngredients fillCocktails(ShoppingListEntity shoppingListEntity) {
        return new ShoppingListIngredients(shoppingListEntity.getId(), shoppingListEntity.getName());
    }

    public List<ShoppingListIngredients> getShoppingLists() {
        List<ShoppingListEntity> shoppingListEntity = shoppingListRepository.findAll();
        return shoppingListEntity.stream().map(this::fillCocktailIngredients).collect(Collectors.toList());
//        ShoppingListIngredients shoppingListIngredients = new ShoppingListIngredients(shoppingListEntity.getId(), shoppingListEntity.getname());
//        List<ShoppingListIngredients> shoppingListIngredients = new ArrayList<>();
//        shoppingListIngredients = Arrays.asList(
//            new ShoppingListIngredients(
//            UUID.fromString("4ba92a46-1d1b-4e52-8e38-13cd56c7224c"),
//            "Stephanie's birthday",
//            Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao")),
//            new ShoppingListIngredients(
//            UUID.fromString("6c7d09c2-8a25-4d54-a979-25ae779d2465"),
//            "My Birthday",
//            Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao")));
//        return shoppingListIngredients;
    }

    private ShoppingListIngredients fillCocktailIngredients(ShoppingListEntity entity){
        ShoppingListIngredients shoppingListIngredients = fillCocktails(entity);
        List<CocktailEntity> entities = (entity.getCocktails() != null) ? entity.getCocktails() : new ArrayList<>();
        List<String> ids = entities.stream().map(CocktailEntity::getId).map(UUID::toString).collect(Collectors.toList());
        List<String> ingredients = cocktailService.findByCocktailId(ids).stream().map(CocktailEntity::getIngredients).flatMap(Set::stream).distinct().collect(Collectors.toList());
        shoppingListIngredients.addIngredients(ingredients);
        return shoppingListIngredients;
    }
}
