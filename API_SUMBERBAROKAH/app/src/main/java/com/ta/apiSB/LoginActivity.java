package com.ta.apiSB;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ta.apiSB.RESTAPI.ResponseServer;
import com.ta.apiSB.Session.SessionManager;
import com.ta.apiSB.StoreAPI.StoreApi;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin, btnSettings;
    SessionManager mSession;

    TextView tvText;
    String URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        mSession = new SessionManager(getApplicationContext());
        mSession.checkLogin();
        mSession.checkConfig();  // Mengecek apakah ip dan port sudah di setting ?
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnSettings = (Button) findViewById(R.id.btn_settings);


        URL = mSession.getsIpServer() + ":" + mSession.getsPortServer();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username, password, token ="xxxTOKENxxx";

                username = etUsername.getText().toString();
                password = etPassword.getText().toString();
                login(username, password);
//
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(in);
            }
        });
    }


    private void login(final String username, final String password)
    {
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
        final Map<String, String> isiData = new HashMap<String, String>();
        isiData.put("user_username", username);
        isiData.put("user_password", password);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("data", isiData);


        final Call<ResponseServer> result = service.doLogin(data);

        result.enqueue(new Callback<ResponseServer>() {
            @Override
            public void onResponse(Call<ResponseServer> call, Response<ResponseServer> response) {
                dialog.dismiss();
                if (response.code() != 200)
                {
                    Toast.makeText(LoginActivity.this, "Not Respon", Toast.LENGTH_SHORT).show();
//                    tvPesan.setText("ERROR RESPOND : " + response.code());
                }
                else {
                    String token="", message="", statusCode="", level="";
                    statusCode = response.body().getStatusCode();
                    message= response.body().getStatus();
                    String text = message;
                    if (statusCode.equals("00"))
                    {
                        token = response.body().getToken();
                        level = response.body().getLevel();
                        if (level.equalsIgnoreCase("0"))
                        {
                            text = "Anda tidak dapat Login melalui Mobile";
                        }
                        else
                        {
                            doLogin(username, password, token);
                            Log.d("TOKEN", token);
                        }
                    }
                    Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseServer> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(LoginActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void doLogin(String username, String password, String token)
    {
        mSession.createLoginSession(username, password, token);
        mSession.checkLogin();
    }

    @Override
    public void onResume(){
        super.onResume();
        URL = mSession.getsIpServer() + ":" + mSession.getsPortServer();
    }

}
