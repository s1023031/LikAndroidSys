package com.lik.android.om;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lik.android.main.LikDBAdapter;

public abstract class BaseOM<T> implements Serializable {
	
	
	private static final long serialVersionUID = 2223868632396020923L;
	
	protected static final String FLAG_KEY = "Flag";
	protected static final String FLAG_UPDATE = "U";
	protected static final String FLAG_DELETE = "D";

	protected String TAG = "BaseOM";
	
	/**
     * The database used as its underlying data store
     */
	public static final String DATABASE_NAME = "LikDB";

    /**
     * The database version
     */
	public static final int DATABASE_VERSION = 108; 
	
	protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",Locale.CHINESE);
	protected SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINESE); // sqlite ����x�s�榡-�u�����
	
	protected boolean isDebug = true;
	
	 private long rid; // used to store msg, <0 error, >=0 success

	 private int tableCompanyID;
	 private String companyParent;
	 
	 public BaseOM() {
	    	this.TAG = this.getClass().getName();
	 }
	 
	 /**
	  * convenient method for getting DB readable/writable connection
	  * @param adapter
	  * @return
	  */
	  public SQLiteDatabase getdb(LikDBAdapter adapter) {
	    if(tableCompanyID==0) {
	    	if(adapter.getCompanyID()!=0) {
	    		tableCompanyID = adapter.getCompanyID();
	    	}
	    }

	   	if(tableCompanyID==0) Log.e(TAG,"tableCompanyID is 0!");
	    return adapter.getdb();
	  }
	  
	  /**
	   * convenient method for closing DB readable/writable connection
	   * @param adapter
	   * @return
	   */
	  public void closedb(LikDBAdapter adapter) {
	      adapter.closedb();
	  }
	  
	  public long getRid() {
			return rid;
	  }
		
	  public void setRid(long rid) {
			this.rid = rid;
	  }
		
	  public int getCount(LikDBAdapter adapter) {
			int result = 0;
			setTableCompanyID(adapter.getCompanyID());
			String sql = "select count(*) from "+getTableName();
			Cursor c = adapter.getdb().rawQuery(sql, null);
	        if (c != null && c.getCount()>0) {
	            c.moveToFirst();
	            result = c.getInt(0);
	        }
	        c.close();
	    	adapter.closedb();
	        return result;
		}

	  public int getTableCompanyID() {
			return tableCompanyID;
	  }

	  public void setTableCompanyID(int tableCompanyID) {
			this.tableCompanyID = tableCompanyID;
	  }
	  
	  public String getCompanyParent(){
		  return companyParent;
	  }
	  
	  public void setCompanyParent(String companyParent) {
			this.companyParent = companyParent;
	  }
		
	  public boolean testTableExists(LikDBAdapter adapter) {
			if(adapter==null) return false;
			SQLiteDatabase db = getdb(adapter);
			String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='"+getTableName()+"'";
			try {
				Cursor c = db.rawQuery(sql, null);
				if(c != null && c.moveToFirst()) {
					return true;
				} else {
					return false;
				}
			} finally {
				closedb(adapter);
			}
		}

	  public boolean testTableExists(SQLiteDatabase db) {
			String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='"+getTableName()+"'";
			Cursor c = db.rawQuery(sql, null);
			if(c != null && c.moveToFirst()) {
				return true;
			} else {
				return false;
			}
	 }
	  
	  public boolean deleteAllData(LikDBAdapter adapter, String userNo) {
			boolean result = true;
			SQLiteDatabase db = getdb(adapter);
			String sqlsur = getTableName();
			if(sqlsur.startsWith(Customers.TABLE_NAME)) {
				sqlsur = sqlsur + " where "+Customers.COLUMN_NAME_USERNO+"='"+userNo+"'";
			} 
			/*else if(sqlsur.startsWith(SellDetail.TABLE_NAME)) {
				Customers omC = new Customers();
				omC.setTableCompanyID(tableCompanyID);
				sqlsur = sqlsur + " where "+SellDetail.COLUMN_NAME_CUSTOMERID+" in (select "+Customers.COLUMN_NAME_CUSTOMERID;
				sqlsur = sqlsur + " from "+omC.getTableName()+" where "+Customers.COLUMN_NAME_USERNO+"='"+userNo+"')";
			} else if(sqlsur.startsWith(PhotoProject.TABLE_NAME)) {
				sqlsur = sqlsur + " where "+PhotoProject.COLUMN_NAME_USERNO+"='"+userNo+"'";
			} else if(sqlsur.startsWith(CustomersForMapTracker.TABLE_NAME)) {
				sqlsur = sqlsur + " where "+CustomersForMapTracker.COLUMN_NAME_USERNO+"='"+userNo+"'";
			}*/
			String sql = "delete FROM "+sqlsur;
			Log.d(TAG,"delete sql="+sql);
			try {
				db.execSQL(sql);
			} catch(SQLException e) {
				Log.e(TAG,sql+" failed");
				result = false;
			} finally {
				closedb(adapter);
			}
			return result;
	  }
		 		
	 /**
	  * The following methods
	  * should be overwritten by subclass.
	  */
	  public abstract String getTableName();

	  public abstract String getCreateCMD();
	    
	  public abstract String getDropCMD();
	    
	  public abstract String[] getCreateIndexCMD();

	  public abstract T doInsert(LikDBAdapter adapter);
	  public abstract T doUpdate(LikDBAdapter adapter);
	  public abstract T doDelete(LikDBAdapter adapter);
	  public abstract T findByKey(LikDBAdapter adapter);
	  public abstract T queryBySerialID(LikDBAdapter adapter);
	    
	 
}
