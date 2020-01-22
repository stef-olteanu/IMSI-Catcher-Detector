package Model;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import Controller.DispozitivController;

public class Dispozitiv {
    //region Constructor
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Dispozitiv(Context mContext){
        mDispozitivController = new DispozitivController(mContext);
        mPhoneType = mDispozitivController.GetPhoneType();
        mIMEI = mDispozitivController.GetIMEI();
        mLat = mDispozitivController.GetLatitude();
        mLon = mDispozitivController.GetLongitude();
    }
    //endregion

    //region Private Members
    private String mPhoneType;
    private String mIMEI;
    private String mLat;
    private String mLon;
    private DispozitivController mDispozitivController;
    //endregion

    //region Public Methods
    public String getPhoneType(){
        return mPhoneType;
    }

    public String getIMEI(){
        return mIMEI;
    }

    public String GetLatitude(){ return mLat; }

    public String GetLongitude(){ return mLon; }

    //endregion
}
