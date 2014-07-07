package com.odc.beachodc.utilities.placeAutocomplete;

/**
 * Created by Paco on 07/07/2014.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;


import android.content.Context;
import android.os.AsyncTask;

import com.odc.beachodc.R;
import com.odc.beachodc.interfaces.IStandardTaskListener;

public class DetailsPlaceOne extends AsyncTask<String, Void, Boolean>{

    private IStandardTaskListener listener;
    public Map<String, Double> coordinates;
    Context ctx;

    public void setContext (Context ctx){
        this.ctx = ctx;
    }

    @Override
    // Se ejecuta en segundo plano
    protected Boolean doInBackground(String... args) {
        coordinates = new HashMap<String, Double>();
        try{
            // TODO: Cambiar la KEY
            String requesturl="https://maps.googleapis.com/maps/api/place/details/json?reference="+URLEncoder.encode(args[0], "UTF-8")+"&sensor=false&key="+ctx.getResources().getString(R.string.app_key_google_apis_web);
            DefaultHttpClient client=new DefaultHttpClient();
            HttpGet req=new HttpGet(requesturl);
            HttpResponse res=client.execute(req);
            HttpEntity jsonentity=res.getEntity();
            InputStream in=jsonentity.getContent();
            JSONObject jObject=new JSONObject(convertStreamToString(in));
            Double lat = (Double)jObject.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").get("lat");
            Double lng = (Double)jObject.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").get("lng");
            coordinates.put("lat", lat);
            coordinates.put("lng", lng);
            return true;
        } catch (Exception e){}
        return false;
    }

    // Este metodo se ejecuta cuando el 'doInBackground' ha terminado, y recibe como parametro
    // lo devuelto en el 'doInBackground'
    @Override
    protected void onPostExecute(Boolean result){
        if (listener != null) {
            listener.taskComplete(result);
        }
    }

    public void setListener(IStandardTaskListener listener) {
        this.listener = listener;
    }

    public static String convertStreamToString(InputStream in) {
        BufferedReader br=new BufferedReader(new InputStreamReader(in));
        StringBuilder jsonstr=new StringBuilder();
        String line;
        try {
            while((line=br.readLine())!=null)
            {
                String t=line+"\n";
                jsonstr.append(t);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonstr.toString();
    }

}
