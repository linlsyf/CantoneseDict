package com.ui.common.register;

import com.alibaba.fastjson.JSON;
import com.business.login.User;
import com.core.ServerUrl;
import com.easysoft.utils.lib.http.CallBackResult;
import com.easysoft.utils.lib.http.EasyHttpCallback;
import com.easysoft.utils.lib.http.IEasyResponse;
import com.ui.HttpService;

//import cn.smssdk.EventHandler;
//import cn.smssdk.SMSSDK;


public class   RegisterPersenter{
     IregisterView iregisterView;
     HttpService service;
     private String registerUserName;
     private String registerPwd;
     private String registerPhone;

     public RegisterPersenter(IregisterView iregisterView) {
          this.iregisterView=iregisterView;
          service=new HttpService();
     }


     public void register(String phone,String code,String userName,String pwd){
           registerPhone=phone;
           registerUserName=userName;
            registerPwd=pwd;
//          registerServerUser();
          // 注册一个事件回调，用于处理提交验证码操作的结果
//          SMSSDK.registerEventHandler(new EventHandler() {
//               public void afterEvent(int event, int result, Object data) {
//                    if (result == SMSSDK.RESULT_COMPLETE) {
//                         // TODO 处理验证成功的结果
//
//                         registerServerUser();
//
//
//                    } else{
//                         iregisterView.showToast("验证码错误，注册失败");
//                    }
//
//               }
//          });
//          // 触发操作
//          SMSSDK.submitVerificationCode("86", phone, code);
     }

     private void registerServerUser() {
           String url = ServerUrl.baseUrl+ ServerUrl.REGISTER_UserUrl;
          User loginUser=new User();
          loginUser.setLoginId(registerPhone);
          loginUser.setName(registerUserName);
          loginUser.setPwd(registerPwd);
          final String json= JSON.toJSONString(loginUser);
          url=ServerUrl.getFinalUrl(url,json);

          service.request( url , new EasyHttpCallback(new IEasyResponse() {
               @Override
               public void onFailure(CallBackResult serviceCallBack) {
                    iregisterView.showToast("连接服务器错误，注册失败");
               }

               @Override
               public void onResponse(CallBackResult serviceCallBack) {
                    if (serviceCallBack.isSucess()){
//                         ResponseMsg msg=   serviceCallBack.getResponseMsg();
                         iregisterView.register(true);
                    }else{
                         iregisterView.showToast("连接服务器错误，注册失败");
                    }

//                ilogInView.showToast("登录成功");
               }
          }));
     }

     // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
     public void sendCode( String phone) {
          String country="86";
          // 注册一个事件回调，用于处理发送验证码操作的结果
//          SMSSDK.registerEventHandler(new EventHandler() {
//               public void afterEvent(int event, int result, Object data) {
//                    if (result == SMSSDK.RESULT_COMPLETE) {
//                         // TODO 处理成功得到验证码的结果
//                         // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
//                    } else{
//                         // TODO 处理错误的结果
//                    }
//
//               }
//          });
//          // 触发操作
//          SMSSDK.getVerificationCode(country, phone);
     }


}
