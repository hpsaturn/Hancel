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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.hansel.myAlert.Log.Log;
<<<<<<< HEAD
import org.hansel.myAlert.dataBase.RingDAO;

import org.holoeverywhere.app.Activity;
import org.linphone.LinphoneManager.AddressType;
import org.linphone.LinphoneSimpleListener.LinphoneOnCallStateChangedListener;
import org.linphone.compatibility.Compatibility;
import org.linphone.core.LinphoneAddress;
import org.linphone.core.LinphoneCall;
import org.linphone.core.LinphoneCall.State;
import org.linphone.core.LinphoneCallLog.CallStatus;
import org.linphone.core.LinphoneChatMessage;
import org.linphone.core.LinphoneCore.RegistrationState;
import org.linphone.core.CallDirection;
import org.linphone.core.LinphoneAuthInfo;
import org.linphone.core.LinphoneCallLog;
import org.linphone.core.LinphoneCore;
import org.linphone.core.LinphoneCoreException;
import org.linphone.core.LinphoneCoreFactory;
import org.linphone.core.LinphoneFriend;
import org.linphone.core.LinphoneProxyConfig;
import org.linphone.ui.AddressText;

=======
import org.hansel.myAlert.Utils.PreferenciasHancel;
import org.hansel.myAlert.WelcomeInfo.StartFragment;


import org.holoeverywhere.app.Activity;
import org.linphone.LinphoneManager.AddressType;
import org.linphone.LinphoneSimpleListener.LinphoneOnCallStateChangedListener;
import org.linphone.compatibility.Compatibility;
import org.linphone.core.LinphoneAddress;
import org.linphone.core.LinphoneCall;
import org.linphone.core.LinphoneCall.State;
import org.linphone.core.LinphoneCallLog.CallStatus;
import org.linphone.core.LinphoneChatMessage;
import org.linphone.core.LinphoneCore.MediaEncryption;
import org.linphone.core.LinphoneCore.RegistrationState;
import org.linphone.core.CallDirection;
import org.linphone.core.LinphoneAuthInfo;
import org.linphone.core.LinphoneCallLog;
import org.linphone.core.LinphoneCore;
import org.linphone.core.LinphoneCoreException;
import org.linphone.core.LinphoneCoreFactory;
import org.linphone.core.LinphoneFriend;
import org.linphone.core.LinphoneProxyConfig;
import org.linphone.ui.AddressText;

>>>>>>> second_stage
import android.net.Uri;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment.SavedState;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.linphone.AboutFragment;
import org.linphone.AccountPreferencesFragment;
import org.linphone.ChatFragment;
import org.linphone.ChatListFragment;
import org.linphone.ChatMessage;
import org.linphone.ChatStorage;
import org.linphone.Contact;
import org.linphone.ContactFragment;
import org.linphone.ContactsFragment;
import org.linphone.EditContactFragment;
import org.linphone.FragmentsAvailable;
import org.linphone.HistoryDetailFragment;
import org.linphone.HistoryFragment;
import org.linphone.HistorySimpleFragment;
import org.linphone.InCallActivity;
import org.linphone.IncomingCallActivity;

<<<<<<< HEAD
=======
import org.linphone.AboutFragment;
import org.linphone.AccountPreferencesFragment;
import org.linphone.ChatFragment;
import org.linphone.ChatListFragment;
import org.linphone.ChatMessage;
import org.linphone.ChatStorage;
import org.linphone.Contact;
import org.linphone.ContactFragment;
import org.linphone.ContactsFragment;
import org.linphone.EditContactFragment;
import org.linphone.FragmentsAvailable;
import org.linphone.HistoryDetailFragment;
import org.linphone.HistoryFragment;
import org.linphone.HistorySimpleFragment;
import org.linphone.InCallActivity;
import org.linphone.IncomingCallActivity;

>>>>>>> second_stage
import org.linphone.LinphoneManager;
import org.linphone.LinphonePreferences;
import org.linphone.LinphoneService;
import org.linphone.SettingsFragment;
import org.linphone.StatusFragment;

import org.linphone.LinphoneSimpleListener.LinphoneOnMessageReceivedListener;
import org.linphone.LinphoneSimpleListener.LinphoneOnRegistrationStateChangedListener;
import org.linphone.LinphoneUtils;


