package com.shyohan.aac.activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shyohan.aac.adapter.ExpressChoiceAdapter;
import com.shyohan.aac.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import acc.tulip.com.accreputation.R;
/**
 * 快递选择
 */
public class ExpressChoiceActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private Button btnConfirm;
    private ExpressChoiceAdapter adapter;
    private List<String> datas = new ArrayList<>();

    @Override
    protected void initView() {
        iniTitlelayout();
        tvTitle.setText(R.string.express_choice);
        findId();
//        onBack("");
        setTextString();
        initRv();
    }

    private void initRv() {
        datas.add("顺丰快递");
        datas.add("韵达快递");
        datas.add("申通快递");
        datas.add("圆通快递");
        adapter = new ExpressChoiceAdapter(datas);
        recyclerView.setLayoutManager(new LinearLayoutManager(ExpressChoiceActivity.this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showShort(" "+position);
            }
        });
    }
    private void findId() {
        recyclerView = findViewById(R.id.rv);
        btnConfirm = findViewById(R.id.btn_confirm);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_express_choice;
    }

    @Override
    protected void setTextString() {
        tvTitle.setText(R.string.express_choice);
    }
}
