package com.app.emapyc.dir_deudas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.emapyc.BD;
import com.app.emapyc.Constantes;
import com.app.emapyc.R;
import com.app.emapyc.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link deudas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class deudas extends Fragment {

    private OnFragmentInteractionListener mListener;
    //String IP="http://192.168.43.29/phpemapyc";
    //String AVISO_USU=IP+"/deuda_usuario.php";
    TextView cod,nom,dir,cat,med;
    Cursor fila;
    String deud_cod;
    tabla_deuda tabla;

    String resultJSON= null;
    String devuelve="";
    JSONArray deuJSON;

    public deudas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.fragment_deudas, container, false);


        cod=(TextView)view.findViewById(R.id.codi);
        nom=(TextView)view.findViewById(R.id.nombrec);
        dir=(TextView)view.findViewById(R.id.direcc);
        cat=(TextView)view.findViewById(R.id.categoria);
        med=(TextView)view.findViewById(R.id.medid);

        tabla = new tabla_deuda(getActivity(), (TableLayout)view.findViewById(R.id.tabla));
        tabla.agregarCabecera(R.array.cabecera_tabla);



        carda();
        deu();
       // String a= String.valueOf(tabla.getCeldasTotales());
        //Toast.makeText(getContext(),a,Toast.LENGTH_LONG).show();

        setHasOptionsMenu(true);
        return view;
    }

    public void carda(){
        BD base=new BD(getContext(),"baseEmapyc",null,1);
        SQLiteDatabase bd=base.getWritableDatabase();
        fila = bd.rawQuery(
                "select * from usuario where estado='activo'", null);
        if (fila.moveToFirst()) {
            cod.setText(fila.getString(0));
            nom.setText(fila.getString(2));
            dir.setText(fila.getString(3));
            cat.setText(fila.getString(4));
            med.setText(fila.getString(5));
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.av,menu);
    }

public void deu(){
    deud_cod= Constantes.DEUDA_USU+"?usu_cod="+cod.getText();

    VolleySingleton.getInstance(getContext()).addToRequestQueue(
            new JsonObjectRequest(
                    Request.Method.GET,deud_cod,new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    try {
                        resultJSON = response.getString("estado");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (resultJSON.equals("1")){

                        try {
                            deuJSON = response.getJSONArray("datos_deudas");

                            for(int i=0;i<deuJSON.length();i++){

                                ArrayList<String> elementos = new ArrayList<String>();
                                elementos.add(Integer.toString(i+1));
                                elementos.add(deuJSON.getJSONObject(i).getString("gestion"));
                                String me=deuJSON.getJSONObject(i).getString("periodo");
                                if(me.equals("1")){
                                    me="enero";
                                }if(me.equals("2")){
                                    me="febrero";
                                }if(me.equals("3")){
                                    me="marzo";
                                }if(me.equals("4")){
                                    me="abril";
                                }if(me.equals("5")){
                                    me="mayo";
                                }if(me.equals("6")){
                                    me="junio";
                                }if(me.equals("7")){
                                    me="julio";
                                }if(me.equals("8")){
                                    me="agosto";
                                }if(me.equals("9")){
                                    me="septiembre";
                                }if(me.equals("10")){
                                    me="octubre";
                                }if(me.equals("11")){
                                    me="noviembre";
                                }if(me.equals("12")){
                                    me="diciembre";
                                }
                                elementos.add(me);
                                elementos.add(deuJSON.getJSONObject(i).getString("monto_total"));
                                tabla.agregarFilaTabla(elementos);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        if (resultJSON.equals("2")){
                            try {
                                devuelve=devuelve+response.getString("mensaje");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error instanceof AuthFailureError){
                                Log.e("VOLLEY", "Se ha producido un fallo con las credenciales. " + error.getMessage() );
                            } else if (error instanceof NetworkError) {
                                Log.e("VOLLEY", "Se ha producido un fallo fallo en la red. "+ error.getMessage());
                            }if (error instanceof NoConnectionError){
                                Log.e("VOLLEY", "Se ha producido un fallo con las credenciales. " + error.getMessage() );
                            } else if (error instanceof ParseError) {
                                Log.e("VOLLEY", "Se ha producido un fallo fallo en la red. "+ error.getMessage());
                            }if (error instanceof ServerError){
                                Log.e("VOLLEY", "Se ha producido un fallo con las credenciales. " + error.getMessage() );
                            } else if (error instanceof TimeoutError) {
                                Log.e("VOLLEY", "Se ha producido un fallo fallo en la red. "+ error.getMessage());
                            }
                        }
                    }
            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String>headers= new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    headers.put("Accept", "application/json");
                    return headers;
                }
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8" + getParamsEncoding();
                }
            }

    );



}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_actua:
                carda();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
