package com.kastudio.sonsigaram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.ContentValues;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.kastudio.sonsigaram.KayitOlmaFragments.KayitOlamViewPagerAdapter;

import maes.tech.intentanim.CustomIntent;

public class KayitOlmaActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;

    static KayitOlmaActivity kayitOlmaActivity;

    public static KayitOlmaActivity getInstance(){
        return kayitOlmaActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_olma);
        kayitOlmaActivity = this;

        viewPager = findViewById(R.id.kayit_olma_view_pager);
        tabLayout = findViewById(R.id.kayit_olma_tab_layout);
        

        KayitOlamViewPagerAdapter kayitOlamViewPagerAdapter = new KayitOlamViewPagerAdapter(getSupportFragmentManager(),this);

        viewPager.setAdapter(kayitOlamViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this,"fadein-to-fadeout");
    }
}