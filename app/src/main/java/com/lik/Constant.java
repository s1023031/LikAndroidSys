package com.lik;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * �w�q�Ψӧ@���M�D���ݷ��q�ɿ��~�T������
 * @author charles
 *
 */
public class Constant {

	public static final String TAG = "LikSys";
	public static final String KEYSTORE_PASS = "likwebapp";
	public static final String MANUFACTURER_SAMSUNG = "samsung";
	public static final String MANUFACTURER_ASUS = "asus";
	public static final String MODEL_SAMSUNG_GT_P6810 = "GT-P6810";
	public static final String MODEL_ASUS_NEXUS7 = "Nexus 7";
	public boolean isDebug = true;
	public static final String SUCCESS = "0000";
	public static final String FINISH = "0001"; // general success
	public static final String FLAG_ON = "0002"; // �@�ӤU�������i�U��
	public static final String FLAG_ONS = "0003"; // �h�ӤU�������i�U��
	public static final String FLAG_OFF = "0004"; // �L�����i�U��
	public static final String PDAID = "0005"; // pdaid
	public static final String NO_MEMORY = "0006";
	
	public static final String FAIL = "1000"; // general fail
	public static final String ERROR_COMPANYNOTREGISTER = "1001";
	public static final String ERROR_REGISTERCONSTRAINT = "1002";
	public static final String ERROR_UPLOAD_DATA_ERROR = "E1003";
	public static final String ERROR_STOCKREPLY_DATANOTFOUND = "1111";
	public static final String ERROR_STOCKREPLY_NOTFINISH = "1112";
	public static final String ERROR_AUTHETICATE_FAIL = "1103";
	public static final String ERROR_CONNECT_NETWORK = "1104";
	public static final String ERROR_SOCKET_TIMEOUT = "1105";
	public static final String MSG_NODATA = "2101";

	public static final String MESSAGE_SUCCESS = "success"; // general success message
	public static final String MESSAGE_FAIL = "fail"; // general fail message
	public static final String XMPP_DATA_IOEXCEPTION = "1001";
	public static final String XMPP_ROOT_HEADER = "ROOTHEADER";
	public static final String XMPP_ROOT_FOOTER = "ROOTFOOTER";
	public static final String XMPP_HEADER = "HEADER";
	public static final String XMPP_FOOTER = "FOOTER";
	public static final String XMPP_SIZE = "SIZE";
	public static final String XMPP_SEPERATOR = ":";
	public static final String XMPP_EQUAL = "=";
	public static final String XMPP_UPLOAD_RESPONSE = "UPLOADRESPONSE";
	public static final String XMPP_ECHO_RESPONSE = "ECHORESPONSE";
	public static final String MQTT_CONNECTLOST = "MqttConnectLost";
	public static final String MESSAGE_SEPERATOR = "---~~~seperate_line~~~---";
	public static final String MESSAGE_SEPERATOR_ATOMIC = "---~~~seperate_line_atomic~~~---";
	public static final String MESSAGE_SEPERATOR_PRE = "---~~~";
	public static final String MESSAGE_SEPERATOR_MID = "seperate_line";
	public static final String MESSAGE_SEPERATOR_SUR = "~~~---";
	public static final String MESSAGE_SEPERATOR_SUMMARY = "---~~~seperate_line_summary~~~---";
	
	public static final int ZOOM_INCREMENTAL = 10;
	
//	private static final Calendar cal = Calendar.getInstance();
	private static final SimpleDateFormat format2 = new SimpleDateFormat("yyyy/MM/dd",Locale.CHINESE);
	private static final SimpleDateFormat format3 = new SimpleDateFormat("MM/dd/yyyy",Locale.CHINESE);
	private static final SimpleDateFormat format4 = new SimpleDateFormat("dd/MM/yyyy",Locale.CHINESE);

