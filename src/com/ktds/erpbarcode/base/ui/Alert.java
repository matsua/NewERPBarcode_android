package com.ktds.erpbarcode.base.ui;

import com.ktds.erpbarcode.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class Alert {
	
	//Activity, Title, Message, Mode(1: dissmiss, 2: finish())
	public void alertShow (final Activity act,int title, int msg, final int mode) {
		
		AlertDialog.Builder alert = new AlertDialog.Builder(act);
		alert.setTitle(title);
		alert.setMessage(msg);
		alert.setPositiveButton(R.string.alert_title, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

				switch (mode) {
					case 1 :
						dialog.dismiss();
						break;
					case 2 :
						dialog.dismiss();
						act.finish();
						break;						
				}
					dialog.dismiss();
			}
		
		});

		alert.show();
		
		}
}
