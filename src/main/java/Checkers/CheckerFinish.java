package Checkers;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.licentav00.Popups.ResultInfoPopup;
import com.example.licentav00.R;

import Informers.CheckerStatusInformer;
import Managers.CheckerManager;
import Utils.GlobalMainContext;
import Utils.MConstants;
import Utils.VibratorHelper;

public class CheckerFinish {
    //region Members Declaration
    private View mView;
    private CheckerManager mCheckerManager;
    private VibratorHelper mVibratorHelper;
    private FragmentManager mFragmentManager;
    //endregion


    //region Constructor
    public CheckerFinish(View view, CheckerManager checkerManager, FragmentManager fragmentManager) {
        this.mCheckerManager = checkerManager;
        this.mView = view;
        this.mVibratorHelper = new VibratorHelper();
        this.mFragmentManager = fragmentManager;
    }
    //endregion


    //region Public methods
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public boolean onFinish() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            final CheckerStatusInformer checkerStatusInformer = new CheckerStatusInformer(mView);
            Handler handler1 = new Handler();
                for (int counter = 0; counter < MConstants.NUM_OF_CHECKS; counter++) {
                    SharedPreferences sharedPreferences = GlobalMainContext.getMainContext().getSharedPreferences("CheckInfo", Context.MODE_PRIVATE);
                    boolean isPaused = sharedPreferences.getBoolean("isPaused",false);
                    if(!isPaused) {
                    switch (counter) {
                        case 0:
                            SharedPreferences sharedPreferences2 = GlobalMainContext.getMainContext().getSharedPreferences("CheckInfo", Context.MODE_PRIVATE);
                            boolean isPaused2 = sharedPreferences2.getBoolean("isPaused",false);
                            if(!isPaused2) {
                                this.mCheckerManager.performConectivityCheck();
                                String result = mCheckerManager.getmCheckerResponseManager().getmConectivityCheckerResponse().getmCheckingStatus();
                                checkerStatusInformer.OnCheckCompleted(MConstants.CONNECTIVITY_CHECKER,result);
                                startOnClickListeners(MConstants.CONNECTIVITY_CHECKER,result);
                            } else {
                                handler1.removeCallbacksAndMessages(null);
                            }


                        case 1:
                            sharedPreferences2 = GlobalMainContext.getMainContext().getSharedPreferences("CheckInfo", Context.MODE_PRIVATE);
                            isPaused2 = sharedPreferences2.getBoolean("isPaused",false);
                            if(!isPaused2) {
                                String result = mCheckerManager.getmCheckerResponseManager().GetFinalSignalResponse();
                                checkerStatusInformer.OnCheckCompleted(MConstants.SIGNAL_CHECKER, result);
                                startOnClickListeners(MConstants.SIGNAL_CHECKER,result);
                            } else {
                                handler1.removeCallbacksAndMessages(null);
                            }
                            break;
                        case 2:
                            handler1.postDelayed(() -> {
                                SharedPreferences sharedPreferences1 = GlobalMainContext.getMainContext().getSharedPreferences("CheckInfo", Context.MODE_PRIVATE);
                                boolean isPaused1 = sharedPreferences1.getBoolean("isPaused",false);
                                if(!isPaused1) {
                                    String result = mCheckerManager.getmCheckerResponseManager().getmPublicDbCheckerResponse().getmCheckingStatus();
                                    checkerStatusInformer.OnCheckCompleted(MConstants.PUBLIC_DB_CHECKER, result);
                                    startOnClickListeners(MConstants.PUBLIC_DB_CHECKER,result);
                                } else {
                                    handler1.removeCallbacksAndMessages(null);
                                }
                            }, counter * 1000);
                            break;
                        case 3:
                            handler1.postDelayed(() -> {
                                SharedPreferences sharedPreferences12 = GlobalMainContext.getMainContext().getSharedPreferences("CheckInfo", Context.MODE_PRIVATE);
                                boolean isPaused12 = sharedPreferences12.getBoolean("isPaused",false);
                                if(!isPaused12) {
                                    String result = mCheckerManager.getmCheckerResponseManager().getmInternalDBCheckerResponse().getmCheckingStatus();
                                    checkerStatusInformer.OnCheckCompleted(MConstants.INTERNAL_DB_CHECKER, result);
                                    startOnClickListeners(MConstants.INTERNAL_DB_CHECKER,result);
                                } else {
                                    handler1.removeCallbacksAndMessages(null);
                                }
                            }, counter * 2 * 500 + 1000);
                            break;
                        case 4:
                                handler1.postDelayed(() -> {
                                    SharedPreferences sharedPreferences13 = GlobalMainContext.getMainContext().getSharedPreferences("CheckInfo", Context.MODE_PRIVATE);
                                    boolean isPaused13 = sharedPreferences13.getBoolean("isPaused",false);
                                    if(!isPaused13) {
                                        String result = mCheckerManager.getmCheckerResponseManager().getmNeighbourListCheckerResponse().getmCheckingStatus();
                                        checkerStatusInformer.OnCheckCompleted(MConstants.NEIGHBOUR_LIST_CHECKER, result);
                                        startOnClickListeners(MConstants.NEIGHBOUR_LIST_CHECKER,result);
                                    } else {
                                        handler1.removeCallbacksAndMessages(null);
                                    }
                                }, counter * 3 * 500 + 1000);
                                break;
                        case 5:
                            handler1.postDelayed(() -> {
                                SharedPreferences sharedPreferences14 = GlobalMainContext.getMainContext().getSharedPreferences("CheckInfo", Context.MODE_PRIVATE);
                                boolean isPaused14 = sharedPreferences14.getBoolean("isPaused",false);
                                if(!isPaused14) {
                                    String result = mCheckerManager.getmCheckerResponseManager().getmCellConsistencyCheckerResponse().getmCheckingStatus();
                                    checkerStatusInformer.OnCheckCompleted(MConstants.CELL_CONSISTENCY_CHECKER, mCheckerManager.getmCheckerResponseManager().getmCellConsistencyCheckerResponse().getmCheckingStatus());
                                    startOnClickListeners(MConstants.CELL_CONSISTENCY_CHECKER,result);
                                } else {
                                    handler1.removeCallbacksAndMessages(null);
                                }
                            }, counter * 4 * 500 + 1000);
                            break;
                        case 6:
                            handler1.postDelayed(() -> {
                                SharedPreferences sharedPreferences15 = GlobalMainContext.getMainContext().getSharedPreferences("CheckInfo", Context.MODE_PRIVATE);
                                boolean isPaused15 = sharedPreferences15.getBoolean("isPaused",false);
                                if(!isPaused15) {
                                    mCheckerManager.performOverallCheck();
                                    String response = mCheckerManager.getmCheckerResponseManager().getmOverallResponse().getmCheckingStatus();
                                    checkerStatusInformer.OnCheckCompleted(MConstants.OVERALL_CHECKER, response);

                                    mView.findViewById(R.id.progressBarCheck).setVisibility(View.INVISIBLE);
                                    mVibratorHelper.Vibrate(response);
                                    mView.findViewById(R.id.buttonRetake).setVisibility(View.VISIBLE);
                                    mView.findViewById(R.id.buttonPause).setVisibility(View.INVISIBLE);
                                    TextView textView = mView.findViewById(R.id.checkStartedText);
                                    textView.setText(R.string.CHECKSENDED);
                                } else {
                                    handler1.removeCallbacksAndMessages(null);
                                }
                            }, counter * 5 * 500);
                            break;
                    }
                } else return;
            }

