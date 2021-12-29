package com.fahamutech.doctorapp.adapter;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.fahamutech.doctorapp.fragment.HomeArticlesFragment;
import com.fahamutech.doctorapp.fragment.HomeTestimonyFragment;

public class HomePageFragmentAdapter extends FragmentPagerAdapter {

    public HomePageFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new HomeArticlesFragment();
        } else if (position == 1) {
            return new HomeTestimonyFragment();
        } else return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Masomo";
        } else if (position == 1) {
            return "Ushuhuda";
        }else return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
