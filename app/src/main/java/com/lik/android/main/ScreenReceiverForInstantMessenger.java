package com.lik.android.main;

import com.lik.android.om.Account;
import com.lik.android.om.SysProfile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenReceiverForInstantMessenger extends BroadcastReceiver {

	private static final String TAG = ScreenReceiverForInstantMessenger.class.getName();

	private boolean screenOff;
	private SysProfile sysProfile;
	private Account omCurrentAccount;
	
	public SysProfile getSysProfile() {
		return sysProfile;
	}

	public void setSysProfile(SysProfile sysProfile) {
		this.sysProfile = sysProfile;
	}

	public Account getOmCurrentAccount() {
		return omCurrentAccount;
	}

	public void setOmCurrentAccount(Account omCurrentAccount) {
		this.omCurrentAccount = omCurrentAccount;
	}

	public ScreenReceiverForInstantMessenger() {
		super();
	}

	public ScreenReceiverForInstantMessenger(SysProfile sysProfile, Account omCurrentAccount) {
		this.sysProfile = sysProfile;
		this.omCurrentAccount = omCurrentAccount;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG,"onReceive called");
		if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
			screenOff = true;
		} else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
			screenOff = false;
		}
		boolean isAliveIMS = InstantMessengerService.isAliveIMS;
		Intent i = new Intent(context, InstantMessengerService.class);
/*		if(sysProfile!=null && sysProfile.isCloud()) {
			i = new Intent(context, InstantMessengerMqttService.class);
			i.putExtra("omCurrentSysProfile", sysProfile);
			i.putExtra("omCurrentAccount", omCurrentAccount);
			isAliveIMS = InstantMessengerMqttService.isAliveIMS;
		}*/
		i.putExtra("screen_state", screenOff);
		if(!isAliveIMS) {
			Log.d(TAG,"start InstantMessengerService...");
			context.startService(i);
		} else {
			Log.d(TAG,"InstantMessengerService is alive, skipped...");
		}
	}
	 
}