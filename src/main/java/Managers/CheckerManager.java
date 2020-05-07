package Managers;

import android.os.Build;
import android.os.Handler;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.HashMap;

import CallBacks.CheckerCallBack;
import CallBacks.ConnectivityCheckCallBack;
import CallBacks.InternalDatabaseCallBack;
import Checkers.CellConsistencyChecker;
import Checkers.CellSignalChecker;
import Checkers.ConectivityChecker;
import Checkers.InternalDBChecker;
import Checkers.NeighbourListChecker;
import Checkers.OverallChecker;
import Checkers.PublicDBChecker;
import Model.Cell;
import Responses.CellConsistencyCheckerResponse;
import Responses.CheckerResponse;
import Responses.InternalDBCheckerResponse;
import Responses.NeighbourListCheckerResponse;
import Responses.OverallResponse;
import Responses.SignalCheckerResponse;
import Utils.GlobalMainContext;
import Utils.MConstants;

public class CheckerManager {
    //region Private Members
    private CheckerResponseManager mCheckerResponseManager;
    private HashMap<String,String> FinalCheckerResponse;
    private String mFinalSignalStatus;
    private Cell mCell;
    //endregion


    //region Constructor
    @RequiresApi(api = Build.VERSION_CODES.P)
    public CheckerManager() {
        this.mCheckerResponseManager = new CheckerResponseManager();
        this.FinalCheckerResponse = new HashMap<>();
    }
    //endregion

    //region Public Methods
    @RequiresApi(api = Build.VERSION_CODES.P)
    public void RunCheckers() {
                try {
                    this.mCell = new Cell(GlobalMainContext.getMainContext());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                performSignalCheck();
                performPublicDBCheck();
                performInteralDBCheck();
                performNeighbourListCheck();
                performCellConsistencyCheck();
                //performConectivityCheck();
                //performOverallCheck();
    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    public void RunCheckers(Cell cell) {

        this.mCell = cell;

        performSignalCheck();
        performPublicDBCheck();
        performInteralDBCheck();
        performNeighbourListCheck();
        performCellConsistencyCheck();
        //performConectivityCheck();
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
                CheckerResponse signalCheckerResponse = cellSignalChecker.checkSignalStrength(this.mCell);
                mCheckerResponseManager.setmSignalCheckerResponse(signalCheckerResponse);
                mFinalSignalStatus =  mCheckerResponseManager.GetFinalSignalResponse();
                FinalCheckerResponse.put(MConstants.SIGNAL_CHECKER, mFinalSignalStatus);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void performPublicDBCheck() {
                PublicDBChecker publicDBChecker = new PublicDBChecker();
                CheckerResponse publicDBResponse = publicDBChecker.checkPublicDB(this.mCell);
                mCheckerResponseManager.setmPublicDbCheckerResponse(publicDBResponse);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void performInteralDBCheck() {
                InternalDBChecker internalDBChecker = new InternalDBChecker();
                internalDBChecker.checkInternalDatabase(this.mCell, response -> {
                    InternalDBCheckerResponse internalDBCheckerResponse = new InternalDBCheckerResponse(response);
                    mCheckerResponseManager.setmInternalDBCheckerResponse(internalDBCheckerResponse);
                });


    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void performNeighbourListCheck() {
        NeighbourListChecker neighbourListChecker = new NeighbourListChecker();
        NeighbourListCheckerResponse neighbourListCheckerResponse =  neighbourListChecker.CheckNeighbourList(this.mCell);
        mCheckerResponseManager.setmNeighbourListCheckerResponse(neighbourListCheckerResponse);

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void performCellConsistencyCheck() {
        CellConsistencyChecker cellConsistencyChecker = new CellConsistencyChecker();
        cellConsistencyChecker.CheckCellConsistency(this.mCell, response -> {
            CellConsistencyCheckerResponse cellConsistencyCheckerResponse = new CellConsistencyCheckerResponse(response);
            mCheckerResponseManager.setmCellConsistencyCheckerResponse(cellConsistencyCheckerResponse);
        });
    }

    public void performConectivityCheck() {
        ConectivityChecker conectivityChecker = new ConectivityChecker();
        conectivityChecker.checkForInternetConnection(checkerResponse -> mCheckerResponseManager.setmConectivityCheckerResponse(checkerResponse));

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

    public Cell getmCell() {
        return mCell;
    }

    //endregion
}
