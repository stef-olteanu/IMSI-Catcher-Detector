package DatabaseLogic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import Utils.MConstants;


public class IMSICatcherDetectorDatabase extends SQLiteOpenHelper {


    public IMSICatcherDetectorDatabase(Context context) {
        super(context, MConstants.Database.DATABASE_NAME,null,MConstants.Database.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SIGNAL_TABLE = "CREATE TABLE " + MConstants.Database.SIGNAL_TABLE.TABLE_NAME + " ("
                + MConstants.Database.SIGNAL_TABLE.KEY_ID + " INTEGER PRIMARY KEY,"
                + MConstants.Database.SIGNAL_TABLE.CELL_ID + " INTEGER,"
                + MConstants.Database.SIGNAL_TABLE.KEY_SIGNAL_VALUE + " INTEGER)";
        db.execSQL(CREATE_SIGNAL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IOF EXISTS " + MConstants.Database.SIGNAL_TABLE.TABLE_NAME);
        onCreate(db);
    }

    public void AddSignalStrength(int cellId, int signalStrength) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MConstants.Database.SIGNAL_TABLE.CELL_ID,cellId);
        values.put(MConstants.Database.SIGNAL_TABLE.KEY_SIGNAL_VALUE,signalStrength);

        db.insert(MConstants.Database.SIGNAL_TABLE.TABLE_NAME,null,values);
        db.close();
    }

    public ArrayList<Integer> getSignalValues(int cellId) {
        ArrayList<Integer> signalValues = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + MConstants.Database.SIGNAL_TABLE.TABLE_NAME + " WHERE " +
                MConstants.Database.SIGNAL_TABLE.CELL_ID + "=" + cellId;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()) {
            do {
                signalValues.add(cursor.getInt(2));

            } while (cursor.moveToNext());
        }
        return signalValues;
    }
}