public class MainActivity extends Activity implements 
OnClickListener, ContactPicked, LinphoneOnCallStateChangedListener,
LinphoneOnMessageReceivedListener,LinphoneOnRegistrationStateChangedListener{
<<<<<<< HEAD
	
	ViewPager mViewPager;
	
=======

	ViewPager mViewPager;

>>>>>>> second_stage
	/** flag for panic button pressing**/
	private boolean panicPressed;	
	/** For singleton **/
	private static MainActivity instance;	
	/** Recovering code after crashing Google Play services **/
	private static final int RECOVER_CODE_PLAY_SERVICES = 1001;
<<<<<<< HEAD
	
	private static final String TAG = "Hancel";
	
	public static final String PREF_FIRST_LAUNCH = "pref_first_launch";
	private static final int SETTINGS_ACTIVITY = 123;
		private static final int CALL_ACTIVITY = 19;

=======
	public static final String PREF_FIRST_LAUNCH = "pref_first_launch";
	private static final int SETTINGS_ACTIVITY = 123;
	private static final int CALL_ACTIVITY = 19;
>>>>>>> second_stage
	private StatusFragment statusFragment;
	private TextView missedCalls, missedChats;
	private ImageView dialer;
	private LinearLayout menu, mark;
<<<<<<< HEAD
	private RelativeLayout contacts, history, settings, chat, aboutChat, aboutSettings, rings;
=======
	private RelativeLayout contacts, history, settings, chat, about, rings;
>>>>>>> second_stage
	private FragmentsAvailable currentFragment, nextFragment;
	private List<FragmentsAvailable> fragmentsHistory;
	private Fragment panicFragment,messageListenerFragment, messageListFragment, friendStatusListenerFragment;
	private SavedState  panicSavedState;
	private boolean preferLinphoneContacts = false, isAnimationDisabled = false, isContactPresenceDisabled = true;
	private Handler mHandler = new Handler();
	private List<Contact> contactList, sipContactList;
<<<<<<< HEAD
	private List<Ring> allRings;
	private Cursor contactCursor, sipContactCursor, ringsCursor;
	private OrientationEventListener mOrientationHelper;
	
	
=======

	private Cursor contactCursor, sipContactCursor;
	private OrientationEventListener mOrientationHelper;


>>>>>>> second_stage
	public static final boolean isInstanciated() {
		return instance != null;
	}

	public static final MainActivity instance() {
		if (instance != null)
			return instance;
<<<<<<< HEAD
		throw new RuntimeException("LinphoneActivity not instantiated yet");
=======
		throw new RuntimeException("MainActivity not instantiated yet");
>>>>>>> second_stage
	}

	
	@Override
<<<<<<< HEAD
	protected void onCreate(Bundle savedInstanceState) {		
		ServicioLeeBotonEncendido.login = MainActivity.this;
		startService(new Intent(MainActivity.this,ServicioLeeBotonEncendido.class));
=======
	protected void onCreate(Bundle savedInstanceState) {	
		ServicioLeeBotonEncendido.login = MainActivity.this;
		startService(new Intent(MainActivity.this,ServicioLeeBotonEncendido.class));
		startService(new Intent(ACTION_MAIN).setClass(this, 
				LinphoneService.class));
>>>>>>> second_stage
		super.onCreate(savedInstanceState);
		
		if(!isGooglePlayServicesAvailable())
			finish();
				
		setContentView(R.layout.tabs);
		instance = this;		
				
<<<<<<< HEAD
		if(!isGooglePlayServicesAvailable()){
			finish();
		}
		
		//Setting the main content view and default frame 
		setContentView(R.layout.tabs);
				
		panicPressed = getIntent().getBooleanExtra("panico",false);
		Bundle data = null;
		
=======
		if(!PreferenciasHancel.getLoginOk(getApplicationContext())){
			showStartFragment();			
		}
		else			
			showMainFragment();				
	}
	
	/*
	 * Shows the welcome fragment to register or login
	 */
	private void showStartFragment(){				
		StartFragment start = new StartFragment();	
		currentFragment = FragmentsAvailable.START;
		
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();				
		fragmentTransaction.replace(R.id.activityContainer, start);		
		fragmentTransaction.addToBackStack("");
		fragmentTransaction.commit();		
	}
	

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		Log.v("onConfigurationChangedMain");
	}
	
	public void showMainFragment(){					
		panicPressed = getIntent().getBooleanExtra("panico",false);
		Bundle data = null;

>>>>>>> second_stage
		if(panicPressed){
			data = new Bundle();
			data.putBoolean("panico", true);
		}
<<<<<<< HEAD
				
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		panicFragment = new PanicButtonFragment();
		
		panicFragment.setArguments(data);
		fragmentTransaction.replace(R.id.fragmentContainer, panicFragment);
		fragmentTransaction.commit();
		initButtons();
		
		instance = this;
		fragmentsHistory = new ArrayList <FragmentsAvailable>();
		currentFragment = nextFragment = FragmentsAvailable.PANIC;
		fragmentsHistory.add(currentFragment);	
	}
	
	
	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		Log.v("onConfigurationChangedMain");
	}
	

	private void initButtons() {
		menu = (LinearLayout) findViewById(R.id.menu);
		mark = (LinearLayout) findViewById(R.id.mark);

		history = (RelativeLayout) findViewById(R.id.history);
		history.setOnClickListener(this);
		contacts = (RelativeLayout) findViewById(R.id.contacts);
		contacts.setOnClickListener(this);
		dialer = (ImageView) findViewById(R.id.dialer);
		dialer.setOnClickListener(this);
		settings = (RelativeLayout) findViewById(R.id.settings);
		settings.setOnClickListener(this);
		rings = (RelativeLayout) findViewById(R.id.rings);
		rings.setOnClickListener(this);
		chat = (RelativeLayout) findViewById(R.id.chat);
		chat.setOnClickListener(this);
		aboutChat = (RelativeLayout) findViewById(R.id.about_chat);
		aboutSettings = (RelativeLayout) findViewById(R.id.about_settings);

		if (getResources().getBoolean(R.bool.replace_chat_by_about)) {
			chat.setVisibility(View.GONE);
			chat.setOnClickListener(null);
			findViewById(R.id.completeChat).setVisibility(View.GONE);
			aboutChat.setVisibility(View.VISIBLE);
			aboutChat.setOnClickListener(this);
		}
		if (getResources().getBoolean(R.bool.replace_settings_by_about)) {
			settings.setVisibility(View.GONE);
			settings.setOnClickListener(null);
			aboutSettings.setVisibility(View.VISIBLE);
			aboutSettings.setOnClickListener(this);
		}

		missedCalls = (TextView) findViewById(R.id.missedCalls);
		missedChats = (TextView) findViewById(R.id.missedChats);
	}
	
	private boolean isTablet() {
		return getResources().getBoolean(R.bool.isTablet);
	}

	public void hideStatusBar() {
		if (isTablet()) {
			return;
		}
		
		findViewById(R.id.status).setVisibility(View.GONE);
		findViewById(R.id.fragmentContainer).setPadding(0, 0, 0, 0);
	}

	public void showStatusBar() {
		if (isTablet()) {
			return;
		}
		
		if (statusFragment != null && !statusFragment.isVisible()) {
			// Hack to ensure statusFragment is visible after coming back to
			// dialer from chat
			statusFragment.getView().setVisibility(View.VISIBLE);
		}
		findViewById(R.id.status).setVisibility(View.VISIBLE);
		findViewById(R.id.fragmentContainer).setPadding(0, LinphoneUtils.pixelsToDpi(getResources(), 40), 0, 0);
	}
	
	
	private void changeCurrentFragment(FragmentsAvailable newFragmentType, Bundle extras) {
		changeCurrentFragment(newFragmentType, extras, false);
	}

	@SuppressWarnings("incomplete-switch")
	private void changeCurrentFragment(FragmentsAvailable newFragmentType, Bundle extras, boolean withoutAnimation) {
		if (newFragmentType == currentFragment && newFragmentType != FragmentsAvailable.CHAT) {
			return;
		}
		nextFragment = newFragmentType;		
		Fragment newFragment = null;

		switch (newFragmentType) {
		case HISTORY:
			if (getResources().getBoolean(R.bool.use_simple_history)) {
				newFragment = new HistorySimpleFragment();
			} 
			else {
				newFragment = new HistoryFragment();
			}
			break;
		case HISTORY_DETAIL:
			newFragment = new HistoryDetailFragment();
			break;
		case CONTACTS:
			if (getResources().getBoolean(R.bool.use_android_native_contact_edit_interface)) {
				Intent i = new Intent();
			    i.setComponent(new ComponentName("com.android.contacts", "com.android.contacts.DialtactsContactsEntryActivity"));
			    i.setAction("android.intent.action.MAIN");
			    i.addCategory("android.intent.category.LAUNCHER");
			    i.addCategory("android.intent.category.DEFAULT");
			    startActivity(i);
			} 
			else {
				newFragment = new ContactsFragment();
				friendStatusListenerFragment = newFragment;
			}
			break;
		case CONTACT:
			newFragment = new ContactFragment();
			break;
		case EDIT_CONTACT:			
			newFragment = new EditContactFragment();
			break;
		case PANIC:
			newFragment = new PanicButtonFragment();
			if (extras == null) {
				newFragment.setInitialSavedState(panicSavedState);
			}
			break;	
		case SETTINGS:
			newFragment = new SettingsFragment();
			break;
		case RINGS:
			newFragment = new RingsFragment();
			break;
		case EDIT_RING:
			Log.v("=== Cambianbdo frame a EditRing");
			newFragment = new EditRingFragment();			
			break;
		case ACCOUNT_SETTINGS:
			newFragment = new AccountPreferencesFragment();
			break;
		case ABOUT:
		case ABOUT_INSTEAD_OF_CHAT:
		case ABOUT_INSTEAD_OF_SETTINGS:
			newFragment = new AboutFragment();
			break;
		case CHAT:
			newFragment = new ChatFragment();
			messageListenerFragment = newFragment;
			break;
		case CHATLIST:
			newFragment = new ChatListFragment();
			messageListFragment = new Fragment();
			break;
		}

		if (newFragment != null) {
			newFragment.setArguments(extras);
			if (isTablet()) {
				changeFragmentForTablets(newFragment, newFragmentType, withoutAnimation);
			} 
			else {
				changeFragment(newFragment, newFragmentType, withoutAnimation);
			}
		}
	}

	private void updateAnimationsState() {
		isAnimationDisabled = getResources().getBoolean(R.bool.disable_animations) || !LinphonePreferences.instance().areAnimationsEnabled();
		isContactPresenceDisabled = !getResources().getBoolean(R.bool.enable_linphone_friends);
	}


	public boolean isAnimationDisabled() {
		return isAnimationDisabled;
	}

	public boolean isContactPresenceDisabled() {
		return isContactPresenceDisabled;
	}

	private void changeFragment(Fragment newFragment, FragmentsAvailable newFragmentType, boolean withoutAnimation) {
		if (statusFragment != null) {
			statusFragment.closeStatusBar();
		}

		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

		if (!withoutAnimation && !isAnimationDisabled && currentFragment.shouldAnimate()) {
			if (newFragmentType.isRightOf(currentFragment)) {
				transaction.setCustomAnimations(R.anim.slide_in_right_to_left,
						R.anim.slide_out_right_to_left,
						R.anim.slide_in_left_to_right,
						R.anim.slide_out_left_to_right);
			} else {
				transaction.setCustomAnimations(R.anim.slide_in_left_to_right,
						R.anim.slide_out_left_to_right,
						R.anim.slide_in_right_to_left,
						R.anim.slide_out_right_to_left);
			}
		}
		try {
			getSupportFragmentManager().popBackStackImmediate(newFragmentType.toString(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
		} 
		catch (java.lang.IllegalStateException e) {
			e.printStackTrace();
		}

		transaction.addToBackStack(newFragmentType.toString());
		transaction.replace(R.id.fragmentContainer, newFragment, newFragmentType.toString());
		Log.v("=== Cambiando a fragment " + newFragmentType.toString());
		transaction.commitAllowingStateLoss();
		getSupportFragmentManager().executePendingTransactions();

		currentFragment = newFragmentType;
	}

	private void changeFragmentForTablets(Fragment newFragment, FragmentsAvailable newFragmentType, boolean withoutAnimation) {
		if (statusFragment != null) {
			statusFragment.closeStatusBar();
		}

		LinearLayout ll = (LinearLayout) findViewById(R.id.fragmentContainer2);

		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		if (newFragmentType.shouldAddItselfToTheRightOf(currentFragment)) {
			ll.setVisibility(View.VISIBLE);
			
			transaction.addToBackStack(newFragmentType.toString());
			transaction.replace(R.id.fragmentContainer2, newFragment);
		} 
		else {
			if (newFragmentType == FragmentsAvailable.PANIC  
					|| newFragmentType == FragmentsAvailable.ABOUT 
					|| newFragmentType == FragmentsAvailable.ABOUT_INSTEAD_OF_CHAT 
					|| newFragmentType == FragmentsAvailable.ABOUT_INSTEAD_OF_SETTINGS
					|| newFragmentType == FragmentsAvailable.SETTINGS 
					|| newFragmentType == FragmentsAvailable.ACCOUNT_SETTINGS) {
				ll.setVisibility(View.GONE);
			} 
			else {
				ll.setVisibility(View.INVISIBLE);
			}
			
			if (!withoutAnimation && !isAnimationDisabled && currentFragment.shouldAnimate()) {
				if (newFragmentType.isRightOf(currentFragment)) {
					transaction.setCustomAnimations(R.anim.slide_in_right_to_left, 
							R.anim.slide_out_right_to_left, R.anim.slide_in_left_to_right, 
							R.anim.slide_out_left_to_right);
				} 
				else {
					transaction.setCustomAnimations(R.anim.slide_in_left_to_right, 
							R.anim.slide_out_left_to_right, R.anim.slide_in_right_to_left, 
							R.anim.slide_out_right_to_left);
				}
			}
			
			try {
				getSupportFragmentManager().popBackStackImmediate(newFragmentType.toString(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
			} 
			catch (java.lang.IllegalStateException e) {
				
			}
			
			transaction.addToBackStack(newFragmentType.toString());
			transaction.replace(R.id.fragmentContainer, newFragment);
		}
		transaction.commitAllowingStateLoss();
		getSupportFragmentManager().executePendingTransactions();
		
		currentFragment = newFragmentType;		
		fragmentsHistory.add(currentFragment);
	}

	public void displayHistoryDetail(String sipUri, LinphoneCallLog log) {
		LinphoneAddress lAddress;
		try {
			lAddress = LinphoneCoreFactory.instance().createLinphoneAddress(sipUri);
		} 
		catch (LinphoneCoreException e) {
			//Log.e("Cannot display history details",e);
			return;
		}
		Uri uri = LinphoneUtils.findUriPictureOfContactAndSetDisplayName(lAddress, getContentResolver());

		String displayName = lAddress.getDisplayName();
		String pictureUri = uri == null ? null : uri.toString();

		String status;
		if (log.getDirection() == CallDirection.Outgoing) {
			status = "Outgoing";
		} 
		else {
			if (log.getStatus() == CallStatus.Missed) {
				status = "Missed";
			} 
			else {
				status = "Incoming";
			}
		}

		String callTime = secondsToDisplayableString(log.getCallDuration());
		String callDate = String.valueOf(log.getTimestamp());

		Fragment fragment2 = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer2);
		if (fragment2 != null && fragment2.isVisible() && currentFragment == FragmentsAvailable.HISTORY_DETAIL) {
			HistoryDetailFragment historyDetailFragment = (HistoryDetailFragment) fragment2;
			historyDetailFragment.changeDisplayedHistory(sipUri, displayName, pictureUri, status, callTime, callDate);
		} 
		else {
			Bundle extras = new Bundle();
			extras.putString("SipUri", sipUri);
			if (displayName != null) {
				extras.putString("DisplayName", displayName);
				extras.putString("PictureUri", pictureUri);
			}
			extras.putString("CallStatus", status);
			extras.putString("CallTime", callTime);
			extras.putString("CallDate", callDate);

			changeCurrentFragment(FragmentsAvailable.HISTORY_DETAIL, extras);
		}
	}

	@SuppressLint("SimpleDateFormat")
	private String secondsToDisplayableString(int secs) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.set(0, 0, 0, 0, 0, secs);
		return dateFormat.format(cal.getTime());
	}

	public void displayContact(Contact contact, boolean chatOnly) {
		Fragment fragment2 = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer2);
		if (fragment2 != null && fragment2.isVisible() && currentFragment == FragmentsAvailable.CONTACT) {
			ContactFragment contactFragment = (ContactFragment) fragment2;
			contactFragment.changeDisplayedContact(contact);
		} 
		else {
			Bundle extras = new Bundle();
			extras.putSerializable("Contact", contact);
			extras.putBoolean("ChatAddressOnly", chatOnly);
			changeCurrentFragment(FragmentsAvailable.CONTACT, extras);
		}
	}

	public void displayContacts(boolean chatOnly) {
		if (chatOnly) {
			preferLinphoneContacts = true;
		}

		Bundle extras = new Bundle();
		extras.putBoolean("ChatAddressOnly", chatOnly);
		changeCurrentFragment(FragmentsAvailable.CONTACTS, extras);
		preferLinphoneContacts = false;
	}

	public void displayContactsForEdition(String sipAddress) {
		Bundle extras = new Bundle();
		extras.putBoolean("EditOnClick", true);
		extras.putString("SipAddress", sipAddress);
		changeCurrentFragment(FragmentsAvailable.CONTACTS, extras);
	}

	public void displayAbout() {
		changeCurrentFragment(FragmentsAvailable.ABOUT, null);
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
			//Log.e("Cannot display chat",e);
			return;
		}
		Uri uri = LinphoneUtils.findUriPictureOfContactAndSetDisplayName(lAddress, getContentResolver());
		String displayName = lAddress.getDisplayName();
		String pictureUri = uri == null ? null : uri.toString();

		if (currentFragment == FragmentsAvailable.CHATLIST || currentFragment == FragmentsAvailable.CHAT) {
			Fragment fragment2 = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer2);
			if (fragment2 != null && fragment2.isVisible() && currentFragment == FragmentsAvailable.CHAT) {
				ChatFragment chatFragment = (ChatFragment) fragment2;
				chatFragment.changeDisplayedChat(sipUri, displayName, pictureUri);
			} 
			else {
				Bundle extras = new Bundle();
				extras.putString("SipUri", sipUri);
				if (lAddress.getDisplayName() != null) {
					extras.putString("DisplayName", displayName);
					extras.putString("PictureUri", pictureUri);
				}
				changeCurrentFragment(FragmentsAvailable.CHAT, extras);
			}
		} 
		else {
			changeCurrentFragment(FragmentsAvailable.CHATLIST, null);
			displayChat(sipUri);
		}
		
		LinphoneService.instance().resetMessageNotifCount();
		LinphoneService.instance().removeMessageNotification();
		displayMissedChats(getChatStorage().getUnreadMessageCount());
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		resetSelection();

		if (id == R.id.history) {
			changeCurrentFragment(FragmentsAvailable.HISTORY, null);
			history.setSelected(true);
			LinphoneManager.getLc().resetMissedCallsCount();
			displayMissedCalls(0);
		} 
		else if (id == R.id.contacts) {
			changeCurrentFragment(FragmentsAvailable.CONTACTS, null);
			contacts.setSelected(true);
		}
		else if (id == R.id.dialer) {
			changeCurrentFragment(FragmentsAvailable.PANIC, null);
			dialer.setSelected(true);
		}
		else if (id == R.id.settings) {
			changeCurrentFragment(FragmentsAvailable.SETTINGS, null);
			settings.setSelected(true);
			Log.v("Click en settings");
		} 
		else if (id == R.id.rings) {
			changeCurrentFragment(FragmentsAvailable.RINGS, null);
			rings.setSelected(true);
			Log.v("Click en rings");
		} 
		else if (id == R.id.about_chat) {
			Bundle b = new Bundle();
			b.putSerializable("About", FragmentsAvailable.ABOUT_INSTEAD_OF_CHAT);
			changeCurrentFragment(FragmentsAvailable.ABOUT_INSTEAD_OF_CHAT, b);
			aboutChat.setSelected(true);
		} 
		else if (id == R.id.about_settings) {
			Bundle b = new Bundle();
			b.putSerializable("About", FragmentsAvailable.ABOUT_INSTEAD_OF_SETTINGS);
			changeCurrentFragment(FragmentsAvailable.ABOUT_INSTEAD_OF_SETTINGS, b);
			aboutSettings.setSelected(true);
		} 
		else if (id == R.id.chat) {
			changeCurrentFragment(FragmentsAvailable.CHATLIST, null);
			chat.setSelected(true);
		}
	}

	private void resetSelection() {
		history.setSelected(false);
		contacts.setSelected(false);
		dialer.setSelected(false);
		settings.setSelected(false);
		rings.setSelected(false);
		chat.setSelected(false);
		aboutChat.setSelected(false);
		aboutSettings.setSelected(false);
	}

	@SuppressWarnings("incomplete-switch")
	public void selectMenu(FragmentsAvailable menuToSelect) {
		currentFragment = menuToSelect;
		resetSelection();

		switch (menuToSelect) {
		case HISTORY:
		case HISTORY_DETAIL:
			history.setSelected(true);
			break;
		case CONTACTS:
		case CONTACT:
		case EDIT_CONTACT:
			contacts.setSelected(true);
			break;
		case PANIC:
			dialer.setSelected(true);
			break;
		case SETTINGS:
		case ACCOUNT_SETTINGS:
			settings.setSelected(true);
			break;
		case ABOUT_INSTEAD_OF_CHAT:
			aboutChat.setSelected(true);
			break;
		case ABOUT_INSTEAD_OF_SETTINGS:
			aboutSettings.setSelected(true);
			break;
		case CHAT:
		case CHATLIST:
			chat.setSelected(true);
			break;
		}
	}
	
	public void updateChatFragment(ChatFragment fragment) {
		messageListenerFragment = fragment;
		// Hack to maintain soft input flags
		getWindow().setSoftInputMode(WindowManager.LayoutParams
				.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams
				.SOFT_INPUT_STATE_HIDDEN);
	}

	public void updateChatListFragment(ChatListFragment fragment) {
		messageListFragment = fragment;
	}

	public void hideMenu(boolean hide) {
		menu.setVisibility(hide ? View.GONE : View.VISIBLE);
		mark.setVisibility(hide ? View.GONE : View.VISIBLE);
	}

	public void updateStatusFragment(StatusFragment fragment) {
		statusFragment = fragment;

		LinphoneCore lc = LinphoneManager.getLcIfManagerNotDestroyedOrNull();
		if (lc != null && lc.getDefaultProxyConfig() != null) {
			statusFragment.registrationStateChanged(LinphoneManager.getLc()
					.getDefaultProxyConfig().getState());
		}
	}

	public void displaySettings() {
		changeCurrentFragment(FragmentsAvailable.SETTINGS, null);
		settings.setSelected(true);
	}

	public void applyConfigChangesIfNeeded() {
		if (nextFragment != FragmentsAvailable.SETTINGS && nextFragment != 
				FragmentsAvailable.ACCOUNT_SETTINGS) {
			updateAnimationsState();
		}
	}

	public void displayAccountSettings(int accountNumber) {
		Bundle bundle = new Bundle();
		bundle.putInt("Account", accountNumber);
		changeCurrentFragment(FragmentsAvailable.ACCOUNT_SETTINGS, bundle);
		settings.setSelected(true);
	}

	public StatusFragment getStatusFragment() {
		return statusFragment;
	}

	public List<String> getChatList() {
		return getChatStorage().getChatList();
	}

	public List<String> getDraftChatList() {
		return getChatStorage().getDrafts();
	}

	public List<ChatMessage> getChatMessages(String correspondent) {
		return getChatStorage().getMessages(correspondent);
	}

	public void removeFromChatList(String sipUri) {
		getChatStorage().removeDiscussion(sipUri);
	}

	public void removeFromDrafts(String sipUri) {
		getChatStorage().deleteDraft(sipUri);
	}

	@Override
	public void onMessageReceived(LinphoneAddress from, 
			LinphoneChatMessage message, int id) {
		ChatFragment chatFragment = ((ChatFragment) messageListenerFragment);
		if (messageListenerFragment != null && messageListenerFragment.isVisible() 
				&& chatFragment.getSipUri().equals(from.asStringUriOnly())) {
			chatFragment.onMessageReceived(id, from, message);
			getChatStorage().markMessageAsRead(id);			
		} 
		else if (LinphoneService.isReady()) {
			displayMissedChats(getChatStorage().getUnreadMessageCount());
			if (messageListFragment != null && messageListFragment.isVisible()) {
				((ChatListFragment) messageListFragment).refresh();
			}
		}
	}

	public void updateMissedChatCount() {
		displayMissedChats(getChatStorage().getUnreadMessageCount());
	}

	public int onMessageSent(String to, String message) {
		getChatStorage().deleteDraft(to);
		int result = getChatStorage().saveTextMessage("", to, message, System.currentTimeMillis());
		
		return result;
	}

	public int onMessageSent(String to, Bitmap image, String imageURL) {
		getChatStorage().deleteDraft(to);
		return getChatStorage().saveImageMessage("", to, image, imageURL, System.currentTimeMillis());
	}

	public void onMessageStateChanged(String to, String message, int newState) {
		getChatStorage().updateMessageStatus(to, message, newState);
	}

	public void onImageMessageStateChanged(String to, int id, int newState) {
		getChatStorage().updateMessageStatus(to, id, newState);
	}

	public void onRegistrationStateChanged(LinphoneProxyConfig proxy, RegistrationState state, String message) {
		LinphoneCore lc = LinphoneManager.getLcIfManagerNotDestroyedOrNull();
		if (statusFragment != null) {
			if (lc != null)
				if(lc.getDefaultProxyConfig() == null)
					statusFragment.registrationStateChanged(proxy.getState());
				else 
					statusFragment.registrationStateChanged(lc.getDefaultProxyConfig().getState());
			else
				statusFragment.registrationStateChanged(RegistrationState.RegistrationNone);
		}
		
		if(state.equals(RegistrationState.RegistrationCleared)){ 
			if(lc != null){
				LinphoneAuthInfo authInfo = lc.findAuthInfo(proxy.getIdentity(), proxy.getRealm(), proxy.getDomain());
				if(authInfo != null)
					lc.removeAuthInfo(authInfo);
			}
		}
	}

	private void displayMissedCalls(final int missedCallsCount) {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (missedCallsCount > 0) {
					missedCalls.setText(missedCallsCount + "");
					missedCalls.setVisibility(View.VISIBLE);
					if (!isAnimationDisabled) {
						missedCalls.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.bounce));
					}
				} else {
					missedCalls.clearAnimation();
					missedCalls.setVisibility(View.GONE);
				}
			}
		});
	}

	private void displayMissedChats(final int missedChatCount) {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (missedChatCount > 0) {
					missedChats.setText(missedChatCount + "");
					if (missedChatCount > 99) {
						missedChats.setTextSize(12);
					} else {
						missedChats.setTextSize(20);
					}
					missedChats.setVisibility(View.VISIBLE);
					if (!isAnimationDisabled) {
						missedChats.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.bounce));
					}
				} else {
					missedChats.clearAnimation();
					missedChats.setVisibility(View.GONE);
				}
			}
		});
	}

	@Override
	public void onCallStateChanged(LinphoneCall call, State state, String message) {
		if (state == State.IncomingReceived) {
			startActivity(new Intent(this, IncomingCallActivity.class));
		} else if (state == State.OutgoingInit) {
			if (call.getCurrentParamsCopy().getVideoEnabled()) {
				startVideoActivity(call);
			} else {
				startIncallActivity(call);
			}
		} else if (state == State.CallEnd || state == State.Error || 
				state == State.CallReleased) {
			// Convert LinphoneCore message for internalization
			if (message != null && message.equals("Call declined.")) { 
				displayCustomToast(getString(R.string.error_call_declined), 
						Toast.LENGTH_LONG);
			} 
			else if (message != null && message.equals("Not Found")) {
				displayCustomToast(getString(R.string.error_user_not_found), 
						Toast.LENGTH_LONG);
			} 
			else if (message != null && message.equals("Unsupported media type")) {
				displayCustomToast(getString(R.string.error_incompatible_media), 
						Toast.LENGTH_LONG);
			}
			resetClassicMenuLayoutAndGoBackToCallIfStillRunning();
		}

		int missedCalls = LinphoneManager.getLc().getMissedCallsCount();
		displayMissedCalls(missedCalls);
	}

	public void displayCustomToast(final String message, final int duration) {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(R.layout.toast, (ViewGroup) 
						findViewById(R.id.toastRoot));

				TextView toastText = (TextView) layout.findViewById(R.id.toastMessage);
				toastText.setText(message);

				final Toast toast = new Toast(getApplicationContext());
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.setDuration(duration);
				toast.setView(layout);
				toast.show();
			}
		});
	}

	@Override
	public void setAddresGoToDialerAndCall(String number, String name, Uri photo) {
//		Bundle extras = new Bundle();
//		extras.putString("SipUri", number);
//		extras.putString("DisplayName", name);
//		extras.putString("Photo", photo == null ? null : photo.toString());
//		changeCurrentFragment(FragmentsAvailable.DIALER, extras);
		
		AddressType address = new AddressText(this, null);
		address.setDisplayedName(name);
		address.setText(number);
		LinphoneManager.getInstance().newOutgoingCall(address);
	}
	
	/* TODO test
	public void setAddressAndGoToDialer(String number) {
		Bundle extras = new Bundle();
		extras.putString("SipUri", number);
		changeCurrentFragment(FragmentsAvailable.DIALER, extras);
	}*/
	
	@Override	
	 public void goToDialer() {	 
		//TODO test changeCurrentFragment(FragmentsAvailable.DIALER, null);
	}
	public void startVideoActivity(LinphoneCall currentCall) {
		Intent intent = new Intent(this, InCallActivity.class);
		intent.putExtra("VideoEnabled", true);
		startOrientationSensor();
		startActivityForResult(intent, CALL_ACTIVITY);
	}

	public void startIncallActivity(LinphoneCall currentCall) {
		Intent intent = new Intent(this, InCallActivity.class);
		intent.putExtra("VideoEnabled", false);
		startOrientationSensor();
		startActivityForResult(intent, CALL_ACTIVITY);
	}

	/**
	 * Register a sensor to track phoneOrientation changes
	 */
	private synchronized void startOrientationSensor() {
		if (mOrientationHelper == null) {
			mOrientationHelper = new LocalOrientationEventListener(this);
		}
		mOrientationHelper.enable();
	}

	private int mAlwaysChangingPhoneAngle = -1;
	private AcceptNewFriendDialog acceptNewFriendDialog;

	private class LocalOrientationEventListener extends OrientationEventListener {
		public LocalOrientationEventListener(Context context) {
			super(context);
		}

		@Override
		public void onOrientationChanged(final int o) {
			if (o == OrientationEventListener.ORIENTATION_UNKNOWN) {
				return;
			}

			int degrees = 270;
			if (o < 45 || o > 315)
				degrees = 0;
			else if (o < 135)
				degrees = 90;
			else if (o < 225)
				degrees = 180;

			if (mAlwaysChangingPhoneAngle == degrees) {
				return;
			}
			mAlwaysChangingPhoneAngle = degrees;

			//Log.d("Phone orientation changed to ", degrees);
			int rotation = (360 - degrees) % 360;
			LinphoneCore lc = LinphoneManager.getLcIfManagerNotDestroyedOrNull();
			if (lc != null) {
				lc.setDeviceRotation(rotation);
				LinphoneCall currentCall = lc.getCurrentCall();
				if (currentCall != null && currentCall.cameraEnabled() && currentCall.getCurrentParamsCopy().getVideoEnabled()) {
					lc.updateCall(currentCall, null);
				}
			}
		}
	}

	public void showPreferenceErrorDialog(String message) {

	}

	public List<Contact> getAllContacts() {
		return contactList;
	}

	public List<Contact> getSIPContacts() {
		return sipContactList;
	}

	public Cursor getAllContactsCursor() {
		return contactCursor;
	}

	public Cursor getSIPContactsCursor() {
		return sipContactCursor;
	}
	
	public Cursor getAllRingsCursor(){
		
		return ringsCursor;		
	}
	
	public List<Ring> getAllRings() {
		return allRings;
	}

	public void setLinphoneContactsPrefered(boolean isPrefered) {
		preferLinphoneContacts = isPrefered;
	}

	public boolean isLinphoneContactsPrefered() {
		return preferLinphoneContacts;
	}

	public void onNewSubscriptionRequestReceived(LinphoneFriend friend,
			String sipUri) {
		if (isContactPresenceDisabled) {
			return;
		}

		sipUri = sipUri.replace("<", "").replace(">", "");
		if (LinphonePreferences.instance().shouldAutomaticallyAcceptFriendsRequests()) {
			Contact contact = findContactWithSipAddress(sipUri);
			if (contact != null) {
				friend.enableSubscribes(true);
				try {
					LinphoneManager.getLc().addFriend(friend);
					contact.setFriend(friend);
				} catch (LinphoneCoreException e) {
					e.printStackTrace();
				}
			}
		} else {
			Contact contact = findContactWithSipAddress(sipUri);
			if (contact != null) {
				FragmentManager fm = getSupportFragmentManager();
				acceptNewFriendDialog = new AcceptNewFriendDialog(contact, sipUri);
				acceptNewFriendDialog.show(fm, "New Friend Request Dialog");
			}
		}
	}

	private Contact findContactWithSipAddress(String sipUri) {
		if (!sipUri.startsWith("sip:")) {
			sipUri = "sip:" + sipUri;
		}

		for (Contact contact : sipContactList) {
			for (String addr : contact.getNumerosOrAddresses()) {
				if (addr.equals(sipUri)) {
					return contact;
				}
			}
		}
		return null;
	}

	public void onNotifyPresenceReceived(LinphoneFriend friend) {
		if (!isContactPresenceDisabled && currentFragment == FragmentsAvailable.CONTACTS && friendStatusListenerFragment != null) {
			((ContactsFragment) friendStatusListenerFragment).invalidate();
		}
	}

	public boolean newFriend(Contact contact, String sipUri) {
		LinphoneFriend friend = LinphoneCoreFactory.instance().createLinphoneFriend(sipUri);
		friend.enableSubscribes(true);
		friend.setIncSubscribePolicy(LinphoneFriend.SubscribePolicy.SPAccept);
		try {
			LinphoneManager.getLc().addFriend(friend);
			contact.setFriend(friend);
			return true;
		} catch (LinphoneCoreException e) {
			e.printStackTrace();
		}
		return false;
	}

	private void acceptNewFriend(Contact contact, String sipUri, boolean accepted) {
		acceptNewFriendDialog.dismissAllowingStateLoss();
		if (accepted) {
			newFriend(contact, sipUri);
		}
	}

	public boolean removeFriend(Contact contact, String sipUri) {
		LinphoneFriend friend = LinphoneManager.getLc().findFriendByAddress(sipUri);
		if (friend != null) {
			friend.enableSubscribes(false);
			LinphoneManager.getLc().removeFriend(friend);
			contact.setFriend(null);
			return true;
		}
		return false;
	}

	private void searchFriendAndAddToContact(Contact contact) {
		if (contact == null || contact.getNumerosOrAddresses() == null) {
			return;
		}

		for (String sipUri : contact.getNumerosOrAddresses()) {
			if (LinphoneUtils.isSipAddress(sipUri)) {
				LinphoneFriend friend = LinphoneManager.getLc().findFriendByAddress(sipUri);
				if (friend != null) {
					friend.enableSubscribes(true);
					friend.setIncSubscribePolicy(LinphoneFriend.SubscribePolicy.SPAccept);
					contact.setFriend(friend);
					break;
				}
			}
		}
	}
	
	public void removeContactFromLists(Contact contact) {
		for (Contact c : contactList) {
			if (c != null && c.getID().equals(contact.getID())) {
				contactList.remove(c);
				contactCursor = Compatibility.getContactsCursor(getContentResolver());
				break;
			}
		}
		
		for (Contact c : sipContactList) {
			if (c != null && c.getID().equals(contact.getID())) {
				sipContactList.remove(c);
				sipContactCursor = Compatibility.getSIPContactsCursor(getContentResolver());
				break;
			}
		}
	}
	
	/*public synchronized void prepareRingsInBackground(){
		if(ringsCursor != null){
			ringsCursor.close();
		}
		RingDAO ringDao = new RingDAO(LinphoneService.instance()
				.getApplicationContext());
		ringDao.open();
		ringsCursor = ringDao.getRigsCursor();
		allRings = new ArrayList<Ring>();		
		Thread ringHandler = new Thread(new Runnable() {
			@Override
			public void run() {
				ringsCursor.moveToFirst();
				for(int i = 0; i < ringsCursor.getCount(); i++){
					Ring r = new Ring(ringsCursor.getString(0),ringsCursor
							.getString(1), ringsCursor.getLong(2));					
					allRings.add(r);
					ringsCursor.moveToNext();
				}
			}			
		});
		ringHandler.start();
		ringDao.close();
	}*/

	public synchronized void prepareContactsInBackground() {
		if (contactCursor != null) {
			contactCursor.close();
		}
		if (sipContactCursor != null) {
			sipContactCursor.close();
		}

		contactCursor = Compatibility.getContactsCursor(getContentResolver());
		sipContactCursor = Compatibility.getSIPContactsCursor(getContentResolver());

		Thread sipContactsHandler = new Thread(new Runnable() {
			@Override
			public void run() {
				if(sipContactCursor != null) {
					for (int i = 0; i < sipContactCursor.getCount(); i++) {
						Contact contact = Compatibility.getContact(getContentResolver(), sipContactCursor, i);
						if (contact == null)
							continue;
						
						contact.refresh(getContentResolver());
						if (!isContactPresenceDisabled) {
							searchFriendAndAddToContact(contact);
						}
						sipContactList.add(contact);
					}
				}
				if(contactCursor != null) {
					for (int i = 0; i < contactCursor.getCount(); i++) {
						Contact contact = Compatibility.getContact(getContentResolver(), contactCursor, i);
						if (contact == null)
							continue;
						
						for (Contact c : sipContactList) {
							if (c != null && c.getID().equals(contact.getID())) {
								contact = c;
								break;
							}
						}
						contactList.add(contact);
					}
				}
			}
		});

		contactList = new ArrayList<Contact>();
		sipContactList = new ArrayList<Contact>();
		
		sipContactsHandler.start();
	}

	/*TODO test
	 * private void initInCallMenuLayout(boolean callTransfer) {
		selectMenu(FragmentsAvailable.DIALER);
		if (dialerFragment != null) {
			((DialerFragment) dialerFragment).resetLayout(callTransfer);
		}
	}*/

	public void resetClassicMenuLayoutAndGoBackToCallIfStillRunning() {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				/* TODO test
				if (dialerFragment != null) {
					((DialerFragment) dialerFragment).resetLayout(false);
				}
				*/
				if (LinphoneManager.isInstanciated() && LinphoneManager.getLc().getCallsNb() > 0) {
					LinphoneCall call = LinphoneManager.getLc().getCalls()[0];
					if (call.getState() == LinphoneCall.State.IncomingReceived) {
						startActivity(new Intent(MainActivity.this, IncomingCallActivity.class));
					} else if (call.getCurrentParamsCopy().getVideoEnabled()) {
						startVideoActivity(call);
					} else {
						startIncallActivity(call);
					}
				}
			}
		});
	}

	public FragmentsAvailable getCurrentFragment() {
		return currentFragment;
	}

	public ChatStorage getChatStorage() {
		return ChatStorage.getInstance();
	}
	
	public void addRing()
	{
		Log.v("=== En addRing de MainActivity");
		Bundle extras = new Bundle();
		extras.putSerializable("NewRing", true);
		changeCurrentFragment(FragmentsAvailable.EDIT_RING, extras);	
	}
	
	public void addContact(String displayName, String sipUri)
	{
		if (getResources().getBoolean(R.bool.use_android_native_contact_edit_interface)) {
			Intent intent = Compatibility.prepareAddContactIntent(displayName, sipUri);
			startActivity(intent);
		} else {
			Bundle extras = new Bundle();
			extras.putSerializable("NewSipAdress", sipUri);
			changeCurrentFragment(FragmentsAvailable.EDIT_CONTACT, extras);
		}
	}
	
	public void addRing(String displayName, String sipUri)
	{
		if (getResources().getBoolean(R.bool.use_android_native_contact_edit_interface)) {
			Intent intent = Compatibility.prepareAddContactIntent(displayName, sipUri);
			startActivity(intent);
		} else {
			Bundle extras = new Bundle();
			extras.putSerializable("NewSipAdress", sipUri);
			changeCurrentFragment(FragmentsAvailable.EDIT_CONTACT, extras);
		}
	}
	
	public void editRing(Ring ring){
		Bundle extras = new Bundle();
		extras.putSerializable("Ring", ring);
		changeCurrentFragment(FragmentsAvailable.EDIT_RING, extras);		
	}
	
	public void editContact(Contact contact){
		if (getResources().getBoolean(R.bool.use_android_native_contact_edit_interface)) {
			Intent intent = Compatibility.prepareEditContactIntent(Integer.parseInt(contact.getID()));
			startActivity(intent);
		} else {
			Bundle extras = new Bundle();
			extras.putSerializable("Contact", contact);
			changeCurrentFragment(FragmentsAvailable.EDIT_CONTACT, extras);
		}
	}
	
	public void editContact(Contact contact, String sipAddress)
	{
		if (getResources().getBoolean(R.bool.use_android_native_contact_edit_interface)) {
			Intent intent = Compatibility.prepareEditContactIntentWithSipAddress(Integer.parseInt(contact.getID()), sipAddress);
			startActivity(intent);
		} else {
			Bundle extras = new Bundle();
			extras.putSerializable("Contact", contact);
			extras.putSerializable("NewSipAdress", sipAddress);
			changeCurrentFragment(FragmentsAvailable.EDIT_CONTACT, extras);
		}
	}

	public void exit() {
		finish();
		stopService(new Intent(ACTION_MAIN).setClass(this, LinphoneService.class));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_FIRST_USER && requestCode == SETTINGS_ACTIVITY) {
			if (data.getExtras().getBoolean("Exit", false)) {
				exit();
			} else {
				FragmentsAvailable newFragment = (FragmentsAvailable) data.getExtras().getSerializable("FragmentToDisplay");
				changeCurrentFragment(newFragment, null, true);
				selectMenu(newFragment);
			}
		} 
		/*TODO test
		 * else if (resultCode == Activity.RESULT_FIRST_USER && requestCode == CALL_ACTIVITY) {
			getIntent().putExtra("PreviousActivity", CALL_ACTIVITY);
			boolean callTransfer = data == null ? false : data.getBooleanExtra("Transfer", false);
			if (LinphoneManager.getLc().getCallsNb() > 0) {
				initInCallMenuLayout(callTransfer);
			} else {
				resetClassicMenuLayoutAndGoBackToCallIfStillRunning();
			}
		}*/ 
		else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}
	
	@Override
	protected void onPause() {
		getIntent().putExtra("PreviousActivity", 0);
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (!LinphoneService.isReady())  {
			startService(new Intent(ACTION_MAIN).setClass(this, LinphoneService.class));
		}

		// Remove to avoid duplication of the listeners
		LinphoneManager.removeListener(this);
		LinphoneManager.addListener(this);

		prepareContactsInBackground();

		updateMissedChatCount();
		
		displayMissedCalls(LinphoneManager.getLc().getMissedCallsCount());

		LinphoneManager.getInstance().changeStatusToOnline();

		if(getIntent().getIntExtra("PreviousActivity", 0) != CALL_ACTIVITY){
			if (LinphoneManager.getLc().getCalls().length > 0) {
				LinphoneCall call = LinphoneManager.getLc().getCalls()[0];
				LinphoneCall.State callState = call.getState();
				if (callState == State.IncomingReceived) {
					startActivity(new Intent(this, IncomingCallActivity.class));
				} else {
					
						if (call.getCurrentParamsCopy().getVideoEnabled()) {
							startVideoActivity(call);
						} else {
							startIncallActivity(call);
						}
					}
				}
		}
	}

	@Override
	protected void onDestroy() {
		LinphoneManager.removeListener(this);

		if (mOrientationHelper != null) {
			mOrientationHelper.disable();
			mOrientationHelper = null;
		}

		instance = null;
		super.onDestroy();

		unbindDrawables(findViewById(R.id.topLayout));
		System.gc();
	}

