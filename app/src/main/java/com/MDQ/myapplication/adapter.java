package com.MDQ.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class adapter extends FragmentStatePagerAdapter {
    public adapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new fragment0();
            case 1:
                return new fragment1();
            case 2:
                return new fragment2();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
