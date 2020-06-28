package com.ta.apiSB.RESTAPI.Kontrak;

import com.google.gson.annotations.SerializedName;

public class responsetenderDetail {

    @SerializedName("data")
    private DataTender data;

    @SerializedName("statusCode")
    private String statusCode;

    @SerializedName("status")
    private String status;

    public DataTender getDataTender() {
        return data;
    }

    public void setDataTender(DataTender dataTender) {
        this.data = dataTender;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
