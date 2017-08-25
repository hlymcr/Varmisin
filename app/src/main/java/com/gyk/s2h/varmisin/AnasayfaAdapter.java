package com.gyk.s2h.varmisin;

/**
 * Created by HULYA on 18.08.2017.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by HULYA on 20.07.2017.
 */
//Alt Bottom Navigation Adapter sayfası
public class AnasayfaAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public AnasayfaAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                AnaSayfa tab1 = new AnaSayfa();
                return tab1;
            case 1:
                İddialarim tab2 = new İddialarim();
                return tab2;
            case 2:
                Yapilacaklar tab3=new Yapilacaklar();
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