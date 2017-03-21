package com.lik.android.view;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import com.lik.android.main.R;
import com.lik.android.main.LikDBAdapter;
import com.lik.android.main.MainMenuActivity;
import com.lik.android.main.MainMenuFragment;
import com.lik.android.om.Customers;
import com.lik.android.om.Phrase;
import com.lik.android.om.TemporaryCredit;
import com.lik.Constant;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class AddVisitCustomerDataAdapter extends BaseDataAdapter<AddVisitCustomerView> {
	
	protected static final String TAG = AddVisitCustomerDataAdapter.class.getName();
	private static final int COLUMN_SIZE = 2;
	private TreeMap<String,String> tmPDA8;
	private boolean isHEB = false;
	private boolean isSND = false;
	private boolean isSHD = false;
	private boolean isWHU = false;
	private boolean isYLN = false;
	MainMenuActivity myActivity;
	DisplayFeeDataAdapter dfadapter;

	public AddVisitCustomerDataAdapter(MainMenuActivity context, List<AddVisitCustomerView> data) {
		super(context,data);
		init(COLUMN_SIZE);
		MainMenuFragment mmf = (MainMenuFragment)context.getFragmentManager().findFragmentById(R.id.main_frameLayout1);
		//tmPDA8 = mmf.getKindMap(Phrase.PHKINDNO_8);
		isHEB = context.isHEB;
		isSND = context.isSND;
		isSHD = context.isSHD;
		isWHU = context.isWHU;
		isYLN = context.isYLN;
		this.myActivity = context;
		dfadapter = new DisplayFeeDataAdapter(myActivity,DBAdapter);
	}

	public AddVisitCustomerDataAdapter(MainMenuActivity context, LikDBAdapter DBAdapter) {
		super(context, DBAdapter);
		init(COLUMN_SIZE);
		MainMenuFragment mmf = (MainMenuFragment)context.getFragmentManager().findFragmentById(R.id.main_frameLayout1);
		//tmPDA8 = mmf.getKindMap(Phrase.PHKINDNO_8);
		isHEB = context.isHEB;
		isSND = context.isSND;
		isSHD = context.isSHD;
		isWHU = context.isWHU;
		isYLN = context.isYLN;
		this.myActivity = context;
		dfadapter = new DisplayFeeDataAdapter(myActivity,DBAdapter);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if(view == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.addvisitcustomer_row, null);
			ViewHolder holder = new ViewHolder();
			holder.tv[0] = (TextView)view.findViewById(R.id.addvisitcustomer_row_textView1);
			holder.tv[1] = (TextView)view.findViewById(R.id.addvisitcustomer_row_textView2);
/*			holder.tv[2] = (TextView)view.findViewById(R.id.addvisitcustomer_row_textView3);
			holder.tv[3] = (TextView)view.findViewById(R.id.addvisitcustomer_row_textView4);
			holder.tv[4] = (TextView)view.findViewById(R.id.addvisitcustomer_row_textView5);
			holder.tv[5] = (TextView)view.findViewById(R.id.addvisitcustomer_row_textView6);
			holder.tv[6] = (TextView)view.findViewById(R.id.addvisitcustomer_row_textView7);
			holder.tv[7] = (TextView)view.findViewById(R.id.addvisitcustomer_row_textView8);
			holder.tv[8] = (TextView)view.findViewById(R.id.addvisitcustomer_row_textView9);
			holder.tv[9] = (TextView)view.findViewById(R.id.addvisitcustomer_row_textView10);
			holder.tv[10] = (TextView)view.findViewById(R.id.addvisitcustomer_row_textView11);
			holder.tv[11] = (TextView)view.findViewById(R.id.addvisitcustomer_row_textView12);*/
			view.setTag(holder);
			Log.d(TAG, "doremi="+holder.tv.length);
			
		}
		
		ViewHolder holder = (ViewHolder) view.getTag();

		ViewGroup vg = (ViewGroup)holder.tv[0].getParent();
		vg.removeAllViews();
		for(Iterator<Integer> ir=sequences.iterator();ir.hasNext();) {
			int seq = ir.next();
			Log.d(TAG, "check="+holder.tv.length);
			vg.addView(holder.tv[seq]);
		}

		// �]�wuser defined LayoutParams
		for(Iterator<Integer> ir = columns.keySet().iterator();ir.hasNext();) {
			int columnNO = ir.next();
			int width = columns.get(columnNO);
			if(width == 0) continue;
			LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)holder.tv[columnNO].getLayoutParams();
			params.width = width;			
		}
		Log.d(TAG, "");
		if(tmPDA8!=null && tmPDA8.get(Phrase.PHPHRASENO_ID)!=null && tmPDA8.get(Phrase.PHPHRASENO_ID).equals(Phrase.PHRASE_DESC_SHD)) {
			holder.tv[0].setText(data.get(position).getFullName());
		} else {
			holder.tv[0].setText(data.get(position).getShortName());
			
		}
		if(isWHU) 
			holder.tv[0].setText(data.get(position).getFullName());
		
		holder.tv[1].setText(data.get(position).getCustomerNO());
