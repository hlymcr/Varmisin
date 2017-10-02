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


public class VarmisinYIstekleriAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    ArrayList<YuruyusModel> vIstekleri;
    TextView isim, iddia;
    ImageView resim;
    FirebaseDatabase database;
    private String userID;
    private DatabaseReference mDatabase;
    FloatingActionButton onay,sil;



    public VarmisinYIstekleriAdapter(FragmentActivity activity, ArrayList<YuruyusModel> vIstekleri) {

        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.vIstekleri = vIstekleri;

    }


    @Override
    public int getCount() {
        return vIstekleri.size();
    }

    @Override
    public Object getItem(int i) {
        return vIstekleri.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final YuruyusModel kisiModel = vIstekleri.get(i);
        final View satir = layoutInflater.inflate(R.layout.banagelen_list_item, null);


        database = FirebaseDatabase.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        userID = user.getUid();

        Log.d("userID:", userID);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        isim=(TextView)satir.findViewById(R.id.adSoyad);
        iddia=(TextView)satir.findViewById(R.id.iddia);
        onay=(FloatingActionButton)satir.findViewById(R.id.onay);
        sil=(FloatingActionButton)satir.findViewById(R.id.sil);

        onay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mDatabase.child("adimIddia").child(kisiModel.getUid()).child(userID).child("istek").setValue("kabul");



                mDatabase.child("Yapilacaklar").child(userID).child(kisiModel.getUid()).child("sure").setValue(kisiModel.getSure());
                mDatabase.child("Yapilacaklar").child(userID).child(kisiModel.getUid()).child("adimS").setValue(kisiModel.getAdimS());
                mDatabase.child("Yapilacaklar").child(userID).child(kisiModel.getUid()).child("isim").setValue(kisiModel.getIsim());
                mDatabase.child("Yapilacaklar").child(userID).child(kisiModel.getUid()).child("uid").setValue(kisiModel.getUid());
                mDatabase.child("Yapilacaklar").child(userID).child(kisiModel.getUid()).child("durum").setValue("kabul");

                final  DatabaseReference dbRef=FirebaseDatabase.getInstance().getReference().child("adimIstek").child(userID);
                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            String key =ds.getKey().toString();
                            if(key.equals(kisiModel.getUid())){
                                ds.getRef().removeValue();
                                Toast.makeText(satir.getContext(), "İstek kabul edildi.", Toast.LENGTH_SHORT).show();
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


                final  DatabaseReference dbRef1=FirebaseDatabase.getInstance().getReference().child("adimIddia").child(kisiModel.getUid());
                dbRef1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            String key =ds.getKey().toString();
                            if(key.equals(userID)){
                                ds.getRef().removeValue();

                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                final  DatabaseReference dbRef=FirebaseDatabase.getInstance().getReference().child("adimIstek").child(userID);
                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            String key =ds.getKey().toString();
                            if(key.equals(kisiModel.getUid())){
                                ds.getRef().removeValue();
                                Toast.makeText(satir.getContext(), "İstek reddedildi.", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });


        isim.setText(kisiModel.getIsim());
        iddia.setText(kisiModel.getSure()+" saat sürede "+kisiModel.getAdimS()+" adım atmaya var mısın?");


        return satir;

    }

}