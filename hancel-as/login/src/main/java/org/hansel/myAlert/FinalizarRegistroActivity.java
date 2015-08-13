package org.hansel.myAlert;

import org.hansel.myAlert.Log.Log;
import org.hansel.myAlert.Utils.PreferenciasHancel;
import org.hansel.myAlert.Utils.Util;
import org.hansel.myAlert.dataBase.ContactoDAO;
import org.hansel.myAlert.dataBase.RingDAO;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;



public class FinalizarRegistroActivity extends FragmentActivity implements OnClickListener{

	/* (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		//super.onBackPressed(); //no se puede regresar solo aceptar
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registro_layout_04);
		PreferenciasHancel.setCurrentWizardStep(getApplicationContext(), Util.REGISTRO_PASO_4);
		Button btnFinalizar = (Button)findViewById(R.id.btnFinalizarRegistro);
		Button cancelButton = (Button)findViewById(R.id.btnCancelarRegistro);
		cancelButton.setOnClickListener(this);
		btnFinalizar.setOnClickListener(this);
		
		ContactoDAO contactDao = new ContactoDAO(getApplicationContext());
		contactDao.open();
		Cursor c = contactDao.getList();
		
		if(c != null && c.getCount() > 0){
			c.moveToFirst();
			RingDAO ringDao = new RingDAO(getApplicationContext());
			ringDao.open();
			long idRing = ringDao.addRing("DEFAULT",true);
			for(int i = 0; i < c.getCount(); i++){							
				ringDao.addContactToRing(c.getString(1), String.valueOf(idRing));
				Log.v("=== Padre: " + c.getString(1));
				Log.v("=== id: " + c.getString(0));
				c.moveToNext();
			}
			ringDao.close();
		}
		contactDao.close();
	}

	@SuppressLint("InlinedApi")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnFinalizarRegistro:
			Intent i = new Intent(getApplicationContext(), MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_NEW_TASK);
			if(Util.isICS()) i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); 
			startActivity(i);
			break;
		case R.id.btnCancelarRegistro:
			Intent cancel = new Intent(getApplicationContext(), Registro.class);
			cancel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
			if(Util.isICS()) cancel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(cancel);
			break;
		default:
			break;
		}
		
	}

}
