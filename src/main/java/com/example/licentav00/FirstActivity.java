package com.example.licentav00;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import Listeners.CellLocationChangeListener;
import Utils.GlobalMainContext;
import Utils.MConstants;

public class FirstActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        GlobalMainContext.getInstance(FirstActivity.this);
        SharedPreferences sharedPreferences = getSharedPreferences("AppLanguage",this.MODE_PRIVATE);
        String lang = sharedPreferences.getString("Language", MConstants.AppLanguages.RO_LANG);
        GlobalMainContext.setAppLocale(lang);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);

    }
}
