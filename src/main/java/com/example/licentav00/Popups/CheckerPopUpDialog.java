package com.example.licentav00.Popups;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.licentav00.R;

import Checkers.CheckerStarter;
import Utils.GlobalMainContext;

public class CheckerPopUpDialog extends DialogFragment implements DialogInterface.OnDismissListener {

    //region Members declarations
    private CheckerStarter mCheckerStarter;
    private String mOpenType;
    //endregion


    //region Constructor
    public CheckerPopUpDialog(CheckerStarter checkerStarter, String openType) {
        this.mCheckerStarter = checkerStarter;
        this.mOpenType = openType;
    }
    //endregion


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_checker_info_popup,container,false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("CheckInfo", GlobalMainContext.getMainContext().MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        Button okButton = inflatedView.findViewById(R.id.okDismissButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                if(mOpenType.equals("FIRST"))
                    mCheckerStarter.startChecker();
        }
        });


        CheckBox checkBox = inflatedView.findViewById(R.id.checkBox);
        boolean isChecked = sharedPreferences.getBoolean("checked",true);
        if(isChecked) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("checked",isChecked);
                editor.commit();
            }
        });


        return inflatedView;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if(mOpenType.equals("FIRST"))
            mCheckerStarter.startChecker();
    }
}
