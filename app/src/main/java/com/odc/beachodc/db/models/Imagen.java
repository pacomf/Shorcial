package com.odc.beachodc.db.models;


import java.io.Serializable;
import java.util.Date;

/**
 * Created by Paco on 07/07/2014.
 */
public class Imagen implements Serializable {

    public String idfbautor;

    public String nombreautor;

    public String idplaya;

    public String comentario;

    public String link;

    public Date fecha;


    public Imagen(){
        super();
    }

    public Imagen(String idfbautor, String idplaya, String comentario, String nombreautor, Date fecha, String link){
        this.idfbautor = idfbautor;
        this.fecha = fecha;
        this.idplaya = idplaya;
        this.nombreautor = nombreautor;
        this.comentario = comentario;
        this.link = link;
    }


}
