package com.shyouhan.aac.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tulib.util.base.glide.CropCircleTransformation;
import com.shyouhan.aac.ProcessType;
import com.shyouhan.aac.R;
import com.shyouhan.aac.base.BaseActivity;
import com.shyouhan.aac.bean.BannerBean;
import com.shyouhan.aac.bean.BaseObject;
import com.shyouhan.aac.bean.PackStatusBean;
import com.shyouhan.aac.constant.AppConstant;
import com.shyouhan.aac.db.DBUtils;
import com.shyouhan.aac.google.zxing.activity.CaptureActivity;
import com.shyouhan.aac.mvp.contract.ArrivePlaceContract;
import com.shyouhan.aac.mvp.contract.BannerContract;
import com.shyouhan.aac.mvp.contract.DeliveryContract;
import com.shyouhan.aac.mvp.contract.PackageContract;
import com.shyouhan.aac.mvp.contract.SearchContract;
import com.shyouhan.aac.mvp.contract.SendingContract;
import com.shyouhan.aac.mvp.contract.SignContract;
import com.shyouhan.aac.mvp.presenter.ArrivePlacePresenter;
import com.shyouhan.aac.mvp.presenter.BannerPresenter;
import com.shyouhan.aac.mvp.presenter.DeliveryPresenter;
import com.shyouhan.aac.mvp.presenter.PackagePresenter;
import com.shyouhan.aac.mvp.presenter.SearchPresenter;
import com.shyouhan.aac.mvp.presenter.SendingPresenter;
import com.shyouhan.aac.mvp.presenter.SignPresenter;
import com.shyouhan.aac.widget.SPUtils;
import com.shyouhan.aac.widget.ToastUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 首页   2018-06-29
 */
