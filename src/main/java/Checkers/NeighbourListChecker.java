package Checkers;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.List;

import Model.Cell;
import Model.Device;
import Responses.NeighbourListCheckerResponse;
import Utils.GlobalMainContext;
import Utils.MConstants;

public class NeighbourListChecker {
    //region Members Declaration
    private Device mCurrentDevice;
    //endregion


    //region Constructor
    @RequiresApi(api = Build.VERSION_CODES.P)
    public NeighbourListChecker(){
        this.mCurrentDevice = new Device(GlobalMainContext.getMainContext());
    }
    //endregion


    //region Methods
    public NeighbourListCheckerResponse CheckNeighbourList(Cell currentCell){
        String manufacturer = this.mCurrentDevice.GetManufacturer();
        if(manufacturer.equals(MConstants.SAMSUNG_PHONE_MODEL)){
            return new NeighbourListCheckerResponse(MConstants.TEST_NEUTRAL_RO);
        }
        List<Cell> neighbourList = currentCell.getmNeighbouringCells();
        if(neighbourList.size() != 0){
            return new NeighbourListCheckerResponse(MConstants.TEST_PASSED_RO);
        }
        return new NeighbourListCheckerResponse(MConstants.TEST_FAILED_RO);
    }
    //endregion
}
