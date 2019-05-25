/**
 * 
 */
package com.core.db.manger;

import java.io.Serializable;


/**
 * 创建者：qjt
 * 修改时间：2015-5-21 下午1:38:11
 * 作用：所有消息类型的基类
 */

public class MessageBaseEntity implements Serializable,Cloneable{

	private static final long serialVersionUID = 7823899870300079759L;
	/** * 消息来源者的id*/
	private String userId = "";
	/** * 消息来源者的名称 */
	private String userName = "";
	/** * 消息来源者的头像 */
	private String userAvatar;
	/** * 消息内容 */
	private String messageContent;
	/** * 消息发送的时间 */
	private long time;
	/** * 消息的类型 */
	private MESSAGE_TYPE messageType;
	/** * 聊天对象类型 */
	private CHAT_OBJECT_TYPE chatObjectType;
	
	/** 消息来源类型 接收或是发送 */
	private MESSAGE_SOURCE_TYPE mSourceType;
	private String targetName = "";
	
	/**
	* <br>创建者：qjt
	* <br>时间：2015-5-21 下午3:30:30
	* @return 聊天对象类型(系统提示消息,陌生人消息,个人聊天消息,群组聊天消息)
	 */
	public CHAT_OBJECT_TYPE getChatObjectType() {
		return chatObjectType;
	}

   /**
	* <br>创建者：qjt
	* <br>时间：2015-5-21 下午3:31:36
	* <br>注释： chatObjectType 聊天对象类型(系统提示消息,陌生人消息,个人聊天消息,群组聊天消息)
	* @param chatObjectType
	 */
	public void setChatObjectType(CHAT_OBJECT_TYPE chatObjectType) {
		this.chatObjectType = chatObjectType;
	}
	
	/**
	* <br>创建者：qjt
	* <br>时间：2015-5-21 下午2:17:28
	* @return 消息来源者的id
	 */
	public String getUserId() {
		return userId;
	}


	/**
	* <br>创建者：qjt
	* <br>时间：2015-5-21 下午2:21:57
	* <br>注释： userId 为 消息来源者的id
	* @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}


	/**
	* <br>创建者：qjt
	* <br>时间：2015-5-21 下午2:17:51
	* @return 消息来源者的名称
	 */
	public String getUserName() {
		return userName;
	}


	/**
	* <br>创建者：qjt
	* <br>时间：2015-5-21 下午2:22:27
	* <br>注释：  userName 为 消息来源者的名称
	* @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}


	/**
	* <br>创建者：qjt
	* <br>时间：2015-5-21 下午2:19:19
	* @return 消息来源者的头像
	 */
	public String getUserAvatar() {
		return userAvatar;
	}

	/**
	* <br>创建者：qjt
	* <br>时间：2015-5-21 下午2:22:59
	* <br>注释：userAvatar 为 消息来源者的头像
	* @param userAvatar
	 */
	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	/**
	* <br>创建者：qjt
	* <br>时间：2015-5-21 下午2:19:19
	* @return 消息内容
	 */
	public String getMessageContent() {
		return messageContent;
	}

	/**
	* <br>创建者：qjt
	* <br>时间：2015-5-21 下午2:23:19
	* <br>注释： messageContent 为 消息内容
	* @param messageContent
	 */
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	/**
	* <br>创建者：qjt
	* <br>时间：2015-5-21 下午2:19:19
	* @return 消息发送的时间
	 */
	public long getTime() {
		return time;
	}

	/**
	* <br>创建者：qjt
	* <br>时间：2015-5-21 下午2:23:19
	* <br>注释：time 为 消息发送的时间
	* @param time
	 */
	public void setTime(long time) {
		this.time = time;
	}

	public MESSAGE_TYPE getMessageType() {
		return messageType;
	}


	public void setMessageType(MESSAGE_TYPE messageType) {
		this.messageType = messageType;
	}


