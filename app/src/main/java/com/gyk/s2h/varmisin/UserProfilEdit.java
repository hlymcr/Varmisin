package com.gyk.s2h.varmisin;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

public class UserProfilEdit extends AppCompatActivity {

    Button kaydet;
    private String userID;
    private KisiModel kisiModel;
    private DatabaseReference mDatabase;
    private Map<String, Object> postValues;
    String kisiAd,kullanıcıAd,Dtarih,kresim;
    private static final int SELECT_SINGLE_PICTURE = 101;
    private ImageView selectedImagePreview;
    EditText isimET,kad,dtarih;
    Uri selectedImageUri;
    Uri secilenResim;
    ImageView resim;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    private static final String TAG = "UserProfilEdit";


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profil);


        isimET = (EditText) findViewById(R.id.isim);
        kad = (EditText) findViewById(R.id.kad);
        dtarih = (EditText) findViewById(R.id.dtarih);
        resim=(ImageView)findViewById(R.id.resim);
        kaydet = (Button) findViewById(R.id.kayit);
        selectedImagePreview = (ImageView)findViewById(R.id.resim);


        final Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.GRAY)
                .borderWidthDp(4)
                .cornerRadiusDp(35)
                .oval(true)
                .build();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        userID = user.getUid();

        Log.d("userID:", userID);

        mDatabase = FirebaseDatabase.getInstance().getReference();


        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (kisiModel == null) {
                    kisiModel = new KisiModel();
                    kisiModel.setAdSoyad(isimET.getText().toString());
                    kisiModel.setKullanici_adi(kad.getText().toString());
                    kisiModel.setDtarih(dtarih.getText().toString());
                    kisiModel.setUid(mDatabase.child("users").child(userID).toString());
                    kisiModel.setPath(String.valueOf(selectedImageUri));
                    mDatabase.child("users").child(userID).setValue(kisiModel);

                    Toast.makeText(UserProfilEdit.this, "Kaydedildi", Toast.LENGTH_SHORT).show();
                    Log.i("SAVE", "saveEntry: Kaydedildi.");

                } else {
                    kisiModel.setAdSoyad(isimET.getText().toString());
                    kisiModel.setKullanici_adi(kad.getText().toString());
                    kisiModel.setDtarih(dtarih.getText().toString());
                    kisiModel.setUid(mDatabase.child("users").child(userID).toString());
                    kisiModel.setPath( String.valueOf(selectedImageUri));
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
                    kresim=user.getPath();
                    secilenResim= Uri.parse(kresim);
                    Picasso.with(UserProfilEdit.this).load(secilenResim).fit().transform(transformation).into(resim);


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
        Intent i;
        i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, SELECT_SINGLE_PICTURE);


    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
         final Transformation transformation = new RoundedTransformationBuilder()
                 .borderColor(Color.GRAY)
                 .borderWidthDp(3)
                 .cornerRadiusDp(30)
                 .oval(true)
                 .build();
         if (resultCode == RESULT_OK) {
             if (requestCode == SELECT_SINGLE_PICTURE) {
                 selectedImageUri = data.getData();
                 Picasso.with(UserProfilEdit.this).load(selectedImageUri).fit().transform(transformation).into(selectedImagePreview);

             }
             else{



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
