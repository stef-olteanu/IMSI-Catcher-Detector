package DatabaseLogic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import Utils.MConstants;

public class DatabaseAdapter {
    //region Members Declaration
    IMSICatcherDetectorDatabase mImsiCatcherDetectorDatabase;
    SQLiteDatabase mDatabase;
    //endregion


    //region Constructor
    public DatabaseAdapter(Context context) {
        this.mImsiCatcherDetectorDatabase = new IMSICatcherDetectorDatabase(context);
    }

    //endregion

    //region Public Methods
    public void OpenConnection(){
        this.mDatabase = this.mImsiCatcherDetectorDatabase.getWritableDatabase();
    }

    public void CloseConnection(){
        this.mDatabase.close();
    }

    public void AddSignalStrength(int cellId, int signalStrength) {
        ContentValues values = new ContentValues();
        values.put(MConstants.Database.SIGNAL_TABLE.CELL_ID,cellId);
        values.put(MConstants.Database.SIGNAL_TABLE.KEY_SIGNAL_VALUE,signalStrength);

        this.mDatabase.insert(MConstants.Database.SIGNAL_TABLE.TABLE_NAME,null,values);
    }

    public ArrayList<Integer> getSignalValues(int cellId) {
        ArrayList<Integer> signalValues = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + MConstants.Database.SIGNAL_TABLE.TABLE_NAME + " WHERE " +
                MConstants.Database.SIGNAL_TABLE.CELL_ID + "=" + cellId;
        Cursor cursor = this.mDatabase.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()) {
            do {
                signalValues.add(cursor.getInt(2));

            } while (cursor.moveToNext());
        }
        return signalValues;
    }
    //endregion
}
