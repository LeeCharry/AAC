package com.shyohan.aac.activity;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.example.tulib.util.utils.DeviceUtil;
import com.shyohan.aac.base.BaseActivity;
import com.shyohan.aac.mvp.contract.LoginContract;
import com.shyohan.aac.mvp.presenter.LoginPresenter;

import acc.tulip.com.accreputation.R;

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
    @Override
    protected void initView() {
        iniTitlelayout();
        tvTitle.setText(R.string.login);
        findId();
//        onBack("");
        setTextString();
        initPresenter();
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
    public void onLogin() {
        ToastUtils.showShort("登录成功");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_is_see:  //密码是否可见

                break;
            case R.id.iv_clear:  //清除用户名

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
}
