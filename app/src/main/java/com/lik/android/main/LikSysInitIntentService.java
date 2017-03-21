package com.lik.android.main;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.jivesoftware.smack.XMPPException;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.lik.Constant;
import com.lik.android.main.R;
import com.lik.android.om.Company;
import com.lik.android.om.DailySequence;
import com.lik.android.om.PriorityList;
import com.lik.android.om.SiteIPList;
import com.lik.android.om.SiteInfo;
import com.lik.android.om.SysProfile;
import com.lik.android.om.UserCompy;
import com.lik.android.util.HttpMessage;
import com.lik.android.util.HttpUtil;
import com.lik.android.util.OmUtil;
import com.lik.android.util.XmppCallBack;
import com.lik.android.util.XmppUtil;

public class LikSysInitIntentService extends IntentService implements XmppCallBack{
	
	private static final String TAG = "LikSysInitIntentService";
	public static final String LIKSYS_INIT_ACTION = "LIKSYS_INIT_ACTION";
	public static final int SLEEP_INTERVAL = 1000;
	private static final int LOOP_BEFORE_STOP = 60; // 60 times
	public static final String RESULT = "DATA";
	public static final String RESULT_CONNECT_ERROR = "INFO:CONNECT ERROR";
	public static final String RESULT_NODATA = "INFO:NO DATA NEED DOWNLOAD";

	// XMPP utility
	private final XmppUtil xmppUtil = new XmppUtil(this);

	// keep db reference
	private LikDBAdapter DBAdapter;	
	private static boolean isConnect = false;
	
	// service alive flag
	private boolean isAlive = true;
	// count
	private int count = 0;
	private int countLast = -1;
	
	private SysProfile omCurrentSysProfile;
	private String companyNo;
	String result;
	
	public LikSysInitIntentService() {
        super("LikSysInitIntentService");
    }	

	@Override
	protected void onHandleIntent(Intent intent) {
		DBAdapter = LikSysActivity.DBAdapter;
		String ip = intent.getStringExtra("ip"); // ��x�D����IP
		String siteName = intent.getStringExtra("siteName"); // tablet sitename
		String http_port = intent.getStringExtra("http_port");
		String xmpp_port = intent.getStringExtra("xmpp_port");
		String uri = intent.getStringExtra("uri");
		String accountNo = intent.getStringExtra("accountNo");
		companyNo = intent.getStringExtra("companyNo");
		omCurrentSysProfile = (SysProfile)intent.getSerializableExtra("omCurrentSysProfile");
		Log.d(TAG, "start");
		Toast.makeText(this,"start activity",Toast.LENGTH_SHORT).show();
		
		// start init job
		try 
		{
				startInitData(ip,siteName,http_port,xmpp_port,uri,accountNo);
				// make service alive for callback
				int loop = 0;
				while(isAlive) 
				{
					Thread.sleep(SLEEP_INTERVAL);
					if(count == countLast) {
						if(loop>=LOOP_BEFORE_STOP) {
							 Log.d(TAG, "Warning, data download not finished in last 60 seconds!, ending service loop...");
							 isAlive = false;
							 sendBroadcast(Constant.FAIL+":"+getBaseContext().getResources().getString(R.string.Message15a));
						 }
					 } else 
						 loop = 0;
							
					countLast = count;
					loop++;
				 }
			} catch(Exception ioe) {
					ioe.printStackTrace();
					Log.e(TAG, "error in startInitData"+ioe.fillInStackTrace());
					sendBroadcast(RESULT_CONNECT_ERROR);
				}
		
	}
	
	@Override
	public void onDestroy () {
		isAlive = false;
		isConnect = false;
		super.onDestroy();
		Log.i(TAG, "onDestroy called");
		// service destroy, disconnect XMPP connection 2012/4/24
		if(xmppUtil != null && xmppUtil.isConnect()) {
			xmppUtil.disConnectViaXMPP();
		}
		DBAdapter.closedb();
	}


