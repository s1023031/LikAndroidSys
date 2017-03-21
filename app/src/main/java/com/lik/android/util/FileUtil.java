package com.lik.android.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.util.Log;

public class FileUtil {

	protected static final String TAG = FileUtil.class.getName();
	private File file;
	
	public FileUtil(File file, boolean isDir) {
		Log.d(TAG,"FileUtil dir="+file.getAbsolutePath());
		this.file = file;
		if (!file.exists()) {
			try {
				File pFile = file.getParentFile();
				if(!pFile.exists()) {
					pFile.mkdirs();
				} else {
					if(!pFile.isDirectory()) {
						pFile.delete();
						pFile.mkdirs();
					}
				}
				if(isDir)  file.mkdir();
				else file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void write(String str) throws IOException {
		if(file==null || !file.exists()) return;
		BufferedWriter buf = new BufferedWriter(new FileWriter(file, false));
		buf.append(str);
		buf.close();
	}
	
	public String readFile() throws IOException {
		StringBuffer sb = new StringBuffer();
		if(file==null || !file.exists()) return sb.toString();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = reader.readLine();
		while(line != null) {
			sb.append(line).append("\n");
			line = reader.readLine();
		}
		reader.close();
		return sb.toString();
	}
	
	public boolean deleteFiles(File file) {
		boolean isSuccess = true;
		if(!file.exists()) return isSuccess;
		if(file.isFile()) isSuccess = file.delete();
		else {
			File[] files = file.listFiles();
			if(files.length>0) {
				for(File f : files) {
					if(f.isDirectory()) {
						if(!deleteFiles(f)) {
							isSuccess = false;
							break;
						}
					} else {
						if(!f.delete()) {
							isSuccess = false;
							break;
						}						
					}
				}
			}
			if(isSuccess) isSuccess = file.delete();
		}
		return isSuccess;
	}
	
}
