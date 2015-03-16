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
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;

import org.hancel.adapters.customUi.TrackDialog;
import org.hancel.customclass.TrackDate;
import org.hansel.myAlert.Log.Log;
import org.hansel.myAlert.Utils.PreferenciasHancel;
import org.hansel.myAlert.Utils.SimpleCrypto;
import org.hansel.myAlert.Utils.Util;
import org.hansel.myAlert.dataBase.TrackDAO;
import org.hansel.myAlert.dataBase.UsuarioDAO;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PanicButtonFragment extends Fragment implements OnClickListener{	
	private static final String STOP_TRACK = "Detener";
	private static final String START_TRACK = "Programar Rastreo";
	private static final int RQS_1 = 12;
	private final int REQUEST_CODE = 0;
	private boolean corriendo=false;
	private int minutos;	
	private UsuarioDAO usuarioDao;
	private AlarmManager alarmManager;
	private TrackDAO track;	
	private View currentTrackInfo;
	private TextView txtCurrentTrackInfo, txtLastPanic;
	private TrackDate trackDate;
	private Button btnTracking, btnCancelCurrentTrack, btnModifyCurrentTrack, btnShareCurrentTrack;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		//WIFI	
		View v = inflater.inflate(R.layout.fragment_panic, container,false);
		Button btnPanico = (Button)v.findViewById(R.id.btnPanic);
		txtLastPanic =(TextView)v.findViewById(R.id.txtLastPanic);		
		btnPanico.setOnClickListener(new View.OnClickListener() {						
			@Override
			public void onClick(View v) {

				//Arregla el bug en el que al iniciar el servicio, no notifica a la Actividad de que
				//ya se estaa ejecutando, entonces no se puede detener inmediatamente.
				//btnTracking.setText(STOP_TRACK);

				AlertDialog.Builder alt_bld = new AlertDialog.Builder(getActivity());
				alt_bld.setMessage("Desea enviar la alerta?")
				.setCancelable(false)
				.setPositiveButton("Si", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						getActivity().startService(new Intent(getActivity(),SendPanicService.class));
						Toast.makeText(getActivity().getApplicationContext(), "Alerta enviada"
								, Toast.LENGTH_SHORT).show();
						//btnTracking.setText(STOP_TRACK);
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

				AlertDialog alert = alt_bld.create();
				alert.setTitle("Confirmación de alerta");
				alert.show();				
			}
		});
		
		//RASTREO
		btnTracking = (Button)v.findViewById(R.id.iniciaTrackId);
		usuarioDao = new UsuarioDAO(getActivity().getApplicationContext());
		usuarioDao.open();
		minutos = Util.getTrackingMinutes(getActivity().getApplicationContext());
		currentTrackInfo = v.findViewById(R.id.layoutCurrentTrack);
		txtCurrentTrackInfo = (TextView)v.findViewById(R.id.txtUltimaAlerta);
		btnCancelCurrentTrack = (Button)v.findViewById(R.id.btnCancelCurrentTrack);
		btnModifyCurrentTrack = (Button)v.findViewById(R.id.btnModifyCurrentTrack);
		btnShareCurrentTrack = (Button)v.findViewById(R.id.btnShareCurrentTrack);
		btnCancelCurrentTrack.setOnClickListener(this);
		btnModifyCurrentTrack.setOnClickListener(this);
		btnShareCurrentTrack.setOnClickListener(this);
		showCurrentTrackInfo(false); 

		Log.v("=== Buscando el tiempo por defecto para actualizar: " + minutos);
		if(savedInstanceState!=null)
		{
			corriendo = savedInstanceState.getBoolean("run");
			if(corriendo){
				btnTracking.setText(STOP_TRACK);
			}
			else{
				btnTracking.setText(START_TRACK);
			}
		}
		btnTracking.setOnClickListener(this); 	
		//REASTREO
		return v;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		txtLastPanic.setText(PreferenciasHancel.getLastPanicAlert(getActivity().getApplicationContext()));
		
		//RASTREO
		long time = PreferenciasHancel.getAlarmStartDate(getActivity());
		if(time != 0){
			Calendar currentTime = Calendar.getInstance();
			Calendar alarmTime = Calendar.getInstance();
			alarmTime.setTimeInMillis(time);
			//comparamos la fecha para saber si ya esta corriendo la alarma
			if(alarmTime.compareTo(currentTime)!=-1) {
				showCurrentTrackInfo(true);
			}
			txtCurrentTrackInfo.setText(Util.getSimpleDateFormatTrack(alarmTime) );
		}
		corriendo =Util.isMyServiceRunning(getActivity().getApplicationContext()); 
		setupButtonText();		
		//REASTREO
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//INICIO RASTREO
		alarmManager = (AlarmManager)getActivity().getSystemService(Activity.ALARM_SERVICE);
		track = new TrackDAO(getActivity().getApplicationContext());
		track.open();
		//Fin Rastreo
		try{
			ActivaRadios();
		}
		catch(Exception ex){
			Log.v("Error al activar los radios!!!!");
			ex.printStackTrace();
		}
		Bundle datos = getArguments();
		if(datos !=null){
			if(datos.getBoolean("panico")){
				//getActivity().moveTaskToBack(true);
			}
		}
	}

	private void ActivaRadios(){	
		Log.v("Intentamos activar Datos");
		try {
			setMobileDataEnabled(getActivity().getApplicationContext(), true);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		Log.v("Activamos WIFI");
		//WifiManager wifiManager = (WifiManager)getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
		//wifiManager.setWifiEnabled(true);

	}

	@SuppressWarnings("rawtypes")
	private void setMobileDataEnabled(Context context, boolean enabled) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, NoSuchFieldException, InvocationTargetException {
		final ConnectivityManager conman = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
		final Class conmanClass = Class.forName(conman.getClass().getName());
		final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
		iConnectivityManagerField.setAccessible(true);
		final Object iConnectivityManager = iConnectivityManagerField.get(conman);
		final Class iConnectivityManagerClass =  Class.forName(iConnectivityManager.getClass().getName());
		@SuppressWarnings("unchecked")
		final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
		setMobileDataEnabledMethod.setAccessible(true);

		setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled);
	}
	
	//RASTREO
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data!=null){
			cancelAlarms();
			PreferenciasHancel.setReminderCount(getActivity(), 0);
			trackDate = (TrackDate) data.getExtras().get(TrackDialog.TRACK_EXTRA);
			alarmManager.set(AlarmManager.RTC_WAKEUP, trackDate.getStartTimeTrack().getTimeInMillis(), Util.getReminderPendingIntennt(getActivity()));
			Log.v("Finalizando en: "+ new Date(trackDate.getEndTimeTrack().getTimeInMillis()) );
			alarmManager.set(AlarmManager.RTC_WAKEUP, trackDate.getEndTimeTrack().getTimeInMillis(), Util.getStopSchedulePendingIntentWithExtra(getActivity()));
			//guardamos inicio de alarma
			PreferenciasHancel.setAlarmStartDate(getActivity(), trackDate.getEndTimeTrack().getTimeInMillis());
			showCurrentTrackInfo(true);
			txtCurrentTrackInfo.setText(Util.getSimpleDateFormatTrack(trackDate.getStartTimeTrack()) );

		}
		else{
			super.onActivityResult(requestCode, resultCode, data);
		}

	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override  //RASTREO
	public void onDestroy() {
		super.onDestroy();
		try {
			if(track!=null)	{
				track.close();
			}
		} 
		catch (Exception e) {
		}
	}

	@Override //RASTREO
	public void onClick(View v) {
		if (btnTracking.getText() == STOP_TRACK) {
			createPasswordDialog(btnTracking);
			return;
		}
		switch (v.getId()) {
		case R.id.btnCancelCurrentTrack:			
		case R.id.btnModifyCurrentTrack:
			createPasswordDialog((Button) v);	
			break;
		case R.id.btnShareCurrentTrack:
			shareTrace();
			break;
		case R.id.iniciaTrackId:
			if(!corriendo){
				startActivityForResult(new Intent(getActivity(), TrackDialog.class),REQUEST_CODE );
			}
			else{				
				createPasswordDialog(btnTracking);
			}
			break;
		default:
			break;
		}
	}
	
	@Override  //RASTREO
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if(outState!=null){
			outState.putBoolean("run", corriendo);
		}
	}
	
	//RASTREO
	protected void createPasswordDialog(final Button btnPanico) {
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());                 
		alert.setTitle("Contraseña para cancelar...");  
		alert.setMessage("Contraseña:");                

		// Set an EditText view to get user input   
		final EditText input = new EditText(getActivity());
		input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {  
			public void onClick(DialogInterface dialog, int whichButton) {  
				String value = input.getText().toString();
				String crypto = SimpleCrypto.md5(value);	  

				boolean isOK = usuarioDao.getPassword(crypto);	           	          

				if(isOK && btnPanico.getId()== R.id.iniciaTrackId ){
					Log.v("Detener Rastreo");
					alarmManager.cancel(getPendingAlarm());
					btnPanico.setText(START_TRACK);
					Util.setRunningService(getActivity().getApplicationContext(), false);
					getActivity().stopService(new Intent(getActivity().getApplicationContext()
							,LocationManagement.class));
					alarmManager.cancel(Util.getPendingAlarmPanicButton(getActivity().getApplicationContext()));
					corriendo = false;

					Toast.makeText(getActivity(), "Rastreo Detenido", Toast.LENGTH_SHORT).show();
					PreferenciasHancel.setAlarmStartDate(getActivity(), 0);
					return;                  
				}
				else if(isOK && btnPanico.getId() == R.id.btnCancelCurrentTrack ){
					//cancelamos alarma para iniciar servicio
					//alarmManager.cancel(Util.getServicePendingIntent (getActivity()));
					cancelAlarms();
					Toast.makeText(getActivity(), "Programación de Rastreo Cancelado", Toast.LENGTH_SHORT).show();
					showCurrentTrackInfo(false);
					PreferenciasHancel.setAlarmStartDate(getActivity(), 0);
				}
				else if(isOK && btnPanico.getId()==R.id.btnModifyCurrentTrack){
					startActivityForResult(new Intent(getActivity(), TrackDialog.class),REQUEST_CODE );
				}
				else{
					Toast.makeText(getActivity(), "Contraseña Incorrecta", 
							Toast.LENGTH_SHORT).show();
					return;
				}
			}
		});  

		alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				return;   
			}
		});
		alert.show();
	}
	
	//RASTREO
	private void setupButtonText() {
		if(corriendo){
			showCurrentTrackInfo(false);
			btnTracking.setText(STOP_TRACK);

		}
		else{
			btnTracking.setText(START_TRACK);
		}
	}
	
	//RASTREO
	private void shareTrace() {
		Intent share = new Intent(android.content.Intent.ACTION_SEND);
		share.setType("text/plain");
		share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		share.putExtra(Intent.EXTRA_SUBJECT, "[Hance] Traza de periodista");
		String h = SimpleCrypto.md5(Integer.toString(PreferenciasHancel.getUserId(getActivity().getApplicationContext())));
		share.putExtra(Intent.EXTRA_TEXT, "http://hancel.flip.org.co/hansel/?f=trace&hash="+h);
		startActivity(Intent.createChooser(share, "Share trace!"));
	}
	
	//RASTREO
	private void cancelAlarms() {
		alarmManager.cancel(Util.getReminderPendingIntennt(getActivity()));
		alarmManager.cancel(Util.getStopSchedulePendingIntentWithExtra(getActivity()));
	}
	
	//Rastreo
	private PendingIntent getPendingAlarm()	{
		Intent intent = new Intent(getActivity().getApplicationContext()
				, AlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), RQS_1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		return pendingIntent;
	}
	
	//RASTREO
	public boolean isTracking(){
		return corriendo;
	}
	
	//RASTREO
	private void showCurrentTrackInfo(boolean showTrackInfo){
		Log.v("=== ShowTrackInfo " + showTrackInfo);
		if(showTrackInfo){
			currentTrackInfo.setVisibility(View.VISIBLE);
			btnTracking.setVisibility(View.GONE);
		}
		else{
			currentTrackInfo.setVisibility(View.GONE);
			btnTracking.setVisibility(View.VISIBLE);
		}
	}

	//RASTREO
	public void panicButtonPressed(){
		//se presiona boton de panico, cancelamos "servicio" si aun no esta corriendo y cancelamos la alarma
		showCurrentTrackInfo(false);
		//cancelamos la alarma antes de ejecutar el servicio por el bot�n de p�nico
		//si esta corriendo es que corri� por causa de la alarma
		if(!Util.isMyServiceRunning(getActivity())){
			alarmManager.cancel(Util.getServicePendingIntent(getActivity()));
		}
		//corriendo siempre ser� "true" por que al presionar el bot�n de p�nico
		// se inizializa el servicio
		corriendo = true;
		setupButtonText();		
	}
}
