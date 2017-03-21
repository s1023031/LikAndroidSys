package com.lik.android.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import com.lik.android.main.LikDBAdapter;
import com.lik.android.om.Settings;

import android.app.Activity;
import android.util.Log;
import android.widget.BaseAdapter;

public abstract class BaseDataAdapter<T> extends BaseAdapter {

	protected String TAG = "BaseDataAdapter";
	protected List<T> data = new ArrayList<T>();
	protected Activity context;
	protected LikDBAdapter DBAdapter;
	protected TreeMap<Integer,Integer> columns = new TreeMap<Integer,Integer>();
	protected LinkedList<Integer> sequences = new LinkedList<Integer>();
	protected String dateFormat;
	protected final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

	public BaseDataAdapter(Activity context, List<T> data) {
		this.context = context;
		this.data = data;
		this.TAG = this.getClass().getName();
	}

	public BaseDataAdapter(Activity context, LikDBAdapter DBAdapter) {
		this.context = context;
		this.DBAdapter = DBAdapter;
		this.TAG = this.getClass().getName();
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int index) {
		return data.get(index);
	}

	@Override
	public long getItemId(int position) {
		// TODO to be defined
		return position;
	}
	
	/**
	 * implemented by subclass for data elements
	 * @param args
	 */
	abstract public void gatherData(String... args);
	
	/**
	 * save user defined Width settings
	 */
	public void saveWidthSetting(int columnNO, int widthValue) {
		Settings omS = new Settings();
		omS.setTag(TAG);
		omS.setColumnNO(columnNO);
		omS.findByKey(DBAdapter);
		if(omS.getRid()<0) {
			omS.setWidthValue(widthValue);
			omS.setSequence(columnNO);
			omS.doInsert(DBAdapter);
			if(omS.getRid()>=0) Log.d(TAG, "ColumnNO="+columnNO+",WidthValue="+widthValue+" inserted!");
		} else {
			if(omS.getWidthValue() != widthValue) {
				omS.setWidthValue(widthValue);
				omS.doUpdate(DBAdapter);
				if(omS.getRid()>=0) Log.d(TAG, "ColumnNO="+columnNO+",WidthValue="+widthValue+" updated!");
			}
		}
	}

	/**
	 * save user defined sequence settings
	 */
	public void saveSequenceSetting(int columnNO, int sequence) {
		Settings omS = new Settings();
		omS.setTag(TAG);
		omS.setColumnNO(columnNO);
		omS.findByKey(DBAdapter);
		if(omS.getRid()<0) {
			omS.setSequence(sequence);
			omS.doInsert(DBAdapter);
			if(omS.getRid()>=0) Log.d(TAG, "ColumnNO="+columnNO+",sequence="+sequence+" inserted!");
		} else {
			if(omS.getSequence() != sequence) {
				omS.setSequence(sequence);
				omS.doUpdate(DBAdapter);
				if(omS.getRid()>=0) Log.d(TAG, "ColumnNO="+columnNO+",sequence="+sequence+" updated!");
			}
		}
	}

	public void setSequence(int index1, int index2) {
		setSequence(index1,index2,true);		
	}
	
	public void setSequence(int index1, int index2, boolean flag) {
		int i1 = sequences.get(index1);
		if(index1>index2) {
			sequences.remove(index1);
			sequences.add(index2, i1);
		} else {
			sequences.add(index2, i1);
			sequences.remove(index1);
		}
		int idx = 0;
		if(flag) {
			for(Iterator<Integer> ir=sequences.iterator();ir.hasNext();) {
				int seq = ir.next();
				saveSequenceSetting(idx++,seq);
			}
		}
		Log.d(TAG,"new order="+sequences);
		Log.d(TAG,"columns="+columns);
	}
	
	public LinkedList<Integer> getSequences() {
		return sequences;
	}

	public void setColumnWidth(int column, int width) {
		columns.put(column, width);
		saveWidthSetting(column, width);
		notifyDataSetChanged(); // add by MOU 2014/7/23
	}
	
	public TreeMap<Integer, Integer> getColumns() {
		return columns;
	}

	public int getColumnWidth(int column) {
		return columns.get(column)==null?0:columns.get(column);
	}

	protected void init(int COLUMN_SIZE) {
//		for(int i=0;i<COLUMN_SIZE;i++) {
//			sequences.add(i);
//		}
		Settings omS = new Settings();
		omS.setTag(TAG);
		List<Settings> ltS = omS.getSettingsByTag(DBAdapter);
		Log.d(TAG,"columns size from DB="+ltS.size());
		if(ltS.size() < COLUMN_SIZE) {
			for(int i=0;i<COLUMN_SIZE;i++) {
				sequences.add(i);	
			}
		} else {
			sequences.clear();
			for(Iterator<Settings> ir = ltS.iterator();ir.hasNext();) {
				Settings om = ir.next();
				if(om.getWidthValue() != 0) columns.put(om.getColumnNO(), om.getWidthValue());
//				if(om.getSequence() != 0) setSequence(om.getSequence(),om.getColumnNO(),false);
				sequences.add(om.getSequence());
			}
			Log.d(TAG,sequences.toString());
		}
		for(Iterator<Settings> ir = ltS.iterator();ir.hasNext();) {
			Settings om = ir.next();
			if(om.getWidthValue() != 0) columns.put(om.getColumnNO(), om.getWidthValue());
		}
		
      //  dateFormat = ((MainMenuActivity)context).currentCompany.getDateFormat();
        Log.d(TAG,"dateFormat="+dateFormat);
	}
	
}
