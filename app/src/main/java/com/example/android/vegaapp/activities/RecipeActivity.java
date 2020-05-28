package com.example.android.vegaapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.android.vegaapp.MainActivity;
import com.example.android.vegaapp.R;
import com.example.android.vegaapp.adapters.RecipeAdapter;
import com.example.android.vegaapp.adapters.RecipeOnClickHandler;
import com.example.android.vegaapp.domain.Recipies;
import com.example.android.vegaapp.networkutils.RecipeNetworkUtils;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity implements View.OnClickListener, RecipeOnClickHandler, RecipeNetworkUtils.OnRecipeApiListener {

    private static String TAG = RecipeActivity.class.getName();

    ArrayList<Recipies> recipies = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecipeAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Log.d(TAG, "THIS CLASS CALLED");
        RecipeNetworkUtils networkUtils = new RecipeNetworkUtils();
        networkUtils.createRecipes();

//        View rootview = inflater.inflate(R.layout.activity_recipe, container, false);
        //TODO XML moet nog mooi gemaakt worden.

        mRecyclerView = findViewById(R.id.recipeRecyclerView);
        //additemDecoration add een divider aan de recyclerview
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        //// Create adapter passing in the elements data
        mAdapter = new RecipeAdapter(recipies, this);
        // Attach the adapter to the recyclerview to populate items
        mRecyclerView.setAdapter(mAdapter);
        // Set layout manager to position the items
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onElementClick(View view, int itemIndex) {

    }


    @Override
    public void onRecipeAvailable(ArrayList<Recipies> recipies) {

        this.recipies.add(new Recipies("g","is","g","k"));
        this.recipies.add(new Recipies("g","is","g","k"));
        this.recipies.add(new Recipies("g","is","g","k"));
        this.recipies.add(new Recipies("g","is","g","k"));
        this.recipies.add(new Recipies("g","is","g","k"));
        mAdapter.notifyDataSetChanged();
    }

    //click on toolbar to go to profile
    public void goToProfile(View view){
        Intent intent= new Intent(RecipeActivity.this, ProfileActivity.class);
        startActivity(intent);
    }
}
