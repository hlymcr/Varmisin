package com.gyk.s2h.varmisin;

/**
 * Created by HULYA on 28.08.2017.
 */

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


//Arkadaş  İstekleri listemizi doldurmamızı sağlayan class
public class YapilacaklarAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    ArrayList<YuruyusModel> yapilacaklarList;
    TextView adSoyad, Iddia;
    ImageView resim;
    FirebaseDatabase database;
    private String userID;
    private DatabaseReference mDatabase;
    FloatingActionButton durum;
    String hazır,Esaat,süre,gün,saat;
    int zaman;
    String[] süreler,Asaat;
    Date currentTime;


    public YapilacaklarAdapter(FragmentActivity activity, ArrayList<YuruyusModel> yapilacaklarList) {

        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.yapilacaklarList = yapilacaklarList;

    }


    @Override
    public int getCount() {
        return yapilacaklarList.size();
    }

    @Override
    public Object getItem(int i) {
        return yapilacaklarList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final YuruyusModel kisiModel = yapilacaklarList.get(i);
        final View satir = layoutInflater.inflate(R.layout.yapilacaklar_list_item, null);


        adSoyad = (TextView) satir.findViewById(R.id.adSoyad);
        Iddia = (TextView) satir.findViewById(R.id.iddia);
        durum=(FloatingActionButton)satir.findViewById(R.id.durum);

        database = FirebaseDatabase.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        userID = user.getUid();

        Log.d("userID:", userID);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        adSoyad.setText(kisiModel.getIsim());
        Iddia.setText(kisiModel.getSure()+" saat de "+kisiModel.getAdimS()+" adıma varım");
        durum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(satir.getContext());
                builder.setMessage("Başlamaya hazır mısın?");
                builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id) {

                        //İptal butonuna basılınca yapılacaklar.Sadece kapanması isteniyorsa boş bırakılacak

                    }
                });


                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mDatabase.child("Yapilacaklar").child(userID).child(kisiModel.getUid()).child("durum").setValue("hazır");
                        currentTime= Calendar.getInstance().getTime();
                        Log.d("date1", String.valueOf(currentTime));
                        süre =String.valueOf(currentTime);
                        süreler=süre.split(" ");
                        gün =süreler[2];
                        saat=süreler[3];
                      //  mDatabase.child("Yapilacaklar").child(userID).child(kisiModel.getUid()).child("Bsaat").setValue(saat);

                        Asaat=saat.split(":");
                        Esaat=Asaat[0];
                        String dakika =Asaat[1];
                        zaman=Integer.parseInt(kisiModel.getSure())+Integer.parseInt(Esaat);
                        Toast.makeText(satir.getContext(), "zaman:"+zaman, Toast.LENGTH_SHORT).show();
                        Log.d("saat",saat);



                        //Tamam butonuna basılınca yapılacaklar

                    }
                });



                builder.show();





            }
        });

        if(kisiModel.getIstek().equals("hazır")){
            durum.setImageResource(R.drawable.walk);

            durum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(satir.getContext(), "Süre Başladı!!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(satir.getContext(),"zaman"+zaman,Toast.LENGTH_LONG).show();
                }
            });

        }


        return satir;

    }
}


