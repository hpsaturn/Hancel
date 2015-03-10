package org.hansel.myAlert.dataBase;
import java.util.Iterator;
import java.util.List;

import org.linphone.mediastream.Log;

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
	
	public Cursor getAllContactsRing(String idRing){
		String query = "select _id_contact from " + DBConstants.TABLE_CONTACS_RINGS
				+" where _ID_RING = " + idRing;
		Cursor c = super.mDb.rawQuery(query, null);
		return c;
		
	}
	
	public Cursor getRingsByNameCursor(String name) {
		String query = "select * from " + DBConstants.TABLE_RINGS
				+" where NAME like '%" + name +"%'";
		Cursor c = super.mDb.rawQuery(query, null);
		return c;
	}
	
	public Cursor getRigsCursor(){
		Cursor c = super.mDb.rawQuery("select * from " + DBConstants.TABLE_RINGS,
				null);
		return c;
	}
	
	public Cursor getRing(String id){
		Cursor c = super.mDb.rawQuery("select * from " + DBConstants.TABLE_RINGS 
				+ " where _id = ?1", new String[]{id});
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
	
	public long addRing(String name, boolean always){
		int alwaysNotify = always==true?1:0;
		ContentValues newValues = new ContentValues();
		newValues.put("name", name);
		newValues.put("notify", String.valueOf(alwaysNotify));		
		return super.mDb.insert(DBConstants.TABLE_RINGS, null, newValues);		
	}
	
	public long updateRing(String id, String name, boolean always, List<String> contacts){
		int result = -1;
		ContentValues values = new ContentValues();
		values.put("NAME", name);
		values.put("NOTIFY", always==true?1:0);
		
		String whereClause = "_ID = " + id;
		super.mDb.beginTransaction();
		try{
			result = super.mDb.update(DBConstants.TABLE_RINGS, values, whereClause, 
				null);
			
			whereClause = "_ID_RING = " + id;
			super.mDb.delete(DBConstants.TABLE_CONTACS_RINGS, whereClause, null);
		
			ContentValues insertValues = new ContentValues();
			insertValues.put("_ID_RING", id);
			Iterator <String> it = contacts.iterator();
		
			while(it.hasNext()){
				insertValues.put("_ID_CONTACT", it.next());
				super.mDb.insert(DBConstants.TABLE_CONTACS_RINGS, null, insertValues);
			}
			Log.d("=== Poniendo transaccion exitosa");
			mDb.setTransactionSuccessful();
		}
		finally {
			mDb.endTransaction();			
		}
		return result;
	}
	
	public boolean deleteRing(String idRing){
		String whereClause = "_ID_RING = ?1";
		return super.mDb.delete(DBConstants.TABLE_CONTACS_RINGS, whereClause,
				new String[] {idRing}) > 0;
	}
}