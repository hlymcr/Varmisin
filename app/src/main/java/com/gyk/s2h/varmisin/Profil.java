package com.gyk.s2h.varmisin;

import android.content.Intent;
import android.graphics.Color;
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
import java.util.Map;


public class Profil extends AppCompatActivity {
    private String userID;
    private KisiModel kisiModel;
    private DatabaseReference mDatabase;
    Uri selectedImageUri;
    Uri secilenResim;
    ImageView resim;
    TextView isim,kad,dtarih;
    private ImageView selectedImagePreview;
    String kisiAd,kullanıcıAd,Dtarih,kresim;
    private Map<String, Object> postValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        userID = user.getUid();

        Log.d("userID:", userID);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout1);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_home_black_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.arkadaslar));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.yaptiklarim));

        isim=(TextView)findViewById(R.id.isim);
        kad=(TextView)findViewById(R.id.kullanici_adi);
        dtarih=(TextView)findViewById(R.id.dtarih);
        selectedImagePreview=(ImageView)findViewById(R.id.userpicture);

        final Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.LTGRAY)
                .borderWidthDp(4)
                .cornerRadiusDp(35)
                .oval(true)
                .build();
        mDatabase.child("users").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                KisiModel user = dataSnapshot.getValue(KisiModel.class);
                if(user==null){

                    Toast.makeText(Profil.this, "Lütfen Profil bilgilerini ekleyiniz", Toast.LENGTH_SHORT).show();

                }
                else{

                    kisiAd= user.getAdSoyad();
                    kullanıcıAd=user.getKullanici_adi();
                    Dtarih=user.getDtarih();

                    isim.setText(kisiAd);
                    kad.setText(kullanıcıAd);
                    dtarih.setText(Dtarih);
                    kresim=user.getPath();
                    secilenResim= Uri.parse(kresim);
                    //Picasso ile kullanı profili için secilenResim Uri siyle profil resmi oluşturuyoruz.
                    Picasso.with(Profil.this).load(secilenResim).fit().transform(transformation).into(selectedImagePreview);

                }
            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Hata","Hata mesajı");
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

    public void ProfilDüzenle(View view){
        Intent intent= new Intent(this,UserProfilEdit.class);
        startActivity(intent);
    }
}
