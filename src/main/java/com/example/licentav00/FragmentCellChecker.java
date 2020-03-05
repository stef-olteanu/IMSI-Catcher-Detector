package com.example.licentav00;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.licentav00.Popups.CheckerPopUpDialog;
import com.google.api.Context;

import Listeners.OnClickListeners.CheckerFinishOnTouchListener;
import Listeners.OnClickListeners.CheckerStartOnClickListener;
import Managers.CheckerManager;
import Utils.GlobalMainContext;

public class FragmentCellChecker extends Fragment {
    //region Private Members
    CheckerManager mCheckerManager;
    CheckerPopUpDialog mCheckerPopUpDialog;
    SharedPreferences mSharedPreferences;
    //endregion

    //region Constructor
    public FragmentCellChecker(){
        this.mCheckerManager = new CheckerManager();
        this.mCheckerPopUpDialog = new CheckerPopUpDialog();

    }
    //endregion


    //region Public Methods
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflaterView =  inflater.inflate(R.layout.fragment_cell_checker, container,false);
        setOnClickListeners(inflaterView,this.getFragmentManager());
        final DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        ImageView hamburger = inflaterView.findViewById(R.id.hamburger);
        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        this.mSharedPreferences = getActivity().getSharedPreferences("CheckInfo", GlobalMainContext.getMainContext().MODE_PRIVATE);
        boolean isChecked = this.mSharedPreferences.getBoolean("checked",true);

        if(isChecked)
            this.mCheckerPopUpDialog.show(this.getFragmentManager(),"checkerpopup");
        return inflaterView;
    }

    private void setOnClickListeners(final View inflatedView, final FragmentManager fragmentManager){
        ImageView imgInfo = inflatedView.findViewById(R.id.imageInfoCheck);
        imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckerPopUpDialog.show(fragmentManager,"checkerpopup");
            }
        });
       // checkerStart.setOnClickListener(new CheckerStartOnClickListener(inflatedView, this.mCheckerManager));
        //checkerStart.setOnTouchListener(new CheckerFinishOnTouchListener(inflatedView,this.mCheckerManager));
    }


    //endregion
}
