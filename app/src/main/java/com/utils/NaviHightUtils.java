package com.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.linlsyf.area.R;


import java.lang.reflect.Method;

/**
 * Created by Administrator on 2020/1/22 0022.
 */

public class NaviHightUtils {

    //获取底部导航的高度
    public static int getBottomStatusHeight(Context context) {
        int totalHeight = getDpi(context);
        int contentHeight = getScreenHeight(context);
//        PrintLog.printDebug(TAG, "--显示虚拟导航了--");
        return totalHeight - contentHeight;
    }
    //获取屏幕原始尺寸高度，包括虚拟功能键高度
    public static int getDpi(Context context) {
        int dpi = 0;
        WindowManager windowManager = (WindowManager)
                context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi = displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

    public static void setHight( com.core.base.BasicActivity context ){
        int   hight=NaviHightUtils.getBottomStatusHeight(context);

        if (hight>200){
            hight=hight-100;
        }
        LinearLayout mView=context.findViewById(R.id.naLyout);
        ViewGroup.LayoutParams layoutParams= mView.getLayoutParams();
        layoutParams.height=hight;
        mView.setLayoutParams(layoutParams);

    }
    //获取屏幕高度 不包含虚拟按键=
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

}
