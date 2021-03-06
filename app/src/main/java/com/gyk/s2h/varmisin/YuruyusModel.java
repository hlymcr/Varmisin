package com.gyk.s2h.varmisin;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HULYA on 26.08.2017.
 */

public class YuruyusModel implements Serializable{

    private String isim;
    private String sure;
    private String adimS;
    private String uid;
    private String istek;




    public YuruyusModel(String isim,String sure,String adimS,String uid,String istek){
        this.isim=isim;
        this.sure=sure;
        this.adimS=adimS;
        this.uid=uid;
        this.istek=istek;

    }
    public String getIsim() {
        return isim;
    }

    public void setIsim(String sure) {
        this.isim = isim;
    }


    public String getSure() {
        return sure;
    }

    public void setSure(String sure) {
        this.sure = sure;
    }

    public String getAdimS() {
        return adimS;
    }

    public void setAdimS(String adimS) {
        this.adimS = adimS;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIstek() {
        return istek;
    }

    public void setIstek(String istek) {
        this.istek = istek;
    }

    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();

        result.put("isim",isim);

        result.put("sure", sure);

        result.put("adimS", adimS);

        result.put("uid", uid);

        result.put("istek",istek);


        return result;

    }




}
