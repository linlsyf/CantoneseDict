package com.core.base;

import java.io.File;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.core.db.greenDao.gen.DaoSession;
import com.easysoft.utils.lib.system.FileUtils;

/**
 
 * 全局变量
 * 
 */
public class GlobalConstants {

	private DaoSession mDaoSession;

	/**临时文件夹名字，不同activity的保存路径可能不同须知*/
	public static   String temp="temp";
	public UserSession userSession = new UserSession();
	private boolean isPadDevice = false;
	DisplayMetrics AppWindowDisplayMetrics = null;
	String appDocumentHomePath = "", mmaServerIP = "";

	public static int TYPE_SYSTEM_APP=2;
	public static int TYPE_SHOP_APP=1;

	public int getAppType() {
		return appType;
	}

	public void setAppType(int appType) {
		this.appType = appType;
	}

	int  appType=2;
	int mmaServerPort = 9000;
	// VPN
	String mmaServerVPNIP = "114.251.218.39", mmaServerVPNNAME = "ydbg-ctg",
			mmaServerVPNPWD = "miracleS2#@!";

	int mmaServerVPNPort = 443;

	String connMMAServeUrl, connMMAServerResUrl, postResourceUrl;
///**应用图标*/
//	public int appIcon;
	private static GlobalConstants instance;
	/**当前登录名*/
	private  String loginUseName;
	/**当前登录密码*/
	private  String loginPwd;

	Context applicationContext = null;
	public  String sessionId = "";

	/**用于标识是当前应用 当前局标识 程序发送的广播*/
	private  String BROAD_CAST_RECEIVER_ACTION_NAME = "com.linlsyf.area";
	int listRowTextColor, listRowSubTextColor, listColumnLineColor;

	String   deviceId;
	// String sdkVersion;
	int intSDKVersion;
	float devDensityVal;

	boolean externalAvaliable = false, externalWritable = false;

	private boolean isPad;
	private boolean misbigicon = true;
	private String imei;
	
	private boolean isInActionRequestProcess = false;
//	/**用于记录设置的信息以及登录信息*/
//	private SharedPreferences share;

	String APPHomeDocFolder = "EasySoft";
	String APPTempDocFolder = "temp";
	/**是否显示双列*/
	public  boolean istabandTwoClomnstyle = false;
	/**本机电话号码*/
	public  String phoneNumber = "";
	public  String phoneModel = "";
	
	/**IMEI 作为 Device Id*/
	public  String phoneDeviceId = "";

	/**系统版本号*/
	public  String osVersion = "2.1";
	public String addressBookTreeTypeString = "";
	public String strAPKVersion = "";
	/**每页显示数目*/
	public  int perPageRecSize = 20;// 8;


	public static String getTemp() {
		return temp;
	}

	public static void setTemp(String temp) {
		GlobalConstants.temp = temp;
	}

    public void setDaoSession(DaoSession daoSession) {
        this.mDaoSession = daoSession;
    }

	public DaoSession getDaoSession() {
		return mDaoSession;
	}

    public class UserSession {
		String seesionId = "";

		public String getSeesionId() {
			return seesionId;
		}

		public void setSeesionId(String seesionId) {
			this.seesionId = seesionId;
		}
	}



	public static GlobalConstants getInstance() {
		if (instance == null) {

			instance = new GlobalConstants();

		}

		return instance;
	}


	public void setApplicationContext(Context context) {
		this.applicationContext = context;
	}

	public Context getApplicationContext() {
		return applicationContext;
	}
	public String getBroadCastReceiverActionName() {
		return BROAD_CAST_RECEIVER_ACTION_NAME;
	}
	
	/**
	 * 
	 * <br>创建者：ldh
	 * <br>修改时间：2015年10月21日 下午1:43:46 
	 * <br>注释：设置广播名字
	 * @param bROAD_CAST_RECEIVER_ACTION_NAME
	 */
	public void setBroadCastReceiverActionName(String bROAD_CAST_RECEIVER_ACTION_NAME) {
		BROAD_CAST_RECEIVER_ACTION_NAME = bROAD_CAST_RECEIVER_ACTION_NAME;
	}


	
	/**
	 * @return the postResourceUrl
	 */
	public String getPostResourceUrl() {
		return postResourceUrl;
	}

	/**
	 * @param postResourceUrl
	 *            the postResourceUrl to set
	 */
	public void setPostResourceUrl(String postResourceUrl) {
		this.postResourceUrl = postResourceUrl;
	}

	/**
	 * @return the aPPTempDocFolder
	 */
	public String getAPPTempDocFolder() {
		return APPTempDocFolder;
	}

	

	public String getMmaServerIP() {
		return mmaServerIP;
	}

	public int getMmaServerPort() {
		return mmaServerPort;
	}

	public String getMmaServerVPNIP() {
		return mmaServerVPNIP;
	}

	public String getMmaServerVPNNAME() {
		return mmaServerVPNNAME;
	}

	public String getMmaServerVPNPWD() {
		return mmaServerVPNPWD;
	}

	public int getMmaServerVPNPort() {
		return mmaServerVPNPort;
	}


