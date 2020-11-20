package com.core.base;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.business.BusinessBroadcastUtils;
import com.easysoft.utils.lib.system.ToastUtils;

@SuppressLint("HandlerLeak")
public abstract class BasicActivity extends FragmentActivity implements BaseUiInterface{

	BasicActivity activity;
	protected MYBroadcastReceiver receiver;

	protected IntentFilter filter;


	/**当前正在运行的文件的类名 用于关闭当前activity*/
	public String currentClassName ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		currentClassName = this.getClass().getName();


		receiver = new MYBroadcastReceiver();
		activity = this;
		
		 filter = new IntentFilter();
		filter.addAction(PublicVariable.ACTIVITY_RESULTCODE_999_RETURNLOGINVIEW);
		filter.addAction(GlobalConstants.getInstance().getBroadCastReceiverActionName());
		filter.addAction(BaseUiInterface.BROAD_CAST_RECEIVER_ACTION_NAME);

		registerReceiver(receiver, filter);

	}



	@Override
	protected void onDestroy() {
		
		
		unregisterReceiver(receiver);
		
		super.onDestroy();
	}

	public void showToast(final int id) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ToastUtils.show(activity,activity.getResources().getString(id));

			}
		});
	}
	class MYBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			String type = intent.getStringExtra(MSG_TYPE);
			Object mode = intent.getSerializableExtra(MSG_MODE);

		
				if(type!=null){
					if(type.equals(BusinessBroadcastUtils.TYPE_APP_EXIT)){
						finish();
					}
					getBroadcastReceiverMessage(type,mode);

				}
				
			
			
		}
	}

	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}


	
	// 以下为activities 的方法
	private BasicActivity requestSubActivity;
	private int resultCode;
	private Intent Intentdata;
	protected Dialog alertDialog;
	private Intent subintent;

	public BasicActivity getRequestSubActivity() {
		return requestSubActivity;
	}

	public void setRequestSubActivity(BasicActivity requestSubActivity) {
		this.requestSubActivity = requestSubActivity;
	}

	public void setRequestSubActivity(BasicActivity requestSubActivity,
			Intent subintent) {
		this.requestSubActivity = requestSubActivity;
		this.subintent = subintent;
	}

	public  Class<?> getTargetClass(Intent intent) {
		Class<?> clazz = null;
		try {
			if (intent.getComponent() != null)
				clazz = Class.forName(intent.getComponent().getClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return clazz;
	}

	

	

}
