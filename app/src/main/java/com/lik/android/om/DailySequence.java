package com.lik.android.om;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.lik.android.main.LikDBAdapter;

public class DailySequence extends BaseDailySequence {

	private static final long serialVersionUID = -4213513040915673822L;
	private static final String DEFAULT = "Default";
	private static int MAX = 1000000;

	public DailySequence() {
		setColumnName(DEFAULT);
	}
	
	public DailySequence(String columnName) {
		setColumnName(columnName);
	}
	
	public DailySequence(String columnName, int max) {
		setColumnName(columnName);
		MAX = max;
	}
	
	@Override
	public DailySequence doInsert(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues initialValues = new ContentValues();
        initialValues.put(COLUMN_NAME_COLUMNNAME, getColumnName());
        initialValues.put(COLUMN_NAME_SEQ, getSeq());

        long rid = db.insert(TABLE_NAME, null, initialValues);
        setRid(rid);
//        db.close();
        closedb(adapter);
		return this;
	}

	@Override
	public DailySequence doUpdate(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues updateValues = new ContentValues();

        updateValues.put(COLUMN_NAME_COLUMNNAME, getColumnName());
        updateValues.put(COLUMN_NAME_SEQ, getSeq());
        String[] whereArgs = {String.valueOf(getSerialID())};
        long rid = db.update(TABLE_NAME, updateValues, COLUMN_NAME_SERIALID+"=?", whereArgs);
        setRid(rid);
        if(rid==0) setRid(-1); // update�ɡA�^�ЭY��0��ܨS����s�@����ơA�]���]-1��ܥ���
//        db.close();
        closedb(adapter);
		return this;		
	}

	@Override
	public DailySequence doDelete(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		String whereClause = COLUMN_NAME_SERIALID+"="+getSerialID();
		int rid = db.delete(TABLE_NAME, whereClause, null);
        setRid(rid);
		if(rid==0) setRid(-1); // delete�ɡA�^�ЭY��0��ܨS���R���@����ơA�]���]-1��ܥ���
//		db.close();
        closedb(adapter);
		return this;
	}

	@Override
	public DailySequence findByKey(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_COLUMNNAME+"='"+getColumnName()+"'");
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_DAILYSEQUENCE_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_DAILYSEQUENCE_SERIALID_INDEX));
        	setColumnName(c.getString(READ_DAILYSEQUENCE_COLUMNNAME_INDEX));
        	setSeq(c.getInt(READ_DAILYSEQUENCE_SEQ_INDEX));
        	setRid(0);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}

	@Override
	public DailySequence queryBySerialID(LikDBAdapter adapter) {
		return findByKey(adapter);
	}
	
	public int getSequence(LikDBAdapter adapter) {
		int seq = 1;
		findByKey(adapter);
		if(getRid()<0) {
			setSeq(seq);
			doInsert(adapter);
		} else {
			seq = getSeq();
			if(seq>=MAX) seq = 1;
			setSeq(++seq);
			doUpdate(adapter);
		}
		return seq;
	}
	
	public void clear(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		db.execSQL(CLEAR_CMD);
        closedb(adapter);
	}

	public void reset(LikDBAdapter adapter) {
		findByKey(adapter);
		if(getRid()>=0) {
			setSeq(0);
			doUpdate(adapter);
		}
	}

}
