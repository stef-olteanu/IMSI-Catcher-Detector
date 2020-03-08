package Checkers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.licentav00.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

import Managers.CheckerManager;
import Utils.GlobalMainContext;

public class CheckerStarter {
    //region Members Declaration
    private CheckerManager mCheckerManager;
    private View mView;
    private CheckerFinish mCheckerFinish;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    //endregion


    //region Constructor
    public CheckerStarter(View view, CheckerManager checkerManager, CheckerFinish checkerFinish){
        this.mCheckerManager = checkerManager;
        this.mView = view;
        this.mCheckerFinish = checkerFinish;
        this.mSharedPreferences = GlobalMainContext.getMainContext().getSharedPreferences("CheckInfo", Context.MODE_PRIVATE);
        this.mEditor = this.mSharedPreferences.edit();
    }
    //endregion

    //region Public Methods
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void startChecker() {
        ProgressBar progressBar = this.mView.findViewById(R.id.progressBarCheck);
        progressBar.setVisibility(View.VISIBLE);

        TextView textCheckStarted = this.mView.findViewById(R.id.checkStartedText);
        textCheckStarted.setVisibility(View.VISIBLE);
        textCheckStarted.setText(R.string.CHECKSTARTED);

        Button buttonRetake = this.mView.findViewById(R.id.buttonRetake);
        buttonRetake.setVisibility(View.INVISIBLE);

        Button buttonStop = this.mView.findViewById(R.id.buttonPause);
        buttonStop.setVisibility(View.VISIBLE);

        mEditor.putBoolean("isPaused",false);
        mEditor.commit();

        Thread th = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void run() {
                mCheckerManager.RunCheckers();
            }
        });
        th.start();
        this.mCheckerFinish.onFinish();
    }

    //endregion
}
