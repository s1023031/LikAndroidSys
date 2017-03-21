package com.lik.android.main;

import java.util.TreeMap;

import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public abstract class InstantMessageHandler extends Handler {

	private static final String TAG = InstantMessageHandler.class.getName();

	public static final int MSG_REGISTER_CLIENT = 1;
	public static final int MSG_UNREGISTER_CLIENT = 2;
	public static final int MSG_TO_SERVICE = 100;
	public static final int MSG_FROM_SERVICE = 200;
	
	public static final String MSG_CONTENT ="Content";
	
	public static final int CODE_SUCCESS = 0; // success

	TreeMap<Integer,Messenger> mClients = new TreeMap<Integer,Messenger>(); // Keeps track of all current registered clients.
	
	@Override
    public void handleMessage(Message msg) {
		switch (msg.what) {
		case MSG_REGISTER_CLIENT: // used in client
			mClients.put(msg.arg1,msg.replyTo);
			break;
		case MSG_UNREGISTER_CLIENT: // used in client
			mClients.remove(msg.arg1);
			break;
		case MSG_TO_SERVICE: // used in client
			handleToService(msg);
			break;
		case MSG_FROM_SERVICE: // use in service
			handleFromService(msg);
			break;
        default:
            super.handleMessage(msg);
		
		}
	}
	
	private void handleToService(Message msg) {
		int id = msg.arg1;
		Log.i(TAG,"message to service="+msg.getData().getString(MSG_CONTENT));
		handleToServiceExt(msg);
		// tell client message received successfully
		Messenger m = mClients.get(id);
		try {
			if(m!=null) m.send(Message.obtain(null, MSG_FROM_SERVICE, id, CODE_SUCCESS));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void handleFromService(Message msg) {
		Log.i(TAG,"message from service, id="+msg.arg1+",code="+msg.arg2);
		handleFromServiceExt(msg);
	}
	
	/**
	 * implement in service
	 * @param msg
	 */
	public abstract void handleToServiceExt(Message msg);

	/**
	 * implement in client
	 * @param msg
	 */
	public abstract void handleFromServiceExt(Message msg);

}
