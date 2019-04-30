package com.app.emapyc;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText etx;
    private Button aceptar;
    private ProgressBar progressBar;
    //String IP="http://pruebaemapyc.esy.es/phpemapyc";
    //String IP="http://192.168.0.105/phpemapyc";
    //String GET_BY_COD=IP+"/obtener_usuario_por_cod.php";

    BD base=new BD(this,"baseEmapyc",null,1);
    SQLiteDatabase bd;
    ImageView ima;
TextInputLayout textinputlayout;
    String resultJSON= null;
    String devuelve="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etx=(TextInputEditText)findViewById(R.id.editTextPassword);
        aceptar=(Button)findViewById(R.id.acep);
        aceptar.setOnClickListener(this);
        aceptar.setEnabled(true);
        progressBar=(ProgressBar)findViewById(R.id.progress);
        bd=base.getWritableDatabase();
        ima=(ImageView)findViewById(R.id.imageView2);
textinputlayout=(TextInputLayout)findViewById(R.id.text_input_layout_pass);
    }

    @Override
    protected void onResume() {
        super.onResume();

        BD base = new BD(this,
                "baseEmapyc", null, 1);
        SQLiteDatabase bd = base.getWritableDatabase();

        Cursor fila = bd.rawQuery(
                "select * from usuario where estado='activo'", null);
        if (fila.moveToFirst()) {
            Intent inici=new Intent(this,InicioActivity.class);
            startActivity(inici);
            finish();
        }
       bd.close();
    }
private static void toggleTextInputLayoutError(@NonNull TextInputLayout textInputLayout,
                                               String msg) {
    textInputLayout.setError(msg);
    if (msg == null) {
        textInputLayout.setErrorEnabled(false);
    } else {
        textInputLayout.setErrorEnabled(true);
    }
}
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.acep:

                String passError = null;
                if (TextUtils.isEmpty(etx.getText())) {
                    passError = getString(R.string.mandatory);
                }
                toggleTextInputLayoutError(textinputlayout, passError);

                //clearFocus();
                Animation giro;
                giro= AnimationUtils.loadAnimation(getBaseContext(),R.anim.girar);
                giro.reset();
                ima.startAnimation(giro);
                String usucodigo=Constantes.GET_BY_COD+"?codigo="+etx.getText().toString();

                 VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,usucodigo,new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {


                                try {
                                    resultJSON = response.getString("estado");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                if (resultJSON.equals("1")){

                                    try {
                                    if(etx.getText().toString().equals(response.getJSONObject("usuario").getString("codigo_ins"))){
                                            ContentValues registro = new ContentValues();

                                            registro.put("codigo", response.getJSONObject("usuario").getString("codigo_ins"));
                                            registro.put("estado","activo");
                                            registro.put("nombre",response.getJSONObject("usuario").getString("nombre"));
                                            registro.put("calle",response.getJSONObject("usuario").getString("calle"));
                                            registro.put("categoria",response.getJSONObject("usuario").getString("categoria"));
                                            registro.put("medidor",response.getJSONObject("usuario").getString("medidor"));
                                            bd.insert("usuario", null, registro);

                                            Intent inici=new Intent(getBaseContext(),InicioActivity.class);
                                            startActivity(inici);
                                            overridePendingTransition(R.anim.zoom_in,R.anim.zoom_out);
                                            finish();
                                        }else{
                                            Toast.makeText(getBaseContext(),"No concuerda el codigo",Toast.LENGTH_LONG).show();
                                            etx.setText("");
                                            etx.setEnabled(true);
                                            aceptar.setEnabled(true);
                                            progressBar.setVisibility(View.GONE);
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
                                            Log.e("VOLLEY", "hubo una falla de autenticacion al realizar la solicitud. " + error.getMessage() );
                                        } else if (error instanceof NetworkError) {
                                            Log.e("VOLLEY", "hubo un error de red al realizar la solicitud. "+ error.getMessage());
                                        }if (error instanceof NoConnectionError){
                                            Log.e("VOLLEY", "la consulta tiene tiempo de espera o no hay conexion. " + error.getMessage() );
                                        } else if (error instanceof ParseError) {
                                            Log.e("VOLLEY", " la respuesta del servidor no pudo ser analizada. "+ error.getMessage());
                                        }if (error instanceof ServerError){
                                            Log.e("VOLLEY", "El servidor respondio con una respuesta de error. " + error.getMessage() );
                                        } else if (error instanceof TimeoutError) {
                                            Log.e("VOLLEY", "Se ha producido un fallo en la red. "+ error.getMessage());
                                        }
                                    }
                                }
                        ){
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String,String>headers= new HashMap<String, String>();
                                headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1)");
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

                break;
            default:
                break;
        }

    }

    private boolean revisarconexion(){
        ConnectivityManager conec=(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        if (conec.getNetworkInfo(0).getState()== NetworkInfo.State.CONNECTED||
                conec.getNetworkInfo(0).getState()== NetworkInfo.State.CONNECTING||
                conec.getNetworkInfo(1).getState()== NetworkInfo.State.CONNECTING||
                conec.getNetworkInfo(1).getState()== NetworkInfo.State.CONNECTED){
            Toast.makeText(this,"Conectado",Toast.LENGTH_LONG).show();
        return true;
        }else if (conec.getNetworkInfo(0).getState()== android.net.NetworkInfo.State.DISCONNECTED||
                conec.getNetworkInfo(1).getState()== NetworkInfo.State.DISCONNECTED){
            Toast.makeText(this,"Desconectado",Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

}