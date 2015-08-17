package org.hansel.myAlert;


import com.actionbarsherlock.view.ActionProvider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;



public class MainMenu extends ActionProvider {
	
	Context mContext;

	public MainMenu(Context context) {
		super(context);
		this.mContext = context;
	}
	
	@Override
	public View onCreateActionView() {
		LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.main_menu,null);
        return view;
	}

}
