package Listeners.OnClickListeners;

import android.os.Build;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.licentav00.R;

import Informers.CheckerStatusInformer;
import Managers.CheckerManager;
import Utils.MConstants;

public class CheckerStartOnClickListener implements View.OnClickListener {

    //region Private Members
    private CheckerManager mCheckerManager;
    private View mView;
    //endregion


    //region Constructor
    public CheckerStartOnClickListener(View view, CheckerManager checkerManager){
        this.mCheckerManager = checkerManager;
        this.mView = view;
    }
    //endregion

    //region Public Methods
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onClick(View v) {


        Button button =  mView.findViewById(R.id.checkerStartButtonView);
        button.setClickable(false);
        button.setBackground(mView.getResources().getDrawable(R.drawable.custom_button_gray));
        button.setTextColor(mView.getResources().getColor(R.color.gray));

        final ProgressBar progressBar = mView.findViewById(R.id.progressBarCheck);
        progressBar.setVisibility(View.VISIBLE);
        new CheckerStatusInformer(mView).OnCheckStarted(MConstants.SIGNAL_CHECKER);


        Thread th = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void run() {
                mCheckerManager.RunCheckers();
            }
        });
        th.start();

    }

    //endregion
}
