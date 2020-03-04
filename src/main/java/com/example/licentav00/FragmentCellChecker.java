package com.example.licentav00;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import Informers.CheckerStatusInformer;
import Listeners.OnClickListeners.CheckerFinishOnTouchListener;
import Listeners.OnClickListeners.CheckerStartOnClickListener;
import Listeners.OnClickListeners.DialogOpenOnClickListener;
import Managers.CheckerManager;
import Utils.MConstants;

public class FragmentCellChecker extends Fragment {
    //region Private Members
    CheckerManager mCheckerManager;
    //endregion

    //region Constructor
    public FragmentCellChecker(){
        this.mCheckerManager = new CheckerManager();
    }
    //endregion


    //region Public Methods
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflaterView =  inflater.inflate(R.layout.fragment_cell_checker, container,false);
        setOnClickListeners(inflaterView);

        return inflaterView;
    }

    private void setOnClickListeners(final View inflatedView){
        ImageView warningImageView = inflatedView.findViewById(R.id.warningImageView);
        warningImageView.setOnClickListener(new DialogOpenOnClickListener(this));

        TextView warningTextView = inflatedView.findViewById(R.id.adviceTextView);
        warningTextView.setOnClickListener(new DialogOpenOnClickListener(this));

        TextView littleWarningTextView = inflatedView.findViewById(R.id.littleAdviceTextView);
        littleWarningTextView.setOnClickListener(new DialogOpenOnClickListener(this));

        final Button checkerStart = inflatedView.findViewById(R.id.checkerStartButtonView);
        checkerStart.setOnClickListener(new CheckerStartOnClickListener(inflatedView, this.mCheckerManager));
        checkerStart.setOnTouchListener(new CheckerFinishOnTouchListener(inflatedView,this.mCheckerManager));
    }


    //endregion
}
