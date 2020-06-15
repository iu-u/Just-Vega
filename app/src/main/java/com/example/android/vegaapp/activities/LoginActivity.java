package com.example.android.vegaapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.android.vegaapp.MainActivity;
import com.example.android.vegaapp.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG ="";
    private static final int RC_SIGN_IN = 1;
    private CallbackManager callbackManager;
    private FirebaseAuth mFirebaseAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private boolean mgooglesignin=false;
    private boolean mfacebooksignin=false;
    private EditText txt_email;
    private EditText txt_password;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private CheckBox rememberMeCheckbox;
    private Boolean rememberLogin;
    private Button forgotPass;
//    private RelativeLayout layout_forgot_password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        if(currentUser!=null){
            Intent intent= new Intent(LoginActivity.this, WelcomeActivity.class);
            startActivity(intent);
        }
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);

        txt_email = (EditText)findViewById(R.id.txt_email);
        txt_password = (EditText)findViewById(R.id.txt_password);
        Button btn_fb_login = (Button)findViewById(R.id.btn_fb_login);
        Button btn_g_login = (Button)findViewById(R.id.btn_g_login);
        Button btn_register = (Button)findViewById(R.id.btn_register);
        Button btn_email_login = (Button)findViewById(R.id.btn_email_login);
        Button btn_forgot = (Button) findViewById(R.id.btn_forgot_password) ;
        forgotPass = findViewById(R.id.btn_forgot_password);
        forgotPass.setVisibility(View.GONE);
//        layout_forgot_password = findViewById(R.id.layout_forgot_password);

        //buttons for remember me
        rememberMeCheckbox = (CheckBox) findViewById(R.id.checkBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        //remember me if boolean is true set the password and email.
        rememberLogin = loginPreferences.getBoolean("saveLogin", false);
        if (rememberLogin == true) {
            txt_email.setText(loginPreferences.getString("username", txt_email.getText().toString()));
            txt_password.setText(loginPreferences.getString("password", txt_password.getText().toString()));
            rememberMeCheckbox.setChecked(true);
        }



        btn_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt_email.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "enter email", Toast.LENGTH_SHORT).show();
                }else{
                    mFirebaseAuth.sendPasswordResetEmail(txt_email.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "email sent", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(LoginActivity.this, "email does not exist", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });

        btn_email_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt_email.getText().toString().equals("")||txt_password.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "empty fields", Toast.LENGTH_SHORT).show();

                }else{
                    signInWithEmail();


                    //remember me, set boolean on true after login and set the username and password, commit after.
                    if (rememberMeCheckbox.isChecked()) {
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("username", txt_email.getText().toString());
                        loginPrefsEditor.putString("password", txt_password.getText().toString());
                        loginPrefsEditor.commit();
                    } else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }
                }

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
        btn_g_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mgooglesignin=true;
                signInWithGoogle();
            }
        });

        btn_fb_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mfacebooksignin=true;
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "user_friends"));
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                Log.d("Success", "Login");
                                handleFacebookToken(loginResult.getAccessToken());
                            }

                            @Override
                            public void onCancel() {
                                Toast.makeText(LoginActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }

    private void signInWithEmail(){
        mFirebaseAuth.signInWithEmailAndPassword(txt_email.getText().toString(), txt_password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            Intent intent= new Intent(LoginActivity.this, WelcomeActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Wrong email or password",
                                    Toast.LENGTH_SHORT).show();
                            forgotPass.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleFacebookToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Intent intent= new Intent(LoginActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this, "Account already exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mgooglesignin){
            mgooglesignin=false;
            if(requestCode==RC_SIGN_IN){
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            }
        }else if(mfacebooksignin){
            mfacebooksignin=false;
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount acc = task.getResult(ApiException.class);
            FirebaseGoogleAuth(acc);

        }catch (ApiException e){
            Toast.makeText(this, "Sign in failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acc) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acc.getIdToken(),null);
        mFirebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Intent intent= new Intent(LoginActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this, "Account already exist", Toast.LENGTH_SHORT).show();
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
