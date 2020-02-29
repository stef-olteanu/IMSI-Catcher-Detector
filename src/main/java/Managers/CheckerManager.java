package Managers;

import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.util.HashMap;

import CallBacks.CheckerCallBack;
import CallBacks.InternalDatabaseCallBack;
import Checkers.CellSignalChecker;
import Checkers.InternalDBChecker;
import Checkers.NeighbourListChecker;
import Checkers.PublicDBChecker;
import Responses.CheckerResponse;
import Responses.InternalDBCheckerResponse;
import Responses.NeighbourListCheckerResponse;
import Responses.SignalCheckerResponse;
import Utils.MConstants;

public class CheckerManager {
    //region Private Members
    private CheckerResponseManager mCheckerResponseManager;
    private HashMap<String,String> FinalCheckerResponse;
    private String mFinalSignalStatus;
    private boolean mFinish;
    //endregion


    //region Constructor
    public CheckerManager() {
        this.mCheckerResponseManager = new CheckerResponseManager();
        this.FinalCheckerResponse = new HashMap<>();
        this.mFinish = false;
    }
    //endregion

    //region Public Methods
    @RequiresApi(api = Build.VERSION_CODES.P)
    public void RunCheckers() {
                performSignalCheck();
                performPublicDBCheck();
                performInteralDBCheck();
                performNeighbourListCheck();
                this.mFinish = true;
    }

    public CheckerResponseManager getmCheckerResponseManager() {
        return this.mCheckerResponseManager;
    }

    public boolean ismFinish() {
        return mFinish;
    }
    //endregion

    //region Private Methods
    @RequiresApi(api = Build.VERSION_CODES.P)
    public void performSignalCheck() {
                CellSignalChecker cellSignalChecker = new CellSignalChecker();
                CheckerResponse signalCheckerResponse = cellSignalChecker.checkSignalStrength();
                mCheckerResponseManager.setmSignalCheckerResponse((SignalCheckerResponse)signalCheckerResponse);
                mFinalSignalStatus =  mCheckerResponseManager.GetFinalSignalResponse();
                FinalCheckerResponse.put(MConstants.SIGNAL_CHECKER, mFinalSignalStatus);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void performPublicDBCheck() {
                PublicDBChecker publicDBChecker = new PublicDBChecker();
                CheckerResponse publicDBResponse = publicDBChecker.checkPublicDB();
                mCheckerResponseManager.setmPublicDbCheckerResponse(publicDBResponse);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void performInteralDBCheck() {
                InternalDBChecker internalDBChecker = new InternalDBChecker();
                internalDBChecker.checkInternalDatabase(new InternalDatabaseCallBack() {
                    @Override
                    public void OnReturnResponseCallback(String response) {
                        InternalDBCheckerResponse internalDBCheckerResponse = new InternalDBCheckerResponse(response);
                        mCheckerResponseManager.setmInternalDBCheckerResponse(internalDBCheckerResponse);
                    }
                });


    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void performNeighbourListCheck() {
        NeighbourListChecker neighbourListChecker = new NeighbourListChecker();
        NeighbourListCheckerResponse neighbourListCheckerResponse =  neighbourListChecker.CheckNeighbourList();
        mCheckerResponseManager.setmNeighbourListCheckerResponse(neighbourListCheckerResponse);

    }
    //endregion
}
