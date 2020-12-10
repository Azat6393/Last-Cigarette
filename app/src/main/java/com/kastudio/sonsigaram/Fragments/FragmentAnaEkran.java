package com.kastudio.sonsigaram.Fragments;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kastudio.sonsigaram.AnaEkranActivity;
import com.kastudio.sonsigaram.TavsiyelerActivity;
import com.kastudio.sonsigaram.MainActivity;
import com.kastudio.sonsigaram.R;

import java.util.HashMap;

public class FragmentAnaEkran extends Fragment {

    ImageView akCiger, gunImageView, sigaraIcmekIstiyorumImageView;
    Animation animation, animationOnClickButton;
    TextView tassarufTl, omreEklenenSure, motivationTextView;
    Chronometer gecenGunler, icilmeyenSigara;

    long timeInMillis;

    Dialog dialog;
    EditText motivationEditText;
    Button saveButtonDialog;

    String money;
    int sigaraSayisi, pakettekiSigaraSayisi, days, para, cigarette, hours;

    float sigaraIcmeSuresi, fiyat;

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public static FragmentAnaEkran newInstance(){
        return new FragmentAnaEkran();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ana_ekran,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        akCiger = view.findViewById(R.id.akCigerImageView);
        gecenGunler = view.findViewById(R.id.gunlerSayisiTextView);
        tassarufTl = view.findViewById(R.id.tassarufSayisiTextView);
        omreEklenenSure = view.findViewById(R.id.omrunuzeEklenenSureTextView);
        icilmeyenSigara = view.findViewById(R.id.icilmeyenSigaraSayisiTextView);
        gunImageView = view.findViewById(R.id.gunButtonImageView);
        sigaraIcmekIstiyorumImageView = view.findViewById(R.id.siragaIcmekIstiyorumImageView);
        motivationTextView = view.findViewById(R.id.motivation_text_view);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Profiles");

        motivationTextView.setText(AnaEkranActivity.motivation);

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.motivation_alert_dialog);
        motivationEditText = dialog.findViewById(R.id.dialog_text_id);
        saveButtonDialog = dialog.findViewById(R.id.dialog_button_id);

        fiyat = AnaEkranActivity.fiyat;
        sigaraSayisi = AnaEkranActivity.sigaraSayisi;
        pakettekiSigaraSayisi = AnaEkranActivity.pakettekiSigaraSayisi;
        money = AnaEkranActivity.money;
        timeInMillis = AnaEkranActivity.timeInMillis;

        sigaraIcmeSuresi = (float) 24 / sigaraSayisi;

        animation = AnimationUtils.loadAnimation(getContext(),R.anim.scale);
        //Akciger animation
        animationOnClickButton = AnimationUtils.loadAnimation(getContext(),R.anim.on_click_button);
        akCiger.setAnimation(animation);

        long x = System.currentTimeMillis() - timeInMillis;
        gecenGunler.setBase(SystemClock.elapsedRealtime() - x);
        icilmeyenSigara.setBase(SystemClock.elapsedRealtime() - x);

        //chronometer sayac
        gecenGunler.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                int years = (int)(time / (3600000 * 24 * 30 * 365));
                int months = (int)(time / (3600000 * 24 * 30));
                days = (int) (time / (3600000 * 24));
                hours = (int)(time / 3600000);
                int minutes = (int)(time - hours*3600000)/60000;
                int seconds = (int)(time - hours*3600000- minutes*60000)/1000;

