package com.utils;

import android.content.Context;

import androidx.recyclerview.widget.LinearSmoothScroller;

/**
 * Created by Administrator on 2019/8/12 0012.
 */

public class TopSmoothScroller extends LinearSmoothScroller {
    TopSmoothScroller(Context context) {
        super(context);
    }
    @Override
    protected int getHorizontalSnapPreference() {
        return SNAP_TO_START;//具体见源码注释
    }
    @Override
    protected int getVerticalSnapPreference() {
        return SNAP_TO_START;//具体见源码注释
    }
}