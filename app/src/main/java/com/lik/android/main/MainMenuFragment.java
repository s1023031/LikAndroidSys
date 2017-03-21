package com.lik.android.main;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lik.android.main.R;
import com.lik.android.om.Account;
import com.lik.android.om.Company;
import com.lik.android.om.Phrase;
import com.lik.android.om.SiteIPList;
import com.lik.android.om.SiteInfo;
import com.lik.android.om.SysProfile;
import com.lik.android.view.DeptNameView;

public class MainMenuFragment extends Fragment{
	
	protected static final String TAG = MainMenuFragment.class.getName();
	public static final String LAST_BROWSED_FRAGMENT_KEY = "MainMenuFragment.LastBrowsedFragmentKey";
	protected static final long VIBRATE_PERIOD = 300;
	protected static final int HTTP_CONNECTION_TIMEOUT = 10000;
	protected static final int HTTP_DATA_TIMEOUT = 10000;
	
	// keep db reference
	protected static LikDBAdapter DBAdapter;	
	protected MainMenuActivity myActivity = null;
	protected static String DEVICEID;
	private int index = 0;
	protected final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd",Locale.CHINESE);
	protected NumberFormat numf = NumberFormat.getInstance();
	protected Vibrator myVibrator;
	protected static boolean isTakeOrderChecked = false; 
	protected static int lastSelectedLVposition = -1;
	protected static double price; 
	protected static String unit; 
	protected static double discRate; 
	protected static SiteIPList siteIPListUpLoad;
	//public static String companyParent = MainMenuActivity.companyParent;
	
	

	
	public static MainMenuFragment newInstance(int index) {
        Log.v(TAG, "in MainMenuFragment newInstance(" + index + ")");

        MainMenuFragment mf = new MainMenuFragment();

        // Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("index", index);
		mf.setArguments(args);
		return mf;
	}
	
	public static MainMenuFragment newInstance(Bundle bundle) {
		int index = bundle.getInt("index", 0);
        return newInstance(index);
	}
	
	public int getIndex() {
		return index;
	}

    @Override
    public void onAttach(Activity myActivity) {
    	Log.v(TAG, "in MainMenuFragment onAttach; activity is: " + myActivity);
//    	Log.v(TAG, "in MainMenuFragment onAttach; getBackStackEntryCount: " + getFragmentManager().getBackStackEntryCount());
//		getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    	super.onAttach(myActivity);
    	this.myActivity = (MainMenuActivity)myActivity;   
    	Log.w(TAG,"this.myActivity.currentCompany is null?"+(this.myActivity.currentDept==null));
    	DBAdapter = MainMenuActivity.DBAdapter;
		SharedPreferences settings = myActivity.getPreferences(Context.MODE_PRIVATE);
		int companyID = settings.getInt(MainMenuActivity.KEY_DEPTID, 13);
        if(DBAdapter==null) {
        	DBAdapter = new LikDBAdapter(this.myActivity,true,companyID);
        	MainMenuActivity.DBAdapter = DBAdapter;        	
        }
        if(DBAdapter.getCompanyID()==0) DBAdapter.setCompanyID(companyID);
		setHasOptionsMenu(true);		
    }

