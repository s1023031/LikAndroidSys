package com.lik.android.om;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lik.android.main.LikDBAdapter;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class Users extends BaseUsers implements ProcessDownloadInterface {

	private static final long serialVersionUID = -6909984978774848405L;
	
	@Override
	public Users doInsert(LikDBAdapter adapter) {
		InsertHelper ih = adapter.getInsertHelper(Users.TABLE_NAME);
		ih.prepareForInsert();
		ih.bind(2, getUSER_NO());
		ih.bind(3, getUSER_NAME());
		ih.bind(4, getLOOK_MAPTRACK());
		ih.bind(5, getBOSS_USERNO());
	    long rid = ih.execute();
	    if(rid != -1) setRid(0);
		return this;
	}

	@Override
	public Users doUpdate(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues updateValues = new ContentValues();
        updateValues.put(COLUMN_NAME_USER_NO, getUSER_NO());
        updateValues.put(COLUMN_NAME_USER_NAME,getUSER_NAME());
        updateValues.put(COLUMN_NAME_LOOK_MAPTRACK,getLOOK_MAPTRACK());
        updateValues.put(COLUMN_NAME_BOSS_USERNO,getBOSS_USERNO());
        String[] whereArgs = {String.valueOf(getSerialID())};
        long rid = db.update(TABLE_NAME, updateValues, COLUMN_NAME_SERIALID+"=?", whereArgs);
        setRid(rid);
        if(rid==0) setRid(-1); // delete�ɡA�^�ЭY��0��ܨS���R���@����ơA�]���]-1��ܥ���
        closedb(adapter);
		return this;		
	}

	@Override
	public Users doDelete(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		String whereClause = COLUMN_NAME_SERIALID+"="+getSerialID();
		if(isDebug) Log.d(TAG,whereClause);
		int rid = db.delete(TABLE_NAME, whereClause, null);
		if(rid==0) setRid(-1); // delete�ɡA�^�ЭY��0��ܨS���R���@����ơA�]���]-1��ܥ���
        closedb(adapter);
		return this;
	}

	@Override
	public Users findByKey(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_USER_NO+"='"+getUSER_NO()+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_USERS_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
        if (c != null) {
        	boolean notEmpty = c.moveToFirst();
        	if(notEmpty) {
        		setSerialID(c.getInt(READ_USERS_SERIALID_INDEX));
        		setUSER_NO(c.getString(READ_USERS_USER_NO_INDEX));
        		setUSER_NAME(c.getString(READ_USERS_USER_NAME_INDEX));
        		setLOOK_MAPTRACK(c.getString(READ_USERS_LOOK_MAPTRACK_INDEX));
        		setBOSS_USERNO(c.getString(READ_USERS_BOSS_USERNO_INDEX));
        		setRid(0);
        	} else setRid(-1);
        }
        c.close();
        closedb(adapter);
		return this;
	}

	@Override
	public Users queryBySerialID(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_SERIALID+"="+getSerialID());
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_USERS_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
        if (c != null) {
        	boolean notEmpty = c.moveToFirst();
        	if(notEmpty) {
        		setSerialID(c.getInt(READ_USERS_SERIALID_INDEX));
        		setUSER_NO(c.getString(READ_USERS_USER_NO_INDEX));
        		setUSER_NAME(c.getString(READ_USERS_USER_NAME_INDEX));
        		setLOOK_MAPTRACK(c.getString(READ_USERS_LOOK_MAPTRACK_INDEX));
        		setBOSS_USERNO(c.getString(READ_USERS_BOSS_USERNO_INDEX));
        		setRid(0);
        	} else setRid(-1);
        }
        c.close();
        closedb(adapter);
		return this;
	}
	
	@Override
	public boolean processDownload(LikDBAdapter adapter,
			Map<String, String> detail, boolean isOnlyInsert) {
		boolean result = true;
		String flag = detail.get(FLAG_KEY)==null?"N":detail.get(FLAG_KEY);
		setUSER_NO(detail.get(COLUMN_NAME_USER_NO));
		if(!isOnlyInsert) findByKey(adapter);
		setUSER_NAME(detail.get(COLUMN_NAME_USER_NAME));		
		setLOOK_MAPTRACK(detail.get(COLUMN_NAME_LOOK_MAPTRACK));		
		setBOSS_USERNO(detail.get(COLUMN_NAME_BOSS_USERNO));		
		if(isOnlyInsert) doInsert(adapter);
		else {
			if(getRid()<0) doInsert(adapter);
			else {
				if(flag.equals(FLAG_DELETE)) doDelete(adapter);
				else doUpdate(adapter);
			}
		}
		if(getRid()<0) result = false;
		return result;
	}
	
	public List<Users> getAllUsers(LikDBAdapter adapter) {
		List<Users> result = new ArrayList<Users>();
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_USERS_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				COLUMN_NAME_USER_NO        // The sort order
				);
		if(c != null && c.getCount()>0) {
			c.moveToFirst();
            do {
            	Users om = new Users();
            	om.setSerialID(c.getInt(READ_USERS_SERIALID_INDEX));
            	om.setUSER_NO(c.getString(READ_USERS_USER_NO_INDEX));
            	om.setUSER_NAME(c.getString(READ_USERS_USER_NAME_INDEX));
            	om.setLOOK_MAPTRACK(c.getString(READ_USERS_LOOK_MAPTRACK_INDEX));
            	om.setBOSS_USERNO(c.getString(READ_USERS_BOSS_USERNO_INDEX));
            	om.setRid(0);
            	result.add(om);
            } while(c.moveToNext());
		} 		
        closedb(adapter);
		return result;
	}
	
	public boolean isBossOf(String userNo, LikDBAdapter adapter) {
		boolean result = false;
		String boss = getUSER_NO();
//		if(boss.equals(userNo)) return true; // �ۤv�i�ݦۤv
		Users omU = new Users();
		omU.setUSER_NO(userNo);
		omU.findByKey(adapter);
		StringBuffer sb = new StringBuffer();
		sb.append(userNo);
		while(omU.getBOSS_USERNO()!=null && omU.getRid()>=0) {
			if(omU.getBOSS_USERNO().equals(boss)) {
				result = true;
				break;
			}
			if(sb.indexOf(omU.getBOSS_USERNO())>=0) {
				result = false;
				break;
			}
			sb.append(",").append(omU.getBOSS_USERNO());
//			if(omU.getBOSS_USERNO().equals(omU.getUSER_NO())) {
//				result = false;
//				break;
//			}
			omU.setUSER_NO(omU.getBOSS_USERNO());
			omU.findByKey(adapter);
		}
		return result;
	}
}
