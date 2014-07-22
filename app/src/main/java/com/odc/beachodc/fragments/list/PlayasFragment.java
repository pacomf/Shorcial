package com.odc.beachodc.fragments.list;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.odc.beachodc.Playas;
import com.odc.beachodc.R;
import com.odc.beachodc.activities.EdicionPlaya;
import com.odc.beachodc.db.models.Playa;
import com.odc.beachodc.adapters.PlayasAdapter;
import com.odc.beachodc.fragments.VerPlayaFragment;
import com.odc.beachodc.utilities.Geo;
import com.odc.beachodc.utilities.ValidacionPlaya;
import com.odc.beachodc.webservices.Request;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Paco on 7/01/14.
 * Fragment que se encarga de Loguear al usuario, es el splash screen inicial de login
 */
public class PlayasFragment extends Fragment {

        ListView listView;
        ArrayList<Playa> playas;
        PlayasAdapter playasAdapter;

        public PlayasFragment() {
            // Se ejecuta antes que el onCreateView
            playas = new ArrayList<Playa>();
        }

        public void setPlayas (ArrayList<Playa> playas){
            this.playas = playas;

            if (listView != null) {
                Boolean search = false;
                try {
                    search = getActivity().getIntent().getExtras().getBoolean("isSearch");
                } catch (Exception e) {
                }

                if (search) {
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
            View rootView = inflater.inflate(R.layout.fragment_list_playas, container, false);
            // Empezar aqui a trabajar con la UI

            listView = (ListView) rootView.findViewById(R.id.listaPlayas);

            Boolean search = false;

            try {
                search = getActivity().getIntent().getExtras().getBoolean("isSearch");
            } catch (Exception e){}

            if (search){
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
                    // TODO: Redirigir a la pagina de VER PLAYA, ahora se esta redirigiendo a la de Editar
                    Intent intent = new Intent(getActivity(), Playas.class);
                    Playa item = (Playa) listView.getItemAtPosition(i);
                    ValidacionPlaya.playa = item;
                    ProgressDialog pd = ProgressDialog.show(getActivity(), getResources().getText(R.string.esperar), getResources().getText(R.string.esperar));
                    pd.setIndeterminate(false);
                    pd.setCancelable(true);
                    // TODO: Estas dos validaciones de cargadas hay que setearlas bien donde sea conveniente
                    Request.getTemp(getActivity(), item.latitud, item.longitud, pd);
                    ValidacionPlaya.cargadaImagenWeb=true;

                    Request.getComentariosPlaya(getActivity(), item.idserver, pd);
                    if (Geo.isNearToMe(item.latitud, item.longitud))
                        Request.getMensajesBotella(getActivity(), item.idserver, pd);
                    else
                        ValidacionPlaya.cargadosMensajesPlaya=true;
                    startActivity(intent);
                }
            });

            return rootView;
        }

}
