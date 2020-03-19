package com.example.licentav00;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.licentav00.Popups.CheckerPopUpDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        this.mSharedPreferences = getActivity().getSharedPreferences("CheckInfo", GlobalMainContext.getMainContext().MODE_PRIVATE);
        setOnClickListeners(inflaterView,getFragmentManager());
        final DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        ImageView hamburger = inflaterView.findViewById(R.id.hamburger);
        hamburger.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.LEFT));
        boolean isChecked = this.mSharedPreferences.getBoolean("checked",true);

        this.mCheckerFinish = new CheckerFinish(inflaterView,this.mCheckerManager,getFragmentManager());
        this.mCheckerStarter = new CheckerStarter(inflaterView,this.mCheckerManager, this.mCheckerFinish);

        SimpleDateFormat formattter = new SimpleDateFormat("yyyy-MM-dd ' ' HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String lastActivity = this.mSharedPreferences.getString("last",formattter.format(date));
        TextView lastActivityTV = inflaterView.findViewById(R.id.LastActivity);
        lastActivityTV.setText(lastActivity);
        SharedPreferences.Editor editor = this.mSharedPreferences.edit();
        editor.putString("last",formattter.format(date));
        editor.commit();


        if(isChecked) {
            this.mCheckerPopUpDialog = new CheckerPopUpDialog(this.mCheckerStarter,"FIRST");
            this.mCheckerPopUpDialog.show(this.getFragmentManager(),"checkerpopup");
        } else {
            mCheckerStarter.startChecker();
        }



        return inflaterView;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setOnClickListeners(final View inflatedView, final FragmentManager fragmentManager){
        ImageView imgInfo = inflatedView.findViewById(R.id.imageInfoCheck);
        imgInfo.setOnClickListener(v -> {
            mCheckerPopUpDialog = new CheckerPopUpDialog(mCheckerStarter,"IMAGE");
            mCheckerPopUpDialog.show(fragmentManager,"checkerpopup");
        });

        Button button = inflatedView.findViewById(R.id.buttonRetake);
        button.setOnClickListener(v -> {
            SharedPreferences.Editor editor = mSharedPreferences.edit();

            SimpleDateFormat formattter = new SimpleDateFormat("yyyy-MM-dd ' ' HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());

            editor.putString("last",formattter.format(date));
            editor.commit();

            TextView lastActivityTV = inflatedView.findViewById(R.id.LastActivity);
            String lastActivity = mSharedPreferences.getString("last",formattter.format(date));
            lastActivityTV.setText(lastActivity);

            v.setVisibility(View.INVISIBLE);
            setViewVisibility(inflatedView);
            mCheckerStarter.startChecker();
        });

        Button buttonStop = inflatedView.findViewById(R.id.buttonPause);
        buttonStop.setOnClickListener(v -> {
            v.setVisibility(View.INVISIBLE);
            SharedPreferences.Editor editor = this.mSharedPreferences.edit();
            editor.putBoolean("isPaused",true);
            editor.apply();
            Button buttonRetake = inflatedView.findViewById(R.id.buttonRetake);
            buttonRetake.setVisibility(View.VISIBLE);

            inflatedView.findViewById(R.id.progressBarCheck).setVisibility(View.INVISIBLE);
            TextView textAnnounce =  inflatedView.findViewById(R.id.checkStartedText);
            textAnnounce.setText(R.string.CHECKSTOPPED);
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        SharedPreferences.Editor editor = this.mSharedPreferences.edit();
        editor.putBoolean("isPaused",true);
        editor.apply();
    }


    //endregion


    //region Private Methods
    private void setViewVisibility(View inflatedView) {
        inflatedView.findViewById(R.id.progressBarCheck).setVisibility(View.VISIBLE);
        inflatedView.findViewById(R.id.checkStartedText).setVisibility(View.VISIBLE);

        inflatedView.findViewById(R.id.imageSignal).setVisibility(View.INVISIBLE);
        inflatedView.findViewById(R.id.imagePBDB).setVisibility(View.INVISIBLE);
        inflatedView.findViewById(R.id.imageINTDB).setVisibility(View.INVISIBLE);
        inflatedView.findViewById(R.id.imageNeigh).setVisibility(View.INVISIBLE);
        inflatedView.findViewById(R.id.imageCell).setVisibility(View.INVISIBLE);
        inflatedView.findViewById(R.id.finalResult).setVisibility(View.INVISIBLE);
        inflatedView.findViewById(R.id.conectivityImageView).setVisibility(View.INVISIBLE);
    }
    //endregion

}
