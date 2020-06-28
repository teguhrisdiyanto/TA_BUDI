package com.ta.apiSB.DataTransaksi.Kontrak;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ta.apiSB.Adapter.RvListTender;
import com.ta.apiSB.R;
import com.ta.apiSB.RESTAPI.Kontrak.ListDataItemTender;
import com.ta.apiSB.RESTAPI.Kontrak.ResponseTender;
import com.ta.apiSB.Session.SessionManager;
import com.ta.apiSB.StoreAPI.StoreApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListKontrakActivity extends AppCompatActivity {

    SessionManager mSession;
    String URL;

    //recylceview
    RecyclerView rvListKontrak;
    LinearLayoutManager llmListKontrak;
    RvListTender mRvListTender;

    Button btnTambah;

    SwipeRefreshLayout pullToRefresh;
    LinearLayout llKosong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_kontrak);

        // Recycleview
        rvListKontrak = (RecyclerView) findViewById(R.id.rv_listKontrak);
        rvListKontrak.setHasFixedSize(true);
        llmListKontrak = new LinearLayoutManager(this);
        llmListKontrak.setOrientation(LinearLayoutManager.VERTICAL);
        rvListKontrak.setLayoutManager(llmListKontrak);

        llKosong = (LinearLayout)findViewById(R.id.ll_kosong);
        pullToRefresh = (SwipeRefreshLayout) findViewById(R.id.sr_pullToRefresh);

        mSession = new SessionManager(getApplicationContext());
        final String token = mSession.getToken();
        final String user = mSession.getUsername();
        URL = mSession.getsIpServer() + ":" + mSession.getsPortServer();

        getData(token, user);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(token, user);
                pullToRefresh.setRefreshing(false);
            }
        });
    }
    private void getData(String token, String user)
    {
        Log.d("TOKEN", "getData: "+ token);
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Mengambil data...");
        dialog.show();
        String baseURL = "http://" + URL + "/sumberbarokah-api/service/";
        Log.e("URL", "ambilData: " +baseURL );
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StoreApi service = retrofit.create(StoreApi.class);
        final Call<ResponseTender> result = service.getDatatender(token, user);

        result.enqueue(new Callback<ResponseTender>() {
            @Override
            public void onResponse(Call<ResponseTender> call, Response<ResponseTender> response) {
                dialog.dismiss();
                String message, statusCode;
                if (response.code() != 200)
                {
                    message = "Server Not Respond";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
                else
                {
                    message = response.body().getStatus();
                    statusCode = response.body().getStatusCode();

                    if (statusCode.equals("00"))
                    {
                        // Berhasil dapat data
                        List<ListDataItemTender> listDatatender= new ArrayList<>();
                        listDatatender = response.body().getListDataKontrak();
                        mRvListTender = new RvListTender(getApplicationContext(), listDatatender, URL, mSession);
                        rvListKontrak.setAdapter(mRvListTender);

                        llKosong.setVisibility(View.GONE);
                    }
                    else if(statusCode.equals("02"))
                    {
                        // Token bermasalah (Expired, tidak sesuai, dll) -> Arahkan ke logout, login ulang
                        mSession.logoutUser();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        rvListKontrak.setAdapter(null);
                        llKosong.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseTender> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Gagal " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("ERR", "onFailure: " + t.getMessage());
            }
        });
    }
}
