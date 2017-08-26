package com.gyk.s2h.varmisin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

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

/**
 * Created by HULYA on 25.08.2017.
 */

public class ArkadasProfilArkadaslar extends Fragment  {
    ListView listView;
    FirebaseDatabase database;
    private String userID;
    ArkadaslarAdapter adapter;
    private DatabaseReference mDatabase;
    String isim,kullaniciAdi,dtarih,path,uri;
    List<String> arrList = new ArrayList<String>();
    final ArrayList<ArkadasModel> arkadaslar = new ArrayList<ArkadasModel>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        userID = user.getUid();

        Log.d("userID:", userID);

        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.arkadasprofilarkadaslar, container, false);

        listView=(ListView)view.findViewById(R.id.listview);


        ArkadasProfil activity = (ArkadasProfil) getActivity();
        String key=activity.getMyData();
        
        final DatabaseReference dbRef =database.getReference("arkadaslar").child(key);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren()){

                    String key=ds.getValue().toString();

                    arrList.add(key);


                }
                Log.d("arrlist", String.valueOf(arrList));
                Log.d("uzunluk", String.valueOf(arrList.size()));
                //Log.d("ilk eleman",arrList.get(0));
                //arrlist de bir eleman varsa bu metod çalışır.
                if(arrList.size()>0) {
                    for (int i = 0; i < arrList.size(); i++) {

                        final DatabaseReference dbRef1 = database.getReference("users").child(arrList.get(i));
                        final int finalI = i;
                        dbRef1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds1 : dataSnapshot.getChildren()) {

                                    if (ds1.getKey().toString().equals("adSoyad")) {
                                        isim = ds1.getValue().toString();
                                        Log.d("isimler", isim);
                                    }
                                    if (ds1.getKey().toString().equals("dtarih")) {
                                        dtarih = ds1.getValue().toString();

                                    }
                                    if (ds1.getKey().toString().equals("kullanici_adi")) {
                                        kullaniciAdi = ds1.getValue().toString();

                                    }
                                    if (ds1.getKey().toString().equals("path")) {
                                        path = ds1.getValue().toString();

                                    }



                                /*String value = ds1.getKey().toString();
                                Log.d("value1",value);
                                arrList1.add(value);*/

                                }
                                Log.d("isim1", isim);
                                Log.d("kullaniciAdi", kullaniciAdi);
                                Log.d("dtarih", dtarih);
                                //Log.d("uri", uri);
                                Log.d("path", path);
                                uri=arrList.get(finalI);

                                arkadaslar.add(new ArkadasModel(isim, kullaniciAdi, dtarih, path, uri));
                                Log.d("istekler", String.valueOf(arkadaslar));
                                adapter = new ArkadaslarAdapter(view.getContext(), arkadaslar);
                                listView.setAdapter(adapter);


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }

                }
                else{
                    Toast.makeText(view.getContext(), "Arkadaşınız Bulunmuyor..", Toast.LENGTH_SHORT).show();
                }





            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        return view;
    }


}