package com.gyk.s2h.varmisin;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HULYA on 30.09.2017.
 */

public class PaylasilanlarAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    ArrayList<PaylasmaModel> paylasilanList;
    TextView isim, detay;
    ImageView kategori;
    Context context;

    public PaylasilanlarAdapter(FragmentActivity activity, ArrayList<PaylasmaModel> paylasilanList) {

        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.paylasilanList = paylasilanList;

    }

    @Override
    public int getCount() {
        return paylasilanList.size();
    }

    @Override
    public Object getItem(int i) {
        return paylasilanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PaylasmaModel paylasilan = paylasilanList.get(i);


        View satir = layoutInflater.inflate(R.layout.anasayfa_list_item, null);
        kategori = (ImageView) satir.findViewById(R.id.kategori);
        isim = (TextView) satir.findViewById(R.id.isim);
        detay = (TextView) satir.findViewById(R.id.gonderen);
        final ImageView tebrik=(ImageView)satir.findViewById(R.id.tebrik);

        String Kategori =paylasilan.getKategori();
        detay.setText(paylasilan.getKategori());
        String durum =paylasilan.getDurum();
        isim.setText(paylasilan.getIsim());
        if(Kategori.equals("guzelIsler")){
            kategori.setImageResource(R.drawable.hearth);

            if(durum.equals("0")){
                detay.setText(paylasilan.getGonderen()+" tarafından atanan "+paylasilan.getDetay()+" etkinliğini "+paylasilan.getSure()+" saat de gerçekleştiremedi :(");


            }
            else if(durum.equals("1")){

                detay.setText(paylasilan.getGonderen()+" tarafından atanan "+paylasilan.getDetay()+" etkinliğini "+paylasilan.getSure()+" saat de gerçekleştirdi :)");

            }

        }
        if(Kategori.equals("yürüyüs")){
            kategori.setImageResource(R.drawable.walk2);
            if(durum.equals("0")){
                detay.setText(paylasilan.getGonderen()+" tarafından atanan "+paylasilan.getDetay()+" adım sayısını atmayı "+paylasilan.getSure() + " saat de başaramadı :(");

            }
            else if(durum.equals("1")){

                detay.setText(paylasilan.getGonderen()+" tarafından atanan "+paylasilan.getDetay()+" adım sayısını atmayı "+paylasilan.getSure() + " saat de başardı :)");

            }
        }
        if(Kategori.equals("müzik")){
            kategori.setImageResource(R.drawable.singer);
            if(durum.equals("0")){
                detay.setText(paylasilan.getGonderen()+" tarafından atanan "+paylasilan.getDetay()+" şarkısını söylemeyi "+paylasilan.getSure() + " saat de başaramadı :(");

            }
            else if(durum.equals("1")){

                detay.setText(paylasilan.getGonderen()+" tarafından atanan "+paylasilan.getDetay()+" şarkısını söylemeyi "+paylasilan.getSure() + " saat de başardı :)");

            }
        }


        tebrik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tebrik.setImageResource(R.drawable.handshake2);
            }
        });


        return satir;
    }
}
