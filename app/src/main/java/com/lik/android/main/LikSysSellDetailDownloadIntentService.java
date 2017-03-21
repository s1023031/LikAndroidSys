package com.lik.android.main;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.jivesoftware.smack.XMPPException;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.lik.Constant;
import com.lik.android.main.R;
import com.lik.android.util.FileUtil;
import com.lik.android.util.HttpUtil;
import com.lik.android.util.XmppCallBack;
import com.lik.android.util.XmppUtil;
import com.lik.android.util.FileUtil;

/**
 * 
 * @author mouwang
 * @deprecated
 */
public class LikSysSellDetailDownloadIntentService extends IntentService implements XmppCallBack {

	private static final String TAG = "LikSysSellDetailDownloadIntentService";
	private static final int SLEEP_INTERVAL = 3000; // 3 secs
	private static final int LOOP_BEFORE_STOP = 60; // 60 times
	public static final String LIKSYS_SELLDETAIL_XMLDOWNLOAD_ACTION = "LIKSYS_SELLDETAIL_XMLDOWNLOAD_ACTION";
	public static final String RESULT = "DATA";
	public static final String RESULT_CONNECT_ERROR = "INFO:CONNECT ERROR";

	// XMPP utility
	private final XmppUtil xmppUtil = new XmppUtil(this);
	
	// service alive flag
	private boolean isAlive = true;

	// count
	private int count = 0;
	private int countLast = -1;

	// input data
	String ip; // ��x�D����IP
	String siteName; // tablet sitename
	String httpPort;
	String XMPPPort;
	String SellDetailXMLDownloadURI;
	String accountNo;
	int companyID;
	int historyPeriod;
	
	public LikSysSellDetailDownloadIntentService() {
        super("LikSysSellDetailDownloadIntentService");
    }

	@Override
	protected void onHandleIntent(Intent intent) {
		// ���oactivity�ǤJ��IP
		ip = intent.getStringExtra("ip"); // ��x�D����IP
		siteName = intent.getStringExtra("siteName"); // tablet sitename
		httpPort = intent.getStringExtra("http_port");
		XMPPPort = intent.getStringExtra("xmpp_port");
		SellDetailXMLDownloadURI = intent.getStringExtra("SellDetailXMLDownloadURI");
		historyPeriod = intent.getIntExtra("historyPeriod", 1);
		accountNo = intent.getStringExtra("accountNo");
		String sCompanyID = intent.getStringExtra("companyID");
		companyID = Integer.parseInt(sCompanyID);

		// start init job
		try {
			startDownloadData();
			// make service alive for callback
			int loop = 0;
			while(isAlive) {
				Thread.sleep(SLEEP_INTERVAL);
				if(count == countLast) {
					if(loop>=LOOP_BEFORE_STOP) {
						Log.d(TAG, "Warning, no data download in last 180 seconds!, ending service loop...");
						// �����U��
						isAlive = false;
					}
				} else loop = 0;
				countLast = count;
				loop++;
			}
		} catch(Exception ioe) {
			ioe.printStackTrace();
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
	}

	String dataDir = null;
	
	private void startDownloadData() throws IOException,XMPPException {
		Log.d(TAG, "host:"+ip);
		Log.d(TAG, "siteName:"+siteName);
		Log.d(TAG, "httpPort:"+httpPort);
		Log.d(TAG, "XMPPPort:"+XMPPPort);
		Log.d(TAG, "SellDetailXMLDownloadURI:"+SellDetailXMLDownloadURI);
		Log.d(TAG, "historyPeriod:"+historyPeriod);
		Log.d(TAG, "companyID:"+companyID);
		
		dataDir = Environment.getExternalStorageDirectory()+getResources().getString(R.string.SellDetalFileDir);
		
		// clear files
        File file = new File(dataDir);
        File[] files = file.listFiles();
        if(files != null) {
	        for(int i=0;i<files.length;i++) {
	        	File tmp = files[i];
	        	if(tmp.isFile()) {
	        		boolean b = tmp.delete();
	        		if(b) Log.i(TAG,tmp.getName()+" deleted!");
	        	} else {
	        		File[] filesDown = tmp.listFiles();
	        		for(File fdown : filesDown) {
	            		boolean b = fdown.delete();
	            		if(b) Log.i(TAG,fdown.getName()+" deleted!");        			
	        		}
	        		boolean b = tmp.delete();
	        		if(b) Log.i(TAG,tmp.getName()+" deleted!");
	        	}
	        }
        }
		// �s�Wactivemq topic using XMPP
		xmppUtil.connectViaXMPP(ip,Integer.parseInt(XMPPPort),siteName);
		Log.i(TAG, "XMPP connected!");
		String result,url;
		url = "http://"+ip+":"+httpPort+SellDetailXMLDownloadURI+"?userNo="+accountNo+"&companyID="+companyID+"&siteName="+siteName+"&systemNo="+getResources().getText(R.string.app_code).toString()+"&historyPeriod="+historyPeriod;
		result = HttpUtil.httpConnect(url);
		Log.d(TAG,"HTTP result:"+result);
		if(result.startsWith(Constant.FINISH)) {
			sendBroadcast(getBaseContext().getResources().getString(R.string.Message10));
		}
		
	}
	
	int totalSize =0;
	int actSize = 0;
	int customerID = 0;
	String sellDate = null;
	
	@Override
	public void callBack(String message) {
		Log.d(TAG,message);	
		if(message.startsWith(Constant.XMPP_ROOT_HEADER)) {
			String[] split = message.split(":");
			if(split.length==2) {
				String[] splitt = split[1].split("=");
				String sCompanyID = splitt[0];
				totalSize = Integer.parseInt(splitt[1]);
			}
			return;
		}
		
		if(message.startsWith(Constant.XMPP_ROOT_FOOTER)) {
			// �����U��
			isAlive = false;
			sendBroadcast(Constant.SUCCESS+Constant.XMPP_SEPERATOR+getBaseContext().getResources().getString(R.string.Message11));	
			return;
		} 
		if(message.startsWith(Constant.XMPP_HEADER)) {
			String[] split = message.split(":");
			if(split.length==2) {
				String[] splitt = split[1].split(",");
				if(splitt.length==2) {
					customerID = Integer.parseInt(splitt[0]);
					sellDate = splitt[1];
				}
			}
			return;
		}
		if(message.startsWith(Constant.XMPP_FOOTER)) {
			actSize++;
			count++;
			int percent = totalSize==0?100:100*actSize/totalSize;
			sendBroadcast(MainMenuActivity.BROADCAST_HEADER_PROGRESS+percent);
			return;
		}
		// write to file
		File file = new File(dataDir+customerID+"/"+sellDate+".xml");
        FileUtil futil = new FileUtil(file,false);
        try {
			futil.write(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sendBroadcast(String msg) {
		//---send a broadcast to inform the activity 
		Intent broadcastIntent = new Intent();
		broadcastIntent.putExtra(RESULT, msg);
		broadcastIntent.setAction(LIKSYS_SELLDETAIL_XMLDOWNLOAD_ACTION);            
		getBaseContext().sendBroadcast(broadcastIntent);								
	}
	

}
