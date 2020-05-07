package Checkers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Handler;

import CallBacks.CheckerCallBack;
import CallBacks.ConnectivityCheckCallBack;
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
    public void checkForInternetConnection(ConnectivityCheckCallBack connectivityCheckCallBack) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //mWifiManager.setWifiEnabled(false);
                if(isOnline()) {
                    //mWifiManager.setWifiEnabled(true);
                    connectivityCheckCallBack.onCheckCompleted(new CheckerResponse(MConstants.TEST_PASSED_RO));
                }
                else {
                    mWifiManager.setWifiEnabled(true);
                    connectivityCheckCallBack.onCheckCompleted(new CheckerResponse(MConstants.TEST_FAILED_RO));
                }

            }
        });
        thread.start();
    }

    public boolean isOnline() {
        try {
            Log.i("CONNECTIVITY:","DEVICE IS ONLINE");
            return (Runtime.getRuntime().exec("ping -c 1 -w 1 google.com").waitFor() == 0);
        } catch (IOException e) {
            Log.i("CONNECTIVITY:","DEVICE IS OFFLINE");
            System.out.println(e);
            return false;
        } catch (InterruptedException e) {
            Log.i("CONNECTIVITY:","DEVICE IS OFFLINE");
            e.printStackTrace();
            return false;
        }
    }
    //endregion
}
