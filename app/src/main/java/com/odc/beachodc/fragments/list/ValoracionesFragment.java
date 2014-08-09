package com.odc.beachodc.fragments.list;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.odc.beachodc.R;
import com.odc.beachodc.activities.ValoracionPlaya;
import com.odc.beachodc.adapters.ComentariosAdapter;
import com.odc.beachodc.adapters.MensajesBotellasAdapter;
import com.odc.beachodc.db.models.Comentario;
import com.odc.beachodc.db.models.MensajeBotella;
import com.odc.beachodc.utilities.Utilities;
import com.odc.beachodc.utilities.ValidacionPlaya;
import com.odc.beachodc.webservices.Request;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


/**
 * Created by Paco on 7/01/14.
 * Fragment que se encarga de Loguear al usuario, es el splash screen inicial de login
 */
public class ValoracionesFragment extends Fragment {

        ListView listView;
        RelativeLayout novaloraciones;

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
                    if (Utilities.isAnonymous(getActivity())){
                        Utilities.goToLoginAsking(getActivity());
                    } else {
                        if (Utilities.haveInternet(getActivity())) {
                            Intent intentV = new Intent(getActivity(), ValoracionPlaya.class);
                            startActivity(intentV);
                        } else {
                            Crouton.makeText(getActivity(), getString(R.string.no_internet), Style.ALERT).show();
                        }
                    }
                }
            });

            novaloraciones = (RelativeLayout) rootView.findViewById(R.id.novaloraciones);

            if ((ValidacionPlaya.comentariosPlaya == null) || (ValidacionPlaya.comentariosPlaya.size() == 0)){
               novaloraciones.setVisibility(View.VISIBLE);
            } else{
                ComentariosAdapter comentariosAdapter = new ComentariosAdapter(getActivity(), Utilities.orderByDateComentario(ValidacionPlaya.comentariosPlaya));
                listView.setAdapter(comentariosAdapter);
                novaloraciones.setVisibility(View.GONE);
            }

            Button updateValoraciones = (Button) rootView.findViewById(R.id.updateBTN);

            updateValoraciones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProgressDialog pd = ProgressDialog.show(getActivity(), getResources().getText(R.string.esperar), getResources().getText(R.string.esperar));
                    pd.setIndeterminate(false);
                    pd.setCancelable(true);
                    pd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            if ((ValidacionPlaya.comentariosPlaya == null) || (ValidacionPlaya.comentariosPlaya.size() == 0)){
                                novaloraciones.setVisibility(View.VISIBLE);
                            } else{
                                ComentariosAdapter comentariosAdapter = new ComentariosAdapter(getActivity(), Utilities.orderByDateComentario(ValidacionPlaya.comentariosPlaya));
                                listView.setAdapter(comentariosAdapter);
                                novaloraciones.setVisibility(View.GONE);
                            }
                        }
                    });
                    Request.getComentariosPlaya(getActivity(), ValidacionPlaya.playa.idserver, pd, null);
                }
            });

            return rootView;
        }

}
