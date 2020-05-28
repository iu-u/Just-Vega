package com.example.android.vegaapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.android.vegaapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    Button btn_sign_out;
    Button deleteAccount;
    TextView email;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        deleteAccount = (Button) findViewById(R.id.deleteAccountButton);
        mFirebaseAuth = FirebaseAuth.getInstance();

        try {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            email = (TextView)findViewById(R.id.showEmail);
            email.setSelected(true);
            email.setText(user.getEmail());
        } catch (Exception e){
            Toast.makeText(ProfileActivity.this, "Not logged in", Toast.LENGTH_SHORT).show();
        }

        btn_sign_out = (Button)findViewById(R.id.logoutButton);
        btn_sign_out.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    mFirebaseAuth.signOut();
                    Intent intent= new Intent(ProfileActivity.this, LoginActivity.class);
                    startActivity(intent);
                } catch (Exception e){
                    Toast.makeText(ProfileActivity.this, "Not logged in", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    mFirebaseAuth.getCurrentUser().delete();
                    mFirebaseAuth.signOut();
                    Intent intent= new Intent(ProfileActivity.this, LoginActivity.class);
                    startActivity(intent);
                } catch (Exception e){
                    Toast.makeText(ProfileActivity.this, "Not logged in", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

}


