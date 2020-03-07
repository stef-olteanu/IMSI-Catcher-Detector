package Checkers;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.ArrayList;

import DatabaseLogic.IMSICatcherDetectorDatabase;
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
    private SharedPreferences mSharedPreferences = GlobalMainContext.getMainContext().getSharedPreferences("sharedTime",Context.MODE_PRIVATE);
    private SharedPreferences.Editor mEditor = mSharedPreferences.edit();
    private IMSICatcherDetectorDatabase mDatabase;
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
        this.mDatabase = new IMSICatcherDetectorDatabase(GlobalMainContext.getMainContext());
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
    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public CheckerResponse checkSignalStrength() {
        float currentTimeMilis = System.currentTimeMillis();


        mLastMovementTime = mSharedPreferences.getLong("lastMovementTime", mLastMovementTime);
        if (currentTimeMilis - mLastMovementTime >= MAXIMUM_SAFE_PERIOD) {
            ArrayList<Integer> signalValues =  this.mDatabase.getSignalValues(Integer.parseInt(this.mCurrentCell.GetCid()));
            Double signalAverage = signalValues.stream().mapToInt(val -> val).average().orElse(0.0);
            int currentSignalStrenght = Integer.parseInt(this.mCurrentCell.GetSignalDbm());
            if(currentSignalStrenght > signalAverage + 6)
                return new CheckerResponse(MConstants.TEST_FAILED_RO);
            if(currentSignalStrenght < signalAverage - 6)
                return new CheckerResponse(MConstants.TEST_FAILED_RO);
        }
        return new CheckerResponse(MConstants.TEST_PASSED_RO);
    }

    //endregion
}
