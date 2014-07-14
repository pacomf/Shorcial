package com.odc.beachodc.fragments.list;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.odc.beachodc.R;
import com.odc.beachodc.adapters.MensajesBotellasAdapter;
import com.odc.beachodc.db.models.MensajeBotella;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Paco on 7/01/14.
 * Fragment que se encarga de Loguear al usuario, es el splash screen inicial de login
 */
public class ValoracionesFragment extends Fragment {

        ListView listView;

        public ValoracionesFragment() {
            // Se ejecuta antes que el onCreateView

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_list_mensajes_botellas, container, false);
            // Empezar aqui a trabajar con la UI

            listView = (ListView) rootView.findViewById(R.id.listaMensajesBotellas);

            List<MensajeBotella> mensajes = new ArrayList<MensajeBotella>();

            MensajeBotella mb = new MensajeBotella(getActivity(), true);

            mensajes.add(mb);
            mensajes.add(mb);
            mensajes.add(mb);
            mensajes.add(mb);
            mensajes.add(mb);
            mensajes.add(mb);


            MensajesBotellasAdapter mensajesBotellasAdapter = new MensajesBotellasAdapter(getActivity(), mensajes);
            listView.setAdapter(mensajesBotellasAdapter);

            return rootView;
        }

}
