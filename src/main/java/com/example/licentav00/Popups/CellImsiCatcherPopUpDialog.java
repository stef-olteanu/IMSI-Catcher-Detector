package com.example.licentav00.Popups;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.licentav00.FragmentCellChecker;
import com.example.licentav00.MainActivity;
import com.example.licentav00.R;
import com.google.android.material.navigation.NavigationView;

import Managers.CheckerManager;
import Model.Cell;
import Utils.GlobalMainContext;

public class CellImsiCatcherPopUpDialog extends DialogFragment {
    //region Members declaration
    private CheckerManager mCheckerManager;
    private NavigationView mNavigationView;
    private FragmentManager mFragmentManager;
    //endregion


    //region Constructor
    public CellImsiCatcherPopUpDialog(CheckerManager checkerManager, NavigationView navigationView, FragmentManager fragmentManager) {
        this.mCheckerManager = checkerManager;
        this.mNavigationView = navigationView;
        this.mFragmentManager = fragmentManager;
    }

    //endregion

    //region Create View

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_cell_imsicatcher_popup,container,false);
        Button button = contentView.findViewById(R.id.okButton);
        button.setOnClickListener(v -> {
                    mNavigationView.setCheckedItem(R.id.nav_verify);
                    mFragmentManager.beginTransaction().replace(R.id.fragment_container, new FragmentCellChecker(mCheckerManager)).commit();
                    dismiss();
                }
        );
        return contentView;
    }

    //endregion


    //region Public methods

    //endregion

}
