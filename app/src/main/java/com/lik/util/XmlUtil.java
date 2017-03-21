package com.lik.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.TreeMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class XmlUtil {

	protected static final String TAG = XmlUtil.class.getName();
	public static final String ROOT_TAG = "Record";
    private XmlPullParser xpp;
    private TreeMap<String,String> map = new TreeMap<String,String>();
    private String tableName;
    
    public XmlUtil(boolean isNamespaceAware) {
    	XmlPullParserFactory factory;
		try {
			factory = XmlPullParserFactory.newInstance();
	    	factory.setNamespaceAware(isNamespaceAware);
	    	xpp = factory.newPullParser();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
    }

    public XmlUtil(String xml, boolean isNamespaceAware) {
    	this(isNamespaceAware);
    	setInput(xml);
    }
    
    public TreeMap<String,String> getMap() {
    	return map;
    }
    
    public String getTableName() {
    	return tableName;
    }
    
    public void setInput(String xml) {
    	try {
			xpp.setInput(new StringReader(xml));
			int eventType = xpp.getEventType();
	        while (eventType != XmlPullParser.END_DOCUMENT) {
	            if (xpp.getEventType() != XmlPullParser.START_TAG) {
	            	eventType = xpp.next();
	                continue;
	            }
	            String tagName = xpp.getName();
	            Log.d(TAG,"tag="+tagName);
	            if(tagName.equals(ROOT_TAG)) {
	            	tableName = xpp.getAttributeValue(null,"TableName");
	            } else {
	            	String value = readText(xpp);
	            	xpp.require(XmlPullParser.END_TAG, null, tagName);
	            	if(!value.equals("") && !value.equalsIgnoreCase("null")) map.put(tagName, value);
	            }
	            eventType = xpp.next();
	        }			
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
}
