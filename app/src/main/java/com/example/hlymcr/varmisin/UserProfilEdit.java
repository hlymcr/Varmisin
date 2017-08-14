package com.example.hlymcr.varmisin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class UserProfilEdit extends AppCompatActivity {


    Button kaydet;
    public static String USER_ID = "com.example.hlymcr.tutumlulistem.main.USER_ID";
    private String userID;
    private KisiModel kisiModel;
    private DatabaseReference mDatabase;
    private Map<String, Object> postValues;
    String kisiAd,adres,tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profil);


        final EditText isimET = (EditText) findViewById(R.id.isim);
        final EditText adresET = (EditText) findViewById(R.id.adres);
        final EditText telET = (EditText) findViewById(R.id.tel);
        kaydet = (Button) findViewById(R.id.kayit);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        userID = user.getUid();

        Log.d("userID:", userID);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();


        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*String key = mDatabase.child("users").child(userID).push().getKey();

                HashMap<String, String> result =new HashMap<>();
                result.put("adSoyad",isimET.getText().toString());
                result.put("adres",adresET.getText().toString());
                result.put("telefon",telET.getText().toString());

                Map<String,Object> childUpdate = new HashMap<String, Object>();
                childUpdate.put("users/"+userID+"/"+key,result);
                mDatabase.updateChildren(childUpdate);
                Toast.makeText(UserProfilEdit.this, "Kaydedildi", Toast.LENGTH_SHORT).show();*/

                if (kisiModel == null) {
                    kisiModel = new KisiModel();
                    kisiModel.setAdSoyad(isimET.getText().toString());
                    kisiModel.setAdres(adresET.getText().toString());
                    kisiModel.setTel(telET.getText().toString());
                    mDatabase.child("users").child(userID).setValue(kisiModel);
                    Toast.makeText(UserProfilEdit.this, "Kaydedildi", Toast.LENGTH_SHORT).show();
                    Log.i("SAVE", "saveEntry: Kaydedildi.");

                } else {
                    kisiModel.setAdSoyad(isimET.getText().toString());
                    kisiModel.setAdres(adresET.getText().toString());
                    kisiModel.setTel(telET.getText().toString());
                    postValues = kisiModel.toMap();
                    mDatabase.child("users").child(userID).updateChildren(postValues);
                    Toast.makeText(UserProfilEdit.this, "Güncellendi", Toast.LENGTH_SHORT).show();
                    Log.i("SAVE", "saveEntry: Güncellendi.");

                }
            }
        });
        mDatabase.child("users").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                KisiModel user = dataSnapshot.getValue(KisiModel.class);
                if(user==null){

                    Toast.makeText(UserProfilEdit.this, "Lütfen Profil bilgilerini ekleyiniz", Toast.LENGTH_SHORT).show();

                }
                else{

                    kisiAd= user.getAdSoyad();
                    adres=user.getAdres();
                    tel=user.getTel();
                    isimET.setText(kisiAd);
                    adresET.setText(adres);
                    telET.setText(tel);
                }
            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Hata","Hata mesajı");
            }
        });



    }
}
