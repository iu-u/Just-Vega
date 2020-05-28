package com.example.android.vegaapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.android.vegaapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "";
    private FirebaseAuth mAuth;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mRPassword;
    private Button mLogin;
    private Button mSignUp;
    private Button mGoogle;
    private Button mFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            Intent intent= new Intent(RegisterActivity.this, WelcomeActivity.class);
            startActivity(intent);
        }

        mLogin=(Button)findViewById(R.id.btn_login);
        mSignUp=(Button)findViewById(R.id.btn_email_sign_up);
        mGoogle=(Button)findViewById(R.id.btn_g_register);
        mFacebook=(Button)findViewById(R.id.btn_fb_register);

        mEmail=(EditText)findViewById(R.id.txt_email_register);
        mPassword=(EditText)findViewById(R.id.txt_password_register);
        mRPassword=(EditText)findViewById(R.id.txt_repeat_password);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEmail.getText().toString().equals("")||mPassword.getText().toString().equals("")||mRPassword.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this, "empty fields", Toast.LENGTH_SHORT).show();
                }else if(!mPassword.getText().toString().equals(mRPassword.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "passwords doesnt match", Toast.LENGTH_SHORT).show();
                }else{
                    signUp();
                }
            }
        });
    }
    private void signUp() {
        mAuth.createUserWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent= new Intent(RegisterActivity.this, WelcomeActivity.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "user already exist",
                                    Toast.LENGTH_SHORT).show();
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
