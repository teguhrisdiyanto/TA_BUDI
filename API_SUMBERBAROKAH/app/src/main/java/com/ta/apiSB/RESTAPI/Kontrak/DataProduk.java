package com.ta.apiSB.RESTAPI.Kontrak;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataProduk implements Serializable {

    @SerializedName("produk_id")
    private String produkId;

    @SerializedName("produk_nama")
    private String produkNama;

    @SerializedName("produk_deskripsi")
    private String produkDeskripsi;

    @SerializedName("produk_harga")
    private int produkHarga;

    public String getProdukId() {
        return produkId;
    }

    public void setProdukId(String produkId) {
        this.produkId = produkId;
    }

    public String getProdukNama() {
        return produkNama;
    }

    public void setProdukNama(String produkNama) {
        this.produkNama = produkNama;
    }

    public String getProdukDeskripsi() {
        return produkDeskripsi;
    }

    public void setProdukDeskripsi(String produkDeskripsi) {
        this.produkDeskripsi = produkDeskripsi;
    }

    public int getProdukHarga() {
        return produkHarga;
    }

    public void setProdukHarga(int produkHarga) {
        this.produkHarga = produkHarga;
    }

    @Override
    public String toString(){
        return
             "DataProduk{" +
             "produk_id = '" + produkId + '\'' +
             ",produk_nama = '" + produkNama + '\'' +
             ",produk_deskripsi = '" + produkDeskripsi + '\'' +
             ",produk_harga = '" + produkHarga + '\'' +
             "}";
    }
}
