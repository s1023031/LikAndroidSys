package com.lik.android.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lik.Constant;
import com.lik.android.om.Account;
import com.lik.android.om.BaseOM;
import com.lik.android.om.ConfigControl;
import com.lik.android.om.CurrentCompany;
import com.lik.android.om.SiteIPList;
import com.lik.android.om.SiteInfo;
import com.lik.android.om.SysProfile;
import com.lik.android.om.UserCompy;
import com.lik.android.util.HttpUtil;
import com.lik.android.view.BaseDataAdapter;
import com.lik.android.view.CompanyNameDataAdapter;
import com.lik.android.view.CompanyNameView;
import com.lik.android.view.DeptNameDataAdapter;
import com.lik.android.view.DeptNameView;
import com.lik.util.ExternalStorage;

public class LoginActivity extends Activity {

	private static final String TAG = LoginActivity.class.getName();

	private String ip, http_port, xmpp_port;

	boolean isDebug = true;
	private String DEVICEID;
	private String sLikURL;
	public String companyNo;
	
	public static LikDBAdapter DBAdapter;

	private BaseDataAdapter<DeptNameView> adapter;
	private BaseDataAdapter<CompanyNameView> cmpyAdapter;

	private DeptNameView currentDept = new DeptNameView();
	private CompanyNameView currentCompany = new CompanyNameView();

	SysProfile omCurrentSysProfile;
	Account omCurrentAccount;

	private Spinner sp1 = null;
	private Spinner sp2 = null;
	private TextView tv = null;
	private RelativeLayout registerBtn = null;
	private RelativeLayout loginBtn = null;
	private RelativeLayout btn5 = null;
	private EditText uir002et1 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		initViews();
		setListener();
		
		sLikURL = getResources().getString(R.string.LikHostURL);
	       
		Bundle extras = getIntent().getExtras();
		
		//omCurrentSysProfile = (SysProfile)getIntent().getSerializableExtra(MainMenuActivity.KEY_SYSPROFILE);
		omCurrentSysProfile = new SysProfile();
		DBAdapter = LikSysActivity.DBAdapter;
		DEVICEID = getIntent().getStringExtra("DEVICEID");
				
		cmpyAdapter = new CompanyNameDataAdapter(LoginActivity.this, DBAdapter);
		cmpyAdapter.gatherData();
		if (cmpyAdapter.getCount() > 0) {
			Log.d(TAG, "setting adapter..." + cmpyAdapter.getCount());
			sp1.setVisibility(View.VISIBLE);
			sp1.setEnabled(true);
			sp1.setAdapter(cmpyAdapter);
			// default currentCompanyID
			currentCompany = (CompanyNameView) cmpyAdapter.getItem(0);
			tv.setText("");
		} else {
			currentCompany = new CompanyNameView();
			tv = (TextView) findViewById(R.id.login_textView11);
			tv.setText(getResources().getString(R.string.Message14));
		}
		
		sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
				Log.d(Constant.TAG, "onItemClick index=" + position);
				if (cmpyAdapter != null) {
					CompanyNameView view = (CompanyNameView) cmpyAdapter.getItem(position);
					Log.d(Constant.TAG, "text="+view.getCompanyNO()+" SystemNo= "+view.getSystemNo());
					companyNo = view.getCompanyNO();
					omCurrentSysProfile.setCompanyNo(view.getCompanyNO());
					omCurrentSysProfile.setSystemNo(view.getSystemNo());
					omCurrentSysProfile=omCurrentSysProfile.getSysProfileByPrimaryKey(DBAdapter);
					
					SiteIPList omSIP = getSiteIP(SiteIPList.TYPE_DOWNLOAD,view.getCompanyNO());
			        if(omSIP != null) {
			        	ip = omSIP.getIp();
			        	http_port = String.valueOf(omSIP.getWebPort());
			        	xmpp_port = String.valueOf(omSIP.getQueuePort());
			        }	
			        
					Log.d(TAG, "start query usercompy..."+ uir002et1.getText().toString());
					if(!"".equals(uir002et1.getText().toString()))
					{
						UserCompy omUC = new UserCompy();
						omUC.setAccountNo(uir002et1.getText().toString());
						omUC.setCompanyParent(companyNo);
						List<UserCompy> ltUC = omUC.getUserCompyByAccountNo(DBAdapter);
						if (ltUC.size() == 0) {
							TextView tv = (TextView) findViewById(R.id.login_textView11);
							tv.setText(getResources().getString(R.string.Message30));
							loginBtn.setEnabled(false);
							return;
						}
						
						loginBtn.setEnabled(true);
						adapter = new DeptNameDataAdapter(LoginActivity.this,DBAdapter);
						adapter.gatherData(uir002et1.getText().toString(),companyNo);
						if (adapter.getCount() > 0) {
							Log.d(TAG, "setting adapter..." + adapter.getCount());
							sp2.setVisibility(View.VISIBLE);
							if (omCurrentSysProfile.getVersionInfo().equals(SysProfile.VERSIONINFO_1))
								sp2.setEnabled(false);
							else
								sp2.setEnabled(true);
							sp2.setAdapter(adapter);
							// default currentCompanyID
							currentDept = (DeptNameView) adapter.getItem(0);

							TextView tv = (TextView) findViewById(R.id.login_textView11);
							tv.setText("");
						} else {
							sp2.setVisibility(View.GONE);
							currentDept = new DeptNameView();
							TextView tv = (TextView) findViewById(R.id.login_textView11);
							tv.setText(getResources().getString(R.string.Message14));
						}
					}
				}else
					Log.d(Constant.TAG, "haha");
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

	
		uir002et1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					EditText et1 = (EditText) v;
					Log.d(TAG, "start query usercompy..."+ et1.getText().toString());
					UserCompy omUC = new UserCompy();
					omUC.setAccountNo(et1.getText().toString());
					omUC.setCompanyParent(companyNo);
					List<UserCompy> ltUC = omUC.getUserCompyByAccountNo(DBAdapter);
					if (ltUC.size() == 0) {
						TextView tv = (TextView) findViewById(R.id.login_textView11);
						tv.setText(getResources().getString(R.string.Message30));
						loginBtn.setEnabled(false);
						return;
					}
					loginBtn.setEnabled(true);
					//Spinner sp2 = (Spinner) findViewById(R.id.login_spinner2);
					adapter = new DeptNameDataAdapter(LoginActivity.this,DBAdapter);
					adapter.gatherData(et1.getText().toString(),companyNo);
					if (adapter.getCount() > 0) {
						Log.d(TAG, "setting adapter..." + adapter.getCount());
						sp2.setVisibility(View.VISIBLE);
						if (omCurrentSysProfile.getVersionInfo().equals(SysProfile.VERSIONINFO_1))
							sp2.setEnabled(false);
						else
							sp2.setEnabled(true);
						sp2.setAdapter(adapter);
						// default currentCompanyID
						currentDept = (DeptNameView) adapter.getItem(0);

						TextView tv = (TextView) findViewById(R.id.login_textView11);
						tv.setText("");
					} else {
						sp2.setVisibility(View.GONE);
						currentDept = new DeptNameView();
						TextView tv = (TextView) findViewById(R.id.login_textView11);
						tv.setText(getResources().getString(R.string.Message14));
					}
				}
			}
		});

		//Spinner sp2 = (Spinner) findViewById(R.id.login_spinner2);
		sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
				Log.d(Constant.TAG, "onItemClick index=" + position);
				if (adapter != null) {
					DeptNameView view = (DeptNameView) adapter.getItem(position);
					currentDept = view;
					Log.d(Constant.TAG, "DeptNameView companyID=" +view.getCompanyID());
					DBAdapter.setCompanyID(currentDept.getCompanyID());
					MainMenuActivity.companyID = currentDept.getCompanyID();
					CurrentCompany omCurrentCompany = new CurrentCompany();
					 omCurrentCompany = omCurrentCompany.getCurrentCompany(DBAdapter);
				     if(omCurrentCompany.getRid() == -1) //no record insert data
				     {
				        	omCurrentCompany.setCompanyName(omCurrentSysProfile.getCompanyNo());
				        	omCurrentCompany.setDeptNO(String.valueOf(view.getCompanyID()));
				        	omCurrentCompany.setCompanyNO(view.getCompanyNO());
				        	omCurrentCompany.doInsert(DBAdapter);
				        	if(omCurrentCompany.getRid() >  -1)
				        		Log.d(TAG,"Current Company insert success = "+omCurrentCompany.getCompanyName() + " DeptNo" + omCurrentCompany.getDeptNO());
				        	else
				        		Log.d(TAG,"Current Company insert error");
				        }else{
				        	omCurrentCompany.setCompanyName(omCurrentSysProfile.getCompanyNo());
				        	omCurrentCompany.setDeptNO(String.valueOf(view.getCompanyID()));
				        	omCurrentCompany.setCompanyNO(view.getCompanyNO());
				        	omCurrentCompany.doUpdate(DBAdapter);
				        	if(omCurrentCompany.getRid() >  -1)
				        		Log.d(TAG,"Current Company update success = "+omCurrentCompany.getCompanyName() + " DeptNo" + omCurrentCompany.getDeptNO());
				        	else
				        		Log.d(TAG,"Current Company update error");
				        }		
					
				     for (int i = 0; i < LikDBAdapter.DATABASE_TABLES_FOR_DOWNLOAD.length; i++) {
						BaseOM<?> om = LikDBAdapter.DATABASE_TABLES_FOR_DOWNLOAD[i];
						om.setTableCompanyID(Integer.parseInt(omCurrentCompany.getDeptNO()));
						om.setCompanyParent(omCurrentCompany.getCompanyName());
						Log.d(Constant.TAG, "DeptNameView 123=" +om.getTableCompanyID());
						if (om.testTableExists(DBAdapter))
							continue;
						try {
							String cmd = om.getCreateCMD();
							if (cmd != null)
								DBAdapter.getdb().execSQL(cmd);
							String[] cmds = om.getCreateIndexCMD();
							for (int j = 0; j < cmds.length; j++) {
								cmd = cmds[j];
								DBAdapter.getdb().execSQL(cmd);
							}
						} catch (SQLException e) {
							Log.w(TAG,"exception while creating table..."+ om.getTableName());
							e.printStackTrace();
						}
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		sp2.setEnabled(false);
	}

	private void initViews() {
		sp1 = (Spinner) findViewById(R.id.login_spinner1);
		sp2 = (Spinner) findViewById(R.id.login_spinner2);
		tv = (TextView) findViewById(R.id.login_textView11);
		registerBtn = (RelativeLayout) findViewById(R.id.login_button1);
		loginBtn = (RelativeLayout) findViewById(R.id.login_button2);
		btn5 = (RelativeLayout) findViewById(R.id.login_button3);
		uir002et1 = (EditText) findViewById(R.id.login_editText1);
	}

	private void setListener() {
		registerBtn.setOnClickListener(register);
		loginBtn.setOnClickListener(login);
		btn5.setOnClickListener(cancel);

	}

	private OnClickListener register = new OnClickListener() {
		public void onClick(View v) {
	    ConfigControl omConfig= new ConfigControl();
	    omConfig.findByKey(DBAdapter);
	    omConfig.setLoginFlag("F");
	    omConfig.doUpdate(DBAdapter);
	    
    	Intent intent = new Intent(LoginActivity.this,LikSysActivity.class);
        startActivity(intent);
		}
	};

	private OnClickListener login = new OnClickListener() {
		public void onClick(View v) {
			Log.d(TAG, "login button");
			EditText userName = (EditText) findViewById(R.id.login_editText1);
			String accountNo = userName.getText().toString();
			EditText password = (EditText) findViewById(R.id.login_editText2);
			autheticate(accountNo, password);
		}
	};

	private OnClickListener cancel = new OnClickListener() {
		public void onClick(View v) {
			// close db & finish
			DBAdapter.endTransaction();
			finishAffinity();
		}
	};

	private void autheticate(String accountNo, EditText etPassword) {
		final Intent intent;
		Log.d(TAG, "autheticate");
		UserCompy omUC = new UserCompy();
		omUC.setAccountNo(accountNo);
		omUC.setCompanyParent(companyNo);
		List<UserCompy> ltUC = omUC.getUserCompyByAccountNo(DBAdapter);
		if (ltUC.size() == 0) {
			TextView tv11 = (TextView) findViewById(R.id.login_textView11);
			if (tv11 != null) {
				tv11.setVisibility(View.VISIBLE);
				tv11.setText(getString(R.string.Message30));
			}
			return;
		}

		Account omA = new Account();
		omA.setAccountNo(accountNo);
		omA.setSerialID(omCurrentSysProfile.getSerialID());
		omA = omA.getAccountByAccountNo(DBAdapter);
		if (omA.getRid() < 0) {
			TextView tv11 = (TextView) findViewById(R.id.login_textView11);
			tv11.setVisibility(View.VISIBLE);
			tv11.setText(getString(R.string.AccoutNotYetRegister));
		} else {
			omCurrentAccount = omA;
			String password = etPassword.getText().toString();
			if (password.equals(omA.getPassword())) {
				if (isDebug)
					Toast.makeText(this,"Finishing login process, go to main menu!",	Toast.LENGTH_SHORT).show();

				/*Users omU = new Users();
				omU.setUSER_NO(omCurrentAccount.getAccountNo());
				omU.findByKey(DBAdapter);
				if (omU.getRid() >= 0) {
					omCurrentAccount.setBOSS_USERNO(omU.getBOSS_USERNO());
					omCurrentAccount.setLOOK_MAPTRACK(omU.getLOOK_MAPTRACK());
					omCurrentAccount.setAccountName(omU.getUSER_NAME());
				}*/

				intent = new Intent(LoginActivity.this, MainMenuActivity.class);
				intent.putExtra(MainMenuActivity.KEY_SYSPROFILE,omCurrentSysProfile);
				intent.putExtra(MainMenuActivity.KEY_ACCOUNT, omCurrentAccount);
				intent.putExtra(MainMenuActivity.KEY_DEPTID, currentDept);
				startActivity(intent);
				finish(); // finish LikSysActivity
			} else {
				etPassword.setText("");
				etPassword.requestFocus();
			}
		}
	}

	private AlertDialog getAlertDialogForAccountCreate(String title,String message) {
		Builder builder = new AlertDialog.Builder(LoginActivity.this);
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
				EditText et1 = null;
				EditText et2 = null;
				et1 = (EditText) findViewById(R.id.login_editText1);
				et2 = (EditText) findViewById(R.id.login_editText2);
				et1.setText("");
				et2.setText("");
				et1.requestFocus();

			}
		});
		return builder.create();
	}

	private void processAccountCreate() {
		String accountNo = null, password = null;

		EditText et1 = (EditText) findViewById(R.id.login_editText1);
		accountNo = et1.getText().toString();
		EditText et2 = (EditText) findViewById(R.id.login_editText2);
		password = et2.getText().toString();

		String url = "http://";
		url += ip;
		url += ":";
		url += http_port;
		url += getResources().getText(R.string.verifyUserURI);
		if (omCurrentSysProfile.isCloud()) {
			// new HttpsProcessVerifyUserTask(this).execute(accountNo,password);
		} else {
			new ProcessVerifyUser().execute(url, accountNo, password);
		}
	}

	class ProcessVerifyUser extends AsyncTask<String, Integer, String> {

		String userNo, password;

		protected String doInBackground(String... params) {
			String result = null;
			userNo = params[1];
			password = params[2];
			HttpClient httpclient = new DefaultHttpClient();
			try {
				Log.d(TAG, "params[0]=" + params[0]);
				HttpPost httppost = new HttpPost(params[0]);
				HttpConnectionParams.setConnectionTimeout(httppost.getParams(),5000); // timeout 5 secs
				HttpConnectionParams.setSoTimeout(httppost.getParams(), 10000); // timeout// 10// secs
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("siteName", DEVICEID));
				nameValuePairs.add(new BasicNameValuePair("userNo", userNo));
				//nameValuePairs.add(new BasicNameValuePair("password", Hash.encryptXOR(password)));
				nameValuePairs.add(new BasicNameValuePair("password", password));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				result = httpclient.execute(httppost, responseHandler).trim();
				Log.d(TAG, "xxxx result=" + result);
				// When HttpClient instance is no longer needed,
				// shut down the connection manager to ensure
				// immediate deallocation of all system resources
				httpclient.getConnectionManager().shutdown();
			} catch (ClientProtocolException e) {
				Log.e(TAG, e.fillInStackTrace().toString());
			} catch (IOException e) {
				Log.e(TAG, e.fillInStackTrace().toString());
			}
			return result;
		}

		protected void onPostExecute(String result) {
			if (result == null) {
				// ����
				String msg = getResources().getString(R.string.Message5);
				Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
				EditText et2 = null;
				TextView tv11 = null;

				et2 = (EditText) findViewById(R.id.login_editText2);
				tv11 = (TextView) findViewById(R.id.login_textView11);

				if (et2 != null)
					et2.requestFocus();
				if (tv11 != null) {
					tv11.setVisibility(View.VISIBLE);
					tv11.setText(msg);
				}
				return;
			}
			String[] split = result.split(":");
			if (split.length == 2) {
				if (split[0].equals(Constant.SUCCESS)) {
					String[] split2 = split[1].split(",");
		
					Account omA = new Account();
					omA.setAccountNo(userNo);
					omA.setPassword(password);
					omA.setSerialID(omCurrentSysProfile.getSerialID());
					omA.setLastModifiedDate(new Date());
					if (split2.length > 0)
						omA.setLOOK_MAPTRACK(split2[0]);
					if (split2.length > 1)
						omA.setBOSS_USERNO(split2[1]);
					if (split2.length > 2)
						omA.setAccountName(split2[2]);
					omA.insertAccount(DBAdapter);
					if (omA.getRid() >= 0) {
						omCurrentAccount = omA;
						Log.i(TAG, "insert Account success");
						Toast.makeText(getBaseContext(), "Account created!",Toast.LENGTH_SHORT).show();
						String url = sLikURL;
						url += "processUpdateRegisterInfoDetail.action?";
						url += "companyNo="+ omCurrentSysProfile.getCompanyNo() + "&";
						url += "systemNo=" + omCurrentSysProfile.getSystemNo()+ "&";
						url += "userNo=" + userNo + "&";
						if (isDebug)
							Log.d(TAG, DEVICEID);

						url += "serialNo=" + DEVICEID;
						Log.i(TAG, "connecting..." + url);
						new ProcessUpdateRegisterInfoDetail().execute(url);

						// init DB if not exists
						if (currentDept != null && currentDept.getCompanyID() != 0) {
							Log.i(TAG, "test123..."+currentDept.getCompanyID( ));
							DBAdapter.setCompanyID(currentDept.getCompanyID());
							for (int i = 0; i < LikDBAdapter.DATABASE_TABLES_FOR_DOWNLOAD.length; i++) {
								BaseOM<?> om = LikDBAdapter.DATABASE_TABLES_FOR_DOWNLOAD[i];
								om.setTableCompanyID(currentDept.getCompanyID());
								try {
									String cmd = om.getCreateCMD();
									if (cmd != null)
										DBAdapter.getdb().execSQL(cmd);
									String[] cmds = om.getCreateIndexCMD();
									for (int j = 0; j < cmds.length; j++) {
										cmd = cmds[j];
										DBAdapter.getdb().execSQL(cmd);
									}
								} catch (SQLException e) {
									Log.w(TAG,"exception while creating table..."+ om.getTableName());
									e.printStackTrace();
								}
							}
						}

						Intent intent = new Intent(LoginActivity.this,MainMenuActivity.class);
						intent.putExtra(MainMenuActivity.KEY_SYSPROFILE,omCurrentSysProfile);
						intent.putExtra(MainMenuActivity.KEY_ACCOUNT,omCurrentAccount);
						intent.putExtra(MainMenuActivity.KEY_DEPTID,currentDept);
						startActivity(intent);
						finish(); // finish LikSysActivity

					} else {
						Log.i(TAG, "insert Account fail");
						Toast.makeText(getBaseContext(),"Account create failed!", Toast.LENGTH_SHORT).show();
					}

				} else {
					String msg = getResources().getString(R.string.Message34);
					Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
					EditText et2 = null;
					TextView tv11 = null;

					et2 = (EditText) findViewById(R.id.login_editText2);
					tv11 = (TextView) findViewById(R.id.login_textView11);

					if (et2 != null)
						et2.requestFocus();
					if (tv11 != null) {
						tv11.setVisibility(View.VISIBLE);
						tv11.setText(msg);
					}
				}
			} else {
				String msg = getResources().getString(R.string.Message5);
				Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
				EditText et2 = null;
				TextView tv11 = null;

				et2 = (EditText) findViewById(R.id.login_editText2);
				tv11 = (TextView) findViewById(R.id.login_textView11);

				if (et2 != null)
					et2.requestFocus();
				if (tv11 != null) {
					tv11.setVisibility(View.VISIBLE);
					tv11.setText(msg);
				}
			}
		}

	}
	
    class ProcessUpdateRegisterInfoDetail extends AsyncTask<String, Integer, String> {
    	
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
				String msg = getResources().getString(R.string.Message5);
				Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();
				return;
    		}
    		Log.d(TAG,"result = "+ result);
    		String[] split = result.split(":");    		
			if(split.length==2) {
				if(split[0].equals(Constant.SUCCESS)) {
					String msg = getResources().getString(R.string.Message7b);
					Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();										
			
				} else {
					String msg = getResources().getString(R.string.Message7c);
					Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();										
				}
			}
    	}
    	
    }
    
    protected SiteIPList getSiteIP(String type,String companyParent) {
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
 		omSIP.setCompanyParent(companyParent);
 		List<SiteIPList> ltSIP = omSIP.getSiteIPListBySiteNameAndType(DBAdapter);
 		if(ltSIP.size()>0) {
 			omSIP = ltSIP.get(0);	
 			return omSIP;
 		} else {
 			return null;
 		}
 	}
    
    @Override      
	  public void onStop() {
	    super.onStop();
	    Log.d(TAG,"onStop called!");
	  }
    
    @Override      
	  public void onDestroy() {
	    super.onDestroy();
	    Log.d(TAG,"onDestroy called!");
	     DBAdapter.endTransaction();
	     finishAffinity();
	}
    
}
