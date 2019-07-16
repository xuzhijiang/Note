package com.android.core.view;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class CustomBrowser extends WebView {

    public CustomBrowser(Context context) {
        super(context);

        WebSettings setting = getSettings();
        // // 支持JS
        setting.setJavaScriptEnabled(true);
        setting.setAllowFileAccess(true);// 可以访问文件
        setting.setBuiltInZoomControls(false);// 支持缩放
        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        setting.setLoadWithOverviewMode(true);
        setting.setUseWideViewPort(true);// 让浏览器支持用户自定义view
        setting.setSupportZoom(false);
        setting.setDomStorageEnabled(true);// 支持html5的localStorage
        setting.setAllowFileAccess(true);

        setWindowSizeInUserAgent(setting);
        setBackgroundColor(0x00000001);
        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(true);
        setPersistentDrawingCache(android.view.ViewGroup.PERSISTENT_ANIMATION_CACHE);

        setWebViewClient(new CustomWebViewClient());

        // 设置缩放比例
        setInitialScale(100);

        initJavascriptInterface();
    }

    private void setWindowSizeInUserAgent(WebSettings settings) {
        try {
            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;
            int height = displayMetrics.heightPixels;
            StringBuffer sb = new StringBuffer();
            sb.append(getSettings().getUserAgentString());
            sb.append(";Ranger:");
            sb.append("width=");
            sb.append(width);
            sb.append("&height=");
            sb.append(height);
            settings.setUserAgentString(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initJavascriptInterface() {
        addJavascriptInterface(new JSExtension(), "JSExtension");
    }

}
