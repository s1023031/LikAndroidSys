package com.lik.android.util;

import java.security.MessageDigest;
import java.util.Random;

import android.util.Base64;

public class Hash {
	
	public static final String HASH_METHOD = "MD5";
	
	public static final String KEY = "LiKuan";
	
	public static final Random random = new Random();
	
	public static String getHash(String org) {
		String result = org;
		try {     
			   MessageDigest messageDigest = MessageDigest.getInstance(HASH_METHOD);  
			   messageDigest.update(org.getBytes());  
			   result = Base64.encodeToString(messageDigest.digest(), Base64.DEFAULT);
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
		return result;
	}
	
	/**
	 * encrypt use XOR
	 * @param str
	 * @param key
	 * @return
	 */
	public static String encryptXOR(String str, String key) {
		StringBuffer sb = new StringBuffer();
		int keyPos = 0;
		int keyLenth = key==null?0:key.length();
		key = key==null?KEY:key;
		int offset = random.nextInt(256);
		String hex = Integer.toHexString(offset);
		sb.append(hex.length()==1?"0"+hex:hex);
		for(int i=0;i<str.length();i++) {
			int srcASCii = (str.charAt(i) + offset)%255;
			if(keyPos<keyLenth-1) keyPos++;
			else keyPos = 0;
			srcASCii = srcASCii ^ key.charAt(keyPos);
			hex = Integer.toHexString(srcASCii);
			sb.append(hex.length()==1?"0"+hex:hex);
			offset = srcASCii;
		}
		return sb.toString();
	}

	/**
	 * encrypt use XOR
	 * @param str
	 * @return
	 */
	public static String encryptXOR(String str) {
		return encryptXOR(str,null);
	}

	/**
	 * decrypt use XOR
	 * @param str
	 * @param key
	 * @return
	 */
	public static String decryptXOR(String str, String key) {
		StringBuffer sb = new StringBuffer();
		int offset = Integer.parseInt(str.substring(0,2), 16);
		int srcPos = 2;
		int keyPos = 0;
		int keyLenth = key==null?0:key.length();
		key = key==null?KEY:key;
		do {
			String sub = str.substring(srcPos,srcPos+2);
			int srcASCii = Integer.parseInt(sub, 16);
			if(keyPos<keyLenth-1) keyPos++;
			else keyPos = 0;
			int tmpSrcAsc = (char)srcASCii^key.charAt(keyPos);
	        if(tmpSrcAsc <= offset) tmpSrcAsc = 255 + tmpSrcAsc - offset;
	        else tmpSrcAsc = tmpSrcAsc - offset;
	        sb.append((char)tmpSrcAsc);
	        offset = srcASCii;
	        srcPos += 2;
		} while(srcPos<str.length());
		return sb.toString();
	}
	
	/**
	 * decrypt use XOR
	 * @param str
	 * @return
	 */
	public static String decryptXOR(String str) {
		return decryptXOR(str,null);
	}
}
