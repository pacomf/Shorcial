package com.odc.beachodc.fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.facebook.widget.ProfilePictureView;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.odc.beachodc.R;
import com.odc.beachodc.adapters.ComentariosAdapter;
import com.odc.beachodc.db.models.Imagen;
import com.odc.beachodc.utilities.AnimateFirstDisplayListener;
import com.odc.beachodc.utilities.DescriptionAnimationSlider;
import com.odc.beachodc.utilities.Image;
import com.odc.beachodc.utilities.Utilities;
import com.odc.beachodc.utilities.ValidacionPlaya;
import com.odc.beachodc.webservices.Request;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


/**
 * Created by Paco on 7/01/14.
 * Fragment que se encarga de Loguear al usuario, es el splash screen inicial de login
 */
public class ImagenesPlayaFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ImageChooserListener {

    View rootView;
    TextView nombreAutor;
    ProfilePictureView autorProfile;
    private SliderLayout mSlider;
    Fragment fragment;
    RelativeLayout no_photos;

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

        nombreAutor = (TextView) rootView.findViewById(R.id.nombreAutorTV);
        autorProfile = (ProfilePictureView) rootView.findViewById(R.id.fotoAutorImage);
        no_photos = (RelativeLayout) rootView.findViewById(R.id.no_photos);

        mSlider = (SliderLayout) rootView.findViewById(R.id.slider);

        updateImages();

        Button uploadBTN = (Button) rootView.findViewById(R.id.uploadBTN);

        uploadBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utilities.isAnonymous(getActivity())){
                    Utilities.goToLoginAsking(getActivity());
                } else {
                    if (Utilities.haveInternet(getActivity())) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                        builder.setMessage(getString(R.string.description_choose_image))
                                .setTitle(getString(R.string.title_choose_image))
                                .setPositiveButton(getString(R.string.choose_gallery), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        chooseImage();
                                        dialog.cancel();
                                    }
                                })
                                .setNegativeButton(getString(R.string.choose_camera), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        takePicture();
                                        dialog.cancel();
                                    }
                                });
                        builder.show();
                    } else {
                        Crouton.makeText(getActivity(), getString(R.string.no_internet), Style.ALERT).show();
                    }
                }
            }
        });

        Button reloadBTN = (Button) rootView.findViewById(R.id.updateBTN);

        reloadBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateImages();
            }
        });

        return rootView;
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        // Al hacer click sobre la imagen la abrimos en el Gallery de Android
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(slider.getBundle().get("link").toString()),"image/*");
        startActivity(intent);
    }

    private void chooseImage() {
        Image.chooserType = ChooserType.REQUEST_PICK_PICTURE;
        Image.imageChooserManager = new ImageChooserManager(this, ChooserType.REQUEST_PICK_PICTURE, "ShorcialPhotos", true);
        Image.imageChooserManager.setImageChooserListener(this);
        try {
            Image.filePath = Image.imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void takePicture() {
        Image.chooserType = ChooserType.REQUEST_CAPTURE_PICTURE;
        Image.imageChooserManager = new ImageChooserManager(this,ChooserType.REQUEST_CAPTURE_PICTURE, "ShorcialPhotos", true);
        Image.imageChooserManager.setImageChooserListener(this);
        try {
            Image.filePath = Image.imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onImageChosen(final ChosenImage image) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (image != null) {
                    if (Utilities.haveInternet(getActivity())) {
                        ProgressDialog pd = ProgressDialog.show(getActivity(), getResources().getText(R.string.esperar), getResources().getText(R.string.esperar));
                        pd.setIndeterminate(false);
                        pd.setCancelable(true);
                        Bitmap bitmap = null;
                        File fichero = new File(image.getFilePathOriginal());
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.fromFile(fichero));
                        } catch (Exception e) {
                        }

                        if (bitmap == null) {
                            Crouton.makeText(getActivity(), getString(R.string.error_unknown), Style.ALERT).show();
                            pd.dismiss();
                            return;
                        }

                        pd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                updateImages();
                            }
                        });

                        Image.enviarImagen(getActivity(), bitmap, pd);

                        try {
                            // Para borrar la foto (y sus Thumbails que crea la libreria), y asi no almacenarlas
                            // TODO: En el futuro ¿Permitir almacenarlas en funcion de lo que diga el usuario?
                            File carpeta = fichero.getParentFile();
                            for (File file : carpeta.listFiles()) {
                                file.delete();
                            }
                        } catch (Exception e) {
                        }
                    } else {
                        Crouton.makeText(getActivity(), getString(R.string.no_internet), Style.ALERT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onError(final String reason) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getActivity(), reason, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK && (requestCode == ChooserType.REQUEST_PICK_PICTURE || requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)) {
            if (Image.imageChooserManager == null) {
                Image.reinitializeImageChooser(getActivity(), "ShorcialPhotos", this);
            }
            Image.imageChooserManager.submit(requestCode, data);
        }
    }

    private void updateImages (){
        mSlider.removeAllSliders();
        if ((ValidacionPlaya.imagenes != null) && (ValidacionPlaya.imagenes.size() > 0)){
            no_photos.setVisibility(View.GONE);
            if (Image.imageChooserManager == null) {
                Image.reinitializeImageChooser(getActivity(), "ShorcialPhotos", this);
            }
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
            mSlider.setPresetTransformer(SliderLayout.Transformer.Tablet);
            if (ValidacionPlaya.imagenes.size() > 1) {
                DescriptionAnimationSlider descriptionAnimationSlider = new DescriptionAnimationSlider();
                descriptionAnimationSlider.setParams(mSlider, autorProfile, nombreAutor);
                mSlider.setCustomAnimation(descriptionAnimationSlider);
                mSlider.setDuration(6000);
            } else {
                mSlider.stopAutoCycle();
                autorProfile.setProfileId(mSlider.getCurrentSlider().getBundle().get("idfb").toString());
                nombreAutor.setText(mSlider.getCurrentSlider().getBundle().get("nombrefb").toString());
            }

        } else {
            ProgressDialog pd = ProgressDialog.show(getActivity(), getResources().getText(R.string.esperar), getResources().getText(R.string.esperar));
            pd.setIndeterminate(false);
            pd.setCancelable(true);
            pd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    if ((ValidacionPlaya.imagenes != null) && (ValidacionPlaya.imagenes.size() > 0)) {
                        no_photos.setVisibility(View.VISIBLE);
                    }
                }
            });
            Request.getImagenesPlaya(getActivity(), ValidacionPlaya.playa.idserver, pd, null);

        }
    }


}
