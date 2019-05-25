package com.ui.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.business.BusinessBroadcastUtils;
import com.business.bean.ResponseMsgData;
import com.business.login.User;
import com.core.ServerUrl;
import com.core.utils.SpUtils;
import com.easysoft.utils.lib.http.CallBackResult;
import com.easysoft.utils.lib.http.EasyHttpCallback;
import com.easysoft.utils.lib.http.IEasyResponse;
import com.easysoft.utils.lib.http.ResponseMsg;
import com.ui.HttpService;


public class LoginPresenter {
    IlogInView ilogInView;
    HttpService service;
    public LoginPresenter(IlogInView ilogInView) {
        this.ilogInView=ilogInView;
        service=new HttpService();
    }
    public void login(final String logInId, final String pwd){

         String url =ServerUrl.baseUrl+ ServerUrl.loginUrl;
        User loginUser=new User();
        loginUser.setLoginId(logInId);
        loginUser.setPwd(pwd);
        final String json= JSON.toJSONString(loginUser);
        url=ServerUrl.getFinalUrl(url,json);

        service.request( url ,new EasyHttpCallback(new IEasyResponse() {
            @Override
            public void onFailure(CallBackResult serviceCallBack) {
                ilogInView.showToast("登录失败");
            }

            @Override
            public void onResponse(CallBackResult serviceCallBack) {
                 if (serviceCallBack.isSucess()){
                     ResponseMsg msg=   serviceCallBack.getResponseMsg();
                     ResponseMsgData  serverUserResponseMsgData=JSONObject.parseObject(msg.getMsg(), ResponseMsgData.class);
                     User  serverUser=JSONObject.parseObject(serverUserResponseMsgData.getData().toString(), User.class);
                     BusinessBroadcastUtils.USER_VALUE_LOGIN_ID =logInId ;
                     BusinessBroadcastUtils.USER_VALUE_PWD 	   =pwd;
                     BusinessBroadcastUtils.USER_VALUE_USER_ID  =serverUser.getId() ;
                     BusinessBroadcastUtils.loginUser=serverUser;

                     SpUtils.putString(ilogInView.getContext(),BusinessBroadcastUtils.STRING_LOGIN_USER_ID,serverUser.getId());
                     SpUtils.putString(ilogInView.getContext(),BusinessBroadcastUtils.STRING_LOGIN_USER_PWD,serverUser.getPwd());
                     SpUtils.putString(ilogInView.getContext(),BusinessBroadcastUtils.STRING_LOGIN_ID,serverUser.getLoginId());
                     BusinessBroadcastUtils.sendBroadcast(ilogInView.getContext(),BusinessBroadcastUtils.TYPE_LOGIN_SUCESS,null);
                     ilogInView.loginSucess();
                 }else{
                     ilogInView.loginFails();
                     BusinessBroadcastUtils.sendBroadcast(ilogInView.getContext(),BusinessBroadcastUtils.TYPE_LOGIN_FAILS,null);

                 }

            }
        }));

    }
}
