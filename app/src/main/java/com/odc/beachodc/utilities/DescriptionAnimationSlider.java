package com.odc.beachodc.utilities;

import android.view.View;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.BaseAnimationInterface;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.facebook.widget.ProfilePictureView;

/**
 * Created by Paco on 05/08/2014.
 */
public class DescriptionAnimationSlider implements BaseAnimationInterface {

    private SliderLayout mSlider;
    ProfilePictureView foto;
    TextView nombre, comentario;

    public void setParams(SliderLayout mSlider, ProfilePictureView foto, TextView nombre, TextView comentario){
        this.mSlider = mSlider;
        this.foto = foto;
        this.nombre = nombre;
        this.comentario = comentario;
    }

    @Override
    public void onPrepareCurrentItemLeaveScreen(View current) {

    }

    @Override
    public void onPrepareNextItemShowInScreen(View next) {

    }


    @Override
    public void onCurrentItemDisappear(View view) {

    }

    @Override
    public void onNextItemAppear(View view) {
        try {
            foto.setProfileId(mSlider.getCurrentSlider().getBundle().get("idfb").toString());
            nombre.setText(mSlider.getCurrentSlider().getBundle().get("nombrefb").toString());
            comentario.setText(mSlider.getCurrentSlider().getBundle().get("comentario").toString());
        } catch (Exception e){

        }
    }

}