                String allCounts = (years < 10 ? "0"+years: years)+":"+
                        (months < 10 ? "0"+months: months)+":"+
                        (days < 10 ? "0"+days: days)+":"+
                        (hours < 10 ? "0"+hours: hours)+":"+
                        (minutes < 10 ? "0"+minutes: minutes)+":"+
                        (seconds < 10 ? "0"+seconds: seconds);
                String daysString = (days < 10 ? "0"+days: days)+"";
                chronometer.setText(daysString);
            }
        });
        gecenGunler.start();

        final float i = sigaraIcmeSuresi;
        final int[] sigara = {0};

        final float dalFiyat = (float) fiyat / pakettekiSigaraSayisi;
        final float[] toplamTassaruf = {0};

        final int dalBasiSure = 14;
        final int[] sureMinute = {0};
        final int[] sureHour = {0};
        final int[] sureDays = {0};

        //icilmeyen sigara sayaci
        icilmeyenSigara.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long time = SystemClock.elapsedRealtime() - chronometer.getBase();

                float hours = (float)(time / 3600000);

                if (sigaraIcmeSuresi <= hours){
                    while (sigaraIcmeSuresi <= hours){
                        sigara[0]++;
                        toplamTassaruf[0] += dalFiyat;
                        sigaraIcmeSuresi += i;
                        sureMinute[0] += dalBasiSure;
                        if (sureMinute[0] >= 60){
                            while (sureMinute[0] >= 60){
                                sureHour[0]++;
                                sureMinute[0]-=60;
                            }
                        } if (sureHour[0] >= 24){
                            while (sureHour[0] >= 24){
                                sureDays[0]++;
                                sureHour[0]-= 24;
                            }
                        }
                    }
                }
                omreEklenenSure.setText(sureMinute[0] + getResources().getString(R.string.m) + " "
                        + sureHour[0] + getResources().getString(R.string.h) + " "
                        + sureDays[0] + getResources().getString(R.string.d));
                tassarufTl.setText(""+ String.format("%.1f",toplamTassaruf[0]) + " " + money);
                icilmeyenSigara.setText(""+ sigara[0]);

                para = sigara[0];
                cigarette = (int)toplamTassaruf[0];
            }
        });
        icilmeyenSigara.start();

        //tavsiyeler onClick
        sigaraIcmekIstiyorumImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sigaraIcmekIstiyorumImageView.startAnimation(animationOnClickButton);
                Intent intent1 = new Intent(getActivity(), TavsiyelerActivity.class);
                getActivity().startActivity(intent1);
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        //gun onClick Listener
        gunImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gunImageView.startAnimation(animationOnClickButton);
                Intent intent = MainActivity.newIntent(getContext());
                intent.putExtra("time",timeInMillis);
                startActivity(intent);
            }
        });

        //motivation text view onClick
        motivationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                motivationEditText.setText(AnaEkranActivity.motivation);
                dialog.show();
            }
        });

        //dialog button onClick
        saveButtonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()){
                            HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                            String userName = hashMap.get("username");
                            if (userName.matches(mAuth.getCurrentUser().getEmail().toString())){
                                String uuid = ds.getKey();
                                //update Firebase
                                DatabaseReference databaseReference1 = firebaseDatabase.getReference();
                                databaseReference1.child("Profiles").child(uuid).child("motivation").setValue(motivationEditText.getText().toString());
                                //update Database
                                String[] selectionArguments = {AnaEkranActivity.motivation};
                                ContentValues contentValues = new ContentValues();
                                String contentUriString = "content://com.kastudio.sonsigaram.LastContentProvider/last";
                                Uri contentUri = Uri.parse(contentUriString);
                                contentValues.put("motivation",motivationEditText.getText().toString());
                                getActivity().getContentResolver().update(contentUri,contentValues,"motivation=?",selectionArguments);

                                AnaEkranActivity.getInstance().getData();
                                motivationTextView.setText(AnaEkranActivity.motivation);
                                dialog.cancel();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
        checkButton();
    }

    public void checkButton(){

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (motivationEditText.getText().toString().equals("")){
                    saveButtonDialog.setEnabled(false);
                    saveButtonDialog.setAlpha(.5f);
                }else {
                    saveButtonDialog.setEnabled(true);
                    saveButtonDialog.setAlpha(1f);
                }
            }
        };

        motivationEditText.addTextChangedListener(textWatcher);

    }

    @Override
    public void onStart() {
        super.onStart();
        akCiger.setAnimation(animation);
    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences.Editor editor = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
        editor.putInt("days",days);
        editor.putInt("money",para);
        editor.putInt("cigarette",cigarette);
        editor.putInt("hours",hours);
        editor.apply();

    }

}
