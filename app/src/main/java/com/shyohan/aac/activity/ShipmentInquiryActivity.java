package com.shyohan.aac.activity;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.shyohan.aac.base.BaseActivity;
import com.zhy.autolayout.AutoLinearLayout;

import acc.tulip.com.accreputation.R;

/**
 * 货件查询
 */
public class ShipmentInquiryActivity extends BaseActivity implements View.OnClickListener{

    private AutoLinearLayout llSearchBox;
    private ImageView ivScan;
    private Button btnInquiry;
    private EditText etWaybillNo;

    @Override
    protected void initView() {
        iniTitlelayout();
        tvTitle.setText(R.string.shippment_inquiry);
        findId();
        onBack("");
    }

    private void findId() {
        llSearchBox = findViewById(R.id.ll_search_box);
        etWaybillNo = findViewById(R.id.et_waybill_no);
        ivScan = findViewById(R.id.iv_scan);
        btnInquiry = findViewById(R.id.btn_inquiry);

        ivScan.setOnClickListener(this);
        btnInquiry.setOnClickListener(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_shipment_inquiry;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_scan:  //扫描
//                startActivity(new Intent(MainActivity.this, ShipmentInquiryActivity.class));
                break;
            case R.id.btn_inquiry:  //查询
//                startActivity(new Intent(MainActivity.this, ShipmentInquiryActivity.class));
                break;
        }
    }

    @Override
    protected void setTextString() {
        tvTitle.setText(R.string.shippment_inquiry);
        etWaybillNo.setHint(R.string.please_input_waybill_no);
        btnInquiry.setText(R.string.inquiry);
    }
}
