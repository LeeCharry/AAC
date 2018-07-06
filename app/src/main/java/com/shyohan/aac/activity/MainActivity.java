package com.shyohan.aac.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.example.tulib.util.utils.DeviceUtil;
import com.example.tulib.util.utils.StatusBarUtil;
import com.shyohan.aac.base.BaseActivity;
import com.shyohan.aac.google.zxing.activity.CaptureActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;
import com.zhy.autolayout.AutoLinearLayout;


import java.util.ArrayList;
import java.util.List;

import acc.tulip.com.accreputation.R;

/**
 * 首页   2018-06-29
 */
public class MainActivity extends BaseActivity
        implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    public static final String TAG = "lcy";
    private Banner banner;
    private AutoLinearLayout llSearchBox;
    private ImageView ivScan;
    private AutoLinearLayout llShipmentInquiry;
    private AutoLinearLayout llSiteInquiry;
    private AutoLinearLayout llServerIntro;
    private AutoLinearLayout llPrecaution;
    private AutoLinearLayout llImportantMatters;
    private AutoLinearLayout llContactServer;

    private TextView tvWaybillNo;
    private TextView tvShippmentInquiry;
    private TextView tvSiteInquiry;
    private TextView tvServiceIntroduction;
    private TextView tvPrecaution;
    private TextView tvImportantMatters;
    private TextView tvFeedBack;
    private TextView tvStafferLogin;
    private TextView tvReward;
    private TextView tvShipping;
    private TextView tvArrivalStation;
    private TextView tvTransfer;
    private TextView tvDelivery,tvSigning;
    private TextView tvLogout;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private List<String> bannerTitleList = new ArrayList<>();
    private List<Integer> imageList = new ArrayList<>();

    @Override
    protected void initView() {
        //实现侧滑菜单状态栏透明
//        getWindow().setStatusBarColor(Color.TRANSPARENT);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        findId();
        setListener();

        String uniqueId = DeviceUtil.getUniqueId(MainActivity.this);
        LogUtils.a(TAG,"设备号：  "+uniqueId+"  ");
    }

    private void setListener() {
        llSearchBox.setOnClickListener(this);
        ivScan.setOnClickListener(this);
        llShipmentInquiry.setOnClickListener(this);
        llSiteInquiry.setOnClickListener(this);
        llServerIntro.setOnClickListener(this);
        llPrecaution.setOnClickListener(this);
        llImportantMatters.setOnClickListener(this);
        llContactServer.setOnClickListener(this);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void findId() {
        toolbar = findViewById(R.id.toolbar);
        banner = findViewById(R.id.banner);
        llSearchBox = findViewById(R.id.ll_search_box);
        ivScan = findViewById(R.id.iv_scan);
        llShipmentInquiry = findViewById(R.id.ll_shipment_inquiry);
        llSiteInquiry = findViewById(R.id.ll_site_inquiry);
        llServerIntro = findViewById(R.id.ll_server_intro);
        llPrecaution = findViewById(R.id.ll_precaution);
        llImportantMatters = findViewById(R.id.ll_important_matters);
        llContactServer = findViewById(R.id.ll_feed_back);
        findViewById(R.id.iv_switch_language).setOnClickListener(this);

        findViewById(R.id.iv_login).setOnClickListener(this);

        tvWaybillNo = findViewById(R.id.tv_waybill_no);
        tvShippmentInquiry = findViewById(R.id.tv_shippment_inquiry);
        tvSiteInquiry = findViewById(R.id.tv_site_inquiry);
        tvServiceIntroduction = findViewById(R.id.tv_service_introduction);
        tvPrecaution = findViewById(R.id.tv_precaution);
        tvImportantMatters = findViewById(R.id.tv_important_matters);
        tvFeedBack = findViewById(R.id.tv_feed_back);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        initHeadview(headerView);

        //初始化banner
        initBanner();
        //显示各标题内容
        onBack("");
//        setTextString();
    }
    private void initBanner() {
        bannerTitleList.add("Banner文案1");
        bannerTitleList.add("Banner文案2");
        bannerTitleList.add("Banner文案3");
        bannerTitleList.add("Banner文案4");
        banner.setBannerTitles(bannerTitleList);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);

        imageList.add(R.mipmap.ic_banner01);
        imageList.add(R.mipmap.ic_banner01);
        imageList.add(R.mipmap.ic_banner01);
        imageList.add(R.mipmap.ic_banner01);
        banner.setImages(imageList);

        banner.setIndicatorGravity(BannerConfig.RIGHT);
        banner.setDelayTime(2000);
        banner.isAutoPlay(true);

        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(MainActivity.this)
                        .load((int)path)
                        .into(imageView);
            }
        });
        banner.start();
        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                ToastUtils.showShort(" "+position);
            }
        });
    }

    /**
     * 设置侧边栏布局
     *
     * @param headerView
     */
    private void initHeadview(View headerView) {
        tvReward =headerView. findViewById(R.id.tv_reward);
        tvStafferLogin =headerView.findViewById(R.id.tv_staffer_login);
        tvShipping = headerView. findViewById(R.id.tv_shipping);
        tvArrivalStation = headerView. findViewById(R.id.tv_arrival_station);
        tvTransfer = headerView. findViewById(R.id.tv_transfer);
        tvDelivery = headerView. findViewById(R.id.tv_delivery);
        tvLogout = headerView. findViewById(R.id.tv_logout);
        tvSigning = headerView.findViewById(R.id.tv_signing);
        headerView.findViewById(R.id.ll_signing).setOnClickListener(this);
        headerView.findViewById(R.id.ll_login_head).setOnClickListener(this);
        headerView.findViewById(R.id.ll_reward).setOnClickListener(this);
        headerView.findViewById(R.id.ll_shipping).setOnClickListener(this);
        headerView.findViewById(R.id.ll_arrival_station).setOnClickListener(this);
        headerView.findViewById(R.id.ll_transfer).setOnClickListener(this);
        headerView.findViewById(R.id.ll_delivery).setOnClickListener(this);
        headerView.findViewById(R.id.ll_logout).setOnClickListener(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_search_box:  //运单查询
                startActivity(new Intent(MainActivity.this, ShipmentInquiryActivity.class));
                break;
            case R.id.iv_scan:  //扫一扫
                startActivity(new Intent(MainActivity.this, CaptureActivity.class));
                break;
            case R.id.ll_shipment_inquiry:  //运单查询
                startActivity(new Intent(MainActivity.this, DeliveryActivity.class));
                break;
            case R.id.ll_site_inquiry:  //站点查询
                startActivity(new Intent(MainActivity.this, SiteInquiryActivity.class));
                break;
            case R.id.ll_server_intro:  //服务介绍
                startActivity(new Intent(MainActivity.this, ServiceIntroductionActivity.class));
                break;
            case R.id.ll_precaution:  //注意事项
                startActivity(new Intent(MainActivity.this, PrecautionActivity.class));
                break;
            case R.id.ll_important_matters:  //重要事项
                startActivity(new Intent(MainActivity.this, ImportantMattersActivity.class));
                break;
            case R.id.ll_feed_back:  //意见反馈
                startActivity(new Intent(MainActivity.this, FeedBackActivity.class));
                break;
            case R.id.ll_login_head:  //登录
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.ll_reward:  //发货
                startActivity(new Intent(MainActivity.this, ExpressChoiceActivity.class));
                break;
            case R.id.ll_shipping:  //出货
                startActivity(new Intent(MainActivity.this, SearchResultActivity.class));
                break;
            case R.id.ll_arrival_station:  //到达站点
                startActivity(new Intent(MainActivity.this, SiteInquiryActivity.class));
                break;
            case R.id.ll_transfer:  //国内转运
                startActivity(new Intent(MainActivity.this, DomesticTransferActivity.class));
                break;

            case R.id.ll_delivery:  //派送
                startActivity(new Intent(MainActivity.this, DeliveryActivity.class));
                break;
            case R.id.ll_signing:  //签收

                break;
            case R.id.ll_logout:  //退出登录

                showLogoutDialog();
                startActivity(new Intent(MainActivity.this, PrecautionActivity.class));
                break;
            case R.id.iv_switch_language:  //简繁体切换
                switchLanguage();
                break;
            case R.id.iv_login:  //运单查询
                if (!drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.openDrawer(GravityCompat.START);
                }
                break;
        }
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_logout,null);
        TextView tvCancel = contentView.findViewById(R.id.tv_cancel);
        TextView tvConfirm = contentView.findViewById(R.id.tv_confirm);

        builder.setView(contentView);
        final AlertDialog dialog = builder.create();

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                MainActivity.this.finish();
                clearLocalData();
            }
        });
        dialog.show();
        dialog.getWindow().setLayout(800,LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 清空本地数据
     */
    private void clearLocalData() {

    }

    @Override
    protected void setTextString() {
        tvWaybillNo.setHint(R.string.please_input_waybill_no);
        tvShippmentInquiry.setText(R.string.shippment_inquiry);
        tvSiteInquiry.setText(R.string.site_inquiry);
        tvServiceIntroduction.setText(R.string.service_introduction);
        tvPrecaution.setText(R.string.precaution);
        tvImportantMatters.setText(R.string.important_matters);
        tvFeedBack.setText(R.string.feed_back);

        tvReward.setText(R.string.reward);
        tvStafferLogin.setText(R.string.staffer_login);
        tvShipping.setText(R.string.shipping);
        tvArrivalStation.setText(R.string.arrival_station);
        tvTransfer.setText(R.string.transfer);
        tvDelivery.setText(R.string.delivery);
        tvLogout.setText(R.string.logout);
        tvSigning.setText(R.string.signing);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CaptureActivity.RESULT_CODE_QR_SCAN){
            Bundle extra = data.getExtras();
            String result = extra.getString(CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN);
            ToastUtils.showShort(result+"  ");
        }
    }
}
