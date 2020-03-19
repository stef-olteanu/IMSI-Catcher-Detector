package Managers;

import Responses.CellConsistencyCheckerResponse;
import Responses.CheckerResponse;
import Responses.InternalDBCheckerResponse;
import Responses.NeighbourListCheckerResponse;
import Responses.OverallResponse;
import Responses.SignalCheckerResponse;
import Utils.MConstants;

public class CheckerResponseManager {
    //region Private Members
    private CheckerResponse mSignalCheckerResponse;
    private CheckerResponse mPublicDbCheckerResponse;
    private InternalDBCheckerResponse mInternalDBCheckerResponse;
    private NeighbourListCheckerResponse mNeighbourListCheckerResponse;
    private CellConsistencyCheckerResponse mCellConsistencyCheckerResponse;
    private OverallResponse mOverallResponse;
    private CheckerResponse mConectivityCheckerResponse;
    //endregion

    //region Constructor
    public CheckerResponseManager() { }
    //endregion

    //region Public Methods

    public void setmSignalCheckerResponse(CheckerResponse mSignalCheckerResponse) {
        this.mSignalCheckerResponse = mSignalCheckerResponse;
    }

    public String GetFinalSignalResponse(){
        return this.mSignalCheckerResponse.getmCheckingStatus();
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

    public void setmCellConsistencyCheckerResponse(CellConsistencyCheckerResponse mCellConsistencyCheckerResponse) {
        this.mCellConsistencyCheckerResponse = mCellConsistencyCheckerResponse;
    }

    public CellConsistencyCheckerResponse getmCellConsistencyCheckerResponse() {
        return mCellConsistencyCheckerResponse;
    }

    public OverallResponse getmOverallResponse() {
        return mOverallResponse;
    }

    public void setmOverallResponse(OverallResponse mOverallResponse) {
        this.mOverallResponse = mOverallResponse;
    }

    public CheckerResponse getmConectivityCheckerResponse() {
        return mConectivityCheckerResponse;
    }

    public void setmConectivityCheckerResponse(CheckerResponse mConectivityCheckerResponse) {
        this.mConectivityCheckerResponse = mConectivityCheckerResponse;
    }

    //endregion
}
