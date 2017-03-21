package com.lik.android.main;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lik.Constant;
import com.lik.android.om.Customers;
import com.lik.android.om.Orders;


public class QueryNotUploadFragment  extends MainMenuFragment implements OnLongClickListener,OnClickListener{


	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getOrders();
		
		
	}

	protected static final String TAG = QueryNotUploadFragment.class.getName();
	protected static final String CUSTOMER_BUNDLE_KEY = "CustomerBundleKey";
	public static final String LAST_SELECTED_POSITION_KEY = "QueryNotUploadFragment.LastSelectedPositionKey";
	public static final String BYPASS_KEY = "QueryNotUploadFragment.ByPassKey";
	protected int lastSelectedPosition = -1;

	//QueryNotUploadDataAdapter adapter;
	TreeMap<String,String> pda8 = new TreeMap<String,String>();
	ProgressDialog pDialog;
	boolean isOrderEnabled = false;
    View view;
    MyAdapter myadm;
	int selectedPosition = 0;
	
	ListView lv =null;

	
	public static MainMenuFragment newInstance() {
       Log.d(TAG, "in QueryNotUploadFragment newInstance");

        QueryNotUploadFragment mf = new QueryNotUploadFragment();

        // Supply index input as an argument.
		Bundle args = new Bundle();
		//args.putInt("index", index);
		mf.setArguments(args);
		return mf;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater,container,savedInstanceState);
		view = inflater.inflate(R.layout.main_querynotupload, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG,"onActivityCreated start!");
		queryNotUploadUI();
		myActivity.setBadges();
	}
	
	@Override
	public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		Log.d(TAG,"onCreateOptionsMenu start!");
		Log.d(TAG,"isOrderEnabled="+isOrderEnabled);
	/*	if(isOrderEnabled) {
			// ����enable
			MenuItem item = (MenuItem)myActivity.menu.findItem(R.id.mainmenu_item4);
			if(item != null) 
				item.setEnabled(true);
		} else {
			// ����disable
			MenuItem item = (MenuItem)myActivity.menu.findItem(R.id.mainmenu_item4);
			if(item != null) item.setEnabled(false);
			isOrderEnabled = false;
		}*/
	}
	
	private void queryNotUploadUI() {
		// check lastSelectedPosition
		Log.d(TAG,"lastSelectedPosition="+lastSelectedPosition);
		//pda8 = getKindMap(Phrase.PHKINDNO_8);
		pDialog = new ProgressDialog(myActivity);    
        // Restore preferences
        SharedPreferences settings = myActivity.getPreferences(Context.MODE_PRIVATE);
        lastSelectedPosition = settings.getInt(LAST_SELECTED_POSITION_KEY, -1);
        String stockInfo = myActivity.omCurrentSysProfile.getStockInfo();
    	lv= (ListView)view.findViewById(R.id.querynotupload_listView1);
    	myadm = new MyAdapter(myActivity);
    	lv.setAdapter(myadm);
    	
    	RelativeLayout addCustomer = (RelativeLayout)view.findViewById(R.id.querynotupload_button1);
    	addCustomer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MainMenuFragment mmf = AddVisitCustomerFragment.newInstance();
			/*	if(lastSelectedPosition != -1 && lastSelectedPosition<adapter.getCount()) {
					Bundle bundle = mmf.getArguments();
					bundle.putSerializable(CUSTOMER_BUNDLE_KEY, (QueryNotUploadView)adapter.getItem(lastSelectedPosition));
				}*/
				myActivity.showMainMenuFragment(mmf);
				
			}
    		
    	});
    	
    	RelativeLayout deleteCustomer = (RelativeLayout)view.findViewById(R.id.deleteBtn);
    	deleteCustomer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Orders omOrder  = new Orders();
				omOrder.setCompanyParent(myActivity.omCurrentSysProfile.getCompanyNo());
				omOrder.setUserNO(myActivity.omCurrentAccount.getAccountNo());
				omOrder.setCompanyID(myActivity.currentDept.getCompanyID());
				Log.d(TAG,"CompanyParent = " + omOrder.getCompanyParent() + " UserNo = "+omOrder.getUserNO()+ " MaxOrder="+omOrder.getMaxViewOrderByUserNO(DBAdapter));
				if(omOrder.getMaxViewOrderByUserNO(DBAdapter)>0){
					List <Orders> orderList = omOrder.getOrdersByUserNO(DBAdapter);
					Dialog orderDialog = getOrdersDialog(orderList);
					orderDialog.show();
				
				}else{
					Toast.makeText(myActivity, "尚未選擇客戶", Toast.LENGTH_SHORT).show();
				}
			}
    		
    	});
    	

    	//root = view;
		// take care of abnormal case, need to restore from activity destroy
    	Bundle bundle = getArguments();
		/*if(bundle.getBoolean(BYPASS_KEY,false)) {
			bundle.remove(BYPASS_KEY);
			if(lastSelectedPosition==-1 || lastSelectedPosition>=adapter.getCount()) return view;
			QueryNotUploadView omview = (QueryNotUploadView)adapter.getItem(lastSelectedPosition);
			MainMenuFragment mmf = TakeOrderFragment.newInstance(R.id.mainmenu_item4);
			bundle = mmf.getArguments();
			bundle.putSerializable(CUSTOMER_BUNDLE_KEY, omview);
	        isTakeOrderChecked = settings.getBoolean(TakeOrderFragment.TAKEORDER_CHECKED_KEY, false);
	        String lastSelectedTab = settings.getString(TakeOrderFragment.LAST_SELECTED_TAB_KEY, TakeOrderFragment.TAB_ADD);
	        if(lastSelectedTab.equals(TakeOrderFragment.TAB_ADD)) {
	        	lastSelectedLVposition = settings.getInt(SubAddFragment.LAST_SELECTED_LVPOSITION_FOR_BYPASS_KEY, -1);
		        bundle.putInt(SubAddFragment.LAST_SELECTED_LVPOSITION_FOR_BYPASS_KEY, lastSelectedLVposition);
	        } else {
	        	lastSelectedLVposition = settings.getInt(SubDetailFragment.LAST_SELECTED_LVPOSITION_FOR_BYPASS_KEY, -1);	        	
		        bundle.putInt(SubDetailFragment.LAST_SELECTED_LVPOSITION_FOR_BYPASS_KEY, lastSelectedLVposition);
	        }
	        bundle.putString(TakeOrderFragment.LAST_SELECTED_TAB_KEY,lastSelectedTab);
	        Log.d(TAG,"in bypass--> isTakeOrderChecked="+isTakeOrderChecked+",lastSelectedLVposition="+lastSelectedLVposition);
			myActivity.showMainMenuFragment(mmf);
		}*/

    	//return view;
	}
	
    @Override
    public void onStop() {
    	super.onStop();
    	Log.d(TAG,"onStop called");
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = myActivity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(LAST_SELECTED_POSITION_KEY, lastSelectedPosition);
        //editor.remove(TakeOrderFragment.LAST_SELECTED_TAB_KEY); // reset TakeOrderFragment LAST_SELECTED_TAB_KEY
       /* editor.remove(SubAddFragment.LAST_SELECTED_CATEGORY_KEY); // reset SubAddFragment LAST_SELECTED_CATEGORY_KEY
        editor.remove(SubAddFragment.LAST_SELECTED_TOF_CATEGORY_KEY); // reset SubAddFragment LAST_SELECTED_TOF_CATEGORY_KEY
        editor.remove(SubAddFragment.LAST_SELECTED_SUPPLIER_KEY);
        editor.remove(SubAddFragment.LAST_SELECTED_SEARCH_KEY);
        editor.remove(SubAddFragment.LAST_SELECTED_SEARCHVALUE_KEY);
        editor.remove(SubAddFragment.LAST_SELECTED_LVPOSITION_KEY);
        editor.remove(SubAddFragment.LAST_SELECTED_CACHEORALL_KEY);
        editor.remove(SubDetailFragment.LAST_SELECTED_LVPOSITION_KEY);
        editor.remove(SubStockFragment.LAST_SELECTED_LVPOSITION_KEY);*/
        lastSelectedLVposition = -1;
        // Commit the edits!
        editor.commit();
    }

  /*  private AlertDialog getAlertDialogForOrderDelete(String title, String message, final Orders omO) {
    	Builder builder = new AlertDialog.Builder(myActivity);
    	builder.setTitle(title);
    	builder.setMessage(message);
    	String msg = getResources().getString(R.string.Button1);
    	builder.setPositiveButton(msg, new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			omO.doDelete(DBAdapter);
    			if(omO.getRid()>=0) {
    				adapter.gatherData(myActivity.omCurrentAccount.getAccountNo());
//    				if(adapter.getCount()==0) {
    					// ����disable
    		    		MenuItem item = (MenuItem)myActivity.menu.findItem(R.id.mainmenu_item4);
    		    		item.setEnabled(false);
//    				}
    				adapter.notifyDataSetChanged();
        			lastSelectedPosition = -1; // �]�w���窱�A
    				Log.d(Constant.TAG,"order deleted!");
    				Toast.makeText(myActivity,"order deleted!", Toast.LENGTH_SHORT).show(); 
					synchronized(myActivity) {
						myActivity.setNeedBackup(true);
						myActivity.notify();
					}
    			}
    		}
    	});
    	msg = getResources().getString(R.string.Button2);
    	builder.setNegativeButton(msg, new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			// do nothing
    		}
    	});
    	return builder.create();
    }
*/

	@Override
	public boolean onLongClick(View v) {
		TextView tv = (TextView)v;
		Log.d(Constant.TAG,"onLongClick index="+tv.getText()+tv.getContentDescription()+",width="+tv.getWidth());
		// �]�w LayoutParams�Aadapter��Jtag���A�H�Kdrag event�A�ϥ�
		//tv.setTag(adapter);
		// start drag for this view, no ClipData need
		tv.startDrag(null, new DragShadowBuilder(v), (Object)v, 0);
		return true;
	}

	@Override
	public void onClick(View v) {
		TextView tv = (TextView)v;
		Log.d(Constant.TAG,"onClick index="+tv.getText()+tv.getContentDescription()+",width="+tv.getWidth());
		int newWidth = tv.getWidth()+Constant.ZOOM_INCREMENTAL;
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(newWidth, tv.getHeight());
		tv.setLayoutParams(params);
	}
	
