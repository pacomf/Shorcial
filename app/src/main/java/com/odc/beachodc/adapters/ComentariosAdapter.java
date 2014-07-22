package com.odc.beachodc.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.widget.ProfilePictureView;
import com.odc.beachodc.R;
import com.odc.beachodc.db.models.Comentario;
import com.odc.beachodc.db.models.MensajeBotella;
import com.odc.beachodc.utilities.Utilities;

import java.util.List;

/**
 * Created by Paco on 09/07/2014.
 */
public class ComentariosAdapter extends BaseAdapter {

    protected Activity activity;
    protected List<Comentario> items;
    ImageView v1, v2, v3, v4, v5;

    public ComentariosAdapter(Activity activity, List<Comentario> items) {
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
            vi = inflater.inflate(R.layout.item_valoraciones_list, null);
        }

        // Trabajar con la UI, que es 'vi'

        Comentario comentario = items.get(position);

        TextView nombreautor = (TextView) vi.findViewById(R.id.nombreAutorTV);
        TextView fecha = (TextView) vi.findViewById(R.id.fechaTV);
        TextView mensaje = (TextView) vi.findViewById(R.id.valoracionTV);

        nombreautor.setText(comentario.nombreautor);
        fecha.setText(Utilities.formatFechaNotHour(comentario.fecha));
        mensaje.setText(comentario.comentario);

        ProfilePictureView profilePictureView = (ProfilePictureView) vi.findViewById(R.id.fotoAutorImage);
        profilePictureView.setCropped(true);
        profilePictureView.setProfileId(comentario.idfbautor);

        v1 = (ImageView) vi.findViewById(R.id.v1);
        v2 = (ImageView) vi.findViewById(R.id.v2);
        v3 = (ImageView) vi.findViewById(R.id.v3);
        v4 = (ImageView) vi.findViewById(R.id.v4);
        v5 = (ImageView) vi.findViewById(R.id.v5);

        setValoracion(comentario.valoracion);

        return vi;
    }

    public void setValoracion(int valoracion){
        if (valoracion == 1){
            v1.setImageResource(android.R.drawable.star_on);
            v2.setImageResource(android.R.drawable.star_off);
            v3.setImageResource(android.R.drawable.star_off);
            v4.setImageResource(android.R.drawable.star_off);
            v5.setImageResource(android.R.drawable.star_off);
        } else if (valoracion == 2){
            v1.setImageResource(android.R.drawable.star_on);
            v2.setImageResource(android.R.drawable.star_on);
            v3.setImageResource(android.R.drawable.star_off);
            v4.setImageResource(android.R.drawable.star_off);
            v5.setImageResource(android.R.drawable.star_off);
        } else if (valoracion == 3){
            v1.setImageResource(android.R.drawable.star_on);
            v2.setImageResource(android.R.drawable.star_on);
            v3.setImageResource(android.R.drawable.star_on);
            v4.setImageResource(android.R.drawable.star_off);
            v5.setImageResource(android.R.drawable.star_off);
        } else if (valoracion == 4){
            v1.setImageResource(android.R.drawable.star_on);
            v2.setImageResource(android.R.drawable.star_on);
            v3.setImageResource(android.R.drawable.star_on);
            v4.setImageResource(android.R.drawable.star_on);
            v5.setImageResource(android.R.drawable.star_off);
        } else if (valoracion == 5){
            v1.setImageResource(android.R.drawable.star_on);
            v2.setImageResource(android.R.drawable.star_on);
            v3.setImageResource(android.R.drawable.star_on);
            v4.setImageResource(android.R.drawable.star_on);
            v5.setImageResource(android.R.drawable.star_on);
        }
    }

}
