package com.gyk.s2h.varmisin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by HULYA on 23.07.2017.
 */

public class Yapilacaklar extends Fragment {
    Button button;
    TextView deneme;
    public static Yapilacaklar newInstance() {
        Yapilacaklar fragment = new Yapilacaklar();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.yapilcaklar, container, false);

        return view;
    }
}