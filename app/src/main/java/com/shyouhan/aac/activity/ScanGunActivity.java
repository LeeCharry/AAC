package com.shyouhan.aac.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shyouhan.aac.ProcessType;
import com.shyouhan.aac.R;
import com.shyouhan.aac.adapter.ScanGunRvAdapter;
import com.shyouhan.aac.base.BaseActivity;
import com.shyouhan.aac.bean.BaseObject;
import com.shyouhan.aac.bean.ResultBean;
import com.shyouhan.aac.mvp.contract.ArrivePlaceContract;
import com.shyouhan.aac.mvp.contract.DeliveryContract;
import com.shyouhan.aac.mvp.contract.FakeContract;
import com.shyouhan.aac.mvp.contract.PackageContract;
import com.shyouhan.aac.mvp.presenter.ArrivePlacePresenter;
import com.shyouhan.aac.mvp.presenter.DeliveryPresenter;
import com.shyouhan.aac.mvp.presenter.FakePresenter;
import com.shyouhan.aac.mvp.presenter.PackagePresenter;
import com.shyouhan.aac.widget.ToastUtils;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcy on 2018/7/13.
 */

public class ScanGunActivity extends BaseActivity implements FakeContract.View,ArrivePlaceContract.View,DeliveryContract.View,PackageContract.View{
    private AutoRelativeLayout rlTitle;
    private EditText etInput;
    private RecyclerView recyclerview;
    private List<ResultBean> barcodeList = new ArrayList<>();
    private ScanGunRvAdapter adapter;
    private FakePresenter fakePresenter;
    private ArrivePlacePresenter arrivePlacePresenter;
    private DeliveryPresenter deliveryPresenter;
    private PackagePresenter packagePresenter;
    private String barCode;
    private int scanCode;
    private ImageView ivBack;

    @Override
    protected void initView() {
        iniTitlelayout();
        rlTitle = (AutoRelativeLayout) findViewById(R.id.rl_title);
        etInput = (EditText) findViewById(R.id.et_input);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        tvTitle.setText(getString(R.string.scan_gun));
        tvTitle.setTextColor(Color.BLACK);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(2);
                ScanGunActivity.this.finish();
            }
        });

         scanCode = getIntent().getIntExtra("scancode",0);
        initPresenter();
        initRv();

        
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("lcy", "afterTextChanged:    "+s.toString() );
                if (!s.toString().endsWith("\n")) {
                    return;
                }
                //
                 String data = s.toString();
                String[] split = data.split("\n");
                if (null != split && split.length > 0) {
                    //上传数据
                     barCode = split[split.length - 1];
                    if (barcodeList.contains(barCode)) {
                        ToastUtils.showShort("请勿重复扫描");
                        return;
                    }
                    switch (scanCode) {
                        case ProcessType.REQUEST_CODE_ARRIVEPLACE_DUO:
                            //假单扫描
                            fakePresenter.fake(barCode);
                            break;
                        case ProcessType.REQUEST_CODE_ARRIVEPLACE_DAN:
                            //单件扫描
                            arrivePlacePresenter.arrivePlace(barCode,"");
                            break;
                        case ProcessType.REQUEST_CODE_DELIVERY:
                            //派件
                            deliveryPresenter.delivery(barCode);
                            break;
                        case ProcessType.REQUEST_CODE_REWARD:
                            //收件
                            packagePresenter.lanjian(barCode);
                            break;
                    }
                }
            }
        });
    }

    private void initPresenter() {
        fakePresenter = new FakePresenter(this,this);
        arrivePlacePresenter = new ArrivePlacePresenter(this,this);
        deliveryPresenter = new DeliveryPresenter(this,this);
        packagePresenter = new PackagePresenter(this,this);
    }

    private void initRv() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ScanGunRvAdapter(barcodeList);
        recyclerview.setAdapter(adapter);
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_layout, null);
        TextView tvEmpty = emptyView.findViewById(R.id.tv_empty);
        tvEmpty.setHint("还没有数据");
        adapter.setEmptyView(emptyView);
    }

    @Override
    protected int getLayoutResId() {
//        //设置全屏
//
//        //去除标题栏
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        //去除状态栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_scan_gun;
    }

    @Override
    protected void setTextString() {

    }

    @Override
    public void showLoading() {
            loadingDailog.show();
    }

    @Override
    public void hideLoading() {
        loadingDailog.dismiss();
    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMySelf() {

    }

    @Override
    public void onFakeSuccess(BaseObject baseObject) {
        playMusic();
        barcodeList.add(0,new ResultBean(barCode,baseObject.getMsg(),baseObject.getTramsg()));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFakeFailed(String error) {
        barcodeList.add(0,new ResultBean(barCode,error,error));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onArrivePlaceSuccess(BaseObject baseObject) {
        playMusic();
        barcodeList.add(0,new ResultBean(barCode,baseObject.getMsg(),baseObject.getTramsg()));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onArrivePlaceFailed(String error) {
        barcodeList.add(0,new ResultBean(barCode,error,error));
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onDeliverySuccess(BaseObject baseObject) {
        playMusic();
        barcodeList.add(0,new ResultBean(barCode,baseObject.getMsg(),baseObject.getTramsg()));
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onDeliveryFailed(String error) {
        barcodeList.add(0,new ResultBean(barCode,error,error));
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onPackageSuccess(BaseObject baseObject) {
        playMusic();
        barcodeList.add(0,new ResultBean(barCode,baseObject.getMsg(),baseObject.getTramsg()));
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onPackageFailed(String error) {
        barcodeList.add(0,new ResultBean(barCode,error,error));
        adapter.notifyDataSetChanged();
    }
}
