package com.gyk.s2h.varmisin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//Kullanıcı Anasayfası Arkadaşlarımızın paylaştıkları iddialar burada!

public class AnaSayfa extends Fragment {
    FirebaseDatabase database;
    PaylasilanlarAdapter adapter;
    private String userID,key,value,isim;
    private String gonderen,detay,sure,durum,kategori;
    private DatabaseReference mDatabase;
    List<String> arrList = new ArrayList<String>();




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
        final View view = inflater.inflate(R.layout.activity_ana_sayfa, container, false);

        final ArrayList<PaylasmaModel> paylasilanList = new ArrayList<PaylasmaModel>();
        final ListView listView =(ListView)view.findViewById(R.id.listView);

        database=FirebaseDatabase.getInstance();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        userID = user.getUid();

        Log.d("userID:", userID);

        mDatabase = FirebaseDatabase.getInstance().getReference();

            final DatabaseReference dbRef =database.getReference("Paylasilanlar");
            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot ds :dataSnapshot.getChildren()){

                        key=ds.getKey().toString();

                        Log.d("keyA",key);
                        DatabaseReference dbRef1=database.getReference("Paylasilanlar").child(key);
                        dbRef1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds1:dataSnapshot.getChildren()){
                                    durum=ds1.child("durum").getValue().toString();
                                    Log.d("durum",durum);
                                    kategori=ds1.child("kategori").getValue().toString();
                                    Log.d("kategori",kategori);
                                    sure=ds1.child("sure").getValue().toString();
                                    Log.d("sure",sure);
                                    gonderen =ds1.child("gonderen").getValue().toString();
                                    Log.d("gonderen",gonderen);
                                    detay =ds1.child("adi").getValue().toString();
                                    Log.d("detay",detay);

                                    isim=ds1.child("isim").getValue().toString();
                                    Log.d("isim",isim);


                                    paylasilanList.add(new PaylasmaModel(isim,gonderen,kategori,detay,durum,sure));
                                    adapter = new PaylasilanlarAdapter((FragmentActivity) view.getContext(), paylasilanList);
                                    listView.setAdapter(adapter);




                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });





                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });








        return view;
    }




}
