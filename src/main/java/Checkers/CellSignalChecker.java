package Checkers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import Model.Cell;
import Responses.CheckerResponse;
import Responses.SignalCheckerResponse;
import Utils.MConstants;
import Utils.GlobalMainContext;

/**
 * Class that monitors signal strength of the current cell
 * For GSM - 2G
 *      RSSI level should be between -50 -> -110
 *      ASU level should be between 0 -> 31
 * While listening for the signal, the phone should not be moving in any way.
 * If the phone is still but there are spikes in the signal levels, it might mean that the current cell
 * is an IMSI Catcher
 */
public class CellSignalChecker {
    //region Private Members
    private Cell mCurrentCell = null;
    private long mLastMovementTime;
    final private long MAXIMUM_SAFE_PERIOD = 5000;
    final private long COUNTS_VERIFY_SIGNAL = 5000;
    final private int MIN_RSSI_RANGE = -110;
    final private int MAX_RSSI_RANGE = -50;
    final private int MIN_ASU_RANGE = 0;
    final private int MAX_ASU_RANGE = 31;
    private SharedPreferences mSharedPreferences = GlobalMainContext.getMainContext().getSharedPreferences("sharedTime",Context.MODE_PRIVATE);
    private SharedPreferences.Editor mEditor = mSharedPreferences.edit();
    //endregion


    //region Constructor
    @RequiresApi(api = Build.VERSION_CODES.P)
    public CellSignalChecker(){

        try {
            mCurrentCell = new Cell(GlobalMainContext.getMainContext());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //endregion


    //region Public Methods

    /**
     * Function that checks anomalies in the way signal behaves. If the phone stays still for more than 5 seconds
     * it wouldn't be normal for the signal to have spikes. If it has it clearly is a problem, as IMSI CATCHERS
     * have high power signal in order to attract phones easily.
     * What I check here is:
     *      RSSI level;
     *      ASU level;
     * of the current cell.
     * If the phone is moved while checking, it will interrupt the process and it won't be taken into consideration.
     * This is done using the status, which won't be modified unless the phone is moved.
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public CheckerResponse checkSignalStrength(){
        String rssiStatus = MConstants.TEST_PASSED_RO;
        String asuStatus = MConstants.TEST_PASSED_RO;
        String checkerStatus = MConstants.CHECKER_STATUS_COMPLETE;
        float currentTimeMilis = System.currentTimeMillis();



        mLastMovementTime = mSharedPreferences.getLong("lastMovementTime", mLastMovementTime);
        if (currentTimeMilis - mLastMovementTime >= MAXIMUM_SAFE_PERIOD) {
            for (int count = 0; count < COUNTS_VERIFY_SIGNAL; count++) {
                boolean checkerRSSI = true;
                boolean checkerASU = true;
                int rssiLevel = Integer.parseInt(mCurrentCell.GetSignalDbm());
                int asuLevel = Integer.parseInt(mCurrentCell.GetAsuLevel());
                if (rssiLevel < MIN_RSSI_RANGE || rssiLevel > MAX_RSSI_RANGE)
                    checkerRSSI = false;
                if (asuLevel < MIN_ASU_RANGE || asuLevel > MAX_ASU_RANGE)
                    checkerASU = false;
                if (checkerRSSI) {
                   rssiStatus = MConstants.TEST_PASSED_RO;
                } else {
                    rssiStatus = MConstants.TEST_FAILED_RO;
                    break;
                }
                if (checkerASU) {
                    asuStatus = MConstants.TEST_PASSED_RO;
                } else {
                    asuStatus = MConstants.TEST_FAILED_RO;
                    break;
                }
            }
        } else {
            checkerStatus = MConstants.SIGNAL_CHECKER_STATUS_FAILED_RO;
        }
        return  new SignalCheckerResponse(checkerStatus, rssiStatus, asuStatus);
    }


    //endregion
}
