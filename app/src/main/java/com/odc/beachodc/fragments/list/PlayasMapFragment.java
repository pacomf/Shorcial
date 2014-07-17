package com.odc.beachodc.fragments.list;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.odc.beachodc.R;
import com.odc.beachodc.activities.EdicionPlaya;
import com.odc.beachodc.adapters.PlayasAdapter;
import com.odc.beachodc.db.models.Playa;
import com.odc.beachodc.utilities.Geo;
import com.odc.beachodc.utilities.ValidacionPlaya;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Paco on 7/01/14.
 * Fragment que se encarga de Loguear al usuario, es el splash screen inicial de login
 */
public class PlayasMapFragment extends Fragment {

        View rootView;
        GoogleMap mapa;
        private Map<Marker, Integer> markersMap;
        ArrayList<Playa> playas;

        public PlayasMapFragment() {
            // Se ejecuta antes que el onCreateView
            playas = new ArrayList<Playa>();
        }

        public void setPlayas (ArrayList<Playa> playas){
            this.playas = playas;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            if (rootView != null) {
                ViewGroup parent = (ViewGroup) rootView.getParent();
                if (parent != null)
                    parent.removeView(rootView);
            }
            try {
                rootView = inflater.inflate(R.layout.fragment_map_playas, container, false);
            } catch (InflateException e) {}
            // Empezar aqui a trabajar con la UI

            try {
                MapsInitializer.initialize(getActivity());
                mapa = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
                mapa.setMyLocationEnabled(true);
            } catch (GooglePlayServicesNotAvailableException e) {}

            markersMap = new HashMap();

            mapa.clear();

            if (!playas.isEmpty()) {
                for (int i=0; i<playas.size(); i++) {
                    Playa playa = playas.get(i);
                    Marker marcador = mapa.addMarker(new MarkerOptions()
                            .position(new LatLng(playa.latitud, playa.longitud))
                            .title(playa.nombre)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                    markersMap.put(marcador, Integer.valueOf(i));
                }

                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(playas.get(0).latitud, playas.get(0).longitud), 9.0f);
                mapa.moveCamera(cameraUpdate);
            }

            mapa.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marcador) {
                    Integer posicion = markersMap.get(marcador);
                    System.out.println("Posicion: " + posicion);
                    if (posicion != null){
                        Intent intent = new Intent(getActivity(), EdicionPlaya.class);
                        intent.putExtra("nuevo", false);
                        ValidacionPlaya.playa = playas.get(posicion);
                        startActivity(intent);
                    }
                }
            });

            return rootView;
        }

}
