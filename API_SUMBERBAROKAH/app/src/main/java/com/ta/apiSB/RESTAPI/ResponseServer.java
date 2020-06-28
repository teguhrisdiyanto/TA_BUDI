package com.ta.apiSB.RESTAPI;

import com.google.gson.annotations.SerializedName;

public class ResponseServer {

	@SerializedName("token")
	private String token;

	@SerializedName("statusCode")
	private String statusCode;

	@SerializedName("status")
	private String status;


	@SerializedName("level")
	private String level;

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
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
			"ResponseServer{" +
			"token = '" + token + '\'' + 
			",statusCode = '" + statusCode + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}