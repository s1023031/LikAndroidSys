package com.lik.android.om;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.lik.android.main.LikDBAdapter;

public class Bulletin extends BaseBulletin implements ProcessDownloadInterface {

	private static final long serialVersionUID = 4657283906436808948L;

	public Bulletin getBulletinByKey(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_COMPANYID+"="+getCompanyID());
		qb.appendWhere(" and "+COLUMN_NAME_BULLETINID+"="+getBulletinID());
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_BULLETIN_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_BULLETIN_SERIALID_INDEX));
            try {
            	Date dateFrom = sdf.parse(c.getString(READ_BULLETIN_DATEFROM_INDEX));
            	setDateFrom(dateFrom);
            } catch(ParseException pe) {
            	setDateFrom(null);
            }
            try {
            	Date dateTo = sdf.parse(c.getString(READ_BULLETIN_DATETO_INDEX));
            	setDateTo(dateTo);
            } catch(ParseException pe) {
            	setDateTo(null);
            }
            try {
            	Date buildDate = sdf.parse(c.getString(READ_BULLETIN_BUILDDATE_INDEX));
            	setBuildDate(buildDate);
            } catch(ParseException pe) {
            	setBuildDate(null);
            }
            setBulletinFrom(c.getString(READ_BULLETIN_BULLETINFROM_INDEX));
            setBulletinSubject(c.getString(READ_BULLETIN_BULLETINSUBJECT_INDEX));
            setBulletinBody(c.getString(READ_BULLETIN_BULLETINBODY_INDEX));
            setVersionNo(c.getString(READ_BULLETIN_VERSIONNO_INDEX));
        	setRid(0);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}
	
	public Bulletin insertBulletin(LikDBAdapter adapter) {
		Log.d(TAG,"do insertBegin="+getTableName());
		getdb(adapter);
		  InsertHelper ih = adapter.getInsertHelper(getTableName());
		  ih.prepareForInsert();
        ih.bind(2, getCompanyID());
        ih.bind(3, getBulletinID());
        ih.bind(4, sdf.format(getDateFrom()));
        ih.bind(5, sdf.format(getDateTo()));
        ih.bind(6, sdf.format(getBuildDate()));
        ih.bind(7, getBulletinFrom());
        ih.bind(8, getBulletinSubject());
        ih.bind(9, getBulletinBody());
        ih.bind(10, getVersionNo());
  	    long rid = ih.execute();
        if(rid != -1) setRid(0);
		return this;
	}

