package com.core.db.manger;


/**
 * 创建者：zw
 * 修改时间：2015-4-23 下午4:03:02
 * 注释：业务层创建IM数据表
 */
public class CreatTablesUtil {
//	
//	/**
//	 * <br>创建者：zw 
//	 * <br>时间：2015-4-27 下午6:28:00
//	 * <br>注释：创建聊天记录数据表
//	 * 
//	 * <br>通过用户的ID，或者群组的ID作为数据库表名的一部分。 创建聊天消息记录的表
//	 * <br>例如：IM的app定义聊天的表名： Chat+useid的MD5值或者群组ID的MD5值 
//	 * @param UseridOrGroupId
//	 * @return true 为建表成功
//	 */
//	public static boolean creatChatTable(String UserIdOrGroupId)
//	{
//		return DbTableUtil.createTable(Chat.class,TablePrimaryKeyEnum.TABLE_CHAT.getValue(),true,true,UserIdOrGroupId);
//	}
//	
//	/**
//	 * <br>创建者：zw
//	 * <br>时间：2015-4-30 下午3:24:52
//	 * <br>注释：创建 人员表
//	 * @return true 为建表成功
//	 */
//	public static boolean creatColleague(){
//		return DbTableUtil.createTable(Colleague.class, TablePrimaryKeyEnum.TABLE_COLLEAGUE.getValue(), false, true);
//	}
//	
//	/**
//	 * <br>创建者：zw
//	 * <br>时间：2015-4-30 下午2:23:58
//	 * <br>注释：创建 联系人关系表
//	 * @return true 为建表成功
//	 */
//	public static boolean creatRelationColleagueTable(){
//		return DbTableUtil.createTable(RelationColleague.class,TablePrimaryKeyEnum.TABLE_RELATION_COLLEAGUE.getValue(),false,true);
//	}
//	
//   /**
//	* <br>创建者：qjt
//	* <br>时间：2015-4-30 下午3:22:39
//	* <br>注释：创建聊天设置表  
//	* @return true 为建表成功
//	*/
//	public static boolean creatChatSettingTable(){
//		return DbTableUtil.createTable(ChatSetting.class, TablePrimaryKeyEnum.TABLE_CHAT_SETTING.getValue(), false, true);
//	}
//	
//   /**
//	* <br>创建者：qjt
//	* <br>时间：2015-4-30 下午3:23:58
//	* <br>注释：创建群组表 true 
//	* @return true 为建表成功
//	*/
//	public static boolean creatChatGroupTable(){
//		return DbTableUtil.createTable(ChatGroup.class, TablePrimaryKeyEnum.TABLE_GROUP.getValue(), false, true);
//	}
//	
//   /** 
//	* <br>创建者：qjt
//	* <br>时间：2015-4-30 下午3:25:47
//	* <br>注释：创建表 收到的其他同事邀请或添加其他同事表
//	* @return true 为建表成功
//	*/
//	public static boolean creatHelloColleagueTable(){
//		return DbTableUtil.createTable(HelloColleague.class, TablePrimaryKeyEnum.TABLE_HELLO_COLLEAGUE.getValue(), false, true);
//	}
//	
//   /**
//	* <br>创建者：qjt
//	* <br>时间：2015-4-30 下午3:27:36
//	* <br>注释：创建最后通讯的消息列表
//	* @return true 为建表成功
//	*/
//	public static boolean creatSessionLastTable(){
//		return DbTableUtil.createTable(SessionLast.class, TablePrimaryKeyEnum.TABLE_SESSION_LAST.getValue(), false, true);
//	}
//	
//   /**
//	* <br>创建者：qjt
//	* <br>时间：2015-4-30 下午3:38:56
//	* <br>注释：创建企业关系表
//	* @return true 为建表成功
//	*/
//	public static boolean creatRelationCorpTable(){
//		return DbTableUtil.createTable(RelationCorp.class,TablePrimaryKeyEnum.TABLE_RELATION_CORP.getValue(), false, true);
//	}
//	/**
//	 * <br>创建者：zw
//	 * <br>时间：2015-4-30 下午3:11:28
//	 * <br>注释：创建 企业信息表 		
//	 * @return true 为建表成功
//	 */
//	public static boolean creatCorporationTable(){
//		
//		return DbTableUtil.createTable(Corporation.class,TablePrimaryKeyEnum.TABLE_CORPORATION.getValue(),false,true);
//	}
//	/**
//	 * <br>创建者：zw
//	 * <br>时间：2015-4-30 下午3:23:49
//	 * <br>注释：创建 企业部门信息表 
//	 * @return true 为建表成功
//	 */
//	public static boolean creatDepartmentTable(){
//		
//		return DbTableUtil.createTable(Department.class,TablePrimaryKeyEnum.TABLE_DEPARTMENT.getValue(),false,true);
//	}
//	/**
//	 * <br>创建者：zw
//	 * <br>时间：2015-4-30 下午4:28:05
//	 * <br>注释：创建 部门人员关系表 (这个表没有)
//	 * @return true 为建表成功
//	 */
//	public static boolean creatDepartmentColleagueTable(){
//		
//		return DbTableUtil.createTable(DepartmentColleague.class,TablePrimaryKeyEnum.TABLE_DEPARTMENT_COLLEAGUE.getValue(),false,true);
//	}
//	/**
//	 * <br>创建者：zw
//	 * <br>时间：2015-6-1 下午6:33:54
//	 * <br>注释：创建 文件资源表
//	 * @return 是否建表成功
//	 */
//	public static boolean creatFileListTable(){
//		
//		return DbTableUtil.createTable(FileList.class,TablePrimaryKeyEnum.TABLE_FILELIST.getValue(),false,true);
//	}
//	/**
//	 * <br>创建者：zw
//	 * <br>时间：2015-6-18 下午2:23:50
//	 * <br>注释：消息发送缓存表
//	 * @return
//	 */
//	public static boolean creatSendMsgTable(){
//		
//		return DbTableUtil.createTable(SendMsg.class,TablePrimaryKeyEnum.TABLE_SEND_MESSAGE.getValue(),false,true);
//	}
//	/**
//	 * 业务消息补发缓存表
//	 * @return
//	 */
//	public static boolean creatSendSocketMsgTable(){
//		
//		return DbTableUtil.createTable(SendSocketMsg.class,TablePrimaryKeyEnum.TABLE_SEND_SOCKET_MESSAGE.getValue(),false,true);
//	}
//	/**
//	 * 应用工作提醒表 
//	 * @return
//	 */
//	public static boolean creatAppRemindListTable(){
//		
//		return DbTableUtil.createTable(AppRemindList.class,TablePrimaryKeyEnum.TABLE_APP_REMIND_LIST.getValue(),false,true);
//	}
//	/**
//	 * 文件上传下载任务表
//	 * @return
//	 */
//	public static boolean creatFileTaskListTable(){
//		
//		return DbTableUtil.createTable(FileTaskList.class,TablePrimaryKeyEnum.TABLE_FILE_TASK_LIST.getValue(),false,true);
//	}
//	
//	/**
//	 * 应用资源文件映射表
//	 * @return
//	 */
//	public static boolean creatAppResFileMappingTable(){
//		
//		return DbTableUtil.createTable(AppResFileMapping.class,TablePrimaryKeyEnum.TABLE_AppResFileMapping.getValue(),false,true);
//	}
//	/**
//	 * 版本号表
//	 * @return
//	 */
//	public static boolean creatVersionTable(){
//		return DbTableUtil.createTable(DatabaseVersion.class, TablePrimaryKeyEnum.TABLE_VERSION_NO.getValue(), true, true);
//	}
}