=======
			
		panicFragment = new PanicButtonFragment();
		panicFragment.setArguments(data);
		
		findViewById(R.id.menu).setVisibility(View.VISIBLE);
		findViewById(R.id.activityContainer2).setVisibility(View.VISIBLE);				
		
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		panicFragment = new PanicButtonFragment();		
		panicFragment.setArguments(data);
		fragmentTransaction.replace(R.id.activityContainer, panicFragment);
		fragmentTransaction.commit();
		initButtons();
		
		instance = this;
		fragmentsHistory = new ArrayList <FragmentsAvailable>();
		currentFragment = nextFragment = FragmentsAvailable.PANIC;
		fragmentsHistory.add(currentFragment);	
		
		
		
		LinphonePreferences.instance().setMediaEncryption(MediaEncryption.SRTP);
	}


	private void initButtons() {
		menu = (LinearLayout) findViewById(R.id.menu);
		mark = (LinearLayout) findViewById(R.id.mark);

		history = (RelativeLayout) findViewById(R.id.history);
		history.setOnClickListener(this);
		contacts = (RelativeLayout) findViewById(R.id.contacts);
		contacts.setOnClickListener(this);
		dialer = (ImageView) findViewById(R.id.dialer);
		dialer.setOnClickListener(this);
		settings = (RelativeLayout) findViewById(R.id.settings);
		settings.setOnClickListener(this);
		rings = (RelativeLayout) findViewById(R.id.rings);
		rings.setOnClickListener(this);
		chat = (RelativeLayout) findViewById(R.id.chat);
		chat.setOnClickListener(this);
		about = (RelativeLayout) findViewById(R.id.about);
		about.setOnClickListener(this);	
		missedCalls = (TextView) findViewById(R.id.missedCalls);
		missedChats = (TextView) findViewById(R.id.missedChats);
	}

	private boolean isTablet() {
		return getResources().getBoolean(R.bool.isTablet);
	}

	public void hideStatusBar() {
		if (isTablet()) {
			return;
		}

		//findViewById(R.id.status).setVisibility(View.GONE);
		findViewById(R.id.activityContainer).setPadding(0, 0, 0, 0);
	}

	/*public void showStatusBar() {
		if (isTablet()) {
			return;
		}

		if (statusFragment != null && !statusFragment.isVisible()) {
			// Hack to ensure statusFragment is visible after coming back to
			// dialer from chat
			statusFragment.getView().setVisibility(View.VISIBLE);
		}
		findViewById(R.id.status).setVisibility(View.VISIBLE);
		findViewById(R.id.activityContainer).setPadding(0, LinphoneUtils.pixelsToDpi(getResources(), 40), 0, 0);
	}*/

	public void changeCurrentFragment(FragmentsAvailable newFragmentType, Bundle extras) {
		changeCurrentFragment(newFragmentType, extras, false);
	}

	@SuppressWarnings("incomplete-switch")
	public void changeCurrentFragment(FragmentsAvailable newFragmentType, Bundle extras, boolean withoutAnimation) {
		Log.v("=== Current Fragment: " + currentFragment.name()); 
		if (newFragmentType == currentFragment && newFragmentType != FragmentsAvailable.CHAT) {
			return;
		}
		nextFragment = newFragmentType;		
		Fragment newFragment = null;

		switch (newFragmentType) {
		case START:
			newFragment = new StartFragment();
			break;
		case LOGIN:
			newFragment = new LoginFragment();
			break;
		case HISTORY:
			if (getResources().getBoolean(R.bool.use_simple_history)) {
				newFragment = new HistorySimpleFragment();
			} 
			else {
				newFragment = new HistoryFragment();
			}
			break;
		case HISTORY_DETAIL:
			newFragment = new HistoryDetailFragment();
			break;
		case CONTACTS:
			if (getResources().getBoolean(R.bool.use_android_native_contact_edit_interface)) {
				Intent i = new Intent();
				i.setComponent(new ComponentName("com.android.contacts", "com.android.contacts.DialtactsContactsEntryActivity"));
				i.setAction("android.intent.action.MAIN");
				i.addCategory("android.intent.category.LAUNCHER");
				i.addCategory("android.intent.category.DEFAULT");
				startActivity(i);
			} 
			else {
				newFragment = new ContactsFragment();
				friendStatusListenerFragment = newFragment;
			}
			break;
		case CONTACT:
			newFragment = new ContactFragment();
			break;
		case EDIT_CONTACT:			
			newFragment = new EditContactFragment();
			break;
		case PANIC:
			newFragment = new PanicButtonFragment();
			if (extras == null) {
				newFragment.setInitialSavedState(panicSavedState);
			}
			break;	
		case SETTINGS:
			newFragment = new SettingsFragment();
			break;
		case RINGS:
			newFragment = new RingsFragment();
			break;
		case EDIT_RING:
			newFragment = new EditRingFragment();			
			break;
		case ACCOUNT_SETTINGS:
			newFragment = new AccountPreferencesFragment();
			break;
		case ABOUT:			
			newFragment = new AboutFragment();
			break;
		case CHAT:
			newFragment = new ChatFragment();
			messageListenerFragment = newFragment;
			break;
		case CHATLIST:
			newFragment = new ChatListFragment();
			messageListFragment = new Fragment();
			break;
		}

		if (newFragment != null) {
			newFragment.setArguments(extras);
			if (isTablet()) {
				changeFragmentForTablets(newFragment, newFragmentType, withoutAnimation);
			} 
			else {
				changeFragment(newFragment, newFragmentType, withoutAnimation);
			}
		}
	}

	private void updateAnimationsState() {
		isAnimationDisabled = getResources().getBoolean(R.bool.disable_animations) || !LinphonePreferences.instance().areAnimationsEnabled();
		isContactPresenceDisabled = !getResources().getBoolean(R.bool.enable_linphone_friends);
	}

	public boolean isAnimationDisabled() {
		return isAnimationDisabled;
	}

	public boolean isContactPresenceDisabled() {
		return isContactPresenceDisabled;
	}

	private void changeFragment(Fragment newFragment, FragmentsAvailable newFragmentType, boolean withoutAnimation) {
		if (statusFragment != null) {
			statusFragment.closeStatusBar();
		}

		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				
		
		if (!withoutAnimation && !isAnimationDisabled && currentFragment.shouldAnimate()) {
			if (newFragmentType.isRightOf(currentFragment)) {
				transaction.setCustomAnimations(R.anim.slide_in_right_to_left,
						R.anim.slide_out_right_to_left,
						R.anim.slide_in_left_to_right,
						R.anim.slide_out_left_to_right);
			} 
			else {
				transaction.setCustomAnimations(R.anim.slide_in_left_to_right,
						R.anim.slide_out_left_to_right,
						R.anim.slide_in_right_to_left,
						R.anim.slide_out_right_to_left);
			}
		}
		try {
			getSupportFragmentManager().popBackStackImmediate(newFragmentType.toString(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
		} 
		catch (java.lang.IllegalStateException e) {
			e.printStackTrace();
		}

		transaction.addToBackStack(newFragmentType.toString());
		transaction.replace(R.id.activityContainer, newFragment, newFragmentType.toString());
		Log.v("=== Cambiando a fragment " + newFragmentType.toString());
		transaction.commitAllowingStateLoss();
		getSupportFragmentManager().executePendingTransactions();

		currentFragment = newFragmentType;
	}

	private void changeFragmentForTablets(Fragment newFragment, FragmentsAvailable newFragmentType, boolean withoutAnimation) {
		if (statusFragment != null) {
			statusFragment.closeStatusBar();
		}

		LinearLayout ll = (LinearLayout) findViewById(R.id.activityContainer2);
		//ll.setVisibility(View.VISIBLE);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		if (newFragmentType.shouldAddItselfToTheRightOf(currentFragment)) {
			ll.setVisibility(View.VISIBLE);

			transaction.addToBackStack(newFragmentType.toString());
			transaction.replace(R.id.activityContainer2, newFragment);
		} 
		else {
			if (newFragmentType == FragmentsAvailable.PANIC  
					|| newFragmentType == FragmentsAvailable.ABOUT 					
					|| newFragmentType == FragmentsAvailable.SETTINGS 
					|| newFragmentType == FragmentsAvailable.ACCOUNT_SETTINGS) {
				ll.setVisibility(View.GONE);
			} 
			else {
				ll.setVisibility(View.INVISIBLE);
			}
			
			if(currentFragment != null){
				if (!withoutAnimation && !isAnimationDisabled && currentFragment.shouldAnimate()) {
					if (newFragmentType.isRightOf(currentFragment)) {
						transaction.setCustomAnimations(R.anim.slide_in_right_to_left, 
								R.anim.slide_out_right_to_left, R.anim.slide_in_left_to_right, 
								R.anim.slide_out_left_to_right);
					} 
					else {
						transaction.setCustomAnimations(R.anim.slide_in_left_to_right, 
								R.anim.slide_out_left_to_right, R.anim.slide_in_right_to_left, 
								R.anim.slide_out_right_to_left);
					}
				}
			}
		
			try {
				getSupportFragmentManager().popBackStackImmediate(newFragmentType.toString(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
			} 
			catch (java.lang.IllegalStateException e) {

			}

			transaction.addToBackStack(newFragmentType.toString());
			transaction.replace(R.id.activityContainer, newFragment);
		}
		transaction.commitAllowingStateLoss();
		getSupportFragmentManager().executePendingTransactions();

		currentFragment = newFragmentType;		
		fragmentsHistory.add(currentFragment);
	}

	public void displayHistoryDetail(String sipUri, LinphoneCallLog log) {
		LinphoneAddress lAddress;
		try {
			lAddress = LinphoneCoreFactory.instance().createLinphoneAddress(sipUri);
		} 
		catch (LinphoneCoreException e) {
			//Log.e("Cannot display history details",e);
			return;
		}
		Uri uri = LinphoneUtils.findUriPictureOfContactAndSetDisplayName(lAddress, getContentResolver());

		String displayName = lAddress.getDisplayName();
		String pictureUri = uri == null ? null : uri.toString();

		String status;
		if (log.getDirection() == CallDirection.Outgoing) {
			status = getString(R.string.history_outgoing);
		} 
		else {
			if (log.getStatus() == CallStatus.Missed) {
				status = getString(R.string.history_missed);
			} 
			else {
				status = getString(R.string.history_incoming);
			}
		}

		String callTime = secondsToDisplayableString(log.getCallDuration());
		String callDate = String.valueOf(log.getTimestamp());

		Fragment fragment2 = getSupportFragmentManager().findFragmentById(R.id.activityContainer2);
		if (fragment2 != null && fragment2.isVisible() && currentFragment == FragmentsAvailable.HISTORY_DETAIL) {
			HistoryDetailFragment historyDetailFragment = (HistoryDetailFragment) fragment2;
			historyDetailFragment.changeDisplayedHistory(sipUri, displayName, pictureUri, status, callTime, callDate);
		} 
		else {
			Bundle extras = new Bundle();
			extras.putString("SipUri", sipUri);
			if (displayName != null) {
				extras.putString("DisplayName", displayName);
				extras.putString("PictureUri", pictureUri);
			}
			extras.putString("CallStatus", status);
			extras.putString("CallTime", callTime);
			extras.putString("CallDate", callDate);

			changeCurrentFragment(FragmentsAvailable.HISTORY_DETAIL, extras);
		}
	}

	@SuppressLint("SimpleDateFormat")
	private String secondsToDisplayableString(int secs) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.set(0, 0, 0, 0, 0, secs);
		return dateFormat.format(cal.getTime());
	}

	public void displayContact(Contact contact, boolean chatOnly) {
		Fragment fragment2 = getSupportFragmentManager().findFragmentById(R.id.activityContainer2);
		if (fragment2 != null && fragment2.isVisible() && currentFragment == FragmentsAvailable.CONTACT) {
			ContactFragment contactFragment = (ContactFragment) fragment2;
			contactFragment.changeDisplayedContact(contact);
		} 
		else {
			Bundle extras = new Bundle();
			extras.putSerializable("Contact", contact);
			extras.putBoolean("ChatAddressOnly", chatOnly);
			changeCurrentFragment(FragmentsAvailable.CONTACT, extras);
		}
	}

	public void displayContacts(boolean chatOnly) {
		if (chatOnly) {
			preferLinphoneContacts = true;
		}

		Bundle extras = new Bundle();
		extras.putBoolean("ChatAddressOnly", chatOnly);
		changeCurrentFragment(FragmentsAvailable.CONTACTS, extras);
		preferLinphoneContacts = false;
	}

	public void displayContactsForEdition(String sipAddress) {
		Bundle extras = new Bundle();
		extras.putBoolean("EditOnClick", true);
		extras.putString("SipAddress", sipAddress);
		changeCurrentFragment(FragmentsAvailable.CONTACTS, extras);
	}

	public void displayAbout() {
		changeCurrentFragment(FragmentsAvailable.ABOUT, null);
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
			//Log.e("Cannot display chat",e);
			return;
		}
		Uri uri = LinphoneUtils.findUriPictureOfContactAndSetDisplayName(lAddress, getContentResolver());
		String displayName = lAddress.getDisplayName();
		String pictureUri = uri == null ? null : uri.toString();

		if (currentFragment == FragmentsAvailable.CHATLIST || currentFragment == FragmentsAvailable.CHAT) {
			Fragment fragment2 = getSupportFragmentManager().findFragmentById(R.id.activityContainer2);
			if (fragment2 != null && fragment2.isVisible() && currentFragment == FragmentsAvailable.CHAT) {
				ChatFragment chatFragment = (ChatFragment) fragment2;
				chatFragment.changeDisplayedChat(sipUri, displayName, pictureUri);
			} 
			else {
				Bundle extras = new Bundle();
				extras.putString("SipUri", sipUri);
				if (lAddress.getDisplayName() != null) {
					extras.putString("DisplayName", displayName);
					extras.putString("PictureUri", pictureUri);
				}
				changeCurrentFragment(FragmentsAvailable.CHAT, extras);
			}
		} 
		else {
			changeCurrentFragment(FragmentsAvailable.CHATLIST, null);
			displayChat(sipUri);
		}

		LinphoneService.instance().resetMessageNotifCount();
		LinphoneService.instance().removeMessageNotification();
		displayMissedChats(getChatStorage().getUnreadMessageCount());
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		resetSelection();

		if (id == R.id.history) {
			changeCurrentFragment(FragmentsAvailable.HISTORY, null);
			history.setSelected(true);
			LinphoneManager.getLc().resetMissedCallsCount();
			displayMissedCalls(0);
		} 
		else if (id == R.id.contacts) {
			changeCurrentFragment(FragmentsAvailable.CONTACTS, null);
			contacts.setSelected(true);
		}
		else if (id == R.id.dialer) {
			changeCurrentFragment(FragmentsAvailable.PANIC, null);
			dialer.setSelected(true);
		}
		else if (id == R.id.settings) {
			changeCurrentFragment(FragmentsAvailable.SETTINGS, null);
			settings.setSelected(true);
			Log.v("Click en settings");
		} 
		else if (id == R.id.rings) {
			changeCurrentFragment(FragmentsAvailable.RINGS, null);
			rings.setSelected(true);
			Log.v("Click en rings");
		} 
		else if (id == R.id.about) {
			Bundle b = new Bundle();
			b.putSerializable("About", FragmentsAvailable.ABOUT);//_INSTEAD_OF_CHAT);
			changeCurrentFragment(FragmentsAvailable.ABOUT, null);//_INSTEAD_OF_CHAT, b);
			about.setSelected(true);
		} 		
		else if (id == R.id.chat) {
			changeCurrentFragment(FragmentsAvailable.CHATLIST, null);
			chat.setSelected(true);
		}
	}

	private void resetSelection() {
		history.setSelected(false);
		contacts.setSelected(false);
		dialer.setSelected(false);
		settings.setSelected(false);
		rings.setSelected(false);
		chat.setSelected(false);
		about.setSelected(false);		
	}

	@SuppressWarnings("incomplete-switch")
	public void selectMenu(FragmentsAvailable menuToSelect) {
		currentFragment = menuToSelect;
		resetSelection();

		switch (menuToSelect) {
		case HISTORY:
		case HISTORY_DETAIL:
			history.setSelected(true);
			break;
		case CONTACTS:
		case CONTACT:
		case EDIT_CONTACT:
			contacts.setSelected(true);
			break;
		case PANIC:
			dialer.setSelected(true);
			break;
		case SETTINGS:
		case ACCOUNT_SETTINGS:
			settings.setSelected(true);
			break;
		case ABOUT:
			about.setSelected(true);
			break;		
		case CHAT:
		case CHATLIST:
			chat.setSelected(true);
			break;
		}
	}

	public void updateChatFragment(ChatFragment fragment) {
		messageListenerFragment = fragment;
		// Hack to maintain soft input flags
		getWindow().setSoftInputMode(WindowManager.LayoutParams
				.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams
				.SOFT_INPUT_STATE_HIDDEN);
	}

	public void updateChatListFragment(ChatListFragment fragment) {
		messageListFragment = fragment;
	}

	public void hideMenu(boolean hide) {
		menu.setVisibility(hide ? View.GONE : View.VISIBLE);
		mark.setVisibility(hide ? View.GONE : View.VISIBLE);
	}

	public void updateStatusFragment(StatusFragment fragment) {
		statusFragment = fragment;

		LinphoneCore lc = LinphoneManager.getLcIfManagerNotDestroyedOrNull();
		if (lc != null && lc.getDefaultProxyConfig() != null) {
			statusFragment.registrationStateChanged(LinphoneManager.getLc()
					.getDefaultProxyConfig().getState());
		}
	}

	public void displaySettings() {
		changeCurrentFragment(FragmentsAvailable.SETTINGS, null);
		settings.setSelected(true);
	}

	public void applyConfigChangesIfNeeded() {
		if (nextFragment != FragmentsAvailable.SETTINGS && nextFragment != 
				FragmentsAvailable.ACCOUNT_SETTINGS) {
			updateAnimationsState();
		}
	}

	public void displayAccountSettings(int accountNumber) {
		Bundle bundle = new Bundle();
		bundle.putInt("Account", accountNumber);
		changeCurrentFragment(FragmentsAvailable.ACCOUNT_SETTINGS, bundle);
		settings.setSelected(true);
	}

	public StatusFragment getStatusFragment() {
		return statusFragment;
	}

	public List<String> getChatList() {
		return getChatStorage().getChatList();
	}

	public List<String> getDraftChatList() {
		return getChatStorage().getDrafts();
	}

	public List<ChatMessage> getChatMessages(String correspondent) {
		return getChatStorage().getMessages(correspondent);
	}

	public void removeFromChatList(String sipUri) {
		getChatStorage().removeDiscussion(sipUri);
	}

	public void removeFromDrafts(String sipUri) {
		getChatStorage().deleteDraft(sipUri);
	}

	@Override
	public void onMessageReceived(LinphoneAddress from, 
			LinphoneChatMessage message, int id) {
		ChatFragment chatFragment = ((ChatFragment) messageListenerFragment);
		if (messageListenerFragment != null && messageListenerFragment.isVisible() 
				&& chatFragment.getSipUri().equals(from.asStringUriOnly())) {
			chatFragment.onMessageReceived(id, from, message);
			getChatStorage().markMessageAsRead(id);			
		} 
		else if (LinphoneService.isReady()) {
			displayMissedChats(getChatStorage().getUnreadMessageCount());
			if (messageListFragment != null && messageListFragment.isVisible()) {
				((ChatListFragment) messageListFragment).refresh();
			}
		}
	}

	public void updateMissedChatCount() {
		displayMissedChats(getChatStorage().getUnreadMessageCount());
	}

	public int onMessageSent(String to, String message) {
		getChatStorage().deleteDraft(to);
		int result = getChatStorage().saveTextMessage("", to, message, System.currentTimeMillis());

		return result;
	}

	public int onMessageSent(String to, Bitmap image, String imageURL) {
		getChatStorage().deleteDraft(to);
		return getChatStorage().saveImageMessage("", to, image, imageURL, System.currentTimeMillis());
	}

	public void onMessageStateChanged(String to, String message, int newState) {
		getChatStorage().updateMessageStatus(to, message, newState);
	}

	public void onImageMessageStateChanged(String to, int id, int newState) {
		getChatStorage().updateMessageStatus(to, id, newState);
	}

	public void onRegistrationStateChanged(LinphoneProxyConfig proxy, RegistrationState state, String message) {
		LinphoneCore lc = LinphoneManager.getLcIfManagerNotDestroyedOrNull();
		if (statusFragment != null) {
			if (lc != null)
				if(lc.getDefaultProxyConfig() == null)
					statusFragment.registrationStateChanged(proxy.getState());
				else 
					statusFragment.registrationStateChanged(lc.getDefaultProxyConfig().getState());
			else
				statusFragment.registrationStateChanged(RegistrationState.RegistrationNone);
		}

		if(state.equals(RegistrationState.RegistrationCleared)){ 
			if(lc != null){
				LinphoneAuthInfo authInfo = lc.findAuthInfo(proxy.getIdentity(), proxy.getRealm(), proxy.getDomain());
				if(authInfo != null)
					lc.removeAuthInfo(authInfo);
			}
		}
	}

	private void displayMissedCalls(final int missedCallsCount) {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (missedCallsCount > 0) {
					missedCalls.setText(missedCallsCount + "");
					missedCalls.setVisibility(View.VISIBLE);
					if (!isAnimationDisabled) {
						missedCalls.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.bounce));
					}
				} else {
					missedCalls.clearAnimation();
					missedCalls.setVisibility(View.GONE);
				}
			}
		});
	}

	private void displayMissedChats(final int missedChatCount) {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (missedChatCount > 0) {
					missedChats.setText(missedChatCount + "");
					if (missedChatCount > 99) {
						missedChats.setTextSize(12);
					} else {
						missedChats.setTextSize(20);
					}
					missedChats.setVisibility(View.VISIBLE);
					if (!isAnimationDisabled) {
						missedChats.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.bounce));
					}
				} else {
					missedChats.clearAnimation();
					missedChats.setVisibility(View.GONE);
				}
			}
		});
	}

	@Override
	public void onCallStateChanged(LinphoneCall call, State state, String message) {
		if (state == State.IncomingReceived) {
			startActivity(new Intent(this, IncomingCallActivity.class));
		} else if (state == State.OutgoingInit) {
			if (call.getCurrentParamsCopy().getVideoEnabled()) {
				startVideoActivity(call);
			} else {
				startIncallActivity(call);
			}
		} else if (state == State.CallEnd || state == State.Error || 
				state == State.CallReleased) {
			// Convert LinphoneCore message for internalization
			if (message != null && message.equals("Call declined.")) { 
				displayCustomToast(getString(R.string.error_call_declined), 
						Toast.LENGTH_LONG);
			} 
			else if (message != null && message.equals("Not Found")) {
				displayCustomToast(getString(R.string.error_user_not_found), 
						Toast.LENGTH_LONG);
			} 
			else if (message != null && message.equals("Unsupported media type")) {
				displayCustomToast(getString(R.string.error_incompatible_media), 
						Toast.LENGTH_LONG);
			}
			resetClassicMenuLayoutAndGoBackToCallIfStillRunning();
		}

		int missedCalls = LinphoneManager.getLc().getMissedCallsCount();
		displayMissedCalls(missedCalls);
	}

	public void displayCustomToast(final String message, final int duration) {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(R.layout.toast, (ViewGroup) 
						findViewById(R.id.toastRoot));

				TextView toastText = (TextView) layout.findViewById(R.id.toastMessage);
				toastText.setText(message);

				final Toast toast = new Toast(getApplicationContext());
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.setDuration(duration);
				toast.setView(layout);
				toast.show();
			}
		});
	}

	@Override
	public void setAddresGoToDialerAndCall(String number, String name, Uri photo) {
		AddressType address = new AddressText(this, null);
		address.setDisplayedName(name);		
		address.setText(number);
		LinphoneManager.getInstance().newOutgoingCall(address);
	}

	@Override	
	public void goToDialer() {	 
		//TODO test changeCurrentFragment(FragmentsAvailable.DIALER, null);
	}

	public void startVideoActivity(LinphoneCall currentCall) {
		Intent intent = new Intent(this, InCallActivity.class);
		intent.putExtra("VideoEnabled", true);
		startOrientationSensor();
		startActivityForResult(intent, CALL_ACTIVITY);
	}

	public void startIncallActivity(LinphoneCall currentCall) {
		Intent intent = new Intent(this, InCallActivity.class);
		intent.putExtra("VideoEnabled", false);
		startOrientationSensor();
		startActivityForResult(intent, CALL_ACTIVITY);
	}

	/**
	 * Register a sensor to track phoneOrientation changes
	 */
	private synchronized void startOrientationSensor() {
		if (mOrientationHelper == null) {
			mOrientationHelper = new LocalOrientationEventListener(this);
		}
		mOrientationHelper.enable();
	}

	private int mAlwaysChangingPhoneAngle = -1;

	private AcceptNewFriendDialog acceptNewFriendDialog;

	private class LocalOrientationEventListener extends OrientationEventListener {
		public LocalOrientationEventListener(Context context) {
			super(context);
		}

		@Override
		public void onOrientationChanged(final int o) {
			if (o == OrientationEventListener.ORIENTATION_UNKNOWN) {
				return;
			}

			int degrees = 270;
			if (o < 45 || o > 315)
				degrees = 0;
			else if (o < 135)
				degrees = 90;
			else if (o < 225)
				degrees = 180;

			if (mAlwaysChangingPhoneAngle == degrees) {
				return;
			}
			mAlwaysChangingPhoneAngle = degrees;

			//Log.d("Phone orientation changed to ", degrees);
			int rotation = (360 - degrees) % 360;
			LinphoneCore lc = LinphoneManager.getLcIfManagerNotDestroyedOrNull();
			if (lc != null) {
				lc.setDeviceRotation(rotation);
				LinphoneCall currentCall = lc.getCurrentCall();
				if (currentCall != null && currentCall.cameraEnabled() && currentCall.getCurrentParamsCopy().getVideoEnabled()) {
					lc.updateCall(currentCall, null);
				}
			}
		}
	}

	public void showPreferenceErrorDialog(String message) {

	}

	public List<Contact> getAllContacts() {
		return contactList;
	}

	public List<Contact> getSIPContacts() {
		return sipContactList;
	}

	public Cursor getAllContactsCursor() {
		return contactCursor;
	}

	public Cursor getSIPContactsCursor() {
		return sipContactCursor;
	}

	public void setLinphoneContactsPrefered(boolean isPrefered) {
		preferLinphoneContacts = isPrefered;
	}

	public boolean isLinphoneContactsPrefered() {
		return preferLinphoneContacts;
	}

	public void onNewSubscriptionRequestReceived(LinphoneFriend friend,
			String sipUri) {
		if (isContactPresenceDisabled) {
			return;
		}

		sipUri = sipUri.replace("<", "").replace(">", "");
		if (LinphonePreferences.instance().shouldAutomaticallyAcceptFriendsRequests()) {
			Contact contact = findContactWithSipAddress(sipUri);
			if (contact != null) {
				friend.enableSubscribes(true);
				try {
					LinphoneManager.getLc().addFriend(friend);
					contact.setFriend(friend);
				} catch (LinphoneCoreException e) {
					e.printStackTrace();
				}
			}
		} else {
			Contact contact = findContactWithSipAddress(sipUri);
			if (contact != null) {
				FragmentManager fm = getSupportFragmentManager();
				acceptNewFriendDialog = new AcceptNewFriendDialog(contact, sipUri);
				acceptNewFriendDialog.show(fm, "New Friend Request Dialog");
			}
		}
	}

	private Contact findContactWithSipAddress(String sipUri) {
		if (!sipUri.startsWith("sip:")) {
			sipUri = "sip:" + sipUri;
		}

		for (Contact contact : sipContactList) {
			for (String addr : contact.getNumerosOrAddresses()) {
				if (addr.equals(sipUri)) {
					return contact;
				}
			}
		}
		return null;
	}

	public void onNotifyPresenceReceived(LinphoneFriend friend) {
		if (!isContactPresenceDisabled && currentFragment == FragmentsAvailable.CONTACTS && friendStatusListenerFragment != null) {
			((ContactsFragment) friendStatusListenerFragment).invalidate();
		}
	}

	public boolean newFriend(Contact contact, String sipUri) {
		LinphoneFriend friend = LinphoneCoreFactory.instance().createLinphoneFriend(sipUri);
		friend.enableSubscribes(true);
		friend.setIncSubscribePolicy(LinphoneFriend.SubscribePolicy.SPAccept);
		try {
			LinphoneManager.getLc().addFriend(friend);
			contact.setFriend(friend);
			return true;
		} catch (LinphoneCoreException e) {
			e.printStackTrace();
		}
		return false;
	}

	private void acceptNewFriend(Contact contact, String sipUri, boolean accepted) {
		acceptNewFriendDialog.dismissAllowingStateLoss();
		if (accepted) {
			newFriend(contact, sipUri);
		}
	}

	public boolean removeFriend(Contact contact, String sipUri) {
		LinphoneFriend friend = LinphoneManager.getLc().findFriendByAddress(sipUri);
		if (friend != null) {
			friend.enableSubscribes(false);
			LinphoneManager.getLc().removeFriend(friend);
			contact.setFriend(null);
			return true;
		}
		return false;
	}

	private void searchFriendAndAddToContact(Contact contact) {
		if (contact == null || contact.getNumerosOrAddresses() == null) {
			return;
		}

		for (String sipUri : contact.getNumerosOrAddresses()) {
			if (LinphoneUtils.isSipAddress(sipUri)) {
				LinphoneFriend friend = LinphoneManager.getLc().findFriendByAddress(sipUri);
				if (friend != null) {
					friend.enableSubscribes(true);
					friend.setIncSubscribePolicy(LinphoneFriend.SubscribePolicy.SPAccept);
					contact.setFriend(friend);
					break;
				}
			}
		}
	}

	public void removeContactFromLists(Contact contact) {
		for (Contact c : contactList) {
			if (c != null && c.getID().equals(contact.getID())) {
				contactList.remove(c);
				contactCursor = Compatibility.getContactsCursor(getContentResolver());
				break;
			}
		}

		for (Contact c : sipContactList) {
			if (c != null && c.getID().equals(contact.getID())) {
				sipContactList.remove(c);
				sipContactCursor = Compatibility.getSIPContactsCursor(getContentResolver());
				break;
			}
		}
	}

	public synchronized void prepareContactsInBackground() {
		if (contactCursor != null) {
			contactCursor.close();
		}
		if (sipContactCursor != null) {
			sipContactCursor.close();
		}

		contactCursor = Compatibility.getContactsCursor(getContentResolver());
		sipContactCursor = Compatibility.getSIPContactsCursor(getContentResolver());

		Thread sipContactsHandler = new Thread(new Runnable() {
			@Override
			public void run() {
				if(sipContactCursor != null) {
					for (int i = 0; i < sipContactCursor.getCount(); i++) {
						Contact contact = Compatibility.getContact(getContentResolver(), sipContactCursor, i);
						if (contact == null)
							continue;

						contact.refresh(getContentResolver());/*, getResources().getString(R.string.default_account_prefix));*/
						if (!isContactPresenceDisabled) {
							searchFriendAndAddToContact(contact);
						}
						sipContactList.add(contact);
					}
				}
				if(contactCursor != null) {
					for (int i = 0; i < contactCursor.getCount(); i++) {
						Contact contact = Compatibility.getContact(getContentResolver(), contactCursor, i);
						if (contact == null)
							continue;

						for (Contact c : sipContactList) {
							if (c != null && c.getID().equals(contact.getID())) {
								contact = c;
								break;
							}
						}
						contactList.add(contact);
					}
				}
			}
		});

		contactList = new ArrayList<Contact>();
		sipContactList = new ArrayList<Contact>();

		sipContactsHandler.start();
	}

	public void resetClassicMenuLayoutAndGoBackToCallIfStillRunning() {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				if (LinphoneManager.isInstanciated() && LinphoneManager.getLc().getCallsNb() > 0) {
					LinphoneCall call = LinphoneManager.getLc().getCalls()[0];
					if (call.getState() == LinphoneCall.State.IncomingReceived) {
						startActivity(new Intent(MainActivity.this, IncomingCallActivity.class));
					} else if (call.getCurrentParamsCopy().getVideoEnabled()) {
						startVideoActivity(call);
					} else {
						startIncallActivity(call);
					}
				}
			}
		});
	}

	public FragmentsAvailable getCurrentFragment() {
		return currentFragment;
	}

	public ChatStorage getChatStorage() {
		return ChatStorage.getInstance();
	}

	public void addRing()
	{
		Log.v("=== En addRing de MainActivity");
		Bundle extras = new Bundle();
		extras.putSerializable("NewRing", true);
		changeCurrentFragment(FragmentsAvailable.EDIT_RING, extras);	
	}

	public void addContact(String displayName, String sipUri)
	{
		if (getResources().getBoolean(R.bool.use_android_native_contact_edit_interface)) {
			Intent intent = Compatibility.prepareAddContactIntent(displayName, sipUri);
			startActivity(intent);
		} 
		else {
			Bundle extras = new Bundle();
			extras.putSerializable("NewSipAdress", sipUri);
			changeCurrentFragment(FragmentsAvailable.EDIT_CONTACT, extras);
		}
	}

	public void addRing(String displayName, String sipUri)
	{
		if (getResources().getBoolean(R.bool.use_android_native_contact_edit_interface)) {
			Intent intent = Compatibility.prepareAddContactIntent(displayName, sipUri);
			startActivity(intent);
		} else {
			Bundle extras = new Bundle();
			extras.putSerializable("NewSipAdress", sipUri);
			changeCurrentFragment(FragmentsAvailable.EDIT_CONTACT, extras);
		}
	}

	public void editRing(Ring ring){
		Bundle extras = new Bundle();
		extras.putSerializable("Ring", ring);
		changeCurrentFragment(FragmentsAvailable.EDIT_RING, extras);		
	}

	public void editContact(Contact contact){
		if (getResources().getBoolean(R.bool.use_android_native_contact_edit_interface)) {
			Intent intent = Compatibility.prepareEditContactIntent(Integer.parseInt(contact.getID()));
			startActivity(intent);
		} else {
			Bundle extras = new Bundle();
			extras.putSerializable("Contact", contact);
			changeCurrentFragment(FragmentsAvailable.EDIT_CONTACT, extras);
		}
	}

	public void editContact(Contact contact, String sipAddress)
	{
		if (getResources().getBoolean(R.bool.use_android_native_contact_edit_interface)) {
			Intent intent = Compatibility.prepareEditContactIntentWithSipAddress(Integer.parseInt(contact.getID()), sipAddress);
			startActivity(intent);
		} else {
			Bundle extras = new Bundle();
			extras.putSerializable("Contact", contact);
			extras.putSerializable("NewSipAdress", sipAddress);
			changeCurrentFragment(FragmentsAvailable.EDIT_CONTACT, extras);
		}
	}

	public void exit() {
		finish();
		stopService(new Intent(ACTION_MAIN).setClass(this, LinphoneService.class));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_FIRST_USER && requestCode == SETTINGS_ACTIVITY) {
			if (data.getExtras().getBoolean("Exit", false)) {
				exit();
			} else {
				FragmentsAvailable newFragment = (FragmentsAvailable) data.getExtras().getSerializable("FragmentToDisplay");
				changeCurrentFragment(newFragment, null, true);
				selectMenu(newFragment);
			}
		} 	
		else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	@Override
	protected void onPause() {
		getIntent().putExtra("PreviousActivity", 0);
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		Log.v("=== OnResume del MainActivity..................");			

		if (!LinphoneService.isReady())
			startService(new Intent(ACTION_MAIN).setClass(this, LinphoneService.class));
		
		if(currentFragment == FragmentsAvailable.START || 
				currentFragment == FragmentsAvailable.WELCOME){
			return;
		}
		
		// Remove to avoid duplication of the listeners
		LinphoneManager.removeListener(this);
		LinphoneManager.addListener(this);
				
		prepareContactsInBackground();
		
		updateMissedChatCount();
		displayMissedCalls(LinphoneManager.getLc().getMissedCallsCount());
		
		LinphoneManager.getInstance().changeStatusToOnline();

		if(getIntent().getIntExtra("PreviousActivity", 0) != CALL_ACTIVITY){
			if (LinphoneManager.getLc().getCalls().length > 0) {
				LinphoneCall call = LinphoneManager.getLc().getCalls()[0];
				LinphoneCall.State callState = call.getState();
				if (callState == State.IncomingReceived) {
					startActivity(new Intent(this, IncomingCallActivity.class));
				} else {

					if (call.getCurrentParamsCopy().getVideoEnabled()) {
						startVideoActivity(call);
					} else {
						startIncallActivity(call);
					}
				}
			}
		}
	}

	@Override
	protected void onDestroy() {
		LinphoneManager.removeListener(this);		

		if (mOrientationHelper != null) {
			mOrientationHelper.disable();
			mOrientationHelper = null;
		}
		
		LinphoneManager.getInstance().changeStatusToOffline();

		Intent lp = new Intent(MainActivity.instance()
				.getApplicationContext(),LinphoneService.class);
		LinphoneService.instance().	
		stopService(lp);
		
		instance = null;
		
		super.onDestroy();

		unbindDrawables(findViewById(R.id.topLayout));
		System.gc();
	}

