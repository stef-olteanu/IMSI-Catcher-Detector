package Controller;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.CellIdentityGsm;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.TelephonyManager;

import androidx.annotation.RequiresApi;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import DatabaseLogic.IMSICatcherDetectorDatabase;
import Model.Cell;
import Model.Dispozitiv;
import Utils.AsyncGetter;
import Utils.Requester;

public class CellController {
    //region Constructor
    @TargetApi(Build.VERSION_CODES.P)
    @RequiresApi(api = Build.VERSION_CODES.P)
    public CellController(Context mContext) throws IOException, InterruptedException {
        mainContext = mContext;
        this.mDatabase = new IMSICatcherDetectorDatabase(this.mainContext);
        mTelephonyManager = (TelephonyManager) mainContext.getSystemService(Context.TELEPHONY_SERVICE);
        if (mainContext.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mCellInfoList = (List<CellInfo>) mTelephonyManager.getAllCellInfo();
            if (!mCellInfoList.isEmpty()) {
                mCellInfoGsm = (CellInfoGsm) mCellInfoList.get(0);
                mCellIdentityGsm = (CellIdentityGsm) mCellInfoGsm.getCellIdentity();
                mCellSignalStrengthGsm = (CellSignalStrengthGsm) mCellInfoGsm.getCellSignalStrength();
            }
        }
        //region Request Cell Coordinates
        mRequester = new Requester(mainContext);
        mRequester.PostToLocation("https://us2.unwiredlabs.com/v2/process.php",
                "e4509bb63be6b3", "gsm", this.GetMcc(), this.GetMnc(), this.GetLac(), this.GetCid(), "1");
        AsyncGetter asyncGetter = AsyncGetter.getInstance();
        mCellLocationJson = asyncGetter.getJsonObject();
        //endregion

    }
    //endregion

    //region Private Members
    private Context mainContext;
    private TelephonyManager mTelephonyManager;
    private List<CellInfo> mCellInfoList;
    private CellInfoGsm mCellInfoGsm;
    private CellIdentityGsm mCellIdentityGsm;
    private CellSignalStrengthGsm mCellSignalStrengthGsm;
    private Requester mRequester;
    private JsonObject mCellLocationJson;
    private IMSICatcherDetectorDatabase mDatabase;
    //endregion


    //region Public Methods
    //region Cell Identity Information
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String GetArfcn() {
        int arfcn = mCellIdentityGsm.getArfcn();
        return Integer.toString(arfcn);
    }

    @TargetApi(Build.VERSION_CODES.N)
    public String GetBsic() {
        int bsic = mCellIdentityGsm.getBsic();
        return Integer.toString(bsic);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public String GetCid() {
        int cid = mCellIdentityGsm.getCid();
        return Integer.toString(cid);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public String GetLac() {
        int lac = mCellIdentityGsm.getLac();
        return Integer.toString(lac);

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public String GetMcc() {
        int mcc = mCellIdentityGsm.getMcc();
        return Integer.toString(mcc);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public String GetMnc() {
        int mnc = mCellIdentityGsm.getMnc();
        return Integer.toString(mnc);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public String getNetworkOperator() {
        String networkOperator = GetMcc() + GetMnc();
        return networkOperator;
    }
    //endregion

    //region Signal Strength Information
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public String GetAsuLevel() {
        int asu = mCellSignalStrengthGsm.getAsuLevel();
        return Integer.toString(asu);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public String GetSignalDbm(){
        int dbm = mCellSignalStrengthGsm.getDbm();
        return Integer.toString(dbm);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public String GetSignalLevel(){
        int level = mCellSignalStrengthGsm.getLevel();
        return Integer.toString(level);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String GetTimingAdvance(){
        int timing = mCellSignalStrengthGsm.getTimingAdvance();
        return Integer.toString(timing);
    }

    //endregion

    //region Cell Location
    public String GetCellLat(){
        String cellLat = "Cell not found";
        if(mCellLocationJson.has("lat"))
            cellLat = mCellLocationJson.get("lat").toString();
        return cellLat;
    }
    public String GetCellLong(){
        String cellLong = "Cell not found";
        if(mCellLocationJson.has("lon"))
            cellLong = mCellLocationJson.get("lon").toString();
        return cellLong;
    }
    public String GetCellAddress(){
        String cellAddress = "Cell not found";
        if(mCellLocationJson.has("address"))
            cellAddress = mCellLocationJson.get("address").toString();
        return cellAddress;
    }
    //endregion

    //region CellNeighbouring
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public List<Cell> GetAllCellNeighbours() throws IOException, InterruptedException {
        List<Cell> cellNeighbours = new ArrayList<>();
        for(int count = 1; count < mCellInfoList.size(); count++){
            CellInfoGsm cellInfoGsm = (CellInfoGsm) mCellInfoList.get(count);
            CellIdentityGsm cellIdentityGsm = (CellIdentityGsm) cellInfoGsm.getCellIdentity();
            Cell neighbourCell = new Cell();

            mRequester.PostToLocation("https://eu1.unwiredlabs.com/v2/process.php",
                    "e4509bb63be6b3", "gsm",
                    Integer.toString(cellIdentityGsm.getMcc()),
                    Integer.toString(cellIdentityGsm.getMnc()),
                    Integer.toString(cellIdentityGsm.getLac()),
                    Integer.toString(cellIdentityGsm.getCid()),
                    "1");
            AsyncGetter asyncGetter = AsyncGetter.getInstance();
            JsonObject cellLocationJson = asyncGetter.getJsonObject();

            if(cellLocationJson.has("lat")){
                neighbourCell.setmCellLat(cellLocationJson.get("lat").toString());
                neighbourCell.setmCellLong(cellLocationJson.get("lon").toString());
                neighbourCell.setmCellStatus("GOOD");
            }else{
                Dispozitiv dispozitiv = new Dispozitiv(mainContext);
                neighbourCell.setmCellLat(dispozitiv.GetLatitude());
                neighbourCell.setmCellLong(dispozitiv.GetLongitude());
                neighbourCell.setmCellStatus("WARNING");
            }
            neighbourCell.setmCid(Integer.toString(cellIdentityGsm.getCid()));
            neighbourCell.setmLac(Integer.toString(cellIdentityGsm.getLac()));
            neighbourCell.setmMcc(Integer.toString(cellIdentityGsm.getMcc()));
            neighbourCell.setmMnc(Integer.toString(cellIdentityGsm.getMnc()));
            cellNeighbours.add(neighbourCell);
        }
        return cellNeighbours;
    }
    //endregion
    //region Database Interraction
    public void addSignalStrengthToDB(int cellId, int signalStrength) {
        this.mDatabase.AddSignalStrength(cellId,signalStrength);
    }
    //endregion


}
