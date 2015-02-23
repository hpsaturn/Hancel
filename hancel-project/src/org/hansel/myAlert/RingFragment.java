package org.hansel.myAlert;

import java.util.List;

import org.hansel.myAlert.Utils.Ring;
import org.hansel.myAlert.dataBase.RingDAO;
import org.linphone.Contact;
import org.linphone.ContactsFragment;
import org.linphone.FragmentsAvailable;
import org.linphone.LinphoneManager;
import org.linphone.LinphoneService;
import org.linphone.LinphoneUtils;
//import org.linphone.ContactsFragment.ContactsListAdapter;
import org.linphone.compatibility.Compatibility;
import org.linphone.core.LinphoneFriend;
import org.linphone.core.PresenceActivityType;
import org.linphone.mediastream.Log;

import android.content.pm.PackageManager.NameNotFoundException;
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

public class RingFragment extends Fragment implements OnClickListener, OnItemClickListener {

private Handler mHandler = new Handler();
	
	private LayoutInflater mInflater;
	private ListView ringsList;
	private TextView allRings, newRing, noRings;//linphoneContacts, newRing;// noSipContact, noContact;
	private int lastKnownPosition;
	private AlphabetIndexer indexer;
	private boolean editOnClick = false, editConsumed = false, onlyDisplayChatAddress = false;
	//private String sipAddressToAdd;
	private ImageView clearSearchField;
	private EditText searchField;
	private Cursor searchCursor;

	private static RingFragment instance;
	
	static final boolean isInstanciated() {
		return instance != null;
	}

	public static final RingFragment instance() {
		return instance;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
		mInflater = inflater;
        View view = inflater.inflate(R.layout.rings_list, container, false);
        /*
        if (getArguments() != null) {
	        editOnClick = getArguments().getBoolean("EditOnClick");
	        sipAddressToAdd = getArguments().getString("SipAddress");	        
	        onlyDisplayChatAddress = getArguments().getBoolean("ChatAddressOnly");
        }*/
        
        noRings = (TextView) view.findViewById(R.id.noRings);
        
        ringsList = (ListView) view.findViewById(R.id.ringsList);        
        ringsList.setOnItemClickListener(this);
        
        allRings = (TextView) view.findViewById(R.id.allRings);
        allRings.setOnClickListener(this);
                  
        newRing = (TextView) view.findViewById(R.id.newRing);
        newRing.setOnClickListener(this);
        newRing.setEnabled(LinphoneManager.getLc().getCallsNb() == 0);
        
        allRings.setEnabled(true);
               
		clearSearchField = (ImageView) view.findViewById(R.id.clearSearchField);
		clearSearchField.setOnClickListener(this);
		
		searchField = (EditText) view.findViewById(R.id.searchField);
		searchField.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				searchRings(searchField.getText().toString());
			}
		});
        
		return view;
    }

	@Override
	public void onClick(View v) {
		int id = v.getId();
		
		if (id == R.id.allRings) {
			if (searchField.getText().toString().length() > 0) {
				searchContacts();
			} 
			else {
				changeContactsAdapter();
			}
		} 		
		else if (id == R.id.newRing) {
			editConsumed = true;
			/**TODO implementar **/
			//MainActivity.instance().addContact(null, sipAddressToAdd);
		} 
		else if (id == R.id.clearSearchField) {
			searchField.setText("");
		}
	}
	
	private void searchContacts() {
		searchRings(searchField.getText().toString());
	}

	
	private void searchRings(String search) {
		if (search == null || search.length() == 0) {
			changeContactsAdapter();
			return;
		}
		
		//changeContactsToggle();
		
		if (searchCursor != null) {
			searchCursor.close();
		}
		RingDAO ringDAO = new RingDAO(LinphoneService.instance().getApplicationContext());
		searchCursor = ringDAO.getRingsByNameCursor(search);
		//searchCursor = Compatibility.getSIPContactsCursor(getActivity().getContentResolver(), search);-
		indexer = new AlphabetIndexer(searchCursor, 1, " ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		ringsList.setAdapter(new RingsListAdapter(null, searchCursor));		
	}
	
	private void changeContactsAdapter() {
		//changeContactsToggle();		
		if (searchCursor != null) {
			searchCursor.close();
		}
				
		Cursor allRingsCursor = MainActivity.instance().getAllRingsCursor();
		
		ringsList.setVisibility(View.VISIBLE);
		
		if (allRingsCursor == null || allRingsCursor.getCount() == 0) {
			noRings.setVisibility(View.VISIBLE);
			ringsList.setVisibility(View.GONE);
		} 
		else {
			indexer = new AlphabetIndexer(allRingsCursor, Compatibility
					.getCursorDisplayNameColumnIndex(allRingsCursor), 
					" ABCDEFGHIJKLMNOPQRSTUVWXYZ");
			ringsList.setAdapter(new RingsListAdapter(MainActivity.instance()
					.getAllRings(), allRingsCursor));
		}		
	}
	
	/*
	private void changeContactsToggle() {
		if (onlyDisplayLinphoneContacts) {
			allRings.setEnabled(true);
			linphoneContacts.setEnabled(false);
		} else {
			allContacts.setEnabled(false);
			linphoneContacts.setEnabled(true);
		}
	}
	*/
	
	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
		Contact contact = (Contact) adapter.getItemAtPosition(position);
		if (editOnClick) {
			editConsumed = true;
			/**TODO Implementar edicion **/
			//MainActivity.instance().editContact(contact, sipAddressToAdd);
		} 
		else {
			lastKnownPosition = ringsList.getFirstVisiblePosition();
			MainActivity.instance().displayContact(contact, 
					onlyDisplayChatAddress);
		}
	}
	
	@Override
	public void onResume() {
		instance = this;
		super.onResume();
		
		if (editConsumed) {
			editOnClick = false;
			//sipAddressToAdd = null;
		}
		
		if (MainActivity.isInstanciated()) {
			MainActivity.instance().selectMenu(FragmentsAvailable.RINGS);
			//onlyDisplayLinphoneContacts = MainActivity.instance().isLinphoneContactsPrefered();
			
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
				if (searchField != null && searchField.getText().toString().length() > 0) {
					searchRings(searchField.getText().toString());
				} 
				else {
					changeContactsAdapter();
				}
				ringsList.setSelectionFromTop(lastKnownPosition, 0);
			}
		});
	}
	
	class RingsListAdapter extends BaseAdapter implements SectionIndexer {
		private int margin;
		private Bitmap bitmapUnknown;
		private List<Ring> rings;
		private Cursor cursor;
		
		public RingsListAdapter(List<Ring> ringsList, Cursor c) {
			rings = ringsList;
			cursor = c;
			margin = LinphoneUtils.pixelsToDpi(getResources(), 10);
			bitmapUnknown = BitmapFactory.decodeResource(getResources(), R.drawable.unknown_small);
		}
		
		public int getCount() {
			return cursor.getCount();
		}

		public Object getItem(int position) {
			if (rings == null || position >= rings.size()) {
				return Compatibility.getContact(getActivity().getContentResolver(), cursor, position);
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
