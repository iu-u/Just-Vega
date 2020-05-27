package com.example.android.vegaapp.domain;

import java.util.ArrayList;

public class Recipies {

    private String recipeName;
    private ArrayList ingredients;
    private String kindOfFood;

    public Recipies(String recipeName, ArrayList ingredients, String kindOfFood) {
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.kindOfFood = kindOfFood;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public ArrayList getIngredients() {
        return ingredients;
    }

    public String getKindOfFood() {
        return kindOfFood;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setIngredients(ArrayList ingredients) {
        this.ingredients = ingredients;
    }

    public void setKindOfFood(String kindOfFood) {
        this.kindOfFood = kindOfFood;
    }
}
