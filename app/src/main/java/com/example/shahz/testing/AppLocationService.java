package com.example.shahz.testing;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.util.List;

public class AppLocationService extends Service implements LocationListener {

    Location location;
    Context mcontext;
    protected LocationManager locationManager;
    private static final long MIN_DISTANCE_FOR_UPDATE = 10, MIN_TIME_FOR_UPDATE = 1000 * 60;
    IBinder binder = new LocalBinder();

    @Override
    public void onCreate() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class LocalBinder extends Binder{
        public AppLocationService getInstance(){
            return AppLocationService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public Location getLastKnownLocation() {
        final long MIN_DISTANCE_FOR_UPDATE = 10, MIN_TIME_FOR_UPDATE = 1000 * 60;
        Location bestLocation = null;
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGPSEnabled) {
            if (Permissions.getInstance(this).checkLocationPermission()) {
                locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, this);
                List<String> providers = locationManager.getProviders(true);
                for (String provider : providers) {

                    Location l = locationManager.getLastKnownLocation(provider);

                    if (l == null) {
                        continue;
                    }
                    if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                        bestLocation = l;

                    }
                }
            }
        } else {
            Log.e("check", "Gps is not enable");
        }
        return bestLocation;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