	private void startInitData(String ip,  String siteName, String httpPort, String XMPPPort, String uri,String accountNo) throws IOException,XMPPException 
	{
			
		Log.d(TAG, "host:"+ip);
		Log.d(TAG, "siteName:"+siteName);
		Log.d(TAG, "httpPort:"+httpPort);
		Log.d(TAG, "XMPPPort:"+XMPPPort);
		Log.d(TAG, "URI:"+uri);
		Log.d(TAG, "accountNo:"+accountNo);

		// �s�Wactivemq topic using XMPP
		if(!isConnect) {
			xmppUtil.connectViaXMPP(ip,Integer.parseInt(XMPPPort),siteName);
			Log.d(TAG, "XMPP connected!");
			isConnect = true;
		}
		// connect to server via http to start download
		// uri sample: "/activemq/struts2/processTabletRequetDataForInit.action?siteName="
		String url = null;
		if(omCurrentSysProfile.getPdaId() == 0) {
			url = "http://"+ip+":"+httpPort+uri+siteName+"&userNo="+accountNo+"&systemNo="+omCurrentSysProfile.getSystemNo();
		} else {
			url = "http://"+ip+":"+httpPort+uri+siteName+"&userNo="+accountNo;
		}
		Log.d(TAG,"connecting..."+url);
		result = HttpUtil.httpConnect(url); // �Y���\�A�^��p
		Log.d(TAG,"result="+result);
		HttpMessage msg = new HttpMessage();
		if(msg.parseMessage(result)) {
			try {
				Integer.parseInt(msg.getReturnMessage());
				Log.d(TAG,"msg="+msg.getReturnMessage());
				omCurrentSysProfile.setPdaId(Integer.parseInt(msg.getReturnMessage()));
			} catch(NumberFormatException pe) {
				Log.i(TAG, "pdaid not needed to update, msg="+msg.getReturnMessage());
			}			
			sendBroadcast(Constant.PDAID+":"+msg.getReturnMessage());
		} else {
			sendBroadcast(result); // ERROR
		}
		
		Log.d(TAG,"XMPP result:"+result);

	}
	
	private void sendBroadcast(String msg) {
		//---send a broadcast to inform the activity 
		Intent broadcastIntent = new Intent();
		broadcastIntent.putExtra(RESULT, msg);
		broadcastIntent.setAction(LIKSYS_INIT_ACTION);            
		getBaseContext().sendBroadcast(broadcastIntent);								
	}
	
