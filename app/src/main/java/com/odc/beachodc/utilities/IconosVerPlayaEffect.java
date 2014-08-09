package com.odc.beachodc.utilities;

import android.widget.ImageView;

import com.facebook.rebound.BaseSpringSystem;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;

/**
 * Created by Paco on 24/07/2014.
 */
public class IconosVerPlayaEffect {

    public static Spring mScaleSpringBA;
    public static Spring mScaleSpringDA;
    public static Spring mScaleSpringL;
    public static Spring mScaleSpringTA;
    public static Spring mScaleSpringR;
    public static Spring mScaleSpringH;
    public static Spring mScaleSpringS;
    public static Spring mScaleSpringC;
    public static Spring mScaleSpringD;
    public static Spring mScaleSpringSO;
    public static Spring mScaleSpringP;

    public static BaseSpringSystem mSpringSystem;

    public static IconoSpringListener mSpringListenerBA;
    public static IconoSpringListener mSpringListenerDA;
    public static IconoSpringListener mSpringListenerL;
    public static IconoSpringListener mSpringListenerTA;
    public static IconoSpringListener mSpringListenerR;
    public static IconoSpringListener mSpringListenerH;
    public static IconoSpringListener mSpringListenerS;
    public static IconoSpringListener mSpringListenerC;
    public static IconoSpringListener mSpringListenerD;
    public static IconoSpringListener mSpringListenerSO;
    public static IconoSpringListener mSpringListenerP;

    public static void initConfig (){
        mSpringSystem = SpringSystem.create();
        mSpringListenerBA = new IconoSpringListener();
        mSpringListenerDA = new IconoSpringListener();
        mSpringListenerL = new IconoSpringListener();
        mSpringListenerTA = new IconoSpringListener();
        mSpringListenerR = new IconoSpringListener();
        mSpringListenerH = new IconoSpringListener();
        mSpringListenerS = new IconoSpringListener();
        mSpringListenerC = new IconoSpringListener();
        mSpringListenerD = new IconoSpringListener();
        mSpringListenerSO = new IconoSpringListener();
        mSpringListenerP = new IconoSpringListener();

        mScaleSpringBA = mSpringSystem.createSpring();
        mScaleSpringBA.getSpringConfig().tension = 200;
        mScaleSpringBA.getSpringConfig().friction = 5;

        mScaleSpringDA = mSpringSystem.createSpring();
        mScaleSpringDA.getSpringConfig().tension = 200;
        mScaleSpringDA.getSpringConfig().friction = 5;

        mScaleSpringL = mSpringSystem.createSpring();
        mScaleSpringL.getSpringConfig().tension = 200;
        mScaleSpringL.getSpringConfig().friction = 5;

        mScaleSpringTA = mSpringSystem.createSpring();
        mScaleSpringTA.getSpringConfig().tension = 200;
        mScaleSpringTA.getSpringConfig().friction = 5;

        mScaleSpringR = mSpringSystem.createSpring();
        mScaleSpringR.getSpringConfig().tension = 200;
        mScaleSpringR.getSpringConfig().friction = 5;

        mScaleSpringH = mSpringSystem.createSpring();
        mScaleSpringH.getSpringConfig().tension = 200;
        mScaleSpringH.getSpringConfig().friction = 5;

        mScaleSpringS = mSpringSystem.createSpring();
        mScaleSpringS.getSpringConfig().tension = 200;
        mScaleSpringS.getSpringConfig().friction = 5;

        mScaleSpringC = mSpringSystem.createSpring();
        mScaleSpringC.getSpringConfig().tension = 200;
        mScaleSpringC.getSpringConfig().friction = 5;

        mScaleSpringD = mSpringSystem.createSpring();
        mScaleSpringD.getSpringConfig().tension = 200;
        mScaleSpringD.getSpringConfig().friction = 5;

        mScaleSpringSO = mSpringSystem.createSpring();
        mScaleSpringSO.getSpringConfig().tension = 200;
        mScaleSpringSO.getSpringConfig().friction = 5;

        mScaleSpringP = mSpringSystem.createSpring();
        mScaleSpringP.getSpringConfig().tension = 200;
        mScaleSpringP.getSpringConfig().friction = 5;
    }

    public static void setImages(ImageView banderaazulIV, ImageView dificultadaccesoIV, ImageView limpiezaIV, ImageView tipoarenaIV,
                                 ImageView romepolasIV, ImageView hamacasIV, ImageView sombrillasIV, ImageView chiringuitosIV,
                                 ImageView duchasIV, ImageView socorristaIV, ImageView perrosIV ){
        mSpringListenerBA.setImagen(banderaazulIV);
        mSpringListenerDA.setImagen(dificultadaccesoIV);
        mSpringListenerL.setImagen(limpiezaIV);
        mSpringListenerTA.setImagen(tipoarenaIV);
        mSpringListenerR.setImagen(romepolasIV);
        mSpringListenerH.setImagen(hamacasIV);
        mSpringListenerS.setImagen(sombrillasIV);
        mSpringListenerC.setImagen(chiringuitosIV);
        mSpringListenerD.setImagen(duchasIV);
        mSpringListenerSO.setImagen(socorristaIV);
        mSpringListenerP.setImagen(perrosIV);
    }

    public static void addListeners(){
        mScaleSpringBA.addListener(mSpringListenerBA);
        mScaleSpringDA.addListener(mSpringListenerDA);
        mScaleSpringL.addListener(mSpringListenerL);
        mScaleSpringTA.addListener(mSpringListenerTA);
        mScaleSpringR.addListener(mSpringListenerR);
        mScaleSpringH.addListener(mSpringListenerH);
        mScaleSpringS.addListener(mSpringListenerS);
        mScaleSpringC.addListener(mSpringListenerC);
        mScaleSpringD.addListener(mSpringListenerD);
        mScaleSpringSO.addListener(mSpringListenerSO);
        mScaleSpringP.addListener(mSpringListenerP);
    }

    public static void removeListeners(){
        mScaleSpringBA.removeListener(mSpringListenerBA);
        mScaleSpringDA.removeListener(mSpringListenerDA);
        mScaleSpringL.removeListener(mSpringListenerL);
        mScaleSpringTA.removeListener(mSpringListenerTA);
        mScaleSpringR.removeListener(mSpringListenerR);
        mScaleSpringH.removeListener(mSpringListenerH);
        mScaleSpringS.removeListener(mSpringListenerS);
        mScaleSpringC.removeListener(mSpringListenerC);
        mScaleSpringD.removeListener(mSpringListenerD);
        mScaleSpringSO.removeListener(mSpringListenerSO);
        mScaleSpringP.removeListener(mSpringListenerP);
    }

    public static class IconoSpringListener extends SimpleSpringListener {

        ImageView imagen;

        public void setImagen(ImageView imagen) {
            this.imagen = imagen;
        }

        @Override
        public void onSpringUpdate(Spring spring) {
            float mappedValue = (float) SpringUtil.mapValueFromRangeToRange(spring.getCurrentValue(), 0, 1, 1, 0.5);
            imagen.setScaleX(mappedValue);
            imagen.setScaleY(mappedValue);

        }
    }
}
