package com.ui;


import android.content.Context;

import com.easysoft.utils.lib.http.EasyHttpCallback;
import com.easysoft.utils.lib.http.EasyHttpUtils;
import com.easysoft.utils.lib.http.IEasyResponse;

public class HttpService {
//	ServiceCallBack serviceCallBack=new ServiceCallBack();

	public void request(Context context,final String url , EasyHttpCallback callback){

		//HttpUtils.getInStance().post(url,  callback);
		EasyHttpUtils.getInStance(context).post(url,callback);
		
	}
	public void request(Context context,final String url , IEasyResponse callback){

		//HttpUtils.getInStance().post(url,  callback);
		EasyHttpUtils.getInStance(context).post(url,callback);

	}

//	public ServiceCallBack getServiceCallBack() {
//		return serviceCallBack;
//	}
}
