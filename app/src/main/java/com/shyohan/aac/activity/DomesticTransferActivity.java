package com.shyohan.aac.activity;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shyohan.aac.base.BaseActivity;
import com.zhy.autolayout.AutoLinearLayout;


import acc.tulip.com.accreputation.R;

/**
 * 国内转运
 */
public class DomesticTransferActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvWaybillNo;
    private TextView tvDateAddr;
    private TextView tvSelect;
    private ImageView ivSelect;
    private AutoLinearLayout llSearchBox;
    private EditText etWaybillNo;
    private ImageView ivScan;
    private Button btnInquiry;

    @Override
    protected void initView() {
        iniTitlelayout();
        tvTitle.setText(R.string.domestic_transfer);
        findId();
//        onBack("");
        setTextString();
    }

    private void findId() {
        tvWaybillNo = findViewById(R.id.tv_waybill_no);
        tvDateAddr = findViewById(R.id.tv_date_addr);
        tvSelect = findViewById(R.id.tv_select);
        ivSelect = findViewById(R.id.iv_select);
        llSearchBox = findViewById(R.id.ll_search_box);
        etWaybillNo = findViewById(R.id.et_waybill_no);
        ivScan = findViewById(R.id.iv_scan);
        btnInquiry = findViewById(R.id.btn_inquiry);

        btnInquiry.setOnClickListener(this);
        ivScan.setOnClickListener(this);
        ivSelect.setOnClickListener(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_domestic_transfer;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_select:  //选择国内快递
//               startActivity(new Intent(DomesticTransferActivity.this, ShipmentInquiryActivity.class));
                break;
            case R.id.iv_scan:  //扫一扫
//                startActivity(new Intent(DomesticTransferActivity.this, ShipmentInquiryActivity.class));
                break;
            case R.id.btn_inquiry:  //确定
//                startActivity(new Intent(DomesticTransferActivity.this, ShipmentInquiryActivity.class));
                break;
        }
    }

    @Override
    protected void setTextString() {
        tvTitle.setText(R.string.domestic_transfer);
        tvWaybillNo.setText(R.string.waybill_no);
        tvSelect.setText(R.string.select_guonei_kuaidi);
        etWaybillNo.setHint(R.string.please_input_waybill_no);
        btnInquiry.setText(R.string.confirm);

    }
}
