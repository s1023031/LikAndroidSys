package com.lik.android.main;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lik.Constant;
import com.lik.android.main.R;
import com.lik.util.ExternalStorage;

public class PictPage extends MainMenuFragment  implements LocationListener{
	View view;
	PpItemObject pi;
	String tno;
	TextView back,detail;
	TextView et1;
	ListView ls;
	MyAdapter mba;
	RelativeLayout cameraBtn/*,vBut*/;
	Handler mHamdler = new Handler();
	private File imageRef;
	protected final SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd",Locale.CHINESE);
	protected final SimpleDateFormat sdfDate1 = new SimpleDateFormat("yyyy/MM/dd",Locale.CHINESE);
	protected final SimpleDateFormat sdfDate2 = new SimpleDateFormat("yyyy/MM/dd",Locale.CHINESE);
	protected final SimpleDateFormat sdfDate3 = new SimpleDateFormat("MM/dd/yyyy",Locale.CHINESE);
	protected final SimpleDateFormat sdfDate4 = new SimpleDateFormat("dd/MM/yyyy",Locale.CHINESE);
	protected Calendar cal = Calendar.getInstance(Locale.CHINESE);
	private static final int TAKEPIC_REQUEST_CODE = 1;
	private String listClickFlag=null;
	private int listClickFlagID=-1;
	
	private Dialog dialog;
	
	private DBHelper DH = null;
	//PhotoProjectStateItem
	ArrayList<PhotoProjectStateItem> ppsList = new ArrayList<PhotoProjectStateItem>();
	
	
	//GPS
	private LocationManager lms;
	private boolean getService = false;
	private Boolean isLocat = false;
	private Double lon=0.0,lat=0.0,cLon=0.0,cLat=0.0;
	private String bestProvider = LocationManager.GPS_PROVIDER;
	private TextView local;
	private boolean isIn500M = false;
	private boolean isGetAddress = false;
	String address="";
	int selectPoint = -1;
	String companyDateFormat  = "";
	private void openDB(){
    	DH = new DBHelper(myActivity.getApplicationContext());  
    }
    private void closeDB(){
    	DH.close();    	
    }
	
	public PictPage(PpItemObject pi){
		this.pi=pi;
	}
	
