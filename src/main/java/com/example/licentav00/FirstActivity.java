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
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import CallBacks.ConnectivityCheckCallBack;
import Checkers.ConectivityChecker;
import Controller.NetworkController;
import Listeners.CellLocationChangeListener;
import Responses.CheckerResponse;
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
        TextView loading = findViewById(R.id.loadingTextView);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        ConectivityChecker conectivityChecker = new ConectivityChecker();
        conectivityChecker.checkForInternetConnection(checkerResponse -> {
                    if (checkerResponse.getmCheckingStatus().equals(MConstants.TEST_FAILED_RO)) {
                        loading.setText("Nu exista conexiune la internet! Aplicatia se va inchide!");
                        progressBar.setVisibility(View.INVISIBLE);
                        Handler handler = new Handler(getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                System.exit(0);
                            }
                        }, 3500);
                    } else {
                        NetworkController networkController = new NetworkController(this);
                        if (!networkController.GetProviderType().equals("GSM")) {
                            loading.setText("Tipul retelei nu este GSM! Aplicatia se va inchide!");
                            progressBar.setVisibility(View.INVISIBLE);
                            Handler handler = new Handler(getMainLooper());
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    System.exit(0);
                                }
                            }, 3500);
                        } else {
                            Handler handler = new Handler(getMainLooper());
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            },3000);
                        }

                    }
                });

    }
}
