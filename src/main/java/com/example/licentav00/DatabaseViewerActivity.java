package com.example.licentav00;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import Adapters.CellListAdapter;
import DatabaseLogic.FirebaseHelper;
import Model.Cell;
import Utils.MConstants;

public class DatabaseViewerActivity extends AppCompatActivity {
    //region Members Declaration
    private ListView mListView;
    private FirebaseHelper mFirebaseHelper;
    private ArrayList<Cell> mDatabaseList;
    //endregion

    //region Constructor
    public DatabaseViewerActivity(){
        this.mFirebaseHelper = new FirebaseHelper();
        this.mDatabaseList = new ArrayList<>();
    }
    //endregion

    //region onCreate

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_database_viewer);
        this.setOnClickListeners();

        this.mListView = findViewById(R.id.databaseListView);

        Resources resources = getResources();
        String[] cellTypes = resources.getStringArray(R.array.cellTypes);

        for(String cellType : cellTypes) {
            this.mFirebaseHelper.getAllCells(databaseCellList -> {
                for(Cell cell: databaseCellList){
                    mDatabaseList.add(cell);
                }
            },cellType);
        }

        Context context = this;
        ProgressBar progressBar = findViewById(R.id.progressList);
        final CellListAdapter[] cellListAdapter = new CellListAdapter[1];

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            cellListAdapter[0] = new CellListAdapter(context,R.layout.item_list_view,mDatabaseList);
            mListView.setAdapter(cellListAdapter[0]);
        },3000);


        EditText filterET = findViewById(R.id.editText);
        filterET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cellListAdapter[0].FilterData(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    //endregion


    //region Private Methods
    private void setOnClickListeners() {
        ImageView backImageView = findViewById(R.id.imageBack);
        backImageView.setOnClickListener(v ->{
            finish();
        });
    }
    //endregion
}
