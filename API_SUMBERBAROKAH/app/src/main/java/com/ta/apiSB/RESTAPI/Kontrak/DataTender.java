package com.ta.apiSB.RESTAPI.Kontrak;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class DataTender implements  Serializable{

    @SerializedName("tender_id")
    private String tenderid;

    @SerializedName("dataPelangan")
    private DataPelangan dataPelangan;

    @SerializedName("tender_nomer")
    private String tenderNomer;

    @SerializedName("tender_date")
    private long tender_date;


    @SerializedName("tender_note")
    private String tenderNote;


    @SerializedName("tender_status")
    private String tenderStatus;

    @SerializedName("dataLaporan")
    private List<dataLaporan> dataLaporan;

    public String getTenderid() {
        return tenderid;
    }

    public void setTenderid(String tenderid) {
        this.tenderid = tenderid;
    }

    public DataPelangan getDataPelangan() {
        return dataPelangan;
    }

    public void setDataPelangan(DataPelangan dataPelangan) {
        this.dataPelangan = dataPelangan;
    }

    public String getTenderNomer() {
        return tenderNomer;
    }

    public void setTenderNomer(String tenderNomer) {
        this.tenderNomer = tenderNomer;
    }

    public long getTender_date() {
        return tender_date;
    }

    public void setTender_date(long tender_date) {
        this.tender_date = tender_date;
    }

    public String getTenderNote() {
        return tenderNote;
    }

    public void setTenderNote(String tenderNote) {
        this.tenderNote = tenderNote;
    }

    public String getTenderStatus() {
        return tenderStatus;
    }

    public void setTenderStatus(String tenderStatus) {
        this.tenderStatus = tenderStatus;
    }

    public List<dataLaporan> getDataLaporan() {
        return dataLaporan;
    }

    public void setDataLaporan(List<dataLaporan> dataLaporan) {
        this.dataLaporan = dataLaporan;
    }
}
