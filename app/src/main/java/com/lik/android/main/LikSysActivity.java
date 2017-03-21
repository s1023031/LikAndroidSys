package com.lik.android.main;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONObject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.SQLException;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lik.Constant;
import com.lik.android.om.Account;
import com.lik.android.om.BaseOM;
import com.lik.android.om.Company;
import com.lik.android.om.ConfigControl;
import com.lik.android.om.SiteIPList;
import com.lik.android.om.SiteInfo;
import com.lik.android.om.SysProfile;
import com.lik.android.om.UserCompy;
import com.lik.android.om.Users;
import com.lik.android.util.FileUtil;
import com.lik.android.util.HttpUtil;
import com.lik.android.util.OmUtil;
import com.lik.android.view.BaseDataAdapter;
import com.lik.android.view.CompanyNameView;
import com.lik.android.view.DeptNameView;
import com.lik.util.ExternalStorage;

public class LikSysActivity extends Activity implements OnClickListener{

	private static final long serialVersionUID = -3168252658129327760L;

	private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
	private static final int CAMERA = 2;
	private static final int WRITE_EXTERNAL_STORAGE=3;
	
	public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
	private static final String TAG = LikSysActivity.class.getName();
	protected static final int HISTORY_DOWNLOAD_PERIOD = 12; // 預設12個月
	static String DEVICEID;
    public static String PackageName;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy 年 MM 月 dd 日 HH 時 mm 分",Locale.CHINESE);
	SimpleDateFormat sdf2 = new SimpleDateFormat(" 年 MM 月 dd 日 HH 時 mm 分",Locale.CHINESE);

	IntentFilter intentFilter;
     
	boolean isDebug = true;
	// keep current SysProfile
	SysProfile omCurrentSysProfile;
	// keep current Account
	Account omCurrentAccount;
	// keep current selected CompanyID
	DeptNameView currentDept = new DeptNameView();
	CompanyNameView currentCompany = new CompanyNameView();
	BaseDataAdapter<DeptNameView> adapter;
	BaseDataAdapter<CompanyNameView> cmpyAdapter;
	
	
	// keep db reference
	public static LikDBAdapter DBAdapter;
	// keep record the current UI
	int uiNo=1;

	String tabletSerialNoList;
	private String ip,http_port,xmpp_port; 
	boolean isUir003Entered = false;
    boolean isSkipLogin = true;
	Intent intent;
	String sLikURL;
	
	boolean isSecure = false;
	public static String  companyNo; // 公司賬號
	String password;  // 公司密碼
	
