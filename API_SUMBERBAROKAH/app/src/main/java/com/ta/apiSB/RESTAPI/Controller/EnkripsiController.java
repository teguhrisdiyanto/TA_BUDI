package com.ta.apiSB.RESTAPI.Controller;

import com.ta.apiSB.Session.SessionManager;
import com.ta.apiSB.StoreAPI.StoreApi;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EnkripsiController {

    public void enkripsiId(String id, String URL, SessionManager mSession, Callback<ResponseAES> callback)
    {
        String baseURL = "http://" + URL + "/sumberbarokah-api/service/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StoreApi service = retrofit.create(StoreApi.class);

        final Map<String, Object> data = new HashMap<String, Object>();
        data.put("enkripsi", id);

        Call<ResponseAES> result = service.enkripsi(data, mSession.getToken(), mSession.getUsername());
        result.enqueue(callback);
    }

    public void enkripsiData(Map<String, Object> mapData, String URL, SessionManager mSession, Callback<ResponseAES> callback)
    {
        String baseURL = "http://" + URL + "/sumberbarokah-api/service/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StoreApi service = retrofit.create(StoreApi.class);

        final Map<String, Object> data = new HashMap<String, Object>();
        Map<String, Object> mapKontrak = new HashMap<String, Object>();
        Map<String, Object> mapEnkripsi = new HashMap<String, Object>();
        mapKontrak.put("data", mapData);
        mapEnkripsi.put("enkripsi", mapKontrak);
        data.put("enkripsi", mapData);

        Call<ResponseAES> result = service.enkripsi(mapEnkripsi, mSession.getToken(), mSession.getUsername());
        result.enqueue(callback);
    }


}
