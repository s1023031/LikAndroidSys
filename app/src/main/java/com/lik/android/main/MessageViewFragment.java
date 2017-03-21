package com.lik.android.main;

import java.util.ArrayList;









import com.lik.android.om.InstantMessages;
import com.lik.android.view.BaseDataAdapter;
import com.lik.android.view.IMSubjectView;
import com.lik.android.view.InstantMessagesDataAdapter;
import com.lik.android.view.InstantMessagesView;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MessageViewFragment extends MainMenuFragment{
	View view;
	ListView ls;
	RadioGroup rg;
	RadioButton rbAll,rbIsRead,rbNotRead;
	MyAdapter mba;
	BaseDataAdapter<InstantMessagesView> adapter2;
	BaseDataAdapter<IMSubjectView> adapter1;
	boolean read = false;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater,container,savedInstanceState);
		view = inflater.inflate(R.layout.main_mesg_view, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG,"onActivityCreated start!");
		setView();
		getMesg();
		//testSetBadge();
		myActivity.setBadges();
	}
	
	
	
//+myActivity.currentDept.getCompanyID()
	private void getMesg() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = DBAdapter.getReadableDatabase();
		Cursor c=db.query(false,
				"InstantMessages_"+myActivity.omCurrentSysProfile.getCompanyNo()+myActivity.currentDept.getCompanyID(),				//��ƪ�W��
				new String[] {"SerialID","Subject","Content","IsRead"},	//���W��
				null,				//WHERE
				null, // WHERE ���Ѽ�
				null, // GROUP BY
				null, // HAVING
				null, // ORDOR BY
				null  // ����^�Ǫ�rows�ƶq
				);
		Toast.makeText(myActivity, ""+c.getCount(), Toast.LENGTH_SHORT).show();
		if(c.getCount()>0){
			c.moveToFirst();
			do{
				//String mesgID,String title,String message,Boolean isRead
				if(c.getInt(3)==1)
					mba.addListItem(new MessageItem(c.getLong(0),c.getString(1),c.getString(2),true));
				else
					mba.addListItem(new MessageItem(c.getLong(0),c.getString(1),c.getString(2),false));
			}while(c.moveToNext());
			
		}
	}

	private void testSetBadge() {
		// TODO Auto-generated method stub
		myActivity.setBadges(19);
	}

	private void setView() {
		// TODO Auto-generated method stub
		ls = (ListView)view.findViewById(R.id.mmv_ls);
		mba = new MyAdapter(myActivity);
		ls.setAdapter(mba);
		
		rg = (RadioGroup)view.findViewById(R.id.mmv_rg);
		rbAll = (RadioButton)view.findViewById(R.id.mmv_rb1);
		rbIsRead = (RadioButton)view.findViewById(R.id.mmv_rb3);
		rbNotRead = (RadioButton)view.findViewById(R.id.mmv_rb2);
		
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.mmv_rb1:
					mba.removeAll();
					SQLiteDatabase db = DBAdapter.getReadableDatabase();
					Cursor c=db.query(false,
							"InstantMessages_"+myActivity.omCurrentSysProfile.getCompanyNo()+myActivity.currentDept.getCompanyID(),				//��ƪ�W��
							new String[] {"SerialID","Subject","Content","IsRead"},	//���W��
							null,				//WHERE
							null, // WHERE ���Ѽ�
							null, // GROUP BY
							null, // HAVING
							null, // ORDOR BY
							null  // ����^�Ǫ�rows�ƶq
							);
					if(c.getCount()>0){
						c.moveToFirst();
						do{
							//String mesgID,String title,String message,Boolean isRead
							if(c.getInt(3)==1)
								mba.addListItem(new MessageItem(c.getLong(0),c.getString(1),c.getString(2),true));
							else
								mba.addListItem(new MessageItem(c.getLong(0),c.getString(1),c.getString(2),false));
						}while(c.moveToNext());
						
					}
					break;
					case R.id.mmv_rb2:
						mba.removeAll();
						if(rbNotRead.isChecked()) {
							read = false;
						}
						SQLiteDatabase db2 = DBAdapter.getReadableDatabase();
						Cursor c2=db2.query(false,
								"InstantMessages_"+myActivity.omCurrentSysProfile.getCompanyNo()+myActivity.currentDept.getCompanyID(),				//��ƪ�W��
								new String[] {"SerialID","Subject","Content","IsRead"},	//���W��
								null,				//WHERE
								null, // WHERE ���Ѽ�
								null, // GROUP BY
								null, // HAVING
								null, // ORDOR BY
								null  // ����^�Ǫ�rows�ƶq
								);
						if(c2.getCount()>0){
							c2.moveToFirst();
							do{
								//String mesgID,String title,String message,Boolean isRead
								if(c2.getInt(3)==0)
									mba.addListItem(new MessageItem(c2.getLong(0),c2.getString(1),c2.getString(2),false));
								
							}while(c2.moveToNext());
							
						}
						break;
					case R.id.mmv_rb3:
						mba.removeAll();
						SQLiteDatabase db3 = DBAdapter.getReadableDatabase();
						Cursor c3=db3.query(false,
								"InstantMessages_"+myActivity.omCurrentSysProfile.getCompanyNo()+myActivity.currentDept.getCompanyID(),				//��ƪ�W��
								new String[] {"SerialID","Subject","Content","IsRead"},	//���W��
								null,				//WHERE
								null, // WHERE ���Ѽ�
								null, // GROUP BY
								null, // HAVING
								null, // ORDOR BY
								null  // ����^�Ǫ�rows�ƶq
								);
						if(c3.getCount()>0){
							c3.moveToFirst();
							do{
								//String mesgID,String title,String message,Boolean isRead
								if(c3.getInt(3)==1)
									mba.addListItem(new MessageItem(c3.getLong(0),c3.getString(1),c3.getString(2),true));
								
							}while(c3.moveToNext());
							
						}
						break;
				}
	
			}
		});
		
		ls.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			             Log.d(TAG,"onItemClick index="+position);
							  InstantMessages omIM = new InstantMessages();
							  if(read) return; // 已讀不用再設了
								omIM.setSerialID(mba.getList().get(position).mesgID);
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
								MainMenuFragment mmf = MessageItemViewFragmant.newInstance(mba.getList(),position);
								myActivity.showMainMenuFragment(mmf);
								synchronized(myActivity) 
								{
									myActivity.setNeedBackup(true);
									myActivity.notify();
								}
				
			}
		});
		
		/*ls.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
			      Log.d(TAG,"Gallery onItemClick index="+position);
				  InstantMessages omIM = new InstantMessages();
				  if(read) return; // 已讀不用再設了
					omIM.setSerialID(mba.getList().get(position).mesgID);
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
					
					MainMenuFragment mmf = MessageItemViewFragmant.newInstance(mba.getList(),position);
					myActivity.showMainMenuFragment(mmf);
					synchronized(myActivity) {
						myActivity.setNeedBackup(true);
						myActivity.notify();
					}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});*/
	}

	public static MainMenuFragment newInstance() {
        Log.d(TAG, "in AddVisitCustomerFragment newInstance()");

        MessageViewFragment mf = new MessageViewFragment();

        // Supply index input as an argument.
		Bundle args = new Bundle();
		//.args.putInt("index", index);
		mf.setArguments(args);
		return mf;
	}
	
	
	
	
	
