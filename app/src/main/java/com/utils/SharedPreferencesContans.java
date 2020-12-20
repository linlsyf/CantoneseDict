package com.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.core.CoreApplication;
import com.easysoft.utils.lib.system.ToastUtils;
import com.linlsyf.area.R;

/**
 * Created by Administrator on 2019/9/20 0020.
 */

public class SharedPreferencesContans {

    public  static String  AV_PRE_NAME="AV_PRE_NAME";



    public  static Object getStoreTheme(Context context,String name,String defaultString) {
        SharedPreferences sharedPreferences;
        String themeType=defaultString;
        try{
             sharedPreferences = context.getSharedPreferences(name, context.MODE_PRIVATE);
            themeType= sharedPreferences.getString(name,defaultString);

        }catch (Exception e){

        }

        return themeType;
    }
    public  static Object getStoreTheme(Context context,String name) {
        return getStoreTheme(context,name,"");

    }
    public    static void chaneStoreTheme(Context context,String name,String content) {
        if(null==context){
            ToastUtils.show(CoreApplication.getAppContext(),"context传参错误");
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                name, context.MODE_PRIVATE);
        sharedPreferences.edit().putString(name, content).commit();
    }


}
