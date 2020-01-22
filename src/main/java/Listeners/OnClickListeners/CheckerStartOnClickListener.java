package Listeners.OnClickListeners;

import android.os.Build;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.RequiresApi;

import Informers.CheckerStatusInformer;
import Managers.CheckerManager;

public class CheckerStartOnClickListener implements View.OnTouchListener {

    //region Private Members
    private CheckerManager mCheckerManager;
    private View mView;
    //endregion


    //region Constructor
    public CheckerStartOnClickListener(View view){
        this.mCheckerManager = new CheckerManager();
        this.mView = view;
    }
    //endregion

    //region Public Methods
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public boolean onTouch(View v,MotionEvent event) {
        mView.requestLayout();
        this.mCheckerManager.RunCheckers(new CheckerStatusInformer(mView));
        return true;
    }

    //endregion
}
