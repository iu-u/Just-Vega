package com.example.android.vegaapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.android.vegaapp.R;

import java.util.ArrayList;

public class RecipepreperationMethodActivity extends AppCompatActivity {
    private static String TAG = RecipepreperationMethodActivity.class.getName();
    private ImageView mRecipeImage;
    private Button mGoToRecipe;
    private Button mGoToIngredients;
    private TextView mCategory;
    private TextView mRecipeName;
    private TextView mAllergies;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_preperationmethod);
        Log.i(TAG, "Activity is started");
        Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        Intent intent = getIntent();
        String title = intent.getExtras().getString("name");
        String category = intent.getExtras().getString("category");
        String image = intent.getExtras().getString("image");
        ArrayList<String> allergieList = intent.getStringArrayListExtra("allergies");

        mRecipeImage = findViewById(R.id.recipeimageview);
        mGoToRecipe = findViewById(R.id.btn_back_to_recipe);
        mGoToIngredients = findViewById(R.id.btn_go_to_ingredients);
        mCategory = findViewById(R.id.category);
        mRecipeName = findViewById(R.id.recipeName);
        mAllergies = findViewById(R.id.allergies);

        mGoToRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mGoToIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Log.d(TAG, "title: " + title);
        mRecipeName.setText(title);
        mCategory.setText(category);
        Glide.with(getApplicationContext())
                .asBitmap().load(image)
                .into(mRecipeImage);
        StringBuilder sb = new StringBuilder("");
        for(String s: allergieList){
            sb.append(s).append(", ");
        }
        mAllergies.setText(sb.toString());


    }
}
