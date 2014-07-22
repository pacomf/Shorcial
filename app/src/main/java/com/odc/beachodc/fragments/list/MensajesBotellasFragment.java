package com.odc.beachodc.fragments.list;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.facebook.android.Util;
import com.odc.beachodc.R;
import com.odc.beachodc.activities.EdicionPlaya;
import com.odc.beachodc.activities.NuevoMensajeBotellaPlaya;
import com.odc.beachodc.adapters.MensajesBotellasAdapter;
import com.odc.beachodc.adapters.PlayasAdapter;
import com.odc.beachodc.db.models.MensajeBotella;
import com.odc.beachodc.db.models.Playa;
import com.odc.beachodc.utilities.Geo;
import com.odc.beachodc.utilities.Utilities;
import com.odc.beachodc.utilities.ValidacionPlaya;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Paco on 7/01/14.
 * Fragment que se encarga de Loguear al usuario, es el splash screen inicial de login
 */
public class MensajesBotellasFragment extends Fragment {

        ListView listView;
        Button lanzarMensaje;

        public MensajesBotellasFragment() {
            // Se ejecuta antes que el onCreateView

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_list_mensajes_botellas, container, false);
            // Empezar aqui a trabajar con la UI

            listView = (ListView) rootView.findViewById(R.id.listaMensajesBotellas);


            MensajesBotellasAdapter mensajesBotellasAdapter = new MensajesBotellasAdapter(getActivity(), Utilities.orderByDateMensajeBotella(ValidacionPlaya.mensajesBotella));
            listView.setAdapter(mensajesBotellasAdapter);

            lanzarMensaje = (Button) rootView.findViewById(R.id.nuevoMensajeBTN);
            lanzarMensaje.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentM = new Intent(getActivity(), NuevoMensajeBotellaPlaya.class);
                    startActivity(intentM);
                }
            });

            return rootView;
        }

}
