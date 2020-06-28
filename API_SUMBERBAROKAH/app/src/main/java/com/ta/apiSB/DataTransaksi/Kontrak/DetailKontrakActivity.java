package com.ta.apiSB.DataTransaksi.Kontrak;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ta.apiSB.Adapter.RvListKontrakDetilTender;
import com.ta.apiSB.R;
import com.ta.apiSB.RESTAPI.Controller.EnkripsiController;
import com.ta.apiSB.RESTAPI.Controller.KontrakController;
import com.ta.apiSB.RESTAPI.Controller.ResponseAES;
import com.ta.apiSB.RESTAPI.Kontrak.dataLaporan;
import com.ta.apiSB.RESTAPI.Kontrak.ListDataItemTender;
import com.ta.apiSB.RESTAPI.Kontrak.responsetenderDetail;
import com.ta.apiSB.Session.SessionManager;
import com.ta.apiSB.StoreAPI.StoreApi;
import com.ta.apiSB.Util.EnkripsiAES256;
import com.ta.apiSB.Utils;

import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailKontrakActivity extends AppCompatActivity {
    SessionManager mSession;
    String URL;

    //recylceview
    RecyclerView rvListKontrakDetilTender;
    LinearLayoutManager llmListKontrakDetilTender;
    RvListKontrakDetilTender mRvListKontrakDetilTender;

    SwipeRefreshLayout pullToRefresh;

    Button btnProses;
    TextView tvIdKontrak, tvnamacustomer, tvAlamatcustomer, tvtendernote, tvStatusTender, tvTanggalTender;

    EnkripsiController controllerEnkripsi = new EnkripsiController();
    KontrakController controllerKontrak = new KontrakController();

    String dataEnkripsi="";
    EnkripsiAES256 mEnkripsi = new EnkripsiAES256();
//    AES mAES = new AES();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kontrak);

        mSession = new SessionManager(getApplicationContext());
        final String token = mSession.getToken();
        final String user = mSession.getUsername();
        URL = mSession.getsIpServer() + ":" + mSession.getsPortServer();

        mSession = new SessionManager(getApplicationContext());
        URL = mSession.getsIpServer() + ":" + mSession.getsPortServer();





        // Recycleview
        rvListKontrakDetilTender = (RecyclerView) findViewById(R.id.rv_listTenderDetail);
        rvListKontrakDetilTender.setHasFixedSize(true);
        llmListKontrakDetilTender = new LinearLayoutManager(this);
        llmListKontrakDetilTender.setOrientation(LinearLayoutManager.VERTICAL);
        rvListKontrakDetilTender.setLayoutManager(llmListKontrakDetilTender);


        final Bundle mData = getIntent().getExtras();
        String sjJSON = mData.getString("data");
        final ListDataItemTender tender = Utils.getGsonParser().fromJson(sjJSON, ListDataItemTender.class);
        EnkripsiController controllerEnkripsi = new EnkripsiController();

       String id = tender.getTender_id();
        Log.d("id", "sebelum enkripsi: "+ id);
        controllerEnkripsi.enkripsiId(id, URL, mSession, new Callback<ResponseAES>() {
                    @Override
                    public void onResponse(Call<ResponseAES> call, Response<ResponseAES> response) {

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

                            Log.d("status", "data: " +message );
                            Log.d("status", "status code: " +statusCode );
                            Log.d("status", "token: " +token );

                            if (statusCode.equals("00"))
                            {
                                // Berhasil dapat data
                                String idtender = response.body().getEnkripsi();

                                getData(idtender,token,user);

                            }
                            else if(statusCode.equals("02"))
                            {
                                // Token bermasalah (Expired, tidak sesuai, dll) -> Arahkan ke logout, login ulang
//                        mSession.logoutUser();
//                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            }
                            else
                            {
        //                        rvListKontrak.setAdapter(null);
        //                        llKosong.setVisibility(View.VISIBLE);
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<ResponseAES> call, Throwable t) {

                    }
                });
       //  mEnkripsi.enkripsi("enkripsi",tender.getTender_id());

        System.out.println("ini url" + URL);
        System.out.println("ini User" + user);
        System.out.println("ini Token" + token);
        System.out.println("ini ID" + id);


        tvIdKontrak = (TextView) findViewById(R.id.tv_idKontrak);
        tvnamacustomer = (TextView) findViewById(R.id.tv_namaCustomer);
        tvAlamatcustomer= (TextView) findViewById(R.id.tv_alamat);
//        tvNamaCustomer = (TextView) findViewById(R.id.tv_namaCustomer);
        tvtendernote = (TextView) findViewById(R.id.tv_alamatCustomer);
        tvStatusTender = (TextView) findViewById(R.id.tv_statusTender);
        tvTanggalTender = (TextView) findViewById(R.id.tv_tanggaldetailtender);


//        getData(tender,token,user);





     //  btnProses = (Button) findViewById(R.id.btn_updateStatus2);


//        btnProses.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                if (btnProses.getText().toString().equalsIgnoreCase("proses")) {
////                    mKontrak.setKontrak_status("1"); // Artinya dalam pengerjaan
////                    updateStatus(mKontrak);
////                } else {
////                    mKontrak.setKontrak_status("2"); // Artinya SELESAI
////                    updateStatus(mKontrak);
////                }
//            }
//        });
    }



    private void getData(String id , final String token, String user)
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
        final Call<responsetenderDetail> result = service.getDatatenderdetail(id,token, user );

        Log.d("id", "sesudah enkripsi: "+ id);

        result.enqueue(new Callback<responsetenderDetail>()


        {



            @Override
            public void onResponse(Call<responsetenderDetail> call, Response<responsetenderDetail> response) {
                Gson gson = new Gson();
                Log.d("statustender detail", "data: " + gson.toJson(response) );
                Long tanggalTender;
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

                    Log.d("status", "data: " +message );
                    Log.d("status", "status code: " +statusCode );
                    Log.d("status", "token: " +token );

                    if (statusCode.equals("00"))
                    {
                        // Berhasil dapat data
//                        List<dataLaporan> listDatatender= new ArrayList<>();
//                        listDatatender = response.body().getDataTender().getDataLaporan();
                        List<dataLaporan> listTenderDetail = response.body().getDataTender().getDataLaporan();
                        Gson gson1 = new Gson();

                        System.out.println("ini dapet data" + gson.toJson(response.body().getDataTender()));
                        tvIdKontrak.setText(response.body().getDataTender().getTenderNomer());
                        tvnamacustomer.setText(response.body().getDataTender().getDataPelangan().getPelanganNama());
                        tvAlamatcustomer.setText(response.body().getDataTender().getDataPelangan().getPelanganAlamat());
                        tvtendernote.setText(response.body().getDataTender().getTenderNote());

                        tanggalTender  = response.body().getDataTender().getTender_date();
                        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy");
                        String dateText2 = df2.format(tanggalTender);
                        tvTanggalTender.setText(dateText2 );
                       String statusTender =(response.body().getDataTender().getTenderStatus());

                        if (statusTender.equalsIgnoreCase("open")) {
                            tvStatusTender.setText(statusTender);
                            tvStatusTender.setTextColor(Color.parseColor("#32cd32"));
                        }
                        else if (statusTender.equalsIgnoreCase("progres"))
                        {
                            tvStatusTender.setText(statusTender);
                            tvStatusTender.setTextColor(Color.parseColor("#0000FF"));
                        }
                        else
                        {
                            tvStatusTender.setText(statusTender);
                            tvStatusTender.setTextColor(Color.parseColor("#FF0000"));
                        }
                                if (listTenderDetail.size() > 0)
                                {
                                    mRvListKontrakDetilTender= new RvListKontrakDetilTender(getApplicationContext(), listTenderDetail, URL, mSession);
                                    rvListKontrakDetilTender.setAdapter(mRvListKontrakDetilTender);
                                }
//                        mRvListTender = new RvListTender(getApplicationContext(), listDatatender, URL, mSession);
//                        rvListKontrak.setAdapter(mRvListTender);
//
//                        llKosong.setVisibility(View.GONE);
                    }
                  //  else if(statusCode.equals("02"))
                   // {
                        // Token bermasalah (Expired, tidak sesuai, dll) -> Arahkan ke logout, login ulang
//                        mSession.logoutUser();
//                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    //}
                    else
                    {
//                        rvListKontrak.setAdapter(null);
//                        llKosong.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<responsetenderDetail> call, Throwable t) {
                Gson gson = new Gson();

                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Gagal " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("ERR", "onFailure: " + t.getMessage());

                System.out.println("fAILURE" + t.getMessage());
            }
        });
    }

//        private void updateStatus(final ListDataItemTender mKontrak)
//        {
//            final ProgressDialog dialog = new ProgressDialog(this);
//            dialog.setCancelable(false);
//            dialog.setMessage("Melakukan Proses Data...");
//            dialog.show();
//
//            String idKontrak = mKontrak.getKontrak_id();
//            controllerEnkripsi.enkripsiId(idKontrak, URL, mSession, new Callback< ResponseAES >(){
//
//                @Override
//                public void onResponse(Call<ResponseAES> call, Response<ResponseAES> response) {
//                    final String id, message, statusCode;
//                    if(response.code() != 200){
//                        message = "Server Not Respond";
//                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//                    }
//                    else {
//                        message = response.body().getStatus();
//                        statusCode = response.body().getStatusCode();
//                        id = response.body().getEnkripsi();
//                        if(statusCode.equals("00")){
//                            ObjectMapper oMapper = new ObjectMapper();
//                            Map<String, Object> mapData = oMapper.convertValue(mKontrak, Map.class);
//                            controllerEnkripsi.enkripsiData(mapData, URL, mSession,  new Callback<ResponseAES>(){
//
//                                @Override
//                                public void onResponse(Call<ResponseAES> call, Response<ResponseAES> response) {
//                                    String datakontrak, message, statusCode;
//                                    if(response.code()!=200){
//                                        message = "Server Not Respond";
//                                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//                                    }
//                                    else {
//                                        message = response.body().getStatus();
//                                        statusCode = response.body().getStatusCode();
//                                        datakontrak = response.body().getEnkripsi();
//                                        if(statusCode.equals("00")){
//
//                                            controllerKontrak.updateStatus(id, datakontrak, URL, mSession,  new Callback<ResponseServer>(){
//
//                                                @Override
//                                                public void onResponse(Call<ResponseServer> call, Response<ResponseServer> response) {
//                                                    String message, statusCode;
//                                                    if (response.code() != 200)
//                                                    {
//                                                        message = "Server Not Respond";
//                                                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//                                                    }
//                                                    else {
//                                                        message = response.body().getStatus();
//                                                        statusCode = response.body().getStatusCode();
//                                                        if (statusCode.equals("00")) {
//                                                            // Berhasil ubah data
//                                                            Intent intent = new Intent(getApplicationContext(), ListKontrakActivity.class);
//                                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                                            startActivity(intent);
//                                                            finish(); // call this to finish the current activityteguh
//                                                        } else if (statusCode.equals("02")) {
//                                                            // Token bermasalah (Expired, tidak sesuai, dll) -> Arahkan ke logout, login ulang
//                                                            mSession.logoutUser();
//                                                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//                                                        }
//                                                        else
//                                                        {
//                                                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//                                                        }
//                                                    }
//                                                }
//
//                                                @Override
//                                                public void onFailure(Call<ResponseServer> call, Throwable t) {
//                                                    Toast.makeText(getApplicationContext(), "Err : " + t.getMessage(), Toast.LENGTH_LONG).show();
//                                                    Log.e("PROSES UPDATE", "onFailure: " + t.getMessage());
//                                                }
//                                            });
//                                        }
//                                        else if (statusCode.equals("02")){
//                                            mSession.logoutUser();
//                                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//                                        }
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<ResponseAES> call, Throwable t) {
//                                    Toast.makeText(getApplicationContext(), "Err : " + t.getMessage(), Toast.LENGTH_LONG).show();
//                                    Log.e("PROSES UPDATE", "onFailure: " + t.getMessage());
//                                }
//                            });
//
//
//
//                        }
//                        else if(statusCode.equals("02")){
//                            mSession.logoutUser();
//                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseAES> call, Throwable t) {
//                    Toast.makeText(getApplicationContext(), "Err : " + t.getMessage(), Toast.LENGTH_LONG).show();
//                    Log.e("PROSES UPDATE", "onFailure: " + t.getMessage());
//
//                }
//            });





            // Enkrip data id
//            idKontrak = mAES.encrypt(idKontrak);
//
//            ObjectMapper oMapper = new ObjectMapper();
//            Map<String, Object> mapData = oMapper.convertValue(mKontrak, Map.class);
//            Map<String, Object> mapKontrak = new HashMap<String, Object>();
//            Map<String, Object> mapEnkripsi = new HashMap<String, Object>();
//
//            mapKontrak.put("data", mapData);
//            mapEnkripsi.put("enkripsi", mapKontrak);
//            dataEnkripsi = mEnkripsi.enkripsi(mapEnkripsi);
//
//
//            // lempar ke API
//            controllerKontrak.updateStatus(idKontrak, dataEnkripsi, URL, mSession, new Callback<ResponseServer>() {
//
//                @Override
//                public void onResponse(Call<ResponseServer> call, Response<ResponseServer> response) {
//                    String message, statusCode;
//                    if (response.code() != 200)
//                    {
//                        message = "Server Not Respond";
//                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//                    }
//                    else {
//                        message = response.body().getStatus();
//                        statusCode = response.body().getStatusCode();
//                        if (statusCode.equals("00")) {
//                            // Berhasil ubah data
//                            Intent intent = new Intent(getApplicationContext(), ListKontrakActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(intent);
//                            finish(); // call this to finish the current activity
//                        } else if (statusCode.equals("02")) {
//                            // Token bermasalah (Expired, tidak sesuai, dll) -> Arahkan ke logout, login ulang
//                            mSession.logoutUser();
//                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//                        }
//                        else
//                        {
//                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//                        }
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<ResponseServer> call, Throwable t) {
//                    Toast.makeText(getApplicationContext(), "Err : " + t.getMessage(), Toast.LENGTH_LONG).show();
//                    Log.e("PROSES UPDATE", "onFailure: " + t.getMessage());
//                }
//            });
//
//        }

    private void setData(ListDataItemTender mKontrak)
    {






        String  noKontrak, namaCustomer, alamatCustomer, statusTender, statusKontrak;
        Long awalKontrak, akhirKontrak, tanggalTender;

        noKontrak = mKontrak.getTender_nomer();
        statusKontrak = mKontrak.getTenderStatus();
//        awalKontrak = mKontrak.getKontrak_tglAwal();
//        akhirKontrak = mKontrak.getKontrak_tglAkhir();
        namaCustomer = mKontrak.getDataPelangan().getPelanganNama();
        alamatCustomer = mKontrak.getDataPelangan().getPelanganAlamat();
        statusTender = mKontrak.getTenderStatus();
        tanggalTender = mKontrak.getTender_date();
        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy");
//        String dateText1 = df2.format(awalKontrak);
//        String dateText2 = df2.format(akhirKontrak);
        String dateText3 = df2.format(tanggalTender);

        if (statusKontrak.equalsIgnoreCase("0"))
        {
            btnProses.setText("Proses");
        }
        else
        {
            btnProses.setText("Selesai");
        }
        tvIdKontrak.setText(noKontrak);
//        tvAWalKontrak.setText(dateText1);
//        tvAkhirKontrak.setText(dateText2);
        //tvNamaCustomer.setText(namaCustomer);
      //  tvAlamatCustomer.setText(alamatCustomer);
        tvStatusTender.setText(statusTender);
        tvTanggalTender.setText(dateText3);

        if (statusTender.equalsIgnoreCase("open")) {
            tvStatusTender.setTextColor(Color.parseColor("#32cd32"));
        }
        else if (statusTender.equalsIgnoreCase("progres"))
        {
            tvStatusTender.setTextColor(Color.parseColor("#0000FF"));
        }
        else
        {
            tvStatusTender.setTextColor(Color.parseColor("#FF0000"));
        }

//        List<dataLaporan> listTenderDetail = mKontrak.getDataLaporan();
//        if (listTenderDetail.size() > 0)
//        {
//            mRvListKontrakDetilTender= new RvListKontrakDetilTender(getApplicationContext(), listTenderDetail, URL, mSession);
//            rvListKontrakDetilTender.setAdapter(mRvListKontrakDetilTender);
//        }
    }
}
