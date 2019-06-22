package com.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.business.BusinessBroadcastUtils;
import com.core.ServerUrl;
import com.core.utils.SpUtils;
import com.easysoft.utils.lib.system.StringUtils;
import com.easysoft.utils.lib.system.ThreadPoolUtils;
import com.utils.ThemeHelper;

import java.util.Timer;
import java.util.TimerTask;

/**
 * <br>创建者：ldh 
 * <br>时间：2015年4月27日 上午10:35:01 
 * <br>注释：欢迎界面
 */
public class WellComeHelper   {
	private int TIME = 200;
	String TAG = WellComeHelper.class.getSimpleName();

	Context  mContext;


	public void init(Context context ){
		mContext=context;

//		PermissionCheckUtils.startDevelopmentActivity(this);
//        if (GlobalConstants.getInstance().getAppType()==GlobalConstants.TYPE_SYSTEM_APP){
//			gotoMainOrloginUI(this);
//			return;
//		}

		ThreadPoolUtils.execute(new Runnable() {
			@Override
			public void run() {

				ThemeHelper.initConfig(mContext);
				BusinessBroadcastUtils.USER_VALUE_LOGIN_ID = SpUtils.getString(mContext, BusinessBroadcastUtils.STRING_LOGIN_ID,"");
				BusinessBroadcastUtils.USER_VALUE_PWD 	   = SpUtils.getString(mContext, BusinessBroadcastUtils.STRING_LOGIN_USER_PWD,"");
				BusinessBroadcastUtils.USER_VALUE_USER_ID  = SpUtils.getString(mContext, BusinessBroadcastUtils.STRING_LOGIN_USER_ID,"");

				String ip = SpUtils.getString(mContext, ServerUrl.list_server_iP);
				String port = SpUtils.getString(mContext,ServerUrl.list_server_port);
				 if (StringUtils.isNotEmpty(ip)){
					 ServerUrl.ip=ip;
				 }
				 if (StringUtils.isNotEmpty(port)){
					 ServerUrl.port=Integer.parseInt(port);
				 }
				if (StringUtils.isNotEmpty(ip)&&StringUtils.isNotEmpty(port)){
					ServerUrl.resetBaseUrl();
				}
				timer.schedule(task, TIME); // 1s后执行task
			}
		});
	}


		@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what==1){
				switchActivity();
			}
		};
	};

	private void switchActivity() {
//		 if (CoreApplication.getInstance().isDubug){
//			 gotoMainOrloginUI();
//		 }else if(StringUtils.isNotEmpty(BusinessBroadcastUtils.USER_VALUE_LOGIN_ID)){
//			 gotoMainOrloginUI(wellComeActivity);
//		 }else{
//			 Intent  homeIntent=new Intent(wellComeActivity,LoginActivity.class);
//			 wellComeActivity.startActivity(homeIntent);
//		 }

	}

	//
	Timer timer = new Timer();
	TimerTask task = new TimerTask() {

		@Override
		public void run() {
			// 需要做的事:发送消息
			handler.sendEmptyMessage(1);
		}
	};

//	/**
//	 * 时间：2015-6-29 下午5:46:38
//	 * 注释：判断是进入登录界面还是主界面
//	 */
//	public static void gotoMainOrloginUI(){
//		Intent  homeIntent=new Intent(mContext,HomeActivity.class);
//		mContext.startActivity(homeIntent);
//
//	}
}
