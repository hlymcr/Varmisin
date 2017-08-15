package com.gyk.s2h.varmisin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class AnaSayfa extends Fragment implements
        GoogleApiClient.OnConnectionFailedListener {

    public static AnaSayfa newInstance() {
        AnaSayfa fragment = new AnaSayfa();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ana_sayfa, container, false);

        return view;
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }



}
