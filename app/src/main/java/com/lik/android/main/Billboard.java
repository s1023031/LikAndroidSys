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
import android.widget.Toast;

public class Billboard extends MainMenuFragment{
	View view;
	MyAdapter mba;
	ListView ls;
	
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mba.removeAll();
		Log.d(TAG,"OnResume start");
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String strDate = sdFormat.format(date);
		
		SQLiteDatabase db = DBAdapter.getReadableDatabase();
		//String bulletinStr="Bulletin_"+MainMenuActivity.omCurrentSysProfile.getCompanyNo()+myActivity.currentDept.getCompanyID();
		String bulletinStr="Bulletin_"+MainMenuActivity.companyParent+myActivity.companyID;
		Cursor c2=db.query(false,
				"sqlite_master",				
				new String[] {"name"},	
				"type='table' AND name='"+bulletinStr+"' ;",				//WHERE
				null, // WHERE ���Ѽ�
				null, // GROUP BY
				null, // HAVING
				null, // ORDOR BY
				null  // ����^�Ǫ�rows�ƶq
				);
		
		Log.d(TAG,"bulletinStr="+bulletinStr);
		
		if(c2.getCount()==1){

			Cursor c=db.query(true,
					bulletinStr,				
					new String[] {"BulletinSubject","BulletinBody"},	
					"",		//DateTo <='"+strDate+"';		//WHERE
					null, // WHERE ���Ѽ�
					null, // GROUP BY
					null, // HAVING
					null, // ORDOR BY
					null  // ����^�Ǫ�rows�ƶq
					);
			
			if (c.getCount()>0) {
				c.moveToFirst();
				//Toast.makeText(myActivity, ""+c.getString(0), Toast.LENGTH_LONG).show();
				mba.addListItem(new BBitem(c.getString(0),c.getString(1)));
				while(c.moveToNext()){
					//Toast.makeText(myActivity, ""+c.getString(0), Toast.LENGTH_LONG).show();
					mba.addListItem(new BBitem(c.getString(0),c.getString(1)));
				}
			}
		}else{
			//Toast.makeText(myActivity, "2222  "+c2.getCount(), Toast.LENGTH_SHORT).show();
		}
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater,container,savedInstanceState);
		view = inflater.inflate(R.layout.main_bb, container, false);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG,"onActivityCreated start!");
		setView();
		myActivity.setBadges();
	}

	private void setView() {
		// TODO Auto-generated method stub
		ls = (ListView)view.findViewById(R.id.main_bb_ls);
		mba= new MyAdapter(myActivity);
		ls.setAdapter(mba);

	}
	
	
	public static MainMenuFragment newInstance() {
        Log.d(TAG, "in Billboard newInstance()");

        Billboard mf = new Billboard();

        // Supply index input as an argument.
		Bundle args = new Bundle();
		//.args.putInt("index", index);
		mf.setArguments(args);
		return mf;
	}
	public class MyAdapter extends BaseAdapter{    
	    
	    private LayoutInflater myInflater;
	   ArrayList<BBitem> list = new ArrayList<BBitem>();
	  
	   
	   private class View_TalkLayout{
		   ImageView iv1;
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
	    
	    public void addListItem(BBitem o){
	    	list.add(o);
	    	this.notifyDataSetChanged();
	    }
	  
	    @Override
	    public View getView(final int position,View convertView,ViewGroup parent)
	    {
	 
	    	View_TalkLayout view_TalkLayout ;
	        
	        if(convertView == null){
	        	convertView = myInflater.inflate(R.layout.bb_ls_item, null);
	        	view_TalkLayout = new View_TalkLayout();

	        	view_TalkLayout.name=(TextView)convertView.findViewById(R.id.bb_ls_item__tv);
	        	//iv1 rl
	        	view_TalkLayout.iv1=(ImageView)convertView.findViewById(R.id.bb_ls_item__iv_r);
	        	
	        	view_TalkLayout.rl = (RelativeLayout)convertView.findViewById(R.id.bb_ls_item__back);
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
					MainMenuFragment mmf = BBdetail.newInstance(list.get(position));
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
