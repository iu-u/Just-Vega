package com.example.android.vegaapp.domain;

import java.util.List;

public class TypeOfFood {
    private String name;
    private List<String> ingredients;
    private List<Integer> amount;
    private List<String> preperation;

    public TypeOfFood(String name, List<String> ingredients, List<Integer> amount, List<String> preperation) {
        this.name = name;
        this.ingredients = ingredients;
        this.amount = amount;
        this.preperation = preperation;
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
}
