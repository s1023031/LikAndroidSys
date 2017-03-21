package com.lik.android.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.TreeMap;

import com.lik.Constant;
import com.lik.util.XmlUtil;

import android.util.Log;

/**
 * This class is used to transfer om toString() to map
 *
 */
public class OmUtil {
	
	public static final String TABLE_HEADER = "TableName";
	public static final String TABLE_DETAIL = "Data";
	public static final String TABLE_SURFIX = "His";
	private static OmUtil ref = null;

    public static OmUtil getInstance() {
        if(ref == null) return new OmUtil();
        else return ref;
    }

    /**
     * wrap om data into map in the following format:
     *  TableName = om table name
     *  Data =  map of om attributes
     * @param map result map of in put string
     * @param s in put om.toString()
     * @throws IOException
     */
    public void toMap(Map<String,Map<String,String>> map, String s) throws IOException {
    	BufferedReader br = new BufferedReader(new StringReader(s));
    	String line = br.readLine(); // header
    	Log.d("OmUtil asdsa",s);
    	if(line == null) {
    		Log.d("OmUtil","nothing to do ");
    		return;
    	}
    	Log.d("OmUtil",line);
    	if(line.startsWith(Constant.XMPP_UPLOAD_RESPONSE)) {
    		String[] split = line.split(":");
    		Map<String,String> response = new TreeMap<String,String>(); 
    		if(split.length==2) response.put(split[0],split[1]);
    		map.put(split[0], response);
    		return;    		
    	}
    	if(line.startsWith(Constant.XMPP_ROOT_HEADER) || line.startsWith(Constant.XMPP_ROOT_FOOTER)) {
    		String[] split = line.split(":");
    		Map<String,String> root = new TreeMap<String,String>(); 	
    		if(split.length==2) root.put(split[0],split[1]);
    		map.put(split[0], root);
    		return;
    	}
    	if(line.startsWith(Constant.XMPP_HEADER) || line.startsWith(Constant.XMPP_FOOTER)) {
    		String[] split = line.split(":");
    		Map<String,String> headerfooter = new TreeMap<String,String>(); 	
    		if(split.length==2) headerfooter.put(split[0],split[1]);
    		map.put(split[0], headerfooter);
    		return;
    	}   	
    	if(s.startsWith("<")) {
    		// xml Record
    		XmlUtil util = new XmlUtil(s,false);
    		Map<String,String> header = new TreeMap<String,String>(); 
    		header.put(TABLE_HEADER, util.getTableName());
    		map.put(TABLE_HEADER,header);
    		map.put(TABLE_DETAIL, util.getMap());
    	} else {
        	String table = line.trim().substring(0, line.length()-1);
        	// �Ytable�O�Ѳ���table�ӡA�h��HIS
        	if(table.endsWith(TABLE_SURFIX)) table = table.substring(0,table.length()-TABLE_SURFIX.length());
        	Map<String,String> header = new TreeMap<String,String>(); 
        	header.put(TABLE_HEADER, table);
        	map.put(TABLE_HEADER,header);
        	Map<String,String> detail = new TreeMap<String,String>(); 	
        	String key = null;
        	StringBuffer value = new StringBuffer();
        	while((line=br.readLine()) != null) {
        		if(line.indexOf("=") >=0) {
        			if(key != null) detail.put(key, value.toString());
        			key = null;
        			value = new StringBuffer();
        			String[] split = line.split(" = ");
        			if(split.length == 2 && !split[1].equalsIgnoreCase("null")) {
        				key = split[0];
        				value.append(split[1]);
        			}
        		} else {
        			value.append("\n").append(line);
        		}
        	}
        	if(key != null) detail.put(key, value.toString());
        	map.put(TABLE_DETAIL, detail);    		
    	}
    }

}
