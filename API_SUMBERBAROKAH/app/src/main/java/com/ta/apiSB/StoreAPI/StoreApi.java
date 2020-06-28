package com.ta.apiSB.StoreAPI;


import com.ta.apiSB.RESTAPI.Controller.ResponseAES;
import com.ta.apiSB.RESTAPI.Kontrak.ResponseTender;
import com.ta.apiSB.RESTAPI.Kontrak.responseaddlaporan;
import com.ta.apiSB.RESTAPI.Kontrak.responsetenderDetail;
import com.ta.apiSB.RESTAPI.ResponseServer;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface StoreApi {

    // Enkripsi
    @POST("autentikasi/enkripsi")
    Call<ResponseAES> enkripsi(@Body Map<String, Object> request,
                               @Header("token") String token,
                               @Header("username") String user);

    @POST("proses/login/autentikasi")
    Call<ResponseServer> doLogin(@Body Map<String, Object> request);

    @GET("transaksi/tender")
    Call<ResponseTender> getDatatender(
            @Header("token") String token,
            @Header("username") String user);


    @GET("transaksi/tender/{id}")
    Call<responsetenderDetail> getDatatenderdetail(
            @Path("id") String id,
            @Header("token") String token,
            @Header("username") String user);


    @POST("/sumberbarokah-api/service/transaksi/laporan")
    Call<responseaddlaporan> updateStatus(
                                      @Body Map<String, Object> request,
                                      @Header("token") String token,
                                      @Header("username") String user);

}
