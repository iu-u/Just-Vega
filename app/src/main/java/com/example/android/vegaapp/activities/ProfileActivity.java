package com.example.android.vegaapp.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.android.vegaapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    Button btn_sign_out;
    Button deleteAccount;
    TextView email;
    TextView allergenen;
    Button addAllergenButton;
    private FirebaseAuth mFirebaseAuth;
    Button changeLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadlocale();
        setContentView(R.layout.activity_profile);



       ActionBar actionBar = getSupportActionBar();
   //    actionBar.setTitle(getResources().getString(R.string.app_name));
        changeLanguage = (Button)findViewById(R.id.language_button_id);
        changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.signOut();
                Intent intent= new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                showChangeLanguageDialog();
            }
        });


        Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        deleteAccount = (Button) findViewById(R.id.deleteAccountButton);
        addAllergenButton = (Button) findViewById(R.id.add_allergen_button_id);
        allergenen = (TextView) findViewById(R.id.all_allergenes_id);
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



//
//        addAllergenButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                    String OldAllergenen = allergenen.getText().toString();
//                    allergenen.setText(OldAllergenen + "," +);
//
//                    Intent intent= new Intent(ProfileActivity.this, ProfileActivity.class);
//                    startActivity(intent);
//            }
//        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    private void showChangeLanguageDialog() {
        final String[] listitems = {"French", "English", "Deutsche"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProfileActivity.this);
        mBuilder.setTitle("Choose Language...");
        mBuilder.setSingleChoiceItems(listitems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    //English
                    setLocale("EN");
                    recreate();
                }
                else if (i == 1) {
                    setLocale("DE");
                    recreate();
                }
                else if (i == 2) {
                    setLocale("FR");
                    recreate();
                }
                dialogInterface.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();

    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("my_Lang", lang);
        editor.apply();
    }

    public void loadlocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("my_Lang", "");
        setLocale(language);
    }

}


