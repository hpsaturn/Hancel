package org.hansel.myAlert;

import java.util.ArrayList;
import java.util.List;


import org.hansel.myAlert.dataBase.RingDAO;
import org.linphone.FragmentsAvailable;
import org.linphone.LinphoneManager;
import org.linphone.LinphoneService;
import org.linphone.LinphoneUtils;
import org.linphone.compatibility.Compatibility;
import org.linphone.mediastream.Log;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AlphabetIndexer;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class RingsFragment extends Fragment implements OnClickListener, OnItemClickListener {

private Handler mHandler = new Handler();
	
	private LayoutInflater mInflater;
	private ListView ringsList;
	private TextView newRing, noRings;
	private int lastKnownPosition;
	private AlphabetIndexer indexer;;
	private Cursor searchCursor;

	private static RingsFragment instance;
	
	static final boolean isInstanciated() {
		return instance != null;
	}

	public static final RingsFragment instance() {
		return instance;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
		mInflater = inflater;
        View view = inflater.inflate(R.layout.rings_list, container, false);
               
        noRings = (TextView) view.findViewById(R.id.noRings);
        
        ringsList = (ListView) view.findViewById(R.id.ringsList);        
        ringsList.setOnItemClickListener(this);
                         
        newRing = (TextView) view.findViewById(R.id.newRing);
        newRing.setOnClickListener(this);
        
        changeRingsAdapter();
                                           
		return view;
    }

	@Override
	public void onClick(View v) {
		int id = v.getId();
					
		if (id == R.id.newRing) {
			//editConsumed = true;			
			MainActivity.instance().addRing();
		} 		
	}
	
	private void changeRingsAdapter() {		
		if (searchCursor != null) {
			searchCursor.close();
		}
		
		RingDAO ringDAO = new RingDAO(LinphoneService.instance().getApplicationContext());
		ringDAO.open();
		Cursor ringsCursor = ringDAO.getRigsCursor();		
		List<Ring> allRings = null;
				
		if(ringsCursor != null && ringsCursor.getCount() > 0){
			allRings = new ArrayList<Ring>();		
			ringsCursor.moveToFirst();
			
			for(int i = 0; i< ringsCursor.getCount(); i++){
				allRings.add(new Ring(ringsCursor.getString(0),ringsCursor
						.getString(1), ringsCursor.getLong(2)));
				ringsCursor.moveToNext();
			}
			
			indexer = new AlphabetIndexer(ringsCursor, 1, 
					" ABCDEFGHIJKLMNOPQRSTUVWXYZ");
			ringsList.setAdapter(new RingsListAdapter(allRings));
			noRings.setVisibility(View.GONE);
			ringsList.setVisibility(View.VISIBLE);
			
			ringDAO.close();
		}
		else {
			noRings.setVisibility(View.VISIBLE);
			ringsList.setVisibility(View.GONE);
		} 
	}
	
	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
		Ring ring = (Ring) adapter.getItemAtPosition(position);		
		MainActivity.instance().editRing(ring);
		
	}
	
	@Override
	public void onResume() {
		instance = this;
		super.onResume();		
			
		if (MainActivity.isInstanciated()) {
			MainActivity.instance().selectMenu(FragmentsAvailable.RINGS);
			
			if (getResources().getBoolean(R.bool.show_statusbar_only_on_dialer)) {
				MainActivity.instance().hideStatusBar();
			}
		}
		
		invalidate();
	}
	
	@Override
	public void onPause() {
		instance = null;
		if (searchCursor != null) {
			searchCursor.close();
		}
		super.onPause();
	}
	
	public void invalidate() {
		mHandler.post(new Runnable() {
			@Override
			public void run() {				
				changeRingsAdapter();
				ringsList.setSelectionFromTop(lastKnownPosition, 0);
			}
		});
	}
	
	class RingsListAdapter extends BaseAdapter implements SectionIndexer {
		private int margin;
		private Bitmap bitmapUnknown;
		private List<Ring> rings;
				
		public RingsListAdapter(List<Ring> ringsList) {
			rings = ringsList;			
			margin = LinphoneUtils.pixelsToDpi(getResources(), 10);
			bitmapUnknown = BitmapFactory.decodeResource(getResources(), R.drawable.unknown_small);
		}
				
		public int getCount() {
			if(rings != null)
				return rings.size();
			return 0;
		}

		public Object getItem(int position) {
			if (rings == null || position >= rings.size()) {
				return null;		
			} 
			
			else {
				return rings.get(position);
			}
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			Ring ring = null;
			do {
				ring = (Ring) getItem(position);
			} 
			while (ring == null);
			
			if (convertView != null) {
				view = convertView;
			} 
			else {
				view = mInflater.inflate(R.layout.ring_cell, parent, false);
			}
			
			TextView name = (TextView) view.findViewById(R.id.name);
			name.setText(ring.getName());
			
			TextView separator = (TextView) view.findViewById(R.id.separator);
			LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout);
			
			if (getPositionForSection(getSectionForPosition(position)) != position) {
				separator.setVisibility(View.GONE);
				layout.setPadding(0, margin, 0, margin);
			} 
			else {
				separator.setVisibility(View.VISIBLE);
				separator.setText(String.valueOf(ring.getName().charAt(0)));
				layout.setPadding(0, 0, 0, margin);
			}
			
			ImageView icon = (ImageView) view.findViewById(R.id.icon);
			icon.setImageBitmap(bitmapUnknown);
						
			return view;
		}

		@Override
		public int getPositionForSection(int section) {
			return indexer.getPositionForSection(section);
		}

		@Override
		public int getSectionForPosition(int position) {
			return indexer.getSectionForPosition(position);
		}

		@Override
		public Object[] getSections() {
			return indexer.getSections();
		}
	}
}

