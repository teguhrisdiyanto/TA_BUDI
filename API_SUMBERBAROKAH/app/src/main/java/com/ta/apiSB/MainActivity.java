package com.ta.apiSB;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ta.apiSB.DataTransaksi.Kontrak.Activity_Listtenderaddlaporan;
import com.ta.apiSB.DataTransaksi.Kontrak.ListKontrakActivity;
import com.ta.apiSB.Session.SessionManager;

public class MainActivity extends AppCompatActivity {

    SessionManager mSession;
    TextView tvText;
    Button ivAbout, btnSettings, btnSuratJalan;
    //SwipeRefreshLayout pullToRefresh;
    ImageView menu1, menu2, menu3, menu4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mSession = new SessionManager(getApplicationContext());
        tvText = (TextView) findViewById(R.id.nameuser);
//        btnLogout = (Button) findViewById(R.id.btn_logout);
//        btnSuratJalan = (Button) findViewById(R.id.btn_suratjalan);
//        btnSettings = (Button) findViewById(R.id.btn_settings);

        menu1 =(ImageView) findViewById(R.id.ImageView1);
        menu2 =(ImageView) findViewById(R.id.ImageView2);
        menu3 =(ImageView) findViewById(R.id.ImageView3);
        menu4 =(ImageView) findViewById(R.id.ImageView4);
        ivAbout = (Button) findViewById(R.id.btnguide);

//        pullToRefresh = (SwipeRefreshLayout)findViewById(R.id.pullToRefresh);
//
//        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                tampilDataSession();
//                pullToRefresh.setRefreshing(false);
//            }
//        });

        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), Activity_Listtenderaddlaporan.class);
                startActivity(in);
            }
        });

        menu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSession.logoutUser();
            }
        });

        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ListKontrakActivity.class);
                startActivity(in);
            }
        });

        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(in);
            }
        });

        ivAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), PackageAct.class);
                startActivity(in);
            }
        });
        tampilDataSession();
    }

    private void tampilDataSession()
    {
        Log.d("TOKEN", mSession.getToken());
        String text = "";
        text +=  mSession.getUsername();
//        text += "\nToken : " + mSession.getToken();
       // text += "\nIp Server : " + mSession.getsIpServer();
       // text += "\nPort Server : " + mSession.getsPortServer();

        tvText.setText(text);

    }

    @Override
    public void onResume(){
        super.onResume();
        tampilDataSession();
    }

}
