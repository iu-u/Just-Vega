package com.example.android.vegaapp.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.android.vegaapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    private static String TAG = ProfileActivity.class.getName();

    Button changeLanguage;

    Button btn_sign_out;
    Button deleteAccount;
    TextView email;
    TextView allergenen;
    Button addAllergenButton;
    EditText changeEmail;
    EditText changePassword;
    Button edit;
    Button comfirm;

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadlocale();
        setContentView(R.layout.activity_profile);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        deleteAccount = (Button) findViewById(R.id.deleteAccountButton);
        addAllergenButton = (Button) findViewById(R.id.add_allergen_button_id);
        allergenen = (TextView) findViewById(R.id.all_allergenes_id);
        comfirm = (Button) findViewById(R.id.Comfirm_button);

        changeEmail = (EditText) findViewById(R.id.showEmail) ;
        changePassword = (EditText) findViewById(R.id.password_edittext_id);

        edit = (Button) findViewById(R.id.EditButton);
        changeEmail.setEnabled(false);
        changeEmail.setFocusableInTouchMode(false);
        changeEmail.setClickable(false);

        changePassword.setEnabled(false);
        changePassword.setFocusableInTouchMode(false);
        changePassword.setClickable(false);

        mFirebaseAuth = FirebaseAuth.getInstance();




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


        //TODO deze edit functie moet nog gefixd worden.

        comfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (email.getText().toString().equals("") || changePassword.getText().toString().equals("")){
                    Toast.makeText(ProfileActivity.this, "Empty fields", Toast.LENGTH_SHORT).show();
                }else{
                    try{
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();







                        user.updateEmail(email.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(ProfileActivity.this, "Email is updated", Toast.LENGTH_SHORT).show();
                                            Intent intent= new Intent(ProfileActivity.this, ProfileActivity.class);
                                            startActivity(intent);
                                        }else{
                                            Toast.makeText(ProfileActivity.this, "Email is not updated", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        String newPassword = changePassword.getText().toString();

                        user = FirebaseAuth.getInstance().getCurrentUser();

                        user.updatePassword(newPassword)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(ProfileActivity.this, "Passsword is updated", Toast.LENGTH_SHORT).show();
                                            Intent intent= new Intent(ProfileActivity.this, ProfileActivity.class);
                                            startActivity(intent);
                                        }else{
                                            Toast.makeText(ProfileActivity.this, "Passsword is not updated", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                    }catch (Exception e){
                        Toast.makeText(ProfileActivity.this, "Did not work", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(ProfileActivity.this, ProfileActivity.class);
                        startActivity(intent);
                    }
                }

            }
        });

        //this can change the password or email in the profile page
        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                comfirm.setVisibility(View.VISIBLE);
                try {

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    //checks if user uses gmail or facebook email
                    Boolean gmail = user.getEmail().endsWith("gmail.com");
                    Boolean facebook = user.getProviderId().equals("facebook.com");

                    Log.d(TAG, "INFO IN EMAIL USER = " + user.getEmail());

                    if(gmail || facebook){
                        Toast.makeText(ProfileActivity.this, "You can not change the email or the password of your Facebook or Gmail.", Toast.LENGTH_SHORT).show();
                        Intent intent = getIntent();
                        startActivity(intent);
                        finish();
                    }else{
                        changeEmail.setEnabled(true);
                        changeEmail.setFocusableInTouchMode(true);
                        changeEmail.setClickable(true);

                        changePassword.setEnabled(true);
                        changePassword.setFocusableInTouchMode(true);
                        changePassword.setClickable(true);


                        Toast.makeText(ProfileActivity.this, "You can change your email and password now", Toast.LENGTH_SHORT).show();
                    }

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


