package com.example.licentav00;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.licentav00.Popups.CellPopUpDialog;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import Listeners.DeviceLocationListener;
import Model.Cell;
import DatabaseLogic.FirebaseHelper;
import CallBacks.DatabaseReaderCallBack;
import Utils.GlobalMainContext;
import Utils.MConstants;

public class FragmentCellMap extends Fragment implements OnMapReadyCallback {

    //region Private Members
    private MapView mMapView;
    private FirebaseHelper mDatabase;
    private View mInflatedView;
    private String mActiveChoice;
    private GoogleMap mGoogleMap;
    private ArrayList<Cell> mCellList;
    //endregion


    //region Constructor
    public FragmentCellMap(){
        mDatabase = new FirebaseHelper();
        mCellList = new ArrayList<>();
    }

    //endregion


    //region Public Methods

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflateView = inflater.inflate(R.layout.fragment_cell_map, container, false);
        final DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        ImageView hamburger = inflateView.findViewById(R.id.hamburger);
        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        this.mActiveChoice = MConstants.Map_Choices.ALL;
        TextView allTextView = inflateView.findViewById(R.id.allTextView);
        allTextView.setTextColor(getResources().getColor(R.color.green));
        this.setOnClickListeners(inflateView);


        mMapView = inflateView.findViewById(R.id.cellsMapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
        this.mInflatedView = inflateView;
        return inflateView;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        Resources resources = getResources();
        String[] cellTypes = resources.getStringArray(R.array.cellTypes);
        if(mCellList.size() == 0) {
            for (String cell : cellTypes) {
                mDatabase.getAllCells(new DatabaseReaderCallBack() {
                    @Override
                    public void OnCallBack(List<Cell> databaseCellList) {
                        for (Cell cell : databaseCellList) {
                            mCellList.add(cell);
                           addToMap(cell,googleMap);
                        }
                    }
                }, cell);
            }
        } else {
            for (Cell cell : mCellList) {
                addToMap(cell,googleMap);
            }
        }
        DeviceLocationListener locationListener = new DeviceLocationListener(GlobalMainContext.getMainContext());
        locationListener.getLocation();
        LatLng celllatlon = new LatLng(locationListener.getmLat(), locationListener.getmLon());
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(celllatlon));

        float zoomLevel = (float) 10.0;
        googleMap.animateCamera(CameraUpdateFactory.zoomTo( zoomLevel ));



        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                double lat = marker.getPosition().latitude;
                double lon = marker.getPosition().longitude;

