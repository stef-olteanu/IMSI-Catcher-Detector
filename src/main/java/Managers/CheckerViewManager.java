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
        TextView sigCheckTextView = new CheckerTextView(GlobalMainContext.getMainContext(),this.mView);
        ((CheckerTextView) sigCheckTextView).SetParamsTextView(checkDone);
        ((CheckerTextView) sigCheckTextView).ShowTextView();

        TextView publicDbTextView = new CheckerTextView(GlobalMainContext.getMainContext(), this.mView);
        ((CheckerTextView) publicDbTextView).SetParamsTextView(checkDone);
        ((CheckerTextView) publicDbTextView).ShowTextView();
    }

    /**
     * Function that creates view when the checker stops
     * @param checkDone
     * @param checkStatus
     */
    public void CreateView(String checkDone, String checkStatus) {
        TextView finalCheckTextView = new CheckerTextView(GlobalMainContext.getMainContext(),this.mView);
        ((CheckerTextView) finalCheckTextView).SetParamsTextView(checkDone,checkStatus);
        ((CheckerTextView) finalCheckTextView).ShowTextView();

        TextView finalPublicDBTextView = new CheckerTextView(GlobalMainContext.getMainContext(),this.mView);
        ((CheckerTextView) finalPublicDBTextView).SetParamsTextView(checkDone,checkStatus);
        ((CheckerTextView) finalPublicDBTextView).ShowTextView();
    }



    //endregion
}
