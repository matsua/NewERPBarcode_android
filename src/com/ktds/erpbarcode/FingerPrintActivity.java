package com.ktds.erpbarcode;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class FingerPrintActivity extends Activity {
	
	private static final String TAG = "FingerPrintActivity";
	
	public static final String CERTIFICATION = "certification";
	
	public static final String INPUT_PWUPDATE_YN = "passwdUpdateYn";
	
	public static final String INPUT_NOTICE = "passwdIsNotice";
	
	public static final String INPUT_AGREE = "informationIsagree";
	
	private String p_passwdUpdateYn = "";
	
	private boolean p_passwdIsNotice = false;	
	
	private boolean p_informationIsagree = false;

	private boolean isCertified = false;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//-----------------------------------------------------------
	    // ActionBar에 progress를 활성화 시킨다.
	    //-----------------------------------------------------------
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		
		super.onCreate(savedInstanceState);
		
		//-----------------------------------------------------------
		// Open된 Activity를 Set한다.
	    //-----------------------------------------------------------
		GlobalData.getInstance().setNowOpenActivity(this);
		

		setMenuLayout();
		setContentView(R.layout.base_finger_certification_activity);
		
		p_passwdUpdateYn = getIntent().getStringExtra(INPUT_PWUPDATE_YN);
    	p_passwdIsNotice = getIntent().getBooleanExtra(INPUT_NOTICE, false);
    	p_informationIsagree = getIntent().getBooleanExtra(INPUT_AGREE, false);
		
		setLayout();
		
		initScreen();
	}
	
	private void setMenuLayout() {
		ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.fingerPrint_title);
        actionBar.setDisplayShowTitleEnabled(true);
        setProgressBarIndeterminateVisibility(false);
	}
	
	/**
     * 화면 Layout.xml 설정한다.
     */
	private void setLayout() {

	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId()==android.R.id.home) {
			returnData();
		} else {
        	return true;
        }
	    return false;
    }
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	
	
	@Override
	public void onBackPressed() {
		returnData();
		super.onBackPressed();
	}
	
	public void returnData(){
		Intent intent = new Intent();
        intent.putExtra(CERTIFICATION, isCertified);
        intent.putExtra(INPUT_PWUPDATE_YN, p_passwdUpdateYn);
        intent.putExtra(INPUT_NOTICE, p_passwdIsNotice);
        intent.putExtra(INPUT_AGREE, p_informationIsagree);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}

	private void initScreen() {
		isCertified = false;
		
	}
	

}
