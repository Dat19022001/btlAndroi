package com.example.btl.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.btl.fragment.FragmentBill;
import com.example.btl.fragment.FragmentCart;
import com.example.btl.fragment.FragmentHome;
import com.example.btl.fragment.FragmentSearch;
import com.example.btl.fragment.FragmentUser;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behaviorResumeOnlyCurrentFragment) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new FragmentHome();
            case 1: return new FragmentSearch();
            case 2:return new FragmentCart();
            case 3: return new FragmentBill();
            case 4: return new FragmentUser();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
