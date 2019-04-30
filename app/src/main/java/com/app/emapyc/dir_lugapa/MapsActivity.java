package com.app.emapyc.dir_lugapa;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.app.emapyc.InicioActivity;
import com.app.emapyc.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static java.lang.Double.parseDouble;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {
    private GoogleMap mMap;
    private  ImageView educ,edus,forta,fas,porr,so;
    private long startTime=1*60*1000;
    private final long interval =1*1000;
    MyCountDownTimer countDownTimer;

    private static final LatLng educe = new LatLng(-22.017565907384743, -63.67634475231171);
    private static final LatLng edusu = new LatLng(-22.002789803122667, -63.67629647254944);
    private static final LatLng fort = new LatLng(-22.016778276948337, -63.678918834775686);
    private static final LatLng sol = new LatLng(-22.01859286646042, -63.67797017097473);
    private static final LatLng por = new LatLng(-22.014087121451638, -63.67869973182678);
    private static final LatLng fass = new LatLng(-22.014873, -63.679019);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        countDownTimer =new MyCountDownTimer(startTime,interval);
         // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    educ=(ImageView)findViewById(R.id.educen);
        edus=(ImageView)findViewById(R.id.edusuc);
        forta=(ImageView)findViewById(R.id.forta);
        fas=(ImageView)findViewById(R.id.fassil);
        porr=(ImageView)findViewById(R.id.porres);
        so=(ImageView)findViewById(R.id.sol);
        educ.setOnClickListener(this);
        edus.setOnClickListener(this);
        forta.setOnClickListener(this);
        fas.setOnClickListener(this);
        porr.setOnClickListener(this);
        so.setOnClickListener(this);
        educ.setBackgroundResource(R.color.white);
        edus.setBackgroundResource(R.color.white);
        forta.setBackgroundResource(R.color.white);
        fas.setBackgroundResource(R.color.white);
        porr.setBackgroundResource(R.color.white);
        so.setBackgroundResource(R.color.white);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng mapa = new LatLng(parseDouble(getIntent().getStringExtra("lat")) , parseDouble(getIntent().getStringExtra("lon")));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapa, 16));
        //Toast.makeText(getBaseContext(), getIntent().getStringExtra("lat")+getIntent().getStringExtra("lon"), Toast.LENGTH_LONG).show();

        mMap.addMarker(new MarkerOptions().position(educe).title("Coop. Educadores Gran Chaco Oficina central").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.addMarker(new MarkerOptions().position(edusu).title("Coop. Educadores Gran Chaco Sucursal").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.addMarker(new MarkerOptions().position(fort).title("Banco Fortaleza").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        mMap.addMarker(new MarkerOptions().position(sol).title("Banco Sol").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        mMap.addMarker(new MarkerOptions().position(por).title("Coop. San Martin de Porres").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mMap.addMarker(new MarkerOptions().position(fass).title("Banco Fassil").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        if(Build.VERSION.SDK_INT>=23){
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            }  else {
            ActivityCompat.requestPermissions(this, new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION },2);
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                                PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                    mMap.getUiSettings().setMyLocationButtonEnabled(true);
                }
        }
        }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);





    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.educen:
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(educe, 16));
                educ.setBackgroundResource(R.color.ag);
                edus.setBackgroundResource(R.color.white);
                forta.setBackgroundResource(R.color.white);
                fas.setBackgroundResource(R.color.white);
                porr.setBackgroundResource(R.color.white);
                so.setBackgroundResource(R.color.white);
                break;
            case R.id.edusuc:
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(edusu, 16));
                educ.setBackgroundResource(R.color.white);
                edus.setBackgroundResource(R.color.green);
                forta.setBackgroundResource(R.color.white);
                fas.setBackgroundResource(R.color.white);
                porr.setBackgroundResource(R.color.white);
                so.setBackgroundResource(R.color.white);
                break;
            case R.id.forta:
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fort, 16));
                educ.setBackgroundResource(R.color.white);
                edus.setBackgroundResource(R.color.white);
                forta.setBackgroundResource(R.color.blue);
                fas.setBackgroundResource(R.color.white);
                porr.setBackgroundResource(R.color.white);
                so.setBackgroundResource(R.color.white);
                break;
            case R.id.sol:
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sol, 16));
                educ.setBackgroundResource(R.color.white);
                edus.setBackgroundResource(R.color.white);
                forta.setBackgroundResource(R.color.white);
                fas.setBackgroundResource(R.color.white);
                porr.setBackgroundResource(R.color.white);
                so.setBackgroundResource(R.color.orange);
                break;
            case R.id.porres:
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(por, 16));
                educ.setBackgroundResource(R.color.white);
                edus.setBackgroundResource(R.color.white);
                forta.setBackgroundResource(R.color.white);
                fas.setBackgroundResource(R.color.white);
                porr.setBackgroundResource(R.color.red);
                so.setBackgroundResource(R.color.white);
                break;
            case R.id.fassil:
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fass, 16));
                educ.setBackgroundResource(R.color.white);
                edus.setBackgroundResource(R.color.white);
                forta.setBackgroundResource(R.color.white);
                fas.setBackgroundResource(R.color.yellow);
                porr.setBackgroundResource(R.color.white);
                so.setBackgroundResource(R.color.white);
                break;
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
}
