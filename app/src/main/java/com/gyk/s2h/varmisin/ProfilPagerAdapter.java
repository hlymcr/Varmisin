package com.gyk.s2h.varmisin;

/**
 * Created by HULYA on 16.08.2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by HULYA on 20.07.2017.
 */

public class ProfilPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public ProfilPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ProfilAnasayfa tab1 = new ProfilAnasayfa();
                return tab1;
            case 1:
                ProfilArkadaslar tab2 = new ProfilArkadaslar();
                return tab2;
            case 2:
                ProfilYaptiklarim tab3=new ProfilYaptiklarim();
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