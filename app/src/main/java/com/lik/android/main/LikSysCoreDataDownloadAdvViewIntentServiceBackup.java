/*package com.lik.android.main;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jivesoftware.smack.XMPPException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lik.Constant;
import com.lik.android.main.R;
import com.lik.android.main.LikSysActivity.ProcessCompany;
import com.lik.android.main.LikSysActivity.ProcessSiteInfo;
import com.lik.android.main.ResPage.Base64Decode;
import com.lik.android.main.ResPage.DebugPring;
import com.lik.android.main.ResPage.SendPost;
import com.lik.android.om.BaseOM;
import com.lik.android.om.Company;
import com.lik.android.om.PhotoProject;
import com.lik.android.om.PhotoProjectState;
import com.lik.android.om.Phrase;
import com.lik.android.om.ProcessDownloadInterface;
import com.lik.android.om.ResPict;
import com.lik.android.om.SiteInfo;
import com.lik.android.om.UserCompy;
import com.lik.android.util.HttpMessage;
import com.lik.android.util.HttpUtil;
import com.lik.android.util.OmUtil;
import com.lik.android.util.XmppCallBack;
import com.lik.android.util.XmppUtil;
import com.lik.util.ExternalStorage;

*//**
 * 每次只送一個table給主機，由主機決定送多少資料到XMPP，
 * FOOTER中flag告知是否尚有資料待傳
 * 該table完成後再送下一個table，
 * 待全部table完成後service才結束
 *
 *//*
public class LikSysCoreDataDownloadAdvViewIntentServiceBackup extends IntentService implements XmppCallBack {

	private static final String TAG = "LikSysCoreDataDownloadAdvViewIntentService";
	private static final int SLEEP_INTERVAL = 3000; // 5 secs
	private static final int LOOP_BEFORE_STOP = 60; // 60 times
	private static final int KEEP_DOWNLOAD_RECORD_INTERVAL = 600; // 500���e�D����s�@��
	public static final String LIKSYS_COREDATA_DOWNLOAD_ACTION = "LIKSYS_COREDATA_DOWNLOAD_ACTION";
	public static final String CODE = "CODE";
	public static final String RESULT = "DATA";
	public static final String RESULT_CONNECT_ERROR = Constant.ERROR_CONNECT_NETWORK+":CONNECT ERROR";
	public static final String OM_PACKAGE_NAME = "com.lik.android.om.";
	private static final String MESSAGE_SEPERATOR = "---seperate_line---";


	private TreeMap<String,Integer> tmActSize = new TreeMap<String,Integer>(); // 下載table筆數 key=table name, value=已下載筆數
	private TreeMap<String,Integer> tmRoundSize = new TreeMap<String,Integer>(); // round�U��table���� key=table name, value=��������
	private TreeMap<String,Integer> tmTotalSize = new TreeMap<String,Integer>(); // �U��table���� key=table name, value=��������
	private TreeMap<String,ProcessDownloadInterface> tmClass = new TreeMap<String,ProcessDownloadInterface>(); // class name, object mapping 

	int progressInterval = 10; // default
	// XMPP utility
	private final XmppUtil xmppUtil = new XmppUtil(this);
	
	// service alive flag
	private boolean isAlive = true;
	
	// �Y���t�βĤ@���w�ˡADB���šA�HOnlyInsert�ӶiDB�A�į���n
	private boolean isOnlyInsert = true;
	private boolean isContinue = false;
	
	// count
	private int count = 0;
	private int countLast = -1;
	private int keepRecordCount = 0;
	private int maxPhotoProject=0;
	private int countPhotoProject = 0;
	private int temp = 0;

	// input data
	String ip; // ��x�D����IP
	String siteName; // tablet sitename
	String httpPort;
	String XMPPPort;
	String sCheckDownloadFlagURI;
	String sDownloadURI;
	String sDownloadFinishURI;
	String accountNo;
	int companyID;
	String tableNameList;
	String lastReceivedDataURI;
	String lineDate;
	int historyPeriod;
	String SellDetailStorageType;
	int downloadMinute;
	boolean hasCameraInfo;
	boolean isFTN;
	
	ArrayList<String> alTableName = new ArrayList<String>(); // table name set

	// keep db reference
	private LikDBAdapter DBAdapter;
	private Map<String,String> detail;
	private String tableName;
	
	public LikSysCoreDataDownloadAdvViewIntentServiceBackup() {
        super("LikSysCoreDataDownloadAdvIntentService");
    }	

	@Override
	protected void onHandleIntent(Intent intent) {

		ip = intent.getStringExtra("ip"); // ��x�D����IP
		siteName = intent.getStringExtra("siteName"); // tablet sitename
		httpPort = intent.getStringExtra("http_port");
		XMPPPort = intent.getStringExtra("xmpp_port");
		sCheckDownloadFlagURI = intent.getStringExtra("CheckDownloadFlagURI");
		sDownloadURI = intent.getStringExtra("DownloadURI");
		sDownloadFinishURI = intent.getStringExtra("DownloadFinishURI");
		accountNo = intent.getStringExtra("accountNo");
		tableNameList = intent.getStringExtra("tableNameList");
		lastReceivedDataURI = intent.getStringExtra("LastReceivedDataURI");
		isContinue = intent.getBooleanExtra("isContinue", false);
		lineDate = intent.getStringExtra("lineDate");
		historyPeriod = intent.getIntExtra("historyPeriod", 1);
		String sCompanyID = intent.getStringExtra("companyID");
		companyID = Integer.parseInt(sCompanyID);
		SellDetailStorageType = intent.getStringExtra("SellDetailStorageType");
		downloadMinute = intent.getIntExtra("downloadMinute", SLEEP_INTERVAL);
		hasCameraInfo = intent.getBooleanExtra("hasCameraInfo", false);
		isFTN = intent.getBooleanExtra("isFTN", false);
		
//		DBAdapter = new LikDBAdapter(getBaseContext(),true,companyID);
	  DBAdapter = MainMenuActivity.DBAdapter;
	

		String[] tableNameListArray = tableNameList.split(",");
		for(int i=0;i<tableNameListArray.length;i++) {
			alTableName.add(tableNameListArray[i]);
		}
		
		alTableName.add(PhotoProject.TABLE_NAME);
		alTableName.add(PhotoProjectState.TABLE_NAME);
		//alTableName.add(Company.TABLE_NAME);
		alTableName.add(Phrase.TABLE_NAME);
		 
		if(alTableName.size()==0) {
			isAlive = false;
			sendBroadcast(Constant.SUCCESS+Constant.XMPP_SEPERATOR+getBaseContext().getResources().getString(R.string.Message11));
			return;
		}
		
		// start init job
		try {
			startDownloadData();
			// make service alive for callback
			int loop = 0;
			while(isAlive) {
				Thread.sleep(downloadMinute);
				if(count == countLast) {
					if(loop>=LOOP_BEFORE_STOP) {
						Log.d(TAG, "Warning, no data download in last 180 seconds!, ending service loop...");
						// �����U��
						isAlive = false;
						// �i�D�D���̫�@��������
						if(detail != null) {
							int downloadSeq = Integer.parseInt(detail.get("DownloadSeq"));
							String url = "http://"+ip+":"+httpPort+lastReceivedDataURI+"?userNo="+accountNo+"&tableName="+tableName+"&downloadSeq="+downloadSeq+"&siteName="+siteName+"&systemNo="+getResources().getText(R.string.app_code).toString();
							Log.d(TAG,"connecting..."+url);
							String result = HttpUtil.httpConnect(url);
							Log.d(TAG,"XMPP result:"+result);
						}
						sendBroadcast(Constant.FAIL+Constant.XMPP_SEPERATOR+getBaseContext().getResources().getString(R.string.Message15));
					}
				} 
				else 
					loop = 0;
				
				countLast = count;
				loop++;
				int tmpKeepRecordCount = count/KEEP_DOWNLOAD_RECORD_INTERVAL;
				if(tmpKeepRecordCount>keepRecordCount) {
					// �i�D�D���̫�@��������
					if(detail != null) {
						int downloadSeq = Integer.parseInt(detail.get("DownloadSeq"));
						String url = "http://"+ip+":"+httpPort+lastReceivedDataURI+"?userNo="+accountNo+"&tableName="+tableName+"&downloadSeq="+downloadSeq+"&siteName="+siteName+"&systemNo="+getResources().getText(R.string.app_code).toString();
						Log.d(TAG,"connecting..."+url);
						String result = HttpUtil.httpConnect(url);
						Log.d(TAG,"XMPP result:"+result);
					}					
				}
				keepRecordCount = tmpKeepRecordCount;
			}
		} catch(Exception ioe) {
			Log.e(TAG, ioe.getMessage());
			isAlive = false;
			sendBroadcast(RESULT_CONNECT_ERROR);
		}
	}

	@Override
	public void onDestroy () {
		isAlive = false;
		super.onDestroy();
		Log.i(TAG, "onDestroy called");
		// service destroy, disconnect XMPP connection 2012/4/24
		if(xmppUtil != null && xmppUtil.isConnect()) {
			xmppUtil.disConnectViaXMPP();
		}
//		DBAdapter.endTransaction();
	}


	private void startDownloadData() throws IOException,XMPPException {
			
		Log.d(TAG, "host:"+ip);
		Log.d(TAG, "siteName:"+siteName);
		Log.d(TAG, "httpPort:"+httpPort);
		Log.d(TAG, "XMPPPort:"+XMPPPort);
		Log.d(TAG, "CheckDownloadFlagURI:"+sCheckDownloadFlagURI);
		Log.d(TAG, "DownloadURI:"+sDownloadURI);
		Log.d(TAG, "DownloadFinishURI:"+sDownloadFinishURI);
		Log.d(TAG, "accountNo:"+accountNo);
		Log.d(TAG, "tableNameList:"+tableNameList);
		Log.d(TAG, "isOnlyInsert:"+isOnlyInsert);
		Log.d(TAG, "lineDate:"+lineDate);
		Log.d(TAG, "companyID:"+companyID);
		Log.d(TAG, "isContinue:"+isContinue);
		Log.d(TAG, "historyPeriod:"+historyPeriod);
		Log.d(TAG, "SellDetailStorageType:"+SellDetailStorageType);
		Log.d(TAG, "downloadMinute:"+downloadMinute);
		
		 ���M���A�令�b�n�J��,�R���W�L7�Ѥw�W�Ǫ�
		// clear OrderDetail
		OrderDetail omOD = new OrderDetail();
		omOD.setUserNO(accountNo);
		omOD.setCompanyID(companyID);
		omOD.deleteUploadedOrderDetailByUserNo(DBAdapter);
		// ���ڸ��
		OrderCheck omOCK = new OrderCheck();
		if(omOCK.testTableExists(DBAdapter)) {
			omOCK.setUserNO(accountNo);
			omOCK.setCompanyID(companyID);
			omOCK.deleteUploadedOrderCheckByUserNo(DBAdapter);
		}
		// ���ڸ��
		OrderReceive omOR = new OrderReceive();
		if(omOR.testTableExists(DBAdapter)) {
			omOR.setUserNO(accountNo);
			omOR.setCompanyID(companyID);
			omOR.deleteUploadedOrderReceiveByUserNo(DBAdapter);
		}
		// clear Orders
		Orders omO = new Orders();
		omO.setUserNO(accountNo);
		omO.setCompanyID(companyID);
		omO.deleteUploadedOrdersByUserNo(DBAdapter);
		
		
		xmppUtil.connectViaXMPP(ip,Integer.parseInt(XMPPPort),siteName);
		Log.i(TAG, "XMPP connected!");
		// connect to server via http to check download availability
		String result,url;
		// request for download
		clearTable(alTableName.get(0));
		url = "http://"+ip+":"+httpPort+sDownloadURI+"?userNo="+accountNo+"&companyID="+companyID+"&siteName="+siteName+"&systemNo="+getResources().getText(R.string.app_code).toString()+"&lineDate="+lineDate+"&tableName="+alTableName.get(0); // ���Ĥ@��table�e
		Log.d(TAG,"url:"+url);
		result = HttpUtil.httpConnect(url);
		Log.d(TAG,"HTTP result:"+result);
		if(result.startsWith(Constant.FINISH)) {
			sendBroadcast(getBaseContext().getResources().getString(R.string.Message10));
		} else {
			// authetication error
			sendBroadcast(Constant.ERROR_AUTHETICATE_FAIL,getBaseContext().getResources().getString(R.string.Message41));
			isAlive = false;
		}

	}
	
	private void clearTable(String tableName) {
		// 若只新增，確認table都是空的
//		if(!isContinue) {
		if(true) { // 都做清除，以避免資料重複
			try {
				@SuppressWarnings("unchecked")
				Class<BaseOM<?>> c = (Class<BaseOM<?>>) Class.forName(OM_PACKAGE_NAME+tableName);
				BaseOM<?> om = c.newInstance();	
				om.setTableCompanyID(MainMenuActivity.currentDept.getCompanyID());
				om.setCompanyParent(MainMenuActivity.omCurrentSysProfile.getCompanyNo());
				Log.d(TAG,"abce="+	om.getTableCompanyID());
			
				if(om.testTableExists(DBAdapter)) {
					boolean isSuccess = om.deleteAllData(DBAdapter,accountNo);
					if(isSuccess) 
						Log.i(TAG,"delete all data in "+tableName+" success!");
					else 
						Log.i(TAG,"delete all data in "+tableName+" failed!");
				} else {
					Log.i(TAG, "creating table..."+tableName);
		    		String cmd = om.getDropCMD();
		    		Log.i(TAG, "getDropCMD..."+cmd);
		    		if(cmd != null) DBAdapter.getdb().execSQL(cmd);	
		    		cmd = om.getCreateCMD();
		    		Log.i(TAG, "getCreateCMD..."+cmd);
		    		if(cmd != null) DBAdapter.getdb().execSQL(cmd);	
	    			String[] cmds = om.getCreateIndexCMD();
	    			for(int j=0;j<cmds.length;j++) {
	    				cmd = cmds[j];
	    				DBAdapter.getdb().execSQL(cmd);	
	    			}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} 
		}

	}
	
	private void sendBroadcast(String msg) {
		sendBroadcast(Constant.SUCCESS,msg);
	}

	private void sendBroadcast(String code, String msg) {
		//---send a broadcast to inform the activity 
		Intent broadcastIntent = new Intent();
		broadcastIntent.putExtra(RESULT, msg);
		broadcastIntent.putExtra(CODE, code);
		broadcastIntent.setAction(LIKSYS_COREDATA_DOWNLOAD_ACTION);            
		getBaseContext().sendBroadcast(broadcastIntent);								
	}
	
	public void callBack(String message) {
		// handle XMPP message
		OmUtil util = OmUtil.getInstance();
		Map<String,Map<String,String>> map = new TreeMap<String,Map<String,String>>();
		try {
			if(message.startsWith(Constant.XMPP_HEADER)) {
				DBAdapter.getdb().beginTransaction();
				DBAdapter.getdb().setLockingEnabled(false);
				util.toMap(map, message);
				String s = map.get(Constant.XMPP_HEADER).get(Constant.XMPP_HEADER);
				Log.d(TAG,"get data from XMPP, HEADER:"+s);
				String[] split = s.split("="); 
				if(split.length==2) {
					// 回覆table download size %
					String[] split2 = split[1].split(","); // total size,flag(0=開始 1=續傳)
					if(split2[1].equals("0")) {
						tmTotalSize.put(split[0], Integer.parseInt(split2[0]));
						tmActSize.put(split[0], 0);
						//sendBroadcast(MainMenuActivity1.BROADCAST_HEADER_PROGRESS+0);
					}
					progressInterval = tmTotalSize.get(split[0])/100<1?1:tmTotalSize.get(split[0])/100;
					Log.i(TAG,"totalSize="+tmTotalSize.get(split[0]));
					tmRoundSize.put(split[0], 0);
					sendBroadcast(split[0]+getBaseContext().getResources().getString(R.string.Message10));
				}
				return;
			}
			if(message.startsWith(Constant.XMPP_FOOTER)) {
				DBAdapter.getdb().setTransactionSuccessful();
				DBAdapter.getdb().endTransaction();
				DBAdapter.getdb().setLockingEnabled(true);
				util.toMap(map, message);
				String url,result;
				String s = map.get(Constant.XMPP_FOOTER).get(Constant.XMPP_FOOTER);
				Log.d(TAG,"get data from XMPP, FOOTER:"+s);
				String[] split = s.split("=");
				if(split.length==2) {
					String[] split2 = split[1].split(","); // actual download size,flag(0=已結束 1=未結束)
					int expSize = Integer.parseInt(split2[0]);
					if(expSize==0) { // 該table已無資料可下傳，直接抓下一個table
						alTableName.remove(split[0]);
					//	sendBroadcast(MainMenuActivity1.BROADCAST_HEADER_UPDATE_TABLELIST+split[0]);
						if(alTableName.size()==0) {
							// 結束下載
							isAlive = false;
							sendBroadcast(Constant.SUCCESS+Constant.XMPP_SEPERATOR+getBaseContext().getResources().getString(R.string.Message11));							
							return;
						}
						// request for next table download
						clearTable(alTableName.get(0));
						if(alTableName.get(0).equals(Company.TABLE_NAME)) {
							DBAdapter.getdb().execSQL("delete from "+UserCompy.TABLE_NAME);
						}
						//if(alTableName.get(0).equals(SellDetail.TABLE_NAME)) lineDate = String.valueOf(historyPeriod);
						url = "http://"+ip+":"+httpPort+sDownloadURI+"?userNo="+accountNo+"&companyID="+companyID+"&siteName="+siteName+"&systemNo="+getResources().getText(R.string.app_code).toString()+"&lineDate="+lineDate+"&tableName="+alTableName.get(0);
						Log.i(TAG,"DownloadURI="+url);
						result = HttpUtil.httpConnect(url);
						Log.i(TAG,"HTTP result(next table download):"+result);
						return;
					}
					Log.i(TAG,"roundSize:expSize"+tmRoundSize.get(split[0])+":"+expSize);
					// 比對資料筆數是否正確，並送 DownloadFinishURI 更新後台DataDownloadTrace該筆資料已下載
					if(tmRoundSize.get(split[0])==expSize) {
						int downloadSeq = Integer.parseInt(detail.get("DownloadSeq"));
						url = "http://"+ip+":"+httpPort+lastReceivedDataURI+"?userNo="+accountNo+"&tableName="+tableName+"&downloadSeq="+downloadSeq+"&siteName="+siteName+"&systemNo="+getResources().getText(R.string.app_code).toString();
						Log.i(TAG,"lastReceivedDataURL="+url);
						result = HttpUtil.httpConnect(url);
						HttpMessage msg = new HttpMessage();
						if(msg.parseMessage(result)) {
							if(msg.getReturnCode().equals(Constant.SUCCESS)) {
								if(split2[1].equals("0")) {
									if(tmActSize.get(split[0]).intValue()==tmTotalSize.get(split[0]).intValue()) {
										
										Log.i(TAG,"table finished, act size="+tmActSize.get(split[0]).intValue());
										
										// notify server 
										url = "http://"+ip+":"+httpPort+sDownloadFinishURI+"?userNo="+accountNo+"&siteName="+siteName+"&systemNo="+getResources().getText(R.string.app_code).toString()+"&tableName="+split[0]+"&companyID="+companyID;
										Log.i(TAG,"DownloadFinishURI="+url);
										result = HttpUtil.httpConnect(url);
										Log.i(TAG,"HTTP result:"+result);
										if(msg.parseMessage(result)) {
											if(msg.getReturnCode().equals(Constant.SUCCESS))
												sendBroadcast(split[0]+getBaseContext().getResources().getString(R.string.Message11));
										}														

										// request for next table download
//										if(alTableName.get(0).equals(SellDetail.TABLE_NAME)) lineDate = String.valueOf(historyPeriod);
//										url = "http://"+ip+":"+httpPort+sDownloadURI+"?userNo="+accountNo+"&companyID="+companyID+"&siteName="+siteName+"&systemNo="+getResources().getText(R.string.app_code).toString()+"&lineDate="+lineDate+"&tableName="+alTableName.get(0)+"&downloadSeq=-1"; // downloadSeq is dummy
//										Log.i(TAG,"DownloadURI="+url);
//										result = HttpUtil.httpConnect(url);
//										Log.i(TAG,"HTTP result(next table download):"+result);
										alTableName.remove(split[0]);
	
										if(alTableName.size()==0) {
											// 結束下載
											Log.i(TAG,"testabcde");
											isAlive = false;
											//getHTTPBase64Pict();
											ResPict omResPict = new ResPict();
											if(omResPict.testTableExists(DBAdapter)) {
												DBAdapter.getdb().execSQL("delete from "+omResPict.getTableName());	
												Log.i(TAG, "delete from"+omResPict.getTableName());
											} else {
												Log.i(TAG, "creating table..."+omResPict.getTableName());
									    		String cmd = omResPict.getDropCMD();
									    		Log.i(TAG, "getDropCMD..."+cmd);
									    		if(cmd != null) 
									    			DBAdapter.getdb().execSQL(cmd);	
									    		cmd = omResPict.getCreateCMD();
									    		Log.i(TAG, "getCreateCMD..."+cmd);
									    		if(cmd != null) DBAdapter.getdb().execSQL(cmd);	
								    			String[] cmds = omResPict.getCreateIndexCMD();
								    			for(int j=0;j<cmds.length;j++) {
								    				cmd = cmds[j];
								    				DBAdapter.getdb().execSQL(cmd);	
								    			}
											}
											
											new ProcessResImage().execute(ip,httpPort);
											sendBroadcast("ResPict"+getBaseContext().getResources().getString(R.string.Message10));
											//sendBroadcast(Constant.SUCCESS+Constant.XMPP_SEPERATOR+getBaseContext().getResources().getString(R.string.Message11));							
											return;
										}
										// request for next table download
										clearTable(alTableName.get(0));
										if(alTableName.get(0).equals(Company.TABLE_NAME)) {
											DBAdapter.getdb().execSQL("delete from "+UserCompy.TABLE_NAME);
										}
										//if(alTableName.get(0).equals(SellDetail.TABLE_NAME)) lineDate = String.valueOf(historyPeriod);
										url = "http://"+ip+":"+httpPort+sDownloadURI+"?userNo="+accountNo+"&companyID="+companyID+"&siteName="+siteName+"&systemNo="+getResources().getText(R.string.app_code).toString()+"&lineDate="+lineDate+"&tableName="+alTableName.get(0);
										Log.i(TAG,"DownloadURI="+url);
										result = HttpUtil.httpConnect(url);
										Log.i(TAG,"HTTP result(next table download):"+result);
										
									} else {
										Log.e(TAG,"actSize:totalSize"+tmActSize.get(split[0])+":"+tmTotalSize.get(split[0]));
										sendBroadcast(Constant.FAIL+Constant.XMPP_SEPERATOR+split[0]+getBaseContext().getResources().getString(R.string.Message15a));
										// 結束下載
										isAlive = false;
									}
								} else {
									// request for continue download
								//	if(alTableName.get(0).equals(SellDetail.TABLE_NAME)) lineDate = String.valueOf(historyPeriod);
									url = "http://"+ip+":"+httpPort+sDownloadURI+"?userNo="+accountNo+"&companyID="+companyID+"&siteName="+siteName+"&systemNo="+getResources().getText(R.string.app_code).toString()+"&lineDate="+lineDate+"&tableName="+alTableName.get(0)+"&flag=1"+"&downloadSeq=-1"; // downloadSeq is dummy
										
									Log.i(TAG,"DownloadURI="+url);
									result = HttpUtil.httpConnect(url);
									Log.i(TAG,"HTTP result(continue download):"+result);
									
								}
							} else {
								Log.e(TAG,"ReturnCode="+msg.getReturnCode()+", ReturnMessage="+msg.getReturnMessage());
								sendBroadcast(Constant.FAIL+Constant.XMPP_SEPERATOR+split[0]+getBaseContext().getResources().getString(R.string.Message15a));
								// 結束下載
								isAlive = false;
							}
						} else {
							Log.e(TAG,"unknown http result="+result);
							sendBroadcast(Constant.FAIL+Constant.XMPP_SEPERATOR+split[0]+getBaseContext().getResources().getString(R.string.Message15a));
							// 結束下載
							isAlive = false;
						}
					} else {
						Log.e(TAG,"roundSize!=expSize");
						sendBroadcast(Constant.FAIL+Constant.XMPP_SEPERATOR+split[0]+getBaseContext().getResources().getString(R.string.Message15a));
						// 結束下載
						isAlive = false;
					}
				}
				return;
			}
			if(message.startsWith(Constant.XMPP_ECHO_RESPONSE)) {
				// ignore echo message
				return;
			}			
			// body
			String[] split = message.split(MESSAGE_SEPERATOR);
			for(int i=0;i<split.length;i++) {
				util.toMap(map, split[i]);
				Map<String,String> header = map.get(OmUtil.TABLE_HEADER);
				tableName = header.get(OmUtil.TABLE_HEADER);
				detail = map.get(OmUtil.TABLE_DETAIL);
				Log.d(TAG,"insert data into ..."+tableName);
				ProcessDownloadInterface om;
				try {
					if(tmClass.get(OM_PACKAGE_NAME+tableName) == null) {
						@SuppressWarnings("unchecked")
						Class<ProcessDownloadInterface> c = (Class<ProcessDownloadInterface>) Class.forName(OM_PACKAGE_NAME+tableName);
						Log.d(TAG,"getClass");
						om = c.newInstance();
						tmClass.put(OM_PACKAGE_NAME+tableName,om);
						Log.d(TAG,"getClass1");
					} else {
						Log.d(TAG,"getClass2");
						om = tmClass.get(OM_PACKAGE_NAME+tableName);
					}
					Log.d(TAG,"getClass3");
				
					boolean isSucess = om.processDownload(DBAdapter, detail,isOnlyInsert);
					Log.d(TAG,"xxxxxx isSucess="+isSucess);
					count++;
					if(isSucess) {
						int actSize = tmActSize.get(tableName);
						actSize++;
						tmActSize.put(tableName, actSize);
						int roundSize = tmRoundSize.get(tableName);
						roundSize++;
						tmRoundSize.put(tableName,roundSize);
						int percent = 100*actSize/tmTotalSize.get(tableName);
					//	if(actSize%progressInterval==0) sendBroadcast(MainMenuActivity1.BROADCAST_HEADER_PROGRESS+percent);
					} else {
						Log.e(TAG,"can not process data:"+detail);					
					}
				} catch (ClassNotFoundException e) {
					Log.e(TAG,"ClassNotFoundException:"+detail);
				} catch (InstantiationException e) {
					Log.e(TAG,"InstantiationException:"+detail);
				} catch (IllegalAccessException e) {
					Log.e(TAG,"IllegalAccessException:"+detail);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			sendBroadcast(Constant.XMPP_DATA_IOEXCEPTION+Constant.XMPP_SEPERATOR+e.getMessage());
		}
	}
	
	private void getHTTPBase64Pict() {
		Log.d(TAG,"getHTTPBase64Pict Start");
		String url=null; 
		String uri = getResources().getString(R.string.DownloadProductsImagesURI);
		String userNO = MainMenuActivity.currentUserNo;
		int companyID = MainMenuActivity.companyID;
		PhotoProject omPhotoProject = new PhotoProject();
		omPhotoProject.setUserNO(userNO);
		omPhotoProject.setCompanyID(companyID);
		List<PhotoProject> photoPorjectList = omPhotoProject.getProjects(DBAdapter);
		Log.d(TAG,"photoPorjectList size="+photoPorjectList.size());
		if(photoPorjectList.size() > 0)
		{
			for(int i = 0 ; i < photoPorjectList.size() ; i ++)
			{
				omPhotoProject =photoPorjectList.get(0);
				String pNO = omPhotoProject.getProjectNO();
				String yearMonth = omPhotoProject.getYearMonth();
				Log.d(TAG,"id="+i+"pNo="+pNO + " yearMonth="+yearMonth);
				Log.d(TAG,"url="+url);
				 url = "http://"+ip+":"+httpPort+uri+"?userNo="+userNO+"&projectNO="+pNO+"&yearMonth="+yearMonth;
			}
		}
	

		//Thread t1 = new Thread(new SendPost("http://59.126.49.129:8082/goodsamenityweb/struts2/ProcessResourcePictureAction.action?userNo="+userNO+"&projectNO="+pNO+"&yearMonth="+yearMonth,0));
		//Thread t1 = new Thread(new SendPost(url,0));
		//t1.start();
	}
	
    class ProcessResImage extends AsyncTask<String, Integer, String> {  

    	String siteName;
    	String ip;
    	String httpPort;
		@Override
		protected String doInBackground(String... params) 
		{
			Log.d(TAG,"ProcessResImage Start temp="+temp);
			ip = params[0];
			httpPort = params[1];
			String uri = getResources().getString(R.string.DownloadProductsImagesURI);
    		String passMsg = null;
    		String  url =null;
    		String userNO = MainMenuActivity.currentUserNo;
    		int companyID = MainMenuActivity.companyID;
    		UrlEncodedFormEntity ent;
    		ArrayList<NameValuePair> nameValuePairs =new ArrayList<NameValuePair>();
    		PhotoProject omPhotoProject = new PhotoProject();
    		omPhotoProject.setUserNO(userNO);
    		omPhotoProject.setCompanyID(companyID);
    		List<PhotoProject> photoPorjectList = omPhotoProject.getProjects(DBAdapter);
    		maxPhotoProject = photoPorjectList.size();
    		Log.d(TAG,"photoPorjectList size="+photoPorjectList.size() + " countPhotoProject="+countPhotoProject);
    		if(photoPorjectList.size() > 0)
    		{
    			String pNO ="";
    			for(int i = countPhotoProject ; i < photoPorjectList.size() ; i ++)
    			{
    				 //temp=i;
    				  //Log.d(TAG,"id="+i+" temp ="+temp);
    				 omPhotoProject =photoPorjectList.get(i);
    				 if (pNO.equals(omPhotoProject.getProjectNO()))
    				 {
    					 countPhotoProject++;
    					 Log.d(TAG,"id="+i+" countPhotoProject ="+countPhotoProject);
    					 continue; 
    				 }
    				else
    				{
    					 pNO = omPhotoProject.getProjectNO();				
    					 countPhotoProject++;
    					 Log.d(TAG,"id="+i+" countPhotoProject ="+countPhotoProject);
    				}
    				//  Log.d(TAG,"id="+i+" countPhotoProject ="+countPhotoProject);
    				
    				String yearMonth = omPhotoProject.getYearMonth();
    				
    				Log.d(TAG,"id="+i+" pNo="+pNO + " yearMonth="+yearMonth);
    				Log.d(TAG,"url="+url);
    				 url = "http://"+ip+":"+httpPort+uri+"?userNo="+userNO+"&projectNO="+pNO+"&yearMonth="+yearMonth+"&CompanyID="+MainMenuActivity.companyID;
    				 HttpClient httpclient = new DefaultHttpClient();	
    				HttpPost httppost = new HttpPost(url);
    				 try {
    		    	    	Log.d(TAG, "connecting = " + url);
    		    	    	ent = new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8);
    						httppost.setEntity(ent);
    						HttpResponse responsePOST = httpclient.execute(httppost);
    						HttpEntity resEntity = responsePOST.getEntity();
    						passMsg=EntityUtils.toString(resEntity, HTTP.UTF_8);
    						if (passMsg.startsWith("\ufeff")) {
    							passMsg = passMsg.substring(1);
    							}
    						nameValuePairs.clear();
    						//Handler.obtainMessage(0,passMsg).sendToTarget();
    						new Base64Decode(passMsg.toString(),pNO).start();
    		    	        httpclient.getConnectionManager().shutdown();
    		    	        Log.d(TAG,"id="+i+" countPhotoProject ="+countPhotoProject);
    		    	    //    if(countPhotoProject == maxPhotoProject)
    						//	sendBroadcast(Constant.SUCCESS+Constant.XMPP_SEPERATOR+getBaseContext().getResources().getString(R.string.Message11));
    		    	    }
    		    	    catch (Exception e)
    		    	    {
    		    	    	e.printStackTrace();
    		    	    }
    			}
    		}
    	   
    	    Log.d(TAG,"ProcessResImage End");
    	    return passMsg;
    	}
		
		protected void onPostExecute(String result) 
		{
			  Log.d(TAG,"onPostExecute Start");
			  Log.d(TAG,"onPostExecute countPhotoProject ="+countPhotoProject);
			if(countPhotoProject == maxPhotoProject)
			{
				sendBroadcast(Constant.SUCCESS+Constant.XMPP_SEPERATOR+getBaseContext().getResources().getString(R.string.Message11));
				return;
			}
			
		}
    	
    }
   
    class Base64Decode extends Thread{
		String r;
		String projectNo;
		public Base64Decode(String r, String projectNo){
			this.r=r;
			this.projectNo=projectNo;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			synchronized (this)
			{
					try {
						
						Log.d(TAG,"Base64Decode Start");
						// save image file to phone
						FileOutputStream fos = null;
						Map<String, File> externalLocations = ExternalStorage.getAllStorageLocations();
						File externalSdCard = externalLocations.get(ExternalStorage.EXTERNAL_SD_CARD);
						StringBuffer sbDisplayDir = new StringBuffer();
						if(externalSdCard!=null) 
							sbDisplayDir.append(externalSdCard);
						else 
							sbDisplayDir.append(Environment.getExternalStorageDirectory());
						
						sbDisplayDir.append("account/");
						sbDisplayDir.append(projectNo);
						File dir = new File(sbDisplayDir.toString());
						Log.d(TAG,"dir.exists()="+dir.exists());
						Log.d(TAG,"dir="+sbDisplayDir);
						if(!dir.exists()) 
							dir.mkdirs();
						
						StringBuffer sbFileName = new StringBuffer();
					      
	
						JSONObject jsonObject =new JSONObject(r);
				
						JSONArray accountImgName =jsonObject.getJSONArray("accountImgName");
						JSONArray contractImgName =jsonObject.getJSONArray("contractImgName");
						JSONArray accountImg =jsonObject.getJSONArray("accountImgBase64Str");
						//mHandler.post(new DebugPring("accountImgBase64Str Count:"+a.length()+""));
						Log.d(TAG,"testing="+accountImg.length());
						for(int j=0;j<accountImg.length();j++){
							byte[] decodedString = Base64.decode(accountImg.get(j).toString(), Base64.URL_SAFE);
							Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
							Drawable d = new BitmapDrawable(getResources(), decodedByte);
							//sDrawables1.add(d);
							BitmapDrawable bitDw = ((BitmapDrawable) d);
							Bitmap bitmap = bitDw.getBitmap();
							//ByteArrayOutputStream stream = new ByteArrayOutputStream();
							//bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
							//byte[] imageInByte = stream.toByteArray();
							sbFileName.append(accountImgName.get(j));
							sbFileName.append(".jpg");
					       // save into file
							Log.d(TAG,"FileName="+sbFileName+" sbFileName.toString()="+sbFileName.toString());
							File accountImgFile= new File(sbDisplayDir.toString(),sbFileName.toString());
							Log.d(TAG,"createImageFile end="+accountImg.toString());
						    fos = new FileOutputStream(accountImgFile);
						    // Use the compress method on the BitMap object to write image to the OutputStream
							bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
							//add("0",projectNo, imageInByte);
							add("0",projectNo, accountImgFile.getPath());
						}
						
						sbDisplayDir.delete(0, sbDisplayDir.length());
						if(externalSdCard!=null) 
							sbDisplayDir.append(externalSdCard);
						else 
							sbDisplayDir.append(Environment.getExternalStorageDirectory());
						sbDisplayDir.append("contract/");
						sbDisplayDir.append(projectNo);
						
						
						JSONArray b =jsonObject.getJSONArray("contractImgBase64Str");
						Log.d(TAG,"testing123="+b.length());
						for(int k=0;k<b.length();k++){
							byte[] decodedString = Base64.decode(b.get(k).toString(), Base64.URL_SAFE);
							Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
							Drawable d = new BitmapDrawable(getResources(), decodedByte);
						//	sDrawables2.add(d);
							BitmapDrawable bitDw = ((BitmapDrawable) d);
							Bitmap bitmap = bitDw.getBitmap();
							ByteArrayOutputStream stream = new ByteArrayOutputStream();
							bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
							byte[] imageInByte = stream.toByteArray();
							add("1",projectNo, imageInByte);
						}
					//	countPhotoProject++;
	
						Log.d(TAG,"Base64Decode END");
				//	imageFlag=1;
				//	mHandler.post(new DebugPring("活動合約 與 抬帳 下載完成！"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			}
			
			
		}
	}
    
	private void add(String type,String projectNo, String path){
	    ResPict omResPict = new ResPict();
	    omResPict.setpNO(projectNo);
	    omResPict.setType(type);
	    omResPict.setDate(getDate());
	    //omResPict.setBase64(base64);
	    omResPict.setPath(path);
	    omResPict.doInsert(DBAdapter);
	    
	    if(omResPict.getRid()==0)
	    	Log.d(TAG,"ResPict insert success [ProjectNo]"+projectNo);
	    else
	    	Log.d(TAG,"ResPict insert error [ProjectNo]"+projectNo);
    }
	
	public String getDate(){
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		String date = sDateFormat.format(new java.util.Date());
		return date;
	}
}
*/