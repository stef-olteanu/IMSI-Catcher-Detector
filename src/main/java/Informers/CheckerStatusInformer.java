package Informers;


import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import CallBacks.CheckerCallBack;
import Managers.CheckerViewManager;
import Utils.MConstants;

public class CheckerStatusInformer implements CheckerCallBack {
    //region Private Members
    private CheckerViewManager mCheckerViewManager;
    //endregion

    //region Constructor
    public CheckerStatusInformer(View view){
        this.mCheckerViewManager = new CheckerViewManager(view);
    }
    //endregion

    //region Public Methods
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void OnCheckCompleted(String checkDone, String checkStatus) {
        this.mCheckerViewManager.CreateView(checkDone,checkStatus);

    }

    //endregion
}
