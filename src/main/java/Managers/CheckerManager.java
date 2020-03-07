package Managers;

import android.os.Build;
import android.os.Handler;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.util.HashMap;

import CallBacks.CheckerCallBack;
import CallBacks.InternalDatabaseCallBack;
import Checkers.CellConsistencyChecker;
import Checkers.CellSignalChecker;
import Checkers.InternalDBChecker;
import Checkers.NeighbourListChecker;
import Checkers.OverallChecker;
import Checkers.PublicDBChecker;
import Responses.CellConsistencyCheckerResponse;
import Responses.CheckerResponse;
import Responses.InternalDBCheckerResponse;
import Responses.NeighbourListCheckerResponse;
import Responses.OverallResponse;
import Responses.SignalCheckerResponse;
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
    public void RunCheckers() {
                performSignalCheck();
                performPublicDBCheck();
                performInteralDBCheck();
                performNeighbourListCheck();
                performCellConsistencyCheck();
                //performOverallCheck();
    }

    public CheckerResponseManager getmCheckerResponseManager() {
        return this.mCheckerResponseManager;
    }

    //endregion

    //region Private Methods
    @RequiresApi(api = Build.VERSION_CODES.P)
    public void performSignalCheck() {
                CellSignalChecker cellSignalChecker = new CellSignalChecker();
                CheckerResponse signalCheckerResponse = cellSignalChecker.checkSignalStrength();
                mCheckerResponseManager.setmSignalCheckerResponse(signalCheckerResponse);
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
                internalDBChecker.checkInternalDatabase(response -> {
                    InternalDBCheckerResponse internalDBCheckerResponse = new InternalDBCheckerResponse(response);
                    mCheckerResponseManager.setmInternalDBCheckerResponse(internalDBCheckerResponse);
                });


    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void performNeighbourListCheck() {
        NeighbourListChecker neighbourListChecker = new NeighbourListChecker();
        NeighbourListCheckerResponse neighbourListCheckerResponse =  neighbourListChecker.CheckNeighbourList();
        mCheckerResponseManager.setmNeighbourListCheckerResponse(neighbourListCheckerResponse);

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void performCellConsistencyCheck() {
        CellConsistencyChecker cellConsistencyChecker = new CellConsistencyChecker();
        cellConsistencyChecker.CheckCellConsistency(new InternalDatabaseCallBack() {
            @Override
            public void OnReturnResponseCallback(String response) {
                CellConsistencyCheckerResponse cellConsistencyCheckerResponse = new CellConsistencyCheckerResponse(response);
                mCheckerResponseManager.setmCellConsistencyCheckerResponse(cellConsistencyCheckerResponse);
            }
        });
    }

    public void performOverallCheck() {
        OverallChecker overallChecker = new OverallChecker(mCheckerResponseManager);
        OverallResponse overallResponse = overallChecker.OverallCheck();
        if(overallResponse.getmCheckingStatus().equals(MConstants.TEST_PASSED_RO))
            overallResponse.setmCheckingStatus(MConstants.OVERALL_PASSED_RO);
        else
            overallResponse.setmCheckingStatus(MConstants.OVERALL_FAILED_RO);
        mCheckerResponseManager.setmOverallResponse(overallResponse);
    }
    //endregion
}
