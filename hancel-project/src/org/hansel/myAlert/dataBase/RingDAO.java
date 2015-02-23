package org.hansel.myAlert.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class RingDAO extends SQLiteHelper {

	public RingDAO(Context ctx) {
		super(ctx);		
	}

	public Cursor getRingsByContactCursor(String idContact) {
		String query = "select * from " + DBConstants.TABLE_RINGS
				+" where " + DBConstants.TABLE_CONTACS_RINGS + "._ID_CONTACT = '"
				+ idContact + "' AND " + DBConstants.TABLE_CONTACS_RINGS + 
				"._ID_RING = " + DBConstants.TABLE_RINGS + "._ID";
		Cursor c = super.mDb.rawQuery(query, null);				
		return c;
	}
	
	public Cursor getRingsByNameCursor(String name) {
		String query = "select * from " + DBConstants.TABLE_RINGS
				+" where NAME like %" + name +"%";
		Cursor c = super.mDb.rawQuery(query, null);				
		return c;
	}
	
	public Cursor getRigsCursor(){
		Cursor c = super.mDb.rawQuery("select * from " + DBConstants.TABLE_RINGS, 
				null);
		return c;
	}
	
	public long addContactToRing(String idContact, String idRing) {		
		ContentValues newValues = new ContentValues();		
		newValues.put("_ID_RING", idRing);
		newValues.put("_ID_CONTACT", idContact);		
		return super.mDb.insert(DBConstants.TABLE_CONTACS_RINGS, null, 
				newValues);
	}	
	
	public boolean deleteContactFromRing(String idContact, String idRing){	
		String whereClause = "_ID_RING = ?1 AND _ID_CONTACT = ?2" ;
		String[] whereArgs = new String[2];
		whereArgs[0] = idRing;
		whereArgs[1] = idContact;
		return super.mDb.delete(DBConstants.TABLE_CONTACS_RINGS, whereClause, 
				whereArgs) > 0;
	}
	
	public boolean deleteRing(String idRing){		
		String whereClause = "_ID_RING = ?1";		
		return super.mDb.delete(DBConstants.TABLE_CONTACS_RINGS, whereClause, 
				new String[] {idRing}) > 0;
	}

}
