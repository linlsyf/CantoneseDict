package com.ui.common.register.check;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.business.bean.ResponseMsgData;
import com.business.login.User;
import com.core.ServerUrl;
import com.easysoft.utils.lib.http.CallBackResult;
import com.easysoft.utils.lib.http.EasyHttpCallback;
import com.easysoft.utils.lib.http.IEasyResponse;
import com.easysoft.utils.lib.http.ResponseMsg;
import com.ui.HttpService;


public class CheckUserExitPersenter {
    IcheckPhoneView icheckPhoneView;
    HttpService service;
    public CheckUserExitPersenter(IcheckPhoneView icheckPhoneView) {
        this.icheckPhoneView =icheckPhoneView;
        service=new HttpService();
    }
    public void checkPhoneExit(String logInId){
         String url = ServerUrl.baseUrl+ ServerUrl.checkUserExitUrl;
        User loginUser=new User();
        loginUser.setLoginId(logInId);
        final String json= JSON.toJSONString(loginUser);
        url=ServerUrl.getFinalUrl(url,json);

        service.request( url ,new EasyHttpCallback(new IEasyResponse() {
            @Override
            public void onFailure(CallBackResult serviceCallBack) {
                icheckPhoneView.checkPhoneUserExit(false);
            }

            @Override
            public void onResponse(CallBackResult serviceCallBack) {
                if (serviceCallBack.isSucess()){
                    ResponseMsg msg=   serviceCallBack.getResponseMsg();
                    ResponseMsgData  serverUserResponseMsgData=JSONObject.parseObject(msg.getMsg(), ResponseMsgData.class);
//                    User  serverUser=JSONObject.parseObject(msg.getMsg().toString(), User.class);
                    icheckPhoneView.checkPhoneUserExit(msg.isSuccess());
                }else{

                    icheckPhoneView.showToast("连接服务器失败");
//                    icheckPhoneView.checkPhoneUserExit(false);
                }

//                ilogInView.showToast("登录成功");
            }
        }));

    }
}
