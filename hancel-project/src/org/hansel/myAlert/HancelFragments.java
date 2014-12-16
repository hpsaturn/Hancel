package org.hansel.myAlert;

public enum HancelFragments {
	NONE,
	PANIC,
	TRAKING,
	DIALER,
	DIALER_HISTORY,
	HISTORY_DETAIL,
	RING,
	CONTACTS,
	CONTACT_INFO,
	EDIT_CONTACT,
	ACCOUNT_SETTINGS,
	SETTINGS,
	ABOUT,
	ABOUT_SETTINGS,
	ABOUT_CHAT,		
	CHAT,
	CHATLIST;
	
	public boolean shouldAddToBackStack() {
		return true;
	}

	public boolean shouldAnimate() {
		return true;
	}
/*
	public boolean isRightOf(FragmentsAvailable fragment) {
		switch (this) {
		case HISTORY:
			return fragment == UNKNOW;

		case HISTORY_DETAIL:
			return HISTORY.isRightOf(fragment) || fragment == HISTORY;
			
		case CONTACTS:
			return HISTORY_DETAIL.isRightOf(fragment) || fragment == HISTORY_DETAIL;
			
		case CONTACT:
			return CONTACTS.isRightOf(fragment) || fragment == CONTACTS;
			
		case EDIT_CONTACT:
			return CONTACT.isRightOf(fragment) || fragment == CONTACT;
			
		case DIALER:
			return EDIT_CONTACT.isRightOf(fragment) || fragment == EDIT_CONTACT;
			
		case ABOUT_INSTEAD_OF_CHAT:
		case CHATLIST:
			return DIALER.isRightOf(fragment) || fragment == DIALER;
			
		case CHAT:
			return CHATLIST.isRightOf(fragment) || fragment == CHATLIST;
			
		case ABOUT_INSTEAD_OF_SETTINGS:
		case SETTINGS:
			return CHATLIST.isRightOf(fragment) || fragment == CHATLIST || fragment == FragmentsAvailable.ABOUT_INSTEAD_OF_CHAT;
		
		case ABOUT:
		case ACCOUNT_SETTINGS:
			return SETTINGS.isRightOf(fragment) || fragment == SETTINGS;
			
		default:
			return false;
		}
	}


	public boolean shouldAddItselfToTheRightOf(FragmentsAvailable fragment) {
		switch (this) {
		case HISTORY_DETAIL:
			return fragment == HISTORY;
			
		case CONTACT:
			return fragment == CONTACTS;
			
		case EDIT_CONTACT:
			return fragment == CONTACT || fragment == CONTACTS;
			
		case CHAT:
			return fragment == CHATLIST;
			
		default:
			return false;
		}
	}*/
}