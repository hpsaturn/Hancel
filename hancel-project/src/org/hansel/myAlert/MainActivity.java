package org.hansel.myAlert;
/*This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.
Created by Javier Mejia @zenyagami
zenyagami@gmail.com
 */
import static android.content.Intent.ACTION_MAIN;

import java.util.ArrayList;
import java.util.List;

import org.hansel.myAlert.Log.Log;
import org.hansel.myAlert.Utils.Util;
import org.holoeverywhere.app.Activity;
import org.linphone.LinphoneSimpleListener.LinphoneOnCallStateChangedListener;
import org.linphone.core.LinphoneAddress;
import org.linphone.core.LinphoneCall;
import org.linphone.core.LinphoneCall.State;
import org.linphone.core.LinphoneChatMessage;
import org.linphone.core.LinphoneCore.RegistrationState;
import org.linphone.core.LinphoneCoreException;
import org.linphone.core.LinphoneCoreFactory;
import org.linphone.core.LinphoneProxyConfig;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.linphone.ChatFragment;
import org.linphone.ChatListFragment;
import org.linphone.LinphoneService;
import org.linphone.LinphoneSimpleListener.LinphoneOnCallStateChangedListener;
import org.linphone.LinphoneSimpleListener.LinphoneOnMessageReceivedListener;
import org.linphone.LinphoneSimpleListener.LinphoneOnRegistrationStateChangedListener;


