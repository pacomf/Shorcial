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
import com.odc.beachodc.db.models.Playa;
import com.odc.beachodc.utilities.Geo;
import com.odc.beachodc.utilities.Utilities;
import com.odc.beachodc.utilities.ValidacionPlaya;

import java.util.ArrayList;

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
        if (savedInstanceState != null){
            if (Utilities.imageLoader == null) {
                Utilities.setImageLoader(getApplicationContext());
            }
            if ((savedInstanceState.getSerializable("playa") != null) && (ValidacionPlaya.playa == null)) {
                ValidacionPlaya.playa = (Playa) savedInstanceState.getSerializable("playa");
            }
            if ((savedInstanceState.getSerializable("cargadaImagenes") != null)) {
                ValidacionPlaya.cargadaImagenes = (Boolean) savedInstanceState.getSerializable("cargadaImagenes");
            }
            if ((savedInstanceState.getSerializable("cargadaPlayas") != null)) {
                ValidacionPlaya.cargadaPlayas = (Boolean) savedInstanceState.getSerializable("cargadaPlayas");
            }
            if ((savedInstanceState.getSerializable("cargadaTemperatura") != null)) {
                ValidacionPlaya.cargadaTemperatura = (Boolean) savedInstanceState.getSerializable("cargadaTemperatura");
            }
            if ((savedInstanceState.getSerializable("cargadosComentarios") != null)) {
                ValidacionPlaya.cargadosComentarios = (Boolean) savedInstanceState.getSerializable("cargadosComentarios");
            }
            if ((savedInstanceState.getSerializable("cargadosMensajesPlaya") != null)) {
                ValidacionPlaya.cargadosMensajesPlaya = (Boolean) savedInstanceState.getSerializable("cargadosMensajesPlaya");
            }
            if ((savedInstanceState.getSerializable("cargadosUltimosCheckins") != null)) {
                ValidacionPlaya.cargadosUltimosCheckins = (Boolean) savedInstanceState.getSerializable("cargadosUltimosCheckins");
            }
            if ((savedInstanceState.getSerializable("comentariosPlaya") != null)) {
                ValidacionPlaya.comentariosPlaya = (ArrayList) savedInstanceState.getSerializable("comentariosPlaya");
            }
            if ((savedInstanceState.getSerializable("iconWeather") != null)) {
                ValidacionPlaya.iconWeather = (String) savedInstanceState.getSerializable("iconWeather");
            }
            if ((savedInstanceState.getSerializable("imagenes") != null)) {
                ValidacionPlaya.imagenes = (ArrayList) savedInstanceState.getSerializable("imagenes");
            }
            if ((savedInstanceState.getSerializable("lanzadaVerPlaya") != null)) {
                ValidacionPlaya.lanzadaVerPlaya = (Boolean) savedInstanceState.getSerializable("lanzadaVerPlaya");
            }
            if ((savedInstanceState.getSerializable("mensajesBotella") != null)) {
                ValidacionPlaya.mensajesBotella = (ArrayList) savedInstanceState.getSerializable("mensajesBotella");
            }
            if ((savedInstanceState.getSerializable("playas") != null)) {
                ValidacionPlaya.playas = (ArrayList) savedInstanceState.getSerializable("playas");
            }
            if ((savedInstanceState.getSerializable("playasCheckins") != null)) {
                ValidacionPlaya.playasCheckins = (ArrayList) savedInstanceState.getSerializable("playasCheckins");
            }
            if ((savedInstanceState.getSerializable("temperatura") != null)) {
                ValidacionPlaya.temperatura = (Double) savedInstanceState.getSerializable("temperatura");
            }
        }
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

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putSerializable("playa", ValidacionPlaya.playa);
        state.putSerializable("cargadaImagenes", ValidacionPlaya.cargadaImagenes);
        state.putSerializable("cargadaPlayas", ValidacionPlaya.cargadaPlayas);
        state.putSerializable("cargadaTemperatura", ValidacionPlaya.cargadaTemperatura);
        state.putSerializable("cargadosComentarios", ValidacionPlaya.cargadosComentarios);
        state.putSerializable("cargadosMensajesPlaya", ValidacionPlaya.cargadosMensajesPlaya);
        state.putSerializable("cargadosUltimosCheckins", ValidacionPlaya.cargadosUltimosCheckins);
        state.putSerializable("comentariosPlaya", ValidacionPlaya.comentariosPlaya);
        state.putSerializable("iconWeather", ValidacionPlaya.iconWeather);
        state.putSerializable("imagenes", ValidacionPlaya.imagenes);
        state.putSerializable("lanzadaVerPlaya", ValidacionPlaya.lanzadaVerPlaya);
        state.putSerializable("mensajesBotella", ValidacionPlaya.mensajesBotella);
        state.putSerializable("playas", ValidacionPlaya.playas);
        state.putSerializable("playasCheckins", ValidacionPlaya.playasCheckins);
        state.putSerializable("temperatura", ValidacionPlaya.temperatura);
    }

}