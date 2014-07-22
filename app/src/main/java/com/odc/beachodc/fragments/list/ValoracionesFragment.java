package com.odc.beachodc.fragments.list;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.odc.beachodc.R;
import com.odc.beachodc.activities.ValoracionPlaya;
import com.odc.beachodc.adapters.ComentariosAdapter;
import com.odc.beachodc.adapters.MensajesBotellasAdapter;
import com.odc.beachodc.db.models.Comentario;
import com.odc.beachodc.db.models.MensajeBotella;
import com.odc.beachodc.utilities.ValidacionPlaya;

import java.util.ArrayList;
import java.util.Date;
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
            View rootView = inflater.inflate(R.layout.fragment_list_valoraciones, container, false);
            // Empezar aqui a trabajar con la UI

            listView = (ListView) rootView.findViewById(R.id.listaValoraciones);

            Button nuevaValoracion = (Button) rootView.findViewById(R.id.nuevaValoracionBTN);
            nuevaValoracion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentV = new Intent(getActivity(), ValoracionPlaya.class);
                    startActivity(intentV);
                }
            });

            ComentariosAdapter comentariosAdapter = new ComentariosAdapter(getActivity(), ValidacionPlaya.comentariosPlaya);
            listView.setAdapter(comentariosAdapter);

            return rootView;
        }

}
