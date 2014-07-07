package com.odc.beachodc.utilities.placeAutocomplete;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.odc.beachodc.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/*
* Author: Paco Martín
* Esta clase se encarga de autocompletar en un AutoCompleteTextView un lugar/direccion
* a medida que vamos escribiendo en dicho AutoCompleteTextView. Para ello se hace uso
* de la API de Google Place.
*
* Se ha generado una clave, para poder usar dicha API
*/
public class FillPlace extends AsyncTask<String, Void, ArrayList<String>>
{
    public ArrayAdapter<String> adapter;
    public Map<String, String> referencesPlace;
    public AutoCompleteTextView autoComplete;
    public Context context;

    // Constructor que necesita
    // adaptarArg: El Adaptador usado para mostrar las distinas posibilidades de autocompletado en una lista debajo del Campo
    // textview: El Campo donde se está escrbiendo y que se quiere autocompletar
    // contextArg: El Contexto de donde se llamó al constructor
    public FillPlace(ArrayAdapter<String> adapterArg, AutoCompleteTextView textview, Context contextArg){
        adapter = adapterArg;
        autoComplete = textview;
        context = contextArg;
        referencesPlace = new HashMap<String, String>();
    }

    @Override
    // Se ejecuta en segundo plano, la busqueda de posibilidades de autocompletado, para no sobrecargar el hilo principal
    protected ArrayList<String> doInBackground(String... args) {
        ArrayList<String> predictionsArr = new ArrayList<String>();
        referencesPlace.clear();
        try{
            // Llamamos a la API de Google, con lo que de momento lleva escrito el usuario
            String direccion = args[0];
            URL googlePlaces = new URL("https://maps.googleapis.com/maps/api/place/autocomplete/json?input="+ URLEncoder.encode(direccion, "UTF-8") +"&types=geocode&language=en&sensor=true&key="+context.getResources().getString(R.string.app_key_google_apis_web));
            URLConnection tc = googlePlaces.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(tc.getInputStream()));
            String line;
            StringBuffer sb = new StringBuffer();
            // Parseamos la respuesta de la API Place para aclimatarla a un objeto conocido y poder manejar lo que ha devuelto
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            // Convertimos a JSON lo leido en la respuesta de la API
            JSONObject predictions = new JSONObject(sb.toString());
            // Convertimos el Objeto JSON anterior a un Array de JSON para poder iterar las respuesta de la API
            JSONArray ja = new JSONArray(predictions.getString("predictions"));
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = (JSONObject) ja.get(i);
                // Convertimos finalmente cada iteracion del JSON a String, ya que cada iteracion es un posible Autocompletado
                predictionsArr.add(jo.getString("description"));
                referencesPlace.put(jo.getString("description"), jo.getString("reference"));
            }
        } catch (Exception e){
            referencesPlace.put("error", "errorWS");
        }

        return predictionsArr;

    }

    // Este metodo se ejecuta cuando el 'doInBackground' ha terminado, y recibe como parametro
    // lo devuelto en el 'doInBackground'
    @Override
    protected void onPostExecute(ArrayList<String> result){

        // Inicializamos la lista que muestra las posibles propuestas de autocompletado
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        adapter.setNotifyOnChange(true);
        // Y asignamos esa lista a nuestro Campo, para que se lo muestre al usuario
        autoComplete.setAdapter(adapter);

        // Ahora vamos rellenando la lista con las distintas propuestas de autocompletado
        for (String string : result){
            adapter.add(string);
            adapter.notifyDataSetChanged();
        }
    }

}
