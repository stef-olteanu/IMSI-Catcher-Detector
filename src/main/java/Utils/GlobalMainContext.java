package Utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import java.util.Locale;

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

    public static void setAppLocale(String localeCode) {
        Resources res = mainContext.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLocale(new Locale(localeCode.toLowerCase()));
        } else {
            conf.locale = new Locale(localeCode.toLowerCase());
        }
        res.updateConfiguration(conf,dm);

    }

    //endregion
}
