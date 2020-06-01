package com.example.android.vegaapp.domain;

import java.util.ArrayList;
import java.util.List;

public class TypeOfFood {
    private String name;
    private List<String> ingredients = new ArrayList<>();
    private List<Integer> amount= new ArrayList<>();
    private List<String> preperation=new ArrayList<>();

    public TypeOfFood(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Integer> getAmount() {
        return amount;
    }

    public void setAmount(List<Integer> amount) {
        this.amount = amount;
    }

    public List<String> getPreperation() {
        return preperation;
    }

    public void setPreperation(List<String> preperation) {
        this.preperation = preperation;
    }

    public void addAmount(int amount){
        this.amount.add(amount);
    }
    public void addIngredient(String ingredient){
        this.ingredients.add(ingredient);
    }
    public void addPrep(String prep){
        this.preperation.add(prep);
    }
}
