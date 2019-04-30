package com.app.emapyc.dir_mensaje;

/**
 * Created by Mendez on 01/06/2017.
 */

public class mensaje_cuer {
    private   String mmessage;
    private   String mhora;
    private   String mfecha;

    public mensaje_cuer(String message,String hora,String fecha) {
        mmessage = message;
        mhora=hora;
        mfecha=fecha;
    }

    @Override
    public String toString() {
        return "mensaje_cuer{" +
                "mmessage='" + mmessage + '\'' +
                ", mhora='" + mhora + '\'' +
                ", mfecha='" + mfecha + '\'' +
                '}';
    }

    public String getMmessage() {
        return mmessage;
    }

    public String getMhora() {
        return mhora;
    }

    public String getMfecha() {
        return mfecha;
    }

    public void setMmessage(String mmessage) {
        this.mmessage = mmessage;
    }

    public void setMhora(String mhora) {
        this.mhora = mhora;
    }

    public void setMfecha(String mfecha) {
        this.mfecha = mfecha;
    }
}
