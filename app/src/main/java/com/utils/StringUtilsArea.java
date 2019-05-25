package com.utils;

import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by lindanghong on 2019/3/13.
 */

public class StringUtilsArea {

    public static void copy(Context context,String text){
        ClipboardManager cm = (ClipboardManager)context. getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(text);
    }
}
