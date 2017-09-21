package com.gyk.s2h.varmisin;


import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Giris extends Fragment implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {


    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    ActionBar actionBar;
    private SignInButton btnSignIn;
    private LoginButton facebookLogin;
    private CallbackManager callbackManager;
    SharedPreferences sp;


    EditText kullaniciEt, sifreEt;
    Button giris;


    private FirebaseAuth mAuth;


    private static String APP_ID = "245472879280215";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Actionbar gizlenir.
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.giris, container, false);
        sp=getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        if(sp.contains("email")){
            Intent i = new Intent(getActivity(), NavigationDrawer.class);
            getContext().startActivity(i);
            getActivity().finish();   //finish current activity
        }




        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        giris = (Button) view.findViewById(R.id.button2);

        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                girisYap();
            }
        });


        ImageButton sifreH = (ImageButton) view.findViewById(R.id.sifreH);
        ImageView facebook =(ImageView)view.findViewById(R.id.face);
        ImageView google=(ImageView)view.findViewById(R.id.google);


        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facebookLogin.performClick();
            }
        });

        //şifre hatırla kısmı
        sifreH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Şifreni mi Unuttun?");
                builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //İptal butonuna basılınca yapılacaklar.Sadece kapanması isteniyorsa boş bırakılacak

                    }
                });


                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Tamam butonuna basılınca yapılacaklar
                        Intent intent = new Intent(getActivity(), SifremiHatirla.class);
                        startActivity(intent);

                    }
                });


                builder.show();
            }
        });


        //kullanıcı ve şifre edittext in yanına image ekleme
        kullaniciEt = (EditText) view.findViewById(R.id.kullanici);

        sifreEt = (EditText) view.findViewById(R.id.sifre);



        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .

                        enableAutoManage(getActivity(), this)
                .

                        addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .

                        build();
        mAuth = FirebaseAuth.getInstance();

        google.setOnClickListener(this);
        facebookLogin = (LoginButton) view.findViewById(R.id.facebook);
        facebookLogin.setFragment(this);
        facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Flogin();
            }
        });

        return view;

    }

    //Facebook login işlemleri

    public void Flogin() {
        facebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                Log.d(TAG, "facebook:onSuccess:" + loginResult);

                handleFacebookAccessToken(loginResult.getAccessToken());


            }

            @Override
            public void onCancel() {


            }

            @Override
            public void onError(FacebookException e) {

            }
        });

    }

    private void girisYap() {

        final String email = kullaniciEt.getText().toString();
        final String parola = sifreEt.getText().toString();
        if (TextUtils.isEmpty(email)) {

            Toast.makeText(getApplicationContext(), "Lütfen emailinizi giriniz", Toast.LENGTH_SHORT).show();

            return;

        }

        //Parola girilmemiş ise kullanıcıyı uyarıyoruz

        if (TextUtils.isEmpty(parola)) {

            Toast.makeText(getApplicationContext(), "Lütfen parolanızı giriniz", Toast.LENGTH_SHORT).show();

            return;

        }


        mAuth.signInWithEmailAndPassword(email, parola).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Log.d("email",user.getEmail());
                    String kullanici=user.getEmail();
                    SharedPreferences.Editor e=sp.edit();
                    e.putString("email",email);
                    e.putString("parola",parola);
                    e.commit();
                    Toast.makeText(getActivity(), "Giriş yapıldı", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getActivity(), NavigationDrawer.class);
                    i.putExtra("kullanici",kullanici);
                    startActivity(i);
                    getActivity().finish();
                } else {
                    Log.e("Giriş Hatası", task.getException().getMessage());
                    Toast.makeText(getActivity(), "Kullanıcı adı veya şifre başarısız", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.google:
                signIn();
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);

        if (requestCode == RC_SIGN_IN) {

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {

                // Google Sign In was successful, authenticate with Firebase

                GoogleSignInAccount account = result.getSignInAccount();

                firebaseAuthWithGoogle(account);

            } else {

                // Google Sign In failed, update UI appropriately

                // [START_EXCLUDE]



                // [END_EXCLUDE]

            }

        }
    }

    @Override
    public void onStart() {
        super.onStart();



        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        mAuth.signInWithCredential(credential)

                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {

                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String kullanici=user.getEmail();
                            Log.d("email",user.getEmail());
                            Log.d(TAG, "signInWithCredential:success");
                            SharedPreferences.Editor e=sp.edit();
                            e.putString("email",user.getEmail());
                            e.commit();
                            Toast.makeText(getActivity(), "Giriş başarılı", Toast.LENGTH_SHORT).show();

                            Intent intent =new Intent(getActivity(),NavigationDrawer.class);
                            intent.putExtra("kullanici",kullanici);
                            startActivity(intent);
                            getActivity().finish();



                        } else {

                            // If sign in fails, display a message to the user.

                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                            Toast.makeText(getActivity(), "Authentication failed.",

                                    Toast.LENGTH_SHORT).show();



                        }


                    }

                });

    }
    private void handleFacebookAccessToken(AccessToken token) {

        Log.d(TAG, "handleFacebookAccessToken:" + token);


        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        mAuth.signInWithCredential(credential)

                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {

                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information

                            Log.d(TAG, "signInWithCredential:success");


                            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String kullanici =user.getDisplayName();
                            Log.d("email",user.getDisplayName());
                            SharedPreferences.Editor e=sp.edit();
                            e.putString("email",user.getDisplayName());
                            e.commit();
                            Toast.makeText(getActivity(), "Giriş başarılı", Toast.LENGTH_SHORT).show();
                            Intent intent =new Intent(getActivity(),NavigationDrawer.class);
                            intent.putExtra("kullanici",kullanici);
                            startActivity(intent);
                            getActivity().finish();

                        } else {

                            // If sign in fails, display a message to the user.

                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                            Toast.makeText(getActivity(), "Authentication failed.",

                                    Toast.LENGTH_SHORT).show();



                        }

                    }

                });

    }


}
