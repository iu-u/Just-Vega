package com.example.android.vegaapp.domain;

import java.util.List;

public class Recipe {

    private String category;
    private List<TypeOfFood> tof;
    private String recipeName;
    private String ingredients;
    private String typeOfFood;
    private String image;
    private String preperationMethod;
    private String video;



    public Recipe() {

    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getPreperationMethod() {
        return preperationMethod;
    }

    public void setPreperationMethod(String preperationMethod) {
        this.preperationMethod = preperationMethod;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getTypeOfFood() {
        return typeOfFood;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setTypeOfFood(String typeOfFood) {
        this.typeOfFood = typeOfFood;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
