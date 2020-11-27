package com.ezgroceries.shoppinglist.persistence.shoppingList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.Repository;

/**
 * @author Chris Costermans (u24390)
 * @since release/ (2020-11-20)
 */
public interface ShoppingListRepository extends Repository<ShoppingListEntity, UUID> {

    List<ShoppingListEntity> findAll();
    Optional<ShoppingListEntity> findById(UUID id);
    ShoppingListEntity save(ShoppingListEntity shoppingListEntity);
}
