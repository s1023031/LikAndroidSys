package com.lik.android.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

import com.lik.android.main.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;




import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CheckPage extends MainMenuFragment{
	View view;
	PpItemObject pi;
	TextView back,detail;
	EditText et;
	GridView ls;
	MyAdapter mba;
	RelativeLayout saveBut,vBut;
	private DBHelper DH = null;
	private int pictPoint=-1,pictListPoint=-1;
	private Integer  doubleCilckFlag=new Integer(0);
	private Spinner spinner;
	private ArrayAdapter<String> lunchList;
	private CheckPagePictItem selectPict;
	ArrayList<String> als = new ArrayList<String>(); 
	//ArrayList<CPPictList> cpplList = new ArrayList<CPPictList>(); 
	
	private DisplayImageOptions options;
	private ImageLoader imageLoader;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	
	private Dialog dialog;
	PhotoViewAttacher mAttacher;
	private void openDB(){
    	DH = new DBHelper(myActivity.getApplicationContext());  
    }
    private void closeDB(){
    	DH.close();    	
    }
	
	public CheckPage(PpItemObject pi){
		this.pi=pi;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater,container,savedInstanceState);
		view = inflater.inflate(R.layout.main_check_page, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG,"CheckPage onActivityCreated start!");
		setView();
		initImageLoder();
		getData();
		
	}

	private void getData() {
		// TODO Auto-generated method stub
		mba.removeAll();
		openDB();
		SQLiteDatabase db = DH.getReadableDatabase();
		Cursor c=db.query(false,
				"MyPict",				//��ƪ�W��
				new String[] {"_id","_Sno","_FileName","_Dir","_Tno","_Detail","_Sflag"},	//���W��
				"_Sno='"+pi.no+"' and _Pid='"+pi.takePhotoID+"' and _CompanyParent='"+myActivity.companyParent+"';",				//WHERE
				null, // WHERE ���Ѽ�
				null, // GROUP BY
				null, // HAVING
				null, // ORDOR BY
				null  // ����^�Ǫ�rows�ƶq
				);
		
		
		if (c.getCount()>0) {
			c.moveToFirst();
			//Toast.makeText(myActivity, ""+c.getString(0), Toast.LENGTH_LONG).show();
			Log.d(TAG,"Dir= "+c.getString(3));
			mba.addListItem(new CheckPagePictItem(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6))); 
			while(c.moveToNext()){
				Log.d(TAG,"Dir= "+c.getString(3));
				//Toast.makeText(myActivity, ""+c.getString(0), Toast.LENGTH_LONG).show();
				mba.addListItem(new CheckPagePictItem(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6)));
			}
		}else
		{
			Log.d(TAG,"no data");
		}
		closeDB();
	}

	
	private void setView() {
		// TODO Auto-generated method stub
		//mcp_page_word
		
		
		saveBut = (RelativeLayout)view.findViewById(R.id.mcp_page_word);
		spinner = (Spinner)view.findViewById(R.id.mcp_spinner);
		
		/**/
		// TODO Auto-generated method stub
				SQLiteDatabase db = DBAdapter.getReadableDatabase();
				String tableName="PhotoProjectState_"+MainMenuActivity.omCurrentSysProfile.getCompanyNo()+myActivity.currentDept.getCompanyID();
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
								new String[] {"StateName"},	
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
								//Toast.makeText(myActivity, ""+c.getString(0), Toast.LENGTH_SHORT).show();
								als.add(c.getString(0));
							while(c.moveToNext()){
								//Toast.makeText(myActivity, ""+c.getString(0), Toast.LENGTH_SHORT).show();
								als.add(c.getString(0));
							}
						}
					}else{
						//Toast.makeText(myActivity, "2222  "+c2.getCount(), Toast.LENGTH_SHORT).show();
					}
		/**/
		
		lunchList = new ArrayAdapter<String>(myActivity, android.R.layout.simple_spinner_item, als.toArray(new String[als.size()]));
		spinner.setAdapter(lunchList);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

		    @Override
		    public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
		    	/*getData(als.get(position));
		    	et.setEnabled(false);
		    	et.setText("");
		    	et.setHint("請選擇照片");*/
		    	pictListPoint=position;
		    	
		    }
		    @Override
		    public void onNothingSelected(AdapterView<?> arg0) {
		    }
		});
		
		et = (EditText)view.findViewById(R.id.mcp_page_et);
		et.setEnabled(false);
		et.setHint("請選擇照片");
		back = (TextView)view.findViewById(R.id.mcp_back);
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
		detail  = (TextView)view.findViewById(R.id.mcp_name);
		detail.setText("店家名稱："+pi.shopName+"\n" +"活動名稱："+pi.title);
		
		ls = (GridView)view.findViewById(R.id.mcp_list);
		mba =new MyAdapter(myActivity);
		ls.setAdapter(mba);
		ls.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(position!=pictPoint){
					synchronized(doubleCilckFlag){
                        doubleCilckFlag=0;
                        }
				}
				if(doubleCilckFlag!=1 ){
					
					doubleCilckFlag=1;
					new Thread(new Runnable() {
		                    public void run() {
		                        try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		                        synchronized(doubleCilckFlag){
		                        doubleCilckFlag=0;
		                        }
		                    }
		                }).start();
					Toast.makeText(myActivity, "已選擇第"+(position+1)+"張圖片", Toast.LENGTH_SHORT).show();
					pictPoint= position;
					CheckPagePictItem p = (CheckPagePictItem)mba.getItem(position);
					et.setEnabled(true);
					et.setHint("已選擇第"+(position+1)+"張圖片");
					
					et.setText(p._Detail);
					selectPict=p;
					for(int i=0;i<als.size();i++){
						if(p._Sflag.equals(als.get(i))){
							spinner.setSelection(i);
						}
					}
					
				}else if(position==pictPoint && doubleCilckFlag==1){
					Toast.makeText(myActivity, "你已雙擊第"+(position+1)+"張圖片", Toast.LENGTH_SHORT).show();
					dialog = new Dialog(myActivity, R.style.Translucent_NoTitle);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.photo_zoom_view2);
					ImageView iv = (ImageView)dialog.findViewById(R.id.pzv2_iv1);
					//p
					CheckPagePictItem p = (CheckPagePictItem)mba.getItem(position);
					
					imageLoader.displayImage("file:///"+p._Dir,iv , options, animateFirstListener);
					//iv.setImageDrawable(bitmap1);
					mAttacher = new PhotoViewAttacher(iv);
					dialog.show();
				}
			}});
		
		ls.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                    final int pos, long id) {
                // TODO Auto-generated method stub

               //Toast.makeText(myActivity, "~~~~"+pos, Toast.LENGTH_SHORT).show();
            	new AlertDialog.Builder(myActivity)
                .setTitle("刪除照片")
                .setMessage("你確定要刪除照片嗎？")
                .setIcon(R.drawable.ic_launcher)
                .setPositiveButton("確定",  new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {          
                                openDB();
                                SQLiteDatabase db = DH.getWritableDatabase();
                                db.delete("MyPict", "_id" + "=" + mba.getItem(pos)._id , null);
                                closeDB();
                                mba.removeByP(pos);
                                pictPoint=-1;
                                pictListPoint=-1;
                                spinner.setAdapter(null);
                                et.setText("");
                                et.setHint("請選擇照片");
                                et.setEnabled(false);
                            }
                        })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                            }
                        }).show();
                return true;
            }
        }); 
		//
		vBut = (RelativeLayout)view.findViewById(R.id.mcp_page_word2);
		vBut.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(pictListPoint!=-1 && pictPoint!=-1){
					ListView ls;
					Button butOK,butNO;
					final MyAdapter mba2 = new MyAdapter(myActivity);
					
					dialog = new Dialog(myActivity, R.style.Translucent_NoTitle);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.vocabulary);
					ls = (ListView)dialog.findViewById(R.id.vb_ls);
					ls.setAdapter(mba2);
					
					SQLiteDatabase db = DBAdapter.getReadableDatabase();
					Cursor c=db.query(false,
							"Phrase",				//��ƪ�W��
							new String[] {"PhraseDESC"},	//���W��
							null,				//WHERE
							null, // WHERE ���Ѽ�
							null, // GROUP BY
							null, // HAVING
							null, // ORDOR BY
							null  // ����^�Ǫ�rows�ƶq
							);
					
					if (c.getCount()>0) {
						c.moveToFirst();
						//Toast.makeText(myActivity, ""+c.getString(0), Toast.LENGTH_LONG).show();
						mba2.addListItem(new VbListItem(c.getString(0)));
						
						while(c.moveToNext()){
							//Toast.makeText(myActivity, ""+c.getString(0), Toast.LENGTH_LONG).show();
							mba2.addListItem(new VbListItem(c.getString(0)));
						}
					}
					
					butOK = (Button)dialog.findViewById(R.id.vb_ok_but);
					butOK.setOnClickListener(new OnClickListener(){
	
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							ArrayList<VbListItem> list =mba2.getAllList();

							for(int i=0;i<list.size();i++){
								if(list.get(i).flag==1){
									String s = et.getText().toString();
									if(s.length()==0){
										et.setText(s+list.get(i).topic);
									}else{
										et.setText(s+"\n"+list.get(i).topic);
									}
								}
							}
							dialog.cancel();
						}});
					butNO = (Button)dialog.findViewById(R.id.vb_no_but);
					butNO.setOnClickListener(new OnClickListener(){
	
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.cancel();
						}});
					
					dialog.show();
				}else{
					Toast.makeText(myActivity, "尚未選擇照片", Toast.LENGTH_SHORT).show();
				}
			}
			class MyAdapter extends BaseAdapter{    
			    
			    private LayoutInflater myInflater;
			   ArrayList<VbListItem> list = new ArrayList<VbListItem>();
			  
			   class View_TalkLayout{

				   TextView name; 
				   RelativeLayout rl;
				   CheckBox cb;
			       
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
			    public VbListItem getItem(int position)
			    {
			        return list.get(position);
			    }
			  
			    @Override
			    public long getItemId(int position)
			    {
			        return position;
			    }
			    
			   public ArrayList<VbListItem> getAllList(){
				   return list;
			   }
			    
			    public void addListItem(VbListItem o){
			    	list.add(o);
			    	this.notifyDataSetChanged();
			    }

			    @Override
			    public View getView(final int position,View convertView,ViewGroup parent)
			    {
			    	View_TalkLayout view_TalkLayout ;
			        
			        if(convertView == null){
			        	convertView = myInflater.inflate(R.layout.vb_listitem, null);
			        	view_TalkLayout = new View_TalkLayout();

			        	view_TalkLayout.name=(TextView)convertView.findViewById(R.id.vbl_tv);
			        	view_TalkLayout.cb = (CheckBox)convertView.findViewById(R.id.vbl_cb);

			        	view_TalkLayout.rl = (RelativeLayout)convertView.findViewById(R.id.vbl_back);
			        	convertView.setTag(view_TalkLayout);
			        }else{
			        	view_TalkLayout = (View_TalkLayout)convertView.getTag();
			        }
			      
			        view_TalkLayout.name.setText(list.get(position).topic);
			        
			        if(list.get(position).flag==0){
			        	view_TalkLayout.cb.setChecked(false);
			        }else{
			        	view_TalkLayout.cb.setChecked(true);
			        }
			        view_TalkLayout.cb.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if(list.get(position).flag==0){
								list.get(position).flag=1;
							}else{
								list.get(position).flag=0;
							}
						}});
			       
		        	if(position%2==0){
		        		view_TalkLayout.rl.setBackgroundColor(Color.parseColor("#F5F5F5"));
		        	}else if(position%2==1){
		        		view_TalkLayout.rl.setBackgroundColor(Color.parseColor("#FFFFFF"));
		        	}

			        return convertView;
			    }
			}
		});
		//
		saveBut.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
					if(pictListPoint==-1 || pictPoint==-1){
						Toast.makeText(myActivity, "尚未選則照片或情境", Toast.LENGTH_SHORT).show();
					}else{
						openDB();
						SQLiteDatabase db = DH.getReadableDatabase();
						ContentValues values = new ContentValues();
						values.put("_Detail", et.getText().toString());
						values.put("_Sflag", als.get(pictListPoint));
						selectPict._Detail=et.getText().toString();
						selectPict._Sflag=als.get(pictListPoint);
						db.update("MyPict", values, "_id" + "=" + selectPict._id, null);
						closeDB();
						Toast.makeText(myActivity, "已儲存", Toast.LENGTH_SHORT).show();
						
						
					}
					
				
			}});
	}
	
	public static MainMenuFragment newInstance(PpItemObject pi) {
        Log.d(TAG, "in AddVisitCustomerFragment newInstance()");

        CheckPage mf = new CheckPage(pi);

        // Supply index input as an argument.
		Bundle args = new Bundle();
		//.args.putInt("index", index);
		mf.setArguments(args);
		return mf;
	}
	
	
	
	public class MyAdapter extends BaseAdapter{    
	    
	    private LayoutInflater myInflater;
	   ArrayList<CheckPagePictItem> list = new ArrayList<CheckPagePictItem>();
	  
	   
	   private class View_TalkLayout{

		   ImageView iv;
	       
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
	    public CheckPagePictItem getItem(int position)
	    {
	        return list.get(position);
	    }
	  
	    @Override
	    public long getItemId(int position)
	    {
	        return position;
	    }
	    
	   public void setSflag(int index,String _sFlag){
		   list.get(index)._Sflag=_sFlag;
	   }
	    
	    public void addListItem(CheckPagePictItem o){
	    	
	    	list.add(o);
	    	this.notifyDataSetChanged();
	    }
	  
	    @Override
	    public View getView(final int position,View convertView,ViewGroup parent)
	    {
	    	View_TalkLayout view_TalkLayout ;
	        
	        if(convertView == null){
	        	convertView = myInflater.inflate(R.layout.pict_ls_item, null);
	        	view_TalkLayout = new View_TalkLayout();

	        	view_TalkLayout.iv=(ImageView)convertView.findViewById(R.id.cpli_iv1);
	        	
	        	//iv1 rl

	        	
	        	convertView.setTag(view_TalkLayout);
	        }else{
	        	view_TalkLayout = (View_TalkLayout)convertView.getTag();
	        }
	      
	        String pathName = list.get(position)._Dir; 
	        imageLoader.displayImage("file:///"+pathName,view_TalkLayout.iv , options, animateFirstListener);
	        return convertView;
	    }
	}
		// 圖片顯示動畫
		private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

			static final List displayedImages = Collections.synchronizedList(new LinkedList());

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				if (loadedImage != null) {
					ImageView imageView = (ImageView) view;
					boolean firstDisplay = !displayedImages.contains(imageUri);
					if (firstDisplay) {
						FadeInBitmapDisplayer.animate(imageView, 500);
					} else {
						imageView.setImageBitmap(loadedImage);
					}
					displayedImages.add(imageUri);
				}
			}
		}
		private void initImageLoder() {
			// TODO Auto-generated method stub
			imageLoader = ImageLoader.getInstance();
			options = new DisplayImageOptions.Builder()
	        .showStubImage(R.drawable.balck_c)
	        .showImageForEmptyUri(R.drawable.balck_c)
	        .showImageOnFail(R.drawable.balck_c)
	        .cacheInMemory()
	        .cacheOnDisc()
	        .build();
	       ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(myActivity.getApplicationContext()).defaultDisplayImageOptions(options) .build();
	       imageLoader.init(config);
		}
	
}
