package com.lik.android.util;

import android.util.Log;

public class HttpMessage {
	
	private String returnCode;
	private String returnMessage;
	public static final String TAG ="HTTP";
	public boolean parseMessage(String msg) {
		boolean result = false;
		if(msg==null) 
			return result;
		String[] split = msg.split(":");
		Log.d(TAG, "splitlengt="+msg);
		Log.d(TAG, "splitlengt="+split.length);
		if(split.length != 2) 
			return result;
		returnCode = split[0];
		returnMessage = split[1];
		result = true;
		return result;
	}
	
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMessage() {
		return returnMessage;
	}
	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	
}
