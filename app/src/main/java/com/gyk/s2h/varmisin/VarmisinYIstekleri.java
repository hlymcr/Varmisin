package com.gyk.s2h.varmisin;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VarmisinYIstekleri extends AppCompatActivity {

    ListView listView;
    FirebaseDatabase database;
    private String userID;
    VarmisinYIstekleriAdapter adapter;
    private DatabaseReference mDatabase;
    String isim,sureS,adimS,uid;
    String istek="";
    List<String> isteklerListY = new ArrayList<String>();
    final ArrayList<YuruyusModel> Yistekler = new ArrayList<YuruyusModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varmisin_istekleri);
        listView=(ListView)findViewById(R.id.listview);

        database = FirebaseDatabase.getInstance();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        userID = user.getUid();

        Log.d("userID:", userID);

        mDatabase = FirebaseDatabase.getInstance().getReference();


        final DatabaseReference dbRef =database.getReference("adimIstek").child(userID);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds:dataSnapshot.getChildren()){

                    uid=ds.child("uid").getValue().toString();

                    Log.d("uidd",uid);
                    DatabaseReference dbRef1 =database.getReference("adimIstek").child(userID).child(uid);

                    dbRef1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for(DataSnapshot ds1:dataSnapshot.getChildren()){
                                if(ds1.getKey().toString().equals("isim")){

                                    isim=ds1.getValue().toString();

                                }
                                if(ds1.getKey().toString().equals("sure")){

                                    sureS=ds1.getValue().toString();

                                }
                                if(ds1.getKey().toString().equals("adimS")){

                                    adimS=ds1.getValue().toString();

                                }
                                if(ds1.getKey().toString().equals("istek")){
                                    istek=ds1.getValue().toString();
                                }

                            }

                            /*Log.d("isim",isim);
                            Log.d("adimS",adimS);
                            Log.d("sures",sureS);*/
                            Yistekler.add(new YuruyusModel(isim, sureS, adimS, uid,istek));
                            Log.d("Yiddialar", String.valueOf(Yistekler));
                            adapter = new VarmisinYIstekleriAdapter(VarmisinYIstekleri.this, Yistekler);
                            listView.setAdapter(adapter);



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




    }
}
