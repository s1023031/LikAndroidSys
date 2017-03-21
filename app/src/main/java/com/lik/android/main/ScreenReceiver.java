package com.lik.android.main;

import com.lik.android.om.SysProfile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenReceiver extends BroadcastReceiver {

	private static final String TAG = ScreenReceiver.class.getName();

	private boolean screenOff;
	 
	private SysProfile sysProfile;
	
	public SysProfile getSysProfile() {
		return sysProfile;
	}

	public void setSysProfile(SysProfile sysProfile) {
		this.sysProfile = sysProfile;
	}

	public ScreenReceiver() {
		super();
	}

	public ScreenReceiver(SysProfile sysProfile) {
		this.sysProfile = sysProfile;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG,"onReceive called");
		if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
			screenOff = true;
		} else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
			screenOff = false;
		}
		Intent i = new Intent(context, LogLocationService.class);
		boolean isAliveLogPosition = LogLocationService.isAliveLogPosition;
		if(sysProfile!=null && sysProfile.isCloud()) {
			i = new Intent(context, HttpsLogLocationService.class);
			i.putExtra("omCurrentSysProfile", sysProfile);
			isAliveLogPosition = HttpsLogLocationService.isAliveLogPosition;
		}
		i.putExtra("screen_state", screenOff);
		if(!isAliveLogPosition) {
			Log.d(TAG,"start LogLocationService...");
			context.startService(i);
		} else {
			Log.d(TAG,"LogLocationService is alive, skipped...");
		}
	}
	 
}