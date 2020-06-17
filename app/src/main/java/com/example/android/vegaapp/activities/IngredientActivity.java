package com.example.android.vegaapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.vegaapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IngredientActivity extends AppCompatActivity {

    private static String TAG = IngredientActivity.class.getName();
    private ImageView mImage;
    private LinearLayout linearLayout;
    private ImageView backToRecipe;
    private TextView backToRecipe2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ingredients);

        Intent intent = getIntent();
        String title = intent.getExtras().getString("name");
        String category = intent.getExtras().getString("category");
        String image = intent.getExtras().getString("image");
        ArrayList<String> allergieList = intent.getStringArrayListExtra("allergies");
        HashMap<String, List<String>> mIngredientList = (HashMap<String, List<String>>)intent.getSerializableExtra("ingredientList");

        mImage = findViewById(R.id.recipe_ingredients_image);
        linearLayout = findViewById(R.id.ingredientScreen_layout);
        backToRecipe = findViewById(R.id.btn_back_to_recipe);
        backToRecipe2 = findViewById(R.id.backToRecipe);

        Glide.with(getApplicationContext())
                .asBitmap().load(image)
                .into(mImage);

        setLayouts(mIngredientList);

        backToRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        backToRecipe2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setLayouts(HashMap<String, List<String>> map){
        Log.d(TAG, "setLayouts called");
//        LinearLayout ingredientWrapper = null;

        for(String s: map.keySet()){
            Log.d(TAG, "key= " + s);

            LinearLayout wrapper = new LinearLayout(this);
            wrapper.setOrientation(LinearLayout.VERTICAL);

            TextView typeOfFoodTitle = new TextView(this);
            typeOfFoodTitle.setText(s);
            typeOfFoodTitle.setTextSize(24);
            typeOfFoodTitle.setTextColor(getResources().getColor(R.color.colorTextWhite));

            wrapper.addView(typeOfFoodTitle);

            for(String ingredient: map.get(s)){
                Log.d(TAG, "ingredient: " + ingredient);

                LinearLayout ingredientWrapper = new LinearLayout(this);
                ingredientWrapper.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout ingredientLayout = new LinearLayout(this);
                ingredientLayout.setOrientation(LinearLayout.VERTICAL);

                LinearLayout pointLayout = new LinearLayout(this);

                TextView ingredientName = new TextView(this);
                ingredientName.setTextColor(getResources().getColor(R.color.colorTextWhite));

                TextView ingredientAmount = new TextView(this);
                ingredientAmount.setTextColor(getResources().getColor(R.color.colorTextLightGrey));

                ImageView bulletPoint = new ImageView(this);
                bulletPoint.setImageDrawable(getResources().getDrawable(R.drawable.bullet_point));

                final ImageView checkList = new ImageView(this);
                checkList.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_green));

                checkList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkList.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_green));
                    }
                });

                ingredientName.setTextSize(20);
                ingredientAmount.setTextSize(20);

//                LinearLayout.LayoutParams paramsIngredientWrapper =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                paramsIngredientWrapper.setMargins(80,30,0,20);
//                ingredientWrapper.setLayoutParams(paramsIngredientWrapper);

                String ingName = "";
                String ingAmount = "";

                if(ingredient.startsWith("gr,")){
                    ingName = ingredient.substring(3);
                    ingAmount = "gram";

                } else if(ingredient.startsWith("st")){
                    ingName = ingredient.substring(3);
                    ingAmount = "stuks";
                } else{
                    ingName = ingredient;
                }

                ingredientName.setText(ingName);
                ingredientAmount.setText(ingAmount);

                ingredientLayout.addView(ingredientName);
                ingredientLayout.addView(ingredientAmount);

                LinearLayout.LayoutParams paramsMiddle =new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
                paramsMiddle.weight = 0.76f;
                paramsMiddle.setMargins(20,0,20,0);
                ingredientLayout.setLayoutParams(paramsMiddle);

                LinearLayout.LayoutParams paramsBulletPoint =new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
                paramsBulletPoint.weight = 0.12f;
                paramsBulletPoint.setMargins(0,0,10,0);
                pointLayout.setLayoutParams(paramsBulletPoint);

                LinearLayout.LayoutParams paramsButton =new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
                paramsButton.weight = 0.12f;
                checkList.setLayoutParams(paramsButton);

                ingredientWrapper.setWeightSum(1f);

                pointLayout.addView(bulletPoint);
                pointLayout.setGravity(Gravity.CENTER);

                ingredientWrapper.addView(pointLayout);
                ingredientWrapper.addView(ingredientLayout);
                ingredientWrapper.addView(checkList);

                wrapper.addView(ingredientWrapper);
            }

            LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(80,50,80,50);
            wrapper.setLayoutParams(params);

            linearLayout.addView(wrapper);
        }
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
