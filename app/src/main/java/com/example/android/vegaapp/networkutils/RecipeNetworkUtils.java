package com.example.android.vegaapp.networkutils;

import com.example.android.vegaapp.domain.Recipies;

import java.util.ArrayList;
import java.util.List;

public class RecipeNetworkUtils {
    public RecipeNetworkUtils() {
    }
    public ArrayList<Recipies> createRecipes(){
        ArrayList<Recipies> list = new ArrayList<>();
        list.add(new Recipies("afrash","is","mad","gitdamo"));
        list.add(new Recipies("afrash","is","mad","gitdamo1"));
        list.add(new Recipies("afrash","is","mad","gitdamo2"));
        list.add(new Recipies("afrash","is","mad","gitdamo3"));
        list.add(new Recipies("afrash","is","mad","gitdamo4"));

        return list;
    }
    public interface OnRecipeApiListener{
        public void onRecipeAvailable(ArrayList<Recipies> recipies);
    }
}
