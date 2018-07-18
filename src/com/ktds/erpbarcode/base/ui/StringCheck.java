package com.ktds.erpbarcode.base.ui;

import android.app.Activity;

import android.os.AsyncTask;

public class StringCheck {
	private Alert alert;
	
	public boolean StringNullCheck (String str) {
		
		if (str.getBytes().length <= 0) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public void StringCheckAlert (final Activity act, int title, int msg, int mode ) {		

		alert = new Alert();
		alert.alertShow(act , title, msg, mode);	
		return;

	}
}
