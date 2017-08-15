package com.gyk.s2h.varmisin;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Kaydol extends Fragment {

    private FirebaseAuth auth;
    EditText  email, sifre;
    Button kaydol;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kaydol, container, false);


        email = (EditText) view.findViewById(R.id.email);
        sifre = (EditText) view.findViewById(R.id.sifre);


        auth=FirebaseAuth.getInstance();

        kaydol = (Button) view.findViewById(R.id.kaydol);


        kaydol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Email = email.getText().toString();
                String Sifre = sifre.getText().toString();

                if (TextUtils.isEmpty(Email)) {

                    Toast.makeText(getApplicationContext(), "Lütfen E-posta giriniz", Toast.LENGTH_SHORT).show();

                    return;

                }
                if (TextUtils.isEmpty(Sifre)) {

                    Toast.makeText(getApplicationContext(), "Lütfen şifre giriniz", Toast.LENGTH_SHORT).show();

                    return;

                }

                if (Sifre.length() < 4 && Sifre.length() > 8) {

                    Toast.makeText(getApplicationContext(), "Parola en az 4 en çok 8 haneli olmalıdır", Toast.LENGTH_SHORT).show();

                }

                auth.createUserWithEmailAndPassword(Email,Sifre).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //İşlem başarılı ise Giriş e yönlendiriliyor.

                        if (task.isSuccessful()) {
                            Intent intent =new Intent(getActivity(),MainActivity.class);
                            startActivity(intent);


                            Toast.makeText(getActivity(), "Kayıt Yapıldı",

                                    Toast.LENGTH_SHORT).show();

                        }




                    }
                });


            }
        });

        return view;
    }


}
