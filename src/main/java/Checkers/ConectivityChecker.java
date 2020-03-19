package Checkers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import java.io.IOException;

import Responses.CheckerResponse;
import Utils.GlobalMainContext;
import Utils.MConstants;

public class ConectivityChecker {
    //region Members Declaration
    WifiManager mWifiManager;
    TelephonyManager mTelephonyManager;
    //endregion

    //region Constructor
    public ConectivityChecker() {
        this.mWifiManager = (WifiManager) GlobalMainContext.getMainContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        this.mTelephonyManager =  (TelephonyManager) GlobalMainContext.getMainContext().getSystemService(Context.TELEPHONY_SERVICE);
    }
    //endregion


    //region Public Methods
    public CheckerResponse checkForInternetConnection() {
        mWifiManager.setWifiEnabled(false);
        boolean isConnected;
        if(isOnline())
            isConnected = true;
        else
            isConnected = false;

        mWifiManager.setWifiEnabled(true);
        if(isConnected)
            return new CheckerResponse(MConstants.TEST_PASSED_RO);
        return new CheckerResponse(MConstants.TEST_FAILED_RO);
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }
    //endregion
}
