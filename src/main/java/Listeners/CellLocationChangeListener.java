package Listeners;

import android.content.Context;
import android.os.Build;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.List;

import Model.Cell;
import DatabaseLogic.FirebaseHelper;

public class CellLocationChangeListener extends PhoneStateListener {
    //region Constructor
    @RequiresApi(api = Build.VERSION_CODES.P)
    public CellLocationChangeListener(Context mContext) throws IOException, InterruptedException {
        mainContext = mContext;
        mFirebaseHelper = new FirebaseHelper();
        //mCurrentCell = new Cell(mainContext);
    }
    //endregion

    //region Private Members
    private Context mainContext;
    private Cell mCurrentCell;
    private FirebaseHelper mFirebaseHelper;

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

    }
    //endregion
}
