package com.example.android.vegaapp.networkutils;

import com.example.android.vegaapp.domain.Recipe;

import java.util.ArrayList;

public class RecipeNetworkUtils {
    public RecipeNetworkUtils() {
    }
    public ArrayList<Recipe> createRecipes(){
        ArrayList<Recipe> list = new ArrayList<>();

        return list;
    }
    public interface OnRecipeApiListener{
        public void onRecipeAvailable(ArrayList<Recipe> recipes);
    }
}
