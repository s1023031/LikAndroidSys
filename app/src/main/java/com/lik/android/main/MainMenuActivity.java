package com.lik.android.main;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeSet;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.PowerManager;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.lik.Constant;
import com.lik.android.om.Account;
import com.lik.android.om.BaseOM;
import com.lik.android.om.Company;
import com.lik.android.om.CurrentCompany;
import com.lik.android.om.InstantMessages;
import com.lik.android.om.Phrase;
import com.lik.android.om.SellDetail;
import com.lik.android.om.SiteIPList;
import com.lik.android.om.SysProfile;
import com.lik.android.util.FileUtil;
import com.lik.android.util.HttpMessage;
import com.lik.android.view.DeptNameView;
import com.lik.util.ExternalStorage;
import com.readystatesoftware.viewbadger.BadgeView;


public class MainMenuActivity extends MapActivity implements Runnable{
	
	private boolean isIMEnable = false;
	private DBHelper DH = null;
	protected static final String TAG = MainMenuActivity.class.getName();
	public static final String KEY_SYSPROFILE = SysProfile.class.getName();
	public static final String KEY_ACCOUNT = Account.class.getName();
	public static final String KEY_DEPTID = "KEY_COMPANYID";
	public static final String KEY_LAST_UPLOAD_POSITION = "LastUploadPosition.";	
	public static final String BROADCAST_HEADER_TOAST = "Toast:";
	public static final String BROADCAST_HEADER_PROGRESS = "Progress:"; // progress %
	public static final String BROADCAST_HEADER_UPDATE_TABLELIST = "UpdateTableList:"; // table name
	public static final String BROADCAST_HEADER_PUT_LAST_SEQ = "PutLastSeq:"; // table name = seq
	protected static final String IS_STOP_CALLED_KEY = "IsStopCalledKey";
	public final static Logger log = Logger.getLogger(MainMenuActivity.class.getName());
	
	public static SysProfile omCurrentSysProfile;
	public Account omCurrentAccount;
	public static DeptNameView  currentDept;
	//protected List<SubAddProductsView> cacheSelledProducts; // �w��L��products set	
	protected TreeSet<Long> selledProducts;
	protected int cacheCustomerID;
	protected boolean isStopCalled = false;
	private AlertDialog popMessage = null;

	
	//Animation anim;
	// instant messenger service
	public static final int SERVICE_ID = 1;
	Messenger imService = null;
	boolean imServiceBinded = false;
	boolean isAliveIMS;
	
	IntentFilter intentFilter;
	private int iOrdersBackupFileDays;
	
	public static LikDBAdapter DBAdapter; // the only one instance of the system
	public static String currentUserNo; // for service
	public static String companyDateFormat ="";
	public static int companyID;
	public static String companyNO;
	public static String companyParent;

