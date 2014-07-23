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
    ViewHolder viewHolder;

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
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.item_valoraciones_list, null);
            viewHolder.v1 = (ImageView) vi.findViewById(R.id.v1);
            viewHolder.v2 = (ImageView) vi.findViewById(R.id.v2);
            viewHolder.v3 = (ImageView) vi.findViewById(R.id.v3);
            viewHolder.v4 = (ImageView) vi.findViewById(R.id.v4);
            viewHolder.v5 = (ImageView) vi.findViewById(R.id.v5);
            viewHolder.profilePictureView = (ProfilePictureView) vi.findViewById(R.id.fotoAutorImage);
            viewHolder.nombreautor = (TextView) vi.findViewById(R.id.nombreAutorTV);
            viewHolder.fecha = (TextView) vi.findViewById(R.id.fechaTV);
            viewHolder.mensaje = (TextView) vi.findViewById(R.id.valoracionTV);
            vi.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) vi.getTag();
        }

        // Trabajar con la UI, que es 'vi'

        Comentario comentario = items.get(position);

        viewHolder.nombreautor.setText(comentario.nombreautor);
        viewHolder.fecha.setText(Utilities.formatFechaNotHour(comentario.fecha));
        viewHolder.mensaje.setText(comentario.comentario);

        try {
            viewHolder.profilePictureView.setCropped(true);
            viewHolder.profilePictureView.setProfileId(comentario.idfbautor);
        } catch (Exception e){}

        setValoracion(comentario.valoracion);

        return vi;
    }

    public void setValoracion(int valoracion){
        if (valoracion == 1){
            viewHolder.v1.setImageResource(R.drawable.star_on);
            viewHolder.v2.setImageResource(R.drawable.star_off);
            viewHolder.v3.setImageResource(R.drawable.star_off);
            viewHolder.v4.setImageResource(R.drawable.star_off);
            viewHolder.v5.setImageResource(R.drawable.star_off);
        } else if (valoracion == 2){
            viewHolder.v1.setImageResource(R.drawable.star_on);
            viewHolder.v2.setImageResource(R.drawable.star_on);
            viewHolder.v3.setImageResource(R.drawable.star_off);
            viewHolder.v4.setImageResource(R.drawable.star_off);
            viewHolder.v5.setImageResource(R.drawable.star_off);
        } else if (valoracion == 3){
            viewHolder.v1.setImageResource(R.drawable.star_on);
            viewHolder.v2.setImageResource(R.drawable.star_on);
            viewHolder.v3.setImageResource(R.drawable.star_on);
            viewHolder.v4.setImageResource(R.drawable.star_off);
            viewHolder.v5.setImageResource(R.drawable.star_off);
        } else if (valoracion == 4){
            viewHolder.v1.setImageResource(R.drawable.star_on);
            viewHolder.v2.setImageResource(R.drawable.star_on);
            viewHolder.v3.setImageResource(R.drawable.star_on);
            viewHolder.v4.setImageResource(R.drawable.star_on);
            viewHolder.v5.setImageResource(R.drawable.star_off);
        } else if (valoracion == 5){
            viewHolder.v1.setImageResource(R.drawable.star_on);
            viewHolder.v2.setImageResource(R.drawable.star_on);
            viewHolder.v3.setImageResource(R.drawable.star_on);
            viewHolder.v4.setImageResource(R.drawable.star_on);
            viewHolder.v5.setImageResource(R.drawable.star_on);
        }
    }

    static class ViewHolder {
        ImageView v1, v2, v3, v4, v5;
        ProfilePictureView profilePictureView;
        TextView nombreautor;
        TextView fecha;
        TextView mensaje;
    }

}
