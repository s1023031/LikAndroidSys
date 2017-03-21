package com.lik.android.om;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.lik.android.main.LikDBAdapter;

public class InstantMessages extends BaseInstantMessages {

	private static final long serialVersionUID = -4583002289619122199L;

	@Override
	public InstantMessages doInsert(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		Log.d(TAG,"db isOpen="+db.isOpen());
		InsertHelper ih = adapter.getInsertHelper(getTableName());
		ih.prepareForInsert();
		ih.bind(2, getUserNo());
		ih.bind(3, getContent());
		ih.bind(4, sdf.format(getPublishTime()));
		if(getReceiveTime()!=null) ih.bind(5, sdf.format(getReceiveTime()));
		ih.bind(6, getOwner());
		ih.bind(7, getSubject());
		ih.bind(8, isRead()?1:0);
		ih.bind(9, getMessageKey());
	    long rid = ih.execute();
	    if(rid != -1) setRid(0);
		return this;
	}

	public InstantMessages doInsertWithoutTransaction(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues initialValues = new ContentValues();
        initialValues.put(COLUMN_NAME_USERNO, getUserNo());
        initialValues.put(COLUMN_NAME_CONTENT, getContent());
        initialValues.put(COLUMN_NAME_PUBLISHTIME, sdf.format(getPublishTime()));
        if(getReceiveTime()!=null) initialValues.put(COLUMN_NAME_RECEIVETIME, sdf.format(getReceiveTime()));
        initialValues.put(COLUMN_NAME_OWNER, getOwner());
        initialValues.put(COLUMN_NAME_SUBJECT, getSubject());
        initialValues.put(COLUMN_NAME_ISREAD, isRead()?1:0);
        initialValues.put(COLUMN_NAME_MESSAGEKEY, getMessageKey());
        long rid = db.insert(getTableName(), null, initialValues);
        setRid(rid);
        closedb(adapter);
		return this;
	}

	@Override
	public InstantMessages doUpdate(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues updateValues = new ContentValues();
        updateValues.put(COLUMN_NAME_USERNO, getUserNo());
        updateValues.put(COLUMN_NAME_CONTENT, getContent());
        updateValues.put(COLUMN_NAME_PUBLISHTIME, sdf.format(getPublishTime()));
        if(getReceiveTime()!=null) updateValues.put(COLUMN_NAME_RECEIVETIME, sdf.format(getReceiveTime()));
        updateValues.put(COLUMN_NAME_OWNER, getOwner());
        updateValues.put(COLUMN_NAME_SUBJECT, getSubject());
        updateValues.put(COLUMN_NAME_ISREAD, isRead()?1:0);
        updateValues.put(COLUMN_NAME_MESSAGEKEY, getMessageKey());
        String[] whereArgs = {String.valueOf(getSerialID())};
        long rid = db.update(getTableName(), updateValues, COLUMN_NAME_SERIALID+"=?", whereArgs);
        setRid(rid);
        if(rid==0) setRid(-1); // update�ɡA�^�ЭY��0��ܨS����s�@����ơA�]���]-1��ܥ���
        closedb(adapter);
		return this;		
	}

	@Override
	public InstantMessages doDelete(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		String whereClause = COLUMN_NAME_SERIALID+"="+getSerialID();
		if(isDebug) Log.d(TAG,whereClause);
		//String[] whereArgs = {String.valueOf(getSerialID())};
		int rid = db.delete(getTableName(), whereClause, null);
        setRid(rid);
		if(rid==0) setRid(-1); // delete�ɡA�^�ЭY��0��ܨS���R���@����ơA�]���]-1��ܥ���
        closedb(adapter);
		return this;
	}

