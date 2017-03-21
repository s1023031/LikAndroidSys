package com.lik.android.main;

import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class DateTimeChangedReceiver extends BroadcastReceiver {

	private static final String TAG = DateTimeChangedReceiver.class.getName();

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG,"onReceive called");
		if (intent.getAction().equals(Intent.ACTION_TIME_CHANGED) || intent.getAction().equals(Intent.ACTION_DATE_CHANGED)) {
			Log.d(TAG,"date or time has reset..."+new Date());
			MainMenuActivity.timeDiff = 0;
			new NetworkTimeTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}
	}
	 
}