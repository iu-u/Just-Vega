package com.example.android.vegaapp.activities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.vegaapp.R;
import com.squareup.picasso.Picasso;


public class FilterPopUp extends AppCompatActivity {
    private ImageView filterImage;

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

    }

}
