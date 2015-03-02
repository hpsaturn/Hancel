package org.hansel.myAlert.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class HancelService extends Service implements HancelServiceListener {
	
	@Override
	public IBinder onBind(Intent intent) {
		
		return null;
	}

}
