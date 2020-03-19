package Checkers;

import Managers.CheckerResponseManager;
import Responses.CellConsistencyCheckerResponse;
import Responses.CheckerResponse;
import Responses.InternalDBCheckerResponse;
import Responses.NeighbourListCheckerResponse;
import Responses.OverallResponse;
import Responses.SignalCheckerResponse;
import Utils.MConstants;

public class OverallChecker {
    //region Members Declaration
    private CheckerResponseManager mCheckerResponseManager;
    //endregion


    //region Constructor
    public OverallChecker(CheckerResponseManager checkerResponseManager) {
        this.mCheckerResponseManager = checkerResponseManager;
    }
    //endregion


    //region Methods
    public OverallResponse OverallCheck() {
        String signalCheckerResponse = this.mCheckerResponseManager.GetFinalSignalResponse();
        String publicDBCheckerResponse = this.mCheckerResponseManager.getmPublicDbCheckerResponse().getmCheckingStatus();
        String internalDBCheckerResponse  = this.mCheckerResponseManager.getmInternalDBCheckerResponse().getmCheckingStatus();
        String neighbourListChecker = this.mCheckerResponseManager.getmNeighbourListCheckerResponse().getmCheckingStatus();
        String cellConsistencyChecker = this.mCheckerResponseManager.getmCellConsistencyCheckerResponse().getmCheckingStatus();
        String connectivityChecker = this.mCheckerResponseManager.getmConectivityCheckerResponse().getmCheckingStatus();

        int score = 0;
        if(signalCheckerResponse.equals(MConstants.TEST_PASSED_RO)) {
            score += 10;
        } else if ( signalCheckerResponse.equals(MConstants.TEST_FAILED_RO) ){
            score -= 10;
        } else if (signalCheckerResponse.equals(MConstants.SIGNAL_CHECKER_STATUS_FAILED_RO)){
            score += 0;
        }

        if(publicDBCheckerResponse.equals(MConstants.TEST_PASSED_RO)) {
            score += 10;
        } else {
            score -= 10;
        }

        if(internalDBCheckerResponse.equals(MConstants.TEST_PASSED_RO)) {
            score += 10;
        } else if (internalDBCheckerResponse.equals(MConstants.TEST_FAILED_RO)) {
            score -= 10;
        } else if (internalDBCheckerResponse.equals(MConstants.TEST_NEUTRAL_RO)) {
            score += 0;
        }

        if(neighbourListChecker.equals(MConstants.TEST_PASSED_RO)) {
            score += 10;
        } else {
            score -= 10;
        }

        if(cellConsistencyChecker.equals(MConstants.TEST_PASSED_RO)) {
            score += 10;
        } else {
            score -= 10;
        }

        if(connectivityChecker.equals(MConstants.TEST_PASSED_RO)) {
            score += 10;
        } else {
            score -=10;
        }

        if(score >= 0)
            return new OverallResponse(MConstants.TEST_PASSED_RO);

        return new OverallResponse(MConstants.TEST_FAILED_RO);
    }
    //endregion
}
