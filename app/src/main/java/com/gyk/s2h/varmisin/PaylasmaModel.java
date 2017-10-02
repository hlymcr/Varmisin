package com.gyk.s2h.varmisin;

/**
 * Created by HULYA on 30.09.2017.
 */
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HULYA on 20.07.2017.
 */

public class PaylasmaModel implements Serializable {

    private String isim;
    private String gonderen;
    private String kategori;
    private String detay;
    private String durum;
    private String sure;


    public PaylasmaModel(String isim, String gonderen, String kategori, String detay,String durum,String sure) {

        this.isim =isim;
        this.gonderen=gonderen;
        this.kategori=kategori;
        this.detay=detay;
        this.durum=durum;
        this.sure=sure;



    }

    public PaylasmaModel() {

    }




    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getKategori() {
        return kategori;
    }

    public String getGonderen() {
        return gonderen;
    }

    public void setGonderen(String gonderen) {
        this.gonderen = gonderen;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }

    public String getSure() {
        return sure;
    }

    public void setSure(String sure) {
        this.sure = sure;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getDetay() {
        return detay;
    }

    public void setDetay(String detay) {
        this.detay = detay;
    }


    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();

        result.put("isim", isim);
        result.put("gonderen", gonderen);
        result.put("kategori", kategori);

        result.put("detay", detay);

        result.put("durum", durum);
        result.put("sure", sure);


        return result;

    }




}
