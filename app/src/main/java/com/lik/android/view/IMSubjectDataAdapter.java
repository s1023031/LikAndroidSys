package com.lik.android.view;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lik.Constant;
import com.lik.android.main.LikDBAdapter;
import com.lik.android.main.MainMenuActivity;
import com.lik.android.main.R;
import com.lik.android.om.InstantMessages;

public class IMSubjectDataAdapter extends BaseDataAdapter<IMSubjectView> {

	protected static final String TAG = IMSubjectDataAdapter.class.getName();
	private static final int COLUMN_SIZE = 1;

	public IMSubjectDataAdapter(MainMenuActivity context,
			LikDBAdapter DBAdapter) {
		super(context, DBAdapter);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if(view == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.imsubject, null);
			ViewHolder holder = new ViewHolder();
			holder.tv[0] = (TextView)view.findViewById(R.id.imsubject_textView1);
			view.setTag(holder);
		}
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.tv[0].setText(data.get(position).getSubject());
		return view;
	}

	/**
	 * args[0] : userNo
	 * args[1] : true(read), false(unread)
	 */
	@Override
	public void gatherData(String... args) {
		data =  new ArrayList<IMSubjectView>();
		InstantMessages omIM = new InstantMessages();
		omIM.setUserNo(args[0]);
		omIM.setRead(Boolean.parseBoolean(args[1]));
		List<InstantMessages> ltIM = omIM.getMessages(DBAdapter);
		Log.d(Constant.TAG,"InstantMessages size="+ltIM.size());
		for(InstantMessages om : ltIM) {
			IMSubjectView view = new IMSubjectView();
			view.setSubject(om.getSubject());
			data.add(view);
		}
	}

    private static class ViewHolder {  
        public TextView[] tv = new TextView[COLUMN_SIZE];  
    }  
}
