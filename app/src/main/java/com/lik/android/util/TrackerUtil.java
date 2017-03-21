package com.lik.android.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import com.lik.Constant;
import com.lik.android.main.R;

import android.app.Activity;
import android.util.Log;

public class TrackerUtil {

	protected static final String TAG = TrackerUtil.class.getName();
	private File file;
	private FileWriter writer;
	private String userNo;
	private String deviceId;
	private String dateString;
	private String dir;
	
	public TrackerUtil(String userNo, String deviceId, String dir) {
		init(null,userNo,deviceId,dir);
	}

	public TrackerUtil(Activity myActivity, String userNo, String deviceId) {
		String dir = myActivity.getString(R.string.TrackerBackupDir);
		init(myActivity,userNo,deviceId,dir);
	}

	public TrackerUtil(Activity myActivity, String userNo, String deviceId, String dir) {
		init(myActivity,userNo,deviceId,dir);
	}
	
	private void init(Activity myActivity, String userNo, String deviceId, String dir) {
		this.userNo = userNo;
		this.deviceId = deviceId;
		this.dir = dir;
		file = new File(dir);
		if(!file.exists()) {
			file.mkdirs();
		}
		StringBuffer fileName = new StringBuffer();
		Date current = new Date();
		fileName.append(userNo);
		fileName.append("-").append(deviceId);
		dateString = Constant.sdf2d.format(current);
		fileName.append("-").append(dateString);
		fileName.append(".trk");
		file = new File(dir+"/"+fileName);
		try {
			writer = new FileWriter(file,true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void write(String str) {
		try {
			Date current = new Date();
			// check date
			String s = Constant.sdf2d.format(current);
			if(!s.equals(dateString)) {
				close(); // close previous file
				dateString = s;
				StringBuffer fileName = new StringBuffer();
				fileName.append(userNo);
				fileName.append("-").append(deviceId);
				fileName.append("-").append(dateString);
				fileName.append(".trk");
				file = new File(dir+"/"+fileName);
				try {
					writer = new FileWriter(file,true);
				} catch (IOException e) {
					e.printStackTrace();
				}				
				Log.i(TAG,"day changed, switch to new file:"+file.getName());
			}
			if(writer==null) {
				Log.e(TAG,"writer is null, please check!");
				return;
			}
			writer.write(str);
			writer.write("\n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			if(writer!=null) writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public File getFile() {
		return file;
	}
	
}
