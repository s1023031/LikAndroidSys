package com.lik.android.main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lik.Constant;
import com.lik.android.main.CoreDownloadFragment.ProcessCompany;
import com.lik.android.om.SiteIPList;
import com.lik.android.util.HttpMessage;
import com.lik.android.util.HttpUtil;

public class UploadActivty extends MainMenuFragment{
	View view;
	ListView ls;
	RelativeLayout butUp,butCancel;
	LinearLayout linearLayout1;
	MyAdapter mba;
	Handler mHamdler = new Handler();
	private DBHelper DH = null;
	ArrayList<UploadObject> upLoadObjectList = new ArrayList<UploadObject>();
	ArrayList<UploadObject> upLoadFailList = new ArrayList<UploadObject>();
	SiteIPList omSIP;
	String uploadImageURI = null;
	Boolean isUpload = false;
	protected final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	private void openDB(){
    	DH = new DBHelper(myActivity.getApplicationContext());  
    }
    private void closeDB(){
    	DH.close();    	
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater,container,savedInstanceState);
		view = inflater.inflate(R.layout.main_upload_page, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG,"onActivityCreated start!");
		setView();
		//testData();
		loadNoUpDateTitle();	
	}

	private void loadNoUpDateTitle() {
		// TODO Auto-generated method stub
		openDB();
		SQLiteDatabase db = DH.getReadableDatabase();
		Cursor c=db.query(false,
				"MyPict",				
				new String[] {"_LastDate","COUNT(_id)"},	
				"_isUpolad = 'n'",				//WHERE
				null, // WHERE ���Ѽ�
				"_LastDate", // GROUP BY
				"COUNT(_id)>0", // HAVING
				null, // ORDOR BY
				null  // ����^�Ǫ�rows�ƶq
				);
		
		if (c.getCount()>0) 
		{
			c.moveToFirst();
			mba.addListItem(new UploadListItem(c.getString(0),"("+c.getString(1)+")",false)); 
			while(c.moveToNext()){
				mba.addListItem(new UploadListItem(c.getString(0),"("+c.getString(1)+")",false)); 
			}
		}
		closeDB();
	}

	

	private void setView() {
		
	    String type = SiteIPList.TYPE_UPLOAD;
	    uploadImageURI=getResources().getString(R.string.UploadPhotoFile);
		omSIP = getSiteIP(type); 
		ls = (ListView)view.findViewById(R.id.mup_ls);
		mba = new MyAdapter(myActivity);
		ls.setAdapter(mba);
		butUp = (RelativeLayout)view.findViewById(R.id.mup_upload_but);
		butCancel = (RelativeLayout)view.findViewById(R.id.mup_cancel_but);
		linearLayout1 = (LinearLayout)myActivity.findViewById(R.id.global_linearLayout1);
    	linearLayout1.setVisibility(LinearLayout.GONE);
		
		butUp.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
			    if(isUpload)
			    {
			    	String msg = myActivity.getString(R.string.Message19);
        			AlertDialog dialog1 = getAlertDialogForMessage(myActivity.getString(R.string.downloadMessage1),msg);
        			dialog1.show();
			    }else{
			    	upLoadObjectList.clear();
					 ArrayList<UploadListItem> list = mba.getAllList();
					 int listSize = list.size();
					 for(int i =0; i<listSize ; i++){
						 if(list.get(i).isCheck){
							
							 /*-----------------------*/
							 /*	String PHFL_CMPY_ID;   //_CompanyID
								String PHFL_PHOTOFILE_ID; // _id
								String PHFL_NAME; // _FileName
								String PHFL_PATH; // _Dir
								String PHFL_DATE;  //_DateTime
								String PHFL_LONGITUDE; //_Lon
								String PHFL_LATITUDE; //_Lat
								String PHFL_PROJECT_ID; //_Pid
								String PHFL_CUST_ID; //_CustomerID
								String PHFL_SALES_ID; //_SalesID 
								String PHFL_PHOTOSTATE_ID; //_SflagID
								String PHFL_REMARK; //_Detail
								String PHFL_POSITION_DIFFERENCE;
								String PHFL_GRADE;*/
							    openDB();
								SQLiteDatabase db = DH.getReadableDatabase();
								Cursor c=db.query(false,
										"MyPict",			
										new String[] {"_CompanyID","_id","_FileName","_Dir","_DateTime","_Lon","_Lat","_Pid","_CustomerID","_SalesID","_SflagID","_Detail","_PHFL_POSITION_DIFFERENCE"},	//���W��
										"_LastDate = '"+list.get(i).title+"' AND _isUpolad ='n';",				//WHERE
										null, // WHERE ���Ѽ�
										null, // GROUP BY
										null, // HAVING
										null, // ORDOR BY
										null  // ����^�Ǫ�rows�ƶq
										);
								
								if (c.getCount()>0) {
									c.moveToFirst();
									
									upLoadObjectList.add(new UploadObject(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8),c.getString(9),c.getString(10),c.getString(11),c.getString(12),""));
									upLoadFailList.add(new UploadObject(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8),c.getString(9),c.getString(10),c.getString(11),c.getString(12),""));
									while(c.moveToNext()){
										upLoadObjectList.add(new UploadObject(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8),c.getString(9),c.getString(10),c.getString(11),c.getString(12),""));
										upLoadFailList.add(new UploadObject(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8),c.getString(9),c.getString(10),c.getString(11),c.getString(12),""));
									}
								}
								closeDB();
							 /*-----------------------*/	
						 }
					 }
					 Toast.makeText(myActivity, "共要上傳"+upLoadObjectList.size()+"相片資訊", Toast.LENGTH_LONG).show();
					 /*-----------------------------------*/
				    	//上傳upLoadObjectList 裡面的所有東西
					/*------------------------------------*/
				    
				    int upLoadObjectListSize = upLoadObjectList.size();
			    	Log.i(TAG,"upLoadObjectListSize="+upLoadObjectListSize);
				    Log.i(TAG,"ImageUploadTask Start");
					// start upload
				    // check ip and sitename
				    new ProcessCompany().execute(omSIP.getIp(),String.valueOf(omSIP.getWebPort()),DEVICEID);

			    }
			}});
		
		butCancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
			    if(isUpload)
			    {
			    	String msg = myActivity.getString(R.string.Message19);
    				//Toast.makeText(myActivity,msg,Toast.LENGTH_SHORT).show();
        			AlertDialog dialog1 = getAlertDialogForMessage(myActivity.getString(R.string.downloadMessage1),msg);
        			dialog1.show();
			    }else
			    {
			    	linearLayout1.setVisibility(1);
					MainMenuFragment mmf = QueryNotUploadFragment.newInstance();
					myActivity.showMainMenuFragment(mmf);
			    }
					
			}});
	}
	
	public static MainMenuFragment newInstance() {
        Log.d(TAG, "in AddVisitCustomerFragment newInstance()");

        UploadActivty mf = new UploadActivty();

        // Supply index input as an argument.
		Bundle args = new Bundle();
		//.args.putInt("index", index);
		mf.setArguments(args);
		return mf;
	}
	
	
	
