package org.hansel.myAlert;

import java.util.ArrayList;
import java.util.List;

import org.hansel.myAlert.MainActivity;
import org.hansel.myAlert.R;
import org.hansel.myAlert.dataBase.RingDAO;
import org.linphone.Contact;
import org.linphone.LinphoneManager;
import org.linphone.LinphoneService;
import org.linphone.LinphoneUtils;
import org.linphone.compatibility.Compatibility;
import org.linphone.core.LinphoneFriend;
import org.linphone.core.PresenceActivityType;
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

public class EditRingFragment extends Fragment implements OnClickListener, OnItemClickListener{
	private View view;
	private TextView ok;
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
	private ArrayList<ContentProviderOperation> ops;

	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.inflater = inflater;		
		ring = null;
		
		if (getArguments() != null) {
			if (getArguments().getSerializable("Ring") != null) {
				ring = (Ring) getArguments().getSerializable("Ring");
				isNewRing = false;
				ringID = Long.parseLong(ring.getId());
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
        contactsList.setOnItemClickListener(this);
                              
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
				searchContacts(searchField.getText().toString());
			}
		});

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
									
		        MainActivity.instance().prepareRingsInBackground();		        		       
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
		}
		
		AvatarWithShadow ringPicture = (AvatarWithShadow) view.findViewById(R.id.ringPicture);
		ringPicture.setImageResource(R.drawable.unknown_small);

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
	
	/*private void initNumbersFields(final TableLayout controls, final Contact contact) {
		controls.removeAllViews();
		numbersAndAddresses = new ArrayList<NewOrUpdatedNumberOrAddress>();
		
		if (contact != null) {
			for (String numberOrAddress : contact.getNumerosOrAddresses()) {
				View view = displayNumberOrAddress(controls, numberOrAddress);
				if (view != null)
					controls.addView(view);
			}
		}
		if (newSipOrNumberToAdd != null) {
			View view = displayNumberOrAddress(controls, newSipOrNumberToAdd);
			if (view != null)
				controls.addView(view);
		}
		
		if (!isNewRing) {
			deleteContact = inflater.inflate(R.layout.contact_delete_button, null);
			deleteContact.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					deleteExistingContact();
					MainActivity.instance().removeContactFromLists(contact);
					MainActivity.instance().displayContacts(false);
				}
			});
			controls.addView(deleteContact, controls.getChildCount());
		}

		// Add one for phone numbers, one for SIP address
		if (!getResources().getBoolean(R.bool.hide_phone_numbers_in_editor)) {
			addEmptyRowToAllowNewNumberOrAddress(controls, false);
		}
		
		if (!getResources().getBoolean(R.bool.hide_sip_addresses_in_editor)) {
			firstSipAddressIndex = controls.getChildCount() - 2; // Update the value to always display phone numbers before SIP accounts
			addEmptyRowToAllowNewNumberOrAddress(controls, true);
		}
	} */
	
	/*
	private View displayNumberOrAddress(final TableLayout controls, String numberOrAddress) {
		return displayNumberOrAddress(controls, numberOrAddress, false);
	}*/
	
	/*
	private View displayNumberOrAddress(final TableLayout controls, String numberOrAddress, boolean forceAddNumber) {
		boolean isSip = LinphoneUtils.isStrictSipAddress(numberOrAddress) || !LinphoneUtils.isNumberAddress(numberOrAddress);
		
		if (isSip) {
			if (firstSipAddressIndex == -1) {
				firstSipAddressIndex = controls.getChildCount();
			}
			numberOrAddress = numberOrAddress.replace("sip:", "");
		}
		if ((getResources().getBoolean(R.bool.hide_phone_numbers_in_editor) && !isSip) || (getResources().getBoolean(R.bool.hide_sip_addresses_in_editor) && isSip)) {
			if (forceAddNumber)
				isSip = !isSip; // If number can't be displayed because we hide a sort of number, change that category
			else
				return null;
		}
		
		NewOrUpdatedNumberOrAddress tempNounoa;
		if (forceAddNumber) {
			tempNounoa = new NewOrUpdatedNumberOrAddress(isSip);
		} else {
			if(isNewRing) {
				tempNounoa = new NewOrUpdatedNumberOrAddress(isSip, numberOrAddress);
			} else {
				tempNounoa = new NewOrUpdatedNumberOrAddress(numberOrAddress, isSip);
			}
		}
		final NewOrUpdatedNumberOrAddress nounoa = tempNounoa;
		numbersAndAddresses.add(nounoa);
		
		final View view = inflater.inflate(R.layout.contact_edit_row, null);
		
		final EditText noa = (EditText) view.findViewById(R.id.numoraddr);
		noa.setInputType(isSip ? InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS : InputType.TYPE_CLASS_PHONE);
		noa.setText(numberOrAddress);
		noa.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				nounoa.setNewNumberOrAddress(noa.getText().toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		if (forceAddNumber) {
			nounoa.setNewNumberOrAddress(noa.getText().toString());
		}
		
		ImageView delete = (ImageView) view.findViewById(R.id.delete);
		delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				nounoa.delete();
				numbersAndAddresses.remove(nounoa);
				view.setVisibility(View.GONE);
			}
		});
		return view;
	} */
	
	/*
	private void addEmptyRowToAllowNewNumberOrAddress(final TableLayout controls, final boolean isSip) {
		final View view = inflater.inflate(R.layout.contact_add_row, null);
		
		final NewOrUpdatedNumberOrAddress nounoa = new NewOrUpdatedNumberOrAddress(isSip);
		
		final EditText noa = (EditText) view.findViewById(R.id.numoraddr);
		numbersAndAddresses.add(nounoa);
		noa.setHint(isSip ? getString(R.string.sip_address) : getString(R.string.phone_number));
		noa.setInputType(isSip ? InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS : InputType.TYPE_CLASS_PHONE);
		noa.requestFocus();
		noa.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				nounoa.setNewNumberOrAddress(noa.getText().toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		
		final ImageView add = (ImageView) view.findViewById(R.id.add);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Add a line, and change add button for a delete button
				add.setImageResource(R.drawable.list_delete);
				ImageView delete = add;
				delete.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						nounoa.delete();
						numbersAndAddresses.remove(nounoa);
						view.setVisibility(View.GONE);
					}
				});
				if (!isSip) {
					firstSipAddressIndex++;
					addEmptyRowToAllowNewNumberOrAddress(controls, false);
				} else {
					addEmptyRowToAllowNewNumberOrAddress(controls, true);
				}
			}
		});
		
		if (isSip) {
			controls.addView(view, controls.getChildCount());
		} else {
			if (firstSipAddressIndex != -1) {
				controls.addView(view, firstSipAddressIndex);
			} else {
				controls.addView(view);
			}
		}
		if (deleteContact != null) {
			// Move to the bottom the remove contact button
			controls.removeView(deleteContact);
			controls.addView(deleteContact, controls.getChildCount());
		}
	}
	*/
	private void createRing() {
        ringID = 0;
        ringDao = new RingDAO(LinphoneService.instance()
        		.getApplicationContext());  
        ringDao.open();
        ringID = ringDao.addRing(ringName.getText().toString(),ringDefault
        		.isChecked());       
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	
	private void searchContacts(String search) {
		if(searchCursor != null)
			searchCursor.close();
	
		if (search == null || search.length() == 0) 		
			searchCursor = MainActivity.instance().getAllContactsCursor();			
		
		else{
			searchCursor = Compatibility.getContactsCursor(getActivity().getContentResolver(), search);
			indexer = new AlphabetIndexer(searchCursor, Compatibility.getCursorDisplayNameColumnIndex(searchCursor), " ABCDEFGHIJKLMNOPQRSTUVWXYZ");			
		}
		
		contactsList.setAdapter(new RingsContactsListAdapter(null, searchCursor));
	}
	
	/*private String getDisplayName() {
		String displayName = null;
		if (firstName.getText().length() > 0 && lastName.getText().length() > 0)
			displayName = firstName.getText().toString() + " " + lastName.getText().toString();
		else if (firstName.getText().length() > 0)
			displayName = firstName.getText().toString();
		else if (lastName.getText().length() > 0)
			displayName = lastName.getText().toString();
		return displayName;
	}*/
	
	/*private String findRawContactID(String contactID) {
		Cursor c = getActivity().getContentResolver().query(RawContacts.CONTENT_URI,
		          new String[]{RawContacts._ID},
		          RawContacts.CONTACT_ID + "=?",
		          new String[]{contactID}, null);
		if (c != null) {
			String result = null;
			if (c.moveToFirst()) {
				result = c.getString(c.getColumnIndex(RawContacts._ID));
			}
			c.close();
			return result;
		}
		return null;
	}*/
	
	/*private String findContactFirstName(String contactID) {
		Cursor c = getActivity().getContentResolver().query(ContactsContract.Data.CONTENT_URI,
		          new String[]{ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME},
		          ContactsContract.Data.CONTACT_ID + "=? AND " + ContactsContract.Data.MIMETYPE + "=?",
		          new String[]{contactID, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE}, null);
		if (c != null) {
			String result = null;
			if (c.moveToFirst()) {
				result = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
			}
			c.close();
			return result;
		}
		return null;
	}*/
	
	/*private String findContactLastName(String contactID) {
		Cursor c = getActivity().getContentResolver().query(ContactsContract.Data.CONTENT_URI,
		          new String[]{ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME},
		          ContactsContract.Data.CONTACT_ID + "=? AND " + ContactsContract.Data.MIMETYPE + "=?",
		          new String[]{contactID, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE}, null);
		if (c != null) {
			String result = null;
			if (c.moveToFirst()) {
				result = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
			}
			c.close();
			return result;
		}
		return null;
	}*/
	
	/*
	class NewOrUpdatedNumberOrAddress {
		private String oldNumberOrAddress;
		private String newNumberOrAddress;
		private boolean isSipAddress;
		
		public NewOrUpdatedNumberOrAddress() {
			oldNumberOrAddress = null;
			newNumberOrAddress = null;
			isSipAddress = false;
		}
		
		public NewOrUpdatedNumberOrAddress(boolean isSip) {
			oldNumberOrAddress = null;
			newNumberOrAddress = null;
			isSipAddress = isSip;
		}
		
		public NewOrUpdatedNumberOrAddress(String old, boolean isSip) {
			oldNumberOrAddress = old;
			newNumberOrAddress = null;
			isSipAddress = isSip;
		}
		
		public NewOrUpdatedNumberOrAddress(boolean isSip, String newSip) {
			oldNumberOrAddress = null;
			newNumberOrAddress = newSip;
			isSipAddress = isSip;
		}
		
		public void setNewNumberOrAddress(String newN) {
			newNumberOrAddress = newN;
		}
		
		public void save() {
			if (newNumberOrAddress == null || newNumberOrAddress.equals(oldNumberOrAddress))
				return;

			if (oldNumberOrAddress == null) {
				// New number to add
				addNewNumber();
			} else {
				// Old number to update
				updateNumber();
			}
		}
		
		public void delete() {
			if (isSipAddress) {
				Compatibility.deleteSipAddressFromContact(ops, oldNumberOrAddress, String.valueOf(contactID));
			} else {
				String select = ContactsContract.Data.CONTACT_ID + "=? AND " 
						+ ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE +  "' AND " 
						+ ContactsContract.CommonDataKinds.Phone.NUMBER + "=?"; 
				String[] args = new String[] { String.valueOf(contactID), oldNumberOrAddress };   
				
	            ops.add(ContentProviderOperation.newDelete(ContactsContract.Data.CONTENT_URI) 
	        		.withSelection(select, args) 
	                .build()
	            );
			}
		}
		
		private void addNewNumber() {
			if (newNumberOrAddress == null || newNumberOrAddress.length() == 0) {
				return;
			}
			
			if (isNewRing) {
				if (isSipAddress) {
					if (newNumberOrAddress.startsWith("sip:"))
						newNumberOrAddress = newNumberOrAddress.substring(4);
					if(!newNumberOrAddress.contains("@"))
						newNumberOrAddress = newNumberOrAddress + "@" + getResources().getString(R.string.default_domain);
					Compatibility.addSipAddressToContact(getActivity(), ops, newNumberOrAddress);
				} else {
					ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)        
				        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
				        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, newNumberOrAddress)
				        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,  ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM)
				        .withValue(ContactsContract.CommonDataKinds.Phone.LABEL, getString(R.string.addressbook_label))
				        .build()
				    );
				}
			} else {
				String rawContactId = findRawContactID(String.valueOf(contactID));
				
				if (isSipAddress) {
					if (newNumberOrAddress.startsWith("sip:"))
						newNumberOrAddress = newNumberOrAddress.substring(4);
					if(!newNumberOrAddress.contains("@"))
						newNumberOrAddress = newNumberOrAddress + "@" + getResources().getString(R.string.default_domain);
					Compatibility.addSipAddressToContact(getActivity(), ops, newNumberOrAddress, rawContactId);	
				} else {
					ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)         
					    .withValue(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)       
				        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
				        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, newNumberOrAddress)
				        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,  ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM)
				        .withValue(ContactsContract.CommonDataKinds.Phone.LABEL, getString(R.string.addressbook_label))
				        .build()
				    );
				}
			}
		}
		
		private void updateNumber() {
			if (newNumberOrAddress == null || newNumberOrAddress.length() == 0) {
				return;
			}
			
			if (isSipAddress) {
				if (newNumberOrAddress.startsWith("sip:"))
					newNumberOrAddress = newNumberOrAddress.substring(4);
				if(!newNumberOrAddress.contains("@"))
					newNumberOrAddress = newNumberOrAddress + "@" + getResources().getString(R.string.default_domain);
				Compatibility.updateSipAddressForContact(ops, oldNumberOrAddress, newNumberOrAddress, String.valueOf(contactID));
			} else {
				String select = ContactsContract.Data.CONTACT_ID + "=? AND " 
						+ ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE +  "' AND " 
						+ ContactsContract.CommonDataKinds.Phone.NUMBER + "=?"; 
				String[] args = new String[] { String.valueOf(contactID), oldNumberOrAddress };   
				
	            ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI) 
	        		.withSelection(select, args) 
	                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
	                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, newNumberOrAddress)
	                .build()
	            );
			}
		}
	}*/
	
	class RingsContactsListAdapter extends BaseAdapter implements SectionIndexer {
		private int margin;
		private Bitmap bitmapUnknown;
		private List<Contact> contacts;
		private Cursor cursor;
		
		RingsContactsListAdapter(List<Contact> contactsList, Cursor c) {
			contacts = contactsList;
			cursor = c;

			margin = LinphoneUtils.pixelsToDpi(getResources(), 10);
			bitmapUnknown = BitmapFactory.decodeResource(getResources(), R.drawable.unknown_small);
		}
		
		public int getCount() {
			return cursor.getCount();
		}

		public Object getItem(int position) {
			if (contacts == null || position >= contacts.size()) {
				return Compatibility.getContact(getActivity().getContentResolver(), cursor, position);
			} else {
				return contacts.get(position);
			}
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			Contact contact = null;
			do {
				contact = (Contact) getItem(position);
			} while (contact == null);
			
			if (convertView != null) {
				view = convertView;
			} 
			else {
				view = inflater.inflate(R.layout.contact_cell, parent, false);
			}
			
			TextView name = (TextView) view.findViewById(R.id.name);
			name.setText(contact.getName());
			
			TextView separator = (TextView) view.findViewById(R.id.separator);
			LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout);
			
			if (getPositionForSection(getSectionForPosition(position)) != position) {
				separator.setVisibility(View.GONE);
				layout.setPadding(0, margin, 0, margin);
			} 
			else {
				separator.setVisibility(View.VISIBLE);
				separator.setText(String.valueOf(contact.getName().charAt(0)));
				layout.setPadding(0, 0, 0, margin);
			}
			
			ImageView icon = (ImageView) view.findViewById(R.id.icon);
			if (contact.getPhoto() != null) {
				icon.setImageBitmap(contact.getPhoto());
			} 
			else if (contact.getPhotoUri() != null) {
				icon.setImageURI(contact.getPhotoUri());
			} 
			else {
				icon.setImageBitmap(bitmapUnknown);
			}
			
			ImageView friendStatus = (ImageView) view.findViewById(R.id.friendStatus);
			LinphoneFriend friend = contact.getFriend();
			if (!MainActivity.instance().isContactPresenceDisabled() && friend != null) {
				friendStatus.setVisibility(View.VISIBLE);
				PresenceActivityType presenceActivity = friend.getPresenceModel().getActivity().getType();
				if (presenceActivity == PresenceActivityType.Online) {
					friendStatus.setImageResource(R.drawable.led_connected);
				} else if (presenceActivity == PresenceActivityType.Busy) {
					friendStatus.setImageResource(R.drawable.led_error);
				} else if (presenceActivity == PresenceActivityType.Away) {
					friendStatus.setImageResource(R.drawable.led_inprogress);
				} else if (presenceActivity == PresenceActivityType.Offline) {
					friendStatus.setImageResource(R.drawable.led_disconnected);
				} else {
					friendStatus.setImageResource(R.drawable.call_quality_indicator_0);
				}
			}
			
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

