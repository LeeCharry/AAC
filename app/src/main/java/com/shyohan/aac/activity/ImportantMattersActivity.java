package com.shyohan.aac.activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shyohan.aac.adapter.ImportantMattersRvAdapter;
import com.shyohan.aac.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import acc.tulip.com.accreputation.R;

/**
 * 重要事项
 */
public class ImportantMattersActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private ImportantMattersRvAdapter adapter;
    private List<String> datas = new ArrayList<>();

    @Override
    protected void initView() {
        iniTitlelayout();
        tvTitle.setText(R.string.important_matters);
        findId();
        onBack("");
        initRv();
    }

    private void initRv() {
        for (int i = 0; i < 3 ; i++) {
            datas.add("重要事项"+(i+1));
        }
        adapter = new ImportantMattersRvAdapter(datas);
        recyclerView.setLayoutManager(new LinearLayoutManager(ImportantMattersActivity.this));
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
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_important_matters;
    }

    @Override
    protected void setTextString() {
        tvTitle.setText(R.string.important_matters);
    }
}
