package Checkers;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import CallBacks.InternalDatabaseCallBack;
import Model.Cell;
import DatabaseLogic.FirebaseHelper;
import Utils.GlobalMainContext;
import Utils.MConstants;

public class InternalDBChecker {
    //region Members Declaration
    private FirebaseHelper mFirebaseHelper;
    public String mCheckerResponse;
    private InternalDBChecker mInternalDBChecker;
    //endregion


    //region Constructor
    @RequiresApi(api = Build.VERSION_CODES.P)
    public InternalDBChecker() {
        this.mFirebaseHelper = new FirebaseHelper();
        this.mInternalDBChecker = this;
    }
    //endregion


    //region Methods
    public void checkInternalDatabase(Cell currentCell,final InternalDatabaseCallBack internalDatabaseCallBack) {
        mFirebaseHelper.checkDatabaseEntry(currentCell, new InternalDatabaseCallBack() {
            @Override
            public void OnReturnResponseCallback(String response) {
                switch (response) {
                    case MConstants.FirebaseHelper.EXISTS_IN_GOOD:
                        internalDatabaseCallBack.OnReturnResponseCallback(MConstants.TEST_PASSED_RO);
                        break;
                    case MConstants.FirebaseHelper.EXISTS_IN_WARNING:
                        internalDatabaseCallBack.OnReturnResponseCallback(MConstants.TEST_NEUTRAL_RO);
                        break;
                    case MConstants.FirebaseHelper.EXISTS_IN_ALERT:
                        internalDatabaseCallBack.OnReturnResponseCallback(MConstants.TEST_FAILED_RO);
                        break;
                }
            }
        });
    }


    //endregion
}