public class MainActivity extends BaseActivity
        implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener,
        SignContract.View,
        PackageContract.View,
        SendingContract.View,
        ArrivePlaceContract.View,
        DeliveryContract.View,
        BannerContract.View,
        SearchContract.View {
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
    private TextView tvDelivery, tvSigning;
    private TextView tvLogout;
    private DrawerLayout drawer;
    private List<BannerBean> bannerList = new ArrayList<>();

    private SearchPresenter searchPresenter;  //揽件
    private PackagePresenter packagePresenter;  //揽件
    private SendingPresenter sendingPresenter;  //出货
    private ArrivePlacePresenter arrivePlacePresenter;  //抵达站点
    private DeliveryPresenter deliveryPresenter;  //派送
    private SignPresenter signPresenter;  //签收
    private BannerPresenter bannerPresenter;
    private AutoRelativeLayout llLogout;
    private ArrayList<String> bannerTitleList = new ArrayList<>();
    //简体
    //    private ArrayList<String> bannerTitleListTran; //繁体
    private ImageView ivHead;
    private static int REQUEST_CODE_TO_LOGIN = 97;
    private TextView tvSwitchLanguage;
    private boolean fromlogin = false;
    private final static int REQUEST_PERMISSION_CODE_CAMERA = 102;
    private int scanCode;
    private boolean isDuo = false;  //是否是多件
    //    HashMap<String, List<String>> multiplePieces;  //一个多件对应多个假件
    List<String> fakePackIds;
    private String realPackNum = "";
    private BottomSheetDialog selectDialog;

    @Override
    protected void initView() {
        //实现侧滑菜单状态栏透明
//        getWindow().setStatusBarColor(Color.TRANSPARENT);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        findId();
        setListener();
        initPresenter();

        onBack();
    }

    private void initPresenter() {
        packagePresenter = new PackagePresenter(MainActivity.this, MainActivity.this);
        sendingPresenter = new SendingPresenter(MainActivity.this, MainActivity.this);
        arrivePlacePresenter = new ArrivePlacePresenter(MainActivity.this, MainActivity.this);
        deliveryPresenter = new DeliveryPresenter(MainActivity.this, MainActivity.this);
        signPresenter = new SignPresenter(MainActivity.this, MainActivity.this);
        bannerPresenter = new BannerPresenter(MainActivity.this, MainActivity.this);
        searchPresenter = new SearchPresenter(MainActivity.this, MainActivity.this);
        bannerPresenter.getBanner();
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
        banner = findViewById(R.id.banner);
        setBannerrOption(banner);

        llSearchBox = findViewById(R.id.ll_search_box);
        ivScan = findViewById(R.id.iv_scan);
        llShipmentInquiry = findViewById(R.id.ll_shipment_inquiry);
        llSiteInquiry = findViewById(R.id.ll_site_inquiry);
        llServerIntro = findViewById(R.id.ll_server_intro);
        llPrecaution = findViewById(R.id.ll_precaution);
        llImportantMatters = findViewById(R.id.ll_important_matters);
        llContactServer = findViewById(R.id.ll_feed_back);
        tvSwitchLanguage = findViewById(R.id.tv_switch_language);
        tvSwitchLanguage.setOnClickListener(this);
        findViewById(R.id.iv_login).setOnClickListener(this);

        tvWaybillNo = findViewById(R.id.tv_waybill_no);
        tvShippmentInquiry = findViewById(R.id.tv_shippment_inquiry);
        tvSiteInquiry = findViewById(R.id.tv_site_inquiry);
        tvServiceIntroduction = findViewById(R.id.tv_service_introduction);
        tvPrecaution = findViewById(R.id.tv_precaution);
        tvImportantMatters = findViewById(R.id.tv_important_matters);
        tvFeedBack = findViewById(R.id.tv_feed_back);


        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        initHeadview(headerView);
        //显示各标题内容
        setTextString();
        //是否显示登出
        showLLogoutAndUserInfo();
    }

    private void setBannerrOption(Banner banner) {
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        banner.setDelayTime(2000);
        banner.isAutoPlay(true);
        banner.setImageLoader(imageLoader);
    }


    private ImageLoader imageLoader = new ImageLoader() {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            BannerBean bannerBean = (BannerBean) path;
            Glide.with(MainActivity.this)
                    .load(bannerBean.getImage())
                    .into(imageView);
        }
    };

    private void initBanner(final List<BannerBean> bannerList) {
        bannerTitleList.clear();
        for (BannerBean bean : bannerList
                ) {
            if (isTranditional()) {
                bannerTitleList.add(bean.getTratitle());
            } else {
                bannerTitleList.add(bean.getTitle());
            }
        }
        banner.setBannerTitles(bannerTitleList);
        banner.setImages(bannerList);
        banner.start();
        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                //跳转页面
                if (!bannerList.get(position - 1).getLink().contains("##")) {
                    Intent intent = new Intent(MainActivity.this, WebviewActivity.class);
                    intent.putExtra(AppConstant.WEB_TITLE, bannerTitleList.get(position - 1));
                    intent.putExtra(AppConstant.WEB_URL, bannerList.get(position - 1).getLink());
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 设置侧边栏布局
     *
     * @param headerView
     */
    private void initHeadview(View headerView) {
        ivHead = headerView.findViewById(R.id.iv_head);
        tvReward = headerView.findViewById(R.id.tv_reward);
        tvStafferLogin = headerView.findViewById(R.id.tv_staffer_login);
        tvShipping = headerView.findViewById(R.id.tv_shipping);
        tvArrivalStation = headerView.findViewById(R.id.tv_arrival_station);
        tvTransfer = headerView.findViewById(R.id.tv_transfer);
        tvDelivery = headerView.findViewById(R.id.tv_delivery);
        tvLogout = headerView.findViewById(R.id.tv_logout);
        tvSigning = headerView.findViewById(R.id.tv_signing);
        headerView.findViewById(R.id.ll_signing).setOnClickListener(this);
        headerView.findViewById(R.id.ll_login_head).setOnClickListener(this);
        headerView.findViewById(R.id.ll_reward).setOnClickListener(this);
        headerView.findViewById(R.id.ll_shipping).setOnClickListener(this);
        headerView.findViewById(R.id.ll_arrival_station).setOnClickListener(this);
        headerView.findViewById(R.id.ll_transfer).setOnClickListener(this);
        headerView.findViewById(R.id.ll_delivery).setOnClickListener(this);
        llLogout = headerView.findViewById(R.id.ll_logout);
        llLogout.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (fromlogin) {
            fromlogin = false;
            showLLogoutAndUserInfo();
        }
    }

    /**
     * 是否显示登出
     */
    private void showLLogoutAndUserInfo() {
        if (null != llLogout) {
            if (SPUtils.getInstance().getBoolean(AppConstant.IS_LOGIN)) {
                llLogout.setVisibility(View.VISIBLE);
                llLogout.setClickable(true);
                //用户名，头像
                tvStafferLogin.setText(SPUtils.getInstance().getString(AppConstant.USER_NAME));
                Glide.with(this)
                        .load(SPUtils.getInstance().getString(AppConstant.AVATAR))
                        .crossFade()
                        .bitmapTransform(new CropCircleTransformation(this))
                        .into(ivHead);
            } else {
                llLogout.setVisibility(View.INVISIBLE);
                llLogout.setClickable(false);
            }
        }
    }

    @Override
    protected int getLayoutResId() {
        setImmersiveStatus(MainActivity.this);
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        String webUrlParam;
        if (isTranditional()) {
            //繁体
            webUrlParam = "&l=2";
        }else{
            //简体
            webUrlParam = "&l=1";
        }
        switch (v.getId()) {
            case R.id.ll_search_box:  //运单查询
                startActivity(new Intent(MainActivity.this, ShipmentInquiryActivity.class));
                break;
            case R.id.iv_scan:  //扫一扫
                if (requestForCamera(ProcessType.REQUEST_CODE_SEARCH)) {
                    intent2CaptureActivity(ProcessType.REQUEST_CODE_SEARCH);
                }
                break;
            case R.id.ll_shipment_inquiry:  //运单查询
                startActivity(new Intent(MainActivity.this, ShipmentInquiryActivity.class));
                break;
            case R.id.ll_site_inquiry:  //站点查询
                startActivity(new Intent(MainActivity.this, SiteInquiryActivity.class));
                break;
            case R.id.ll_server_intro:  //服务介绍
                intent = new Intent(MainActivity.this, WebviewActivity.class);
                intent.putExtra(AppConstant.WEB_URL, AppConstant.INTROSERVICE_URL+webUrlParam);
                startActivity(intent);
                break;
            case R.id.ll_precaution:  //注意事项
                intent = new Intent(MainActivity.this, WebviewActivity.class);
                intent.putExtra(AppConstant.WEB_URL, AppConstant.PRECAUTIONS_URL+webUrlParam);
                startActivity(intent);
                break;
            case R.id.ll_important_matters:  //重要事项
                intent = new Intent(MainActivity.this, WebviewActivity.class);
                intent.putExtra(AppConstant.WEB_URL, AppConstant.IMPORTANT_URL+webUrlParam);
                startActivity(intent);
                break;
            case R.id.ll_feed_back:  //意见反馈
                if (hasLogin()) {
                    startActivity(new Intent(MainActivity.this, FeedBackActivity.class));
                }

                break;
            case R.id.ll_login_head:  //登录
                if (!SPUtils.getInstance().getBoolean(AppConstant.IS_LOGIN)) {
                    fromlogin = true;
                    intent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_TO_LOGIN);
                }
                break;
            case R.id.ll_reward:  //收货，揽件
                if (hasLogin()) {
                    if (requestForCamera(ProcessType.REQUEST_CODE_REWARD)) {
                        intent2CaptureActivity(ProcessType.REQUEST_CODE_REWARD);
                    }
                }
                break;
            case R.id.ll_shipping:  //出货
                if (hasLogin()) {
                    if (requestForCamera(ProcessType.REQUEST_CODE_SENDING)) {
                        intent2CaptureActivity(ProcessType.REQUEST_CODE_SENDING);
                    }

                }
                break;
            case R.id.ll_arrival_station:  //到达站点
                if (hasLogin()) {
                    if (requestForCamera(ProcessType.REQUEST_CODE_ARRIVEPLACE)) {
                        startActivity(new Intent(mContext, ArriveStationActivity.class));
                        //选择单多件
//                        showCheckDialog(mContext, new CallBack() {
//                            @Override
//                            public void onConfirm() {
//                            }
//
//                            @Override
//                            public void onSelect(View view) {
//                                switch (view.getId()) {
//                                    case R.id.rb_dan:
//                                        intent2CaptureActivity(ProcessType.REQUEST_CODE_ARRIVEPLACE);
//                                        break;
//                                    case R.id.rb_duo:
//                                        isDuo = true;
////                                        multiplePieces = new HashMap<>();
//                                        fakePackIds = new ArrayList<>();
//                                        intent2CaptureActivity(ProcessType.REQUEST_CODE_ARRIVEPLACE);
//                                        break;
//                                }
//                            }
//                        });
                    }
                }
                break;
            case R.id.ll_transfer:  //国内转运
                if (hasLogin()) {
                    scanCode = ProcessType.REQUEST_CODE_TRANSFER;
                    //显示选择框
                    showSelectBottomSheet(scanCode);

//                    if (requestForCamera(ProcessType.REQUEST_CODE_TRANSFER)) {
//                        intent.putExtra(AppConstant.PROCESS_TYPE, ProcessType.REQUEST_CODE_TRANSFER);
//                        startActivity(intent);
//                    }
                }
                break;
            case R.id.ll_delivery:  //派送
                if (hasLogin()) {
                    showSelectBottomSheet(ProcessType.REQUEST_CODE_DELIVERY);
//                    if (requestForCamera(ProcessType.REQUEST_CODE_DELIVERY)) {
//                        intent2CaptureActivity(ProcessType.REQUEST_CODE_DELIVERY);
//                    }
                }
                break;
            case R.id.ll_signing:  //签收
                if (hasLogin()) {
                    showSelectBottomSheet(ProcessType.REQUEST_CODE_SIGNING);
//                    if (requestForCamera(ProcessType.REQUEST_CODE_SIGNING)) {
//                        intent2CaptureActivity(ProcessType.REQUEST_CODE_SIGNING);
//                    }
                }
                break;
            case R.id.ll_logout:  //退出登录
                showdialog();
                break;
            case R.id.tv_switch_language:  //简繁体切换
                switchLanguage();
                break;
            case R.id.iv_login:  //运单查询
                if (!drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.openDrawer(GravityCompat.START);
                }
                break;
        }
    }

    public void showSelectBottomSheet(int scanCode) {
        selectDialog = new BottomSheetDialog(this);
        selectDialog.setCancelable(true);
        selectDialog.setCanceledOnTouchOutside(false);
        selectDialog.setTitle(R.string.please_select_express_no_way);

        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_select, null);
        initContentView(contentView, scanCode);
        selectDialog.setContentView(contentView);

        selectDialog.show();

    }

    public void initContentView(View contentView, final int scanCode) {
        TextView tvManualInput;
        TextView tvScanBarcode;
        tvManualInput = contentView.findViewById(R.id.tv_manual_input);
        tvScanBarcode = contentView.findViewById(R.id.tv_scan_barcode);

        tvManualInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDialog.dismiss();
                showNumberInputDialog(scanCode);
            }
        });
        tvScanBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDialog.dismiss();
                if (scanCode == ProcessType.REQUEST_CODE_TRANSFER) {
                    //转运
                    if (requestForCamera(scanCode)) {
                        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                        intent.putExtra(AppConstant.PROCESS_TYPE, scanCode);
                        startActivity(intent);
                    }
                } else {
                    //签收，派送
                    if (requestForCamera(scanCode)) {
                        intent2CaptureActivity(scanCode);
                    }
                }
            }
        });
        contentView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 selectDialog.dismiss();
             }
         });
    }

    public void showNumberInputDialog(final int scanCode) {
        final EditText editText = new EditText(this);
        editText.setHint(R.string.please_input_express_number);
        editText.setKeyListener(DigitsKeyListener.getInstance("1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM"));
        editText.setTextColor(getResources().getColor(R.color.dark_gray));
        editText.setTextSize(16);
        editText.setPadding(0,20,0,20);

        new AlertDialog.Builder(MainActivity.this)
                .setCustomTitle(LayoutInflater.from(this).inflate(R.layout.custom_title,null))
                .setView(editText)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = null;
                        String result = editText.getText().toString().trim();
                        if (!TextUtils.isEmpty(result)) {
                            switch (scanCode) {
                                case ProcessType.REQUEST_CODE_TRANSFER:
                                    //中转国内快递
                                    intent = new Intent(MainActivity.this, DomesticTransferActivity.class);
                                    intent.putExtra(CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN, result);
                                    startActivity(intent);
                                    break;
                                default:
                                    //派送，签收
                                    showDialog(scanCode, result);
                                    break;
                            }
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    //6.0以上动态申请拍照权限
    private boolean requestForCamera(int requestPermissionCode) {
        scanCode = requestPermissionCode;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkMyPermission(Manifest.permission.CAMERA) && checkMyPermission(Manifest.permission.READ_EXTERNAL_STORAGE) &&
                    checkMyPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                return true;
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE_CAMERA);
                return false;
            }
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean checkMyPermission(String code) {
        if (ContextCompat.checkSelfPermission(mContext, code) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CODE_CAMERA) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    ToastUtils.showShort(R.string.permission_request_failed);
                    return;
                }
            }
            intent2CaptureActivity(scanCode);
        }
        if (requestCode == REQUEST_PERMISSION_CODE_CAMERA) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    ToastUtils.showShort(R.string.permission_request_failed);
                    return;
                }
            }
            intent2CaptureActivity(scanCode);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 跳转到扫描页面
     *
     * @param code
     */
    private void intent2CaptureActivity(int code) {
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        if (code == ProcessType.REQUEST_CODE_TRANSFER) {
            intent.putExtra(AppConstant.PROCESS_TYPE, ProcessType.REQUEST_CODE_TRANSFER);
            startActivity(intent);
        } else {
            startActivityForResult(intent, code);
        }
    }

    /**
     * 是否已登录
     *
     * @return
     */
    private boolean hasLogin() {
        if (SPUtils.getInstance().getBoolean(AppConstant.IS_LOGIN)) {
            return true;
        } else {
            fromlogin = true;
            ToastUtils.showShort(R.string.please_login_first);
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivityForResult(intent, REQUEST_CODE_TO_LOGIN);
            return false;
        }
    }

    /**
     * 显示自定义对话框
     */
    private void showdialog() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_logout, null);
        final Dialog dialog = new Dialog(this, R.style.style_logout_dialog);
        // 退出登录
        contentView.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                MainActivity.this.finish();
                clearLocalData();
            }
        });
        contentView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(contentView);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    /**
     * 清空本地数据
     */
    private void clearLocalData() {
        SPUtils.getInstance().put(AppConstant.TOKEN, "");
        SPUtils.getInstance().put(AppConstant.IS_LOGIN, false);
        SPUtils.getInstance().put(AppConstant.USER_NAME, "");
        SPUtils.getInstance().put(AppConstant.AVATAR, "");

        //清空数据库数据
        new DBUtils(this).deleteDatabase();
    }

    @Override
    protected void setTextString() {
        tvSwitchLanguage.setText(R.string.simple_complex_switch);
        tvWaybillNo.setHint(R.string.please_input_waybill_no);
        tvShippmentInquiry.setText(R.string.shippment_inquiry);
        tvSiteInquiry.setText(R.string.site_inquiry);
        tvServiceIntroduction.setText(R.string.service_introduction);
        tvPrecaution.setText(R.string.precaution);
        tvImportantMatters.setText(R.string.important_matters);
        tvFeedBack.setText(R.string.feed_back);

        tvReward.setText(R.string.reward);
        if (!TextUtils.isEmpty(SPUtils.getInstance().getString(AppConstant.USER_NAME))) {
            tvStafferLogin.setText(SPUtils.getInstance().getString(AppConstant.USER_NAME));
        } else {
            tvStafferLogin.setText(R.string.staffer_login);
        }

        tvShipping.setText(R.string.shipping);
        tvArrivalStation.setText(R.string.arrival_station);
        tvTransfer.setText(R.string.transfer);
        tvDelivery.setText(R.string.delivery);
        tvLogout.setText(R.string.logout);
        tvSigning.setText(R.string.signing);


        if (null != banner && bannerList.size() > 0) {
            initBanner(bannerList);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CaptureActivity.RESULT_CODE_QR_SCAN) {
            Bundle extra = data.getExtras();
            String result = extra.getString(CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN);

            switch (requestCode) {
                case ProcessType.REQUEST_CODE_REWARD:  //揽件
                    showDialog(ProcessType.REQUEST_CODE_REWARD, result);
                    break;
                case ProcessType.REQUEST_CODE_SENDING:      //出货
                    showDialog(ProcessType.REQUEST_CODE_SENDING, result);
                    break;
//                case ProcessType.REQUEST_CODE_ARRIVEPLACE:      //抵达站所
//                    if (isDuo) {
//                        isDuo = false;
//                        realPackNum = result; //真单号
//                        showDialogDuo();
//                    } else {
//                        showDialog(ProcessType.REQUEST_CODE_ARRIVEPLACE, result);
//                    }
//                    break;
//                case ProcessType.REQUEST_CODE_FAKE_PACK:      //扫描假件返回
//                    if (!fakePackIds.contains(result)){
//                        fakePackIds.add(result);
//                    }else{
//                        ToastUtils.showShort(R.string.please_do_not_scan_again);
//                    }
//                    showDialogDuo();
//                    break;
                case ProcessType.REQUEST_CODE_DELIVERY:      //派送
                    showDialog(ProcessType.REQUEST_CODE_DELIVERY, result);
                    break;
                case ProcessType.REQUEST_CODE_SIGNING:      //签收
                    showDialog(ProcessType.REQUEST_CODE_SIGNING, result);
                    break;
                case ProcessType.REQUEST_CODE_SEARCH:      //
                    if (!TextUtils.isEmpty(result)) {
                        searchPresenter.search(result);
                    }
                    break;
            }
        }
//        else if (resultCode == AppConstant.RESULT_CODE_LOGIN_SUCCESS) {
//            showLLogoutAndUserInfo();
//        }
    }

    /**
     * 揽件对话框
     *
     * @param requestCode
     * @param result
     */
    private void showDialog(final int requestCode, final String result) {
        String string1 = "";
        String string2 = "";
        switch (requestCode) {
            case ProcessType.REQUEST_CODE_REWARD:
                string1 = getString(R.string.waybill_no3) + " ";
                string2 = getString(R.string.is_sure_to_reward);
                break;
            case ProcessType.REQUEST_CODE_SENDING:
                string1 = getString(R.string.waybill_no3) + " ";
                string2 = getString(R.string.is_sure_to_send);
                break;
            case ProcessType.REQUEST_CODE_ARRIVEPLACE:
                string1 = getString(R.string.waybill_no3) + " ";
                string2 = getString(R.string.is_sure_to_arriveplace);
                break;
            case ProcessType.REQUEST_CODE_DELIVERY:
                string1 = getString(R.string.waybill_no3) + " ";
                string2 = getString(R.string.is_sure_to_delivery);
                break;
            case ProcessType.REQUEST_CODE_SIGNING:
                string1 = getString(R.string.waybill_no3) + " ";
                string2 = getString(R.string.is_sure_to_sign);
                break;
        }
        showdialog(MainActivity.this, string1 + result + "\r\n" + string2, new CallBack() {
            @Override
            public void onConfirm() {
                if (requestCode == ProcessType.REQUEST_CODE_REWARD) {
                    packagePresenter.lanjian(result);
                } else if (requestCode == ProcessType.REQUEST_CODE_SENDING) {
                    sendingPresenter.sending(result);
                } else if (requestCode == ProcessType.REQUEST_CODE_ARRIVEPLACE) {
                    arrivePlacePresenter.arrivePlace(result, "");
                } else if (requestCode == ProcessType.REQUEST_CODE_DELIVERY) {
                    deliveryPresenter.delivery(result);
                } else if (requestCode == ProcessType.REQUEST_CODE_SIGNING) {
                    signPresenter.sign(String.valueOf(result));
                }
            }

            @Override
            public void onSelect(View view) {

            }
        });
    }

