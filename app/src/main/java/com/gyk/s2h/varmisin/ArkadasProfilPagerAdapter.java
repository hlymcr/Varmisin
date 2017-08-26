package com.gyk.s2h.varmisin;

/**
 * Created by HULYA on 25.08.2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by HULYA on 20.07.2017.
 */

public class ArkadasProfilPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public ArkadasProfilPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ArkadasProfilAnasayfa tab1 = new ArkadasProfilAnasayfa();
                return tab1;
            case 1:
                ArkadasProfilArkadaslar tab2 = new ArkadasProfilArkadaslar();
                return tab2;
            case 2:
                ArkadasProfilArmalar tab3=new ArkadasProfilArmalar();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}