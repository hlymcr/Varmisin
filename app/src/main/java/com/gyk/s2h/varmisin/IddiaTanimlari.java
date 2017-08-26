package com.gyk.s2h.varmisin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class IddiaTanimlari extends AppCompatActivity {
    RelativeLayout yuruyusDetay;

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


    }
}
