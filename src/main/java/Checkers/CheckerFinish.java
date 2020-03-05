package Checkers;

import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.licentav00.R;

import Informers.CheckerStatusInformer;
import Managers.CheckerManager;
import Utils.MConstants;
import Utils.VibratorHelper;

public class CheckerFinish {
    //region Members Declaration
    private View mView;
    private CheckerManager mCheckerManager;
    private VibratorHelper mVibratorHelper;
    //endregion


    //region Constructor
    public CheckerFinish(View view, CheckerManager checkerManager) {
        this.mCheckerManager = checkerManager;
        this.mView = view;
        this.mVibratorHelper = new VibratorHelper();
    }
    //endregion


    //region Public methods
    public boolean onFinish() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void run() {
                final CheckerStatusInformer checkerStatusInformer = new CheckerStatusInformer(mView);
                Handler handler1 = new Handler();
                for (int counter = 0; counter < MConstants.NUM_OF_CHECKS; counter++) {
                    switch (counter) {
                        case 0:
                            checkerStatusInformer.OnCheckCompleted(MConstants.SIGNAL_CHECKER, mCheckerManager.getmCheckerResponseManager().GetFinalSignalResponse());
                            break;
                        case 1:
                            handler1.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    checkerStatusInformer.OnCheckCompleted(MConstants.PUBLIC_DB_CHECKER, mCheckerManager.getmCheckerResponseManager().getmPublicDbCheckerResponse().getmCheckingStatus());
                                }
                            }, counter * 1000);
                            break;
                        case 2:
                            handler1.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    checkerStatusInformer.OnCheckCompleted(MConstants.INTERNAL_DB_CHECKER, mCheckerManager.getmCheckerResponseManager().getmInternalDBCheckerResponse().getmCheckingStatus());
                                }
                            }, counter * 2 * 500 + 1000);
                            break;
                        case 3:
                            handler1.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    checkerStatusInformer.OnCheckCompleted(MConstants.NEIGHBOUR_LIST_CHECKER, mCheckerManager.getmCheckerResponseManager().getmNeighbourListCheckerResponse().getmCheckingStatus());
                                }
                            }, counter * 3 * 500 + 1000);
                            break;
                        case 4:
                            handler1.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    checkerStatusInformer.OnCheckCompleted(MConstants.CELL_CONSISTENCY_CHECKER, mCheckerManager.getmCheckerResponseManager().getmCellConsistencyCheckerResponse().getmCheckingStatus());
                                }
                            }, counter * 4 * 500 + 1000);
                            break;
                        case 5:
                            handler1.postDelayed(new Runnable() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void run() {
                                    mCheckerManager.performOverallCheck();
                                    String response = mCheckerManager.getmCheckerResponseManager().getmOverallResponse().getmCheckingStatus();
                                    checkerStatusInformer.OnCheckCompleted(MConstants.OVERALL_CHECKER, response);

                                    mView.findViewById(R.id.progressBarCheck).setVisibility(View.INVISIBLE);
                                    mVibratorHelper.Vibrate(response);
                                    mView.findViewById(R.id.buttonRetake).setVisibility(View.VISIBLE);
                                    mView.findViewById(R.id.buttonPause).setVisibility(View.INVISIBLE);
                                    TextView textView =  mView.findViewById(R.id.checkStartedText);
                                    textView.setText(R.string.CHECKSENDED);
                                }
                            }, counter * 5 * 500);
                            break;

                    }
                }

            }
        }, 5000);
        return false;
    }

    //endregion
}
