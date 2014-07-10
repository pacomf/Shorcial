package com.odc.beachodc.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

import com.facebook.widget.ProfilePictureView;

import com.odc.beachodc.R;
import com.odc.beachodc.db.models.MensajeBotella;

import com.odc.beachodc.utilities.Utilities;

import java.util.List;

/**
 * Created by Paco on 09/07/2014.
 */
public class MensajesBotellasAdapter extends BaseAdapter {

    protected Activity activity;
    protected List<MensajeBotella> items;

    public MensajesBotellasAdapter(Activity activity, List<MensajeBotella> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return (items == null) ? 0 : items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.item_mensajes_botellas_list, null);
        }

        // Trabajar con la UI, que es 'vi'

        MensajeBotella mensajeBotella = items.get(position);

        TextView nombreautor = (TextView) vi.findViewById(R.id.nombreAutorTV);
        TextView fecha = (TextView) vi.findViewById(R.id.fechaTV);
        TextView mensaje = (TextView) vi.findViewById(R.id.mensajeBotellaTV);
        TextView origen = (TextView) vi.findViewById(R.id.playaOrigenTV);

        nombreautor.setText(mensajeBotella.nombreautor);
        fecha.setText(Utilities.formatFecha(mensajeBotella.fecha));
        mensaje.setText(mensajeBotella.mensaje);
        if (mensajeBotella.idserverplayadestino != mensajeBotella.idserverplayaorigen){
            origen.setText(mensajeBotella.nombreplayadestino);
        } else {
            origen.setVisibility(View.GONE);
        }

        ProfilePictureView profilePictureView = (ProfilePictureView) vi.findViewById(R.id.fotoAutorImage);
        profilePictureView.setCropped(true);
        profilePictureView.setProfileId(mensajeBotella.idfbautor);

        return vi;
    }

}
