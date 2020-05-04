package Utils;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.annotation.RequiresApi;

public class VibratorHelper {
    //region Members Declaration
    Vibrator mVibrator;
    //endregion

    //region Constructor
    public VibratorHelper() {
        this.mVibrator = (Vibrator) GlobalMainContext.getMainContext().getSystemService(Context.VIBRATOR_SERVICE);
    }
    //endregion


    //region Methods
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Vibrate(String response){
        if(response.equals(MConstants.OVERALL_FAILED_RO))
            this.mVibrator.vibrate(VibrationEffect.createOneShot(1000,VibrationEffect.DEFAULT_AMPLITUDE));
    }
    //endregion
}
