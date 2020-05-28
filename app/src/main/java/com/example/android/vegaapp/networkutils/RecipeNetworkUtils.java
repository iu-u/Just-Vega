package com.example.android.vegaapp.networkutils;

import com.example.android.vegaapp.domain.Recipies;

import java.util.ArrayList;
import java.util.List;

public class RecipeNetworkUtils {
    public RecipeNetworkUtils() {
    }
    public ArrayList<Recipies> createRecipes(){
        ArrayList<Recipies> list = new ArrayList<>();
        list.add(new Recipies("s","is","blij","l"));
        list.add(new Recipies("s","is","blij","l"));
        list.add(new Recipies("s","is","blij","l"));
        list.add(new Recipies("s","is","blij","l"));
        list.add(new Recipies("s","is","blij","l"));

        return list;
    }
    public interface OnRecipeApiListener{
        public void onRecipeAvailable(ArrayList<Recipies> recipies);
    }
}
