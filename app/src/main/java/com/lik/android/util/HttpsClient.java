package com.lik.android.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import com.lik.Constant;

public enum HttpsClient {
	;
	public static HttpClient getHttpsClient(InputStream is) {
		return getHttpsClient(is, 443);
	}
	
	public static HttpClient getHttpsClient(InputStream is, int port) {
		HttpClient result = null;
	    try {
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        	ks.load(is, Constant.KEYSTORE_PASS.toCharArray());
        	SSLSocketFactory sslf = new SSLSocketFactory(ks);
        	sslf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sslf, port));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
            result = new DefaultHttpClient(ccm,params);
	    } catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
	    	if(is!=null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    }
		return result;
	}
}
