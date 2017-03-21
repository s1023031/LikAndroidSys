package com.lik.android.main;

import java.io.Serializable;
import java.util.Iterator;
import java.util.TreeMap;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils.InsertHelper;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lik.android.om.Account;
import com.lik.android.om.BaseOM;
import com.lik.android.om.Bulletin;
import com.lik.android.om.Company;
import com.lik.android.om.ConfigControl;
import com.lik.android.om.CurrentCompany;
import com.lik.android.om.Customers;
import com.lik.android.om.DailySequence;
import com.lik.android.om.InstantMessages;
import com.lik.android.om.LoadPHTF;
import com.lik.android.om.MyPict;
import com.lik.android.om.Orders;
import com.lik.android.om.PhotoProject;
import com.lik.android.om.Phrase;
import com.lik.android.om.PriorityList;
import com.lik.android.om.Products;
import com.lik.android.om.Promotion;
import com.lik.android.om.ResPict;
import com.lik.android.om.Settings;
import com.lik.android.om.SiteIPList;
import com.lik.android.om.SiteInfo;
import com.lik.android.om.SysProfile;
import com.lik.android.om.UserCompy;
import com.lik.android.om.Users;


public class LikDBAdapter extends SQLiteOpenHelper implements Serializable{

	private static final long serialVersionUID = -4775140218532806453L;
	
	private static final String TAG = LikDBAdapter.class.getName();
	private static final String DATABASE_NAME = BaseOM.DATABASE_NAME;
    private static final int DATABASE_VERSION = BaseOM.DATABASE_VERSION;
    private Context ctx;
    private int companyID;
    private SQLiteDatabase db;
    private TreeMap<String,InsertHelper> ihMap = new TreeMap<String,InsertHelper>();
    private boolean isTransaction = false; // default disable closedb(), �H�קK�@�����ƶ}��db
    
	
    /**
     * list of the tables used in this database 
     */
    public static final BaseOM<?>[] DATABASE_TABLES = {
    	new SysProfile(),
    	new Account(),
    	new UserCompy(),
    	new SiteInfo(),
    	new SiteIPList(),
    	//new PriorityList(),
    	//new SiteTrace(),
    	//new ConnectStatus(),
    	new Company(),
     	new Orders(),
    	//new OrderDetail(),
    	new Settings(),
    	new DailySequence(),
    //	new Users(),
    	new ConfigControl(),
    	new CurrentCompany(),
    	//new AddressLocation(),
    };
    
    public static final BaseOM<?>[] DATABASE_TABLES_FOR_DOWNLOAD = {

    	new Customers(),
    	new Bulletin(),
    	new Phrase(),
    	//new SellDetail(),
    	//new Products(),
    	//new Promotion(),
    //	new LoadPHTF(),
    	new PhotoProject(),
    	new ResPict(),
    	new InstantMessages(),
    	/*
    	new PrdtPrice(),
    	new PrdtUnits(),
    	new Suppliers()
    	new CustomersForMapTracker(),
    	new PriceLock(),
    	new GetStockFTN(),
    	new Limit(),
    	new CustBank(),
    	new OrderCheck(),
    	new OrderReceive(),
    	new OrderStock(),
    	new SE20(),
    	new ImageUploadTrackLog()*/
    };
    
    
    public LikDBAdapter(Context ctx) {
    	super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    	this.ctx = ctx;
    }

    public LikDBAdapter(Context ctx, boolean isTransaction) {
    	super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    	this.ctx = ctx;
    	this.isTransaction = isTransaction;
    }
	
    public LikDBAdapter(Context ctx, boolean isTransaction, int companyID) {
    	super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    	this.ctx = ctx;
    	this.isTransaction = isTransaction;
    	this.companyID = companyID;
    }



	@Override
    public void onCreate(SQLiteDatabase db) 
    {
    	for(int i=0;i<DATABASE_TABLES.length;i++) {
        	// create table
    		String cmd = DATABASE_TABLES[i].getCreateCMD();
    		try {
    			if(cmd != null) 
    				db.execSQL(cmd);	
    			Log.i(TAG,"table "+DATABASE_TABLES[i].getTableName()+"  "+cmd);
    			Log.i(TAG,"table "+DATABASE_TABLES[i].getTableName()+" created!");
    		} catch (SQLException e) {
    			Log.w(TAG,"exception while creating table..."+DATABASE_TABLES[i].getTableName());
    			e.printStackTrace();
    		}
        	// create index
    		String[] index_cmd = DATABASE_TABLES[i].getCreateIndexCMD();
    		if(index_cmd == null) continue;
    		for(int j=0;j<index_cmd.length;j++) {
	    		try {
	    			db.execSQL(index_cmd[j]);	
	    		} catch (SQLException e) {
	    			Log.w(TAG,"exception while creating index..."+index_cmd[j]);
	    			e.printStackTrace();
	    		}
    		}
    	}  
    	
    }

