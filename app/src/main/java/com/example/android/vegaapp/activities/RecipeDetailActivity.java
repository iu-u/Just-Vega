package com.example.android.vegaapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.vegaapp.MainActivity;
import com.example.android.vegaapp.R;
import com.example.android.vegaapp.adapters.RecipeOnClickHandler;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeOnClickHandler {
    private ImageView image;
    private TextView name;
    private TextView category;
    private Context mContext;
    private static String TAG = RecipeDetailActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Log.i(TAG, "Activity is started");
        Button button =(Button)findViewById(R.id.detail_return_button);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        Intent intent = getIntent();
        String mname = intent.getExtras().getString("name");
        String mcategory = intent.getExtras().getString("category");
        image = findViewById(R.id.recipeimageview);
        name = findViewById(R.id.recipeName);
        category = findViewById(R.id.categoryrecipe);


        category.setText(mcategory);
        name.setText(mname);
//        Glide.with(mContext)
//                .asBitmap().load("https://firebasestorage.googleapis.com/v0/b/justvega-aacaa.appspot.com/o/image.png?alt=media&token=a19b6709-8799-4a49-a1a2-1d0498b5111a")
//                .into(image);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent int1= new Intent(RecipeDetailActivity.this, RecipeActivity.class);
                startActivity(int1);
            }
        });

    }


    //click on toolbar to go to profile
    public void goToProfile(View view){
        Intent intent= new Intent(RecipeDetailActivity.this, ProfileActivity.class);
        startActivity(intent);
    }



    public void recipeActivity(View view){
        Intent intent= new Intent(RecipeDetailActivity.this, RecipeActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onElementClick(View view, int itemIndex) {
        Intent intent= new Intent(RecipeDetailActivity.this, RecipeDetailActivity.class);
        startActivity(intent);
    }


}
