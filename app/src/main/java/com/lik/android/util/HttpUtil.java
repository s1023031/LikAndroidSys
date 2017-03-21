package com.lik.android.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;

import android.content.Context;
import android.util.Log;

import com.lik.android.main.R;

public class HttpUtil {

	// the timeout until a connection is established
	private static final int CONNECTION_TIMEOUT = 10000; /* 10 seconds */

	// the timeout for waiting for data
	private static final int SOCKET_TIMEOUT = 10000; /* 10 seconds */

	/**
	 * ²����http�s�u���^server�ݦ^�Э�
	 * String[]���e���Nserver�ݦ^�Ф��e�H";"��}��array
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static String httpConnect(String url) throws IOException {
        StringBuffer sb = new StringBuffer() ;
        InputStream in = OpenHttpConnection(url);
        if(in ==null) return sb.toString();
        try {
            InputStreamReader isr = new InputStreamReader(in);
            char[] inputBuffer = new char[128];          
            int charRead;
            while ((charRead = isr.read(inputBuffer))>0)
            {                    
                //---convert the chars to a String---
                String readString = String.copyValueOf(inputBuffer, 0, charRead);                    
                sb.append(readString.trim());
                inputBuffer = new char[128];
            }
        } finally {
        	try {
        		if(in != null) in.close();
        	} catch(IOException e) {
        		e.printStackTrace();
        	}
        }
		
		return sb.toString();
	}
	
    private static InputStream OpenHttpConnection(String urlString) throws IOException {
        InputStream in = null;
        int response = -1;
               
        URL url = new URL(urlString); 
        URLConnection conn = url.openConnection();
                 
        if (!(conn instanceof HttpURLConnection))                     
            throw new IOException("Not an HTTP connection");        
        try{
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setConnectTimeout(CONNECTION_TIMEOUT);
            httpConn.setReadTimeout(SOCKET_TIMEOUT);
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();    
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();                                 
            }            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        	throw new IOException("Error connecting");            
        }
        return in;     
    }	
    
    public static String httpsConnect(Context ctx, String url) throws IOException {
		String result = null;
		int port = 443; // default
		if(url==null) return result;
		// find port
		int istart,iend;
		istart = url.lastIndexOf(":");
		if(istart!=-1) {
			istart++;
			iend = url.substring(istart).indexOf("/");
			if(iend!=-1) {
				try {
					iend += istart;
					String s = url.substring(istart, iend);
					Log.d("HttpUtil","istart="+istart+",iend="+iend+",s="+s);
					port = Integer.parseInt(s);
				} catch(NumberFormatException e) {
					// do nothing
				}
			}
		}
		Log.d("HttpUtil","port="+port);
	    HttpClient httpclient = HttpsClient.getHttpsClient(ctx.getResources().openRawResource(R.raw.jssecacerts), port);
	    try {
	    	HttpGet httpget = new HttpGet(url);
	        ResponseHandler<String> responseHandler=new BasicResponseHandler();
	        result = httpclient.execute(httpget,responseHandler).trim();
	        Log.d("HttpUtil","result="+result);
    	    // When HttpClient instance is no longer needed,
    	    // shut down the connection manager to ensure
    	    // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
	    } catch (ClientProtocolException e) {
	        Log.e("HttpUtil",e.fillInStackTrace().toString());
	    } catch (IOException e) {
	        Log.e("HttpUtil",e.fillInStackTrace().toString());
	    }

		return result;
    	
    }
}
