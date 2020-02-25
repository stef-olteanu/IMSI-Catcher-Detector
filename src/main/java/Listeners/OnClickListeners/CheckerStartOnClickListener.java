package Listeners.OnClickListeners;

import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;

import com.example.licentav00.R;

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
        this.mView.requestLayout();
        Thread th = new Thread() {
            @Override
            public void run() {
                ProgressBar progressBar = mView.findViewById(R.id.progressBarCheck);
                progressBar.setVisibility(View.VISIBLE);
            }
        };
        th.run();

        this.mCheckerManager.RunCheckers(new CheckerStatusInformer(this.mView),this.mView);
        return true;
    }

    //endregion
}
