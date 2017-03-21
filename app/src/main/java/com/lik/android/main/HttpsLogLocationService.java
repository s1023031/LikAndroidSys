package com.lik.android.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.params.HttpConnectionParams;
import org.xmlpull.v1.XmlPullParserException;

import com.google.android.maps.GeoPoint;
import com.lik.Constant;
import com.lik.android.main.R;
import com.lik.android.om.SysProfile;
import com.lik.android.util.FileUtil;
import com.lik.android.util.HttpsClient;
import com.lik.android.util.TrackerUtil;
import com.lik.util.ExternalStorage;
import com.lik.util.StringCompressor;
import com.lik.util.XmlUtilExt;

import android.app.IntentService;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

/**
 * �N���O�W����m��T�A�W�Ǧܫ�x
 * 
 * InformServerThread : �N���O�W�w���T�W�Ǧܫ�x�D��
 * 
 * @author mouwang
 *
 */
public class HttpsLogLocationService extends IntentService implements LocationListener {

	private static final String TAG = HttpsLogLocationService.class.getName();
	private final int LOG_POSITION_WAIT_INTERVAL = 60000; // 1 min,�]�A�W�Ǧ�m��T�Υ��O�����O����m�϶�
	private final int LOG_POSITION_DISTANCE = 1; // >1m will log position 
	private final int LOG_POSITION_NOT_UPDATE_INTERVAL = 5; // 5 min�����`������m��T�A�N���s���oprovider

	public static final String KEY_LAST_UPLOAD_POSITION = "LastUploadPosition.";	
	public static final String KEY_USERNO = "userNo.";	
	public static final String KEY_DIR = "dir";	
	public static final String KEY_DEVICEID = "DEVICEID";	
	public static final String RETURN_CODE = "ReturnCode";

	SimpleDateFormat sqliteDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",Locale.CHINESE); // sqlite ����x�s�榡
	SimpleDateFormat sdf3t = new SimpleDateFormat("HHmm",Locale.CHINESE); // time use for log location
	
	ScreenReceiver mReceiver;
	public static boolean isAliveLogPosition = false;
	LocationManager locationManager;
	String provider = LocationManager.GPS_PROVIDER; // default
	private Date lastTimeUpdatePosition = new Date(); // init
	private Location lastLocation;
	TrackerUtil tracker;
	MyLocationListener gpsListener,networkListener;
	private String startTime = "0800";
	private String endTime = "2000";

	String siteName;
	String systemNo;
	String version;
	boolean isGPSEnabled;
	boolean isNetworkEnabled;
	boolean isLastTabletInfoSuccess = false;
	
	String userNo;
	File file;
	String DEVICEID;
	String dir;

	SysProfile omCurrentSysProfile;

	public HttpsLogLocationService() {
		super(TAG);
	}
	
	public HttpsLogLocationService(String name) {
		super(name);
	}

