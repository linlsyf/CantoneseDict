package com.core.base;

/**
 * 
 * @author {Denforth}
 *  公用变量
 */
public class PublicVariable {

	public static final CharSequence loadingDialogTitle = "请稍候";
	public static final CharSequence loadingDialogBody = "正在加载数据...";

	public static final String ITEM_DOCID = "item_DocId";// OA文档ID（公文和邮件）
	public static final String LISTVIEW_TYPE = "item_ListViewType";// 视图类型(在办列表、待办列表、已办列表、收件箱等列表)

	public static final String REGET_DOC_DATASOURCE = "reget_datasource"; // 重新向Server获取文档信息

	// Request Code
	public static final int ACTIVITY_FINISH_DOCFLOW_SIGNCODE = 9001; // 在onActivityResult中判断是否需要关闭自己Activity
	public static final int ACTIVITY_FINISH_MAILSEND_SIGNCODE = 9002;
	public static final int ACTIVITY_FINISH_DOCFLOW_NIBAN = 9003;
	public static final int ACTIVITY_requestMailcontactcode = 9004;

	// Result Code
	public static final int ACTIVITY_RESULTCODE_REFRESHALL = 8001;//
	public static final int ACTIVITY_RESULTCODE_REFRESHDOCPROPERTY = 8002;// 刷新DetaislDocPropertyView
																			// DataSource

	public static final String NEWMAIL_FROMVIEW = "newmail_from_view";// 标识是从哪里进入新建邮件页面

	// 用户没有登录系统或当前帐号已在另一地点登录，冲掉本次SessionId,需要强制用户会到登陆界面
	public static final String ACTIVITY_RESULTCODE_999_RETURNLOGINVIEW = "999";

}
