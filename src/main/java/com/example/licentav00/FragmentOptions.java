package com.example.licentav00;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import Utils.GlobalMainContext;

import Utils.MConstants.*;

public class FragmentOptions extends Fragment {


    //region Constructor
    public FragmentOptions(){}
    //endregion

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflaterView =  inflater.inflate(R.layout.fragment_options, container,false);

        final DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        ImageView hamburger = inflaterView.findViewById(R.id.hamburger);
        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        RadioButton radioRO = inflaterView.findViewById(R.id.radioRO);
        RadioButton radioEN = inflaterView.findViewById(R.id.radioEN);
        RadioGroup radioGroup = inflaterView.findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AppLanguage",GlobalMainContext.getMainContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Intent refresh = new Intent(GlobalMainContext.getMainContext(), MainActivity.class);
                String lang = sharedPreferences.getString("Language", AppLanguages.RO_LANG);

                if(checkedId == R.id.radioRO && !lang.equals(AppLanguages.RO_LANG)){
                    GlobalMainContext.setAppLocale(AppLanguages.RO_LANG);
                    editor.putString("Language", AppLanguages.RO_LANG);
                    startActivity(refresh);
                }

                if(checkedId == R.id.radioEN && !lang.equals(AppLanguages.EN_LANG)) {
                    GlobalMainContext.setAppLocale(AppLanguages.EN_LANG);
                    editor.putString("Language", AppLanguages.EN_LANG);
                    startActivity(refresh);
                }

                editor.commit();


            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AppLanguage",GlobalMainContext.getMainContext().MODE_PRIVATE);
        String lang = sharedPreferences.getString("Language", AppLanguages.RO_LANG);

        if(lang.equals(AppLanguages.RO_LANG)) {
            radioRO.setChecked(true);
        }
        if(lang.equals(AppLanguages.EN_LANG)) {
            radioEN.setChecked(true);
        }


        return inflaterView;
    }
}
