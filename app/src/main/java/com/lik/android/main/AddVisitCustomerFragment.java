package com.lik.android.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lik.Constant;
import com.lik.android.om.Customers;
import com.lik.android.om.DailySequence;
import com.lik.android.om.Orders;
import com.lik.android.om.SysProfile;
import com.lik.android.view.AddVisitCustomerDataAdapter;
import com.lik.android.view.AddVisitCustomerView;
import com.lik.android.view.QueryNotUploadView;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class AddVisitCustomerFragment  extends MainMenuFragment implements OnLongClickListener,OnClickListener {

	public static final String SEQUENCE_ORDERID_KEY = "ORDERID";
	public static final String SEQUENCE_VIEWORDERID_KEY = "VIEWORDERID";

	protected int lastSelectedPosition = -1;
	protected String isDirty; //HAO 104.04.27
	AddVisitCustomerDataAdapter adapter;
	TreeMap<String,String> pda8 = new TreeMap<String,String>();
	QueryNotUploadView omQNU;
	ListView lv;
	RadioGroup rg1;
	RadioButton rb0,rb1,rb2;
	View view;
	TextView tv1;




	public static MainMenuFragment newInstance() {
		Log.d(TAG, "in AddVisitCustomerFragment newInstance()");
		Log.d("EE", "2222in AddVisitCustomerFragment newInstance()");
		AddVisitCustomerFragment mf = new AddVisitCustomerFragment();
        int latest2 = 111;
		// Supply index input as an argument.
		Bundle args = new Bundle();
		//.args.putInt("index", index);
		mf.setArguments(args);
		return mf;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater,container,savedInstanceState);
		view = inflater.inflate(R.layout.main_addvisitcusomer, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG,"onActivityCreated start!");
		// �s�W���X�Ȥ�
		addVisitCustomer();
	}

	private void addVisitCustomer() {
		String current = Constant.sdf.format(new Date());
		SharedPreferences settings = myActivity.getPreferences(Context.MODE_PRIVATE);
		String savedDate = settings.getString(SEQUENCE_VIEWORDERID_KEY, "");
		Log.d(TAG,"current="+current+",savedDate="+savedDate);
		if(!current.equals(savedDate)) {
			Log.d(TAG,"reset called");
			DailySequence omDSV = new DailySequence(SEQUENCE_VIEWORDERID_KEY);
			omDSV.reset(DBAdapter);
		}
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(SEQUENCE_VIEWORDERID_KEY, current);
		editor.commit();
		Bundle bundle = getArguments();
		//pda8 = getKindMap(Phrase.PHKINDNO_8);
		omQNU = (QueryNotUploadView)bundle.getSerializable(QueryNotUploadFragment.CUSTOMER_BUNDLE_KEY);
		final Spinner sp1 = (Spinner)view.findViewById(R.id.addvisitcustomer_spinner1);
		String[] search_method = getResources().getStringArray(R.array.addvisitcustomer_search_method);
		ArrayAdapter<String> aadapter = new ArrayAdapter<String>(myActivity,R.layout.addvisitcustomer_search_method,search_method);
		sp1.setAdapter(aadapter);
		final EditText et1 = (EditText)view.findViewById(R.id.addvisitcustomer_editText1);
		rg1 = (RadioGroup)view.findViewById(R.id.addvisitcustomer_radioGroup1);
		rb0 = (RadioButton)view.findViewById(R.id.addvisitcustomer_radio0);
		rb1 = (RadioButton)view.findViewById(R.id.addvisitcustomer_radio1);
		lv = (ListView)view.findViewById(R.id.addvisitcustomer_listView1);
		tv1 =(TextView)view.findViewById(R.id.mc_detail);
		rg1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				String msg="";
				switch(arg1) {
					case R.id.addvisitcustomer_radio0:
						msg = rb0.getText().toString();
						if(rb0.isChecked()) {
							adapter.gatherData(myActivity.omCurrentAccount.getAccountNo(),String.valueOf(myActivity.currentDept.getCompanyID()),"Y",myActivity.isSND?"2":"1");
							//adapter.notifyDataSetChanged();
						}
						break;
					case R.id.addvisitcustomer_radio1:
						msg = rb1.getText().toString();
						if(rb1.isChecked()) {
							adapter.gatherData(myActivity.omCurrentAccount.getAccountNo(),String.valueOf(myActivity.currentDept.getCompanyID()),"N",myActivity.isSND?"2":"1");
							//adapter.notifyDataSetChanged();
						}
						break;
/*			case R.id.addvisitcustomer_radio2:
				msg = rb2.getText().toString();
				if(rb2.isChecked()) {
					adapter.gatherData(myActivity.omCurrentAccount.getAccountNo(),String.valueOf(myActivity.currentCompany.getCompanyID()),null,"1");
//					adapter.notifyDataSetChanged();
				}
				break;*/
					default:
						return;
				}
				initSetToPreSelectedCommmon();
				adapter.notifyDataSetChanged();
				Log.d(TAG,"onCheckedChanged:"+msg);

			}});

		Button bn1 = (Button)view.findViewById(R.id.addvisitcustomer_button1);
		bn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG,"search key word:"+et1.getText().toString());
				Log.d(TAG,"search by"+sp1.getSelectedItem().toString());
				switch(sp1.getSelectedItemPosition()) {
					case 0:
						adapter.gatherData(myActivity.omCurrentAccount.getAccountNo(),String.valueOf(myActivity.currentDept.getCompanyID()),null,null,et1.getText().toString());
						break;
					case 1:
						adapter.gatherData(myActivity.omCurrentAccount.getAccountNo(),String.valueOf(myActivity.currentDept.getCompanyID()),null,null,null,et1.getText().toString());
						break;
					default:
						return;
				}
				adapter.notifyDataSetChanged();
				//rg1.clearCheck();
			}

		});

		RelativeLayout bn2 = (RelativeLayout)view.findViewById(R.id.addvisitcustomer_button2);
		bn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(adapter.getCount()==0) {
					AlertDialog dialog = getAlertDialogForMessage(getResources().getString(R.string.takeorderMessage1a),getResources().getString(R.string.addvisitcustomerMessage1));
					dialog.show();
					return;
				}
				if(lastSelectedPosition==-1) {
					AlertDialog dialog = getAlertDialogForMessage(getResources().getString(R.string.takeorderMessage1a),getResources().getString(R.string.addvisitcustomerMessage1));
					dialog.show();
					return;
				}
				if(lastSelectedPosition>=adapter.getCount()) {
					AlertDialog dialog = getAlertDialogForMessage(getResources().getString(R.string.takeorderMessage1a),getResources().getString(R.string.addvisitcustomerMessage1));
					dialog.show();
					adapter.notifyDataSetChanged();
					return;
				}
				//HAO 104.04.27
				if(myActivity.isSND) {
					isDirty = ((AddVisitCustomerView)adapter.getItem(lastSelectedPosition)).getIsDirty();
					if(isDirty.equals("Y")) {
						AlertDialog dialog = getAlertDialogForMessage(getResources().getString(R.string.takeorderMessage1a),getResources().getString(R.string.addvisitcustomerMessage2));
						dialog.show();
					}
				}
				//
				DailySequence omDS = new DailySequence(SEQUENCE_ORDERID_KEY);
				DailySequence omDSV = new DailySequence(SEQUENCE_VIEWORDERID_KEY);

				if(((AddVisitCustomerView)adapter.getItem(lastSelectedPosition)).getSerialID() == -1)
				{
					AlertDialog dialog = getAlertDialogForMessage(getResources().getString(R.string.takeorderMessage1a),getResources().getString(R.string.addvisitcustomerMessage1));
					dialog.show();
					return;
				}
				Date date = MainMenuActivity.getCurrentDate();
				Orders omO = new Orders();
				omO.setUserNO(myActivity.omCurrentAccount.getAccountNo());
				omO.setUploadFlag(Orders.UPLOADFLAG_N);
				omO.setTabletSerialNO(DEVICEID);
				omO.setOrderID(omDS.getSequence(DBAdapter));
				omO.setViewOrder(omDSV.getSequence(DBAdapter));
				omO.setCompanyID(myActivity.currentDept.getCompanyID());
				omO.setUserNO(myActivity.omCurrentAccount.getAccountNo());
				omO.setOrderDT(date);
				omO.setLastDT(date);

				omO.setCustomerID(((AddVisitCustomerView)adapter.getItem(lastSelectedPosition)).getCustomerID());
				omO.setSalesID(((AddVisitCustomerView)adapter.getItem(lastSelectedPosition)).getSalesID());
				omO.setCustomerNO(((AddVisitCustomerView)adapter.getItem(lastSelectedPosition)).getCustomerNO());
				omO.setPdaId(myActivity.omCurrentSysProfile.getPdaId());
				omO.setUploadFlag(Orders.UPLOADFLAG_N);
				omO.setReplyFlag(Orders.REPLYFLAG_N);
				omO.setCompanyParent(myActivity.omCurrentSysProfile.getCompanyNo());
				if(check(((AddVisitCustomerView)adapter.getItem(lastSelectedPosition)).getShortName())){
					omO.doInsert(DBAdapter);
				}else{

					Toast.makeText(myActivity, "已選擇過了", Toast.LENGTH_SHORT).show();
					MainMenuFragment mmf = QueryNotUploadFragment.newInstance();
					myActivity.showMainMenuFragment(mmf);
					synchronized(myActivity) {
						myActivity.setNeedBackup(true);
						myActivity.notify();
					}
				}


				if(omO.getRid()>=0) {
					Toast.makeText(myActivity,"Orders created!",Toast.LENGTH_LONG).show();
					synchronized(myActivity) {
						myActivity.setNeedBackup(true);
						myActivity.notify();
					}
					Log.d(TAG,"Orders created! TabletSerialNO:OrderID:ViewOrder:CompanyID:UserNO->"+
							omO.getTabletSerialNO()+":"+omO.getOrderID()+":"+omO.getViewOrder()+":"+omO.getCompanyID()+":"+omO.getUserNO());

					omO.findByKey(DBAdapter);
					long serialID = omO.getSerialID();

					String stockInfo = myActivity.omCurrentSysProfile.getStockInfo();
					if(stockInfo.equals(SysProfile.STOCKINFO_R)) omO.setUploadFlag(null);
					else omO.setUploadFlag(Orders.UPLOADFLAG_N);
					List<Orders> ltO = omO.getOrdersByUserNO(DBAdapter);
					int last = 0;
					for(int i=0;i<ltO.size();i++) {
						Orders o = ltO.get(i);
						//Toast.makeText(myActivity, ""+o.getCompanyID(),Toast.LENGTH_LONG ).show();
						if(serialID==o.getSerialID()) {
							last = i;
							break;
						}
					}
					Log.d(TAG,"last="+last);
					SharedPreferences settings = myActivity.getPreferences(Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = settings.edit();
					editor.putInt(QueryNotUploadFragment.LAST_SELECTED_POSITION_KEY, last);
					editor.commit();
					// switch to Home page (QueryNotUploadFragment)
					MainMenuFragment mmf = QueryNotUploadFragment.newInstance();
					myActivity.showMainMenuFragment(mmf);
					synchronized(myActivity) {
						myActivity.setNeedBackup(true);
						myActivity.notify();
					}

				}
			}

			private boolean check(String oName) {
				Log.d(TAG, "Check add duplicate customer");
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


				if(c2.getCount()==2){

					Cursor c=db.query(true,
							"Orders,"+coustomersStr,				//��ƪ�W��
							new String[] {"ShortName",coustomersStr+".CustomerID","Orders.OrderID"},	//���W��
							coustomersStr+".CustomerID = Orders.CustomerID AND Orders.UserNO = '"+myActivity.omCurrentAccount.getAccountNo()+
									" AND Orders.CompanyParent = '"+myActivity.omCurrentSysProfile.getCompanyNo()+"';",			//WHERE
							null, // WHERE ���Ѽ�
							null, // GROUP BY
							null, // HAVING
							null, // ORDOR BY
							null  // ����^�Ǫ�rows�ƶq
					);

					if (c.getCount()>0) {
						c.moveToFirst();
						if(oName.equals(c.getString(0))){
							return false;
						}
						while(c.moveToNext()){
							if(oName.equals(c.getString(0))){
								return false;
							}
						}
					}
				}else{

					return true;
				}

				return true;
			}

		});

		RelativeLayout cancelBtn = (RelativeLayout)view.findViewById(R.id.addvisitcustomer_button3);
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// switch to Home page (QueryNotUploadFragment)
				MainMenuFragment mmf = QueryNotUploadFragment.newInstance();
				myActivity.showMainMenuFragment(mmf);
			}

		});

		Button bn4 = (Button)view.findViewById(R.id.addvisitcustomer_button4);
		bn4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				et1.setText("");
			}

		});

		adapter = new AddVisitCustomerDataAdapter(myActivity,DBAdapter);
		adapter.gatherData(myActivity.omCurrentAccount.getAccountNo(),String.valueOf(myActivity.currentDept.getCompanyID()),"Y","1");
		// set adapter
		lv.setAdapter(adapter);
		initSetToPreSelected();
		TextView qTV1 = (TextView)view.findViewById(R.id.addvisitcustomer_header_textView1);
		qTV1.setOnClickListener(this);
		qTV1.setOnLongClickListener(this);
		TextView qTV2 = (TextView)view.findViewById(R.id.addvisitcustomer_header_textView2);
		qTV2.setOnClickListener(this);
		qTV2.setOnLongClickListener(this);
		// set user defined header sequence
		ViewGroup vg = (ViewGroup)qTV1.getParent();
		vg.removeAllViews();
		LinkedList<Integer> sequences = adapter.getSequences();
		for(Iterator<Integer> ir=sequences.iterator();ir.hasNext();) {
			int seq = ir.next();
			switch(seq) {
				case 0:
					vg.addView(qTV1);
					break;
				case 1:
					vg.addView(qTV2);
					break;
			}
		}

		// set user defined header width
		TreeMap<Integer,Integer> columns = adapter.getColumns();
		for(Iterator<Integer> ir = columns.keySet().iterator();ir.hasNext();) {
			int columnNO = ir.next();
			int width = columns.get(columnNO);
			if(width == 0) continue;
			switch(columnNO) {
				case 0:
					LinearLayout.LayoutParams params0 = (LinearLayout.LayoutParams)qTV1.getLayoutParams();
					params0.width = width;
					break;
				case 1:
					LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams)qTV2.getLayoutParams();
					params1.width = width;
					break;
			}

		}
		Log.d(TAG,"getHeaderViewsCount="+lv.getHeaderViewsCount());
		final int HeaderViewsCount =lv.getHeaderViewsCount();
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1, int position,long id) {
				Log.d(TAG,"onItemClick index="+position);
				tv1.setText("確認");
				if(lastSelectedPosition != -1 && lastSelectedPosition<adapter.getCount()) {
					((AddVisitCustomerView)adapter.getItem(lastSelectedPosition)).setActivated(false);
				}
				lastSelectedPosition = position-HeaderViewsCount;
				if(lastSelectedPosition<adapter.getCount()) ((AddVisitCustomerView)adapter.getItem(lastSelectedPosition)).setActivated(true);
				adapter.notifyDataSetChanged();
			}});

	}

	@Override
	public void onClick(View v) {
		TextView tv = (TextView)v;
		Log.d(Constant.TAG,"onClick index="+tv.getText()+tv.getContentDescription()+",width="+tv.getWidth());
		int newWidth = tv.getWidth()+Constant.ZOOM_INCREMENTAL;
		adapter.setColumnWidth(Integer.parseInt(tv.getContentDescription().toString()),newWidth);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(newWidth, tv.getHeight());
		tv.setLayoutParams(params);
	}

	@Override
	public boolean onLongClick(View v) {
		TextView tv = (TextView)v;
		Log.d(Constant.TAG,"onLongClick index="+tv.getText()+tv.getContentDescription()+",width="+tv.getWidth());
		// �]�w LayoutParams�A��Jtag���A�H�Kdrag event�A�ϥ�
		tv.setTag(adapter);
		// start drag for this view, no ClipData need
		tv.startDrag(null, new DragShadowBuilder(v), (Object)v, 0);
		return true;
	}

	private void initSetToPreSelected() {
		if(myActivity.isSND) {
			if(omQNU != null) {
				Log.d(TAG,"CustType="+omQNU.getCustType());
				Log.d(TAG,"BeVisit="+omQNU.getBeVisit());
				if(omQNU.getCustType()==2) {
					if(omQNU.getBeVisit().equals(Customers.BEVISIT_Y)) {
						rb2.setChecked(true);
						rb0.setChecked(true);
					} else {
						rb1.setChecked(true);
					}
				} else {
					rb2.setChecked(true);
				}
			} else {
				rb2.setChecked(true);
			}
		} else {
			initSetToPreSelectedCommmon();
		}

	}

	private void initSetToPreSelectedCommmon() {
		if(omQNU != null) {
			int activatedItemIndex = -1;
			for(int i=0;i<adapter.getCount();i++) {
				AddVisitCustomerView omAVC = (AddVisitCustomerView)adapter.getItem(i);
				if(omAVC.getCustomerNO().equals(omQNU.getCustomerNO())
						&& ((omAVC.getDeliverOrder()==null && omQNU.getDeliverOrder()==null) ||
						(omAVC.getDeliverOrder()!=null && omQNU.getDeliverOrder()!=null && omAVC.getDeliverOrder().intValue()==omQNU.getDeliverOrder()))) {
					activatedItemIndex = i;
					omAVC.setActivated(true);
					break;
				}
			}
			if(activatedItemIndex != -1) {
				lastSelectedPosition = activatedItemIndex;
				lv.setSelection(activatedItemIndex);
				omQNU = null;
			} else {
				lastSelectedPosition = -1; // 2015/01/26 by MOU
				rb1.setChecked(true); // ��쥼�W���Ȥ�

			}
		} else {
			lastSelectedPosition = -1; // 2015/01/26 by MOU
			if(adapter.getCount()==0) {
				rb1.setChecked(true); // ��쥼�W���Ȥ�
			}
		}

	}


}
