package com.example.licentav00;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import CallBacks.DatabaseReaderCallBack;
import DatabaseLogic.FirebaseHelper;
import Model.Cell;

public class PieChartActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        ImageView backImageView = findViewById(R.id.backImageView);
        backImageView.setOnClickListener(v -> {
          finish();
        });

        FirebaseHelper firebaseHelper = new FirebaseHelper();
        String[] cells = getResources().getStringArray(R.array.cellTypes);
        final int[] size = {0};
        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        for(String cell : cells) {
            firebaseHelper.getAllCells(new DatabaseReaderCallBack() {
                @Override
                public void OnCallBack(List<Cell> databaseCellList) {
                    if(databaseCellList.size() != 0) {
                        PieEntry pieEntry = new PieEntry(databaseCellList.size(), size[0]);
                        pieEntry.setLabel(cell);
                        pieEntries.add(pieEntry);
                    }


                    size[0]++;
                }
            },cell);
        }
        PieChart pieChart = findViewById(R.id.pieChart);
        pieChart.setVisibility(View.INVISIBLE);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                PieDataSet pieDataSet = new PieDataSet(pieEntries,"");
                PieData pieData = new PieData(pieDataSet);
                pieChart.setVisibility(View.VISIBLE);

                pieChart.setData(pieData);
                pieDataSet.setColors(ColorTemplate.PASTEL_COLORS);
                pieDataSet.setSliceSpace(1f);
                pieDataSet.setValueTextColor(Color.WHITE);
                pieDataSet.setValueTextSize(10f);
            }
        },2000);




    }
}