public class MyAdapter extends BaseAdapter{    
	    
	    private LayoutInflater myInflater;
	   ArrayList<UploadListItem> list = new ArrayList<UploadListItem>();
	  
	   
	   private class View_TalkLayout{
		
		   TextView title; 
		   RelativeLayout rl;
		   CheckBox ck;
	       
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
	    
	   public  ArrayList<UploadListItem> getAllList(){
		   return list;
	   }
	    
	    public void addListItem(UploadListItem o){
	    	list.add(o);
	    	this.notifyDataSetChanged();
	    }
	  
	    @Override
	    public View getView(final int position,View convertView,ViewGroup parent)
	    {
	    	View_TalkLayout view_TalkLayout ;
	        
	        if(convertView == null){
	        	convertView = myInflater.inflate(R.layout.mup_ls_item, null);
	        	view_TalkLayout = new View_TalkLayout();

	        	view_TalkLayout.title=(TextView)convertView.findViewById(R.id.mli_tv);
	        	//iv1 rl
	        	view_TalkLayout.ck = (CheckBox)convertView.findViewById(R.id.mli_ck);
	        	view_TalkLayout.rl = (RelativeLayout)convertView.findViewById(R.id.mli_rl);
	        	convertView.setTag(view_TalkLayout);
	        }else{
	        	view_TalkLayout = (View_TalkLayout)convertView.getTag();
	        }
	        view_TalkLayout.title.setText(list.get(position).title+list.get(position).count);
	       
        	if(position%2==0){
        		view_TalkLayout.rl.setBackgroundColor(Color.parseColor("#F5F5F5"));
        	}else if(position%2==1){
        		view_TalkLayout.rl.setBackgroundColor(Color.parseColor("#FFFFFF"));
        	}
        	
        	if(list.get(position).isCheck){
        		view_TalkLayout.ck.setChecked(true);
        	}else{
        		view_TalkLayout.ck.setChecked(false);
        	}
        	view_TalkLayout.ck.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(((CheckBox)v).isChecked()){
						list.get(position).isCheck=true;
					}else{
						list.get(position).isCheck=false;
					}
				}});
	        return convertView;
	    }
	}

	class ImageUploadTask extends AsyncTask<String, Integer, String> {

			@Override
			protected String doInBackground(String... params) {
				Log.i(TAG,"ImageUploadTask Start");
				String result = null;
				int upLoadObjectListSize = upLoadObjectList.size();
	    	    HttpClient httpclient = new DefaultHttpClient();
	    	    try {
	    	    	Log.i(TAG,"upLoadObjectListSize="+upLoadObjectListSize);
	    	    	for(UploadObject uploadObject : upLoadObjectList) {
	    	    	   	Log.i(TAG,"upLoadObjecte Start" );
	    	    		File file = new File(uploadObject.PHFL_PATH);
	    	    		Log.i(TAG,"file="+file.exists() );	    	    	
	    	    		//Uri uri = Uri.fromFile(file);
	    	    		Uri uri =  Uri.parse("file://"+file.getPath());  //解決中文亂碼
		    	    	final InputStream stream = myActivity.getContentResolver().openInputStream(uri);
						StringBuffer url = new StringBuffer();
						url.append("http://").append(omSIP.getIp());
						url.append(":").append(omSIP.getWebPort());
						url.append(uploadImageURI);
						url.append("?companyID=").append(myActivity.currentDept.getCompanyID());
						url.append("&companyNO=").append(myActivity.currentDept.getCompanyNO());
						url.append("&photoFileID=").append(sdf.format(new Date())+uploadObject.PHFL_SALES_ID+uploadObject.PHFL_PHOTOFILE_ID);
						url.append("&fileName=").append(file.getName().replaceAll(" ", "%20"));
						url.append("&longitude=").append(uploadObject.PHFL_LONGITUDE);
						url.append("&latitude=").append(uploadObject.PHFL_LATITUDE);
						url.append("&projectID=").append(uploadObject.PHFL_PROJECT_ID);
						url.append("&customerID=").append(uploadObject.PHFL_CUST_ID);
						url.append("&salesID=").append(uploadObject.PHFL_SALES_ID);
						url.append("&photoStateID=").append(uploadObject.PHFL_PHOTOSTATE_ID);
						url.append("&remark=").append(uploadObject.PHFL_REMARK);
						url.append("&positionDiff=").append(uploadObject.PHFL_POSITION_DIFFERENCE);
						
						Bitmap bm = getBitmapFromFile(uploadObject.PHFL_PATH);
						ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
						bm.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
					  /* // compress to 300kb
						int quality=100;
			            while (byteArrayOutputStream.toByteArray().length / 1024f>300f) {
			                        quality=quality-4;// 每次都减少4
			                        byteArrayOutputStream.reset();// 重置baos即清空baos
			                       if(quality<=0){
			                                     break;
			                      }
			            }
			            //compress to 300kb
			            bm.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);*/
						byte[] byteArray = byteArrayOutputStream .toByteArray();
						String encoded = Base64.encodeToString(byteArray, Base64.URL_SAFE|Base64.NO_PADDING|Base64.NO_WRAP);
						//base64ImageString
					  //	url.append("&base64ImageString=").append(encoded);
						Log.i(TAG,"url="+url);
						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
						nameValuePairs.add(new BasicNameValuePair("base64ImageString",encoded));
    	     
	    				HttpPost httppost = new HttpPost(url.toString().replaceAll(" ", "%20"));
		  	    	  	HttpConnectionParams.setConnectionTimeout(httppost.getParams(), 10000); // timeout 10 secs
		  	    	  	HttpConnectionParams.setSoTimeout(httppost.getParams(), 10000); // timeout 10 secs
		  	    	  	httppost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

			    	    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		  	          	ResponseHandler<String> responseHandler=new BasicResponseHandler();
		  	          	result = httpclient.execute(httppost,responseHandler).trim();
		  	          	Log.i(TAG,"result="+result);
			  	          HttpMessage msg = new HttpMessage();
			  	          if(msg.parseMessage(result)) {
			  	          		if(msg.getReturnCode().equals(Constant.SUCCESS)) {
				  	          		openDB();
							    	SQLiteDatabase db = DH.getWritableDatabase();
							    	ContentValues values = new ContentValues();
									values.put("_isUpolad", "y");
									db.update("MyPict", values, "_id" + "=" + uploadObject.PHFL_PHOTOFILE_ID, null);
									closeDB();
									upLoadFailList.remove(uploadObject);
		  	          		}
		  	          	}
			  	        Thread.sleep(1000) ;
	    	    	}    	
	    	    	/*if(upLoadFailList.size()==0) {
	    	    		result = getResources().getString(R.string.Message18);
	    	    	} else {
	    	    		result = getResources().getString(R.string.Message17);    	    		
	    	    	}*/
	    	    	
	    	    	  httpclient.getConnectionManager().shutdown();
	    	    	  mHamdler.post(new Runnable(){
							@Override
							public void run() {
								// TODO Auto-generated method stub
								isUpload = false;
								 MainMenuFragment mmf = UploadActivty.newInstance();
					  				myActivity.showMainMenuFragment(mmf);
					  				synchronized(myActivity) {
					  					myActivity.setNeedBackup(true);
					  					myActivity.notify();
					  					}
							}});
	    	    } 	catch (FileNotFoundException e) {
	  	      		e.printStackTrace();
	  	      		result = e.fillInStackTrace().toString();
	    	    } catch (ClientProtocolException e) {
	    	        Log.e(TAG,e.fillInStackTrace().toString());
	    	        result = e.fillInStackTrace().toString();
	    	    } catch (IOException e) {
	    	        Log.e(TAG,e.fillInStackTrace().toString());
	    	        result = e.fillInStackTrace().toString();
	    	    } catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	    return result;
			}
			
			private Bitmap getBitmapFromFile(String photoPath) {
				// TODO Auto-generated method stub
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inPreferredConfig = Bitmap.Config.RGB_565;
				Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);
				//return bitmap;
				return comp(bitmap);
			}
			
			private Bitmap comp(Bitmap image) {  
			      
			    ByteArrayOutputStream baos = new ByteArrayOutputStream();         
			    image.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
			    if( baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出    
			        baos.reset();//重置baos即清空baos  
			        image.compress(Bitmap.CompressFormat.JPEG, 80, baos);//这里压缩50%，把压缩后的数据存放到baos中  
			    }  
			    ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());  
			    BitmapFactory.Options newOpts = new BitmapFactory.Options();  
			    //开始读入图片，此时把options.inJustDecodeBounds 设回true了  
			    newOpts.inJustDecodeBounds = true;  
			    Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
			    newOpts.inJustDecodeBounds = false;  
			    int w = newOpts.outWidth;  
			    int h = newOpts.outHeight;  
			    //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为  
			    float hh = 800f;//这里设置高度为800f  
			    float ww = 480f;//这里设置宽度为480f  
			    //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
			    int be = 1;//be=1表示不缩放  
			    if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放  
			        be = (int) (newOpts.outWidth / ww);  
			    } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放  
			        be = (int) (newOpts.outHeight / hh);  
			    }  
			    if (be <= 0)  
			        be = 1;  
			    newOpts.inSampleSize = be;//设置缩放比例  
			    //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
			    isBm = new ByteArrayInputStream(baos.toByteArray());  
			    bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
			    //return compressImage(bitmap);//压缩好比例大小后再进行质量压缩  
			    return bitmap;
			}  
			private Bitmap compressImage(Bitmap image) {  
				  
		        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
		        int options = 100;  
		        while ( baos.toByteArray().length / 1024>100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩         
		            baos.reset();//重置baos即清空baos  
		            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
		            options -= 10;//每次都减少10  
		        }  
		        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
		        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
		        return bitmap;  
		    } 
			@Override
	    	protected void onPostExecute(String result) {
				if(isAdded()){
			        getResources().getString(R.string.UploadPhotoFile);
			    }
				Toast.makeText(myActivity,result,Toast.LENGTH_LONG).show();			
				Log.i(TAG,"ImageUploadTask finished");
	    	}
		
	}

    class ProcessCompany extends AsyncTask<String, Integer, String> {

    	String ip;
    	String httpPort;
    	String siteName;
		@Override
		protected String doInBackground(String... params) 
		{
			Log.d(TAG,"ProcessCompany Start");
			ip = params[0];
			httpPort = params[1];
			siteName = params[2];
    		String result = null;
    		String url = "http://"+ip+":"+httpPort+getResources().getString(R.string.ProcessCompany)+"?siteName="+myActivity.DEVICEID+"&userNo="+myActivity.omCurrentAccount.getAccountNo()+"?SysID="+myActivity.omCurrentSysProfile.getCompanyNo();
    		HttpClient httpclient = new DefaultHttpClient();	
    	    try {
    	    	Log.d(TAG, "connectiong = " + url);
    	    	result = HttpUtil.httpConnect(url);
    			if (result.startsWith("\ufeff")) {
    				result = result.substring(1);
					}
    	        Log.d(TAG,"result = " + result);
    	        httpclient.getConnectionManager().shutdown();
    	    }
    	    catch (Exception e)
    	    {
    	    	result="false";
    	    }
    	    Log.d(TAG,"ProcessCompany End");
    	    return result;
    	}
		
		protected void onPostExecute(String result) 
		{
			  Log.d(TAG,"ProcessCompany onPostExecute Start");
			try
			{
				if(result.equals("false"))
				{
	    			//String msg = getResources().getString(R.string.Message28);
	    			//downLoadMessage.setText(msg);
	    			return;	
				}else{
					
					JSONObject jsonObject =new JSONObject(result);
					String errorFlag = (String)jsonObject.get("tabletErrorFlag");
					if(errorFlag.equals("true"))
					{
						String msg = getResources().getString(R.string.	Message43);
						msg+=DEVICEID;
						AlertDialog dialog = ErroDialog(getResources().getString(R.string.takeorderMessage1a),msg);
    					dialog.show();	 
					}else
					{
						Log.d(TAG,"Start to Upload");
						new ImageUploadTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
					}


					
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		
			  Log.d(TAG,"ProcessCompany onPostExecute End  result="+result+"]");
		}
		
	 	protected AlertDialog ErroDialog(String title, String message) {
	    	Builder builder = new AlertDialog.Builder(myActivity);
	    	builder.setTitle(title);
	    	builder.setMessage(message);
	    	final String msgY = myActivity.getString(R.string.Button1);
	    	builder.setPositiveButton(msgY, new DialogInterface.OnClickListener() {
	    		@Override
	    		public void onClick(DialogInterface dialog, int which) {
	    			MainMenuFragment mmf = QueryNotUploadFragment.newInstance();
	    			myActivity.showMainMenuFragment(mmf);
	    		}
	    	});
	    	return builder.create();
	    }
	}
}
