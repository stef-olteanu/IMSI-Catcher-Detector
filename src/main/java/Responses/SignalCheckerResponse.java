package Responses;

public class SignalCheckerResponse extends CheckerResponse {

    //region Private Members
    private String mRssiStatus;
    private String mAsuStatus;
    //endregion

    //region Constructor
    public SignalCheckerResponse(String checkingStatus, String rssiStatus, String asuStatus) {
        super(checkingStatus);
        this.mRssiStatus = rssiStatus;
        this.mAsuStatus = asuStatus;
    }
    //endregion

    //region Public Methods

    public String getmRssiStatus() {
        return this.mRssiStatus;
    }

    public String getmAsuStatus() {
        return this.mAsuStatus;
    }

    public String getmCheckingStatus() {
        return super.getmCheckingStatus();
    }

    //endregion
}
