package com.core.db.manger;
import android.util.Log;

/**
 * 创建者：zw
 * 修改时间�?015-2-5 上午10:45:22
 * 作用�?
 */
public class DebugUtil {
	public static boolean isDebug = true;
	
	public static void setErrorLog(String tag,String LogMsg){
		if(isDebug){
			Log.e(tag, LogMsg);
		}
	}
	
	public static void setLog(String tag,String LogMsg){
		if(isDebug){
			Log.d(tag, LogMsg);
		}
	}
	
	public static void setInfoLog(String tag,String LogMsg){
		if(isDebug){
			Log.i(tag, LogMsg);
		}
	}
	
}
