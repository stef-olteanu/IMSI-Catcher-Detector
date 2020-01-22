package Listeners;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

public class DeviceLocationListener implements LocationListener {
    //region Constructor
    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    public DeviceLocationListener(Context context) {
        mContext = context;
        getLocation();
    }

    //endregion


    //region Private Members
    private Context mContext;
    private Location mLocation;
    private LocationManager mLocationManager;
    private double mLat;
    private double mLon;


    //endregion


    //region Public Methods
    @RequiresApi(api = Build.VERSION_CODES.M)
    public Location getLocation() {
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if (mContext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && mContext.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 60 * 1, 10, this);
            if(mLocationManager != null){
                mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(mLocation != null){
                    mLat = mLocation.getLatitude();
                    mLon = mLocation.getLongitude();
                }
            }
        }

        return null;
    }


    public double getmLat() {
        if(mLocation != null)
            mLat = mLocation.getLatitude();
        return mLat;
    }

    public double getmLon() {
        if(mLocation != null)
            mLon = mLocation.getLongitude();
        return mLon;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    //endregion
}
