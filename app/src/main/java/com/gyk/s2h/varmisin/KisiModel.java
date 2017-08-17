package com.gyk.s2h.varmisin;

import android.net.Uri;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HULYA on 20.07.2017.
 */

public class KisiModel implements Serializable {

    private String AdSoyad;

    private String kullanici_adi;
    private String dtarih;
    private String path;

    public KisiModel(String adSoyad, String kullanici_adi, String dtarih,String path) {
        this.AdSoyad = adSoyad;
        this.kullanici_adi = kullanici_adi;
        this.dtarih = dtarih;
        this.path=path;
    }

    public KisiModel() {

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

        result.put("adres", kullanici_adi);

        result.put("tel", dtarih);

        result.put("path",path);

        return result;

    }




}
