package com.lik.android.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.lik.Constant;
import com.lik.android.main.LikDBAdapter;
import com.lik.android.main.MainMenuActivity;
import com.lik.android.main.R;
import com.lik.android.om.Bulletin;
import com.lik.android.om.Company;
import com.lik.android.om.InstantMessages;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Gallery;

public class InstantMessagesDataAdapter extends BaseDataAdapter<InstantMessagesView> {

	public InstantMessagesDataAdapter(MainMenuActivity context, List<InstantMessagesView> data) {
		super(context, data);
	}

	public InstantMessagesDataAdapter(MainMenuActivity context, LikDBAdapter DBAdapter) {
		super(context, DBAdapter);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if(view == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.im_gallery_body, null);
			view.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.MATCH_PARENT, Gallery.LayoutParams.MATCH_PARENT));
			ViewHolder holder = new ViewHolder();
			holder.tv = (EditText)view.findViewById(R.id.im_gallery_body_editText1);
			view.setTag(holder);
		}
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.tv.setText(data.get(position).getContent());
		Log.d(TAG,"setting data..."+holder.tv.getText());
		return view;
	}

	/**
	 * args[0] : userNo
	 * args[1] : true(read), false(unread)
	 */
	@Override
	public void gatherData(String... args) {
		data =  new ArrayList<InstantMessagesView>();
		InstantMessages omIM = new InstantMessages();
		omIM.setUserNo(args[0]);
		omIM.setRead(Boolean.parseBoolean(args[1]));
		List<InstantMessages> ltIM = omIM.getMessages(DBAdapter);
		Log.d(Constant.TAG,"InstantMessages size="+ltIM.size());
		for(InstantMessages om : ltIM) {
			InstantMessagesView view = new InstantMessagesView();
			view.setSerialID(om.getSerialID());
			view.setUserNo(om.getUserNo());
			view.setContent(om.getContent());
			view.setPublishTime(om.getPublishTime());
			view.setReceiveTime(om.getReceiveTime());
			view.setOwner(om.getOwner());
			view.setRead(om.isRead());
			data.add(view);
		}
	}

    private static class ViewHolder {  
        public EditText tv;  
    }  
}
