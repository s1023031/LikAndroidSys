package com.lik.android.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import javax.net.SocketFactory;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;

import com.lik.Constant;

import android.util.Log;

public class XmppUtil implements PacketListener {
	
	private static final String TAG = XmppUtil.class.getName();

	// the timeout until a connection is established
	private static final int CONNECTION_TIMEOUT = 10000; /* 10 seconds */

	private XmppCallBack callBack;
	private Connection con;
	private Chat chat;
	private boolean isConnect = false;
	
	public Chat getChat() {
		return chat;
	}
	
	public boolean isConnect() {
		return isConnect;
	}

	public void setConnect(boolean isConnect) {
		this.isConnect = isConnect;
	}

	public XmppUtil(XmppCallBack callBack) {
		this.callBack = callBack;
	}

	/**
	 * default XMPP for download
	 * @param XMPPServer
	 * @param XMPPPort
	 * @param siteName
	 * @return
	 * @throws XMPPException
	 */
	public Connection connectViaXMPP(String XMPPServer, int XMPPPort, String siteName) throws XMPPException,IOException {
		return connectViaXMPP(XMPPServer,XMPPPort,siteName,false);
	}
	
	/**
	 * 
	 * @param XMPPServer
	 * @param XMPPPort
	 * @param siteName
	 * @param isUpload = true for upload
	 * @return
	 * @throws XMPPException
	 */
	public Connection connectViaXMPP(String XMPPServer, int XMPPPort, String siteName, boolean isUpload) throws XMPPException,IOException {
		if(isConnect) {
			Log.d(TAG,"XMPP server already connected, bypass this call:"+con.getConnectionID());
			return con;
		}
		testConnection(XMPPServer,XMPPPort);
		ConnectionConfiguration cfg = new ConnectionConfiguration(XMPPServer,XMPPPort);
		con = new XMPPConnection(cfg);
		// Connect to the server
		con.connect();
		isConnect = true;
		Log.d(TAG,"XMPP server connected:"+con.getConnectionID());
		// login name is topic name
		//con.login("topic-"+siteName.toLowerCase()+"-dl", "manager");
		if(isUpload) 
			con.login(Constant.getUploadTopicName(siteName), "manager");
		else 
			con.login(Constant.getDownloadTopicName(siteName), "manager");
        // add listener to get message from server side
		con.addPacketListener(this, new PacketFilter() {
            public boolean accept(Packet packet) {
                return true;
            }
        });
		// added on 2012/5/24 for upload
//			chat = con.getChatManager().createChat(Constant.getUploadTopicName(siteName), new org.jivesoftware.smack.MessageListener() {			 
//			public void processMessage(Chat chat, org.jivesoftware.smack.packet.Message msg) {
//				Log.i("XmppUtil","Body:"+msg.getBody());
//			}
//		});
		// modified on 2012/9/12 for upload/download
		if(isUpload) {
			chat = con.getChatManager().createChat(Constant.getUploadTopicName(siteName), new org.jivesoftware.smack.MessageListener() {			 
				public void processMessage(Chat chat, org.jivesoftware.smack.packet.Message msg) {
					Log.i("XmppUtil","Body:"+msg.getBody());
				}
			});
		} else {
			chat = con.getChatManager().createChat(Constant.getDownloadTopicName(siteName), new org.jivesoftware.smack.MessageListener() {			 
				public void processMessage(Chat chat, org.jivesoftware.smack.packet.Message msg) {
					Log.i("XmppUtil","Body:"+msg.getBody());
				}
			});			
		}
		return con;
	}
	
	public void disConnectViaXMPP() {
		if(isConnect) {
			String connectionID = con.getConnectionID();
			con.disconnect();
			Log.d(TAG,"XMPP server disconnected:"+connectionID);
		}
		isConnect = false;
	}

	/*
     * 主要接收XMPP message的method
     * @see org.jivesoftware.smack.PacketListener#processPacket(org.jivesoftware.smack.packet.Packet)
     */
	public void processPacket(Packet packet) {
    	//Log.d(TAG," <:> " + packet.toXML());
    	if(packet instanceof Message) {
    		String message = (((Message)packet).getBody());
    		Log.d(TAG,message);
    		callBack.callBack(message);
    	}
    }

	public XmppCallBack getCallBack() {
		return callBack;
	}

	public void setCallBack(XmppCallBack callBack) {
		this.callBack = callBack;
	}

	private void testConnection(String ip, int XMPPPort) throws IOException {
		// test before connect 
		Socket socket = SocketFactory.getDefault().createSocket();
		SocketAddress remoteaddr = new InetSocketAddress(ip, XMPPPort);
		socket.connect(remoteaddr, CONNECTION_TIMEOUT);   // 就在connect時設timeout
		socket.close();

	}
	
}