                mDatabase.getCellByLatLon(new DatabaseReaderCallBack() {
                    @Override
                    public void OnCallBack(List<Cell> databaseCellList) {
                        Cell returnedCell = databaseCellList.get(0);
                        CellPopUpDialog cellDialog = new CellPopUpDialog(returnedCell);
                        cellDialog.show(getFragmentManager(), "CellPopUpDialog");
                    }
                }, lat, lon);
                return false;
            }
        });
        mGoogleMap = googleMap;
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onResume() {
        super.onResume();

        if(mGoogleMap != null) {
            mGoogleMap.clear();
            this.onMapReady(mGoogleMap);
        }

        mMapView.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    //endregion

    //region Private Methods
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void setOnClickListeners(View inflatedView) {
        TextView goodTextView = inflatedView.findViewById(R.id.goodTextView);
        TextView warningTextView = inflatedView.findViewById(R.id.warningTextView);
        TextView alertTextView = inflatedView.findViewById(R.id.alertTextView);
        TextView allTextView = inflatedView.findViewById(R.id.allTextView);

        goodTextView.setOnClickListener(l -> {
            TextView activeTextView = null;
            activeTextView = setActiveTextView(activeTextView,inflatedView);
            activeTextView.setTextColor(getResources().getColor(R.color.blue));

            goodTextView.setTextColor(getResources().getColor(R.color.green));
            mActiveChoice = MConstants.Map_Choices.GOOD;
            this.onResume();
        });

        warningTextView.setOnClickListener(l -> {
            TextView activeTextView = null;
            activeTextView = setActiveTextView(activeTextView,inflatedView);
            activeTextView.setTextColor(getResources().getColor(R.color.blue));

            warningTextView.setTextColor(getResources().getColor(R.color.green));
            mActiveChoice = MConstants.Map_Choices.WARNING;
            this.onResume();
        });

        alertTextView.setOnClickListener(l -> {
            TextView activeTextView = null;
            activeTextView = setActiveTextView(activeTextView,inflatedView);
            activeTextView.setTextColor(getResources().getColor(R.color.blue));

            alertTextView.setTextColor(getResources().getColor(R.color.green));
            mActiveChoice = MConstants.Map_Choices.ALERT;
            this.onResume();
        });

        allTextView.setOnClickListener(l -> {
            TextView activeTextView = null;
            activeTextView = setActiveTextView(activeTextView,inflatedView);
            activeTextView.setTextColor(getResources().getColor(R.color.blue));

            allTextView.setTextColor(getResources().getColor(R.color.green));
            mActiveChoice = MConstants.Map_Choices.ALL;
            this.onResume();
        });


    }

    private TextView setActiveTextView(TextView activeTextView, View inflatedView) {
        switch (mActiveChoice) {
            case MConstants.Map_Choices.GOOD:
                activeTextView = inflatedView.findViewById(R.id.goodTextView);
                break;
            case MConstants.Map_Choices.WARNING:
                activeTextView = inflatedView.findViewById(R.id.warningTextView);
                break;
            case MConstants.Map_Choices.ALERT:
                activeTextView = inflatedView.findViewById(R.id.alertTextView);
                break;
            case MConstants.Map_Choices.ALL:
                activeTextView = inflatedView.findViewById(R.id.allTextView);
                break;
        }
        return activeTextView;
    }

    private void addToMap(Cell cell, GoogleMap googleMap) {
        LatLng cellLatLng = new LatLng(Double.parseDouble(cell.GetCellLat()), Double.parseDouble(cell.GetCellLong()));
        String title = "Celula de baza";
        float markerColor = BitmapDescriptorFactory.HUE_GREEN;
        if (cell.getmCellStatus().equals("WARNING")) {
            title = "Posibil IMSI CATCHER";
            markerColor = BitmapDescriptorFactory.HUE_ORANGE;
        }
        if (cell.getmCellStatus().equals("ALERT")) {
            title = "IMSI CATCHER";
            markerColor = BitmapDescriptorFactory.HUE_RED;
        }
        if (mActiveChoice.equals(MConstants.Map_Choices.ALL)) {
            googleMap.addMarker(new MarkerOptions()
                    .position(cellLatLng)
                    .title(title)
                    .icon(BitmapDescriptorFactory.defaultMarker(markerColor)));
        } else if (mActiveChoice.equals(MConstants.Map_Choices.GOOD)) {
            if (cell.getmCellStatus().equals("GOOD")) {
                googleMap.addMarker(new MarkerOptions()
                        .position(cellLatLng)
                        .title(title)
                        .icon(BitmapDescriptorFactory.defaultMarker(markerColor)));
            }
        } else if (mActiveChoice.equals(MConstants.Map_Choices.WARNING)) {
            if (cell.getmCellStatus().equals("WARNING")) {
                googleMap.addMarker(new MarkerOptions()
                        .position(cellLatLng)
                        .title(title)
                        .icon(BitmapDescriptorFactory.defaultMarker(markerColor)));
            }
        } else if (mActiveChoice.equals(MConstants.Map_Choices.ALERT)) {
            if (cell.getmCellStatus().equals("ALERT")) {
                googleMap.addMarker(new MarkerOptions()
                        .position(cellLatLng)
                        .title(title)
                        .icon(BitmapDescriptorFactory.defaultMarker(markerColor)));
            }
        }
    }
    //endregion

}
