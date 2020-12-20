package com.core.base;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;

import com.easysoft.utils.lib.system.ToastUtils;

@SuppressLint("HandlerLeak")
public abstract class BaseUIFragement  extends  BaseFragment{

	public ProgressDialog dialog = null;
//	private ProgressHUD authProGressHud = null;
	static final int LOADING_THREAD_NOTIFIER_UPDATE_UI = 0x109;
	static final int LOADING_THREAD_NOTIFIER_ERROR_RESPONSE = 0x110;
	public FragmentActivity activity;
	MYBroadcastReceiver receiver;

	public boolean blHandleProcessEnd = true;

	public boolean isSendCommentData = false; // 用于DetailsPadView作为区别标识

	public boolean isExtractFileData = false; // //用于DetailsPadView作为区别标识

	public boolean isshowdialog = true;
	public myHandler mHandler;
	public static String ISMenuActivty = "isMenuActivity";
	public static String fromSubActivity = "fromSubActivity";
	public static String SubActivityrequestCode = "requestCode";
	public class myHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			
			ToastUtils.show(getActivity(), "vpn通道未建立，请先登录移动平台");
		}

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		receiver = new MYBroadcastReceiver();
		activity = getActivity();
		
//		IntentFilter filter = new IntentFilter(PublicVariable.ACTIVITY_RESULTCODE_999_RETURNLOGINVIEW);
		IntentFilter filter = new IntentFilter();

		filter.addAction(PublicVariable.ACTIVITY_RESULTCODE_999_RETURNLOGINVIEW);

		filter.addAction(GlobalConstants.getInstance().getBroadCastReceiverActionName());

		getActivity().registerReceiver(receiver, filter);
		return super.onCreateView(inflater, container, savedInstanceState);

	}
	class MYBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(PublicVariable.ACTIVITY_RESULTCODE_999_RETURNLOGINVIEW)) {

//				activity.finishdefault();

			}
			else if(action.equals(GlobalConstants.getInstance().getBroadCastReceiverActionName())){
				String type = intent.getStringExtra(MSG_TYPE);
				Object mode = intent.getSerializableExtra(MSG_MODE);
				if(type!=null){getBroadcastReceiverMessage(type,mode);}
				//DebugUtil.setLog(BROAD_CAST_RECEIVER_ACTION_NAME, "收到广播消息");
			}
		}
	}


	
	public abstract void ResponseSuccessProcess();



	@Override
	public void getBroadcastReceiverMessage(String type, Object mode) {
	}

}
