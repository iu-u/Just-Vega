package com.example.android.vegaapp.networkutils;

import com.example.android.vegaapp.domain.Recipes;

import java.util.ArrayList;

public class RecipeNetworkUtils {
    public RecipeNetworkUtils() {
    }
    public ArrayList<Recipes> createRecipes(){
        ArrayList<Recipes> list = new ArrayList<>();
        list.add(new Recipes("s","is","blij","l"));
        list.add(new Recipes("s","is","blij","l"));
        list.add(new Recipes("s","is","blij","l"));
        list.add(new Recipes("s","is","blij","l"));
        list.add(new Recipes("s","is","blij","l"));

        return list;
    }
    public interface OnRecipeApiListener{
        public void onRecipeAvailable(ArrayList<Recipes> recipes);
    }
}