/*		holder.tv[1].setText(data.get(position).getTel1());
		holder.tv[2].setText(data.get(position).getCustomerNO());
		holder.tv[3].setText(data.get(position).getActTel());*/
		//Log.d(TAG, "data.get(position).getAmt()="+data.get(position).getAmt());
/*		if(data.get(position).getAmt()>0) {
			holder.tv[4].setText(String.valueOf(data.get(position).getAmt()));
			holder.tv[4].setTag(data.get(position).getTemporaryCredit());
			holder.tv[4].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Log.d(TAG,"view="+((TextView)v).getText());
					AlertDialog dialog = getAlertDialogForDisplayFee((List<TemporaryCredit>)v.getTag());
					dialog.show();
				}
				
			});
		}
		else
			holder.tv[4].setText(null);*/
/*		holder.tv[5].setText(data.get(position).getDeliveryWay());
		holder.tv[6].setText(data.get(position).getRequestDay());
		holder.tv[7].setText(String.valueOf(data.get(position).getSettleDay()));
		holder.tv[8].setText(data.get(position).getCheckDay());
		holder.tv[9].setText(data.get(position).getVisitLine());
		holder.tv[10].setText(data.get(position).getDeliverOrder()==null?"":String.valueOf(data.get(position).getDeliverOrder()));
		holder.tv[11].setText(data.get(position).getAddress());*/
