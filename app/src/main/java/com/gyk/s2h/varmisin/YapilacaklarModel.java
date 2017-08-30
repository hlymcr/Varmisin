package com.gyk.s2h.varmisin;

/**
 * Created by HULYA on 28.08.2017.
 */
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HULYA on 26.08.2017.
 */

public class YapilacaklarModel implements Serializable{

    private String isim;
    private String sure;
    private String adimS;
    private String uid;
    private String saat;




    public YapilacaklarModel(String isim,String sure,String adimS,String uid,String saat){
        this.isim=isim;
        this.sure=sure;
        this.adimS=adimS;
        this.uid=uid;
        this.saat=saat;

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

    public String getSaat() {
        return saat;
    }

    public void setSaat(String istek) {
        this.saat = saat;
    }

    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();

        result.put("isim",isim);

        result.put("sure", sure);

        result.put("adimS", adimS);

        result.put("uid", uid);

        result.put("saat",saat);


        return result;

    }




}
