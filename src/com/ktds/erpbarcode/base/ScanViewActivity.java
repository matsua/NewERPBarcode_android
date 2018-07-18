package com.ktds.erpbarcode.base;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ktds.erpbarcode.GlobalData;
import com.ktds.erpbarcode.R;
import com.ktds.erpbarcode.SessionUserData;
import com.ktds.erpbarcode.base.ui.StringCheck;
import com.manateeworks.cameraDemo.ActivityCapture;



public class ScanViewActivity extends Activity {
	
	private static final String TAG = "ScanViewActivity";
	public static final int REQ_CODE_SCAN_LOCATION = 1000;
	public static final int REQ_CODE_SCAN_COST = 2000;
	
	private StringCheck stringCheck;
	
	private String mJobGubun;
	private LinearLayout mLocInputbar;
	private Button mlocScan;
	private EditText mLocCdText;
	private Button mfccScan;
	private EditText mFccCdText;
	private Button mSend;
	private String type;
	private String bsnNo;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	mJobGubun = GlobalData.getInstance().getJobGubun();
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        
        setMenuLayout();
        setContentView(R.layout.scanview_activity);
        
        Intent intent = new Intent(this.getIntent());
        type = intent.getStringExtra("GB");
        
        mLocInputbar = (LinearLayout) findViewById(R.id.locView);
        if(mJobGubun.equals("불용요청") || mJobGubun.equals("OA연식조회") || mJobGubun.equals("비품연식조회")){
        	mLocInputbar.setVisibility(View.INVISIBLE);
        }
        
