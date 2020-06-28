package com.ta.apiSB;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ta.apiSB.RESTAPI.Kontrak.ListDataItemTender;
import com.ta.apiSB.Session.SessionManager;

public class ProfileUser extends AppCompatActivity {

    SessionManager mSession;
    String URL;
//    DataKaryawan mProfileUser;

    TextView tvNamaKaryawan, tvTelpKaryawan, tvEmailKaryawan, tvAlamatKaryawan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);

        mSession = new SessionManager(getApplicationContext());
        URL = mSession.getsIpServer() + ":" + mSession.getsPortServer();

        final Bundle mData = getIntent().getExtras();
        String sjJSON = mData.getString("data");
        final ListDataItemTender mProfileUser = Utils.getGsonParser().fromJson(sjJSON, ListDataItemTender.class);

        tvNamaKaryawan = (TextView) findViewById(R.id.tv_namaKaryawan);
        tvTelpKaryawan = (TextView) findViewById(R.id.tv_telpKaryawan);
        tvEmailKaryawan = (TextView) findViewById(R.id.tv_emailKaryawan);
        tvAlamatKaryawan = (TextView) findViewById(R.id.tv_alamatKaryawan);

        setData(mProfileUser);
    }

    private void setData(ListDataItemTender mProfileUser)
    {
        String namaKaryawan, telpKaryawan, emailKaryawan, alamatKaryawan;

//        namaKaryawan = mProfileUser.getDataKaryawan().getKaryawanNama();
//        telpKaryawan = mProfileUser.getDataKaryawan().getKaryawanTelepon();
//        emailKaryawan = mProfileUser.getDataKaryawan().getKaryawanEmail();
//        alamatKaryawan = mProfileUser.getDataKaryawan().getKaryawanAlamat();

//        tvNamaKaryawan.setText(namaKaryawan);
//        tvTelpKaryawan.setText(telpKaryawan);
//        tvEmailKaryawan.setText(emailKaryawan);
//        tvAlamatKaryawan.setText(alamatKaryawan);
    }


//    @Override
//    public void onResume(){
//        super.onResume();
//        setData(DataKaryawan );
//    }
}
