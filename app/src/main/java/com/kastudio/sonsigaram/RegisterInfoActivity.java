package com.kastudio.sonsigaram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import maes.tech.intentanim.CustomIntent;

public class RegisterInfoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    EditText nameEditText, sigaraSayisiEditText, paketteSigaraSayisiEditText, fiyatEditText, kacSeneIctinEditText;
    TextView soruTextView, soruTextView1, soruTextView2, soruTextView3, soruTextView4, soruTextView5, calendarTextView, backTextView;
    ProgressBar progressBar;
    Spinner spinner;
    Button devamButton,devamButton1, devamButton2, devamButton3, devamButton4, devamButton5;
    int hourCalendar,minuteCalendar,dayCalendar,monthCalendar,yearCalendar;
    int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    int minute = Calendar.getInstance().get(Calendar.MINUTE);
    int count = 0;

    TextWatcher textWatcher;

    static RegisterInfoActivity registerInfoActivity;

    FirebaseDatabase firebaseDatabase;

    long timeInMillis;

    String name, sigara, paket, fiyat, money, date, kacSeneIctin, uuid;

    Animation translateAnimationOne;
    Animation translateAnimationTwo;
    Animation backTranslateAnimationOne;
    Animation backTranslateAnimationTwo;
    Animation animationOnClickButton;

    FirebaseAuth mAuth;

    boolean check, isNewUser;

    public static RegisterInfoActivity getInstance(){
        return registerInfoActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);

        registerInfoActivity = this;

        Intent intent = getIntent();
        check = intent.getBooleanExtra("check",false);
        isNewUser = intent.getBooleanExtra("isNewUser",false);

        //sigara Animation
        final AnimatedVectorDrawableCompat sigaraAnimation = AnimatedVectorDrawableCompat.create(this,R.drawable.sigara_animation);
        final ImageView sigaraAnimationImageView = findViewById(R.id.sigaraAnimation);
        sigaraAnimationImageView.setImageDrawable(sigaraAnimation);
        sigaraAnimation.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                super.onAnimationEnd(drawable);
                sigaraAnimationImageView.setImageDrawable(sigaraAnimation);
                sigaraAnimation.start();
            }
        });
        sigaraAnimation.start();

        findViewById();
        checkButton();
        onClickBackButton();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy  kk:mm");
        timeInMillis = System.currentTimeMillis();
        calendarTextView.setText(simpleDateFormat.format(timeInMillis));

        if (check){
            mAuth = FirebaseAuth.getInstance();
            firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference databaseReference = firebaseDatabase.getReference("Profiles");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds: snapshot.getChildren()){
                        HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                        String userName = hashMap.get("username");
                        if (userName.matches(mAuth.getCurrentUser().getEmail().toString())){
                            uuid = ds.getKey();
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println(error.getMessage());
                }
            });
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.moneys,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        progressBar.setMax(6);
        progressBar.setProgress(1);

        translateAnimationOne = AnimationUtils.loadAnimation(this,R.anim.animation_translate_one);
        translateAnimationTwo = AnimationUtils.loadAnimation(this,R.anim.animation_translate_two);
        backTranslateAnimationOne = AnimationUtils.loadAnimation(this,R.anim.back_animtion_translate_one);
        backTranslateAnimationTwo = AnimationUtils.loadAnimation(this,R.anim.back_animation_translate_two);
        animationOnClickButton = AnimationUtils.loadAnimation(this,R.anim.on_click_button);

        calendarTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


    }

    public void devamOnClick(View view){

        devamButton.startAnimation(animationOnClickButton);

        progressBar.setProgress(2);
        nameEditText.startAnimation(translateAnimationOne);
        nameEditText.setVisibility(View.INVISIBLE);
        soruTextView.startAnimation(translateAnimationOne);
        soruTextView.setVisibility(View.INVISIBLE);
        devamButton.setVisibility(View.INVISIBLE);
        devamButton.startAnimation(translateAnimationOne);
        name = nameEditText.getText().toString();
        devamButton1.setVisibility(View.VISIBLE);
        devamButton1.startAnimation(translateAnimationTwo);
        soruTextView1.startAnimation(translateAnimationTwo);
        soruTextView1.setVisibility(View.VISIBLE);
        kacSeneIctinEditText.setVisibility(View.VISIBLE);
        kacSeneIctinEditText.startAnimation(translateAnimationTwo);
        backTextView.setVisibility(View.VISIBLE);
        count++;

    }

    public void devamOnClick1(View view){
        int x = Integer.parseInt(kacSeneIctinEditText.getText().toString());
        if (x == 0 || x > 80){
            Toast.makeText(this,"1 - 80",Toast.LENGTH_LONG).show();
        }else {
            devamButton1.startAnimation(animationOnClickButton);

            progressBar.setProgress(3);
            kacSeneIctinEditText.startAnimation(translateAnimationOne);
            kacSeneIctinEditText.setVisibility(View.INVISIBLE);
            soruTextView1.startAnimation(translateAnimationOne);
            soruTextView1.setVisibility(View.INVISIBLE);
            devamButton1.setVisibility(View.INVISIBLE);
            devamButton1.startAnimation(translateAnimationOne);
            kacSeneIctin = kacSeneIctinEditText.getText().toString();
            devamButton2.setVisibility(View.VISIBLE);
            devamButton2.startAnimation(translateAnimationTwo);
            soruTextView2.startAnimation(translateAnimationTwo);
            soruTextView2.setVisibility(View.VISIBLE);
            sigaraSayisiEditText.setVisibility(View.VISIBLE);
            sigaraSayisiEditText.startAnimation(translateAnimationTwo);
            backTextView.setVisibility(View.VISIBLE);
            count++;
        }
    }

    public void devamOnClick2(View view){
        int x = Integer.parseInt(sigaraSayisiEditText.getText().toString());
        if (x == 0 || x > 150){
            Toast.makeText(this,"1 - 150",Toast.LENGTH_LONG).show();
        }else {
            devamButton2.startAnimation(animationOnClickButton);

            progressBar.setProgress(4);
            soruTextView2.startAnimation(translateAnimationOne);
            soruTextView2.setVisibility(View.INVISIBLE);
            sigaraSayisiEditText.startAnimation(translateAnimationOne);
            sigaraSayisiEditText.setVisibility(View.INVISIBLE);
            devamButton2.setVisibility(View.INVISIBLE);
            devamButton2.startAnimation(translateAnimationOne);
            sigara = sigaraSayisiEditText.getText().toString();
            devamButton3.setVisibility(View.VISIBLE);
            devamButton3.startAnimation(translateAnimationTwo);
            soruTextView3.startAnimation(translateAnimationTwo);
            soruTextView3.setVisibility(View.VISIBLE);
            paketteSigaraSayisiEditText.setVisibility(View.VISIBLE);
            paketteSigaraSayisiEditText.startAnimation(translateAnimationTwo);
            backTextView.setVisibility(View.VISIBLE);
            count++;
        }
    }

    public void devamOnClick3(View view){
        float x = Float.parseFloat(paketteSigaraSayisiEditText.getText().toString());
        if (x == 0 || x > 50){
            Toast.makeText(this,"1 - 50",Toast.LENGTH_LONG).show();
        }else {
            devamButton3.startAnimation(animationOnClickButton);

            progressBar.setProgress(5);
            soruTextView3.startAnimation(translateAnimationOne);
            soruTextView3.setVisibility(View.INVISIBLE);
            paketteSigaraSayisiEditText.startAnimation(translateAnimationOne);
            paketteSigaraSayisiEditText.setVisibility(View.INVISIBLE);
            devamButton3.setVisibility(View.INVISIBLE);
            devamButton3.startAnimation(translateAnimationOne);
            paket = paketteSigaraSayisiEditText.getText().toString();
            devamButton4.setVisibility(View.VISIBLE);
            devamButton4.startAnimation(translateAnimationTwo);
            soruTextView4.startAnimation(translateAnimationTwo);
            soruTextView4.setVisibility(View.VISIBLE);
            fiyatEditText.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.VISIBLE);
            spinner.startAnimation(translateAnimationTwo);
            fiyatEditText.startAnimation(translateAnimationTwo);
            backTextView.setVisibility(View.VISIBLE);
            count++;
        }
    }

    public void devamOnClick4(View view){
        float x = Float.parseFloat(fiyatEditText.getText().toString());
        if (x == 0 || x > 10000){
            Toast.makeText(this, "1 - 10000", Toast.LENGTH_SHORT).show();
        }else {
            devamButton4.startAnimation(animationOnClickButton);

            progressBar.setProgress(6);
            soruTextView4.startAnimation(translateAnimationOne);
            soruTextView4.setVisibility(View.INVISIBLE);
            fiyatEditText.startAnimation(translateAnimationOne);
            fiyatEditText.setVisibility(View.INVISIBLE);
            spinner.setVisibility(View.INVISIBLE);
            spinner.startAnimation(translateAnimationOne);
            devamButton4.setVisibility(View.INVISIBLE);
            devamButton4.startAnimation(translateAnimationOne);
            fiyat = fiyatEditText.getText().toString();
            devamButton5.setVisibility(View.VISIBLE);
            devamButton5.startAnimation(translateAnimationTwo);
            soruTextView5.startAnimation(translateAnimationTwo);
            soruTextView5.setVisibility(View.VISIBLE);
            calendarTextView.setVisibility(View.VISIBLE);
            calendarTextView.startAnimation(translateAnimationTwo);
            backTextView.setVisibility(View.VISIBLE);
            count++;
        }
    }

    public void devamOnClick5(View view){
        devamButton5.startAnimation(animationOnClickButton);

        int sene = Integer.parseInt(kacSeneIctin);
        int gunlukIcilenSigaraInteger = Integer.parseInt(sigara);
        int pakettekiSigaraSayisiInteger = Integer.parseInt(paket);
        float paketinFiyatiInteger = Float.parseFloat(fiyat);

        final int profileBir = (365 * sene) * gunlukIcilenSigaraInteger;
        float taneFiyat = paketinFiyatiInteger / pakettekiSigaraSayisiInteger;
        final float profileIki = profileBir * taneFiyat;
        int profileUcMinutes = profileBir * 14;
        int profileUcHour = 0;
        int profileUcMonth = 0;
        int profileUcDay = 0;

        while (profileUcMinutes >= 60){
            profileUcHour++;
            profileUcMinutes-=60;
            while (profileUcHour >= 24){
                profileUcDay++;
                profileUcHour-=24;
                while (profileUcDay >= 31){
                    profileUcMonth++;
                    profileUcDay-=31;
                }
            }
        }

        if (!check){

            Intent intent = new Intent(this,CreateAccountActivity.class);
            intent.putExtra("fiyat",Float.parseFloat(fiyat));
            intent.putExtra("name",name);
            intent.putExtra("sigara",Integer.parseInt(sigara));
            intent.putExtra("paket",Integer.parseInt(paket));
            intent.putExtra("money",money);
            intent.putExtra("profileOne",profileBir);
            intent.putExtra("profileTwo",profileIki);
            intent.putExtra("profileThreeHour",profileUcHour);
            intent.putExtra("profileThreeDay",profileUcDay);
            intent.putExtra("profileThreeMonth",profileUcMonth);

            if (timeInMillis >= System.currentTimeMillis()){
                timeInMillis = System.currentTimeMillis();
            }
            intent.putExtra("time",timeInMillis);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }else {

            boolean isConnected = ConnectivityReceiver.isConnected();
            if (isConnected){

                if (timeInMillis >= System.currentTimeMillis()){
                    timeInMillis = System.currentTimeMillis();
                }

                final int finalProfileUcHour = profileUcHour;
                final int finalProfileUcDay = profileUcDay;
                final int finalProfileUcMonth = profileUcMonth;

                //update Firebase
                DatabaseReference databaseReference1 = firebaseDatabase.getReference();

                if (isNewUser){
                    //insert to Database
                    UUID uuid = UUID.randomUUID();
                    String uuidString = uuid.toString();

                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    String userName = firebaseUser.getEmail().toString();

                    //insert to Firebase
                    databaseReference1.child("Profiles").child(uuidString).child("username").setValue(userName);
                    databaseReference1.child("Profiles").child(uuidString).child("time").setValue(timeInMillis);
                    databaseReference1.child("Profiles").child(uuidString).child("fiyat").setValue(fiyat);
                    databaseReference1.child("Profiles").child(uuidString).child("name").setValue(name);
                    databaseReference1.child("Profiles").child(uuidString).child("sigara").setValue(sigara);
                    databaseReference1.child("Profiles").child(uuidString).child("paket").setValue(paket);
                    databaseReference1.child("Profiles").child(uuidString).child("money").setValue(money);
                    databaseReference1.child("Profiles").child(uuidString).child("profileOne").setValue(profileBir);
                    databaseReference1.child("Profiles").child(uuidString).child("profileTwo").setValue(profileIki);
                    databaseReference1.child("Profiles").child(uuidString).child("profileThreeHour").setValue(finalProfileUcHour);
                    databaseReference1.child("Profiles").child(uuidString).child("profileThreeDay").setValue(finalProfileUcDay);
                    databaseReference1.child("Profiles").child(uuidString).child("profileThreeMonth").setValue(finalProfileUcMonth);
                    databaseReference1.child("Profiles").child(uuidString).child("motivation").setValue(getResources().getString(R.string.motivation));

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(LastContentProvider.USERNAME,userName);
                    contentValues.put(LastContentProvider.TIME,timeInMillis);
                    contentValues.put(LastContentProvider.FIYAT,fiyat);
                    contentValues.put(LastContentProvider.NAME,name);
                    contentValues.put(LastContentProvider.SIGARA,sigara);
                    contentValues.put(LastContentProvider.PAKET,paket);
                    contentValues.put(LastContentProvider.MONEY,money);
                    contentValues.put(LastContentProvider.PROFILE_ONE,profileBir);
                    contentValues.put(LastContentProvider.PROFILE_TWO,profileIki);
                    contentValues.put(LastContentProvider.PROFILE_THREE_HOUR,finalProfileUcHour);
                    contentValues.put(LastContentProvider.PROFILE_THREE_DAY,finalProfileUcDay);
                    contentValues.put(LastContentProvider.PROFILE_THREE_MONTH,finalProfileUcMonth);
                    contentValues.put(LastContentProvider.MOTIVATION,getResources().getString(R.string.motivation));

                    Uri uri = getContentResolver().insert(LastContentProvider.CONTENT_URI,contentValues);
                }else {
                    databaseReference1.child("Profiles").child(uuid).child("time").setValue(timeInMillis);
                    databaseReference1.child("Profiles").child(uuid).child("fiyat").setValue(fiyat);
                    databaseReference1.child("Profiles").child(uuid).child("name").setValue(name);
                    databaseReference1.child("Profiles").child(uuid).child("sigara").setValue(sigara);
                    databaseReference1.child("Profiles").child(uuid).child("paket").setValue(paket);
                    databaseReference1.child("Profiles").child(uuid).child("money").setValue(money);
                    databaseReference1.child("Profiles").child(uuid).child("profileOne").setValue(profileBir);
                    databaseReference1.child("Profiles").child(uuid).child("profileTwo").setValue(profileIki);
                    databaseReference1.child("Profiles").child(uuid).child("profileThreeHour").setValue(finalProfileUcHour);
                    databaseReference1.child("Profiles").child(uuid).child("profileThreeDay").setValue(finalProfileUcDay);
                    databaseReference1.child("Profiles").child(uuid).child("profileThreeMonth").setValue(finalProfileUcMonth);

                    //update DataBase
                    String contentUriString = "content://com.kastudio.sonsigaram.LastContentProvider/last";
                    Uri contentUri = Uri.parse(contentUriString);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(LastContentProvider.TIME, timeInMillis);
                    contentValues.put(LastContentProvider.FIYAT, fiyat);
                    contentValues.put(LastContentProvider.NAME, name);
                    contentValues.put(LastContentProvider.SIGARA, sigara);
                    contentValues.put(LastContentProvider.PAKET, paket);
                    contentValues.put(LastContentProvider.MONEY, money);
                    contentValues.put(LastContentProvider.PROFILE_ONE, profileBir);
                    contentValues.put(LastContentProvider.PROFILE_TWO, profileIki);
                    contentValues.put(LastContentProvider.PROFILE_THREE_HOUR, finalProfileUcHour);
                    contentValues.put(LastContentProvider.PROFILE_THREE_DAY, finalProfileUcDay);
                    contentValues.put(LastContentProvider.PROFILE_THREE_MONTH, finalProfileUcMonth);
                    getContentResolver().update(contentUri, contentValues, "time=?", new String[]{String.valueOf(AnaEkranActivity.timeInMillis)});
                    getContentResolver().update(contentUri, contentValues, "fiyat=?", new String[]{String.valueOf(AnaEkranActivity.fiyat)});
                    getContentResolver().update(contentUri, contentValues, "name=?", new String[]{String.valueOf(AnaEkranActivity.name)});
                    getContentResolver().update(contentUri, contentValues, "sigara=?", new String[]{String.valueOf(AnaEkranActivity.sigaraSayisi)});
                    getContentResolver().update(contentUri, contentValues, "paket=?", new String[]{String.valueOf(AnaEkranActivity.pakettekiSigaraSayisi)});
                    getContentResolver().update(contentUri, contentValues, "money=?", new String[]{String.valueOf(AnaEkranActivity.money)});
                    getContentResolver().update(contentUri, contentValues, "profileThreeHour=?", new String[]{String.valueOf(AnaEkranActivity.profileThreeHour)});
                    getContentResolver().update(contentUri, contentValues, "profileThreeDay=?", new String[]{String.valueOf(AnaEkranActivity.profileThreeDay)});
                    getContentResolver().update(contentUri, contentValues, "profileThreeMonth=?", new String[]{String.valueOf(AnaEkranActivity.profileThreeMonth)});
                }
                Intent intent = new Intent(RegisterInfoActivity.this, AnaEkranActivity.class);
                AnaEkranActivity.getInstance().finish();
                startActivity(intent);
                finish();
                CustomIntent.customType(RegisterInfoActivity.this, "fadein-to-fadeout");
            }else {
                Toast.makeText(this, getResources().getString(R.string.failed_network), Toast.LENGTH_LONG).show();
            }

        }

    }

    public void checkButton(){

        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkEditText();
            }
        };
        nameEditText.addTextChangedListener(textWatcher);
        sigaraSayisiEditText.addTextChangedListener(textWatcher);
        paketteSigaraSayisiEditText.addTextChangedListener(textWatcher);
        fiyatEditText.addTextChangedListener(textWatcher);
        kacSeneIctinEditText.addTextChangedListener(textWatcher);
        checkEditText();

    }

    public void checkEditText(){

        name = nameEditText.getText().toString();
        sigara = sigaraSayisiEditText.getText().toString();
        fiyat = fiyatEditText.getText().toString();
        paket = paketteSigaraSayisiEditText.getText().toString();
        kacSeneIctin = kacSeneIctinEditText.getText().toString();

        if (name.equals("")){
            devamButton.setEnabled(false);
            devamButton.setAlpha(.5f);
        }else {
            devamButton.setEnabled(true);
            devamButton.setAlpha(1f);
        }
        if (kacSeneIctin.equals("")){
            devamButton1.setEnabled(false);
            devamButton1.setAlpha(0.5f);
        }else {
            devamButton1.setEnabled(true);
            devamButton1.setAlpha(1f);
        }
        if (sigara.equals("")){
            devamButton2.setEnabled(false);
            devamButton2.setAlpha(.5f);
        }else {
            devamButton2.setEnabled(true);
            devamButton2.setAlpha(1f);
        }
        if (paket.equals("")){
            devamButton3.setEnabled(false);
            devamButton3.setAlpha(.5f);
        }else {
            devamButton3.setEnabled(true);
            devamButton3.setAlpha(1f);
        }
        if (fiyat.equals("")){
            devamButton4.setEnabled(false);
            devamButton4.setAlpha(.5f);
        }else {
            devamButton4.setEnabled(true);
            devamButton4.setAlpha(1f);
        }
    }

    public void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - System.currentTimeMillis());
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        dayCalendar = dayOfMonth;
        yearCalendar = year;
        monthCalendar = month;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hourCalendar = hourOfDay;
                minuteCalendar = minute;

                Calendar calendar = Calendar.getInstance();
                calendar.set(yearCalendar,monthCalendar,dayCalendar,hourCalendar,minuteCalendar);
                timeInMillis = calendar.getTimeInMillis();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy  kk:mm");
                date = simpleDateFormat.format(timeInMillis);

                calendarTextView.setText(date);
            }
        },hour,minute,android.text.format.DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        money = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void findViewById(){
        nameEditText = findViewById(R.id.nameEditText);
        soruTextView = findViewById(R.id.soruTextView);
        backTextView = findViewById(R.id.backTextView);
        progressBar = findViewById(R.id.progressBar);
        soruTextView1 = findViewById(R.id.soruTextView1);
        soruTextView2 = findViewById(R.id.soruTextView2);
        soruTextView3 = findViewById(R.id.soruTextView3);
        soruTextView4 = findViewById(R.id.soruTextView4);
        soruTextView5 = findViewById(R.id.soruTextView5);
        calendarTextView = findViewById(R.id.calendarTextView);
        sigaraSayisiEditText = findViewById(R.id.sigaraSayiEditText);
        paketteSigaraSayisiEditText = findViewById(R.id.paketteSigaraSayiEditText);
        kacSeneIctinEditText = findViewById(R.id.kacSeneEditText);
        fiyatEditText = findViewById(R.id.fiyatEditText);
        spinner = findViewById(R.id.spinner2);
        devamButton = findViewById(R.id.devamButton);
        devamButton1 = findViewById(R.id.devamButton1);
        devamButton2 = findViewById(R.id.devamButton2);
        devamButton3 = findViewById(R.id.devamButton3);
        devamButton4 = findViewById(R.id.devamButton4);
        devamButton5 = findViewById(R.id.devamButton5);
    }

    public void onClickBackButton(){
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backTextView.startAnimation(animationOnClickButton);
                switch (count){
                    case 1:
                        devamButton1.startAnimation(backTranslateAnimationOne);
                        soruTextView1.startAnimation(backTranslateAnimationOne);
                        kacSeneIctinEditText.startAnimation(backTranslateAnimationOne);
                        devamButton1.setVisibility(View.INVISIBLE);
                        soruTextView1.setVisibility(View.INVISIBLE);
                        kacSeneIctinEditText.setVisibility(View.INVISIBLE);
                        soruTextView.startAnimation(backTranslateAnimationTwo);
                        nameEditText.startAnimation(backTranslateAnimationTwo);
                        devamButton.startAnimation(backTranslateAnimationTwo);
                        devamButton.setVisibility(View.VISIBLE);
                        soruTextView.setVisibility(View.VISIBLE);
                        nameEditText.setVisibility(View.VISIBLE);
                        backTextView.setVisibility(View.INVISIBLE);
                        progressBar.setProgress(1);
                        count--;
                        break;
                    case 2:
                        devamButton2.startAnimation(backTranslateAnimationOne);
                        soruTextView2.startAnimation(backTranslateAnimationOne);
                        sigaraSayisiEditText.startAnimation(backTranslateAnimationOne);
                        devamButton2.setVisibility(View.INVISIBLE);
                        soruTextView2.setVisibility(View.INVISIBLE);
                        sigaraSayisiEditText.setVisibility(View.INVISIBLE);
                        soruTextView1.startAnimation(backTranslateAnimationTwo);
                        kacSeneIctinEditText.startAnimation(backTranslateAnimationTwo);
                        devamButton1.startAnimation(backTranslateAnimationTwo);
                        devamButton1.setVisibility(View.VISIBLE);
                        soruTextView1.setVisibility(View.VISIBLE);
                        kacSeneIctinEditText.setVisibility(View.VISIBLE);
                        backTextView.setVisibility(View.VISIBLE);
                        progressBar.setProgress(2);
                        count--;
                        break;
                    case 3:
                        devamButton3.startAnimation(backTranslateAnimationOne);
                        soruTextView3.startAnimation(backTranslateAnimationOne);
                        paketteSigaraSayisiEditText.startAnimation(backTranslateAnimationOne);
                        devamButton3.setVisibility(View.INVISIBLE);
                        soruTextView3.setVisibility(View.INVISIBLE);
                        paketteSigaraSayisiEditText.setVisibility(View.INVISIBLE);
                        devamButton2.startAnimation(backTranslateAnimationTwo);
                        soruTextView2.startAnimation(backTranslateAnimationTwo);
                        sigaraSayisiEditText.startAnimation(backTranslateAnimationTwo);
                        devamButton2.setVisibility(View.VISIBLE);
                        soruTextView2.setVisibility(View.VISIBLE);
                        sigaraSayisiEditText.setVisibility(View.VISIBLE);
                        progressBar.setProgress(3);
                        count--;
                        break;
                    case 4:
                        devamButton4.startAnimation(backTranslateAnimationOne);
                        soruTextView4.startAnimation(backTranslateAnimationOne);
                        fiyatEditText.startAnimation(backTranslateAnimationOne);
                        spinner.startAnimation(backTranslateAnimationOne);
                        devamButton4.setVisibility(View.INVISIBLE);
                        spinner.setVisibility(View.INVISIBLE);
                        soruTextView4.setVisibility(View.INVISIBLE);
                        fiyatEditText.setVisibility(View.INVISIBLE);
                        devamButton3.startAnimation(backTranslateAnimationTwo);
                        soruTextView3.startAnimation(backTranslateAnimationTwo);
                        paketteSigaraSayisiEditText.startAnimation(backTranslateAnimationTwo);
                        devamButton3.setVisibility(View.VISIBLE);
                        soruTextView3.setVisibility(View.VISIBLE);
                        paketteSigaraSayisiEditText.setVisibility(View.VISIBLE);
                        progressBar.setProgress(4);
                        count--;
                        break;
                    case 5:
                        devamButton5.setVisibility(View.INVISIBLE);
                        soruTextView5.startAnimation(backTranslateAnimationOne);
                        calendarTextView.startAnimation(backTranslateAnimationOne);
                        devamButton5.startAnimation(backTranslateAnimationOne);
                        soruTextView5.setVisibility(View.INVISIBLE);
                        calendarTextView.setVisibility(View.INVISIBLE);
                        devamButton4.startAnimation(backTranslateAnimationTwo);
                        soruTextView4.startAnimation(backTranslateAnimationTwo);
                        fiyatEditText.startAnimation(backTranslateAnimationTwo);
                        devamButton4.setVisibility(View.VISIBLE);
                        spinner.startAnimation(backTranslateAnimationTwo);
                        spinner.setVisibility(View.VISIBLE);
                        soruTextView4.setVisibility(View.VISIBLE);
                        fiyatEditText.setVisibility(View.VISIBLE);
                        progressBar.setProgress(5);
                        count--;
                        break;
                }
            }
        });
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}