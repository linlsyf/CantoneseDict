package com.utils;

import com.easy.recycleview.outinter.IDyviewThemeConfig;
import com.easysoft.widget.config.IWidgetConfig;

/**
 * Created by Administrator on 2019/6/21 0021.
 */

public class AppThemeConfig implements IDyviewThemeConfig ,IWidgetConfig {
    private static AppThemeConfig config;
    private int bgColor;
    private int textSize;
    private int textColor;
    private int selectedTextColor;
    private int drawablePadding;
    private int iconHeight;
    private int iconWidth;
    private int divideLineColor;
    private int divideLineHeight;
    int titleColorResId = 0;
    int hintColorResId = 0;
    int bgColorResId = 0;
    int bgResourcResId = 0;
    public AppThemeConfig() {
    }

    public static AppThemeConfig getInstance() {
        if(config == null) {
            config = new AppThemeConfig();
        }

        return config;
    }

    public int getBgColor() {
        return this.bgColor;
    }

    public AppThemeConfig setBgColor(int bgColor) {
        this.bgColor = bgColor;  return this;
    }

    public int getTextSize() {
        return this.textSize;
    }

    public AppThemeConfig setTextSize(int textSize) {
        this.textSize = textSize;
        return this;
    }

    public int getTextColor() {
        return this.textColor;
    }

    public AppThemeConfig setTextColor(int textColor) {
        this.textColor = textColor;  return this;
    }

    public int getSelectedTextColor() {
        return this.selectedTextColor;
    }

    public AppThemeConfig setSelectedTextColor(int selectedTextColor) {
        this.selectedTextColor = selectedTextColor;  return this;
    }

    public int getDrawablePadding() {
        return this.drawablePadding;
    }

    public AppThemeConfig setDrawablePadding(int drawablePadding) {
        this.drawablePadding = drawablePadding;  return this;
    }

    public int getIconHeight() {
        return this.iconHeight;
    }

    public AppThemeConfig setIconHeight(int iconHeight) {
        this.iconHeight = iconHeight;  return this;
    }

    public int getIconWidth() {
        return this.iconWidth;
    }

    public AppThemeConfig setIconWidth(int iconWidth) {
        this.iconWidth = iconWidth;  return this;
    }

    public int getDivideLineColor() {
        return this.divideLineColor;
    }

    public AppThemeConfig setDivideLineColor(int divideLineColor) {
        this.divideLineColor = divideLineColor;  return this;
    }

    public int getDivideLineHeight() {
        return this.divideLineHeight;
    }

    public AppThemeConfig setDivideLineHeight(int divideLineHeight) {
        this.divideLineHeight = divideLineHeight;  return this;
    }

    public int getTitleColorResId() {
        return titleColorResId;
    }

    public AppThemeConfig setTitleColorResId(int titleColorResId) {
        this.titleColorResId = titleColorResId;  return this;
    }

    public int getHintColorResId() {
        return hintColorResId;
    }

    public AppThemeConfig setHintColorResId(int hintColorResId) {
        this.hintColorResId = hintColorResId;  return this;
    }

    public int getBgColorResId() {
        return bgColorResId;
    }

    public AppThemeConfig setBgColorResId(int bgColorResId) {
        this.bgColorResId = bgColorResId;  return this;
    }

    public int getBgResourcResId() {
        return bgResourcResId;
    }

    public AppThemeConfig setBgResourcResId(int bgResourcResId) {
        this.bgResourcResId = bgResourcResId;  return this;
    }
}
