package com.odc.beachodc.fragments;


import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.odc.beachodc.R;
import com.odc.beachodc.activities.ValoracionPlaya;
import com.odc.beachodc.utilities.BitmapLruCache;
import com.odc.beachodc.utilities.ValidacionPlaya;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


/**
 * Created by Paco on 7/01/14.
 * Fragment que se encarga de Loguear al usuario, es el splash screen inicial de login
 */
public class PlayaDirectoFragment extends Fragment {

    View rootView;
    Button recargarImagen;
    TextView nombrePlaya;
    NetworkImageView webcam;
    Fragment fragment;

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

        webcam = (NetworkImageView) rootView.findViewById(R.id.webcamView);

        if ((ValidacionPlaya.playa.webcamURL != null) && (!ValidacionPlaya.playa.webcamURL.equals(""))) {
            try {
                ImageLoader.ImageCache imageCache = new BitmapLruCache();
                ImageLoader imageLoader = new ImageLoader(Volley.newRequestQueue(getActivity()), imageCache);
                webcam.setErrorImageResId(R.drawable.webcamloading);
                webcam.setImageUrl(ValidacionPlaya.playa.webcamURL, imageLoader);
            } catch (Exception e){}
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
                    FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
                    fragTransaction.detach(fragment);
                    fragTransaction.attach(fragment);
                    fragTransaction.commit();
                } catch (Exception e){}
            }
        });

        return rootView;
    }

}
