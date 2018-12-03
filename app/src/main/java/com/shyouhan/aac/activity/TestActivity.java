package com.shyouhan.aac.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.shyouhan.aac.R;
import com.shyouhan.aac.base.BaseActivity;

/**
 * Created by lcy on 2018/7/13.
 */

public class TestActivity extends BaseActivity {
    private EditText etInput;
    @Override
    protected void initView() {
        etInput = (EditText) findViewById(R.id.et_input);

        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String inputData = s.toString();
                String[] split = inputData.split("\n");
                if (null != split && split.length > 0) {
                    //上传数据
                    String barCode = split[split.length - 1];
                    //，，
                }
            }
        });

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
        return R.layout.activity_test;
    }

    @Override
    protected void setTextString() {

    }
}
