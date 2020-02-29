package Managers;

import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.licentav00.CheckersViews.CheckerTextView;

import Utils.GlobalMainContext;
import Utils.MConstants;

public class CheckerViewManager {
    //region Private Members
    private View mView;
    //endregion


    //region Constructor
    public CheckerViewManager(View view) {
        this.mView = view;
    }
    //endregion


    //region Public Methods
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    /**
     * Function that creates the view when the check is started
     */
    public void CreateView(String checkDone) {
        switch (checkDone) {
            case MConstants.SIGNAL_CHECKER:
                TextView sigCheckTextView = new CheckerTextView(GlobalMainContext.getMainContext(),this.mView);
                ((CheckerTextView) sigCheckTextView).SetParamsTextView(checkDone);
                ((CheckerTextView) sigCheckTextView).ShowTextView();
                break;
            case MConstants.PUBLIC_DB_CHECKER:
                TextView publicDbTextView = new CheckerTextView(GlobalMainContext.getMainContext(), this.mView);
                ((CheckerTextView) publicDbTextView).SetParamsTextView(checkDone);
                ((CheckerTextView) publicDbTextView).ShowTextView();
                break;
            case MConstants.INTERNAL_DB_CHECKER:
                TextView internalDBTextView = new CheckerTextView(GlobalMainContext.getMainContext(), this.mView);
                ((CheckerTextView) internalDBTextView).SetParamsTextView(checkDone);
                ((CheckerTextView) internalDBTextView).ShowTextView();
                break;
            case MConstants.NEIGHBOUR_LIST_CHECKER:
                TextView neighbourListTextView = new CheckerTextView(GlobalMainContext.getMainContext(), this.mView);
                ((CheckerTextView) neighbourListTextView).SetParamsTextView(checkDone);
                ((CheckerTextView) neighbourListTextView).ShowTextView();
                break;
        }
    }

    /**
     * Function that creates view when the checker stops
     * @param checkDone
     * @param checkStatus
     */
    public void CreateView(String checkDone, String checkStatus) {
        switch (checkDone) {
            case MConstants.SIGNAL_CHECKER:
                TextView finalCheckTextView = new CheckerTextView(GlobalMainContext.getMainContext(), this.mView);
                ((CheckerTextView) finalCheckTextView).SetParamsTextView(checkDone, checkStatus);
                ((CheckerTextView) finalCheckTextView).ShowTextView();
                break;
            case MConstants.PUBLIC_DB_CHECKER:
                TextView finalPublicDBTextView = new CheckerTextView(GlobalMainContext.getMainContext(), this.mView);
                ((CheckerTextView) finalPublicDBTextView).SetParamsTextView(checkDone, checkStatus);
                ((CheckerTextView) finalPublicDBTextView).ShowTextView();
            case MConstants.INTERNAL_DB_CHECKER:
                TextView internalDBTextView = new CheckerTextView(GlobalMainContext.getMainContext(), this.mView);
                ((CheckerTextView) internalDBTextView).SetParamsTextView(checkDone, checkStatus);
                ((CheckerTextView) internalDBTextView).ShowTextView();
                break;
            case MConstants.NEIGHBOUR_LIST_CHECKER:
                TextView neighbourListTextView = new CheckerTextView(GlobalMainContext.getMainContext(), this.mView);
                ((CheckerTextView) neighbourListTextView).SetParamsTextView(checkDone, checkStatus);
                ((CheckerTextView) neighbourListTextView).ShowTextView();
                break;
        }




    }



    //endregion
}
