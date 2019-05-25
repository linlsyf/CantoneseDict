package com.ui;


import com.easysoft.utils.lib.http.EasyHttpCallback;
import com.easysoft.utils.lib.http.EasyHttpUtils;

public class HttpService {
//	ServiceCallBack serviceCallBack=new ServiceCallBack();

	public void request(final String url ,EasyHttpCallback callback){

		//HttpUtils.getInStance().post(url,  callback);
		EasyHttpUtils.getInStance().post(url,callback);
		
	}

//	public ServiceCallBack getServiceCallBack() {
//		return serviceCallBack;
//	}
}