	public void callBack(String message) {
		// handle XMPP message
		OmUtil util = OmUtil.getInstance();
		Map<String,Map<String,String>> map = new TreeMap<String,Map<String,String>>();
		try {
			Log.d(TAG,"message="+message);
			util.toMap(map, message);
			if(map.get(Constant.XMPP_HEADER) != null) {
				Log.d(TAG,"get data from XMPP, HEADER:"+map.get(Constant.XMPP_HEADER).get(Constant.XMPP_HEADER));
				return;
			}
			if(map.get(Constant.XMPP_FOOTER) != null) {
				Log.d(TAG,"get data from XMPP, FOOTER:"+map.get(Constant.XMPP_FOOTER).get(Constant.XMPP_FOOTER));
				// update data
				omCurrentSysProfile = omCurrentSysProfile.getSysProfileByPrimaryKey(DBAdapter);
				if(omCurrentSysProfile.getRid()<0) { // ���s�b�A�n�s�W
					omCurrentSysProfile.insertSysProfile(DBAdapter);
					Log.d(TAG,"insert SysProfile success");
				} else {
					omCurrentSysProfile.updateSysProfile(DBAdapter);
					Log.d(TAG,"update SysProfile success");
				}
				omCurrentSysProfile = omCurrentSysProfile.getSysProfileByPrimaryKey(DBAdapter);  
				// clear DailySequence 
				DailySequence omDS = new DailySequence();
				omDS.clear(DBAdapter);				
				// download finish
				sendBroadcast(Constant.FINISH);
				isAlive = false;
				return;
			}
			Map<String,String> header = map.get(OmUtil.TABLE_HEADER);
			String tableName = header.get(OmUtil.TABLE_HEADER);
			Map<String,String> detail = map.get(OmUtil.TABLE_DETAIL);
			Log.d(TAG,"get data from XMPP, table name:"+tableName);
			// insert into tablet DB
			if(tableName.equals(SiteInfo.TABLE_NAME)) {
				SiteInfo omSI = new SiteInfo();
				omSI.setSiteName(detail.get("SiteName"));
				omSI.setParent(detail.get("Parent"));
				omSI.setType(detail.get("Type"));
				omSI.getSiteInfoBySiteName(DBAdapter);
				if(omSI.getRid()<0) {
					omSI.insertSiteInfo(DBAdapter);
					Log.i(TAG,"Table Name:"+tableName);
					Log.i(TAG,"inserted Site Name:"+omSI.getSiteName());
				} else {
					Log.i(TAG,"Table Name:"+tableName);
					Log.i(TAG,"data existed, Site Name:"+omSI.getSiteName());					
				}
			} else if(tableName.equals(SiteIPList.TABLE_NAME)) {
				SiteIPList omSIP = new SiteIPList();
				omSIP.setSiteName(detail.get(SiteIPList.COLUMN_NAME_SITENAME));
				omSIP.setIp(detail.get(SiteIPList.COLUMN_NAME_IP));
				omSIP.setType(detail.get(SiteIPList.COLUMN_NAME_TYPE));
				omSIP.setCompanyParent(companyNo);
				if(detail.get(SiteIPList.COLUMN_NAME_WEBPORT) != null) omSIP.setWebPort(Integer.parseInt(detail.get(SiteIPList.COLUMN_NAME_WEBPORT)));
				if(detail.get(SiteIPList.COLUMN_NAME_QUEUEPORT) != null) omSIP.setQueuePort(Integer.parseInt(detail.get(SiteIPList.COLUMN_NAME_QUEUEPORT)));
				omSIP.findByKey(DBAdapter);
				if(omSIP.getRid()<0) {
					omSIP.insertSiteIP(DBAdapter);
					Log.i(TAG,"Table Name:"+tableName);
					Log.i(TAG,"inserted Site Name:IP->"+omSIP.getSiteName()+":"+omSIP.getIp());
				} else {
					Log.i(TAG,"Table Name:"+tableName);
					Log.i(TAG,"data existed Site Name:IP->"+omSIP.getSiteName()+":"+omSIP.getIp());					
				}
			} else if(tableName.equals(PriorityList.TABLE_NAME)) {
				PriorityList omPL = new PriorityList();
				omPL.setSiteName(detail.get("SiteName"));
				omPL.setPriority(Integer.parseInt(detail.get("Priority")));
				omPL.getPriorityListByPrimaryKey(DBAdapter);
				if(omPL.getRid()<0) {
					omPL.insertPriorityList(DBAdapter);
					Log.i(TAG,"Table Name:"+tableName);
					Log.i(TAG,"inserted Site Name:Priority->"+omPL.getSiteName()+":"+omPL.getPriority());
				} else {
					Log.i(TAG,"Table Name:"+tableName);
					Log.i(TAG,"data existed Site Name:Priority->"+omPL.getSiteName()+":"+omPL.getPriority());					
				}
			} else if(tableName.equals(UserCompy.MAPPING_TABLE_NAME)) { 
				UserCompy omUC = new UserCompy();
				omUC.setCompanyID(Integer.parseInt(detail.get("CompanyID")));
				omUC.setAccountNo(detail.get("UserNO"));
				omUC.setCompanyParent(companyNo);
				omUC.getUserCompyByPrimaryKey(DBAdapter);
				if(omUC.getRid()<0) {
					omUC.insertUserCompy(DBAdapter);
					Log.i(TAG,"Table Name:"+UserCompy.TABLE_NAME);
					Log.i(TAG,"inserted AccountNo:CompanyID->"+omUC.getAccountNo()+":"+omUC.getCompanyID());
				} else {
					Log.i(TAG,"Table Name:"+UserCompy.TABLE_NAME);
					Log.i(TAG,"data existed AccountNo:CompanyID->"+omUC.getAccountNo()+":"+omUC.getCompanyID());
					
				}
				// insert Company
				Company omC = new Company();
				omC.setCompanyParent(companyNo);
				omC.processDownload(DBAdapter, detail, false);
			}
		} catch (IOException e) {
			e.printStackTrace();
			sendBroadcast(Constant.XMPP_DATA_IOEXCEPTION+":"+e.getMessage());
		}
	}
}

