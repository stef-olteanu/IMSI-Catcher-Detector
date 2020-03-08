package com.example.licentav00;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.licentav00.Popups.CellPopUpDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import Model.Cell;
import DatabaseLogic.FirebaseHelper;
import CallBacks.DatabaseReaderCallBack;

public class FragmentCellMap extends Fragment implements OnMapReadyCallback {
    //region Constructor
    public FragmentCellMap(){

        mDatabase = new FirebaseHelper();
    }

    //endregion

    //region Private Members
    private MapView mMapView;
    private FirebaseHelper mDatabase;
    private View mInflatedView;
    //endregion


    //region Public Methods

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



        mMapView = inflateView.findViewById(R.id.cellsMapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
        this.mInflatedView = inflateView;
        return inflateView;

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        Resources resources = getResources();
        String[] cellTypes = resources.getStringArray(R.array.cellTypes);
        for(String cell : cellTypes) {
            mDatabase.getAllCells(new DatabaseReaderCallBack() {
                @Override
                public void OnCallBack(List<Cell> databaseCellList) {
                    for (Cell cell : databaseCellList) {
                        LatLng cellLatLng = new LatLng(Double.parseDouble(cell.GetCellLat()), Double.parseDouble(cell.GetCellLong()));
                        String title = "Celula de baza";
                        float markerColor = BitmapDescriptorFactory.HUE_GREEN;
                        if(cell.getmCellStatus().equals("WARNING")){
                            title = "Posibil IMSI CATCHER";
                            markerColor = BitmapDescriptorFactory.HUE_ORANGE;
                        }
                        if(cell.getmCellStatus().equals("ALERT")){
                            title = "IMSI CATCHER";
                            markerColor = BitmapDescriptorFactory.HUE_RED;
                        }
                            googleMap.addMarker(new MarkerOptions()
                                    .position(cellLatLng)
                                    .title(title)
                                    .icon(BitmapDescriptorFactory.defaultMarker(markerColor)));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(cellLatLng));
                    }
                }
            }, cell);
        }
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
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onResume() {
        super.onResume();


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
}
