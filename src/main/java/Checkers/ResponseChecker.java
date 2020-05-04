package Checkers;

import android.util.Log;

import DatabaseLogic.FirebaseHelper;
import Managers.CheckerManager;
import Model.Cell;
import Responses.OverallResponse;
import Utils.MConstants;

public class ResponseChecker {
    //region Members Declaration
    CheckerManager mCheckerManager;
    //endregion

    //region Constructor

    public ResponseChecker(CheckerManager mCheckerManager) {
        this.mCheckerManager = mCheckerManager;
    }
    //endregion

    //region Public Methods
    public void checkToInsert(OverallResponse finalResponse) {
        if(finalResponse.getmCheckingStatus().equals(MConstants.OVERALL_FAILED_RO)) {
            Cell cell = mCheckerManager.getmCell();
            cell.setmCellStatus(MConstants.Cell.ALERT);
            FirebaseHelper firebaseHelper = new FirebaseHelper();
            firebaseHelper.insertCell(cell);
        } else {
            Log.i("INFO:","CELULA APARTINE UNUI FURNIZOR GSM");
        }
    }
    //endregion
}
