package org.hansel.myAlert;


import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;


public class AlarmReceiver extends SherlockActivity{
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		AlarmFragment alarm = new AlarmFragment();
		alarm.show();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	


}