//    private void showDialogDuo() {
//        String string1 = "";
//        String string2 = "";
//        string1 = getString(R.string.waybill_no3) + " ";
//        string2 = getString(R.string.is_sure_to_arriveplace);
//        String fakeIdStr = getFakeIdStr(fakePackIds);
//        String msg = "";
//        if (null != fakeIdStr && fakePackIds.size() > 0){
//           msg =  string1 + realPackNum + "\r\n" +"("+fakeIdStr+")"+"\r\n"+ string2;
//        }else{
//            msg =  string1 + realPackNum + "\r\n" + string2;
//        }
//        showDialogDuo(MainActivity.this,msg , new CallBack() {
//            @Override
//            public void onConfirm() {
//            }
//            @Override
//            public void onSelect(View view) {
//                switch (view.getId()) {
//                    case R.id.tv_queren:
//                        arrivePlacePresenter.arrivePlace(realPackNum,getFakeIdStr(fakePackIds));
//                        break;
//                    case R.id.tv_queren_continue:
//                        intent2CaptureActivity(ProcessType.REQUEST_CODE_FAKE_PACK);
//                        break;
//                    case R.id.tv_cancel:
//                       //取消
//                        realPackNum = "";
//                        if (null != fakePackIds) {
//                            fakePackIds.clear();
//                        }
//                        break;
//                }
//            }
//        });
//    }

    /**
     * list转化为 字符串
     * //
     */
