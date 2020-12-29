package com.iflytek.voicedemo;

import com.iflytek.cloud.SpeechUtility;
import com.iflytek.speech.setting.UrlSettings;
import com.linlsyf.cantonese.R;
import com.webview.WebMainActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import static com.iflytek.speech.setting.UrlSettings.PREFER_NAME;

public class MainActivity extends Activity implements OnClickListener {

	private static final String TAG = MainActivity.class.getSimpleName();
	private Toast mToast;
	private final int URL_REQUEST_CODE = 0X001;
	private TextView edit_text;

	@SuppressLint("ShowToast")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		edit_text = (TextView) findViewById(R.id.edit_text);
		StringBuffer buf = new StringBuffer();
		buf.append("当前APPID为：");
		buf.append(getString(R.string.app_id)+"\n");
		buf.append(getString(R.string.example_explain));
		edit_text.setText(buf);
		requestPermissions();
		mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
		//mscInit(null);//采用sdk默认url
		SimpleAdapter listitemAdapter = new SimpleAdapter();
		((ListView) findViewById(R.id.listview_main)).setAdapter(listitemAdapter);


	}

	@Override
	public void onClick(View view) {
		int tag = Integer.parseInt(view.getTag().toString());
		Intent intent = null;
		switch (tag) {
		case 0:
			// 语音转写
			intent = new Intent(MainActivity.this, IatDemo.class);
			break;

		case 2:
			intent = new Intent(MainActivity.this, WebMainActivity.class);
			// 语义理解
			//showTip("请登录：http://www.xfyun.cn/ 下载aiui体验吧！");
			break;


		default:
			break;
		}
		
		if (intent != null) {
			startActivity(intent);
		}
	}

	// Menu 列表
	String items[] = { "立刻体验语音听写", "立刻体验语法识别", "立刻体验语义理解", };

	private class SimpleAdapter extends BaseAdapter {
		public View getView(int position, View convertView, ViewGroup parent) {
			if (null == convertView) {
				LayoutInflater factory = LayoutInflater.from(MainActivity.this);
				View mView = factory.inflate(R.layout.list_items, null);
				convertView = mView;
			}
			
			Button btn = (Button) convertView.findViewById(R.id.btn);
			btn.setOnClickListener(MainActivity.this);
			btn.setTag(position);
			btn.setText(items[position]);
			
			return convertView;
		}

		@Override
		public int getCount() {
			return items.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}
	}

	private void showTip(final String str) {
		mToast.setText(str);
		mToast.show();
	}

	@Override
	protected void onResume() {
		// 开放统计 移动数据统计分析

		super.onResume();
	}

	@Override
	protected void onPause() {
		// 开放统计 移动数据统计分析

		super.onPause();
	}

	private void requestPermissions(){
		try {
			if (Build.VERSION.SDK_INT >= 23) {
				int permission = ActivityCompat.checkSelfPermission(this,
						Manifest.permission.WRITE_EXTERNAL_STORAGE);
				if(permission!= PackageManager.PERMISSION_GRANTED) {
					ActivityCompat.requestPermissions(this,new String[]
							{Manifest.permission.WRITE_EXTERNAL_STORAGE,
							Manifest.permission.LOCATION_HARDWARE,Manifest.permission.READ_PHONE_STATE,
							Manifest.permission.WRITE_SETTINGS,Manifest.permission.READ_EXTERNAL_STORAGE,
							Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_CONTACTS},0x0010);
				}

				if(permission != PackageManager.PERMISSION_GRANTED) {
					ActivityCompat.requestPermissions(this,new String[] {
							Manifest.permission.ACCESS_COARSE_LOCATION,
							Manifest.permission.ACCESS_FINE_LOCATION},0x0010);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}

	private void mscInit (String serverUrl){
		// 注意：此接口在非主进程调用会返回null对象，如需在非主进程使用语音功能，请增加参数：SpeechConstant.FORCE_LOGIN+"=true"
		// 参数间使用半角“,”分隔。
		// 设置你申请的应用appid,请勿在'='与appid之间添加空格及空转义符

		// 注意： appid 必须和下载的SDK保持一致，否则会出现10407错误
		StringBuffer bf = new StringBuffer();
		bf.append("appid="+getString(R.string.app_id));
		bf.append(",");
		if (!TextUtils.isEmpty(serverUrl)) {
			bf.append("server_url="+serverUrl);
			bf.append(",");
		}
		//此处调用与SpeechDemo中重复，两处只调用其一即可
		SpeechUtility.createUtility(this.getApplicationContext(), bf.toString());
		// 以下语句用于设置日志开关（默认开启），设置成false时关闭语音云SDK日志打印
		// Setting.setShowLog(false);
	}

	private void mscUninit() {
		if (SpeechUtility.getUtility()!= null) {
			SpeechUtility.getUtility().destroy();
			try{
				new Thread().sleep(40);
			}catch (InterruptedException e) {
				Log.w(TAG,"msc uninit failed"+e.toString());
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (URL_REQUEST_CODE == requestCode) {
			Log.d(TAG,"onActivityResult>>");
			try{
				SharedPreferences pref = getSharedPreferences(PREFER_NAME, MODE_PRIVATE);
				String server_url = pref.getString("url_preference","");
				String domain = pref.getString("url_edit","");
				Log.d(TAG,"onActivityResult>>domain = "+domain);
				if (!TextUtils.isEmpty(domain)) {
					server_url = "http://"+domain+"/msp.do";
				}
				Log.d(TAG,"onActivityResult>>server_url = "+server_url);
				mscUninit();
				new Thread().sleep(40);
				//mscInit(server_url);
			}catch (Exception e) {
				showTip("reset url failed");
			}

		}
	}
}
