package com.kastudio.sonsigaram.KayitOlmaFragments;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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
import com.kastudio.sonsigaram.AnaEkranActivity;
import com.kastudio.sonsigaram.CreateAccountActivity;
import com.kastudio.sonsigaram.KayitOlmaActivity;
import com.kastudio.sonsigaram.R;
import com.kastudio.sonsigaram.RegisterInfoActivity;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.HashMap;

import maes.tech.intentanim.CustomIntent;

public class KayitOlmaFragmentIki extends Fragment {

    Button createAccount;

    LoginButton facebook;
    SignInButton google;

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    CallbackManager mCallBackManager;
    GoogleSignInClient mGoogleSignInClient;

    private static final int RC_SIGN_IN = 9001;

    TextView forgot;


    public static KayitOlmaFragmentIki newInstance(){
        return new KayitOlmaFragmentIki();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.kayit_olma_fragment_iki,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        createAccount = view.findViewById(R.id.createAccountRight);
        facebook = view.findViewById(R.id.facebook_button_logIn);
        google = view.findViewById(R.id.google_button_logIn);
        forgot = view.findViewById(R.id.forgot_textView_iki);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookLogIn();
            }
        });
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleLogIn();
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(),R.style.BottomSheetDialogTheme);
                View view1 = LayoutInflater.from(getContext())
                        .inflate(R.layout.forgot_pop_up, (LinearLayout)getActivity().findViewById(R.id.forgot_id));
                final EditText forgotEditText = view1.findViewById(R.id.forgot_edit_text);
                final Button forgotSendButton = view1.findViewById(R.id.forgot_send_button);
                final TextView forgotBackButton = view1.findViewById(R.id.forgot_back_button);
                final ProgressBar forgotProgressBar = view1.findViewById(R.id.forgot_progress_bar);

                forgotSendButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        forgotSendButton.setVisibility(View.INVISIBLE);
                        forgotProgressBar.setVisibility(View.VISIBLE);
                        mAuth.sendPasswordResetEmail(forgotEditText.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()){
                                            Toast.makeText(getActivity(),
                                                    getResources().getString(R.string.password_send_to_your_email), Toast.LENGTH_LONG).show();
                                            bottomSheetDialog.dismiss();
                                        }else {
                                            Toast.makeText(getActivity(),
                                                    task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                        forgotSendButton.setVisibility(View.VISIBLE);
                                        forgotProgressBar.setVisibility(View.INVISIBLE);

                                    }
                                });

                    }
                });

                forgotBackButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetDialog.setContentView(view1);
                bottomSheetDialog.show();
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RegisterInfoActivity.class);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

    }

    public void googleLogIn(){

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(KayitOlmaFragmentIki.this.getActivity(),gso);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(KayitOlmaFragmentIki.this.getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();
                            if (isNewUser){
                                Intent intent = new Intent(getActivity(),RegisterInfoActivity.class);
                                intent.putExtra("check",true);
                                intent.putExtra("isNewUser",true);
                                startActivity(intent);
                            }else {
                                getData();
                                Intent intent = new Intent(getActivity(), AnaEkranActivity.class);
                                startActivity(intent);
                                CustomIntent.customType(getActivity(),"fadein-to-fadeout");
                                getActivity().finish();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void facebookLogIn(){
        mCallBackManager = CallbackManager.Factory.create();
        facebook.setFragment(this);
        facebook.setReadPermissions("email","public_profile");
        facebook.registerCallback(mCallBackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }
            @Override
            public void onCancel() {
            }
            @Override
            public void onError(FacebookException error) {
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

            mCallBackManager.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(KayitOlmaFragmentIki.this.getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();
                            if (isNewUser){
                                Intent intent = new Intent(getActivity(),RegisterInfoActivity.class);
                                intent.putExtra("check",true);
                                intent.putExtra("isNewUser",true);
                                startActivity(intent);
                            }else {
                                getData();
                                Intent intent = new Intent(getActivity(), AnaEkranActivity.class);
                                startActivity(intent);
                                CustomIntent.customType(getActivity(),"fadein-to-fadeout");
                                getActivity().finish();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getActivity(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void getData(){

        DatabaseReference newReference = firebaseDatabase.getReference("Profiles");

        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds: snapshot.getChildren()){

                    HashMap<String, String> hashMap = (HashMap<String,String>) ds.getValue();

                    String userName = hashMap.get("username");

                    if (userName.matches(mAuth.getCurrentUser().getEmail().toString())){

                        Long time = ds.child("time").getValue(Long.class);

                        String fiyatString = String.valueOf(hashMap.get("fiyat"));
                        String moneyString = String.valueOf(hashMap.get("money"));
                        String nameString = String.valueOf(hashMap.get("name"));
                        String pakettekiSigaraSayisiString = String.valueOf(hashMap.get("paket"));
                        String profileOneString = String.valueOf(hashMap.get("profileOne"));
                        String profileThreeHourString = String.valueOf(hashMap.get("profileThreeHour"));
                        String profileThreeDayString = String.valueOf(hashMap.get("profileThreeDay"));
                        String profileThreeMonthString = String.valueOf(hashMap.get("profileThreeMonth"));
                        String profileTwoString = String.valueOf(hashMap.get("profileTwo"));
                        String sigaraSayisiString = String.valueOf(hashMap.get("sigara"));
                        String timeInMillisString = String.valueOf(hashMap.get("time"));
                        String usernameString = String.valueOf(hashMap.get("username"));
                        String motivationString = hashMap.get("motivation");

                        //inset to Database
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("username",usernameString);
                        contentValues.put("time",time);
                        contentValues.put("fiyat",Float.parseFloat(fiyatString));
                        contentValues.put("name",nameString);
                        contentValues.put("sigara",Integer.parseInt(sigaraSayisiString));
                        contentValues.put("paket",Integer.parseInt(pakettekiSigaraSayisiString));
                        contentValues.put("money",moneyString);
                        contentValues.put("profileOne",Integer.parseInt(profileOneString));
                        contentValues.put("profileTwo",Float.parseFloat(profileTwoString));
                        contentValues.put("profileThreeHour",Integer.parseInt(profileThreeHourString));
                        contentValues.put("profileThreeDay",Integer.parseInt(profileThreeDayString));
                        contentValues.put("profileThreeMonth",Integer.parseInt(profileThreeMonthString));
                        contentValues.put("motivation",motivationString);

                        String contentUriString = "content://com.kastudio.sonsigaram.LastContentProvider/last";
                        Uri contentUri = Uri.parse(contentUriString);
                        Uri uri = getActivity().getContentResolver().insert(contentUri,contentValues);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
