package com.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.easy.recycleview.outinter.RecycleConfig;
import com.easysoft.widget.config.WidgetConfigManger;
import com.linlsyf.cantonese.R;

public class ThemeHelper {

    private static final String THEME_NAME = "THEME_NAME";

    public  static void  initConfig(Context context){
        int type= ThemeHelper.getStoreTheme(context);
        changeTheme(context,type);
    }


    public  static int getStoreThemeIcon(Context context) {
        int themeType=       getStoreTheme(context);

        int  themLogo=R.drawable.theme_night;
          if (themeType==R.style.theme_dark){
              themLogo=R.drawable.theme_day;
          }
       return themLogo;
    }
    public  static int getStoreTheme(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                THEME_NAME, context.MODE_PRIVATE);
        int themeType= sharedPreferences.getInt(THEME_NAME, R.style.theme_light);
        return themeType;
    }
        private   static void chaneStoreTheme(Context context, int type) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                THEME_NAME, context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(THEME_NAME, type).commit();
    }

    /**
     * 获取配置
     * @param context
     * @param themeType
     * @return
     */
        private static AppThemeConfig getThemeConfig(Context context, int themeType) {
            AppThemeConfig  widgetConfig=AppThemeConfig.getInstance();

        switch (themeType) {
            case R.style.theme_light:
                widgetConfig.setBgColor(context.getResources().getColor(R.color.white));
                widgetConfig.setTextColor(context.getResources().getColor(R.color.color_dark));
                widgetConfig.setBgResourcResId(R.drawable.common_select);


                break;
            case R.style.theme_dark:
                widgetConfig.setBgColor(context.getResources().getColor(R.color.color_dark));
                widgetConfig.setTextColor(context.getResources().getColor(R.color.color_white));
                widgetConfig.setBgResourcResId(R.drawable.common_select_dark);

                break;
            case R.style.theme_green:
                widgetConfig.setBgColor(context.getResources().getColor(R.color.color_eye_green));
                widgetConfig.setTextColor(context.getResources().getColor(R.color.common_title_color));
                widgetConfig.setBgResourcResId(R.drawable.common_select_green);

                break;
            case R.style.theme_easy:
                widgetConfig.setBgColor(context.getResources().getColor(R.color.color_lightcyan));
                widgetConfig.setTextColor(context.getResources().getColor(R.color.common_title_color));
                widgetConfig.setBgResourcResId(R.drawable.common_select_green);

                break;

        }
            widgetConfig.setBgColorResId(widgetConfig.getBgColor());

            widgetConfig.setTitleColorResId(widgetConfig.getTextColor());

        return widgetConfig;
    }



    public  static void changeTheme(Context context,int type){
        AppThemeConfig themeConfigApp=      getThemeConfig(context,type);

//        ThemeConfig themeConfig= new ThemeConfig();
//        themeConfig.setTitleColorResId(themeConfigApp.getTextColor());
//        themeConfig.setHintColorResId(themeConfigApp.getHintColorResId());
//        themeConfig.setBgResourcResId(themeConfigApp.getBgResourcResId());
//        themeConfig.setBgColorResId(themeConfigApp.getBgColorResId());

        RecycleConfig.getInstance().setThemeConfig(themeConfigApp);
        WidgetConfigManger.getInstance().setiWidgetConfig(themeConfigApp);
//
//
//        WidgetConfig.getInstance().setSelectedTextColor(themeConfigApp.getSelectedTextColor());
//        WidgetConfig.getInstance().setTextColor(themeConfigApp.getTextColor());
//        WidgetConfig.getInstance().setBgColor(themeConfigApp.getBgColor());
        chaneStoreTheme(context,type);
    }

}