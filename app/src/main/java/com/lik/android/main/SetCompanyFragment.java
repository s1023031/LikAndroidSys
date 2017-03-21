package com.lik.android.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.lik.Constant;
import com.lik.android.main.R;
import com.lik.android.om.BaseOM;
import com.lik.android.om.CurrentCompany;
import com.lik.android.om.SysProfile;
import com.lik.android.view.BaseDataAdapter;
import com.lik.android.view.DeptNameDataAdapter;
import com.lik.android.view.DeptNameView;

public class SetCompanyFragment extends MainMenuFragment {
	
	View view;

	public static MainMenuFragment newInstance() {
        Log.v(TAG, "in SetCompanyFragment newInstance");

        SetCompanyFragment mf = new SetCompanyFragment();

        // Supply index input as an argument.
		Bundle args = new Bundle();
	//	args.putInt("index", index);
		mf.setArguments(args);
		return mf;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater,container,savedInstanceState);
    	view = inflater.inflate(R.layout.main_set_company, container, false);
        return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG,"onActivityCreated start!");
		setup_mainmenu_item12();
	}
	
	 private void setup_mainmenu_item12() {
		 	myActivity.getActionBar().hide();
	    	final BaseDataAdapter<DeptNameView> adapter = new DeptNameDataAdapter(myActivity,DBAdapter);
	    	adapter.gatherData(myActivity.omCurrentAccount.getAccountNo(),myActivity.omCurrentSysProfile.getCompanyNo());
	    	Log.d(TAG,"company size="+adapter.getCount());
	        Spinner sp1 = (Spinner)view.findViewById(R.id.set_company_spinner1);
	    	sp1.setAdapter(adapter);
	    	if(myActivity.omCurrentSysProfile.getVersionInfo().equals(SysProfile.VERSIONINFO_1)) 
	    		sp1.setEnabled(false);
	    	else 
	    		sp1.setEnabled(true);
	    	sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
					Log.d(Constant.TAG,"onItemClick index="+position);
					if(adapter != null) {
						DeptNameView view = (DeptNameView)adapter.getItem(position);
						Toast.makeText(myActivity,"You have selected item : " + view.getCompanyNM(), Toast.LENGTH_SHORT).show();    		
						myActivity.currentDept = view;
		
						DBAdapter.setCompanyID(view.getCompanyID());
						
						MainMenuActivity.companyID = DBAdapter.getCompanyID();
				        SharedPreferences settings = myActivity.getPreferences(Context.MODE_PRIVATE);
				        SharedPreferences.Editor editor = settings.edit();
				        editor.putInt(MainMenuActivity.KEY_DEPTID, view.getCompanyID());
				        editor.commit();
						
					SQLiteDatabase db = DBAdapter.getdb();

						 CurrentCompany omCurrentCompany = new CurrentCompany();
						// omCurrentCompany.setCompanyName(myActivity.omCurrentSysProfile.getCompanyNo());
						//omCurrentCompany.setDeptNO(String.valueOf(view.getCompanyID()));
						 omCurrentCompany = omCurrentCompany.getCurrentCompany(DBAdapter);
					     if(omCurrentCompany.getRid() == -1) //no record insert data
					     {
					        	omCurrentCompany.setCompanyName(myActivity.omCurrentSysProfile.getCompanyNo());
					        	omCurrentCompany.setDeptNO(String.valueOf(view.getCompanyID()));
					           	omCurrentCompany.setCompanyNO(view.getCompanyNO());
					        	omCurrentCompany.doInsert(DBAdapter);
					        	if(omCurrentCompany.getRid() >  -1)
					        		Log.d(TAG,"Current Company insert success = "+omCurrentCompany.getCompanyName() + " CompanyNO" + omCurrentCompany.getCompanyNO());
					        	else
					        		Log.d(TAG,"Current Company insert error");
					      }else{
					        	omCurrentCompany.setCompanyName(myActivity.omCurrentSysProfile.getCompanyNo());
					        	omCurrentCompany.setDeptNO(String.valueOf(view.getCompanyID()));
					        	omCurrentCompany.setCompanyNO(view.getCompanyNO());
					        	omCurrentCompany.doUpdate(DBAdapter);
					        	if(omCurrentCompany.getRid() >  -1)
					        	{
					        		Log.d(TAG,"Current Company update success = "+omCurrentCompany.getCompanyName() + " CompanyNO" + omCurrentCompany.getCompanyNO());
					        		myActivity.companyParent=omCurrentCompany.getCompanyName();
					        		myActivity.companyID=Integer.parseInt(omCurrentCompany.getDeptNO());
					        		myActivity.companyNO=omCurrentCompany.getCompanyNO();
					        	}
					        		
					        	else
					        		Log.d(TAG,"Current Company update error");
					        }
					     
					     for (int i = 0; i < LikDBAdapter.DATABASE_TABLES_FOR_DOWNLOAD.length; i++) {
								BaseOM<?> om = LikDBAdapter.DATABASE_TABLES_FOR_DOWNLOAD[i];
								//om.setTableCompanyID(currentDept.getCompanyID());
								//om.setCompanyParent(omCurrentSysProfile.getCompanyNo());
								om.setTableCompanyID(Integer.parseInt(omCurrentCompany.getDeptNO()));
								om.setCompanyParent(omCurrentCompany.getCompanyName());
								Log.d(Constant.TAG, "DeptNameView 123=" +om.getTableCompanyID());
								Log.d(Constant.TAG, "DeptNameView 456=" +om.getCompanyParent());
								if (om.testTableExists(DBAdapter))
								{
									Log.d(Constant.TAG, LikDBAdapter.DATABASE_TABLES_FOR_DOWNLOAD[i]+ "Exists");
									continue;
								}
								else{
									try {
										String cmd = om.getCreateCMD();
										Log.d(Constant.TAG, LikDBAdapter.DATABASE_TABLES_FOR_DOWNLOAD[i]+ " "+cmd);
										if (cmd != null)
											DBAdapter.getdb().execSQL(cmd);
										String[] cmds = om.getCreateIndexCMD();
										for (int j = 0; j < cmds.length; j++) {
											cmd = cmds[j];
											DBAdapter.getdb().execSQL(cmd);
										}
										Log.d(Constant.TAG, "	om.testTableExists(DBAdapter)"+ om.testTableExists(DBAdapter));
										
									} catch (SQLException e) {
										Log.w(TAG,"exception while creating table..."+ om.getTableName());
										e.printStackTrace();
									}
								}

							}
					     
					     
						DBAdapter.closedb();

					}
				
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}});
		        
	    	final RelativeLayout btn = (RelativeLayout)view.findViewById(R.id.set_company_button1);
		    btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					MainMenuFragment mmf = QueryNotUploadFragment.newInstance();
					myActivity.getActionBar().show();
					myActivity.getActionBar().setTitle(myActivity.currentDept.getCompanyNM());
					myActivity.showMainMenuFragment(mmf);
				}
	        	
	        });
		    
		    final RelativeLayout btn2 = (RelativeLayout)view.findViewById(R.id.set_company_button2);
		    btn2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {    	
					getActivity().finish();
				}
	        	
	        });

	    }

}
