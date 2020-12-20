package com.utils;

public class ClickUtils {
    private static long lastClickTime=0;
    private static final int CLICK_TIME = 500; //快速点击间隔时间

    // 判断按钮是否快速点击
    public static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < CLICK_TIME) {//判断系统时间差是否小于点击间隔时间
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