/*		if(isHEB) {
			holder.tv[3].setVisibility(View.GONE);
			holder.tv[4].setVisibility(View.VISIBLE);
			holder.tv[5].setVisibility(View.VISIBLE);
			holder.tv[6].setVisibility(View.VISIBLE);
			holder.tv[7].setVisibility(View.VISIBLE);
			holder.tv[8].setVisibility(View.VISIBLE);
			holder.tv[9].setVisibility(View.VISIBLE);
		}
		if(isSND) {
			holder.tv[3].setVisibility(View.GONE);
			holder.tv[10].setVisibility(View.VISIBLE);
			holder.tv[11].setVisibility(View.VISIBLE);
		}
		if(isYLN) {
			if(data.get(position).getDirtyPay().equals(Customers.DIRTYPAY_Y)) {
				view.setBackgroundResource(R.color.salmon);
			} else {
				view.setBackgroundResource(R.color.wwhite);
			}
		}*/
		
		if(data.get(position).isActivated()) {
			for(int i=0;i<COLUMN_SIZE;i++) {
				holder.tv[i].setActivated(true);
			}
		} else {
			for(int i=0;i<COLUMN_SIZE;i++) {
				holder.tv[i].setActivated(false);
			}
		}

		return view;
	}

	/**
	 * args[0]: UserNO
	 * args[1]: CompanyID
	 * args[2]: BeVisit
	 * args[3]: CustType
	 * args[4]: ShortName
	 * args[5]: CustomerNO
	 */
	@Override
	public void gatherData(String... args) {
		data =  new ArrayList<AddVisitCustomerView>();
		List<Customers> ltC = null;
		Customers omC = new Customers();
		switch(args.length) {
			case 4:
				omC.setUserNO(args[0]);
				omC.setCompanyID(Integer.parseInt(args[1]));
				omC.setBeVisit(args[2]);				
				//omC.setCustType(args[3]==null?0:Integer.parseInt(args[3]));
				omC.setCompanyParent(myActivity.omCurrentSysProfile.getCompanyNo());
				omC.setTableCompanyID(myActivity.companyID);
				Log.d(TAG, "abcde="+myActivity.omCurrentSysProfile.getCompanyNo()+" TableCompanyID="+omC.getTableCompanyID());
				ltC = omC.getCustomersByUserNoCompanyID(DBAdapter);
				Log.d(TAG,"case 4 called!"+ltC.size());
				break;
			case 5:
				omC.setUserNO(args[0]);
				omC.setShortName(args[4]);	
				ltC = omC.searchByKeyWord(DBAdapter);
				Log.d(TAG,"case 5 called!"+ltC.size());
				break;
			case 6:
				omC.setUserNO(args[0]);
				omC.setCustomerNO(args[5]);
				ltC = omC.searchByKeyWord(DBAdapter);
				Log.d(TAG,"case 6 called!"+ltC.size());
				break;
			default:
				return;
		}
		if(ltC.size() > 0)
		{
			for(Iterator<Customers> ir=ltC.iterator();ir.hasNext();) {
				Customers om = ir.next();			
				AddVisitCustomerView omCV = new AddVisitCustomerView();
				omCV.setSerialID(om.getSerialID());
				omCV.setShortName(om.getShortName());
				omCV.setFullName(om.getFullName());
				omCV.setTel1(om.getTel1());
				omCV.setActTel(om.getActTel());
				omCV.setCustomerNO(om.getCustomerNO());
				omCV.setCustomerID(om.getCustomerID());
				omCV.setSalesID(om.getSalesID());
				
				/*if(isHEB) {
					TemporaryCredit omTC = new TemporaryCredit();
					omTC.setCompanyID(Integer.parseInt(args[1]));
					omTC.setCustomerID(om.getCustomerID());
					omTC.setUserNo(args[0]);
					List<TemporaryCredit> lt = omTC.findByCustomerID(DBAdapter);
					Log.d(TAG,"lt.size()="+lt.size());
					if(lt.size()>0) {
						omCV.setTemporaryCredit(lt);
						double amt = 0;
						for(TemporaryCredit omtc:lt) {
							amt += omtc.getAccountAmount();
						}
						omCV.setAmt(amt);
					}
				}*/
				data.add(omCV);
			}
		}else
		{
			AddVisitCustomerView omCV = new AddVisitCustomerView();
			omCV.setSerialID(-1);
			data.add(omCV);
		}
			
		
	
		// for SND, ���P����B�Ƨ�
		if(isSND) {
			TreeSet<AddVisitCustomerView> ts = new TreeSet<AddVisitCustomerView>(new Comparator<AddVisitCustomerView>() {

				@Override
				public int compare(AddVisitCustomerView lhs,AddVisitCustomerView rhs) {
					String l = lhs.getSellAmount()>=rhs.getSellAmount()?"0":"1";
					String r = lhs.getSellAmount()<=rhs.getSellAmount()?"0":"1";
					String lno = lhs.getCustomerNO();
					String rno = rhs.getCustomerNO();
					if(lno.length()>rno.length()) {
						for(int i=0;i<lno.length()-rno.length();i++) {
							rno += "0";
						}
					} else if(lno.length()<rno.length()) {
						for(int i=0;i<rno.length()-lno.length();i++) {
							lno += "0";
						}
					}
					l += lno+(lhs.getDeliverOrder()==null?"":lhs.getDeliverOrder());
					r += rno+(rhs.getDeliverOrder()==null?"":rhs.getDeliverOrder());
					return l.compareTo(r);
				}
				
			});
			for(AddVisitCustomerView om:data) {
				ts.add(om);
			}
			data.clear();
			for(AddVisitCustomerView om:ts) {
				data.add(om);
			}
		}
	}
	
 	protected AlertDialog getAlertDialogForDisplayFee(List<TemporaryCredit> lt) {
		Builder builder = new AlertDialog.Builder(myActivity);
		final String title = myActivity.getResources().getString(R.string.addvisitcustomerheaderTextView5);
		final String msgY = myActivity.getResources().getString(R.string.Button1);
		builder.setTitle(title);
		LayoutInflater inflator = myActivity.getLayoutInflater();
		View view = inflator.inflate(R.layout.displayfee, null);
		final ListView lv = (ListView)view.findViewById(R.id.displayfee_listView1);
		dfadapter.setTemporaryCredit(lt);
		dfadapter.gatherData();
		lv.setAdapter(dfadapter);
		PopDialogListener listener = new PopDialogListener();
		TextView qTV1 = (TextView)view.findViewById(R.id.displayfee_header_textView1);
		qTV1.setOnLongClickListener(listener);
		qTV1.setOnClickListener(listener);
		TextView qTV2 = (TextView)view.findViewById(R.id.displayfee_header_textView2);
		qTV2.setOnLongClickListener(listener);
		qTV2.setOnClickListener(listener);
		TextView qTV3 = (TextView)view.findViewById(R.id.displayfee_header_textView3);
		qTV3.setOnLongClickListener(listener);
		qTV3.setOnClickListener(listener);
		// set user defined header sequence
		ViewGroup vg = (ViewGroup)qTV1.getParent();
		vg.removeAllViews();
		LinkedList<Integer> sequences = dfadapter.getSequences();
		for(Iterator<Integer> ir=sequences.iterator();ir.hasNext();) {
			int seq = ir.next();
			switch(seq) {
				case 0:
					vg.addView(qTV1);
					break;
				case 1:
					vg.addView(qTV2);
					break;
				case 2:
					vg.addView(qTV3);
					break;
			}
		}
		// set user defined header width
		TreeMap<Integer,Integer> columns = dfadapter.getColumns();
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
				case 2:
					LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams)qTV3.getLayoutParams();
					params2.width = width;
					break;
			}
			
		}
	    builder.setView(view);
		builder.setPositiveButton(msgY, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		return builder.create();
		
	}

	private class PopDialogListener implements OnLongClickListener,OnClickListener {

		@Override
		public boolean onLongClick(View v) {
			TextView tv = (TextView)v;
			Log.d("PopDialogListener","onLongClick index="+tv.getText()+tv.getContentDescription()+",width="+tv.getWidth());
			// �]�w LayoutParams�A��Jtag���A�H�Kdrag event�A�ϥ�
			tv.setTag(dfadapter);
			// start drag for this view, no ClipData need
			tv.startDrag(null, new DragShadowBuilder(v), (Object)v, 0);
			return true;
		}

		@Override
		public void onClick(View v) {
			TextView tv = (TextView)v;
			Log.d("PopDialogListener","onClick index="+tv.getText()+tv.getContentDescription()+",width="+tv.getWidth());
			int newWidth = tv.getWidth()+Constant.ZOOM_INCREMENTAL;
			// �Nheader�����]�w�A�ǵ�data row
			dfadapter.setColumnWidth(Integer.parseInt(tv.getContentDescription().toString()),newWidth);
			// �]�wheader�A�P��trigger reload ListView 
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(newWidth, tv.getHeight());
			tv.setLayoutParams(params);
		}
		
	}


    private static class ViewHolder {  
        public TextView[] tv = new TextView[COLUMN_SIZE];  
    }  
}
