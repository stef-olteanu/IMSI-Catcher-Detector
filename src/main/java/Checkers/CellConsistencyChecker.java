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
    private Cell mCurrentCell;
    private FirebaseHelper mFirebaseHelper;
    //region

    //region Constructor
    @RequiresApi(api = Build.VERSION_CODES.P)
    public CellConsistencyChecker() {
        try {
            this.mCurrentCell = new Cell(GlobalMainContext.getMainContext());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.mFirebaseHelper = new FirebaseHelper();
    }
    //endregion


    //region Methods
    public void CheckCellConsistency(final InternalDatabaseCallBack internalDatabaseCallBack) {
        this.mFirebaseHelper.getAllCells(new DatabaseReaderCallBack() {
            @Override
            public void OnCallBack(List<Cell> databaseCellList) {
                for(Cell cell : databaseCellList) {
                    if(cell.GetCid().equals(mCurrentCell.GetCid())){
                        if(cell.GetLac().equals(mCurrentCell.GetLac())) {
                            internalDatabaseCallBack.OnReturnResponseCallback(MConstants.TEST_PASSED_RO);
                            return;
                        } else {
                            internalDatabaseCallBack.OnReturnResponseCallback(MConstants.TEST_FAILED_RO);
                            return;
                        }

                    }
                }
                if(mCurrentCell.getmCellStatus().equals(MConstants.Cell.WARNING)) {
                    internalDatabaseCallBack.OnReturnResponseCallback(MConstants.TEST_FAILED_RO);
                    return;
                } else if(mCurrentCell.getmCellStatus().equals(MConstants.Cell.GOOD)){
                    internalDatabaseCallBack.OnReturnResponseCallback(MConstants.TEST_PASSED_RO);
                }
            }
        },"GoodCells");
    }
    //endregion
}
