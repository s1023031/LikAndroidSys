package com.lik.android.main;

import com.lik.android.main.R;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerDetail extends MainMenuFragment{
	View view;
	TextView back,detail,phone,address;
	String OrderID;
	String CustomerID;
	ImageView camera;
	public CustomerDetail(String OrderID,String CustomerID){
		this.OrderID=OrderID;
		this.CustomerID=CustomerID;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater,container,savedInstanceState);
		view = inflater.inflate(R.layout.main_customer_detail, container, false);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG,"onActivityCreated start!");
        // �s�W���X�Ȥ�
		setView();
		getCustomerDetail();
	}
	
	private void getCustomerDetail() {
		
		String tableName = "Customers_"+myActivity.omCurrentSysProfile.getCompanyNo()+myActivity.currentDept.getCompanyID();
		// TODO Auto-generated method stub
		SQLiteDatabase db = DBAdapter.getReadableDatabase();
		Cursor c=db.query(false,
				"Orders ,"+ tableName,				
				new String[] {"ShortName",tableName+".FullName",tableName+".Tel1",tableName+".Address"},	
				tableName+".CustomerNO = Orders.CustomerNO AND Orders.OrderID = '"+OrderID+"' AND Orders.CompanyParent='"+myActivity.omCurrentSysProfile.getCompanyNo()+"';",				//WHERE
				null, // WHERE ���Ѽ�
				null, // GROUP BY
				null, // HAVING
				null, // ORDOR BY
				null  // ����^�Ǫ�rows�ƶq
				);
		Log.d(TAG,"aaaa="+c.getCount());
		if (c.getCount()>0) {
			c.moveToFirst();
			Toast.makeText(myActivity, ""+c.getString(0), Toast.LENGTH_LONG).show();
			Toast.makeText(myActivity, ""+c.getString(1), Toast.LENGTH_LONG).show();
			Toast.makeText(myActivity, ""+c.getString(2), Toast.LENGTH_LONG).show();
			//myadm.addListItem(new Page6Object(c.getString(0),c.getString(1),c.getString(2)));
			
			detail.setText("店家名稱："+c.getString(0)+"("+c.getString(1)+")\n");
			phone.setText(c.getString(2)+"");
			address.setText(c.getString(3)+"");
			address.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Uri uri = Uri.parse("geo:0,0?q="+address.getText().toString()); 
					Intent it = new Intent(Intent.ACTION_VIEW, uri);  
					startActivity(it);
				}});
			
		}
	}
	private void setView() {
		// TODO Auto-generated method stub
		back = (TextView)view.findViewById(R.id.mc_back);
		phone = (TextView)view.findViewById(R.id.mc_phone);
		address = (TextView)view.findViewById(R.id.mc_address);
		detail = (TextView)view.findViewById(R.id.mc_detail);
		
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
		
		camera = (ImageView)view.findViewById(R.id.mc_camera);
		camera.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//myActivity.getFragmentManager().beginTransaction().remove(CustomerDetail.this).commit();
				MainMenuFragment mmf = ProjectPhoto.newInstance(OrderID,CustomerID);
				myActivity.showMainMenuFragment(mmf);
				synchronized(myActivity) {
					myActivity.setNeedBackup(true);
					myActivity.notify();
				}
			}
		});
		
	}
	public static MainMenuFragment newInstance(String OrderID,String CustomerID) {
        Log.d(TAG, "in AddVisitCustomerFragment newInstance()");

        CustomerDetail mf = new CustomerDetail(OrderID,CustomerID);

        // Supply index input as an argument.
		Bundle args = new Bundle();
		//.args.putInt("index", index);
		mf.setArguments(args);
		return mf;
	}
}
