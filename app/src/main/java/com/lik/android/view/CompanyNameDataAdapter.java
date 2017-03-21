package com.lik.android.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lik.Constant;
import com.lik.android.main.R;
import com.lik.android.main.LikDBAdapter;
import com.lik.android.om.Company;
import com.lik.android.om.SysProfile;
import com.lik.android.om.UserCompy;

public class CompanyNameDataAdapter extends BaseDataAdapter<CompanyNameView>{

	public CompanyNameDataAdapter(Activity context, List<CompanyNameView> data) {
		super(context, data);
	}

	public CompanyNameDataAdapter(Activity context, LikDBAdapter DBAdapter) {
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
		//holder.tv.setText(data.get(position).getCompanyNO());
		holder.tv.setText(data.get(position).getCompanyName());
		return view;
	}
	
	@Override
	public void gatherData(String... args) {
		data =  new ArrayList<CompanyNameView>();
		SysProfile omSP = new SysProfile();
        ArrayList<SysProfile> alSP = omSP.getAllSysProfile(DBAdapter);
		Log.d(Constant.TAG,"Company size="+alSP.size());
		for(int i = 0 ; i< alSP.size() ; i ++) {
			SysProfile om =alSP.get(i);
			Log.d(Constant.TAG,"Company getCompanyNo="+om.getCompanyNo());
			Log.d(Constant.TAG,"Company getCompanyName="+om.getCompanyName());
			if(om.getRid()>=0) {
				CompanyNameView view = new CompanyNameView();
				view.setCompanyNO(om.getCompanyNo());
				view.setSystemNo(om.getSystemNo());
				view.setCompanyName(om.getCompanyName());
				data.add(view);	
			}
		}
	}
	 
	private static class ViewHolder {  
	        public TextView tv;  
	    }  
}
