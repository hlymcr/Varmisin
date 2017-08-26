package com.gyk.s2h.varmisin;

/**
 * Created by HULYA on 26.08.2017.
 */
/**
 * Created by HULYA on 23.07.2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//İddialarım sayfası.
public class Iddialarim extends Fragment implements View.OnClickListener {

    public static Iddialarim newInstance() {
        Iddialarim fragment = new Iddialarim();
        return fragment;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public Iddialarim(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.iddilarim, container, false);

        return view;
    }

    @Override
    public void onClick(View view) {

    }



}