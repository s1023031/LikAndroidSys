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

import com.lik.Constant;
import com.lik.android.main.LikDBAdapter;

public class LoadPHTF extends BaseLoadPHTF 
	implements ProcessDownloadInterface {

	private static final long serialVersionUID = 4657283906436808948L;

	public LoadPHTF getBulletinByKey(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_COMPANYID+"="+getCompanyID());
		qb.appendWhere(" and "+COLUMN_NAME_PHOTOFILEID+"="+getPhotoFileID());
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_LOADPHTF_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_LOADPHTF_SERIALID_INDEX));
            try {
            	Date dateFrom = sdf.parse(c.getString(READ_LOADPHTF_DATEFROM_INDEX));
            	setDateFrom(dateFrom);
            } catch(ParseException pe) {
            	setDateFrom(null);
            }
            try {
            	Date dateTo = sdf.parse(c.getString(READ_LOADPHTF_DATETO_INDEX));
            	setDateTo(dateTo);
            } catch(ParseException pe) {
            	setDateTo(null);
            }
            setDepartment(c.getString(READ_LOADPHTF_DEPARTMENT_INDEX));
            setClassify(c.getString(READ_LOADPHTF_CLASSIFY_INDEX));
            setItem(c.getString(READ_LOADPHTF_ITEM_INDEX));
            setStatement(c.getString(READ_LOADPHTF_STATEMENT_INDEX));
        	setRid(0);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}
	
	public LoadPHTF insertBulletin(LikDBAdapter adapter) {
		getdb(adapter);
		  InsertHelper ih = adapter.getInsertHelper(getTableName());
		  ih.prepareForInsert();
        ih.bind(2, getCompanyID());
        ih.bind(3, getPhotoFileID());
        ih.bind(4, sdf.format(getDateFrom()));
        ih.bind(5, sdf.format(getDateTo()));
        ih.bind(6, getDepartment());
        ih.bind(7, getClassify());
        ih.bind(8, getItem());
        ih.bind(9, getStatement());
  	    long rid = ih.execute();
        if(rid != -1) setRid(0);
		return this;
	}

	public LoadPHTF updateBulletin(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
        ContentValues updateValues = new ContentValues();
        updateValues.put(COLUMN_NAME_DATEFROM, sdf.format(getDateFrom()));
        updateValues.put(COLUMN_NAME_DATETO, sdf.format(getDateTo()));
        updateValues.put(COLUMN_NAME_DEPARTMENT, getDepartment());
        updateValues.put(COLUMN_NAME_CLASSIFY, getClassify());
        updateValues.put(COLUMN_NAME_ITEM, getItem());
        updateValues.put(COLUMN_NAME_STATEMENT, getStatement());
        String[] whereArgs = {String.valueOf(getSerialID())};
        long rid = db.update(getTableName(), updateValues, COLUMN_NAME_SERIALID+"=?", whereArgs);
        setRid(rid);
        if(rid==0) setRid(-1); // update�ɡA�^�ЭY��0��ܨS����s�@����ơA�]���]-1��ܥ���
//        db.close();
        closedb(adapter);
		return this;		
	}
	
	public LoadPHTF deleteBulletin(LikDBAdapter adapter) {
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
		setPhotoFileID(Integer.parseInt(detail.get(COLUMN_NAME_PHOTOFILEID)));
		if(!isOnlyInsert) getBulletinByKey(adapter);
		try {
			if(detail.get(COLUMN_NAME_DATEFROM) != null) setDateFrom(sdf2.parse(detail.get(COLUMN_NAME_DATEFROM)));
			else setDateFrom(null);
			if(detail.get(COLUMN_NAME_DATETO) != null) setDateTo(sdf2.parse(detail.get(COLUMN_NAME_DATETO)));
			else setDateTo(null);
		} catch (ParseException e) {
			Log.e(TAG,e.getMessage());
		}
		setDepartment(detail.get(COLUMN_NAME_DEPARTMENT));
		setClassify(detail.get(COLUMN_NAME_CLASSIFY));
		setItem(detail.get(COLUMN_NAME_ITEM));
		setStatement(detail.get(COLUMN_NAME_STATEMENT));
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
	
	private Date date;
	
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<LoadPHTF> getBulletinByDate(LikDBAdapter adapter) {
		List<LoadPHTF> result = new ArrayList<LoadPHTF>();
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_COMPANYID+"="+getCompanyID());
		if(getDate()!=null) {
			qb.appendWhere(" and "+COLUMN_NAME_DATEFROM+"<='"+Constant.sqliteDF.format(getDate())+"'");
			qb.appendWhere(" and "+COLUMN_NAME_DATETO+">='"+Constant.sqliteDF.format(getDate())+"'");
		}
		/*
		 * Performs the query. If no problems occur trying to read the database, then a Cursor
		 * object is returned; otherwise, the cursor variable contains null. If no records were
		 * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
		 */
		Cursor c = qb.query(
				db,            // The database to query
				READ_LOADPHTF_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
            	LoadPHTF om = new LoadPHTF();
            	om.setSerialID(c.getInt(READ_LOADPHTF_SERIALID_INDEX));

            	om.setCompanyID(c.getInt(READ_LOADPHTF_COMPANYID_INDEX));
            	om.setPhotoFileID(c.getInt(READ_LOADPHTF_PHOTOFILEID_INDEX));
                try {
                	Date dateFrom = sdf.parse(c.getString(READ_LOADPHTF_DATEFROM_INDEX));
                	om.setDateFrom(dateFrom);
                } catch(ParseException pe) {
                	om.setDateFrom(null);
                }
                try {
                	Date dateTo = sdf.parse(c.getString(READ_LOADPHTF_DATETO_INDEX));
                	om.setDateTo(dateTo);
                } catch(ParseException pe) {
                	om.setDateTo(null);
                }
                om.setDepartment(c.getString(READ_LOADPHTF_DEPARTMENT_INDEX));
                om.setClassify(c.getString(READ_LOADPHTF_CLASSIFY_INDEX));
                om.setItem(c.getString(READ_LOADPHTF_ITEM_INDEX));
                om.setStatement(c.getString(READ_LOADPHTF_STATEMENT_INDEX));
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
	public LoadPHTF doInsert(LikDBAdapter adapter) {
		return insertBulletin(adapter);
	}

	@Override
	public LoadPHTF doUpdate(LikDBAdapter adapter) {
		return updateBulletin(adapter);
	}

	@Override
	public LoadPHTF doDelete(LikDBAdapter adapter) {
		return deleteBulletin(adapter);
	}

	@Override
	public LoadPHTF findByKey(LikDBAdapter adapter) {
		return getBulletinByKey(adapter);
	}

	@Override
	public LoadPHTF queryBySerialID(LikDBAdapter adapter) {
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
				READ_LOADPHTF_PROJECTION,    // The columns to return from the query
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
        	setSerialID(c.getInt(READ_LOADPHTF_SERIALID_INDEX));
        	setCompanyID(c.getInt(READ_LOADPHTF_COMPANYID_INDEX));
        	setPhotoFileID(c.getInt(READ_LOADPHTF_PHOTOFILEID_INDEX));
            try {
            	Date dateFrom = sdf.parse(c.getString(READ_LOADPHTF_DATEFROM_INDEX));
            	setDateFrom(dateFrom);
            } catch(ParseException pe) {
            	setDateFrom(null);
            }
            try {
            	Date dateTo = sdf.parse(c.getString(READ_LOADPHTF_DATETO_INDEX));
            	setDateTo(dateTo);
            } catch(ParseException pe) {
            	setDateTo(null);
            }
            setDepartment(c.getString(READ_LOADPHTF_DEPARTMENT_INDEX));
            setClassify(c.getString(READ_LOADPHTF_CLASSIFY_INDEX));
            setItem(c.getString(READ_LOADPHTF_ITEM_INDEX));
            setStatement(c.getString(READ_LOADPHTF_STATEMENT_INDEX));
        	setRid(0);
        } else setRid(-1);
//        db.close();
        c.close();
        closedb(adapter);
		return this;
	}

}
