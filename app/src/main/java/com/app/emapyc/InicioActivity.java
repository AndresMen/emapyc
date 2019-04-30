package com.app.emapyc;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.app.emapyc.dir_aviso.avisos;
import com.app.emapyc.dir_aviso.avisos_glo;
import com.app.emapyc.dir_deudas.deudas;
import com.app.emapyc.dir_lugapa.lugarespago;
import com.app.emapyc.dir_mensaje.mensajes;

public class InicioActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, lugarespago.OnFragmentInteractionListener, inicio.OnFragmentInteractionListener, mensajes.OnFragmentInteractionListener, avisos.OnFragmentInteractionListener, deudas.OnFragmentInteractionListener,avisos_glo.OnFragmentInteractionListener {

    private long startTime=1*60*1000;
    private final long interval =1*1000;
    MyCountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        countDownTimer =new MyCountDownTimer(startTime,interval);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
if (savedInstanceState==null){
    displaySelectedScreen(R.id.inicio);
}


    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        countDownTimer.cancel();
        countDownTimer.start();
    }
    public class MyCountDownTimer extends CountDownTimer {


        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onTick(long millisUntilFinished) {}

        @Override
        public void onFinish() {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int itemid){
        Fragment fragment=null;
        switch (itemid){
            case R.id.inicio:
                fragment=new inicio();
                setTitle("Inicio");
                break;
            case R.id.deudas:
                fragment=new deudas();
                setTitle("Deudas");
                break;
            case R.id.avisosusu:
                fragment=new avisos();
                setTitle("Avisos usuario");
                break;
            case R.id.avisosglo:
                fragment=new avisos_glo();
                setTitle("Avisos global");
                break;
            case R.id.mensajes:
                fragment=new mensajes();
                setTitle("Mensajes");
                break;
            case R.id.luga:
                fragment=new lugarespago();
                setTitle("Centros de cobranza autorizados");
                break;
            case R.id.cesa:
                String x="";
                BD base = new BD(this,
                        "baseEmapyc", null, 1);
                SQLiteDatabase bd = base.getWritableDatabase();

                Cursor fila = bd.rawQuery(
                        "select * from usuario where estado='activo'", null);
                if (fila.moveToFirst()) {
                    x=x+fila.getString(0);
                }

                int cant = bd.delete("usuario", "codigo="+x , null);
                bd.close();
                if (cant>=1){
                    Intent inici=new Intent(this,MainActivity.class);
                    startActivity(inici);
                    finish();
                }
                break;
            case R.id.salir:
                finish();
                break;
        }
        if (fragment!=null){
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_inicio,fragment);
            ft.commit();
        }
        DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
       displaySelectedScreen(item.getItemId());
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
