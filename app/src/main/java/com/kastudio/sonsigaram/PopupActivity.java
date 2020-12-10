package com.kastudio.sonsigaram;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.kastudio.sonsigaram.Fragments.FragmentPopUp;
import com.kastudio.sonsigaram.Fragments.FragmentPopupKarisik;

public class PopupActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_activity);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.popup_activity);

        fragment = FragmentPopupKarisik.newInstance();
        fragmentManager.beginTransaction().add(R.id.popup_activity,fragment).commit();

    }


    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,PopupActivity.class);
        return intent;
    }

}