	public static MainMenuFragment newInstance(PpItemObject pi) {
        Log.d(TAG, "in PictPage newInstance()");

        PictPage mf = new PictPage(pi);

        // Supply index input as an argument.
		Bundle args = new Bundle();
		//.args.putInt("index", index);
		mf.setArguments(args);
		return mf;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater,container,savedInstanceState);
		view = inflater.inflate(R.layout.main_pict_page, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG,"onActivityCreated start!");
		tno = System.currentTimeMillis()+"";
		companyDateFormat = MainMenuActivity.companyDateFormat;
		setView();
		//testDataSet();
		getTakePhotoState();
		getLastDate();
		getGPS();
		getAddress();
	}

	private void getAddress() {
		// TODO Auto-generated method stub
		//
		SQLiteDatabase db = DBAdapter.getReadableDatabase();
		Cursor c=db.query(false,
				"Orders , Customers_"+myActivity.omCurrentSysProfile.getCompanyNo()+myActivity.currentDept.getCompanyID(),				//��ƪ�W��
				new String[] {"Customers_"+myActivity.omCurrentSysProfile.getCompanyNo()+myActivity.currentDept.getCompanyID()+".Address"},	//���W��
				"Customers_"+myActivity.omCurrentSysProfile.getCompanyNo()+myActivity.currentDept.getCompanyID()+".CustomerNO = Orders.CustomerNO AND Orders.OrderID = '"+pi.OrderID+"';",				//WHERE
				null, // WHERE ���Ѽ�
				null, // GROUP BY
				null, // HAVING
				null, // ORDOR BY
				null  // ����^�Ǫ�rows�ƶq
				);
		
		if (c.getCount()>0) {
			c.moveToFirst();
			address = c.getString(0);
		}
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				double[]lonlat = getLocationInfo(address);
				if(lonlat!=null)
				{
					cLon=lonlat[0];
			        cLat=lonlat[1];
			        isGetAddress=true;
			        mHamdler.post(new Runnable(){

						@Override
						public void run() {
							// TODO Auto-generated method stub
							check500m();
						}
			        	
			        }); 
				}
				else
					return;
		
			}
		}.start();
		
	}
	private void getGPS() {
		// TODO Auto-generated method stub
		
		lms = (LocationManager) myActivity.getSystemService(Context.LOCATION_SERVICE);
		if (lms.isProviderEnabled(LocationManager.GPS_PROVIDER) || lms.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			//如果GPS或網路定位開啟，呼叫locationServiceInitial()更新位置
			getService = true;
			locationServiceInitial();
		} else {
			Toast.makeText(myActivity, "請開啟定位服務", Toast.LENGTH_LONG).show();
			startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));	//開啟設定頁面
		}
	}
	private void locationServiceInitial() {
		// TODO Auto-generated method stub
		
		lms = (LocationManager) myActivity.getSystemService(Context.LOCATION_SERVICE);	//取得系統定位服務
		Criteria criteria = new Criteria();	//資訊提供者選取標準
		bestProvider = lms.getBestProvider(criteria, true);	//選擇精準度最高的提供者
		Location location = lms.getLastKnownLocation(bestProvider);
		
		getLocation(location);
		
	}
	private void getLocation(Location location) {
		// TODO Auto-generated method stub
		if(location != null) {
			isLocat = true;
 
			lon = location.getLongitude();	//取得經度
			lat = location.getLatitude();	//取得緯度
			local.setText("Lon:"+lon+" Lat:"+lat);
			if(isGetAddress){
				check500m();
			}
		}
		else {
			isLocat = false;
			lon = 0.0;	//取得經度
			lat = 0.0;	//取得緯度
			local.setText("Lon:"+lon+" Lat:"+lat);
			Toast.makeText(myActivity, "無法定位座標", Toast.LENGTH_LONG).show();
		}
	}
	private void getLastDate() {
		// TODO Auto-generated method stub
		for(int i=0;i<ppsList.size();i++){
			openDB();
			SQLiteDatabase db = DH.getReadableDatabase();
			Cursor c=db.query(false,
					"MyPict",				//��ƪ�W��
					new String[] {"_LastDate"},	//���W��
					"_Sflag='"+ppsList.get(i).topic+"' AND _Sno ='"+pi.no+"';",				//WHERE
					null, // WHERE ���Ѽ�
					null, // GROUP BY
					null, // HAVING
					null, // ORDOR BY
					null  // ����^�Ǫ�rows�ƶq
					);
			if (c.getCount()>0) {
				c.moveToLast();
				ppsList.get(i).lastDate=c.getString(0);
			}
			closeDB();
			mba.addListItem(new PPListItem(ppsList.get(i).topic,ppsList.get(i).lastDate,ppsList.get(i).stateNO));
		}
		
		//
		
	}
	private void getTakePhotoState() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = DBAdapter.getReadableDatabase();
		String tableName="PhotoProjectState_"+myActivity.omCurrentSysProfile.getCompanyNo()+myActivity.currentDept.getCompanyID();
		Cursor c2=db.query(false,
				"sqlite_master",				//��ƪ�W��
				new String[] {"name"},	//���W��
				"type='table' AND name='"+tableName+"' ;",				//WHERE
				null, // WHERE
				null, // GROUP BY
				null, // HAVING
				null, // ORDOR BY
				null  
				);
		if(c2.getCount()==1){
			Log.d(TAG,"have table");
				//Toast.makeText(myActivity, "1111  "+c2.getCount(), Toast.LENGTH_SHORT).show();
				
				Cursor c=db.query(true,
						tableName,				
						new String[] {"StateName","StateID","Kind"},	
						"ProjectID="+pi.takePhotoID,		//DateTo <='"+strDate+"';		//WHERE
						null, // WHERE ���Ѽ�
						null, // GROUP BY
						null, // HAVING
						null, // ORDOR BY
						null  // ����^�Ǫ�rows�ƶq
						);
				Log.d(TAG,"have data="+c.getCount());
				if (c.getCount()>0) {
					c.moveToFirst();
						ppsList.add(new PhotoProjectStateItem(c.getString(0),"no",String.valueOf(c.getInt(2))));
					while(c.moveToNext()){
						ppsList.add(new PhotoProjectStateItem(c.getString(0),"no",String.valueOf(c.getInt(2))));
					}
				}
			}else{
				//Toast.makeText(myActivity, "2222  "+c2.getCount(), Toast.LENGTH_SHORT).show();
			}
	}

	private void setView() {
		// TODO Auto-generated method stub
		local = (TextView)view.findViewById(R.id.mp_local_tv);
		local.setText("定位中...");
		et1 = (TextView)view.findViewById(R.id.mp_page_et);
		/*et1.setClickable(false);
		et1.setFocusable(false);*/
		//et1.setHint("定位中...");
		et1.setText(pi.remark);
		//et1.setText("這是強森最近的連續第六個前十之作，包括美國公開賽和WGC - Bridgestone Invitational（世界邀請賽）兩座冠軍，總計共進帳235.05分，平均每場比賽獲得39.175分，也讓他的世界排名從第八變成第二，從原本落後Jason Day（傑森•戴伊）6.7936縮小至1.146分，本週有機會在PGA錦標賽取而代之。這是強森最近的連續第六個前十之作，包括美國公開賽和WGC - Bridgestone Invitational（世界邀請賽）兩座冠軍，總計共進帳235.05分，平均每場比賽獲得39.175分，也讓他的世界排名從第八變成第二，從原本落後Jason Day（傑森•戴伊）6.7936縮小至1.146分，本週有機會在PGA錦標賽取而代之。");
		
		
		ls = (ListView)view.findViewById(R.id.mp_page_ls);
		mba = new MyAdapter(myActivity);
		ls.setAdapter(mba);

		back = (TextView)view.findViewById(R.id.mp_page_back);
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
		detail.setText("店家名稱 : "+pi.shopName+"\n" +"活動名稱 : "+pi.title);
		
		//call Camera Function
		cameraBtn = (RelativeLayout)view.findViewById(R.id.mp_page_pict);
		cameraBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				try {
					if(listClickFlag!=null){
						if(isLocat){
							if(!isIn500M){
								/*if(et1.getText().toString().length()==0){
									Toast.makeText(myActivity, "請輸入描述！", Toast.LENGTH_LONG).show();
								}else{*/
									Intent intent_camera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
									imageRef = createImageFile();
									Uri newImageUri =  Uri.parse("file://"+imageRef.getPath());  //解決中文亂碼
									intent_camera.putExtra(MediaStore.EXTRA_OUTPUT, newImageUri);	
								    startActivityForResult(intent_camera, TAKEPIC_REQUEST_CODE);
								//}
							}else{
								Intent intent_camera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
								imageRef = createImageFile();
								Uri newImageUri =  Uri.parse("file://"+imageRef.getPath());  //解決中文亂碼
								intent_camera.putExtra(MediaStore.EXTRA_OUTPUT, newImageUri);	
							    startActivityForResult(intent_camera, TAKEPIC_REQUEST_CODE);
							}  
						}else{
							Toast.makeText(myActivity, "尚未定位完成!", Toast.LENGTH_LONG).show();
							lon=0.0;
							lat=0.0;
							Intent intent_camera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
							imageRef = createImageFile();
							Uri newImageUri =  Uri.parse("file://"+imageRef.getPath());  //解決中文亂碼
							intent_camera.putExtra(MediaStore.EXTRA_OUTPUT, newImageUri);
							//intent_camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageRef));
						    startActivityForResult(intent_camera, TAKEPIC_REQUEST_CODE);
						}
					}else{
						Toast.makeText(myActivity, "請選擇情境。", Toast.LENGTH_LONG).show();
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});

		et1.addTextChangedListener(new TextWatcher(){

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,int count) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				mba.setItemDetail(listClickFlag, et1.getText().toString());
			}
			
		});
	}
	

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG,"Camera onActivityResult start");
	    super.onActivityResult(requestCode, resultCode, data);
	    if(requestCode == TAKEPIC_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
	      	// add water mark to image
	    	if(imageRef!=null) {
	    		Log.d(TAG,"imageRef path="+imageRef.getAbsolutePath());
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inPurgeable = true;
				options.inSampleSize = 1;
				Bitmap bitmap = BitmapFactory.decodeFile(imageRef.getAbsolutePath(),options);
				//String watermark = viewQNU.getShortName()+"/"+Constant.sqliteDFT.format(new Date())+(prj==null?"":"/"+prj.getProjectName().replaceAll("[\\/*:?\"<>|\n\r\t]", "").trim());
				//String watermark = "WaterMarkTesting";
				String watermark = pi.title+"/"+pi.shopName+"/"+myActivity.omCurrentAccount.getAccountName()+"/"+Constant.sqliteDFT.format(new Date());
				Log.d(TAG,"waterMark="+watermark);
				Bitmap wb = mark(bitmap,watermark);
				FileOutputStream out = null;
				try {
					File newFile = new File(imageRef.getAbsolutePath().replaceFirst(".jpg", "-1.jpg"));
				    out = new FileOutputStream(newFile);
				  //  ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				    wb.compress(Bitmap.CompressFormat.JPEG, 30, out);
					/*   // compress to 300kb
						int quality=100;
			            while (byteArrayOutputStream.toByteArray().length / 1024f>300f) {
			                        quality=quality-4;// 每次都减少4
			                        byteArrayOutputStream.reset();// 重置baos即清空baos
			                       if(quality<=0){
			                                     break;
			                      }
			            }
			            //compress to 300kb
			            wb.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
			            out.write(byteArrayOutputStream.toByteArray());*/
				    
				    if(imageRef.delete()) {
				    	newFile.renameTo(imageRef);
				    }
				    String date =getDate();
				    String dateTime = getDateTime();
				    openDB();
				    SQLiteDatabase db = DH.getWritableDatabase();
			        ContentValues values = new ContentValues();
			        values.put("_Sno", pi.no);
			        values.put("_Pid", pi.takePhotoID);
			        values.put("_Pno", pi.projectNO);
			        values.put("_CustomerID", pi.customerID);
			        values.put("_CompanyID", myActivity.currentDept.getCompanyID()+"");
			        values.put("_SalesID", myActivity.omCurrentAccount.getAccountNo()+"");
			        values.put("_FileName", imageRef.getName());
			        values.put("_Dir", imageRef.getPath());
			        values.put("_Tno",tno );
			        values.put("_Sflag", listClickFlag+"");
			        values.put("_SflagID", ppsList.get(listClickFlagID).stateNO);
			        values.put("_LastDate",date );
			        values.put("_DateTime",dateTime );
			       // values.put("_Detail", et1.getText().toString());
			        values.put("_Lon", ""+lon);
			        values.put("_Lat", ""+lat);
			        values.put("_isUpolad", "n");
			        values.put("_CompanyParent",myActivity.companyParent);
			        
			        if(!isIn500M){
			        	values.put("_PHFL_POSITION_DIFFERENCE",  "與店家超過500公尺");
			        }else{
			        	
			        	values.put("_PHFL_POSITION_DIFFERENCE", "");
			        }
			        db.insert("MyPict", null, values);
				    closeDB();
				    
				    Toast.makeText(myActivity, "照片與說明已儲存!", Toast.LENGTH_LONG).show();
				    MainMenuFragment mmf = PictPage.newInstance(pi);
					myActivity.showMainMenuFragment(mmf);
					synchronized(myActivity) {
						myActivity.setNeedBackup(true);
						myActivity.notify();
					}
				} catch (Exception e) {
				    e.printStackTrace();
				} finally {
				    try {
				        if (out != null) {
				            out.close();
				        }
				        if(bitmap!=null) bitmap.recycle();
				        if(wb!=null) wb.recycle();
				    } catch (IOException e) {
				        e.printStackTrace();
				    }
				}	
	    	}
	    }else
	    {
	    	Log.d(TAG,"Camera onActivityResult error");
	    }
	    
	    Log.d(TAG,"Camera onActivityResult end");
	}
	public String getDate(){
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String strDate = sdFormat.format(date);
		System.out.println(strDate);
		return strDate;
		}
	
	public String getDateTime(){
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String strDate = sdFormat.format(date);
		System.out.println(strDate);
		return strDate;
		}
	private File createImageFile() throws IOException {
		Log.d(TAG,"createImageFile start");
		Date current = MainMenuActivity.getCurrentDate();
		cal.setTime(current);
		Map<String, File> externalLocations = ExternalStorage.getAllStorageLocations();
		File externalSdCard = externalLocations.get(ExternalStorage.EXTERNAL_SD_CARD);
		StringBuffer sbDisplayDir = new StringBuffer();
		int month = cal.get(Calendar.MONTH)+1;
		// dir yyyyMMdd/月份/專案名稱/sales name/客戶簡稱
		//dir yyyyMMdd/月份/專案名稱/sales no/客戶簡稱 2013/7/15 sales name改no
		// dir yyyyMMdd/月份/專案名稱/sales no/客戶簡稱 2014/9/18 改sales name(no)
		// dir yyyyMMdd/月份/公司代號
		if(externalSdCard!=null) 
			sbDisplayDir.append(externalSdCard);
		else 
			sbDisplayDir.append(Environment.getExternalStorageDirectory());
		sbDisplayDir.append(myActivity.getString(R.string.DisplayDir2));
		sbDisplayDir.append(sdfDate.format(current));
		sbDisplayDir.append("/").append(month); 
		sbDisplayDir.append("/").append(myActivity.currentDept.getCompanyID());
	//	sbDisplayDir.append("/").append(view.getProjectName().replaceAll("[\\/*:?\"<>|\n\r\t]", "").trim());// 2014/01/20 by mou
		//sbDisplayDir.append("/").append(getSalesDir());
		//sbDisplayDir.append("/").append(viewQNU.getShortName().replaceAll("[\\/*:?\"<>|\n\r\t]", "").trim());
		File dir = new File(sbDisplayDir.toString());
		Log.d(TAG,"dir.exists()="+dir.exists());
		Log.d(TAG,"dir="+sbDisplayDir);
		if(!dir.exists()) 
			dir.mkdirs();
		
		// file name：專案名稱-客戶簡稱-userNo-hhmmss+.jpg 
		Date date = MainMenuActivity.getCurrentDate();
		StringBuffer sbFileName = new StringBuffer();
		//sbFileName.append(view.getProjectName().replaceAll("[\\/*:?\"<>|\n\r\t]", "").trim()).append("-");//projectName
		//sbFileName.append(viewQNU.getShortName().replaceAll("[\\/*:?\"<>|\n\r\t]", "")).append("-");//CustomerNamu
		sbFileName.append(pi.shopName).append("-");
		sbFileName.append(pi.title).append("-");
		sbFileName.append(myActivity.omCurrentAccount.getAccountNo()).append("-");
		sbFileName.append(Constant.sdf2t.format(date));
		//sbFileName.append("test");
		sbFileName.append(".jpg");
       // save into file
		Log.d(TAG,"FileName="+sbFileName+" sbFileName.toString()="+sbFileName.toString());
		File a = new File(sbDisplayDir.toString(),sbFileName.toString());
		Log.d(TAG,"createImageFile end="+a.toString());
		return new File(sbDisplayDir.toString(),sbFileName.toString());
	}
	
	private static Bitmap mark(Bitmap src, String watermark) {
		int w = src.getWidth();
		int h = src.getHeight();
		Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);

		Canvas canvas = new Canvas(result);
		canvas.drawBitmap(src, 0, 0, null);
		//canvas.drawColor(Color.RED);  
	
		Paint paint = new Paint();
	    //paint.setARGB(255, 0, 0, 0); 
		//paint.setARGB(100, 255, 0, 0);
		paint.setColor(Color.WHITE);
		paint.setTextSize(80);
		paint.setAntiAlias(true);
		paint.setUnderlineText(false);
		/*
		paint.setTextAlign(Paint.Align.LEFT);
		canvas.drawText(watermark, 0, 0, paint); // 左上角
		*/
		
		/*paint.setTextAlign(Paint.Align.CENTER);
			canvas.drawText(watermark, w/2, h/2, paint); // 中間
		 */		 	
		
		Rect bounds = new Rect();
		paint.setTextAlign(Paint.Align.RIGHT);
		paint.getTextBounds(watermark, 0, watermark.length(), bounds);
		canvas.drawText(watermark, w, h-bounds.height(), paint); // 右下角
		
		return result;
	}
	
	public class MyAdapter extends BaseAdapter{    
	    
	    private LayoutInflater myInflater;
	   ArrayList<PPListItem> list = new ArrayList<PPListItem>();
	  
	   
	   private class View_TalkLayout{

		   TextView name,lastPictDate; 
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
	    public PPListItem getItem(int position)
	    {
	        return list.get(position);
	    }
	  
	    @Override
	    public long getItemId(int position)
	    {
	        return position;
	    }
	    
	   public ArrayList<PPListItem> getAllList(){
		   return list;
	   }
	    
	   
	    public void addListItem(PPListItem o){
	    	list.add(o);
	    	this.notifyDataSetChanged();
	    }
	  
	    public void setItemDetail(String s,String detail){
	    	int p=0;
	    	for(int i=0;i<list.size();i++){
	    		if(list.get(i).topic.equals(s))
	    			p=i;
	    	}
	    	list.get(p).setDetail(detail);
	    	this.notifyDataSetChanged();
	    }
	    
	    public void setItemLastTime(int p,String lastTime){
	    	list.get(p).lastPictDate=lastTime;
	    	this.notifyDataSetChanged();
	    }
	    
	    @Override
	    public View getView(final int position,View convertView,ViewGroup parent)
	    {
	    	View_TalkLayout view_TalkLayout ;
	        
	        if(convertView == null){

	        	convertView = myInflater.inflate(R.layout.pict_page_list_item, null);
	        	view_TalkLayout = new View_TalkLayout();

	        	view_TalkLayout.name=(TextView)convertView.findViewById(R.id.ppli_tv1);
	        	view_TalkLayout.lastPictDate=(TextView)convertView.findViewById(R.id.ppli_tv2);
	        	view_TalkLayout.rl = (RelativeLayout)convertView.findViewById(R.id.ppli_back);
	        	convertView.setTag(view_TalkLayout);
	        }else{
	        	view_TalkLayout = (View_TalkLayout)convertView.getTag();
	        }
	      
	        view_TalkLayout.name.setText(list.get(position).topic);
	        
	        if(list.get(position).lastPictDate.equals("no"))
	        {
	        	view_TalkLayout.lastPictDate.setText("無");
	        }else
	        {        	
	        	 if(companyDateFormat.equals("1"))
	 	        {
	 	        	Calendar cal = Calendar.getInstance();
	 	        	Log.d(TAG,"date= "+  	list.get(position).lastPictDate);
	 	        	cal.setTime(new Date(list.get(position).lastPictDate));
	 	        	Log.d(TAG,"month= "+  	cal.get(Calendar.MONTH));
	 	        	Log.d(TAG,"date= "+ cal.get(Calendar.YEAR));
	 	        	int rocYear = cal.get(Calendar.YEAR)-1911;
	 	        	int month =cal.get(Calendar.MONTH)+1;
	 	        	Log.d(TAG,"month123= "+  	month);
	 	        	String rocDate = "民國 " + String.valueOf(rocYear +"年 "+ month + " 月 " + cal.get(Calendar.DAY_OF_MONTH) +" 日");
	 	        	view_TalkLayout.lastPictDate.setText(rocDate);
	 	        }
	 	        else if (companyDateFormat.equals("2"))
	 	        {
	 	        	view_TalkLayout.lastPictDate.setText(sdfDate2.format(list.get(position).lastPictDate));
	 	        }  	
	 	        else if(companyDateFormat.equals("3"))
	 	        {
	 	        	view_TalkLayout.lastPictDate.setText(sdfDate3.format(list.get(position).lastPictDate));
	 	        }
	 	        else if(companyDateFormat.equals("4"))
	 	        {
	 	        	view_TalkLayout.lastPictDate.setText(sdfDate4.format(list.get(position).lastPictDate));
	 	        }
	        }
	       
	        
        	if(selectPoint!=-1){
        		if(selectPoint==position){
        				view_TalkLayout.rl.setBackgroundColor(Color.parseColor("#FCD4D4"));
        		}else{
        			if(list.get(position).kind.equals("1"))
            			view_TalkLayout.rl.setBackgroundColor(Color.YELLOW);
            		else if (list.get(position).kind.equals("2"))
            			view_TalkLayout.rl.setBackgroundColor(Color.parseColor("#87CEFA")); // light blue
            		else
            			view_TalkLayout.rl.setBackgroundColor(Color.parseColor("#FFFFFF"));
        		}
        	}else{
        		if(list.get(position).kind.equals("1"))
        			view_TalkLayout.rl.setBackgroundColor(Color.YELLOW);
        		else if (list.get(position).kind.equals("2"))
        			view_TalkLayout.rl.setBackgroundColor(Color.parseColor("#87CEFA")); // light blue
        		else
        			view_TalkLayout.rl.setBackgroundColor(Color.parseColor("#FFFFFF"));
        	}
        	view_TalkLayout.rl.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					listClickFlag = list.get(position).topic;
					listClickFlagID = position;
					selectPoint=position;
					mba.notifyDataSetChanged();
				}});
 
	        return convertView;
	    }
	}
	
	class PhotoProjectStateItem{
		String topic;
		String lastDate;
		String stateNO;
		public PhotoProjectStateItem(String topic,String lastDate,String stateNO){
			this.topic=topic;
			this.lastDate=lastDate;
			this.stateNO=stateNO;
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		getLocation(location);
		
	}
	private void check500m() {
		// TODO Auto-generated method stub
		//isIn500M
		double mm= getDistance(cLat,cLon,lat,lon);
		//
		//double mm= getDistance(25.136979,121.777691,lat,lon);
		if(mm >=500.0){
			/*if(isIn500M==true){
				et1.setText("");
			}*/
			isIn500M=false;
			//et1.setEnabled(true);
			//et1.setHint("與店家超過500公尺(距離"+mm+"公尺)，請輸入原因!");
			local.setText("拍照地點和商家位置有誤(與店家超過500公尺)");
			
		}/*else if(mm <500.0){
			if(isIn500M==false){
				et1.setText("");
			}
			isIn500M=true;
			et1.setEnabled(false);
			et1.setHint("與店家在500公尺內(距離"+mm+"公尺)，不需輸入原因。");
			local.setText("拍照地點和商家位置正確");
		}*/
		
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(getService) {
			lms.requestLocationUpdates(bestProvider, 5000, 1, this);
			//服務提供者、更新頻率60000毫秒=1分鐘、最短距離、地點改變時呼叫物件
		}
		
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(getService) {
			lms.removeUpdates(this);	//離開頁面時停止更新
		}
	}
	
	public static double[] getLocationInfo(String address)  
    {  
        // 定义一个HttpClient，用于向指定地址发送请求  
        HttpClient client = new DefaultHttpClient();  
        // 向指定地址发送GET请求  
        HttpGet httpGet = new HttpGet("http://maps.google.com/maps/api/geocode/json?address=" + address);  
        StringBuilder sb = new StringBuilder();  
        try  
        {  
            // 获取服务器的响应  
            HttpResponse response = client.execute(httpGet);  
            HttpEntity entity = response.getEntity();  
            // 获取服务器响应的输入流  
            InputStream stream = entity.getContent();  
            int b;  
            // 循环读取服务器响应  
            while ((b = stream.read()) != -1)  
            {  
                sb.append((char) b);  
            }  
            // 将服务器返回的字符串转换为JSONObject对象  
            JSONObject jsonObject = new JSONObject(sb.toString());  
            // 从JSONObject对象中取出代表位置的location属性  
            JSONObject location = jsonObject.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");  
            // 获取经度信息  
            double longitude = location.getDouble("lng");  
            // 获取纬度信息  
            double latitude = location.getDouble("lat");  
            // 将经度、纬度信息组成double[]数组  
            return new double[]{longitude , latitude};            
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
        return null;  
    } 
	
	
	public double getDistance(double lat1, double lon1, double lat2, double lon2) {  
	    float[] results=new float[1];  
	    Location.distanceBetween(lat1, lon1, lat2, lon2, results);  
	    return results[0];  
	}  
	
	public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;

	    if (height > reqHeight || width > reqWidth) {
	             final int heightRatio = Math.round((float) height/ (float) reqHeight);
	             final int widthRatio = Math.round((float) width / (float) reqWidth);
	             inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
	    }
	        return inSampleSize;
	}
}
