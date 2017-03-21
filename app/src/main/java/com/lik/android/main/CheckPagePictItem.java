package com.lik.android.main;

import android.graphics.drawable.Drawable;

public class CheckPagePictItem {
	public String _id;
	public String _Sno;
	public String _FileName;
	public String _Dir;
	public String _Tno;
	public String _Detail;
	public Drawable pict;
	public String _Sflag;
	public String dateTime;
	
	public CheckPagePictItem(String _id,String _Sno,String _FileName,String _Dir,String _Tno,String _Detail,String _Sflag){
		this._id=_id;
		this._Sno=_Sno;
		this._FileName=_FileName;
		this._Dir=_Dir;
		this._Tno=_Tno;
		this._Detail=_Detail;
		this._Sflag=_Sflag;
		
	}
	
	public CheckPagePictItem(String _id,String dateTime,String _Dir){
		this._id=_id;
		this.dateTime=dateTime;
		this._Dir=_Dir;
		
	}
	
	public void setDrawable(Drawable pict){
		this.pict = pict;
	}
}