	public enum MESSAGE_TYPE{
		/**更新通知类型**/    
		UPDATE_NOTICE(0),
		/**系统通知类型(提示信息（例如:你邀请了xx加入群聊）)**/    
		SYSTEM(1),  
		/**文本,表情类型消息**/ 
		TEXT(2),
		/**图片类型消息**/ 	  
		PICTRUE(3),
		/**语音类型消息**/ 	  
		VOICE(4),
		/**地理位置类型消息**/ 
		LOCATIONMAP(5),
		/**文件(file)类型消息**/    
		FILE(6),
		/**名片类型消息**/    
		CARD(7),
		/**video录制小视频**/    
		VIDEO(8),
		/** 窗口震动 */
		MsgMediaTypeScreenShock(9),
		/**消息撤回功能*/
		DELETE_CHAT_MESSAGE_BACK(10),
		/**视频聊天*/
		VIDEO_CHAT(11),
		/**文字图片混排内含链接*/
		RICH_TEXT_lINK(12);

		private int    intkey;	
		MESSAGE_TYPE(int intkey) {  
			this.intkey = intkey;  
		} 
		public int getValue() {  
			return intkey;  
		}
	}
	
	/**
	* <br>创建者：qjt
	* <br>时间：2015-5-21 下午5:37:54
	* <br>注释：获取消息来源类型(接收或是发送)
	* @return
	 */
	public MESSAGE_SOURCE_TYPE getmSourceType() {
		return mSourceType;
	}

	/**
	* <br>创建者：qjt
	* <br>时间：2015-5-21 下午5:37:59
	* <br>注释：mSourceType 为消息来源类型(接收或是发送)
	* @param mSourceType
	 */
	public void setmSourceType(MESSAGE_SOURCE_TYPE mSourceType) {
		this.mSourceType = mSourceType;
	}
	
   /**
	* <br>创建者：qjt
	* <br>修改时间：2015-5-21 下午3:04:37
	* <br>作用：聊天对象类型
	* 0：系统提示消息,陌生人消息,个人聊天消息,群组聊天消息)
	* 
	*/
	public enum CHAT_OBJECT_TYPE{
		/**系统提示消息**/ SYSTEM_NOTICE    ("system_notice"),
		/**陌生人消息**/  STRANGER_NOTICE   ("stranger_notice"),
		/**个人聊天消息**/ PERSONAL_NOTICE   ("personal_notice"),
		/**群组聊天消息**/ GROUP_NOTICE      ("group_notice"),
		/**工作提醒消息**/ APPREMIND      ("appremind_notice");

		private String strkey;
		CHAT_OBJECT_TYPE(String strkey){
			this.strkey = strkey;
		}
		public String getValue() {  
	         return strkey;  
	     } 
	}
	/**
	 * <br>创建者：zw
	 * <br>修改时间：2015-6-4 下午1:41:50
	 * <br>作用：跳转到chat界面时，intent传递的参数
	 */
	public enum CHAT_DATA_TYPE{
		/**对象id*/
		 TARGE_ID					("targeId"),
		 /**对象名称*/
		 TARGE_NAME					("userName"),
		 /**对象头像*/
		 TARGE_AVATAR				("avatar"),
		 /**消息主体内容*/
		 TARGE_CONTENT				("content"),
		 /**data字段*/
		 DATA						("data"),
		 /**对象类型*/
	     TYPE                        ("type"),
	     /**对象来源*/
		 SOURCE                      ("source"),
		 /**当前对象进入时未读消息数量*/
		 NUMBER                     ("number"),
		 /**当前对象进入后剩余未读消息数量*/
		 UNREAD_UNMBER				("unreadNumber");
	     private String strkey; //支持String类型的数据
	     
	     CHAT_DATA_TYPE(String strkey) {  
	         this.strkey = strkey;  
	     }  
	     
	     public String getValue() {  
	         return strkey;  
	     } 
	}
	/**
	 * <br>创建者：qjt 
	 * <br>修改时间：2015-4-30 上午10:53:35
	 * <br> 作用：消息来源类型 接收或是发送
	 * <br> 0:发送  
	 * <br> 1:接收
	 */
	public enum MESSAGE_SOURCE_TYPE {
		 SEND(0),RECEIVER(1);

		private int intkey;

		MESSAGE_SOURCE_TYPE(int intkey) {  
			this.intkey = intkey;  
		}  

		public int getValue() {  
			return intkey;  
		} 
	}
	
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
