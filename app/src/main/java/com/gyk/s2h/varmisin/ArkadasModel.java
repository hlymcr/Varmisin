package com.gyk.s2h.varmisin;

import android.net.Uri;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HULYA on 20.07.2017.
 */

public class ArkadasModel implements Serializable {

    private String AdSoyad;
    private String kullanici_adi;
    private String dtarih;
    private String path;
    private String uid;


    public ArkadasModel(String kullaniciAdi, String isim, String dTarih, String path, String uid) {

        this.kullanici_adi =kullaniciAdi;
        this.AdSoyad=isim;
        this.dtarih=dTarih;
        this.path=path;
        this.uid=uid;

    }

    public ArkadasModel() {

    }


    public String getUid() {

        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }



    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAdSoyad() {
        return AdSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        AdSoyad = adSoyad;
    }

    public String getKullanici_adi() {
        return kullanici_adi;
    }

    public void setKullanici_adi(String kullanici_adi) {
        this.kullanici_adi = kullanici_adi;
    }

    public String getDtarih() {
        return dtarih;
    }

    public void setDtarih(String dtarih) {
        this.dtarih = dtarih;
    }

    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();

        result.put("adSoyad", AdSoyad);

        result.put("kullaniciadi", kullanici_adi);

        result.put("dtarihi", dtarih);

        result.put("path",path);

        result.put("uid",uid);

        return result;

    }




}