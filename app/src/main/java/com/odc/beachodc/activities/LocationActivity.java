package com.odc.beachodc.activities;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.odc.beachodc.utilities.Geo;

/**
 * Created by Paco on 09/08/2014.
 */
public class LocationActivity extends FragmentActivity implements GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener,
        LocationListener {
    private LocationClient mLocationClient;
    private LocationRequest mLocationRequest;
    private boolean firstStarted = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(this,this,this);
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(30000);
        mLocationRequest.setFastestInterval(1000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS)
            mLocationClient.connect();
    }

    @Override
    public void onResume(){
        super.onResume();
        if(!firstStarted && mLocationClient.isConnected()){
            startUpdates();
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        if(mLocationClient.isConnected())
            mLocationClient.removeLocationUpdates(this);
    }

    @Override
    protected void onStop() {
        if(mLocationClient.isConnected())
            mLocationClient.disconnect();
        firstStarted = true;
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        startUpdates();
        firstStarted = false;
    }

    private void startUpdates(){
        mLocationClient.requestLocationUpdates(mLocationRequest,this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Geo.myLocation = location;
    }

    @Override
    public void onDisconnected(){
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }
}