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

    //endregion


    //region Constructor
    @RequiresApi(api = Build.VERSION_CODES.P)
    public PublicDBChecker() {

    }
    //endregion


    //region Methods
    public PublicDBCheckerResponse checkPublicDB(Cell currentCell){
        String cellStatus = currentCell.getmCellStatus();
        if(cellStatus.equals(MConstants.Cell.GOOD))
            return new PublicDBCheckerResponse(MConstants.TEST_PASSED_RO);
        return new PublicDBCheckerResponse(MConstants.TEST_FAILED_RO);
    }
    //endregion
}
