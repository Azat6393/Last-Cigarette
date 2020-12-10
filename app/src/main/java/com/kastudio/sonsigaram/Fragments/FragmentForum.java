package com.kastudio.sonsigaram.Fragments;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kastudio.sonsigaram.R;
import com.kastudio.sonsigaram.basarilar.BasarilarFragmentViewPager;

public class FragmentForum extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    TextView hedef;
    int basarilanHedefler;

    ImageView imageView;

    ArgbEvaluator evaluator = new ArgbEvaluator();

    public static FragmentForum newInstance(){
        return new FragmentForum();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.fragment_basarilar_viewPager);
        tabLayout = view.findViewById(R.id.fragment_basarilar_tabLayout);
        hedef = view.findViewById(R.id.fragment_basarilar_hedef_textView);
        imageView = view.findViewById(R.id.imageView2);

        final BasarilarFragmentViewPager basarilarFragmentViewPager = new BasarilarFragmentViewPager(getChildFragmentManager(),getContext());
        viewPager.setAdapter(basarilarFragmentViewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("Cigarette");
        tabLayout.getTabAt(1).setText("Money");
        tabLayout.getTabAt(2).setText("Time");

        final int[] ints = {R.drawable.basari_icon_bir,R.drawable.basari_icon_iki,R.drawable.basari_icon_uc};

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position < (basarilarFragmentViewPager.getCount() - 1)){
                    imageView.setImageResource((Integer) evaluator.evaluate(positionOffset,ints[position],ints[position + 1]));
                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        int days = sharedPreferences.getInt("days",0);
        int hours = sharedPreferences.getInt("hours",0);
        int money = sharedPreferences.getInt("money",0);
        int cigarette = sharedPreferences.getInt("cigarette",0);

        int[] progresses1 = new int[]{(hours * 100) / 24, (hours * 100) / 24, (hours * 100) / 168, (hours * 100) / 336, (hours * 100) / 504, (days * 100) / 31,
                (days * 100) / 93, (days * 100) / 186, (days * 100) / 279, (days * 100) / 365, (days * 100) / 730, (days * 100) / 1825};

        int[] progresses2 = new int[]{(money * 100) / 10, (money * 100) / 20, (money * 100) / 50, (money * 100) / 100, (money * 100) / 150, (money * 100) / 250,
                (money * 100) / 500, (money * 100) / 1000, (money * 100) / 2500, (money * 100) / 5000, (money * 100) / 10000, (money * 100) / 20000};

        int[] progresses3 = new int[]{(cigarette * 100) / 20, (cigarette * 100) / 50, (cigarette * 100) / 100, (cigarette * 100) / 250, (cigarette * 100) / 500,
                (cigarette * 100) / 750, (cigarette * 100) / 1000, (cigarette * 100) / 1500, (cigarette * 100) / 2500, (cigarette * 100) / 5000,
                (cigarette * 100) / 7500, (cigarette * 100) / 10000};

        for (int i = 0; i <=11; i++){
            if (progresses1[i] >= 100){
                basarilanHedefler++;
            }
            if (progresses2[i] >= 100) {
                basarilanHedefler++;
            }
            if (progresses3[i] >= 100){
                basarilanHedefler++;
            }
        }
        hedef.setText(" " + basarilanHedefler);
    }

    @Override
    public void onStop() {
        super.onStop();
        basarilanHedefler = 0;
    }
}
