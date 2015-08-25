package org.hansel.myAlert;
/*This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
Created by Javier Mejia @zenyagami
zenyagami@gmail.com
	*/

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.hancel.http.HttpUtils;
import org.hansel.myAlert.Log.Log;
import org.hansel.myAlert.Utils.PreferenciasHancel;
import org.hansel.myAlert.Utils.Util;

import java.util.Calendar;

public class LocationManagement extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener{

    public static final String TAG = LocationManagement.class.getSimpleName();
    private long trackId;
    private Handler handlerTime;
    private conexionWS ws;
    private int interval;
    private GoogleApiClient mGoogleApiClient;
    private Location location;
    private LocationRequest locationRequest;
    private boolean running;


    @Override
    public void onCreate(){
        handlerTime = new Handler();
        trackId = Util.getLastTrackId(getApplicationContext());
        Log.v("=== Valor del trackID en LocationManagement onCreate: " + trackId);
        interval = 3;
        running = false;
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId){
        trackId = Util.getLastTrackId(getApplicationContext());
        Log.v("=== Valor del trackID en LocationManagement onStartCommand: " + trackId);
        startLocationService();
        setupLocationForMap();
        if(!running){
            ws = new conexionWS();
            ws.execute();
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        trackId = Util.getLastTrackId(getApplicationContext());
        Log.v("=== Valor del trackID en LocationManagement onBind: " + trackId);
        return null;

    }

    public void onDestroy(){
        if(running)
            ws.cancel(true);
        stopLocationService();
        Log.v("=== En el Ondestroy");
    }

    public void stopLocationService() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        handlerTime.removeCallbacks(getData);
    }

    private final Runnable getData =
            new Runnable() {
        public void run() {

            getDataFrame();
        }
    };

    private void getDataFrame() {
        Log.v("=== Inicia Handler de Rastreo");
        //ws.execute();

        try {

                HttpUtils.sendTrack(PreferenciasHancel.getDeviceId(getApplicationContext())
                        , String.valueOf(trackId)
                        , String.valueOf(PreferenciasHancel.getUserId(getApplicationContext()))
                        , String.valueOf(location.getLatitude())
                        , String.valueOf(location.getLongitude())
                        , String.valueOf(Util.getBatteryLevel(getApplicationContext())));


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void setupLocationForMap() {
        long fastUpdate = Config.DEFAULT_INTERVAL_FASTER;
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setInterval(Config.DEFAULT_INTERVAL);
        locationRequest.setFastestInterval(fastUpdate);
    }

    private void startLocationService() {
        if( mGoogleApiClient == null || !mGoogleApiClient.isConnected()) {
            android.util.Log.i(TAG, "=== Iniciando servicio de geolocalizacion: NO CONECTADO -> CONECTADO");
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
            mGoogleApiClient.connect();
        }
        else
            android.util.Log.i(TAG, "=== Iniciando servicio de geolocalizacion: ESTABA CONECTADO");
    }

    @Override
    public void onConnected(Bundle bundle) {
        locationRequest = LocationRequest.create();
        setupLocationForMap();
        LocationServices.FusedLocationApi
                .requestLocationUpdates(mGoogleApiClient, locationRequest, this);

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            this.location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }

        Log.v("=== Se conectó: Latitud: " + this.location.getLatitude() + " Longitud: " + this.location.getLongitude());

        //startLocationService();
        //handlerTime.postDelayed(getData, Config.DEFAULT_INTERVAL);
        //getDataFrame();
        /*if (ws == null) {
            ws = new conexionWS();
            ws.execute();
        }*/

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v("=== Conexion suspendidaaaaaaaaaaaaaaaaaaaaaa");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.v("=== Cambió Location.  Anterior: " + this.location.getLatitude() + ", " + this.location.getLongitude() +
        " Nueva: " + location.getLatitude() + ", " + location.getLongitude());
        Log.v("=== Distancia entre puntos: " + this.location.distanceTo(location));
        this.location = location;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.v("=== Fallo la conexion");
    }


    private class conexionWS extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            //TODO: Aqui leer el lastLocation del servicio ServicioLeeBotonEncendido
            System.out.println("=== Latitud: " + location.getLatitude());
            System.out.println("=== Longitud: " + location.getLongitude());
            running =  true;
            try {
                while(true) {
                    Log.v("=== TrackID en el WS: " + trackId);
                    HttpUtils.sendTrack(PreferenciasHancel.getDeviceId(getApplicationContext())
                            , String.valueOf(trackId)
                            , String.valueOf(PreferenciasHancel.getUserId(getApplicationContext()))
                            , String.valueOf(location.getLatitude())
                            , String.valueOf(location.getLongitude())
                            , String.valueOf(Util.getBatteryLevel(getApplicationContext())));
                    Thread.sleep(Config.DEFAULT_INTERVAL);
                }
            } catch (Exception e) {
                running = false;
            }
            return null;
        }
    }
}

