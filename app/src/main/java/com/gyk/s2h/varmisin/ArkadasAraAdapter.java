package com.gyk.s2h.varmisin;

/**
 * Created by HULYA on 18.08.2017.
 */
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import android.content.Context;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by HULYA on 12.05.2017.
 */

public class ArkadasAraAdapter extends BaseAdapter implements Filterable {

    LayoutInflater layoutInflater;
    ArrayList<KisiModel> kullaniciList;
    TextView kullaniciAdi,adSoyad;
    ImageView resim;
    Context context;


    //Arkadaş Arama için Listemizi doldurmayı sağladığımız adaptörümüz..

    public ArkadasAraAdapter(FragmentActivity activity, ArrayList<KisiModel> kullaniciList) {

        layoutInflater =(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.kullaniciList =kullaniciList;

    }


    @Override
    public int getCount() {
        return kullaniciList.size();
    }

    @Override
    public Object getItem(int i) {
        return kullaniciList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       KisiModel kisiModel =kullaniciList.get(i);
        View satir =layoutInflater.inflate(R.layout.list_item,null);

        //Resmi oval yapmak için kullanılan metod
        final Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.DKGRAY)
                .borderWidthDp(4)
                .cornerRadiusDp(35)
                .oval(true)
                .build();

        kullaniciAdi=(TextView)satir.findViewById(R.id.kullaniciAd);
        adSoyad = (TextView)satir.findViewById(R.id.adSoyad);
        resim=(ImageView)satir.findViewById(R.id.resim);
        String secilenresim=kisiModel.getPath();
        Uri secim=Uri.parse(secilenresim);
        Picasso.with(satir.getContext()).load(secim).fit().transform(transformation).into(resim);

        kullaniciAdi.setText(kisiModel.getKullanici_adi());
        adSoyad.setText(kisiModel.getAdSoyad());



        return satir;

    }

    //Aramayı sağlayan metod.
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results =new FilterResults();

                if(charSequence==null || charSequence.length()==0){
                    results.values=kullaniciList;
                    results.count=kullaniciList.size();
                }
                else{
                    ArrayList<KisiModel> filterResultData =new ArrayList<>();

                    for (KisiModel data:kullaniciList){

                        if(data.getKullanici_adi().toUpperCase().contains(charSequence.toString().toUpperCase())){
                            filterResultData.add(data);
                        }
                    }
                    results.values=filterResultData;
                    results.count=filterResultData.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    kullaniciList=(ArrayList<KisiModel>) filterResults.values;
                    notifyDataSetChanged();
            }
        };
    }


}
