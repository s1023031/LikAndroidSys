package com.lik.android.view;

import com.lik.Constant;
import com.lik.android.main.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * �@��ListView���Aheader�ҨϥΡC��gonDragEvent�A�H�K����drag�ĪG
 * @author charles
 *
 */
public class LikTextView extends TextView {
	
	protected static final String TAG = LikTextView.class.getName();

	public LikTextView(Context context) {
		super(context);
	}

    public LikTextView(Context context, AttributeSet attrs) {  
        super(context, attrs); 
    }
    
    @Override  
    public boolean onDragEvent(DragEvent event) { 

    	boolean result = false; 
	    TextView mDragView = (TextView)event.getLocalState();
	    if(mDragView==null) return result;
	    // �n�i�Hdrag�ק�sequence���A���n��
	    BaseDataAdapter<?> adapter = (BaseDataAdapter<?>)mDragView.getTag();
	    switch (event.getAction()) { 
	        case DragEvent.ACTION_DRAG_STARTED: { 	             
	            Log.i("LikSys", "ACTION_DRAG_STARTED="+mDragView.getText()+":"+this.getText()); 	             
	            /** 
	             * return true�Athen�]ACTION_DRAG_ENTERED, ACTION_DRAG_LOCATION, ACTION_DROP�^will be executed! 
	             */ 
	            result = true; 	             
	            break; 
	        }  
	        case DragEvent.ACTION_DRAG_ENTERED: { 	             
	            Log.i("LikSys", "ACTION_DRAG_ENTERED="+mDragView.getText()+":"+this.getText()); 	             
	            break; 
	        }  	  
	        case DragEvent.ACTION_DRAG_LOCATION: { 	             
	            Log.i("LikSys", "ACTION_DRAG_LOCATION="+mDragView.getText()+":"+this.getText()); 	             
	            break; 
	        }  	  
	        case DragEvent.ACTION_DROP: { 	             
	            Log.i("LikSys", "ACTION_DROP="+mDragView.getText()+":"+this.getText()); 
	            if(mDragView.getId()==this.getId()) { // ��ܨS�����ʦ�m�A�]�wzoom
	            	Log.i("LikSys", "setting zoom...");
	            	// �]�wheader�A�P��trigger reload ListView
	            	int newWidth = this.getWidth()-Constant.ZOOM_INCREMENTAL;
	            	// �Ywidth�L�p�A���i�A�Y�p 
	            	if(newWidth<this.getContext().getResources().getDimension(R.dimen.list_item_width_short)) {
	            		Log.i(TAG,"width can not be shorted!");
	            	} else {
	            		adapter.setColumnWidth(Integer.parseInt(this.getContentDescription().toString()),newWidth);
	            		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(newWidth, this.getHeight());
	            		this.setLayoutParams(params);
	            	}
	            } else {
	            	ViewGroup vg = (ViewGroup)mDragView.getParent();
	            	int index1 = vg.indexOfChild(mDragView);
	            	int index2 = vg.indexOfChild(this);
	            	Log.d("LikSys","drag view index="+index1+", to index="+index2);
	            	if(index1>index2) { 
	            		vg.removeView(mDragView);
	            		vg.addView(mDragView, index2);
	            	} else {
	            		vg.removeView(mDragView);
	            		vg.addView(mDragView, index2-1);
	            	}
	            	// �]�wsequence
	            	adapter.setSequence(index1, index2);
	            	index1 = vg.indexOfChild(mDragView);
	            	index2 = vg.indexOfChild(this);
	            	Log.d("LikSys","after drag view index="+index1+", to index="+index2);
	            	adapter.notifyDataSetChanged();
	            }
	            break; 
	        }  
	
	        case DragEvent.ACTION_DRAG_ENDED: { 	             
	            Log.i("LikSys", "ACTION_DRAG_ENDED="+mDragView.getText()+":"+this.getText()); 	
	            break; 
	        }  	  
	        case DragEvent.ACTION_DRAG_EXITED: { 	             
	            Log.i("LikSys", "ACTION_DRAG_EXITED="+mDragView.getText()+":"+this.getText()); 	             
	            break; 
	        }  	  
	        default: { 	 
	            break; 
	        }  
	    }  	
	    return result;  
    }

}
