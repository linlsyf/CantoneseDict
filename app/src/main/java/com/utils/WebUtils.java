package com.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by lindanghong on 2019/6/5.
 */

public class WebUtils {
    public static void openUrl(Context context,String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);


    }
}
