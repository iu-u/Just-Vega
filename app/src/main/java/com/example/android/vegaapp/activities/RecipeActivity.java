package com.example.android.vegaapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.vegaapp.MainActivity;
import com.example.android.vegaapp.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

public class RecipeActivity extends AppCompatActivity{

    Button btn_sign_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

//        btn_sign_out = (Button)findViewById(R.id.btn_sign_out);
//        btn_sign_out.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                AuthUI.getInstance().signOut(RecipeActivity.this)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                Intent intent= new Intent(RecipeActivity.this, LoginActivity.class);
//                                startActivity(intent);
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(RecipeActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
        Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

    }

}
