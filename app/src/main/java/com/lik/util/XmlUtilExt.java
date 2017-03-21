package com.lik.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * 
 * <Root TableName='master'>
 * <DetailSize></DetailSize>
 * <MasterColumn1></MasterColumn1>
 * <MasterColumn2></MasterColumn2>
 * <DetailList TableName='detail'>
 * <Detail>
 * <DetailColumn1></DetailColumn1>
 * <DetailColumn2></DetailColumn2>
 * </Detail>
 * <Detail>
 * <DetailColumn1></DetailColumn1>
 * <DetailColumn2></DetailColumn2>
 * </Detail>
 * </DetailList>
 * </Root>
 * @author charles
 *
 */
public class XmlUtilExt {

	protected static final String TAG = XmlUtilExt.class.getName();
	
	public static final String ROOT_TAG = "Root";
	public static final String DETAILLIST_TAG = "DetailList";
	public static final String DETAIL_TAG = "Detail";
	public static final String SIZE_TAG = "DetailSize";
    private XmlPullParser xpp;
    private TreeMap<String,String> master = new TreeMap<String,String>();
    private List<TreeMap<String,String>> detail = new ArrayList<TreeMap<String,String>>();
    private String masterTableName;
    private String detailTableName;
    
    public XmlUtilExt(boolean isNamespaceAware) {
    	XmlPullParserFactory factory;
		try {
			factory = XmlPullParserFactory.newInstance();
	    	factory.setNamespaceAware(isNamespaceAware);
	    	xpp = factory.newPullParser();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
    }

    public XmlUtilExt(String xml, boolean isNamespaceAware) throws XmlPullParserException {
    	this(isNamespaceAware);
    	setInput(xml);
    }
    
    public XmlUtilExt(String xml)  throws XmlPullParserException {
    	this(xml,false);
    }
    
    public TreeMap<String,String> getMaster() {
    	return master;
    }
    
    public List<TreeMap<String,String>> getDetail() {
    	return detail;
    }
    
    public String getMasterTableName() {
    	return masterTableName;
    }
    
    public String getDetailTableName() {
    	return detailTableName;
    }
    
    public void setInput(String xml) throws XmlPullParserException {
    	try {
			xpp.setInput(new StringReader(xml));
			int eventType = xpp.getEventType();
			int size = 0;
	        while (eventType != XmlPullParser.END_DOCUMENT) {
	            if (xpp.getEventType() != XmlPullParser.START_TAG) {
	            	eventType = xpp.next();
	                continue;
	            }
	            String tagName = xpp.getName();
	            if(tagName.equals(ROOT_TAG)) {
	            	masterTableName = xpp.getAttributeValue(null,"TableName");
	            } else if(tagName.equals(SIZE_TAG)) {
	            	String value = readText(xpp);
	            	xpp.require(XmlPullParser.END_TAG, null, tagName);
	            	size = Integer.parseInt(value);
	            } else if(tagName.equals(DETAILLIST_TAG)) {
        			if(detailTableName== null) detailTableName = xpp.getAttributeValue(null,"TableName");
	            	for(int i=0;i<size;i++) {
	            		eventType = xpp.nextTag();
	            		if(eventType == XmlPullParser.END_DOCUMENT) break;
	            		if(eventType == XmlPullParser.START_TAG) {
	            			tagName = xpp.getName();
	            			TreeMap<String,String> detailMap = new TreeMap<String,String>();
	            			while((tagName!= null && !tagName.equals(DETAIL_TAG)) || eventType != XmlPullParser.END_TAG) {
	            				if(eventType != XmlPullParser.START_TAG || tagName.equals(DETAIL_TAG)) {
	            	            	eventType = xpp.next();
	            	            	tagName = xpp.getName();
	            	                continue;	            					
	            				}
	            				tagName = xpp.getName();
		            			String value = readText(xpp);
		            			xpp.require(XmlPullParser.END_TAG, null, tagName);
		            			if(!value.equals("") && !value.equalsIgnoreCase("null")) detailMap.put(tagName, value);
		            			eventType = xpp.next();	
		            			tagName = xpp.getName();
	            			}
	            			detail.add(detailMap);
	            			xpp.require(XmlPullParser.END_TAG, null, tagName);
	            			eventType = xpp.next();
	            		}
	            	}

	            } else { // master column tag
	            	String value = readText(xpp);
	            	xpp.require(XmlPullParser.END_TAG, null, tagName);
	            	if(!value.equals("") && !value.equalsIgnoreCase("null")) master.put(tagName, value);
	            }
	            eventType = xpp.next();
	        }			
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
    
	public static void main(String[] args) throws XmlPullParserException {
		String xml = "<Root TableName='master'>"+"\n"+
		"<DetailSize>2</DetailSize>"+"\n"+
		"<DetailList TableName='MapTracker'>"+"\n"+
		"<Detail>"+"\n"+
		"<SerialNo>2f3e5fa53beb0c7e</SerialNo>"+"\n"+
		"<UserNo>1104</UserNo>"+"\n"+
		"<TimeString>2012-10-31 09:09:33.181</TimeString>"+"\n"+
		"<Longtitude>121.3143951</Longtitude>"+"\n"+
		"<Lattitude>24.9912553</Lattitude>"+"\n"+
		"</Detail>"+"\n"+
		"<Detail>"+"\n"+
		"<SerialNo>2f3e5fa53beb0c7e</SerialNo>"+"\n"+
		"<UserNo>1104</UserNo>"+"\n"+
		"<TimeString>2012-10-31 09:09:33.181</TimeString>"+"\n"+
		"<Longtitude>121.3143951</Longtitude>"+"\n"+
		"<Lattitude>24.9912553</Lattitude>"+"\n"+
		"</Detail>"+"\n"+
		"</DetailList>"+"\n"+
		"</Root>";
		XmlUtilExt util = new XmlUtilExt(xml);
		System.out.println(util.getMasterTableName());
		System.out.println(util.getDetailTableName());
		System.out.println(util.getMaster());
		System.out.println(util.getDetail());
    }
}