>>>>>>> second_stage
	private void unbindDrawables(View view) {
		if (view != null && view.getBackground() != null) {
			view.getBackground().setCallback(null);
		}
		if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
				unbindDrawables(((ViewGroup) view).getChildAt(i));
			}
			((ViewGroup) view).removeAllViews();
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		Bundle extras = intent.getExtras();
		if (extras != null && extras.getBoolean("GoToChat", false)) {
			LinphoneService.instance().removeMessageNotification();
			String sipUri = extras.getString("ChatContactSipUri");
			displayChat(sipUri);
		} else if (extras != null && extras.getBoolean("Notification", false)) {
			if (LinphoneManager.getLc().getCallsNb() > 0) {
				LinphoneCall call = LinphoneManager.getLc().getCalls()[0];
				if (call.getCurrentParamsCopy().getVideoEnabled()) {
					startVideoActivity(call);
				} else {
					startIncallActivity(call);
				}
			}
<<<<<<< HEAD
		} else {
			/* TODO test
			 * if (dialerFragment != null) {
			 
				if (extras != null && extras.containsKey("SipUriOrNumber")) {
					if (getResources().getBoolean(R.bool.automatically_start_intercepted_outgoing_gsm_call)) {
						((DialerFragment) dialerFragment).newOutgoingCall(extras.getString("SipUriOrNumber"));
					} else {
						((DialerFragment) dialerFragment).displayTextInAddressBar(extras.getString("SipUriOrNumber"));
					}
				} else {
					((DialerFragment) dialerFragment).newOutgoingCall(intent);
				}
			}*/
=======
		} else {			
>>>>>>> second_stage
			if (LinphoneManager.getLc().getCalls().length > 0) {
				LinphoneCall calls[] = LinphoneManager.getLc().getCalls();
				if (calls.length > 0) {
					LinphoneCall call = calls[0];
<<<<<<< HEAD
					
=======

>>>>>>> second_stage
					if (call != null && call.getState() != LinphoneCall.State.IncomingReceived) {
						if (call.getCurrentParamsCopy().getVideoEnabled()) {
							startVideoActivity(call);
						} else {
							startIncallActivity(call);
						}
					}
				}
<<<<<<< HEAD
				
=======

>>>>>>> second_stage
				// If a call is ringing, start incomingcallactivity
				Collection<LinphoneCall.State> incoming = new ArrayList<LinphoneCall.State>();
				incoming.add(LinphoneCall.State.IncomingReceived);
				if (LinphoneUtils.getCallsInState(LinphoneManager.getLc(), incoming).size() > 0) {
					if (InCallActivity.isInstanciated()) {
						InCallActivity.instance().startIncomingCallActivity();
					} else {
						startActivity(new Intent(this, IncomingCallActivity.class));
					}
				}
			}
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
<<<<<<< HEAD
			/*TODO test
			if (currentFragment == FragmentsAvailable.DIALER) {
				boolean isBackgroundModeActive = LinphonePreferences.instance().isBackgroundModeEnabled();
				if (!isBackgroundModeActive) {
					stopService(new Intent(Intent.ACTION_MAIN).setClass(this, LinphoneService.class));
					finish();
				} else if (LinphoneUtils.onKeyBackGoHome(this, keyCode, event)) {
					return true;
				}
			} else {*/
				if (isTablet()) {
					if (currentFragment == FragmentsAvailable.SETTINGS) {
						updateAnimationsState();
					}
					
					fragmentsHistory.remove(fragmentsHistory.size() - 1);
					if (fragmentsHistory.size() > 0) {
						FragmentsAvailable newFragmentType = fragmentsHistory.get(fragmentsHistory.size() - 1);
						LinearLayout ll = (LinearLayout) findViewById(R.id.fragmentContainer2);
						if (newFragmentType.shouldAddItselfToTheRightOf(currentFragment)) {
							ll.setVisibility(View.VISIBLE);
						} else {
							if (newFragmentType == FragmentsAvailable.PANIC //TODO test newFragmentType == FragmentsAvailable.DIALER 
									|| newFragmentType == FragmentsAvailable.ABOUT 
									|| newFragmentType == FragmentsAvailable.ABOUT_INSTEAD_OF_CHAT 
									|| newFragmentType == FragmentsAvailable.ABOUT_INSTEAD_OF_SETTINGS
									|| newFragmentType == FragmentsAvailable.SETTINGS 
									|| newFragmentType == FragmentsAvailable.ACCOUNT_SETTINGS) {
								ll.setVisibility(View.GONE);
							} else {
								ll.setVisibility(View.INVISIBLE);
							}
						}
					}
				}
			//}
		} else if (keyCode == KeyEvent.KEYCODE_MENU && statusFragment != null) {
=======

			if (isTablet()) {
				if (currentFragment == FragmentsAvailable.SETTINGS) {
					updateAnimationsState();
				}

				fragmentsHistory.remove(fragmentsHistory.size() - 1);
				if (fragmentsHistory.size() > 0) {
					FragmentsAvailable newFragmentType = fragmentsHistory.get(fragmentsHistory.size() - 1);
					LinearLayout ll = (LinearLayout) findViewById(R.id.activityContainer2);
					if (newFragmentType.shouldAddItselfToTheRightOf(currentFragment)) {
						ll.setVisibility(View.VISIBLE);
					} else {
						if (newFragmentType == FragmentsAvailable.PANIC 
								|| newFragmentType == FragmentsAvailable.ABOUT 								
								|| newFragmentType == FragmentsAvailable.SETTINGS 
								|| newFragmentType == FragmentsAvailable.ACCOUNT_SETTINGS) {
							ll.setVisibility(View.GONE);
						} else {
							ll.setVisibility(View.INVISIBLE);
						}
					}
				}
			}			
		} 
		else if (keyCode == KeyEvent.KEYCODE_MENU && statusFragment != null) {
>>>>>>> second_stage
			if (event.getRepeatCount() < 1) {
				statusFragment.openOrCloseStatusBar(true);
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@SuppressLint("ValidFragment")
	class AcceptNewFriendDialog extends DialogFragment {
		private Contact contact;
		private String sipUri;

		public AcceptNewFriendDialog(Contact c, String a) {
			contact = c;
			sipUri = a;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.new_friend_request_dialog, container);

			getDialog().setTitle(R.string.linphone_friend_new_request_title);

			Button yes = (Button) view.findViewById(R.id.yes);
			yes.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					acceptNewFriend(contact, sipUri, true);
				}
			});

			Button no = (Button) view.findViewById(R.id.no);
			no.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					acceptNewFriend(contact, sipUri, false);
				}
			});

			return view;
		}
	}
<<<<<<< HEAD
=======

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
>>>>>>> second_stage
	
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

}

interface ContactPicked {
	void setAddresGoToDialerAndCall(String number, String name, Uri photo);
	void goToDialer();
}