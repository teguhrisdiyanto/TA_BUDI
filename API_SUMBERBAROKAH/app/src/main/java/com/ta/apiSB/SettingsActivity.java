package com.ta.apiSB;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ta.apiSB.Session.SessionManager;

public class SettingsActivity extends AppCompatActivity {

    SessionManager mSession;
    EditText etIpServer, etPortServer;
    Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mSession = new SessionManager(getApplicationContext());
        etIpServer = (EditText) findViewById(R.id.et_ipServer);
        etPortServer = (EditText) findViewById(R.id.et_portServer);
        btnUpdate = (Button) findViewById(R.id.btn_updateSettings);
        String ipServer, portServer;

        ipServer = mSession.getsIpServer();
        portServer = mSession.getsPortServer();


        etIpServer.setText(ipServer);
        etPortServer.setText(portServer);


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = etIpServer.getText().toString();
                String port = etPortServer.getText().toString();
                mSession.createSettings(ip, port);

                finish();
            }
        });
    }
}
