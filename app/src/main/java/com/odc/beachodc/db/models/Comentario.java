package com.odc.beachodc.db.models;


import android.content.Context;

import com.odc.beachodc.utilities.Utilities;

import java.util.Date;

/**
 * Created by Paco on 07/07/2014.
 */
public class Comentario {

    public String idfbautor;

    public String idplaya;

    public String comentario;

    public Integer valoracion;

    public Date fecha;


    public Comentario(){
        super();
    }

    public Comentario(String idfbautor, String idplaya, String comentario, Date fecha, Integer valoracion){
        this.idfbautor = idfbautor;
        this.fecha = fecha;
        this.idplaya = idplaya;
        this.comentario = comentario;
        this.valoracion = valoracion;
    }


}
