package Managers;

import Responses.CheckerResponse;
import Responses.InternalDBCheckerResponse;
import Responses.NeighbourListCheckerResponse;
import Responses.SignalCheckerResponse;
import Utils.MConstants;

public class CheckerResponseManager {
    //region Private Members
    private SignalCheckerResponse mSignalCheckerResponse;
    private CheckerResponse mPublicDbCheckerResponse;
    private InternalDBCheckerResponse mInternalDBCheckerResponse;
    private NeighbourListCheckerResponse mNeighbourListCheckerResponse;
    //endregion

    //region Constructor
    public CheckerResponseManager() { }
    //endregion

    //region Public Methods

    public void setmSignalCheckerResponse(SignalCheckerResponse mSignalCheckerResponse) {
        this.mSignalCheckerResponse = mSignalCheckerResponse;
    }

    public String GetFinalSignalResponse(){
        if (mSignalCheckerResponse.getmCheckingStatus().equals(MConstants.CHECKER_STATUS_COMPLETE)) {
            if (mSignalCheckerResponse.getmAsuStatus().equals(MConstants.TEST_PASSED_RO) && mSignalCheckerResponse.getmRssiStatus().equals(MConstants.TEST_PASSED_RO)) {
                return MConstants.TEST_PASSED_RO;
            } else {
                return MConstants.TEST_FAILED_RO;
            }
        } else {
            return this.mSignalCheckerResponse.getmCheckingStatus();
        }
    }

    public CheckerResponse getmPublicDbCheckerResponse() {
        return mPublicDbCheckerResponse;
    }

    public void setmPublicDbCheckerResponse(CheckerResponse mPublicDbCheckerResponse) {
        this.mPublicDbCheckerResponse = mPublicDbCheckerResponse;
    }

    public void setmInternalDBCheckerResponse(InternalDBCheckerResponse mInternalDBCheckerResponse) {
        this.mInternalDBCheckerResponse = mInternalDBCheckerResponse;
    }

    public InternalDBCheckerResponse getmInternalDBCheckerResponse() {
        return mInternalDBCheckerResponse;
    }

    public NeighbourListCheckerResponse getmNeighbourListCheckerResponse() {
        return mNeighbourListCheckerResponse;
    }

    public void setmNeighbourListCheckerResponse(NeighbourListCheckerResponse mNeighbourListCheckerResponse) {
        this.mNeighbourListCheckerResponse = mNeighbourListCheckerResponse;
    }

    //endregion
}
