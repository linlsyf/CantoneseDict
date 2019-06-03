package com.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.easy.recycleview.outinter.RecycleConfig;
import com.easy.recycleview.outinter.ThemeConfig;
import com.easysoft.widget.config.WidgetConfig;
import com.linlsyf.area.R;

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
    public  static int getStoreThemeIcon(Context context) {
        int themeType=       getStoreTheme(context);

        int  themLogo=R.drawable.theme_night;
          if (themeType==R.style.theme_dark){
              themLogo=R.drawable.theme_day;
          }
       return themLogo;
    }


    private WidgetConfig switchThemeConfig(Context context, int type) {

        return getThemeConfig( context,  type);
    }

    public  static void  initConfig(Context context){
        int type= ThemeUtils.getStoreTheme(context);

        WidgetConfig widgetConfig=      getThemeConfig(context,type);
        ThemeConfig themeConfig=new ThemeConfig();
        themeConfig.setBgColorResId(widgetConfig.getBgColor());
        themeConfig.setTitleColorResId(widgetConfig.getTextColor());
        RecycleConfig.getInstance().setThemeConfig(themeConfig);

    }

    public static  WidgetConfig getThemeConfig(Context context, int themeType) {
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

        }

        return widgetConfig;
    }


}