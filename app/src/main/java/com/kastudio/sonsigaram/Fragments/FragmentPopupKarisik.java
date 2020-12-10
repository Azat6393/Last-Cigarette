package com.kastudio.sonsigaram.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kastudio.sonsigaram.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;


public class FragmentPopupKarisik extends Fragment {

    TextView nameText, progressTextView, zamanTextView;
    ImageView icon, progressTic;
    CircularProgressBar circularProgressBar;

    KonfettiView konfettiView;

    public static FragmentPopupKarisik newInstance(){
        return new FragmentPopupKarisik();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popup_saglik,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.pop_up_animation);
        View layout = view.findViewById(R.id.fragment_popup_saglik);
        layout.setAnimation(animation);

        Drawable drawable1 = getResources().getDrawable(R.drawable.yirmi_dakka_icon);
        Drawable drawable2 = getResources().getDrawable(R.drawable.iki_saat_icon);
        Drawable drawable3 = getResources().getDrawable(R.drawable.sekiz_saat_icon);
        Drawable drawable4 = getResources().getDrawable(R.drawable.on_iki_saat_icon);
        Drawable drawable5 = getResources().getDrawable(R.drawable.yirmi_dort_saat_icon);
        Drawable drawable6 = getResources().getDrawable(R.drawable.iki_gun_icon);
        Drawable drawable7 = getResources().getDrawable(R.drawable.uc_gun_icon);
        Drawable drawable8 = getResources().getDrawable(R.drawable.dort_gun_icon);
        Drawable drawable9 = getResources().getDrawable(R.drawable.alti_gun_icon);
        Drawable drawable10 = getResources().getDrawable(R.drawable.on_iki_hafta_icon);
        Drawable drawable11 = getResources().getDrawable(R.drawable.alti_ay_icon);
        Drawable drawable12 = getResources().getDrawable(R.drawable.bir_yil_icon);
        Drawable drawable13 = getResources().getDrawable(R.drawable.bes_yil_icon);
        Drawable drawable14 = getResources().getDrawable(R.drawable.on_yil_icon);
        Drawable drawable15 = getResources().getDrawable(R.drawable.on_bes_yil_icon);

        konfettiView = view.findViewById(R.id.kofetti);

        nameText = view.findViewById(R.id.popup_textView);
        icon = view.findViewById(R.id.popup_imageView);
        progressTextView = view.findViewById(R.id.popup_progress_textView);
        circularProgressBar = view.findViewById(R.id.circularProgressBar);
        zamanTextView = view.findViewById(R.id.popup_zamant_textView);
        progressTic = view.findViewById(R.id.progress_tic);

        circularProgressBar.setProgressMax(100);

        Intent intent = getActivity().getIntent();

        nameText.setText(intent.getStringExtra("name"));
        int progressInt = intent.getIntExtra("progress",0);
        int image = intent.getIntExtra("image",1);
        String zamanString = intent.getStringExtra("zaman");

        zamanTextView.setText(zamanString);
        progressTextView.setText(progressInt+"%");
        circularProgressBar.setProgress(progressInt);
        System.out.println(progressInt);

        if (progressInt >= 100){

            progressTextView.setVisibility(View.INVISIBLE);
            progressTic.setVisibility(View.VISIBLE);
            circularProgressBar.setProgressBarColor(getResources().getColor(R.color.progressColor));

            konfettiView.build()
                    .addColors(Color.YELLOW, Color.GREEN, Color.RED, Color.MAGENTA)
                    .setDirection(0.0, 359.0)
                    .setSpeed(1f, 10f)
                    .setFadeOutEnabled(true)
                    .setTimeToLive(2000L)
                    .addShapes(Shape.Square.INSTANCE, Shape.Circle.INSTANCE)
                    .addSizes(new Size(8, 3f))
                    .setPosition(450f,0)
                    .burst(200);

        }

        switch (image){
            case 1:
                icon.setImageDrawable(drawable1);
                break;
            case 2:
                icon.setImageDrawable(drawable2);
                break;
            case 3:
                icon.setImageDrawable(drawable3);
                break;
            case 4:
                icon.setImageDrawable(drawable4);
                break;
            case 5:
                icon.setImageDrawable(drawable5);
                break;
            case 6:
                icon.setImageDrawable(drawable6);
                break;
            case 7:
                icon.setImageDrawable(drawable7);
                break;
            case 8:
                icon.setImageDrawable(drawable8);
                break;
            case 9:
                icon.setImageDrawable(drawable9);
                break;
            case 10:
                icon.setImageDrawable(drawable10);
                break;
            case 11:
                icon.setImageDrawable(drawable11);
                break;
            case 12:
                icon.setImageDrawable(drawable12);
                break;
            case 13:
                icon.setImageDrawable(drawable13);
                break;
            case 14:
                icon.setImageDrawable(drawable14);
                break;
            case 15:
                icon.setImageDrawable(drawable15);
                break;
        }


    }
}
