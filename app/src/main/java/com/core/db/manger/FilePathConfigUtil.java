/**
 * 
 */
package com.core.db.manger;

import java.io.File;

import android.content.Context;
import android.os.Environment;

import com.core.db.manger.MessageBaseEntity.MESSAGE_TYPE;

/**
 * 创建者：zw
 * 修改时间：2015-6-17 下午3:13:39
 * 作用：app文件存储路径配置工具类
 */
public class FilePathConfigUtil {
	private static String Tag = FilePathConfigUtil.class.getSimpleName();
	private String filePath = Environment.getExternalStorageDirectory()+ "/";
	
	private static FilePathConfigUtil instance = null;
	private static String appName;
	public static FilePathConfigUtil getInstance(Context context){
		synchronized (Tag) {
			if(instance==null){
				instance = new FilePathConfigUtil(context);
			}
		}
		return instance;
	}
	
	
	public FilePathConfigUtil(Context context) {
		super();
//		appName = ApkUtils.getInstance(context).getApplicationName();
	}

//	public String getFilePath(String id,ChatMessageEntity entity){
//		return getFilePath(id, entity.getMessageType());
//	}
	
	public String getRootFilePath(){
		return filePath + appName + "/";
	}
	
	public String getFilePath(String id,MESSAGE_TYPE chatType){
		String fileTypeName = FileTypeName.DEFULT.getName();
		int Type =  FileTypeName.DEFULT.getkey();
		if (chatType!=null) {
			 Type = chatType.getValue();
		}
		if(Type == FileTypeName.Img_thumbnail.getkey()){
			
			fileTypeName = FileTypeName.Img_thumbnail.getName();
			
		}else if(Type == FileTypeName.Audio.getkey()){
			
			fileTypeName = FileTypeName.Audio.getName();
			
		}else if(Type == FileTypeName.Video.getkey()){
			
			fileTypeName = FileTypeName.Video.getName();
			
		}else if(Type == FileTypeName.FILE_RECV.getkey()){
			
			fileTypeName = FileTypeName.FILE_RECV.getName();
			
		}else if(Type == FileTypeName.Camera.getkey()){
			
			fileTypeName = FileTypeName.Camera.getName();
			
		}else if(Type == FileTypeName.DEFULT.getkey()){
			
			fileTypeName = FileTypeName.DEFULT.getName();
			
		}else if(Type == FileTypeName.Temp.getkey()){
			
			fileTypeName = FileTypeName.Temp.getName();
			
		}else if(Type == FileTypeName.WebRes.getkey()){
			
			fileTypeName = FileTypeName.WebRes.getName();
			
		}
		
		String driPath = filePath+appName+"/"+MD5StringUtil.stringToMD5(id)+"/"+fileTypeName;
		
		creatFileIfIsNoExists(driPath);
		
		return driPath;
	}
	
	public String getFilePath(String id,String fileType){
		String driPath = filePath+appName+"/"+MD5StringUtil.stringToMD5(id)+"/"+fileType;
		creatFileIfIsNoExists(driPath);
		return driPath;
	}
	
	
	public String getFilePath(String id,FileTypeName fileType){
		String driPath = filePath+appName+"/"+MD5StringUtil.stringToMD5(id)+"/"+fileType.getName();
		creatFileIfIsNoExists(driPath);
		return driPath;
	}
	
	private void creatFileIfIsNoExists(String driPath){
		File driFile = new File(driPath);
		if(!driFile.exists()){
			driFile.mkdirs();
		}
	}
	
	/**
	 * 创建者：zw<br>
	 * 修改时间：2015-6-27 下午3:29:07<br>
	 * 作用：app文件夹名称，枚举<br>
	 * 注意：Img对应的文件夹是：Img_thumbnail，Img_original对应的文件夹是：Img
	 */
	public enum FileTypeName{
		 
		 WebRes       (1, "WebRes"),
		 Camera		  (2, "Camera"),
		 Img_thumbnail(3, "Img_thumbnail"),
		 Audio		  (4, "Audio"),
		 FILE_RECV    (6, "FileRecv"),
		 Video		  (8, "VideoDB"),
		 DB			  (12,"DB"),
		 Img_original (13,"Img"),
		 Temp		  (0, "Temp"),
		 SAVE_PHOTO   (14,"save_photo"),
		 appRes		  (15,"AppRes"),
		 Archive      (16, "Archive"),
		 DEFULT    	  (-1,"defult");
	      
		 private int    key;
	     private String name;
	     
	     FileTypeName(int key,String value) {  
	    	 this.key   = key;
	         this.name = value;  
	     }  
	       
	     public int getkey() {  
	         return key;  
	     } 
	     
	     public String getName() {  
	         return name;  
	     } 
	}
}
