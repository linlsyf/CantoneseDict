package com.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.business.BusinessBroadcastUtils;
import com.core.ServerUrl;
import com.core.base.BasicActivity;
import com.core.utils.SpUtils;
import com.easy.recycleview.outinter.RecycleConfig;
import com.easy.recycleview.outinter.ThemeConfig;
import com.easysoft.utils.lib.system.StringUtils;
import com.easysoft.utils.lib.system.ThreadPoolUtils;
import com.easysoft.widget.config.WidgetConfig;
import com.linlsyf.area.R;
import com.utils.ThemeUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * <br>创建者：ldh 
 * <br>时间：2015年4月27日 上午10:35:01 
 * <br>注释：欢迎界面
 */
public class WellComeActivity extends BasicActivity    {
	private int TIME = 200;
	String TAG = WellComeActivity.class.getSimpleName();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.wellcome_ui);
//		PermissionCheckUtils.startDevelopmentActivity(this);
//        if (GlobalConstants.getInstance().getAppType()==GlobalConstants.TYPE_SYSTEM_APP){
//			gotoMainOrloginUI(this);
//			return;
//		}
		initUIView();

		ThreadPoolUtils.execute(new Runnable() {
			@Override
			public void run() {

				int type= ThemeUtils.getStoreTheme(WellComeActivity.this);
				WidgetConfig widgetConfig;

				if (type==0||type==R.style.theme_light){

					widgetConfig= ThemeUtils.getThemeConfig(WellComeActivity.this,R.style.theme_light);
					//type=R.style.theme_light;

				}else{

					widgetConfig= ThemeUtils.getThemeConfig(WellComeActivity.this,R.style.theme_dark);
					//type=R.style.theme_dark;
				}
				ThemeConfig themeConfig=new ThemeConfig();
				themeConfig.setBgColorResId(widgetConfig.getBgColor());
				themeConfig.setTitleColorResId(widgetConfig.getTextColor());
				RecycleConfig.getInstance().setThemeConfig(themeConfig);

				BusinessBroadcastUtils.USER_VALUE_LOGIN_ID = SpUtils.getString(WellComeActivity.this, BusinessBroadcastUtils.STRING_LOGIN_ID,"");
				BusinessBroadcastUtils.USER_VALUE_PWD 	   = SpUtils.getString(WellComeActivity.this, BusinessBroadcastUtils.STRING_LOGIN_USER_PWD,"");
				BusinessBroadcastUtils.USER_VALUE_USER_ID  = SpUtils.getString(WellComeActivity.this, BusinessBroadcastUtils.STRING_LOGIN_USER_ID,"");

				String ip = SpUtils.getString(WellComeActivity.this, ServerUrl.list_server_iP);
				String port = SpUtils.getString(WellComeActivity.this,ServerUrl.list_server_port);
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

	@Override
	public void initUIView() {

	}

		@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what==1){
				switchActivity(WellComeActivity.this);
			}
		};
	};

	private void switchActivity(WellComeActivity wellComeActivity) {
//		 if (CoreApplication.getInstance().isDubug){
			 gotoMainOrloginUI(wellComeActivity);
			 finish();
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
	@Override
	public void initData() {
		
	}

	@Override
	public void initListener() {
		
	}



	@Override
	public void getBroadcastReceiverMessage(String type, Object mode) {

	}
//	/**
//	 * 时间：2015-6-29 下午5:46:38
//	 * 注释：判断是进入登录界面还是主界面
//	 */
	public static void gotoMainOrloginUI(final Context context){
		Intent  homeIntent=new Intent(context,HomeActivity.class);
       context.startActivity(homeIntent);

	}
}
