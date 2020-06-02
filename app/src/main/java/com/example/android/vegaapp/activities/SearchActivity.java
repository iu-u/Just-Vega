package com.example.android.vegaapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.android.vegaapp.R;

public class SearchActivity extends AppCompatActivity {

    LinearLayout lastSearchedView;
    LinearLayout searchResultView;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipe);

        searchView = findViewById(R.id.searchView);

        lastSearchedView = findViewById(R.id.lastSearchLayout);
        searchResultView = findViewById(R.id.recyclerViewLayout);

        lastSearchedView.setVisibility(View.VISIBLE);
        searchResultView.setVisibility(View.GONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length()==0){
                    lastSearchedView.setVisibility(View.VISIBLE);
                    searchResultView.setVisibility(View.GONE);
                }else {
                    lastSearchedView.setVisibility(View.GONE);
                    searchResultView.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
    }
}
