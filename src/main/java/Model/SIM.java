package Model;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import Controller.SIMController;

public class SIM {
    //region Constructor
    @RequiresApi(api = Build.VERSION_CODES.M)
    public SIM(Context mContext){
        this.mContext = mContext;
        mSimController = new SIMController(mContext);
        mCountry = mSimController.GetSIMCountry();
        mOperatorID = mSimController.GetOperatorID();
        mOperatorName = mSimController.GetOperatorName();
        mIMSI = mSimController.GetIMSI();
        mSerial = mSimController.GetSerial();

    }
    //endregion


    //region Private members
    private Context mContext;
    private String mCountry;
    private String mOperatorID;
    private String mOperatorName;
    private String mIMSI;
    private String mSerial;
    private SIMController mSimController;
    //endregion

    //region Public methods
    public String GetSIMCountry(){
        return mCountry;
    }

    public String GetOperatorID(){
        return mOperatorID;
    }

    public String GetOperatorName(){
        return mOperatorName;
    }

    public String GetIMSI(){
        return mIMSI;
    }

    public String GetSerial(){
        return mSerial;
    }
    //endregion
}
