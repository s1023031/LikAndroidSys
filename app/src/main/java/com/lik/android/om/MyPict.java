package com.lik.android.om;

import java.util.Map;

import android.database.DatabaseUtils.InsertHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lik.android.main.LikDBAdapter;

public class MyPict extends BaseMyPict implements ProcessDownloadInterface{

	private static final long serialVersionUID = 795322499972199761L;

	@Override
	public boolean processDownload(LikDBAdapter adapter,
			Map<String, String> detail, boolean isOnlyInsert) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public MyPict doInsert(LikDBAdapter adapter) {
		 InsertHelper ih = adapter.getInsertHelper(MyPict.TABLE_NAME);
	    ih.prepareForInsert();
        ih.bind(2, getSno());
        ih.bind(3, getFileName());
        ih.bind(4, getTno());
        ih.bind(5, getSFlag());
        ih.bind(6, sdf.format(getLastDate()));
        ih.bind(7, getDetail());
        ih.bind(8, getDir());
  	    long rid = ih.execute();
        if(rid != -1) setRid(0);
		return this;
	}

	@Override
	public MyPict doUpdate(LikDBAdapter adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyPict doDelete(LikDBAdapter adapter) {
		SQLiteDatabase db = getdb(adapter);
		String whereClause = COLUMN_NAME_SERIALID+"="+getSerialID();
		if(isDebug) Log.d(TAG,whereClause);
		//String[] whereArgs = {String.valueOf(getSerialID())};
		int rid = db.delete(getTableName(), whereClause, null);
        setRid(rid);
		if(rid==0) setRid(-1); 
        closedb(adapter);
		return this;
	}

	@Override
	public MyPict findByKey(LikDBAdapter adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyPict queryBySerialID(LikDBAdapter adapter) {
		// TODO Auto-generated method stub
		return null;
	}




}
