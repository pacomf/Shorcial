package com.odc.beachodc.fragments.list;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.odc.beachodc.R;
import com.odc.beachodc.activities.EdicionPlaya;
import com.odc.beachodc.db.models.Playa;
import com.odc.beachodc.adapters.PlayasAdapter;
import com.odc.beachodc.utilities.Geo;
import com.odc.beachodc.utilities.ValidacionPlaya;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Paco on 7/01/14.
 * Fragment que se encarga de Loguear al usuario, es el splash screen inicial de login
 */
public class PlayasFragment extends Fragment {

        ListView listView;

        public PlayasFragment() {
            // Se ejecuta antes que el onCreateView

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_list_playas, container, false);
            // Empezar aqui a trabajar con la UI

            listView = (ListView) rootView.findViewById(R.id.listaPlayas);

            List<Playa> playas = new ArrayList<Playa>();

            Playa playa1 = new Playa(false);
            Playa playa2 = new Playa(false);
            Playa playa3 = new Playa(false);
            Playa playa4 = new Playa(false);
            Playa playa5 = new Playa(false);

            playas.add(playa1);
            playa2.latitud+=0.001;
            playa2.longitud+=0.00001;
            playas.add(playa2);
            playas.add(playa3);
            playa4.latitud+=0.002;
            playa4.longitud+=0.00002;
            playas.add(playa4);
            playas.add(playa5);


            PlayasAdapter playasAdapter = new PlayasAdapter(getActivity(), Geo.orderByDistance(playas));
            listView.setAdapter(playasAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // TODO: Redirigir a la pagina de VER PLAYA, ahora se esta redirigiendo a la de Editar
                    Intent intent = new Intent(getActivity(), EdicionPlaya.class);
                    intent.putExtra("nuevo", false);
                    Playa item = (Playa) listView.getItemAtPosition(i);
                    ValidacionPlaya.playa = item;
                    startActivity(intent);
                }
            });

            return rootView;
        }

}
