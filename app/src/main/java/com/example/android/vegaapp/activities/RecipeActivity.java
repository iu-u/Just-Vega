package com.example.android.vegaapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.example.android.vegaapp.R;
import com.example.android.vegaapp.adapters.RecipeAdapter;
import com.example.android.vegaapp.adapters.RecipeOnClickHandler;
import com.example.android.vegaapp.domain.Recipe;
import com.example.android.vegaapp.domain.TypeOfFood;
import com.example.android.vegaapp.networkutils.RecipeNetworkUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecipeActivity extends AppCompatActivity implements View.OnClickListener, RecipeOnClickHandler, RecipeNetworkUtils.OnRecipeApiListener {

    private static String TAG = RecipeActivity.class.getName();

    ArrayList<Recipe> recipes = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecipeAdapter mAdapter;
    private List<Recipe> recipeList = new ArrayList<>();
    private List<TypeOfFood> typeOfFoods = new ArrayList<>();

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mRecipeRef = mRootRef.child("recipe");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Log.d(TAG, "THIS CLASS CALLED");
        RecipeNetworkUtils networkUtils = new RecipeNetworkUtils();
        networkUtils.createRecipes();

//        View rootview = inflater.inflate(R.layout.activity_recipe, container, false);
        //TODO XML moet nog mooi gemaakt worden.

        mRecyclerView = findViewById(R.id.recipeRecyclerView);
        //additemDecoration add een divider aan de recyclerview
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        //// Create adapter passing in the elements data
        mAdapter = new RecipeAdapter(recipes, this);
        // Attach the adapter to the recyclerview to populate items
        mRecyclerView.setAdapter(mAdapter);
        // Set layout manager to position the items
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onElementClick(View view, int itemIndex) {

    }


    @Override
    public void onRecipeAvailable(ArrayList<Recipe> recipes) {

        mAdapter.notifyDataSetChanged();
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
                    Log.d(TAG, "onDataChange: "+receptList.get(2));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
