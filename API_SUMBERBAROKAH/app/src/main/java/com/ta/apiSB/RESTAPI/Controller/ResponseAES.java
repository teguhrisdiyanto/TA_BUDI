package com.ta.apiSB.RESTAPI.Controller;

import com.google.gson.annotations.SerializedName;

public class ResponseAES{

	@SerializedName("enkripsi")
	private String enkripsi;

	@SerializedName("statusCode")
	private String statusCode;

	@SerializedName("status")
	private String status;

	public void setEnkripsi(String enkripsi){
		this.enkripsi = enkripsi;
	}

	public String getEnkripsi(){
		return enkripsi;
	}

	public void setStatusCode(String statusCode){
		this.statusCode = statusCode;
	}

	public String getStatusCode(){
		return statusCode;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ResponseAES{" + 
			"enkripsi = '" + enkripsi + '\'' + 
			",statusCode = '" + statusCode + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}