package com.MDQ.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class Adapterstab extends FragmentStatePagerAdapter {
    public Adapterstab(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
               return new f001();
            case 1:
                return new f002();
            case 2:
                return new f003();
            case 3:
                return new f004();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence[] names={"Day","Week","Month","Year"};
        return names[position];
    }
}
