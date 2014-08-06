package com.odc.beachodc.utilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;

import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;
import com.odc.beachodc.R;
import com.odc.beachodc.webservices.image.UploadToImgurTask;

import java.io.ByteArrayOutputStream;

/**
 * Created by Paco on 05/08/2014.
 */
public class Image {

    public static ImageChooserManager imageChooserManager;

    public static String filePath;

    public static int chooserType;

    public static String ImageToBase64 (Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 70, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }

    public static void enviarImagen (Activity ctx, Bitmap imagen, ProgressDialog pd){
        UploadToImgurTask uploadToImgurTask = new UploadToImgurTask();
        uploadToImgurTask.setParams(ctx, pd, imagen);
        uploadToImgurTask.execute();
    }

    // Should be called if for some reason the ImageChooserManager is null (Due
    // to destroying of activity for low memory situations)
    public static void reinitializeImageChooser(Activity ctx, String path, ImageChooserListener listener) {
        Image.imageChooserManager = new ImageChooserManager(ctx, Image.chooserType, path, true);
        Image.imageChooserManager.setImageChooserListener(listener);
        Image.imageChooserManager.reinitialize(filePath);
    }
}
