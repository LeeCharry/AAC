package com.shyohan.aac.activity;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shyohan.aac.base.BaseActivity;

import acc.tulip.com.accreputation.R;

/**
 * 意见反馈
 */
public class FeedBackActivity extends BaseActivity {
    private EditText etName;
    private EditText etPhone;
    private EditText etContent;
    private Button btnSubmit;

    @Override
    protected void initView() {
        iniTitlelayout();
        tvTitle.setText(R.string.feed_back);
        findId();
//      onBack("");
        setTextString();
    }
    private void findId() {
        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_phone);
        etContent = findViewById(R.id.et_content);
        btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedBackActivity.this.finish();
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void setTextString() {
        tvTitle.setText(R.string.feed_back);
        etName.setHint(R.string.please_input_name);
        etPhone.setHint(R.string.please_input_phone_no);
        etContent.setHint(R.string.please_input_feed_back_content);
        btnSubmit.setText(R.string.submit);
    }
}
