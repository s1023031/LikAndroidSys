package com.lik.android.main;

import android.app.IntentService;
import android.content.Intent;

public class OrdersUploadService extends IntentService {
	
	private static final String TAG = OrdersUploadService.class.getName();
	private static final int SLEEP_INTERVAL = 2000; // 2 secs
	private static final int LOOP_BEFORE_STOP = 5; // 5 times
	public static final String LIKSYS_COREDATA_UPLOAD_ACTION = "LIKSYS_COREDATA_UPLOAD_ACTION";
	public static final String CODE = "CODE";
	public static final String RESULT = "DATA";
	public static final String RESULT_CONNECT_ERROR = "INFO:CONNECT ERROR";
	public static final String OM_PACKAGE_NAME = "com.lik.android.om.";
	
	public OrdersUploadService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		
	}

}
