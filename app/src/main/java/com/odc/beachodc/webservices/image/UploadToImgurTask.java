package com.odc.beachodc.webservices.image;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.odc.beachodc.R;
import com.odc.beachodc.utilities.Image;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Created by Paco on 05/08/2014.
 */
public class UploadToImgurTask extends AsyncTask<String, Void, String> {

    Activity ctx;
    ProgressDialog pd;
    Bitmap imagen;

    public void setParams (Activity ctx, ProgressDialog pd, Bitmap imagen){
        this.ctx = ctx;
        this.pd = pd;
        this.imagen = imagen;
    }

    @Override
    protected String doInBackground(String... params) {
        final String upload_to = "https://api.imgur.com/3/image";

        System.out.println("Entrando....");

        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(upload_to);
        httpPost.setHeader("Authorization", "Client-ID " + ctx.getString(R.string.clientid_imgur));

        try {
            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            entityBuilder.addTextBody("image", Image.ImageToBase64(this.imagen));

            HttpEntity entity = entityBuilder.build();

            httpPost.setEntity(entity);

            final HttpResponse response = httpClient.execute(httpPost, localContext);

            final String response_string = EntityUtils.toString(response.getEntity());

            final JSONObject json = new JSONObject(response_string);

            Log.d("JSON", json.toString()); //for my own understanding

            return json.getJSONObject("data").optString("link");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        // Mandar link (Result) al servidor
        System.out.println("LLLLLL: "+result);
        if (result != null){

        } else {
            if ((pd != null) && (pd.isShowing()))
                pd.dismiss();
            Crouton.makeText(ctx, ctx.getString(R.string.error_unknown), Style.ALERT).show();
        }
    }

}