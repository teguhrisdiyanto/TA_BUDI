package com.ta.apiSB.Session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.ta.apiSB.LoginActivity;
import com.ta.apiSB.MainActivity;
import com.ta.apiSB.SettingsActivity;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "API_BCA";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String IS_SETTING = "isSetting";

    // make variable public to access from outside
    public static final String S_IP_SERVER = "IPSERVER";
    public static final String S_PORT_SERVER = "PORTSERVER";
    public static final String S_USER_NAME = "USERNAME";
    public static final String S_PASSWORD = "PASSWORD";
    public static final String S_TOKEN = "TOKEN";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.apply();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String username, String password, String token){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        // Storing name in pref
        editor.putString(S_USER_NAME, username);
        editor.putString(S_PASSWORD, password);
        editor.putString(S_TOKEN, token);
        // commit changes
        editor.apply();
    }

    public String getUsername()
    {
        return pref.getString(S_USER_NAME, null);
    }
    public String getPassword()
    {
        return pref.getString(S_PASSWORD, null);
    }
    public String getToken()
    {
        return pref.getString(S_TOKEN, null);
    }
    public String getsPortServer()
    {
        return pref.getString(S_PORT_SERVER, null);
    }
    public String getsIpServer()
    {
        return pref.getString(S_IP_SERVER, null);
    }

    public void createSettings(String IpServer, String portServer){
        editor.putString(S_IP_SERVER, IpServer);
        editor.putString(S_PORT_SERVER, portServer);
        editor.putBoolean(IS_SETTING, true);
        editor.apply();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(isLoggedIn() == true){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Staring Login Activity
            _context.startActivity(i);
        }
    }

    public void checkConfig(){
        // Check login status
        if(sudahSetting() == false){

            Toast.makeText(_context, "Harap konfigurasi IP dan Port terlebih dahulu", Toast.LENGTH_SHORT).show();
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, SettingsActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Staring Login Activity
            _context.startActivity(i);
        }
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.remove(S_PASSWORD);
        editor.remove(S_USER_NAME);
        editor.remove(S_TOKEN);
        editor.remove(IS_LOGIN);
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
    public boolean sudahSetting(){
        return pref.getBoolean(IS_SETTING, false);
    }
}