public class MyAdapter extends BaseAdapter{    
	    
	    private LayoutInflater myInflater;
	   ArrayList<MessageItem> list = new ArrayList<MessageItem>();
	  
	   
	   private class View_TalkLayout{

		   TextView tv1,tv2;
		   RelativeLayout rl;
		}
	   
	    public MyAdapter(Context ctxt){
	    	myInflater=(LayoutInflater)ctxt.getSystemService(myActivity.LAYOUT_INFLATER_SERVICE);
	    }
	    
	    public void removeAll(){
	    	list.clear();
	    	this.notifyDataSetChanged();
	    }
	    
	    public void removeByP(int p){
	    	list.remove(p);
	    	this.notifyDataSetChanged();
	    }
	    
	    @Override
	    public int getCount()
	    {
	        return list.size();
	    }

	    @Override
	    public MessageItem getItem(int position)
	    {
	        return list.get(position);
	    }
	  
	    @Override
	    public long getItemId(int position)
	    {
	        return position;
	    }
	    
	   
	    
	    public void addListItem(MessageItem o){
	    	
	    	list.add(o);
	    	this.notifyDataSetChanged();
	    }
	  
	    public ArrayList<MessageItem> getList(){
	    	return list;
	    }
	    
	    @Override
	    public View getView(final int position,View convertView,ViewGroup parent)
	    {
	        //嚙諛訂嚙踝蕭嚙瞌嚙璀嚙踝蕭F嚙諉別listItem嚙踝蕭嚙踝蕭view嚙踝蕭嚙踟集合嚙瘠
	    	View_TalkLayout view_TalkLayout ;
	        
	        if(convertView == null){
	            //嚙踝蕭嚙緻listItem嚙箴嚙踝蕭 view
	        	convertView = myInflater.inflate(R.layout.mmv_ls_item, null);
	        	view_TalkLayout = new View_TalkLayout();

	        	view_TalkLayout.tv1=(TextView)convertView.findViewById(R.id.mmvli_tv1);
	        	view_TalkLayout.tv2=(TextView)convertView.findViewById(R.id.mmvli_tv2);
	        	view_TalkLayout.rl = (RelativeLayout)convertView.findViewById(R.id.mmvli_back);
	        	//iv1 rl

	        	
	        	convertView.setTag(view_TalkLayout);
	        }else{
	        	view_TalkLayout = (View_TalkLayout)convertView.getTag();
	        }
	        
		        if(position%2==0){
	        		view_TalkLayout.rl.setBackgroundColor(Color.parseColor("#F5F5F5"));
	        	}else if(position%2==1){
	        		view_TalkLayout.rl.setBackgroundColor(Color.parseColor("#FFFFFF"));
	        	}
		        
	        	if(list.get(position).isRead){
	        		view_TalkLayout.tv1.setText("[已讀]");
	        	}else{
	        		view_TalkLayout.tv1.setText("[未讀]");
	        	}
	        	view_TalkLayout.tv2.setText(list.get(position).title);
	        return convertView;
	    }
	}
}
