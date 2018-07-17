package com.shyouhan.aac.activity;

import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.shyouhan.aac.R;
import com.shyouhan.aac.base.BaseActivity;
import com.shyouhan.aac.constant.AppConstant;

/**
 * Created by lcy on 2018/7/8.
 */

public class WebviewActivity extends BaseActivity {
    private WebView webview;
    private String webUrl;
    private LinearLayout ll_root;
    private String webTitle;


    @Override
    protected void initView() {
        iniTitlelayout();
        webview = findViewById(R.id.webview);

        webUrl = getIntent().getStringExtra(AppConstant.WEB_URL);

        try {
            webTitle = getIntent().getStringExtra(AppConstant.WEB_TITLE);
            if (null != webTitle) {
                tvTitle.setText(webTitle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setTextString();
        WebSettings webSettings = webview.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setBlockNetworkImage(false);
//
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(WebviewActivity.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }

                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }
        });
        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(webUrl);
                return true;
            }
        });
        webview.loadUrl(webUrl);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void setTextString() {
        switch (webUrl) {
            case AppConstant.IMPORTANT_URL:
                tvTitle.setText(R.string.important_matters);
                break;
            case AppConstant.INTROSERVICE_URL:
                tvTitle.setText(R.string.service_introduction);
                break;
            case AppConstant.PRECAUTIONS_URL:
                tvTitle.setText(R.string.precaution);
                break;
        }
    }
}
