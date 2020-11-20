package com.ezgroceries.shoppinglist.internal.shoppingList;

import com.ezgroceries.shoppinglist.resources.ShoppingList;
import java.util.List;

/**
 * @author Chris Costermans (u24390)
 * @since release/ (2020-11-20)
 */
public interface ShoppingListRepository {

    List<ShoppingListEntity> findAll();
}
