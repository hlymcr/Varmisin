package com.gyk.s2h.varmisin;

/**
 * Created by HULYA on 27.08.2017.
 */

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


//Arkadaş  İstekleri listemizi doldurmamızı sağlayan class
public class IddialarimAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    ArrayList<YuruyusModel> iddiaList;
    TextView adSoyad, Iddia;
    ImageView resim;
    FirebaseDatabase database;
    private String userID, istekC;
    private DatabaseReference mDatabase;
    FloatingActionButton bekleme;
    String istek;

    List<String> arrList = new ArrayList<String>();



    public IddialarimAdapter(FragmentActivity activity, ArrayList<YuruyusModel> iddiaList) {

        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.iddiaList = iddiaList;

    }


    @Override
    public int getCount() {
        return iddiaList.size();
    }

    @Override
    public Object getItem(int i) {
        return iddiaList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        YuruyusModel kisiModel = iddiaList.get(i);
        final View satir = layoutInflater.inflate(R.layout.benim_yolladiklarim_list_item, null);


        adSoyad = (TextView) satir.findViewById(R.id.adSoyad);
        Iddia = (TextView) satir.findViewById(R.id.iddia);
        bekleme = (FloatingActionButton) satir.findViewById(R.id.bekleme);


        database = FirebaseDatabase.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        userID = user.getUid();

        Log.d("userID:", userID);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        if(kisiModel.getIstek().equals("kabul")){
            bekleme.setImageResource(R.drawable.check);
            bekleme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(satir.getContext(), "İstek kabul edildi.", Toast.LENGTH_SHORT).show();


                }
            });

        }
        bekleme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(satir.getContext(), "Onay bekleniyor...", Toast.LENGTH_SHORT).show();
            }
        });
        adSoyad.setText(kisiModel.getIsim());
        Iddia.setText(kisiModel.getSure() + " saat sürede " + kisiModel.getAdimS() + " adım");

        return satir;

    }


}