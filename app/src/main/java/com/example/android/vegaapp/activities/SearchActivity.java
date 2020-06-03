package com.example.android.vegaapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.android.vegaapp.R;
import com.example.android.vegaapp.adapters.RecipeAdapter;
import com.example.android.vegaapp.adapters.RecipeSmallAdapter;
import com.example.android.vegaapp.adapters.RecipeSmallOnClickHandler;
import com.example.android.vegaapp.domain.Recipe;
import com.example.android.vegaapp.domain.TypeOfFood;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    LinearLayout lastSearchedView;
    LinearLayout searchResultView;
    SearchView searchView;
    private List<Recipe> recipeList = new ArrayList<>();
    private List<Recipe> recipeListAll = new ArrayList<>();
    private List<TypeOfFood> typeOfFoods = new ArrayList<>();
    private static String TAG = RecipeActivity.class.getName();

    private RecipeSmallAdapter mAdapter;


    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipe);

        searchView = findViewById(R.id.searchView);

        lastSearchedView = findViewById(R.id.lastSearchLayout);
        searchResultView = findViewById(R.id.recyclerViewLayout);

        lastSearchedView.setVisibility(View.VISIBLE);
        searchResultView.setVisibility(View.GONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length()==0){
                    lastSearchedView.setVisibility(View.VISIBLE);
                    searchResultView.setVisibility(View.GONE);
                }else {
                    lastSearchedView.setVisibility(View.GONE);
                    searchResultView.setVisibility(View.VISIBLE);
                    Log.d(TAG, "onQueryTextChange: "+recipeListAll.size());;

                    mAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, String> m = (HashMap<String, String>) dataSnapshot.getValue();
                JSONObject result = new JSONObject(m);
                recipeList.clear();
                typeOfFoods.clear();
                try {
                    JSONArray receptList = result.getJSONArray("recipes");

                    for (int i = 0; i < receptList.length(); i++) {
                        JSONObject recipe = (JSONObject) receptList.get(i);
                        String recipeName =recipe.getString("name");
                        String category = recipe.getString("category");
                        String image = recipe.getString("image");
                        String video = recipe.getString("video");

                        JSONArray typeList = recipe.getJSONArray("type");

                        for (int j = 0; j < typeList.length(); j++) {
                            JSONObject type = (JSONObject) typeList.get(j);
                            String typeName = type.getString("name");

                            JSONArray amountList = type.getJSONArray("amount");
                            JSONArray ingredientList = type.getJSONArray("ingredients");
                            JSONArray preperationList = type.getJSONArray("preperationmethod");

                            TypeOfFood tof = new TypeOfFood(typeName);
                            for (int k = 0; k < amountList.length(); k++) {
//                                int a = (int)amountList.get(k);
//                                tof.addAmount(a);

                                tof.addAmount(amountList.getInt(k));
                            }
                            for (int k = 0; k < ingredientList.length(); k++) {
//                                tof.addIngredient((String) ingredientList.get(k));

                                tof.addIngredient(ingredientList.getString(k));
                            }
                            for (int k = 0; k < preperationList.length(); k++) {
//                                tof.addIngredient((String) preperationList.get(k));

                                tof.addPrep(preperationList.getString(k));
                            }
                            typeOfFoods.add(tof);
                        }
                        Recipe recipes = new Recipe(category,typeOfFoods,recipeName,image,video);
                        recipeList.add(recipes);
                        recipeListAll.add(recipes);
                    }
                    initRecyclerView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.resultRecycleView);

        Log.d(TAG, "onDataChange: "+this.recipeList.size());

        mAdapter = new RecipeSmallAdapter(SearchActivity.this,recipeList,recipeListAll);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
    }

//    @Override
//    public void onElementClick(View view, int itemIndex) {
//        Intent intent= new Intent(SearchActivity.this, RecipeDetailActivity.class);
//
////        //hier kan je data meegeven naar recepidetailactivity
////        String name =  recipeList.get(itemIndex).getRecipeName();
////        String image = recipeList.get(itemIndex).getImage();
////        intent.putExtra("image", image);
////        intent.putExtra("name", name );
//        startActivity(intent);
//    }
}
