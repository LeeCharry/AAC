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
 * 派送
 */
public class DeliveryActivity extends BaseActivity {

    private ImageView ivSelect;
    private TextView tvDate;
    private TextView tvIsSign;
    private Button btnConfirm;

    @Override
    protected void initView() {
        iniTitlelayout();
        tvTitle.setText(R.string.delivery);
        findId();
//        onBack("");
        setTextString();
    }
    private void findId() {
        ivSelect = findViewById(R.id.iv_select);
        tvDate = findViewById(R.id.tv_date);
        tvIsSign = findViewById(R.id.tv_is_sign);
        btnConfirm = findViewById(R.id.btn_confirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeliveryActivity.this.finish();
            }
        });

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_delivery;
    }


    @Override
    protected void setTextString() {
        tvTitle.setText(R.string.delivery);
        tvIsSign.setText(R.string.yiqianshou);
        btnConfirm.setText(R.string.confirm);
    }
}
