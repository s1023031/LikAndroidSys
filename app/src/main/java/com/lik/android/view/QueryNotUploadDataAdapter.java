/*package com.lik.android.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import com.lik.Constant;
import com.lik.android.LikDBAdapter;
import com.lik.android.MainMenuActivity;
import com.lik.android.MainMenuFragment;
import com.lik.android.R;
import com.lik.android.om.Customers;
import com.lik.android.om.Orders;
import com.lik.android.om.Phrase;
import com.lik.android.om.SysProfile;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class QueryNotUploadDataAdapter extends BaseDataAdapter<QueryNotUploadView> {
	
	protected static final String TAG = QueryNotUploadDataAdapter.class.getName();
	private static final int COLUMN_SIZE = 9;
	private boolean hasMapFunction = false;
	private boolean hasTelephoneFunction = false;
	private MainMenuActivity context;
	private TreeMap<String,String> tmPDA8;
	private TelephonyManager telephonyManager;
	TreeMap<String,String> tmKind1;
	boolean isFTN = false;
	boolean isSND = false;
	boolean isWHU = false;
	boolean isYLN = false;

	public QueryNotUploadDataAdapter(MainMenuActivity context, List<QueryNotUploadView> data) {
		super(context,data);
		init(COLUMN_SIZE);
		isFTN = context.isFTN;
		isSND = context.isSND;
		isWHU = context.isWHU;
		isYLN = context.isYLN;
		if(context.omCurrentSysProfile.getMapInfo().equals(SysProfile.MAPINFO_Y)) {
			hasMapFunction = true;
		}
		if(context.omCurrentSysProfile.getTelephoneInfo().equals(SysProfile.TELEPHONEINFO_Y)) {
			telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//			String ableToMakePhoneCalls = telephonyManager.getVoiceMailNumber(); //check device for voicemail number (null means no voicemail number).
//			if(ableToMakePhoneCalls != null) hasTelephoneFunction = true;
			if(telephonyManager.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE) hasTelephoneFunction = true;
		}
		MainMenuFragment mmf = (MainMenuFragment)context.getFragmentManager().findFragmentById(R.id.main_frameLayout1);
		tmPDA8 = mmf.getKindMap(Phrase.PHKINDNO_8);
		tmKind1 = mmf.getKindMap(Phrase.PHKINDNO_1);
		this.context = context;
	}

	public QueryNotUploadDataAdapter(MainMenuActivity context, LikDBAdapter DBAdapter) {
		super(context, DBAdapter);
		init(COLUMN_SIZE);
		isFTN = context.isFTN;
		isSND = context.isSND;
		isWHU = context.isWHU;
		isYLN = context.isYLN;
		if(context.omCurrentSysProfile.getMapInfo().equals(SysProfile.MAPINFO_Y)) {
			hasMapFunction = true;
		}
		if(context.omCurrentSysProfile.getTelephoneInfo().equals(SysProfile.TELEPHONEINFO_Y)) {
			telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//			String ableToMakePhoneCalls = telephonyManager.getVoiceMailNumber(); //check device for voicemail number (null means no voicemail number).
//			if(ableToMakePhoneCalls != null) hasTelephoneFunction = true;
			if(telephonyManager.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE) hasTelephoneFunction = true;
		}
		MainMenuFragment mmf = (MainMenuFragment)context.getFragmentManager().findFragmentById(R.id.main_frameLayout1);
		tmPDA8 = mmf.getKindMap(Phrase.PHKINDNO_8);
		tmKind1 = mmf.getKindMap(Phrase.PHKINDNO_1);
		this.context = context;
	}
		
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if(view == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.querynotupload_row, null);
			ViewHolder holder = new ViewHolder();
			holder.tv[0] = (TextView)view.findViewById(R.id.querynotupload_row_textView1);
			holder.tv[1] = (TextView)view.findViewById(R.id.querynotupload_row_textView2);
			holder.tv[2] = (TextView)view.findViewById(R.id.querynotupload_row_textView3);
			holder.tv[3] = (TextView)view.findViewById(R.id.querynotupload_row_textView4);
			holder.tv[4] = (TextView)view.findViewById(R.id.querynotupload_row_textView5);
			holder.tv[5] = (TextView)view.findViewById(R.id.querynotupload_row_textView6);
			holder.tv[6] = (TextView)view.findViewById(R.id.querynotupload_row_textView7);
			holder.tv[7] = (TextView)view.findViewById(R.id.querynotupload_row_textView8);
			holder.tv[8] = (TextView)view.findViewById(R.id.querynotupload_row_textView9);
			view.setTag(holder);
		}
		final ViewHolder holder = (ViewHolder) view.getTag();
		// 設定user defined order
		ViewGroup vg = (ViewGroup)holder.tv[0].getParent();
		vg.removeAllViews();
		for(Iterator<Integer> ir=sequences.iterator();ir.hasNext();) {
			int seq = ir.next();
			vg.addView(holder.tv[seq]);
		}
		// 設定user defined LayoutParams
		for(Iterator<Integer> ir = columns.keySet().iterator();ir.hasNext();) {
			int columnNO = ir.next();
			int width = columns.get(columnNO);
			if(width == 0) continue;
			LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)holder.tv[columnNO].getLayoutParams();
			params.width = width;			
		}
		final String tel = data.get(position).getTel1();
		if(hasTelephoneFunction) {
			holder.tv[3].setOnClickListener(new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					if(tel==null) return;
					if(telephonyManager.getSimState()==TelephonyManager.SIM_STATE_READY) {
						Intent callIntent = new Intent(Intent.ACTION_CALL);
						callIntent.setData(Uri.parse("tel:"+tel));
						context.startActivity(callIntent);
					} else {
						Toast.makeText(context,context.getString(R.string.Message36), Toast.LENGTH_LONG).show();    		
					}
				}
				
			});
		}
		if(hasMapFunction) holder.tv[4].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MainMenuFragment mmf = MapFragment.newInstance(R.id.mainmenu_item57);
				Bundle bundle = mmf.getArguments();
				bundle.putSerializable(MapFragment.CUSTOMER_BUNDLE_KEY, data.get(holder.position));
				context.showMainMenuFragment(mmf);
				
			}
			
		});
		holder.tv[6].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG,"item is clicked! "+((TextView)v).getText()+",index="+holder.position);
				String title = context.getResources().getString(R.string.querynotuploadheaderTextView6);
				String msg = context.getResources().getString(R.string.querynotuploadDialogMessage);
				final DatePickerDialog dialog = getDatePickerDialogForInput(title,msg,v,holder.position);
				dialog.setTitle(title);
				dialog.setMessage(msg);
				dialog.show();
			}
			
		});
		if(!isYLN) {
			holder.tv[7].setOnClickListener(new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					Log.d(TAG,"item is clicked! "+((TextView)v).getText()+",index="+holder.position);
					String title = context.getResources().getString(R.string.querynotuploadheaderTextView7);
					String msg = context.getResources().getString(R.string.querynotuploadDialogMessage);
					final AlertDialog dialog = getAlertDialogForInput(title,msg,v,holder.position);
					dialog.show();
				}
				
			});
		}
		holder.tv[8].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG,"item is clicked! "+((TextView)v).getText()+",index="+holder.position);
				String title = context.getResources().getString(R.string.querynotuploadheaderTextView8);
				String msg = context.getResources().getString(R.string.querynotuploadDialogMessage);
				final AlertDialog dialog = getAlertDialogForInputCustomerStock(title,msg,v,holder.position);
				dialog.show();
			}
			
		});

		if(tmPDA8!=null && tmPDA8.get(Phrase.PHPHRASENO_ID)!=null && tmPDA8.get(Phrase.PHPHRASENO_ID).equals(Phrase.PHRASE_DESC_SHD)) {
			holder.tv[0].setText(data.get(position).getFullName());			
		} else {
			holder.tv[0].setText(data.get(position).getShortName());
		}
		if(isWHU) holder.tv[0].setText(data.get(position).getFullName());		
		if(isFTN) {
			holder.tv[0].setText(holder.tv[0].getText()+" ("+tmKind1.get(data.get(position).getPayType())+")");
		}
		holder.tv[1].setText(data.get(position).getCustomerNO());
		holder.tv[2].setText(data.get(position).getBackRate()==null?"":data.get(position).getBackRate()+"%");
		if(isFTN || isSND) {
			holder.tv[2].setVisibility(View.GONE);
		}
		holder.tv[3].setText(data.get(position).getTel1());
		if(hasTelephoneFunction) holder.tv[3].setTextColor(context.getResources().getColor(R.color.blue));
		String datenull = context.getResources().getString(R.string.querynotuploadDialogMessage);
		holder.tv[4].setText(data.get(position).getAddress());
		//if(hasMapFunction) holder.tv[4].setVisibility(View.VISIBLE);
		if(hasMapFunction) holder.tv[4].setTextColor(context.getResources().getColor(R.color.blue));
		if(data.get(position).getOrderDT() != null) holder.tv[5].setText(Constant.getDateFormat(data.get(position).getOrderDT(), dateFormat));
		else holder.tv[5].setText(" ");
		if(data.get(position).getSellDT() != null) holder.tv[6].setText(Constant.getDateFormat(data.get(position).getSellDT(), dateFormat));
		else holder.tv[6].setText(datenull);
		holder.tv[7].setText(data.get(position).getPayNextMonth()==null?"N":"Y");
		if(data.get(position).getUploadFlag().equals(Orders.UPLOADFLAG_Y)) {
			view.setBackgroundResource(R.color.orange);
			holder.tv[6].setEnabled(false);
			holder.tv[7].setEnabled(false);
		} else {
			view.setBackgroundResource(R.color.mistyrose);
			holder.tv[6].setEnabled(true);
			holder.tv[7].setEnabled(true);
		}
		holder.tv[8].setText(data.get(position).getCustomerStock()==null?"N":"Y");
		if(tmPDA8!=null && tmPDA8.get(Phrase.PHPHRASENO_ID)!=null && tmPDA8.get(Phrase.PHPHRASENO_ID).equals(Phrase.PHRASE_DESC_SHD)) {
			holder.tv[8].setVisibility(View.VISIBLE);
		}
		
		if(data.get(position).isSelected()) {
			Log.d(TAG,"position="+position+" selected!");
			holder.tv[0].setActivated(true);
			holder.tv[1].setActivated(true);
			holder.tv[2].setActivated(true);
			holder.tv[3].setActivated(true);
			holder.tv[4].setActivated(true);
			holder.tv[5].setActivated(true);
			holder.tv[6].setActivated(true);
			holder.tv[7].setActivated(true);
			holder.tv[8].setActivated(true);
		} else {
			holder.tv[0].setActivated(false);
			holder.tv[1].setActivated(false);
			holder.tv[2].setActivated(false);
			holder.tv[3].setActivated(false);
			holder.tv[4].setActivated(false);
			holder.tv[5].setActivated(false);
			holder.tv[6].setActivated(false);
			holder.tv[7].setActivated(false);
			holder.tv[8].setActivated(false);
		}
		holder.position = position;
		return view;
	}

	@Override
	public void gatherData(String... args) {
		data =  new ArrayList<QueryNotUploadView>();
		Orders omO = new Orders();
		omO.setUserNO(args[0]);
		if(args.length ==2) omO.setCompanyID(Integer.parseInt(args[1]));
		Log.d(TAG,"getOrdersByUserNO="+omO.getUserNO());
		// 若非即時庫存，只撈未上傳的
		if(context instanceof MainMenuActivity) {
			String stockInfo = ((MainMenuActivity)context).omCurrentSysProfile.getStockInfo();
			if(!stockInfo.equals(SysProfile.STOCKINFO_R)) {
				omO.setUploadFlag(Orders.UPLOADFLAG_N);
			}
		}
		List<Orders> ltO = omO.getOrdersByUserNO(DBAdapter);
		for(Iterator<Orders> ir=ltO.iterator();ir.hasNext();) {
			Orders om = ir.next();
			// 若uploadFlag=true, 則只有當天才要show
			if(om.getUploadFlag()!=null && om.getUploadFlag().equals(Orders.UPLOADFLAG_Y) && om.getLastDT()!=null) {
				boolean isToday = Constant.sdf2d.format(om.getLastDT()).equals(Constant.sdf2d.format(MainMenuActivity.getCurrentDate()));
				if(!isToday) continue;
			}
			QueryNotUploadView omQNV = new QueryNotUploadView();
			Customers omC = new Customers();
			omC.setUserNO(om.getUserNO());
			omC.setCompanyID(om.getCompanyID());
			omC.setCustomerID(om.getCustomerID());
			if(isSND) omC.setDeliverOrder(om.getDeliverViewOrder());
			omC.getCustomersByKey(DBAdapter);
			if(omC.getRid()<0) {
				Log.e(TAG,"UserNO:CompanyID:CustomerID"+omC.getUserNO()+":"+omC.getCompanyID()+":"+omC.getCustomerID());
			}
			omQNV.setSerialID(om.getSerialID());
			omQNV.setShortName(omC.getShortName());
			omQNV.setFullName(omC.getFullName());
			omQNV.setTel1(omC.getTel1());
			omQNV.setAddress(omC.getAddress());
			omQNV.setPayNextMonth(om.getPayNextMonth());
			omQNV.setOrderDT(om.getOrderDT());
			omQNV.setSellDT(om.getSellDT());
			omQNV.setLastDT(om.getLastDT());
			omQNV.setCustomerNO(omC.getCustomerNO());
			omQNV.setCustomerID(omC.getCustomerID());
			omQNV.setNote1(om.getNote1());
			omQNV.setNote2(om.getNote2());
			omQNV.setPayType(omC.getPayType());
			omQNV.setPriceGrade(omC.getPriceGrade());
			omQNV.setUploadFlag(om.getUploadFlag());
			omQNV.setReplyFlag(om.getReplyFlag());
			omQNV.setOrderID(om.getOrderID());
			omQNV.setSettleDay(omC.getSettleDay());
			omQNV.setRevCash_Disrate(omC.getRevCash_Disrate());
			omQNV.setPromoteGroupID(omC.getPromoteGroupID());
			omQNV.setCustomerStock(om.getCustomerStock());
			omQNV.setSalesName(omC.getSalesName());
			omQNV.setSalesNO(omC.getSalesNO());
			omQNV.setIsLimit(omC.getIsLimit());
			Log.d(TAG,"omC.getIsLimit()="+omC.getIsLimit());
			omQNV.setDeliveryWay(omC.getDeliveryWay());
			omQNV.setRequestDay(omC.getRequestDay());
			omQNV.setCheckDay(omC.getCheckDay());
			omQNV.setVisitLine(omC.getVisitLine());
			omQNV.setNoReturn(omC.getNoReturn());
			omQNV.setCustType(omC.getCustType());
			omQNV.setBeVisit(omC.getBeVisit());
			omQNV.setDeliverOrder(omC.getDeliverOrder());
			omQNV.setDirtyPay(omC.getDirtyPay());
			omQNV.setBillDays(omC.getBillDays());
			omQNV.setBackRate(omC.getBackRate());
			Log.d(TAG,"omC.getBackRate()="+omC.getBackRate());
			data.add(omQNV);
		}
	}
	
    private AlertDialog getAlertDialogForInput(String title, String message, final View v, final int position) {
    	Builder builder = new AlertDialog.Builder(context);
    	builder.setTitle(title);
    	builder.setMessage(message);
    	final String msgY = context.getResources().getString(R.string.querynotuploadDialogChoiceY);
    	builder.setPositiveButton(msgY, new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			// 設定TextView
    			TextView tv = (TextView)v;
    			tv.setText(msgY);
    			// 設定data
    			data.get(position).setPayNextMonth(1);
    			// update to DB
    			Orders om = new Orders();
    			om.setSerialID(data.get(position).getSerialID());
    			om.getOrdersBySerialID(DBAdapter);
    			om.setPayNextMonth(1);
    			om.updateOrders(DBAdapter);
    			if(om.getRid() >= 0) {
    				Log.d(TAG,"update Orders success!, serialID="+om.getSerialID()); 
    				notifyDataSetChanged();
    			}
    		}
    	});
    	final String msgN = context.getResources().getString(R.string.querynotuploadDialogChoiceN);
    	builder.setNegativeButton(msgN, new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			// 設定TextView
    			TextView tv = (TextView)v;
    			tv.setText(msgN);
    			// 設定data -- leave NULL    			
    			data.get(position).setPayNextMonth(null);
    			// update to DB
    			Orders om = new Orders();
    			om.setSerialID(data.get(position).getSerialID());
    			om.getOrdersBySerialID(DBAdapter);
    			om.setPayNextMonth(null);
    			om.updateOrders(DBAdapter);
    			if(om.getRid() >= 0) {
    				Log.d(TAG,"update Orders success!, serialID="+om.getSerialID());
    				notifyDataSetChanged();
    			}
    			
    		}
    	});
    	return builder.create();
    }
    
    private DatePickerDialog getDatePickerDialogForInput(String title,String message, final View v, final int position) {
    	DatePickerDialog.OnDateSetListener myDateSetListener= new DatePickerDialog.OnDateSetListener(){	
	    	@Override
		    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
	    		TextView tv = (TextView)v;
	    		Log.d(TAG,"original date="+tv.getText());
	            Calendar c = Calendar.getInstance();
	            c.set(Calendar.YEAR, year);
	            c.set(Calendar.MONTH, monthOfYear);
	            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
	            Date date = c.getTime();
	    		Log.d(TAG,"set date="+Constant.getDateFormat(date, dateFormat));
	    		// 設定view
	    		tv.setText(Constant.getDateFormat(date, dateFormat));
	    		// 設定data
	    		data.get(position).setSellDT(date);
	    		// update to DB
    			Orders om = new Orders();
    			om.setSerialID(data.get(position).getSerialID());
    			om.getOrdersBySerialID(DBAdapter);
    			om.setSellDT(date);
    			om.updateOrders(DBAdapter);
    			if(om.getRid() >= 0) {
    				Log.d(TAG,"update Orders success!, serialID="+om.getSerialID()); 
    				notifyDataSetChanged();
    			}
		   }
        };
        TextView tv = (TextView)v;
        Date date = MainMenuActivity.getCurrentDate();
        if(tv.getText() != null) {
        	Date tmp = Constant.getDateFromFormat(tv.getText().toString(), dateFormat);
        	if(tmp != null) date = tmp;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
	    return new DatePickerDialog(context, myDateSetListener, year, month, day);
    }

    private AlertDialog getAlertDialogForInputCustomerStock(String title, String message, final View v, final int position) {
    	Builder builder = new AlertDialog.Builder(context);
    	builder.setTitle(title);
    	builder.setMessage(message);
    	final String msgY = context.getResources().getString(R.string.querynotuploadDialogChoiceY);
    	builder.setPositiveButton(msgY, new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			// 設定TextView
    			TextView tv = (TextView)v;
    			tv.setText(msgY);
    			// 設定data
    			data.get(position).setCustomerStock(1);
    			// update to DB
    			Orders om = new Orders();
    			om.setSerialID(data.get(position).getSerialID());
    			om.getOrdersBySerialID(DBAdapter);
    			om.setCustomerStock(1);
    			om.updateOrders(DBAdapter);
    			if(om.getRid() >= 0) {
    				Log.d(TAG,"update Orders success!, serialID="+om.getSerialID()); 
    				notifyDataSetChanged();
    			}
    		}
    	});
    	final String msgN = context.getResources().getString(R.string.querynotuploadDialogChoiceN);
    	builder.setNegativeButton(msgN, new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			// 設定TextView
    			TextView tv = (TextView)v;
    			tv.setText(msgN);
    			// 設定data -- leave NULL    			
    			data.get(position).setCustomerStock(null);
    			// update to DB
    			Orders om = new Orders();
    			om.setSerialID(data.get(position).getSerialID());
    			om.getOrdersBySerialID(DBAdapter);
    			om.setCustomerStock(null);
    			om.updateOrders(DBAdapter);
    			if(om.getRid() >= 0) {
    				Log.d(TAG,"update Orders success!, serialID="+om.getSerialID());
    				notifyDataSetChanged();
    			}
    			
    		}
    	});
    	return builder.create();
    }

    private static class ViewHolder {  
        public TextView[] tv = new TextView[COLUMN_SIZE];  
        public int position;
    }  
}
*/