	 @Override
	 public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(TAG, "Upgrading database from version " + oldVersion + " to "+ newVersion + ", which will destroy some tables");
	    /*
	     * define tables to be done
	     * 1. drop all tables
	     * 2. clear reference
	     * 3. call onCreate
	     */
	    for(int i=0;i<DATABASE_TABLES.length;i++) {
	    	String cmd = DATABASE_TABLES[i].getDropCMD();
	    	try {
	    		// �Ysettings��drop
	    		if(DATABASE_TABLES[i].getTableName().equals("Settings")) continue;
	   			if(cmd != null) db.execSQL(cmd);	
	   			Log.i(TAG,"table "+DATABASE_TABLES[i].getTableName()+" dropped!");
	   		} catch (SQLException e) {
	   			Log.w(TAG,"exception while drop table..."+DATABASE_TABLES[i].getTableName());
    			e.printStackTrace();	    		}
	    	}
	    	String sql = "SELECT name FROM sqlite_master WHERE type = 'table'";
	    	Cursor c = db.rawQuery(sql,null);
	        if (c != null && c.moveToFirst()) {
	        	do {
	        		String tableName = c.getString(0);
			    	for(int i=0;i<DATABASE_TABLES_FOR_DOWNLOAD.length;i++) {
			    		String[] tmpTableName = DATABASE_TABLES_FOR_DOWNLOAD[i].getTableName().split("_");
			    		if(tmpTableName.length>0 && tableName.startsWith(tmpTableName[0])) {
				    		String cmd = "DROP TABLE IF EXISTS "+tableName;
				    		try {
				    			db.execSQL(cmd);
				    			Log.i(TAG,"table "+tableName+" dropped!");
				    		} catch (SQLException e) {
				    			Log.w(TAG,"exception while drop table..."+tableName);
				    			e.printStackTrace();
				    		}
				    		break;
			    		}
			    	}
	            } while(c.moveToNext());
	        }
	        c.close();
	        /* verify drop complete */
	        SysProfile omSP = new SysProfile();
	        if(!omSP.testTableExists(db)) {
	        	Log.i(TAG,"verify drop complete!");
	        } else {
	        	String sqlsp = "delete FROM SysProfile";
	        	try {
	        		db.execSQL(sqlsp);
	        	} catch(SQLException e) {
	        		Log.e(TAG,"delete FROM SysProfile failed!");
	        		e.printStackTrace();
	        	}
	        }

	    	// clear reference
	      /*  SharedPreferences settings = ctx.getSharedPreferences(MainMenuActivity.class.getSimpleName(), Context.MODE_PRIVATE);
	        SharedPreferences.Editor editor = settings.edit();
	        editor.clear();
	        editor.commit();*/
	    	onCreate(db);
	}
	 
	public boolean isTransaction() {
	    return isTransaction;
	}
	    
	public void setCtx(MainMenuActivity ctx) {
	    this.ctx = ctx;
	}

	public SQLiteDatabase getdb() {
	    if(db != null && db.isOpen()) return db;
	    db = getWritableDatabase();
	    return db;
	}
	    
	public void closedb() {
	    if(!isTransaction) {
	    	if(db != null) db.close();
	    }
	}
	
    /**
     * must call to close db if transaction is enable
     */
    public void endTransaction() {
    	if(isTransaction) {
    		for(Iterator<String> ir = ihMap.keySet().iterator();ir.hasNext();) {
    			InsertHelper ih = ihMap.get(ir.next());
    			ih.close();
    		}
    		if(db != null) db.close();
    		ihMap = new TreeMap<String,InsertHelper>();
    		db = null;
    	}
    	
    }
	
    public InsertHelper getInsertHelper(String tableName) {
    	if(ihMap.get(tableName) != null) {    		
    		return ihMap.get(tableName);
    	}
    	else {
    		InsertHelper ih = new InsertHelper(getdb(), tableName);
    		ihMap.put(tableName, ih);
    		return ih;
    	}
    }

	
	public Context getCtx() {
		return ctx;
	}

	public int getCompanyID() {
		if(companyID==0) {
			companyID = MainMenuActivity.companyID;
		}
		return companyID;
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}
    
}
