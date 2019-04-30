package com.app.emapyc.dir_lugapa;

/**
 * Created by Mendez on 13/12/2017.
 */

public class cobr {
    private int imagen;
    private String nombre;
    private int visitas;
    private String lat;
    private String lon;

    public int getImagen() {
        return imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public int getVisitas() {
        return visitas;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public cobr(int imagen, String nombre, int visitas, String lat, String lon) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.visitas = visitas;
        this.lat = lat;
        this.lon = lon;
    }
}
