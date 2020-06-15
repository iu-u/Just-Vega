package com.example.android.vegaapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.android.vegaapp.R;

public class IngredientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ingredients);
    }

    //click on toolbar to go to profile
    public void goToWelcome(View view) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

    //click on toolbar to go to profile
    public void goToProfile(View view){
        Intent intent= new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}
