package Listeners;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.List;

import Checkers.ResponseChecker;
import Managers.CheckerManager;
import Model.Cell;
import DatabaseLogic.FirebaseHelper;
import Responses.OverallResponse;
import Utils.MConstants;
import Utils.VibratorHelper;

public class CellLocationChangeListener extends PhoneStateListener {
    //region Private Members
    private Context mainContext;
    private Cell mCurrentCell;
    private FirebaseHelper mFirebaseHelper;
    private CheckerManager mCheckerManager;
    private VibratorHelper mVibratorHelper;
    private NavigationView mNavigationView;
    private FragmentManager mFragmentManager;

    //endregion

    //region Constructor
    @RequiresApi(api = Build.VERSION_CODES.P)
    public CellLocationChangeListener(Context mContext,NavigationView navigationView,FragmentManager fragmentManager) {
        mainContext = mContext;
        mFirebaseHelper = new FirebaseHelper();
        mCheckerManager = new CheckerManager();
        mVibratorHelper = new VibratorHelper();
        mNavigationView = navigationView;
        mFragmentManager = fragmentManager;
        //mCurrentCell = new Cell(mainContext);
    }
    //endregion

    //region Listener
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onCellLocationChanged(CellLocation location) {
        super.onCellLocationChanged(location);
        try {
           mCurrentCell = new Cell(mainContext);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mFirebaseHelper.insertCell(mCurrentCell);
        List<Cell> neighbouringCells = mCurrentCell.getmNeighbouringCells();
        for(int count = 0; count < neighbouringCells.size(); count++){
            mFirebaseHelper.insertCell(neighbouringCells.get(count));
        }

            mCheckerManager.RunCheckers(mCurrentCell);
            Handler handler1 = new Handler();
            handler1.postDelayed(() -> {
                mCheckerManager.performConectivityCheck();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mCheckerManager.performOverallCheck();
                        OverallResponse finalResponse =  mCheckerManager.getmCheckerResponseManager().getmOverallResponse();
                        ResponseChecker responseChecker = new ResponseChecker(mCheckerManager);
                        Handler handler2 = new Handler();
                        handler2.postDelayed(() -> responseChecker.checkToInsert(finalResponse),3000);
                        mVibratorHelper.Vibrate(finalResponse.getmCheckingStatus());
                        responseChecker.checkIfPopup(mCheckerManager,mNavigationView,mFragmentManager);
                    }
                },3000);
            },5000);


    }
    //endregion
}
