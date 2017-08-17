package com.gyk.s2h.varmisin;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

public class UserProfilEdit extends AppCompatActivity {

    public static final int KITKAT_VALUE = 1002;


    Button kaydet;
    public static String USER_ID = "com.example.hlymcr.tutumlulistem.main.USER_ID";
    private String userID;
    private KisiModel kisiModel;
    private DatabaseReference mDatabase;
    private Map<String, Object> postValues;
    String kisiAd,kullanıcıAd,Dtarih,kresim;
    public static final String IMAGE_TYPE = "image/*";
    private static final int SELECT_SINGLE_PICTURE = 101;
    private ImageView selectedImagePreview;
    EditText isimET,kad,dtarih;
    private StorageReference mStorage;
    Uri selectedImageUri;
    Uri secilenResim;
    ImageView resim;
    private static final String TAG = "UserProfilEdit";
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS=1;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profil);


       checkFilePermissions();



        isimET = (EditText) findViewById(R.id.isim);
        kad = (EditText) findViewById(R.id.kad);
        dtarih = (EditText) findViewById(R.id.dtarih);
        resim=(ImageView)findViewById(R.id.resim);
        kaydet = (Button) findViewById(R.id.kayit);
        selectedImagePreview = (ImageView)findViewById(R.id.resim);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        userID = user.getUid();

        Log.d("userID:", userID);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();



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
                    kisiModel.setPath( String.valueOf(selectedImageUri));

                    mDatabase.child("users").child(userID).setValue(kisiModel);
                    /*StorageReference filpath =mStorage.child("ProfilPicture").child(selectedImageUri.getLastPathSegment());
                    filpath.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(UserProfilEdit.this, "Kaydedildi Resim", Toast.LENGTH_SHORT).show();

                        }
                    });*/
                    Toast.makeText(UserProfilEdit.this, "Kaydedildi", Toast.LENGTH_SHORT).show();
                    Log.i("SAVE", "saveEntry: Kaydedildi.");

                } else {
                    kisiModel.setAdSoyad(isimET.getText().toString());
                    kisiModel.setKullanici_adi(kad.getText().toString());
                    kisiModel.setDtarih(dtarih.getText().toString());
                    kisiModel.setPath( String.valueOf(selectedImageUri));

                    postValues = kisiModel.toMap();
                    mDatabase.child("users").child(userID).updateChildren(postValues);
                    /*StorageReference filpath =mStorage.child("ProfilPicture").child(selectedImageUri.getLastPathSegment());
                    filpath.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(UserProfilEdit.this, "Kaydedildi Resim", Toast.LENGTH_SHORT).show();

                        }
                    });*/
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
                    if(secilenResim!=null && selectedImagePreview!=null){

                        try {
                            selectedImagePreview.setImageBitmap(new UserPicture(secilenResim, getContentResolver()).getBitmap());
                        } catch (IOException e) {
                            Log.e(MainActivity.class.getSimpleName(), "Failed to load image", e);
                        }

                    }


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

        /*Intent intent = new Intent();
        intent.setType(IMAGE_TYPE);
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_SINGLE_PICTURE);*/



    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_SINGLE_PICTURE) {

                selectedImageUri = data.getData();
                /*StorageReference filpath =mStorage.child("ProfilPicture").child(selectedImageUri.getLastPathSegment());
                filpath.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(UserProfilEdit.this, "", Toast.LENGTH_SHORT).show();

                    }
                });*/
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


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkFilePermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = UserProfilEdit.this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck += UserProfilEdit.this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            if (permissionCheck != 0) {
                this.requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1001); //Any number
            }
        }else{
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }
}
