package com.ezgroceries.shoppinglist.controllers;

import com.ezgroceries.shoppinglist.resources.CocktailResource;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Chris Costermans (u24390)
 * @since release/ (2020-10-30)
 * Cocktail API
 */
@RestController
//@RequestMapping(value = "/cocktails", produces = "application/json")
public class CocktailController {

//    Search cocktails
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @RequestMapping(value = "/cocktails", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<CocktailResource> getCocktails(@RequestParam String search) {
//        MockHttpServletResponse:
//        Status = 400
//        Error message = Required String parameter 'search' is not present
//    public List<CocktailResource> getCocktails() {
        return getDummyResources();
    }

    private List<CocktailResource> getDummyResources() {
        return Arrays.asList(
            new CocktailResource(
                UUID.fromString("23b3d85a-3928-41c0-a533-6538a71e17c4"), "Margerita",
                "Cocktail glass",
                "Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..",
                "https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg",
                Arrays.asList("Tequila", "Triple sec", "Lime juice", "Salt")),
            new CocktailResource(
                UUID.fromString("d615ec78-fe93-467b-8d26-5d26d8eab073"), "Blue Margerita",
                "Cocktail glass",
                "Rub rim of cocktail glass with lime juice. Dip rim in coarse salt..",
                "https://www.thecocktaildb.com/images/media/drink/qtvvyq1439905913.jpg",
                Arrays.asList("Tequila", "Blue Curacao", "Lime juice", "Salt")));
    }
}
