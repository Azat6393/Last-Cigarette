package com.kastudio.sonsigaram.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kastudio.sonsigaram.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class FragmentPopUp extends Fragment {

    public static FragmentPopUp newInstance(){
        return new FragmentPopUp();
    }

    Chronometer chronometerTop;
    Chronometer chronometerBottom;

    long timeInMillis;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pop_up,container,false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.pop_up_animation);
        View layout = view.findViewById(R.id.fragment_pop_up);
        layout.setAnimation(animation);

        chronometerTop = view.findViewById(R.id.fragment_pop_up_chronometer_top);
        chronometerBottom = view.findViewById(R.id.fragment_pop_up_chronometer_bottom);

        Intent intent = getActivity().getIntent();
        timeInMillis = intent.getLongExtra("time",0);

        long x = System.currentTimeMillis() - timeInMillis;
        chronometerTop.setBase(SystemClock.elapsedRealtime() - x);
        chronometerBottom.setBase(SystemClock.elapsedRealtime() - x);

        chronometerTop.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                int days = (int)(time / (3600000 * 24));
                int months = (int)(days / 30);
                int years = (int)(days / 365);

                if (months >= 12){
                    while (months >= 12){
                        months -= 12;
                    }
                }
                if (days >= 30){
                    while (days >= 30){
                        days -= 30;
                    }
                }

                String topString = (years < 10 ? "0"+years: years)+" : "+
                        (months < 10 ? "0"+months: months)+" : "+
                        (days < 10 ? "0"+days: days)+"";
                chronometerTop.setText(topString);

            }
        });
        chronometerBottom.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                int hours = (int)(time / 3600000);
                int minutes = (int)(time - hours*3600000)/60000;
                int seconds = (int)(time - hours*3600000 - minutes*60000)/1000 ;

                if (hours >= 24){
                    while (hours >= 24){
                        hours = hours - 24;
                    }
                }

                String bottomString = (hours < 10 ? "0"+hours: hours)+" : "+
                        (minutes < 10 ? "0"+minutes: minutes)+" : "+
                        (seconds < 10 ? "0"+seconds: seconds);
                chronometerBottom.setText(bottomString);
            }
        });
        chronometerTop.start();
        chronometerBottom.start();
    }
}
