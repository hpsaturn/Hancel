package org.hansel.myAlert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hansel.myAlert.MainActivity;
import org.hansel.myAlert.R;
import org.hansel.myAlert.Utils.ContactRing;
import org.hansel.myAlert.dataBase.RingDAO;
import org.linphone.Contact;
import org.linphone.LinphoneManager;
import org.linphone.LinphoneService;
import org.linphone.LinphoneUtils;
import org.linphone.compatibility.Compatibility;
import org.linphone.core.LinphoneFriend;
import org.linphone.core.PresenceActivityType;
import org.linphone.mediastream.Log;
import org.linphone.mediastream.Version;
import org.linphone.ui.AvatarWithShadow;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.RawContacts;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AlphabetIndexer;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class EditRingFragment extends Fragment {
	private View view;
	private TextView ok, deleteContacts, addContacts, deleteRing;
	private EditText ringName;
	private CheckBox ringDefault;
	private LayoutInflater inflater;
	private ListView contactsList;
	private ImageView clearSearchField;
	private EditText searchField;
	private boolean isNewRing = true;
	private Ring ring;
	private long ringID;
	private RingDAO ringDao;
	private Cursor searchCursor;
	private AlphabetIndexer indexer;
	private List<String> idContacts;

	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.inflater = inflater;		
		ring = null;
		idContacts = new ArrayList<String>();
		
		if (getArguments() != null) {
			if (getArguments().getSerializable("Ring") != null) {
				ring = (Ring) getArguments().getSerializable("Ring");
				isNewRing = false;
				ringID = Long.parseLong(ring.getId());
				
				RingDAO ringDao = new RingDAO(LinphoneService.instance()
						.getApplicationContext());
				
				ringDao.open();
				Cursor c = ringDao.getAllContactsRing(ring.getId());
				
				if(c != null && c.getCount() > 0){
					c.moveToFirst();
					do{
						idContacts.add(c.getString(0));
						c.moveToNext();
					}while(!c.isLast());
				}
								
				Log.d("=== Cantidad de contactos en el anillo: " + idContacts.size());
				ringDao.close();
			}			
		}
		
		view = inflater.inflate(R.layout.edit_ring, container, false);
		
		TextView cancel = (TextView) view.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getFragmentManager().popBackStackImmediate();
			}
		});
		
		ringName = (EditText) view.findViewById(R.id.ringName);
		ringDefault = (CheckBox) view.findViewById(R.id.ringDefault);
		
		contactsList = (ListView) view.findViewById(R.id.ringContactsList);        
                              		
		ok = (TextView) view.findViewById(R.id.ok);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isNewRing) {
					boolean areAllFielsEmpty = true;
					if (ringName != null && ringDefault !=null && !ringName.equals("")) {
						areAllFielsEmpty = false;
					}
					if (areAllFielsEmpty) {
						getFragmentManager().popBackStackImmediate();
						return;					
					}
					createRing();
				} 
				else 
					updateRing();
									
		        //MainActivity.instance().prepareRingsInBackground();		        		       
				getFragmentManager().popBackStackImmediate();
			}
		});
				
		// Hack to display keyboard when touching focused edittext on Nexus One
		if (Version.sdkStrictlyBelow(Version.API11_HONEYCOMB_30)) {
			ringName.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					InputMethodManager imm = (InputMethodManager) MainActivity.instance().getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
				}
			});
		}
		
		ringName.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, 
					int count) {
				if (ringName.getText().length() > 0 || ringName.getText()
						.length() > 0) {
					ok.setEnabled(true);
				} 
				else {
					ok.setEnabled(false);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, 
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
			
		if (!isNewRing) {
			String rn = ring.getName();			
			Long rd = ring.getAlways();
			
			if (rn != null) 
				ringName.setText(rn);
			
			if (rd != null && rd == 1)
				ringDefault.setChecked(true);
			else 
				ringDefault.setChecked(false);
	
			deleteContacts = (TextView) view.findViewById(R.id.deleteContact);
			deleteContacts.setVisibility(View.VISIBLE);
			deleteContacts.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					Log.d("=== Eliminar contactos seleccionados");
				}
			} );
			addContacts = (TextView) view.findViewById(R.id.newContact);
			addContacts.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					Log.d("=== Adicionar contactos seleccionados");
				}
			} );
			deleteRing = (TextView) view.findViewById(R.id.deleteRing);
			deleteRing.setVisibility(View.VISIBLE);
			deleteRing.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					Log.d("=== Eliminando anillo");
				}
			} );
		}
		else{
			clearSearchField = (ImageView) view.findViewById(R.id.clearSearchField);
			//clearSearchField.setOnClickListener(this);
			clearSearchField.setVisibility(View.VISIBLE);
			searchField = (EditText) view.findViewById(R.id.searchField);
			searchField.setVisibility(View.VISIBLE);
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
					searchContacts(searchField.getText().toString());
				}
			});
		}
		
		searchContacts("");
		
		contactsList.setOnItemClickListener(new OnItemClickListener() {
			   public void onItemClick(AdapterView<?> adapter, View view,
					     int position, long id) {
				   Log.d("=== Selecciono/deselecciono Contacto");
				   ContactRing contactRing = (ContactRing) adapter.getItemAtPosition(position);		
					if(contactRing.isSelected())
						idContacts.add(contactRing.getContactId());
					else
						idContacts.remove(contactRing.getContactId());			
				}
			 });
		
		ringName.requestFocus();
		
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		if (MainActivity.isInstanciated()) {
			if (getResources().getBoolean(R.bool.show_statusbar_only_on_dialer)) {
				MainActivity.instance().hideStatusBar();
			}
		}
	}

	
	private void createRing() {
        ringID = 0;
        ringDao = new RingDAO(LinphoneService.instance()
        		.getApplicationContext());  
        ringDao.open();
        ringID = ringDao.addRing(ringName.getText().toString(),ringDefault
        		.isChecked());
        Iterator<String> it = idContacts.iterator();        
        while( it.hasNext() ){        	
        	ringDao.addContactToRing(it.next(),String.valueOf(ringID));
        }
        ringDao.close();
	}
	
	private void updateRing() {
		if (ringName.getText().length() > 0) {
			ringDao = new RingDAO(LinphoneManager.getInstance()
					.getContext());
			ringDao.open();
			ringDao.updateRing(String.valueOf(ring.getId()), ringName.getText()
					.toString(), ringDefault.isChecked());
			
			ringDao.close();
		}
			
	}
	
	private void deleteRing() {
		/*String select = ContactsContract.Data.CONTACT_ID + "=?"; 
		String[] args = new String[] { String.valueOf(contactID) };   
		
        ops.add(ContentProviderOperation.newDelete(ContactsContract.Data.CONTENT_URI) 
    		.withSelection(select, args) 
            .build()
        );
        
        try {
            getActivity().getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {
        	e.printStackTrace();
        }*/
	}

	
	
	private void searchContacts(String search) {
		if(searchCursor != null)
			searchCursor.close();
			
		List<ContactRing> contactsRing = new ArrayList<ContactRing>();
		
		if(isNewRing){
			searchCursor = Compatibility.getContactsCursor(getActivity()
					.getContentResolver(), search);			
		}
		else{
			String in = "";
			if(idContacts.size() > 1){	
				Iterator<String> it = idContacts.iterator();
				while(it.hasNext()){
					in += it.next() + ",";
				}			
				in = in.substring(0,in.length()-1);				
			}
			searchCursor = Compatibility.getContactsInCursor(getActivity()
					.getContentResolver(), in);
		}
		
		indexer = new AlphabetIndexer(searchCursor, Compatibility
				.getCursorDisplayNameColumnIndex(searchCursor), 
				" ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		ringDao = new RingDAO(LinphoneManager.getInstance()
					.getContext());
		ringDao.open();		
		searchCursor.moveToFirst();		
			
		for(int i= 0; i<searchCursor.getCount(); i++){
			Contact c = Compatibility.getContact(getActivity()
				.getContentResolver(), searchCursor, i);
			contactsRing.add(new ContactRing(c.getID(), c.getName(), 
					c.getPhotoUri(), c.getPhoto(), !isNewRing));				
		}
		ringDao.close();		
		contactsList.setAdapter(new RingsContactsListAdapter(contactsRing));
	}
	
	
	class RingsContactsListAdapter extends BaseAdapter implements SectionIndexer {
		private int margin;
		private Bitmap bitmapUnknown;
		private List<ContactRing> contactsRing;
		//private Cursor cursor;
		
		RingsContactsListAdapter(List<ContactRing> contacts) {
			this.contactsRing = contacts;				
			margin = LinphoneUtils.pixelsToDpi(getResources(), 10);
			bitmapUnknown = BitmapFactory.decodeResource(getResources(), R.drawable.unknown_small);
		}
		
		public int getCount() {
			//return cursor.getCount();
			if(contactsRing == null)
				return 0;
			return contactsRing.size();
		}

		public Object getItem(int position) {
			if (contactsRing == null || position >= contactsRing.size()) {
				//return Compatibility.getContact(getActivity().getContentResolver(), cursor, position);
				return null;
			} 
			else {
				return contactsRing.get(position);
			}
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
						
			if (convertView != null) {
				holder = (ViewHolder) convertView.getTag();
			} 
			else {
				convertView= inflater.inflate(R.layout.contact_ring_cell, parent, false);					
				holder = new ViewHolder();
				holder.contactName = (TextView) convertView.findViewById(R.id.name);
				holder.contactId = (TextView) convertView.findViewById(R.id.contactId);
				holder.selected = (CheckBox) convertView.findViewById(R.id.chooseFriend);
				holder.icon = (ImageView) convertView.findViewById(R.id.icon);
				convertView.setTag(holder);
			   
				holder.selected.setOnClickListener(new View.OnClickListener() { 
					public void onClick(View v) {
						Log.d("=== Seleccionado/Deseleccionado Objeto");
						CheckBox cb = (CheckBox) v ; 
						ContactRing cr = (ContactRing)cb.getTag(); 
						cr.setSelected(cb.isChecked());
				        if(cb.isChecked())
				        	idContacts.add(cr.getContactId());
				        else
				        	idContacts.remove(cr.getContactId());				        
					}    
				});
			}
			
			ContactRing cr = contactsRing.get(position);			
			
			holder.contactId.setText(cr.getContactId());
			holder.contactName.setText(cr.getContactName());
			holder.selected.setChecked(cr.isSelected());
			holder.selected.setTag(cr);
			holder.icon.setImageBitmap(bitmapUnknown);
			 
			if (cr.getPhoto() != null) {
				holder.icon.setImageBitmap(cr.getPhoto());
			} 
			else if (cr.getPhotoUri() != null) {
				holder.icon.setImageURI(cr.getPhotoUri());
			} 
			else {
				holder.icon.setImageBitmap(bitmapUnknown);
			}
			
			TextView separator = (TextView) convertView.findViewById(R.id.separator);
			LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.layout);
						
			if (getPositionForSection(getSectionForPosition(position)) != position) {
				separator.setVisibility(View.GONE);
				layout.setPadding(0, margin, 0, margin);
			} 
			else {
				separator.setVisibility(View.VISIBLE);
				separator.setText(String.valueOf(cr.getContactName().charAt(0)));
				layout.setPadding(0, 0, 0, margin);
			}
			
			return convertView;
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
		
		private class ViewHolder {			   
			   TextView contactId;
			   TextView contactName;
			   CheckBox selected;
			   ImageView icon;
		}
	}
		
}
