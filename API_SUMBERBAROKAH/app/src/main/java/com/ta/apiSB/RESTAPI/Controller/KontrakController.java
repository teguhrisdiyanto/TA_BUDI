package com.ta.apiSB.RESTAPI.Controller;

import android.util.Log;

import com.google.gson.Gson;
import com.ta.apiSB.RESTAPI.Kontrak.responseaddlaporan;
import com.ta.apiSB.RESTAPI.ResponseServer;
import com.ta.apiSB.Session.SessionManager;
import com.ta.apiSB.StoreAPI.StoreApi;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KontrakController {
    public void tambahlaporan(String dataEnkripsi, String URL, SessionManager mSession, Callback<responseaddlaporan> callback)
    {
        Gson gson = new Gson();
        String baseURL = "http://" + URL;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StoreApi service = retrofit.create(StoreApi.class);

        final Map<String, Object> mapData = new HashMap<String, Object>();
        mapData.put("enkripsi", dataEnkripsi);

        Call<responseaddlaporan> result = service.updateStatus(mapData, mSession.getToken(), mSession.getUsername());

        Log.d("INI ", "KONTRAOLLER KONTRAK :" + result);
        result.enqueue(callback);
    }
}
