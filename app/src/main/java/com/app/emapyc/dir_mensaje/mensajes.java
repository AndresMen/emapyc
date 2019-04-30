package com.app.emapyc.dir_mensaje;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.app.emapyc.BD;
import com.app.emapyc.Constantes;
import com.app.emapyc.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link mensajes.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class mensajes extends Fragment {

    private OnFragmentInteractionListener mListener;
    //private array_adapater_men chatArrayAdapter;
     ListView listView;
     EditText chatText;
     Button buttonSend;
   // private boolean side = false;
    array_adapater_men men;
    //String IP="http://192.168.43.29/phpemapyc";
    //String INSERT=IP+"/insertar_mensaje.php";
    private BD base ;
    SQLiteDatabase bd;
    enviar enmen;
    String fecha,ho,usario;
    public mensajes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View viw=inflater.inflate(R.layout.fragment_mensajes,container,false);
        buttonSend = (Button) viw.findViewById(R.id.send);
        chatText = (EditText) viw.findViewById(R.id.msg);
        listView = (ListView) viw.findViewById(R.id.msgview);
        men=new array_adapater_men(getContext(),R.layout.mensaje);
        base = new BD(getActivity(), "baseEmapyc", null, 1);
        bd= base.getWritableDatabase();

buttonSend.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        //agarra la fecha
        Date d = new Date();
        SimpleDateFormat fec = new SimpleDateFormat("yyyy-MM-dd");
        fecha = fec.format(d);
        //agarra la hora
        Date h = new Date();
        SimpleDateFormat hor = new SimpleDateFormat("HH:mm:ss");
         ho = hor.format(h);

        if (chatText.getText().length()>=3) {
            //agarra el codigo del usuario

            bd = base.getWritableDatabase();
            Cursor fila = bd.rawQuery(
                    "select * from usuario where estado='activo'", null);
            if (fila.moveToFirst()) {
                usario =fila.getString(0);
            }
           enmen = new enviar();
            enmen.execute(Constantes.INSERT,chatText.getText().toString(),fecha,ho,usario);

         //guarda en la base de datos de android
            ContentValues registro = new ContentValues();
            registro.put("mensaje", chatText.getText().toString());
            registro.put("fecha", fecha);
            registro.put("hora", ho);
            bd.insert("mensajes", null, registro);
            //muestra el mensaje en pantalla
            men.add(new mensaje_cuer(chatText.getText().toString(), ho,fecha));
            chatText.setText("");
        }else{
            Toast.makeText(getContext(),"No se envian mensajes tan cortos", Toast.LENGTH_LONG).show();
        }
    }
});

        listView.setAdapter(men);
        return viw;
    }

    @Override
    public void onResume() {
        super.onResume();
        men.clear();
        Cursor fila = bd.rawQuery(
                "select * from mensajes", null);
        if (fila.moveToFirst()) {
            do {
                men.add(new mensaje_cuer(fila.getString(1), fila.getString(3),fila.getString(2)));
                chatText.setText("");
            }while (fila.moveToNext());
        }
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
private class enviar extends AsyncTask<String,Void,String>{


    @Override
    protected String doInBackground(String... params) {
        String cadena=params[0];
        URL url;
        String devuelve="";

            try {
                HttpURLConnection urlConn;

                url = new URL(cadena);
                urlConn = (HttpURLConnection) url.openConnection();
                urlConn.setDoInput(true);
                urlConn.setDoOutput(true);
                urlConn.setUseCaches(false);
                urlConn.setRequestProperty("Content-Type", "application/json");
                urlConn.setRequestProperty("Accept", "application/json");
                urlConn.connect();
                //Creo el Objeto JSON
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("mensaje", params[1]);
                jsonParam.put("fecha", params[2]);
                jsonParam.put("hora", params[3]);
                jsonParam.put("codigo", params[4]);
                // Envio los parámetros post.
                OutputStream os = urlConn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(jsonParam.toString());
                writer.flush();
                writer.close();

                int respuesta = urlConn.getResponseCode();


                StringBuilder result = new StringBuilder();

                if (respuesta == HttpURLConnection.HTTP_OK) {

                    String line;
                    BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                    while ((line=br.readLine()) != null) {
                        result.append(line);
                        //response+=line;
                    }

                    //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                    JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                    //Accedemos al vector de resultados

                    String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON

                    if (resultJSON.equals("1")) {      // hay un alumno que mostrar
                        devuelve = "Mensaje enviado correctamente";

                    } else if (resultJSON.equals("2")) {
                        devuelve = "El mensaje no pudo enviarse";
                    }

                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

        return devuelve;




    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();
        //super.onPostExecute(s);
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
}
