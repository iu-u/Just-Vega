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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<TypeOfFood> getTof() {
        return tof;
    }

    public void setTof(List<TypeOfFood> tof) {
        this.tof = tof;
    }

    public Recipe(String category, List<TypeOfFood> tof, String recipeName, String image, String video) {
        this.category = category;
        this.tof = tof;
        this.recipeName = recipeName;
        this.image = image;
        this.video = video;
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
