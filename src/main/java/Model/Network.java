package Model;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import Controller.NetworkController;

public class Network {

    //region Constructor
    @RequiresApi(api = Build.VERSION_CODES.M)
    public Network(Context mContext){
        networkController = new NetworkController(mContext);
        providerName = networkController.GetProviderName();
        providerCode = networkController.GetProviderCode();
        type = networkController.GetProviderType();
        CID = networkController.GetProviderCID();
        LAC = networkController.GetProviderLAC();
    }

    //endregion

    //region Private variables
    private String providerName;
    private String providerCode;
    private String type;
    private String CID;
    private String LAC;

    private NetworkController networkController;

    //endregion


    //region Public Methods
    public String GetProviderName(){
        return providerName;
    }

    public String GetProviderCode(){
        return providerCode;
    }

    public String GetProviderType(){
        return type;
    }

    public String GetProviderCID(){
        return CID;
    }

    public  String GetProviderLAC(){
        return LAC;
    }
    //endregion


}
