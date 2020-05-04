package Checkers;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.List;

import CallBacks.DatabaseReaderCallBack;
import CallBacks.InternalDatabaseCallBack;
import Model.Cell;
import DatabaseLogic.FirebaseHelper;
import Utils.GlobalMainContext;
import Utils.MConstants;

public class CellConsistencyChecker {
    //region Members Declaration
    private FirebaseHelper mFirebaseHelper;
    //region

    //region Constructor
    @RequiresApi(api = Build.VERSION_CODES.P)
    public CellConsistencyChecker() {
        this.mFirebaseHelper = new FirebaseHelper();
    }
    //endregion


    //region Methods
    public void CheckCellConsistency(Cell currentCell,final InternalDatabaseCallBack internalDatabaseCallBack) {
        this.mFirebaseHelper.getAllCells(new DatabaseReaderCallBack() {
            @Override
            public void OnCallBack(List<Cell> databaseCellList) {
                for(Cell cell : databaseCellList) {
                    if(cell.GetCid().equals(currentCell.GetCid())){
                        if(cell.GetLac().equals(currentCell.GetLac())) {
                            internalDatabaseCallBack.OnReturnResponseCallback(MConstants.TEST_PASSED_RO);
                            return;
                        } else {
                            internalDatabaseCallBack.OnReturnResponseCallback(MConstants.TEST_FAILED_RO);
                            return;
                        }

                    }
                }
                if(currentCell.getmCellStatus().equals(MConstants.Cell.WARNING)) {
                    internalDatabaseCallBack.OnReturnResponseCallback(MConstants.TEST_FAILED_RO);
                    return;
                } else if(currentCell.getmCellStatus().equals(MConstants.Cell.GOOD)){
                    internalDatabaseCallBack.OnReturnResponseCallback(MConstants.TEST_PASSED_RO);
                }
            }
        },"GoodCells");
    }
    //endregion
}
