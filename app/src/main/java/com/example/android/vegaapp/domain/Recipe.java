package com.example.android.vegaapp.domain;

import java.util.ArrayList;
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
    private int difficulty;
    private int preparationTime;
    private List<String> allergies =new ArrayList<>();

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

    public Recipe(String category, List<TypeOfFood> tof, String recipeName, String image, String video, int difficulty, int preparationTime) {
        this.preparationTime = preparationTime;
        this.difficulty = difficulty;
        this.category = category;
        this.tof = tof;
        this.recipeName = recipeName;
        this.image = image;
        this.video = video;
    }

    public void addAllergy(String allergy){
        allergies.add(allergy);
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
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



    public String getTypeOfFood() {
        return typeOfFood;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "category='" + category + '\'' +
                ", tof=" + tof +
                ", recipeName='" + recipeName + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", typeOfFood='" + typeOfFood + '\'' +
                ", image='" + image + '\'' +
                ", preperationMethod='" + preperationMethod + '\'' +
                ", video='" + video + '\'' +
                ", difficulty=" + difficulty +
                ", preparationTime=" + preparationTime +
                ", allergies=" + allergies +
                '}';
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
