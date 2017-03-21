package com.lik.android.main;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lik.Constant;
import com.lik.android.main.R;
import com.lik.android.main.LikSysActivity.ProcessSiteInfo;
import com.lik.android.om.SiteIPList;
import com.lik.android.om.SiteInfo;
import com.lik.android.util.HttpUtil;

public class SetupIpPortFragment extends MainMenuFragment {
	
	private String selectedType = SiteIPList.TYPE_DOWNLOAD;
	LinearLayout linearLayout1;
	View view;
	String testServer="false";
	String result = "";
	public static MainMenuFragment newInstance(int index) {
        Log.v(TAG, "in SetupIpPortFragment newInstance(" + index + ")");

        SetupIpPortFragment mf = new SetupIpPortFragment();

        // Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("index", index);
		mf.setArguments(args);
		return mf;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater,container,savedInstanceState);
    	view = inflater.inflate(R.layout.uir003, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG,"onActivityCreated start!");
		setup_uir003();
	}

    private void setup_uir003() {
		Log.d(TAG,"WebPort from SysProfile:"+myActivity.omCurrentSysProfile.getWebPort());
		final TextView tv11 = (TextView)view.findViewById(R.id.uir003_textView11);
		final RadioGroup rg1 = (RadioGroup)view.findViewById(R.id.uir003_radioGroup1);
		final RadioButton upload = (RadioButton)view.findViewById(R.id.uir003_radio0);
		final RadioButton download = (RadioButton)view.findViewById(R.id.uir003_radio1);
		
		linearLayout1 = (LinearLayout)myActivity.findViewById(R.id.global_linearLayout1);
    	linearLayout1.setVisibility(LinearLayout.GONE);
		
		rg1.setVisibility(View.VISIBLE);
		rg1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				String msg="";
				switch(arg1) 
				{
					case R.id.uir003_radio0:
						msg = upload.getText().toString();
						if(upload.isChecked()) {
							selectedType = SiteIPList.TYPE_UPLOAD;
						}
						break;
					case R.id.uir003_radio1:
						msg = download.getText().toString();
						if(download.isChecked()) {
							selectedType = SiteIPList.TYPE_DOWNLOAD;
						}
						break;
					default:
						return;
				}
				setUp(view);
				
				tv11.setText("");
				Log.d(TAG,"onCheckedChanged:"+msg);
			}
			
		});
		Log.d(TAG,"onCheckedChanged123:");
		setUp(view);
		RelativeLayout b1 = (RelativeLayout)view.findViewById(R.id.uir003_button1);
		b1.setOnClickListener( new OnClickListener() {

					@Override
					public void onClick(View v) {
						String msg;
			    		EditText et31 = (EditText)view.findViewById(R.id.uir003_editText1);
			    		String ip = et31.getText().toString();
			    		EditText et32 = (EditText)view.findViewById(R.id.uir003_editText2);
			    		String http_port = et32.getText().toString();
			    		EditText et33 = (EditText)view.findViewById(R.id.uir003_editText3);
			    		String xmpp_port = et33.getText().toString();
			    		testServer="false";
			    		result ="";
			    		tv11.setText("");//clear AlertMessage
			    		new TestServer().execute(ip,http_port);
			    		while(testServer.equals("false"))
			    		{
			    			if(result.equals("false"))
			    			{
			    				msg = getResources().getString(R.string.Message28);
				    	    	tv11.setText(msg);
				    	    	return;
			    			}
			    		}
			    	    

			    		SiteInfo omSI = new SiteInfo();
			    		omSI.setSiteName(DEVICEID);
			    		omSI.getSiteInfoBySiteName(DBAdapter);
			    		if(omSI.getRid()>=0) {
			    			SiteIPList omSIP = new SiteIPList();
			    			omSIP.setSiteName(omSI.getParent());
			    			omSIP.setIp(ip);
			    			omSIP.setType(selectedType);
			    			omSIP.setWebPort(Integer.parseInt(http_port));
			    			omSIP.setQueuePort(Integer.parseInt(xmpp_port));
			    			omSIP.setCompanyParent(myActivity.omCurrentSysProfile.getCompanyNo());
			    			omSIP.findByKey(DBAdapter);
			    			long rid = 0;
			    			if(omSIP.getRid()<0) {
			    				Log.d(Constant.TAG,"before insert...");
			    				List<SiteIPList> ltSIP = omSIP.getSiteIPListBySiteName(DBAdapter);
			    				for(Iterator<SiteIPList> ir=ltSIP.iterator();ir.hasNext();) {
			    					SiteIPList om = ir.next();
			    					if(om.getType() != null && om.getType().equals(selectedType)) om.doDelete(DBAdapter);
			    				}
			    				omSIP.insertSiteIP(DBAdapter);
			    				rid = omSIP.getRid();
			    				if(rid>=0) Log.i(Constant.TAG,"SiteIPList inserted Site Name:IP:WebPort:QueuePort->"+
									omSIP.getSiteName()+":"+omSIP.getIp()+":"+omSIP.getType()+":"+omSIP.getWebPort()+":"+omSIP.getQueuePort());	
			    			} else {
			    				Log.d(TAG,"update ip="+ip+" web port = "+ http_port + " queue port = "+xmpp_port);
			    				omSIP.setIp(ip);
			    				omSIP.setWebPort(Integer.parseInt(http_port));
			    				omSIP.setQueuePort(Integer.parseInt(xmpp_port));
			    				omSIP.setCompanyParent(myActivity.omCurrentSysProfile.getCompanyNo());
			    				omSIP.updateSiteIP(DBAdapter);
			    				rid = omSIP.getRid();
			    				if(rid>=0) Log.i(Constant.TAG,"SiteIPList updated Site Name:IP:WebPort:QueuePort->"+
									omSIP.getSiteName()+":"+omSIP.getIp()+":"+omSIP.getType()+":"+omSIP.getWebPort()+":"+omSIP.getQueuePort());				    				
			    			}
			    			if(rid>=0) {
				    			msg = getResources().getString(R.string.Message8);
				    			siteIPListUpLoad = getSiteIP(SiteIPList.TYPE_UPLOAD);
			    			} else {
				    			msg = getResources().getString(R.string.Message9);			    				
			    			}
			    		} else {
			    			msg = getResources().getString(R.string.Message9);		
			    		}
			    		tv11.setText(msg);
					}
					
				});
		RelativeLayout b2 = (RelativeLayout)view.findViewById(R.id.uir003_button2);
		b2.setOnClickListener( new OnClickListener() {

					@Override
					public void onClick(View v) {
		    			// switch to Home page (QueryNotUploadFragment)
						linearLayout1.setVisibility(1);
			            MainMenuFragment mmf = QueryNotUploadFragment.newInstance();
						myActivity.showMainMenuFragment(mmf);
					}
					
				});
    }
    
    public void setUp(View view) {
    	Log.d(TAG,"setUp start");
		SiteInfo omSI = new SiteInfo();
		omSI.setSiteName(DEVICEID);
		omSI.getSiteInfoBySiteName(DBAdapter);
		if(omSI.getRid()>=0) {
			SiteIPList omSIP = new SiteIPList();
			omSIP.setSiteName("901");
			omSIP.setType(selectedType);
			omSIP.setCompanyParent(myActivity.omCurrentSysProfile.getCompanyNo());
			List<SiteIPList> ltSIP = omSIP.getSiteIPListBySiteNameAndType(DBAdapter);
			if(ltSIP.size()>0) {
				SiteIPList om = ltSIP.get(0); 
	    		EditText et31 = (EditText)view.findViewById(R.id.uir003_editText1);
	    		et31.setText(om.getIp());
	    		EditText et32 = (EditText)view.findViewById(R.id.uir003_editText2);
	    		if(om.getWebPort()!=0) et32.setText(String.valueOf(om.getWebPort()));
	    		EditText et33 = (EditText)view.findViewById(R.id.uir003_editText3);
	    		if(om.getQueuePort()!=0) et33.setText(String.valueOf(om.getQueuePort()));				
			}
		}
		else{
			Log.d(TAG,"no siteInfo");
		}
		Log.d(TAG,"setUp end");
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
 
}
