package com.gyk.s2h.varmisin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class IddiaTanimlari extends AppCompatActivity {
    RelativeLayout yuruyusDetay,yemekDetay,resimDetay,muzikDetay,guzelDetay,guncelDetay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iddia_tanimlari);



        yuruyusDetay=(RelativeLayout)findViewById(R.id.lin1);
        yuruyusDetay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle extras = getIntent().getExtras();
                String key=extras.getString("Uid");
                Intent intent =new Intent(getApplicationContext(),YuruyusDetay.class);
                intent.putExtra("uid",key);
                startActivity(intent);

            }
        });

        yemekDetay =(RelativeLayout)findViewById(R.id.lin2);
        yemekDetay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent =new Intent(IddiaTanimlari.this,YemekDetay.class);
                startActivity(intent);

            }
        });

        resimDetay =(RelativeLayout)findViewById(R.id.lin3);
        resimDetay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =new Intent(IddiaTanimlari.this, ResimDetay.class);
                startActivity(intent);
            }
        });

        muzikDetay=(RelativeLayout)findViewById(R.id.lin4);
        muzikDetay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(IddiaTanimlari.this, MuzikDetay.class);
                startActivity(intent);
            }
        });
        guzelDetay=(RelativeLayout)findViewById(R.id.lin5);
        guzelDetay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(IddiaTanimlari.this, GuzelDetay.class);
                startActivity(intent);
            }
        });
        guncelDetay=(RelativeLayout)findViewById(R.id.lin6);

        guncelDetay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(IddiaTanimlari.this, GuncelDetay.class);
                startActivity(intent);
            }
        });





    }
}
