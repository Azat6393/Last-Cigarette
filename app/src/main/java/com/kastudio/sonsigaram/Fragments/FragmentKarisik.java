package com.kastudio.sonsigaram.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kastudio.sonsigaram.AnaEkranActivity;
import com.kastudio.sonsigaram.PopupActivity;
import com.kastudio.sonsigaram.R;

import java.text.SimpleDateFormat;

import me.itangqi.waveloadingview.WaveLoadingView;

public class FragmentKarisik extends Fragment implements View.OnClickListener {


    WaveLoadingView wlv1,wlv2,wlv3,wlv4,wlv5,wlv6,wlv7,wlv8,wlv9,wlv10,wlv11,wlv12,wlv13,wlv14,wlv15;
    ImageView icon1,icon2,icon3,icon4,icon5,icon6,icon7,icon8,icon9,icon10,icon11,icon12,icon13,icon14,icon15;
    Chronometer myChronometer;
    long timeInMillis;

    TextView tarihTextView;

    int x1,x2,x3,x4,x5,x6,x7,x8,x9,x10,x11,x12,x13,x14,x15;


    public static FragmentKarisik newInstance(){
        return new FragmentKarisik();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_karisik_xml,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        wlv1 = view.findViewById(R.id.waveLoadingView1);
        wlv2 = view.findViewById(R.id.waveLoadingView2);
        wlv3 = view.findViewById(R.id.waveLoadingView3);
        wlv4 = view.findViewById(R.id.waveLoadingView4);
        wlv5 = view.findViewById(R.id.waveLoadingView5);
        wlv6 = view.findViewById(R.id.waveLoadingView6);
        wlv7 = view.findViewById(R.id.waveLoadingView7);
        wlv8 = view.findViewById(R.id.waveLoadingView8);
        wlv9 = view.findViewById(R.id.waveLoadingView9);
        wlv10 = view.findViewById(R.id.waveLoadingView10);
        wlv11 = view.findViewById(R.id.waveLoadingView11);
        wlv12 = view.findViewById(R.id.waveLoadingView12);
        wlv13 = view.findViewById(R.id.waveLoadingView13);
        wlv14 = view.findViewById(R.id.waveLoadingView14);
        wlv15 = view.findViewById(R.id.waveLoadingView15);

        setProgress();

        icon1 = view.findViewById(R.id.icon_image_view1);
        icon2 = view.findViewById(R.id.icon_image_view2);
        icon3 = view.findViewById(R.id.icon_image_view3);
        icon4 = view.findViewById(R.id.icon_image_view4);
        icon5 = view.findViewById(R.id.icon_image_view5);
        icon6 = view.findViewById(R.id.icon_image_view6);
        icon7 = view.findViewById(R.id.icon_image_view7);
        icon8 = view.findViewById(R.id.icon_image_view8);
        icon9 = view.findViewById(R.id.icon_image_view9);
        icon10 = view.findViewById(R.id.icon_image_view10);
        icon11 = view.findViewById(R.id.icon_image_view11);
        icon12 = view.findViewById(R.id.icon_image_view12);
        icon13 = view.findViewById(R.id.icon_image_view13);
        icon14 = view.findViewById(R.id.icon_image_view14);
        icon15 = view.findViewById(R.id.icon_image_view15);
        tarihTextView = view.findViewById(R.id.tarih_textView);

        myChronometer = view.findViewById(R.id.karisik_fragment_chronometer);

        timeInMillis = AnaEkranActivity.timeInMillis;
        long x = System.currentTimeMillis() - timeInMillis;
        myChronometer.setBase(SystemClock.elapsedRealtime() - x);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("kk:mm, dd MMMM EEEE yyyy");
        String tarih = simpleDateFormat.format(timeInMillis);
        tarihTextView.setText(tarih);

        myChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                int days = (int) (time / (3600000 * 24));
                int hours = (int)(time / 3600000);
                int minutes = (int)(time - hours*3600000)/60000;
                int seconds = (int)(time - hours*3600000- minutes*60000)/1000;

                int minute = (int)(time / 1000)/60;

                if (minute >= 20){
                    x1 = 100;
                    wlv1.setProgressValue(100);
                }else {
                    x1 = (minute*100)/20;
                    wlv1.setProgressValue(x1);
                }
                if (minute >= 120){
                    x2 = 100;
                    wlv2.setProgressValue(100);
                }else {
                    x2 = (minute*100)/120;
                    wlv2.setProgressValue(x2);
                }
                if (minute >= 480){
                    x3 = 100;
                    wlv3.setProgressValue(100);
                }else {
                     x3 = (minute*100)/480;
                    wlv3.setProgressValue(x3);
                }
                if (minute >= 720){
                    x4 = 100;
                    wlv4.setProgressValue(100);
                }else {
                     x4 = (minute*100)/720;
                    wlv4.setProgressValue(x4);
                }
                if (minute >= 1440) {
                    x5 = 100;
                    wlv5.setProgressValue(100);
                }else {
                     x5 = (minute*100)/1440;
                    wlv5.setProgressValue(x5);
                }
                if (minute >= 2880) {
                    x6 = 100;
                    wlv6.setProgressValue(100);
                }else {
                     x6 = (minute*100)/2880;
                    wlv6.setProgressValue(x6);
                }
                if (minute >= 4320){
                    x7 = 100;
                    wlv7.setProgressValue(100);
                }else {
                     x7 = (minute*100)/4320;
                    wlv7.setProgressValue(x7);
                }
                if (minute >= 5760){
                    x8 = 100;
                    wlv8.setProgressValue(100);
                }else {
                     x8 = (minute*100)/5760;
                    wlv8.setProgressValue(x8);
                }
                if (minute >= 8640){
                    x9 = 100;
                    wlv9.setProgressValue(100);
                }else {
                     x9 = (minute*100)/8640;
                    wlv9.setProgressValue(x9);
                }
                if (days >= 84) {
                    x10 = 100;
                    wlv10.setProgressValue(100);
                }else {
                     x10 = (days*100)/84;
                    wlv10.setProgressValue(x10);
                }
                if (days >= 180){
                    x11 = 100;
                    wlv11.setProgressValue(100);
                }else {
                     x11 = (days*100)/180;
                    wlv11.setProgressValue(x11);
                }
                if (days >= 365){
                    x12 = 100;
                    wlv12.setProgressValue(100);
                }else {
                     x12 = (days*100)/365;
                    wlv12.setProgressValue(x12);
                }
                if (days >= 1825){
                    x13 = 100;
                    wlv13.setProgressValue(100);
                }else {
                     x13 = (days*100)/1825;
                    wlv13.setProgressValue(x13);
                }
                if (days >= 3650){
                    x14 = 100;
                    wlv14.setProgressValue(100);
                }else {
                     x14= (days*100)/3650;
                    wlv14.setProgressValue(x14);
                }
                if (days >= 5475){
                    x15 = 100;
                    wlv15.setProgressValue(100);
                }else {
                     x15 = (days*100)/5475;
                    wlv15.setProgressValue(x15);
                }
            }
        });
        myChronometer.start();

        wlv1.setOnClickListener(this);
        wlv2.setOnClickListener(this);
        wlv3.setOnClickListener(this);
        wlv4.setOnClickListener(this);
        wlv5.setOnClickListener(this);
        wlv6.setOnClickListener(this);
        wlv7.setOnClickListener(this);
        wlv8.setOnClickListener(this);
        wlv9.setOnClickListener(this);
        wlv10.setOnClickListener(this);
        wlv11.setOnClickListener(this);
        wlv12.setOnClickListener(this);
        wlv13.setOnClickListener(this);
        wlv14.setOnClickListener(this);
        wlv15.setOnClickListener(this);

    }

    public void setProgress(){

        wlv1.setProgressValue(0);
        wlv2.setProgressValue(0);
        wlv3.setProgressValue(0);
        wlv4.setProgressValue(0);
        wlv5.setProgressValue(0);
        wlv6.setProgressValue(0);
        wlv7.setProgressValue(0);
        wlv8.setProgressValue(0);
        wlv9.setProgressValue(0);
        wlv10.setProgressValue(0);
        wlv11.setProgressValue(0);
        wlv12.setProgressValue(0);
        wlv13.setProgressValue(0);
        wlv14.setProgressValue(0);
        wlv15.setProgressValue(0);

    }

    @Override
    public void onClick(View v) {

        Intent intent = PopupActivity.newIntent(getContext());
        switch (v.getId()){
            case R.id.waveLoadingView1:
                intent.putExtra("progress",x1);
                intent.putExtra("name",getResources().getString(R.string.Your_blood_pressure_and_pulse_return_to_normal));
                intent.putExtra("image",1);
                intent.putExtra("zaman",20 + " " + getResources().getString(R.string.min));
                break;
            case R.id.waveLoadingView2:
                intent.putExtra("progress",x2);
                intent.putExtra("name",getResources().getString(R.string.Nicotine_receptors_starts_to_cry_and_go_hungry));
                intent.putExtra("image",2);
                intent.putExtra("zaman",2 + " " + getResources().getString(R.string.hours));
                break;
            case R.id.waveLoadingView3:
                intent.putExtra("progress",x3);
                intent.putExtra("name",getResources().getString(R.string.Nicotine_level_in_your_bloodstream_has_decreased_by_90_percent));
                intent.putExtra("image",3);
                intent.putExtra("zaman",8 + " " + getResources().getString(R.string.hours));
                break;
            case R.id.waveLoadingView4:
                intent.putExtra("progress",x4);
                intent.putExtra("name",getResources().getString(R.string.Nicotine_carbon_monoxide_and_oxygen_levels_in_your_body_begin_to_return_to_normal));
                intent.putExtra("image",4);
                intent.putExtra("zaman",12 + " " + getResources().getString(R.string.hours));
                break;
            case R.id.waveLoadingView5:
                intent.putExtra("progress",x5);
                intent.putExtra("name",getResources().getString(R.string.Risk_of_heart_attack_is_decreased));
                intent.putExtra("image",5);
                intent.putExtra("zaman",24 + " " + getResources().getString(R.string.hours));
                break;
            case R.id.waveLoadingView6:
                intent.putExtra("progress",x6);
                intent.putExtra("name",getResources().getString(R.string.Ability_to_smell_and_taste_has_increased));
                intent.putExtra("image",6);
                intent.putExtra("zaman",2 + " " + getResources().getString(R.string.days));
                break;
            case R.id.waveLoadingView7:
                intent.putExtra("progress",x7);
                intent.putExtra("name",getResources().getString(R.string.Your_body_is_now_free_of_nicotine_and_your_lungs_are_now_clearner_breating_is_more_easier));
                intent.putExtra("image",7);
                intent.putExtra("zaman",3 + " " + getResources().getString(R.string.days));
                break;
            case R.id.waveLoadingView8:
                intent.putExtra("progress",x8);
                intent.putExtra("name",getResources().getString(R.string.Worst_withdrawal_symptoms_have_passed_now_you_can_brag_about_quiting_smoking));
                intent.putExtra("image",8);
                intent.putExtra("zaman",4 + " " + getResources().getString(R.string.days));
                break;
            case R.id.waveLoadingView9:
                intent.putExtra("progress",x9);
                intent.putExtra("name",getResources().getString(R.string.Energy_level_peaks_you_feek_like_an_athlete));
                intent.putExtra("image",9);
                intent.putExtra("zaman",6 + " " + getResources().getString(R.string.weeks));
                break;
            case R.id.waveLoadingView10:
                intent.putExtra("progress",x10);
                intent.putExtra("name",getResources().getString(R.string.Circulation_improves_and_exercise_gets_easier_lung_functions_also_improves));
                intent.putExtra("image",10);
                intent.putExtra("zaman",2 + "-" + 12 + " " + getResources().getString(R.string.weeks));
                break;
            case R.id.waveLoadingView11:
                intent.putExtra("progress",x11);
                intent.putExtra("name",getResources().getString(R.string.Coughing_and_shortness_of_breath_decrease_your_lungs_are_now_very_healthy));
                intent.putExtra("image",11);
                intent.putExtra("zaman",6 + " " + getResources().getString(R.string.months));
                break;
            case R.id.waveLoadingView12:
                intent.putExtra("progress",x12);
                intent.putExtra("name",getResources().getString(R.string.Your_risk_of_coronary_heart_disease_is_about_half_that_of_a_smoker));
                intent.putExtra("image",12);
                intent.putExtra("zaman",1 + " " + getResources().getString(R.string.year));
                break;
            case R.id.waveLoadingView13:
                intent.putExtra("progress",x13);
                intent.putExtra("name",getResources().getString(R.string.Your_stroke_risk_is_reduced_to_that_of_a_nonsmoker_5_to_15_years_after_qutting));
                intent.putExtra("image",13);
                intent.putExtra("zaman",5 + " " + getResources().getString(R.string.years));
                break;
            case R.id.waveLoadingView14:
                intent.putExtra("progress",x14);
                intent.putExtra("name",getResources().getString(R.string.Your_risk_of_lung_cancer_falls_to_about_half_that));
                intent.putExtra("image",14);
                intent.putExtra("zaman",10 + " " + getResources().getString(R.string.years));
                break;
            case R.id.waveLoadingView15:
                intent.putExtra("progress",x15);
                intent.putExtra("name",getResources().getString(R.string.The_risk_of_coronary_heart_desease_is_like_you_have_never_smoked));
                intent.putExtra("image",15);
                intent.putExtra("zaman",15 + " " + getResources().getString(R.string.years));
                break;
        }
        startActivity(intent);
    }
}
