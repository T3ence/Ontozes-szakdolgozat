package com.example.ontozes.ui.main;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.ontozes.Dob;
import com.example.ontozes.Linear;
import com.example.ontozes.Parameterek;
import com.example.ontozes.Vezerles;
import com.example.ontozes.data.App;
import com.example.ontozes.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */


public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final App app = new App();
    //Fragments:
    public Parameterek parameterek;
    public Dob dob;
    public Vezerles vezerles;
    public Linear linear;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.Paraméterek, R.string.Vezérlés, R.string.Dob,R.string.Lineár};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        parameterek = new Parameterek();
        vezerles = new Vezerles();
        dob = new Dob();
        linear = new Linear();

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                //fragment = new Parameterek();
                fragment = this.parameterek;
                break;
            case 1:
                fragment = this.vezerles;
                break;
            case 2:
                fragment = this.dob;
                break;
            case 3:
                fragment = this.linear;
                break;
        }
        return fragment;


        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        //return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show total pages.
        return 4;
    }
}