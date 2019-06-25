package com;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.linlsyf.area.R;

public class TestActivity extends AppCompatActivity {

    private WebView webview;
    private TextView tvJs;
    private TextView tvJsArgs;
    private TextView tvShowmsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        setWebview();
        initView();
    }

    private void initView() {
        tvJs = (TextView) findViewById(R.id.tv_androidcalljs);
        tvJsArgs = (TextView) findViewById(R.id.tv_androidcalljsargs);
        tvShowmsg = (TextView) findViewById(R.id.tv_showmsg);

        tvJs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                webview.loadUrl("javascript:javacalljs()");
                webview.loadUrl("javascript:speak(" + "'婴儿'" + ")");


            }
        });

        tvJsArgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview.loadUrl("javascript:javacalljswith(" + "'Android传过来的参数'" + ")");
            }
        });
    }

    private void setWebview() {
        webview = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webview.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        //与js交互必须设置
        webSettings.setJavaScriptEnabled(true);
        webview.loadUrl("file:///android_asset/cantonsprak.html");
//        webview.loadUrl("file:///android_asset/html.html");
        webview.addJavascriptInterface(TestActivity.this,"android");
    }

    @JavascriptInterface
    public void jsCallAndroid(){
        tvShowmsg.setText("Js调用Android方法");
    }

    @JavascriptInterface
    public void jsCallAndroidArgs(String args){
        tvShowmsg.setText(args);
    }
}
