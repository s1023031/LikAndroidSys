package com.lik.android.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.lik.Constant;
import com.lik.android.main.R;
import com.lik.android.main.LikDBAdapter;
import com.lik.android.om.Company;
import com.lik.android.om.UserCompy;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DeptNameDataAdapter extends BaseDataAdapter<DeptNameView> {

	public DeptNameDataAdapter(Activity context, List<DeptNameView> data) {
		super(context, data);
	}

	public DeptNameDataAdapter(Activity context, LikDBAdapter DBAdapter) {
		super(context, DBAdapter);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if(view == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.login_company_name, null);
			ViewHolder holder = new ViewHolder();
			holder.tv = (TextView)view.findViewById(R.id.login_company_name_textView1);
			view.setTag(holder);
		}
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.tv.setText(data.get(position).getCompanyNM());		
		return view;
	}

	@Override
	public void gatherData(String... args) {
		data =  new ArrayList<DeptNameView>();
		UserCompy omUC = new UserCompy();
		omUC.setAccountNo(args[0]);
		omUC.setCompanyParent(args[1]);
		List<UserCompy> ltUC = omUC.getUserCompyByAccountNo(DBAdapter);
		Log.d(Constant.TAG,"UserCompy size="+ltUC.size());
		for(Iterator<UserCompy> ir=ltUC.iterator();ir.hasNext();) {
			UserCompy om = ir.next();
			Company omC = new Company();
			omC.setCompanyID(om.getCompanyID());
			omC.setUserNO(args[0]);
			omC.setCompanyParent(args[1]);
			omC.getCompanyByKey(DBAdapter);
			Log.d(Constant.TAG,"Company getCompanyNM="+omC.getCompanyNM());
			Log.d(Constant.TAG,"Company getCompanyID="+omC.getCompanyID());
			Log.d(Constant.TAG,"Company getCompanyNO="+omC.getCompanyNO());
			if(omC.getRid()>=0) {
				DeptNameView view = new DeptNameView();
				view.setCompanyNM(omC.getCompanyNM());
				view.setCompanyNO(omC.getCompanyNO());
				view.setCompanyID(omC.getCompanyID());
				view.setDateFormat(omC.getDateFormat());
				view.setTelNo(omC.getTelNo());
				data.add(view);
			}
		}
	}

    private static class ViewHolder {  
        public TextView tv;  
    }  
}