        mlocScan = (Button) findViewById(R.id.locCdScan);
        mlocScan.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						scan("loc");
					}
				});
        
        mLocCdText = (EditText) findViewById(R.id.scanview_locCd);
        
        mfccScan= (Button) findViewById(R.id.fccCdScan);
        mfccScan.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						scan("fcc");
					}
				});
        mFccCdText = (EditText) findViewById(R.id.scanview_fccCd);

        mSend= (Button) findViewById(R.id.send);
        mSend.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						send();
					}
				});
    }
    
    private void setMenuLayout() {
    	ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(mJobGubun + " [" + SessionUserData.getInstance().getAccessServerName() + " V" + GlobalData.getInstance().getAppVersionName()+"]");
        actionBar.setDisplayShowTitleEnabled(true);
        setProgressBarIndeterminateVisibility(false);
	}
    
    private void scan(String type){
    	
    	if(type.equals("loc")){
    		Intent intent = new Intent(ScanViewActivity.this, ActivityCapture.class);
			startActivityForResult(intent, REQ_CODE_SCAN_LOCATION);
    	}else{
    		Intent intent = new Intent(ScanViewActivity.this, ActivityCapture.class);
			startActivityForResult(intent, REQ_CODE_SCAN_COST);
    	}
    }
    
 	@Override
 	protected void onActivityResult(int reqCode, int resCode, Intent data) {
 		if (resCode != RESULT_OK) return;
 		String barcode = data.getStringExtra("BARCODE");
 		if (TextUtils.isEmpty(barcode)) {
 			return;
 		}
 		switch (reqCode) {
 			case REQ_CODE_SCAN_LOCATION:
 				EditText scanText1 = (EditText) findViewById(R.id.scanview_locCd);
 				if (barcode.length() <= 10) {
 					scanText1.setText(barcode);
 				} else {
 					scanText1.setText(null);
 					stringCheck = new StringCheck();
 					stringCheck.StringCheckAlert(ScanViewActivity.this, R.string.alert_title, R.string.content_msg_3, 1);
 					return;
 				}
 				break;
 			case REQ_CODE_SCAN_COST:
 				EditText scanText2 = (EditText) findViewById(R.id.scanview_fccCd);

 				if (barcode.length() > 10) {
 					scanText2.setText(barcode);
 				} else {
 					scanText2.setText(null);

 					stringCheck = new StringCheck();
 					stringCheck.StringCheckAlert(ScanViewActivity.this, R.string.alert_title, R.string.content_msg_2, 1);
 					return;
 				}
 				break;
 		}
 	}
 	
 	private void send(){
// 		#define API_BASE_OA_LOGIN           @"https://base.kt.com/base/OA/smart/login.jsp?USERID=%@&USERPWD=%@"                             //베이스OA 로그인
// 		#define API_BASE_OA_WORK_LIST_HALF  @"http://base.kt.com/base/OA/smart/work_list.jsp?COM=%@&USERID=%@&BCID=%@&ACT=search"           //베이스OA 불용요청
// 		#define API_BASE_OA_WORK_LIST       @"http://base.kt.com/base/OA/smart/work_list.jsp?COM=%@&USERID=%@&SDID=%@&BCID=%@&ACT=search"   //베이스OA 신규등록, 관리자변경, 재물조사, 납품확인, 대여등록, 대여반납
// 		#define API_BASE_OA_ITEM_SEARCH     @"http://base.kt.com/base/OA/smart/item_search.jsp?COM=%@&USERID=%@&BCID=%@"                    //베이스OA OA연식조회
//
// 		#define API_BASE_OE_LOGIN           @"https://base.kt.com/base/OA/smart_OE/login.jsp?USERID=%@&USERPWD=%@"                              //베이스OE 로그인
// 		#define API_BASE_OE_WORK_LIST_HALF  @"http://base.kt.com/base/OA/smart_OE/work_list.jsp?COM=%@&USERID=%@&BCID=%@&ACT=search"            //베이스OE 불용요청
// 		#define API_BASE_OE_WORK_LIST       @"http://base.kt.com/base/OA/smart_OE/work_list.jsp?COM=%@&USERID=%@&SDID=%@&BCID=%@&ACT=search"    //베이스OE 신규등록, 관리자변경, 재물조사, 납품확인, 대여등록, 대여반납
// 		#define API_BASE_OE_ITEM_SEARCH     @"http://base.kt.com/base/OA/smart_OE/item_search.jsp?COM=%@&USERID=%@&BCID=%@"                     //베이스OE 비품연식조회
 		if(mJobGubun.equals("신규등록")) bsnNo = "0501";
 	    else if(mJobGubun.equals("관리자변경")) bsnNo = "0504";
 	    else if(mJobGubun.equals("재물조사")) bsnNo = "0601";
 	    else if(mJobGubun.equals("불용요청")) bsnNo = "0505";
 	    else if(mJobGubun.equals("OA연식조회") || mJobGubun.equals("비품연식조회")) bsnNo = "0602";
 	    else if(mJobGubun.equals("납품확인")) bsnNo = "0512";
 	    else if(mJobGubun.equals("대여등록")) bsnNo = "0513";
 	    else if(mJobGubun.equals("대여반납")) bsnNo = "0503";
 		
 		String url = "http://base.kt.com/base/OA/smart";
		if(type.equals("OE")){
			url += "_OE";
		}
 		
 		if(mJobGubun.equals("신규등록") || mJobGubun.equals("관리자변경") || mJobGubun.equals("재물조사") || mJobGubun.equals("납품확인") || mJobGubun.equals("대여등록") || mJobGubun.equals("대여반납")){
				url += "/work_list.jsp?COM=" + bsnNo + "&USERID=91186176&SDID=" + mLocCdText.getText().toString() + "&BCID=001Z00911318010012&ACT=search";
		}else if(mJobGubun.equals("불용요청")){
			url += "/work_list.jsp?COM=" + bsnNo + "&USERID=91186176&BCID=001Z00911318010012&ACT=search";
		}else if(mJobGubun.equals("OA연식조회") || mJobGubun.equals("비품연식조회")){
			url += "/item_search.jsp?COM=" + bsnNo + "&USERID=91186176&BCID=001Z00911318010012";
		}
 		
 		System.out.print("url >>>>>>>>>>>" + url);
 		
 		Intent intent = new Intent(getApplicationContext(), ScanViewResultActivity.class);
		intent.putExtra("mLoadUrl",url);
		startActivity(intent);
 	}
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (GlobalData.getInstance().isGlobalProgress()) return false;
		if (item.getItemId()==android.R.id.home) {
    		if (GlobalData.getInstance().isChangeFlag()) {
				return true;
	        }
			
			finish();
		} else {
        	return super.onOptionsItemSelected(item);
        }
	    return false;
    }

	@Override
	public void onBackPressed() {
		if (GlobalData.getInstance().isGlobalProgress()) return;
		if (GlobalData.getInstance().isChangeFlag()) {
			return;
        }
		
		super.onBackPressed();
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		GlobalData.getInstance().setNowOpenActivity(this);
	}


	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
