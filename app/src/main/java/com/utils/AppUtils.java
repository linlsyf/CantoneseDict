package com.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2020/1/4 0004.
 */

public class AppUtils {
    public  static void openApp(Context context ,String packageName){
        PackageManager packageManager = context.getPackageManager();
        Intent intent= packageManager.getLaunchIntentForPackage(packageName);
        if (null==context){
            ErrorExeUtils.errorNotice("AppUtils-openApp","null");
            return ;

        }
        context.startActivity(intent);
    }

    public  static  void closeAndroidPDialog(){
        try {
Class aClass = Class.forName("android.content.pm.PackageParser$Package");
Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
declaredConstructor.setAccessible(true);
 } catch (Exception e) {
e.printStackTrace();
 }
 try {
Class cls = Class.forName("android.app.ActivityThread");
Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
declaredMethod.setAccessible(true);
Object activityThread = declaredMethod.invoke(null);
Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
mHiddenApiWarningShown.setAccessible(true);
mHiddenApiWarningShown.setBoolean(activityThread, true);
 } catch (Exception e) {
e.printStackTrace();
 }
 }

}
