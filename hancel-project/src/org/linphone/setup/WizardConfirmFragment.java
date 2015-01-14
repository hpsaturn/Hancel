package org.linphone.setup;
/*
WizardConfirmFragment.java
Copyright (C) 2012  Belledonne Communications, Grenoble, France

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/
import java.net.URL;
import java.util.Calendar;

import org.hansel.myAlert.R;
import org.hansel.myAlert.Log.Log;
import org.hansel.myAlert.Utils.PreferenciasHancel;
import org.hansel.myAlert.Utils.SimpleCrypto;
import org.hansel.myAlert.Utils.Util;
import org.hancel.http.HttpUtils;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import de.timroes.axmlrpc.XMLRPCCallback;
import de.timroes.axmlrpc.XMLRPCClient;
import de.timroes.axmlrpc.XMLRPCException;
import de.timroes.axmlrpc.XMLRPCServerException;



/**
 * @author Sylvain Berfini
 */
public class WizardConfirmFragment extends Fragment {
	private String username;
	private String IMEI;
	private String mErrores;
	private Handler mHandler = new Handler();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.setup_wizard_confirm, container, false);
		
		username = getArguments().getString("Username");
		IMEI = getArguments().getString("IMEI");
		
		ImageView checkAccount = (ImageView) view.findViewById(R.id.setup_check);
		checkAccount.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				isAccountVerified(username);
			}
		});
		
		return view;
	}	
	
	private void isAccountVerified(String username) {
		final Runnable runNotReachable = new Runnable() {
			public void run() {
				Toast.makeText(getActivity(), getString(R.string.wizard_server_unavailable), Toast.LENGTH_LONG).show();
			}
		};
		
		try {
			XMLRPCClient client = new XMLRPCClient(new URL(getString(R.string.wizard_url)));
			
			XMLRPCCallback listener = new XMLRPCCallback() {
				Runnable runNotOk = new Runnable() {
    				public void run() {
    					Toast.makeText(getActivity(), getString(R.string.setup_account_not_validated), Toast.LENGTH_LONG).show();
					}
	    		};
	    		
	    		Runnable runOk = new Runnable() {
    				public void run() {
    					SetupActivity.instance().isAccountVerified();
					}
	    		};
	    		
			    public void onResponse(long id, Object result) {
			    	int answer = (Integer) result;
			    	if (answer != 1) {
			    		mHandler.post(runNotOk);
			    	} else {
			    		mHandler.post(runOk);
			    	}
			    }
			    
			    public void onError(long id, XMLRPCException error) {
			    	mHandler.post(runNotReachable);
			    }
			   
			    public void onServerError(long id, XMLRPCServerException error) {
			    	mHandler.post(runNotReachable);
			    }
			};

		    client.callAsync(listener, "check_account_validated", username + "@" + getString(R.string.default_domain));
		} 
		catch(Exception ex) {
			mHandler.post(runNotReachable);
		}
	}

	@SuppressLint("DefaultLocale")
	public class UserCreateTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... arg0) {
			//conexion a la BD para obtener Login.
			
			try
			{	
				String mPassword="";
				String mEmail="";
				String id = SimpleCrypto.md5(String.valueOf(Calendar.getInstance().getTimeInMillis()));
				JSONObject result=  HttpUtils.Register(id, username, SimpleCrypto.md5(mPassword), mEmail,"", IMEI);
				try {
					 
					 if(result.optString("resultado").equals("ok"))
					 {
						 
						return true;
						 /*
					 
						 //parseamos data del ID
						 JSONObject jObject = result.getJSONObject("descripcion");
						 int androidId = Integer.parseInt(jObject.getString("usr-id"));
						 
						 PreferenciasHancel.setDeviceId(getApplicationContext(), id);
						 PreferenciasHancel.setUserId(getApplicationContext(), androidId);
						 Util.insertNewTrackId(getApplicationContext(), 0);
						  Util.setLoginOkInPreferences(getApplicationContext(), true);
						  int idUsr=(int) usuarioDAO.Insertar( mUsuario, mPassword, mEmail);
							if(idUsr!=0){
								return true;
							}
							*/
					 
					 }else if(result.optString("resultado").equals("error"))
					 {
						 JSONObject jObject = result.getJSONObject("descripcion");
						 mErrores = jObject.getString("usuario");
						 //buscamos el error:
						 return false;
					 }
					 
					
				} catch (Exception e) {
					Log.v("Error al parsear Json: "+ result);
					mErrores= "Error al obtener los datos";
					return false;
				}
			
				return false;
			}catch(Exception ex)
			{
				mErrores="Error al intentar la conexi�n";
				Log.v("Error login: "+ex.getMessage());
			}
			return false;
		}
	}
}
