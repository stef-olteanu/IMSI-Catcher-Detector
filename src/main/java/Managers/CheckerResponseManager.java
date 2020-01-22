package Managers;

import Responses.SignalCheckerResponse;
import Utils.MConstants;

public class CheckerResponseManager {
    //region Private Members
    private SignalCheckerResponse mSignalCheckerResponse;
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
            if (mSignalCheckerResponse.getmAsuStatus().equals(MConstants.TEST_PASSED) && mSignalCheckerResponse.getmRssiStatus().equals(MConstants.TEST_PASSED)) {
                return MConstants.TEST_PASSED;
            } else {
                return MConstants.TEST_FAILED;
            }
        } else {
            return this.mSignalCheckerResponse.getmCheckingStatus();
        }
    }

    //endregion
}
