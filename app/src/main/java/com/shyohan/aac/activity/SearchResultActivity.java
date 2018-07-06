package com.shyohan.aac.activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shyohan.aac.adapter.SearchResultAdapter;
import com.shyohan.aac.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import acc.tulip.com.accreputation.R;

/**
 * 搜索结果
 */
public class SearchResultActivity extends BaseActivity {
    private TextView tvWaybillNo;
    private RecyclerView recyclerView;
    private SearchResultAdapter adapter;
    private List<String> datas = new ArrayList<>();

    @Override
    protected void initView() {
        iniTitlelayout();
        tvTitle.setText(R.string.search_result);
        findId();
        initRv();
    }

    private void initRv() {
        for (int i = 0; i < 3 ; i++) {
            datas.add("重要事项"+(i+1));
        }
        adapter = new SearchResultAdapter(datas);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchResultActivity.this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showShort(" "+position);
            }
        });
    }
    private void findId() {
        tvWaybillNo = findViewById(R.id.tv_waybill_no);
        recyclerView = findViewById(R.id.rv);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void setTextString() {

    }
}
