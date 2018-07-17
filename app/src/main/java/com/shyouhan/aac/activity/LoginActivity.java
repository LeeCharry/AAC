package com.shyouhan.aac.activity;


import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.tulib.util.utils.DeviceUtil;
import com.shyouhan.aac.R;
import com.shyouhan.aac.base.BaseActivity;
import com.shyouhan.aac.mvp.contract.LoginContract;
import com.shyouhan.aac.mvp.presenter.LoginPresenter;
import com.shyouhan.aac.widget.ToastUtils;


/**
 * 登录
 */
public class LoginActivity extends BaseActivity implements LoginContract.View ,View.OnClickListener{
    private EditText etUsername;
    private ImageView ivClear;
    private EditText etPwd;
    private ImageView ivIsSee;
    private Button btnConfirm;
    private LoginPresenter presenter;
    private boolean isPwdSee = false;


    @Override
    protected void initView() {
        iniTitlelayout();
        tvTitle.setText(R.string.login);
        findId();
        setTextString();
        initPresenter();
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
    private void initPresenter() {
        presenter = new LoginPresenter(LoginActivity.this,LoginActivity.this);
    }


    private void findId() {
        etUsername = findViewById(R.id.et_username);
        ivClear = findViewById(R.id.iv_clear);
        etPwd = findViewById(R.id.et_pwd);
        ivIsSee = findViewById(R.id.iv_is_see);
        btnConfirm = findViewById(R.id.btn_confirm);
        ivClear.setOnClickListener(this);
        ivIsSee.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);



    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void setTextString() {
        tvTitle.setText(R.string.login);
        etUsername.setHint(R.string.please_input_name_or_phoneNo);
        etPwd.setHint(R.string.please_input_pwd);
        btnConfirm.setText(R.string.confirm);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_is_see:  //密码是否可见
                if (!isPwdSee) {
                    isPwdSee = true;
                    ivIsSee.setImageResource(R.mipmap.ic_see);
                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    isPwdSee = false;
                    ivIsSee.setImageResource(R.mipmap.ic_unsee);
                    etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                etPwd.setSelection(etPwd.getText().toString().length());
                break;
            case R.id.iv_clear:  //清除用户名
                etUsername.setText("");
                break;
            case R.id.btn_confirm:  //登录
                if (  checkInfo()){
                    presenter.login(etUsername.getText().toString().trim(),etPwd.getText().toString().trim(), DeviceUtil.getUniqueId(LoginActivity.this));
                }
                break;
        }
    }

    /**
     * 检验输入的信息
     * @return
     */
    private boolean checkInfo() {
        String username = etUsername.getText().toString();
        String pwd = etPwd.getText().toString();
        if (TextUtils.isEmpty(username)) {
            ToastUtils.showShort(R.string.username_must_not_empty);
            return false;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showShort(R.string.pwd_must_not_empty);
            return false;
        }
        return true;
    }

    @Override
    public void onLoginSucess() {
//        setResult(AppConstant.RESULT_CODE_LOGIN_SUCCESS);
        startActivity(new Intent(this,MainActivity.class));
        LoginActivity.this.finish();
    }

    @Override
    public void onLoginFailed(String error) {
        ToastUtils.showShort(error);
    }
}