	String DEVICEID;
	String versionName;
	public String SellDetailStorageType = "DB"; // default
	PowerManager.WakeLock wl,wlp;
	// global UI
	//ProgressBar gbar;
	//public TextView gtv;
	ImageView /*giv,giv2,*/iv3,ivBT4,ivBT5,ivBT6,ivCal;
	//Button btnMsg;
	// menu reference
	Menu menu;
	// map reference
	MapView mapview;
	LocationManager locationManager;
	String provider;
	Location mostRecentLocation;
	public boolean isGPSEnabled =false;
	public boolean isNetworkEnabled = false;
	private boolean isAlive = true; 
	private boolean needBackup = false;
	private final int THREAD_WAIT_INTERVAL = 3600000;
	// FTN special
	public boolean isFTN = false;
	// SHD special
	private String titleOfSHDStatisticsToday;
	// time related
	// HEB special
	public boolean isHEB = false;
	BroadcastReceiver timeChangedReceiver;
	public static long timeDiff = 0;
	public static boolean isNetworkTimeEnabled = false;
	// �B���L
	public boolean isWatermark = false;
	public boolean isImageSizeD = false;
	// ZFS special
	public boolean isZFS = false;
	// SHD special
	public boolean isSHD = false;
	// JYU special
	public boolean isJYU = false;
	// YLN special
	public boolean isYLN = false;
	// XIJ special
	public boolean isXIJ = false;
	// SND special
	public boolean isSND = false;
	// WHU special
	public boolean isWHU = false;
	// XYU special
	public boolean isXYU = false;
	// LIK special
	public boolean isLIK = false;
	// SXI special
	public boolean isSXI = false;
	// QHU special HAO 104.08.25
	public boolean isQHU = false;
	
	
	//private RelativeLayout fastBut1,fastBut2,fastBut3,fastBut4;
	private RelativeLayout fastBut1,fastBut2,fastBut3,fastBut4;
	int selectPoint=0;
	TextView mesgTv;
	BadgeView badge;
	LinearLayout linearLayout1;
	//Button fastBut3;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	Log.d(TAG,"onCreate called!");
    	super.onCreate(savedInstanceState);
    	//setContentView(R.layout.main_set_company);
    	// listen to time change
    	intentFilter = new IntentFilter(Intent.ACTION_TIME_CHANGED);
    	timeChangedReceiver = new DateTimeChangedReceiver();
    	registerReceiver(timeChangedReceiver, intentFilter);    	
    	// listen to wifi change
    	intentFilter = new IntentFilter();
    	intentFilter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
    	registerReceiver(wifiChangedReceiver, intentFilter);    	
    	// listen to network change
    	registerReceiver(networkChangedReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    	
    	if(getResources().getString(R.string.LogExtDirFlag) != null &&
    			getResources().getString(R.string.LogExtDirFlag).equalsIgnoreCase("true")) {
	    	try {
	    		boolean hasFileHandler = false;
	        	for(java.util.logging.Handler handler : log.getHandlers()) {
	        		if(handler instanceof FileHandler) {
	        			hasFileHandler = true;
	        			break;
	        		}
	        	}
	    		if(!hasFileHandler) {
	    			Map<String, File> externalLocations = ExternalStorage.getAllStorageLocations();
	    			File externalSdCard = externalLocations.get(ExternalStorage.EXTERNAL_SD_CARD);
	    			Log.d(TAG,"externalSdCard="+externalSdCard);
	    			if(externalSdCard!=null) {
		            	String LogExtDir = externalSdCard+getResources().getString(R.string.LogExtDir);
		            	String fileName = "/patrol-"+Constant.sqliteDFS.format(getCurrentDate())+".log";
		            	new FileUtil(new File(LogExtDir),true); // create dir if not exits
		            	Log.d(TAG,LogExtDir+fileName);
		    			log.addHandler(new FileHandler(LogExtDir+fileName,true));
	    			}
	    		}
	 		} catch (IOException e) {
				e.printStackTrace();
			}
    	}

    	iOrdersBackupFileDays = Integer.parseInt(getResources().getString(R.string.OrdersBackupFileDays));
    	isWatermark = Boolean.parseBoolean(getResources().getString(R.string.watermark));
    	isImageSizeD = Boolean.parseBoolean(getResources().getString(R.string.imageSizeD));
    	// Restore preferences
        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy�~MM��dd��",Locale.CHINESE);
       // titleOfSHDStatisticsToday = settings.getString(SHDSalesStatisticsFragment.ONE_DATE_KEY, sdf.format(getCurrentDate()));
        isStopCalled = settings.getBoolean(IS_STOP_CALLED_KEY, false);
    	omCurrentSysProfile = (SysProfile)getIntent().getSerializableExtra(KEY_SYSPROFILE);
    	omCurrentAccount = (Account)getIntent().getSerializableExtra(KEY_ACCOUNT);
    	currentDept = (DeptNameView)getIntent().getSerializableExtra(KEY_DEPTID);
    	companyID = currentDept.getCompanyID();
    	companyNO= currentDept.getCompanyNO();
    	currentUserNo = omCurrentAccount.getAccountNo();
    	companyParent = omCurrentSysProfile.getCompanyNo();
    	Log.d(TAG,"omCurrentSysProfile CompanyNo->"+omCurrentSysProfile.getCompanyNo());
    	Log.d(TAG,"omCurrentSysProfile cameraInfo->"+omCurrentSysProfile.getCameraInfo());
    	Log.d(TAG,"omCurrentSysProfile InstantMessengerInfo->"+omCurrentSysProfile.getInstantMessengerInfo());
    	Log.d(TAG,"omCurrentAccount AccountNo->"+omCurrentAccount.getAccountNo());
    	Log.d(TAG,"currentDeptNO->"+currentDept.getCompanyID());
    	Log.d(TAG,"currentDeptNO->"+currentDept.getCompanyNO());
    	//Log.d(TAG,"companyParentl="+companyParent);
    	Log.d(TAG,"isStopCalled="+isStopCalled);
    	Log.d(TAG,"selledProducts is null="+selledProducts);

    	getActionBar().setTitle(currentDept.getCompanyNM());
    	try {
			versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
			int versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
			Log.i(TAG,"versionName="+versionName);
			Log.i(TAG,"versionCode="+versionCode);
		} catch (NameNotFoundException e) {
			Log.w(TAG,"NameNotFoundException",e);
		}
		
    	if(DBAdapter== null) {
    		if(LikSysActivity.DBAdapter != null) {
    			DBAdapter = LikSysActivity.DBAdapter;
    			DBAdapter.setCompanyID(currentDept.getCompanyID());
    			DBAdapter.setCtx(this);
    		}
    		else DBAdapter = new LikDBAdapter(this,true,currentDept.getCompanyID());
    	} else {
    		if(DBAdapter.getCompanyID()==0) DBAdapter.setCompanyID(currentDept.getCompanyID());
    		DBAdapter.setCtx(this);
    	}
    	if(DBAdapter.getCompanyID()==0) {
    		int companyID = settings.getInt(MainMenuActivity.KEY_DEPTID, 13);
    		DBAdapter.setCompanyID(companyID);
    	}
		Log.d(TAG,"DBAdapter.isTransaction()="+DBAdapter.isTransaction());
		
		
        CurrentCompany omCurrentCompany = new CurrentCompany();
        omCurrentCompany.getCurrentCompany(DBAdapter);
        if(omCurrentCompany.getRid() == -1) //no record insert data
        {
        	Log.d(TAG,"Current Company no record");
        	omCurrentCompany.setCompanyName(omCurrentSysProfile.getCompanyNo());
        	omCurrentCompany.setDeptNO(String.valueOf(companyID));
        	omCurrentCompany.setCompanyNO(companyNO);
        	omCurrentCompany.doInsert(DBAdapter);
        	if(omCurrentCompany.getRid() >  -1)
        		Log.d(TAG,"Current Company insert success companyNo="+omCurrentCompany.getCompanyNO());
        	else
        		Log.d(TAG,"Current Company insert error");
        }else{
        	companyParent=omCurrentCompany.getCompanyName();
        	companyID=Integer.parseInt(omCurrentCompany.getDeptNO());
        	companyNO=omCurrentCompany.getCompanyNO();
        	Log.d(TAG,"Cuurent CompanyParent="+companyParent);
        	Log.d(TAG,"Cuurent CompanyID="+companyID);
        	Log.d(TAG,"Cuurent companyNO="+companyNO);
        }
        
        Company omC = new Company();
        omC.setCompanyID(companyID);
        omC.setUserNO(currentUserNo);
        omC.setCompanyParent(companyParent);
        omC= omC.getCompanyByKey(DBAdapter);
        companyDateFormat= omC.getDateFormat();
		
		Calendar calendar = Calendar.getInstance(); // this would default to now
		//calendar.add(Calendar.DAY_OF_MONTH, -14); // get be2 weeks date
	   // int theresholdDay = calendar.get(Calendar.DAY_OF_MONTH);
	 //   Log.d(TAG,"theresholdDay "+theresholdDay);
		List <CheckPagePictItem> checkPageList = new ArrayList<CheckPagePictItem>();
		openDB();
		SQLiteDatabase db = DH.getReadableDatabase();
		Cursor c=db.query(false,
				"MyPict",				//��ƪ�W��
				new String[] {"_id","_DateTime","_Dir"},	
				"_isUpolad = 'n' and _CompanyParent='"+companyParent+"';",				//WHERE
				null, // WHERE ���Ѽ�
				null, // GROUP BY
				null, // HAVING
				null, // ORDOR BY
				null  // ����^�Ǫ�rows�ƶq
				);
		
		
		if (c.getCount()>0) {
			c.moveToFirst();
			Log.d(TAG,"_id "+c.getString(0));
			Log.d(TAG,"Date= "+c.getString(1));
			Log.d(TAG,"Dir= "+c.getString(2));
			checkPageList.add(new CheckPagePictItem(c.getString(0),c.getString(1),c.getString(2))); 
			while(c.moveToNext()){
				Log.d(TAG,"Dir= "+c.getString(2));
				//Toast.makeText(myActivity, ""+c.getString(0), Toast.LENGTH_LONG).show();
				checkPageList.add(new CheckPagePictItem(c.getString(0),c.getString(1),c.getString(2))); 
			}
		}else
		{
			Log.d(TAG,"no data");
		}
		
		if(checkPageList.size() > 0)
		{
			for(int i = 0 ; i < checkPageList.size() ; i ++)
			{		
				String imageDir = checkPageList.get(0)._Dir;
				String imageDateTime = checkPageList.get(0).dateTime;
				Date imageDate = new Date(imageDateTime);	 
				File imageFile = new File(imageDir);
				if(imageFile.exists())
				{
					Log.d(TAG,"file exits");
					int differentDay=differentDaysByMillisecond(imageDate,calendar.getTime());
					Log.d(TAG,"differentDay="+differentDay);
					if(differentDay == 1)
					{
						imageFile.delete();
						if(!imageFile.exists())
						{
							Log.d(TAG,"imageDir ="+imageDir+" delete success");
							db.delete("MyPict", "_Dir='"+checkPageList.get(0)._Dir+"'", null);
						}
					}	
				}
				
			}
		}
		closeDB();
        	

		
		// abnormal case
		if(getFragmentManager() != null) { 
			MainMenuFragment mmf = (MainMenuFragment)getFragmentManager().findFragmentById(R.id.main_frameLayout1);
			if(mmf != null) {
				Log.i(TAG,"zzzzzzzz="+mmf.getClass().getName());
				DBAdapter = new LikDBAdapter(this,true,currentDept.getCompanyID());
			}
		}

		if(omCurrentSysProfile.getCompanyNo().equalsIgnoreCase(Phrase.PHRASE_DESC_FTN)) {
			isFTN = true;
		}
		if(omCurrentSysProfile.getCompanyNo().equalsIgnoreCase(Phrase.PHRASE_DESC_HEB)) {
			isHEB = true;
		}
		if(omCurrentSysProfile.getCompanyNo().equalsIgnoreCase(Phrase.PHRASE_DESC_ZFS)) {
			isZFS = true;
		}
		if(omCurrentSysProfile.getCompanyNo().equalsIgnoreCase(Phrase.PHRASE_DESC_SHD)) {
			isSHD = true;
		}
		if(omCurrentSysProfile.getCompanyNo().equalsIgnoreCase(Phrase.PHRASE_DESC_JYU)) {
			isJYU = true;
		}
		if(omCurrentSysProfile.getCompanyNo().equalsIgnoreCase(Phrase.PHRASE_DESC_YLN)) {
			isYLN = true;
		}
		if(omCurrentSysProfile.getCompanyNo().equalsIgnoreCase(Phrase.PHRASE_DESC_XIJ)) {
			isXIJ = true;
		}
		if(omCurrentSysProfile.getCompanyNo().equalsIgnoreCase(Phrase.PHRASE_DESC_SND)) {
			isSND = true;
		}
		if(omCurrentSysProfile.getCompanyNo().equalsIgnoreCase(Phrase.PHRASE_DESC_WHU)) {
			isWHU = true;
		}
		if(omCurrentSysProfile.getCompanyNo().equalsIgnoreCase(Phrase.PHRASE_DESC_XYU)) {
			isXYU = true;
		}
		if(omCurrentSysProfile.getCompanyNo().equalsIgnoreCase(Phrase.PHRASE_DESC_LIK)) {
			isLIK = true;
		}
		if(omCurrentSysProfile.getCompanyNo().equalsIgnoreCase(Phrase.PHRASE_DESC_SXI)) {
			isSXI = true;
		}
		//HAO 104.08.25
		if(omCurrentSysProfile.getCompanyNo().equalsIgnoreCase(Phrase.PHRASE_DESC_QHU)) {
			isQHU = true;
		}
		DEVICEID = Settings.System.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
    	if(getResources().getString(R.string.Test_Tablet).indexOf(DEVICEID) >=0) {
    		//mapview = new LikMapView(MainMenuActivity.this, getString(R.string.mapApiKeyDebug));
    	} else {
    		//mapview = new LikMapView(MainMenuActivity.this, getString(R.string.mapApiKey));
    	}	
		
    	//mapview.setClickable(true);
    	//mapview.setBuiltInZoomControls(true);
    	locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    	isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    	isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		Log.d(TAG,"isGPSEnabled="+isGPSEnabled);
		Log.d(TAG,"isNetworkEnabled="+isNetworkEnabled);
    	List<String> providers = locationManager.getAllProviders();
		
		// gps is 1st priority
		if(isGPSEnabled) provider = LocationManager.GPS_PROVIDER;
		else if(isNetworkEnabled) provider = LocationManager.NETWORK_PROVIDER;
	    if(provider!=null) {
		    mostRecentLocation = locationManager.getLastKnownLocation(provider);
		    if(mostRecentLocation == null) {
		    	for(String s : providers) {
		    		mostRecentLocation = locationManager.getLastKnownLocation(s);
		    		if(mostRecentLocation != null) {
		    			break;
		    		}
		    	}
		    }
		    Log.i(TAG,"mostRecentLocation="+mostRecentLocation);
		    Log.i(TAG,"mostRecentLocation network="+locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
	    	Log.i(TAG,"provider used="+provider);
	    	Log.d(TAG,"getMapInfo()="+omCurrentSysProfile.getMapInfo());
	    }
	    setContentView(R.layout.main);
    	/*
		  if(omCurrentSysProfile.getButtonAlign()!=null && omCurrentSysProfile.getButtonAlign().equals(SysProfile.BUTTONALIGN_L)) {
			  Log.d(TAG,"asdsasadasda");
		    	setContentView(R.layout.main_l);
		    } else {
		    	setContentView(R.layout.main);
		    }
	    	*/
	    	// show the heap memory used
	    	Runtime rt = Runtime.getRuntime();
	    	long maxMemory = rt.maxMemory();
	    	Log.i(TAG, "maxMemory:" + Long.toString(maxMemory));
	    	Log.i(TAG, "usedMemory: "+Debug.getNativeHeapSize()/ 1048576L);
	    	
	    	// show manufacturer info
	    	Log.i(TAG, "android.os.Build.MANUFACTURER:" + android.os.Build.MANUFACTURER);
	    	Log.i(TAG, "android.os.Build.MODEL:" + android.os.Build.MODEL);
	    	Log.i(TAG, "android.os.Build.PRODUCT:" + android.os.Build.PRODUCT);
	    	Log.i(TAG, "android.os.Build.HARDWARE:" + android.os.Build.HARDWARE);
	    	Log.i(TAG, "android.os.Build.BRAND:" + android.os.Build.BRAND);
	    	Log.i(TAG, "android.os.Build.DEVICE:" + android.os.Build.DEVICE);
	    	Log.i(TAG, "android.os.Build.PRODUCT:" + android.os.Build.PRODUCT);
	    	Log.i(TAG, "density:" + getResources().getDisplayMetrics().density);
	    	Log.i(TAG, "densityDpi:" + getResources().getDisplayMetrics().densityDpi);
	    	log.info("android.os.Build.MANUFACTURER:" + android.os.Build.MANUFACTURER);
	    	log.info("android.os.Build.MODEL:" + android.os.Build.MODEL);
	    	log.info("android.os.Build.PRODUCT:" + android.os.Build.PRODUCT);
	    	log.info("android.os.Build.HARDWARE:" + android.os.Build.HARDWARE);
	    	log.info("android.os.Build.BRAND:" + android.os.Build.BRAND);
	    	log.info("android.os.Build.DEVICE:" + android.os.Build.DEVICE);
	    	log.info("android.os.Build.PRODUCT:" + android.os.Build.PRODUCT);
	    	
			SellDetailStorageType = getResources().getString(R.string.SellDetailStorageType);

			
			
			
	    	FragmentManager.enableDebugLogging(true);
	    	//gtv = (TextView)findViewById(R.id.global_textView2);
	    	//Log.d(TAG,"abcde="+gtv);
	    	//gbar = (ProgressBar)findViewById(R.id.global_progressBar1);
			//giv = (ImageView)findViewById(R.id.global_imageView1);
			//giv2 = (ImageView)findViewById(R.id.global_imageView2);
			
	    	if(currentDept.getCompanyNM() != null) 
	    		Log.d(TAG,"currentCompanyID->"+currentDept.getCompanyNM());
	    	// check if fragment exists
			if(getFragmentManager() != null) { 
				MainMenuFragment mmf = (MainMenuFragment)getFragmentManager().findFragmentById(R.id.main_frameLayout1);
				if(mmf != null) {
					Log.i(TAG,"xxxxxxx="+mmf.getClass().getName());
					// �����`���_�Ammf�|����null�A�ѷӬۥ\��y���A���ɦ^��CameraFragment
			/*		if(mmf instanceof CameraFragment) {
						mmf = CameraFragment.newInstance(R.layout.camera_gallery_body);
					} else {
						mmf = QueryNotUploadFragment.newInstance(R.id.mainmenu_item51);
					}*/
					showMainMenuFragment(mmf);
				} else {
			    	// show entry UI
			    	if(currentDept.getCompanyID() != 0) {
			    		Log.d(TAG,"setCompany id = 0");
			    		mmf = QueryNotUploadFragment.newInstance();
			    		showMainMenuFragment(mmf);
			    	} else {
			    		mmf = SetCompanyFragment.newInstance();
			    		showMainMenuFragment(mmf);
			    	}
					
				}
			}
			

	    	
			fastBut1 = (RelativeLayout)findViewById(R.id.global_fast_but1);
			fastBut2 = (RelativeLayout)findViewById(R.id.global_fast_but2);
			fastBut3 = (RelativeLayout)findViewById(R.id.global_fast_but3);
			fastBut4 = (RelativeLayout)findViewById(R.id.global_fast_but4);
			
        	
			mesgTv = (TextView)findViewById(R.id.global_mesg_tv);
			badge = new BadgeView(this, mesgTv);
			/*
			fastBut1.setBackgroundColor(Color.parseColor("#3F4957"));
			fastBut2.setBackgroundColor(Color.parseColor("#553F4957"));
			fastBut3.setBackgroundColor(Color.parseColor("#553F4957"));
			fastBut4.setBackgroundColor(Color.parseColor("#553F4957"));
			*/
			fastBut1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//Toast.makeText(MainMenuActivity.this, "11111", Toast.LENGTH_SHORT).show();
					MainMenuFragment mmf = QueryNotUploadFragment.newInstance();
					MainMenuActivity.this.showMainMenuFragment(mmf);
					synchronized(MainMenuActivity.this) {
						MainMenuActivity.this.setNeedBackup(true);
						MainMenuActivity.this.notify();
					}
					/*   
					fastBut1.setBackgroundColor(Color.parseColor("#3F4957"));
					fastBut2.setBackgroundColor(Color.parseColor("#553F4957"));
					fastBut3.setBackgroundColor(Color.parseColor("#553F4957"));
					fastBut4.setBackgroundColor(Color.parseColor("#553F4957"));
					*/
				}});
			
			fastBut2.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//Toast.makeText(MainMenuActivity.this, "22222", Toast.LENGTH_SHORT).show();
					MainMenuFragment mmf = Billboard.newInstance();
					MainMenuActivity.this.showMainMenuFragment(mmf);
					synchronized(MainMenuActivity.this) {
						MainMenuActivity.this.setNeedBackup(true);
						MainMenuActivity.this.notify();
					}
					
					/*   
					fastBut2.setBackgroundColor(Color.parseColor("#3F4957"));
					fastBut1.setBackgroundColor(Color.parseColor("#553F4957"));
					fastBut3.setBackgroundColor(Color.parseColor("#553F4957"));
					fastBut4.setBackgroundColor(Color.parseColor("#553F4957"));
					 */
				}});
			
			
			// instant messenger
			/*
	    	anim = new AlphaAnimation(0.0f, 1.0f);
	    	anim.setDuration(1000); //You can manage the time of the blink with this parameter
	    	anim.setStartOffset(20);
	    	anim.setRepeatMode(Animation.REVERSE);
	    	anim.setRepeatCount(Animation.INFINITE);
	    	*/
	    	if(omCurrentSysProfile.getInstantMessengerInfo()!=null && omCurrentSysProfile.getInstantMessengerInfo().equals(SysProfile.INSTANTMESSENGERINFO_Y)) {
	    		isIMEnable = true;
	    	} else {
	    		isIMEnable = false;
	    	}
	    	Log.i(TAG,"isIMEnable="+isIMEnable);
	    	if(isIMEnable) {
	    		fastBut3.setOnClickListener(new OnClickListener() {
	    			@Override
	    			public void onClick(View arg0) {
	    				
							MainMenuFragment mmf = MessageViewFragment.newInstance();
							MainMenuActivity.this.showMainMenuFragment(mmf);
							synchronized(MainMenuActivity.this) {
								MainMenuActivity.this.setNeedBackup(true);
								MainMenuActivity.this.notify();
							}
	    				
						
	    			}

					
	        	});
	    		
	    		// check for unread messages
	        	InstantMessages omIM = new InstantMessages();

	        	if(omIM.testTableExists(DBAdapter)) {
	        		omIM.setUserNo(omCurrentAccount.getAccountNo());
	        		List<InstantMessages> lt = omIM.getMessages(DBAdapter);
	        		Log.d(TAG,"unread messages="+lt.size());
	        		if(lt.size()>0) {	
	                	setBadges(lt.size());
	        		}
	        		else {
	        			setBadges(0);
	        		}
	        	}
	        	
	        	Intent i;
/*	        	if(omCurrentSysProfile.isCloud()) {
	        		i = new Intent(this, InstantMessengerMqttService.class);
					i.putExtra("omCurrentSysProfile", omCurrentSysProfile);
					i.putExtra("omCurrentAccount", omCurrentAccount);
	        		isAliveIMS = InstantMessengerMqttService.isAliveIMS;
	        	} else {*/
	           	 	i= new Intent(this, InstantMessengerService.class); 
	           	 	isAliveIMS = InstantMessengerService.isAliveIMS;
	        //	}
	        	i.putExtra("screen_state", true);
	        	i.putExtra("userNo", omCurrentAccount.getAccountNo());
	        	
	        	if(!isAliveIMS) {
	        		if(DBAdapter!=null) this.startService(i);
	        		this.bindService(i, imConnection, Context.BIND_AUTO_CREATE);
	        		imServiceBinded = true;
	        		isAliveIMS = true;
	        		Log.i(TAG,"InstantMessengerService start!");
	    		} else {
	    			if(!imServiceBinded) {
	    				imServiceBinded = true;
	    				this.bindService(i, imConnection, Context.BIND_AUTO_CREATE);
	    			}
	    		}
	    	}

			
			fastBut4.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//pop up alert dialog if didn't choose customer
					/*String msg = getResources().getString(R.string.subaddnoteMessage1);
        			AlertDialog dialog = getAlertDialogForMessage(getResources().getString(R.string.querynotuploadDialogMessage5),msg);
        			dialog.show();*/
					if(checkOrdersNum()>0){
						
						final ArrayList<Page6Object> list = getOrderList();
						ArrayList<String> sList = new ArrayList<String> ();
						for(int i=0;i<list.size();i++){
							sList.add(list.get(i).name);
						}
						final Dialog dialog;
						Spinner spinner;
						Button but1,but2;
						ArrayAdapter<String> lunchList;   
						dialog = new Dialog(MainMenuActivity.this, R.style.Translucent_NoTitle);
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog.setContentView(R.layout.select_orders);
						
						spinner = (Spinner) dialog.findViewById(R.id.so_sp1);
						String[] lunch = sList.toArray(new String[0]);
						lunchList = new ArrayAdapter<String>(MainMenuActivity.this, android.R.layout.simple_spinner_item, lunch);                                     
						spinner.setAdapter(lunchList);
						but1 = (Button) dialog.findViewById(R.id.so_but1);
						but2 = (Button) dialog.findViewById(R.id.so_but2);
						
						but1.setOnClickListener(new OnClickListener(){

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								MainMenuFragment mmf = ReportFragment.newInstance(list.get(selectPoint));
								MainMenuActivity.this.showMainMenuFragment(mmf);
								synchronized(MainMenuActivity.this) {
									MainMenuActivity.this.setNeedBackup(true);
									MainMenuActivity.this.notify();
								}
								dialog.dismiss();
							}});
						
						but2.setOnClickListener(new OnClickListener(){

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}});
						
						spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

						    @Override
						    public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
						       //Toast.makeText(mContext, "你選的是"+lunch[position], Toast.LENGTH_SHORT).show();
						    	selectPoint = position;
						    }
						    @Override
						    public void onNothingSelected(AdapterView<?> arg0) {
						    }
						});
						
						dialog.show();
						
						
						
					}else{
						Toast.makeText(MainMenuActivity.this, "尚未選擇客戶", Toast.LENGTH_SHORT).show();
					}
				}

				
				private ArrayList<Page6Object> getOrderList(){
					ArrayList<Page6Object> list = new ArrayList<Page6Object>();
					
					SQLiteDatabase db = DBAdapter.getReadableDatabase();
					String coustomersStr="Customers_"+MainMenuActivity.omCurrentSysProfile.getCompanyNo()+MainMenuActivity.this.currentDept.getCompanyID();
					Cursor c2=db.query(false,
							"sqlite_master",				//��ƪ�W��
							new String[] {"name"},	//���W��
							"type='table' AND (name='"+coustomersStr+"' OR name='Orders') ;",				//WHERE
							null, // WHERE ���Ѽ�
							null, // GROUP BY
							null, // HAVING
							null, // ORDOR BY
							null  // ����^�Ǫ�rows�ƶq
							);
					
					
					if(c2.getCount()==2){
					
						Cursor c=db.query(true,
								"Orders , Customers_"+MainMenuActivity.omCurrentSysProfile.getCompanyNo()+MainMenuActivity.this.currentDept.getCompanyID(),				//��ƪ�W��
								new String[] {"ShortName",coustomersStr+".CustomerID","Orders.OrderID"},	//���W��
								coustomersStr+".CustomerID = Orders.CustomerID AND Orders.UserNO = '"+MainMenuActivity.this.omCurrentAccount.getAccountNo()+"';",				//WHERE
								null, // WHERE ���Ѽ�
								null, // GROUP BY
								null, // HAVING
								null, // ORDOR BY
								null  // ����^�Ǫ�rows�ƶq
								);
						
						if (c.getCount()>0) {
							c.moveToFirst();
							//Toast.makeText(myActivity, ""+c.getString(0), Toast.LENGTH_LONG).show();
							list.add(new Page6Object(c.getString(0),c.getString(1),c.getString(2)));
							while(c.moveToNext()){
								//Toast.makeText(myActivity, ""+c.getString(0), Toast.LENGTH_LONG).show();
								list.add(new Page6Object(c.getString(0),c.getString(1),c.getString(2)));
							}
						}
					}else{
						//Toast.makeText(myActivity, "2222  "+c2.getCount(), Toast.LENGTH_SHORT).show();
					}
					
					return list;
					
				}
				
				private int checkOrdersNum() {
					// TODO Auto-generated method stub
					
					int count=0;
					
					SQLiteDatabase db = DBAdapter.getReadableDatabase();
					String coustomersStr="Customers_"+MainMenuActivity.omCurrentSysProfile.getCompanyNo()+MainMenuActivity.this.currentDept.getCompanyID();
					Cursor c2=db.query(false,
							"sqlite_master",				//��ƪ�W��
							new String[] {"name"},	//���W��
							"type='table' AND (name='"+coustomersStr+"' OR name='Orders') ;",				//WHERE
							null, // WHERE ���Ѽ�
							null, // GROUP BY
							null, // HAVING
							null, // ORDOR BY
							null  // ����^�Ǫ�rows�ƶq
							);
					
					
					if(c2.getCount()==2){
						
						//Toast.makeText(myActivity, "1111  "+c2.getCount(), Toast.LENGTH_SHORT).show();
						
						Cursor c=db.query(true,
								"Orders , Customers_"+MainMenuActivity.omCurrentSysProfile.getCompanyNo()+MainMenuActivity.this.currentDept.getCompanyID(),				//��ƪ�W��
								new String[] {"ShortName",coustomersStr+".CustomerID","Orders.OrderID"},	//���W��
								coustomersStr+".CustomerID = Orders.CustomerID AND Orders.UserNO = '"+MainMenuActivity.this.omCurrentAccount.getAccountNo()+"';",				//WHERE
								null, // WHERE ���Ѽ�
								null, // GROUP BY
								null, // HAVING
								null, // ORDOR BY
								null  // ����^�Ǫ�rows�ƶq
								);
						count = c.getCount();
						
					}else{
						//Toast.makeText(myActivity, "2222  "+c2.getCount(), Toast.LENGTH_SHORT).show();
						count=0;
					}
					return count;
				}
			});
			fastBut1.setOnTouchListener(new OnTouchListener(){
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					if(event.getAction()==MotionEvent.ACTION_DOWN){
						v.setBackgroundColor(Color.parseColor("#3F4957"));
						return false;
					}
					if(event.getAction()==MotionEvent.ACTION_UP){
						v.setBackgroundColor(Color.parseColor("#553F4957"));
						
						return false;
					}
					return false;
					
					
				}
			});
			
			fastBut2.setOnTouchListener(new OnTouchListener(){
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					if(event.getAction()==MotionEvent.ACTION_DOWN){
						v.setBackgroundColor(Color.parseColor("#3F4957"));
						return false;
					}
					if(event.getAction()==MotionEvent.ACTION_UP){
						v.setBackgroundColor(Color.parseColor("#553F4957"));
						
						return false;
					}
					return false;
					
					
				}
			});
			
			fastBut3.setOnTouchListener(new OnTouchListener(){
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					if(event.getAction()==MotionEvent.ACTION_DOWN){
						v.setBackgroundColor(Color.parseColor("#3F4957"));
						/*
						fastBut3.setText("");
						fastBut3.clearAnimation();*/
						return false;
					}
					if(event.getAction()==MotionEvent.ACTION_UP){
						v.setBackgroundColor(Color.parseColor("#553F4957"));
						return false;
					}
					return false;
					
					
				}
			});
			
			fastBut4.setOnTouchListener(new OnTouchListener(){
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					if(event.getAction()==MotionEvent.ACTION_DOWN){
						v.setBackgroundColor(Color.parseColor("#3F4957"));
						return false;
					}
					if(event.getAction()==MotionEvent.ACTION_UP){
						v.setBackgroundColor(Color.parseColor("#553F4957"));
						
						return false;
					}
					return false;
					
					
				}
			});
			
			
			/*iv3 = (ImageView)findViewById(R.id.global_imageView3);
	    	ivCal = (ImageView)findViewById(R.id.global_imageView_calculator);
	    	// bluetooth
	    	ivBT4 = (ImageView)findViewById(R.id.global_imageView4);
	    	ivBT5 = (ImageView)findViewById(R.id.global_imageView5);
	    	ivBT6 = (ImageView)findViewById(R.id.global_imageView6);
	    	ivBT6.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					String title = getResources().getString(R.string.Message24);
					String msg = getResources().getString(R.string.Message25);
					final AlertDialog alertDialog = getAlertDialogForDisconnectBluetooth(title,msg);
					alertDialog.show();
				}
	    		
	    	});
	    	ivBT5.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if(!mBluetoothAdapter.isEnabled()) {
						// enable bluetooth
						Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
						startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
					} else {
		        		ivBT5.setVisibility(View.GONE);
		        		ivBT4.setVisibility(View.VISIBLE);					
	                	ivBT6.setVisibility(View.GONE);
					}
				}
	    		
	    	});
	    	ivBT4.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
			        // Request discover from BluetoothAdapter
			        gbar.setVisibility(View.VISIBLE);
			        Set<BluetoothDevice> set = mBluetoothAdapter.getBondedDevices();
			        if(set.isEmpty()) {
				        // If we're already discovering, stop it
				        if (mBluetoothAdapter.isDiscovering()) {
				        	mBluetoothAdapter.cancelDiscovery();
				        }
			        	ltDeviceInfo = new TreeSet<BluetoothDevice>(new DeviceComparator());
			        	mBluetoothAdapter.startDiscovery();		
			        	Log.i(TAG,"starting discovery...");
			        } else {
			        	ltDeviceInfo = set;
			        	// start connection...
	                    // Initialize the BluetoothChatService to perform bluetooth connections
	                    if(mService == null) mService = new BluetoothService(MainMenuActivity.this, mHandler,null);
	                    // Only if the state is STATE_NONE, do we know that we haven't started already
	                    if (mService.getState() == BluetoothService.STATE_NONE) {
	                      // Start the Bluetooth chat services
	                      mService.start();
	                    }
			        }
				}
	    		
	    	});
	    	
	    	mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	    	*/
	    	if(omCurrentSysProfile.getMapTrackerInfo()!=null && omCurrentSysProfile.getMapTrackerInfo().equals(SysProfile.MAPTRACKERINFO_Y)) 
	    	{
	            // check if previous LogLocationService not stop
	    		if(LogLocationService.isAliveLogPosition) {
	        		Intent i = new Intent(this, LogLocationService.class);
	        		if(omCurrentSysProfile.isCloud()) {
	        			i = new Intent(this, HttpsLogLocationService.class);
	        		}
	                i.putExtra("screen_state", true);
	    			this.stopService(i);
	    			LogLocationService.isAliveLogPosition = false;
	    			Log.i(TAG,"stoping LogLocationService...");
	    		}
	    		// start LogLocationService
	    		Intent i = new Intent(this, LogLocationService.class);
	    		if(omCurrentSysProfile.isCloud()) {
	    			i = new Intent(this, HttpsLogLocationService.class);
	    		}
				i.putExtra("omCurrentSysProfile", omCurrentSysProfile);
	            i.putExtra("screen_state", true);
	            i.putExtra(LogLocationService.KEY_USERNO, omCurrentAccount.getAccountNo());
	            i.putExtra(LogLocationService.KEY_DIR, getBackupDir());
	            i.putExtra(LogLocationService.KEY_DEVICEID, DEVICEID);
	            this.startService(i);
	    		Log.i(TAG,"LogLocationService start!");
	    	}
	    	        
	     /*  // 自動上傳，目前只開放張豐盛，穎蓮
	    	autoUploadOrdersThread= new AutoUploadOrdersThread(this);
	        if(isZFS || isYLN) {
	        	autoUploadOrdersThread.start();
	        	Log.d(TAG,"autoUploadOrdersThread start!");
	        	// patrol wake lock
	        	PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
	        	wlp = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Tag for patrol");
	        	wlp.acquire();    	
	        }
	    	*/
	    	

    	
	    	// stick on portrait 
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	   

	@Override
	    public boolean onCreateOptionsMenu(Menu menu){ 
	    	super.onCreateOptionsMenu(menu);
	    	Log.d(TAG,"onCreateOptionsMenu start!");
	 	   	MenuInflater inflater = getMenuInflater();
	 	   	inflater.inflate(R.menu.main, menu);
	 	   	this.menu = menu;
	 	   	// �Y QueryNotUploadFragment�D�e�������W�ǫȤ��ơA�~�i�N���� menu item enable
	 	   	MainMenuFragment mmf = (MainMenuFragment)getFragmentManager().findFragmentById(R.id.main_frameLayout1);
	/* 	   if(mmf != null && mmf instanceof QueryNotUploadFragment) {
	 			// ����enable
	 	    	MenuItem item = (MenuItem)menu.findItem(R.id.mainmenu_item4);
	 	        if(((QueryNotUploadFragment)mmf).lastSelectedPosition != -1) {
	 	        	int lastSelectedPosition = ((QueryNotUploadFragment)mmf).lastSelectedPosition;
	 	        	QueryNotUploadDataAdapter adapter = ((QueryNotUploadFragment)mmf).adapter;
	 	        	if(adapter != null && ((QueryNotUploadFragment)mmf).lastSelectedPosition<adapter.getCount()) {
	 	        		if(((QueryNotUploadView)adapter.getItem(lastSelectedPosition)).getUploadFlag().equals(Orders.UPLOADFLAG_Y)) {
	 	        			item.setEnabled(false);
	 	        		} else {
	 	        			item.setEnabled(true);
	 	        		}
	 	        	}
	 	        } else {
	 	        	item.setEnabled(false);
	 	        }
	 	        
	 	   }
	        // ���profile�P�_
	        if(omCurrentSysProfile.getCompanyNo().equalsIgnoreCase(Phrase.PHRASE_DESC_SHD)) {
	        	menu.findItem(R.id.mainmenu_item58).setVisible(true);
	        	menu.findItem(R.id.mainmenu_item58a).setVisible(true);
	        	menu.findItem(R.id.mainmenu_item58a).setTitle(menu.findItem(R.id.mainmenu_item58a).getTitle()+"("+titleOfSHDStatisticsToday+")");
	        	Log.d(TAG,"SHD�@��έp��:"+menu.findItem(R.id.mainmenu_item58a).getTitle());
	        }
	        if(isZFS) {
	        	menu.findItem(R.id.mainmenu_item5b).setVisible(true);
	        	Log.d(TAG,"ZFS:"+menu.findItem(R.id.mainmenu_item5b).getTitle());
	        }

	 �\�ಾ�X��M�ܨt��
	 	   // ��l�ܥ\��B�D�ޤ~�i��
	    	if(omCurrentAccount.getLOOK_MAPTRACK()!=null && omCurrentAccount.getLOOK_MAPTRACK().equals("Y") &&
	    			omCurrentSysProfile != null && 
	   			  omCurrentSysProfile.getMapTrackerInfo() != null &&
	   			 omCurrentSysProfile.getMapTrackerInfo().equals(SysProfile.MAPTRACKERINFO_Y)) {
	    		menu.findItem(R.id.mainmenu_item59).setVisible(true); 
	    	}
	    	Log.d(TAG,"omCurrentAccount.getLOOK_MAPTRACK()="+omCurrentAccount.getLOOK_MAPTRACK());
	    	Log.d(TAG,"omCurrentSysProfile.getMapTrackerInfo()="+omCurrentSysProfile.getMapTrackerInfo());
	
	    	
	 	   // �ˬd�Y�Ӭۥ\��S�]�A�h�ﶵ���n�X�{
	 	   MenuItem item = (MenuItem)menu.findItem(R.id.mainmenu_item18);
	 	   if(omCurrentSysProfile != null && 
	 			  omCurrentSysProfile.getCameraInfo() != null &&
	 			 omCurrentSysProfile.getCameraInfo().equals(SysProfile.CAMERAINFO_Y) &&
					isSXI) {
	 		  item.setVisible(true);
	 	   } 
	 	   
	 	   // �۰ʤW��-2013/9/9����]�w�g�� 
//	 	   if(omCurrentSysProfile != null && omCurrentSysProfile.getCompanyNo().equalsIgnoreCase(Phrase.PHRASE_DESC_ZFS)) {
//	 		   MenuItem item19 = (MenuItem)menu.findItem(R.id.mainmenu_item19);
//	 		   item19.setVisible(true);
//	 	   }
	 	   
	 	   // �Y�ɰT��
	 	   if(isIMEnable) {
	 		   MenuItem item5a = (MenuItem)menu.findItem(R.id.mainmenu_item5a);
	 		   item5a.setVisible(true); 		   
	 	   }
	 	   
	 	   // �Ӭ۱M�פW��
	 	   if(omCurrentSysProfile != null && 
	  			  omCurrentSysProfile.getCameraInfo() != null &&
	  			 omCurrentSysProfile.getCameraInfo().equals(SysProfile.CAMERAINFO_Y)) {
	 		   �DFTN�~�n
	 		   if(!isFTN) {
	 			   MenuItem item5e = (MenuItem)menu.findItem(R.id.mainmenu_item5e);
	 			   item5e.setVisible(true);
	 		   }
	 	   }
	 	   
	 	   if(omCurrentSysProfile.isCloud()) {
	 		  MenuItem item11 = (MenuItem)menu.findItem(R.id.mainmenu_item11);
	 		 item11.setVisible(false);
	 	   }

	 	   if(isHEB) {
	 		   MenuItem item5i = (MenuItem)menu.findItem(R.id.mainmenu_item5i);
	 		   item5i.setVisible(true);
	 	   }
	 	   
	 	   // �YFTN,SND�hSE20(��b�h�f�P�Ӿ����R��)���n
	 	   if(isFTN || isSND) {
	 		   MenuItem item5k = (MenuItem)menu.findItem(R.id.mainmenu_item5k);
	 		   item5k.setVisible(false);
	 	   }
	 	   
	 	   // �X���A�����b�ڥu��SND
	 	   if(isSND) {
	 		   MenuItem item5n = (MenuItem)menu.findItem(R.id.mainmenu_item5n);
	 		   item5n.setVisible(true);
	 		   MenuItem item5o = (MenuItem)menu.findItem(R.id.mainmenu_item5o);
	 		   item5o.setVisible(true);
	 		   //HAO 104.05.06
	 		   MenuItem item5p = (MenuItem)menu.findItem(R.id.mainmenu_item5p);
			   item5p.setVisible(true);
	 		   //
			   //HAO 104.06.18
	 		   MenuItem item5q = (MenuItem)menu.findItem(R.id.mainmenu_item5q);
			   item5q.setVisible(true);
	 		   //
	 	   }*/

	 	   return true;
	    }
	   
	   @Override
	    protected void onStart() {
	        super.onStart();
	        Log.d(TAG,"onStart called!");
	        ;
	        // The activity is about to become visible.
	    	// register filter
	        intentFilter = new IntentFilter();
	        intentFilter.addAction(LikSysCoreDataDownloadAdvViewIntentService.LIKSYS_COREDATA_DOWNLOAD_ACTION);
	        registerReceiver(intentReceiver,intentFilter);
	        // register filter for upload
	     //   intentFilter = new IntentFilter();
	     //   intentFilter.addAction(LikSysCoreDataUploadIntentService.LIKSYS_COREDATA_UPLOAD_ACTION);
	        //registerReceiver(intentReceiverUpload,intentFilter);
	        // Register Bluetooth
	      //  intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
	      //  registerReceiver(btReceiver, intentFilter);
	        // Register for broadcasts when discovery has finished
	     //   intentFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
	     //   registerReceiver(btReceiver, intentFilter);   
	        // register filter for real stock reply 
	     //   intentFilter = new IntentFilter();
	      //  intentFilter.addAction(CheckRealStockReplyIntentService.CHECK_REAL_STOCK_REPLY_ACTION);
	     //   registerReceiver(intentReceiverRealStockReply,intentFilter);
	        // register filter for SellDetail XML download 
	        intentFilter = new IntentFilter();
	        intentFilter.addAction(LikSysSellDetailDownloadIntentService.LIKSYS_SELLDETAIL_XMLDOWNLOAD_ACTION);
	        registerReceiver(intentReceiverSellDetailXML,intentFilter);
	        // register filter for Instant Messenger 
	      //  intentFilter = new IntentFilter();
	     //   intentFilter.addAction(InstantMessengerService.INSTANT_MESSENGER_ACTION);
	        registerReceiver(intentReceiverIMS,intentFilter);
	        
	      // start 訂單backup thread
	        isAlive = true;
	    	new Thread(new Runnable() {

				@Override
				public void run() {
					while(isAlive) {
						Log.d(TAG,"needBackup="+needBackup);
						synchronized(MainMenuActivity.this) {
							if(!needBackup) {
								try {
									MainMenuActivity.this.wait(THREAD_WAIT_INTERVAL);
								} catch (InterruptedException e) {
									// do nothing
								}
								continue;
							}
							needBackup = false;
							Log.i(TAG,"start backup...");
							if(!isAlive) continue;
							if(DBAdapter==null || DBAdapter.getCompanyID()==0) continue;
//							LikDBAdapter DBAdapter1 = new LikDBAdapter(getBaseContext(),false,currentCompany.getCompanyID());	
					        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
					      /*  String backupDir = settings.getString(SetupBackupDirFragment.BACKUPDIR_KEY, null);
					        if(backupDir == null) backupDir = getBackupDir();
					        Log.d(TAG,"backupDir in run="+backupDir);
					        String fileName = Constant.sqliteDFS.format(getCurrentDate())+".txt";
					        File file = new File(backupDir+"/"+fileName);
					        FileUtil futil = new FileUtil(file,false);
					        StringBuffer sb = new StringBuffer();*/
		/*			        try {
					       	Orders omO = new Orders();
					        	omO.setCompanyID(currentCompany.getCompanyID());
					        	omO.setUserNO(omCurrentAccount.getAccountNo());
//					        	List<Orders> ltO = omO.getOrdersByUserNO(DBAdapter);
					        	//改成備份未上傳或當天已上傳的
					        	List<Orders> ltO = omO.getTodayOrdersByUserNO(DBAdapter);
					        	for(Iterator<Orders> ir=ltO.iterator();ir.hasNext();) {
					        		sb.append("<Root TableName='Orders'>").append("\n");
					        		Orders om = ir.next();
					        		sb.append(om.toXML());
					        		OrderDetail omOD = new OrderDetail();
					        		omOD.setTabletSerialNO(om.getTabletSerialNO());
					        		omOD.setOrderID(om.getOrderID());
					        		omOD.setCompanyID(om.getCompanyID());
					        		List<OrderDetail> ltOD = omOD.getOrderDetailByOrdersKey(DBAdapter);
					        		if(ltOD.size()>0) {
						    			sb.append("<DetailList TableName='OrderDetail' Size='"+ltOD.size()+"'>").append("\n");
						        		for(Iterator<OrderDetail> irr=ltOD.iterator();irr.hasNext();) {
						        			OrderDetail omODD = irr.next();
						        			omODD.setPdaId(om.getPdaId());
						        			sb.append(omODD.toXML());
						        		}
						        		sb.append("</DetailList>").append("\n");
					        		}
					    			// ����
					    			OrderCheck omOCk = new OrderCheck();
					    			omOCk.setOrdersSerialID(om.getSerialID());
					    			List<OrderCheck> ltOCk = omOCk.queryByOrdersSerialID(DBAdapter);
					    			if(ltOCk.size()>0) {
						    			sb.append("<DetailList TableName='OrderCheck' Size='"+ltOCk.size()+"'>").append("\n");
						    			for(OrderCheck omOC:ltOCk) {
											sb.append("<Detail>").append("\n");
							    			sb.append("<CompanyID>").append(omOC.getCompanyID()).append("</CompanyID>").append("\n");
							    			sb.append("<PdaId>").append(omO.getPdaId()).append("</PdaId>").append("\n");
							    			sb.append("<OrderID>").append(omO.getOrderID()).append("</OrderID>").append("\n");
							    			sb.append("<CheckNo>").append("<![CDATA[").append(omOC.getCheckNo()).append("]]>").append("</CheckNo>").append("\n");
							    			sb.append("<Amount>").append(omOC.getAmount()).append("</Amount>").append("\n");
							    			if(omOC.getDueDate()!=null) sb.append("<DueDate>").append(Constant.sqliteDFS.format(omOC.getDueDate())).append("</DueDate>").append("\n");
							    			if(omOC.getCashDate()!=null) sb.append("<CashDate>").append(Constant.sqliteDFS.format(omOC.getCashDate())).append("</CashDate>").append("\n");
							    			if(!Constant.isEmpty(omOC.getBank())) sb.append("<Bank>").append("<![CDATA[").append(omOC.getBank()).append("]]>").append("</Bank>").append("\n");
							    			if(!Constant.isEmpty(omOC.getAccountNo())) sb.append("<AccountNo>").append("<![CDATA[").append(omOC.getAccountNo()).append("]]>").append("</AccountNo>").append("\n");
							    			sb.append("<CheckSEQ>").append(omOC.getSerialID()).append("</CheckSEQ>").append("\n");
							    			if(omOC.getNote()!=null) sb.append("<Note>").append("<![CDATA[").append(omOC.getNote()).append("]]>").append("</Note>").append("\n");
							    			sb.append("</Detail>").append("\n");						    				
						    			}
						    			sb.append("</DetailList>").append("\n");
					    			}
					    			// �R�P
					    			OrderReceive omOR = new OrderReceive();
					    			omOR.setOrdersSerialID(om.getSerialID());
					    			List<OrderReceive> ltOR = omOR.queryByOrdersSerialID(DBAdapter);
					    			if(ltOR.size()>0) {
						    			sb.append("<DetailList TableName='OrderCancel' Size='"+ltOR.size()+"'>").append("\n");
						    			for(OrderReceive omR:ltOR) {
											sb.append("<Detail>").append("\n");
							    			sb.append("<CompanyID>").append(omR.getCompanyID()).append("</CompanyID>").append("\n");
							    			sb.append("<PdaId>").append(omO.getPdaId()).append("</PdaId>").append("\n");
							    			sb.append("<OrderID>").append(omO.getOrderID()).append("</OrderID>").append("\n");
							    			sb.append("<CustNo>").append("<![CDATA[").append(omR.getCustNo()).append("]]>").append("</CustNo>").append("\n");
							    			sb.append("<Amount>").append(omR.getAmount()).append("</Amount>").append("\n");
							    			if(omR.getStartDate()!=null) sb.append("<StartDate>").append(Constant.sqliteDFS.format(omR.getStartDate())).append("</StartDate>").append("\n");
							    			if(omR.getEndDate()!=null) sb.append("<EndDate>").append(Constant.sqliteDFS.format(omR.getEndDate())).append("</EndDate>").append("\n");
							    			sb.append("<CancelSEQ>").append(omR.getSerialID()).append("</CancelSEQ>").append("\n");
							    			if(omR.getNote()!=null) sb.append("<Note>").append("<![CDATA[").append(omR.getNote()).append("]]>").append("</Note>").append("\n");
							    			sb.append("</Detail>").append("\n");						    				
						    			}
						    			sb.append("</DetailList>").append("\n");
					    			}
					        		sb.append("</Root>");
					        		sb.append(Constant.MESSAGE_SEPERATOR_ATOMIC).append("\n");
					        	}
					        	futil.write(sb.toString());
							} catch (IOException e) {
								Log.e(TAG,"write file error");
//							} finally {
//								DBAdapter.closedb();
							}*/
						}		
					}
				}
	    		
	    	}).start();
	    	
	    	// 清備份檔thread
	    	new Thread(this).start();
	        isStopCalled = false;
	        
	        if(omCurrentSysProfile.getCompanyNo().equalsIgnoreCase(Phrase.PHRASE_DESC_ZFS)) {
		        MainMenuFragment mmf = (MainMenuFragment)getFragmentManager().findFragmentById(R.id.main_frameLayout1);
		        if(mmf instanceof QueryNotUploadFragment) {
					QueryNotUploadFragment qnuf = (QueryNotUploadFragment)mmf;
					// refresh list
					/*qnuf.adapter.gatherData(omCurrentAccount.getAccountNo(),String.valueOf(currentDept.getCompanyID()));
					qnuf.adapter.notifyDataSetChanged();
					if(qnuf.adapter.getCount()==0) {
						if(menu!=null) menu.findItem(R.id.mainmenu_item4).setEnabled(false);
					}*/
		        }
	        }
	        
	        // check for update
/*	        if(omCurrentSysProfile.isCloud()) {
	        	verifyApkVersionTask = new VerifyApkVersionSSLTask(this);
	        } else {
	        	verifyApkVersionTask = new VerifyApkVersionTask(this);
	        }
	        verifyApkVersionTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);*/

	    }

	    @Override
	    protected void onResume() {
	    	super.onResume();
	    	Log.d(TAG,"onResume called!");
	    }
	
	  @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        Log.d(TAG,item.getTitle().toString()+" selected!");
	    	MainMenuFragment mmf = (MainMenuFragment)getFragmentManager().findFragmentById(R.id.main_frameLayout1);
	        // Handle item selection
	        switch (item.getItemId()) {
	        case R.id.mainmenu_item11:
	        	   if (mmf == null || mmf.getIndex() != R.id.mainmenu_item11) 
	        		   mmf = SetupIpPortFragment.newInstance(R.id.mainmenu_item11);
		            showMainMenuFragment(mmf);
		            return true;
	      /*  case R.id.mainmenu_item12:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item12) 
	            	mmf = SetCompanyFragment.newInstance(R.id.mainmenu_item12);
	            showMainMenuFragment(mmf);
	            return true;*/
	        case R.id.mainmenu_item13:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item13) {
	            	mmf = CoreDownloadFragment.newInstance();
	            	showMainMenuFragment(mmf);
	            }
	            return true;
	            
	        case R.id.mainmenu_item14:
	        	if (mmf == null || mmf.getIndex() != R.id.mainmenu_item14) {
	        		   mmf = UploadActivty.newInstance();
		            showMainMenuFragment(mmf);
	        	}
		            return true;
	            
	        case R.id.mainmenu_item8:
				String title = getResources().getString(R.string.Message37b);
				String dialogBody = getResources().getString(R.string.Message37a);
				final AlertDialog alertDialog = getExitDialog(title,dialogBody);    			
				alertDialog.show();    	
			
	     
			
	        default:
	            return super.onOptionsItemSelected(item);
	     /*   case R.id.mainmenu_item11:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item11) 
	            	mmf = SetupIpPortFragment.newInstance(R.id.mainmenu_item11);
	            showMainMenuFragment(mmf);
	            return true;
	        case R.id.mainmenu_item12:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item12) 
	            	mmf = SetCompanyFragment.newInstance(R.id.mainmenu_item12);
	            showMainMenuFragment(mmf);
	            return true;
	        case R.id.mainmenu_item13:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item13) {
	            	mmf = CoreDownloadFragment.newInstance(R.id.mainmenu_item2);
	            	showMainMenuFragment(mmf);
	            }
	            return true;
	        case R.id.mainmenu_item14:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item14) {
	            	mmf = OrderUploadFragment.newInstance(R.id.mainmenu_item3);
	            	showMainMenuFragment(mmf);
	            }
	            return true;
	        case R.id.mainmenu_item15:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item15) {
	            	mmf = SetupBackupDirFragment.newInstance(R.id.mainmenu_item15);
	            	showMainMenuFragment(mmf);
	            }
	            return true;
	        case R.id.mainmenu_item16:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item16) {
	            	mmf = DownloadProductsImagesFragment.newInstance(R.id.mainmenu_item16);
	            	showMainMenuFragment(mmf);
	            }
	            return true;
	        case R.id.mainmenu_item17:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item17) {
	            	mmf = SetHistoryPeriodFragment.newInstance(R.id.mainmenu_item17);
	            	showMainMenuFragment(mmf);
	            }
	            return true;
	        case R.id.mainmenu_item18:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item18) {
	            	if(omCurrentSysProfile != null && omCurrentSysProfile.getCameraInfo().equals(SysProfile.CAMERAINFO_Y)) {
	            		if(isFTN) {
	            			mmf = CameraFTNUploadFragment.newInstance(R.id.mainmenu_item5h);
	            		} else {
	            			mmf = CameraImageUploadFragment.newInstance(R.id.mainmenu_item18);
	            		}
	            		showMainMenuFragment(mmf);
	            	}
	            }
	            return true;
	        case R.id.mainmenu_item19:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item19) {
	            	mmf = SetAutoUploadPeriodFragment.newInstance(R.id.mainmenu_item19);
	            	showMainMenuFragment(mmf);
	            }
	            return true;
	        case R.id.mainmenu_item4:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item4) {
	            	mmf = TakeOrderFragment.newInstance(R.id.mainmenu_item4);
	            	showMainMenuFragment(mmf);
	            }
	            return true;
	        case R.id.mainmenu_item51:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item51) {
	            	mmf = QueryNotUploadFragment.newInstance(R.id.mainmenu_item51);
	            	showMainMenuFragment(mmf);
	            }
	            return true;
	        case R.id.mainmenu_item52:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item52) {
	            	mmf = BulletinFragment.newInstance(R.id.mainmenu_item52);
	            	showMainMenuFragment(mmf);
	            }
	            return true;
	        case R.id.mainmenu_item53:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item53) {
	            	mmf = QueryHistoryFragment.newInstance(R.id.mainmenu_item53);
	    			// get current selected customer
	            	MainMenuFragment mmff = (MainMenuFragment)getFragmentManager().findFragmentById(R.id.main_frameLayout1);
	    			if(mmff instanceof TakeOrderFragment) {
	    				TakeOrderFragment mmTOF = (TakeOrderFragment)mmff;
	    				Bundle bundle = mmf.getArguments();
	    				bundle.putSerializable(QueryNotUploadFragment.CUSTOMER_BUNDLE_KEY, mmTOF.viewQNU);
	    			}
	            	showMainMenuFragment(mmf);
	            }
	            return true;
	        case R.id.mainmenu_item55:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item55) {
	            	mmf = ShowTabletSerialNoFragment.newInstance(R.id.mainmenu_item55);
	            	showMainMenuFragment(mmf);
	            }
	            return true;
	        case R.id.mainmenu_item56:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item56) {
	            	mmf = SalesStatisticsFragment.newInstance(R.id.mainmenu_item56);
	            	showMainMenuFragment(mmf);
	            }
	            return true;
	        case R.id.mainmenu_item58:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item58) {
	            	mmf = SHDSalesStatisticsFragment.newInstance(R.id.mainmenu_item58);
	            	showMainMenuFragment(mmf);
	            }
	            return true;
	        case R.id.mainmenu_item58a:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item58a) {
	            	mmf = SHDSalesStatisticsTodayFragment.newInstance(R.id.mainmenu_item58a);
	            	showMainMenuFragment(mmf);
	            }
	            return true;
	        case R.id.mainmenu_item59:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item59) {
	            	mmf = MapTrackerFragment.newInstance(R.id.mainmenu_item59);
	            	showMainMenuFragment(mmf);
	            }
	            return true;
	        case R.id.mainmenu_item5a:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item5a) {
	            	mmf = InstantMessagesFragment.newInstance(R.id.mainmenu_item5a);
	            	showMainMenuFragment(mmf);
	            }
	            return true;
	        case R.id.mainmenu_item5b:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item5b) {
	            	mmf = ZFSSalesStatisticsFragment.newInstance(R.id.mainmenu_item5b);
	            	showMainMenuFragment(mmf);
	            }
	            return true;
	        case R.id.mainmenu_item5e:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item5e) {
	            	mmf = CameraHEBUploadFragment.newInstance(R.id.mainmenu_item5e);
	            	showMainMenuFragment(mmf);
	            }
	            return true;
	        case R.id.mainmenu_item5i:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item5i) {
	            	mmf = ReceiveStatisticsFragment.newInstance(R.id.mainmenu_item5i);
	            	showMainMenuFragment(mmf);
	            }
	            return true;
	        case R.id.mainmenu_item5j:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item5j) {
	            	mmf = ShowDevelopInfoFragment.newInstance(R.id.mainmenu_item5j);
	            	showMainMenuFragment(mmf);
	            }
	            return true;
	        case R.id.mainmenu_item5k:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item5k) {
	            	QueryNotUploadView omview = null;
	        		if(mmf instanceof QueryNotUploadFragment) {
	        			QueryNotUploadFragment mmQNU = (QueryNotUploadFragment)mmf;
	    				if(mmQNU.lastSelectedPosition != -1 && mmQNU.lastSelectedPosition < mmQNU.adapter.getCount()) {
	    					omview = (QueryNotUploadView)mmQNU.adapter.getItem(mmQNU.lastSelectedPosition);
	    				}
	        		} else if(mmf instanceof AddVisitCustomerFragment) {
	        			AddVisitCustomerFragment mmAVC = (AddVisitCustomerFragment)mmf;
	    				if(mmAVC.lastSelectedPosition != -1 && mmAVC.lastSelectedPosition < mmAVC.adapter.getCount()) {
	    					AddVisitCustomerView aview = (AddVisitCustomerView)mmAVC.adapter.getItem(mmAVC.lastSelectedPosition);
	    					omview = new QueryNotUploadView();
	    					omview.setCustomerID(aview.getCustomerID());
	    				}
	        		} else {
	        	        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
	        	        int last = settings.getInt(QueryNotUploadFragment.LAST_SELECTED_POSITION_KEY, -1);
	        			if(last!=-1) {
	        				QueryNotUploadDataAdapter adapter = new QueryNotUploadDataAdapter(this,DBAdapter);
	        		    	adapter.gatherData(omCurrentAccount.getAccountNo(),String.valueOf(currentCompany.getCompanyID()));
	        		    	if(last<adapter.getCount()) omview = (QueryNotUploadView)adapter.getItem(last);
	        			}
	        		}
	        		if(omview==null) {
						AlertDialog dialog = getAlertDialogForMessage(getResources().getString(R.string.Message37a), getResources().getString(R.string.takeorderMessage3));
						dialog.show();
	        		} else {
		            	mmf = Se20StatisticsFragment.newInstance(R.id.mainmenu_item5k);
						Bundle bundle = mmf.getArguments();
						bundle.putSerializable(QueryNotUploadFragment.CUSTOMER_BUNDLE_KEY, omview);
		            	showMainMenuFragment(mmf);
					}
	            }
	            return true;
	        case R.id.mainmenu_item5n:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item5n) {
	            	QueryNotUploadView omview = null;
	        		if(mmf instanceof QueryNotUploadFragment) {
	        			QueryNotUploadFragment mmQNU = (QueryNotUploadFragment)mmf;
	    				if(mmQNU.lastSelectedPosition != -1 && mmQNU.lastSelectedPosition < mmQNU.adapter.getCount()) {
	    					omview = (QueryNotUploadView)mmQNU.adapter.getItem(mmQNU.lastSelectedPosition);
	    				}
	        		} else if(mmf instanceof AddVisitCustomerFragment) {
	        			AddVisitCustomerFragment mmAVC = (AddVisitCustomerFragment)mmf;
	    				if(mmAVC.lastSelectedPosition != -1 && mmAVC.lastSelectedPosition < mmAVC.adapter.getCount()) {
	    					AddVisitCustomerView aview = (AddVisitCustomerView)mmAVC.adapter.getItem(mmAVC.lastSelectedPosition);
	    					omview = new QueryNotUploadView();
	    					omview.setCustomerID(aview.getCustomerID());
	    				}
	        		} else {
	        	        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
	        	        int last = settings.getInt(QueryNotUploadFragment.LAST_SELECTED_POSITION_KEY, -1);
	        			if(last!=-1) {
	        				QueryNotUploadDataAdapter adapter = new QueryNotUploadDataAdapter(this,DBAdapter);
	        		    	adapter.gatherData(omCurrentAccount.getAccountNo(),String.valueOf(currentCompany.getCompanyID()));
	        				if(last<adapter.getCount()) omview = (QueryNotUploadView)adapter.getItem(last);
	        			}
	        		}
	            	mmf = ContractSNDFragment.newInstance(R.id.mainmenu_item5n);
	        		if(omview!=null) {
						Bundle bundle = mmf.getArguments();
						bundle.putSerializable(QueryNotUploadFragment.CUSTOMER_BUNDLE_KEY, omview);
	        		}
	            	showMainMenuFragment(mmf);
	            }
	            return true;
	        case R.id.mainmenu_item5o:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item5o) {
	            	QueryNotUploadView omview = null;
	        		if(mmf instanceof QueryNotUploadFragment) {
	        			QueryNotUploadFragment mmQNU = (QueryNotUploadFragment)mmf;
	    				if(mmQNU.lastSelectedPosition != -1 && mmQNU.lastSelectedPosition < mmQNU.adapter.getCount()) {
	    					omview = (QueryNotUploadView)mmQNU.adapter.getItem(mmQNU.lastSelectedPosition);
	    				}
	        		} else if(mmf instanceof AddVisitCustomerFragment) {
	        			AddVisitCustomerFragment mmAVC = (AddVisitCustomerFragment)mmf;
	    				if(mmAVC.lastSelectedPosition != -1 && mmAVC.lastSelectedPosition < mmAVC.adapter.getCount()) {
	    					AddVisitCustomerView aview = (AddVisitCustomerView)mmAVC.adapter.getItem(mmAVC.lastSelectedPosition);
	    					omview = new QueryNotUploadView();
	    					omview.setCustomerID(aview.getCustomerID());
	    				}
	        		} else {
	        	        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
	        	        int last = settings.getInt(QueryNotUploadFragment.LAST_SELECTED_POSITION_KEY, -1);
	        			if(last!=-1) {
	        				QueryNotUploadDataAdapter adapter = new QueryNotUploadDataAdapter(this,DBAdapter);
	        		    	adapter.gatherData(omCurrentAccount.getAccountNo(),String.valueOf(currentCompany.getCompanyID()));
	        				if(last<adapter.getCount()) omview = (QueryNotUploadView)adapter.getItem(last);
	        			}
	        		}
	            	mmf = ARSNDFragment.newInstance(R.id.mainmenu_item5o);
	        		if(omview!=null) {
						Bundle bundle = mmf.getArguments();
						bundle.putSerializable(QueryNotUploadFragment.CUSTOMER_BUNDLE_KEY, omview);
	        		}
	            	showMainMenuFragment(mmf);
	            }
	            return true;
	        //HAO 104.05.06
	        case R.id.mainmenu_item5p:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item5p) {
	            	mmf = NotDeliverSNDFragment.newInstance(R.id.mainmenu_item5p);
	            	showMainMenuFragment(mmf);
	            }
	            return true;
	        //
	        //HAO 104.06.18
	        case R.id.mainmenu_item5q:
	            if (mmf == null || mmf.getIndex() != R.id.mainmenu_item5q) {
	            	mmf = DeliverSNDFragment.newInstance(R.id.mainmenu_item5q);
	            	showMainMenuFragment(mmf);
	            }
	            return true;
	        //
	        default:
	            return super.onOptionsItemSelected(item);*/
	        }
	    }
	
	 /**
     * WIFI BroadcastReceiver
     */
    private BroadcastReceiver wifiChangedReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
		    final String action = intent.getAction();
		    if (action.equals(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION)) {
		        if (intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false)) {
		            Log.i(TAG,"WIFI connected!");
		        } else {
		            // wifi connection was lost
		        	Log.i(TAG,"WIFI disconnected!");
		        }
		    }			
		}
    };

    /**
     * Network BroadcastReceiver
     */
    private BroadcastReceiver networkChangedReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            //boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
            //String reason = intent.getStringExtra(ConnectivityManager.EXTRA_REASON);
            //boolean isFailover = intent.getBooleanExtra(ConnectivityManager.EXTRA_IS_FAILOVER, false);

            //NetworkInfo currentNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            //NetworkInfo otherNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO);
        	ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        	NetworkInfo currentNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if(currentNetworkInfo != null && currentNetworkInfo.isConnected()){
                Log.i(TAG,"network connected, calling NetworkTimeTask...");
	    		// get network time
	            new NetworkTimeTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }else{
            	Log.i(TAG,"network not connected!");
            }
        }
    };
    
	public static Date getCurrentDate(Date date) {
		Calendar cal = Calendar.getInstance(Locale.CHINESE);
		if(date==null) date = cal.getTime();
		else cal.setTime(date);
		if(!isNetworkTimeEnabled) return cal.getTime();
		if(timeDiff==0) return cal.getTime();
		else {
			long milliseconds = cal.getTimeInMillis()+timeDiff;
			cal.setTimeInMillis(milliseconds);
			Log.i(TAG,"network time="+cal.getTime());
			return cal.getTime();
		}
	}
	
    public AlertDialog getAlertDialogForMessage(String title, String message) {
    	final String msgY = getResources().getString(R.string.Button1);
    	if(popMessage!=null) {
    		popMessage.setTitle(title);
    		popMessage.setMessage(message);
    	} else {
	    	Builder builder = new AlertDialog.Builder(this);
	    	builder.setTitle(title);
	    	builder.setMessage(message);
	    	builder.setPositiveButton(msgY, new DialogInterface.OnClickListener() {
	    		@Override
	    		public void onClick(DialogInterface dialog, int which) {
	    			// do nothing
	    		}
	    	});
	    	popMessage = builder.create();
    	}
		return popMessage;
    }
    
    public void showMainMenuFragment(MainMenuFragment mmf) {
        // Execute a transaction, replacing any existing
        // fragment with this one inside the frame.
        Log.v(TAG, "about to run FragmentTransaction...");
        
    	// �ˬd���ڬO�_����
		MainMenuFragment oldmmf = (MainMenuFragment)getFragmentManager().findFragmentById(R.id.main_frameLayout1);
/*		if(oldmmf instanceof ReceiveFragment) {
			ReceiveFragment rf = (ReceiveFragment)oldmmf;
			rf.updateTopInfo();
			if(!rf.isCheckCorrect) {
				AlertDialog dialog1 = rf.getAlertDialogForMessage(getResources().getString(R.string.takeorderMessage1a),rf.checkErrorMsg);
				dialog1.show();	
				return;
			}
			if(!rf.isCancelCorrect) {
				AlertDialog dialog1 = rf.getAlertDialogForMessage(getResources().getString(R.string.takeorderMessage1a),rf.cancelErrorMsg);
				dialog1.show();	
				return;								
			}
		}*/

        FragmentTransaction ft  = getFragmentManager().beginTransaction();
        //ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.replace(R.id.main_frameLayout1, mmf);
        //ft.addToBackStack(TAG);
        ft.commit();
    }
	
	public static Date getCurrentDate() {
		return getCurrentDate(null);
	}
	
	public boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	public boolean isNeedBackup() {
		return needBackup;
	}

	public void setNeedBackup(boolean needBackup) {
		this.needBackup = needBackup;
	}
	
	protected String getBackupDir() {
		Map<String, File> externalLocations = ExternalStorage.getAllStorageLocations();
		File externalSdCard = externalLocations.get(ExternalStorage.EXTERNAL_SD_CARD);
		Log.d(TAG,"externalSdCard="+externalSdCard);
        String backupDir = externalSdCard+getResources().getString(R.string.OrdersBackupDir);
        File file = new File(backupDir);
    	new FileUtil(file,true);
    	if(!file.exists()) {
        	backupDir = Environment.getExternalStorageDirectory()+getResources().getString(R.string.OrdersBackupDir);	        		
    	}
    	return backupDir;

	}
	
	
