package com.lik.android.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lik.Constant;
import com.lik.android.main.SetupIpPortFragment.TestServer;
import com.lik.android.om.Customers;
import com.lik.android.om.SellDetail;
import com.lik.android.om.SiteIPList;
import com.lik.android.om.SiteInfo;
import com.lik.android.om.SysProfile;
import com.lik.android.util.FileUtil;
import com.lik.android.util.HttpMessage;
import com.lik.android.util.HttpUtil;

@SuppressLint("WrongViewCast")
public class CoreDownloadFragment extends MainMenuFragment {
	
	protected static final String TAG = CoreDownloadFragment.class.getName();
	public static final String DOWNLOAD_TABLE_LIST_KEY = "CoreDownloadFragment.DownloadTableListKey";
	public static final String DOWNLOAD_PROGRESS_KEY = "CoreDownloadFragment.ProgressKey";
	public static final String DOWNLOAD_PROGRESS_STRING_KEY = "CoreDownloadFragment.ProgressStringKey";
	private DBHelper DH = null;
	StringBuffer sb; // keep select download table list
	String testServer ="false";
	String result = "";
	boolean isDownload = false;
	boolean isContinue = false;
	boolean isgbarVis = false;
	boolean isPictUpload = false;
	ListView lv;
	ProgressBar bar;
	TextView tvProgress,downLoadMessage;
	Button b3,b4;
	RelativeLayout rl5,b1,b2;
	LinearLayout linearLayout1;
	RadioGroup rg1;
	SiteIPList omSIP;
	String uri;
	TreeMap<String,String> tm = new TreeMap<String,String>(); // tableName:selectableItem mapping
	TreeMap<String,String> revtm = new TreeMap<String,String>(); // selectableItem:tableNameList mapping
	StringBuffer sbAllTables = new StringBuffer(); // tablename1,tablename2,...
	StringBuffer returnTableList = new StringBuffer(); // �D���^�Щ|��download������table�M�� tablename1,tablename2,...���ΤF
	StringBuffer sbTableList = new StringBuffer();
	ArrayList<String> toDoTableList = new ArrayList<String>(); // �U��table�M��
	TreeMap<String,Integer> lastSeqMap = new TreeMap<String,Integer>(); // �U��������tableName:seq mapping
	View view;
	
