
package com.business;

import android.content.Context;

import com.business.login.User;
import com.core.base.BaseUiInterface;
import com.core.base.GlobalConstants;
import com.core.utils.BroadcastUtils;


/**
 * <br>创建者：ldh
 * <br>修改时间：2015-5-25 下午6:02:11
 * <br>作用：消息广播与通讯协议，常量定义的类
 */
public class BusinessBroadcastUtils {
	
	/**当前为最新版本，不需要更新*/
	public final static String IS_NEW_VERSION			= "is_new_version";
	/**有最新版本，需要更新*/
	public final static String HAS_NEW_VERSION			= "has_new_version";
	public static final String HTTP_ERROR ="http_error" ;
	public static final String TYPE_CHANGE_THEME_WB ="TYPE_CHANGE_THEME_WB" ;
	public static String TYPE_LOCAL_REGISTER="local_register";
	public static String TYPE_LOCAL_REGISTER_SUCCESS_AUTOLOGIN="register_sucess_autologin";
    /**登录id*/
	public static String USER_VALUE_LOGIN_ID;
	public static String USER_VALUE_PWD;
	/**用户id*/
	public static String USER_VALUE_USER_ID;
	public static String STRING_LOGIN_USER_ID="login_user_id";
	public static String STRING_LOGIN_USER_PWD="login_user_pwd";
	public static String STRING_LOGIN_ID="login_login_id";
	public static User loginUser;
    public static String TYPE_RESTART_ACTIVITY="TYPE_RESTART_ACTIVITY";
    public static String TYPE_CHANGE_THEME_RESTART_ACTIVITY="TYPE_CHANGE_THEME_RESTART_ACTIVITY";

    /**
	 * <br>创建者：ldh
	 * <br>时间：2015-6-11 下午5:30:06
	 * <br>注释： 发送广播给UI层
	 * @param context 上下文
	 * @param type 消息类型
	 * @param obj 传输对象
	 */
	public static void sendBroadcast(Context context,String type,Object obj){
		BroadcastUtils.sendBroadCast(context, GlobalConstants.getInstance().getBroadCastReceiverActionName(), 
								BaseUiInterface.MSG_TYPE, BaseUiInterface.MSG_MODE, type, obj);
	}
	/**获取订单列表成功*/
	public static String  TYPE_SHOPCAR_LIST="com.ui.car.TabFragment4.list";
	/**添加订单成功*/
	public static String  TYPE_GOODS_ADD_SUCESS="goodsAddSucess";
//	/**登录成功*/
//	public static String  TYPE_RELOGIN_SUCESS="TestActivity.relogin.sucess";
	/**登录失败*/
//	public static String  TYPE_RELOGIN_FAILS="TestActivity.relogin.fails";
	/**重新登录失败*/
	public static final String TYPE_RELOGIN ="home_relogin" ;
	public static final String TYPE_LOGIN_SUCESS ="loginSucess" ;
	public static final String TYPE_LOGIN_FAILS ="loginFails" ;


	/**跳转viewpage 主界面*/
	public static String  Type_Local_HOME_PAGE_CHANGE="Type_Local_HOME_PAGE_CHANGE";

	/**获取订单列表成功*/
	public static String  TYPE_REFRESH_VIDEO="TYPE_REFRESH_VIDEO";
	public static String  TYPE_REFRESH_VIDEO_HIDE="TYPE_REFRESH_VIDEO_HIDE";

	public static String  TYPE_APP_EXIT="TYPE_APP_EXIT";

	//粤语相关
	public static final String TYPE_YY_REFRESH_HOME_COUNT ="TYPE_YY_REFRESH_HOME_COUNT" ;

    public static final String TYPE_CHANGE_THEME ="TYPE_CHANGE_THEME";
}