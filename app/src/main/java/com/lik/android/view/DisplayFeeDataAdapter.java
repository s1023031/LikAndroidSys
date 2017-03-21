package com.lik.android.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lik.android.main.LikDBAdapter;
import com.lik.android.main.MainMenuActivity;
import com.lik.android.om.TemporaryCredit;
import com.lik.android.view.BaseDataAdapter;
import com.lik.android.main.R;

public class DisplayFeeDataAdapter extends BaseDataAdapter<DisplayFeeView> {
	
	protected static final String TAG = DisplayFeeDataAdapter.class.getName();
	private static final int COLUMN_SIZE = 3;
	List<TemporaryCredit> lt;
	MainMenuActivity myActivity;

	public DisplayFeeDataAdapter(MainMenuActivity context, LikDBAdapter DBAdapter) {
		super(context,DBAdapter);
		init(COLUMN_SIZE);
		this.myActivity = context;
	}

	public void setTemporaryCredit(List<TemporaryCredit> lt) {
		this.lt = lt;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		Log.d(TAG,"position="+position+","+data.get(position).getAccountName());
		if(view == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.displayfee_row, null);
			ViewHolder holder = new ViewHolder();
			holder.tv[0] = (TextView)view.findViewById(R.id.displayfee_row_textView1);
			holder.tv[1] = (TextView)view.findViewById(R.id.displayfee_row_textView2);
			holder.tv[2] = (TextView)view.findViewById(R.id.displayfee_row_textView3);
			view.setTag(holder);
		}
		final ViewHolder holder = (ViewHolder) view.getTag();
		// �]�wuser defined order
		ViewGroup vg = (ViewGroup)holder.tv[0].getParent();
		vg.removeAllViews();
		for(Iterator<Integer> ir=sequences.iterator();ir.hasNext();) {
			int seq = ir.next();
			switch(seq) {
			case 0:
				vg.addView(holder.tv[seq]);
				break;
			case 1:
				vg.addView(holder.tv[seq]);
				break;
			case 2:
				vg.addView(holder.tv[seq]);
				break;
			}
		}
		// �]�wuser defined LayoutParams
		for(Iterator<Integer> ir = columns.keySet().iterator();ir.hasNext();) {
			int columnNO = ir.next();
			int width = columns.get(columnNO);
			if(width == 0) continue;
			switch(columnNO) {
			case 0:
				LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)holder.tv[columnNO].getLayoutParams();
				params.width = width;		
				break;
			case 1:
				params = (LinearLayout.LayoutParams)holder.tv[columnNO].getLayoutParams();
				params.width = width;		
				break;
			case 2:
				params = (LinearLayout.LayoutParams)holder.tv[columnNO].getLayoutParams();
				params.width = width;		
				break;
			}
		}
		holder.tv[0].setText(data.get(position).getAccountName());
		holder.tv[1].setText(data.get(position).getAccountRemark());
		holder.tv[2].setText(String.valueOf(data.get(position).getAccountAmount()));
		holder.position = position;
		return view;
	}

	@Override
	public void gatherData(String... args) {
		Log.d(TAG,"lt.size()="+lt.size());
		data = new ArrayList<DisplayFeeView>();
		for(TemporaryCredit omtc:lt) {
			DisplayFeeView omview = new DisplayFeeView();
			omview.setAccountName(omtc.getAccountName());
			omview.setAccountRemark(omtc.getAccountRemark());
			omview.setAccountAmount(omtc.getAccountAmount());
			data.add(omview);
		}
	}

	private static class ViewHolder {  
        public TextView[] tv = new TextView[COLUMN_SIZE]; 
        public int position;
    }


}
