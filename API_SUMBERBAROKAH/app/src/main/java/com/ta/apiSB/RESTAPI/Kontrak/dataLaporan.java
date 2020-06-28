package com.ta.apiSB.RESTAPI.Kontrak;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class dataLaporan implements Serializable {

    @SerializedName("laporan_id")
    private String laporan_id;

    @SerializedName("tender_id")
    private String tender_id;


    @SerializedName("laporan_date")
    private Long laporan_date;


    @SerializedName("laporan_matrial")
    private String laporan_matrial;



    @SerializedName("laporan_orang")
    private String laporan_orang;



    @SerializedName("laporan_kegiatan")
    private String laporan_kegiatan;

    public String getTender_id() {
        return tender_id;
    }

    public void setTender_id(String tender_id) {
        this.tender_id = tender_id;
    }

    public String getLaporan_id() {
        return laporan_id;
    }

    public void setLaporan_id(String laporan_id) {
        this.laporan_id = laporan_id;
    }


    public Long getLaporan_date() {
        return laporan_date;
    }

    public void setLaporan_date(Long laporan_date) {
        this.laporan_date = laporan_date;
    }

    public String getLaporan_matrial() {
        return laporan_matrial;
    }

    public void setLaporan_matrial(String laporan_matrial) {
        this.laporan_matrial = laporan_matrial;
    }

    public String getLaporan_orang() {
        return laporan_orang;
    }

    public void setLaporan_orang(String laporan_orang) {
        this.laporan_orang = laporan_orang;
    }

    public String getLaporan_kegiatan() {
        return laporan_kegiatan;
    }

    public void setLaporan_kegiatan(String laporan_kegiatan) {
        this.laporan_kegiatan = laporan_kegiatan;
    }

    @Override
    public String toString(){
        return
             "dataLaporan{" +
             "laporan_id = '" + laporan_id + '\'' +
             ",laporan_date = '" + laporan_date + '\'' +
             ",laporan_matrial = '" + laporan_matrial + '\'' +
             ",laporan_orang = '" + laporan_orang + '\'' +
             ",laporan_kegiatan = '" + laporan_kegiatan + '\'' +
             "}";
    }
}
