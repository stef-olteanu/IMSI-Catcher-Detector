package Listeners.OnClickListeners;

import android.os.Build;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.example.licentav00.R;

import Informers.CheckerStatusInformer;
import Managers.CheckerManager;
import Utils.MConstants;

public class CheckerFinishOnTouchListener implements View.OnTouchListener {

    //region Members Declarations
    private View mView;
    private CheckerManager mCheckerManager;
    //endregion

    //region Constructor
    public CheckerFinishOnTouchListener(View view, CheckerManager checkerManager) {
        this.mCheckerManager = checkerManager;
        this.mView = view;
    }
    //endregion


    //region Public Methods
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void run() {
                final CheckerStatusInformer checkerStatusInformer = new CheckerStatusInformer(mView);
                Handler handler1 = new Handler();
                for(int counter = 0; counter < MConstants.NUM_OF_CHECKS; counter++){
                    switch (counter){
                        case 0:
                            checkerStatusInformer.OnCheckCompleted(MConstants.SIGNAL_CHECKER,mCheckerManager.getmCheckerResponseManager().GetFinalSignalResponse());
                            break;
                        case 1:
                            handler1.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    checkerStatusInformer.OnCheckStarted(MConstants.PUBLIC_DB_CHECKER);
                                }

                            },counter *500);
                            handler1.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    checkerStatusInformer.OnCheckCompleted(MConstants.PUBLIC_DB_CHECKER,mCheckerManager.getmCheckerResponseManager().getmPublicDbCheckerResponse().getmCheckingStatus());
                                }
                            },counter * 1000);
                            break;
                        case 2:
                            handler1.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    checkerStatusInformer.OnCheckStarted(MConstants.INTERNAL_DB_CHECKER);
                                }

                            },counter * 2 * 500);
                            handler1.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    checkerStatusInformer.OnCheckCompleted(MConstants.INTERNAL_DB_CHECKER,mCheckerManager.getmCheckerResponseManager().getmInternalDBCheckerResponse().getmCheckingStatus());
                                }
                            },counter * 2 * 500 + 1000);
                            break;
                        case 3:
                            handler1.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    checkerStatusInformer.OnCheckStarted(MConstants.NEIGHBOUR_LIST_CHECKER);
                                }

                            },counter * 3 * 500);
                            handler1.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    checkerStatusInformer.OnCheckCompleted(MConstants.NEIGHBOUR_LIST_CHECKER,mCheckerManager.getmCheckerResponseManager().getmNeighbourListCheckerResponse().getmCheckingStatus());
                                    mView.findViewById(R.id.progressBarCheck).setVisibility(View.INVISIBLE);
                                }
                            },counter * 3 * 500 + 1000);
                            break;

                    }
                }

            }
        },5000);
        return false;
    }

    //endregion
}