//	public Bulletin insertBulletin(LikDBAdapter adapter) {
//		SQLiteDatabase db = getdb(adapter);
//        ContentValues initialValues = new ContentValues();
//        initialValues.put(COLUMN_NAME_COMPANYID, getCompanyID());
//        initialValues.put(COLUMN_NAME_BULLETINID, getBulletinID());
//        initialValues.put(COLUMN_NAME_DATEFROM, sdf.format(getDateFrom()));
//        initialValues.put(COLUMN_NAME_DATETO, sdf.format(getDateTo()));
//        initialValues.put(COLUMN_NAME_BUILDDATE, sdf.format(getBuildDate()));
//        initialValues.put(COLUMN_NAME_BULLETINFROM, getBulletinFrom());
//        initialValues.put(COLUMN_NAME_BULLETINSUBJECT, getBulletinSubject());
//        initialValues.put(COLUMN_NAME_BULLETINBODY, getBulletinBody());
//        initialValues.put(COLUMN_NAME_VERSIONNO, getVersionNo());
//        long rid = db.insert(TABLE_NAME, null, initialValues);
//        setRid(rid);
////        db.close();
//        closedb(adapter);
//		return this;
//	}

	public Bulletin updateBulletin(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues updateValues = new ContentValues();
        updateValues.put(COLUMN_NAME_DATEFROM, sdf.format(getDateFrom()));
        updateValues.put(COLUMN_NAME_DATETO, sdf.format(getDateTo()));
        updateValues.put(COLUMN_NAME_BUILDDATE, sdf.format(getBuildDate()));
        updateValues.put(COLUMN_NAME_BULLETINFROM, getBulletinFrom());
        updateValues.put(COLUMN_NAME_BULLETINSUBJECT, getBulletinSubject());
        updateValues.put(COLUMN_NAME_BULLETINBODY, getBulletinBody());
        updateValues.put(COLUMN_NAME_VERSIONNO, getVersionNo());
        String[] whereArgs = {String.valueOf(getSerialID())};
        long rid = db.update(getTableName(), updateValues, COLUMN_NAME_SERIALID+"=?", whereArgs);
        setRid(rid);
        if(rid==0) setRid(-1); // update�ɡA�^�ЭY��0��ܨS����s�@����ơA�]���]-1��ܥ���
//        db.close();
        closedb(adapter);
		return this;		
	}
	
	public Bulletin deleteBulletin(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		String whereClause = COLUMN_NAME_SERIALID+"="+getSerialID();
		if(isDebug) Log.d(TAG,whereClause);
		//String[] whereArgs = {String.valueOf(getSerialID())};
		int rid = db.delete(getTableName(), whereClause, null);
        setRid(rid);
		if(rid==0) setRid(-1); // delete�ɡA�^�ЭY��0��ܨS���R���@����ơA�]���]-1��ܥ���
//		db.close();
        closedb(adapter);
		return this;
	}

	@Override
	public boolean processDownload(LikDBAdapter adapter, Map<String, String> detail, boolean isOnlyInsert) {
		boolean result = true;
		String flag = detail.get(FLAG_KEY);
		setCompanyID(Integer.parseInt(detail.get(COLUMN_NAME_COMPANYID)));
		setBulletinID(Integer.parseInt(detail.get(COLUMN_NAME_BULLETINID)));
		if(!isOnlyInsert) getBulletinByKey(adapter);
		try {
			if(detail.get(COLUMN_NAME_DATEFROM) != null) setDateFrom(sdf2.parse(detail.get(COLUMN_NAME_DATEFROM)));
			else setDateFrom(null);
			if(detail.get(COLUMN_NAME_DATETO) != null) setDateTo(sdf2.parse(detail.get(COLUMN_NAME_DATETO)));
			else setDateTo(null);
			if(detail.get(COLUMN_NAME_BUILDDATE) != null) setBuildDate(sdf2.parse(detail.get(COLUMN_NAME_BUILDDATE)));
			else setBuildDate(null);
		} catch (ParseException e) {
			Log.e(TAG,e.getMessage());
		}
		setBulletinFrom(detail.get(COLUMN_NAME_BULLETINFROM));
		setBulletinSubject(detail.get(COLUMN_NAME_BULLETINSUBJECT));
		setBulletinBody(detail.get(COLUMN_NAME_BULLETINBODY));
		setVersionNo(detail.get(COLUMN_NAME_VERSIONNO));
		if(isOnlyInsert) insertBulletin(adapter);
		else {
			if(getRid()<0) insertBulletin(adapter);
			else {
				if(flag.equals(FLAG_DELETE)) doDelete(adapter);
				else updateBulletin(adapter);
			}
		}
		if(getRid()<0) result = false;
		return result;
	}
	
	public List<Bulletin> getBulletinByCompanyID(LikDBAdapter adapter) {
		List<Bulletin> result = new ArrayList<Bulletin>();
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_COMPANYID+"="+getCompanyID());
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_BULLETIN_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
            	Bulletin om = new Bulletin();
            	om.setSerialID(c.getInt(READ_BULLETIN_SERIALID_INDEX));

            	om.setCompanyID(c.getInt(READ_BULLETIN_COMPANYID_INDEX));
            	om.setBulletinID(c.getInt(READ_BULLETIN_BULLETINID_INDEX));
                try {
                	Date dateFrom = sdf.parse(c.getString(READ_BULLETIN_DATEFROM_INDEX));
                	om.setDateFrom(dateFrom);
                } catch(ParseException pe) {
                	om.setDateFrom(null);
                }
                try {
                	Date dateTo = sdf.parse(c.getString(READ_BULLETIN_DATETO_INDEX));
                	om.setDateTo(dateTo);
                } catch(ParseException pe) {
                	om.setDateTo(null);
                }
                try {
                	Date buildDate = sdf.parse(c.getString(READ_BULLETIN_BUILDDATE_INDEX));
                	om.setBuildDate(buildDate);
                } catch(ParseException pe) {
                	om.setBuildDate(null);
                }
                om.setBulletinFrom(c.getString(READ_BULLETIN_BULLETINFROM_INDEX));
                om.setBulletinSubject(c.getString(READ_BULLETIN_BULLETINSUBJECT_INDEX));
                om.setBulletinBody(c.getString(READ_BULLETIN_BULLETINBODY_INDEX));
                om.setVersionNo(c.getString(READ_BULLETIN_VERSIONNO_INDEX));
                om.setRid(0);
            	result.add(om);
            } while(c.moveToNext());
        }
//        db.close();
        c.close();
        closedb(adapter);
		return result;
		
	}

	@Override
	public Bulletin doInsert(LikDBAdapter adapter) {
		return insertBulletin(adapter);
	}

	@Override
	public Bulletin doUpdate(LikDBAdapter adapter) {
		return updateBulletin(adapter);
	}

	@Override
	public Bulletin doDelete(LikDBAdapter adapter) {
		return deleteBulletin(adapter);
	}

	@Override
	public Bulletin findByKey(LikDBAdapter adapter) {
		return getBulletinByKey(adapter);
	}

	@Override
	public Bulletin queryBySerialID(LikDBAdapter adapter) {
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
				READ_BULLETIN_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_BULLETIN_SERIALID_INDEX));
        	setCompanyID(c.getInt(READ_BULLETIN_COMPANYID_INDEX));
        	setBulletinID(c.getInt(READ_BULLETIN_BULLETINID_INDEX));
            try {
            	Date dateFrom = sdf.parse(c.getString(READ_BULLETIN_DATEFROM_INDEX));
            	setDateFrom(dateFrom);
            } catch(ParseException pe) {
            	setDateFrom(null);
            }
            try {
            	Date dateTo = sdf.parse(c.getString(READ_BULLETIN_DATETO_INDEX));
            	setDateTo(dateTo);
            } catch(ParseException pe) {
            	setDateTo(null);
            }
            try {
            	Date buildDate = sdf.parse(c.getString(READ_BULLETIN_BUILDDATE_INDEX));
            	setBuildDate(buildDate);
            } catch(ParseException pe) {
            	setBuildDate(null);
            }
            setBulletinFrom(c.getString(READ_BULLETIN_BULLETINFROM_INDEX));
            setBulletinSubject(c.getString(READ_BULLETIN_BULLETINSUBJECT_INDEX));
            setBulletinBody(c.getString(READ_BULLETIN_BULLETINBODY_INDEX));
            setVersionNo(c.getString(READ_BULLETIN_VERSIONNO_INDEX));
        	setRid(0);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}

}
