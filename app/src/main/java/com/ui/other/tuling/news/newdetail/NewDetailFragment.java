package com.ui.other.tuling.news.newdetail;

import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.core.base.BaseFragment;
import com.easy.recycleview.bean.Section;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.linlsyf.area.R;


public class NewDetailFragment extends BaseFragment implements INewsDetailsView {
    WebView webView;
    private String mUrl = "";
    private WebSettings settings;
    private NavigationBar toolbar;
    NewsDetailPresenter persenter;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_webview_common;
    }

    @Override
    public void initFragment() {
        initUIView();
        initData();
    }
    @Override
    public void initUIView() {
        webView = getViewById(R.id.webView);
        toolbar=getViewById(R.id.toolbar);
        TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), "新闻详情", 0);
//        recycleView.initCustomViewCallBack(new AddressRecycleView.CustomViewCallBack() {
//            @Override
//            public View getCustomView(Context context, int type) {
//                webView= null;
//                if (type==3){
//                    webView=new WebView(context);
//                }
//                return webView;
//            }
//        });
        persenter=new NewsDetailPresenter(this);
        persenter.init();
        initView();
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }


    @Override
    public void getBroadcastReceiverMessage(String type, Object mode) {

    }

    private void initView() {
        mUrl = getArguments().getString("url");

        settings = webView.getSettings();
        settings.setJavaScriptEnabled(true); //如果访问的页面中有Javascript，则WebView必须设置支持Javascript
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportZoom(true); //支持缩放
        settings.setBuiltInZoomControls(true); //支持手势缩放
        settings.setDisplayZoomControls(false); //是否显示缩放按钮
        // >= 19(SDK4.4)启动硬件加速，否则启动软件加速
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            settings.setLoadsImagesAutomatically(false);
        }

        settings.setUseWideViewPort(true); //将图片调整到适合WebView的大小
        settings.setLoadWithOverviewMode(true); //自适应屏幕
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setSaveFormData(true);
        settings.setSupportMultipleWindows(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //优先使用缓存

        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); //可使滚动条不占位
        webView.setHorizontalScrollbarOverlay(true);
        webView.setHorizontalScrollBarEnabled(true);
        webView.requestFocus();

        webView.loadUrl(mUrl);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                toolbar.setTitle("正在加载...");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.setLayerType(View.LAYER_TYPE_NONE, null);
                if (!settings.getLoadsImagesAutomatically()) {
                    settings.setLoadsImagesAutomatically(true);
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
//                toolbar.setTitle("加载失败");
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
//                toolbar.setTitle(title);
            }
        });

    }

    @Override
    public void initUI(final Section section) {
//        getActivity().runOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {
//                recycleView.updateSection(section,true);
//                initView();
//
//            }
//        });
    }

    @Override
    public void loadDataStart() {

    }
}

