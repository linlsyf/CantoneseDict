package com.core.base;

import android.view.View;

/**
 * 创建者：zw
 * 修改时间：2015-5-20 下午3:24:33
 * 作用：
 */
public interface BaseUiInterface {
	/**参数定义：socket收到消息，发送广播一个消息类型给UI*/
	public final static String MSG_TYPE    ="msg_type";
	/**参数定义：socket收到消息，发送广播一个mode数据结构给UI*/
	public final static String MSG_MODE    ="msg_mode";
	
	public static String BROAD_CAST_RECEIVER_ACTION_NAME = "com.base.broadCastReceiver";
	
	String BROAD_CAST_RECEIVER_MESSAGE_PROGRESS = "com.miracle.broadCastReceiver";
	
	void initUIView();
	void initData();
	void initListener();
	

	/**该接口用于回调广播消息*/
	void getBroadcastReceiverMessage(String type, Object mode);
	
}
