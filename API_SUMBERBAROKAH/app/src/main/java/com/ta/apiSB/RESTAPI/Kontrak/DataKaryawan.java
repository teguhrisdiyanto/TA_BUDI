package com.ta.apiSB.RESTAPI.Kontrak;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataKaryawan implements Serializable {

	@SerializedName("karyawan_id")
	private String karyawanId;

	@SerializedName("karyawan_nama")
	private String karyawanNama;

	@SerializedName("karyawan_alamat")
	private String karyawanAlamat;

	@SerializedName("karyawan_tglLahir")
	private long karyawanTglLahir;

	@SerializedName("karyawan_email")
	private String karyawanEmail;

	@SerializedName("karyawan_status")
	private String karyawanStatus;

	@SerializedName("karyawan_jenkel")
	private String karyawanJenkel;

	@SerializedName("karyawan_telepon")
	private String karyawanTelepon;

	@SerializedName("karyawan_tglBuat")
	private long karyawanTglBuat;

	@SerializedName("karyawan_tglUbah")
	private long karyawanTglUbah;

	public String getKaryawanId() {
		return karyawanId;
	}

	public void setKaryawanId(String karyawanId) {
		this.karyawanId = karyawanId;
	}

	public String getKaryawanNama() {
		return karyawanNama;
	}

	public void setKaryawanNama(String karyawanNama) {
		this.karyawanNama = karyawanNama;
	}

	public String getKaryawanAlamat() {
		return karyawanAlamat;
	}

	public void setKaryawanAlamat(String karyawanAlamat) {
		this.karyawanAlamat = karyawanAlamat;
	}

	public long getKaryawanTglLahir() {
		return karyawanTglLahir;
	}

	public void setKaryawanTglLahir(long karyawanTglLahir) {
		this.karyawanTglLahir = karyawanTglLahir;
	}

	public String getKaryawanEmail() {
		return karyawanEmail;
	}

	public void setKaryawanEmail(String karyawanEmail) {
		this.karyawanEmail = karyawanEmail;
	}

	public String getKaryawanStatus() {
		return karyawanStatus;
	}

	public void setKaryawanStatus(String karyawanStatus) {
		this.karyawanStatus = karyawanStatus;
	}

	public String getKaryawanJenkel() {
		return karyawanJenkel;
	}

	public void setKaryawanJenkel(String karyawanJenkel) {
		this.karyawanJenkel = karyawanJenkel;
	}

	public String getKaryawanTelepon() {
		return karyawanTelepon;
	}

	public void setKaryawanTelepon(String karyawanTelepon) {
		this.karyawanTelepon = karyawanTelepon;
	}

	public long getKaryawanTglBuat() {
		return karyawanTglBuat;
	}

	public void setKaryawanTglBuat(long karyawanTglBuat) {
		this.karyawanTglBuat = karyawanTglBuat;
	}

	public long getKaryawanTglUbah() {
		return karyawanTglUbah;
	}

	public void setKaryawanTglUbah(long karyawanTglUbah) {
		this.karyawanTglUbah = karyawanTglUbah;
	}

	@Override
 	public String toString(){
		return 
			"DataKaryawan{" +
			"karyawan_id = '" + karyawanId + '\'' +
			",karyawan_nama = '" + karyawanNama + '\'' +
			",karyawan_alamat = '" + karyawanAlamat + '\'' +
			",karyawan_tglLahir = '" + karyawanTglLahir + '\'' +
			",karyawan_email = '" + karyawanEmail + '\'' +
			",karyawan_status = '" + karyawanStatus + '\'' +
			",karyawan_jenkel = '" + karyawanJenkel + '\'' +
			",karyawan_telepon = '" + karyawanTelepon + '\'' +
			",karyawan_tglBuat = '" + karyawanTglBuat + '\'' +
			",karyawan_tglUbah = '" + karyawanTglUbah + '\'' +
			"}";
		}
}