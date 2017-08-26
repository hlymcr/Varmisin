package com.gyk.s2h.varmisin;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class YuruyusDetay extends AppCompatActivity {
    FirebaseDatabase database;
    private DatabaseReference mDatabase;
    String userID,isim;
    EditText sure,adimS;
    Button Vistek;
    private YuruyusModel yuruyusModel;
    String uid="";
    TextView adSoyad;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuruyus_detay);

        Bundle extras = getIntent().getExtras();
        uid=extras.getString("uid");

        Log.d("idim",uid);

        sure=(EditText)findViewById(R.id.sure);
        adimS=(EditText)findViewById(R.id.adim);
        Vistek=(Button)findViewById(R.id.Vistek);
        adSoyad=(TextView)findViewById(R.id.isim);


        database = FirebaseDatabase.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        userID = user.getUid();

        Log.d("userID:", userID);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference dbRef =database.getReference("users").child(uid);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    if(ds.getKey().toString().equals("adSoyad")){
                        isim=ds.getValue().toString();
                    }
                }
                Log.d("issim",isim);
                adSoyad.setText(isim);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

       // Log.d("issim",isim);


        Vistek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final String sureS=sure.getText().toString();
                final String adimSayisi=adimS.getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(YuruyusDetay.this);
                builder.setMessage("Var mısın? İsteği atmak istediğine emin misin?");

                builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id) {

                        //İptal butonuna basılınca yapılacaklar.Sadece kapanması isteniyorsa boş bırakılacak

                    }
                });


                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Tamam butonuna basılınca yapılacaklar
                        if(sureS.equals("")){
                            Toast.makeText(YuruyusDetay.this, "Süre giriniz.", Toast.LENGTH_SHORT).show();
                        }

                        if (adimSayisi.equals("")){
                            Toast.makeText(YuruyusDetay.this, "Adım sayısı giriniz.", Toast.LENGTH_SHORT).show();

                        }


                        Log.d("uidim",uid);
                        yuruyusModel=new YuruyusModel(sureS,adimSayisi,uid);
                        mDatabase.child("adimIstek").child(userID).push().setValue(yuruyusModel);
                        Toast.makeText(YuruyusDetay.this, "İstek gönderildi.", Toast.LENGTH_SHORT).show();


                    }
                });


                builder.show();

            }
        });

    }
}
