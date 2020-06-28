package com.ta.apiSB.RESTAPI.Kontrak;

import com.google.gson.annotations.SerializedName;

public class ListDataItemTender {

	@SerializedName("tender_id")
	private String tender_id;

	@SerializedName("tender_nomer")
	private String tender_nomer;

	@SerializedName("dataPelangan")
	private DataPelangan dataPelangan;

	@SerializedName("tender_date")
	private long tender_date;

	@SerializedName("tender_note")
	private String tender_note;

	@SerializedName("tender_status")
	private String tenderStatus;

	public String getTender_id() {
		return tender_id;
	}

	public void setTender_id(String tender_id) {
		this.tender_id = tender_id;
	}

	public String getTender_nomer() {
		return tender_nomer;
	}

	public void setTender_nomer(String tender_nomer) {
		this.tender_nomer = tender_nomer;
	}

	public DataPelangan getDataPelangan() {
		return dataPelangan;
	}

	public void setDataPelangan(DataPelangan dataPelangan) {
		this.dataPelangan = dataPelangan;
	}

	public long getTender_date() {
		return tender_date;
	}

	public void setTender_date(long tender_date) {
		this.tender_date = tender_date;
	}

	public String getTender_note() {
		return tender_note;
	}

	public void setTender_note(String tender_note) {
		this.tender_note = tender_note;
	}

	public String getTenderStatus() {
		return tenderStatus;
	}

	public void setTenderStatus(String tenderStatus) {
		this.tenderStatus = tenderStatus;
	}
}