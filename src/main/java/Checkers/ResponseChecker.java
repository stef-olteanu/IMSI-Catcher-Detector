package Checkers;

import android.util.Log;

import androidx.fragment.app.FragmentManager;

import com.example.licentav00.Popups.CellImsiCatcherPopUpDialog;
import com.example.licentav00.Popups.CheckerPopUpDialog;
import com.google.android.material.navigation.NavigationView;

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

    public void checkIfPopup(CheckerManager checkerManager, NavigationView navigationView, FragmentManager fragmentManager) {
        if(checkerManager.getmCheckerResponseManager().getmOverallResponse().getmCheckingStatus().equals(MConstants.OVERALL_FAILED_RO)) {
                CellImsiCatcherPopUpDialog cellImsiCatcherPopUpDialog = new CellImsiCatcherPopUpDialog(checkerManager, navigationView, fragmentManager);
                cellImsiCatcherPopUpDialog.show(fragmentManager, "checkerpopup");
        }
    }
    //endregion
}
