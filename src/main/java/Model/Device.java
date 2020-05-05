package Model;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import Controller.DispozitivController;

public class Device {
    //region Constructor
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Device(Context mContext){
        this.mDispozitivController = new DispozitivController(mContext);
        this.mPhoneType = mDispozitivController.GetPhoneType();
        this.mIMEI = mDispozitivController.GetIMEI();
        this.mLat = mDispozitivController.GetLatitude();
        this.mLon = mDispozitivController.GetLongitude();
        this.mManufacturer = mDispozitivController.GetDeviceManufacturer();
    }
    //endregion

    //region Private Members
    private String mPhoneType;
    private String mIMEI;
    private String mLat;
    private String mLon;
    private String mManufacturer;
    private DispozitivController mDispozitivController;
    //endregion

    //region Public Methods
    public String getPhoneType(){
        return this.mPhoneType;
    }

    public String getIMEI(){
        return this.mIMEI;
    }

    public String GetLatitude(){ return this.mLat; }

    public String GetLongitude(){ return this.mLon; }

    public String GetManufacturer() { return this.mManufacturer; }

    //endregion
}
