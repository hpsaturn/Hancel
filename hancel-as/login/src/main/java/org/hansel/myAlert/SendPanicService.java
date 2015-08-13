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

import android.app.AlarmManager;
import android.app.Service;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.hancel.http.HttpUtils;
import org.hansel.myAlert.Log.Log;
import org.hansel.myAlert.Utils.PreferenciasHancel;
import org.hansel.myAlert.Utils.Util;
import org.hansel.myAlert.dataBase.FlipDAO;
import org.hansel.myAlert.dataBase.RingDAO;
import org.linphone.LinphoneManager;
import org.linphone.compatibility.Compatibility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SendPanicService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private ejecutaPanico mTask;
    private String result;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;

    /*
     * (non-Javadoc)
     *
     * @see android.app.Service#onCreate()
     */
    @Override
    public void onCreate() {
        super.onCreate();
        startLocationService();
    }

    /* (non-Javadoc)
     * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public Location getLocation() {
        Location loc = null;
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
            loc = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }

        if (loc == null) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria req = new Criteria();
            req.setAccuracy(Criteria.ACCURACY_FINE);
            req.setHorizontalAccuracy(Criteria.ACCURACY_MEDIUM);
            List<String> providersList = locationManager.getProviders(req, false);
            int i = 0;

            while (!locationManager.isProviderEnabled(providersList.get(i)) &&
                    i < providersList.size()) {
                i++;
            }

            if (i < providersList.size())
                loc = locationManager.getLastKnownLocation(providersList.get(i));

            if (loc != null) {
                Log.v("=== LAT: " + loc.getLatitude());
                Log.v("=== LON: " + loc.getLongitude());
            }
        }
        return loc;
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.v("On Loc Change");

    }

    public class ejecutaPanico extends AsyncTask<Void, Void, Void> {
        Contacts con = new Contacts(getApplicationContext());

        @Override
        protected void onCancelled() {
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.v("=== Iniciando Panico");
            Location loc = getLocation();

            double Lat = 0;
            double Long = 0;
            String mapa = "";
            // String direccion="";
            if (loc != null) {
                Lat = loc.getLatitude();
                Long = loc.getLongitude();
                mapa = getString(R.string.tracking_loc_aprox) + "http://maps.google.com/?q=" + Lat + "," + Long + "\n";

            }
            RingDAO ringDao = new RingDAO(LinphoneManager.getInstance()
                    .getContext());
            FlipDAO flipDao = new FlipDAO(LinphoneManager.getInstance()
                    .getContext());

            List<String> numbers = new ArrayList<String>();
            flipDao.open();
            Cursor fc = flipDao.getSettingsValueByKey(getResources().getString(
                    R.string.contacts_flip));

            if (fc != null && fc.getCount() > 0) {
                fc.moveToFirst();
                String nums = fc.getString(1);
                if (nums != null && nums.length() > 0) {
                    String[] s = nums.split(",");
                    for (int i = 0; i < s.length; i++) {
                        numbers.add(s[i].replace('"', ' ').trim());
                        Log.v("=== Numero FLIP:" + numbers);
                    }
                }
            }
            flipDao.close();
            ringDao.open();
            Cursor c = ringDao.getNotificationContactsId();

            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                for (int i = 0; i < c.getCount(); i++) {
                    List<String> contactNumbers = Compatibility
                            .extractContactNumbers(c.getString(0),
                                    getContentResolver());
                    if (contactNumbers != null && contactNumbers.size() > 0)
                        numbers.addAll(contactNumbers);
                    c.moveToNext();
                }
            }

            if (numbers.size() == 0) {
                result = getString(R.string.no_configured_rings);
            } else {
                Log.v("=== Numero de mensajes a enviar: " + numbers.size());
                SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String message = preferencias.getString("pref_key_custom_msg",
                        getString(R.string.tracking_help));
                int fails = 0;
                for (int i = 0; i < numbers.size(); i++) {
                    try {

                        String number = numbers.get(i).replaceAll("\\D+", "");
                        Log.v("=== Numero : " + number);
                        if (number != null && number.length() > 0) {
                            Log.v("=== Enviando SMS a : " + number);
                            enviarSMS(number, message + mapa + getString(R.string.tracking_battery_level) +
                                    getNivelBateria() + "%");
                        }
                    } catch (Exception ex) {
                        Log.v("Ocurrio un Error al enviar SMS. Excepcion: " +
                                ex.getMessage());
                        fails += 1;
                    }
                }
                if (fails == numbers.size()) {
                    result = getString(R.string.tracking_invalid_contac_numbers);
                } else {
                    result = "OK";
                }
            }
            try {
                HttpUtils.sendPanic(PreferenciasHancel.getDeviceId(getApplicationContext()),
                        String.valueOf(PreferenciasHancel.getUserId(getApplicationContext())),
                        String.valueOf(loc.getLatitude()),
                        String.valueOf(loc.getLongitude()),
                        String.valueOf(getNivelBateria()),
                        "", "");
            } catch (Exception ex) {
                Log.v("Error al Enviar el track: " + ex.getMessage());
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void r) {
            super.onPostExecute(r);
            // Cancelamos alarma
            cancelAlarms();
            if (!Util.isMyServiceRunning(getApplicationContext())) {
                Util.inicarServicio(getApplicationContext());
            }
            // Muestra la fecha de la ultima vez que se corrio el panico y
            // guarda la nueva fecha si el resultado del envio de SMS fue OK
            if (result.equalsIgnoreCase("OK")) {
                String currentDateandTime = Util.getSimpleDateFormatTrack(Calendar
                        .getInstance());
                PreferenciasHancel.setLastPanicAlert(getApplicationContext(),
                        currentDateandTime);
                Toast.makeText(getApplicationContext(), getString(R.string.tracking_launched),
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
            stopSelf();
        }
    }

    private void cancelAlarms() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(Util
                .getReminderPendingIntennt(getApplicationContext()));
        alarmManager
                .cancel(Util
                        .getStopSchedulePendingIntentWithExtra(getApplicationContext()));

    }

    /**
     * envia un SMS si es muy largo envia varios
     *
     * @param telefono (telefono al que se le enviara el SMS)
     * @param mensaje  (mensaje de panico)
     */
    public void enviarSMS(String telefono, String mensaje) {
        SmsManager sms = SmsManager.getDefault();
        try {
            sms.sendTextMessage(telefono, null, mensaje, null, null);
            Log.v("=== Mensaje enviado a " + telefono);
        } catch (Exception e) {
            Log.v(e.getMessage());
            try {
                ArrayList<String> parts = sms.divideMessage(mensaje);
                sms.sendMultipartTextMessage(telefono, null, parts, null, null);
                Log.v("=== Mensaje enviado en partes " + telefono);
            } catch (Exception i) {
                Log.v("=== Error al enviar , falla al envio de SMS");
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onConnected(Bundle arg0) {

        if (mTask == null) {
            mTask = new ejecutaPanico();
            mTask.execute();
        }

        Log.v("[Home] LocationService: onConnected");

        mLocationRequest = LocationRequest.create();
        setupLocationForMap();

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
//		mLocationClient.requestLocationUpdates(mLocationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

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

        long fastUpdate = Config.DEFAULT_INTERVAL - (60 * 1000); // actualiza el fast a la mitad de tiempo
        //new google PLay service api
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(Config.DEFAULT_INTERVAL);
        mLocationRequest.setFastestInterval(fastUpdate);
    }


    /**
     * revisa el nivel de bateria del celular
     *
     * @return int ( nivel de bateria)
     */
    private int getNivelBateria() {
        Intent i = new ContextWrapper(this).registerReceiver(null,
                new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        return i.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
    }
}
