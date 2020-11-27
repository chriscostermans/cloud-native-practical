package com.ezgroceries.shoppinglist.persistence.cocktail;

import com.ezgroceries.utils.StringSetConverter;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Chris Costermans (u24390)
 * @since release/ (2020-11-20)
 */
@Entity
@Table(name = "COCKTAIL")
public class CocktailEntity {

    @Id
    private UUID id;

    @Column(name="ID_DRINK")
    private String idDrink;

    private String name;

    @Convert(converter = StringSetConverter.class)
    private Set<String> ingredients;

    public CocktailEntity() {
    }

    public CocktailEntity(UUID id, String idDrink, String name, Set<String> ingredients) {
        this.id = id;
        this.idDrink = idDrink;
        this.name = name;
        this.ingredients = ingredients;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getIdDrink() {
        return idDrink;
    }

    public void setIdDrink(String idDrink) {
        this.idDrink = idDrink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<String> ingredients) {
        this.ingredients = ingredients;
    }
}