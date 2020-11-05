package com.ezgroceries.shoppinglist.services;

import com.ezgroceries.shoppinglist.resources.CocktailResource;
import java.util.UUID;
import org.springframework.stereotype.Service;

/**
 * @author Chris Costermans (u24390)
 * @since release/ (2020-11-01)
 */
@Service
public class CocktailResourceService {

    CocktailResource cocktailResource = new CocktailResource();

    public CocktailResource getCocktailResource(UUID cocktailId){
        CocktailResource cocktailResource = new CocktailResource();

        return cocktailResource;
    }

}
