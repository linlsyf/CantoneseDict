package com.ui.other.tuling.util;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

public class KeyBoardUtil {



    // 收起软键盘
    public static boolean hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
            return true;
        }
        return false;
    }

}