private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
    	
    	MainMenuFragment mmf = null;
    	
    	@Override
    	public void onReceive(Context context, Intent intent) {
            Log.d(TAG,"intentReceiver..."+intent.getAction());
    		mmf = (MainMenuFragment)getFragmentManager().findFragmentById(R.id.main_frameLayout1);
    		String code = intent.getStringExtra(OrdersUploadService.CODE);
    		Log.d(TAG,"code="+code);
    		// check for result
    		Log.d(TAG,"LikSysCoreDataDownloadAdvViewIntentService.RESULT="+LikSysCoreDataDownloadAdvViewIntentService.RESULT);
    		String result = intent.getStringExtra(LikSysCoreDataDownloadAdvViewIntentService.RESULT);
    		Log.d(TAG,result);
    		if(result == null) 
    			return;
    		if(result.startsWith(BROADCAST_HEADER_TOAST)) {
    			Log.d(TAG,"a");
    			result = result.substring(BROADCAST_HEADER_TOAST.length()-1);
    			Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
    		} else if(result.startsWith(BROADCAST_HEADER_PROGRESS)) {
    			Log.d(TAG,"ab");
    			// ��sprogress bar
    			HttpMessage msg = new HttpMessage();
    			if(msg.parseMessage(result)) {
	    			if(mmf instanceof CoreDownloadFragment) {
	    				CoreDownloadFragment fragment = (CoreDownloadFragment)mmf;
	    				fragment.bar.setProgress(Integer.parseInt(msg.getReturnMessage()));
	        			Log.d(TAG,"bar setProgress="+fragment.bar.getProgress());
	        			fragment.tvProgress.setText(msg.getReturnMessage()+"%");
	    			}   
    			}
    		} else if(result.startsWith(BROADCAST_HEADER_UPDATE_TABLELIST)) {
    			// ��stable list
    			Log.d(TAG,"abc");
    			HttpMessage msg = new HttpMessage();
    			if(msg.parseMessage(result)) {
	    			if(mmf instanceof CoreDownloadFragment) {
	    				CoreDownloadFragment fragment = (CoreDownloadFragment)mmf;
	    				fragment.removeFromToDoTableList(msg.getReturnMessage());
	    			}   
    			}   
    		} else if(result.startsWith(BROADCAST_HEADER_PUT_LAST_SEQ)) {
    			Log.d(TAG,"abcd");
    			String[] split = result.split(":");
    			if(split.length==2) {
    				String[] split2 = split[1].split("=");
	    			if(mmf instanceof CoreDownloadFragment && split2.length==2) {
	    				CoreDownloadFragment fragment = (CoreDownloadFragment)mmf;
	    				fragment.putLastSeqMap(split2[0],Integer.parseInt(split2[1]));
	    			}       				
    			}
    		} else {
    			Log.d(TAG,"abcde");
        		HttpMessage msg = new HttpMessage();
        		if(msg.parseMessage(result)) {
        			// show message at bottom line
        			//gtv.setVisibility(View.VISIBLE);
        			//gtv.setText(msg.getReturnMessage());
        			Log.d(TAG,"abcgg="+msg.getReturnMessage());
        			Toast.makeText(MainMenuActivity.this, msg.getReturnMessage(), Toast.LENGTH_SHORT).show();
        			
        			if(mmf instanceof CoreDownloadFragment) {
        				Log.d(TAG,"is CoreDownloadFragment");
        				CoreDownloadFragment fragment = (CoreDownloadFragment)mmf;
        				if(msg.getReturnCode().equals(Constant.SUCCESS)) {
        					fragment.bar.setProgress(100);
        					fragment.bar.setVisibility(View.INVISIBLE);
        					fragment.tvProgress.setText("100%");
        					fragment.tvProgress.setVisibility(View.INVISIBLE);
        					//gbar.setProgress(100);
        					//gbar.setVisibility(View.GONE);
    						// list view disable
        					fragment.lv.setEnabled(true);
        					fragment.isDownload = false;
        					
        					fragment.startDownloadFileListTask(true);
        					Log.d(TAG,"haha4");
        					LinearLayout linearLayout1 = (LinearLayout)findViewById(R.id.global_linearLayout1);
        					linearLayout1.setVisibility(1);
        					mmf = QueryNotUploadFragment.newInstance();
    			    		showMainMenuFragment(mmf);
        				}
        				else if (msg.getReturnCode().equals(Constant.NO_MEMORY))
        				{
        					Log.d(TAG,"NO_MEMORY START");
        					
        					fragment.lv.setEnabled(true);
        					fragment.isDownload = false;
        					fragment.startDownloadFileListTask(true);
        					LinearLayout linearLayout1 = (LinearLayout)findViewById(R.id.global_linearLayout1);
        					linearLayout1.setVisibility(1);
        					AlertDialog dialog = mmf.NoEnoughMemoryDialog(getResources().getString(R.string.takeorderMessage1a),getResources().getString(R.string.Message44));
        					dialog.show();	 
        				}else {
        					Log.d(TAG,"MessageCode="+msg.getReturnCode()+",Message="+msg.getReturnMessage());
        					if(msg.getReturnCode()!=null && msg.getReturnCode().equals(Constant.ERROR_CONNECT_NETWORK)) {
            					AlertDialog dialog = mmf.getAlertDialogForMessage(getResources().getString(R.string.takeorderMessage1a),getResources().getString(R.string.Message40));
            					dialog.show();	        						
        					}
                			//gtv.setVisibility(View.VISIBLE);
        					//gtv.setText(msg.getReturnMessage());
        					Toast.makeText(MainMenuActivity.this, msg.getReturnMessage(), Toast.LENGTH_SHORT).show();
        					//gbar.setVisibility(View.GONE);
        					fragment.bar.setVisibility(View.INVISIBLE);
        					fragment.tvProgress.setVisibility(View.INVISIBLE);
        					fragment.lv.setEnabled(true);
        					//fragment.b3.setEnabled(true);
        					//fragment.b4.setEnabled(true);
        					fragment.isDownload = false;     
        					//gbar.setVisibility(View.GONE);
        					fragment.startDownloadFileListTask(true);
        				}
        			} else {
    					Log.d(TAG,"MessageCode="+msg.getReturnCode()+",Message="+msg.getReturnMessage());
            			//gtv.setVisibility(View.VISIBLE);
    					//gtv.setText(msg.getReturnMessage());
    					Toast.makeText(MainMenuActivity.this, msg.getReturnMessage(), Toast.LENGTH_SHORT).show();
    					//gbar.setVisibility(View.GONE);        				
        			}
        		} else {
        			if(code!=null && !code.equals(Constant.SUCCESS)) {
    					if(mmf instanceof CoreDownloadFragment) {
            				if(code.equals(Constant.ERROR_AUTHETICATE_FAIL)) {
                				CoreDownloadFragment fragment = (CoreDownloadFragment)mmf;
            					fragment.bar.setVisibility(View.INVISIBLE);
            					fragment.tvProgress.setVisibility(View.INVISIBLE);
            					fragment.lv.setEnabled(true);
            					fragment.b2.setEnabled(true);
            					fragment.b3.setEnabled(true);
            					fragment.b4.setEnabled(true);
            					fragment.isDownload = false;     
            					AlertDialog dialog = mmf.getAlertDialogForMessage(getResources().getString(R.string.takeorderMessage1a),getResources().getString(R.string.Message41));
            					dialog.show();	
            				}    						
    					}
        			} 
        			else
        			{
        				if(mmf instanceof CoreDownloadFragment) {
        					Log.d(TAG,"haha5");
        					//Toast.makeText(MainMenuActivity.this,result, Toast.LENGTH_SHORT).show();
        					CoreDownloadFragment fragment = (CoreDownloadFragment)mmf;
        					fragment.downLoadMessage.setText(result);
        				}
        				
        			}
        		}
    		}    		
    	}
    };

   /* *//**
     * upload BroadcastReceiver via XMPP
     *//*
    private BroadcastReceiver intentReceiverUpload = new BroadcastReceiver() {

    	MainMenuFragment mmf = null;

    	@Override
		public void onReceive(Context context, Intent intent) {
            Log.d(TAG,"intentReceiverUpload..."+intent.getAction());
    		mmf = (MainMenuFragment)getFragmentManager().findFragmentById(R.id.main_frameLayout1);
			// for upload
    		String code = intent.getStringExtra(OrdersUploadService.CODE);
    		Log.d(TAG,"code="+code);
    		String result = intent.getStringExtra(OrdersUploadService.RESULT);
    		Log.i(TAG,result);
    		if(result == null) return;
    		if(result.startsWith(BROADCAST_HEADER_TOAST)) {
    			result = result.substring(BROADCAST_HEADER_TOAST.length()-1);
    			Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
    		} else if(result.startsWith(BROADCAST_HEADER_PROGRESS)) {
    			// ��sprogress bar
    			HttpMessage msg = new HttpMessage();
    			if(msg.parseMessage(result)) {
	    			if(mmf instanceof OrderUploadFragment) {
	    				OrderUploadFragment fragment = (OrderUploadFragment)mmf;
	    				fragment.bar.setProgress(Integer.parseInt(msg.getReturnMessage()));
	        			Log.d(TAG,"bar setProgress="+fragment.bar.getProgress());
	        			fragment.tvProgress.setText(msg.getReturnMessage()+"%");
	    			}   
    			}
    		} else {
        		HttpMessage msg = new HttpMessage();
        		if(msg.parseMessage(result)) {
        			// show message at bottom line
        			gtv.setVisibility(View.VISIBLE);
        			gtv.setText(msg.getReturnMessage());  
    				// check if no orders not upload
        			QueryNotUploadDataAdapter QNUadapter = new QueryNotUploadDataAdapter(MainMenuActivity.this,DBAdapter);
        			QNUadapter.gatherData(omCurrentAccount.getAccountNo(),String.valueOf(currentCompany.getCompanyID()));
        			if(QNUadapter.getCount()==0) {
    					if(menu!=null) menu.findItem(R.id.mainmenu_item4).setEnabled(false);
    				}
        			// �Yfragment��CoreDownloadFragment�A�Nlist view reset
        			if(mmf instanceof OrderUploadFragment) {
        				OrderUploadFragment fragment = (OrderUploadFragment)mmf;
        				if(msg.getReturnCode().equals(Constant.SUCCESS)) {
        					fragment.bar.setProgress(100);
        					fragment.bar.setVisibility(View.INVISIBLE);
        					fragment.tvProgress.setText("100%");
        					fragment.tvProgress.setVisibility(View.INVISIBLE);
        					gbar.setProgress(100);
        					gbar.setVisibility(View.GONE);
    						// list view disable
        					fragment.lv.setEnabled(true);
        					fragment.isUpload = false;
        					// ���s���o�i�U��table�M��
        					fragment.uploadList = fragment.queryNotUploadCustomerList(omCurrentAccount.getAccountNo(),currentCompany.getCompanyID());
        					fragment.adapter = new ArrayAdapter<OrderUploadView>(MainMenuActivity.this,android.R.layout.select_dialog_multichoice,fragment.uploadList);
        					fragment.lv.setAdapter(fragment.adapter);
        					if(fragment.uploadList.length==0) fragment.b1.setEnabled(false);
        				} else {
        					Log.i(TAG,"MessageCode="+msg.getReturnCode()+",Message="+msg.getReturnMessage());
                			gtv.setVisibility(View.VISIBLE);
        					gtv.setText(msg.getReturnMessage());
        					gbar.setVisibility(View.GONE);
        					fragment.bar.setVisibility(View.INVISIBLE);
        					fragment.tvProgress.setVisibility(View.INVISIBLE);
        					fragment.lv.setEnabled(true);
        					fragment.b3.setEnabled(true);
        					fragment.b4.setEnabled(true);
        					fragment.isUpload = false;        					
        					// ���s���o�i�U��table�M��
        					fragment.uploadList = fragment.queryNotUploadCustomerList(omCurrentAccount.getAccountNo(),currentCompany.getCompanyID());
        					fragment.adapter = new ArrayAdapter<OrderUploadView>(MainMenuActivity.this,android.R.layout.select_dialog_multichoice,fragment.uploadList);
        					fragment.lv.setAdapter(fragment.adapter);
        					if(fragment.uploadList.length==0) fragment.b1.setEnabled(false);
        				}
        			} else if(mmf instanceof QueryNotUploadFragment) {
        				QueryNotUploadFragment qnuf = (QueryNotUploadFragment)mmf;
        				if(msg.getReturnCode().equals(Constant.SUCCESS)) {
	        				// refresh list
	        				qnuf.adapter.gatherData(
	        						omCurrentAccount.getAccountNo(),
	        						String.valueOf(currentCompany.getCompanyID())
	        				);
	        				qnuf.adapter.notifyDataSetChanged();
        				}
        			} else {
    					Log.i(TAG,"MessageCode="+msg.getReturnCode()+",Message="+msg.getReturnMessage());
            			gtv.setVisibility(View.VISIBLE);
    					gtv.setText(msg.getReturnMessage());
    					gbar.setVisibility(View.GONE);        				
        			}
        		} else { 
        			if(code!=null && !code.equals(Constant.SUCCESS)) {
    					if(mmf instanceof OrderUploadFragment) {
            				if(code.equals(Constant.ERROR_CONNECT_NETWORK)) {
            					AlertDialog dialog = mmf.getAlertDialogForMessage(getResources().getString(R.string.takeorderMessage1a),getResources().getString(R.string.Message40));
            					dialog.show();	
            				} else if(code.equals(Constant.ERROR_AUTHETICATE_FAIL)) {
            					AlertDialog dialog = mmf.getAlertDialogForMessage(getResources().getString(R.string.takeorderMessage1a),getResources().getString(R.string.Message41));
            					dialog.show();	
            				}
    					}
        			}
	        		// �Y�DreturnCode:returnMessage�榡�A������ܵ��G
	        		gtv.setVisibility(View.VISIBLE);
	        		gtv.setText(result);
        		}
    		}    		
			
		}
    	
    };*/
    
    /**
     * SellDetail download XML via XMPP
     */
    private BroadcastReceiver intentReceiverSellDetailXML = new BroadcastReceiver() {

    	MainMenuFragment mmf = null;

    	@Override
		public void onReceive(Context context, Intent intent) {
            Log.d(TAG,"intentReceiverSellDetailXML..."+intent.getAction());
    		mmf = (MainMenuFragment)getFragmentManager().findFragmentById(R.id.main_frameLayout1);
    		String result = intent.getStringExtra(LikSysSellDetailDownloadIntentService.RESULT);
    		Log.i(TAG,result);
    		if(result.startsWith(BROADCAST_HEADER_PROGRESS)) {
    			// ��sprogress bar
    			HttpMessage msg = new HttpMessage();
    			if(msg.parseMessage(result)) {
	    			if(mmf instanceof CoreDownloadFragment) {
	    				CoreDownloadFragment fragment = (CoreDownloadFragment)mmf;
	    				fragment.bar.setProgress(Integer.parseInt(msg.getReturnMessage()));
	        			Log.d(TAG,"bar setProgress="+fragment.bar.getProgress());
	        			fragment.tvProgress.setText(msg.getReturnMessage()+"%");
	    			}   
    			}
    		} else {
	    		HttpMessage msg = new HttpMessage();
	    		if(msg.parseMessage(result)) {
	       			// �Yfragment��CoreDownloadFragment�A�Nlist view reset
	    			if(mmf instanceof CoreDownloadFragment) {
	    				CoreDownloadFragment fragment = (CoreDownloadFragment)mmf;
						if(msg.getReturnCode().equals(Constant.SUCCESS)) {
							fragment.bar.setProgress(100);
							fragment.bar.setVisibility(View.INVISIBLE);
							fragment.tvProgress.setText("100%");
							fragment.tvProgress.setVisibility(View.INVISIBLE);
						//	gbar.setProgress(100);
						//	gbar.setVisibility(View.GONE);
							// list view disable
							fragment.lv.setEnabled(true);
							fragment.b3.setEnabled(true);
							fragment.b4.setEnabled(true);
							fragment.isDownload = false;
							// 重新取得可下載table清單
							fragment.removeFromToDoTableList(SellDetail.TABLE_NAME);
							fragment.startDownloadFileListTask(false);
						} else {
							Log.i(TAG,"MessageCode="+msg.getReturnCode()+",Message="+msg.getReturnMessage());
		        		//	gtv.setVisibility(View.VISIBLE);
						//	gtv.setText(msg.getReturnMessage());
							Toast.makeText(MainMenuActivity.this, msg.getReturnMessage(), Toast.LENGTH_SHORT).show();
						//	gbar.setVisibility(View.GONE);
							fragment.bar.setVisibility(View.INVISIBLE);
							fragment.tvProgress.setVisibility(View.INVISIBLE);
							fragment.lv.setEnabled(true);
							fragment.b3.setEnabled(true);
							fragment.b4.setEnabled(true);
							fragment.isDownload = false;     
						//	gbar.setVisibility(View.GONE);
							// 重新取得可下載table清單
							fragment.startDownloadFileListTask(false);
						}
	    			}
	    		}
    		}
		}
    	
    };
    
    /**
     * Instant Messenger BroadcastReceiver
     */
    private BroadcastReceiver intentReceiverIMS = new BroadcastReceiver() {

    	MainMenuFragment mmf = null;
    	
		@Override
		public void onReceive(Context context, Intent intent) {
            Log.d(TAG,"intentReceiverIMS..."+intent.getAction());
    		String result = intent.getStringExtra(InstantMessengerService.RESULT);
    		Log.i(TAG,result);
    		mmf = (MainMenuFragment)getFragmentManager().findFragmentById(R.id.main_frameLayout1);
            if(result.startsWith(InstantMessengerService.BROADCAST_TOAST)) {
            	Toast.makeText(
            		getApplicationContext(), 
            		result,
                    Toast.LENGTH_SHORT
                    ).show();
    			return;
            }
            if(result.startsWith(InstantMessengerService.BROADCAST_DISPLAY)) {
            	fastBut3.setVisibility(View.VISIBLE);
            	//fastBut3.startAnimation(anim);
            	return;
            }
		}
    };
    
    /**
     * Instant Messenger ServiceConnection 
     */
    final Messenger imMessenger = new Messenger(new InstantMessageHandler() {

		@Override
		public void handleToServiceExt(Message msg) {
			// do nothing			
		}

		@Override
		public void handleFromServiceExt(Message msg) {
			Log.i(TAG,"ivMsg Visibility="+fastBut3.getVisibility());
			// implement if get msg from service
			if(msg.obj==null) return;
			if(msg.obj.equals(InstantMessengerService.BROADCAST_TOAST)) {
				Toast.makeText(
            		getApplicationContext(), 
            		msg.getData().getString(InstantMessageHandler.MSG_CONTENT),
                    Toast.LENGTH_SHORT
                    ).show();
				return;
			}
			if(msg.obj.equals(InstantMessengerService.BROADCAST_DISPLAY)) {
				Log.i(TAG,"enter BROADCAST_DISPLAY");
	        	InstantMessages omIM = new InstantMessages();
	        	if(omIM.testTableExists(DBAdapter)) {
	        		omIM.setUserNo(omCurrentAccount.getAccountNo());
	        		List<InstantMessages> lt = omIM.getMessages(DBAdapter);
	        		Log.d(TAG,"unread messages="+lt.size());
	        		if(lt.size()>0) {
	        			//fastBut3.setText(String.valueOf(lt.size()));
	    				fastBut3.setVisibility(View.VISIBLE);
	                	//fastBut3.startAnimation(anim);
	                	palyNotificationSound();
	        		}
	        	}
	        	return;
			}			
		}
    	
    });
    private ServiceConnection imConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            imService = new Messenger(service);
            try {
                Message msg = Message.obtain(null, InstantMessageHandler.MSG_REGISTER_CLIENT,SERVICE_ID,0);
                msg.replyTo = imMessenger;
                imService.send(msg);
                Log.i(TAG,"Instant Messenger service Connected.");
            } catch (RemoteException e) {
                // In this case the service has crashed before we could even do anything with it
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been unexpectedly disconnected - process crashed.
            imService = null;
            Log.i(TAG,"Instant Messenger service Disconnected.");
        }
    };
	
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * 發出系統預設notification sound
	 */
	public void palyNotificationSound() {
		Uri defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		MediaPlayer mediaPlayer = new MediaPlayer();

		try {
		      mediaPlayer.setDataSource(this, defaultRingtoneUri);
		      mediaPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
		      mediaPlayer.prepare();
		      mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

		         @Override
		         public void onCompletion(MediaPlayer mp)
		         {
		            mp.release();
		         }
		      });
		      mediaPlayer.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		
		/*if(DBAdapter==null || DBAdapter.getCompanyID()==0) 
			return;
		SimpleDateFormat sdfrun = new SimpleDateFormat("yyyyMMdd",Locale.CHINESE);
		Calendar cal = Calendar.getInstance();
		cal.setTime(getCurrentDate());
		// remove garbage file
		cal.add(Calendar.DAY_OF_MONTH, 0-iOrdersBackupFileDays);
	    SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
	    String backupDir = settings.getString(SetupBackupDirFragment.BACKUPDIR_KEY, null);*/
		
	}
	
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop called!");
        isStopCalled = true;

        // save state
        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(IS_STOP_CALLED_KEY, isStopCalled);
        editor.putString(KEY_ACCOUNT, omCurrentAccount.getAccountNo());
        if(currentDept.getCompanyID()!=0) editor.putInt(KEY_DEPTID, currentDept.getCompanyID());
        editor.commit();

        // The activity is no longer visible (it is now "stopped")
		unregisterReceiver(intentReceiver);
		//unregisterReceiver(intentReceiverUpload);
		//unregisterReceiver(btReceiver);
		//unregisterReceiver(intentReceiverRealStockReply);
		unregisterReceiver(intentReceiverSellDetailXML);
		unregisterReceiver(intentReceiverIMS);
		// stop thread
		isAlive = false;
		// dismiss update task dialog
		/*if(verifyApkVersionTask!=null &&
				verifyApkVersionTask.dialog!=null &&
				verifyApkVersionTask.dialog.isShowing()) verifyApkVersionTask.dialog.dismiss();*/
    }
    
    @Override      
    public void onDestroy() {

    		// stop bluetooth
		//if(mService != null) mService.stop();
    	super.onDestroy();
    	Log.d(TAG,"onDestroy called!");
    	isAlive = false;
    	/*MapTrackerFragment.isDownloadPositionTask = false;
    	MapTrackerFragment.isGetPositionThread = false;*/
    	DBAdapter.endTransaction();
//    	DBAdapter = null;
    	//autoUploadOrdersThread.setRun(false);
		if(isIMEnable && isAliveIMS) {
//			InstantMessengerService.isAliveIMS = false;
			if(imService!=null) {
                try {
                    Message msg = Message.obtain(null, InstantMessageHandler.MSG_UNREGISTER_CLIENT);
                    msg.replyTo = imMessenger;
                    imService.send(msg);
                } catch (RemoteException e) {
                    // There is nothing special we need to do if the service has crashed.
                }				
			}
			if(imServiceBinded) {
				this.unbindService(imConnection);
				imServiceBinded = false;
			}
    		Intent i = new Intent(this, InstantMessengerService.class);
    		/*if(omCurrentSysProfile.isCloud()) {
    			i = new Intent(this, InstantMessengerMqttService.class);
    		}*/
    		i.putExtra("screen_state", true);
    		this.stopService(i);    		
    		Log.i(TAG,"stoping InstantMessengerService...");
    		finishAffinity();
		}
		
		// release patrol wake lock
		if(wlp!=null) wlp.release();
		
		unregisterReceiver(timeChangedReceiver);
		unregisterReceiver(wifiChangedReceiver);
		unregisterReceiver(networkChangedReceiver);
		
		// remove saved setting
       /* SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(SubAddFragment.LAST_SELECTED_CATEGORY_KEY);
        editor.remove(SubAddFragment.LAST_SELECTED_SUPPLIER_KEY);
        editor.remove(SubAddFragment.LAST_SELECTED_SEARCH_KEY);
        editor.remove(SubAddFragment.LAST_SELECTED_SEARCHVALUE_KEY);
        editor.remove(SubAddFragment.LAST_SELECTED_LVPOSITION_KEY);
        editor.remove(SubAddFragment.LAST_SELECTED_CACHEORALL_KEY);
        editor.remove(SubDetailFragment.LAST_SELECTED_LVPOSITION_KEY);
        editor.remove(SubStockFragment.LAST_SELECTED_LVPOSITION_KEY);
       // Commit the edits!
        editor.commit();*/
		
    }
    
	private AlertDialog getExitDialog(String title,String message) {
		Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
    	builder.setTitle(title);
    	builder.setMessage(message);
    	String msg = getResources().getString(R.string.Button3);
    	builder.setPositiveButton(msg, new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			//Close Application
    			 Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
    			 intent.putExtra("DEVICEID", DEVICEID);
    			 startActivity(intent);
    			 finish();
    			//finishAffinity();
    		     
    		}
    	});
     	msg = getResources().getString(R.string.Button2);
    	builder.setNegativeButton(msg, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//Cancel Dialog
			}
    		
    	});
    	return builder.create();
		
	}
    
    @Override
    public void onBackPressed() {
    	// do nothing when press back button
    }
    
    public int getIMNotReadCount(){
    	SQLiteDatabase db = DBAdapter.getReadableDatabase();
		String coustomersStr="InstantMessages"+this.currentDept.getCompanyID();
		Cursor c2=db.query(false,
				"sqlite_master",				//��ƪ�W��
				new String[] {"name"},	//���W��
				"type='table' AND (name='"+coustomersStr+"') ;",				//WHERE
				null, // WHERE ���Ѽ�
				null, // GROUP BY
				null, // HAVING
				null, // ORDOR BY
				null  // ����^�Ǫ�rows�ƶq
				);
		if(c2.getCount()==1){
			Cursor c=db.query(false,
					"InstantMessages"+this.currentDept.getCompanyID(),				//��ƪ�W��
					new String[] {"SerialID"},	//���W��
					"IsRead = 0;",				//WHERE
					null, // WHERE ���Ѽ�
					null, // GROUP BY
					null, // HAVING
					null, // ORDOR BY
					null  // ����^�Ǫ�rows�ƶq
					);
			//setBadges(c.getCount());
			return c.getCount();
		}else{
			return 0;
		}
    	
    }
    public void setBadges(){
    	
			setBadges(getIMNotReadCount());
			
		
    }
    
    public void setBadges(int count) {
		// TODO Auto-generated method stub
    	badge.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
    	badge.setBadgeBackgroundColor(Color.parseColor("#80F12300"));
    	badge.setTextSize(10);
    	if(count<1){
    		badge.hide();
    	}else if(count>0 && count<100){
    		badge.setText(""+count);
    		badge.show();
    	}else{
    		badge.setText("99+");
    		badge.show();
    	}
	}

    
 	public static CurrentCompany getCurrentCompany() {
 		CurrentCompany omCurrentCompany = new CurrentCompany();
 		omCurrentCompany.getCurrentCompany(DBAdapter);
 		if(omCurrentCompany.getRid() == 0)
 			Log.d(TAG,"getCuuretCompany success="+omCurrentCompany.getCompanyName() + " DeptNo="+ omCurrentCompany.getDeptNO());
 			
 		return omCurrentCompany;

 	}
	
	private void openDB(){
    	DH = new DBHelper(this.getApplicationContext());  
    }
	
	  private void closeDB(){
	    	DH.close();    	
	   }
 
	public static int differentDaysByMillisecond(Date date1,Date date2)
	  {
	        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
	        return days;
	  }

}
