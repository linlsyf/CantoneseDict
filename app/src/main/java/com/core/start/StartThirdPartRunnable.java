package com.core.start;

import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.util.Log;

import com.core.CoreApplication;
import com.core.base.GlobalConstants;
import com.core.db.greenDao.gen.DaoMaster;
import com.core.db.greenDao.gen.DaoSession;
import com.core.utils.ImageLoadUtils;
import com.easy.recycleview.outinter.RecycleConfig;
import com.easysoft.utils.lib.DebugUtlis.CrashHandler;
import com.iflytek.cloud.SpeechUtility;
import com.linlsyf.cantonese.R;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;

import static com.core.base.GlobalConstants.TYPE_SYSTEM_APP;

/**
 * Created by lindanghong on 2019/6/27.
 */

public class StartThirdPartRunnable   implements Runnable,  IstartRun{


    /**公有变量*/
//    public static GlobalConstants globalConstants;

    public boolean isDubug=false;
    @Override
    public void run() {

//        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
//        JPushInterface.init(CoreApplication.getAppContext());     		// 初始化 JPush
        // 是否为平板
//        initGlobalConstants();
        init();
        RecycleConfig.getInstance().setIloadImage(ImageLoadUtils.getInStance());



        GlobalConstants.getInstance().setAppType(TYPE_SYSTEM_APP);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        // 应用程序入口处调用，避免手机内存过小，杀死后台进程后通过历史intent进入Activity造成SpeechUtility对象为null
        // 如在Application中调用初始化，需要在Mainifest中注册该Applicaiton
        // 注意：此接口在非主进程调用会返回null对象，如需在非主进程使用语音功能，请增加参数：SpeechConstant.FORCE_LOGIN+"=true"
        // 参数间使用半角“,”分隔。
        // 设置你申请的应用appid,请勿在'='与appid之间添加空格及空转义符

        // 注意： appid 必须和下载的SDK保持一致，否则会出现10407错误

        SpeechUtility.createUtility(CoreApplication.getAppContext(), "appid=" +CoreApplication.getAppContext(). getString(R.string.app_id));

        // 以下语句用于设置日志开关（默认开启），设置成false时关闭语音云SDK日志打印
        // Setting.setShowLog(false);

        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(CoreApplication.getAppContext(),  cb);
    }





    /**
     *
     * <br>创建者：ldh
     * <br>修改时间：2016年1月25日 上午9:09:38
     * <br>注释：初始化一些数据  如调试信息  jpush信息
     */
    private void init() {
//		MobSDK.init(this,"2433f5d8b5a48","33927e698b643937655aa60604f7686e");
        CrashHandler handler = CrashHandler.getInstance();
        handler.init(CoreApplication.getAppContext());
        CrashReport.initCrashReport(CoreApplication.getAppContext(), "5efad75bba", true);
    }








}
