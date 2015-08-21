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

public class LocationManagement extends Service implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener {

    public static final String TAG = LocationManagement.class.getSimpleName();

    private boolean mLocationTracking = true;
    private boolean mDisableTracking = false;
    private LocationManager mLocationManager;
    private static final int TWO_MINUTES = 1000 * 60 * 3;
    protected Location mLocation;
    private final Handler handler = new Handler();
    private int minutos = 3;
    private TelephonyManager tMgr;
    private long trackId;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;

    public LocationManagement() {

    }

    public void setMinutos(int tiempo) {

        this.minutos = tiempo;
    }

    private final Runnable getData = new Runnable() {
        public void run() {
            getDataFrame();
        }
    };

    private void getDataFrame() {
        Log.v("=== Inicia Handler de Rastreo");

        final boolean gpsEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (mLocationTracking && !mDisableTracking) {
            if (!gpsEnabled && isGPSToggleable()) {
                enableGPS();
            }
            mLocationTracking = true;
        }

        new conexionWS().execute();
        handler.postDelayed(getData, 1000 * 60 * minutos);

    }


    private void getLocation() {
        int attempts = 0;

        try {
            while (!mGoogleApiClient.isConnected() && attempts < 10) {
                Thread.sleep(1000);
                attempts++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }

    }

    public void stopGPS() {
        Log.v("Detenmos Rastreo");
        //mLocationManager.removeUpdates(this);

    }

    public void startGPS(String provider) {
        //escribe("inicia rastreo StartGps: " + provider);
        Log.v("Buscando provider" + provider);
        // mLocationManager.requestLocationUpdates(provider, NORMAL_INTERVAL,NORMAL_DISTANCE , this);
    }

    public void disableTracking() {
        // If the client is connected
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }

        mDisableTracking = true;
        handler.removeCallbacks(getData);
        stopGPS();
    }

    @Deprecated
    private boolean isGPSToggleable() {

        PackageManager pacman = getApplication().getPackageManager();
        PackageInfo pacInfo = null;
        Log.v("=== Intenamos ver si se puede activar GPS");
        try {
            pacInfo = pacman.getPackageInfo("com.android.settings",
                    PackageManager.GET_RECEIVERS);
        } catch (NameNotFoundException e) {
            return false;
        }
        if (pacInfo != null) {
            for (ActivityInfo actInfo : pacInfo.receivers) {
                if (actInfo.name
                        .equals("com.android.settings.widget.SettingsAppWidgetProvider")
                        && actInfo.exported) {
                    return true;
                }
            }
        }
        return false;
    }

    @Deprecated
    private void enableGPS() {
        Log.v("=== Intentamos Activar el GPS(no funciona en todas las versiones) ");
        String provider = Settings.Secure.getString(getApplication().getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (!provider.contains("gps")) {
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings",
                    "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            getApplication().sendBroadcast(poke);
        }
    }

    @Deprecated
    protected boolean isBetterLocation(Location location,
                                       Location currentBestLocation) {
        if (currentBestLocation == null) {
            return true;
        }

        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        if (isSignificantlyNewer) {
            return true;
        } else if (isSignificantlyOlder) {
            return false;
        }

        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation
                .getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate
                && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether two providers are the same
     */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    public void StartTracking() {
        handler.post(getData);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.v("On Loc Change");

        if (location != null) {
            String geoCodedLocation;
            geoCodedLocation = Util.geoCodeMyLocation(location.getLatitude(), location.getLongitude(), getApplicationContext());

            if (isBetterLocation(location, mLocation) && Geocoder.isPresent()) {
                try {

                    //enviamos al servidor el mensaje de reastreo
                    Log.v("Cambio de Localizacion: " + geoCodedLocation);
                } catch (IllegalArgumentException e) {

                }
                mLocation = location;
            }
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        tMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        showNotification();

        minutos = intent.getIntExtra("minutos", minutos);
        handler.post(getData);

        startLocationService();

        //indicador si el servicio esta iniciado,nos servir� si se reinicia el dispositivo
        //o alg�n otro metodo de inicio
        return (START_STICKY);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        mGoogleApiClient.connect();

    }

    @Override
    public void onDestroy() {
        disableTracking();
        stopForeground(true);
    }

    private void showNotification() {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        PendingIntent pi = PendingIntent.getActivity(this, 0,
                i, 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(getResources().getString(R.string.app_name))
                        .setContentText("Hansel Running")
                        .setContentIntent(pi);

        Notification notif = mBuilder.build();
        notif.flags = Notification.FLAG_NO_CLEAR;
        startForeground(1337, notif);
    }

    private class conexionWS extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try {
                getLocation();
                Log.v("=== userID en Location: " + trackId);
                HttpUtils.sendTrack(PreferenciasHancel.getDeviceId(getApplicationContext())
                        , String.valueOf(trackId)
                        , String.valueOf(PreferenciasHancel.getUserId(getApplicationContext()))
                        , String.valueOf(mLocation.getLatitude())
                        , String.valueOf(mLocation.getLongitude())
                        , String.valueOf(Util.getBatteryLevel(getApplicationContext())));
            } catch (Exception e) {
            }
            return null;
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        Log.v("[Home] LocationService: onConnectionFailed");
    }

    @Override
    public void onConnected(Bundle arg0) {

        Log.v("[Home] LocationService: onConnected");

        mLocationRequest = LocationRequest.create();
        setupLocationForMap();

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
//		mLocationClient.requestLocationUpdates(mLocationRequest, this);

    }

    private void startLocationService() {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }

    private void resumeLocationService() {
        mGoogleApiClient.connect();
    }

    private void stopLocationService() {
        mGoogleApiClient.disconnect();
    }

    private void setupLocationForMap() {
        Log.v("LocationService: setup for map");

        long UpdateInterval = (minutos * 60) * 1000 - (4000); //inicia actualizacion 20 segundos antes de obtener nuestros datos
        long fastUpdate = UpdateInterval - (60 * 1000); // actualiza el fast a la mitad de tiempo

        trackId = PreferenciasHancel.getUserId(getApplicationContext());
        //new google PLay service api
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(UpdateInterval);
        mLocationRequest.setFastestInterval(fastUpdate);
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

}

