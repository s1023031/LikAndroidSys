package com.lik.android.main;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.lik.android.main.InstantMessagesFragment;
import com.lik.android.om.InstantMessages;
import com.lik.android.view.BaseDataAdapter;
import com.lik.android.view.IMSubjectDataAdapter;
import com.lik.android.view.IMSubjectView;
import com.lik.android.view.InstantMessagesDataAdapter;
import com.lik.android.view.InstantMessagesView;

public class InstantMessagesFragment extends MainMenuFragment {
	
	protected static final String TAG = InstantMessagesFragment.class.getName();
	protected final SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",Locale.CHINESE);
	Gallery g;
	View view;
	RadioButton rb0,rb1;
	Spinner sp1;
	TextView tv2,tv2a,tv2b;
	BaseDataAdapter<IMSubjectView> adapter1;
	BaseDataAdapter<InstantMessagesView> adapter2;
	boolean read = false;
	
	public static MainMenuFragment newInstance() {
        Log.v(TAG, "in InstantMessagesFragment newInstance");

        InstantMessagesFragment mf = new InstantMessagesFragment();

        // Supply index input as an argument.
		Bundle args = new Bundle();
		//args.putInt("index", index);
		mf.setArguments(args);
		return mf;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.i(TAG,"onCreateView start");
		super.onCreateView(inflater,container,savedInstanceState);
		view = inflater.inflate(R.layout.main_im_gallery, container, false);
		Log.i(TAG,"isIMEnable end");
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG,"onActivityCreated start!");
		gallery();
	}
	
	private void gallery() {
		final String s1 = myActivity.getResources().getString(R.string.imTextView2);
		final String s2 = myActivity.getResources().getString(R.string.imTextView3);
		final String s3 = myActivity.getResources().getString(R.string.imTextView4);
		tv2 = (TextView)view.findViewById(R.id.im_gallery_textView2);
		tv2a = (TextView)view.findViewById(R.id.im_gallery_textView2a);
		tv2b = (TextView)view.findViewById(R.id.im_gallery_textView2b);
		// 檢查table是否存在
		InstantMessages omIMs = new InstantMessages();
    	if(!omIMs.testTableExists(DBAdapter)) {
    		SQLiteDatabase db = DBAdapter.getdb();
    		String cmd = omIMs.getDropCMD();
    		if(cmd != null) db.execSQL(cmd);	
    		cmd = omIMs.getCreateCMD();
    		if(cmd != null) db.execSQL(cmd);	
			String[] cmds = omIMs.getCreateIndexCMD();
			for(int j=0;j<cmds.length;j++) {
				cmd = cmds[j];
				db.execSQL(cmd);	
			}
			DBAdapter.closedb();
    	}
		// for im subject
		sp1 = (Spinner)view.findViewById(R.id.im_gallery_spinner1);
		adapter1 = new IMSubjectDataAdapter(myActivity,DBAdapter);
		adapter1.gatherData(myActivity.omCurrentAccount.getAccountNo(),Boolean.toString(read));
		sp1.setAdapter(adapter1);
    	sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				IMSubjectView omView = (IMSubjectView)adapter1.getItem(position);
				Log.d(TAG,"onItemClick index="+position+",subject="+omView.getSubject());
				// sync gallery
				g.setSelection(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// do nothing
				
			}});
		// for gallery
	    g = (Gallery)view.findViewById(R.id.im_gallery_gallery1);
	    adapter2 = new InstantMessagesDataAdapter(myActivity,DBAdapter);
	    adapter2.gatherData(myActivity.omCurrentAccount.getAccountNo(),Boolean.toString(read));
	    g.setAdapter(adapter2);
	    // gallery scroll to next item, fire OnItemSelectedListener
	    g.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> paramAdapterView,
					View paramView, int paramInt, long paramLong) {
	            Log.d(TAG,"Gallery onItemClick index="+paramInt);
				// sync to spinner
	    		Spinner sp1 = (Spinner)view.findViewById(R.id.im_gallery_spinner1);
	    		sp1.setSelection(paramInt);
	    		InstantMessagesView omView = ((InstantMessagesView)adapter2.getItem(paramInt));
				tv2.setText(s1+omView.getOwner());
				tv2a.setText(s2+sdfTime.format(omView.getPublishTime()));
				tv2b.setText(s3+(omView.getReceiveTime()==null?"":sdfTime.format(omView.getReceiveTime())));
				// set message is read
				if(read) return; // 已讀不用再設了
				InstantMessages omIM = new InstantMessages();
				omIM.setSerialID(omView.getSerialID());
				omIM.queryBySerialID(DBAdapter);
				if(omIM.getRid()>=0) {
					omIM.setRead(true);
					omIM.doUpdate(DBAdapter);
					if(omIM.getRid()>=0) {
						Log.i(TAG,"InstantMessages marked as read,subject="+omIM.getSubject());
				        // send msg to im service
				        if(myActivity.imService != null) {
					        Message msg = Message.obtain(null, InstantMessageHandler.MSG_TO_SERVICE,MainMenuActivity.SERVICE_ID,0);
					        Bundle data = msg.getData();
					        data.putString(InstantMessageHandler.MSG_CONTENT, omIM.getSubject()+" is read");
					        try {
					        	myActivity.imService.send(msg);
							} catch (RemoteException e) {
								e.printStackTrace();
							}
				        }
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> paramAdapterView) {
				// do nothing
				
			}
	    });
		final RadioGroup rg1 = (RadioGroup)view.findViewById(R.id.im_gallery_radioGroup1);
		rb0 = (RadioButton)view.findViewById(R.id.im_gallery_radio0);
		rb1 = (RadioButton)view.findViewById(R.id.im_gallery_radio1);
		rg1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				String msg="";
				switch(arg1) {
				case R.id.im_gallery_radio0:
					msg = rb0.getText().toString();
					if(rb0.isChecked()) {
						read = false;
					}
					break;
				case R.id.im_gallery_radio1:
					msg = rb1.getText().toString();
					if(rb1.isChecked()) {
						read = true;
					}
					break;
				default:
					return;
				}
				adapter1.gatherData(myActivity.omCurrentAccount.getAccountNo(),Boolean.toString(read));
				adapter1.notifyDataSetChanged();
			    adapter2.gatherData(myActivity.omCurrentAccount.getAccountNo(),Boolean.toString(read));
			    adapter2.notifyDataSetChanged();
			    if(adapter1.getCount()>0) sp1.setSelection(0);
			    else {
			    	tv2.setText("");
			    	tv2a.setText("");
			    	tv2b.setText("");
			    }
				Log.d(TAG,"onCheckedChanged:"+msg);
			}
			
		});
		InstantMessages omIM = new InstantMessages();
		omIM.setUserNo(myActivity.omCurrentAccount.getAccountNo());
		List<InstantMessages> lt = omIM.getMessages(DBAdapter);
		// 若沒有未讀訊息，自動改成已讀
		if(lt.size()==0) {
			rb1.setChecked(true);
		}
	    // for button
    	Button b1 = (Button)view.findViewById(R.id.im_gallery_button1);
    	b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				int position = g.getSelectedItemPosition();
				if(position==0) return;
				g.setSelection(position-1);
			}
					
		});
    	Button b2 = (Button)view.findViewById(R.id.im_gallery_button2);
    	b2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				int position = g.getSelectedItemPosition();
				int max = adapter2.getCount();
				if(position==max-1) return;
				g.setSelection(position+1);
			}
					
		});
    	Button b3 = (Button)view.findViewById(R.id.im_gallery_button3);
    	b3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
    			// switch to Home page (QueryNotUploadFragment)
				MainMenuFragment mmf = QueryNotUploadFragment.newInstance();
				myActivity.showMainMenuFragment(mmf);
			}
					
		});
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		//myActivity.fastBut3.clearAnimation();
		//myActivity.fastBut3.setVisibility(View.GONE);
		Log.d(TAG,"InstantMessagesFragment onStart called!");
	}		
	
	@Override
	public void onStop() {
		super.onStop();
		// check if im has unread messages
		InstantMessages omIM = new InstantMessages();
		omIM.setUserNo(myActivity.omCurrentAccount.getAccountNo());
		List<InstantMessages> lt = omIM.getMessages(DBAdapter);
		if(lt.size()>0) {
			/*
			myActivity.fastBut3.setText(String.valueOf(lt.size()));
			myActivity.fastBut3.setVisibility(View.VISIBLE);
			myActivity.fastBut3.startAnimation(myActivity.anim);*/
		} else {
			//myActivity.fastBut3.setVisibility(View.GONE);
		}
		Log.d(TAG,"onStop called!");
	}
}
