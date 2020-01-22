package Responses;

public class CheckerResponse {
    //region Private Members
    private String mCheckingStatus;
    //endregion

    //region Constructor
    public CheckerResponse(String checkingStatus) {
        this.mCheckingStatus = checkingStatus;
    }
    //endregion

    //region Public Methods

    public String getmCheckingStatus() {
        return this.mCheckingStatus;
    }
    //endregion
}
