package com.gyk.s2h.varmisin;

/**
 * Created by HULYA on 23.07.2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ArkadasAra extends Fragment {
    public static ArkadasAra newInstance() {
        ArkadasAra fragment = new ArkadasAra();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.arkara, container, false);
    }
}