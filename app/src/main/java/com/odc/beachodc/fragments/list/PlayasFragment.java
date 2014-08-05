package com.odc.beachodc.fragments.list;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.odc.beachodc.activities.Playas;
import com.odc.beachodc.R;
import com.odc.beachodc.db.models.Playa;
import com.odc.beachodc.adapters.PlayasAdapter;
import com.odc.beachodc.utilities.Geo;
import com.odc.beachodc.utilities.Utilities;
import com.odc.beachodc.utilities.ValidacionPlaya;
import com.odc.beachodc.webservices.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


/**
 * Created by Paco on 7/01/14.
 * Fragment que se encarga de Loguear al usuario, es el splash screen inicial de login
 */
public class PlayasFragment extends Fragment {

        ListView listView;
        ArrayList<Playa> playas;
        PlayasAdapter playasAdapter;
        Button recargar, otraVista;
        Fragment fragment;
        View rootView;
        GoogleMap mapa;
        private Map<Marker, Integer> markersMap;
        RelativeLayout layoutMapa;
        TextView noplayas;
        boolean isSearch;

        public PlayasFragment() {
            // Se ejecuta antes que el onCreateView
            playas = new ArrayList<Playa>();
            fragment = this;
            isSearch = false;
        }

        public void setPlayas (ArrayList<Playa> playas, boolean isSearch){
            this.isSearch = isSearch;
            setPlayas(playas);
        }

        public void setPlayas (ArrayList<Playa> playas){
            this.playas = playas;

            if ((playas == null) || (playas.size() == 0)){
                if (noplayas != null){
                    if (isSearch)
                        noplayas.setText(getString(R.string.no_busqueda));
                    else
                        noplayas.setText(getString(R.string.no_playas));

                    noplayas.setVisibility(View.VISIBLE);
                }
            } else {
                if (noplayas != null)
                    noplayas.setVisibility(View.GONE);
            }

            if (listView != null) {
                Boolean searchNear = false;
                try {
                    searchNear = getActivity().getIntent().getExtras().getBoolean("isSearchNear");
                } catch (Exception e) {
                }

                if (searchNear) {
                    try {
                        Double latitud = getActivity().getIntent().getExtras().getDouble("latitud");
                        Double longitud = getActivity().getIntent().getExtras().getDouble("longitud");
                        playasAdapter = new PlayasAdapter(getActivity(), Geo.orderByDistanceTo(playas, new LatLng(latitud, longitud)), latitud, longitud);
                    } catch (Exception e) {
                        playasAdapter = new PlayasAdapter(getActivity(), Geo.orderByDistance(playas));
                    }
                } else {
                    playasAdapter = new PlayasAdapter(getActivity(), Geo.orderByDistance(playas));
                }

                listView.setAdapter(playasAdapter);
                playasAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            if (rootView != null) {
                ViewGroup parent = (ViewGroup) rootView.getParent();
                if (parent != null)
                    parent.removeView(rootView);
            }
            try {
                rootView = inflater.inflate(R.layout.fragment_list_playas, container, false);
            } catch (InflateException e) {}
            // Empezar aqui a trabajar con la UI

            listView = (ListView) rootView.findViewById(R.id.listaPlayas);

            noplayas = (TextView) rootView.findViewById(R.id.noplayas);

            if ((playas == null) || (playas.size() == 0)){
                if (noplayas != null){
                    if (isSearch)
                        noplayas.setText(getString(R.string.no_busqueda));
                    else
                        noplayas.setText(getString(R.string.no_playas));

                    noplayas.setVisibility(View.VISIBLE);
                }
            } else {
                if (noplayas != null)
                    noplayas.setVisibility(View.GONE);
            }

            Boolean searchNear = false;

            try {
                searchNear = getActivity().getIntent().getExtras().getBoolean("isSearchNear");
            } catch (Exception e){}

            if (searchNear){
                try {
                    Double latitud = getActivity().getIntent().getExtras().getDouble("latitud");
                    Double longitud = getActivity().getIntent().getExtras().getDouble("longitud");
                    playasAdapter = new PlayasAdapter(getActivity(), Geo.orderByDistanceTo(playas, new LatLng(latitud, longitud)), latitud, longitud);
                } catch (Exception e){
                    playasAdapter = new PlayasAdapter(getActivity(), Geo.orderByDistance(playas));
                }
            } else {
                playasAdapter = new PlayasAdapter(getActivity(), Geo.orderByDistance(playas));
            }
            listView.setAdapter(playasAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Intent intent = new Intent(getActivity(), Playas.class);
                    Playa item = (Playa) listView.getItemAtPosition(i);
                    ValidacionPlaya.playa = item;
                    ValidacionPlaya.lanzadaVerPlaya = false;
                    ProgressDialog pd = ProgressDialog.show(getActivity(), getResources().getText(R.string.esperar), getResources().getText(R.string.esperar));
                    pd.setIndeterminate(false);
                    pd.setCancelable(true);

                    Request.getTemp(getActivity(), item.latitud, item.longitud, pd, intent);

                    Request.getComentariosPlaya(getActivity(), item.idserver, pd, intent);
                    if (Geo.isNearToMe(item.latitud, item.longitud))
                        Request.getMensajesBotella(getActivity(), item.idserver, pd, intent);
                    else
                        ValidacionPlaya.cargadosMensajesPlaya=true;
                }
            });

