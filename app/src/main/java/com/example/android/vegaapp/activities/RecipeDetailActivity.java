package com.example.android.vegaapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.vegaapp.MainActivity;
import com.example.android.vegaapp.R;
import com.example.android.vegaapp.adapters.RecipeOnClickHandler;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeOnClickHandler {
    private ImageView image;
    private TextView name;
    private TextView category;
    private Context mContext;
    private FrameLayout playButton;
    private Button recipebutton;
    private TextView prepTime;
    private TextView allergie;

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
        final String mname = intent.getExtras().getString("name");
        final String mcategory = intent.getExtras().getString("category");
        final String mvideo = intent.getExtras().getString("video");
        final String mimage = intent.getExtras().getString("image");
        final ArrayList<String> allergieList = intent.getExtras().getStringArrayList("allergies");
        int mPrepTime = intent.getExtras().getInt("prepTime");
        image = findViewById(R.id.recipeimageview);
        name = findViewById(R.id.recipeName);
        category = findViewById(R.id.categoryrecipe);
        playButton = findViewById(R.id.playButton);
        recipebutton = findViewById(R.id.readrecipebtn);
        prepTime = findViewById(R.id.prepTime);
        allergie = findViewById(R.id.textView_allergies);

        //start new intent when you click on the play button
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mvideo));
                intent.setDataAndType(Uri.parse(mvideo), "video/mp4");
                startActivity(intent);
            }
        });

      recipebutton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent= new Intent(RecipeDetailActivity.this, RecipepreperationMethodActivity.class);

        intent.putExtra("image", mimage);
        intent.putExtra("name", mname);
        intent.putExtra("category", mcategory);
        intent.putStringArrayListExtra("allergies", allergieList);
        startActivity(intent);
    }
});
        category.setText(mcategory);
        prepTime.setText(mPrepTime + " min");
        name.setText(mname);
        Glide.with(getApplicationContext())
                .asBitmap().load(mimage)
                .into(image);
        StringBuilder sb = new StringBuilder("");
        for(String s: allergieList){
            sb.append(s).append(", ");
        }
        allergie.setText(sb.toString());

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

    //click on toolbar to go to profile
    public void goToWelcome(View view) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

    public void goToSearchRecipe(View view) {
        Intent intent = new Intent(RecipeDetailActivity.this, SearchActivity.class);
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
