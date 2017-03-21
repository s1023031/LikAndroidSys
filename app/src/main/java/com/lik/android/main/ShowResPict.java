package com.lik.android.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lik.android.om.ResPict;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ShowResPict  extends MainMenuFragment {

	protected static final String TAG = MainMenuFragment.class.getName();
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private ImageLoader imageLoader;
	private  ImageView expandedImageView;;

	private Dialog dialog;
	View view;
	PhotoViewAttacher mAttacher;
	MyAdapter mba;
	TextView title,mcp_back;
	GridView resPictView;
	PpItemObject pi;
	String projectNo = "";
	String type = "";
	DisplayMetrics metrics ;
	public ShowResPict(PpItemObject pi, String type){
		this.pi=pi;
		this.type=type;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater,container,savedInstanceState);
		view = inflater.inflate(R.layout.res_pict, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG,"onActivityCreated start!");
		  setView();
		 initImageLoder();
		 getImageFile();
	}
	
	/*protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.res_pict);
		 Log.d(TAG,"ShowResPict start");
		 projectNo = (String)getIntent().getSerializableExtra("ProjectNo");;
		 type = (String)getIntent().getSerializableExtra("Type");;
		 pi=(PpItemObject)getIntent().getSerializableExtra("PpItemObject");;
		 Log.d(TAG,"projectNo="+ pi.projectNO);
		 Log.d(TAG,"type="+ type);
		 DBAdapter = MainMenuActivity.DBAdapter;
		 setView();
		 initImageLoder();
		 getImageFile();
	}*/
		
	private void setView()
	{
		title = (TextView) view.findViewById(R.id.ssli_tv);
		resPictView = (GridView) view.findViewById(R.id.gridView);
		mcp_back  = (TextView) view.findViewById(R.id.mcp_back);
		expandedImageView = (ImageView) view.findViewById(R.id.expanded_image);
		projectNo = pi.projectNO;
		
		metrics = new DisplayMetrics();
		myActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		
		if("0".equals(type))
			title.setText(" 陳列");
		else
			title.setText(" 合約");
		
		mcp_back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//myActivity.getFragmentManager().beginTransaction().remove(CustomerDetail.this).commit();
				MainMenuFragment mmf = ResPage.newInstance(pi);
				myActivity.showMainMenuFragment(mmf);
				synchronized(myActivity) {
					myActivity.setNeedBackup(true);
					myActivity.notify();
				}
			}
		});;
		
		mba =new MyAdapter(myActivity);
		resPictView.setAdapter(mba);
		resPictView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

					dialog = new Dialog(myActivity, R.style.Translucent_NoTitle);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.res_pict_zoom);
					ImageView iv = (ImageView)dialog.findViewById(R.id.imageView);
					LayoutParams layoutParams = iv.getLayoutParams();
					layoutParams.width = metrics.widthPixels;
					layoutParams.height = metrics.heightPixels;
					iv.setLayoutParams(layoutParams);
					
					
					//iv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
					iv.setScaleType(ImageView.ScaleType.FIT_XY);
					ResPictItem recPict = (ResPictItem)mba.getItem(position);
					
					imageLoader.displayImage("file:///"+recPict.imagePath, iv , options, animateFirstListener);
					mAttacher = new PhotoViewAttacher(iv);
					dialog.show();
				}
			});
		
	}
	 
	private void getImageFile()
	{
		ResPict omResPict= new ResPict();
		omResPict.setpNO(projectNo);
		omResPict.setType(type);
		List<ResPict> resPict = omResPict.getRictPictB(DBAdapter);
		 Log.d(TAG,"resPict size="+resPict.size());
		if(resPict.size() > 0 )
		{
			for(int i = 0 ;  i< resPict.size() ; i ++)
			{
				Log.d(TAG,"path="+resPict.get(i).getPath());
				mba.addListItem(new ResPictItem(resPict.get(i).getPath()));
			}
				
		}else
			Log.d(TAG,"no data");
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
		.imageScaleType(ImageScaleType.EXACTLY)
		.cacheInMemory(true)
        .cacheOnDisc()
        .build();
       ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(myActivity).defaultDisplayImageOptions(options) .build();
       imageLoader.init(config);
	}
	
	public static MainMenuFragment newInstance(PpItemObject pi, String type) {
        Log.d(TAG, "in ShowResPict newInstance()");

        ShowResPict mf = new ShowResPict(pi,type);

        // Supply index input as an argument.
		Bundle args = new Bundle();
		//.args.putInt("index", index);
		mf.setArguments(args);
		return mf;
	}
	
	public class MyAdapter extends BaseAdapter{    
	    
	    private LayoutInflater myInflater;
	   ArrayList<ResPictItem> list = new ArrayList<ResPictItem>();
	
	   
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
	    public ResPictItem getItem(int position)
	    {
	        return list.get(position);
	    }
	    
	    @Override
	    public long getItemId(int position)
	    {
	        return position;
	    }
	    
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
	    	View_TalkLayout view_TalkLayout ;
	        
	        if(convertView == null){
	        	convertView = myInflater.inflate(R.layout.pict_ls_item, null);
	        	view_TalkLayout = new View_TalkLayout();

	        	view_TalkLayout.iv=(ImageView)convertView.findViewById(R.id.cpli_iv1);
	        	
	        	convertView.setTag(view_TalkLayout);
	        }else{
	        	view_TalkLayout = (View_TalkLayout)convertView.getTag();
	        }
	        
	        String pathName = list.get(position).imagePath; 

	        imageLoader.displayImage("file:///"+pathName,view_TalkLayout.iv , options, animateFirstListener);
	        return convertView;
		}
		
	    public void addListItem(ResPictItem o){	
	    	list.add(o);
	    	this.notifyDataSetChanged();
	    }
	
	}
}