public class MainActivity extends Activity implements 
OnClickListener, ContactPicked, LinphoneOnCallStateChangedListener,
LinphoneOnMessageReceivedListener,LinphoneOnRegistrationStateChangedListener{
	
	ViewPager mViewPager;
	
	/** flag for panic button pressing**/
	private boolean panicPressed;
	
	/** Opened or started app frames **/
	private List<HancelFragments> hancelFragmentsAvailable;
	private HancelFragments currentFragment, nextFragment;
	private Fragment chatMessageFragment;
	
	/** Chat room variables **/
	private RelativeLayout chat;
	
	/** For singleton **/
	private static MainActivity instance;
	
	/** Recovering code after crashing Google Play services **/
	private static final int RECOVER_CODE_PLAY_SERVICES = 1001;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		ServicioLeeBotonEncendido.login = MainActivity.this;
		startService(new Intent(MainActivity.this,ServicioLeeBotonEncendido.class));
		super.onCreate(savedInstanceState);
		
		if(!isGooglePlayServicesAvailable()){
			finish();
		}
		
		//Setting the main content view and default frame 
		setContentView(R.layout.tabs);
				
		panicPressed = getIntent().getBooleanExtra("panico",false);
		Bundle data = null;
		
		if(panicPressed){
			data = new Bundle();
			data.putBoolean("panico", true);
		}
				
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		PanicButtonFragment panicFragment = new PanicButtonFragment();
		
		panicFragment.setArguments(data);
		fragmentTransaction.replace(R.id.status, panicFragment);
		fragmentTransaction.commit();
		initButtons();
		
		instance = this;
		hancelFragmentsAvailable = new ArrayList <HancelFragments>();
		currentFragment = nextFragment = HancelFragments.PANIC;
		hancelFragmentsAvailable.add(currentFragment);	
	}
	
	@Override
	protected void onResume() {
		// a guarantee for never incorrectly assume that Google Play Services 
		// is properly configured
		if(isGooglePlayServicesAvailable()){
			//TODO
		}
		super.onResume();
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		
		Bundle extras = intent.getExtras();		
		if (extras != null && extras.getBoolean("StartChat", false)) {
			LinphoneService.instance().removeMessageNotification();
			String sipUri = extras.getString("ChatContactSipUri");
			displayChat(sipUri);
		}
	}
	
	
	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		Log.v("onConfigurationChangedMain");
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);		
	}
	
	@Override
	public void onBackPressed() {
		this.moveTaskToBack(true);	
		super.onBackPressed();
	}
	
	static final boolean isInstanciated() {
		return instance != null;
	}

	public static final MainActivity instance() {
		if (instance != null)
			return instance;
		throw new RuntimeException("HancelMainActivity not instantiated yet");
	}
	
	
	private boolean isGooglePlayServicesAvailable(){
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplicationContext());

		if(status != ConnectionResult.SUCCESS){
			if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
				GooglePlayServicesUtil.getErrorDialog(status, this, 
						RECOVER_CODE_PLAY_SERVICES).show();				
			}
			else{
				Toast.makeText(this, "Device not supprted", 
						Toast.LENGTH_LONG).show();
				finish();
			}
			return false;
		}
		return true;
	}
			
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater infla = getSupportMenuInflater();
		infla.inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.CMD_PREFERENCES:
			if(!Util.isMyServiceRunning(getApplicationContext())){
				startActivity(new Intent(this, Preferencias.class ));
			}
			else{
				Toast.makeText(getApplicationContext(), 
						"No se puede modificar en este momento...",
					Toast.LENGTH_SHORT).show();
			}
			return true;
		case R.id.CMD_ACTIONS:
			
		default:
			return false;
		}
	}
	
	/*
	 * Initialization of menu buttons
	 */
	private void initButtons() {
		chat = (RelativeLayout) findViewById(R.id.chat);
		chat.setOnClickListener(this);
	}

	
	@Override
	public void onClick(View v) {
		int id = v.getId();
		resetSelection();
		if (id == R.id.chat) {
			//changeCurrentFragment()
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ft.replace(R.id.status, new ChatFragment());
			ft.commit();
			chat.setSelected(true);
		}
	}
	
	private void resetSelection() {
		chat.setSelected(false);		
	}
	
	
	public void displayChat(String sipUri) {
		if (getResources().getBoolean(R.bool.disable_chat)) {
			return;
		}

		LinphoneAddress lAddress;
		try {
			lAddress = LinphoneCoreFactory.instance().createLinphoneAddress(sipUri);
		} 
		catch (LinphoneCoreException e) {
			//TODO LOG("Cannot display chat",e);
			return;
		}
		
		//Uri uri = LinphoneUtils.findUriPictureOfContactAndSetDisplayName(lAddress, getContentResolver());
		String displayName = lAddress.getDisplayName();
		//String pictureUri = uri == null ? null : uri.toString();

		if (currentFragment == HancelFragments.CHAT || currentFragment == HancelFragments.CHATLIST) {
			Fragment fragment2 = getSupportFragmentManager().findFragmentById(R.id.status);
			if (fragment2 != null && fragment2.isVisible() && currentFragment == HancelFragments.CHAT) {
				ChatFragment chatFragment = (ChatFragment) fragment2;
				chatFragment.changeDisplayedChat(sipUri, displayName, null);
			} 
			else {
				Bundle extras = new Bundle();
				extras.putString("SipUri", sipUri);
				if (lAddress.getDisplayName() != null) {
					extras.putString("DisplayName", displayName);
					extras.putString("PictureUri", null);
				}
				changeCurrentFragment(HancelFragments.CHAT, extras);
			}
		} 
		else {
			changeCurrentFragment(HancelFragments.CHATLIST, null);
			displayChat(sipUri);
		}
		LinphoneService.instance().resetMessageNotifCount();
		LinphoneService.instance().removeMessageNotification();
		displayMissedChats(getChatStorage().getUnreadMessageCount());
	}
	
	
	private void changeCurrentFragment(HancelFragments newHancelFragment, Bundle extras){
		if (newHancelFragment == currentFragment && newHancelFragment != HancelFragments.CHAT) {
			return;
		}
		nextFragment = newHancelFragment;
		Fragment newFragment = null;
		
		switch(newHancelFragment){
		case CHAT:
			newFragment = new ChatFragment();
			chatMessageFragment = newFragment;
			break;
		case CHATLIST:
			newFragment = new ChatListFragment();
			messageListFragment = new Fragment();
			break;
		default:
		}		
	}

	@Override
	public void onCallStateChanged(LinphoneCall call, State state,
			String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegistrationStateChanged(LinphoneProxyConfig proxy,
			RegistrationState state, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessageReceived(LinphoneAddress from,
			LinphoneChatMessage message, int id) {
		// TODO Auto-generated method stub
		
	}		
}