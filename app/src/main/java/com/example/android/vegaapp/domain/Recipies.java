package com.example.android.vegaapp.domain;

import android.media.Image;

import java.util.ArrayList;

public class Recipies {

    private String recipeName;
    private String ingredients;
    private String kindOfFood;
    private String image;

    public Recipies(String recipeName, String ingredients, String kindOfFood, String image) {
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.kindOfFood = kindOfFood;
        this.image = image;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getKindOfFood() {
        return kindOfFood;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setKindOfFood(String kindOfFood) {
        this.kindOfFood = kindOfFood;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
