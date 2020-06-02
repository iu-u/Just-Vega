package com.example.android.vegaapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.example.android.vegaapp.MainActivity;
import com.example.android.vegaapp.R;
import com.example.android.vegaapp.adapters.RecipeAdapter;
import com.example.android.vegaapp.adapters.RecipeOnClickHandler;
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

public class RecipeActivity extends AppCompatActivity implements RecipeOnClickHandler {

    private static String TAG = RecipeActivity.class.getName();

    private List<Recipe> recipeList = new ArrayList<>();
    private List<TypeOfFood> typeOfFoods = new ArrayList<>();


    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Log.d(TAG, "THIS CLASS CALLED");

        Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public void goToSearchRecipe(View view){
        Intent intent= new Intent(RecipeActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    //click on toolbar to go to profile
    public void goToProfile(View view){
        Intent intent= new Intent(RecipeActivity.this, ProfileActivity.class);
        startActivity(intent);
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
                    }
                    initRecyclerView();
                    Log.d(TAG, "onDataChange: "+receptList.get(2));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            private void initRecyclerView(){
                RecyclerView recyclerView = findViewById(R.id.recipeRecyclerView);

                RecipeAdapter adapter = new RecipeAdapter(RecipeActivity.this,recipeList, RecipeActivity.this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(RecipeActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onElementClick(View view, int itemIndex) {
        Intent intent= new Intent(RecipeActivity.this, RecipeDetailActivity.class);

        //hier kan je data meegeven naar recepidetailactivity

        startActivity(intent);
    }
}
