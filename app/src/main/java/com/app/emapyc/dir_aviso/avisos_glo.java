package com.app.emapyc.dir_aviso;

import android.content.Context;
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
import android.widget.ListView;

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
import com.app.emapyc.Constantes;
import com.app.emapyc.R;
import com.app.emapyc.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link avisos_glo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class avisos_glo extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ListView listView;
    array_adapater_avi avi;
    //String IP="http://192.168.43.29/phpemapyc";
    //public String AVISO_GLO=IP+"/aviso_global.php";
    String resultJSON= null;
    String devuelve="";
    JSONArray avisosJSON;


    public avisos_glo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View viw=inflater.inflate(R.layout.fragment_avisos, container, false);
        listView = (ListView) viw.findViewById(R.id.aviview);
        avi=new array_adapater_avi(getContext(),R.layout.aviso);
        setHasOptionsMenu(true);
        cargar();

        return viw;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.av,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_actua:

                    cargar();

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public void cargar(){


            VolleySingleton.getInstance(getContext()).addToRequestQueue(
                    new JsonObjectRequest(
                            Request.Method.GET, Constantes.AVISO_GLO,new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                resultJSON = response.getString("estado");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (resultJSON.equals("1")){
                                try {
                                    avisosJSON = response.getJSONArray("avisos");
                                    avi.clear();
                                    for(int i=0;i<avisosJSON.length();i++){
                                        avi.add(new aviso_cuer(avisosJSON.getJSONObject(i).getString("aviso"),avisosJSON.getJSONObject(i).getString("hora"),avisosJSON.getJSONObject(i).getString("fecha")));
                                        listView.setAdapter(avi);
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
