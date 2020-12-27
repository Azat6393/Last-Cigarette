package com.kastudio.sonsigaram.KayitOlmaFragments;


import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
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

import com.airbnb.lottie.animation.content.Content;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kastudio.sonsigaram.AnaEkranActivity;
import com.kastudio.sonsigaram.CreateAccountActivity;
import com.kastudio.sonsigaram.KayitOlmaActivity;
import com.kastudio.sonsigaram.LastContentProvider;
import com.kastudio.sonsigaram.R;
import com.kastudio.sonsigaram.RegisterInfoActivity;

import java.util.HashMap;
import java.util.regex.Pattern;

import maes.tech.intentanim.CustomIntent;

public class KayitOlmaFragmentBir extends Fragment {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    EditText userName;
    EditText password;
    Button createAccount;
    Button logIn;
    TextView forgot;

    TextInputLayout textInputLayoutEmail;
    TextInputLayout textInputLayoutPassword;

    FirebaseAuth mAuth;

    public static KayitOlmaFragmentBir newInstance() {
        return new KayitOlmaFragmentBir();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kayit_olma_fragment_bir, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userName = view.findViewById(R.id.log_in_username_editText);
        password = view.findViewById(R.id.log_in_password_editText);
        createAccount = view.findViewById(R.id.create_account_button);
        logIn = view.findViewById(R.id.log_in_button);
        forgot = view.findViewById(R.id.forgot_textView);
        textInputLayoutEmail = view.findViewById(R.id.text_input_email);
        textInputLayoutPassword = view.findViewById(R.id.text_input_password);

        mAuth = FirebaseAuth.getInstance();

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RegisterInfoActivity.class);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);
                View view1 = LayoutInflater.from(getContext())
                        .inflate(R.layout.forgot_pop_up, (LinearLayout) getActivity().findViewById(R.id.forgot_id));
                final EditText forgotEditText = view1.findViewById(R.id.forgot_edit_text);
                final Button forgotSendButton = view1.findViewById(R.id.forgot_send_button);
                final TextView forgotBackButton = view1.findViewById(R.id.forgot_back_button);
                final ProgressBar forgotProgressBar = view1.findViewById(R.id.forgot_progress_bar);

                forgotSendButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (forgotEditText.getText().toString().matches("")) {
                        } else {
                            forgotSendButton.setVisibility(View.INVISIBLE);
                            forgotProgressBar.setVisibility(View.VISIBLE);
                            mAuth.sendPasswordResetEmail(forgotEditText.getText().toString())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {
                                                Toast.makeText(getActivity(),
                                                        getResources().getString(R.string.password_send_to_your_email), Toast.LENGTH_LONG).show();
                                                bottomSheetDialog.dismiss();
                                            } else {
                                                Toast.makeText(getActivity(),
                                                        task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                            forgotSendButton.setVisibility(View.VISIBLE);
                                            forgotProgressBar.setVisibility(View.INVISIBLE);

                                        }
                                    });

                        }
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
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateEmail() | !validatePassword()) {
                    return;
                } else {
                    mAuth.signInWithEmailAndPassword(textInputLayoutEmail.getEditText().getText().toString(), textInputLayoutPassword.getEditText().getText().toString())
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        //Intent
                                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                        DatabaseReference databaseReference = firebaseDatabase.getReference("Profiles");

                                        databaseReference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                for (DataSnapshot ds : snapshot.getChildren()) {

                                                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();

                                                    String userName = hashMap.get("username");

                                                    if (userName.matches(mAuth.getCurrentUser().getEmail().toString())) {

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
                                                        contentValues.put("username", usernameString);
                                                        contentValues.put("time", time);
                                                        contentValues.put("fiyat", Float.parseFloat(fiyatString));
                                                        contentValues.put("name", nameString);
                                                        contentValues.put("sigara", Integer.parseInt(sigaraSayisiString));
                                                        contentValues.put("paket", Integer.parseInt(pakettekiSigaraSayisiString));
                                                        contentValues.put("money", moneyString);
                                                        contentValues.put("profileOne", Integer.parseInt(profileOneString));
                                                        contentValues.put("profileTwo", Float.parseFloat(profileTwoString));
                                                        contentValues.put("profileThreeHour", Integer.parseInt(profileThreeHourString));
                                                        contentValues.put("profileThreeDay", Integer.parseInt(profileThreeDayString));
                                                        contentValues.put("profileThreeMonth", Integer.parseInt(profileThreeMonthString));
                                                        contentValues.put("motivation", motivationString);

                                                        String contentUriString = "content://com.kastudio.sonsigaram.LastContentProvider/last";
                                                        Uri contentUri = Uri.parse(contentUriString);
                                                        Uri uri = KayitOlmaActivity.getInstance().getContentResolver().insert(contentUri, contentValues);

                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                            }
                                        });

                                        Intent intent = new Intent(getActivity(), AnaEkranActivity.class);
                                        CustomIntent.customType(getActivity(), "fadein-to-fadeout");
                                        startActivity(intent);
                                        getActivity().finish();

                                    } else {
                                        Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

    }

    public boolean validateEmail() {
        String emailInput = textInputLayoutEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            textInputLayoutEmail.setError("  ");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            textInputLayoutEmail.setError("  ");
            return false;
        } else {
            textInputLayoutEmail.setError(null);
            return true;
        }
    }

    public boolean validatePassword() {
        String passwordInput = textInputLayoutPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            textInputLayoutPassword.setError("  ");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            textInputLayoutPassword.setError("  ");
            return false;
        } else {
            textInputLayoutPassword.setError(null);
            return true;
        }
    }

}
