package com.gyk.s2h.varmisin;

/**
 * Created by HULYA on 25.08.2017.
 */

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
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
import java.util.List;


public class ArkadasIstekleri extends AppCompatActivity {


    FirebaseDatabase database;
    ArkadasIstekleriAdapter adapter;
    private String userID,uid;
    String isim,kullaniciAdi,dtarih,uri,path;
    private DatabaseReference mDatabase;

    List<String> arrList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arkadas_istekleri);
        final ArrayList<ArkadasModel> istekler = new ArrayList<ArkadasModel>();
        final ListView listView =(ListView)findViewById(R.id.listview);


        database=FirebaseDatabase.getInstance();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        userID = user.getUid();

        Log.d("userID:", userID);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference dbRef =database.getReference("yistekler").child(userID);
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
                                    if (ds1.getKey().toString().equals("uid")) {
                                        uri = ds1.getValue().toString();

                                    }


                                /*String value = ds1.getKey().toString();
                                Log.d("value1",value);
                                arrList1.add(value);*/

                                }
                                Log.d("isim1", isim);
                                Log.d("kullaniciAdi", kullaniciAdi);
                                Log.d("dtarih", dtarih);
                                Log.d("uri", uri);
                                Log.d("path", path);

                                istekler.add(new ArkadasModel(isim, kullaniciAdi, dtarih, path, uri));
                                Log.d("istekler", String.valueOf(istekler));
                                adapter = new ArkadasIstekleriAdapter(ArkadasIstekleri.this, istekler);
                                listView.setAdapter(adapter);


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }

                }
                else{
                    Toast.makeText(ArkadasIstekleri.this, "Arkadaşlık İsteği bulunmuyor.", Toast.LENGTH_SHORT).show();
                }





            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });








    }
}
