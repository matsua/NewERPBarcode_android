package com.ktds.erpbarcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.TextView;

public class IntroActivity extends Activity {
	
	private static final String TAG = "IntroActivity";
	
//	private final int MY_PERMISSION_REQUEST = 100;
	
	private Handler mTimerHandler = new Handler();
	
	private TextView mAppVersionText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//-----------------------------------------------------------
	    // ActionBar를 hide처리한다.
	    //----------------------------------------------------------- 
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		//-----------------------------------------------------------
	    // ActionBar에 progress를 활성화 시킨다.
	    //-----------------------------------------------------------
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		
		super.onCreate(savedInstanceState);
		
		//-----------------------------------------------------------
		// Open된 Activity를 Set한다.
	    //-----------------------------------------------------------
		GlobalData.getInstance().setNowOpenActivity(this);
		setContentView(R.layout.base_intro_activity);
	}
    
    private void ScreenLoad(){
    	//-----------------------------------------------------------
		// 화면을그린다.
		//-----------------------------------------------------------
		setLayout();
		initScreen();
		//-----------------------------------------------------------
		// 2초후에 로그인엑티비티로 이동한다.
		//-----------------------------------------------------------
		mTimerHandler.postDelayed(mStartActivityTask, 2000);
    }
    
    //-----------------------------------------------------------
    // Permission check.
    //----------------------------------------------------------- 
//    @TargetApi(Build.VERSION_CODES.M)
//    private void checkPermission() {
//        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || 
//        checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || 
//        checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED || 
//        checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
//            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, 
//											Manifest.permission.WRITE_EXTERNAL_STORAGE, 
//											Manifest.permission.READ_PHONE_STATE,
//											Manifest.permission.CAMERA}, MY_PERMISSION_REQUEST);
//        }else{
//        	ScreenLoad();
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSION_REQUEST:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                	ScreenLoad();
//                }else{
//                    Log.d(TAG, "Permission always deny");
//                }
//                break;
//        }
//    }
	
	private void setLayout() {
		//-----------------------------------------------------------
		// 앱 버젼
		//-----------------------------------------------------------
		mAppVersionText = (TextView) findViewById(R.id.intro_appversion);
	}

	@Override
	protected void onStart() {
		super.onStart();
		ScreenLoad();
	}
	
	@Override
	protected void onDestroy() {
		mTimerHandler.removeCallbacks(mStartActivityTask);
		super.onDestroy();
	}
	
	private Runnable mStartActivityTask = new Runnable() {
		public void run() {
			Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
	        startActivity(intent);
		}
	};
	
	private void initScreen() {
		String versionInfo = GlobalData.getInstance().getAppVersionName();
		mAppVersionText.setText(versionInfo);
	}
}
