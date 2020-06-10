package com.example.android.vegaapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.vegaapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class FilterPopUp extends AppCompatActivity {
    private static final String TAG = "FilterPopUp";

    private ImageView filterImage;
    private Button closePopUp;
    private Button applyFilters;
    private Button resetFilter;
    private LinearLayout linearLayoutCategory1;
    private LinearLayout linearLayoutCategory2;
    private CheckBox amuse, starters, entrees, mainCourse, dessertAndDelicatesses;
    private List<String> checkedList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout(width, (int)(height*.85));
        getWindow().setGravity(Gravity.BOTTOM);

//        filterImage = findViewById(R.id.filterImageView);
//        Picasso.get().load(R.drawable.filter_background).into(filterImage);

        linearLayoutCategory1 =findViewById(R.id.layout_Category1);
        linearLayoutCategory1 = findViewById(R.id.layout_Category2);

        closePopUp = findViewById(R.id.close_filterPopUp);
        resetFilter = findViewById(R.id.resetFilter);
        amuse= (CheckBox) findViewById(R.id.amuse);
        starters = findViewById(R.id.starter);
        entrees = findViewById(R.id.entree);
        mainCourse = findViewById(R.id.mainCourse);
        dessertAndDelicatesses = findViewById(R.id.dessert);
        applyFilters = findViewById(R.id.applyFilters);

        resetFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCheckboxes();
            }
        });


        applyFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedList = new ArrayList<>();
                try {
                    if (amuse.isChecked()) {
                        Log.d(TAG, amuse.getText().toString());
                        checkedList.add("Amuse");
                    }
                    if (starters.isChecked()) {
                        starters.setChecked(true);
                        checkedList.add("Voorgerecht");
                    }
                    if (entrees.isChecked()) {
                        entrees.setChecked(true);
                        checkedList.add("Tussengerecht");
                    }
                    if (mainCourse.isChecked()) {
                        mainCourse.setChecked(true);
                        checkedList.add("Hoofdgerecht");
                    }
                    if (dessertAndDelicatesses.isChecked()) {
                        dessertAndDelicatesses.setChecked(true);
                        checkedList.add("Dessert");
                        checkedList.add("Friandise");
                    }

                    for(String s: checkedList){
                        Log.d(TAG, "checkList item: " + s);
                    }
                    String joined = TextUtils.join(",", checkedList);
                    Log.d(TAG, "joinedList: " + joined);

                    if(!checkedList.isEmpty()){
                        Intent returnIntent = new Intent();
//                    returnIntent.putStringArrayListExtra("result: ", checkedList);
                        returnIntent.putExtra("result", joined);
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                    } else{
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_CANCELED, returnIntent);
                        finish();
                    }


                }catch (NullPointerException e){
                    Log.d(TAG, "Nullpointer in catch block");
                    e.printStackTrace();
                }
            }
        });

        closePopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void resetCheckboxes(){
        amuse.setChecked(false);
        starters.setChecked(false);
        entrees.setChecked(false);
        mainCourse.setChecked(false);
        dessertAndDelicatesses.setChecked(false);
    }


}
