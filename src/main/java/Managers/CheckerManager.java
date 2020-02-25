package Managers;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;

import com.example.licentav00.R;

import java.util.HashMap;

import CallBacks.CheckerCallBack;
import Checkers.CellSignalChecker;
import Checkers.PublicDBChecker;
import Responses.CheckerResponse;
import Responses.SignalCheckerResponse;
import Utils.GlobalMainContext;
import Utils.MConstants;

public class CheckerManager {
    //region Private Members
    private CheckerResponseManager mCheckerResponseManager;
    private HashMap<String,String> FinalCheckerResponse;
    private String mFinalSignalStatus;
    //endregion


    //region Constructor
    public CheckerManager() {
        this.mCheckerResponseManager = new CheckerResponseManager();
        this.FinalCheckerResponse = new HashMap<>();
    }
    //endregion

    //region Public Methods
    @RequiresApi(api = Build.VERSION_CODES.P)
    public void RunCheckers(CheckerCallBack checkerStatusInformer, View view) {
        performSignalCheck(checkerStatusInformer);
        performPublicDBCheck(checkerStatusInformer);
    }

    public CheckerResponseManager getmCheckerResponseManager() {
        return this.mCheckerResponseManager;
    }
    //endregion

    //region Private Methods
    @RequiresApi(api = Build.VERSION_CODES.P)
    public void performSignalCheck(CheckerCallBack checkerStatusInformer) {
        checkerStatusInformer.OnCheckStarted(MConstants.SIGNAL_CHECKER);
        CellSignalChecker cellSignalChecker = new CellSignalChecker();
        CheckerResponse signalCheckerResponse = cellSignalChecker.checkSignalStrength();
        mCheckerResponseManager.setmSignalCheckerResponse((SignalCheckerResponse)signalCheckerResponse);
        mFinalSignalStatus =  mCheckerResponseManager.GetFinalSignalResponse();
        FinalCheckerResponse.put(MConstants.SIGNAL_CHECKER, mFinalSignalStatus);
        checkerStatusInformer.OnCheckCompleted(MConstants.SIGNAL_CHECKER, mFinalSignalStatus);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void performPublicDBCheck(CheckerCallBack checkerStatusInformer) {
        checkerStatusInformer.OnCheckStarted(MConstants.PUBLIC_DB_CHECKER);
        PublicDBChecker publicDBChecker = new PublicDBChecker();
        CheckerResponse publicDBResponse = publicDBChecker.checkPublicDB();
        mCheckerResponseManager.setmPublicDbCheckerResponse(publicDBResponse);
        checkerStatusInformer.OnCheckCompleted(MConstants.PUBLIC_DB_CHECKER, publicDBResponse.getmCheckingStatus());
    }
    //endregion
}
