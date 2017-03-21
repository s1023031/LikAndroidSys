package com.lik.android.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;



import java.util.Date;

import com.lik.android.main.R;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ProjectPhoto extends MainMenuFragment{
	View view;
	String OrderID;
	String CustomerID;
	String shopName;
	String salesID;
	TextView back,name;
	ListView ls;
	MyAdapter mba;
	
	public ProjectPhoto(String OrderID, String CustomerID){
		this.OrderID=OrderID;
		this.CustomerID=CustomerID;
	}
	
	public void onResume() {
		// TODO Auto-generated method stub
		Log.d(TAG,"onResume start!");
		super.onResume();
		mba.removeAll();
		
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String strDate = sdFormat.format(date);
		
		SQLiteDatabase db = DBAdapter.getReadableDatabase();
		String photoProjectStr="PhotoProject_"+myActivity.omCurrentSysProfile.getCompanyNo()+myActivity.currentDept.getCompanyID();
		Cursor c2=db.query(false,
				"sqlite_master",				
				new String[] {"name"},	
				"type='table' AND name='"+photoProjectStr+"' ;",				//WHERE
				null, // WHERE
				null, // GROUP BY
				null, // HAVING
				null, // ORDOR BY
				null  
				);
		
	if(c2.getCount()==1){
		Log.d(TAG,"have table");

			Cursor c=db.query(true,
					photoProjectStr,				
					new String[] {"Name","SerialID","TakePhotoID","ProjectNO","YearMonth","Remark","SupplierNM"},	
					"CustomerID="+CustomerID,		//DateTo <='"+strDate+"';		//WHERE
					null, // WHERE ���Ѽ�
					null, // GROUP BY
					null, // HAVING
					null, // ORDOR BY
					null  // ����^�Ǫ�rows�ƶq
					);
			Log.d(TAG,"have data="+c.getCount());
			if (c.getCount()>0) {
				c.moveToFirst();
				mba.addListItem(new PpItemObject(c.getString(0)+"("+c.getString(6)+")",c.getString(1),shopName,OrderID,CustomerID,c.getString(2),c.getString(3),salesID,c.getString(4),c.getString(5)));
				while(c.moveToNext()){
					mba.addListItem(new PpItemObject(c.getString(0)+"("+c.getString(6)+")",c.getString(1),shopName,OrderID,CustomerID,c.getString(2),c.getString(3),salesID,c.getString(4),c.getString(5)));
				}
			}
		}else{
			//Toast.makeText(myActivity, "2222  "+c2.getCount(), Toast.LENGTH_SHORT).show();
		}
		Log.d(TAG,"onResume finish!");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater,container,savedInstanceState);
		view = inflater.inflate(R.layout.main_project_photo, container, false);
		return view;
		
	}
	
	private void getCustomerDetail() {
		Log.d(TAG,"getCustomerDetail start!");
		// TODO Auto-generated method stub
		SQLiteDatabase db = DBAdapter.getReadableDatabase();
		Cursor c=db.query(false,
				"Orders , Customers_"+myActivity.omCurrentSysProfile.getCompanyNo()+myActivity.currentDept.getCompanyID(),				//��ƪ�W��
				new String[] {"ShortName","Orders.SalesID"},	//���W��
				"Customers_"+myActivity.omCurrentSysProfile.getCompanyNo()+myActivity.currentDept.getCompanyID()+".CustomerNO = Orders.CustomerNO AND Orders.OrderID = '"+OrderID+"';",				//WHERE
				null, // WHERE ���Ѽ�
				null, // GROUP BY
				null, // HAVING
				null, // ORDOR BY
				null  // ����^�Ǫ�rows�ƶq
				);
		
		if (c.getCount()>0) {
			c.moveToFirst();
			shopName = c.getString(0);
			name.setText(name.getText()+shopName);
			salesID = c.getInt(1)+"";
		}
		
		Log.d(TAG,"getCustomerDetail finish!");
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG,"onActivityCreated start!");
		setView();
		getCustomerDetail();

	}

	private void setView() {
		// TODO Auto-generated method stub
		Log.d(TAG,"setView start!");
		ls = (ListView)view.findViewById(R.id.mpp_ls);
		mba = new MyAdapter(myActivity);
		ls.setAdapter(mba);
		
		
		name = (TextView)view.findViewById(R.id.mpp_name);
		back = (TextView)view.findViewById(R.id.mp_page_back);
		
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//myActivity.getFragmentManager().beginTransaction().remove(CustomerDetail.this).commit();
				MainMenuFragment mmf = QueryNotUploadFragment.newInstance();
				myActivity.showMainMenuFragment(mmf);
				synchronized(myActivity) {
					myActivity.setNeedBackup(true);
					myActivity.notify();
				}
			}
		});
		
		Log.d(TAG,"setView finish!");
	}
	
	public static MainMenuFragment newInstance(String OrderID,String CustomerID) {
        Log.d(TAG, "in ProjectPhoto newInstance()");
        Log.d(TAG, "CustomerID"+CustomerID);
        ProjectPhoto mf = new ProjectPhoto(OrderID,CustomerID);

        // Supply index input as an argument.
		Bundle args = new Bundle();
		//.args.putInt("index", index);
		mf.setArguments(args);
		return mf;
	}
	
	public class MyAdapter extends BaseAdapter{    
	    
	    private LayoutInflater myInflater;
	   ArrayList<PpItemObject> list = new ArrayList<PpItemObject>();
	  
	   
	   private class View_TalkLayout{
		   ImageView iv1,iv2,iv3;
		   TextView name; 
		   RelativeLayout rl;
	       
		}
	   
	    public MyAdapter(Context ctxt){
	    	myInflater=(LayoutInflater)ctxt.getSystemService(myActivity.LAYOUT_INFLATER_SERVICE);
	    }
	    
	    public void removeAll(){
	    	list.clear();
	    	this.notifyDataSetChanged();
	    }
	    
	    @Override
	    public int getCount()
	    {
	        return list.size();
	    }

	    @Override
	    public Object getItem(int position)
	    {
	        return list.get(position);
	    }
	  
	    @Override
	    public long getItemId(int position)
	    {
	        return position;
	    }
	    
	   
	    
	    public void addListItem(PpItemObject o){
	    	list.add(o);
	    	this.notifyDataSetChanged();
	    }
	  
	    @Override
	    public View getView(final int position,View convertView,ViewGroup parent)
	    {
	    	View_TalkLayout view_TalkLayout ;
	        
	        if(convertView == null){
	        	convertView = myInflater.inflate(R.layout.pp_ls_item, null);
	        	view_TalkLayout = new View_TalkLayout();

	        	view_TalkLayout.name=(TextView)convertView.findViewById(R.id.pp_ls_i_tv);
	        	//iv1 rl
	        	view_TalkLayout.iv1=(ImageView)convertView.findViewById(R.id.pp_ls_i_iv_res);
	        	view_TalkLayout.iv2=(ImageView)convertView.findViewById(R.id.pp_ls_i_iv_picturt);
	        	view_TalkLayout.iv3=(ImageView)convertView.findViewById(R.id.pp_ls_i_iv_search);
	        	view_TalkLayout.rl = (RelativeLayout)convertView.findViewById(R.id.pp_ls_i_back);
	        	convertView.setTag(view_TalkLayout);
	        }else{
	        	view_TalkLayout = (View_TalkLayout)convertView.getTag();
	        }
	        view_TalkLayout.name.setText(list.get(position).title);
	       
        	if(position%2==0){
        		view_TalkLayout.rl.setBackgroundColor(Color.parseColor("#F5F5F5"));
        	}else if(position%2==1){
        		view_TalkLayout.rl.setBackgroundColor(Color.parseColor("#FFFFFF"));
        	}
        	
        	view_TalkLayout.iv1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					MainMenuFragment mmf = ResPage.newInstance(list.get(position));
					myActivity.showMainMenuFragment(mmf);
					synchronized(myActivity) {
						myActivity.setNeedBackup(true);
						myActivity.notify();
					}
				
				}
			});
        	
        	view_TalkLayout.iv2.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					MainMenuFragment mmf = PictPage.newInstance(list.get(position));
					myActivity.showMainMenuFragment(mmf);
					synchronized(myActivity) {
						myActivity.setNeedBackup(true);
						myActivity.notify();
					}
					
				}
			});
        	
        	view_TalkLayout.iv3.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					MainMenuFragment mmf = CheckPage.newInstance(list.get(position));
					myActivity.showMainMenuFragment(mmf);
					synchronized(myActivity) {
						myActivity.setNeedBackup(true);
						myActivity.notify();
					}
					
				}
			});
        	
	        
	        return convertView;
	    }
	}
	
}
