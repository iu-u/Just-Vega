package com.example.android.vegaapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

    private Spinner spinner;
    private Button filter;
    private RecipeAdapter adapter;
    FilterPopUp filterPopUp;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Log.d(TAG, "THIS CLASS CALLED");

        Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);


        spinner = findViewById(R.id.sortFunction);
        ArrayAdapter<CharSequence> mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.sort_options, R.layout.custom_spinner);
        mSpinnerAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner.setAdapter(mSpinnerAdapter);

        filter = findViewById(R.id.filterButton);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(RecipeActivity.this, FilterPopUp.class), 1);
                onStart();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public void goToSearchRecipe(View view) {
        Intent intent = new Intent(RecipeActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    //click on toolbar to go to profile
    public void goToProfile(View view) {
        Intent intent = new Intent(RecipeActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    //click on toolbar to go to profile
    public void goToWelcome(View view) {
        Intent intent = new Intent(RecipeActivity.this, WelcomeActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart called");
        super.onStart();

        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, String> m = (HashMap<String, String>) dataSnapshot.getValue();
                JSONObject result = new JSONObject(m);
                recipeList.clear();

                try {
                    JSONArray receptList = result.getJSONArray("recipes");

                    for (int i = 0; i < receptList.length(); i++) {
                        List<TypeOfFood> typeOfFoods = new ArrayList<>();
                        JSONObject recipe = (JSONObject) receptList.get(i);
                        String recipeName = recipe.getString("name");
                        String category = recipe.getString("category");
                        String image = recipe.getString("image");
                        String video = recipe.getString("video");
                        int diff = recipe.getInt("Difficulty");
                        int prepTime = recipe.getInt("preparation time");

                        JSONArray allergensList = recipe.getJSONArray("allergens");
                        JSONArray typeList = recipe.getJSONArray("type");


                        for (int j = 0; j < typeList.length(); j++) {
                            JSONObject type = (JSONObject) typeList.get(j);
                            String typeName = type.getString("name");

                            JSONArray amountList = type.getJSONArray("amount");
                            JSONArray ingredientList = type.getJSONArray("ingredients");
                            JSONArray preperationList = type.getJSONArray("preperationmethod");

                            TypeOfFood tof = new TypeOfFood(typeName);
                            for (int k = 0; k < amountList.length(); k++) {
                                tof.addAmount(amountList.getInt(k));
                            }
                            for (int k = 0; k < ingredientList.length(); k++) {
                                tof.addIngredient(ingredientList.getString(k));
                            }
                            for (int k = 0; k < preperationList.length(); k++) {
                                tof.addPrep(preperationList.getString(k));
                            }
                            typeOfFoods.add(tof);
                        }
                        Recipe recipes = new Recipe(category, typeOfFoods, recipeName, image, video, diff, prepTime);

                        Log.d(TAG, "onDataChange: " + recipes.getTof());
                        for (int j = 0; j < allergensList.length(); j++) {
                            recipes.addAllergy(allergensList.getString(j));
                        }
                        recipeList.add(recipes);

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

    private void initRecyclerView() {
        Log.i(TAG, "initRecyclerView called");
        RecyclerView recyclerView = findViewById(R.id.recipeRecyclerView);

        adapter = new RecipeAdapter(RecipeActivity.this, recipeList, RecipeActivity.this, recipeList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(RecipeActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "onActivityResult called");
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            String result = data.getStringExtra("result");
            Log.d(TAG, result);

            adapter.getFilter().filter(result);

//            String[] cols = result.split(",");
//            for (String s : cols) {
//                adapter.getFilter().filter(s);
//            }
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            onStart();
        }
    }




    @Override
    public void onElementClick(View view, int itemIndex) {
        Intent intent = new Intent(RecipeActivity.this, RecipeDetailActivity.class);
        //hier kan je data meegeven naar recepidetailactivity
        String name = recipeList.get(itemIndex).getRecipeName();
        String image = recipeList.get(itemIndex).getImage();
        String video = recipeList.get(itemIndex).getVideo();
        String category = recipeList.get(itemIndex).getCategory();
        String preperation = recipeList.get(itemIndex).getPreperationMethod();

        List<TypeOfFood> prep = recipeList.get(itemIndex).getTof();
        HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();
        for(TypeOfFood p: prep){
            List<String> prepList = new ArrayList<>();
            String key = p.getName();
            for(String s: p.getPreperation()){
                prepList.add(s);
            }
            hashMap.put(key, prepList);
        }

        HashMap<String, List<String>> ingredientList = new HashMap<>();
        for(TypeOfFood t: prep){
            List<String> ingredients = new ArrayList<>();
            String key = t.getName();
            for(String string: t.getIngredients()){
                ingredients.add(string);
            }
            ingredientList.put(key, ingredients);
        }

        HashMap<String, List<Integer>> amountList = new HashMap<>();
        for(TypeOfFood type: prep){
            List<Integer> amount = new ArrayList<>();
            String key = type.getName();
            for(int i: type.getAmount()){
                amount.add(i);
            }
            amountList.put(key, amount);
        }


        List<String> getAllergies = recipeList.get(itemIndex).getAllergies();
        ArrayList<String> allergies = new ArrayList<>();
        allergies.addAll(getAllergies);
        int preparationTime = recipeList.get(itemIndex).getPreparationTime();

        intent.putExtra("image", image);
        intent.putExtra("name", name);
        intent.putExtra("category", category);
        intent.putExtra("video", video);
        intent.putExtra("prepTime", preparationTime);
        intent.putStringArrayListExtra("allergies", allergies);
        intent.putExtra("prepMap", hashMap);
        intent.putExtra("preparation", preperation);
        intent.putExtra("ingredients", ingredientList);
        intent.putExtra("amountList", amountList);

        startActivity(intent);


    }



}

