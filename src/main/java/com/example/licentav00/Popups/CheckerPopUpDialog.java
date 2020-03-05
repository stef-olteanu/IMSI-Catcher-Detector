package com.example.licentav00.Popups;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.licentav00.R;

import Utils.GlobalMainContext;

public class CheckerPopUpDialog extends DialogFragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.activity_checker_info_popup,container,false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("CheckInfo", GlobalMainContext.getMainContext().MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        Button okButton = inflatedView.findViewById(R.id.okDismissButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
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

}
