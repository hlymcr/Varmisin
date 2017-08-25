package com.gyk.s2h.varmisin;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//Arkadaşlarımızın Profil sayfası
public class ArkadasProfil extends AppCompatActivity {
    private String userID;
    private KisiModel kisiModel;
    private DatabaseReference mDatabase;
    Uri selectedImageUri;
    Uri secilenResim;
    ImageView resim;
    TextView isim, kad, dtarih;
    private ImageView selectedImagePreview;
    String kisiAd, kullanıcıAd, Dtarih, kresim;
    private Map<String, Object> postValues;
    private FloatingActionButton arkekle;
    FirebaseDatabase database;
    List<String> arrList = new ArrayList<String>();
    boolean arkistek=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arkadas_profil);

        database = FirebaseDatabase.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        userID = user.getUid();

        Log.d("userID:", userID);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        final Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.GRAY)
                .borderWidthDp(4)
                .cornerRadiusDp(35)
                .oval(true)
                .build();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout1);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_home_black_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.arkadaslar));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.yaptiklarim));


        isim = (TextView) findViewById(R.id.isim);
        kad = (TextView) findViewById(R.id.kullanici_adi);
        dtarih = (TextView) findViewById(R.id.dtarih);
        selectedImagePreview = (ImageView) findViewById(R.id.userpicture);
        Bundle extras = getIntent().getExtras();
        final String value = extras.getString("kuid");
        arkekle = (FloatingActionButton) findViewById(R.id.arkekle);
        final DatabaseReference dbRef = database.getReference("yistekler").child(value);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String key = ds.getValue().toString();

                    arrList.add(key);

                }
                Log.d("arkistek", String.valueOf(arrList));
                if(arrList.size()>0) {

                    for (int i = 0; i < arrList.size(); i++) {
                        if (userID.equals(arrList.get(i))) {
                            arkistek = false;
                            arkekle.setImageResource(R.drawable.check);
                        }
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        arkekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(arkistek==true){
                    arkekle.setImageResource(R.drawable.check);
                    mDatabase.child("yistekler").child(value).push().setValue(userID);
                    Toast.makeText(ArkadasProfil.this, "Arkadaşlık isteği gönderildi...", Toast.LENGTH_SHORT).show();
                }
                else if(arkistek==false){
                    Toast.makeText(ArkadasProfil.this, "Arkadaşlık isteği gönderilmiş.", Toast.LENGTH_SHORT).show();
                }





            }
        });


        mDatabase.child("users").child(value).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                KisiModel user = dataSnapshot.getValue(KisiModel.class);


                kisiAd = user.getAdSoyad();
                kullanıcıAd = user.getKullanici_adi();
                Dtarih = user.getDtarih();

                isim.setText(kisiAd);
                kad.setText(kullanıcıAd);
                dtarih.setText(Dtarih);
                kresim = user.getPath();
                secilenResim = Uri.parse(kresim);

                Picasso.with(ArkadasProfil.this).load(secilenResim).fit().transform(transformation).into(selectedImagePreview);
                /*
                if (secilenResim != null && selectedImagePreview != null) {

                    try {
                        selectedImagePreview.setImageBitmap(new UserPicture(secilenResim, getContentResolver()).getBitmap());
                    } catch (IOException e) {
                        Log.e(MainActivity.class.getSimpleName(), "Failed to load image", e);
                    }


                }*/
            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Hata", "Hata mesajı");
            }
        });


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final ProfilPagerAdapter adapter = new ProfilPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

}
