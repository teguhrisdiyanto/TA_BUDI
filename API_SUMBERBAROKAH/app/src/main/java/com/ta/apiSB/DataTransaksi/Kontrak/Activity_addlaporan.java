package com.ta.apiSB.DataTransaksi.Kontrak;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ta.apiSB.R;
import com.ta.apiSB.RESTAPI.Controller.EnkripsiController;
import com.ta.apiSB.RESTAPI.Controller.KontrakController;
import com.ta.apiSB.RESTAPI.Controller.ResponseAES;
import com.ta.apiSB.RESTAPI.Kontrak.ListDataItemTender;
import com.ta.apiSB.RESTAPI.Kontrak.dataLaporan;
import com.ta.apiSB.RESTAPI.Kontrak.responseaddlaporan;
import com.ta.apiSB.RESTAPI.Kontrak.responsetenderDetail;
import com.ta.apiSB.RESTAPI.ResponseServer;
import com.ta.apiSB.Session.SessionManager;
import com.ta.apiSB.StoreAPI.StoreApi;
import com.ta.apiSB.Util.EnkripsiAES256;
import com.ta.apiSB.Utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_addlaporan extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    SessionManager mSession;
    String URL;
    public String idtender;

    Button btnProses;
    TextView tvIdKontrak, tvnamacustomer, tvAlamatcustomer, tvtendernote, tvStatusTender, tvTanggalTender;
    EditText tvtanggallaporan,tv_matrial,tv_orang,tv_kegiatan;

    EnkripsiController controllerEnkripsi = new EnkripsiController();
    KontrakController controllerKontrak = new KontrakController();

    String dataEnkripsi="";
    EnkripsiAES256 mEnkripsi = new EnkripsiAES256();
