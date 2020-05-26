package com.example.android.vegaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.android.vegaapp.R;
import com.example.android.vegaapp.activities.LoginActivity;
import com.example.android.vegaapp.activities.ProfileActivity;
import com.example.android.vegaapp.activities.RecipeActivity;
import com.example.android.vegaapp.activities.RecipeDetailActivity;
import com.example.android.vegaapp.activities.RegisterActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void loginActivity(View view){
        Intent intent= new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void profileActivity(View view){
        Intent intent= new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(intent);
    }
    public void recipeActivity(View view){
        Intent intent= new Intent(MainActivity.this, RecipeActivity.class);
        startActivity(intent);
    }
    public void recipeDetailActivity(View view){
        Intent intent= new Intent(MainActivity.this, RecipeDetailActivity.class);
        startActivity(intent);
    }
    public void registerActivity(View view){
        Intent intent= new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
