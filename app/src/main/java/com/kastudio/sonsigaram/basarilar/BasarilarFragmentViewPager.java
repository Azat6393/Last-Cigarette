package com.kastudio.sonsigaram.basarilar;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class BasarilarFragmentViewPager extends FragmentPagerAdapter {

    public final Context context;

    public BasarilarFragmentViewPager(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        if (position == 0){
            return BasarilarFragmentBir.newInstance();
        }else if (position == 1){
            return BasarilarFragmentIki.newInstance();
        }else {
            return BasarilarFragmentUc.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