	@Override
	public InstantMessages findByKey(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_MESSAGEKEY+"='"+getMessageKey()+"'");
		//qb.appendWhere(" and "+COLUMN_NAME_PUBLISHTIME+"='"+sdf.format(getPublishTime())+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_INSTANTMESSAGES_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
		if(c == null) {
			setRid(-1);
			return this;
		} 	
        boolean notEmpty = c.moveToFirst();
        if(notEmpty) {
        	setSerialID(c.getInt(READ_INSTANTMESSAGES_SERIALID_INDEX));
        	setUserNo(c.getString(READ_INSTANTMESSAGES_USERNO_INDEX));
        	setContent(c.getString(READ_INSTANTMESSAGES_CONTENT_INDEX));
            try {
            	if(c.getString(READ_INSTANTMESSAGES_PUBLISHTIME_INDEX) != null) {
            		Date date = sdf.parse(c.getString(READ_INSTANTMESSAGES_PUBLISHTIME_INDEX));
            		setPublishTime(date);
            	}
            } catch(ParseException pe) {
            	setPublishTime(null); // primary key not null
            }
            try {
            	if(c.getString(READ_INSTANTMESSAGES_RECEIVETIME_INDEX) != null) {
            		Date date = sdf.parse(c.getString(READ_INSTANTMESSAGES_RECEIVETIME_INDEX));
            		setReceiveTime(date);
            	}
            } catch(ParseException pe) {
            	setReceiveTime(null); // primary key not null
            }
        	setOwner(c.getString(READ_INSTANTMESSAGES_OWNER_INDEX));
        	setSubject(c.getString(READ_INSTANTMESSAGES_SUBJECT_INDEX));
        	setRead(c.getInt(READ_INSTANTMESSAGES_ISREAD_INDEX)==1?true:false);
        	setMessageKey(c.getString(READ_INSTANTMESSAGES_MESSAGEKEY_INDEX));
        	setRid(0);
        } else setRid(-1);
        c.close();
        closedb(adapter);
		return this;
	}

	@Override
	public InstantMessages queryBySerialID(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_SERIALID+"="+getSerialID());
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_INSTANTMESSAGES_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
		if(c == null) {
			setRid(-1);
			return this;
		} 	
        boolean notEmpty = c.moveToFirst();
        if(notEmpty) {
        	setSerialID(c.getInt(READ_INSTANTMESSAGES_SERIALID_INDEX));
        	setUserNo(c.getString(READ_INSTANTMESSAGES_USERNO_INDEX));
        	setContent(c.getString(READ_INSTANTMESSAGES_CONTENT_INDEX));
            try {
            	if(c.getString(READ_INSTANTMESSAGES_PUBLISHTIME_INDEX) != null) {
            		Date date = sdf.parse(c.getString(READ_INSTANTMESSAGES_PUBLISHTIME_INDEX));
            		setPublishTime(date);
            	}
            } catch(ParseException pe) {
            	setPublishTime(null); // primary key not null
            }
            try {
            	if(c.getString(READ_INSTANTMESSAGES_RECEIVETIME_INDEX) != null) {
            		Date date = sdf.parse(c.getString(READ_INSTANTMESSAGES_RECEIVETIME_INDEX));
            		setReceiveTime(date);
            	}
            } catch(ParseException pe) {
            	setReceiveTime(null); // primary key not null
            }
        	setOwner(c.getString(READ_INSTANTMESSAGES_OWNER_INDEX));
        	setSubject(c.getString(READ_INSTANTMESSAGES_SUBJECT_INDEX));
        	setRead(c.getInt(READ_INSTANTMESSAGES_ISREAD_INDEX)==1?true:false);
        	setMessageKey(c.getString(READ_INSTANTMESSAGES_MESSAGEKEY_INDEX));
        	setRid(0);
        } else setRid(-1);
        c.close();
        closedb(adapter);
		return this;
	}

	public List<InstantMessages> getMessages(LikDBAdapter adapter) {
		List<InstantMessages> result = new ArrayList<InstantMessages>();
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_USERNO+"='"+getUserNo()+"'");
		if(isRead()) qb.appendWhere(" and "+COLUMN_NAME_ISREAD+"=1");
		else qb.appendWhere(" and "+COLUMN_NAME_ISREAD+"<>1");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_INSTANTMESSAGES_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				COLUMN_NAME_PUBLISHTIME        // The sort order
				);
		if(c != null && c.moveToFirst()) {
            do {
            	InstantMessages om = new InstantMessages();
            	om.setSerialID(c.getInt(READ_INSTANTMESSAGES_SERIALID_INDEX));
            	om.setUserNo(c.getString(READ_INSTANTMESSAGES_USERNO_INDEX));
            	om.setContent(c.getString(READ_INSTANTMESSAGES_CONTENT_INDEX));
                try {
                	if(c.getString(READ_INSTANTMESSAGES_PUBLISHTIME_INDEX) != null) {
                		Date date = sdf.parse(c.getString(READ_INSTANTMESSAGES_PUBLISHTIME_INDEX));
                		om.setPublishTime(date);
                	}
                } catch(ParseException pe) {
                	om.setPublishTime(null); // primary key not null
                }
                try {
                	if(c.getString(READ_INSTANTMESSAGES_RECEIVETIME_INDEX) != null) {
                		Date date = sdf.parse(c.getString(READ_INSTANTMESSAGES_RECEIVETIME_INDEX));
                		om.setReceiveTime(date);
                	}
                } catch(ParseException pe) {
                	om.setReceiveTime(null); // primary key not null
                }
            	om.setOwner(c.getString(READ_INSTANTMESSAGES_OWNER_INDEX));
            	om.setSubject(c.getString(READ_INSTANTMESSAGES_SUBJECT_INDEX));
            	om.setRead(c.getInt(READ_INSTANTMESSAGES_ISREAD_INDEX)==1?true:false);
            	om.setMessageKey(c.getString(READ_INSTANTMESSAGES_MESSAGEKEY_INDEX));
            	result.add(om);
            } while(c.moveToNext());
		} else setRid(-1);		
		c.close();
        closedb(adapter);		
		return result;
	}
	
	public InstantMessages deleteDataBeforeDate(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		String whereClause = COLUMN_NAME_USERNO+"='"+getUserNo()+"'";
		whereClause += " and "+COLUMN_NAME_PUBLISHTIME+"<'"+sdf.format(getPublishTime())+"'";
		if(isDebug) Log.d(TAG,whereClause);
		//String[] whereArgs = {String.valueOf(getSerialID())};
		int rid = db.delete(getTableName(), whereClause, null);
        setRid(rid);
		if(rid==0) setRid(-1); // delete�ɡA�^�ЭY��0��ܨS���R���@����ơA�]���]-1��ܥ���
        closedb(adapter);
		return this;		
	}
	

}
