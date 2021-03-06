package com.gyk.s2h.varmisin;

/**
 * Created by HULYA on 26.08.2017.
 */
/**
 * Created by HULYA on 23.07.2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

//İddialarım sayfası.
public class Iddialarim extends Fragment implements View.OnClickListener {
    ListView listView;
    FirebaseDatabase database;
    private String userID;
    IddialarimAdapter adapter;
    private DatabaseReference mDatabase;
    String isim,sureS,adimS,uid;
    String istek;
    List<String> iddiaListY = new ArrayList<String>();
    final ArrayList<YuruyusModel> Yiddialar = new ArrayList<YuruyusModel>();


    public static Iddialarim newInstance() {
        Iddialarim fragment = new Iddialarim();
        return fragment;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        userID = user.getUid();

        Log.d("userID:", userID);

        mDatabase = FirebaseDatabase.getInstance().getReference();

    }
    public Iddialarim(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.iddilarim, container, false);

        listView=(ListView)view.findViewById(R.id.listview);



        final DatabaseReference dbRef =database.getReference("adimIddia").child(userID);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds:dataSnapshot.getChildren()){

                    uid=ds.child("uid").getValue().toString();

                    Log.d("uidd",uid);
                    DatabaseReference dbRef1 =database.getReference("adimIddia").child(userID).child(uid);

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
                                if (ds1.getKey().toString().equals("istek")){
                                    istek=ds1.getValue().toString();
                                }

                            }

                            /*Log.d("isim",isim);
                            Log.d("adimS",adimS);
                            Log.d("sures",sureS);*/
                            Yiddialar.add(new YuruyusModel(isim, sureS, adimS, uid,istek));
                            Log.d("Yiddialar", String.valueOf(Yiddialar));
                            adapter = new IddialarimAdapter((FragmentActivity) view.getContext(), Yiddialar);
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(view.getContext(),YuruyusDetayKabul.class);
                startActivity(intent);


            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}