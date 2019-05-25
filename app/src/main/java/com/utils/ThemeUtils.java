package com.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.easysoft.widget.config.WidgetConfig;
import com.linlsyf.area.R;

/**
 * Created by Administrator on 2019/5/25 0025.
 */

public class ThemeUtils {

    private static final String THEME_NAME = "THEME_NAME";

    public  static void switchTheme(Context context, int type) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                THEME_NAME, context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(THEME_NAME, type).commit();
    }
    public  static int getStoreTheme(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                THEME_NAME, context.MODE_PRIVATE);
       int themeType= sharedPreferences.getInt(THEME_NAME, R.style.theme_light);
       return themeType;
    }
    private WidgetConfig switchThemeConfig(Context context, int type) {

        return getThemeConfig( context,  type);
    }

    public static  WidgetConfig getThemeConfig(Context context, int themeType) {
//      int themeType=R.style.theme_light;
        WidgetConfig  widgetConfig=WidgetConfig.getInstance();

        switch (themeType) {
            case R.style.theme_light:
                widgetConfig.setBgColor(context.getResources().getColor(R.color.white));
                widgetConfig.setTextColor(context.getResources().getColor(R.color.color_dark));
                break;
            case R.style.theme_dark:
                widgetConfig.setBgColor(context.getResources().getColor(R.color.color_dark));
                widgetConfig.setTextColor(context.getResources().getColor(R.color.color_white));
                break;
            case R.style.theme_green:
                widgetConfig.setBgColor(context.getResources().getColor(R.color.color_eye_green));
                widgetConfig.setTextColor(context.getResources().getColor(R.color.common_title_color));
                break;
            case R.style.theme_easy:
                widgetConfig.setBgColor(context.getResources().getColor(R.color.color_lightcyan));
                widgetConfig.setTextColor(context.getResources().getColor(R.color.common_title_color));
                break;

//            default:
//                widgetConfig.setBgColor(context.getResources().getColor(R.color.white));
//                widgetConfig.setTextColor(context.getResources().getColor(R.color.color_dark));
        }

        return widgetConfig;
    }


}