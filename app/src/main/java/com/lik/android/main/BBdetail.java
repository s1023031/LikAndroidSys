package com.lik.android.main;

import  com.lik.android.main.R;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class BBdetail extends MainMenuFragment{
	View view;
	BBitem bitem;
	
	public BBdetail(BBitem bitem){
		this.bitem=bitem;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater,container,savedInstanceState);
		view = inflater.inflate(R.layout.main_bb_detail, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG,"onActivityCreated start!");
        // �s�W���X�Ȥ�
		setView();
		
		
	}

	private void setView() {
		// TODO Auto-generated method stub
		TextView title = (TextView)view.findViewById(R.id.bbd_title);
		TextView detail = (TextView)view.findViewById(R.id.bbd_detail);
		TextView back = (TextView)view.findViewById(R.id.bbd_back);
		title.setText(bitem.title);
		detail.setText(bitem.detail);
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainMenuFragment mmf = Billboard.newInstance();
				myActivity.showMainMenuFragment(mmf);
				synchronized(myActivity) {
					myActivity.setNeedBackup(true);
					myActivity.notify();
				}
			}});
	}
	
	public static MainMenuFragment newInstance(BBitem bitem) {
        Log.d(TAG, "in AddVisitCustomerFragment newInstance()");

        BBdetail mf = new BBdetail(bitem);

        // Supply index input as an argument.
		Bundle args = new Bundle();
		//.args.putInt("index", index);
		mf.setArguments(args);
		return mf;
	}

}
