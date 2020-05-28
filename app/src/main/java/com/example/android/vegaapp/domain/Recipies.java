package com.example.android.vegaapp.domain;

import android.media.Image;

import java.util.ArrayList;

public class Recipies {

    private String recipeName;
    private String ingredients;
    private String kindOfFood;
    private Image image;

    public Recipies(String recipeName, String ingredients, String kindOfFood, Image image) {
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
