package com.app.emapyc;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Clase que representa un cliente HTTP Volley
 */

public final class VolleySingleton {

    // Atributos
    private static VolleySingleton singleton;
    private RequestQueue requestQueue;
    private static Context context;


    private VolleySingleton(Context context) {
        VolleySingleton.context = context;
        requestQueue = getRequestQueue();
    }

    /**
     * Retorna la instancia unica del singleton
     *
     * @param context contexto donde se ejecutarán las peticiones
     * @return Instancia
     */
    public static synchronized VolleySingleton getInstance(Context context) {
        if (singleton == null) {
            singleton = new VolleySingleton(context.getApplicationContext());
        }
        return singleton;
    }

    /**
     * Obtiene la instancia de la cola de peticiones
     *
     * @return cola de peticiones
     */
    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
       // System.setProperty("http.keepAlive", "false");
        return requestQueue;
    }

    /**
     * Añade la petición a la cola
     *
     * @param req petición
     * @param <T> Resultado final de tipo T
     */
    public <T> void addToRequestQueue(Request<T> req) {
        //System.setProperty("http.keepAlive", "false");
        //req.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*3 ,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(req);
        }


}