/*    class ShowProductsListTask extends AsyncTask<Integer, Integer, List<SubAddProductsView>> {
    	
		int customerID; 

    	@Override
        protected List<SubAddProductsView> doInBackground(Integer... args) {
    		Log.d(TAG,"ShowProductsListTask execute...");
            Products omP = new Products();
            omP.setCompanyID(args[0]);
            omP.setCustomerID(args[1]);
            omP.setDeliverOrder(args[2]==null?null:Integer.valueOf(args[2]));
			customerID = omP.getCustomerID();
            List<SubAddProductsView> lt;
            DBAdapter.closedb();
            if(myActivity.isSND) {
            	omP.setUserNo(myActivity.omCurrentAccount.getAccountNo());
            	lt = omP.getLimitProdList(DBAdapter);
            	Log.d(TAG,"ltPLimit.size()="+lt.size());
            } else {
                if(myActivity.SellDetailStorageType.equals("XML")) {
                	lt = omP.getProductsBySellCacheXML(DBAdapter);
                } else {
                	lt = omP.getProductsBySellCache(DBAdapter);
                }            	
            }
    		return lt;
        }
    	
    	@Override
        protected void onProgressUpdate(Integer... progress) {
        	// do nothing
        }
    	
    	@Override
        protected void onPostExecute(List<SubAddProductsView> lt) {
    		Log.d(TAG,"ShowProductsListTask finishing query...");
    		synchronized(myActivity.selledProducts) {
    			myActivity.cacheCustomerID = customerID;
	            for(Iterator<SubAddProductsView> ir=lt.iterator();ir.hasNext();) {
	            	SubAddProductsView viewSAP = ir.next();
	            	myActivity.cacheSelledProducts.add(viewSAP);
	            	myActivity.selledProducts.add(viewSAP.getSerialID());
	            }
	            if(getFragmentManager()!=null) {
		            MainMenuFragment mmf = (MainMenuFragment)getFragmentManager().findFragmentById(R.id.main_frameLayout1);
		            if(mmf instanceof TakeOrderFragment) {
		            	Fragment tmmf = (Fragment)getFragmentManager().findFragmentById(android.R.id.tabcontent);
		            	if(tmmf instanceof SubAddFragment) {
		            		SubAddFragment saf = (SubAddFragment)tmmf;
		            		if(saf.adapter!=null) saf.adapter.notifyDataSetChanged();
		            	}
		            }
	            }
    		}
        }
    }*/
	
	
	private Dialog getOrdersDialog(final List<Orders> orderList) {
		Log.d(TAG, "getOrdersDialog Start");
		final Dialog dialog = new Dialog(getActivity(), R.style.Translucent_NoTitle);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.select_orders);
		
		Customers omCustomer = new Customers();
		ArrayList<String> customerList = new ArrayList<String> ();
		for(int i=0;i<orderList.size();i++){
			omCustomer.setCompanyID(orderList.get(i).getCompanyID());
			omCustomer.setCustomerNO(orderList.get(i).getCustomerNO());
			omCustomer.setUserNO(orderList.get(i).getUserNO());
			omCustomer=omCustomer.getCustomersByNo(DBAdapter);
			customerList.add(omCustomer.getShortName());
		}

		final String[] custList = customerList.toArray(new String[0]);
		Log.d(TAG, "custList = "+custList[0]);
		Log.d(TAG, "aaa="+getActivity());
		ArrayAdapter<String> custAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, custList);                                     
		
		Button deleteBtn = (Button) dialog.findViewById(R.id.so_but1);
		Button closeBtn = (Button) dialog.findViewById(R.id.so_but2);
		
		deleteBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
					Orders order = orderList.get(selectedPosition);
					order.deleteAllOrdersByUserNo(DBAdapter);
				    if(order.getRid() > 0)
				    {
				    	Log.d(TAG, "Delete order success [CompanyParent]="+order.getCompanyParent()+"[CompanyID]="+order.getCompanyID()+"[CustomerNo]="+order.getCustomerNO());;	
				    	getOrders();
				    }
				    else
				    	Log.d(TAG, "Cannot delete Order [CompanyParent]="+order.getCompanyParent()+"[CompanyID]="+order.getCompanyID()+"[CustomerNo]="+order.getCustomerNO());
				dialog.dismiss();
			}
			
		});
		
		
		closeBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
		   }
			
		});
		
		Spinner spinner = (Spinner) dialog.findViewById(R.id.so_sp1);
		spinner.setAdapter(custAdapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

		    @Override
		    public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
		       Toast.makeText(getActivity(), "你選的是"+custList[position], Toast.LENGTH_SHORT).show();
		       selectedPosition = position;
		    }
		    @Override
		    public void onNothingSelected(AdapterView<?> arg0) {
		    }
		});
		
		Log.d(TAG, "getOrdersDialog End");
		return dialog;
	}
	
	public void getOrders()
	{
		myadm.removeAll();
		SQLiteDatabase db = DBAdapter.getReadableDatabase();
		String coustomersStr="Customers_"+myActivity.omCurrentSysProfile.getCompanyNo()+myActivity.currentDept.getCompanyID();
		Cursor c2=db.query(false,
				"sqlite_master",				//��ƪ�W��
				new String[] {"name"},	//���W��
				"type='table' AND (name='"+coustomersStr+"' OR name='Orders') ;",				//WHERE
				null, // WHERE ���Ѽ�
				null, // GROUP BY
				null, // HAVING
				null, // ORDOR BY
				null  // ����^�Ǫ�rows�ƶq
				);
		
		Log.d(TAG,"QueryNotUpload query="+coustomersStr);
		if(c2.getCount()==2){
			
			//Toast.makeText(myActivity, "1111  "+c2.getCount(), Toast.LENGTH_SHORT).show();
			
			Cursor c=db.query(true,
					"Orders , Customers_"+myActivity.omCurrentSysProfile.getCompanyNo()+myActivity.currentDept.getCompanyID(),			
					new String[] {"ShortName",coustomersStr+".CustomerID","Orders.OrderID","Orders.SerialID"},	
					coustomersStr+".CustomerID = Orders.CustomerID AND Orders.CompanyParent='"+myActivity.omCurrentSysProfile.getCompanyNo()+"' AND Orders.UserNO = '"+myActivity.omCurrentAccount.getAccountNo()+"' order by Orders.SerialID desc;",				
					null, // WHERE ���Ѽ�
					null, // GROUP BY
					null, // HAVING
					null, // ORDOR BY
					null  // ����^�Ǫ�rows�ƶq
					);
			
			if (c.getCount()>0) {
				c.moveToFirst();
				Log.d(TAG," Order data="+c.getString(3)+" "+ c.getString(0)+" "+ c.getString(1)+" "+c.getString(2));
				myadm.addListItem(new Page6Object(c.getString(0),c.getString(1),c.getString(2)));
				while(c.moveToNext()){
					Log.d(TAG," Order data="+c.getString(3)+" "+ c.getString(0)+" "+ c.getString(1)+" "+c.getString(2));
					myadm.addListItem(new Page6Object(c.getString(0),c.getString(1),c.getString(2)));
				}
			}
		}else{
			//Toast.makeText(myActivity, "2222  "+c2.getCount(), Toast.LENGTH_SHORT).show();
		}
	}
	
	
	public class MyAdapter extends BaseAdapter{    
	    
	    private LayoutInflater myInflater;
	   ArrayList<Page6Object> list = new ArrayList<Page6Object>();
	   
	   private class View_TalkLayout{
		   ImageView iv1,iv2;
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
	    
	    public void addListItem(Page6Object o){
	    	list.add(o);
	    	this.notifyDataSetChanged();
	    }
	  
	    @Override
	    public View getView(final int position,View convertView,ViewGroup parent)
	    {
	    	View_TalkLayout view_TalkLayout ;
	        
	        if(convertView == null){

	        	convertView = myInflater.inflate(R.layout.page6_listview_item, null);
	        	view_TalkLayout = new View_TalkLayout();

	        	view_TalkLayout.name=(TextView)convertView.findViewById(R.id.pli6_tv);
	        
	        	view_TalkLayout.iv1=(ImageView)convertView.findViewById(R.id.pli6_iv_r);
	        	view_TalkLayout.iv2=(ImageView)convertView.findViewById(R.id.pli6_iv_picturt);
	        	view_TalkLayout.rl = (RelativeLayout)convertView.findViewById(R.id.pli6_back);
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
        	
        	view_TalkLayout.iv1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					MainMenuFragment mmf = CustomerDetail.newInstance(list.get(position).OrderID,list.get(position).CustomersID);
					myActivity.showMainMenuFragment(mmf);
				
				}
			});
        	
        	view_TalkLayout.iv2.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					MainMenuFragment mmf = ProjectPhoto.newInstance(list.get(position).OrderID,list.get(position).CustomersID);
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
