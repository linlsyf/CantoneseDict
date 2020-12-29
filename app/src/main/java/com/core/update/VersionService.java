package com.core.update;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;

import com.linlsyf.cantonese.R;


/**
 * 程序后台Service下载最新版本apk文件
 * @author zw	
 */
public class VersionService extends Service {
	public static NotificationManager notificationMrg;
	private int old_process = 0;
	private boolean isFirstStart=false;

	public void onCreate() {
		super.onCreate();
		isFirstStart=true;
		notificationMrg = (NotificationManager) this.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		mHandler.handleMessage(mHandler.obtainMessage());
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// 1为出现，2为隐藏
			if(UpdateUtil.loading_process>99){
//				if(UpdateUtil.loading_process>99||MessengerClientApplication.isNetWorkClose()){
				notificationMrg.cancel(0);
				stopSelf();
				return;
			}
			
			if(UpdateUtil.loading_process>old_process){
				displayNotificationMessage(UpdateUtil.loading_process);
			}
			
			isFirstStart=false;
			//循环的调用自己
			mHandler.sendMessage(mHandler.obtainMessage());
			old_process =UpdateUtil.loading_process;
		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		notificationMrg.cancel(0);
	}

	private void displayNotificationMessage(int count) {

		// Notification的Intent，即点击后转向的Activity
		Intent notificationIntent1 = new Intent(this, this.getClass());
		notificationIntent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent contentIntent1 = PendingIntent.getActivity(this, 0,notificationIntent1, 0);

		// 创建Notifcation,设定Notification出现时的声音，一般不建议自定义
		@SuppressWarnings("deprecation")
		Notification notification = new Notification(R.drawable.ic_launcher,"您的应用正在更新中", System.currentTimeMillis());
		if(isFirstStart || UpdateUtil.loading_process>97){
			notification.defaults |= Notification.DEFAULT_SOUND;// 设定是否振动
			notification.defaults |= Notification.DEFAULT_VIBRATE;
		}
		notification.flags |= Notification.FLAG_ONGOING_EVENT;

		// 创建RemoteViews用在Notification中
		RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification_version);
		contentView.setTextViewText(R.id.n_title,"正在为您更新");
//		contentView.setTextViewText(R.id.n_title,"正在为您更新"+MessengerClientApplication.getAppName());
		contentView.setTextViewText(R.id.n_text, "更新进度："+count+"% ");
		contentView.setProgressBar(R.id.n_progress, 100, count, false);

		notification.contentView = contentView;
		notification.contentIntent = contentIntent1;

		notificationMrg.notify(0, notification);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
