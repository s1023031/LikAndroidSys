package com.lik.android.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.jivesoftware.smack.XMPPException;
import org.xmlpull.v1.XmlPullParserException;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;

import com.lik.Constant;
import com.lik.android.main.InstantMessageHandler;
import com.lik.android.main.MainMenuFragment;
import com.lik.android.main.MainMenuActivity;
import com.lik.android.main.R;
import com.lik.android.main.ScreenReceiverForInstantMessenger;
import com.lik.android.om.InstantMessages;
import com.lik.android.om.SiteIPList;
import com.lik.android.util.XmppCallBack;
import com.lik.android.util.XmppUtil;
import com.lik.util.XmlUtilExt;

public class InstantMessengerService extends IntentService implements
		XmppCallBack {

	private static final String TAG = InstantMessengerService.class.getName();
	private final int INSTANT_MESSENGER_WAIT_INTERVAL = 60000; // 1 min
	public static final String INSTANT_MESSENGER_ACTION = "INSTANT_MESSENGER_ACTION";
	public static final String BROADCAST_TOAST = "TOAST:";
	public static final String BROADCAST_DISPLAY = "DISPLAY:";
	public static final String RESULT = "DATA";
	public static final String RETURN_CODE = "ReturnCode";
	public static final String MESSAGE_KEY = "MessageKey";
	public static final String MESSAGE_CONTENT = "Message";
	public static final String MESSAGE_OWNER = "Owner";
	public static final String MESSAGE_SUBJECT = "Subject";
	public static final String SEQUENCE = "Sequence";
	public static boolean isAliveIMS = false;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss",
			Locale.CHINESE);

	boolean isDebug = false;

	// XMPP utility
	private final XmppUtil xmppUtil = new XmppUtil(this);
	private LikDBAdapter DBAdapter;

	BroadcastReceiver mReceiver;
	Handler mHandler;
	private NotificationManager nm;
	private InstantMessageHandler handler = new InstantMessageHandler() {

		@Override
		public void handleToServiceExt(Message msg) {
			// implement if any work to do in service
		}

		@Override
		public void handleFromServiceExt(Message msg) {
			// do nothing
		}

	};

	final Messenger mMessenger = new Messenger(handler);

	SiteIPList omSIP;
	StringBuffer url;
	String siteName;
	String systemNo;
	String userNo;
	List<String> keys = new ArrayList<String>();

	boolean isQueueFine = true;

	public InstantMessengerService() {
		super(TAG);
	}

	public InstantMessengerService(String name) {
		super(name);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mMessenger.getBinder();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		showNotification();
		// register receiver that handles screen on and screen off logic
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		mReceiver = new ScreenReceiverForInstantMessenger();
		registerReceiver(mReceiver, filter);
		isAliveIMS = true;

		siteName = Settings.System.getString(getContentResolver(),
				Settings.Secure.ANDROID_ID);
		systemNo = getBaseContext().getString(R.string.app_code);
		// init InstantMessages table
		DBAdapter = MainMenuActivity.DBAdapter;
		if (DBAdapter == null)
			return;
		InstantMessages omIM = new InstantMessages();
		if (!omIM.testTableExists(DBAdapter)) {
			SQLiteDatabase db = DBAdapter.getdb();
			String cmd = omIM.getDropCMD();
			if (cmd != null)
				db.execSQL(cmd);
			cmd = omIM.getCreateCMD();
			if (cmd != null)
				db.execSQL(cmd);
			String[] cmds = omIM.getCreateIndexCMD();
			for (int j = 0; j < cmds.length; j++) {
				cmd = cmds[j];
				db.execSQL(cmd);
			}
			DBAdapter.closedb();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
		isAliveIMS = false;
		// service destroy, disconnect XMPP connection
		if (xmppUtil != null && xmppUtil.isConnect()) {
			xmppUtil.disConnectViaXMPP();
		}
	}

	private void sendBroadcast(String msg) {
		// ---send a broadcast to inform the activity
		Intent broadcastIntent = new Intent();
		broadcastIntent.putExtra(RESULT, msg);
		broadcastIntent.setAction(INSTANT_MESSENGER_ACTION);
		getBaseContext().sendBroadcast(broadcastIntent);
	}

	private void sendMessage(String content) {
		Message msg = Message
				.obtain(null, InstantMessageHandler.MSG_FROM_SERVICE, 0, 0,
						BROADCAST_DISPLAY);
		Bundle data = msg.getData();
		data.putString(InstantMessageHandler.MSG_CONTENT, content);
		try {
			for (Messenger m : handler.mClients.values()) {
				m.send(msg);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/*
	 * <Root TableName='None'> <DetailSize></DetailSize>
	 * <ReturnCode></ReturnCode> <DetailList TableName='InstantMessages'>
	 * <Detail> <MessageKey></MessageKey> <Message></Message> <Owner></Owner>
	 * <Subject></Subject> </Detail> <Detail> <MessageKey></MessageKey>
	 * <Message></Message> <Owner></Owner> <Subject></Subject> </Detail>
	 * </DetailList> </Root>
	 */
	@Override
	public void callBack(String message) {
		if (isDebug) {
			Message msg = Message.obtain(null,
					InstantMessageHandler.MSG_FROM_SERVICE, 0, 0,
					BROADCAST_TOAST);
			Bundle data = msg.getData();
			data.putString(InstantMessageHandler.MSG_CONTENT,
					"get messages from queue...");
			try {
				for (Messenger m : handler.mClients.values()) {
					m.send(msg);
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		isQueueFine = true;
		try {
			XmlUtilExt util = new XmlUtilExt(message);
			TreeMap<String, String> map = util.getMaster();
			if (!Constant.isEmpty(map.get(RETURN_CODE))) {
				String returnCode = map.get(RETURN_CODE);
				if (returnCode.equals(Constant.SUCCESS)) {
					Log.i(TAG,
							"callback, connect to server success, processing messages...");
					List<TreeMap<String, String>> lt = util.getDetail();
					insertMessages(lt);
				}
			}
		} catch (XmlPullParserException e) {
			Log.e(TAG, e.fillInStackTrace().toString());
		}
	}

	private void insertMessages(List<TreeMap<String, String>> lt) {
		if (DBAdapter == null)
			return;
		boolean hasNewMessage = false;
		keys = new ArrayList<String>();
		for (TreeMap<String, String> tm : lt) {
			Log.i(TAG, "message=" + tm.get(MESSAGE_CONTENT));
			InstantMessages omIM = new InstantMessages();
			omIM.setContent(tm.get(MESSAGE_CONTENT));
			try {
				omIM.setPublishTime(sdf.parse(tm.get(MESSAGE_KEY).substring(0,
						14)));
				keys.add(tm.get(MESSAGE_KEY));
			} catch (ParseException e) {
				Log.e(TAG,
						"can not parse publich time, use system time instead!");
				omIM.setPublishTime(MainMenuActivity.getCurrentDate());
			}
			omIM.setOwner(tm.get(MESSAGE_OWNER));
			omIM.setSubject(tm.get(MESSAGE_SUBJECT));
			omIM.setUserNo(userNo);
			omIM.setMessageKey(tm.get(MESSAGE_KEY));
			omIM.findByKey(DBAdapter);
			if (omIM.getRid() < 0) {
				hasNewMessage = true;
				omIM.setReceiveTime(MainMenuActivity.getCurrentDate());
				omIM.doInsert(DBAdapter);
				Log.i(TAG, "message inserted!");
			} else {
				Log.i(TAG, "message exists, skipped!");
			}
		}
		callUpdate();
		if (hasNewMessage) {
			// sendBroadcast(BROADCAST_DISPLAY);
			sendMessage(BROADCAST_DISPLAY);
		}
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		userNo = intent.getStringExtra("userNo");
		if (userNo == null)
			userNo = MainMenuActivity.currentUserNo;
		omSIP = MainMenuFragment.siteIPListUpLoad;
		if (userNo == null || omSIP == null) {
			Log.w(TAG, "userNo=" + userNo + ",omSIP is null?" + (omSIP == null));
			isAliveIMS = false;
			return;
		}
		// 連上activemq topic using XMPP upload queue
		try {
			xmppUtil.connectViaXMPP(omSIP.getIp(), omSIP.getQueuePort(),
					siteName, true);
			Log.i(TAG, "XMPP connected!");
		} catch (XMPPException e1) {
			Log.e(TAG, "XMPP connect fail");
		} catch (IOException e1) {
			Log.e(TAG, "XMPP connect fail");
		}

		// sleep until prvious service stop if exists
		if (!isAliveIMS) {
			try {
				Thread.sleep(INSTANT_MESSENGER_WAIT_INTERVAL);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
		}
		isAliveIMS = true;

		while (isAliveIMS) {
			try {
				Thread.sleep(INSTANT_MESSENGER_WAIT_INTERVAL);
				if (!isAliveIMS)
					continue;
				omSIP = MainMenuFragment.siteIPListUpLoad; // refresh
				if (omSIP == null)
					continue;
				userNo = MainMenuActivity.currentUserNo;
				if (userNo == null)
					continue;
				if (DBAdapter == null)
					DBAdapter = MainMenuActivity.DBAdapter;
				if (DBAdapter == null)
					continue;
				url = new StringBuffer();
				url.append("http://").append(omSIP.getIp());
				url.append(":").append(omSIP.getWebPort());
				url.append(getBaseContext().getString(
						R.string.syncToServerAction));
				Log.d(TAG, "url=" + url);
				if (!isQueueFine) {
					// re-connect activemq topic using XMPP upload queue
					try {
						xmppUtil.disConnectViaXMPP();
						xmppUtil.connectViaXMPP(omSIP.getIp(),
								omSIP.getQueuePort(), siteName, true);
						Log.i(TAG, "XMPP re-connected!");
						if (isDebug)
							sendBroadcast(BROADCAST_TOAST
									+ "XMPP re-connected!");
					} catch (XMPPException e1) {
						Log.e(TAG, "XMPP connect fail");
					} catch (IOException e1) {
						Log.e(TAG, "XMPP connect fail");
					}
				}
				isQueueFine = false;
				InputStreamReader in = null;
				HttpClient httpclient = new DefaultHttpClient();
				try {
					HttpPost httppost = new HttpPost(url.toString());
					HttpConnectionParams.setConnectionTimeout(
							httppost.getParams(),
							MainMenuFragment.HTTP_CONNECTION_TIMEOUT); // timeout
																		// 5
																		// secs
					HttpConnectionParams.setSoTimeout(httppost.getParams(),
							MainMenuFragment.HTTP_DATA_TIMEOUT); // timeout 10
																	// secs
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					nameValuePairs.add(new BasicNameValuePair("siteName",
							siteName));
					nameValuePairs.add(new BasicNameValuePair("systemNo",
							systemNo));
					nameValuePairs
							.add(new BasicNameValuePair("userNo", userNo));
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					HttpResponse response = httpclient.execute(httppost);
					HttpEntity entity = response.getEntity();
					// If the response does not enclose an entity, there is no
					// need
					// to worry about connection release
					if (entity != null) {
						StringBuffer sb = new StringBuffer();
						in = new InputStreamReader(entity.getContent(), "UTF-8");
						char[] buf = new char[1024];
						int len = in.read(buf);
						while (len != -1) {
							sb.append(buf, 0, len);
							len = in.read(buf);
						}
						Log.i(TAG, sb.toString());
						// if(isDebug)
						// sendBroadcast(BROADCAST_TOAST+"get messages from http...");
						XmlUtilExt util = new XmlUtilExt(sb.toString());
						TreeMap<String, String> map = util.getMaster();
						if (!Constant.isEmpty(map.get(RETURN_CODE))) {
							String returnCode = map.get(RETURN_CODE);
							if (returnCode.equals(Constant.SUCCESS)) {
								Log.i(TAG,
										"connect to server success, processing messages...");
								List<TreeMap<String, String>> lt = util
										.getDetail();
								insertMessages(lt);
							}
						}
					}
					// When HttpClient instance is no longer needed,
					// shut down the connection manager to ensure
					// immediate deallocation of all system resources
					httpclient.getConnectionManager().shutdown();
				} catch (ClientProtocolException e) {
					Log.e(TAG, e.fillInStackTrace().toString());
				} catch (IOException e) {
					Log.e(TAG, e.fillInStackTrace().toString());
				} catch (XmlPullParserException e) {
					Log.e(TAG, e.fillInStackTrace().toString());
				} finally {
					if (in != null)
						try {
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private void callUpdate() {
		Log.i(TAG, "callUpdate");
		if (keys.size() == 0)
			return;
		if (omSIP == null)
			return;
		// update url
		StringBuffer urlUpdate = new StringBuffer();
		urlUpdate.append("http://").append(omSIP.getIp());
		urlUpdate.append(":").append(omSIP.getWebPort());
		urlUpdate.append(getBaseContext().getString(
				R.string.syncToServerUpdateAction));
		urlUpdate.append("?siteName=" + siteName);
		urlUpdate.append("&systemNo=" + systemNo);
		urlUpdate.append("&userNo=" + userNo);
		Log.i(TAG, "update url=" + urlUpdate.toString());
		// update xml
		StringBuffer sb = new StringBuffer();
		sb.append("<Root TableName='None'>").append("\n");
		sb.append("<DetailSize>").append(keys.size()).append("</DetailSize>")
				.append("\n");
		sb.append("<DetailList TableName='InstantMessageList'>").append("\n");
		for (String key : keys) {
			sb.append("<Detail>").append("\n");
			sb.append("<MessageKey>").append(key).append("</MessageKey>")
					.append("\n");
			sb.append("</Detail>").append("\n");
		}
		sb.append("</DetailList>").append("\n");
		sb.append("</Root>");
		final String xml = sb.toString();
		Log.i(TAG, "xml=" + xml);
		InputStreamReader in = null;
		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpPost httppost = new HttpPost(urlUpdate.toString());
			HttpConnectionParams.setConnectionTimeout(httppost.getParams(),
					MainMenuFragment.HTTP_CONNECTION_TIMEOUT); // timeout 5 secs
			HttpConnectionParams.setSoTimeout(httppost.getParams(),
					MainMenuFragment.HTTP_DATA_TIMEOUT); // timeout 10 secs
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
					// Should be implemented as well but is irrelevant for this
					// case
					throw new UnsupportedOperationException();
				}

				public void writeTo(final OutputStream outstream)
						throws IOException {
					byte[] buf = xml.getBytes();
					outstream.write(buf);
					outstream.flush();
				}

			};
			httppost.setEntity(entity);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity rentity = response.getEntity();
		    Log.i(TAG,"response="+response);
			// If the response does not enclose an entity, there is no need
			// to worry about connection release
			if (rentity != null) {
				sb = new StringBuffer();
				in = new InputStreamReader(rentity.getContent(), "UTF-8");
				char[] buf = new char[1024];
				int len = in.read(buf);
				while (len != -1) {
					sb.append(buf, 0, len);
					len = in.read(buf);
				}
				Log.i(TAG, "123="+sb.toString());
				if (isDebug)
					sendBroadcast(BROADCAST_TOAST + "get update response...");
				XmlUtilExt util = new XmlUtilExt(sb.toString());
				TreeMap<String, String> map = util.getMaster();
				if (!Constant.isEmpty(map.get(RETURN_CODE))) {
					String returnCode = map.get(RETURN_CODE);
					if (returnCode.equals(Constant.SUCCESS)) {
						Log.i(TAG, "updater success");
					} else {
						Log.w(TAG, "updater failed!");
					}
				}
			}
			httpclient.getConnectionManager().shutdown();
		} catch (ClientProtocolException e) {
			Log.e(TAG, e.fillInStackTrace().toString());
		} catch (IOException e) {
			Log.e(TAG, e.fillInStackTrace().toString());
		} catch (XmlPullParserException e) {
			Log.e(TAG, e.fillInStackTrace().toString());
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}

	private void showNotification() {
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// In this sample, we'll use the same text for the ticker and the
		// expanded notification
		CharSequence text = getText(R.string.imMessage2);
		// Set the icon, scrolling text and timestamp
		Notification.Builder builder = new Notification.Builder(
				getBaseContext());
		builder.setSmallIcon(R.drawable.icon).setContentText(text)
				.setWhen(System.currentTimeMillis());
		// The PendingIntent to launch our activity if the user selects this
		// notification
		// PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new
		// Intent(this, MainMenuActivity.class), 0);
		// Set the info for the views that show in the notification panel.
		// builder.setContentIntent(contentIntent).setContentTitle(getText(R.string.imMessage1)).setContentText(text);
		Notification notification = builder.getNotification();
		// Send the notification.
		// We use a layout id because it is a unique number. We use it later to
		// cancel.
		nm.notify(R.string.imMessage2, notification);
	}

}