	public String reqDataEncode(String plainText) {
		return "";
	}

	public boolean shouldAutoRotate() {
		return isPadDevice;
	}

	public void initSystemData() {
		String storageRootPath = "";
		File sd = Environment.getExternalStorageDirectory();
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			externalAvaliable = true;
			externalWritable = true;
		} else if (state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
			externalAvaliable = true;
		}

		storageRootPath = sd.getPath();

		appDocumentHomePath = storageRootPath + "/" + APPHomeDocFolder + "/";
		FileUtils.createDir(appDocumentHomePath);

		APPTempDocFolder = storageRootPath + "/" + APPTempDocFolder;
		FileUtils.createDir(APPTempDocFolder);
		applicationContext=getApplicationContext();
//		share = applicationContext.getSharedPreferences(CoreApplication.userSettingXMLFile, Context.MODE_PRIVATE);

	}



	// 判断手机操作系统版本
	public int getAndroidSDKVersion() {
		int version = 0;
		try {
			version = Build.VERSION.SDK_INT;
			// version = Integer.valueOf(android.os.Build.VERSION.SDK);
		} catch (NumberFormatException e) {
			// log
		}
		return version;
	}

	/*
	 * 获取当前App的窗口最大宽度和高度
	 */
	public DisplayMetrics getAppWindowSize() {
		// DisplayMetrics metric = new DisplayMetrics();

		if (applicationContext == null) {
			applicationContext = getApplicationContext();
		}

		DisplayMetrics metric = applicationContext.getResources()
				.getDisplayMetrics();

		
		AppWindowDisplayMetrics = metric;

		devDensityVal = AppWindowDisplayMetrics.density;
		// }
		return metric;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public boolean isPadDevice() {
		TelephonyManager telephony = (TelephonyManager) this.applicationContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		int type = telephony.getPhoneType();
		if (type == TelephonyManager.PHONE_TYPE_NONE) {
			return true;
		}
		/*
		 * type PHONE_TYPE_GSM PHONE_TYPE_CDMA PHONE_TYPE_SIP
		 */

		return false;
	}

	// 官方用法
	public static boolean isTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	/*
	 * 长或宽超高1000定义为高分辨率设备
	 */
	public boolean isDevWithHighResolution() {
		DisplayMetrics dm = this.AppWindowDisplayMetrics;
		if ((dm.widthPixels > 1000) || (dm.heightPixels > 1000))
			return true;
		return false;
	}

	/**
	 * 
	 * @return 服务器地址
	 */

	public String getConnMMAServeUrl() {
		return connMMAServeUrl;
	}

	/**
	 * 
	 * 文件保存地址
	 */
	public String getAppDocumentHomePath() {
		return appDocumentHomePath;
	}

	/**
	 * 
	 * @return 服务器资源地址
	 */

	public String getConnMMAServerResUrl() {
		return connMMAServerResUrl;
	}

	public float getDevDensityVal() {
		return devDensityVal;
	}

	public void setDevDensityVal(float devDensityVal) {
		this.devDensityVal = devDensityVal;
	}

	/*
	 * 设置是否为pad
	 */
	public void setisPad(boolean isPad) {
		this.isPad = isPad;

	}

	public boolean getisPad() {
		return isPad;
	}

	/**
	 * 
	 * @param isbigicon
	 *            是否为大图标
	 */
	public void setisBigicon(boolean isbigicon) {
		this.misbigicon = isbigicon;

	}

	public boolean getisBigicon() {
		return this.misbigicon;

	}

	public void setImei(String imei) {
		// TODO Auto-generated method stub
		this.imei = imei;

	}

	/**
	 * 获得手机IMEI号码
	 * 
	 * @return
	 */
	public String getImei() {
		return imei;
	}

	public void setInActionRequestProcess(boolean isInActionRequestProcess) {
		// TODO Auto-generated method stub
		this.isInActionRequestProcess = isInActionRequestProcess;

	}

	public boolean isInActionRequestProcess() {
		return isInActionRequestProcess;
	}

	
	
	public  String getDeviceid(Context c){
	String	deviceid = "";
//		if (deviceid.equals("")) {
//			String str_Mac = "";
//			WifiManager wifi = (WifiManager) c
//					.getSystemService(Context.WIFI_SERVICE);
//			WifiInfo winfo = wifi.getConnectionInfo();
//			str_Mac = winfo.getMacAddress();
//			if (str_Mac==null) {//获取地址出错
//				return "";
//			}
//			str_Mac = str_Mac.replace(":", "");
//			deviceid = str_Mac;
//			CoreApplication.strMAC = str_Mac;
//		}
	return deviceid;
	}
	
	
	public String getLoginUseName() {
//		if (StringUtils.isEmpty(loginUseName)) {
//			share.getString(key_SignInAccount, "");
//		}
	
		return loginUseName;
	}

	public void setLoginUseName(String loginUseName) {
	
		
		this.loginUseName = loginUseName;
	}

	public String getLoginPwd() {
//		if (StringUtils.isEmpty(loginPwd)) {
//			share.getString(key_loginPwd, "");
//		}
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
}
