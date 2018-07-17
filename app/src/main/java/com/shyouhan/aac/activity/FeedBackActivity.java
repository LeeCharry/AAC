package com.shyouhan.aac.activity;


import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.tulib.util.utils.RegexUtils;
import com.shyouhan.aac.R;
import com.shyouhan.aac.base.BaseActivity;
import com.shyouhan.aac.mvp.contract.FeedbackContract;
import com.shyouhan.aac.mvp.presenter.FeedbackPresenter;
import com.shyouhan.aac.widget.ToastUtils;


/**
 * 意见反馈
 */
public class FeedBackActivity extends BaseActivity implements FeedbackContract.View{
    private EditText etName;
    private EditText etPhone;
    private EditText etContent;
    private Button btnSubmit;
    private FeedbackPresenter presenter;

    @Override
    protected void initView() {
        iniTitlelayout();
        findId();
        presenter = new FeedbackPresenter(FeedBackActivity.this,FeedBackActivity.this);
        setTextString();
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }
    private void findId() {
        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_phone);
        etContent = findViewById(R.id.et_content);
        btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInfo()) {
                    presenter.feedback(etName.getText().toString(),etPhone.getText().toString(),etContent.getText().toString());
                }
            }
        });
    }

    private boolean checkInfo() {
        String phone = etPhone.getText().toString();
        if (TextUtils.isEmpty(  etName.getText().toString())) {
            ToastUtils.showShort(R.string.name_is_not_empty);
            return false;
        }
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort(R.string.phone_is_not_empty);
            return false;
        }
        if (TextUtils.isEmpty(  etContent.getText().toString())) {
            ToastUtils.showShort(R.string.content_is_not_empty);
            return false;
        }
        if (!RegexUtils.isMobileSimple(phone) && !RegexUtils.isMobileExact(phone) && !RegexUtils.isTWMobile(phone)) {
            ToastUtils.showShort(R.string.phone_is_uncorrent);
            return false;
        }
        return true;
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

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
    public void onFeedbackSuccess(String successMsg) {
        ToastUtils.showShort(successMsg);
        FeedBackActivity.this.finish();
    }

    @Override
    public void onFeedbackFailed(String error) {
        ToastUtils.showShort(error);
    }
}
