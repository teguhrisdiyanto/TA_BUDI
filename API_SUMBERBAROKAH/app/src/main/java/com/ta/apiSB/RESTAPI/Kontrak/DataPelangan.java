package com.ta.apiSB.RESTAPI.Kontrak;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataPelangan implements Serializable {

	@SerializedName("pelangan_id")
	private String pelanganid;

	@SerializedName("pelangan_nama")
	private String pelanganNama;

	@SerializedName("pelangan_alamat")
	private String pelanganAlamat;

	@SerializedName("pelangan_telpon")
	private String pelanganTelpon;

	@SerializedName("pelangan_email")
	private String pelanganEmail;

	public String getPelanganid() {
		return pelanganid;
	}

	public void setPelanganid(String pelanganid) {
		this.pelanganid = pelanganid;
	}

	public String getPelanganNama() {
		return pelanganNama;
	}

	public void setPelanganNama(String pelanganNama) {
		this.pelanganNama = pelanganNama;
	}

	public String getPelanganAlamat() {
		return pelanganAlamat;
	}

	public void setPelanganAlamat(String pelanganAlamat) {
		this.pelanganAlamat = pelanganAlamat;
	}

	public String getPelanganTelpon() {
		return pelanganTelpon;
	}

	public void setPelanganTelpon(String pelanganTelpon) {
		this.pelanganTelpon = pelanganTelpon;
	}

	public String getPelanganEmail() {
		return pelanganEmail;
	}

	public void setPelanganEmail(String pelanganEmail) {
		this.pelanganEmail = pelanganEmail;
	}

	@Override
 	public String toString(){
		return 
			"DataPelangan{" +pelanganid + '\'' +
			",pelangan_nama = '" + pelanganNama + '\'' +
			",pelangan_alamat = '" + pelanganAlamat + '\'' +
			",pelangan_telpon = '" + pelanganTelpon + '\'' +
			",pelangan_email = '" + pelanganEmail + '\'' +
			"}";
		}
}