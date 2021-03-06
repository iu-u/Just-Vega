package com.example.android.vegaapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.android.vegaapp.R;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity implements RecipeSmallOnClickHandler {

    LinearLayout lastSearchedView;
    LinearLayout searchResultView;
    LinearLayout layout_ingredient_items;

    private static final String TAG = "SearchActivity";

    AutoCompleteTextView searchView_ingredient;
    Button add_ingredient;
    TextView amountOfResults;
    SearchView searchView_recipe;
    Button goToSearchIngredients;
    ImageButton backToRecipeSearch;
    RelativeLayout searchRecipeView;
    RelativeLayout searchIngredientView;
    private List<Recipe> recipeList = new ArrayList<>();
    private List<Recipe> recipeListAll = new ArrayList<>();
    private ArrayAdapter<String> ingredientAdapter;
    private RecipeSmallAdapter mAdapter;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipe);

        searchView_recipe = findViewById(R.id.searchView_recipe);
        goToSearchIngredients = findViewById(R.id.btn_search_ingredient);
        backToRecipeSearch = findViewById(R.id.backToRecipe);
        amountOfResults = findViewById(R.id.amountOfResults);

        searchView_ingredient = findViewById(R.id.searchView_ingredient);
        String[] ingredients = getResources().getStringArray(R.array.ingredient_suggestions);
        ingredientAdapter = new ArrayAdapter<String>(this, R.layout.ingredient_row, R.id.ingredient_name, ingredients);
        searchView_ingredient.setAdapter(ingredientAdapter);

        add_ingredient =findViewById(R.id.btn_search_ingredient);

        searchRecipeView = findViewById(R.id.layout_search_recipe);
        searchIngredientView = findViewById(R.id.layout_search_ingredient);

        lastSearchedView = findViewById(R.id.lastSearchLayout);
        searchResultView = findViewById(R.id.recyclerViewLayout);
        layout_ingredient_items = findViewById(R.id.layout_ingredient_items);

        //Set visibility
        lastSearchedView.setVisibility(View.VISIBLE);
        searchResultView.setVisibility(View.GONE);
        searchRecipeView.setVisibility(View.VISIBLE);
        searchIngredientView.setVisibility(View.GONE);

        searchView_ingredient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, searchView_ingredient.getText().toString());
                String ingredient = searchView_ingredient.getText().toString();
                addIngredientToSearch(view, ingredient);
                lastSearchedView.setVisibility(View.GONE);
                searchResultView.setVisibility(View.VISIBLE);
                applyFilter();

            }
        });


        searchView_recipe.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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


        goToSearchIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchRecipeView.setVisibility(View.GONE);
                searchIngredientView.setVisibility(View.VISIBLE);
                mAdapter.setTypeOfFilter("ingredient");
                mAdapter.getFilter().filter("");
            }
        });

        backToRecipeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchRecipeView.setVisibility(View.VISIBLE);
                searchIngredientView.setVisibility(View.GONE);
                layout_ingredient_items.removeAllViews();
                mAdapter.setTypeOfFilter("recipe");
                searchView_ingredient.setText("");
            }
        });
    }

    public void applyFilter(){
        List<String> strings = getIngredientFilters();
        if(strings.isEmpty()){
            mAdapter.getFilter().filter("");
        } else {
            String joined = TextUtils.join(",", strings);
            Log.d(TAG, "joinedList: " + joined);
            mAdapter.getFilter().filter(joined);
        }
    }

    public List<String> getIngredientFilters(){
        List<String> ingredientFilters = new ArrayList<>();
        View v = null;
        for(int i=0; i<layout_ingredient_items.getChildCount();i++){
            Button b = (Button)layout_ingredient_items.getChildAt(i);
            String text = b.getText().toString();
            Log.i(TAG, "Button text: " + text);
            ingredientFilters.add(text);
        }
        return ingredientFilters;
    }

    public void addIngredientToSearch(View view, String ingredient){
        Button button = new Button(this);
        button.setTextSize(13);
        button.setText(ingredient);
        button.setTextColor(getResources().getColor(R.color.colorTextWhite));
        button.setBackgroundResource(R.drawable.ingredient_item_shape);
        button.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_close_green,0);

        layout_ingredient_items.addView(button);
        LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,5,10,5);
        button.setLayoutParams(params);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_ingredient_items.removeView(v);
                applyFilter();
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
                try {
                    JSONArray receptList = result.getJSONArray("recipes");

                    for (int i = 0; i < receptList.length(); i++) {
                        List<TypeOfFood> typeOfFoods = new ArrayList<>();
                        JSONObject recipe = (JSONObject) receptList.get(i);
                        String recipeName =recipe.getString("name");
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
                        Recipe recipes = new Recipe(category, typeOfFoods, recipeName, image, video,diff,prepTime);
                        Log.d(TAG, "onDataChange: "+recipes.getTof());
                        for (int j = 0; j < allergensList.length(); j++) {
                            Log.d(TAG, "onDataChange: "+allergensList.getString(j));
                            recipes.addAllergy(allergensList.getString(j));
                        }
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

        mAdapter = new RecipeSmallAdapter(SearchActivity.this,recipeList,recipeListAll, SearchActivity.this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
    }

    public void goToProfile(View view){
        Intent intent= new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    //click on toolbar to go to profile
    public void goToWelcome(View view) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

    public void goToSearchRecipe(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void onElementClick(View view, int itemIndex) {
        Intent intent= new Intent(SearchActivity.this, RecipeDetailActivity.class);

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
