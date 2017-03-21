package com.lik.android.main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lik.android.main.R;
import com.lik.android.main.LikSysActivity.ProcessVerifyCompanyID;
import com.lik.android.om.PhotoProject;
import com.lik.android.om.ResPict;
import com.lik.android.om.SiteIPList;
import com.lik.android.om.SiteInfo;
import com.lik.util.ExternalStorage;

import uk.co.senab.photoview.HackyViewPager;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


import android.app.ActionBar.LayoutParams;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ResPageForLoop extends MainMenuFragment{
	View view;
	PpItemObject pi;
	TextView back,detail;
	ImageView iv1,iv2;
    public ArrayList<NameValuePair> nameValuePairs =new ArrayList<NameValuePair>();
	ListView ls;
	MyAdapter mba;
	PhotoViewAttacher mAttacher;
	SiteIPList omSIP;
	String uri = null;
	String ip = null;
	String webPort = null;
	private Dialog dialog;
	int imageFlag = 0;
	ArrayList<Drawable> sDrawables1 = new ArrayList<Drawable>();
	ArrayList<Drawable> sDrawables2 = new ArrayList<Drawable>();
	
	Handler mHandler = new Handler();
	


	
	//
	DBHelper DH ;
	boolean findBase64 = false; 
	
	private void openDB(){
    	DH = new DBHelper(myActivity);  
    }
    private void closeDB(){
    	DH.close();    	
    }
	
	public ResPageForLoop(PpItemObject pi){
		this.pi=pi;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater,container,savedInstanceState);
		view = inflater.inflate(R.layout.main_res_page, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG,"onActivityCreated start!");
		setView();
		testDataSet();
		findBase64FormSqlite();
		Log.d(TAG,"findBase64="+findBase64);
		//if(findBase64==false)
		//	getHTTPBase64Pict(myActivity.omCurrentAccount.getAccountNo(),pi.projectNO,pi.yearMonth);
		//testImage(); 
	}

	private void findBase64FormSqlite() {
		Toast.makeText(myActivity, "載入合約台帳中，請稍候...", Toast.LENGTH_LONG).show();
		InputStream in;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inSampleSize = 3;
		// TODO Auto-generated method stub
		ResPict omResPict= new ResPict();
		// get accountImgBase64Str
		omResPict.setpNO(pi.projectNO);
		omResPict.setType("0");
		List<ResPict> resPict = omResPict.getRictPictB(DBAdapter);
			try{
				Log.d(TAG,"asdsadas="+resPict.size());
				if(resPict.size() > 0 )
				{
					for(int i = 0 ;  i< resPict.size() ; i ++)
					{
						byte[] accountImg = resPict.get(i).getBase64();
						in = new ByteArrayInputStream(accountImg);
						Bitmap bitMapImage = BitmapFactory.decodeStream(in,null,options);
						//Bitmap bitMapImage = BitmapFactory.decodeByteArray( accountImg, 0, accountImg.length);
						Drawable d = new BitmapDrawable(getResources(), bitMapImage);
						sDrawables1.add(d);
						// bitMapImage.recycle();
					}
				}else
				{
					in = this.getResources().openRawResource(R.drawable.no_pict);
					Bitmap bitMapImage = BitmapFactory.decodeStream(in,null,options);
					Drawable d = new BitmapDrawable(getResources(), bitMapImage);
					sDrawables1.add(d);
				}
					
				resPict.clear();
				omResPict.setpNO(pi.projectNO);
				omResPict.setType("1");
				resPict = omResPict.getRictPictB(DBAdapter);
				// get contractImgBase64Str
				Log.d(TAG,"asdsadsadsa="+resPict.size());
				if(resPict.size() > 0 )
				{
						 for(int i = 0 ;  i< resPict.size() ; i ++)
						 {
							 byte[] contractImg = resPict.get(i).getBase64();
							 in = new ByteArrayInputStream(contractImg);
							 Bitmap bitMapImage = BitmapFactory.decodeStream(in,null,options);
							 //Bitmap bitMapImage = BitmapFactory.decodeByteArray( contractImg, 0, contractImg.length);
							 Drawable d = new BitmapDrawable(getResources(), bitMapImage);
							 sDrawables2.add(d);
							// bitMapImage.recycle();
				
						 }
				}else
				{		
					in = this.getResources().openRawResource(R.drawable.no_pict);
					Bitmap bitMapImage = BitmapFactory.decodeStream(in,null,options);
					Drawable d = new BitmapDrawable(getResources(), bitMapImage);
					sDrawables2.add(d);
				}
			} catch(OutOfMemoryError e1)
			{
				Toast.makeText(myActivity,"手機RAM不足",Toast.LENGTH_LONG).show();
			}
			catch (Exception e)
			{
				Log.d(TAG,"asdsadas error");
			}
		
	}
	
	private void getHTTPBase64Pict(String userNO , String pNO,String yearMonth) {
		Log.d(TAG,"getHTTPBase64Pict Start");
		String url = "http://"+ip+":"+webPort+uri+"?userNo="+userNO+"&projectNO="+pNO+"&yearMonth="+yearMonth;
		Log.d(TAG,"url="+url);
		//Thread t1 = new Thread(new SendPost("http://59.126.49.129:8082/goodsamenityweb/struts2/ProcessResourcePictureAction.action?userNo="+userNO+"&projectNO="+pNO+"&yearMonth="+yearMonth,0));
		Thread t1 = new Thread(new SendPost(url,0));
		t1.start();
	}

	private void testDataSet() {
		Log.d(TAG,"load PhotoDisplay start");
		PhotoProject omPhotoProject = new PhotoProject();
		
		omPhotoProject.setUserNO(MainMenuActivity.currentUserNo);
		omPhotoProject.setCompanyID(MainMenuActivity.companyID);
		omPhotoProject.setProjectNO(pi.projectNO);;
		Log.d(TAG,"userNo = "+ omPhotoProject.getUserNO());
		Log.d(TAG,"companyID="+ omPhotoProject.getCompanyID());
		Log.d(TAG,"projectNo="+ omPhotoProject.getProjectNO());
		omPhotoProject= omPhotoProject.getPhotoProjectDisplay(DBAdapter);
		
		if(omPhotoProject.getPhotoDisplay1()!=null)
			mba.addListItem(new RPListItem(omPhotoProject.getPhotoDisplay1(),""+1));
		
		if(omPhotoProject.getPhotoDisplay2()!=null)
			mba.addListItem(new RPListItem(omPhotoProject.getPhotoDisplay2(),""+2));
		
		if(omPhotoProject.getPhotoDisplay3()!=null)
			mba.addListItem(new RPListItem(omPhotoProject.getPhotoDisplay3(),""+3));
		
		if(omPhotoProject.getPhotoDisplay4()!=null)
			mba.addListItem(new RPListItem(omPhotoProject.getPhotoDisplay4(),""+4));
		
		if(omPhotoProject.getPhotoDisplay5()!=null)
			mba.addListItem(new RPListItem(omPhotoProject.getPhotoDisplay5(),""+5));
		
		if(omPhotoProject.getPhotoDisplay6()!=null)
			mba.addListItem(new RPListItem(omPhotoProject.getPhotoDisplay6(),""+6));
		
		if(omPhotoProject.getPhotoDisplay7()!=null)
			mba.addListItem(new RPListItem(omPhotoProject.getPhotoDisplay7(),""+7));
		
		if(omPhotoProject.getPhotoDisplay8()!=null)
			mba.addListItem(new RPListItem(omPhotoProject.getPhotoDisplay8(),""+8));
		
		
		if(mba.list.size()==0)
			mba.addListItem(new RPListItem("暫無助陳列物",""+8));
		/*for(int i=1;i<=20;i++)
			mba.addListItem(new RPListItem("陳列物品"+i,""+i));*/
		
		Log.d(TAG,"load PhotoDisplay END");
	}

	private void setView() {
		// TODO Auto-generated method stub
		
		ls = (ListView)view.findViewById(R.id.mrp_ls);
		mba = new MyAdapter(myActivity);
		ls.setAdapter(mba);
		
		iv1 = (ImageView)view.findViewById(R.id.mrp_iv1);
		iv1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
			//	if(imageFlag==1){
					ViewPager mViewPager;
					dialog = new Dialog(myActivity, R.style.Translucent_NoTitle);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.photo_zoom_view);
					RelativeLayout lr = (RelativeLayout)dialog.findViewById(R.id.pzv_rl);
					mViewPager = new HackyViewPager(myActivity);
					lr.addView(mViewPager);
					mViewPager.setAdapter(new SamplePagerAdapter(sDrawables2));
					//Drawable bitmap1 = iv1.getDrawable();
					/*iv.setImageDrawable(bitmap1 );
					mAttacher = new PhotoViewAttacher(iv);*/
					//iv.setAdapter(new SamplePagerAdapter(sDrawables1));
					dialog.show();
			//	}else{
					//Toast.makeText(myActivity, "尚未下載完成，請稍候再試...", Toast.LENGTH_LONG).show();
			//	}
			}});
		iv2 = (ImageView)view.findViewById(R.id.mrp_iv2);
	
		iv2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				//if(imageFlag==1){
					ViewPager mViewPager;
					dialog = new Dialog(myActivity, R.style.Translucent_NoTitle);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.photo_zoom_view);
					RelativeLayout lr = (RelativeLayout)dialog.findViewById(R.id.pzv_rl);
					mViewPager = new HackyViewPager(myActivity);
					lr.addView(mViewPager);
					mViewPager.setAdapter(new SamplePagerAdapter(sDrawables1));
					
					dialog.show();
			//	}else{
				//	Toast.makeText(myActivity, "尚未下載完成，請稍候再試...", Toast.LENGTH_LONG).show();
			//	}
			}});
		
		back = (TextView)view.findViewById(R.id.mrp_back);
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				//myActivity.getFragmentManager().beginTransaction().remove(CustomerDetail.this).commit();
				MainMenuFragment mmf = ProjectPhoto.newInstance(pi.OrderID,pi.customerID);
				myActivity.showMainMenuFragment(mmf);
				synchronized(myActivity) {
					myActivity.setNeedBackup(true);
					myActivity.notify();
				}
			}
		});
		
		detail  = (TextView)view.findViewById(R.id.mp_page_name);
		detail.setText("店家名稱："+pi.shopName+"\n" +"活動名稱："+pi.title);
		
		Log.d(TAG,"projectNO="+pi.projectNO);

		omSIP = getSiteIP("D");
		 ip=omSIP.getIp();
		webPort=String.valueOf(omSIP.getWebPort());
		 uri = getResources().getString(R.string.DownloadProductsImagesURI);
		//url = "http://"+ip+":"+webPort+url+"?userNo="+userNo+"&projectNO="+pi.projectNO;
	}
	
	public static MainMenuFragment newInstance(PpItemObject pi) {
        Log.d(TAG, "in AddVisitCustomerFragment newInstance()");

        ResPageForLoop mf = new ResPageForLoop(pi);

        // Supply index input as an argument.
		Bundle args = new Bundle();
		//.args.putInt("index", index);
		mf.setArguments(args);
		return mf;
	}
	
	public class MyAdapter extends BaseAdapter{    
	    
	    private LayoutInflater myInflater;
	   ArrayList<RPListItem> list = new ArrayList<RPListItem>();
	  
	   
	   private class View_TalkLayout{

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
	    
	   
	    
	    public void addListItem(RPListItem o){
	    	list.add(o);
	    	this.notifyDataSetChanged();
	    }
	  
	    @Override
	    public View getView(final int position,View convertView,ViewGroup parent)
	    {
	    	View_TalkLayout view_TalkLayout ;
	        
	        if(convertView == null){

	        	convertView = myInflater.inflate(R.layout.single_string_ls_item, null);
	        	view_TalkLayout = new View_TalkLayout();

	        	view_TalkLayout.name=(TextView)convertView.findViewById(R.id.ssli_tv);
	        	view_TalkLayout.rl = (RelativeLayout)convertView.findViewById(R.id.ssli_back);
	        	convertView.setTag(view_TalkLayout);
	        }else{
	        	view_TalkLayout = (View_TalkLayout)convertView.getTag();
	        }
	      
	        view_TalkLayout.name.setText(list.get(position).name);
	       
        	if(position%2==0){
        		view_TalkLayout.rl.setBackgroundColor(Color.parseColor("#F5F5F5"));
        	}else if(position%2==1){
        		view_TalkLayout.rl.setBackgroundColor(Color.parseColor("#FFFFFF"));
        	}

	        return convertView;
	    }
	}
	
	
	static class SamplePagerAdapter extends PagerAdapter {

		/*private static int[] sDrawables = { R.drawable.wallpaper, R.drawable.wallpaper, R.drawable.wallpaper,
				R.drawable.wallpaper, R.drawable.wallpaper, R.drawable.wallpaper };*/
		
		private ArrayList<Drawable> sDrawables ;
		
		public SamplePagerAdapter(ArrayList<Drawable> sDrawables){
			this.sDrawables=sDrawables;
		}

		@Override
		public int getCount() {
			return sDrawables.size();
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			PhotoView photoView = new PhotoView(container.getContext());
			//photoView.setImageResource(sDrawables[position]);
			photoView.setImageDrawable(sDrawables.get(position));
			// Now just add PhotoView to ViewPager and return it
			container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

			return photoView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}
	/**/
	class SendPost implements Runnable{
		String rul;
		int flag;
		public SendPost(String rul,int flag){
			this.rul=rul;
			this.flag=flag;
		}
		public void run() {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(rul);
			Log.d(TAG, "url="+rul);
			UrlEncodedFormEntity ent;
			try {
				Thread.sleep(150);
				ent = new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8);
				httppost.setEntity(ent);
				HttpResponse responsePOST = httpclient.execute(httppost);
				HttpEntity resEntity = responsePOST.getEntity();
				String passMsg=EntityUtils.toString(resEntity, HTTP.UTF_8);
				if (passMsg.startsWith("\ufeff")) {
					passMsg = passMsg.substring(1);
					}
				nameValuePairs.clear();
				sHandler.obtainMessage(flag,passMsg).sendToTarget();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		
	}
	Handler sHandler = new Handler(){
		
		public void handleMessage(Message msg)
	      {
			String result = null;
			if (msg.obj instanceof String){
				result = (String) msg.obj;
				}
			switch (msg.what) { 
			case 0:
				new Base64Decode(result.toString()).start();
				
				break;
			}
	      }
	};
	/*
	 * 
	 ArrayList<Drawable> sDrawables1 = new ArrayList<Drawable>();
	ArrayList<Drawable> sDrawables2 = new ArrayList<Drawable>(); 
	 */
	class Base64Decode extends Thread{
		String r;
		public Base64Decode(String r){
			this.r=r;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			try {
					Log.d(TAG,"Base64Decode Start");
					JSONObject jsonObject =new JSONObject(r);
					JSONArray a =jsonObject.getJSONArray("accountImgBase64Str");
					mHandler.post(new DebugPring("accountImgBase64Str Count:"+a.length()+""));
					for(int j=0;j<a.length();j++){
						byte[] decodedString = Base64.decode(a.get(j).toString(), Base64.URL_SAFE);
						Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
						Drawable d = new BitmapDrawable(getResources(), decodedByte);
						sDrawables1.add(d);
						BitmapDrawable bitDw = ((BitmapDrawable) d);
						Bitmap bitmap = bitDw.getBitmap();
						ByteArrayOutputStream stream = new ByteArrayOutputStream();
						bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
						byte[] imageInByte = stream.toByteArray();
						add("0",imageInByte);
					}
					
					JSONArray b =jsonObject.getJSONArray("contractImgBase64Str");
					mHandler.post(new DebugPring("contractImgBase64Str Count:"+b.length()+""));
					for(int k=0;k<b.length();k++){
						byte[] decodedString = Base64.decode(b.get(k).toString(), Base64.URL_SAFE);
						Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
						Drawable d = new BitmapDrawable(getResources(), decodedByte);
						sDrawables2.add(d);
						BitmapDrawable bitDw = ((BitmapDrawable) d);
						Bitmap bitmap = bitDw.getBitmap();
						ByteArrayOutputStream stream = new ByteArrayOutputStream();
						bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
						byte[] imageInByte = stream.toByteArray();
						add("1",imageInByte);
					}
					
				
				imageFlag=1;
				mHandler.post(new DebugPring("活動合約 與 抬帳 下載完成！"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		
	}
	 
	
	class DebugPring implements Runnable {
		String s;
		public DebugPring(String s){
			this.s=s;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Toast.makeText(myActivity, s, Toast.LENGTH_LONG).show();
		}
		
	}
	
	private void add(String type,byte[] base64){
		openDB();
        SQLiteDatabase db = DH.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_Pno", pi.projectNO);
        values.put("_type", type);
        values.put("_Date", getDate());
        values.put("_Base64",base64);
        db.insert("ResPict", null, values);
        closeDB();
    }
	
	public String getDate(){
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		String date = sDateFormat.format(new java.util.Date());
		return date;
	}
}
