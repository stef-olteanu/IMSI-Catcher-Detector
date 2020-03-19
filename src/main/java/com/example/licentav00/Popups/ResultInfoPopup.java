package com.example.licentav00.Popups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.licentav00.R;

import Utils.MConstants;

public class ResultInfoPopup extends DialogFragment {

    //region Private Members
    private String mTestDone;
    //endregion


    //region Constructor
    public ResultInfoPopup(String testDone) {
        this.mTestDone = testDone;
    }
    //endregion


    //region onCreate
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_result_info_popup,container,false);

        Button buttonOk = inflatedView.findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(v -> {
            getDialog().dismiss();
        });

        TextView resultTextView = inflatedView.findViewById(R.id.resultTextView);
        switch (this.mTestDone) {
            case MConstants.SIGNAL_CHECKER:
                resultTextView.setText(R.string.SIGNALINFOR);
                break;
            case MConstants.PUBLIC_DB_CHECKER:
                resultTextView.setText(R.string.PBDBINFO);
                break;
            case MConstants.INTERNAL_DB_CHECKER:
                resultTextView.setText(R.string.INTERNALINFO);
                break;
            case MConstants.NEIGHBOUR_LIST_CHECKER:
                resultTextView.setText(R.string.NEIGHINFO);
                break;
            case MConstants.CELL_CONSISTENCY_CHECKER:
                resultTextView.setText(R.string.CELLINFO);
                break;
            case MConstants.CONNECTIVITY_CHECKER:
                resultTextView.setText(R.string.CONNECTIVITYINFO);
                break;
        }



        return inflatedView;
    }
    //endregion
}
