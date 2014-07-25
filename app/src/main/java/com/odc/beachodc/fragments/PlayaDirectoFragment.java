package com.odc.beachodc.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.odc.beachodc.R;

import com.odc.beachodc.utilities.AnimateFirstDisplayListener;

import com.odc.beachodc.utilities.Utilities;
import com.odc.beachodc.utilities.ValidacionPlaya;



/**
 * Created by Paco on 7/01/14.
 * Fragment que se encarga de Loguear al usuario, es el splash screen inicial de login
 */
public class PlayaDirectoFragment extends Fragment {

    View rootView;
    Button recargarImagen;
    TextView nombrePlaya;
    ImageView webcam;
    Fragment fragment;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public PlayaDirectoFragment() {
        // Se ejecuta antes que el onCreateView
        fragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.fragment_directo_playa, container, false);
        } catch (InflateException e) {}

        // Empezar aqui a trabajar con la UI

        webcam = (ImageView) rootView.findViewById(R.id.webcamView);

        if ((ValidacionPlaya.playa.webcamURL != null) && (!ValidacionPlaya.playa.webcamURL.equals(""))) {
            try {
                Utilities.imageLoader.displayImage(ValidacionPlaya.playa.webcamURL, webcam);
            } catch (Throwable ex) {}
        }

        recargarImagen = (Button) rootView.findViewById(R.id.actualizarBTN);
        nombrePlaya =(TextView) rootView.findViewById(R.id.nombreTV);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/aSongforJenniferBold.ttf");
        nombrePlaya.setTypeface(tf);

        nombrePlaya.setText(ValidacionPlaya.playa.nombre);

        recargarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Utilities.imageLoader.displayImage(ValidacionPlaya.playa.webcamURL, webcam);
                } catch (Throwable ex) {}
            }
        });

        return rootView;
    }

}