//    private String getFakeIdStr(List<String> fakePackIds) {
//        if (fakePackIds != null && fakePackIds.size()>0) {
//            String fakeIds = "";
//            for (String fakeId: fakePackIds) {
//                fakeIds = fakeIds.concat(fakeId+",");
//            }
//            return fakeIds.substring(0,fakeIds.length()-1);
//        }else{
//            return "";
//        }
//    }
    @Override
    protected void onDestroy() {
        try {
            loadingDailog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        loadingDailog.show();
    }

    @Override
    public void hideLoading() {
        loadingDailog.hide();
    }

    @Override
    public void showMessage(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void launchActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void killMySelf() {
    }

    @Override
    public void onSignSuccess(BaseObject baseObject) {
        Intent intent = new Intent(MainActivity.this, DoSuccessActivity.class);
        intent.putExtra(AppConstant.PROCESS_TIME, baseObject.getTime());
        intent.putExtra(AppConstant.PROCESS_TYPE, ProcessType.REQUEST_CODE_SIGNING);
        startActivity(intent);
    }

    @Override
    public void onSignFailed(String error) {
        showMessage(error);
    }

    @Override
    public void onPackageSuccess(BaseObject baseObject) {
        showMessage(getString(R.string.reward_success));
        Intent intent = new Intent(MainActivity.this, DoSuccessActivity.class);
        intent.putExtra(AppConstant.PROCESS_TIME, baseObject.getTime());
        intent.putExtra(AppConstant.PROCESS_TYPE, ProcessType.REQUEST_CODE_REWARD);
        startActivity(intent);
    }

    @Override
    public void onPackageFailed(String error) {
        showMessage(error);
    }

    @Override
    public void onSendingSuccess(BaseObject baseObject) {
        showMessage(getString(R.string.send_success));
        Intent intent = new Intent(MainActivity.this, DoSuccessActivity.class);
        intent.putExtra(AppConstant.PROCESS_TIME, baseObject.getTime());
        intent.putExtra(AppConstant.PROCESS_TYPE, ProcessType.REQUEST_CODE_SENDING);
        startActivity(intent);
    }

    @Override
    public void onSendingFailed(String error) {
        showMessage(error);
    }

    @Override
    public void onArrivePlaceSuccess(BaseObject baseObject) {
        realPackNum = "";
        if (null != fakePackIds) {
            fakePackIds.clear();
        }
        Intent intent = new Intent(MainActivity.this, DoSuccessActivity.class);
        intent.putExtra(AppConstant.PROCESS_TIME, baseObject.getTime());
        intent.putExtra(AppConstant.PROCESS_TYPE, ProcessType.REQUEST_CODE_ARRIVEPLACE);
        startActivity(intent);
    }

    @Override
    public void onArrivePlaceFailed(String error) {
        showMessage(error);
        realPackNum = "";
        if (null != fakePackIds) {
            fakePackIds.clear();
        }
    }

    @Override
    public void onDeliverySuccess(BaseObject baseObject) {
        Intent intent = new Intent(MainActivity.this, DoSuccessActivity.class);
        intent.putExtra(AppConstant.PROCESS_TIME, baseObject.getTime());
        intent.putExtra(AppConstant.PROCESS_TYPE, ProcessType.REQUEST_CODE_DELIVERY);
        startActivity(intent);
    }

    @Override
    public void onDeliveryFailed(String error) {
        showMessage(error);
    }

    @Override
    public void onGetBannerSuccess(List<BannerBean> bannerBeanList) {
        //初始化banner
        bannerList.clear();
        bannerList.addAll(bannerBeanList);


        initBanner(bannerList);
    }


    @Override
    public void onSearchSuccess(PackStatusBean packStatusBean) {
        //向数据库插入一条查询历史记录
        new DBUtils(this).insert(AppConstant.TABLE_SEARCH_HISTORY, new String[]{AppConstant.COLUMN_PICKID}, new String[]{packStatusBean.getPack().toString()});
        Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
        intent.putExtra(AppConstant.STATUSBEAN, packStatusBean);
        startActivity(intent);
    }

    @Override
    public void onSearchFailed(String error) {
        ToastUtils.showShort(error);
    }
}
