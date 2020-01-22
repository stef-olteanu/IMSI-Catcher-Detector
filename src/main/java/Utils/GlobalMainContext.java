package Utils;

import android.content.Context;

public class GlobalMainContext {
    //region Private Members
    private static GlobalMainContext mInstance = null;
    private static Context mainContext;
    //endregion

    //region Constructor
    public GlobalMainContext(Context mContext){
        mainContext = mContext;
    }

    public GlobalMainContext(){

    }
    //endregion

    //region Public Methods
    public static GlobalMainContext getInstance(Context mainContext){
        if(mInstance == null){
            mInstance = new GlobalMainContext(mainContext);
        }
        return mInstance;
    }

    public static GlobalMainContext getInstance(){
        if(mInstance == null){
            mInstance = new GlobalMainContext();
        }
        return mInstance;
    }

    public static Context getMainContext(){
        return mainContext;
    }

    //endregion
}
