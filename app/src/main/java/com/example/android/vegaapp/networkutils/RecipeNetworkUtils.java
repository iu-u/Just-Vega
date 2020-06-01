package com.example.android.vegaapp.networkutils;

import com.example.android.vegaapp.domain.Recipe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RecipeNetworkUtils {
    private List<Recipe> mRecipes;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mRecipeRef = mRootRef.child("recipe");

    public RecipeNetworkUtils() {
        mRecipes = new ArrayList<>();
    }
    public ArrayList<Recipe> createRecipes(){
        return new ArrayList<>();
    }


    public List<Recipe> getRecipe(DataSnapshot dataSnapshot){

        return mRecipes;
    }
    public interface OnRecipeApiListener{
        public void onRecipeAvailable(ArrayList<Recipe> recipes);
    }
}
