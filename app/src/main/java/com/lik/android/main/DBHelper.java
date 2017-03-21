package com.lik.android.main;

import com.lik.android.om.BaseOM;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private final static int _DBVersion = BaseOM.DATABASE_VERSION; //<-- 版本
	private final static String _DBName = "OtherDB.db";	//<-- db name
	//LikDB
	//private final static String _DBName = "LikDB";	//<-- db name
	private final static String _TableName = "MyPict"; //<-- table name
	private final static String _TableName2 = "ResPict"; 
	//private final static String _TableName2 = "MyPictList"; //<-- table name
	public DBHelper(Context context) {
		super(context, _DBName, null, _DBVersion);
	}

	

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		final String SQL = "CREATE TABLE IF NOT EXISTS " + _TableName + "( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "_Sno VARCHAR(50), " +
                "_Pid VARCHAR(50), " +
                "_Pno VARCHAR(50), " +
                "_CustomerID VARCHAR(50), " +
                "_CompanyID VARCHAR(50), " +
                "_SalesID VARCHAR(50), " +
	            "_FileName VARCHAR(200), " +
	            "_Tno VARCHAR(50), " +
	            "_Sflag VARCHAR(50), " +
	            "_SflagID VARCHAR(50), " +
	            "_LastDate VARCHAR(50)," +
	            "_DateTime VARCHAR(50)," +
	            "_Lon VARCHAR(50)," +
	            "_Lat VARCHAR(50)," +
	            "_isUpolad VARCHAR(50)," +
	            "_Detail TEXT," +
	            "_PHFL_POSITION_DIFFERENCE TEXT," +
	            "_CompanyParent TEXT," +
                "_Dir TEXT" +
	           ");";
		//PHFL_POSITION_DIFFERENCE
		db.execSQL(SQL);
		
		final String SQL2 = "CREATE TABLE IF NOT EXISTS " + _TableName2 + "( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "_Pno VARCHAR(10), " +
                "_type VARCHAR(10), " +
                "_Date DATE, " +
                "_CompanyParent TEXT," +
                "_Base64 BLOB" +
	           ");";
		
		db.execSQL(SQL2);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
