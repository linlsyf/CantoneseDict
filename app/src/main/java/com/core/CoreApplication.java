package com.core;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.antfortune.freeline.FreelineCore;
import com.core.base.GlobalConstants;
import com.core.db.greenDao.gen.DaoMaster;
import com.core.db.greenDao.gen.DaoSession;
import com.core.thunder.DelegateApplicationPackageManager;
import com.core.utils.ImageLoadUtils;
import com.easy.recycleview.outinter.RecycleConfig;
import com.easysoft.utils.lib.DebugUtlis.CrashHandler;
import com.tencent.bugly.crashreport.CrashReport;

import cn.jpush.android.api.JPushInterface;

import static com.core.base.GlobalConstants.TYPE_SYSTEM_APP;


/**
 * 
 * @author ldh
 * 全局Application
 *  用于初始化局标识，地址端口
 *  初始化全局变量  尽量放置到GlobalConstants类
 *  
 * 
 */
public class CoreApplication extends MultiDexApplication {
	private static final String TAG = "Tinker.CoreApplication";

	public static CoreApplication instance;
	
	/**公有变量*/
	public static GlobalConstants globalConstants;

	private DaoMaster.DevOpenHelper mHelper;
	private SQLiteDatabase db;
	private DaoMaster mDaoMaster;
	private DaoSession mDaoSession;

	public boolean isDubug=false;
	@Override
	public void onCreate() {
		super.onCreate();
//		initTinkerPatch();
		instance = this;
		FreelineCore.init(this);
		  JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
	         JPushInterface.init(this);     		// 初始化 JPush
	      // 是否为平板
			 initGlobalConstants();
			init();
		RecycleConfig.getInstance().setIloadImage(ImageLoadUtils.getInStance());
//		RecycleConfig.getInstance().

//		ThemeConfig themeConfig=new ThemeConfig();
//		themeConfig.setTitleColorResId(this.getResources().getColor(R.color.textcolor_main_normal));
//		themeConfig.setHintColorResId(this.getResources().getColor(R.color.bg_second_normal));
//		themeConfig.setBgColorResId(this.getResources().getColor(R.color.bg_main_dark));
//		RecycleConfig.getInstance().setThemeConfig(themeConfig);


		GlobalConstants.getInstance().setAppType(TYPE_SYSTEM_APP);
		StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
		StrictMode.setVmPolicy(builder.build());
		CrashHandler.getInstance().init(this);
	}


	/**
	 * 
	 * <br>创建者：ldh
	 * <br>修改时间：2016年1月25日 上午9:09:38 
	 * <br>注释：初始化一些数据  如调试信息  jpush信息
	 */
	private void init() {
//
		 setDatabase();
//		MobSDK.init(this,"2433f5d8b5a48","33927e698b643937655aa60604f7686e");
		CrashHandler handler = CrashHandler.getInstance();
		handler.init(getApplicationContext());
		CrashReport.initCrashReport(getApplicationContext(), "5efad75bba", true);
	}
	@Override
	public String getPackageName() {
		if(Log.getStackTraceString(new Throwable()).contains("com.xunlei.downloadlib")) {
			return "com.xunlei.downloadprovider";
		}
		return super.getPackageName();
	}
	@Override
	public PackageManager getPackageManager() {
		if(Log.getStackTraceString(new Throwable()).contains("com.xunlei.downloadlib")) {
			return new DelegateApplicationPackageManager(super.getPackageManager());
		}
		return super.getPackageManager();
	}


	private void setDatabase() {
		// 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
		// 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
		// 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
		// 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
		mHelper = new DaoMaster.DevOpenHelper(this, "easy-db", null);
		db = mHelper.getWritableDatabase();
		// 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
		mDaoMaster = new DaoMaster(db);
		mDaoSession = mDaoMaster.newSession();
	}

	@Override
	public void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		//you must install multiDex whatever tinker is installed!
		MultiDex.install(base);
	}

	public DaoSession getDaoSession() {
		return mDaoSession;
	}

	public SQLiteDatabase getDb() {
		return db;
	}



	public static CoreApplication getInstance() {
		return instance;
	}
	public static CoreApplication getAppContext() {
		return instance;
	}
	

	/**
	 * 简化Application代码
	 */
	public void initGlobalConstants() {
		//初始化全局变量
		 globalConstants=	GlobalConstants.getInstance();
		globalConstants.setApplicationContext(
				this.getApplicationContext());
		globalConstants.initSystemData();

		globalConstants.setDevDensityVal(
				this.getResources().getDisplayMetrics().density);

		globalConstants.istabandTwoClomnstyle = false;


		
	}
	



	




}
