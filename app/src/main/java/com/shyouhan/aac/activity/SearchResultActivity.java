package com.shyouhan.aac.activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.shyouhan.aac.R;
import com.shyouhan.aac.adapter.SearchResultAdapter;
import com.shyouhan.aac.base.BaseActivity;
import com.shyouhan.aac.bean.PackStatusBean;
import com.shyouhan.aac.constant.AppConstant;
import com.shyouhan.aac.widget.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索结果
 */
public class SearchResultActivity extends BaseActivity {
    private TextView tvWaybillNo;
    private RecyclerView recyclerView;
    private SearchResultAdapter adapter;
    private String packId;
    private ArrayList<PackStatusBean.StatusBean> statusList = new ArrayList<>();

    @Override
    protected void initView() {
        iniTitlelayout();
        tvTitle.setText(R.string.search_result);
        findId();

        PackStatusBean packStatusBean = (PackStatusBean) getIntent().getSerializableExtra(AppConstant.STATUSBEAN);
        LogUtils.a(AppConstant.TAG,packStatusBean.toString());
        packId = packStatusBean.getPack();
        tvWaybillNo.setText(getString(R.string.waybill_no3)+" "+ packId);

        initData(packStatusBean);
        initRv();
    }

    private void initData(PackStatusBean packStatusBean) {
       List<PackStatusBean.StatusBean> status =  packStatusBean.getData();
        for (PackStatusBean.StatusBean statusBean:
                status) {
            if (statusBean.getTime() != null) {
                statusList.add(statusBean);
            }
        }
    }

    private void initRv() {
        adapter = new SearchResultAdapter(statusList);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchResultActivity.this));
        recyclerView.setAdapter(adapter);
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
