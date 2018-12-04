package com.shyouhan.aac.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.shyouhan.aac.R;
import com.shyouhan.aac.adapter.ScanGunRvAdapter;
import com.shyouhan.aac.base.BaseActivity;
import com.shyouhan.aac.widget.ToastUtils;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

/**
 * Created by lcy on 2018/7/13.
 */

public class TestActivity extends BaseActivity {
    private AutoRelativeLayout rlTitle;
    private EditText etInput;
    private RecyclerView recyclerview;
    private List<String> barcodeList = new ArrayList<>();
    private ScanGunRvAdapter adapter;

    @Override
    protected void initView() {
        iniTitlelayout();
        rlTitle = (AutoRelativeLayout) findViewById(R.id.rl_title);
        etInput = (EditText) findViewById(R.id.et_input);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        tvTitle.setText("扫码枪");
        tvTitle.setTextColor(Color.BLACK);
        
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
         adapter = new ScanGunRvAdapter(barcodeList);
        recyclerview.setAdapter(adapter);
        
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
                    final String barCode = split[split.length - 1];
                    //
                   loadingDailog.show();
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadingDailog.hide();
                            if (barcodeList.contains(barCode)) {
                                ToastUtils.showShort("请勿重复扫描");
                                return;
                            }
                            barcodeList.add(0,barCode);
                            adapter.notifyDataSetChanged();
                        }
                    },1500);
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
