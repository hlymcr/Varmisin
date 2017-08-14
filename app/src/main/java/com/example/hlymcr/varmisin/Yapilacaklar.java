package com.example.hlymcr.varmisin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by HULYA on 23.07.2017.
 */

public class Yapilacaklar extends Fragment {
    public static Yapilacaklar newInstance() {
        Yapilacaklar fragment = new Yapilacaklar();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.yapilcaklar, container, false);
    }
}