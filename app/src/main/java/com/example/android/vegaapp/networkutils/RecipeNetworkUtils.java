package com.example.android.vegaapp.networkutils;

import com.example.android.vegaapp.domain.Recipe;

import java.util.ArrayList;

public class RecipeNetworkUtils {
    public RecipeNetworkUtils() {
    }
    public ArrayList<Recipe> createRecipes(){
        ArrayList<Recipe> list = new ArrayList<>();
        list.add(new Recipe("s","is","blij","l"));
        list.add(new Recipe("s","is","blij","l"));
        list.add(new Recipe("s","is","blij","l"));
        list.add(new Recipe("s","is","blij","l"));
        list.add(new Recipe("s","is","blij","l"));

        return list;
    }
    public interface OnRecipeApiListener{
        public void onRecipeAvailable(ArrayList<Recipe> recipes);
    }
}
