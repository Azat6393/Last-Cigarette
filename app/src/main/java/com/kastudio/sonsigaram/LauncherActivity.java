package com.kastudio.sonsigaram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import maes.tech.intentanim.CustomIntent;

public class LauncherActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent;
                if (firebaseUser != null){
                    //Intent
                    intent = new Intent(LauncherActivity.this, AnaEkranActivity.class);
                }else {
                    intent = new Intent(LauncherActivity.this, KayitOlmaActivity.class);
                }
                startActivity(intent);
                CustomIntent.customType(LauncherActivity.this,"fadein-to-fadeout");
                finish();
            }
        },2300);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}