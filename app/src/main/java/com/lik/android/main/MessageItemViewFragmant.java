package com.lik.android.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.TreeMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.xmlpull.v1.XmlPullParserException;

import com.lik.Constant;
import com.lik.util.XmlUtilExt;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MessageItemViewFragmant extends MainMenuFragment{
	View view;
	ArrayList<MessageItem> mesgList = new ArrayList<MessageItem>();
	int point;
	TextView titleTv,mesgTv,backBut;
	RelativeLayout nextBut,preBut;
	
	
	public MessageItemViewFragmant(ArrayList<MessageItem> mesgList ,int point){
		this.mesgList=mesgList;
		this.point=point;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater,container,savedInstanceState);
		view = inflater.inflate(R.layout.main_mesg_item_view, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG,"onActivityCreated start!");
		setView();
		setMesg(point);
		
	}

	private void setMesg(int point) {
		// TODO Auto-generated method stub
		
		titleTv.setText(mesgList.get(point).title);
		mesgTv.setText(mesgList.get(point).message);
		
		ContentValues values = new ContentValues();
		values.put("IsRead", 1);
		SQLiteDatabase db = DBAdapter.getWritableDatabase();
		db.update("InstantMessages_"+myActivity.currentDept.getCompanyID(), values, "SerialID" + "=" + mesgList.get(point).mesgID, null);
		myActivity.setBadges();
	}

	private void setView() {
		// TODO Auto-generated method stub
		titleTv = (TextView)view.findViewById(R.id.mmiv_title);
		mesgTv = (TextView)view.findViewById(R.id.mmiv_mesg);
		backBut = (TextView)view.findViewById(R.id.mmiv_back);
		backBut.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainMenuFragment mmf = MessageViewFragment.newInstance();
				myActivity.showMainMenuFragment(mmf);
				synchronized(myActivity) {
					myActivity.setNeedBackup(true);
					myActivity.notify();
				}
			}});
		
		
		nextBut = (RelativeLayout)view.findViewById(R.id.mmiv_next_but);
		preBut= (RelativeLayout)view.findViewById(R.id.mmiv_pre_but);
		
		nextBut.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if((point+1)<mesgList.size()){
					point++;
					setMesg(point);
				}else{
					Toast.makeText(myActivity, "這是最後一封訊息\n已經沒有訊息了！", Toast.LENGTH_LONG).show();
				}
			}});
		
		preBut.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(point!=0){
					point--;
					setMesg(point);
				}else{
					Toast.makeText(myActivity, "這是第一封訊息\n前面沒有其他訊息！", Toast.LENGTH_LONG).show();
				}
			}});
		
		/*--------------*/
		nextBut.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					v.setBackgroundDrawable(getResources().getDrawable( R.drawable.white_noc ));
					return false;
				}
				if(event.getAction()==MotionEvent.ACTION_UP){
					v.setBackgroundDrawable(getResources().getDrawable( R.drawable.balck_c ));
					
					return false;
				}
				return false;
				
				
			}
		});
		
		preBut.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					v.setBackgroundDrawable(getResources().getDrawable( R.drawable.white_noc ));
					return false;
				}
				if(event.getAction()==MotionEvent.ACTION_UP){
					v.setBackgroundDrawable(getResources().getDrawable( R.drawable.balck_c ));
					
					return false;
				}
				return false;
				
				
			}
		});
		
		
	}
	
	

	
	public static MainMenuFragment newInstance(ArrayList<MessageItem> mesgList,int point) {
        Log.d(TAG, "in AddVisitCustomerFragment newInstance()");

        MessageItemViewFragmant mf = new MessageItemViewFragmant(mesgList,point);

        // Supply index input as an argument.
		Bundle args = new Bundle();
		//.args.putInt("index", index);
		mf.setArguments(args);
		return mf;
	}
}
