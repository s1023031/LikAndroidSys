package com.lik.android.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.lik.Constant;
import com.lik.android.main.R;
import com.lik.android.om.SellDetail;

public class XMLUtil {
	
	public static final String TAG = "XMLUtil";

	private static XMLUtil ref = null;
	private Context ctx = null;

	public XMLUtil(Context ctx) {
		this.ctx = ctx;
	}

	public static XMLUtil getInstance(Context ctx) {
        if(ref == null) return new XMLUtil(ctx);
        else return ref;
    }

    public HashMap<String, SellDetallXML> getProductsMap(int companyID, int customerID, Integer deliverOrder) {
    	HashMap<String, SellDetallXML> hm = new  HashMap<String, SellDetallXML>();
		String dataDir = Environment.getExternalStorageDirectory()+ctx.getResources().getString(R.string.SellDetalFileDir);
		InputSource is = null;
		FileReader fr = null;
		try {
			String fileDir = dataDir;				
			fileDir += companyID;
			fileDir += "/";
			fileDir += customerID;
			fileDir += "/";
			if(deliverOrder!=null) {
				fileDir += deliverOrder;
				fileDir += "/";
			}
	        fileDir += "summary.xml";
	        Log.d(TAG,"fileDir="+fileDir);
	        File file = new File(fileDir);
	        if(!file.exists()) {
	        	Log.e(TAG,"not found file="+fileDir);
	        	return hm;
	        }
	        fr = new FileReader(fileDir);
			is = new InputSource(new FileReader(fileDir));
            DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
            Document doc = dfactory.newDocumentBuilder().parse(is);
            Element root = doc.getDocumentElement();
            Log.d(TAG,"root name="+root.getNodeName());
            NodeList nlist = root.getChildNodes();
            for(int i=0;i<nlist.getLength();i++) {
                Node node = nlist.item(i);
                if(node.getNodeName().equals(SellDetail.TABLE_NAME)) {
                	NodeList nlist2 = node.getChildNodes();
                	String sItemNo = null;
                	String sSellDate = null;
                	for(int j=0;j<nlist2.getLength();j++) {
                		Node node2 = nlist2.item(j);
                		if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_ITEMNO)) {
                			sItemNo = node2.getFirstChild().getNodeValue();
                		} else if(node2.getNodeName().equals(SellDetail.COLUMN_NAME_SELLDATE)) {
                			sSellDate = node2.getFirstChild().getNodeValue();
                		}
                	}
                	if(sItemNo != null && sSellDate != null) {
                		SellDetallXML om = hm.get(sItemNo);
                		if(om==null) {
                			om = new SellDetallXML();
                		}
                		String sSellDateList = om.getSellDateList();
                		om.setItemNo(sItemNo);
                		om.setSellDateList(Constant.isEmpty(sSellDateList)?sSellDate:(sSellDateList+","+sSellDate));
                		hm.put(sItemNo, om);
                	}
                }
            }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} finally {
			if(fr != null)
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
    	
    	return hm;
    }
    
    public class SellDetallXML {
    	private String itemNo;
		private String sellDateList;

		public String getItemNo() {
			return itemNo;
		}
		public void setItemNo(String itemNo) {
			this.itemNo = itemNo;
		}
		public String getSellDateList() {
			return sellDateList;
		}
		public void setSellDateList(String sellDateList) {
			this.sellDateList = sellDateList;
		}
    	
    	
    }
}
