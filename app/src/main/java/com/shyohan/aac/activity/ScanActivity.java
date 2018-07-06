package com.shyohan.aac.activity;


import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.blankj.utilcode.util.ToastUtils;
import com.shyohan.aac.base.BaseActivity;
import acc.tulip.com.accreputation.R;
/**
 * 扫描
 */
public class ScanActivity extends BaseActivity {
    private EditText etUsername;
    private ImageView ivClear;
    private EditText etPwd;
    private ImageView ivIsSee;
    private Button btnConfirm;
    @Override
    protected void initView() {
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        iniTitlelayout();
        tvTitle.setText(R.string.login);
        findId();
    }
    private void findId() {
        etUsername = findViewById(R.id.et_username);
        ivClear = findViewById(R.id.iv_clear);
        etPwd = findViewById(R.id.et_pwd);
        ivIsSee = findViewById(R.id.iv_is_see);
        btnConfirm = findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("登陆成功");
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void setTextString() {

    }
}