    @Override
    public void onCreate() {
    	super.onCreate();
		siteName = Settings.System.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
		systemNo = getBaseContext().getString(R.string.app_code);
		/*���A��String.xml �����]�w*/
//		version = getBaseContext().getString(R.string.version);
		try {
			version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
    	// register receiver that handles screen on and screen off logic
    	IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
    	filter.addAction(Intent.ACTION_SCREEN_OFF);
    	mReceiver = new ScreenReceiver();
    	registerReceiver(mReceiver, filter);
    	isAliveLogPosition = true;

    	startTime = getBaseContext().getString(R.string.MapTracker_StartLogTime);
		endTime = getBaseContext().getString(R.string.MapTracker_EndLogTime);

    	locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    	isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    	isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		Log.i(TAG,"isGPSEnabled="+isGPSEnabled);
		Log.i(TAG,"isNetworkEnabled="+isNetworkEnabled);
    	// gps is 1st priority
		if(isGPSEnabled) provider = LocationManager.GPS_PROVIDER;
		else if(isNetworkEnabled) provider = LocationManager.NETWORK_PROVIDER;
		Log.i(TAG,"provider="+provider);
		gpsListener = new MyLocationListener(LocationManager.GPS_PROVIDER);
		networkListener = new MyLocationListener(LocationManager.NETWORK_PROVIDER);
		locationManager.requestLocationUpdates(provider, LOG_POSITION_WAIT_INTERVAL, LOG_POSITION_DISTANCE, this); // every 30 secs log track
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOG_POSITION_WAIT_INTERVAL, LOG_POSITION_DISTANCE, gpsListener); // every 30 secs log track	    		
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOG_POSITION_WAIT_INTERVAL, LOG_POSITION_DISTANCE, networkListener); // every 30 secs log track	
		Notification.Builder builder = new Notification.Builder(getBaseContext());
		builder.setSmallIcon(0);
		builder.setTicker("LogLocationService is about to be killed!");
		builder.setWhen(System.currentTimeMillis());
		Notification note = builder.getNotification();
	    note.flags |= Notification.FLAG_NO_CLEAR;
		startForeground(42,note);
    }

    @Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG,"onHandleIntent called");
		// sleep until prvious service stop if exists
		if(!isAliveLogPosition) {
			try {
				Log.i(TAG,"previous isAliveLogPosition="+isAliveLogPosition);
				Thread.sleep(LOG_POSITION_WAIT_INTERVAL);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
    	isAliveLogPosition = true;
		boolean screenOff = intent.getBooleanExtra("screen_state", false);
		Log.d(TAG,"screenOff="+screenOff);
		
		if(intent.getStringExtra(KEY_USERNO)!=null) userNo = intent.getStringExtra(KEY_USERNO);
		if(intent.getStringExtra(KEY_DEVICEID)!=null) DEVICEID = intent.getStringExtra(KEY_DEVICEID);
		if(userNo==null) userNo = MainMenuActivity.currentUserNo;
		if(DEVICEID==null) DEVICEID = Settings.System.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
		if(intent.getStringExtra(KEY_DIR)!=null && userNo!=null && DEVICEID!=null) {
			dir = intent.getStringExtra(KEY_DIR);
			tracker = new TrackerUtil(userNo,DEVICEID,dir);
		} else {
			Log.w(TAG,"userNo="+userNo+",DEVICEID="+DEVICEID+",dir="+dir);
			isAliveLogPosition = false;
			return;
		}
		omCurrentSysProfile = (SysProfile)intent.getSerializableExtra("omCurrentSysProfile");
		mReceiver.setSysProfile(omCurrentSysProfile);
		Log.d(TAG,"userNo="+userNo);
		new InformServerThread(LocationManager.GPS_PROVIDER,isGPSEnabled).start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// do nothing
		}
		new InformServerThread(LocationManager.NETWORK_PROVIDER,isNetworkEnabled).start();		
		while(isAliveLogPosition) {
			try {
				Thread.sleep(LOG_POSITION_WAIT_INTERVAL); // sleep 60sec
				if(!isAliveLogPosition) continue;
    			if(!isNetworkAvailable()) {
    				Log.i(TAG,"Network not avaliable, by pass upload position...");
    				continue;
    			}
				Log.d(TAG,"start...");
				Date startDate = MainMenuActivity.getCurrentDate();
    			/*�W�������\TabletInfo,�A���@��*/
    			if(!isLastTabletInfoSuccess) {
    		    	isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    		    	isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    				new InformServerThread(LocationManager.GPS_PROVIDER,isGPSEnabled).start();	
    				try {
    					Thread.sleep(1000);
    				} catch (InterruptedException e1) {
    					// do nothing
    				}
    				new InformServerThread(LocationManager.NETWORK_PROVIDER,isNetworkEnabled).start();	
    			}
		        SharedPreferences settings = getBaseContext().getSharedPreferences(MainMenuActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		        SharedPreferences.Editor editor = settings.edit();
        		String last = settings.getString(KEY_LAST_UPLOAD_POSITION+userNo, null);
        		Log.d(TAG,"last ="+last);
        		HttpClient httpclient = HttpsClient.getHttpsClient(
        		    	getResources().openRawResource(R.raw.jssecacerts)
        		    );
            	BufferedReader reader = null;
	    	    try {
	    	    	if(tracker==null) {
	    	    		tracker = new TrackerUtil(userNo,DEVICEID,getBackupDir());
	    	    	}
	    			file = tracker.getFile();
	    			Log.d(TAG,"fileName="+file==null?"":file.getName());
		        	reader = new BufferedReader(new FileReader(file));
		        	String sLine = null;
        			StringBuffer sb = new StringBuffer();
        			sb.append("<Root TableName='MapTracker'>").append("\n");
        			StringBuffer sbd = new StringBuffer();
        			sbd.append("<DetailList TableName='MapTracker'>").append("\n");
        			int size = 0;
        			String timeString = null;
        			String timeLast = null;
		        	while((sLine=reader.readLine()) != null) {
		        		String[] split = sLine.split(",");
		        		if(split.length>=3) {
		        			timeLast = split[0];
			        		if(last != null && last.compareTo(split[0])>=0) continue; 
		        			size++;
		        			if(size==1) timeString = split[0];
		        			// pack to xml
		        			sbd.append("<Detail>").append("\n");
		        			sbd.append("<SerialNo>").append(DEVICEID).append("</SerialNo>").append("\n");
		        			sbd.append("<UserNo>").append(userNo).append("</UserNo>").append("\n");
		        			sbd.append("<TimeString>").append(split[0]).append("</TimeString>").append("\n");
		        			sbd.append("<Longitude>").append(split[1]).append("</Longitude>").append("\n");
		        			sbd.append("<Latitude>").append(split[2]).append("</Latitude>").append("\n");
		        			sbd.append("</Detail>").append("\n");
		        		}        		
		        	}
        			sbd.append("</DetailList>").append("\n");
        			
        			sb.append("<DetailSize>").append(size).append("</DetailSize>").append("\n");
        			sb.append(sbd);
        			sb.append("</Root>");
        			final String xml = sb.toString();
        			Log.d(TAG,xml);
	    	    	
        			/* 2013/10/11 ���קK��x��ư��D�A�Y�ϵL�s��ơA�]�n�W�� 
        			 * 2013/11/26 cloud���n�ǡA�H�`���W�e�Υ��O�ٹq
        			 **/
        			if(size==0) continue;
        			if(timeString==null && size==0) timeString = (timeLast==null?sqliteDF.format(startDate):timeLast);
	    	    		
					Log.d(TAG,"start uploading position...");
        			StringBuffer url = new StringBuffer();
					url.append(omCurrentSysProfile.getProtocolURL());
    				url.append(getText(R.string.ProcessLogLocationURICloud));
    				url.append("?siteName=").append(DEVICEID);
    				url.append("&userNo=").append(userNo);
    				url.append("&systemNo=").append(getText(R.string.app_code));
    				url.append("&registerNo=").append(omCurrentSysProfile.getCompanyNo());
    				url.append("&timeString=").append(URLEncoder.encode(timeString,"UTF-8"));
    				Log.d(TAG,"url="+url);
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
		    	        	byte[] buf = StringCompressor.compress(xml);
		    	        	outstream.write(buf);
		    				outstream.flush();
		    	        }

		    	    };

		    	    httppost.setEntity(entity);
	  	          	ResponseHandler<String> responseHandler=new BasicResponseHandler();
	  	          	String result = httpclient.execute(httppost,responseHandler).trim();
	  	          	Log.i(TAG,"result="+result);
	  	          	if(result.startsWith(Constant.SUCCESS)) {
	  	          		timeString = result.substring(Constant.SUCCESS.length()+1);
  	          			editor.putString(KEY_LAST_UPLOAD_POSITION+userNo, timeString);
  	          			editor.commit();
	  	          	}
	  	      	} catch (FileNotFoundException e) {
	  	      		e.printStackTrace();
	    	    } catch (ClientProtocolException e) {
	    	        Log.e(TAG,e.fillInStackTrace().toString());
	    	    } catch (IOException e) {
	    	        Log.e(TAG,e.fillInStackTrace().toString());
	    	    } finally {
	    	    	if(reader != null)
						try {
							reader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
		    	    // When HttpClient instance is no longer needed,
		    	    // shut down the connection manager to ensure
		    	    // immediate deallocation of all system resources
		            if(httpclient != null) httpclient.getConnectionManager().shutdown();
	    	    }
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    
    }

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
		isAliveLogPosition = false;
    	locationManager.removeUpdates(this);
    	if(networkListener != null) locationManager.removeUpdates(networkListener);
    	if(gpsListener != null) locationManager.removeUpdates(gpsListener);
    	if(tracker != null) tracker.close();
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.d(TAG,location.getLongitude()+","+location.getLatitude()+",provider="+location.getProvider());
		LogLocation(location);
	}

	@Override
	public void onProviderDisabled(String provider) {
		Log.d(TAG,"onProviderDisabled called, provider="+provider);	
		if(provider.equals(LocationManager.GPS_PROVIDER) && 
				locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			this.provider = LocationManager.NETWORK_PROVIDER;
			locationManager.removeUpdates(this);
			locationManager.requestLocationUpdates(this.provider, LOG_POSITION_WAIT_INTERVAL, LOG_POSITION_DISTANCE, this); // every 60 secs log track			
		} else if(provider.equals(LocationManager.NETWORK_PROVIDER) && 
				locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			this.provider = LocationManager.NETWORK_PROVIDER;
			locationManager.removeUpdates(this);
			locationManager.requestLocationUpdates(this.provider, LOG_POSITION_WAIT_INTERVAL, LOG_POSITION_DISTANCE, this); // every 60 secs log track						
		}
	}

	@Override
	public void onProviderEnabled(String provider) {
		Log.d(TAG,"onProviderEnabled called, provider="+provider);				
		if(provider.equals(LocationManager.GPS_PROVIDER)) {
			this.provider = LocationManager.GPS_PROVIDER;
			locationManager.removeUpdates(this);
			locationManager.requestLocationUpdates(this.provider, LOG_POSITION_WAIT_INTERVAL, LOG_POSITION_DISTANCE, this); // every 60 secs log track			
		}	
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Log.d(TAG,"onStatusChanged called, provider="+provider+", status="+status);				
	}
	
	int numOfSkipped = 0;
	private void LogLocation(Location location) {
		Date newDate = new Date(location.getTime());
		// use network time if necessary
		newDate = MainMenuActivity.getCurrentDate(newDate);
		String sTime = sdf3t.format(newDate);
		if(sTime.compareTo(startTime)<0 ||
				sTime.compareTo(endTime)>0) {
			Log.i(TAG,"current time="+sTime+", log file skipped!");
			return;
		}		
		StringBuffer sb = new StringBuffer();
		sb.append(sqliteDF.format(newDate)).append(",");
		sb.append(location.getLongitude()).append(",");
		sb.append(location.getLatitude()).append(",");		
		sb.append(location.getProvider()).append(",");		
		double speed = 0;
		if(location.hasSpeed()) speed = location.getSpeed()*60*60/1000;
		else speed = speedKMH(location,newDate, lastLocation==null?location:lastLocation,lastTimeUpdatePosition);
		sb.append(speed);		
		Log.d(TAG,"speed="+speed);
		Log.d(TAG,"speed2="+location.getSpeed()*60*60/1000);
		Log.d(TAG,"has speed="+location.hasSpeed());
		Log.d(TAG,"location.getTime()="+new Date(location.getTime()));
		if(speed>200 && numOfSkipped<3) {
			Log.i(TAG,"location skipped,"+location);
			numOfSkipped++;
			return; // skipped this location
		}
		numOfSkipped = 0; // reset
		if(tracker!=null) tracker.write(sb.toString());
		lastLocation(location);
		lastTimeUpdatePosition(newDate);
		
	}
	
	private synchronized void lastTimeUpdatePosition(Date date) {
		lastTimeUpdatePosition = date;
	}

	private synchronized void lastLocation(Location location) {
		lastLocation = location;
	}
	
	private String getBackupDir() {
		Map<String, File> externalLocations = ExternalStorage.getAllStorageLocations();
		File externalSdCard = externalLocations.get(ExternalStorage.EXTERNAL_SD_CARD);
		Log.d(TAG,"externalSdCard="+externalSdCard);
        String backupDir = externalSdCard+getResources().getString(R.string.OrdersBackupDir);
    	if(externalSdCard==null) {
        	backupDir = Environment.getExternalStorageDirectory()+getResources().getString(R.string.OrdersBackupDir);	        		
    	}
        File file = new File(backupDir);
    	new FileUtil(file,true);
    	return backupDir;

	}

	/**
	 * distance in KM
	 * @param lat1
	 * @param long1
	 * @param lat2
	 * @param long2
	 * @return
	 */
	public double distanceOfTwoPoint(double lat1, double long1, double lat2, double long2) {
        // convert 2 points distance
        double R = 6371; // Earth's radius (km)
        double convert = Math.PI/180;
        lat1 = lat1*convert;
        lat2 = lat2*convert;
        long1 = long1*convert;
        long2 = long2*convert;
        double d = Math.acos(Math.sin(lat1) * 
        		Math.sin(lat2) + Math.cos(lat1) *
        		Math.cos(lat2) *
        		Math.cos(long2-long1)) * R;
        Log.i(TAG,"distance(m)="+d*1000);
        return d;
	}
	
	public double distanceOfTwoPoint(GeoPoint p1, GeoPoint p2) {
		return distanceOfTwoPoint(p1.getLatitudeE6()/1E6,p1.getLongitudeE6()/1E6,
				p2.getLatitudeE6()/1E6,p2.getLongitudeE6()/1E6);
	}
	
	public double distanceOfTwoPoint(Location l1, Location l2) {
		return distanceOfTwoPoint(l1.getLatitude(),l2.getLongitude(),
				l1.getLatitude(),l2.getLongitude());
	}
	
	/**
	 * sped in KM/h
	 * @param lat1
	 * @param long1
	 * @param t1
	 * @param lat2
	 * @param long2
	 * @param t2
	 * @return
	 */
	public double speedKMH(double lat1, double long1, Date t1,
			double lat2, double long2, Date t2) {
		if(t1==null || t2==null) return 0;
		double d = distanceOfTwoPoint(lat1,long1,lat2,long2);
		long lt1 = t1.getTime();
		long lt2 = t2.getTime();
		if((lt1-lt2)==0) return 0;
		else return d/(lt1-lt2)*1000*60*60;
	}
	
	public double speedKMH(Location l1, Date t1,
			Location l2, Date t2) {
		return speedKMH(l1.getLatitude(),l1.getLongitude(),t1,
				l2.getLatitude(),l2.getLongitude(),t2);
	}

	private class MyLocationListener implements LocationListener {
		
		private String provider;
		
		private MyLocationListener(String provider) {
			this.provider = provider;
		}

		@Override
		public void onLocationChanged(Location location) {
			Log.d(provider,"new location="+location);
			// GPS_PROVIDER
			if(!HttpsLogLocationService.this.provider.equals(provider) && 
					provider.equals(LocationManager.GPS_PROVIDER)) {
				HttpsLogLocationService.this.provider = provider;
				locationManager.removeUpdates(HttpsLogLocationService.this);
				locationManager.requestLocationUpdates(provider, LOG_POSITION_WAIT_INTERVAL, LOG_POSITION_DISTANCE, HttpsLogLocationService.this); // every 60 secs log track								
				LogLocation(location);
			}
			// NETWORK_PROVIDER
			if(provider.equals(LocationManager.NETWORK_PROVIDER) &&
					!HttpsLogLocationService.this.provider.equals(provider)) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.MINUTE, 0-LOG_POSITION_NOT_UPDATE_INTERVAL);
				if(lastTimeUpdatePosition!= null && cal.getTime().after(lastTimeUpdatePosition)) {
					Log.w(provider,"position not updated, try to update provider...");
					HttpsLogLocationService.this.provider = LocationManager.NETWORK_PROVIDER;
					locationManager.removeUpdates(HttpsLogLocationService.this);
					locationManager.requestLocationUpdates(provider, LOG_POSITION_WAIT_INTERVAL, LOG_POSITION_DISTANCE, HttpsLogLocationService.this); // every 60 secs log track
					LogLocation(location);
				}				
			}
			Log.d(provider,"provider="+HttpsLogLocationService.this.provider);
		}

		@Override
		public void onProviderDisabled(String provider) {
			Log.d(provider,"MyLocationListener, onProviderDisabled called, provider="+provider);	
			new InformServerThread(provider,false).start();
		}

		@Override
		public void onProviderEnabled(String provider) {
			Log.d(provider,"MyLocationListener, onProviderEnabled called, provider="+provider);	
			HttpsLogLocationService.this.provider = this.provider;
			locationManager.removeUpdates(HttpsLogLocationService.this);
			locationManager.requestLocationUpdates(this.provider, LOG_POSITION_WAIT_INTERVAL, LOG_POSITION_DISTANCE, HttpsLogLocationService.this); // every 60 secs log track	
			new InformServerThread(provider,true).start();
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			Log.d(provider,"MyLocationListener, onStatusChanged called, provider="+provider);	
		}
		
	}

	private class InformServerThread extends Thread {
		
		String provider;
		boolean isEnabled;
		boolean isSuccess = false;
		
		InformServerThread(String provider, boolean isEnabled) {
			this.provider = provider;
			this.isEnabled = isEnabled;
		}

		@Override
		public void run() {
			// update url
			StringBuffer urlUpdate = new StringBuffer();
			urlUpdate.append(omCurrentSysProfile.getProtocolURL());
			urlUpdate.append(getBaseContext().getString(R.string.processTabletInfoCloud));
			urlUpdate.append("?siteName="+siteName);
			urlUpdate.append("&systemNo="+systemNo);
			urlUpdate.append("&registerNo=").append(omCurrentSysProfile.getCompanyNo());
			urlUpdate.append("&userNo="+userNo);
			Log.i(TAG,"update url="+urlUpdate.toString());
			// update xml
			StringBuffer sb = new StringBuffer();
			sb.append("<Root TableName='None'>").append("\n");
			sb.append("<DetailSize>").append(0).append("</DetailSize>").append("\n");
			sb.append("<Provider>").append(provider).append("</Provider>").append("\n");
			sb.append("<IsEnabled>").append(isEnabled).append("</IsEnabled>").append("\n");
			sb.append("<Version>").append(version).append("</Version>").append("\n");
			sb.append("</Root>");
			final String xml = sb.toString();
			InputStreamReader in = null;
			HttpClient httpclient = HttpsClient.getHttpsClient(
			    	getResources().openRawResource(R.raw.jssecacerts)
			    );
		    try {
		    	HttpPost httppost = new HttpPost(urlUpdate.toString());
			    HttpConnectionParams.setConnectionTimeout(httppost.getParams(), MainMenuFragment.HTTP_CONNECTION_TIMEOUT); // timeout 5 secs
			    HttpConnectionParams.setSoTimeout(httppost.getParams(), MainMenuFragment.HTTP_DATA_TIMEOUT); // timeout 10 secs
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
	    	        	byte[] buf = StringCompressor.compress(xml);
	    	        	outstream.write(buf);
	    				outstream.flush();
	    	        }

	    	    };
	    	    httppost.setEntity(entity);	        
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity rentity = response.getEntity();
		        // If the response does not enclose an entity, there is no need
		        // to worry about connection release
		        if (rentity != null) {
		        	sb = new StringBuffer();
		            in = new InputStreamReader(rentity.getContent(),"UTF-8");
	            	char[] buf = new char[1024];
	            	int len = in.read(buf);
	            	while(len != -1) {
	            		sb.append(buf,0,len);
	            		len = in.read(buf);
	            	}
		            Log.i(TAG,sb.toString());
		            XmlUtilExt util = new XmlUtilExt(sb.toString());
		            TreeMap<String,String> map = util.getMaster();
		            if(!Constant.isEmpty(map.get(RETURN_CODE))) {
		            	String returnCode = map.get(RETURN_CODE);
		            	if(returnCode.equals(Constant.SUCCESS)) {
		            		Log.i(TAG,"update success!");
		            		isSuccess=true;
		            	} else {
		            		Log.w(TAG,"update failed!");
		            	}
		            }
		        }
			    // When HttpClient instance is no longer needed,
			    // shut down the connection manager to ensure
			    // immediate deallocation of all system resources
		        httpclient.getConnectionManager().shutdown();
		    } catch (ClientProtocolException e) {
		        Log.e(TAG,e.fillInStackTrace().toString());
		    } catch (IOException e) {
		        Log.e(TAG,e.fillInStackTrace().toString());
		    } catch (XmlPullParserException e) {
				Log.e(TAG,e.fillInStackTrace().toString());
			} finally {
				if(in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				isLastTabletInfoSuccess = isSuccess;
			}
		}
		
	}

    private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

}
