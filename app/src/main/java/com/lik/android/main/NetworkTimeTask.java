package com.lik.android.main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import android.os.AsyncTask;
import android.util.Log;

public class NetworkTimeTask extends AsyncTask<String, Integer, Long> {

	protected static final String TAG = NetworkTimeTask.class.getName();
	public static final String TIME_SERVER = "time.nist.gov";
	public static final long TIME_DIFF = 1800000; // 30 mins

	@Override
	protected Long doInBackground(String... args) {
		Log.d(TAG,"start NetworkTimeTask...");
//		if(args.length>0) {
//			int misec = Integer.parseInt(args[0])*1000;
//            try {
//				Thread.sleep(misec);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
		long returnTime=0;
	    NTPUDPClient timeClient = new NTPUDPClient();
	    InetAddress inetAddress;
		try {
			inetAddress = InetAddress.getByName(TIME_SERVER);
		    TimeInfo timeInfo = timeClient.getTime(inetAddress);
		    long localTime = timeInfo.getReturnTime();   //local device time
		    Log.d(TAG, "local Time "+new Date(localTime));
		    returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();   //server time
	
		    Date time = new Date(returnTime);
		    Log.d(TAG, "Time from " + TIME_SERVER + ": " + time);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return returnTime;
	}
	
	protected void onPostExecute(Long result) {
		if(result!=0) {
			Calendar cal = Calendar.getInstance(Locale.CHINESE);
			Log.i(TAG,"NetworkTime="+new Date(result));
			Log.i(TAG,"DeviceTime="+cal.getTime());
			long diff = result - cal.getTimeInMillis();
			if(diff>=TIME_DIFF || diff<=-TIME_DIFF) {
				Log.w(TAG,"time diff more than 10 mins!");
				//MainMenuActivity1.timeDiff = diff;
			} else {
				//MainMenuActivity1.timeDiff = 0;
			}
			//Log.i(TAG,"timeDiff="+MainMenuActivity1.timeDiff);
		}
	}
	
}
