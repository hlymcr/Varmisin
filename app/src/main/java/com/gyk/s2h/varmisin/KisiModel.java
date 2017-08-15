package com.gyk.s2h.varmisin;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HULYA on 20.07.2017.
 */

public class KisiModel implements Serializable {

    private String AdSoyad;

    private String Tel;

    private String adres;


    public KisiModel(){
        this.AdSoyad=AdSoyad;
        this.Tel=Tel;
        this.adres =adres;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getAdSoyad() {
        return AdSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        AdSoyad = adSoyad;
    }


    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }
    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();

        result.put("adSoyad", AdSoyad);

        result.put("adres", adres);

        result.put("tel", Tel);

        return result;

    }




}
