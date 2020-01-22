package Controller;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;


import androidx.annotation.RequiresApi;


import java.util.List;




public class NetworkController {

    //region Constructor
    public NetworkController(Context context) {
        contextController = context;
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }
    //endregion

    //region Private Variables
    private TelephonyManager telephonyManager;
    private Context contextController;
    //endregion

    //region Public Methods
    public String GetProviderName() {
        String netowrkOperator = telephonyManager.getNetworkOperatorName();
        netowrkOperator = netowrkOperator.substring(0, 1).toUpperCase() + netowrkOperator.substring(1);

        return netowrkOperator;
    }

    public String GetProviderCode() {
        String providerCode = telephonyManager.getNetworkOperator();

        return providerCode;
    }

    public String GetProviderType() {
        int providerType = telephonyManager.getNetworkType();
        String typeProvider = "Necunoscut";

        switch (providerType) {
            case 7:
                typeProvider = "1xRTT";
                break;
            case 4:
                typeProvider = "CDMA";
                break;
            case 2:
                typeProvider = "EDGE";
                break;
            case 14:
                typeProvider = "eHRPD";
                break;
            case 5:
                typeProvider = "EVDO rev. 0";
                break;
            case 6:
                typeProvider = "EVDO rev. A";
                break;
            case 12:
                typeProvider = "EVDO rev. B";
                break;
            case 1:
                typeProvider = "GPRS";
                break;
            case 8:
                typeProvider = "HSDPA";
                break;
            case 10:
                typeProvider = "HSPA";
                break;
            case 15:
                typeProvider = "HSPA+";
                break;
            case 9:
                typeProvider = "HSUPA";
                break;
            case 11:
                typeProvider = "iDen";
                break;
            case 13:
                typeProvider = "LTE";
                break;
            case 3:
                typeProvider = "UMTS";
                break;
            case 0:
                typeProvider = "Necunoscut";
                break;
        }
        return typeProvider;
    }


    @TargetApi(Build.VERSION_CODES.M)
    public String GetProviderCID() {
        int simState = telephonyManager.getSimState();
        if(simState == telephonyManager.SIM_STATE_ABSENT)
            return "Cartela SIM nu este introdusa";
        if (contextController.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return "Nothing";
        }
        List<CellInfo> cellInfoList = (List<CellInfo>) telephonyManager.getAllCellInfo();
        if(cellInfoList.isEmpty())
            return "Nothing";
        CellInfoGsm cellInfoGsm = (CellInfoGsm) cellInfoList.get(0);
        CellIdentityGsm providerIdentity = cellInfoGsm.getCellIdentity();
        int providerCID = providerIdentity.getCid();

        return Integer.toString(providerCID);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public String GetProviderLAC(){
        int simState = telephonyManager.getSimState();
        if(simState == telephonyManager.SIM_STATE_ABSENT)
            return "Cartela SIM nu este introdusa";
        if (contextController.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return "Nothing";
        }
        List<CellInfo> cellInfoList = (List<CellInfo>) telephonyManager.getAllCellInfo();
        if(cellInfoList.isEmpty())
            return "Nothing";
        CellInfoGsm cellInfoGsm = (CellInfoGsm) cellInfoList.get(0);
        int providerLAC = cellInfoGsm.getCellIdentity().getLac();

        return Integer.toString(providerLAC);
    }
    //endregion

}