            Runnable runnable = () -> {
                while (true) {
                    SharedPreferences sharedPreferences15 = GlobalMainContext.getMainContext().getSharedPreferences("CheckInfo", Context.MODE_PRIVATE);
                    boolean isPaused = sharedPreferences15.getBoolean("isPaused",false);
                    if(isPaused)
                        handler1.removeCallbacksAndMessages(null);
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();

        }, 5000);

        return false;
    }

    public void startOnClickListeners(String testDone, String result) {
        if(result.equals(MConstants.TEST_FAILED_RO)) {
            ImageView imageView = null;
            switch (testDone) {
                case MConstants.SIGNAL_CHECKER:
                    imageView = this.mView.findViewById(R.id.imageSignal);
                    break;
                case MConstants.PUBLIC_DB_CHECKER:
                    imageView = this.mView.findViewById(R.id.imagePBDB);
                    break;
                case MConstants.INTERNAL_DB_CHECKER:
                    imageView = this.mView.findViewById(R.id.imageINTDB);
                    break;
                case MConstants.NEIGHBOUR_LIST_CHECKER:
                    imageView = this.mView.findViewById(R.id.imageNeigh);
                    break;
                case MConstants.CELL_CONSISTENCY_CHECKER:
                    imageView = this.mView.findViewById(R.id.imageCell);
                    break;
                case MConstants.CONNECTIVITY_CHECKER:
                    imageView = this.mView.findViewById(R.id.conectivityImageView);
                    break;
            }
            imageView.setOnClickListener(v -> {
                DialogFragment infoFragment = new ResultInfoPopup(testDone);
                infoFragment.show(this.mFragmentManager,"infopop");
            });
        }

    }

    //endregion
}
