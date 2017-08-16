package com.gyk.s2h.varmisin;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

public class UserProfilEdit extends AppCompatActivity {


    Button kaydet;
    public static String USER_ID = "com.example.hlymcr.tutumlulistem.main.USER_ID";
    private String userID;
    private KisiModel kisiModel;
    private DatabaseReference mDatabase;
    private Map<String, Object> postValues;
    String kisiAd,kullanıcıAd,Dtarih;
    public static final String IMAGE_TYPE = "image/*";
    private static final int SELECT_SINGLE_PICTURE = 101;
    private ImageView selectedImagePreview;
    EditText isimET,kad,dtarih;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profil);


        isimET = (EditText) findViewById(R.id.isim);
        kad = (EditText) findViewById(R.id.kad);
        dtarih = (EditText) findViewById(R.id.dtarih);
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
                    kisiModel.setKullanici_adi(kad.getText().toString());
                    kisiModel.setDtarih(dtarih.getText().toString());
                    mDatabase.child("users").child(userID).setValue(kisiModel);
                    Toast.makeText(UserProfilEdit.this, "Kaydedildi", Toast.LENGTH_SHORT).show();
                    Log.i("SAVE", "saveEntry: Kaydedildi.");

                } else {
                    kisiModel.setAdSoyad(isimET.getText().toString());
                    kisiModel.setKullanici_adi(kad.getText().toString());
                    kisiModel.setDtarih(dtarih.getText().toString());
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
                    kullanıcıAd=user.getKullanici_adi();
                    Dtarih=user.getDtarih();
                    isimET.setText(kisiAd);
                    kad.setText(kullanıcıAd);
                    dtarih.setText(Dtarih);
                }
            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Hata","Hata mesajı");
            }
        });



    }
    public void FotografDegistir(View view){
        Intent intent = new Intent();
        intent.setType(IMAGE_TYPE);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_SINGLE_PICTURE);


        selectedImagePreview = (ImageView)findViewById(R.id.resim);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_SINGLE_PICTURE) {

                Uri selectedImageUri = data.getData();
                Log.d("Urlİmage", String.valueOf(selectedImageUri));
                try {
                    selectedImagePreview.setImageBitmap(new UserPicture(selectedImageUri, getContentResolver()).getBitmap());
                } catch (IOException e) {
                    Log.e(MainActivity.class.getSimpleName(), "Failed to load image", e);
                }
                // original code
                //String selectedImagePath = getPath(selectedImageUri);
                //selectedImagePreview.setImageURI(selectedImageUri);
            }

        } else {
            // report failure
            Toast.makeText(getApplicationContext(), R.string.msg_failed_to_get_intent_data, Toast.LENGTH_LONG).show();
            Log.d(MainActivity.class.getSimpleName(), "Failed to get intent data, result code is " + resultCode);
        }
    }
    public void Tarih(View view){
        //Datepicker
        Calendar mcurrentTime = Calendar.getInstance();
        int year = mcurrentTime.get(Calendar.YEAR);//Güncel Yılı alıyoruz
        int month = mcurrentTime.get(Calendar.MONTH);//Güncel Ayı alıyoruz
        int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);//Güncel Günü alıyoruz
        DatePickerDialog datePicker;//Datepicker objemiz
        datePicker = new DatePickerDialog(UserProfilEdit.this, new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dtarih.setText(dayOfMonth + "/" + monthOfYear+ "/"+year);

            }
        },year,month,day);//başlarken set edilcek değerlerimizi atıyoruz
        datePicker.setTitle("Tarih Seçiniz");
        datePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ayarla", datePicker);
        datePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", datePicker);
        datePicker.show();
    }
}