//    AES mAES = new AES();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlaporan);

        mSession = new SessionManager(getApplicationContext());
        final String token = mSession.getToken();
        final String user = mSession.getUsername();
        URL = mSession.getsIpServer() + ":" + mSession.getsPortServer();

        mSession = new SessionManager(getApplicationContext());
        URL = mSession.getsIpServer() + ":" + mSession.getsPortServer();

        final Bundle mData = getIntent().getExtras();
        String sjJSON = mData.getString("data");
        final ListDataItemTender tender = Utils.getGsonParser().fromJson(sjJSON, ListDataItemTender.class);
        EnkripsiController controllerEnkripsi = new EnkripsiController();

        String id = tender.getTender_id();
        Log.d("id", "sebelum enkripsi: " + id);
        controllerEnkripsi.enkripsiId(id, URL, mSession, new Callback<ResponseAES>() {
            @Override
            public void onResponse(Call<ResponseAES> call, Response<ResponseAES> response) {

                String message, statusCode;
                if (response.code() != 200) {
                    message = "Server Not Respond";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                } else {
                    message = response.body().getStatus();
                    statusCode = response.body().getStatusCode();

                    Log.d("status", "data: " + message);
                    Log.d("status", "status code: " + statusCode);
                    Log.d("status", "token: " + token);

                    if (statusCode.equals("00")) {
                        // Berhasil dapat data
                        String idtender = response.body().getEnkripsi();

                        getData(idtender, token, user);

                    } else if (statusCode.equals("02")) {
                        // Token bermasalah (Expired, tidak sesuai, dll) -> Arahkan ke logout, login ulang
//                        mSession.logoutUser();
//                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    } else {
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
        tvAlamatcustomer = (TextView) findViewById(R.id.tv_alamat);
//        tvNamaCustomer = (TextView) findViewById(R.id.tv_namaCustomer);
        tvtendernote = (TextView) findViewById(R.id.tv_alamatCustomer);
        tvStatusTender = (TextView) findViewById(R.id.tv_statusTender);
        tvTanggalTender = (TextView) findViewById(R.id.tv_tanggaldetailtender);
        tvtanggallaporan = (EditText) findViewById(R.id.tv_tanggallaporan);
        tvtanggallaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
        tv_matrial =(EditText) findViewById(R.id.tv_matrial);
        tv_orang = (EditText) findViewById(R.id.tv_orang);
        tv_kegiatan = (EditText) findViewById(R.id.tv_kegiatan);



//        getData(tender,token,user);


        btnProses = (Button) findViewById(R.id.btn_addlaporan);


        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             String tanggal = tvtanggallaporan.getText().toString();

                Log.d("ini" ,"ini text matrial" + tv_matrial.getText());
                Log.d("ini" ,"ini text orang" +  tv_orang.getText());
                Log.d("ini" ,"ini text kegiatan" + tv_kegiatan.getText());
                Log.d("ini" ,"ini id kontrak :" + idtender);
                dataLaporan dta = new dataLaporan();
                try {
                    dta.setTender_id(idtender);
                    long tanggallapor = convertdattolong(tanggal);
                    dta.setLaporan_date(tanggallapor);
                    dta.setLaporan_matrial(tv_matrial.getText().toString());
                    dta.setLaporan_orang(tv_orang.getText().toString());
                    dta.setLaporan_kegiatan(tv_kegiatan.getText().toString());
                    adLaporan(dta);
                } catch (ParseException e) {
                    e.printStackTrace();
                }



//                if (btnProses.getText().toString().equalsIgnoreCase("proses")) {
//                    mKontrak.setKontrak_status("1"); // Artinya dalam pengerjaan
//                    updateStatus(mKontrak);
//                } else {
//                    mKontrak.setKontrak_status("2"); // Artinya SELESAI
//                    updateStatus(mKontrak);
//                }
            }
        });
    }
    SimpleDateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy");
    private void showDateDialog(){

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                tvtanggallaporan.setText(df2.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
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
                        idtender = response.body().getDataTender().getTenderid();
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
                            btnProses.setVisibility(View.GONE);
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
    private void adLaporan(final dataLaporan mDatalaporan) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Melakukan Proses Data...");
        dialog.show();

//            String idKontrak = mKontrak.getKontrak_id();
        ObjectMapper oMapper = new ObjectMapper();
        Map<String, Object> mapData = oMapper.convertValue(mDatalaporan, Map.class);
        controllerEnkripsi.enkripsiData(mapData, URL, mSession, new Callback<ResponseAES>() {

            @Override
            public void onResponse(Call<ResponseAES> call, Response<ResponseAES> response) {
                final String data, message, statusCode;
                if (response.code() != 200) {
                    message = "Server Not Respond";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                } else {
                    message = response.body().getStatus();
                    statusCode = response.body().getStatusCode();
                    data = response.body().getEnkripsi();
                    Log.d("hasil", "enkripsi :" + data );
                    if (statusCode.equals("00")) {
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
                                            controllerKontrak.tambahlaporan(data, URL, mSession,  new Callback<responseaddlaporan>(){

                                                @Override
                                                public void onResponse(Call<responseaddlaporan> call, Response<responseaddlaporan> response) {
//                                                    Log.d("statusrequest", "data: " + gson.toJson(response) );
                                                    String message, statusCode;
                                                    if (response.code() != 200)
                                                    {
                                                        message = "Server Not Respond";
                                                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                                    }
                                                    else {
                                                        message = response.body().getStatus();
                                                        statusCode = response.body().getStatusCode();
                                                        if (statusCode.equals("00")) {
                                                            // Berhasil ubah data
                                                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                                            Intent intent = new Intent(getApplicationContext(), ListKontrakActivity.class);
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                            startActivity(intent);
                                                            finish(); // call this to finish the current activityteguh
                                                        } else if (statusCode.equals("02")) {
                                                            // Token bermasalah (Expired, tidak sesuai, dll) -> Arahkan ke logout, login ulang
                                                            mSession.logoutUser();
                                                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<responseaddlaporan> call, Throwable t) {
                                                    Toast.makeText(getApplicationContext(), "Err : " + t.getMessage(), Toast.LENGTH_LONG).show();
                                                    Log.e("PROSES UPDATE", "onFailure: " + t.getMessage());
                                                }
                                            });
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
                    } else if (statusCode.equals("02")) {
                        mSession.logoutUser();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseAES> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Err : " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("PROSES UPDATE", "onFailure: " + t.getMessage());

            }
        });

    }

    private long convertdattolong (String tanggalDate) throws ParseException {

        SimpleDateFormat f = new SimpleDateFormat("dd-MMM-yyyy");
        // Convert date to Long (timestamp)
        Date d = f.parse(tanggalDate);
        long milliseconds = d.getTime();
        // Convert selesai

        return milliseconds;
    }

}
