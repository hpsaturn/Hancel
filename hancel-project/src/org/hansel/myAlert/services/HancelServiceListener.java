package org.hansel.myAlert.services;
import org.linphone.core.LinphoneAddress;
import org.linphone.core.LinphoneChatMessage;
import org.linphone.core.LinphoneChatRoom;
import org.linphone.core.LinphoneCore.GlobalState;

import org.linphone.core.LinphoneCore.RegistrationState;
import org.linphone.core.LinphoneProxyConfig;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public interface HancelServiceListener {
	
	public static interface ChatListener extends HancelServiceListener {
		void onMessageReceived(LinphoneAddress from, LinphoneChatMessage message, int id);
		void onComposingReceived(LinphoneChatRoom room);
	}

	public static interface RegistrationServerListener extends HancelServiceListener {
		void onRegistrationStateChange(LinphoneProxyConfig proxy, RegistrationState state, String message);
	}

	public static interface ConnectivityListener extends HancelServiceListener {
		void onConnectivityChange(Context context, NetworkInfo eventInfo, ConnectivityManager cm);
	}
}
