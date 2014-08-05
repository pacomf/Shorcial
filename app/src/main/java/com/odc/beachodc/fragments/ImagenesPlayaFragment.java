package com.odc.beachodc.fragments;


import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.facebook.widget.ProfilePictureView;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.odc.beachodc.R;
import com.odc.beachodc.db.models.Imagen;
import com.odc.beachodc.utilities.AnimateFirstDisplayListener;
import com.odc.beachodc.utilities.DescriptionAnimationSlider;
import com.odc.beachodc.utilities.Utilities;
import com.odc.beachodc.utilities.ValidacionPlaya;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


/**
 * Created by Paco on 7/01/14.
 * Fragment que se encarga de Loguear al usuario, es el splash screen inicial de login
 */
public class ImagenesPlayaFragment extends Fragment implements BaseSliderView.OnSliderClickListener{

    View rootView;
    TextView nombrePlaya, nombreAutor, comentario;
    ProfilePictureView autorProfile;
    private SliderLayout mSlider;
    Fragment fragment;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public ImagenesPlayaFragment() {
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
            rootView = inflater.inflate(R.layout.fragment_imagenes_playa, container, false);
        } catch (InflateException e) {}

        // Empezar aqui a trabajar con la UI

        nombrePlaya = (TextView) rootView.findViewById(R.id.nombreTV);
        nombreAutor = (TextView) rootView.findViewById(R.id.nombreAutorTV);
        comentario = (TextView) rootView.findViewById(R.id.comentarioTV);
        autorProfile = (ProfilePictureView) rootView.findViewById(R.id.fotoAutorImage);


        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/aSongforJenniferBold.ttf");
        nombrePlaya.setTypeface(tf);

        nombrePlaya.setText(ValidacionPlaya.playa.nombre);

        Imagen imagenI = new Imagen(Utilities.getUserIdFacebook(getActivity()), "1", "Perez", "Pablo", new Date(), "http://i.imgur.com/Oz5aDw9.jpg");

        //ValidacionPlaya.imagenes = new ArrayList<Imagen>();
        //ValidacionPlaya.imagenes.add(imagenI);

        mSlider = (SliderLayout) rootView.findViewById(R.id.slider);

        if ((ValidacionPlaya.imagenes != null) && (ValidacionPlaya.imagenes.size() > 0)){
            for (Imagen imagen : ValidacionPlaya.imagenes) {
                TextSliderView textSliderView = new TextSliderView(getActivity());
                // initialize a SliderLayout
                textSliderView
                        .description(Utilities.formatFechaNotHour(imagen.fecha))
                        .image(imagen.link)
                        .setScaleType(BaseSliderView.ScaleType.CenterInside)
                        .setOnSliderClickListener(this);

                textSliderView.getBundle().putString("link", imagen.link);
                textSliderView.getBundle().putString("comentario", imagen.comentario);
                textSliderView.getBundle().putString("idfb", imagen.idfbautor);
                textSliderView.getBundle().putString("nombrefb", imagen.nombreautor);

                mSlider.addSlider(textSliderView);
            }

            mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

            if (ValidacionPlaya.imagenes.size() > 1) {
                mSlider.setPresetTransformer(SliderLayout.Transformer.CubeIn);
                DescriptionAnimationSlider descriptionAnimationSlider = new DescriptionAnimationSlider();
                descriptionAnimationSlider.setParams(mSlider, autorProfile, nombreAutor, comentario);
                mSlider.setCustomAnimation(descriptionAnimationSlider);
                mSlider.setDuration(6000);
            } else {
                mSlider.stopAutoCycle();
                autorProfile.setProfileId(mSlider.getCurrentSlider().getBundle().get("idfb").toString());
                nombreAutor.setText(mSlider.getCurrentSlider().getBundle().get("nombrefb").toString());
                comentario.setText(mSlider.getCurrentSlider().getBundle().get("comentario").toString());
            }

        } else {
            // TODO: Si no hay ninguna imagen, que no salga el slider y salga la pantallita de NO HAY IMAGENES SE EL PRIMERO EN AÑADIR UNA
        }

        return rootView;
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        // Al hacer click sobre la imagen la abrimos en el Gallery de Android
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(slider.getBundle().get("link").toString()),"image/*");
        startActivity(intent);
    }

}