            recargar = (Button) rootView.findViewById(R.id.recargarBTN);

            if (isSearch)
                recargar.setVisibility(View.GONE);
            else {
                recargar.setVisibility(View.VISIBLE);
                recargar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Utilities.haveInternet(getActivity())) {
                            ValidacionPlaya.cargadosUltimosCheckins = true;
                            ProgressDialog pd = ProgressDialog.show(getActivity(), getResources().getText(R.string.esperar), getResources().getText(R.string.esperar));
                            pd.setIndeterminate(false);
                            pd.setCancelable(false);
                            pd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {
                                    if (ValidacionPlaya.playas != null) {
                                        setPlayas(ValidacionPlaya.playas);
                                        playasAdapter.notifyDataSetChanged();
                                    }
                                    if (layoutMapa.getVisibility() == View.VISIBLE) {
                                        setPlayasMapas();
                                    }
                                }
                            });
                            Request.getPlayasCercanas(getActivity(), pd);
                        } else {
                            Crouton.makeText(getActivity(), getString(R.string.no_internet), Style.ALERT).show();
                        }
                    }
                });
            }

            try {
                MapsInitializer.initialize(getActivity());
                mapa = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
                mapa.setMyLocationEnabled(true);
            } catch (GooglePlayServicesNotAvailableException e) {}

            markersMap = new HashMap();

            layoutMapa = (RelativeLayout) rootView.findViewById(R.id.layoutMapa);

            otraVista = (Button) rootView.findViewById(R.id.mapBTN);

            otraVista.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (layoutMapa.getVisibility() == View.VISIBLE){
                        layoutMapa.setVisibility(View.GONE);
                        otraVista.setText(getString(R.string.ver_mapa));
                    } else {
                        if ((playas == null) || (playas.size() == 0)){
                            if (isSearch){
                                Crouton.makeText(getActivity(), getString(R.string.no_busqueda), Style.ALERT).show();
                            } else {
                                if (Utilities.haveInternet(getActivity())) {
                                    ValidacionPlaya.cargadosUltimosCheckins = true;
                                    ProgressDialog pd = ProgressDialog.show(getActivity(), getResources().getText(R.string.esperar), getResources().getText(R.string.esperar));
                                    pd.setIndeterminate(false);
                                    pd.setCancelable(false);
                                    pd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                        @Override
                                        public void onDismiss(DialogInterface dialogInterface) {
                                            if (ValidacionPlaya.playas != null) {
                                                if (ValidacionPlaya.playas.size() == 0) {
                                                    noplayas.setVisibility(View.VISIBLE);
                                                    Crouton.makeText(getActivity(), getString(R.string.no_playas), Style.ALERT).show();
                                                } else {
                                                    setPlayas(ValidacionPlaya.playas);
                                                    playasAdapter.notifyDataSetChanged();
                                                    otraVista.setText(getString(R.string.ver_lista));
                                                    setPlayasMapas();
                                                    layoutMapa.setVisibility(View.VISIBLE);
                                                }
                                            }
                                        }
                                    });
                                    Request.getPlayasCercanas(getActivity(), pd);
                                } else {
                                    Crouton.makeText(getActivity(), getString(R.string.no_internet), Style.ALERT).show();
                                }
                            }
                        } else {
                            setPlayas(ValidacionPlaya.playas);
                            playasAdapter.notifyDataSetChanged();
                            otraVista.setText(getString(R.string.ver_lista));
                            setPlayasMapas();
                            layoutMapa.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });

            setPlayasMapas();

            return rootView;
        }

        public void setPlayasMapas(){
            mapa.clear();

            if (!playas.isEmpty()) {
                for (int i=0; i<playas.size(); i++) {
                    Playa playa = playas.get(i);
                    Marker marcador = mapa.addMarker(new MarkerOptions()
                            .position(new LatLng(playa.latitud, playa.longitud))
                            .title(playa.nombre)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                    markersMap.put(marcador, Integer.valueOf(i));
                }

                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(playas.get(0).latitud, playas.get(0).longitud), 9.0f);
                mapa.moveCamera(cameraUpdate);
            }

            mapa.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marcador) {
                    Integer posicion = markersMap.get(marcador);
                    if (posicion != null){
                        if (Utilities.haveInternet(getActivity())) {
                            Intent intent = new Intent(getActivity(), Playas.class);
                            ValidacionPlaya.playa = playas.get(posicion);
                            ValidacionPlaya.lanzadaVerPlaya = false;
                            ProgressDialog pd = ProgressDialog.show(getActivity(), getResources().getText(R.string.esperar), getResources().getText(R.string.esperar));
                            pd.setIndeterminate(false);
                            pd.setCancelable(true);

                            Request.getTemp(getActivity(), ValidacionPlaya.playa.latitud, ValidacionPlaya.playa.longitud, pd, intent);

                            Request.getComentariosPlaya(getActivity(), ValidacionPlaya.playa.idserver, pd, intent);
                            if (Geo.isNearToMe(ValidacionPlaya.playa.latitud, ValidacionPlaya.playa.longitud))
                                Request.getMensajesBotella(getActivity(), ValidacionPlaya.playa.idserver, pd, intent);
                            else
                                ValidacionPlaya.cargadosMensajesPlaya=true;
                        } else {
                            Crouton.makeText(getActivity(), getString(R.string.no_internet), Style.ALERT).show();
                        }
                    }
                }
            });
        }

}
