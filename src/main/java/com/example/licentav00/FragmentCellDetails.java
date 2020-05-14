package com.example.licentav00;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

import Model.Cell;
import Model.Device;


public class FragmentCellDetails extends Fragment implements OnMapReadyCallback{


    @TargetApi(Build.VERSION_CODES.P)
    @RequiresApi(api = Build.VERSION_CODES.P)
    public FragmentCellDetails(Context mContext) {
        mainContext = mContext;
        mDispozitiv  = new Device(mainContext);

        try {
            mCell = new Cell(mainContext);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FragmentCellDetails(Context context, Cell cell) {
        this.mainContext = context;
        this.mDispozitiv = new Device(this.mainContext);
        this.mCell = cell;
    }

    Context mainContext;
    Device mDispozitiv;
    Cell mCell;
    MapView mapView;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Nullable
    @Override
    /**
     * It will display information about the current cell and a map with its location.
     */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflateView =  inflater.inflate(R.layout.fragment_cell_details, container, false);

        final DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        ImageView hamburger = inflateView.findViewById(R.id.hamburger);
        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });


        TextView arfcnTextView = (TextView) inflateView.findViewById(R.id.arfcnTextView);
        if(mCell.GetArcn() != null)
            arfcnTextView.setText(mCell.GetArcn());


        TextView bsicTextView = (TextView) inflateView.findViewById(R.id.bsicTextView);
        if(mCell.GetBsic() != null)
            bsicTextView.setText(mCell.GetBsic());


        TextView cidTextView = (TextView) inflateView.findViewById(R.id.cidTextView);
        if(mCell.GetCid() != null)
            cidTextView.setText(mCell.GetCid());


        TextView lacTextView = (TextView) inflateView.findViewById(R.id.lacTextView);
        if(mCell.GetLac() != null)
            lacTextView.setText(mCell.GetLac());


        TextView mccTextView = (TextView) inflateView.findViewById(R.id.mccTextView);
        if(mCell.GetMcc() != null)
            mccTextView.setText(mCell.GetMcc());

        TextView mncTextView = (TextView) inflateView.findViewById(R.id.mncTextView);

        if(mCell.GetMnc() != null)
            mncTextView.setText(mCell.GetMnc());

        TextView operatorTextView = (TextView) inflateView.findViewById(R.id.operatorTextView);
        if(mCell.GetNetworkOperator() != null)
            operatorTextView.setText(mCell.GetNetworkOperator());

        TextView asuTextView = (TextView) inflateView.findViewById(R.id.asuTextVIew);
        if(mCell.GetAsuLevel() != null)
            asuTextView.setText(mCell.GetAsuLevel());

        TextView dbmTextView = (TextView) inflateView.findViewById(R.id.dbmTextView);
        if(mCell.GetSignalDbm() != null)
            dbmTextView.setText(mCell.GetSignalDbm());

        TextView levelTextView = (TextView) inflateView.findViewById(R.id.siglevelTextView);
        if(mCell.GetSignalLevel() != null)
            levelTextView.setText(mCell.GetSignalLevel());

        TextView timingTextView = (TextView) inflateView.findViewById(R.id.timingTextView);
        if(mCell.GetTimingAdvance() != null)
            timingTextView.setText(mCell.GetTimingAdvance());



        TextView latTextView = (TextView) inflateView.findViewById(R.id.latTextView);
        if(mCell.GetCellLat() != null)
            latTextView.setText(mCell.GetCellLat());


        TextView longTextView = (TextView) inflateView.findViewById(R.id.longTextView);
        if(mCell.GetCellLong() != null)
            longTextView.setText(mCell.GetCellLong());

        TextView addressTextView = (TextView) inflateView.findViewById(R.id.addressTextView);
        if(mCell.getmCellStatus() != "WARNING" && mCell.getmCellStatus() !=null)
            addressTextView.setText(mCell.GetCellAddress());
        else
            addressTextView.setText(R.string.alert);


        mapView = (MapView) inflateView.findViewById(R.id.mapView);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        
        return inflateView;
    }


    /**
     * In the moment the map is ready it will be showed.
     * @param googleMap is the actual map that will be used
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(mCell.getmCellStatus() != "WARNING") {
            LatLng cellToShow = new LatLng(Double.parseDouble(mCell.GetCellLat()), Double.parseDouble(mCell.GetCellLong()));
            googleMap.addMarker(new MarkerOptions()
                    .position(cellToShow)
                    .title("Pozitie celula curenta")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(cellToShow));
            float zoomLevel = (float) 12.0;
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cellToShow, zoomLevel));
        }else{
            LatLng cellToShow = new LatLng(Double.parseDouble(mDispozitiv.GetLatitude()), Double.parseDouble(mDispozitiv.GetLongitude()));
            googleMap.addMarker(new MarkerOptions()
                    .position(cellToShow)
                    .title("In apropiere de loc. dvs. se afla un IMSI CATCHER!")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(cellToShow));
            float zoomLevel = (float) 12.0;
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(zoomLevel));
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onResume() {
        super.onResume();
        Log.i("Info2:","this is resume");


        mapView.onResume();

    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
