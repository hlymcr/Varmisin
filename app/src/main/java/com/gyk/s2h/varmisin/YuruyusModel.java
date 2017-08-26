package com.gyk.s2h.varmisin;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HULYA on 26.08.2017.
 */

public class YuruyusModel implements Serializable{


    private String sure;
    private String adimS;
    private String uid;


    public YuruyusModel(String sure,String adimS,String uid){

        this.sure=sure;
        this.adimS=adimS;
        this.uid=uid;

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

    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();

        result.put("sure", sure);

        result.put("adimS", adimS);

        result.put("uid", uid);

        return result;

    }




}
