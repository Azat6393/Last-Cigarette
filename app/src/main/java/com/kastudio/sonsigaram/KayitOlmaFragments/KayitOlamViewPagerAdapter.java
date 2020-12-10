package com.kastudio.sonsigaram.KayitOlmaFragments;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.kastudio.sonsigaram.KayitOlmaFragments.KayitOlmaFragmentBir;
import com.kastudio.sonsigaram.KayitOlmaFragments.KayitOlmaFragmentIki;

public class KayitOlamViewPagerAdapter extends FragmentPagerAdapter {

    public final Context mContext;


    public KayitOlamViewPagerAdapter(@NonNull FragmentManager fm,Context context) {
        super(fm);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return KayitOlmaFragmentBir.newInstance();
        }else if (position == 1){
            return KayitOlmaFragmentIki.newInstance();
        }else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

}
