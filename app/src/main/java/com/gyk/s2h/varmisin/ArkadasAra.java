package com.gyk.s2h.varmisin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

// Yan menüde bulunan Arkadaş Arama sayfası
public class ArkadasAra extends AppCompatActivity {

    FirebaseDatabase database;
    ArkadasAraAdapter adapter;
    private String userID,uid;
    String isim;
    String[] kuid;
    private DatabaseReference mDatabase;
    boolean arkistek=true;
    List<String> arrList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arkadas_ara);


        final ArrayList<KisiModel> kullaniciList = new ArrayList<KisiModel>();
        final ListView listView =(ListView)findViewById(R.id.listview);

        final EditText inputSearch = (EditText)findViewById(R.id.textsearch);

        database=FirebaseDatabase.getInstance();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        userID = user.getUid();

        Log.d("userID:", userID);

        mDatabase = FirebaseDatabase.getInstance().getReference();



        final DatabaseReference dbRef =database.getReference("users");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    final String kullaniciAdi = ds.child("kullanici_adi").getValue().toString();
                    final String isim = ds.child("adSoyad").getValue().toString();
                    String dTarih = ds.child("dtarih").getValue().toString();
                    String path = ds.child("path").getValue().toString();
                    String uid =ds.child("uid").getValue().toString();
                    //Listeyi database den çektiğimiz verilerle dolduruyoruz..
                    kullaniciList.add(new KisiModel(kullaniciAdi,isim,dTarih,path,uid));


                }
                adapter = new ArkadasAraAdapter(ArkadasAra.this, kullaniciList);
                listView.setAdapter(adapter);

                // Arkadaş arama için Textwatcher metodu
                inputSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                            ArkadasAra.this.adapter.getFilter().filter(s);



                    }

                    @Override
                    public void afterTextChanged(Editable s) {


                    }
                });

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Listedeki arkadaşlara uzun tıklandığında eklemek isteyip istemediğini sorar..
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ArkadasAra.this);
                builder.setTitle("Arkadaşı Ekle");

                builder.setMessage(kullaniciList.get(i).getKullanici_adi() + " Eklemek ister misin?");
                builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //İptal butonuna basılınca yapılacaklar.Sadece kapanması isteniyorsa boş bırakılacak

                    }
                });
                Log.d("uid", kullaniciList.get(i).getUid());
                // uid dediğimiz String i parçalara ayırıyoruz ve 4. parçada kullanıcı nın id bilgisini veriliyor.
                uid = kullaniciList.get(i).getUid();
                kuid = uid.split("/");
                Log.d("words[3", kuid[4]);
                //Kullanıcı id si ile tıkladığımız arkadaşın id si eşitse yani bizim id mize eşitse Ekleme yapılmıyor.
                if (userID.equals(kuid[4])) {

                    Toast.makeText(ArkadasAra.this, "Kendini ekleyemessin...", Toast.LENGTH_SHORT).show();
                } else {

                    final DatabaseReference dbRef1 = database.getReference("yistekler").child(kuid[4]);
                    dbRef1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                String key = ds.getValue().toString();

                                arrList.add(key);

                            }
                            Log.d("arkistek", String.valueOf(arrList));
                            Log.d("arrlis0", arrList.get(0));

                            //Önceden arkadaşı eklediysek arkistek false değerine eşit oluyor ve ekleme yapmıyor.

                            for (int i = 0; i < arrList.size(); i++) {
                                if (userID.equals(arrList.get(i))) {
                                    arkistek = false;
                                }
                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //arkistek true değerde olunca Arkadaşlık isteği gönderebiliyoruz ve veritabanına kaydediliyor.
                            if (arkistek == true) {
                                Toast.makeText(ArkadasAra.this, "Arkadaşlık isteği gönderildi...", Toast.LENGTH_SHORT).show();
                                mDatabase.child("yistekler").child(kuid[4]).push().setValue(userID);
                            } else if (arkistek == false) {
                                Toast.makeText(ArkadasAra.this, "Arkadaşlık isteği gönderilmiş.", Toast.LENGTH_SHORT).show();
                            }

                        }


                    });
                    builder.show();
                }
                return false;
            }
        });
        //Listedeki arkadaşlarımıza bir kere tıklandığında profil sayfaları açılıyor.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String isim=kullaniciList.get(i).getAdSoyad();
                String kullaniciAdi=kullaniciList.get(i).getKullanici_adi();
                String dtarih=kullaniciList.get(i).getDtarih();
                String path=kullaniciList.get(i).getPath();
                uid=kullaniciList.get(i).getUid();
                kuid=uid.split("/");

                if(userID.equals(kuid[4])){
                    Intent intent =new Intent(ArkadasAra.this,Profil.class);
                    startActivity(intent);
                }
                else{
                    //Tıkladığımız arkadaşımız ile ilgili verileri ArkadaşProfil sayfasına aktarıyoruz.
                    Intent intent=new Intent(getApplicationContext(),ArkadasProfil.class);
                    intent.putExtra("kuid",kuid[4]);
                    intent.putExtra("isim",isim);
                    intent.putExtra("kullaniAdi",kullaniciAdi);
                    intent.putExtra("dtarih",dtarih);
                    intent.putExtra("path",path);
                    startActivity(intent);
                }

            }
        });

        }

}
