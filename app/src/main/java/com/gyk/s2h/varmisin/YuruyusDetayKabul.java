package com.gyk.s2h.varmisin;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.util.List;


public class YuruyusDetayKabul extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yuruyus_kabul);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }


    }
    @Override
    protected void onPostResume() {
        super.onPostResume();

        // Start the service (idempotent)
        startService(new Intent(this, FStepService.class));
    }




    public class PlaceholderFragment extends Fragment implements FStepService.StepCountListener {



        private final PlaceholderFragment.FSSConnection mFSSConnection;

        private TextView sayac, txtadim,adSoyad;
        private FStepService mFSService;
        private ViewPropertyAnimator mStepEventAnim,mStepEventAnim1;
        ImageView aresim,kresim;
        ImageView walk;
        FloatingActionButton basla;
        private String userID,path,path1;
        private KisiModel kisiModel;
        private DatabaseReference mDatabase;
        Uri secilenResim,secilenResim1;


        long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;

        Handler handler;

        int Seconds, Minutes, MilliSeconds ;




        public PlaceholderFragment() {
            mFSSConnection = new FSSConnection();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            final View rootView = inflater.inflate(R.layout.activity_yuruyus_detay_kabul, container, false);


            adSoyad=(TextView)rootView.findViewById(R.id.isim);
            aresim=(ImageView)rootView.findViewById(R.id.aresim);
            kresim=(ImageView)rootView.findViewById(R.id.kresim);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            userID = user.getUid();

            Log.d("userID:", userID);

            //Profil resmini oval yapmayı sağlayan metod.
            mDatabase = FirebaseDatabase.getInstance().getReference();

            final Transformation transformation = new RoundedTransformationBuilder()
                    .borderColor(Color.GRAY)
                    .borderWidthDp(3)
                    .cornerRadiusDp(30)
                    .oval(true)
                    .build();


            Bundle extras = getIntent().getExtras();
            String kId=extras.getString("uid");
            String isim=extras.getString("isim");
            String sure=extras.getString("sure");
            String adimS=extras.getString("adimS");
            String durum=extras.getString("durum");
            adSoyad.setText(isim);

            mDatabase.child("users").child(kId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    KisiModel user = dataSnapshot.getValue(KisiModel.class);

                        path=user.getPath();
                        Log.d("userpath",path);
                        secilenResim= Uri.parse(path);
                        Picasso.with(rootView.getContext()).load(secilenResim).fit().transform(transformation).into(aresim);






                }


                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("Hata","Hata mesajı");
                }
            });
            mDatabase.child("users").child(userID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    KisiModel user = dataSnapshot.getValue(KisiModel.class);

                        path1=user.getPath();
                        Log.d("userpath",path1);
                        secilenResim1= Uri.parse(path1);
                        Picasso.with(rootView.getContext()).load(secilenResim1).fit().transform(transformation).into(kresim);


                }


                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("Hata","Hata mesajı");
                }
            });


            handler = new Handler() ;


            sayac = (TextView) rootView.findViewById(R.id.adim);
            txtadim = (TextView) rootView.findViewById(R.id.txtAdim);
            walk=(ImageView)rootView.findViewById(R.id.walk);

            txtadim.setAlpha(0f);
            walk.setAlpha(0f);




            /*basla.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StartTime = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);
                    rootView.getContext().startService(new Intent(rootView.getContext(), FStepService.class));
                }
            });*/





            return rootView;
        }

        @Override
        public void onResume() {
            super.onResume();
            final Activity activity = getActivity();
            if (activity != null) {
                activity.bindService(new Intent(activity, FStepService.class),
                        mFSSConnection, 0);
            }
        }



        @Override
        public void onPause() {
            super.onPause();

            if (mFSService != null) {
                mFSService.unregisterListener(PlaceholderFragment.this);
            }

            final Activity activity = getActivity();
            if (activity != null) {
                activity.unbindService(mFSSConnection);
            }
        }

        @Override
        public void onStepDataUpdate(int stepCount) {
            sayac.setText(String.valueOf(stepCount));
        }

        //Yürüme anı


        @Override
        public void onStep(int count) {
            if (mStepEventAnim != null) {
                mStepEventAnim.cancel();
            }
            if (mStepEventAnim1 != null) {
                mStepEventAnim1.cancel();
            }

            txtadim.setAlpha(1f);
            walk.setAlpha(1f);
            mStepEventAnim = txtadim.animate().setDuration(500).alpha(0f);
            mStepEventAnim1 = walk.animate().setDuration(500).alpha(0f);

        }

        private class FSSConnection implements ServiceConnection {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d("MainActivity", "Connected to the FStepService");
                mFSService = ((FStepService.LocalBinder) service).getService();
                mFSService.registerListener(PlaceholderFragment.this);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mFSService = null;
                Log.d("MainActivity", "The FStepService has disconnected");
            }
        }
    }

}