	public static MainMenuFragment newInstance() {
        Log.v(TAG, "in CoreDownloadFragment newInstance");

        CoreDownloadFragment mf = new CoreDownloadFragment();

        // Supply index input as an argument.
		Bundle args = new Bundle();
		//args.putInt("index", index);
		mf.setArguments(args);
		return mf;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater,container,savedInstanceState);
		init();
    	view = inflater.inflate(R.layout.main_download21, container, false);
        return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG,"onActivityCreated start!");
		setup_main_download21();
	}
	
	  @Override
	    public void onStop() {
	    	super.onStop();
	        // We need an Editor object to make preference changes.
	        // All objects are from android.context.Context
	        SharedPreferences settings = myActivity.getPreferences(Context.MODE_PRIVATE);
	        SharedPreferences.Editor editor = settings.edit();
	        Log.d(TAG,"saved data="+sb);
	        if(sb != null && sb.length()>0) editor.putString(DOWNLOAD_TABLE_LIST_KEY, sb.toString());
	        editor.putInt(DOWNLOAD_PROGRESS_KEY, bar.getProgress());
	        editor.putString(DOWNLOAD_PROGRESS_STRING_KEY, tvProgress.getText().toString());
	        // Commit the edits!
	        editor.commit();
//	        myActivity.gtv.setVisibility(View.VISIBLE);
	//		myActivity.gtv.setText("");
	        // release wake mode
			try {
				if(myActivity.wl.isHeld()) 
					myActivity.wl.release();
			} catch(Throwable th) {
				// do nothing
			}
			isDownload = false;
	    }
	  
		private void init() {
	        // keep in wake mode
	        PowerManager pm = (PowerManager)myActivity.getSystemService(Context.POWER_SERVICE);
	        myActivity.wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
	        myActivity.wl.acquire();

			SiteInfo omSI = new SiteInfo();
			omSI.setSiteName(DEVICEID);
			omSI.getSiteInfoBySiteName(DBAdapter);
			Log.d(TAG,omSI.getSiteName());

			omSI.setSiteName(omSI.getParent());
			omSI.getSiteInfoBySiteName(DBAdapter);
			Log.d(TAG,"parent:"+omSI.getSiteName());
			omSIP = new SiteIPList();
			omSIP.setSiteName("901");
			omSIP.setType(SiteIPList.TYPE_DOWNLOAD);
			omSIP.setCompanyParent(MainMenuActivity.omCurrentSysProfile.getCompanyNo());
			List<SiteIPList> ltSIP = omSIP.getSiteIPListBySiteNameAndType(DBAdapter);
			
			if(ltSIP.size() > 0)
				omSIP = ltSIP.get(0);	
			else
				Log.d(TAG,"Cannot get IP");
			
			// ���otable mapping���
			ArrayList<String> al = new ArrayList<String>();
			String s = myActivity.getString(R.string.downloadTableNameMapping).trim();
			String[] split = s.split(",");
			for(int i=0;i<split.length;i++) {
				String[] splitt = split[i].split("=");
				if(splitt.length==2) {
					if(myActivity.isFTN || myActivity.isSND) {
						if(splitt[0].equals("SE20")) continue;
					}
					if(!al.contains(splitt[1]))
						al.add(splitt[1]);
					tm.put(splitt[0], splitt[1]);
					if(revtm.get(splitt[1]) == null) {
						StringBuffer sb = new StringBuffer();
						sb.append(splitt[0]).append(",");
						revtm.put(splitt[1], sb.toString());
					} else {
						StringBuffer sb = new StringBuffer();
						sb.append(revtm.get(splitt[1])).append(splitt[0]).append(",");
						revtm.put(splitt[1], sb.toString());
					}
				}
			}
			for(String chName : al) {
				sbAllTables.append(chName).append(",");
			}
			
			openDB();
			SQLiteDatabase db = DH.getReadableDatabase();
			Cursor c=db.query(false,
					"MyPict",				//��ƪ�W��
					new String[] {"_id","_DateTime","_Dir"},	
					"_isUpolad = 'n' and _CompanyParent='"+myActivity.companyParent+"';",				//WHERE
					null, // WHERE ���Ѽ�
					null, // GROUP BY
					null, // HAVING
					null, // ORDOR BY
					null  // ����^�Ǫ�rows�ƶq
					);
			
			
			
			if (c.getCount()>0) {
				Log.d(TAG,"MyPict have data");
				c.moveToFirst();
				isPictUpload=true;
			}else
			{
				Log.d(TAG,"no data");
			}
			closeDB();
			
			Log.d(TAG,sbAllTables.toString());
		}
		
		private void setup_main_download21() {
	    	lv = (ListView)view.findViewById(R.id.main_download21_listView1);
			bar = (ProgressBar)view.findViewById(R.id.main_download21_progressBar1);
	    	uri = myActivity.getString(R.string.ListOfDownloadTableURI);
	    	tvProgress = (TextView)view.findViewById(R.id.main_download21_textView2);
	    	rl5 = (RelativeLayout)view.findViewById(R.id.main_download21_relativeLayout5);
	    	downLoadMessage = (TextView)view.findViewById(R.id.downloadMessage);
	    	linearLayout1 = (LinearLayout)myActivity.findViewById(R.id.global_linearLayout1);
	    	linearLayout1.setVisibility(LinearLayout.GONE);
		   rg1 = (RadioGroup)view.findViewById(R.id.main_download21_radioGroup1);
	    	ListAdapter adapter = new ArrayAdapter<String>(myActivity,android.R.layout.select_dialog_multichoice,sbAllTables.toString().split(","));
			lv.setAdapter(adapter);	
			for(int position=0;position<lv.getCount();position++) {
				lv.setItemChecked(position, true);	
			}
	    	b1 = (RelativeLayout)view.findViewById(R.id.main_download21_button1);
	    	b1.setOnClickListener( new OnClickListener() {
		
						@Override
						public void onClick(View v) {
							Log.d(TAG,"CoreDownLoad dowload btn = "+isDownload);
							
							if(isPictUpload)
							{
								String msg = myActivity.getString(R.string.PictNotUpload);
								AlertDialog dialog = getAlertDialogForMessage(myActivity.getString(R.string.downloadMessage1),msg);
				    			dialog.show();
				    			return;
							}
							
				   			if(isDownload) {
			    				String msg = myActivity.getString(R.string.Message12);
			    				Toast.makeText(myActivity,msg,Toast.LENGTH_SHORT).show();
			        			AlertDialog dialog1 = getAlertDialogForMessage(myActivity.getString(R.string.downloadMessage1),msg);
			        			dialog1.show();
			    				return;
			    			}else{
			    				final AlertDialog alertDialog = getAlertDialogForDownLoad("請確認是否下載資料");
								alertDialog.show();
			    			}
							
							//new ProcessCustomers().execute(omSIP.getIp(),String.valueOf(omSIP.getWebPort()));
						}
			});
	    	b2 = (RelativeLayout)view.findViewById(R.id.main_download21_button2);
	    	b2.setOnClickListener( new OnClickListener() {

						@Override
						public void onClick(View v) {
							Log.d(TAG,"CoreDownLoad cancel btn = "+isDownload);
							
			

							if(isDownload) {
								String msg = myActivity.getString(R.string.Message12);
								Toast.makeText(myActivity,msg,Toast.LENGTH_SHORT).show();
				    			AlertDialog dialog = getAlertDialogForMessage(myActivity.getString(R.string.downloadMessage1),msg);
				    			dialog.show();
								return;
							}
							else
							{
								linearLayout1.setVisibility(1);
					            MainMenuFragment mmf = QueryNotUploadFragment.newInstance();
								myActivity.showMainMenuFragment(mmf);
							}
						
						}
					});			
	    }
	    protected void removeFromToDoTableList(String tableName) {
	    	toDoTableList.remove(tableName);
			Log.d(TAG,"table list after update="+toDoTableList);
	    }
	    
	    protected void putLastSeqMap(String tableName, int seq) {
	    	lastSeqMap.put(tableName, seq);
	    }
	    
	    protected void startDownloadFileListTask(boolean isStart) {
	      	Log.d(TAG,"startDownloadFileListTask start "+ toDoTableList.size());
			ListAdapter adapter = null;
		
	    	if(toDoTableList.size()>0) {
	    		// do XML download if SellDetail exists
	    		if(toDoTableList.contains(SellDetail.TABLE_NAME)&& isStart && myActivity.SellDetailStorageType.equals("XML")) {
	    			//myActivity.gtv.setVisibility(View.VISIBLE);
	    		//	myActivity.gtv.setText(SellDetail.TABLE_NAME+getText(R.string.Message10)); // �M��
	    			Toast.makeText(myActivity, SellDetail.TABLE_NAME+getText(R.string.Message10), Toast.LENGTH_SHORT).show();
					//myActivity.gbar.setVisibility(View.VISIBLE);
	    			isgbarVis=true;
					// list view disable
					lv.setEnabled(false);
					// ����A������ disable
					//b3.setEnabled(false);
				//	b4.setEnabled(false);
					// ����disable
					//b2.setEnabled(false);

					bar.setProgress(0);
	    			bar.setVisibility(View.VISIBLE);
	    			tvProgress.setText(0+"%");
	    			tvProgress.setVisibility(View.VISIBLE);
					// disable other function
				//	myActivity.giv.setEnabled(false);
					//myActivity.giv2.setEnabled(false);
					myActivity.iv3.setEnabled(false);
					myActivity.ivBT4.setEnabled(false);
					myActivity.ivBT5.setEnabled(false);
					///myActivity.btnMsg.setEnabled(false);
					//myActivity.fastBut3.setEnabled(false);
	    			
	    			 //* XMPP version
	    			 
//		    		Intent intent = new Intent(myActivity.getBaseContext(),LikSysSellDetailDownloadIntentService.class);
//		    		intent.putExtra("ip", omSIP.getIp());
//		    		intent.putExtra("siteName", DEVICEID);
//		    		intent.putExtra("http_port", String.valueOf(omSIP.getWebPort()));
//		    		intent.putExtra("xmpp_port", String.valueOf(omSIP.getQueuePort()));
//		    		intent.putExtra("SellDetailXMLDownloadURI", myActivity.getString(R.string.SellDetailXMLDownloadURI));
//		    		intent.putExtra("accountNo", myActivity.omCurrentAccount.getAccountNo());
//		    		intent.putExtra("companyID", String.valueOf(myActivity.currentCompany.getCompanyID()));
//		    		intent.putExtra("historyPeriod", myActivity.omCurrentSysProfile.getHistoryPeriod());
//		    		myActivity.startService(intent);
		    		
		        	
		        	 /* 
		        	  * http/https version
		        	  */
		        	 
					Log.d("xxxxxxx","omSIP.getIp()="+omSIP.getIp());
					Log.d("xxxxxxx","omSIP.getWebPort()="+omSIP.getWebPort());
					Log.d("xxxxxxx","URI1="+(myActivity.isFTN?myActivity.getString(R.string.ListOfSellDetailCustomersURIFTN):myActivity.isSND?myActivity.getString(R.string.ListOfSellDetailCustomersURISND):myActivity.getString(R.string.ListOfSellDetailCustomersURI)));
					Log.d("xxxxxxx","URI2="+(myActivity.isFTN?myActivity.getString(R.string.DetailsOfSellDetailCustomerURIFTN):myActivity.isSND?myActivity.getString(R.string.DetailsOfSellDetailCustomerURISND):myActivity.getString(R.string.DetailsOfSellDetailCustomerURI)));
					Log.d("xxxxxxx","URI3="+(myActivity.isFTN?myActivity.getString(R.string.ProcessSellDetailFinishFTN):myActivity.isSND?myActivity.getString(R.string.ProcessSellDetailFinishSND):myActivity.getString(R.string.ProcessSellDetailFinish)));
					if(myActivity.omCurrentSysProfile.isCloud()) {
						/*new HttpsDownloadXMLFileTask(this).executeOnExecutor(
								AsyncTask.THREAD_POOL_EXECUTOR,
			        			omSIP.getIp(),
			        			String.valueOf(omSIP.getWebPort()),
			        			myActivity.isFTN?myActivity.getString(R.string.ListOfSellDetailCustomersURIFTNCloud):myActivity.isSND?myActivity.getString(R.string.ListOfSellDetailCustomersURISNDCloud):myActivity.getString(R.string.ListOfSellDetailCustomersURICloud),
			        			myActivity.omCurrentAccount.getAccountNo(),
			        			DEVICEID,
			        			String.valueOf(myActivity.currentCompany.getCompanyID()),
			        			String.valueOf(myActivity.omCurrentSysProfile.getHistoryPeriod()),
			        			myActivity.isFTN?myActivity.getString(R.string.DetailsOfSellDetailCustomerURIFTNCloud):myActivity.isSND?myActivity.getString(R.string.DetailsOfSellDetailCustomerURISNDCloud):myActivity.getString(R.string.DetailsOfSellDetailCustomerURICloud),
			        			myActivity.isFTN?myActivity.getString(R.string.ProcessSellDetailFinishFTNCloud):myActivity.isSND?myActivity.getString(R.string.ProcessSellDetailFinishSNDCloud):myActivity.getString(R.string.ProcessSellDetailFinishCloud)
			        					);    						*/
					} else {
						new HttpDownloadXMLFileTask().executeOnExecutor(
								AsyncTask.THREAD_POOL_EXECUTOR,
			        			omSIP.getIp(),
			        			String.valueOf(omSIP.getWebPort()),
			        			myActivity.isFTN?myActivity.getString(R.string.ListOfSellDetailCustomersURIFTN):myActivity.isSND?myActivity.getString(R.string.ListOfSellDetailCustomersURISND):myActivity.getString(R.string.ListOfSellDetailCustomersURI),
			        			myActivity.omCurrentAccount.getAccountNo(),
			        			DEVICEID,
			        			String.valueOf(myActivity.currentDept.getCompanyID()),
			        			String.valueOf(myActivity.omCurrentSysProfile.getHistoryPeriod()),
			        			myActivity.isFTN?myActivity.getString(R.string.DetailsOfSellDetailCustomerURIFTN):myActivity.isSND?myActivity.getString(R.string.DetailsOfSellDetailCustomerURISND):myActivity.getString(R.string.DetailsOfSellDetailCustomerURI),
			        			myActivity.isFTN?myActivity.getString(R.string.ProcessSellDetailFinishFTN):myActivity.isSND?myActivity.getString(R.string.ProcessSellDetailFinishSND):myActivity.getString(R.string.ProcessSellDetailFinish));    	
					}
		    		isDownload = true;    			
	    		} else {
	    			//myActivity.gbar.setVisibility(View.GONE); // reset gbar
	    			isgbarVis=false;
					TreeSet<String> ts = new TreeSet<String>();
					for(String tableName : toDoTableList) {
						if(tm.get(tableName) != null) ts.add(tm.get(tableName));
					}
					String[] downloadList = new String[ts.size()];
					int i=0;
					for(String chName : ts) {
						downloadList[i++] = chName;
					}
					isContinue = true;
					adapter = new ArrayAdapter<String>(myActivity,android.R.layout.select_dialog_multichoice,downloadList);
					lv.setAdapter(adapter); 
					// default ����
					for(int position=0;position<lv.getCount();position++) {
						lv.setItemChecked(position, true);	
					}
					lv.setEnabled(false);
					//b2.setEnabled(true);
					//myActivity.giv2.setEnabled(true);
	    		}
	    	} 
	    else {
	    		//myActivity.gbar.setVisibility(View.GONE); // reset gbar
	    		isgbarVis=false;
				isContinue = false;
				toDoTableList = new ArrayList<String>();
				// no data
				//TextView tv = (TextView)myActivity.findViewById(R.id.global_textView2);
				
				//tv.setText(myActivity.getString(R.string.Message16));
				Toast.makeText(myActivity,myActivity.getString(R.string.Message16),Toast.LENGTH_SHORT).show();
				//ll5.setVisibility(View.INVISIBLE);
				lv.setAdapter(null);
				b1.setEnabled(false);
				//b2.setEnabled(true);
				//b3.setEnabled(false);
				b4.setEnabled(false);
				//myActivity.giv2.setEnabled(true);
				return;    		
	    	}
	    }
	 
	    /**
	     * @deprecated
	     */
	    class DownloadFileListTask extends AsyncTask<String, Integer, String[]> {

	    	HttpMessage msg;
	    	
	    	/**
	    	 * params[0] : ip
	    	 * params[1] : port
	    	 * params[2] : uri
	    	 * params[3] : userNO
	    	 * params[4] : siteName
	   	 */
	    	@Override
	        protected String[] doInBackground(String... params) {
	            // call http protocol�A���oserver������table�idownload list
	    		String[] result = null;
				try {
					String url = "http://"+params[0]+":"+params[1]+params[2]+"?userNo="+params[3]+"&siteName="+params[4]+"&systemNo="+myActivity.getText(R.string.app_code).toString();
					Log.d(TAG,"connecting..."+url);
					String s = HttpUtil.httpConnect(url);
					Log.d(TAG,"http response="+s);
					msg = new HttpMessage();
					if(msg.parseMessage(s)) {
						if(msg.getReturnCode().equals(Constant.SUCCESS)) result = msg.getReturnMessage().split(";");
					} else {
						Log.e(TAG,"parseMessage error="+s);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
	    		return result;
	            //publishProgress(%);
	        }
	    	@Override
	        protected void onProgressUpdate(Integer... progress) {
	            // do nothing
	        }
	    	@Override
	        protected void onPostExecute(String[] result) {
	    		ListAdapter adapter = null;
				//ProgressBar gbar = (ProgressBar)myActivity.findViewById(R.id.global_progressBar1);
				//gbar.setVisibility(View.INVISIBLE);
	            // �N�^�Ǹ�Ʃ�JList View
	    		if(result != null && result.length != 0) {
	    			TreeSet<String> ts = new TreeSet<String>();
	    			for(int i=0;i<result.length;i++) {
	    				if(tm.get(result[i]) != null) ts.add(tm.get(result[i]));
	    				returnTableList.append(result[i]).append(",");
	    			}
	    			Log.d(TAG,"ts="+ts);
	    			String[] downloadList = new String[ts.size()];
	    			int i = 0;
	    			for(Iterator<String> ir=ts.iterator();ir.hasNext();) {
	    				downloadList[i] = ir.next();
	    				i++;
	    			}
	    			isContinue = true;
	    			adapter = new ArrayAdapter<String>(myActivity,android.R.layout.select_dialog_multichoice,downloadList);
	    			lv.setAdapter(adapter);
	    		} else {
	    			isContinue = false;
	    			returnTableList = new StringBuffer();
					if(msg != null && msg.getReturnCode()!= null && msg.getReturnCode().equals(Constant.FLAG_OFF)) {
						// no data
						//TextView tv = (TextView)myActivity.findViewById(R.id.global_textView2);
						//tv.setText(myActivity.getString(R.string.Message16));
						Toast.makeText(myActivity,myActivity.getString(R.string.Message16),Toast.LENGTH_SHORT).show();
						//ll5.setVisibility(View.INVISIBLE);
					} else {
						// error
						TextView tv = (TextView)myActivity.findViewById(R.id.global_textView2);
						tv.setText(myActivity.getString(R.string.Message20));						
					}
	    			lv.setAdapter(null);
	    			Button btn1 = (Button)view.findViewById(R.id.main_download21_button1);
	    			btn1.setEnabled(false);
	    			/*Button btn3 = (Button)view.findViewById(R.id.main_download21_button3);
	    			btn3.setEnabled(false);
	    			Button btn4 = (Button)view.findViewById(R.id.main_download21_button4);
	    			btn4.setEnabled(false);*/
	    			//myActivity.gbar.setVisibility(View.INVISIBLE); // reset gbar
	    			isgbarVis=false;
	    			return;
	    		}
	 
	        	if(isgbarVis) {
	        		Log.d(TAG,"previous download is processing..."+bar.getProgress());
	        		// disable UI 
	                SharedPreferences settings = myActivity.getPreferences(Context.MODE_PRIVATE);
	                String s = settings.getString(DOWNLOAD_TABLE_LIST_KEY, null);
	                if(s != null) {
	            		Log.d(TAG,"previous download is processing...saved data="+s);
	                	String[] tableList = s.split(",");                	
	    				for(int position=0;position<lv.getCount();position++) {
	    					String tableName = lv.getItemAtPosition(position).toString();
	    					for(int i=0;i<tableList.length;i++) {
	    						if(tableName.equals(tableList[i])) {
	    							lv.setItemChecked(position, true);
	    							break;
	    						}
	    					}
	    				}
	                }
	        		bar.setProgress(settings.getInt(DOWNLOAD_PROGRESS_KEY, 0));
	        		bar.setVisibility(View.VISIBLE);
	        		tvProgress.setText(settings.getString(DOWNLOAD_PROGRESS_STRING_KEY, "0%"));
	        		tvProgress.setVisibility(View.VISIBLE);
	                lv.setEnabled(false);
	                b3.setEnabled(false);
	                b4.setEnabled(false);
	                isDownload = true;
	        	}

	    	}
	    
	    }
	    
	    class HttpDownloadXMLFileTask extends AsyncTask<String, Integer, String> {

	    	HttpMessage msg;
	    	
	    	/**
	    	 * params[0] : ip
	    	 * params[1] : port
	    	 * params[2] : uri1
	    	 * params[3] : userNO
	    	 * params[4] : siteName
	    	 * params[5] : companyID
	    	 * params[6] : historyPeriod
	    	 * params[7] : uri2
	    	 * params[8] : uri3
	   	 */
	    	@Override
	        protected String doInBackground(String... params) {
	            // call http protocol�A���oserver������table�idownload list
	    		String result = null;
	    		Log.d("xxxxxxx","1");
				try {
					// clear cache
					//SellDetail.clearCache();
		    		Log.d("xxxxxxx","2");
					
					String dataDir = Environment.getExternalStorageDirectory()+myActivity.getString(R.string.SellDetalFileDir);				
					// clear files
					// find current userNo's customerID list
					Customers omC = new Customers();
					omC.setCompanyID(Integer.parseInt(params[5]));
					omC.setUserNO(params[3]);
					List<Customers> ltC = omC.getCustomersByUserNoCompanyID(DBAdapter);
					TreeSet<String> tsC = new TreeSet<String>();
					for(Customers c : ltC) {
						tsC.add(String.valueOf(c.getCustomerID()));
					}
		    		Log.d("xxxxxxx","3");
					
			        File file = new File(dataDir);
			        File[] files = file.listFiles();
			        if(files != null) {
				        for(int i=0;i<files.length;i++) {
				        	File tmp = files[i];
				        	if(tmp.isFile()) {
				        		boolean b = tmp.delete();
				        		if(b) Log.i(TAG,tmp.getName()+" deleted!");
				        		else Log.w(TAG,tmp.getName()+" can not deleted!");
				        	} else {
				        		if(!tmp.getName().equals(params[5])) continue; // �udelete �ثecompanyID���ɮ�
				        		File[] filesDown = tmp.listFiles();
				        		for(File fdown : filesDown) {
				        			if(!tsC.contains(fdown.getName())) {
				        				Log.d(TAG,"customerID="+fdown.getName()+" skipped!");
				        				continue;
				        			}
				        			if(fdown.isFile()) {
				        				boolean b = fdown.delete();
				        				if(b) Log.i(TAG,fdown.getName()+" deleted!");        			
				        				else Log.w(TAG,fdown.getName()+" can not deleted!");
				        			} else {
				        				File[] filesDown2 = fdown.listFiles();
				        				for(File file2 : filesDown2) {
				        					if(file2.isFile()) {
					        					boolean b = file2.delete();
					        					if(b) Log.i(TAG,file2.getName()+" deleted!");        			
					        					else Log.w(TAG,file2.getName()+" can not deleted!");
				        					} else {
				        						File[] filesDown3 = file2.listFiles();
				        						for(File file3 : filesDown3) {
						        					boolean b = file3.delete();
						        					if(b) Log.i(TAG,file3.getName()+" deleted!");        			
						        					else Log.w(TAG,file3.getName()+" can not deleted!");
				        						}
							        			boolean b = file2.delete();
						        				if(b) Log.i(TAG,file2.getName()+" deleted!");        			
						        				else Log.w(TAG,file2.getName()+" can not deleted!");
				        					}
				        				}			        				
					        			boolean b = fdown.delete();
				        				if(b) Log.i(TAG,fdown.getName()+" deleted!");        			
				        				else Log.w(TAG,fdown.getName()+" can not deleted!");
				        			}
				        		}
				        		boolean b = tmp.delete();
				        		if(b) Log.i(TAG,tmp.getName()+" deleted!");
				        		else Log.w(TAG,tmp.getName()+" can not deleted!");
				        	}
				        }
			        }
		    		Log.d("xxxxxxx","4");
					String url;
					url = "http://"+params[0]+":"+params[1]+params[2];
					// Create a new HttpClient and Post Header
				    HttpClient httpclient = new DefaultHttpClient();
				    HttpPost httppost = new HttpPost(url);
	        	    HttpConnectionParams.setConnectionTimeout(httppost.getParams(), 90000); // timeout 90 secs
	        	    HttpConnectionParams.setSoTimeout(httppost.getParams(), 90000); // timeout 90 secs
				    CookieStore cookieStore = new BasicCookieStore();
				    HttpContext httpContext = new BasicHttpContext();
				    httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
					Log.d(TAG,"connecting..."+url);
			        // Add request data
			        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			        nameValuePairs.add(new BasicNameValuePair("siteName",params[4]));
	    	        nameValuePairs.add(new BasicNameValuePair("systemNo",myActivity.getText(R.string.app_code).toString()));
			        nameValuePairs.add(new BasicNameValuePair("companyID",params[5]));
			        nameValuePairs.add(new BasicNameValuePair("historyPeriod",params[6]));
			        nameValuePairs.add(new BasicNameValuePair("userNo",params[3]));
			        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			        /* 
			         * �d��customers�M��
			         */
			        ResponseHandler<String> responseHandler=new BasicResponseHandler();
			        result = httpclient.execute(httppost,responseHandler,httpContext).trim();
			        Log.d(TAG,result);
	    	        publishProgress(0);
			        String[] splitt = result.split(":");
			        if(splitt.length !=2) {
			        	httpclient.getConnectionManager().shutdown();
			        	publishProgress(100);
			        	return Constant.SUCCESS;
			        }
			        String[] split = splitt[1].split(",");
			        int seq = 0;
			        for(String customerKey : split) {
			        	seq++;
			        	Log.d(TAG,"customerKey="+customerKey);
			        	String[] keypair = customerKey.split("\\.");
			        	String customerID = keypair[0];
			        	String deliverOrder = keypair.length>1?keypair[1]:null;
			           	Log.d(TAG,"customerID="+customerID+",deliverOrder="+deliverOrder);
			           	url = "http://"+params[0]+":"+params[1]+params[7];
			        	httppost = new HttpPost(url);
		        	    HttpConnectionParams.setConnectionTimeout(httppost.getParams(), 60000); // timeout 60 secs
		        	    HttpConnectionParams.setSoTimeout(httppost.getParams(), 60000); // timeout 60 secs
			        	nameValuePairs = new ArrayList<NameValuePair>();
			        	nameValuePairs.add(new BasicNameValuePair("siteName",params[4]));
		    	        nameValuePairs.add(new BasicNameValuePair("systemNo",myActivity.getText(R.string.app_code).toString()));
			        	nameValuePairs.add(new BasicNameValuePair("companyID",params[5]));
			        	nameValuePairs.add(new BasicNameValuePair("customerID",customerID));
			        	nameValuePairs.add(new BasicNameValuePair("userNo",params[3]));
			        	if(deliverOrder!=null) nameValuePairs.add(new BasicNameValuePair("deliverOrder",deliverOrder));
			        	/*
			        	 * �d��customer�������
			        	 */
			        	httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			        	HttpResponse response = httpclient.execute(httppost,httpContext);
			        	HttpEntity entity = response.getEntity();
	        	        if (entity != null) {
	        	            BufferedReader in = new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));  
	        	            OutputStream out = null;
	        	            try {
	        	            	/*
	        	            	 * one customer+date one file version
	        	            	 */
	        	            	// write to data dir
	        	            	String customerDir = dataDir+params[5]+"/";
	        	            	customerDir += customerID;
	        	            	customerDir += "/";
	        	            	if(deliverOrder!=null) {
	            	            	customerDir += deliverOrder;
	            	            	customerDir += "/";
	        	            	}
	        	            	String newLine = in.readLine();
	        	            	if(newLine == null || newLine.equals("")) continue;
	        	            	Log.d(TAG,"first line="+newLine);
	        	            	int idx = newLine.indexOf("<Root>");
	        	            	if(idx >= 0) {
		        	            	String sdate = newLine.substring(0,idx);
		        	            	sdate += ".xml";
		        	            	Log.d(TAG,"dir="+customerDir+sdate);
		        	        		file = new File(customerDir+sdate);
		        	                new FileUtil(file,false); // init dir if needed
		        	            	out = new FileOutputStream(file);
		        	            	StringBuffer sb = new StringBuffer();
		        	            	sb.append("<Root>").append("\n");
		        	            	while(!(newLine= in.readLine()).equals(Constant.MESSAGE_SEPERATOR_SUMMARY) && newLine != null) {
		        	            		if(newLine.indexOf(Constant.MESSAGE_SEPERATOR) >=0) {
		        	            			int idx1 = newLine.indexOf(Constant.MESSAGE_SEPERATOR);
		        	            			int idx2 = newLine.indexOf("<Root>");
		        	            			if(idx1>=0 && idx2>=0) {
		        	            				sdate = newLine.substring(Constant.MESSAGE_SEPERATOR.length(), idx2);
		        	            				sdate += ".xml";
		        	            				out.write(sb.toString().getBytes());
		        	            				out.flush();
		        	            				out.close();
		        	            				out = new FileOutputStream(new File(customerDir+sdate));
		        	            				sb = new StringBuffer();
		        	            				sb.append("<Root>").append("\n");
		        	            				continue;
		        	            			} else {
		        	            				// last one
		        	            				out.write(sb.toString().getBytes());
		        	            				out.flush();
		        	            				out.close();	        	            				
		        	            			}
		        	            		}
		        	            		sb.append(newLine).append("\n");
		        	            	}
		        	            	Log.i(TAG,"receiving summary..."+customerDir+"summary.xml");
		        	            	file = new File(customerDir+"summary.xml");
	        	            		out = new FileOutputStream(file);
		        			        /*
		        			         * �d�ߨC�@customer ������~����
		        			         */
		        	            	while((newLine= in.readLine()) != null) {
		        	            		out.write(newLine.getBytes());
		        	    				out.write("\n".getBytes());
		        	            	}
		            				out.flush();
		            				out.close();	        	            				
	        	            	}       	            	
	        	            	Log.i(TAG,"customer="+customerID+" finished!");
	        	            	if(out != null) out.flush();
	        	            	result = Constant.SUCCESS;
	        	            } catch (RuntimeException ex) {

	        	                // In case of an unexpected exception you may want to abort
	        	                // the HTTP request in order to shut down the underlying
	        	                // connection and release it back to the connection manager.
	        	            	httppost.abort();
	        	                throw ex;

	        	            } finally {
	        	                // Closing the input stream will trigger connection release
	        	                if(in != null) in.close();
	        	                if(out != null) out.close();
	        	            }

	        	        }
	        	        publishProgress(100*seq/split.length);
			        }		        
		    	    // When HttpClient instance is no longer needed,
		    	    // shut down the connection manager to ensure
		    	    // immediate deallocation of all system resources
		            httpclient.getConnectionManager().shutdown();
			    } catch (ClientProtocolException e) {
			    	Log.e(TAG,e.fillInStackTrace().toString());
			    	e.printStackTrace();
			    	result = e.fillInStackTrace().toString();
			    } catch (IOException e) {
			    	Log.e(TAG,e.fillInStackTrace().toString());
			    	e.printStackTrace();
			    	result = e.fillInStackTrace().toString();
	            } finally {
	            	// clean server side temp table
	            	try {
		            	HttpClient httpclient = new DefaultHttpClient();
			        	String url = "http://"+params[0]+":"+params[1]+params[8];
			        	HttpPost httppost = new HttpPost(url);
		        	    HttpConnectionParams.setConnectionTimeout(httppost.getParams(), 60000); // timeout 60 secs
		        	    HttpConnectionParams.setSoTimeout(httppost.getParams(), 60000); // timeout 60 secs
			        	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			        	nameValuePairs.add(new BasicNameValuePair("siteName",params[4]));
		    	        nameValuePairs.add(new BasicNameValuePair("systemNo",myActivity.getText(R.string.app_code).toString()));
			        	httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			        	BasicResponseHandler responseHandler=new BasicResponseHandler();
				        String res = httpclient.execute(httppost,responseHandler).trim();
				        Log.i(TAG,"clean table result="+res);
				        httpclient.getConnectionManager().shutdown();
	            	} catch(Exception e) {
	            		e.printStackTrace();
	            	}

			    }
	    		return result;
	        }
	    	
	    	@Override
	        protected void onProgressUpdate(Integer... progress) {
				bar.setProgress(progress[0]);
				tvProgress.setText(progress[0]+"%");
	        }
	    	
	    	@Override
	        protected void onPostExecute(String result) {
		        // invisible bar
		        bar.setVisibility(View.INVISIBLE);
		        tvProgress.setVisibility(View.INVISIBLE);
		        if(result != null) {
		        	if(result.equals(Constant.SUCCESS)) {
		        		//removeFromToDoTableList(SellDetail.TABLE_NAME);
		        		//myActivity.gtv.setVisibility(View.VISIBLE);
		        		//myActivity.gtv.setText(getText(R.string.Message11));	 
		        		Toast.makeText(myActivity,getText(R.string.Message11),Toast.LENGTH_SHORT).show();
		        		
		        	} else {
		        		//myActivity.gtv.setVisibility(View.VISIBLE);
		    	       // myActivity.gtv.setText(result);	 
		        		Toast.makeText(myActivity,result,Toast.LENGTH_SHORT).show();
		        	}
		        }
		        isDownload = false;
		        startDownloadFileListTask(false);
		        Log.i(TAG,result);
	    		Log.i(TAG,"HttpDownloadXMLFileTask finished");
	     	}
	    
	    }
	    
		private AlertDialog getAlertDialogForDownLoad(String title) {
	    	Builder builder = new AlertDialog.Builder(getActivity());
	    	builder.setTitle(title);

	    	String msg = getResources().getString(R.string.Button1);
	    	builder.setPositiveButton(msg, new DialogInterface.OnClickListener() {
	    		@Override
	    		public void onClick(DialogInterface dialog, int which) {
		    	    try{
		    	    	downLoadMessage.setText("驗證中，請稍後");
	    				new ProcessCompany().execute(omSIP.getIp(),String.valueOf(omSIP.getWebPort()),DEVICEID);
		    	    }catch(Exception e)
		    	    {
		    	    	e.printStackTrace();
		    	    }
	    		}
	    	});
	    	
	    	msg = getResources().getString(R.string.Button2);
	    	builder.setNegativeButton(msg, new DialogInterface.OnClickListener() {
	    		@Override
	    		public void onClick(DialogInterface dialog, int which) {
	    			dialog.cancel();
	    		}
	    	});
	    	return builder.create();
	    }
		
		private void processDownload()
		{
			if(isDownload) {
				String msg = myActivity.getString(R.string.Message12);
				Toast.makeText(myActivity,msg,Toast.LENGTH_SHORT).show();
    			AlertDialog dialog = getAlertDialogForMessage(myActivity.getString(R.string.downloadMessage1),msg);
    			dialog.show();
				return;
			}
			if(myActivity.currentDept == null || myActivity.currentDept.getCompanyID()==0 || DBAdapter.getCompanyID()==0) {
				String msg = myActivity.getString(R.string.Message21);
				Toast.makeText(myActivity,msg,Toast.LENGTH_LONG).show();
    			AlertDialog dialog = getAlertDialogForMessage(myActivity.getString(R.string.downloadMessage1),msg);
    			dialog.show();
				return;
			}		
    		isDownload = true;
			sb = new StringBuffer();
			SparseBooleanArray sba = lv.getCheckedItemPositions();
			for(int position=0;position<lv.getCount();position++) {
				if(sba.get(position)) {
					Log.d(TAG,"selected table:"+lv.getItemAtPosition(position));
					sb.append(lv.getItemAtPosition(position)).append(",");
				}
			}
			if(sb.length()==0) {
				String msg = myActivity.getString(R.string.Message26);
				Toast.makeText(myActivity,msg,Toast.LENGTH_LONG).show();
    			AlertDialog dialog = getAlertDialogForMessage(myActivity.getString(R.string.downloadMessage1),msg);
    			dialog.show();
				return;							
			}

			if(myActivity.isWHU != true && myActivity.isQHU != true) {
			/*	Orders omO = new Orders();
				omO.setUserNO(myActivity.omCurrentAccount.getAccountNo());
				omO.setCompanyID(myActivity.currentCompany.getCompanyID());
				omO.setUploadFlag(Orders.UPLOADFLAG_N);
				List<Orders> ltO = omO.getOrdersByUserNO(DBAdapter);
				if(ltO.size()>0) {
					String msg = myActivity.getString(R.string.downloadMessage2);
        			AlertDialog dialog = getAlertDialogForMessage(myActivity.getString(R.string.downloadMessage1),msg);
        			dialog.show();
    				return;							
				}*/
			}
			//myActivity.gtv.setVisibility(View.VISIBLE);
			//myActivity.gtv.setText(""); // �M��
			
			// list view disable
			lv.setEnabled(false);
			// set progress bar visible
			bar.setProgress(0);
			bar.setVisibility(View.VISIBLE);;
			tvProgress.setVisibility(View.VISIBLE);
			// ����A������ disable
			//b3.setEnabled(false);
		//	b4.setEnabled(false);
			// ����disable
			//b2.setEnabled(false);
			// set global progress bar visible
			//ProgressBar gbar = (ProgressBar)myActivity.findViewById(R.id.global_progressBar1);
			//gbar.setProgress(0);
			//gbar.setVisibility(View.VISIBLE);
			// disable other function
			//myActivity.giv.setEnabled(false);
			//myActivity.giv2.setEnabled(false);
		//	myActivity.iv3.setEnabled(false);
			//myActivity.ivBT4.setEnabled(false);
			//myActivity.ivBT5.setEnabled(false);
		//	myActivity.btnMsg.setEnabled(false);
			//myActivity.menu.findItem(R.id.mainmenu_item4).setEnabled(false);
			//myActivity.menu.findItem(R.id.mainmenu_item5).setEnabled(false);
			//myActivity.menu.findItem(R.id.mainmenu_item6).setEnabled(false);
			myActivity.menu.findItem(R.id.mainmenu_item1).setEnabled(false);
			//myActivity.menu.findItem(R.id.mainmenu_item3).setEnabled(false);
			//myActivity.menu.findItem(R.id.mainmenu_item7).setEnabled(false);
			Log.d(TAG,omSIP.getIp());
			Log.d(TAG,String.valueOf(omSIP.getWebPort()));
			Log.d(TAG,String.valueOf(omSIP.getQueuePort()));
	
			Calendar cal = Calendar.getInstance();
			Date date = cal.getTime();
			switch(rg1.getCheckedRadioButtonId()) {
			case R.id.main_download21_radio0:
				cal.add(Calendar.DAY_OF_MONTH, -1);
				date = cal.getTime();
				break;
			case R.id.main_download21_radio1:
				break;
			case R.id.main_download21_radio2:
				cal.add(Calendar.DAY_OF_MONTH, 1);
				date = cal.getTime();
				break;
			}
			
    	

    	    	// start download 
        		Intent intent = new Intent(myActivity.getBaseContext(),LikSysCoreDataDownloadAdvViewIntentService.class);
        		intent.putExtra("ip", omSIP.getIp());
        		intent.putExtra("siteName", DEVICEID);
        		intent.putExtra("http_port", String.valueOf(omSIP.getWebPort()));
        		intent.putExtra("xmpp_port", String.valueOf(omSIP.getQueuePort()));
        		intent.putExtra("CheckDownloadFlagURI", myActivity.getString(R.string.CheckDownloadFlagURI));
        		intent.putExtra("DownloadURI", myActivity.getString(R.string.DownloadAdvInitViewURI));
        		intent.putExtra("DownloadFinishURI", myActivity.getString(R.string.DownloadFinishURI));
        		intent.putExtra("LastReceivedDataURI", myActivity.getString(R.string.LastReceivedDataURI));
        		intent.putExtra("accountNo", myActivity.omCurrentAccount.getAccountNo());
        		intent.putExtra("companyID", String.valueOf(myActivity.currentDept.getCompanyID()));
        		intent.putExtra("isContinue", isContinue);
        		intent.putExtra("historyPeriod", myActivity.omCurrentSysProfile.getHistoryPeriod());
        		boolean bCameraInfo = false;
        		if(myActivity.omCurrentSysProfile != null && myActivity.omCurrentSysProfile.getCameraInfo() != null &&myActivity.omCurrentSysProfile.getCameraInfo().equals(SysProfile.CAMERAINFO_Y)) bCameraInfo= true;
        		intent.putExtra("hasCameraInfo", bCameraInfo);
        		intent.putExtra("isFTN", myActivity.isFTN);
        		if(myActivity.omCurrentSysProfile.getDownLoadMinute()!=0) intent.putExtra("downloadMinute", myActivity.omCurrentSysProfile.getDownLoadMinute()*1000);
        		String[] split = sb.toString().split(",");
        		Log.d(TAG,"sb="+sb);
        		if(isContinue) {	
        			sbTableList = new StringBuffer();
    	    		for(String tableName : toDoTableList) {
    	    			sbTableList.append(tableName).append(",");
    	    		}
        			Log.d(TAG,"continueTableNameList="+sbTableList);
        			intent.putExtra("tableNameList", sbTableList.toString());
        		} else {
    	    		StringBuffer realTableNameList = new StringBuffer();
    	    		for(int i=0;i<split.length;i++) {
    	    			Log.d(TAG,"split[i]="+split[i]);
    	    			Log.d(TAG,"revtm.get(split[i])="+revtm.get(split[i]));
    	    			if(revtm.get(split[i])!= null) realTableNameList.append(revtm.get(split[i]));
    	    		}
    	    		String[] aTableName = realTableNameList.toString().split(",");
    	    		for(String tableName : aTableName) {
    	    			toDoTableList.add(tableName);
    	    		}
        			Log.d(TAG,"realTableNameList="+realTableNameList);
        			intent.putExtra("tableNameList", realTableNameList.toString());
        		}
        		intent.putExtra("lineDate", sdf.format(date));
        		myActivity.startService(intent);

		}
		
	    class ProcessCustomers extends AsyncTask<String, Integer, String> {

	    	String siteName;
	    	String ip;
	    	String httpPort;
			@Override
			protected String doInBackground(String... params) 
			{
				Log.d(TAG,"ProcessSiteIP Start");
				ip = params[0];
				httpPort = params[1];
				siteName = params[2];

	    		Log.d(TAG,"siteName="+siteName);
	    		String result = null;
	    		String url = "http://"+ip+":"+httpPort+getResources().getString(R.string.ProcessSiteIPList)+"?siteName="+siteName;
	    	    HttpClient httpclient = new DefaultHttpClient();	
	    	    try {
	    	    	Log.d(TAG, "connectiong = " + url);
	    	    	result = HttpUtil.httpConnect(url);
	    			if (result.startsWith("\ufeff")) {
	    				result = result.substring(1);
						}
	    	        Log.d(TAG,"result = " + result);
	    	        httpclient.getConnectionManager().shutdown();
	    	    }
	    	    catch (Exception e)
	    	    {
	    			String msg = getResources().getString(R.string.Message28);
		/*			TextView tv11 = (TextView)findViewById(R.id.uir003_textView11);
					if(tv11 != null) {
						tv11.setVisibility(View.VISIBLE);
						tv11.setText(msg);
					}*/
	    	    }
	    	    Log.d(TAG,"ProcessSiteIP End");
	    	    return result;
	    	}
			
			protected void onPostExecute(String result) 
			{
				try
				{
					JSONObject jsonObject =new JSONObject(result);
					JSONArray siteIPArray =jsonObject.getJSONArray("siteIPList");
					for( int i = 0 ; i < siteIPArray.length();i++)
					{
						jsonObject = siteIPArray.getJSONObject(i);
						String siteName =  jsonObject.getString("siteName");
						String ip =  jsonObject.getString("ip");
						String type =  jsonObject.getString("type");
						String webPort =  jsonObject.getString("webPort");
						String queuePort =  jsonObject.getString("queuePort");
						SiteIPList omSIP = new SiteIPList();
						omSIP.setSiteName(siteName);
						omSIP.setIp(ip);
						omSIP.setType(type);
						if(webPort != null) omSIP.setWebPort(Integer.parseInt(webPort));
						if(queuePort != null) omSIP.setQueuePort(Integer.parseInt(queuePort));
						omSIP.findByKey(DBAdapter);
						if(omSIP.getRid()<0) {
							omSIP.insertSiteIP(DBAdapter);
							Log.i(TAG,"Table Name: SiteIPList");
							Log.i(TAG,"inserted Site Name:IP->"+omSIP.getSiteName()+":"+omSIP.getIp());
						} else {
							Log.i(TAG,"Table Name: SiteIPList");
							Log.i(TAG,"data existed Site Name:IP->"+omSIP.getSiteName()+":"+omSIP.getIp());					
						}
					}
					
				}	
				catch(Exception e)
				{
					e.printStackTrace();
				}
		
			}
	    	
	    }
		
	    class TestServer extends AsyncTask<String, Integer, String> {

	    	String siteName;
	    	String ip;
	    	String httpPort;
			@Override
			protected String doInBackground(String... params) 
			{
				Log.d(TAG,"TestServer Start");
				ip = params[0];
				httpPort = params[1];
	    		String url = "http://"+ip+":"+httpPort+"/goodsamenityweb";
				//String url = "http://59.126.49.129:8082/goodsamenityweb/";
	    		//String url = "http://stackoverflow.com/about";
	    	    HttpClient httpclient = new DefaultHttpClient();	
	    	    URLConnection connection;
	    		URL myUrl;
	    	    try {
	    	    	 Log.d(TAG,"connecting  = "+url);
	    	    	myUrl = new URL(url);
	                connection = myUrl.openConnection();
	                connection.connect();
	                result = "true";
	    	    }
	    	    catch (Exception e)
	    	    {
	    	    	result = "false";
	    	    }
	    	    testServer = "true";
	    	    Log.d(TAG,"TestServer End = "+result);
	    	    return null;
	    	}

	    }
	    
	    class ProcessCompany extends AsyncTask<String, Integer, String> {

	    	String ip;
	    	String httpPort;
	    	String siteName;
			@Override
			protected String doInBackground(String... params) 
			{
				Log.d(TAG,"ProcessCompany Start");
				ip = params[0];
				httpPort = params[1];
				siteName = params[2];
	    		String result = null;
	    		String url = "http://"+ip+":"+httpPort+getResources().getString(R.string.ProcessCompany)+"?siteName="+myActivity.DEVICEID+"&userNo="+myActivity.omCurrentAccount.getAccountNo()+"?SysID="+myActivity.omCurrentSysProfile.getCompanyNo();
	    		HttpClient httpclient = new DefaultHttpClient();	
	    	    try {
	    	    	Log.d(TAG, "connectiong = " + url);
	    	    	result = HttpUtil.httpConnect(url);
	    			if (result.startsWith("\ufeff")) {
	    				result = result.substring(1);
						}
	    	        Log.d(TAG,"result = " + result);
	    	        httpclient.getConnectionManager().shutdown();
	    	    }
	    	    catch (Exception e)
	    	    {
	    	    	result="false";
	    	    }
	    	    Log.d(TAG,"ProcessCompany End");
	    	    return result;
	    	}
			
			protected void onPostExecute(String result) 
			{
				  Log.d(TAG,"ProcessCompany onPostExecute Start");
				try
				{
					if(result.equals("false"))
					{
		    			//String msg = getResources().getString(R.string.Message28);
		    			//downLoadMessage.setText(msg);
		    			return;	
					}else{
						
						JSONObject jsonObject =new JSONObject(result);
						String errorFlag = (String)jsonObject.get("tabletErrorFlag");
						if(errorFlag.equals("true"))
						{
							String msg = getResources().getString(R.string.	Message43);
							msg+=DEVICEID;
			    			downLoadMessage.setText(msg);
						}else
						{
							Log.d(TAG,"Start to DownLoad");
							processDownload();
						}


						
					}
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			
				  Log.d(TAG,"ProcessCompany onPostExecute End [testServer="+testServer+", resulte="+result+"]");
			}
		}
	    
		
		private void openDB(){
	    	DH = new DBHelper(myActivity.getApplicationContext());  
	    }
		
		  private void closeDB(){
		    	DH.close();    	
		   }
	 
}
