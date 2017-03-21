package com.lik.android.main;
/*package com.lik.android;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

import com.lik.android.view.SelectPhraseDataAdapter;
import com.lik.android.view.SelectPhraseView;
import com.lik.android.om.OrderCheck;
import com.lik.android.om.OrderReceive;
import com.lik.android.om.Orders;
import com.lik.android.om.Phrase;
import com.lik.android.view.CustBankView;
import com.lik.android.view.OrderCheckDataAdapter;
import com.lik.android.view.OrderReceiveDataAdapter;
import com.lik.android.view.QueryNotUploadDataAdapter;
import com.lik.android.view.QueryNotUploadView;
import com.lik.android.view.QueryOrderCheckView;
import com.lik.android.view.QueryOrderReceiveView;
import com.lik.Constant;

public class ReceiveFragment extends MainMenuFragment implements 
OnFocusChangeListener {

	protected static final String TAG = ReceiveFragment.class.getName();
	public static final String LAST_SELECTED_CHECKLVPOSITION_KEY = "ReceiveFragment.LastSelectedCheckLVPositionKey";
	public static final String LAST_SELECTED_CANCELLVPOSITION_KEY = "ReceiveFragment.LastSelectedCancelLVPositionKey";
	public static final int CHECK_NOTE = 1;
	public static final int CANCEL_NOTE = 2;
	
	protected View mRoot;
	EditText et1;
	Button btnSNK;
	public LinearLayout checkNote;
	public EditText checketNote;
	public LinearLayout cancelNote;
	public EditText canceletNote;
	TextView tv2a,tv3a,tv4a; // ���ڦX�p�A�R�P�X�p�A����
	
	public InputMethodManager imm;

	public QueryNotUploadView viewQNU;
	public TreeMap<String,String> kind13 = new TreeMap<String,String>();
	protected Orders omO = new Orders(); // �O���ثeorders���
	public QueryOrderCheckView viewQOCK; // �O���ثe�ҿ怜�����Ӹ��
	public QueryOrderReceiveView viewQOCC; // �O���ثe�ҿ�R�P���Ӹ��
	boolean isCheckCorrect = true;
	String checkErrorMsg = null;
	boolean isCancelCorrect = true;
	String cancelErrorMsg = null;
	public int currentInputFocus;
//	public EditText etAmt; // �������B
	public ListView lvCheck,lvCancel;
	public int lastSelectedCheckLVposition = -1;
	public int lastSelectedCancelLVposition = -1;
	public OrderCheckDataAdapter checkAdapter;
	public OrderReceiveDataAdapter cancelAdapter;
	ThisOnClickListener thisOnClickListener;
	CheckOnClickListener checkOnClickListener;
	CheckOnLongClickListener checkOnLongClickListener;
	CancelOnClickListener cancelOnClickListener;
	CancelOnLongClickListener cancelOnLongClickListener;
	ReceiveAmtTextWatcher receiveAmtTextWatcher;
	
	public NumberFormat nfAcDecimal = NumberFormat.getInstance(); // �P�f�������B�p�Ʀ��

	public static MainMenuFragment newInstance(int index) {
        Log.v(TAG, "in ReceiveFragment newInstance(" + index + ")");

        ReceiveFragment mf = new ReceiveFragment();

        // Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("index", index);
		mf.setArguments(args);
		return mf;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG,"onActivityCreated start!");
		imm = (InputMethodManager)myActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
		receiveView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(TAG,"onCreateView start!");
		super.onCreateView(inflater,container,savedInstanceState);
    	mRoot = inflater.inflate(R.layout.main_receive, container, false);
		return mRoot;
	}
	
    private void receiveView() {
		// set parent to selected item
        SharedPreferences settings = myActivity.getPreferences(Context.MODE_PRIVATE);
		Bundle bundle = getArguments();
        viewQNU = (QueryNotUploadView)bundle.getSerializable(QueryNotUploadFragment.CUSTOMER_BUNDLE_KEY);
		if(viewQNU == null) {
	        // Restore preferences
	        int lastSelectedPosition = settings.getInt(QueryNotUploadFragment.LAST_SELECTED_POSITION_KEY, 0);
	        final QueryNotUploadDataAdapter adapterQNU = new QueryNotUploadDataAdapter(myActivity,DBAdapter);
	        adapterQNU.gatherData(myActivity.omCurrentAccount.getAccountNo(),String.valueOf(myActivity.currentCompany.getCompanyID()));
	        if(adapterQNU.getCount()==0) {
	        	TextView tv2 = (TextView)myActivity.findViewById(R.id.global_textView2);
	        	tv2.setText(getResources().getString(R.string.takeorderMessage3));
	        	return; 
	        }
	        if(lastSelectedPosition!=-1 && lastSelectedPosition<adapterQNU.getCount()) {
	        	// �ѩwdata selected
	        	viewQNU = ((QueryNotUploadView)adapterQNU.getItem(lastSelectedPosition));
	        } else {
	        	SharedPreferences.Editor editor = settings.edit();
	        	editor.putInt(QueryNotUploadFragment.LAST_SELECTED_POSITION_KEY, 0);
	        	editor.commit();
	        	viewQNU = ((QueryNotUploadView)adapterQNU.getItem(0));
	        }
	        	        
		}
		thisOnClickListener = new ThisOnClickListener();
		checkOnClickListener = new CheckOnClickListener();
		checkOnLongClickListener = new CheckOnLongClickListener();
		cancelOnClickListener = new CancelOnClickListener();
		cancelOnLongClickListener = new CancelOnLongClickListener();
		receiveAmtTextWatcher = new ReceiveAmtTextWatcher();
		nfAcDecimal.setMinimumFractionDigits(myActivity.currentCompany.getAcDecimal());
		nfAcDecimal.setMaximumFractionDigits(myActivity.currentCompany.getAcDecimal());
		nfAcDecimal.setRoundingMode(RoundingMode.HALF_UP);

		final TreeMap<String,String> tmKind1 = getKindMap(Phrase.PHKINDNO_1);
		kind13 = getKindMap(Phrase.PHKINDNO_13);
		// �]�w�Ȥ�title
        TextView tv4 = (TextView)mRoot.findViewById(R.id.main_receive_textView0);
        tv4.setText(viewQNU.getCustomerNO()+" "+viewQNU.getShortName()+" ("+tmKind1.get(viewQNU.getPayType())+" "+getText(R.string.takeorderMessage15)+" "+viewQNU.getSettleDay()+")");

        // ���o�q���T
		omO.setSerialID(viewQNU.getSerialID());
		omO.getOrdersBySerialID(DBAdapter);
		Log.d(TAG,"omO.getSerialID()="+omO.getSerialID());
		et1 = (EditText)mRoot.findViewById(R.id.main_receive_textView1a);
		et1.setText(omO.getReceiveAmt()==null?null:nfAcDecimal.format(omO.getReceiveAmt()));
		et1.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean isFocus) {
				if(isFocus) {
					Log.d(TAG, "input focus="+v.getId());
					currentInputFocus = v.getId();
					double amt = 0;
					if(!((EditText)v).getText().toString().equals("")) {
						// ��,�~�n���sformat
						if(((EditText)v).getText().toString().indexOf(",")!=-1) {
							try {
								amt = nfAcDecimal.parse(((EditText)v).getText().toString()).doubleValue();
							} catch (ParseException e) {
								e.printStackTrace();
							}
							if(myActivity.currentCompany.getAcDecimal()==0)
								((EditText)v).setText(String.valueOf((int)amt));
							else
								((EditText)v).setText(String.valueOf(amt));
						}
					}
					if(cancelAdapter.canInterrupt) {
						if(!cancelAdapter.clearEmptyRecord(v)) cancelAdapter.resetView();
					}
					if(checkAdapter.canInterrupt) {
						if(!checkAdapter.clearEmptyRecord(v)) checkAdapter.resetView();
					}
				} else {
					Log.d(TAG, "input leave focus="+v.getId());
					currentInputFocus = -1;
					String s = ((EditText)v).getText().toString();
					if(!Constant.isEmpty(s)&&!s.matches(Constant.number)) {
						Log.d(TAG, "reset to empty");
						((EditText)v).setText("");
					} else {
						if(!Constant.isEmpty(s)) {
							et1.removeTextChangedListener(receiveAmtTextWatcher);
							((EditText)v).setText(nfAcDecimal.format(omO.getReceiveAmt()==null?0:omO.getReceiveAmt()));
							et1.addTextChangedListener(receiveAmtTextWatcher);
						}
					}
				}
				
			}
			
		});
		et1.addTextChangedListener(receiveAmtTextWatcher);
		et1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG,"et1 onClick called");
				if(cancelAdapter.canInterrupt) {
					if(!cancelAdapter.clearEmptyRecord(v)) cancelAdapter.resetView();
				}
				if(checkAdapter.canInterrupt) {
					if(!checkAdapter.clearEmptyRecord(v)) checkAdapter.resetView();
				}
			}
			
		});
    	currentInputFocus=et1.getId();
    	tv4a = (TextView)mRoot.findViewById(R.id.main_receive_textView4a);
    	final Button btnSN1 = (Button)mRoot.findViewById(R.id.main_receive_buttonSN1);
    	btnSN1.setOnClickListener(thisOnClickListener);
    	final Button btnSN2 = (Button)mRoot.findViewById(R.id.main_receive_buttonSN2);
    	btnSN2.setOnClickListener(thisOnClickListener);
    	final Button btnSN3 = (Button)mRoot.findViewById(R.id.main_receive_buttonSN3);
    	btnSN3.setOnClickListener(thisOnClickListener);
    	final Button btnSN4 = (Button)mRoot.findViewById(R.id.main_receive_buttonSN4);
    	btnSN4.setOnClickListener(thisOnClickListener);
    	final Button btnSN5 = (Button)mRoot.findViewById(R.id.main_receive_buttonSN5);
    	btnSN5.setOnClickListener(thisOnClickListener);
    	final Button btnSN6 = (Button)mRoot.findViewById(R.id.main_receive_buttonSN6);
    	btnSN6.setOnClickListener(thisOnClickListener);
    	final Button btnSN7 = (Button)mRoot.findViewById(R.id.main_receive_buttonSN7);
    	btnSN7.setOnClickListener(thisOnClickListener);
    	final Button btnSN8 = (Button)mRoot.findViewById(R.id.main_receive_buttonSN8);
    	btnSN8.setOnClickListener(thisOnClickListener);
    	final Button btnSN9 = (Button)mRoot.findViewById(R.id.main_receive_buttonSN9);
    	btnSN9.setOnClickListener(thisOnClickListener);
    	final Button btnSN0 = (Button)mRoot.findViewById(R.id.main_receive_buttonSN0);
    	btnSN0.setOnClickListener(thisOnClickListener);
    	final Button btnSNDOT = (Button)mRoot.findViewById(R.id.main_receive_buttonSNDOT);
    	btnSNDOT.setOnClickListener(thisOnClickListener);
    	btnSNK = (Button)mRoot.findViewById(R.id.main_receive_buttonSNK);
    	btnSNK.setOnClickListener(thisOnClickListener);
    	final Button btnSNB = (Button)mRoot.findViewById(R.id.main_receive_buttonSNB);
    	btnSNB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				View view = mRoot.findViewById(currentInputFocus);
				if(view instanceof EditText) {
					EditText et = (EditText)view;
					int lenth = et.getText().length();
					int pos = et.getSelectionStart();
					if(lenth == 0 || pos ==0) return;
					String pre = et.getText().toString().substring(0, pos-1);
					String pox = et.getText().toString().substring(pos,lenth);
					et.setText(pre+pox);
					et.setSelection(pre.length());
					et.requestFocus();
				}				
			}
    		
    	});
    	final Button btnSNC = (Button)mRoot.findViewById(R.id.main_receive_buttonSNC);
    	btnSNC.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				View view = mRoot.findViewById(currentInputFocus);
				if(view instanceof EditText) {
					EditText et = (EditText)view;
					et.setText("");
					et.requestFocus();
				}				
			}
    		
    	});
    	checkAdapter = new OrderCheckDataAdapter(myActivity,DBAdapter);
    	OrderCheck omOC = new OrderCheck();
		if(!omOC.testTableExists(DBAdapter)) {
    		String cmd = omOC.getDropCMD();
    		if(cmd != null) DBAdapter.getdb().execSQL(cmd);	
    		cmd = omOC.getCreateCMD();
    		if(cmd != null) DBAdapter.getdb().execSQL(cmd);	
			String[] cmds = omOC.getCreateIndexCMD();
			for(int j=0;j<cmds.length;j++) {
				cmd = cmds[j];
				DBAdapter.getdb().execSQL(cmd);	
			}
		}
		checkAdapter.gatherData(
    			String.valueOf(omO.getSerialID())
    			);
		// set listener for check header
		TextView qTV1 = (TextView)mRoot.findViewById(R.id.sub_check_header_textView1);
		qTV1.setOnLongClickListener(checkOnLongClickListener);
		qTV1.setOnClickListener(checkOnClickListener);
		qTV1.setText(Html.fromHtml(getResources().getText(R.string.subcheckheaderTextView1a).toString()));
		TextView qTV2 = (TextView)mRoot.findViewById(R.id.sub_check_header_textView2);
		qTV2.setOnLongClickListener(checkOnLongClickListener);
		qTV2.setOnClickListener(checkOnClickListener);
		qTV2.setText(Html.fromHtml(getResources().getText(R.string.subcheckheaderTextView2a).toString()));
		TextView qTV3 = (TextView)mRoot.findViewById(R.id.sub_check_header_textView3);
		qTV3.setOnLongClickListener(checkOnLongClickListener);
		qTV3.setOnClickListener(checkOnClickListener);
		qTV3.setText(Html.fromHtml(getResources().getText(R.string.subcheckheaderTextView3a).toString()));
		TextView qTV4 = (TextView)mRoot.findViewById(R.id.sub_check_header_textView4);
		qTV4.setOnLongClickListener(checkOnLongClickListener);
		qTV4.setOnClickListener(checkOnClickListener);
		qTV4.setText(Html.fromHtml(getResources().getText(R.string.subcheckheaderTextView4a).toString()));
		TextView qTV5 = (TextView)mRoot.findViewById(R.id.sub_check_header_textView5);
		qTV5.setOnLongClickListener(checkOnLongClickListener);
		qTV5.setOnClickListener(checkOnClickListener);
		qTV5.setText(Html.fromHtml(getResources().getText(R.string.subcheckheaderTextView5a).toString()));
		TextView qTV6 = (TextView)mRoot.findViewById(R.id.sub_check_header_textView6);
		qTV6.setOnLongClickListener(checkOnLongClickListener);
		qTV6.setOnClickListener(checkOnClickListener);
		qTV6.setText(Html.fromHtml(getResources().getText(R.string.subcheckheaderTextView6a).toString()));
		TextView qTV7 = (TextView)mRoot.findViewById(R.id.sub_check_header_textView7);
		qTV7.setOnLongClickListener(checkOnLongClickListener);
		qTV7.setOnClickListener(checkOnClickListener);
		// set user defined header sequence
		ViewGroup vg = (ViewGroup)qTV1.getParent();
		vg.removeAllViews();
		LinkedList<Integer> sequences = checkAdapter.getSequences();
		for(Iterator<Integer> ir=sequences.iterator();ir.hasNext();) {
			int seq = ir.next();
			switch(seq) {
				case 0:
					vg.addView(qTV1);
					break;
				case 1:
					vg.addView(qTV2);
					break;
				case 2:
					vg.addView(qTV3);
					break;
				case 3:
					vg.addView(qTV4);
					break;
				case 4:
					vg.addView(qTV5);
					break;
				case 5:
					vg.addView(qTV6);
					break;
				case 6:
					vg.addView(qTV7);
					break;
			}
		}
		// set user defined header width
		TreeMap<Integer,Integer> columns = checkAdapter.getColumns();
		for(Iterator<Integer> ir = columns.keySet().iterator();ir.hasNext();) {
			int columnNO = ir.next();
			int width = columns.get(columnNO);
			if(width == 0) continue;
			switch(columnNO) {
				case 0:
					LinearLayout.LayoutParams params0 = (LinearLayout.LayoutParams)qTV1.getLayoutParams();
					params0.width = width;
					break;
				case 1:
					LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams)qTV2.getLayoutParams();
					params1.width = width;
					break;
				case 2:
					LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams)qTV3.getLayoutParams();
					params2.width = width;
					break;
				case 3:
					LinearLayout.LayoutParams params3 = (LinearLayout.LayoutParams)qTV4.getLayoutParams();
					params3.width = width;
					break;
				case 4:
					LinearLayout.LayoutParams params4 = (LinearLayout.LayoutParams)qTV5.getLayoutParams();
					params4.width = width;
					break;
				case 5:
					LinearLayout.LayoutParams params5 = (LinearLayout.LayoutParams)qTV6.getLayoutParams();
					params5.width = width;
					break;
				case 6:
					LinearLayout.LayoutParams params6 = (LinearLayout.LayoutParams)qTV7.getLayoutParams();
					params6.width = width;
					break;
			}
			
		}

		lvCheck = (ListView)mRoot.findViewById(R.id.sub_sell_listView1);
		lvCheck.setAdapter(checkAdapter);
		lvCheck.setOnScrollListener(new OnScrollListener(){
    	    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    	    	Log.d(TAG, "onScroll...");
    	      }
    	      public void onScrollStateChanged(AbsListView view, int scrollState) {
    	        Log.d(TAG, "scrollState..."+scrollState);
    	        if(scrollState==OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) checkAdapter.resetView();
    	      }
    	});
		lvCheck.setItemsCanFocus(true);
		lvCheck.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Log.d(TAG,"onItemLongClick index="+position);
				viewQOCK = (QueryOrderCheckView)checkAdapter.getItem(position);
				AlertDialog dialog = getAlertDialogForDeleteMove(getResources().getString(R.string.subdetailDialogMessage1),null, position,CHECK_NOTE);
				dialog.show();
				return true;
			}
    		
    	});
		lvCheck.setOnItemClickListener(new AdapterView.OnItemClickListener(){

    		@Override
    		public void onItemClick(AdapterView<?> parent, View arg1, int position,long id) {

				Log.d(Constant.TAG,"onItemClick index="+position);
				if(lastSelectedCheckLVposition != -1 && lastSelectedCheckLVposition<checkAdapter.getCount()) {
					((QueryOrderCheckView)checkAdapter.getItem(lastSelectedCheckLVposition)).setActivated(false);
				}
				lastSelectedCheckLVposition = position;
				QueryOrderCheckView omview = (QueryOrderCheckView)checkAdapter.getItem(position);
				omview.setActivated(true);
				viewQOCK = omview;
				if(parent!=null) parent.requestFocusFromTouch(); // �Ϧ��ڪ��Blost focus if has
				checkAdapter.notifyDataSetChanged();
    		}
    	});
    	// ���ڷs�W
    	final Button btnCheck = (Button)mRoot.findViewById(R.id.main_receive_buttonAdd1);
    	btnCheck.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				cancelAdapter.resetView();
				if(lastSelectedCheckLVposition!=-1 && lastSelectedCheckLVposition<checkAdapter.getCount()) {
					((QueryOrderCheckView)checkAdapter.getItem(lastSelectedCheckLVposition)).setActivated(false);
				}
				if(checkAdapter.getCount()>0) {
					lastSelectedCheckLVposition = 0;
					QueryOrderCheckView omview = (QueryOrderCheckView)checkAdapter.getItem(0);
					if(Constant.isEmpty(omview.getCheckNo())) {
				    	final ViewGroup vg = omview.getViewGroup();
				    	Log.d(TAG,"vg="+vg);
				    	checkAdapter.notifyDataSetChanged();	
				    	if(vg!=null) {
				    		lvCheck.post(new Runnable() {

								@Override
								public void run() {
						    		Log.d(TAG,"performClick...");
						    		vg.getChildAt(0).performClick();
						    		imm.showSoftInput(vg.getChildAt(0),0);
								}
				    			
				    		});
				    	}				
						return;
					}
				}
				OrderCheck omOC = new OrderCheck();
				omOC.setOrdersSerialID(omO.getSerialID());
				omOC.setCompanyID(omO.getCompanyID());
				omOC.setCustomerID(omO.getCustomerID());
				omOC.setCheckNo("");
				if(checkAdapter.custBankAdapter!=null) {
					CustBankView bankView = checkAdapter.custBankAdapter.getDefaultBank();
					if(bankView!=null) {
						omOC.setBank(bankView.getBankName());
						omOC.setAccountNo(bankView.getBankNo());
					}
				}
				omOC.doInsert(DBAdapter);
				if(omOC.getRid()>=0) {
					checkAdapter.gatherData(
			    			String.valueOf(omO.getSerialID())
			    			);
					checkAdapter.notifyDataSetChanged();	
			    	// �s�W���������ڸ��Xget focus
			    	lvCheck.post(new Runnable() {

						@Override
						public void run() {
					    	ViewGroup vg = ((QueryOrderCheckView)checkAdapter.getItem(0)).getViewGroup();
					    	Log.d(TAG,"vg="+vg);
					    	if(vg!=null) {
					    		for(int i=0;i<vg.getChildCount();i++) {
					    			if(vg.getChildAt(i) instanceof EditText) {
					    				((EditText)vg.getChildAt(i)).setHintTextColor(getResources().getColor(R.color.blue));
					    			}
					    		}
					    		vg.getChildAt(0).performClick();
					    		imm.showSoftInput(vg.getChildAt(0),0);
					    	}
							
						}
			    		
			    	});
				}
		    	validate();
			}
    		
    	});
    	checkNote = (LinearLayout)mRoot.findViewById(R.id.main_receive_linearLayoutck);
    	checketNote = (EditText)mRoot.findViewById(R.id.main_receive_editTextck);
    	checketNote.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean isFocus) {
				if(isFocus) {
					Log.d(TAG, "input focus="+v.getId());
					currentInputFocus = v.getId();
				} else {
					Log.d(TAG, "input leave focus="+v.getId());
					currentInputFocus = -1;
				}
				
			}
			
		});
    	checketNote.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				Log.d(TAG,"checketNote afterTextChanged called...");
				String note = s.toString();
				if(viewQOCK!=null) {
					OrderCheck omOC = new OrderCheck();
					omOC.setSerialID(viewQOCK.getSerialID());
					omOC.queryBySerialID(DBAdapter);
					if(omOC.getRid()>=0) {
						String old = omOC.getNote()==null?"":omOC.getNote();
						Log.d(TAG,"old note="+omOC.getNote());
						omOC.setNote(note);
						if(!old.equals(note)) {
							omOC.doUpdate(DBAdapter);
							if(omOC.getRid()>=0) {
								viewQOCK.setNote(note);
								checkAdapter.notifyDataSetChanged();
								Log.i(TAG,"OrderCheck note updated to "+omOC.getNote());    									
							} else Log.w(TAG,"OrderCheck note update failed!");
						} else Log.i(TAG,"note not changed, update skipped!");
					} else Log.w(TAG,"OrderCheck not found!");							
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// do nothing
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// do nothing
				
			}
    		
    	});
    	ImageView ivck = (ImageView)mRoot.findViewById(R.id.main_receive_imageViewck);
    	ivck.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG,"add phrase onClick");			
    			// show dialog for input note
				AlertDialog dialog = getAlertDialogForSelectPhrase(getResources().getString(R.string.takeorderMessage8),null,CHECK_NOTE);
				dialog.show();
			}
			
    	});
    	
    	cancelAdapter = new OrderReceiveDataAdapter(myActivity,DBAdapter);
    	OrderReceive omOR = new OrderReceive();
		if(!omOR.testTableExists(DBAdapter)) {
    		String cmd = omOR.getDropCMD();
    		if(cmd != null) DBAdapter.getdb().execSQL(cmd);	
    		cmd = omOR.getCreateCMD();
    		if(cmd != null) DBAdapter.getdb().execSQL(cmd);	
			String[] cmds = omOR.getCreateIndexCMD();
			for(int j=0;j<cmds.length;j++) {
				cmd = cmds[j];
				DBAdapter.getdb().execSQL(cmd);	
			}
		}
		cancelAdapter.gatherData(
    			String.valueOf(omO.getSerialID())
    			);
		// set listener for check header
		qTV1 = (TextView)mRoot.findViewById(R.id.sub_cancel_header_textView1);
		qTV1.setOnLongClickListener(cancelOnLongClickListener);
		qTV1.setOnClickListener(cancelOnClickListener);
		qTV1.setText(Html.fromHtml(getResources().getText(R.string.subcancelheaderTextView1a).toString()));
		qTV2 = (TextView)mRoot.findViewById(R.id.sub_cancel_header_textView2);
		qTV2.setOnLongClickListener(cancelOnLongClickListener);
		qTV2.setOnClickListener(cancelOnClickListener);
		qTV2.setText(Html.fromHtml(getResources().getText(R.string.subcancelheaderTextView2a).toString()));
		qTV3 = (TextView)mRoot.findViewById(R.id.sub_cancel_header_textView3);
		qTV3.setOnLongClickListener(cancelOnLongClickListener);
		qTV3.setOnClickListener(cancelOnClickListener);
		qTV3.setText(Html.fromHtml(getResources().getText(R.string.subcancelheaderTextView3a).toString()));
		qTV4 = (TextView)mRoot.findViewById(R.id.sub_cancel_header_textView4);
		qTV4.setOnLongClickListener(cancelOnLongClickListener);
		qTV4.setOnClickListener(cancelOnClickListener);
		qTV4.setText(Html.fromHtml(getResources().getText(R.string.subcancelheaderTextView4a).toString()));
		qTV5 = (TextView)mRoot.findViewById(R.id.sub_cancel_header_textView5);
		qTV5.setOnLongClickListener(cancelOnLongClickListener);
		qTV5.setOnClickListener(cancelOnClickListener);
		// set user defined header sequence
		vg = (ViewGroup)qTV1.getParent();
		vg.removeAllViews();
		sequences = cancelAdapter.getSequences();
		for(Iterator<Integer> ir=sequences.iterator();ir.hasNext();) {
			int seq = ir.next();
			switch(seq) {
				case 0:
					vg.addView(qTV1);
					break;
				case 1:
					vg.addView(qTV2);
					break;
				case 2:
					vg.addView(qTV3);
					break;
				case 3:
					vg.addView(qTV4);
					break;
				case 4:
					vg.addView(qTV5);
					break;
			}
		}
		// set user defined header width
		columns = cancelAdapter.getColumns();
		for(Iterator<Integer> ir = columns.keySet().iterator();ir.hasNext();) {
			int columnNO = ir.next();
			int width = columns.get(columnNO);
			if(width == 0) continue;
			switch(columnNO) {
				case 0:
					LinearLayout.LayoutParams params0 = (LinearLayout.LayoutParams)qTV1.getLayoutParams();
					params0.width = width;
					break;
				case 1:
					LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams)qTV2.getLayoutParams();
					params1.width = width;
					break;
				case 2:
					LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams)qTV3.getLayoutParams();
					params2.width = width;
					break;
				case 3:
					LinearLayout.LayoutParams params3 = (LinearLayout.LayoutParams)qTV4.getLayoutParams();
					params3.width = width;
					break;
				case 4:
					LinearLayout.LayoutParams params4 = (LinearLayout.LayoutParams)qTV5.getLayoutParams();
					params4.width = width;
					break;
			}
			
		}
		lvCancel = (ListView)mRoot.findViewById(R.id.sub_sell_listView2);
		lvCancel.setAdapter(cancelAdapter);
		lvCancel.setOnScrollListener(new OnScrollListener(){
    	    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    	    	Log.d(TAG, "onScroll...");
    	      }
    	      public void onScrollStateChanged(AbsListView view, int scrollState) {
    	        Log.d(TAG, "scrollState..."+scrollState);
    	        if(scrollState==OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) cancelAdapter.resetView();
    	      }
    	});
		lvCancel.setItemsCanFocus(true);
		lvCancel.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Log.d(TAG,"onItemLongClick index="+position);
				viewQOCC = (QueryOrderReceiveView)cancelAdapter.getItem(position);
				AlertDialog dialog = getAlertDialogForDeleteMove(getResources().getString(R.string.subdetailDialogMessage1),null, position,CANCEL_NOTE);
				dialog.show();
				return true;
			}
    		
    	});
		lvCancel.setOnItemClickListener(new AdapterView.OnItemClickListener(){

    		@Override
    		public void onItemClick(AdapterView<?> parent, View arg1, int position,long id) {

				Log.d(Constant.TAG,"onItemClick index="+position);
				if(lastSelectedCancelLVposition != -1 && lastSelectedCancelLVposition<cancelAdapter.getCount()) {
					((QueryOrderReceiveView)cancelAdapter.getItem(lastSelectedCancelLVposition)).setActivated(false);
				}
				lastSelectedCancelLVposition = position;
				QueryOrderReceiveView omview = (QueryOrderReceiveView)cancelAdapter.getItem(position);
				omview.setActivated(true);
				viewQOCC = omview;
				if(parent!=null) parent.requestFocusFromTouch(); // �Ϧ��ڪ��Blost focus if has
				cancelAdapter.notifyDataSetChanged();
    		}
    	});
    	// �R�P�s�W
    	final Button btnCancel = (Button)mRoot.findViewById(R.id.main_receive_buttonAdd2);
    	btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				checkAdapter.resetView();
				if(lastSelectedCancelLVposition!=-1 && lastSelectedCancelLVposition<cancelAdapter.getCount()) {
					((QueryOrderReceiveView)cancelAdapter.getItem(lastSelectedCancelLVposition)).setActivated(false);
				}
				if(cancelAdapter.getCount()>0) {
					lastSelectedCancelLVposition = 0;
					QueryOrderReceiveView omview = (QueryOrderReceiveView)cancelAdapter.getItem(0);
					if(Constant.isEmpty(omview.getCustNo())) {
				    	final ViewGroup vg = omview.getViewGroup();
				    	Log.d(TAG,"vg="+vg);
				    	cancelAdapter.notifyDataSetChanged();	
				    	if(vg!=null) {
				    		lvCancel.post(new Runnable() {

								@Override
								public void run() {
						    		Log.d(TAG,"performClick...");
						    		vg.getChildAt(0).performClick();
						    		imm.showSoftInput(vg.getChildAt(0),0);
								}
				    			
				    		});
				    	}				
						return;
					}
				}
				OrderReceive omOC = new OrderReceive();
				omOC.setOrdersSerialID(omO.getSerialID());
				omOC.setCompanyID(omO.getCompanyID());
				omOC.setCustomerID(omO.getCustomerID());
				omOC.setCustNo(cancelAdapter.getCount()==0?viewQNU.getCustomerNO():"");
				omOC.doInsert(DBAdapter);
				if(omOC.getRid()>=0) {
					cancelAdapter.gatherData(
			    			String.valueOf(omO.getSerialID())
			    			);
					cancelAdapter.notifyDataSetChanged();	
			    	// �s�W���������ڸ��Xget focus
			    	lvCancel.post(new Runnable() {

						@Override
						public void run() {
					    	ViewGroup vg = ((QueryOrderReceiveView)cancelAdapter.getItem(0)).getViewGroup();
					    	Log.d(TAG,"vg="+vg);
					    	if(vg!=null) {
					    		for(int i=0;i<vg.getChildCount();i++) {
					    			if(vg.getChildAt(i) instanceof EditText) {
					    				((EditText)vg.getChildAt(i)).setHintTextColor(getResources().getColor(R.color.blue));
					    			}
					    		}
					    		vg.getChildAt(0).performClick();
					    		imm.showSoftInput(vg.getChildAt(0),0);
					    	}
							
						}
			    		
			    	});
				}
		    	validate();
			}
    		
    	});		
    	cancelNote = (LinearLayout)mRoot.findViewById(R.id.main_receive_linearLayoutcc);
    	canceletNote = (EditText)mRoot.findViewById(R.id.main_receive_editTextcc);
    	canceletNote.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean isFocus) {
				if(isFocus) {
					Log.d(TAG, "input focus="+v.getId());
					currentInputFocus = v.getId();
				} else {
					Log.d(TAG, "input leave focus="+v.getId());
					currentInputFocus = -1;
				}
				
			}
			
		});
    	canceletNote.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				Log.d(TAG,"canceletNote afterTextChanged called...");
				String note = s.toString();
				if(viewQOCC!=null) {
					OrderReceive omOC = new OrderReceive();
					omOC.setSerialID(viewQOCC.getSerialID());
					omOC.queryBySerialID(DBAdapter);
					if(omOC.getRid()>=0) {
						String old = omOC.getNote()==null?"":omOC.getNote();
						Log.d(TAG,"old note="+omOC.getNote());
						omOC.setNote(note);
						if(!old.equals(note)) {
							omOC.doUpdate(DBAdapter);
							if(omOC.getRid()>=0) {
								viewQOCC.setNote(note);
								cancelAdapter.notifyDataSetChanged();
								Log.i(TAG,"OrderReceive note updated to "+omOC.getNote());    									
							} else Log.w(TAG,"OrderReceive note update failed!");
						} else Log.i(TAG,"note not changed, update skipped!");
					} else Log.w(TAG,"OrderReceive not found!");							
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// do nothing
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// do nothing
				
			}
    		
    	});
    	ImageView ivcc = (ImageView)mRoot.findViewById(R.id.main_receive_imageViewcc);
    	ivcc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG,"add phrase onClick");			
    			// show dialog for input note
				AlertDialog dialog = getAlertDialogForSelectPhrase(getResources().getString(R.string.takeorderMessage8),null,CANCEL_NOTE);
				dialog.show();
			}
			
    	});
    	
    	// ���ڦX�p
    	tv2a = (TextView)mRoot.findViewById(R.id.main_receive_textView2a);
    	// �R�P�X�p
    	tv3a = (TextView)mRoot.findViewById(R.id.main_receive_textView3a);
    	updateTopInfo();
    	validate();

    }
    
    @Override
    public void onStop() {
    	Log.d(TAG,"onStop start!");
    	super.onStop();
        SharedPreferences settings = myActivity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        if(lastSelectedCheckLVposition!=-1) editor.putInt(LAST_SELECTED_CHECKLVPOSITION_KEY,lastSelectedCheckLVposition);
        if(lastSelectedCancelLVposition!=-1) editor.putInt(LAST_SELECTED_CANCELLVPOSITION_KEY,lastSelectedCancelLVposition);
        // Commit the edits!
        editor.commit();
        // �M��listview actived item
		if(checkAdapter != null && lastSelectedCheckLVposition != -1 && lastSelectedCheckLVposition<checkAdapter.getCount()) {
			((QueryOrderCheckView)checkAdapter.getItem(lastSelectedCheckLVposition)).setActivated(false);
			// resetView
			((OrderCheckDataAdapter)checkAdapter).resetView();
		}
		if(cancelAdapter != null && lastSelectedCancelLVposition != -1 && lastSelectedCancelLVposition<cancelAdapter.getCount()) {
			((QueryOrderReceiveView)cancelAdapter.getItem(lastSelectedCancelLVposition)).setActivated(false);
			// resetView
			((OrderReceiveDataAdapter)cancelAdapter).resetView();
		}
		// �YcheckNo���ŭn�M��
		OrderCheck omOC = new OrderCheck();
		omOC.deleteByCheckNoIsNull(DBAdapter);
		// �YcustNo���ŭn�M��
		OrderReceive omOR = new OrderReceive();
		omOR.deleteByCustNoIsNull(DBAdapter);
    }
    	
	@Override
	public void onFocusChange(View arg0, boolean arg1) {
		if(arg1) {
			Log.d(TAG, "input focus="+arg0.getId());
			currentInputFocus = arg0.getId();
		} else {
			Log.d(TAG, "input leave focus="+arg0.getId());
		}
	}
    
	private class CheckOnClickListener implements OnClickListener {
	    @Override
		public void onClick(View v) {
			TextView tv = (TextView)v;
			Log.d(Constant.TAG,"onClick index="+tv.getText()+tv.getContentDescription()+",width="+tv.getWidth());
			int newWidth = tv.getWidth()+Constant.ZOOM_INCREMENTAL;
			// �Nheader�����]�w�A�ǵ�data row
			checkAdapter.setColumnWidth(Integer.parseInt(tv.getContentDescription().toString()),newWidth);
			// �]�wheader�A�P��trigger reload ListView 
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(newWidth, tv.getHeight());
			tv.setLayoutParams(params);
		}
	}

	private class CheckOnLongClickListener implements OnLongClickListener {
		@Override
		public boolean onLongClick(View v) {
			TextView tv = (TextView)v;
			Log.d(TAG,"onLongClick index="+tv.getText()+tv.getContentDescription()+",width="+tv.getWidth());
			// �]�w LayoutParams�A��Jtag���A�H�Kdrag event�A�ϥ�
			tv.setTag(checkAdapter);
			// start drag for this view, no ClipData need
			tv.startDrag(null, new DragShadowBuilder(v), (Object)v, 0);
			return true;
		}
	}
	
	private class CancelOnClickListener implements OnClickListener {
	    @Override
		public void onClick(View v) {
			TextView tv = (TextView)v;
			Log.d(Constant.TAG,"onClick index="+tv.getText()+tv.getContentDescription()+",width="+tv.getWidth());
			int newWidth = tv.getWidth()+Constant.ZOOM_INCREMENTAL;
			// �Nheader�����]�w�A�ǵ�data row
			cancelAdapter.setColumnWidth(Integer.parseInt(tv.getContentDescription().toString()),newWidth);
			// �]�wheader�A�P��trigger reload ListView 
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(newWidth, tv.getHeight());
			tv.setLayoutParams(params);
		}
	}

	private class CancelOnLongClickListener implements OnLongClickListener {
		@Override
		public boolean onLongClick(View v) {
			TextView tv = (TextView)v;
			Log.d(TAG,"onLongClick index="+tv.getText()+tv.getContentDescription()+",width="+tv.getWidth());
			// �]�w LayoutParams�A��Jtag���A�H�Kdrag event�A�ϥ�
			tv.setTag(cancelAdapter);
			// start drag for this view, no ClipData need
			tv.startDrag(null, new DragShadowBuilder(v), (Object)v, 0);
			return true;
		}
	}
	
	private class ThisOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			View view = mRoot.findViewById(currentInputFocus);
			if(view instanceof EditText) {
				EditText et = (EditText)view;
				Button btn = (Button)v;
				if(btn.getId()==btnSNK.getId() && 
						(et.getId()==et1.getId())
						) {
					et.setText(btn.getText().toString()+et.getText().toString());
					et.setSelection(et.getText().length());
				} else {
					int length = et.getText().length();
					int pos = et.getSelectionStart();
					String pre = et.getText().toString().substring(0, pos);
					String pox = et.getText().toString().substring(pos,length);
					et.setText(pre+btn.getText().toString()+pox);
					if(et.getText().length()>=pos+btn.getText().toString().length()) et.setSelection(pos+btn.getText().toString().length());
				}
				imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
				et.requestFocus();
			}
		}
		
	}
	
	private class ReceiveAmtTextWatcher implements TextWatcher {

		String beforeTxtOfet1;

		@Override
		public void afterTextChanged(Editable arg0) {
			int maxLength = 0;
			int inputAllowDigit = 0;
			inputAllowDigit = myActivity.currentCompany.getAcDecimal();
			maxLength = getResources().getInteger(R.integer.main_receive_editText1_max_length);			
			
			int idxDot = arg0.toString().indexOf(".");
			// check integer
			if(idxDot>maxLength || (idxDot<0 && arg0.toString().length()>maxLength)) {
				et1.setText(beforeTxtOfet1);
				et1.setSelection(beforeTxtOfet1.length()-1);
				return;
			}
			Log.d(TAG,"inputAllowDigit="+inputAllowDigit);
			// check decimal
			if(idxDot!=-1 && (arg0.toString().length()-idxDot-1)>inputAllowDigit) {
				et1.setText(beforeTxtOfet1);
				et1.setSelection(beforeTxtOfet1.length()-1);					
			}	
			
			// �s�J���B
			double receiveAmt = 0;
			String s = arg0.toString();
			try {
				receiveAmt = nfAcDecimal.parse(s).doubleValue();
			} catch (ParseException e1) {
		    	Log.d(TAG,"not number...");
				if(s.equals("-") || s.equals(".")) return;
				if(!s.equals("")) {
					String msg = getResources().getString(R.string.takeorderMessage10);
					AlertDialog dialog1 = getAlertDialogForMessage(getResources().getString(R.string.takeorderMessage1a),msg);
					dialog1.show();	
					return;    	
				}
			}
			omO.setReceiveAmt(receiveAmt==0?null:receiveAmt);
			omO.doUpdate(DBAdapter);
			if(omO.getRid()>=0) {
				Log.i(TAG,"ReceiveAmt update to "+omO.getReceiveAmt());
				updateTopInfo();
		    	validate();
			} else {
				Log.w(TAG,"ReceiveAmt update failed! "+omO.getReceiveAmt());
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			beforeTxtOfet1 = s.toString();
			Log.d(TAG,"beforeTextChanged:"+s+",start="+start+",count="+count+",after="+after);
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			Log.d(TAG,"onTextChanged:"+s+",start="+start+",before="+before+",count="+count);
		}
			
	}
	
    private AlertDialog getAlertDialogForDeleteMove(String title,String message, final int position, final int iNote) {
    	Builder builder = new AlertDialog.Builder(myActivity);
    	builder.setTitle(title);
    	if(message != null) builder.setMessage(message);
    	final String[] delete_move = getResources().getStringArray(R.array.subcheck_delete_move);
    	builder.setItems(delete_move, new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			String choice = delete_move[which];
    			Log.d(TAG,choice+" selected!");
    			switch(which) {
    			case 0: // delete
    				if(viewQOCK!=null && iNote==CHECK_NOTE) {
						OrderCheck omOC = new OrderCheck();
						omOC.setCompanyID(viewQOCK.getCompanyID());
						omOC.setCustomerID(viewQOCK.getCustomerID());
						omOC.setCheckNo(viewQOCK.getCheckNo());
						omOC.findByKey(DBAdapter);
						if(omOC.getRid()>=0) {
							omOC.doDelete(DBAdapter);
							if(omOC.getRid()>=0) {
								Log.i(TAG,"OrderCheck deleted!,CheckNo="+omOC.getCheckNo());
							} else {
								Log.w(TAG,"OrderCheck delete failed!,CheckNo="+omOC.getCheckNo());
							}
						} else Log.w(TAG,"OrderCheck not found!,CheckNo="+omOC.getCheckNo());
						checkAdapter.gatherData(
   			    			String.valueOf(omO.getSerialID())
    			    			);
						checkAdapter.notifyDataSetChanged();
    			    	validate();
    			    	updateTopInfo();
    			    	// �q��backup
    					synchronized(myActivity) {
    						myActivity.setNeedBackup(true);
    						myActivity.notify();
    					}
    				}
    				if(viewQOCC!=null && iNote==CANCEL_NOTE) {
						OrderReceive omOR = new OrderReceive();
						omOR.setCompanyID(viewQOCC.getCompanyID());
						omOR.setCustomerID(viewQOCC.getCustomerID());
						omOR.setCustNo(viewQOCC.getCustNo());
						omOR.findByKey(DBAdapter);
						if(omOR.getRid()>=0) {
							omOR.doDelete(DBAdapter);
							if(omOR.getRid()>=0) {
								Log.i(TAG,"OrderReceive deleted!,CustNo="+omOR.getCustNo());
							} else {
								Log.w(TAG,"OrderReceive delete failed!,CustNo="+omOR.getCustNo());
							}
						} else Log.w(TAG,"OrderReceive not found!,CustNo="+omOR.getCustNo());
						cancelAdapter.gatherData(
   			    			String.valueOf(omO.getSerialID())
    			    			);
						cancelAdapter.notifyDataSetChanged();
    			    	validate();
    			    	updateTopInfo();
    			    	// �q��backup
    					synchronized(myActivity) {
    						myActivity.setNeedBackup(true);
    						myActivity.notify();
    					}
    				}
    				break;
    			case 1: // delete all
    				String title = getString(R.string.subdetailDialogMessage1);
    				String msg = getString(R.string.subdetailDialogMessage4);
    				AlertDialog dialog2 = getAlertDialogForDeleteAll(title,msg,iNote);
    				dialog2.show();
    				break;
    			}    			        	    			
    			dialog.dismiss();
    		}
    	});

    	String msg = getResources().getString(R.string.Button2);
    	builder.setNegativeButton(msg, null);
    	return builder.create();
    }

    private AlertDialog getAlertDialogForDeleteAll(String title,String message, final int iNote) {
    	Builder builder = new AlertDialog.Builder(myActivity);
    	builder.setTitle(title);
    	if(message != null) builder.setMessage(message);
    	final String msgY = getResources().getString(R.string.Button1);
    	builder.setPositiveButton(msgY, new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			if(iNote==CHECK_NOTE) {
	    			OrderCheck omOC = new OrderCheck();
					omOC.setOrdersSerialID(omO.getSerialID());
					omOC.deleteByOrdersSerialID(DBAdapter);
					checkAdapter.gatherData(
			    			String.valueOf(omO.getSerialID())
			    			);
					checkAdapter.notifyDataSetChanged();
    			} else if(iNote==CANCEL_NOTE) {
	    			OrderReceive omOC = new OrderReceive();
					omOC.setOrdersSerialID(omO.getSerialID());
					omOC.deleteByOrdersSerialID(DBAdapter);
					cancelAdapter.gatherData(
			    			String.valueOf(omO.getSerialID())
			    			);
					cancelAdapter.notifyDataSetChanged();    				
    			}
    			dialog.dismiss();
		    	validate();
		    	updateTopInfo();
		    	// �q��backup
				synchronized(myActivity) {
					myActivity.setNeedBackup(true);
					myActivity.notify();
				}
    		}
    	});
    	final String msgN = getResources().getString(R.string.Button2);
    	builder.setNegativeButton(msgN, new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			dialog.dismiss();
    		}
    	});
    	return builder.create();    	
    }

    private AlertDialog getAlertDialogForSelectPhrase(String title,String message, final int iNote) {
    	Builder builder = new AlertDialog.Builder(myActivity);
    	builder.setTitle(title);
    	if(message != null) builder.setMessage(message);
    	final SelectPhraseDataAdapter adapter = new SelectPhraseDataAdapter(myActivity,DBAdapter);
    	adapter.gatherData(String.valueOf(Phrase.PHKINDNO_15));
    	builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			SelectPhraseView viewSP = (SelectPhraseView)adapter.getItem(which);
    			// �]�wnote
    			if(iNote==CHECK_NOTE) {
	    			int cursor = checketNote.getSelectionStart();
					String prefix_note = checketNote.getText().subSequence(0, cursor).toString();
					String surfix_note = checketNote.getText().subSequence(cursor,checketNote.getText().length()).toString();
					checketNote.setText(prefix_note+viewSP.getPhraseDESC()+surfix_note);
					checketNote.setSelection(cursor+viewSP.getPhraseDESC().length());	  
    			} else if(iNote==CANCEL_NOTE) {
	    			int cursor = canceletNote.getSelectionStart();
					String prefix_note = canceletNote.getText().subSequence(0, cursor).toString();
					String surfix_note = canceletNote.getText().subSequence(cursor,canceletNote.getText().length()).toString();
					canceletNote.setText(prefix_note+viewSP.getPhraseDESC()+surfix_note);
					canceletNote.setSelection(cursor+viewSP.getPhraseDESC().length());	      				
    			}
    			dialog.dismiss();
    		}
    	});
    	String msg = getResources().getString(R.string.Button2);
    	builder.setNegativeButton(msg, null);
    	return builder.create();
    }

   *//**
     * �ˬdOrderCheck�������
     *//*
    public void validate() {
    	validate(this);
    }
    
    public void validate(MainMenuFragment mmf) {
		boolean b = true;
		String err = null;
		double sumOrderCheck=0,sumOrderReceive=0;
		OrderCheck omOCK = new OrderCheck();
		omOCK.setOrdersSerialID(omO.getSerialID());
		List<OrderCheck> ltOCK = omOCK.queryByOrdersSerialID(DBAdapter);
		for(OrderCheck om : ltOCK) {
			sumOrderCheck += om.getAmount();
			if(
				Constant.isEmpty(om.getCheckNo()) ||
				om.getAmount()==0 ||
				om.getDueDate()==null ||
				Constant.isEmpty(om.getBank()) ||
				Constant.isEmpty(om.getAccountNo())
			) {
				b = false;
				err = mmf.getResources().getString(R.string.subcheckDialogMessage3);
				break;
			}
			
		}
		if(!b) {
			isCheckCorrect = false;
			checkErrorMsg = err;
			return;
		} else {
			isCheckCorrect = true;
			checkErrorMsg = null;			
		}
		// �R�P
		b = true;
		OrderReceive omOR = new OrderReceive();
		omOR.setOrdersSerialID(omO.getSerialID());
		List<OrderReceive> ltOR = omOR.queryByOrdersSerialID(DBAdapter);
		for(OrderReceive om : ltOR) {
			sumOrderReceive += om.getAmount();
			if(
				Constant.isEmpty(om.getCustNo()) ||
				om.getAmount()==0 ||
				om.getStartDate()==null ||
				om.getEndDate()==null
			) {
				b = false;
				err = mmf.getResources().getString(R.string.subcancelDialogMessage1);
				break;
			}
			
		}
		���کM�R�P�����۵�
		//Log.d(TAG,"tv4a="+tv4a.getText()+",tv3a="+tv3a.getText());
		//if(!tv4a.getText().equals(tv3a.getText())) {
		//	b = false;
		//	err = getResources().getString(R.string.receiveDialogMessage1);
		//}
		if(sumOrderCheck+(omO.getReceiveAmt()==null?0:omO.getReceiveAmt())!=sumOrderReceive) {
			b = false;
			err = mmf.getResources().getString(R.string.receiveDialogMessage1);
		}
		
		if(!b) {
			isCancelCorrect = false;
			cancelErrorMsg = err;
			return;
		} else {
			isCancelCorrect = true;
			cancelErrorMsg = null;			
		}
    	
    }
    
    public void updateTopInfo() {
		OrderCheck omOCk = new OrderCheck();
		double checkAmt = 0;
		omOCk.setOrdersSerialID(omO.getSerialID());
		List<OrderCheck> ltOCk = omOCk.queryByOrdersSerialID(DBAdapter);
		for(OrderCheck om:ltOCk) {
			checkAmt += om.getAmount();
		}
		Log.d(TAG,"checkAmt="+checkAmt);
		tv2a.setText(nfAcDecimal.format(checkAmt));
		OrderReceive omOR = new OrderReceive();
		double cancelAmt = 0;
		omOR.setOrdersSerialID(omO.getSerialID());
		List<OrderReceive> ltOR = omOR.queryByOrdersSerialID(DBAdapter);
		for(OrderReceive om:ltOR) {
			cancelAmt += om.getAmount();
		}
		Log.d(TAG,"cancelAmt="+cancelAmt);
		tv3a.setText(nfAcDecimal.format(cancelAmt));
		��s�������
		tv4a.setText(nfAcDecimal.format((omO.getReceiveAmt()==null?0:omO.getReceiveAmt())+checkAmt));
    }

}
*/