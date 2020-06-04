package com.example.android.vegaapp.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.android.vegaapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    private static String TAG = ProfileActivity.class.getName();

    Button changeLanguage;
    Button changeLanguage2;
    Button changeLanguage3;

    Button btn_sign_out;
    Button deleteAccount;
    TextView email;
    TextView allergenen;
    Button addAllergenButton;
    EditText changeEmail;
    EditText changePassword;
    Button edit;
    Button confirm;


    Button changeThePassword;

    String mail = "";

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        changeThePassword = (Button) findViewById(R.id.change_password);

        changeLanguage = (Button) findViewById(R.id.language_button_1);
        changeLanguage2 = (Button) findViewById(R.id.language_button_2);
        changeLanguage3 = (Button) findViewById(R.id.language_button_3);
        btn_sign_out = (Button) findViewById(R.id.logoutButton);
        deleteAccount = (Button) findViewById(R.id.deleteAccountButton);
        addAllergenButton = (Button) findViewById(R.id.add_allergen_button_id);
        allergenen = (TextView) findViewById(R.id.all_allergenes_id);
        confirm = (Button) findViewById(R.id.Comfirm_button);

        changeEmail = (EditText) findViewById(R.id.showEmail);
        changePassword = (EditText) findViewById(R.id.password_edittext_id);

        edit = (Button) findViewById(R.id.EditButton);
        changeEmail.setEnabled(false);
        changeEmail.setFocusableInTouchMode(false);
        changeEmail.setClickable(false);

        changePassword.setEnabled(false);
        changePassword.setFocusableInTouchMode(false);
        changePassword.setClickable(false);

        mFirebaseAuth = FirebaseAuth.getInstance();

        try {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            email = (TextView) findViewById(R.id.showEmail);
            email.setSelected(true);
            email.setText(user.getEmail());
        } catch (Exception e) {
            Toast.makeText(ProfileActivity.this, "Not logged in", Toast.LENGTH_SHORT).show();
        }

        //after clicking confirm you look if you can change the user or the password

        confirm.setOnClickListener(new View.OnClickListener() {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            @Override
            public void onClick(View v) {
                user.updateEmail(email.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ProfileActivity.this, "updated email + " + user.getEmail(), Toast.LENGTH_LONG).show();
                                    email.setText(user.getEmail());
                                    Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(ProfileActivity.this, "Not updated email " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        //change password
        changeThePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (changePassword.getText().toString().equals("")){
                    Toast.makeText(ProfileActivity.this, "please fill in your password ", Toast.LENGTH_SHORT).show();

                }else{
                    user.updatePassword(changePassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ProfileActivity.this, "Your password is updated ", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(ProfileActivity.this, "" +task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                    }
                }
        });

        //this can change the password or email in the profile page
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    //checks if user uses gmail or facebook email
                    if (!user.getProviderData().get(user.getProviderData().size() - 1).getProviderId().equals("password")) {
                        Toast.makeText(ProfileActivity.this, "You can not change the email or the password of your Facebook or Gmail.", Toast.LENGTH_SHORT).show();
                        Intent intent = getIntent();
                        startActivity(intent);
                        finish();
                    } else {
                        confirm.setVisibility(View.VISIBLE);
                        changeThePassword.setVisibility(View.VISIBLE);
                        changePassword.setText("");
                        changeEmail.setEnabled(true);
                        changeEmail.setFocusableInTouchMode(true);
                        changeEmail.setClickable(true);

                        changePassword.setEnabled(true);
                        changePassword.setFocusableInTouchMode(true);
                        changePassword.setClickable(true);


                        Toast.makeText(ProfileActivity.this, "You can change your email and password now", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(ProfileActivity.this, "Not logged in", Toast.LENGTH_SHORT).show();
                }
            }
        });
        changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String languageToLoad  = "en";
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                Toast.makeText(ProfileActivity.this, "Language changed to English", Toast.LENGTH_SHORT).show();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        changeLanguage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String languageToLoad  = "de";
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                Toast.makeText(ProfileActivity.this, "Language changed to Deutsche", Toast.LENGTH_SHORT).show();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        changeLanguage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String languageToLoad  = "fr";
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                Toast.makeText(ProfileActivity.this, "Language changed to French", Toast.LENGTH_SHORT).show();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

//        findViewById(R.id.logoutButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               // showTermsOfConditionDialog();
//            }
//        });

        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();
            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteAccountDialog();
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

    //    private void showTermsOfConditionDialog(){
//        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this, R.style.AlertDialogTheme);
//        View view = LayoutInflater.from(ProfileActivity.this).inflate(
//                R.layout.custom_dialog_logout,
//                (ConstraintLayout) findViewById(R.id.layoutDialogContainer)
//        );
//        builder.setView(view);
//
//
//
//        final AlertDialog alertDialog = builder.create();
//
//        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//            }
//        });
//
//        if (alertDialog.getWindow() != null) {
//            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//        }
//        alertDialog.show();
//    }

    private void showLogoutDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(ProfileActivity.this).inflate(
                R.layout.custom_dialog_logout,
                (ConstraintLayout) findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonYes_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                try {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    mFirebaseAuth.signOut();

                    Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(ProfileActivity.this, "You have been logged out", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(ProfileActivity.this, "Not logged in", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();

        view.findViewById(R.id.buttonNo_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    public void goToSearchRecipe(View view) {
        Intent intent = new Intent(ProfileActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    public void goToProfile(View view){
        Intent intent= new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void showDeleteAccountDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(ProfileActivity.this).inflate(
                R.layout.custom_dialog_deleteaccount,
                (ConstraintLayout) findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonYes_deleteaccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                try {
                    mFirebaseAuth.getCurrentUser().delete();
                    mFirebaseAuth.signOut();

                    Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(ProfileActivity.this, "Account has been deleted", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(ProfileActivity.this, "Not logged in", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();

        view.findViewById(R.id.buttonNo_deleteaccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}


