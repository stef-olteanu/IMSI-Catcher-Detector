package com.example.licentav00;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;

import Listeners.CellLocationChangeListener;
import Utils.GlobalMainContext;
import Utils.MConstants;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    View view;
    @TargetApi(Build.VERSION_CODES.P)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GlobalMainContext.getInstance(MainActivity.this);
        SharedPreferences sharedPreferences = getSharedPreferences("AppLanguage",MainActivity.this.MODE_PRIVATE);
        String lang = sharedPreferences.getString("Language", MConstants.AppLanguages.RO_LANG);
        GlobalMainContext.setAppLocale(lang);



        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();


        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 99);
        }
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 99);


        }

        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE}, 2);
        }


        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);


        view = getLayoutInflater().inflate(R.layout.navigation_header,null);
        ProgressBar loadingProgressBar = view.findViewById(R.id.fragmentLoader);
        loadingProgressBar.setVisibility(View.INVISIBLE);

        navigationView.addHeaderView(view);


        /**
         * Instantiate the listener
         */
        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        CellLocationChangeListener mListener = null;
        try {
            mListener = new CellLocationChangeListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mTelephonyManager.listen(mListener, PhoneStateListener.LISTEN_CELL_LOCATION);
    }



    /**
     *
     * @param menuItem Indicates the menu item the user clicked on.
     * @return
     * The fragment must wait 0.1 sec before showing in order to let the progress bar appear;
     * The progress bar waits 1 sec before becoming invisible because the transition was too fast and it didn't show at all.
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {
        ProgressBar loadingProgressBar = view.findViewById(R.id.fragmentLoader);
        loadingProgressBar.setVisibility(View.VISIBLE);

        final Context mainContext = this;

        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            public void run() {

                switch (menuItem.getItemId()) {
                    case R.id.nav_sim_details:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentDetaliiSIM(mainContext)).commit();
                        break;
                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();
                        break;
                    case R.id.nav_cell_info:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentCellDetails(mainContext)).commit();
                        break;
                    case R.id.nav_map:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentCellMap()).commit();
                        break;
                    case R.id.nav_verify:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentCellChecker()).commit();
                        break;
                    case R.id.nav_tutorial:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentOptions()).commit();
                        break;

                }
                drawer.closeDrawer(GravityCompat.START);
            }
        }, 100);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                ProgressBar loadingProgressBar = view.findViewById(R.id.fragmentLoader);
                loadingProgressBar.setVisibility(View.INVISIBLE);
            }
        }, 1000);

        return true;
    }
}
