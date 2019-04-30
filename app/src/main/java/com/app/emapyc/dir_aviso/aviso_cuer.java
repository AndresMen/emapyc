package com.app.emapyc.dir_aviso;

/**
 * Created by Mendez on 01/06/2017.
 */

public class aviso_cuer {
    private   String avis;
    private   String mhora;
    private   String mfecha;

    public aviso_cuer(String aviso, String hora, String fecha) {
        avis = aviso;
        mhora=hora;
        mfecha=fecha;
    }

    @Override
    public String toString() {
        return "aviso_cuer{" +
                "avis='" + avis + '\'' +
                ", mhora='" + mhora + '\'' +
                ", mfecha='" + mfecha + '\'' +
                '}';
    }

    public String getAvis() {
        return avis;
    }

    public void setAvis(String avis) {
        this.avis = avis;
    }

    public String getMhora() {
        return mhora;
    }

    public void setMhora(String mhora) {
        this.mhora = mhora;
    }

    public String getMfecha() {
        return mfecha;
    }

    public void setMfecha(String mfecha) {
        this.mfecha = mfecha;
    }
}
