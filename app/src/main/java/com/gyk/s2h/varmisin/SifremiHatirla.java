package com.gyk.s2h.varmisin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class SifremiHatirla extends AppCompatActivity {

    private EditText email;
    private Button yeniParolaGonder;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifremi_hatirla);
        email = (EditText) findViewById(R.id.eposta);
        yeniParolaGonder = (Button) findViewById(R.id.yeniParolaGonder);


        //FirebaseAuth sınıfının referans olduğu nesneleri kullanabilmek için getInstance methodunu kullanıyoruz.
        auth = FirebaseAuth.getInstance();


        yeniParolaGonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail = email.getText().toString().trim();

                if (TextUtils.isEmpty(mail)) {
                    Toast.makeText(getApplication(), "Lütfen email adresinizi giriniz", Toast.LENGTH_SHORT).show();
                    return;
                }


                //FirebaseAuth sınıfı üzerinden sendPasswordResetEmail nesnesi ile girilen maile parola sıfırlama bağlantısı gönderebiliyoruz.
                auth.sendPasswordResetEmail(mail)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(SifremiHatirla.this);
                                    builder.setMessage("Yeni parola için bağlantı adresi e-postana gönderildi. Giriş sayfasına dönmek ister misin? ");
                                    builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            //İptal butonuna basılınca yapılacaklar.Sadece kapanması isteniyorsa boş bırakılacak

                                        }
                                    });


                                    builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //Tamam butonuna basılınca yapılacaklar
                                            Intent intent = new Intent(SifremiHatirla.this, MainActivity.class);
                                            startActivity(intent);

                                        }
                                    });


                                    builder.show();

                                } else {
                                    Toast.makeText(SifremiHatirla.this, "Mail gönderme hatası!", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
            }
        });
    }

}

