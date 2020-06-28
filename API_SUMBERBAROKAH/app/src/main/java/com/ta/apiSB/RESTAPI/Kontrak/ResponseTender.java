package com.ta.apiSB.RESTAPI.Kontrak;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseTender {

	@SerializedName("total")
	private int total;

	@SerializedName("listData")
	private List<ListDataItemTender> listDataKontrak;

	@SerializedName("statusCode")
	private String statusCode;

	@SerializedName("status")
	private String status;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<ListDataItemTender> getListDataKontrak() {
		return listDataKontrak;
	}

	public void setListDataKontrak(List<ListDataItemTender> listDataKontrak) {
		this.listDataKontrak = listDataKontrak;
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

	@Override
 	public String toString(){
		return 
			"ResponseTender{" +
			"total = '" + total + '\'' + 
			",listDataKontrak = '" + listDataKontrak + '\'' +
			",statusCode = '" + statusCode + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}