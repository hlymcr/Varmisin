package com.gyk.s2h.varmisin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by HULYA on 23.07.2017.
 */

public class Yapilacaklar extends Fragment {
   ListView listView;
    FirebaseDatabase database;
    private String userID;
    YapilacaklarAdapter adapter;
    String isim,adimS,sure,uid,durum;
    private DatabaseReference mDatabase;
    final ArrayList<YuruyusModel> yapilacaklar = new ArrayList<YuruyusModel>();

    public static Yapilacaklar newInstance() {
        Yapilacaklar fragment = new Yapilacaklar();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.yapilacaklar, container, false);
        database = FirebaseDatabase.getInstance();

        listView=(ListView)view.findViewById(R.id.listview);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        userID = user.getUid();

        Log.d("userID:", userID);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference dbref=database.getReference("Yapilacaklar").child(userID);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds:dataSnapshot.getChildren()){

                    isim=ds.child("isim").getValue().toString();
                    adimS=ds.child("adimS").getValue().toString();
                    sure=ds.child("sure").getValue().toString();
                    uid=ds.child("uid").getValue().toString();
                    durum=ds.child("durum").getValue().toString();

                    yapilacaklar.add(new YuruyusModel(isim, sure, adimS, uid,durum));
                    /*if(ds.getKey().toString().equals("isim")){
                        isim=ds.getValue().toString();
                    }
                    if(ds.getKey().toString().equals("adimS")){
                        adimS=ds.getValue().toString();
                    }
                    if(ds.getKey().toString().equals("sure")){
                        sure=ds.getValue().toString();
                    }
                    if(ds.getKey().toString().equals("uid")){
                        uid=ds.getValue().toString();

                    }
                    if(ds.getKey().toString().equals("durum")){

                        durum=ds.getValue().toString();

                    }*/

                }


                Log.d("yapilacaklar", String.valueOf(yapilacaklar));
                adapter = new YapilacaklarAdapter((FragmentActivity) view.getContext(), yapilacaklar);
                listView.setAdapter(adapter);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String isim=yapilacaklar.get(i).getIsim();
                String sure=yapilacaklar.get(i).getSure();
                String adimS=yapilacaklar.get(i).getAdimS();
                String uid=yapilacaklar.get(i).getUid();
                String durum =yapilacaklar.get(i).getIstek();

                Intent intent =new Intent(view.getContext(),YuruyusDetayKabul.class);

                intent.putExtra("isim",isim);
                intent.putExtra("sure",sure);
                intent.putExtra("adimS",adimS);
                intent.putExtra("uid",uid);
                intent.putExtra("durum",durum);
                view.getContext().startActivity(intent);


            }
        });

        return view;
    }
}