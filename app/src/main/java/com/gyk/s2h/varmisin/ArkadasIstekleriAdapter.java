package com.gyk.s2h.varmisin;

/**
 * Created by HULYA on 23.08.2017.
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
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


//Arkadaş  İstekleri listemizi doldurmamızı sağlayan class
public class ArkadasIstekleriAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    ArrayList<ArkadasModel> kullaniciList;
    TextView kullaniciAdi, adSoyad;
    ImageView resim;
    FirebaseDatabase database;
    private String userID;
    private DatabaseReference mDatabase;
    FloatingActionButton onay,sil;


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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ArkadasModel kisiModel = kullaniciList.get(i);
        final View satir = layoutInflater.inflate(R.layout.arkadas_istekleri_list_item, null);


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
        onay=(FloatingActionButton) satir.findViewById(R.id.onay);
        sil=(FloatingActionButton)satir.findViewById(R.id.delete);



        onay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String uid=kullaniciList.get(i).getUid();
                Log.d("uid",uid);
                mDatabase.child("arkadaslar").child(userID).push().setValue(uid);
                mDatabase.child("arkadaslar").child(uid).push().setValue(userID);
                final  DatabaseReference dbRef=FirebaseDatabase.getInstance().getReference().child("yistekler").child(userID);
                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            String key =ds.getValue().toString();
                            if(key.equals(uid)){
                                ds.getRef().removeValue();
                                Toast.makeText(satir.getContext(), "Onaylandı", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String uid=kullaniciList.get(i).getUid();
                Log.d("uid",uid);
                final  DatabaseReference dbRef=FirebaseDatabase.getInstance().getReference().child("yistekler").child(userID);
                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                       for(DataSnapshot ds : dataSnapshot.getChildren()){
                           String key =ds.getValue().toString();
                           if(key.equals(uid)){
                               ds.getRef().removeValue();
                               Toast.makeText(satir.getContext(), "Silindi", Toast.LENGTH_SHORT).show();
                           }
                       }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }
        });

        String secilenresim = kisiModel.getPath();
        Log.d("secilenresim",secilenresim);
        Uri secim = Uri.parse(secilenresim);
        Picasso.with(satir.getContext()).load(secim).fit().transform(transformation).into(resim);

        kullaniciAdi.setText(kisiModel.getKullanici_adi());
        adSoyad.setText(kisiModel.getAdSoyad());


        return satir;

    }
}