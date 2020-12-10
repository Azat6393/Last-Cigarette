package com.kastudio.sonsigaram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.kastudio.sonsigaram.Fragments.FragmentAnaEkran;
import com.kastudio.sonsigaram.Fragments.FragmentForum;
import com.kastudio.sonsigaram.Fragments.FragmentKarisik;
import com.kastudio.sonsigaram.Fragments.FragmentProfile;

import maes.tech.intentanim.CustomIntent;

public class AnaEkranActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    FragmentAnaEkran anaEkran;
    FragmentKarisik karisik;
    FragmentForum forum;
    FragmentProfile profile;

    public static long timeInMillis;
    public static float fiyat;
    public static int sigaraSayisi;
    public static int pakettekiSigaraSayisi;
    public static String money;
    public static String name;
    public static int profileOne;
    public static float profileTwo;
    public static int profileThreeHour;
    public static int profileThreeMonth;
    public static int profileThreeDay;
    public static String username;
    public static String motivation;

    static AnaEkranActivity anaEkranActivity;

    public static AnaEkranActivity getInstance(){
        return anaEkranActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_ekran);

        anaEkranActivity = this;

        anaEkran = new FragmentAnaEkran();
        karisik = new FragmentKarisik();
        forum = new FragmentForum();
        profile = new FragmentProfile();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        setCurrentFragment(anaEkran);
        getData();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()){
                case R.id.page_1:
                    setCurrentFragment(anaEkran);
                    break;
                case R.id.page_2:
                    setCurrentFragment(karisik);
                    break;
                case R.id.page_3:
                    setCurrentFragment(forum);
                    break;
                case R.id.page_4:
                    setCurrentFragment(profile);
                    break;
            }
            return true;
        }
    };

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this,"fadein-to-fadeout");
    }

    public void getData(){
        String Url = "content://com.kastudio.sonsigaram.LastContentProvider";
        Uri lastUri = Uri.parse(Url);

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(lastUri,null,null,null,"name");

        if (cursor != null){

            while (cursor.moveToNext()){

                timeInMillis = cursor.getLong(cursor.getColumnIndex(LastContentProvider.TIME));
                fiyat = cursor.getFloat(cursor.getColumnIndex(LastContentProvider.FIYAT));
                sigaraSayisi = cursor.getInt(cursor.getColumnIndex(LastContentProvider.SIGARA));
                money = cursor.getString(cursor.getColumnIndex(LastContentProvider.MONEY));
                pakettekiSigaraSayisi = cursor.getInt(cursor.getColumnIndex(LastContentProvider.PAKET));
                profileOne = cursor.getInt(cursor.getColumnIndex(LastContentProvider.PROFILE_ONE));
                profileTwo = cursor.getFloat(cursor.getColumnIndex(LastContentProvider.PROFILE_TWO));
                profileThreeHour = cursor.getInt(cursor.getColumnIndex(LastContentProvider.PROFILE_THREE_HOUR));
                profileThreeMonth = cursor.getInt(cursor.getColumnIndex(LastContentProvider.PROFILE_THREE_MONTH));
                profileThreeDay = cursor.getInt(cursor.getColumnIndex(LastContentProvider.PROFILE_THREE_DAY));
                username = cursor.getString(cursor.getColumnIndex(LastContentProvider.USERNAME));
                name = cursor.getString(cursor.getColumnIndex(LastContentProvider.NAME));
                motivation = cursor.getString(cursor.getColumnIndex(LastContentProvider.MOTIVATION));

            }
            if (timeInMillis == 0){
                Intent intent = new Intent(getApplicationContext(),AnaEkranActivity.class);
                finish();
                startActivity(intent);
            }
        }
    }

    public void setCurrentFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame_layout,fragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}