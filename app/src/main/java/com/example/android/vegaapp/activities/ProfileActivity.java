package com.example.android.vegaapp.activities;

import android.content.Intent;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.android.vegaapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private static String TAG = ProfileActivity.class.getName();

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
                    changeEmail.setEnabled(true);
                    changeEmail.setFocusableInTouchMode(true);
                    changeEmail.setClickable(true);

                    changePassword.setEnabled(true);
                    changePassword.setFocusableInTouchMode(true);
                    changePassword.setClickable(true);

                    Toast.makeText(ProfileActivity.this, "You can change your email and password now", Toast.LENGTH_SHORT).show();



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

}


