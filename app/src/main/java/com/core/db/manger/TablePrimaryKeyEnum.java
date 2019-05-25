package com.core.db.manger;

/**
 * <br>创建者：zw
 * <br>修改时间：2015-5-21 下午1:59:01
 * <br>注释：数据表的主键枚举类型
 */
public enum TablePrimaryKeyEnum {
	 /**chat聊天表主键*/
	 TABLE_CHAT					("mesLocalID"),
	 /**聊天设置表主键*/
	 TABLE_CHAT_SETTING			("chatId"),
	 /**人员表 主键*/
	 TABLE_COLLEAGUE			("userId"),
	 /**企业信息表主键*/
	 TABLE_CORPORATION			("corpId"),
	 /**企业部门信息表主键*/
	 TABLE_DEPARTMENT			("departmentId"),
	 /**企业部门-人员关系表主键*/
	 TABLE_DEPARTMENT_COLLEAGUE	("autoid"),
	 /**群组表主键*/
	 TABLE_GROUP				("groupId"),
	 /**收到的其他同事邀请或添加其他同事的表主键*/
	 TABLE_HELLO_COLLEAGUE		("autoid"),
	 /** 联系人关系表主键*/
	 TABLE_RELATION_COLLEAGUE	("userId"),
	 /** 企业关系表主键*/
	 TABLE_RELATION_CORP		("corpId"),
	 /** 最后通讯的消息列表主键*/
	 TABLE_SESSION_LAST			("chatId"),
	 /**  文件资源表*/
	 TABLE_FILELIST				("attachId"),
	 /**  消息发送缓存表 */
	 TABLE_SEND_MESSAGE			("mesSvrId"),
	 /**  发送业务消息缓存表 */
	 TABLE_SEND_SOCKET_MESSAGE	("serverId"),
	 
	 /**   应用工作提醒表   */
	 TABLE_APP_REMIND_LIST     	("chatId"),
	 
	 /**   文件上传下载任务表    */
	 TABLE_FILE_TASK_LIST     	("attachId"),
	 
	 /**   文件下载任务表    */
	 TABLE_AppResFileMapping    ("serverId"),
	 /**版本号表*/
	 TABLE_VERSION_NO			("versionNo");
       
     private String strkey; //支持String类型的数据
     TablePrimaryKeyEnum(String strkey) {  
         this.strkey = strkey;  
     }  
       
     public String getValue() {  
         return strkey;  
     } 

}
