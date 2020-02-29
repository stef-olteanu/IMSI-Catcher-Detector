package Checkers;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.List;

import Model.Cell;
import Model.Dispozitiv;
import Responses.NeighbourListCheckerResponse;
import Utils.GlobalMainContext;
import Utils.MConstants;

public class NeighbourListChecker {
    //region Members Declaration
    private Cell mCurrentCell;
    private Dispozitiv mCurrentDevice;
    //endregion


    //region Constructor
    @RequiresApi(api = Build.VERSION_CODES.P)
    public NeighbourListChecker(){
        try {
            this.mCurrentCell = new Cell(GlobalMainContext.getMainContext());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.mCurrentDevice = new Dispozitiv(GlobalMainContext.getMainContext());
    }
    //endregion


    //region Methods
    public NeighbourListCheckerResponse CheckNeighbourList(){
        String manufacturer = this.mCurrentDevice.GetManufacturer();
        if(manufacturer.equals(MConstants.SAMSUNG_PHONE_MODEL)){
            return new NeighbourListCheckerResponse(MConstants.TEST_NEUTRAL_RO);
        }
        List<Cell> neighbourList = this.mCurrentCell.getmNeighbouringCells();
        if(neighbourList.size() != 0){
            return new NeighbourListCheckerResponse(MConstants.TEST_PASSED_RO);
        }
        return new NeighbourListCheckerResponse(MConstants.TEST_FAILED_RO);
    }
    //endregion
}
