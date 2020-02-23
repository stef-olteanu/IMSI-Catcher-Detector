package com.example.licentav00;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import Utils.GlobalMainContext;

public class FragmentOptions extends Fragment {


    //region Constructor
    public FragmentOptions(){}
    //endregion

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflaterView =  inflater.inflate(R.layout.fragment_options, container,false);

        RadioButton radioRO = inflaterView.findViewById(R.id.radioRO);
        RadioButton radioEN = inflaterView.findViewById(R.id.radioEN);
        RadioGroup radioGroup = inflaterView.findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SharedPreferences sharedPreferences = getActivity().getPreferences(GlobalMainContext.getMainContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Intent refresh = new Intent(GlobalMainContext.getMainContext(), MainActivity.class);
                String lang = sharedPreferences.getString("Language","ro");

                if(checkedId == R.id.radioRO && !lang.equals("ro")){
                    GlobalMainContext.setAppLocale("ro");
                    editor.putString("Language","ro");
                    startActivity(refresh);
                }

                if(checkedId == R.id.radioEN && !lang.equals("en")) {
                    GlobalMainContext.setAppLocale("en");
                    editor.putString("Language","en");
                    startActivity(refresh);
                }

                editor.commit();


            }
        });

        SharedPreferences sharedPreferences = getActivity().getPreferences(GlobalMainContext.getMainContext().MODE_PRIVATE);
        String lang = sharedPreferences.getString("Language","ro");

        if(lang.equals("ro")) {
            radioRO.setChecked(true);
        }
        if(lang.equals("en")) {
            radioEN.setChecked(true);
        }


        return inflaterView;
    }
}
