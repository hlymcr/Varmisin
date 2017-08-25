package com.gyk.s2h.varmisin;

/**
 * Created by HULYA on 23.08.2017.
 */

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;


//Arkadaş  İstekleri listemizi doldurmamızı sağlayan class
public class ArkadasIstekleriAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    ArrayList<ArkadasModel> kullaniciList;
    TextView kullaniciAdi, adSoyad;
    ImageView resim;
    Context context;


    public ArkadasIstekleriAdapter(FragmentActivity activity, ArrayList<ArkadasModel> kullaniciList) {

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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ArkadasModel kisiModel = kullaniciList.get(i);
        View satir = layoutInflater.inflate(R.layout.list_item2, null);

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

        String secilenresim = kisiModel.getPath();
        Log.d("secilenresim",secilenresim);
        Uri secim = Uri.parse(secilenresim);


        Picasso.with(satir.getContext()).load(secim).fit().transform(transformation).into(resim);

        kullaniciAdi.setText(kisiModel.getKullanici_adi());
        adSoyad.setText(kisiModel.getAdSoyad());


        return satir;

    }
}