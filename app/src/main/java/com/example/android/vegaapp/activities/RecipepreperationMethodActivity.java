package com.example.android.vegaapp.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.android.vegaapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecipepreperationMethodActivity extends AppCompatActivity {
    private static String TAG = RecipepreperationMethodActivity.class.getName();
    private ImageView mRecipeImage;
    private Button mGoToRecipe;
    private Button mGoToIngredients;
    private TextView mCategory;
    private TextView mRecipeName;
    private TextView mAllergies;
    private TextView mPreparation;
    private LinearLayout mLinearLayout;
    private TextView mToFName;

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
        HashMap<String, List<String>> mPrepMap = (HashMap<String, List<String>>)intent.getSerializableExtra("prepMap");

        mRecipeImage = findViewById(R.id.recipeimageview);
        mGoToRecipe = findViewById(R.id.btn_back_to_recipe);
        mGoToIngredients = findViewById(R.id.btn_go_to_ingredients);
        mCategory = findViewById(R.id.category);
        mRecipeName = findViewById(R.id.recipeName);
        mAllergies = findViewById(R.id.allergies);
        mLinearLayout = findViewById(R.id.prepLayout);


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

        setElements(mPrepMap);

//        for(String s: mPrepMap.keySet()){
//            System.out.println("current key: " + s);
//            for(String b: mPrepMap.get(s)){
//                System.out.println(b.toString());
//            }
//        }

    }

    public void setElements(HashMap<String, List<String>> map){
        int currentNumber = 1;

        for(String s: map.keySet()){
            Log.d(TAG, "key: " + s);

            //Make Horizontal LinearLayout
            LinearLayout wrapperLayout = new LinearLayout(this);
            wrapperLayout.setOrientation(LinearLayout.HORIZONTAL);

            //Make TextView currentNumber for wrapperLayout
            TextView currentToF = new TextView(this);
            currentToF.setTextColor(getResources().getColor(R.color.colorTextGreen));
            currentToF.setText(currentNumber + ".");
            currentToF.setGravity(Gravity.RIGHT);
            currentNumber++;

            //Make vertical LinearLayout for Preparation TextViews
            LinearLayout prepLayout = new LinearLayout(this);
            prepLayout.setBackgroundColor(getResources().getColor(R.color.colorBlack));
            prepLayout.setOrientation(LinearLayout.VERTICAL);


            //Make Title TextView for vertical LinearLayout
            TextView tof = new TextView(this);
            tof.setTextColor(getResources().getColor(R.color.colorTextWhite));
            tof.setTypeface(null, Typeface.BOLD);
            tof.setText(s);

            //Make Preperation Instructions TextView for vertical LinearLayout
            TextView prep = new TextView(this);
            prep.setTextColor(getResources().getColor(R.color.colorTextLightGrey));
            StringBuilder sb = new StringBuilder("");
            for(String string: map.get(s)){
                sb.append(string).append('\n');
            }
            Log.d(TAG, "preparation string: " + sb.toString());
            prep.setText(sb.toString());

            //Make LinearLayout for button for wrapperLayout
            LinearLayout buttonLayout = new LinearLayout(this);
            buttonLayout.setMinimumWidth(30);
            buttonLayout.setMinimumHeight(30);


            //Make button for LinearLayout
            Button checkButton = new Button(this);
            checkButton.setBackground(getResources().getDrawable(R.drawable.circle_fave_icon));
            checkButton.setHeight(20);
            checkButton.setWidth(20);

            buttonLayout.addView(checkButton);

            //Add Textviews to prepLayout
            prepLayout.addView(tof);
            prepLayout.addView(prep);

            LinearLayout.LayoutParams paramsWrapper =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            paramsWrapper.setMargins(0,40,20,20);
            wrapperLayout.setLayoutParams(paramsWrapper);

            LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight = 0.76f;
            params.setMargins(20,0,20,0);
            prepLayout.setLayoutParams(params);

            LinearLayout.LayoutParams paramsSmall =new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
            paramsSmall.weight = 0.12f;
            paramsSmall.setMargins(0,0,10,0);
            currentToF.setLayoutParams(paramsSmall);

            LinearLayout.LayoutParams paramsButton =new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
            paramsButton.weight = 0.12f;
            buttonLayout.setLayoutParams(paramsButton);

//            LinearLayout border = new LinearLayout(this);
//            border.setBackgroundColor(getResources().getColor(R.color.colorVagueGreen));
//            border.setMinimumHeight(1);

            View border = new View(this);
            border.setBackgroundColor(getResources().getColor(R.color.colorVagueGreen));
            border.setMinimumHeight(1);

            LinearLayout.LayoutParams paramsBorder =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            border.setLayoutParams(paramsBorder);

            wrapperLayout.setWeightSum(1f);

            //Add Elements to wrapperLayout
            wrapperLayout.addView(currentToF);
            wrapperLayout.addView(prepLayout);
            wrapperLayout.addView(buttonLayout);

            mLinearLayout.addView(wrapperLayout);
            mLinearLayout.addView(border);
        }
    }
}
