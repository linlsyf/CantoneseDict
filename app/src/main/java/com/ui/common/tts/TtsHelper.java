package com.ui.common.tts;

import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by Administrator on 2019/6/26 0026.
 */

public class TtsHelper {

    public static void initWeb(WebView webview){
        WebSettings webSettings = webview.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        //与js交互必须设置
        webSettings.setJavaScriptEnabled(true);
        webview.loadUrl("file:///android_asset/cantonsprak.html");
//        webview.loadUrl("file:///android_asset/html.html");
//        webview.addJavascriptInterface(this,"android");
//        webview.addJavascriptInterface(this,"android");
    }

    public static  void speak(WebView webview ,String textYuey) {
        webview.loadUrl("javascript:speak('" + textYuey+ "')");

    }
}
