package Controller;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import androidx.annotation.RequiresApi;

public class  SIMController {
    //region Constructor
    public SIMController(Context mContext) {
        this.mContext = mContext;
        mTelephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
    }
    //endregion


    //region Private Members
    private Context mContext;
    private TelephonyManager mTelephonyManager;
    //endregion


    //region Public Methods
    public String GetSIMCountry() {
        int simState = mTelephonyManager.getSimState();
        if(simState == mTelephonyManager.SIM_STATE_ABSENT)
            return "Cartela SIM nu este introdusa";
        String countryISO = mTelephonyManager.getSimCountryIso();
        countryISO = countryISO.substring(0,1).toUpperCase() + countryISO.substring(1);
        return countryISO;
    }

    public String GetOperatorID() {
        int simState = mTelephonyManager.getSimState();
        if(simState == mTelephonyManager.SIM_STATE_ABSENT)
            return "Cartela SIM nu este introdusa";
        String operatorID = mTelephonyManager.getSimOperator();
        return operatorID;
    }

    public String GetOperatorName() {
        int simState = mTelephonyManager.getSimState();
        if(simState == mTelephonyManager.SIM_STATE_ABSENT)
            return "Cartela SIM nu este introdusa";
        String operatorName = mTelephonyManager.getSimOperatorName();
        operatorName = operatorName.substring(0,1).toUpperCase()+operatorName.substring(1);
        return operatorName;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public String GetIMSI() {
        int simState = mTelephonyManager.getSimState();
        if(simState == mTelephonyManager.SIM_STATE_ABSENT)
            return "Cartela SIM nu este introdusa";
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
        String IMSI = mTelephonyManager.getSubscriberId();
        return IMSI;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public String GetSerial() {
        int simState = mTelephonyManager.getSimState();
        if(simState == mTelephonyManager.SIM_STATE_ABSENT)
            return "Cartela SIM nu este introdusa";
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
        String serial = mTelephonyManager.getSimSerialNumber();
        return serial;
    }

    //endregion
}
