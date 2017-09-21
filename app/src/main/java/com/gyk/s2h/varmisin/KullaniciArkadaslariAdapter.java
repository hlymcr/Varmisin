package com.gyk.s2h.varmisin;

/**
 * Created by HULYA on 25.08.2017.
 */
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


//Kullanıcının arkadaşları listemizi doldurmamızı sağlayan class
public class KullaniciArkadaslariAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    ArrayList<ArkadasModel> kullaniciList;
    TextView kullaniciAdi, adSoyad;
    ImageView resim;
    FirebaseDatabase database;
    private String userID;
    private DatabaseReference mDatabase;
    FloatingActionButton onay, sil;


    public KullaniciArkadaslariAdapter(Context activity, ArrayList<ArkadasModel> kullaniciList) {

        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.kullaniciList = kullaniciList;

    }


    @Override
    public int getCount() {
        return kullaniciList.size();
    }

    @Override
    public Object getItem(int i) {
        return kullaniciList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ArkadasModel arkadasModel = kullaniciList.get(i);
        final View satir = layoutInflater.inflate(R.layout.arkadaslarim_list_item, null);


        database = FirebaseDatabase.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        userID = user.getUid();

        Log.d("userID:", userID);

        mDatabase = FirebaseDatabase.getInstance().getReference();


        //Profil resimlerini oval yapmayı sağlayan metod
        final Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.GRAY)
                .borderWidthDp(4)
                .cornerRadiusDp(35)
                .oval(true)
                .build();


        kullaniciAdi = (TextView) satir.findViewById(R.id.kullaniciAd);
        adSoyad = (TextView) satir.findViewById(R.id.adSoyad);
        resim = (ImageView) satir.findViewById(R.id.resim);

        String secilenresim = arkadasModel.getPath();
//        Log.d("secilenresim", secilenresim);
        Uri secim = Uri.parse(secilenresim);
        Picasso.with(satir.getContext()).load(secim).fit().transform(transformation).into(resim);

        kullaniciAdi.setText(arkadasModel.getKullanici_adi());
        adSoyad.setText(arkadasModel.getAdSoyad());


        return satir;

    }
}