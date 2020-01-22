package com.example.licentav00;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import Listeners.OnClickListeners.CheckerStartOnClickListener;
import Listeners.OnClickListeners.DialogOpenOnClickListener;
import Managers.CheckerManager;

public class FragmentCellChecker extends Fragment {
    //region Private Members

    //endregion

    //region Constructor
    public FragmentCellChecker(){ }
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

    private void setOnClickListeners(View inflatedView){
        ImageView warningImageView = inflatedView.findViewById(R.id.warningImageView);
        warningImageView.setOnClickListener(new DialogOpenOnClickListener(this));

        TextView warningTextView = inflatedView.findViewById(R.id.adviceTextView);
        warningTextView.setOnClickListener(new DialogOpenOnClickListener(this));

        TextView littleWarningTextView = inflatedView.findViewById(R.id.littleAdviceTextView);
        littleWarningTextView.setOnClickListener(new DialogOpenOnClickListener(this));

        Button checkerStart = inflatedView.findViewById(R.id.checkerStartButtonView);
        checkerStart.setOnTouchListener(new CheckerStartOnClickListener(inflatedView));


    }
    //endregion
}
