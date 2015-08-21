package org.hansel.myAlert;


import java.util.Timer;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.os.Handler;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.os.Vibrator;
import android.util.Log;


/**
 * @author mikesaurio
 */
public class ServicioLeeBotonEncendido extends Service {

    private String TAG = "ServicioGeolocalizacion";
    private boolean isFirstTime = true;
    private boolean isSendMesagge = false;
    private Timer timer;
    public static boolean serviceIsIniciado = false;
    public static boolean countTimer = true;
    private static int countStart = -1;
    private BroadcastReceiver mReceiver;
    private Handler handler_time = new Handler();
    private ResultReceiver resultReceiver;


    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        mReceiver = new MyReceiver();
        registerReceiver(mReceiver, filter);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isFirstTime) {
            isFirstTime = false;
            serviceIsIniciado = true;
        }
        try {
            resultReceiver = intent.getParcelableExtra("receiver");
            if (countStart >= 4) {
                Log.i(TAG, "mas de 4");
                countStart = -1;
                countTimer = true;

                // activamos el mensaje de auxilio
                isSendMesagge = true;
                getApplicationContext().startService(new Intent(getApplicationContext(), SendPanicService.class));
                vibrate(5000);
            }
            else {
                countStart += 1;
                // contamos 5 segundos si no reiniciamos los contadores
                if (countTimer) {
                    countTimer = false;
                    handler_time.postDelayed(runnable, 5000);
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "vino null");
        }

        return super.onStartCommand(intent,flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        serviceIsIniciado = false;
        unregisterReceiver(mReceiver);
    }

    @Override
    public IBinder onBind(Intent intencion) {

        return null;
    }


    /**
     * thread for restarting values
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //restart counters
            countStart = -1;
            countTimer = true;
        }
    };


    public void vibrate(long time) {
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(time);
    }


}