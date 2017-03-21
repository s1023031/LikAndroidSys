package com.lik.android.om;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.lik.android.main.LikDBAdapter;

public class ResPict extends BaseResPict implements ProcessDownloadInterface{
	
	private static final long serialVersionUID = -7734076669282283166L;

	@Override
	public boolean processDownload(LikDBAdapter adapter,
			Map<String, String> detail, boolean isOnlyInsert) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ResPict doInsert(LikDBAdapter adapter) {
		getdb(adapter);
		Log.d(TAG,"doInsert ResPict="+getTableName());
		  InsertHelper ih = adapter.getInsertHelper(getTableName());
		  ih.prepareForInsert();
	      ih.bind(2, getpNO());
	      ih.bind(3, getType());
	      ih.bind(4, getDate());
	      ih.bind(5, getBase64());
	      ih.bind(6, getPath());
	    long rid = ih.execute();
	    if(rid != -1) setRid(0);
	    Log.d(TAG,"doInsert End");
		return this;
	}
	
	public List<ResPict> getRictPictB(LikDBAdapter adapter) {
		List<ResPict> result = new ArrayList<ResPict>();
		SQLiteDatabase db = getdb(adapter);
		// Constructs a new query builder and sets its table name
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(getTableName());
		qb.setProjectionMap(projectionMap);
		qb.appendWhere(COLUMN_NAME_PNO+"='"+getpNO()+"'");
		qb.appendWhere(" and "+COLUMN_NAME_TYPE+"='"+getType()+"'");
		
		 
		Cursor c = qb.query(
				db,            // The database to query
				READ_RESPICT_PROJECTION,    // The columns to return from the query
				null,     // The columns for the where clause
				null, // The values for the where clause
				null,          // don't group the rows
				null,          // don't filter by row groups
				null        // The sort order
				);
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            do {
            	ResPict om = new ResPict();
            	om.setSerialID(c.getInt(READ_RESPICT_SERIALID_INDEX));
            	om.setpNO(c.getString(READ_RESPICT_PNO_INDEX));
            	om.setType(c.getString(READ_RESPICT_TYPE_INDEX));
            	
	            if(c.getString(READ_RESPICT_DATE_INDEX)!=null) {
		            try {
		            	Date date = sdf.parse(c.getString(READ_RESPICT_DATE_INDEX));
		            	SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		            	om.setDate(sDateFormat.format(date));
		            } catch(ParseException pe) {
		            	om.setDate(null);
		            }
	            }
	            om.setBase64(c.getBlob(READ_RESPICT_BASE64_INDEX));
	            om.setPath(c.getString(READ_RESPICT_PATH_INDEX));
	            om.setRid(0);
	        	result.add(om);
            } while(c.moveToNext());
        } else setRid(-1);
        c.close();
        closedb(adapter);
		return result;
	}

	@Override
	public ResPict doUpdate(LikDBAdapter adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResPict doDelete(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		//String[] whereArgs = {String.valueOf(getSerialID())};
		int rid = db.delete(getTableName(), null, null);
        setRid(rid);
		if(rid==0)
			setRid(-1); // delete時，回覆若為0表示沒有刪除一筆資料，因此設-1表示失敗
        closedb(adapter);
		return this;
	}

	@Override
	public ResPict findByKey(LikDBAdapter adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResPict queryBySerialID(LikDBAdapter adapter) {
		// TODO Auto-generated method stub
		return null;
	}

}