	@Override
	public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		Log.d(TAG,"onCreateOptionsMenu start!");
	}

    @Override
    public void onCreate(Bundle myBundle) {
    	Log.v(TAG, "in MainMenuFragment onCreate. Bundle contains:");
    	if(myBundle != null) {
            for(String key : myBundle.keySet()) {
                Log.v(TAG, "    " + key);
            }
    	}
    	else {
            Log.v(TAG, "    myBundle is null");
    	}
    	super.onCreate(myBundle);

    	index = getArguments().getInt("index", 0);
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(TAG, "in Fragment onCreateView. container = " + container);
        return super.onCreateView(inflater,container,savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.v(TAG, "in Fragment onActivityCreated");
		super.onActivityCreated(savedInstanceState);
    	if(DBAdapter==null) DBAdapter = MainMenuActivity.DBAdapter;

        // in case process is killed by system
    	if(this.myActivity.currentDept == null) {
    		Log.i(TAG,"enter here");
    		SharedPreferences settings = myActivity.getPreferences(Context.MODE_PRIVATE);
    		int companyID = settings.getInt(MainMenuActivity.KEY_DEPTID, 13);
            this.myActivity.currentDept = new DeptNameView();
            this.myActivity.currentDept.setCompanyID(companyID);
            if(DBAdapter==null) {
            	DBAdapter = new LikDBAdapter(this.myActivity,true,companyID);
            	MainMenuActivity.DBAdapter = DBAdapter;
            }
            if(DBAdapter.getCompanyID()==0) DBAdapter.setCompanyID(companyID);
            SysProfile omSP = new SysProfile();
            ArrayList<SysProfile> alSP = omSP.getAllSysProfile(DBAdapter);
            this.myActivity.omCurrentSysProfile = alSP.get(0);
            Account omA = new Account();
            omA.setAccountNo(settings.getString(MainMenuActivity.KEY_ACCOUNT, null));
            this.myActivity.omCurrentAccount = omA.findByKey(DBAdapter);
            Company omC = new Company();
            omC.setCompanyID(companyID);
            omC.setUserNO(this.myActivity.omCurrentAccount.getAccountNo());
            omC.findByKey(DBAdapter);
            this.myActivity.currentDept.setCompanyNM(omC.getCompanyNM());
            this.myActivity.currentDept.setDateFormat(omC.getDateFormat());
 
    	}
    	DEVICEID = Settings.System.getString(myActivity.getContentResolver(),Settings.Secure.ANDROID_ID);
    	numf.setMaximumFractionDigits(this.myActivity.currentDept.getNsamtDecimal());				
		myVibrator = (Vibrator)this.myActivity.getApplication().getSystemService(Service.VIBRATOR_SERVICE);

		if(siteIPListUpLoad==null) siteIPListUpLoad = getSiteIP(SiteIPList.TYPE_UPLOAD);
		
		setGlobal();
	}	
	
 	protected AlertDialog getAlertDialogForMessage(String title, String message) {
    	Builder builder = new AlertDialog.Builder(myActivity);
    	builder.setTitle(title);
    	builder.setMessage(message);
    	final String msgY = myActivity.getString(R.string.Button1);
    	builder.setPositiveButton(msgY, new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			dialog.cancel();
    		}
    	});
    	return builder.create();
    }
 	
 	protected AlertDialog NoEnoughMemoryDialog(String title, String message) {
    	Builder builder = new AlertDialog.Builder(myActivity);
    	builder.setTitle(title);
    	builder.setMessage(message);
    	final String msgY = this.getString(R.string.Button1);
    	builder.setPositiveButton(msgY, new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			MainMenuFragment mmf = QueryNotUploadFragment.newInstance();
    			myActivity.showMainMenuFragment(mmf);
    		}
    	});
    	return builder.create();
    }
 	
    protected void setGlobal() {
    	// company textview
    	TextView tv1 = (TextView)myActivity.findViewById(R.id.global_textView1);
    	if(tv1 != null) tv1.setText(myActivity.currentDept.getCompanyNM()+"\nTel:"+myActivity.currentDept.getTelNo());
    	// home image 
    	ImageView iv = (ImageView)myActivity.findViewById(R.id.global_imageView1);
    	if(iv != null) iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG,"Home ImageView onClick");			
    			// switch to Home page (QueryNotUploadFragment)
				MainMenuFragment mmf = QueryNotUploadFragment.newInstance();
				myActivity.showMainMenuFragment(mmf);
			}
			
    	});
    	ImageView iv2 = (ImageView)myActivity.findViewById(R.id.global_imageView2);
    	if(iv2 != null) iv2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String title = getString(R.string.Message37a);
				String msg = getString(R.string.Message37b);
				AlertDialog dialog2 = getAlertDialogForMessageYN(title,msg);
				dialog2.show();

			}
			
    	});
    	TextView gtv = (TextView)myActivity.findViewById(R.id.global_textView2);
    	if(gtv!=null) {
    		gtv.setVisibility(View.VISIBLE);
    		gtv.setText(""); // �M�ŤU��T��
    	}
    }
    
    public TreeMap<String,String> getKindMap(int kindNo) {
    	TreeMap<String,String> result = new TreeMap<String,String>();
    	// ���O
		Phrase omP = new Phrase();
		omP.setPhkindNO(kindNo);
		List<Phrase> ltP = omP.getPhraseByPhkindNO(DBAdapter);
		if(omP.getRid()>=0) {
			for(int i=0;i<ltP.size();i++) {
				Phrase om = ltP.get(i);
				result.put(om.getPhraseNO(),om.getPhraseDESC() );
			}
		}
		return result;
    }
    
    protected AlertDialog getAlertDialogForMessageYN(String title, String message) {
    	Builder builder = new AlertDialog.Builder(myActivity);
    	builder.setTitle(title);
    	builder.setMessage(message);
    	final String msgY = getResources().getString(R.string.Button1);
    	builder.setPositiveButton(msgY, new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
				Log.d(TAG,"PowOff ImageView onClick");	
				ProgressBar gbar = (ProgressBar)myActivity.findViewById(R.id.global_progressBar1);
				if(getFragmentManager() != null) { 
					MainMenuFragment mmf = (MainMenuFragment)getFragmentManager().findFragmentById(R.id.main_frameLayout1);
					if(mmf != null) {
			        	// keep last browsed fragment
			            SharedPreferences settings = myActivity.getPreferences(Context.MODE_PRIVATE);
			            SharedPreferences.Editor editor = settings.edit();
			            editor.putString(LAST_BROWSED_FRAGMENT_KEY, mmf.getClass().getName());
			            Log.d(TAG,"LAST_BROWSED_FRAGMENT_KEY="+mmf.getClass().getName());
			            // Commit the edits!
			            editor.commit();						
					}
					// close LikSys App
					if (mmf == null ) {

/*				    	// �ˬd���ڬO�_����
						if(mmf instanceof ReceiveFragment) {
							ReceiveFragment rf = (ReceiveFragment)mmf;
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
						
						mmf = QueryNotUploadFragment.newInstance();
						myActivity.showMainMenuFragment(mmf);
					}
				}
	        	if(gbar.getVisibility()==View.VISIBLE) {
	        		Intent intent = new Intent(myActivity.getBaseContext(),LikSysCoreDataDownloadAdvViewIntentService.class);
	        		myActivity.stopService(intent);
	        	}
	        	
				myActivity.finish();
    		}
    	});
    	final String msgN = getResources().getString(R.string.Button2);
    	builder.setNegativeButton(msgN, new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			dialog.dismiss();
    		}
    	});
    	return builder.create();
    }
	
    protected SiteIPList getSiteIP(String type) {

 		SiteInfo omSI = new SiteInfo();
 		omSI.setSiteName(DEVICEID);
 		omSI.getSiteInfoBySiteName(DBAdapter);
 		Log.d(TAG,omSI.getSiteName());
 		omSI.setSiteName(omSI.getParent());
 		omSI.getSiteInfoBySiteName(DBAdapter);
 		Log.d(TAG,"SiteInfo siteName:"+omSI.getSiteName());
 		SiteIPList omSIP = new SiteIPList();
 		omSIP.setSiteName(omSI.getSiteName());
 		omSIP.setType(type);
 		omSIP.setCompanyParent(myActivity.omCurrentSysProfile.getCompanyNo());
 		List<SiteIPList> ltSIP = omSIP.getSiteIPListBySiteNameAndType(DBAdapter);
 		if(ltSIP.size()>0) {
 			omSIP = ltSIP.get(0);	
 			return omSIP;
 		} else {
 	 		Log.d(TAG,"getSiteIP no record");
 			return null;
 		}
 	}
	
}
