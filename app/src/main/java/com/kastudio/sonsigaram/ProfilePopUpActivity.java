package com.kastudio.sonsigaram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kastudio.sonsigaram.Fragments.FragmentPopupKarisik;
import com.kastudio.sonsigaram.Fragments.FragmentProfilePopUp;

public class ProfilePopUpActivity extends AppCompatActivity {

    static ProfilePopUpActivity profilePopUpActivity;

    public static ProfilePopUpActivity getInstance(){
        return profilePopUpActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pop_up);

        profilePopUpActivity = this;

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.profile_pop_up_activity);

        fragment = FragmentProfilePopUp.newInstance();
        fragmentManager.beginTransaction().add(R.id.profile_pop_up_activity,fragment).commit();

    }


    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,ProfilePopUpActivity.class);
        return intent;
    }

}