package com.gyk.s2h.varmisin;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;


public class Profil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_home_black_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.arkadaslar));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.yaptiklarim));


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    public void ProfilDüzenle(View view){
        Intent intent= new Intent(this,UserProfilEdit.class);
        startActivity(intent);
    }
}
