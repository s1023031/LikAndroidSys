package com.lik.android.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemClickListener;

import com.lik.Constant;
import com.lik.android.om.Customers;
import com.lik.android.om.SalesNote;
import com.lik.android.om.SiteIPList;
import com.lik.android.view.QueryNotUploadView;
import com.lik.android.view.SalesNoteView;

public class ReportFragment extends MainMenuFragment{
	View view;
	MyAdapter mba;
	ListView ls;
	TextView tv;
	EditText et1;
	RelativeLayout sendBut,saveBut,cancelBut;
	SimpleDateFormat keyFormat = new SimpleDateFormat("yyyyMMddHHmmss",Locale.CHINESE);
	SalesNoteView currentView;
	Page6Object po;
	int CustomerID,DeliverViewOrder;
	int selectItem = -1;

	Handler mHamdler = new Handler();

	public ReportFragment(Page6Object po){
		this.po=po;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater,container,savedInstanceState);
		view = inflater.inflate(R.layout.main_report, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG,"onActivityCreated start!");
		setView();
		//testData();


		editSalesNote();
		myActivity.setBadges();
		getOrderInfo();
		readDateFromSqlite();

	}

	private void getOrderInfo() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = DBAdapter.getReadableDatabase();
		Cursor c=db.query(false,
				"Orders",				//��ƪ�W��
				new String[] {"CustomerID","DeliverViewOrder"},	//���W��
				"OrderID ="+po.OrderID+";",				//WHERE
				null, // WHERE ���Ѽ�
				null, // GROUP BY
				null, // HAVING
				null, // ORDOR BY
				null  // ����^�Ǫ�rows�ƶq
		);

		if (c.getCount()>0) {
			c.moveToFirst();
			//Toast.makeText(myActivity, ""+c.getString(0), Toast.LENGTH_LONG).show();
			CustomerID = c.getInt(0);
			DeliverViewOrder =  c.getInt(1);
			//Toast.makeText(myActivity, ""+CustomerID+"  "+DeliverViewOrder, Toast.LENGTH_SHORT).show();
		}
	}
	private void readDateFromSqlite() {
		// TODO Auto-generated method stub
		//mba.addListItem(new ReportItem(""+i,"報告"+i,"2016/03/28 10:39 AM",t));
		//ReportItem(String id,String title,String dateTime,Boolean isSend)
		//Toast.makeText(myActivity, po.CustomersID+" "+po.OrderID+" "+po.name+" ", Toast.LENGTH_LONG).show();
		SQLiteDatabase db = DBAdapter.getReadableDatabase();
		String tableName="SalesNote_"+myActivity.omCurrentSysProfile.getCompanyNo()+myActivity.currentDept.getCompanyID();
		Cursor c=db.query(false,
				tableName,				//��ƪ�W��
				new String[] {"SerialID","Note","IssueTime","isUpload","ReportKey","CustomerID","DeliverOrder"},	//���W��
				"CustomerID ="+CustomerID+" AND DeliverOrder="+DeliverViewOrder+";",				//WHERE
				null, // WHERE ���Ѽ�
				null, // GROUP BY
				null, // HAVING
				null, // ORDOR BY
				null  // ����^�Ǫ�rows�ƶq
		);

		if (c.getCount()>0) {
			c.moveToFirst();
			//Toast.makeText(myActivity, ""+c.getString(0), Toast.LENGTH_LONG).show();
			Boolean t;
			if(c.getInt(3)==0){
				t=false;
			}else{
				t=true;
			}
			mba.addListItem(new ReportItem(c.getString(0),c.getString(1),c.getString(2),t,c.getString(4),myActivity.currentDept.getCompanyID()+"",c.getInt(5)+"",c.getInt(6)+""));
			while(c.moveToNext()){

				if(c.getInt(3)==0){
					t=false;
				}else{
					t=true;
				}
				mba.addListItem(new ReportItem(c.getString(0),c.getString(1),c.getString(2),t,c.getString(4),myActivity.currentDept.getCompanyID()+"",c.getInt(5)+"",c.getInt(6)+""));
			}
		}
	}

	private void editSalesNote() {
		SalesNote omSN = new SalesNote();
		if(!omSN.testTableExists(DBAdapter)) {
			SQLiteDatabase db = DBAdapter.getdb();
			String cmd = omSN.getDropCMD();
			if(cmd != null)
				db.execSQL(cmd);
			cmd = omSN.getCreateCMD();
			if(cmd != null)
				db.execSQL(cmd);
			String[] cmds = omSN.getCreateIndexCMD();
			for(int j=0;j<cmds.length;j++) {
				cmd = cmds[j];
				db.execSQL(cmd);
			}
			DBAdapter.closedb();
		}
	}
	private void testData() {
		// TODO Auto-generated method stub
		for(int i=0;i<30;i++){
			Boolean t;
			if(i%2==0){
				t=true;
			}else{
				t=false;
			}
			//mba.addListItem(new ReportItem(""+i,i+"報告12345888","2016/03/28 10:39 AM",t));
		}

	}

	private void setView() {
		// TODO Auto-generated method stub
		tv = (TextView)view.findViewById(R.id.mrv_title_tv);
		tv.setText("報告\n("+po.name+")");
		ls = (ListView)view.findViewById(R.id.mrv_ls);
		mba = new MyAdapter(myActivity);
		ls.setAdapter(mba);

		ls.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub

				if(selectItem==position){
					selectItem=-1;
					et1.setText("");

				}else{
					selectItem=position;
					//ls.setItemChecked(position, true);
					et1.setText(mba.getItem(position).title);
				}
				mba.notifyDataSetChanged();
			}
		});

		ls.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,final int pos, long id) {
				new AlertDialog.Builder(myActivity) .setTitle("刪除報告").setMessage("你確定要刪除報告嗎？").setIcon(R.drawable.ic_launcher)
						.setPositiveButton("確定",  new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								SalesNote omSN = new SalesNote();
								omSN.setReportKey(mba.getItem(pos).ReportKey);
								omSN.doDelete(DBAdapter);
								mba.removeByP(pos);

							}
						})
						.setNegativeButton("取消", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
							}
						}).show();


				return true;
			}
		});
		et1 = (EditText)view.findViewById(R.id.mrv_et1);
		sendBut = (RelativeLayout)view.findViewById(R.id.mrv_send_but);
		saveBut = (RelativeLayout)view.findViewById(R.id.mrv_save_but);
		cancelBut = (RelativeLayout)view.findViewById(R.id.mrv_cancel_but);

		sendBut.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				//sendBut.performClick();
    			/*if(myActivity.omCurrentSysProfile.isCloud()) {
    				new HttpsSalesNoteUploadTask(EditSalesNoteFragment.this,currentView).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    			} else {*/
				//new UploadTask(mba.getItem(selectItem)).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				//new UploadTask(currentView).executeOnExecutor(Executors.newCachedThreadPool());
				//}
				if(selectItem!=-1){
					new UploadTask(mba.getItem(selectItem)).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				}else{
					//Toast.makeText(myActivity, "請先儲存或選擇一個項目", Toast.LENGTH_SHORT).show();

					SalesNote omSN = new SalesNote();


					QueryNotUploadView omQNV = new QueryNotUploadView();
					Customers omC = new Customers();
					//CustomerID,DeliverViewOrder
					Date issueTime = new Date();
					String reKey=keyFormat.format(issueTime);
					int customerID=CustomerID,deliverOrder=DeliverViewOrder;
					String userNo=myActivity.omCurrentAccount.getAccountNo();

					String note = et1.getText().toString();

					omSN.setReportKey(reKey);
					omSN.setCustomerID(customerID); // CustomerID
					omSN.setDeliverOrder(deliverOrder); //DeliverViewOrder
					omSN.setUserNo(userNo);
					omSN.setIssueTime(issueTime);
					omSN.setNote(note);
					omSN.doInsert(DBAdapter);

					SQLiteDatabase db = DBAdapter.getReadableDatabase();
					String tableName="SalesNote_"+myActivity.omCurrentSysProfile.getCompanyNo()+myActivity.currentDept.getCompanyID();
					Cursor c=db.query(false,
							tableName,				//��ƪ�W��
							new String[] {"SerialID","Note","IssueTime","isUpload","ReportKey","CustomerID","DeliverOrder"},	//���W��
							"CustomerID ="+customerID+" AND DeliverOrder="+deliverOrder+" AND ReportKey ='"+reKey+"' AND Note='"+note+"';",				//WHERE
							null, // WHERE ���Ѽ�
							null, // GROUP BY
							null, // HAVING
							null, // ORDOR BY
							null  // ����^�Ǫ�rows�ƶq
					);

					if (c.getCount()>0) {
						c.moveToFirst();
						//Toast.makeText(myActivity, ""+c.getString(0), Toast.LENGTH_LONG).show();
						Boolean t;
						if(c.getInt(3)==0){
							t=false;
						}else{
							t=true;
						}
						ReportItem ri=new ReportItem(c.getString(0),c.getString(1),c.getString(2),t,c.getString(4),myActivity.currentDept.getCompanyID()+"",c.getInt(5)+"",c.getInt(6)+"");
						mba.addListItem(ri);
						new UploadTask(ri).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
					}else{
						Toast.makeText(myActivity, "ERROR", Toast.LENGTH_SHORT).show();
					}

					//String id,String title,String dateTime,Boolean isSend,String ReportKey,String CompanyID,String CustomerID,String DeliverOrder

				}
			}
		});

		saveBut.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Date issueTime = new Date();
				SalesNote omSN = new SalesNote();
				String note = et1.getText().toString();

				if(selectItem==-1){
					QueryNotUploadView omQNV = new QueryNotUploadView();
					Customers omC = new Customers();
					//CustomerID,DeliverViewOrder
					omSN.setReportKey(keyFormat.format(issueTime));
					omSN.setCustomerID(CustomerID); // CustomerID
					omSN.setDeliverOrder(DeliverViewOrder); //DeliverViewOrder
					omSN.setUserNo(myActivity.omCurrentAccount.getAccountNo());
					omSN.setIssueTime(issueTime);
					omSN.setNote(note);
					omSN.doInsert(DBAdapter);
				}else{
					SQLiteDatabase db = DBAdapter.getWritableDatabase();
					ContentValues values = new ContentValues();
					values.put("Note", et1.getText().toString());
					db.update("SalesNote_"+myActivity.omCurrentSysProfile.getCompanyNo()+myActivity.currentDept.getCompanyID(), values, "SerialID" + "=" + mba.getItem(selectItem).id, null);
				}
				MainMenuFragment mmf = ReportFragment.newInstance(po);
				myActivity.showMainMenuFragment(mmf);
				synchronized(myActivity) {
					myActivity.setNeedBackup(true);
					myActivity.notify();
				}

			}
		});

		cancelBut.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				selectItem=-1;
				et1.setText("");
				mba.notifyDataSetChanged();

			}
		});
		
		/*------------------------------------------*/


		sendBut.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					v.setBackgroundDrawable(getResources().getDrawable( R.drawable.white_noc ));
					return false;
				}
				if(event.getAction()==MotionEvent.ACTION_UP){
					v.setBackgroundDrawable(getResources().getDrawable( R.drawable.balck_c ));

					return false;
				}
				return false;


			}
		});

		saveBut.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					v.setBackgroundDrawable(getResources().getDrawable( R.drawable.white_noc ));
					return false;
				}
				if(event.getAction()==MotionEvent.ACTION_UP){
					v.setBackgroundDrawable(getResources().getDrawable( R.drawable.balck_c ));

					return false;
				}
				return false;


			}
		});

		cancelBut.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					v.setBackgroundDrawable(getResources().getDrawable( R.drawable.white_noc ));
					return false;
				}
				if(event.getAction()==MotionEvent.ACTION_UP){
					v.setBackgroundDrawable(getResources().getDrawable( R.drawable.balck_c ));

					return false;
				}
				return false;


			}
		});
	}

	public static MainMenuFragment newInstance(Page6Object po) {
		Log.d(TAG, "in AddVisitCustomerFragment newInstance()");

		ReportFragment mf = new ReportFragment(po);

		// Supply index input as an argument.
		Bundle args = new Bundle();
		//.args.putInt("index", index);
		mf.setArguments(args);
		return mf;
	}

	public class MyAdapter<T>  extends BaseAdapter{

		private LayoutInflater myInflater;
		ArrayList<ReportItem> list = new ArrayList<ReportItem>();


		private class View_TalkLayout{

			TextView tv1,tv2,tv3;
			RelativeLayout rl;
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
		public ReportItem getItem(int position)
		{
			return list.get(position);
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}



		public void addListItem(ReportItem o){

			list.add(o);
			this.notifyDataSetChanged();
		}


		@Override
		public View getView(final int position,View convertView,ViewGroup parent)
		{
			//嚙諛訂嚙踝蕭嚙瞌嚙璀嚙踝蕭F嚙諉別listItem嚙踝蕭嚙踝蕭view嚙踝蕭嚙踟集合嚙瘠
			View_TalkLayout view_TalkLayout ;

			if(convertView == null){
				//嚙踝蕭嚙緻listItem嚙箴嚙踝蕭 view
				convertView = myInflater.inflate(R.layout.mmv_ls_item, null);
				view_TalkLayout = new View_TalkLayout();

				view_TalkLayout.tv1=(TextView)convertView.findViewById(R.id.mmvli_tv1);
				view_TalkLayout.tv2=(TextView)convertView.findViewById(R.id.mmvli_tv2);
				view_TalkLayout.tv3=(TextView)convertView.findViewById(R.id.mmvli_tv3);
				view_TalkLayout.rl = (RelativeLayout)convertView.findViewById(R.id.mmvli_back);
				//iv1 rl


				convertView.setTag(view_TalkLayout);
			}else{
				view_TalkLayout = (View_TalkLayout)convertView.getTag();
			}

			if(position%2==0){
				view_TalkLayout.rl.setBackgroundColor(Color.parseColor("#F5F5F5"));
			}else if(position%2==1){
				view_TalkLayout.rl.setBackgroundColor(Color.parseColor("#FFFFFF"));
			}

			if(position==selectItem){
				view_TalkLayout.rl.setBackgroundColor(Color.parseColor("#5572C5"));
			}

			if(list.get(position).isSend){
				view_TalkLayout.tv1.setText("[已傳送]");
				view_TalkLayout.tv1.setTextColor(Color.parseColor("#000000"));
				view_TalkLayout.tv2.setTextColor(Color.parseColor("#000000"));
				view_TalkLayout.tv3.setTextColor(Color.parseColor("#000000"));
			}else{
				view_TalkLayout.tv1.setText("[未傳送]");
				view_TalkLayout.tv1.setTextColor(Color.parseColor("#E61C26"));
				view_TalkLayout.tv2.setTextColor(Color.parseColor("#E61C26"));
				view_TalkLayout.tv3.setTextColor(Color.parseColor("#E61C26"));
			}
			view_TalkLayout.tv2.setText(list.get(position).title);
			view_TalkLayout.tv3.setText(list.get(position).dateTime);
			return convertView;
		}
	}

	class UploadTask extends AsyncTask<String, Integer, String> {

		ReportItem view;

		private UploadTask(ReportItem view) {
			super();
			this.view = view;
		}

		@Override
		protected String doInBackground(String... params) {
			String result = Constant.SUCCESS;
			if(view==null || Constant.isEmpty(view.title)) return Constant.ERROR_UPLOAD_DATA_ERROR;
			StringBuffer sb = new StringBuffer();
			sb.append("<Root TableName='SalesNote'>");
			sb.append("<ReportKey>").append(view.ReportKey).append("</ReportKey>");
			sb.append("<Note>").append("<![CDATA[").append(view.title).append("]]>").append("</Note>");
			sb.append("<CompanyID>").append(DBAdapter.getCompanyID()).append("</CompanyID>");
			sb.append("<CustomerID>").append(view.CustomerID).append("</CustomerID>");
			if(view.DeliverOrder!=null) sb.append("<DeliverOrder>").append(view.DeliverOrder).append("</DeliverOrder>");
			sb.append("</Root>");
			final String xml = sb.toString();
			HttpClient httpclient = new DefaultHttpClient();
			try {
				SiteIPList omSIP = MainMenuFragment.siteIPListUpLoad;
				StringBuffer url = new StringBuffer();
				url.append("http://").append(omSIP.getIp());
				url.append(":").append(omSIP.getWebPort());
				url.append(getText(R.string.uploadSalesNoteURI));
				url.append("?siteName=").append(DEVICEID);
				url.append("&userNo=").append(myActivity.omCurrentAccount.getAccountNo());
				url.append("&systemNo=").append(getText(R.string.app_code));
				HttpPost httppost = new HttpPost(url.toString());
				HttpConnectionParams.setConnectionTimeout(httppost.getParams(), 10000); // timeout 10 secs
				HttpConnectionParams.setSoTimeout(httppost.getParams(), 10000); // timeout 10 secs
				AbstractHttpEntity entity = new AbstractHttpEntity() {

					public boolean isRepeatable() {
						return false;
					}

					public long getContentLength() {
						return -1;
					}

					public boolean isStreaming() {
						return false;
					}

					public InputStream getContent() throws IOException {
						// Should be implemented as well but is irrelevant for this case
						throw new UnsupportedOperationException();
					}

					public void writeTo(final OutputStream outstream) throws IOException {
						byte[] buf = xml.getBytes();
						outstream.write(buf);
						outstream.flush();
					}

				};

				httppost.setEntity(entity);
				ResponseHandler<String> responseHandler=new BasicResponseHandler();
				String res = httpclient.execute(httppost,responseHandler).trim();
				Log.i(TAG,"res="+res);
				if(res.startsWith(Constant.SUCCESS)) {
					// update SalesNote as uploaded
					SalesNote om = new SalesNote();
					om.setSerialID(Long.parseLong(view.id));
					om.queryBySerialID(DBAdapter);
					if(om.getRid()>=0) {
						om.setUpload(true);
						om.doUpdate(DBAdapter);
						if(om.getRid()>=0) {
							Log.d(TAG,"ReportKey="+om.getReportKey()+" uploaded!");
							view.isSend=true;

							SQLiteDatabase db = DBAdapter.getWritableDatabase();
							ContentValues values = new ContentValues();
							values.put("isUpload", 1);
							db.update("SalesNote_"+myActivity.omCurrentSysProfile.getCompanyNo()+myActivity.currentDept.getCompanyID(), values, "SerialID" + "=" + view.id, null);
							mHamdler.post(new Runnable(){
								@Override
								public void run() {
									// TODO Auto-generated method stub

									selectItem=-1;
									et1.setText("");
									mba.notifyDataSetChanged();
									Toast.makeText(myActivity, "傳送成功！", Toast.LENGTH_LONG).show();
								}});

						}
					}
				} else result = Constant.FAIL;
			} catch (ClientProtocolException e) {
				Log.e(TAG,e.fillInStackTrace().toString());
				result = Constant.ERROR_CONNECT_NETWORK;
			} catch (IOException e) {
				Log.e(TAG,e.fillInStackTrace().toString());
				result = Constant.ERROR_CONNECT_NETWORK;
			} finally {
				// When HttpClient instance is no longer needed,
				// shut down the connection manager to ensure
				// immediate deallocation of all system resources
				if(httpclient != null) httpclient.getConnectionManager().shutdown();
			}

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			Log.d(TAG, "onPostExecute="+result);
			if(result.equals(Constant.SUCCESS)) {
				Log.d(TAG, "success");
				/*	adapter.gatherData(
						myActivity.omCurrentAccount.getAccountNo(),
						String.valueOf(viewQNU.getCustomerID()),
						"Y");
      			adapter.notifyDataSetChanged();
      			lv.post(new Runnable() {

					@Override
					public void run() {
						bn3.performClick();
					}
      				
      			});*/
			} else if(result.equals(Constant.ERROR_CONNECT_NETWORK)) {
				String msg = myActivity.getString(R.string.editSalesNoteMessage2);
				AlertDialog dialog = getAlertDialogForMessage(myActivity.getString(R.string.Message37a),msg);
				dialog.show();
			} else if(result.equals(Constant.FAIL)) {
				String msg = myActivity.getString(R.string.editSalesNoteMessage3);
				AlertDialog dialog = getAlertDialogForMessage(myActivity.getString(R.string.Message37a),msg);
				dialog.show();
			} else if(result.equals(Constant.ERROR_UPLOAD_DATA_ERROR)) {
				String msg = myActivity.getString(R.string.editSalesNoteMessage4);
				AlertDialog dialog = getAlertDialogForMessage(myActivity.getString(R.string.Message37a),msg);
				dialog.show();
			}
		}

	}

}
