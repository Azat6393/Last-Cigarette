package com.kastudio.sonsigaram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Pattern;

import maes.tech.intentanim.CustomIntent;

public class CreateAccountActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[a-zA-Z])" +
                    "(?=\\S+$)" +
                    ".{4,}" +
                    "$");

    TextInputLayout userName;
    TextInputLayout password;
    TextView logIn;
    Button createAccount, facebookButton, googleButton;

    String name, money, motivasyon;
    int  sigara, paket, profileOne, profileThreeHour, profileThreeDay, profileThreeMonth;
    float profileTwo, fiyat;
    long time;

    CallbackManager mCallbackManager;

    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;

    private static final int RC_SIGN_IN = 9001;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        userName = findViewById(R.id.create_accout_username_editText);
        password = findViewById(R.id.create_account_password_editText);
        logIn = findViewById(R.id.create_account_LogIn_textView);
        createAccount = findViewById(R.id.create_account_two);
        facebookButton = findViewById(R.id.facebook_button_create_button);
        googleButton = findViewById(R.id.googlePlus_button_create_button);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        Intent intent = getIntent();

        fiyat = intent.getFloatExtra("fiyat",0);
        name = intent.getStringExtra("name");
        sigara = intent.getIntExtra("sigara",0);
        paket = intent.getIntExtra("paket",0);
        money = intent.getStringExtra("money");
        profileOne = intent.getIntExtra("profileOne",0);
        profileTwo = intent.getFloatExtra("profileTwo",0);
        profileThreeHour = intent.getIntExtra("profileThreeHour",0);
        profileThreeDay = intent.getIntExtra("profileThreeDay",0);
        profileThreeMonth = intent.getIntExtra("profileThreeMonth",0);
        time = intent.getLongExtra("time",0);
        motivasyon = getResources().getString(R.string.motivation);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateEmail() | !validatePassword()){
                    return;
                }else {
                    mAuth.createUserWithEmailAndPassword(userName.getEditText().getText().toString(),password.getEditText().getText().toString())
                            .addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();
                                        if (isNewUser){
                                            insetData();
                                        }else {
                                            Toast.makeText(CreateAccountActivity.this, getResources().getString(R.string.you_already_have_an_account), Toast.LENGTH_SHORT).show();
                                        }
                                    }else {
                                        Toast.makeText(CreateAccountActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                    }

                                }
                            });
                }
            }
        });

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInGoogle();
            }
        });

        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInFacebook();
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountActivity.this,KayitOlmaActivity.class);
                RegisterInfoActivity.getInstance().finish();
                KayitOlmaActivity.getInstance().finish();
                startActivity(intent);
                finish();
            }
        });
    }

    public void logInGoogle(){

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);


    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();
                            if (isNewUser){
                                insetData();
                            }else {
                                Toast.makeText(CreateAccountActivity.this, getResources().getString(R.string.you_already_have_an_account), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(CreateAccountActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {

            }
        }else {

            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void logInFacebook(){
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(CreateAccountActivity.this, Arrays.asList("email","public_profile"));
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }
            @Override
            public void onCancel() {
                Toast.makeText(CreateAccountActivity.this,"Failed",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onError(FacebookException error) {
                Toast.makeText(CreateAccountActivity.this,"Failed",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void insetData(){
        //Intent and Add data
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String userName = firebaseUser.getEmail().toString();

        //insert to Firebase
        databaseReference.child("Profiles").child(uuidString).child("username").setValue(userName);
        databaseReference.child("Profiles").child(uuidString).child("time").setValue(time);
        databaseReference.child("Profiles").child(uuidString).child("fiyat").setValue(fiyat);
        databaseReference.child("Profiles").child(uuidString).child("name").setValue(name);
        databaseReference.child("Profiles").child(uuidString).child("sigara").setValue(sigara);
        databaseReference.child("Profiles").child(uuidString).child("paket").setValue(paket);
        databaseReference.child("Profiles").child(uuidString).child("money").setValue(money);
        databaseReference.child("Profiles").child(uuidString).child("profileOne").setValue(profileOne);
        databaseReference.child("Profiles").child(uuidString).child("profileTwo").setValue(profileTwo);
        databaseReference.child("Profiles").child(uuidString).child("profileThreeHour").setValue(profileThreeHour);
        databaseReference.child("Profiles").child(uuidString).child("profileThreeDay").setValue(profileThreeDay);
        databaseReference.child("Profiles").child(uuidString).child("profileThreeMonth").setValue(profileThreeMonth);
        databaseReference.child("Profiles").child(uuidString).child("motivation").setValue(motivasyon);

        //insert to Database
        ContentValues contentValues = new ContentValues();
        contentValues.put(LastContentProvider.USERNAME,userName);
        contentValues.put(LastContentProvider.TIME,time);
        contentValues.put(LastContentProvider.FIYAT,fiyat);
        contentValues.put(LastContentProvider.NAME,name);
        contentValues.put(LastContentProvider.SIGARA,sigara);
        contentValues.put(LastContentProvider.PAKET,paket);
        contentValues.put(LastContentProvider.MONEY,money);
        contentValues.put(LastContentProvider.PROFILE_ONE,profileOne);
        contentValues.put(LastContentProvider.PROFILE_TWO,profileTwo);
        contentValues.put(LastContentProvider.PROFILE_THREE_HOUR,profileThreeHour);
        contentValues.put(LastContentProvider.PROFILE_THREE_DAY,profileThreeDay);
        contentValues.put(LastContentProvider.PROFILE_THREE_MONTH,profileThreeMonth);
        contentValues.put(LastContentProvider.MOTIVATION,motivasyon);

        Uri uri = getContentResolver().insert(LastContentProvider.CONTENT_URI,contentValues);

        Intent intent1 = new Intent(CreateAccountActivity.this,AnaEkranActivity.class);
        RegisterInfoActivity.getInstance().finish();
        KayitOlmaActivity.getInstance().finish();
        startActivity(intent1);
        finish();
        CustomIntent.customType(this,"fadein-to-fadeout");
    }

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();
                            if (isNewUser){
                                insetData();
                            }else {
                                Toast.makeText(CreateAccountActivity.this, getResources().getString(R.string.you_already_have_an_account), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(CreateAccountActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public boolean validateEmail(){
        String emailInput = userName.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()){
            userName.setError("  ");
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            userName.setError("  ");
            return false;
        }else {
            userName.setError(null);
            return true;
        }
    }
    public boolean validatePassword(){
        String passwordInput = password.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()){
            password.setError("  ");
            return false;
        }else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()){
            password.setError("  ");
            return false;
        }else {
            password.setError(null);
            return true;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}