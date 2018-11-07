package com.shyouhan.aac.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
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

public class WebviewActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private WebView webview;
    private String webUrl;
    private LinearLayout ll_root;
    private String webTitle;
    private SwipeRefreshLayout refreshLayout;


    @Override
    protected void initView() {
        iniTitlelayout();
        refreshLayout = findViewById(R.id.refresh_layout);
        webview = findViewById(R.id.webview);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        refreshLayout.setOnRefreshListener(this);

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

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadingDailog.dismiss();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loadingDailog.show();
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

    @Override
    public void onRefresh() {
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
             refreshLayout.setRefreshing(false);
            }
        },1500);
    }
}
