package DatabaseLogic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import Utils.MConstants;


public class IMSICatcherDetectorDatabase extends SQLiteOpenHelper {
    //region Constructor
    public IMSICatcherDetectorDatabase(Context context) {
        super(context, MConstants.Database.DATABASE_NAME,null,MConstants.Database.VERSION);
    }
    //endregion


    //region Public Methods
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
        db.execSQL("DROP TABLE IF EXISTS " + MConstants.Database.SIGNAL_TABLE.TABLE_NAME);
        onCreate(db);
    }

}
