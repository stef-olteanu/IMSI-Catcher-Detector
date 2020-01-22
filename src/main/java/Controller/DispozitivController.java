package Controller;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import android.telephony.TelephonyManager;

import androidx.annotation.RequiresApi;

import Listeners.DeviceLocationListener;

public class DispozitivController {
    //region Constructor
    @RequiresApi(api = Build.VERSION_CODES.N)
    public DispozitivController(Context mContext) {
        this.mContext = mContext;
        mTelephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        mDeviceLocationListener = new DeviceLocationListener(mContext);
        mLat = mDeviceLocationListener.getmLat();
        mLon = mDeviceLocationListener.getmLon();
    }
    //endregion

    //region Private Members
    private Context mContext;
    private TelephonyManager mTelephonyManager;
    private DeviceLocationListener mDeviceLocationListener;
    private double mLat;
    private double mLon;
    //endregion

    //region Public Methods
    public String GetPhoneType() {
        int phoneType = mTelephonyManager.getPhoneType();
        String typePhone = "Necunoscut";
        switch (phoneType) {
            case 7:
                typePhone = "1xRTT";
                break;
            case 4:
                typePhone = "CDMA";
                break;
            case 2:
                typePhone = "EDGE";
                break;
            case 14:
                typePhone = "eHRPD";
                break;
            case 5:
                typePhone = "EVDO rev. 0";
                break;
            case 6:
                typePhone = "EVDO rev. A";
                break;
            case 12:
                typePhone = "EVDO rev. B";
                break;
            case 1:
                typePhone = "GPRS";
                break;
            case 8:
                typePhone = "HSDPA";
                break;
            case 10:
                typePhone = "HSPA";
                break;
            case 15:
                typePhone = "HSPA+";
                break;
            case 9:
                typePhone = "HSUPA";
                break;
            case 11:
                typePhone = "iDen";
                break;
            case 13:
                typePhone = "LTE";
                break;
            case 3:
                typePhone = "UMTS";
                break;
            case 0:
                typePhone = "Necunoscut";
                break;
        }
        return typePhone;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String GetIMEI() {
        if (mContext.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return "Nothing";
        }
        String IMEI = mTelephonyManager.getImei();
        return IMEI;
    }

    public String GetLatitude(){
        return Double.toString(mLat);
    }

    public String GetLongitude(){
        return Double.toString(mLon);
    }



    //endregion
}