	public static final SimpleDateFormat sqliteDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",Locale.CHINESE); // sqlite ����x�s�榡
	public static final SimpleDateFormat sqliteDFT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINESE); // sqlite ����x�s�榡
	public static final SimpleDateFormat sqliteDFS = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINESE); // sqlite ����x�s�榡-�u�����
	public static final SimpleDateFormat sdf = format2; // �t�Τ@����ܤ���榡
	public static final SimpleDateFormat sdf2d = new SimpleDateFormat("yyyyMMdd",Locale.CHINESE); // date use for file name
	public static final SimpleDateFormat sdf2t = new SimpleDateFormat("HHmmss",Locale.CHINESE); // time use for file name
	public static final SimpleDateFormat sdf3t = new SimpleDateFormat("HHmm",Locale.CHINESE); // time use for log location
	public static final SimpleDateFormat versionSDF = new SimpleDateFormat("yyyy.MM.dd.HH",Locale.CHINESE); // version�榡

	public static final String number = "([0-9]+\\.[0-9]*)|([0-9]*\\.[0-9]+)|([0-9]+)|(-[0-9]+\\.[0-9]*)|(-[0-9]*\\.[0-9]+)|(-[0-9]+)";

	public static String getTopicSessionName(String siteName) {
		return "session-topic-"+siteName.toLowerCase();
	}

	public static String getQueueSessionName(String siteName) {
		return "session-queue-"+siteName.toLowerCase();
	}

	public static String getUploadTopicName(String siteName) {
		return "topic-"+siteName.toLowerCase()+"-ul";
	}

	public static String getDownloadTopicName(String siteName) {
		return "topic-"+siteName.toLowerCase()+"-dl";
	}

	public static String getUploadTopicSubscriberName(String siteName) {
		return "topic-"+siteName.toLowerCase()+"-ul"+"-Subscriber";
	}

	public static String getUploadQueueName(String siteName) {
		return "queue-"+siteName.toLowerCase()+"-ul";
	}

	public static String getDownloadQueueName(String siteName) {
		return "queue-"+siteName.toLowerCase()+"-dl";
	}
	
	/**
	 * �Ѧ�Company.DateFormat����
	 *	1 = "yyyy/MM/dd"; // ����
	 *	2 = "yyyy/MM/dd"; // ���
	 *	3 = "MM/dd/yyyy"; // ���
	 *	4 = "dd/MM/yyyy"; // ���
	 */
	public static synchronized String getDateFormat(Date date, String df) {
		String result = null;
		if(date == null) return result;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int i = df==null?2:Integer.parseInt(df);
		switch(i) {
			case 1:
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH)+1;
				int day = cal.get(Calendar.DAY_OF_MONTH);
				String sMonth = String.valueOf(month);
				sMonth = sMonth.length()==1?"0"+sMonth:sMonth;
				String sDay = String.valueOf(day);
				sDay = sDay.length()==1?"0"+sDay:sDay;
				StringBuffer sb = new StringBuffer();
				sb.append(year-1911).append("/").append(sMonth).append("/").append(sDay);
				result = sb.toString();
				break;
			case 3:
				result = format3.format(date);
				break;
			case 4:
				result = format4.format(date);
				break;
			case 2:
			default:
				result = format2.format(date);
		}
		return result;
	}
	
	public static synchronized String getYearFormat(Date date, String df) {
		StringBuffer result = new StringBuffer();
		if(date == null) return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int i = df==null?2:Integer.parseInt(df);
		int year = cal.get(Calendar.YEAR);
		switch(i) {
		case 1:
			result.append(year-1911);
			break;
		default:
			result = result.append(year);
		}
		return result.toString();
	}
	
	public static synchronized String getYearMonthFormat(Date date, String df) {
		StringBuffer result = new StringBuffer();
		if(date == null) return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int i = df==null?2:Integer.parseInt(df);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		switch(i) {
		case 1:
			result.append(year-1911);
			break;
		default:
			result = result.append(year);
		}
		result.append("/").append(month);
		return result.toString();
	}
	
	public static synchronized Date getDateFromFormat(String format, String df) {
		Date result = null;
		if(format == null) return result;
		Calendar cal = Calendar.getInstance();
		int i = df==null?2:Integer.parseInt(df);
		switch(i) {
			case 1:
				String[] split = format.split("/");
				if(split.length != 3) break;
				int year = Integer.parseInt(split[0])+1911;
				int month = Integer.parseInt(split[1])-1;
				int day = Integer.parseInt(split[2]);
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.MONTH, month);
				cal.set(Calendar.DAY_OF_MONTH, day);
				result = cal.getTime();
				try {
					result = sdf.parse(sdf.format(result));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				break;
			case 3:
			try {
				result = format3.parse(format);
			} catch (ParseException e) {
				e.printStackTrace();
			}
				break;
			case 4:
			try {
				result = format4.parse(format);
			} catch (ParseException e) {
				e.printStackTrace();
			}
				break;
			case 2:
			default:
			try {
				result = format2.parse(format);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * only truncate to date below.
	 * @param date
	 * @param field
	 * @return
	 */
	public static synchronized Date truncate(Date date, int field) {
		Calendar cal = Calendar.getInstance();
		Date result = date;
		switch(field) {
		case Calendar.YEAR:
			cal.setTime(date);
			cal.set(Calendar.MONTH, Calendar.JANUARY);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			result = cal.getTime();
			break;
		case Calendar.MONTH:
			cal.setTime(date);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			result = cal.getTime();
			break;
		case Calendar.DAY_OF_MONTH:
			cal.setTime(date);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			result = cal.getTime();
			break;
		case Calendar.HOUR_OF_DAY:
			cal.setTime(date);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			result = cal.getTime();
			break;
		case Calendar.MINUTE:
			cal.setTime(date);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			result = cal.getTime();
			break;
		case Calendar.SECOND:
			cal.setTime(date);
			cal.set(Calendar.MILLISECOND, 0);
			result = cal.getTime();
			break;
		}
		return result;
	}
	
	public static boolean isEmpty(String s) {
		if(s==null) return true;
		return s.equals("");
	}
	
}
