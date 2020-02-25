package Checkers;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import Model.Cell;
import Responses.PublicDBCheckerResponse;
import Utils.GlobalMainContext;
import Utils.MConstants;

public class PublicDBChecker {
    //region Members Declaration
    private Cell mCurrentCell;
    //endregion


    //region Constructor
    @RequiresApi(api = Build.VERSION_CODES.P)
    public PublicDBChecker() {
        try {
            this.mCurrentCell = new Cell(GlobalMainContext.getMainContext());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //endregion


    //region Methods
    public PublicDBCheckerResponse checkPublicDB(){
        String cellStatus = this.mCurrentCell.getmCellStatus();
        if(cellStatus.equals(MConstants.Cell.GOOD))
            return new PublicDBCheckerResponse(MConstants.TEST_PASSED_RO);
        return new PublicDBCheckerResponse(MConstants.TEST_FAILED_RO);
    }
    //endregion
}
