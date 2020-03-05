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

import Checkers.CheckerFinish;
import Checkers.CheckerStarter;
import Managers.CheckerManager;
import Utils.GlobalMainContext;

public class FragmentCellChecker extends Fragment {
    //region Private Members
    private CheckerManager mCheckerManager;
    private CheckerPopUpDialog mCheckerPopUpDialog;
    private SharedPreferences mSharedPreferences;
    private CheckerStarter mCheckerStarter;
    private CheckerFinish mCheckerFinish;
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

        this.mCheckerFinish = new CheckerFinish(inflaterView,this.mCheckerManager);
        this.mCheckerStarter = new CheckerStarter(inflaterView,this.mCheckerManager, this.mCheckerFinish);


        if(isChecked) {
            this.mCheckerPopUpDialog = new CheckerPopUpDialog(this.mCheckerStarter,"FIRST");
            this.mCheckerPopUpDialog.show(this.getFragmentManager(),"checkerpopup");
        } else {
            mCheckerStarter.startChecker();
        }


        return inflaterView;
    }

    private void setOnClickListeners(final View inflatedView, final FragmentManager fragmentManager){
        ImageView imgInfo = inflatedView.findViewById(R.id.imageInfoCheck);
        imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckerPopUpDialog = new CheckerPopUpDialog(mCheckerStarter,"IMAGE");
                mCheckerPopUpDialog.show(fragmentManager,"checkerpopup");
            }
        });

        Button button = inflatedView.findViewById(R.id.buttonRetake);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.INVISIBLE);
                inflatedView.findViewById(R.id.progressBarCheck).setVisibility(View.VISIBLE);
                inflatedView.findViewById(R.id.checkStartedText).setVisibility(View.VISIBLE);

                inflatedView.findViewById(R.id.imageSignal).setVisibility(View.INVISIBLE);
                inflatedView.findViewById(R.id.imagePBDB).setVisibility(View.INVISIBLE);
                inflatedView.findViewById(R.id.imageINTDB).setVisibility(View.INVISIBLE);
                inflatedView.findViewById(R.id.imageNeigh).setVisibility(View.INVISIBLE);
                inflatedView.findViewById(R.id.imageCell).setVisibility(View.INVISIBLE);
                inflatedView.findViewById(R.id.finalResult).setVisibility(View.INVISIBLE);
                mCheckerStarter.startChecker();
            }
        });
    }



    //endregion
}