	@Override
	public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
	    switch (requestCode) 
	    {
	    	case REQUEST_ID_MULTIPLE_PERMISSIONS:
	    	{
	    		Map<String, Integer> perms = new HashMap<>();
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "permission granted");
                    }else
                    {
                        Log.d(TAG, "Some permissions are not granted ask again ");
                        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) 
                        	|| shouldShowRequestPermissionRationale( Manifest.permission.ACCESS_FINE_LOCATION)
                        	|| shouldShowRequestPermissionRationale( Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            showDialogOK("此 APP 需要相機和定位的權限",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    break;
                                            }
                                        }
                                    });
                        }
                        else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                break;
	    	}
	    		
	    }
	}
	
    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }
	
	private  boolean checkAndRequestPermissions() {
	    int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
	    int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
	    int writeStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
	    
	    List<String> listPermissionsNeeded = new ArrayList<>();
	    if (locationPermission != PackageManager.PERMISSION_GRANTED) {
	        listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
	    }
	    if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
	        listPermissionsNeeded.add(Manifest.permission.CAMERA);
	    }
	    if (writeStoragePermission != PackageManager.PERMISSION_GRANTED) {
	        listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
	    }
	    if (!listPermissionsNeeded.isEmpty()) {
	        requestPermissions(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
	        return false;
	    }
	    return true;
	}
	
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		isUir003Entered = false;
	    DBAdapter = new LikDBAdapter(getBaseContext(),true);
	    DEVICEID = Settings.System.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
	    
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
	    	checkAndRequestPermissions();
	    }
	 
	  
	    // get package info
        PackageManager pmr = getPackageManager();
        PackageInfo packageInfo;
		try {
			packageInfo = pmr.getPackageInfo(this.getPackageName(), 0);
			PackageName = packageInfo.packageName;
	        Log.d(TAG,"packageInfo.packageName="+PackageName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		if(getResources().getString(R.string.isSecure).equals("true")) {
			isSecure = true; 
		}
		Log.d(TAG,"isSecure="+isSecure);
		 
		sLikURL = getResources().getString(R.string.LikHostURL);
        if(isSecure) {
        	sLikURL = getResources().getString(R.string.LikHostURLS);
			if(getResources().getString(R.string.Test_Tablet).indexOf(DEVICEID) >=0) {
				sLikURL = getResources().getString(R.string.LikTestURLS);
			}       	
        } else {
			if(getResources().getString(R.string.Test_Tablet).indexOf(DEVICEID) >=0) {
				sLikURL = getResources().getString(R.string.LikTestURL);
			}
        }
        
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); 
        if (sdCardExist) {
        	sdDir = Environment.getExternalStorageDirectory();
        	Log.d(TAG,sdDir.getAbsolutePath());
        } else {
        	Log.d(TAG,"sd card not exist");
        }

        ConfigControl omConfig= new ConfigControl();
        ArrayList<ConfigControl> alConfig = omConfig.getConfigControl(DBAdapter);
        if(alConfig.size() == 0)
        {
        	omConfig.findByKey(DBAdapter);
        	if(omConfig.getRid()<0) {
        		omConfig.setLoginFlag("Y");
        		omConfig.doInsert(DBAdapter);;
        		Log.i(TAG,"Table Name: ConfigControl");
        		Log.i(TAG,"inserted ConfigControl:LoginFlag->"+omConfig.getLoginFlag());
        	}
        }else
        	omConfig=alConfig.get(0);
        	
        SysProfile omSP = new SysProfile();
        ArrayList<SysProfile> alSP = omSP.getAllSysProfile(DBAdapter);
        if(isDebug) 
        	Log.d(TAG,"SysProfile size="+alSP.size());
        if(alSP.size() > 0 && omConfig.getLoginFlag().equals("Y")) {
        	if(alSP.size()==1) 
        		omCurrentSysProfile = alSP.get(0);
        	
        	Intent intent = new Intent(LikSysActivity.this,LoginActivity.class);
        	
        	intent.putExtra("DEVICEID", DEVICEID);
        	intent.putExtra(MainMenuActivity.KEY_SYSPROFILE, omCurrentSysProfile);

        	startActivity(intent);

        } else {
        	setContentView(R.layout.uir001);
       
        	EditText et1 = (EditText)findViewById(R.id.uir001_editText1);
        	
        	et1.setOnFocusChangeListener(new OnFocusChangeListener() {
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					TextView tv11 = (TextView)findViewById(R.id.uir001_textView11);
					if(tv11!=null) tv11.setText("");
				}	
        	});
        	
        	// set password
        	EditText et2 = (EditText)findViewById(R.id.uir001_editText2);
        	et1.setText("LIK");
	   		et2.setText("86953527");   
            final RelativeLayout btn1 = (RelativeLayout)findViewById(R.id.uir001_button1);
            btn1.setOnClickListener(this);
            final RelativeLayout btn2 = (RelativeLayout)findViewById(R.id.uir001_button2);
            btn2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					finish();
				}
            });
        }	
        
       // register filter
        intentFilter = new IntentFilter();
        intentFilter.addAction(LikSysInitIntentService.LIKSYS_INIT_ACTION);
        registerReceiver(intentReceiver,intentFilter);

        // stick on portrait 
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        	
      }
        
	  @Override      
	  public void onStop() {
	    super.onStop();
	    ConfigControl omConfig= new ConfigControl();
        ArrayList<ConfigControl> alConfig = omConfig.getConfigControl(DBAdapter);
    	Log.d(TAG,"ConfigControl size="+alConfig.size());
        if(alConfig.size() == 0)
        {
        	omConfig.findByKey(DBAdapter);
        	if(omConfig.getRid()<0) {
        		omConfig.setLoginFlag("Y");
        		omConfig.doInsert(DBAdapter);;
        		Log.i(TAG,"Table Name: ConfigControl");
        		Log.i(TAG,"inserted ConfigControl:LoginFlag->"+omConfig.getLoginFlag());
        	}
        }else
        {
        	omConfig=alConfig.get(0);
            if(omConfig.getLoginFlag().equals("F"))
            {
                omConfig.findByKey(DBAdapter);
          	    omConfig.setLoginFlag("Y");
          	    omConfig.doUpdate(DBAdapter);
            }
        	
        }
	  
	  
	    Log.d(TAG,"onStop called!");
	  }
	    
	  @Override      
	  public void onDestroy() {
	    super.onDestroy();
	    Log.d(TAG,"onDestroy called!");
	    // unregister Receiver
	    unregisterReceiver(intentReceiver);
	    // close databasen no, use for MainmenuActivity
	     DBAdapter.endTransaction();
	}

	public void onClick(final View clicked) {
	    Intent intent;
	    if(isDebug) Toast.makeText(this,"button clicked!"+uiNo,Toast.LENGTH_SHORT).show();
	    switch(uiNo) {
		    case 1:
		    	String systemNo = getResources().getText(R.string.app_code).toString();
		    	EditText et1 = (EditText)findViewById(R.id.uir001_editText1);
		    	companyNo = et1.getText().toString();
		   		EditText et2 = (EditText)findViewById(R.id.uir001_editText2);
		   		password = et2.getText().toString();
		    //	password = Hash.getHash(password).trim();
		    	Log.d(TAG, "password = "+password);
		    	String url = sLikURL;
		    	//url += "processVerifyCompanyID.action";
		    	url += "VerifyCompanyID.action";
		    	Log.i(TAG, "connecting..."+url);
		        if(isDebug) 
		        	Toast.makeText(this,"connecting..."+url,Toast.LENGTH_SHORT).show();
		        if(isSecure) {
		       	//	new HttpsProcessVerifyCompanyIDTask(this).execute(url,companyNo,systemNo,password);
		       	} else {
		       		new ProcessVerifyCompanyID().execute(url,companyNo,systemNo,password);
		       	}
		   		break;
		    case 2:
		    	EditText et4 = (EditText)findViewById(R.id.uir002_editText1);
		   		String accountNo = et4.getText().toString();
				EditText et5 = (EditText)findViewById(R.id.uir002_editText2);
	    		autheticate(accountNo,et5);		            
		    	break;
		    case 3:
		    	EditText et31 = (EditText)findViewById(R.id.uir003_editText1);
		   		ip = et31.getText().toString();
		   		EditText et32 = (EditText)findViewById(R.id.uir003_editText2);
		   		http_port = et32.getText().toString();
	    		EditText et33 = (EditText)findViewById(R.id.uir003_editText3);
	    		xmpp_port = et33.getText().toString();
	    		if(isUir003Entered) {
	    			return;
		    	}
		   		//new ProcessSiteIP().execute(ip,http_port,DEVICEID);
	    		new ProcessCompany().execute(ip,http_port,DEVICEID);
		   		break;
		    case 4:
		    	Log.d(TAG,"button4");
		    	EditText etA4 = (EditText)findViewById(R.id.login_editText1);
		    	accountNo = etA4.getText().toString();
		    	EditText etPassword = (EditText)findViewById(R.id.login_editText2);
		    	autheticate(accountNo,etPassword);
		    	break;
	        }
	    }
	    
	private void autheticate(String accountNo, EditText etPassword) {
	    	final Intent intent;
	    	Log.d(TAG,"autheticate");
	    	UserCompy omUC = new UserCompy();
	    	omUC.setAccountNo(accountNo);
	    	omUC.setCompanyParent(companyNo);
	    	List<UserCompy> ltUC = omUC.getUserCompyByAccountNo(DBAdapter);
	    	if(ltUC.size()==0) {
	    		switch(uiNo) {
	    		case 2:
					TextView tv11 = (TextView)findViewById(R.id.uir002_textView11);
					if(tv11 != null) {
						tv11.setVisibility(View.VISIBLE);
						tv11.setText(getString(R.string.Message30));
					}
					break;
	    		case 4:
					tv11 = (TextView)findViewById(R.id.login_textView11);
					if(tv11 != null) {
						tv11.setVisibility(View.VISIBLE);
						tv11.setText(getString(R.string.Message30));
					}
					break;    			
				}
	    		return;
	    	}

			Account omA = new Account();
			omA.setAccountNo(accountNo);
			omA.setSerialID(omCurrentSysProfile.getSerialID());
			omA = omA.getAccountByAccountNo(DBAdapter);
			if(omA.getRid()<0) { 
				String msg2 = getResources().getString(R.string.Message2);
				String msg3 = getResources().getString(R.string.Message3);
				final AlertDialog alertDialog = getAlertDialogForAccountCreate(msg2,msg3);    			
				alertDialog.show();    			
			} else {
				
				TextView tv11 = (TextView)findViewById(R.id.uir002_textView11);
				tv11.setVisibility(View.VISIBLE);
				tv11.setText(getString(R.string.AccoutDuplicate));

				/*	omCurrentAccount = omA;
				String password = etPassword.getText().toString();
				if(password.equals(omA.getPassword())) {
			    	if(isDebug) 
			    		Toast.makeText(this,"Finishing login process, go to main menu!", Toast.LENGTH_SHORT).show();
		    		// init DB if not exists
					DBAdapter.setCompanyID(currentDept.getCompanyID());
					MainMenuActivity.companyID = currentDept.getCompanyID();
					for(int i=0;i<LikDBAdapter.DATABASE_TABLES_FOR_DOWNLOAD.length;i++) {
						BaseOM<?> om = LikDBAdapter.DATABASE_TABLES_FOR_DOWNLOAD[i];
						om.setTableCompanyID(currentDept.getCompanyID());
						if(om.testTableExists(DBAdapter)) continue;
			    		try {
							String cmd = om.getCreateCMD();
			    			if(cmd != null) DBAdapter.getdb().execSQL(cmd);	
			    			String[] cmds = om.getCreateIndexCMD();
			    			for(int j=0;j<cmds.length;j++) {
			    				cmd = cmds[j];
			    				DBAdapter.getdb().execSQL(cmd);	
			    			}
			    		} catch (SQLException e) {
			    			Log.w(TAG,"exception while creating table..."+om.getTableName());
			    			e.printStackTrace();
			    		}						
					}		    		

					/*Users omU = new Users();
					omU.setUSER_NO(omCurrentAccount.getAccountNo());
					omU.findByKey(DBAdapter);
					if(omU.getRid()>=0) {
						omCurrentAccount.setBOSS_USERNO(omU.getBOSS_USERNO());
						omCurrentAccount.setLOOK_MAPTRACK(omU.getLOOK_MAPTRACK());
						omCurrentAccount.setAccountName(omU.getUSER_NAME());
					}
			    	
					intent = new Intent(LikSysActivity.this,MainMenuActivity.class);
		    		intent.putExtra(MainMenuActivity.KEY_SYSPROFILE,omCurrentSysProfile);
		    		intent.putExtra(MainMenuActivity.KEY_ACCOUNT,omCurrentAccount);
		    		intent.putExtra(MainMenuActivity.KEY_DEPTID,currentDept);
					startActivity(intent);
					finish(); // finish LikSysActivity
				} else {
					etPassword.setText("");
					etPassword.requestFocus();    				
				}*/
			}  	
	}

	private AlertDialog getAlertDialogForAccountCreate(String title,String message) {
    	Builder builder = new AlertDialog.Builder(LikSysActivity.this);
    	builder.setTitle(title);
    	builder.setMessage(message);
    	String msg = getResources().getString(R.string.Button1);
    	builder.setPositiveButton(msg, new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			processAccountCreate();
    		}
    	});
    	msg = getResources().getString(R.string.Button2);
    	builder.setNegativeButton(msg, new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			EditText et1 = null ;
    			EditText et2 = null;
    			switch(uiNo) 
    			{
	    			case 2:
	        			et1 = (EditText)findViewById(R.id.uir002_editText1);
	        			et2 = (EditText)findViewById(R.id.uir002_editText2);
	        			et1.setText("");
	        			et2.setText("");
	        			et1.requestFocus();
	        			break;
	    			case 4:
	        		    et1 = (EditText)findViewById(R.id.login_editText1);
	        			et2 = (EditText)findViewById(R.id.login_editText2);
	        			et1.setText("");
	        			et2.setText("");
	        			et1.requestFocus();
    			}
    		}
    	});
    	return builder.create();
    }


    private void processAccountCreate() {    	
		String accountNo=null,password=null;
		if(uiNo==2) {
			EditText et1 = (EditText)findViewById(R.id.uir002_editText1);
			accountNo = et1.getText().toString();
			EditText et2 = (EditText)findViewById(R.id.uir002_editText2);
			password = et2.getText().toString();
		} else if(uiNo==4) {
			EditText et1 = (EditText)findViewById(R.id.login_editText1);
			accountNo = et1.getText().toString();
			EditText et2 = (EditText)findViewById(R.id.login_editText2);
			password = et2.getText().toString();						
		}
		String url = "http://";
		url += ip;
		url += ":";
		url += http_port;		
		url += getResources().getText(R.string.verifyUserURI);	
		if(omCurrentSysProfile.isCloud()) {
			//new HttpsProcessVerifyUserTask(this).execute(accountNo,password);
		} else {
			new ProcessVerifyUser().execute(url,accountNo,password);			
		}

    }
    
    class ProcessVerifyCompanyID extends AsyncTask<String, Integer, String> {
    	
    	String companyNo;
    	String systemNo;
    	String password;
    	protected String doInBackground(String... params) {
    		companyNo = params[1];
    		systemNo = params[2];
    		password = params[3];
    		Log.d(TAG,"password="+password);
    		String result = null;
    	    HttpClient httpclient = new DefaultHttpClient();	
    	    try {
    	    	HttpPost httppost = new HttpPost(params[0]);
    	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    	        nameValuePairs.add(new BasicNameValuePair("companyNo",companyNo));
    	        nameValuePairs.add(new BasicNameValuePair("systemNo",systemNo));
    	        nameValuePairs.add(new BasicNameValuePair("password",password));
    	      //  nameValuePairs.add(new BasicNameValuePair("userNo","137"));
    	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    	        ResponseHandler<String> responseHandler=new BasicResponseHandler();

    	        result = httpclient.execute(httppost,responseHandler).trim();
    	        Log.d(TAG,"result="+result);
	    	    // When HttpClient instance is no longer needed,
	    	    // shut down the connection manager to ensure
	    	    // immediate deallocation of all system resources
    	        httpclient.getConnectionManager().shutdown();
    	    } catch (ClientProtocolException e) {
    	        Log.e(TAG,e.fillInStackTrace().toString());
    	    } catch (IOException e) {
    	        Log.e(TAG,e.fillInStackTrace().toString());
    	    }
    		return result;
    	}
    	
    	protected void onPostExecute(String result) {
    		if(result == null) {
				TextView tv11 = (TextView)findViewById(R.id.uir001_textView11);
				if(tv11 != null) {
					tv11.setVisibility(View.VISIBLE);
					tv11.setText(getString(R.string.Message7d));
				}
				return;
    		}
    		Log.d(TAG,"ProcessVerifyCompanyID result="+result);
			if(result.startsWith(Constant.SUCCESS)) {
				OmUtil util = OmUtil.getInstance();
				Map<String,Map<String,String>> map = new TreeMap<String,Map<String,String>>();
				try {
					util.toMap(map, result.substring(Constant.SUCCESS.length()+1));
					Map<String,String> header = map.get(OmUtil.TABLE_HEADER);
					String tableName = header.get(OmUtil.TABLE_HEADER);
					Map<String,String> detail = map.get(OmUtil.TABLE_DETAIL);
					Log.d(TAG,"tableName="+tableName);
					if(tableName.equals("RegisterInfo")) {
						Log.i(TAG,"update SysProfile...");
						omCurrentSysProfile = new SysProfile();
						omCurrentSysProfile.setCompanyNo(companyNo);
						omCurrentSysProfile.setSystemNo(systemNo);
						omCurrentSysProfile.setVersionInfo(detail.get(SysProfile.COLUMN_NAME_VERSIONINFO));
						omCurrentSysProfile.setStockInfo(detail.get(SysProfile.COLUMN_NAME_STOCKINFO));
						omCurrentSysProfile.setCameraInfo(detail.get(SysProfile.COLUMN_NAME_CAMERAINFO));
						omCurrentSysProfile.setTelephoneInfo(detail.get(SysProfile.COLUMN_NAME_TELEPHONEINFO));
						omCurrentSysProfile.setMapInfo(detail.get(SysProfile.COLUMN_NAME_MAPINFO));
						omCurrentSysProfile.setMapTrackerInfo(detail.get(SysProfile.COLUMN_NAME_MAPTRACKERINFO));
						omCurrentSysProfile.setLastModifiedDate(new Date());
						omCurrentSysProfile.setHistoryPeriod(HISTORY_DOWNLOAD_PERIOD);
						omCurrentSysProfile.setButtonAlign(detail.get(SysProfile.COLUMN_NAME_BUTTONALIGN));
						omCurrentSysProfile.setInstantMessengerInfo(detail.get(SysProfile.COLUMN_NAME_INSTANTMESSENGERINFO));
						if(detail.get(SysProfile.COLUMN_NAME_DOWNLOADMINUTE)!=null) 
							omCurrentSysProfile.setDownLoadMinute(Integer.parseInt(detail.get(SysProfile.COLUMN_NAME_DOWNLOADMINUTE)));
						omCurrentSysProfile.setCompanyName(detail.get(SysProfile.COLUMN_NAME_COMPANYNAME));
						// verify tablet
						String url = sLikURL;
						url += "processVerifyTablet.action?";
						url += "companyNo="+companyNo+"&";
						url += "systemNo="+systemNo+"&";
				        //String deviceId = Settings.System.getString(getContentResolver(),Settings.System.ANDROID_ID);
				        if(isDebug) 
				        	Log.d(TAG,DEVICEID);

						url += "serialNo="+DEVICEID;
						Log.i(TAG, "connecting..."+url);
						new ProcessVerifyTablet().execute(url); 
					}
				} catch (IOException e) {
					Log.e(TAG,"parse return message error");
					return;
				}				

			} else {
				TextView tv11 = (TextView)findViewById(R.id.uir001_textView11);
				if(tv11 != null) {
					tv11.setVisibility(View.VISIBLE);
					tv11.setText(getString(R.string.Message7d));
				}
				return;
			}
    	}
    	
    }
    
    class ProcessVerifyTablet extends AsyncTask<String, Integer, String> {
    	
    	protected String doInBackground(String... params) {
    		String result = null;
			try {
				result = HttpUtil.httpConnect(params[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}
    		return result;
    	}
    	
    	protected void onPostExecute(String result) {
    		if(result == null) {
				TextView tv11 = (TextView)findViewById(R.id.uir001_textView11);
				if(tv11 != null) {
					tv11.setVisibility(View.VISIBLE);
					tv11.setText(getString(R.string.Message7d));
				}
				return;
    		}
    		Log.d(TAG,"ProcessVerifyTablet result="+result);
    		String[] split = result.split(":");    		
			if(split.length==2) {
				if(split[0].equals(Constant.SUCCESS)) {
					Log.d(TAG,"to uir003...");
					tabletSerialNoList = split[1];
					uiNo =3;
					setContentView(R.layout.uir003);
		        	EditText et1 = (EditText)findViewById(R.id.uir003_editText1);
		        	et1.setText("59.126.49.129");
		            // set default port
		            EditText et2 = (EditText)findViewById(R.id.uir003_editText2);
		            et2.setText(R.string.http_port);
		            EditText et3 = (EditText)findViewById(R.id.uir003_editText3);
		            et3.setText(R.string.queue_port);
		        	
		            final RelativeLayout btn1 = (RelativeLayout)findViewById(R.id.uir003_button1);
		            btn1.setOnClickListener(LikSysActivity.this);
		            final RelativeLayout btn2 = (RelativeLayout)findViewById(R.id.uir003_button2);
		            btn2.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							finish();
						}	
		            });
		 
				} else if(split[0].equals(Constant.ERROR_COMPANYNOTREGISTER)) {
					String msg = getResources().getString(R.string.Message6);
					TextView tv11 = (TextView)findViewById(R.id.uir001_textView11);
					if(tv11 != null) {
						tv11.setVisibility(View.VISIBLE);
						tv11.setText(msg);
					}
				} else if(split[0].equals(Constant.ERROR_REGISTERCONSTRAINT)) {
					String msg = getResources().getString(R.string.Message7);
					TextView tv11 = (TextView)findViewById(R.id.uir001_textView11);
					if(tv11 != null) {
						tv11.setVisibility(View.VISIBLE);
						tv11.setText(msg);
					}
				} else {
			        String backupDir = getBackupDir();
			        File file = new File(backupDir);
			    	File[] deviceidFile = file.listFiles(new FilenameFilter() {

						@Override
						public boolean accept(File arg0, String arg1) {
							if(arg1.endsWith(".id")) return true;
							else return false;
						}
			    		
			    	});
			    	String oldDEVICEID = "";
			    	if(deviceidFile != null) {
			    		Log.d(TAG,"deviceidFile size="+deviceidFile.length);
			    		if(deviceidFile.length>0) {
			    			String[] name = deviceidFile[0].getName().split("\\.");
				    		if(name.length>0) oldDEVICEID = name[0];			    			
			    		}
			    	}			    	
					String msg = getResources().getString(R.string.Message7a);
					TextView tv11 = (TextView)findViewById(R.id.uir001_textView11);
					if(tv11 != null) {
						tv11.setVisibility(View.VISIBLE);
						tv11.setText(oldDEVICEID+(oldDEVICEID.equals("")?"":":")+DEVICEID+"\n"+msg);
					}
				}
			}
    	}
    	
    }

    class ProcessUpdateRegisterInfoDetail extends AsyncTask<String, Integer, String> {
    	
    	protected String doInBackground(String... params) {
    		String result = null;
			try {
				result = HttpUtil.httpConnect(params[0]);
    			if (result.startsWith("\ufeff")) {
    				result = result.substring(1);
					}
			} catch (IOException e) {
				e.printStackTrace();
			}
    		return result;
    	}
    	
    	protected void onPostExecute(String result) {
    		
    		Log.i(TAG,"ProcessUpdateRegisterInfoDetail Begin");
    		if(result == null) {
				String msg = getResources().getString(R.string.Message5);
				Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();
				return;
    		}
    		//Log.d(TAG,"result123213 = "+ result);
    		
    		try{
					JSONObject jsonObject =new JSONObject(result);
					String errorFlag = (String)jsonObject.get("errorFlag");
					if(errorFlag.equals("true"))
					{
						String msg = companyNo+"不存在";
						TextView tv11 = (TextView)findViewById(R.id.uir003_textView11);
						if(tv11 != null) {
							tv11.setVisibility(View.VISIBLE);
							tv11.setText(msg);
						}
						return;
					}
					JSONArray siteInfoArray =jsonObject.getJSONArray("siteInfoList");
					Log.d(TAG,"siteInfoArray = "+ siteInfoArray.length());
					SiteInfo omSiteInfo = new SiteInfo();

					omSiteInfo.deleteSiteInfo(DBAdapter);
					for( int i = 0 ; i < siteInfoArray.length();i++)
					{
						jsonObject = siteInfoArray.getJSONObject(i);
						String siteName =  jsonObject.getString("siteName");
						String parent =  jsonObject.getString("parent");
						String userNo =  jsonObject.getString("type");
					
						Log.i(TAG,"siteName="+siteName);
						Log.i(TAG,"parent="+parent);
						Log.i(TAG,"type="+userNo);
						
						omSiteInfo = new SiteInfo();
						omSiteInfo.setSiteName(siteName);
						omSiteInfo.setParent(parent);
						omSiteInfo.setType(userNo);
						omSiteInfo.insertSiteInfo(DBAdapter);
						Log.i(TAG,"Table Name:"+SiteInfo.TABLE_NAME);
						Log.i(TAG,"inserted SiteInfo->"+omSiteInfo.getSiteName()+":"+omSiteInfo.getParent());
					
					}	
    		}catch(Exception e)
    		{
    			Log.i(TAG,"ProcessUpdateRegisterInfoDetail error");
    			e.printStackTrace();
    		}
    		
    		/*String[] split = result.split(":");    		
			if(split.length==2) {
				if(split[0].equals(Constant.SUCCESS)) {
					String msg = getResources().getString(R.string.Message7b);
					Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();										
			
				} else {
					String msg = getResources().getString(R.string.Message7c);
					Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();										
				}
			}*/
    		
    		Log.i(TAG,"ProcessUpdateRegisterInfoDetail End");
    		new InsertSiteInfo().execute();
    	}
    	
    }
    
    class InsertSiteInfo extends AsyncTask<String, Integer, String> {
    	
    	protected String doInBackground(String... params) {
    		Log.i(TAG,"InsertSiteInfo Begin");
    		String result = null;
    	    HttpClient httpclient = new DefaultHttpClient();
    	    try {
    	    	SiteInfo omSiteInfo = new SiteInfo();
    	    	List<SiteInfo> siteInfoList = omSiteInfo.getAllSiteInfo(DBAdapter);
    	    	JSONObject jsonObject;
    	    	JSONArray jsonArray;
    	    	Log.i(TAG,"siteInfoList length="+siteInfoList.size());
    	    	if(siteInfoList.size() > 0)
    	    	{
    	    		 jsonArray = new JSONArray();
    	    		for(int i=0 ;i < siteInfoList.size() ; i ++)
    	    		{
    	    			  jsonObject = new JSONObject();
    	    			 omSiteInfo=siteInfoList.get(i);
    	    			 jsonObject.put("siteName",omSiteInfo.getSiteName());
    	    			 jsonObject.put("parent", omSiteInfo.getParent());
    	    			 jsonObject.put("type", omSiteInfo.getType());
    	    			 jsonArray.put(jsonObject);
    	    		}
    	    		jsonObject  = new JSONObject();
    	    		jsonObject.put("siteInfoList", jsonArray);
    	    		Log.d(TAG,"json siteInfo="+jsonObject.toString());
    	    		
    	    		StringBuffer sburl = new StringBuffer();
		    		sburl.append("http://").append(ip).append(":").append(http_port);
		    		sburl.append(getResources().getText(R.string.processUpdateAllTabletList));
		    		Log.d(TAG,"update All SiteInfo url="+sburl);
		    		
		  	    	HttpPost httppost = new HttpPost(sburl.toString());
	    	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    	        nameValuePairs.add(new BasicNameValuePair("json",jsonObject.toString()));
	    	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	    	        ResponseHandler<String> responseHandler=new BasicResponseHandler();
	    	        result = httpclient.execute(httppost,responseHandler).trim();
	    	        Log.d(TAG,"result="+result);
    	    	  
    	    	}
    	    } catch (Exception e) {
    	        Log.e(TAG,e.fillInStackTrace().toString());
    	    }     		
    	    Log.i(TAG,"InsertSiteInfo End");
    	    return result;
    	}
    	
    	protected void onPostExecute(String result) {
    		if(result == null) {
				String msg = getResources().getString(R.string.Message9);
				Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();
				return;
    		}
    		String[] split = result.split(":");    		
			if(split.length==2) {
				if(split[0].equals(Constant.SUCCESS)) {
					String msg = getResources().getString(R.string.Message8);
					Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();										
			
				} else {
					String msg = getResources().getString(R.string.Message9);
					Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();										
				}
			}
    	}
    	
    }

    /**
     * params[0] : url
     * params[1] : tablet serialNo list
     * @author charles
     *
     */
    class ProcessUpdateSiteInfo extends AsyncTask<String, Integer, String> {
    	
    	protected String doInBackground(String... params) {
    		String result = null;
    	    HttpClient httpclient = new DefaultHttpClient();
    	    try {
    	    	HttpPost httppost = new HttpPost(params[0]);
    	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    	        Log.d(TAG,"test="+DEVICEID+" "+ params[1]);
    	        nameValuePairs.add(new BasicNameValuePair("siteName",DEVICEID));
    	        nameValuePairs.add(new BasicNameValuePair("systemNo",getResources().getText(R.string.app_code).toString()));
    	        nameValuePairs.add(new BasicNameValuePair("serailNoList",params[1]));
    	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    	        ResponseHandler<String> responseHandler=new BasicResponseHandler();
    	        result = httpclient.execute(httppost,responseHandler).trim();
    	        Log.d(TAG,"result="+result);
	    	    // When HttpClient instance is no longer needed,
	    	    // shut down the connection manager to ensure
	    	    // immediate deallocation of all system resources
	            httpclient.getConnectionManager().shutdown();
    	    } catch (ClientProtocolException e) {
    	        Log.e(TAG,e.fillInStackTrace().toString());
    	    } catch (IOException e) {
    	        Log.e(TAG,e.fillInStackTrace().toString());
    	    }
    		return result;
    	}
    	
    	protected void onPostExecute(String result) {
    		if(result == null) {
				String msg = getResources().getString(R.string.Message9);
				Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();
				return;
    		}
    		String[] split = result.split(":");    		
			if(split.length==2) {
				if(split[0].equals(Constant.SUCCESS)) {
					String msg = getResources().getString(R.string.Message8);
					Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();										
			
				} else {
					String msg = getResources().getString(R.string.Message9);
					Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();										
				}
			}
    	}
    	
    }

    /**
     * params[0] : url
     * params[1] : userNo
     * params[2] : password
     * @author charles
     *
     */
    class ProcessVerifyUser extends AsyncTask<String, Integer, String> {

    	String userNo,password;
    	
    	protected String doInBackground(String... params) {
    		String result = null;
    		userNo = params[1];
    		password = params[2];
    	    HttpClient httpclient = new DefaultHttpClient();
    	    try {
    	    	Log.d(TAG,"params[0]="+params[0]);
    	    	HttpPost httppost = new HttpPost(params[0]);
        	    HttpConnectionParams.setConnectionTimeout(httppost.getParams(), 5000); // timeout 5 secs
        	    HttpConnectionParams.setSoTimeout(httppost.getParams(), 10000); // timeout 10 secs
    	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    	        nameValuePairs.add(new BasicNameValuePair("siteName",DEVICEID));
    	        nameValuePairs.add(new BasicNameValuePair("userNo",userNo));
    	      //  nameValuePairs.add(new BasicNameValuePair("password",Hash.encryptXOR(password)));
    	        nameValuePairs.add(new BasicNameValuePair("password",password));
    	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    	        ResponseHandler<String> responseHandler=new BasicResponseHandler();
    	        result = httpclient.execute(httppost,responseHandler).trim();
    	        Log.d(TAG,"xxxx result="+result);
	    	    // When HttpClient instance is no longer needed,
	    	    // shut down the connection manager to ensure
	    	    // immediate deallocation of all system resources
	            httpclient.getConnectionManager().shutdown();
    	    } catch (ClientProtocolException e) {
    	        Log.e(TAG,e.fillInStackTrace().toString());
    	    } catch (IOException e) {
    	        Log.e(TAG,e.fillInStackTrace().toString());
    	    }
    		return result;
    	}
    	
    	protected void onPostExecute(String result) {
    		if(result == null) {
				String msg = getResources().getString(R.string.Message5);
				Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();
				EditText et2=null;
				TextView tv11=null;
				if(uiNo==2) {
					et2 = (EditText)findViewById(R.id.uir002_editText2);
					tv11 = (TextView)findViewById(R.id.uir002_textView11);
				} else if(uiNo==4) {
					et2 = (EditText)findViewById(R.id.login_editText2);
					tv11 = (TextView)findViewById(R.id.login_textView11);
				}
				if(et2 != null) et2.requestFocus();
				if(tv11 != null) {
					tv11.setVisibility(View.VISIBLE);
					tv11.setText(msg);
				}
				return;
    		}
    		String[] split = result.split(":");   
    		Log.d(TAG,"split.length="+split.length);
			if(split.length==2) {
				if(split[0].equals(Constant.SUCCESS)) {
					String[] split2 = split[1].split(",");
					Account omA = new Account();
					omA.setAccountNo(userNo);
					omA.setPassword(password);
					omA.setSerialID(omCurrentSysProfile.getSerialID());
					omA.setLastModifiedDate(new Date());
					omA.setCompanyParent(companyNo);
					if(split2.length>0) 
						omA.setLOOK_MAPTRACK(split2[0]);
					if(split2.length>1) 
						omA.setBOSS_USERNO(split2[1]);
					if(split2.length>2) 
						omA.setAccountName(split2[2]);
					omA.insertAccount(DBAdapter);
					if(omA.getRid()>=0) {
						omCurrentAccount = omA;
						Log.i(TAG,"insert Account success");
						Toast.makeText(getBaseContext(),"Account created!",Toast.LENGTH_SHORT).show();
						String url = sLikURL;
						//url += "processUpdateRegisterInfoDetail.action?";
						url += "ProcessUpdateTabletRegisterInfoDetail.action?";
						url += "companyNo="+omCurrentSysProfile.getCompanyNo()+"&";
						url += "systemNo="+omCurrentSysProfile.getSystemNo()+"&";
						url += "userNo="+userNo+"&";
				        if(isDebug) 
				        	Log.d(TAG,DEVICEID);

						url += "serialNo="+DEVICEID;
						Log.i(TAG, "connecting..."+url);
						new ProcessUpdateRegisterInfoDetail().execute(url); 

			    		// init DB if not exists
						if(currentDept != null && currentDept.getCompanyID() !=0) {
							DBAdapter.setCompanyID(currentDept.getCompanyID());
							for(int i=0;i<LikDBAdapter.DATABASE_TABLES_FOR_DOWNLOAD.length;i++) {
								BaseOM<?> om = LikDBAdapter.DATABASE_TABLES_FOR_DOWNLOAD[i];
								om.setTableCompanyID(currentDept.getCompanyID());
					    		try {
									String cmd = om.getCreateCMD();
					    			if(cmd != null) DBAdapter.getdb().execSQL(cmd);	
					    			String[] cmds = om.getCreateIndexCMD();
					    			for(int j=0;j<cmds.length;j++) {
					    				cmd = cmds[j];
					    				DBAdapter.getdb().execSQL(cmd);	
					    			}
					    		} catch (SQLException e) {
					    			Log.w(TAG,"exception while creating table..."+om.getTableName());
					    			e.printStackTrace();
					    		}						
							}		    		
						}
					
			    		Intent intent = new Intent(LikSysActivity.this,MainMenuActivity.class);
			    		intent.putExtra(MainMenuActivity.KEY_SYSPROFILE,omCurrentSysProfile);
			    		intent.putExtra(MainMenuActivity.KEY_ACCOUNT,omCurrentAccount);
			    		intent.putExtra(MainMenuActivity.KEY_DEPTID,currentDept);
						startActivity(intent);
						finish(); // finish LikSysActivity

					} else {
						Log.i(TAG,"insert Account fail");
						//Toast.makeText(getBaseContext(),"Account create failed!",Toast.LENGTH_SHORT).show();
						Account existedAcc = new Account();
						existedAcc.setCompanyParent(companyNo);
						existedAcc=existedAcc.getAccountByCompanyParent(DBAdapter);
						Log.i(TAG,"existedAcc="+existedAcc.getAccountNo());
						Log.i(TAG,"existedAcc="+existedAcc.getCompanyParent());
						AlertDialog dialog = getAlertDialogForMessage("",existedAcc.getAccountNo()+"帳號註冊");
						dialog.show();
					}														
			
				} else {
					String msg = getResources().getString(R.string.Message34);
					Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();										
					EditText et2=null;
					TextView tv11=null;
					if(uiNo==2) {
						et2 = (EditText)findViewById(R.id.uir002_editText2);
						tv11 = (TextView)findViewById(R.id.uir002_textView11);
					} else if(uiNo==4) {
						et2 = (EditText)findViewById(R.id.login_editText2);
						tv11 = (TextView)findViewById(R.id.login_textView11);
					}
					if(et2 != null) et2.requestFocus();
					if(tv11 != null) {
						tv11.setVisibility(View.VISIBLE);
						tv11.setText(msg);
					}
				}
			} else {
				String msg = getResources().getString(R.string.Message5);
				Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();										
				EditText et2=null;
				TextView tv11=null;
				if(uiNo==2) {
					et2 = (EditText)findViewById(R.id.uir002_editText2);
					tv11 = (TextView)findViewById(R.id.uir002_textView11);
				} else if(uiNo==4) {
					et2 = (EditText)findViewById(R.id.login_editText2);
					tv11 = (TextView)findViewById(R.id.login_textView11);
				}
				if(et2 != null) et2.requestFocus();
				if(tv11 != null) {
					tv11.setVisibility(View.VISIBLE);
					tv11.setText(msg);
				}				
			}
    	}
    	
    }
   
    class ProcessSiteIP extends AsyncTask<String, Integer, String> {

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
    	    	Log.d(TAG, "connecting = " + url);
    	    	result = HttpUtil.httpConnect(url);
    			if (result.startsWith("\ufeff")) {
    				result = result.substring(1);
					}
    	        Log.d(TAG,"result = " + result);
    	        httpclient.getConnectionManager().shutdown();
    	    }
    	    catch (Exception e)
    	    {
    	    	result = "false";
    	    }
    	    Log.d(TAG,"ProcessSiteIP End");
    	    return result;
    	}
		
		protected void onPostExecute(String result) 
		{
			Log.i(TAG," ProcessSiteIP onPostExecute="+companyNo);
			try
			{
				Log.i(TAG," result"+result);
				if(result.equals("false"))
				{
	    			String msg = getResources().getString(R.string.Message28);
					TextView tv11 = (TextView)findViewById(R.id.uir003_textView11);
					if(tv11 != null) {
						tv11.setVisibility(View.VISIBLE);
						tv11.setText(msg);
					}
					return;
				}else{
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
						omSIP.setCompanyParent(companyNo);
						if(webPort != null) omSIP.setWebPort(Integer.parseInt(webPort));
						if(queuePort != null) omSIP.setQueuePort(Integer.parseInt(queuePort));
						omSIP.findByKey(DBAdapter);
						if(omSIP.getRid()<0) {
							omSIP.insertSiteIP(DBAdapter);
							Log.i(TAG,"Table Name: SiteIPList");
							Log.i(TAG,"inserted Site Name:IP->"+omSIP.getSiteName()+":"+omSIP.getIp()+":"+omSIP.getCompanyParent());
						} else {
							Log.i(TAG,"Table Name: SiteIPList");
							Log.i(TAG,"data existed Site Name:IP->"+omSIP.getSiteName()+":"+omSIP.getIp()+":"+omSIP.getCompanyParent());					
						}
					}
					
					new ProcessSiteInfo().execute(ip,httpPort,siteName);
				}
				
			}	
			catch(Exception e)
			{
				e.printStackTrace();
			}
	
		}
    	
    }
    
    class ProcessSiteInfo extends AsyncTask<String, Integer, String> {

    	String siteName;
    	String ip;
    	String httpPort;
		@Override
		protected String doInBackground(String... params) 
		{
			Log.d(TAG,"ProcessSiteInfo Start");
			ip = params[0];
			httpPort = params[1];
			siteName = params[2];
    		String result = null;
    		String url = "http://"+ip+":"+httpPort+getResources().getString(R.string.ProcessSiteInfo)+"?siteName="+siteName;
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
				TextView tv11 = (TextView)findViewById(R.id.uir003_textView11);
				if(tv11 != null) {
					tv11.setVisibility(View.VISIBLE);
					tv11.setText(msg);
				}
    	    }
    	    Log.d(TAG,"ProcessSiteInfo End");
    	    return result;
    	}
		
		protected void onPostExecute(String result) 
		{
			String pdaID="";
			try
			{
				JSONObject jsonObject =new JSONObject(result);
				JSONArray siteIPArray =jsonObject.getJSONArray("siteInfoList");
				for( int i = 0 ; i < siteIPArray.length();i++)
				{
					jsonObject = siteIPArray.getJSONObject(i);
					String siteName =  jsonObject.getString("siteName");
					String parent =  jsonObject.getString("parent");
					String type =  jsonObject.getString("type");
					pdaID = jsonObject.getString("type");
					SiteInfo omSI = new SiteInfo();
					omSI.setSiteName(siteName);
					omSI.setParent(parent);
					omSI.setType(type);
					omSI.getSiteInfoBySiteName(DBAdapter);
					if(omSI.getRid()<0) {
						omSI.insertSiteInfo(DBAdapter);
						Log.i(TAG,"Table Name:"+SiteInfo.TABLE_NAME);
						Log.i(TAG,"inserted Site Name:"+omSI.getSiteName());
					} else {
						Log.i(TAG,"Table Name:"+SiteInfo.TABLE_NAME);
						Log.i(TAG,"data existed, Site Name:"+omSI.getSiteName());					
					}
				}
				omCurrentSysProfile.setPdaId(Integer.parseInt(pdaID));
				omCurrentSysProfile.updateSysProfile(DBAdapter);
				//new ProcessCompany().execute(ip,httpPort);
				
			}	
			catch(Exception e)
			{
				Log.w(TAG,"test123.");
				e.printStackTrace();
			}
	
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
    		String url = "http://"+ip+":"+httpPort+getResources().getString(R.string.ProcessCompany)+"?SysID="+companyNo;
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
			try
			{
				if(result.equals("false"))
				{
	    			String msg = getResources().getString(R.string.Message28);
					TextView tv11 = (TextView)findViewById(R.id.uir003_textView11);
					if(tv11 != null) {
						tv11.setVisibility(View.VISIBLE);
						tv11.setText(msg);
					}
					return;
				}else{
					
					JSONObject jsonObject =new JSONObject(result);
					String errorFlag = (String)jsonObject.get("errorFlag");
					if(errorFlag.equals("true"))
					{
						String msg = companyNo+"不存在";
						TextView tv11 = (TextView)findViewById(R.id.uir003_textView11);
						if(tv11 != null) {
							tv11.setVisibility(View.VISIBLE);
							tv11.setText(msg);
						}
						return;
					}
					JSONArray companyArray =jsonObject.getJSONArray("companyList");
					 Log.d(TAG,"companyNo"+companyNo);
					for( int i = 0 ; i < companyArray.length();i++)
					{
						jsonObject = companyArray.getJSONObject(i);
						String companyID =  jsonObject.getString("companyID");
						String userNo =  jsonObject.getString("userNO");
						String companyNO =  jsonObject.getString("companyNO");
						String companyNM =  jsonObject.getString("companyNM");
						String address =  jsonObject.getString("address");
						String telNo =  jsonObject.getString("telNo");
						String dateFormat =  jsonObject.getString("dateFormat");
					

						UserCompy omUC = new UserCompy();
						omUC.setCompanyID(Integer.parseInt(companyID));
						omUC.setAccountNo(userNo);
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
						omC.setCompanyID(Integer.parseInt(companyID));
						omC.setUserNO(userNo);
						omC.setCompanyParent(companyNo);
						omC.getCompanyByKey(DBAdapter);
						if(omC.getRid()<0) {
							omC.setCompanyNO(companyNO);
							omC.setCompanyNM(companyNM);
							omC.setAddress(address);
							omC.setTelNo(telNo);
							omC.setDateFormat(dateFormat);
							omC.insertCompany(DBAdapter);
							Log.i(TAG,"Table Name:"+Company.TABLE_NAME);
							Log.i(TAG,"inserted AccountNo:CompanyID->"+omC.getUserNO()+":"+omC.getCompanyID());
						} else {
							Log.i(TAG,"Table Name:"+Company.TABLE_NAME);
							Log.i(TAG,"data existed AccountNo:CompanyID->"+omC.getUserNO()+":"+omC.getCompanyID());	
						}
					}
					
					omCurrentSysProfile = omCurrentSysProfile.getSysProfileByPrimaryKey(DBAdapter);
					if(omCurrentSysProfile.getRid()<0) { 
						omCurrentSysProfile.insertSysProfile(DBAdapter);
						Log.d(TAG,"insert SysProfile success");
					} else {
						omCurrentSysProfile.updateSysProfile(DBAdapter);
						Log.d(TAG,"update SysProfile success");
					}
					omCurrentSysProfile = omCurrentSysProfile.getSysProfileByPrimaryKey(DBAdapter); 
					
					uiNo =2;
					setContentView(R.layout.uir002);
		        	EditText et1 = (EditText)findViewById(R.id.uir002_editText1);
		        	et1.setOnFocusChangeListener(new OnFocusChangeListener() {

						@Override
						public void onFocusChange(View v, boolean hasFocus) {
							TextView tv11 = (TextView)findViewById(R.id.uir002_textView11);
							tv11.setText("");
						}
		        		
		        	});
		            final RelativeLayout btn1 = (RelativeLayout)findViewById(R.id.uir002_button1);
		            btn1.setOnClickListener(LikSysActivity.this);
		            final RelativeLayout btn2 = (RelativeLayout)findViewById(R.id.uir002_button2);
		            btn2.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							finish();
						}
		            	
		            });
		            
		            final RelativeLayout changeUser = (RelativeLayout)findViewById(R.id.ChangeUser);
		            changeUser.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							AlertDialog dialog = getAlertDialogForMessage("",getResources().getText(R.string.ChangeUserMessage).toString());
							dialog.show();
						}        	
		            });
		            
	    			
		    		StringBuffer sburl = new StringBuffer();
		    		sburl.append("http://").append(ip).append(":").append(http_port);
		    		sburl.append(getResources().getText(R.string.updateTabletListURI));
		    		Log.d(TAG,"update SiteInfo url="+sburl);
		    		if(omCurrentSysProfile!=null) {
			    		if(omCurrentSysProfile.isCloud()) {
			    			//new HttpsProcessUpdateSiteInfoTask(LikSysAdvActivity.this).execute(tabletSerialNoList);
			    		} else {
			    			new ProcessUpdateSiteInfo().execute(sburl.toString(),tabletSerialNoList);
			    		}
		    		}
				}// end else
				
				new ProcessSiteIP().execute(ip,http_port,siteName);
			}
			catch(Exception e)
			{
				Log.w(TAG,"test1234.");
    			e.printStackTrace();
			}
		}
    	
    }	
	 private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
	    	@Override
	    	public void onReceive(Context context, Intent intent) {
	    		// check for result
	    		String result = intent.getStringExtra(LikSysInitIntentService.RESULT);
	    		if(result==null) {
	    			Log.w(TAG,"result is null!!!");
	    			return;
	    		}
	    		Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
	    		Log.i(TAG,result);
	    		String[] split = result.split(":");
	    		if(split.length>0 && split[0].equals(Constant.FINISH)) {
	    			// uir002
	    			Log.d(TAG,"uir002 start");
					uiNo =2;
					setContentView(R.layout.uir002);
		        	EditText et1 = (EditText)findViewById(R.id.uir002_editText1);
		        	et1.setOnFocusChangeListener(new OnFocusChangeListener() {
						@Override
						public void onFocusChange(View v, boolean hasFocus) {
							TextView tv11 = (TextView)findViewById(R.id.uir002_textView11);
							tv11.setText("");
						}
		        		
		        	});
		            final RelativeLayout btn1 = (RelativeLayout)findViewById(R.id.uir002_button1);
		            btn1.setOnClickListener(LikSysActivity.this);
		            final RelativeLayout btn2 = (RelativeLayout)findViewById(R.id.uir002_button2);
		            btn2.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							finish();
						}
		            	
		            });
		        	Log.d(TAG,"uir002 end");
		    		StringBuffer sburl = new StringBuffer();
		    		sburl.append("http://").append(ip).append(":").append(http_port);
		    		sburl.append(getResources().getText(R.string.updateTabletListURI));
		    		Log.d(TAG,"update SiteInfo url="+sburl);
		    		if(omCurrentSysProfile!=null) {
			    		if(omCurrentSysProfile.isCloud()) {
			    			//new HttpsProcessUpdateSiteInfoTask(LikSysAdvActivity.this).execute(tabletSerialNoList);
			    		} else {
			    			new ProcessUpdateSiteInfo().execute(sburl.toString(),tabletSerialNoList);
			    		}
		    		}
	
	    		} else if(split.length>0 && split[0].equals(Constant.PDAID)) {
	    			SysProfile omSys= new SysProfile();
	    			omSys.setCompanyNo(companyNo);
	    			omSys.setSystemNo(getResources().getText(R.string.app_code).toString());
					omCurrentSysProfile=omCurrentSysProfile.getSysProfileByPrimaryKey(DBAdapter);
	    			Log.d(TAG,"pdaId="+split[1]+",omCurrentSysProfile.getPdaId()="+omCurrentSysProfile);
//	    			Log.d(TAG,"pdaId="+split[1]+",omCurrentSysProfile.getPdaId()="+omCurrentSysProfile.getPdaId());
	    			try {
	    				Integer.parseInt(split[1]);
						omCurrentSysProfile.setPdaId(Integer.parseInt(split[1])); 
	    			} catch(NumberFormatException pe) {
	    				Log.i(TAG, "pdaid not needed to update, msg="+split[1]);
	    				isUir003Entered = false;
	    			}
	    		} else {
					String msg = getResources().getString(R.string.Message28);
					TextView tv11 = (TextView)findViewById(R.id.uir003_textView11);
					if(tv11 != null) {
						tv11.setVisibility(View.VISIBLE);
						tv11.setText(msg);
					}
					isUir003Entered = false;
	    		}
	    	}
	    };
	
  
 	protected SiteIPList getSiteIP(String type) {
 		SiteInfo omSI = new SiteInfo();
 		omSI.setSiteName(DEVICEID);
 		omSI.getSiteInfoBySiteName(DBAdapter);
 		Log.d(TAG,omSI.getSiteName());
 		omSI.setSiteName(omSI.getParent());
 		omSI.getSiteInfoBySiteName(DBAdapter);
 		Log.d(TAG,"parent:"+omSI.getSiteName());
 		SiteIPList omSIP = new SiteIPList();
 		omSIP.setSiteName(omSI.getSiteName());
 		omSIP.setType(type);
 		List<SiteIPList> ltSIP = omSIP.getSiteIPListBySiteNameAndType(DBAdapter);
 		if(ltSIP.size()>0) {
 			omSIP = ltSIP.get(0);	
 			return omSIP;
 		} else {
 			return null;
 		}
 	}
 	
 	protected String getBackupDir() {
		Map<String, File> externalLocations = ExternalStorage.getAllStorageLocations();
		File externalSdCard = externalLocations.get(ExternalStorage.EXTERNAL_SD_CARD);
		Log.d(TAG,"externalSdCard="+externalSdCard);
        String backupDir = externalSdCard+getResources().getString(R.string.OrdersBackupDir);
    	if(externalSdCard==null) {
        	backupDir = Environment.getExternalStorageDirectory()+getResources().getString(R.string.OrdersBackupDir);	        		
    	}
        File file = new File(backupDir);
    	new FileUtil(file,true);
    	return backupDir;

	}
 	
 	protected AlertDialog getAlertDialogForMessage(String title, String message) {
    	Builder builder = new AlertDialog.Builder(LikSysActivity.this);
    	builder.setTitle(title);
    	builder.setMessage(message);
    	final String msgY = this.getString(R.string.Button1);
    	builder.setPositiveButton(msgY, new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			dialog.cancel();
    		}
    	});
    	return builder.create();
    }

}
